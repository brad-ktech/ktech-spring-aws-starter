package com.ktech.starter.clio.apis;

import com.google.gson.Gson;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.clio.messages.Request;
import com.ktech.starter.clio.messages.Result;
import com.ktech.starter.clio.models.CalendarEntry;
import com.ktech.starter.exceptions.ClioException;
import com.ktech.starter.exceptions.RetryThrowable;
import com.ktech.starter.vaults.ClioConfigurationVault;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AbstractRestAPI {

    @Autowired
    protected ClioConfigurationVault vault;

    private AtomicInteger counter = new AtomicInteger(0);
    private Integer maxTries = 5;

    protected <T> T doGet(Class<T> clazz, Long id) {

        T ret = null;
        try(CloseableHttpClient client = HttpClients.createDefault()) {


            counter.getAndIncrement();
            if(counter.get() > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }

            URIBuilder builder = new URIBuilder();
            builder.setHost(getUrl(clazz));
            builder.setPath(getPathFromClass(clazz));
            builder.setPath(id.toString());
            String url =  builder.build().toURL().toString();
            HttpGet get = new HttpGet(url);
            get.setHeaders(getBasicHeaders());
            HttpResponse response = client.execute(get);
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<T> result = getResultFromResponse(response);
            return result.getData();

        }catch(RetryThrowable retry){
            if(counter.get() < maxTries){
                ret = doGet(clazz, id);
            }


        } catch (IOException | InterruptedException | ClioException | URISyntaxException e) {
            log.error(e.getMessage());
        }
        return ret;




    }


    protected <T> T  doPost(T t) {


        T ret = null;
        try(CloseableHttpClient client = HttpClients.createDefault()) {

            counter.getAndIncrement();
            if(counter.get() > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpPost post = new HttpPost(getUrl(t.getClass()));
            post.setHeaders(getBasicHeaders());
            Request<T> request = Request.of(t);
            post.setEntity(new StringEntity(new Gson().toJson(request)));
            HttpResponse response = client.execute(post);
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<T> result = getResultFromResponse(response);
            ret =  result.getData();


        }catch(RetryThrowable retry){
             ret = doPost(t);

        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;

    }

    protected <T> T doPatch(T t){

        T ret = null;
        try(CloseableHttpClient client = HttpClients.createDefault()) {

            counter.getAndIncrement();
            if(counter.get() > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpPatch patch = new HttpPatch(getUrl(t.getClass()));
            patch.setHeaders(getBasicHeaders());
            Request<T> request = Request.of(t);
            patch.setEntity(new StringEntity(new Gson().toJson(request)));
            HttpResponse response = client.execute(patch);
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<T> result = getResultFromResponse(response);
            ret =  result.getData();


        }catch(RetryThrowable retry){
            ret = doPost(t);

        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;

    }

    protected Header[] getBasicHeaders(){

        Header[] headers = new Header[2];
        headers[0] = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        headers[1] = new BasicHeader("Authorization", "Bearer " + vault.getAuthToken());
        return headers;

    }


    protected <T> Result<T> getResultFromResponse(HttpResponse response) throws IOException, RetryThrowable, InterruptedException, ClioException {

        String json = StringUtils.EMPTY;
        if (response.getStatusLine().getStatusCode() == 429) {
            // sleep or load balance


            Optional<Header> opt = Arrays.stream(response.getAllHeaders()).filter(h -> {

                BasicHeader bh = (BasicHeader)h;
                return bh.getName().contains("Retry");

            }).findFirst();

            int retry = 0;
            if(opt.isPresent()){

                BasicHeader retryHeader = (BasicHeader)opt.get();
                retry = Integer.parseInt(retryHeader.getValue());
            }


            System.out.println("Retry-After: ["+ retry +"]");
            if (retry > 0) {

                TimeUnit.SECONDS.sleep(retry+1);
                throw new RetryThrowable();

            }
        } else if (response.getStatusLine().getStatusCode() == 401) {
            System.out.println("received 401: reloading Client Manager");
            //throw a client failure exception
            //TODO Request is still going to fail due to aTarget referencing old client
            //TODO even calling it recursively here wont help since the aTarget was passed in
        } else if (response.getStatusLine().getStatusCode() != 200 &&response.getStatusLine().getStatusCode() != 201) {
            //throw a failure exception
            //System.err.println("WebTarget: " + aTarget);
            //System.err.println("Entity: " + anEntity);
            throw new ClioException("Status 200 Failure");
        }else{
            HttpEntity entity = response.getEntity();
            json = IOUtils.toString(entity.getContent(), "UTF-8");
        }

        return new Gson().fromJson(json, Result.class);

    }


    protected <T> String getUrl(Class<T> clazz){

        String host = vault.getAPITarget();
        String path = this.getPathFromClass(CalendarEntry.class);
        String url = host + "/" +  path;
        return url;

    }


    protected String encodeFields(String aFieldString) {
        return aFieldString.replace("{", "%7B").replace("}", "%7D");
    }


    protected <T> String getFieldsFromClass(Class<T> clazz){

        String ret = StringUtils.EMPTY;
        if(clazz.isAnnotationPresent(ApiFields.class)){
            ApiFields ann = clazz.getAnnotation(ApiFields.class);
            ret = ann.fields();


        }
        return ret;
    }

    protected <T> String getPathFromClass(Class<T> clazz){

        String ret = StringUtils.EMPTY;
        if(clazz.isAnnotationPresent(ApiPath.class)){
            ApiPath ann = clazz.getAnnotation(ApiPath.class);
            ret = ann.path();


        }
        return ret;


    }
}

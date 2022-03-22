package com.ktech.starter.clio.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.clio.messages.Request;
import com.ktech.starter.clio.messages.Result;
import com.ktech.starter.clio.models.IDObject;
import com.ktech.starter.exceptions.ClioException;
import com.ktech.starter.exceptions.RetryThrowable;
import com.ktech.starter.vaults.ClioConfigurationVault;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AbstractRestAPI {

    @Autowired
    protected ClioConfigurationVault vault;

    @Autowired
    protected Client clio;

    @Value("${clio.retries:5}")
    private Integer maxTries;

    private AtomicInteger counter = new AtomicInteger(0);


    protected <T> T doGet(Class<T> clazz, Long id) {

        WebTarget target = clio.target(vault.getAPITarget())
                                  .path(getPathFromClass(clazz))
                                  .path(id.toString())
                                  .queryParam("fields", getFieldsFromClass(clazz));

        return doGet(clazz, target);


    }

    protected <T> T doGet(Class<T> clazz, String query){

        WebTarget target = clio.target(vault.getAPITarget())
                                 .path(getPathFromClass(clazz))
                                 .queryParam("query", query)
                                 .queryParam("fields", getFieldsFromClass(clazz));
        return doGet(clazz, target);


    }

    protected <T> T doGet(Class<T> clazz, Map<String, String> params){

        WebTarget target = clio.target(vault.getAPITarget())
                               .path(getPathFromClass(clazz))
                               .queryParam("fields", getFieldsFromClass(clazz));

        for(String key : params.keySet()){
            target = target.queryParam(key, params.get(key));
        }
        return doGet(clazz, target);



    }


    protected <T> IDObject  doPost(T t) {


        WebTarget target = clio.target(vault.getAPITarget())
                               .path(getPathFromClass(t.getClass()));

        return doPost(t, target);


    }

    protected <T> IDObject doPatch(T  t){

        IDObject entity = (IDObject)t;
        WebTarget target = clio.target(vault.getAPITarget())
                                  .path(getPathFromClass(t.getClass()))
                                  .queryParam("id", entity.getId());

        return doPatch(t, target);


    }

    protected Header[] getBasicHeaders(){

        Header[] headers = new Header[2];
        headers[0] = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        headers[1] = new BasicHeader("Authorization", "Bearer " + vault.getAuthToken());
        return headers;

    }


    protected <T> Result<T> getResultFromResponse(Class<T> clazz, HttpResponse response) throws IOException, RetryThrowable, InterruptedException, ClioException {

        Result<T> result = null;
        if (response.getStatusLine().getStatusCode() == 429) {
            // sleep or load balance


            Optional<Header> opt = Arrays.stream(response.getAllHeaders()).filter(h -> {
                return h.getName().contains("Retry");
            }).findFirst();

            int retry = 0;
            if(opt.isPresent()){

                Header retryHeader = opt.get();
                retry = Integer.parseInt(retryHeader.getValue());
            }


            System.out.println("Retry-After: ["+ retry +"]");
            if (retry > 0) {

                TimeUnit.SECONDS.sleep(retry+1);
                throw new RetryThrowable();

            }
        } else if (response.getStatusLine().getStatusCode() == 401) {
            System.out.println("Received 401");
            throw new ClioException("Recieved 401 :  Unauthorized");

        } else if (response.getStatusLine().getStatusCode() != 200 &&response.getStatusLine().getStatusCode() != 201) {
            //throw a failure exception
            //System.err.println("WebTarget: " + aTarget);
            //System.err.println("Entity: " + anEntity);
            throw new ClioException("Status 200: Failure");
        }else{

            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            ;

            result =  gson.fromJson(content, TypeToken.getParameterized(Result.class, clazz).getType());


        }

        return result;

    }


    protected String encodeFields(String aFieldString) {
        return aFieldString.replace("{", "%7B").replace("}", "%7D");
    }


    protected <T> String getFieldsFromClass(Class<T> clazz){

        String ret = StringUtils.EMPTY;
        if(clazz.isAnnotationPresent(ApiFields.class)){
            ApiFields ann = clazz.getAnnotation(ApiFields.class);
            ret = encodeFields(ann.fields());

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


    private <T> IDObject doPatch(T t, WebTarget target){

        IDObject ret = null;
        try(CloseableHttpClient client = HttpClients.createDefault()) {

            counter.getAndIncrement();
            if(counter.get() > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpPatch patch = new HttpPatch(target.getUri().toString());
            patch.setHeaders(getBasicHeaders());
            Request<T> request = Request.of(t);
            patch.setEntity(new StringEntity(new Gson().toJson(request)));
            HttpResponse response = client.execute(patch);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<IDObject> result = getResultFromResponse(IDObject.class, response);
            ret =  result.getData();


        }catch(RetryThrowable retry){
            ret = doPatch(t, target);

        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;

    }

    private <T> IDObject doPost(T t, WebTarget target) {

        IDObject ret = null;
        try(CloseableHttpClient client = HttpClients.createDefault()) {

            counter.getAndIncrement();
            if(counter.get() > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpPatch patch = new HttpPatch(target.getUri().toString());
            patch.setHeaders(getBasicHeaders());
            Request<T> request = Request.of(t);
            patch.setEntity(new StringEntity(new Gson().toJson(request)));
            HttpResponse response = client.execute(patch);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<IDObject> result = getResultFromResponse(IDObject.class, response);
            ret =  result.getData();


        }catch(RetryThrowable retry){
            ret = doPost(t, target);

        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;


    }

    private <T> T  doGet(Class<T> clazz, WebTarget target) {

        T ret = null;

System.out.println(target.getUri().toString());
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter.getAndIncrement();
            if(counter.get() > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpGet get = new HttpGet(target.getUri().toString());
            get.setHeaders(getBasicHeaders());
            HttpResponse response = client.execute(get);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<T> result = getResultFromResponse(clazz, response);
            ret = result.getData();

        }catch(RetryThrowable retry){
            if(counter.get() < maxTries){
                ret = doGet(clazz, target);
            }


        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;

    }
}

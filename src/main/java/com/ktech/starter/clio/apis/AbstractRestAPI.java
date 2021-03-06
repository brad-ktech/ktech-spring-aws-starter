package com.ktech.starter.clio.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ktech.starter.annotations.ApiFieldList;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.annotations.EncodedFields;
import com.ktech.starter.clio.messages.Request;
import com.ktech.starter.clio.messages.Result;
import com.ktech.starter.clio.messages.responses.BulkResult;
import com.ktech.starter.clio.messages.responses.ListResult;
import com.ktech.starter.clio.models.IDObject;
import com.ktech.starter.exceptions.ClioException;
import com.ktech.starter.exceptions.RetryThrowable;
import com.ktech.starter.utilities.Reflectotron;
import com.ktech.starter.vaults.ClioConfigurationVault;
import com.ktech.starter.vaults.ClioVault;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AbstractRestAPI {

    @Autowired
    protected ClioVault vault;

    @Autowired
    protected Client clio;

    @Value("${clio.retries:5}")
    private Integer maxTries;


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

    protected <T> byte[] doGetDownload(Class<T> clazz, Long anId) throws IOException {
        WebTarget target = clio.target(vault.getAPITarget())
                .path(getPathFromClass(clazz))
                .path(String.valueOf(anId))
                .path("download");
        return doGetDownload(target);
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

    protected <T> ListResult<T> doGetList(Class<T> clazz) {
        WebTarget target = clio.target(vault.getAPITarget())
                .path(getPathFromClass(clazz))
                .queryParam("fields", getFieldsFromClass(clazz));

        return doGetList(clazz, target);
    }




    protected <T> T  doPost(T t) {


        WebTarget target = clio.target(vault.getAPITarget())
                               .path(getPathFromClass(t.getClass()))
                               .queryParam("fields", getFieldsFromClass(t.getClass()));

        return doPost(t, target);


    }

    protected <T> boolean doDel(Class<T> clazz, Long id) {
        WebTarget target = clio.target(vault.getAPITarget())
                .path(getPathFromClass(clazz))
                .path(id.toString());

        return doDel(target);
    }

    protected <T> IDObject doPatch(T  t){

        IDObject entity = (IDObject)t;
        WebTarget target = clio.target(vault.getAPITarget())
                                  .path(getPathFromClass(t.getClass()))
                                  .queryParam("id", entity.getId());

        return doPatch(t, target);


    }

    protected <T> T doPatch(T t, Long id, Object data){
        WebTarget target = clio.target(vault.getAPITarget())
                .path(getPathFromClass(t.getClass()))
                .path(id.toString())
                .queryParam("fields", getFieldsFromClass(t.getClass()));

        return doPatch(t, target, data);
    }

    protected <T> BulkResult<T> doBulk(Class<T> clazz , LocalDateTime updatedSince, int tries, int aDelayInSeconds){

        BulkResult<T> result = new BulkResult<>();

        WebTarget target = clio.target(vault.getAPITarget())
                                .path(getPathFromClass(clazz))
                                .queryParam("updated_since", DateTimeFormatter.ISO_INSTANT.format(updatedSince))
                                .queryParam("fields", getFieldsFromClass(clazz));


        Map<String,String> requestHeaders = new HashMap<String,String>();
        requestHeaders.put("X-BULK","true");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        for (Map.Entry<String,String> entry: requestHeaders.entrySet()) {
            invocationBuilder.header(entry.getKey(),entry.getValue());
        }
        Response response = invocationBuilder.get();

        System.out.println("Response: " + response.getStatus());
        if (response.getStatus() == 202) { //accepted
            MultivaluedMap<String,String> headers = response.getStringHeaders();
            System.out.println("headers: " + headers.toString());
            System.out.println("Location: " + headers.getFirst("Location"));
            try {
                for (int i = 0; i< tries; i++) {
                    System.out.format("Waiting for bulk result[%d/%d]: %d\n",i+1, tries, aDelayInSeconds);
                    TimeUnit.SECONDS.sleep(aDelayInSeconds);

                    try {
                        Result<T> retz = loadBulkResult(clazz, headers.getFirst("Location"));
                        //BRAD
                        //parse return here

                    }
                    catch (Exception e) {
                        //System.out.println("Error processing bulk request: " + e.getMessage());
                    }
                    if (result != null)
                        return result;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


        return result;
    }


    private <T> Result<T> loadBulkResult(Class<T> clazz, String uri){
        Result<T> ret = null;
        int counter = 0;

        System.out.println(uri);
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter++;
            if(counter > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpGet get = new HttpGet(uri);
            get.setHeaders(getBasicHeaders());
            HttpResponse response = client.execute(get);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            ret = getResultFromResponse(clazz, response);


        }catch(RetryThrowable retry){
            if(counter < maxTries){
                ret = loadBulkResult(clazz, uri);
            }


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


            result =  gson.fromJson(content, TypeToken.getParameterized(Result.class, clazz).getType());


        }

        return result;

    }

    protected <T> ListResult<T> getListResultFromResponse(Class<T> clazz, HttpResponse response) throws IOException, RetryThrowable, InterruptedException, ClioException {
        ListResult<T> result = null;
        if (response.getStatusLine().getStatusCode() == 429) {
            // sleep or load balance
            Optional<Header> opt = Arrays.stream(response.getAllHeaders()).filter(h -> {
                return h.getName().contains("Retry");
            }).findFirst();

            int retry = 0;
            if(opt.isPresent()) {
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
            throw new ClioException("Status 200: Failure");
        } else {
            String content = EntityUtils.toString(response.getEntity());
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            result = gson.fromJson(content, TypeToken.getParameterized(ListResult.class, clazz).getType());
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

        }else if(clazz.isAnnotationPresent(ApiFieldList.class)){

            try {
                Method m = Reflectotron.getFirstMethodWithAnnotation(clazz, EncodedFields.class);
                ret = (String)m.invoke(clazz.newInstance());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                log.error("Could not get Fieldlist from object->" + clazz.getName());

            }
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
        int counter = 0;

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter++;
            if(counter > maxTries){
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

    private <T> T doPatch(T t, WebTarget target, Object data){
        T ret = null;
        int counter = 0;

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter++;
            if(counter > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpPatch patch = new HttpPatch(target.getUri().toString());
            patch.setHeaders(getBasicHeaders());
            Request<T> request = Request.of(t);
            request.setData(data);
            patch.setEntity(new StringEntity(new Gson().toJson(request)));
            HttpResponse response = client.execute(patch);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<T> result = (Result<T>)getResultFromResponse(t.getClass(), response);
            ret =  result.getData();


        }catch(RetryThrowable retry){
            ret = doPatch(t, target, data);

        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;

    }

    private <T> T doPost(T t, WebTarget target) {
        T ret = null;
        int counter = 0;

        System.out.println(target.getUri().toString());
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter++;
            if(counter > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpPost post = new HttpPost(target.getUri().toString());
            post.setHeaders(getBasicHeaders());
            Request<T> request = Request.of(t);
            post.setEntity(new StringEntity(new Gson().toJson(request)));
            HttpResponse response = client.execute(post);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            Result<T> result = (Result<T>)getResultFromResponse(t.getClass(), response);
            ret =  result.getData();


        }catch(RetryThrowable retry){
            ret = doPost(t, target);

        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;


    }

    private boolean doDel(WebTarget target) {
        boolean success = false;
        int counter = 0;

        System.out.println(target.getUri().toString());
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter++;
            if(counter > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpDelete del = new HttpDelete(target.getUri().toString());
            del.setHeaders(getBasicHeaders());
            HttpResponse response = client.execute(del);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            success = response.getStatusLine().getStatusCode() == 204;
        } catch (IOException | ClioException e) {
            log.error(e.getMessage());
        }
        return success;
    }

    private <T> T  doGet(Class<T> clazz, WebTarget target) {
        T ret = null;
        int counter = 0;

        System.out.println(target.getUri().toString());
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter++;
            if(counter > maxTries){
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
            if(counter < maxTries){
                ret = doGet(clazz, target);
            }


        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;

    }

    protected byte[] doGetDownload(WebTarget target) throws IOException {
        return doGetDownload(target, maxTries);
    }

    private byte[] doGetDownload(WebTarget target, int tryCount) throws IOException {
        byte[] ret = null;

        System.out.println(target.getUri().toString());

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_OCTET_STREAM);
        for(Header header : getBasicHeaders()) {
            invocationBuilder.header(header.getName(), header.getValue());
        }
        Response response = invocationBuilder.get();

        MultivaluedMap<String, String> headers = response.getStringHeaders();

        if(response.getStatus() == 429 && tryCount < maxTries) {
            String retry = headers.getFirst("Retry-After");
            System.out.println("Retry-After: [" + retry + "]");
            int timeInSeconds = Integer.parseInt(retry);
            try {
                TimeUnit.SECONDS.sleep(timeInSeconds + 1);
            } catch(Exception e) {
                e.printStackTrace();
            }
            ret = doGetDownload(target, tryCount + 1);
        } else if(response.getStatus() == 401 && tryCount < maxTries) {
            System.out.println("Received 401");
        } else {
            InputStream is = (InputStream)response.getEntity();

            try {
                ret = IOUtils.toByteArray(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                is.close();
            }
        }

        return ret;
    }

    protected <T> ListResult<T> doGetList(Class<T> clazz, WebTarget target) {
        ListResult<T> ret = null;
        int counter = 0;

        System.out.println(target.getUri().toString());
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            counter++;
            if(counter > maxTries){
                throw new ClioException("Exceeded maximum number of retries");
            }
            HttpGet get = new HttpGet(target.getUri().toString());
            get.setHeaders(getBasicHeaders());
            HttpResponse response = client.execute(get);
            log.info("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
            ret = getListResultFromResponse(clazz, response);

        }catch(RetryThrowable retry){
            if(counter < maxTries){
                ret = doGetList(clazz, target);
            }


        } catch (IOException | InterruptedException | ClioException e) {
            log.error(e.getMessage());
        }
        return ret;

    }


}

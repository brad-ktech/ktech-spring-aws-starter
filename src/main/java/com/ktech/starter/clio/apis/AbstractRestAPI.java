package com.ktech.starter.clio.apis;

import com.google.gson.Gson;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.clio.messages.Result;
import com.ktech.starter.clio.models.CalendarEntry;
import com.ktech.starter.clio.models.Request;
import com.ktech.starter.vaults.ClioConfigurationVault;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class AbstractRestAPI {

    @Autowired
    protected ClioConfigurationVault vault;

   protected String encodeFields(String aFieldString) {
        return aFieldString.replace("{", "%7B").replace("}", "%7D");
    }


    protected  <T> String getFieldsFromClass(Class<T> clazz){

        String ret = StringUtils.EMPTY;
        if(clazz.isAnnotationPresent(ApiFields.class)){
            ApiFields ann = clazz.getAnnotation(ApiFields.class);
            ret = ann.fields();


        }
        return ret;
    }

    protected  <T> String getPathFromClass(Class<T> clazz){

        String ret = StringUtils.EMPTY;
        if(clazz.isAnnotationPresent(ApiPath.class)){
            ApiPath ann = clazz.getAnnotation(ApiPath.class);
            ret = ann.path();


        }
        return ret;


    }

    protected <T> void doPost(T t) throws IOException {
System.out.println("in do post");
        try(CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost post = new HttpPost(getUrl(t.getClass()));
            post.setHeaders(getBasicHeaders());
            Request<T> request = new Request<>(t);
            post.setEntity(new StringEntity(new Gson().toJson(request)));
            System.out.println("after post entity");

            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String json = IOUtils.toString(entity.getContent(), "UTF-8");
            Result<CalendarEntry> result = new Gson().fromJson(json, Result.class);

            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());

        }




    }

    private Header[] getBasicHeaders(){

        Header[] headers = new Header[2];
        headers[0] = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        headers[1] = new BasicHeader("Authorization", "Bearer " + vault.getAuthToken());
        return headers;

    }





    protected <T> String getUrl(Class<T> clazz){

        String host = vault.getAPITarget();
        String path = this.getPathFromClass(CalendarEntry.class);
        String url = host + "/" +  path;
        return url;

    }
}

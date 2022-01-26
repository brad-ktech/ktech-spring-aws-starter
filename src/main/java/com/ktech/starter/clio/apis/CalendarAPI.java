package com.ktech.starter.clio.apis;


import com.google.gson.Gson;
import com.ktech.starter.clio.messages.Result;
import com.ktech.starter.clio.models.CalendarEntry;
import com.ktech.starter.clio.models.Request;
import com.ktech.starter.vaults.ClioConfigurationVault;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarAPI extends AbstractRestAPI{


    private RestTemplate rest;

    public CalendarAPI(@Autowired ClioConfigurationVault vault, @Autowired RestTemplate rest){

        this.vault = vault;
        this.rest = rest;


    }

    public <T> CalendarEntry saveCalendarEntry(CalendarEntry ce) throws IOException, URISyntaxException {


        try(CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost post = new HttpPost(getUrl(ce.getClass()));
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            post.setHeader("Authorization", "Bearer " + vault.getAuthToken());

            Request<CalendarEntry> request = new Request<>(ce);
            post.setEntity(new StringEntity(new Gson().toJson(request)));


            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String json = IOUtils.toString(entity.getContent(), "UTF-8");
            Result<CalendarEntry> result = new Gson().fromJson(json, Result.class);

            System.out.println(response.getStatusLine().getReasonPhrase());
            System.out.println("[STATUS] " + response.getStatusLine().getStatusCode());
            return result.getData();
        }

    }




}

package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.CalendarEntry;
import com.ktech.starter.vaults.ClioConfigurationVault;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarAPI extends AbstractRestAPI{

    private ClioConfigurationVault vault;

    public CalendarAPI(@Autowired ClioConfigurationVault vault){

        this.vault = vault;

    }

    public void saveCalendarEntry(CalendarEntry ce) throws IOException, URISyntaxException {
        CloseableHttpClient client = HttpClients.createDefault();

        String host = vault.getAPITarget();
        String path = this.getPathFromClass(CalendarEntry.class);
        String url = host + "/" +  path;

        HttpPost post = new HttpPost(url);
        post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        post.setHeader("authorization", "Bearer:" + vault.getAuthToken());
        post.setEntity(new StringEntity(ce.toJson()));

        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        System.out.println("[STATUS] " +response.getStatusLine().getStatusCode());

    }




}

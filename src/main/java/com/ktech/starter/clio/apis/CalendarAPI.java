package com.ktech.starter.clio.apis;


import com.google.gson.Gson;
import com.ktech.starter.clio.messages.Result;
import com.ktech.starter.clio.models.CalendarEntry;
import com.ktech.starter.clio.models.Request;
import com.ktech.starter.vaults.ClioConfigurationVault;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class CalendarAPI extends AbstractRestAPI{


    public <T> void saveCalendarEntry(CalendarEntry ce) throws IOException, URISyntaxException {


        System.out.println("in CalendarAPI");
            doPost(ce);

    }




}

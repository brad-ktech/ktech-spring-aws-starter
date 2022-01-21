package com.ktech.starter.apis;

import com.ktech.starter.models.Matter;
import com.ktech.starter.services.ClioService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;


@Service
public class MatterApi extends ClioService {



    public Matter getMatterById(Long id) throws URISyntaxException {

        return getById(Matter.class, id);


    }

    public Matter getMatterById(Long id, String... fields) throws URISyntaxException {

        return getById(Matter.class, id, fields);


    }



}

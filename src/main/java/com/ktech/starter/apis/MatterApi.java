package com.ktech.starter.apis;

import com.ktech.starter.models.Matter;
import com.ktech.starter.services.ClioApi;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;


@Service
public class MatterApi extends ClioApi {



    public Matter getMatterById(Long id) throws URISyntaxException {

        return getById(Matter.class, id);


    }

    public Matter getMatterById(Long id, String... fields) throws URISyntaxException {

        return getById(Matter.class, id, fields);


    }



}

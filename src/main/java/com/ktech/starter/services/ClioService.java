package com.ktech.starter.services;

import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.vaults.ClioConfigurationVault;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.locks.ReentrantLock;


public class ClioService {


    protected static ReentrantLock lock = new ReentrantLock();

    @Autowired
    protected RestTemplate rest;

    @Autowired
    protected ClioConfigurationVault vault;


    protected  <T> T getById(Class<T> clazz, Long id) throws URISyntaxException {

        synchronized (lock) {
            URIBuilder builder = new URIBuilder();
            URI uri = builder.setPath(getPathFromClass(clazz))
                    .setPath(id.toString())
                    .setParameter("fields", encodeFields(getFieldsFromClass(clazz))).build();

            return rest.getForObject(uri, clazz);
        }
    }

    protected  <T> T getById(Class<T> clazz, Long id, String... fields) throws URISyntaxException {

        synchronized (lock) {
            URIBuilder builder = new URIBuilder();
            StringUtils.join(fields, ",");
            URI uri = builder.setPath(getPathFromClass(clazz))
                    .setPath(id.toString())
                    .setParameter("fields", encodeFields(StringUtils.join(fields, ","))).build();

            return rest.getForObject(uri, clazz);
        }

    }


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

}

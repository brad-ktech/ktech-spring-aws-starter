package com.ktech.starter.clio.apis;


import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class ClioApi extends AbstractRestAPI{



    public <T> T getById(Class<T> clazz, Long id){

        return getById(clazz, id);


    }


    public <T> T post(T t){

        return doPost(t);
    }

    public <T> T patch(T t){

        return doPatch(t);
    }


}

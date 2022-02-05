package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.IDObject;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class ClioApi extends AbstractRestAPI{



    public <T> T getById(Class<T> clazz, Long id){

        return doGet(clazz, id);

    }

    public <T extends IDObject> T getByQuery(Class<T> clazz, String query){

        return doGet(clazz, query);
    }


    public <T> IDObject post(T t){

        return doPost(t);
    }

    public <T> IDObject patch(T t){

        return doPatch(t);
    }


}

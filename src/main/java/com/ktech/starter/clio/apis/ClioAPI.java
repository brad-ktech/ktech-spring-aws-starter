package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.messages.responses.BulkResult;
import com.ktech.starter.clio.models.IDObject;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequestScope
public class ClioAPI extends AbstractRestAPI{



    public <T> T getById(Class<T> clazz, Long id){

        return doGet(clazz, id);

    }

    public <T extends IDObject> T getByQuery(Class<T> clazz, String query){

        return doGet(clazz, query);
    }


    public <T> T post(T t){

        return doPost(t);
    }

    public <T> IDObject patch(T t){

        return doPatch(t);
    }

    public <T> T patch(T t, Long id, Object data){

        return doPatch(t, id, data);
    }

    public <T> List<T> bulk(Class<T> clazz, LocalDateTime updatedSince, int tries, int aDelayInSeconds) {

        BulkResult<T> result =  doBulk(clazz, updatedSince, tries, aDelayInSeconds);
        return result.asList();

    }


}

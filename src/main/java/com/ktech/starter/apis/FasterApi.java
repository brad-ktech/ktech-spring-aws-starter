package com.ktech.starter.apis;

import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.client.RestTemplate;

public class FasterApi {


    private URIBuilder builder;
    private Class clazz;


    private FasterApi(){


    }

    private FasterApi(Class clazz){
        this.clazz = clazz;
        this.builder = new URIBuilder();
        this.builder.setScheme("https");
        setPath();
        setFields();
    }

    public static FasterApi withModel(Class clazz){

        return new FasterApi(clazz);

    }

    public FasterApi addParameter(String key, String value){

        builder.addParameter(key, value);
        return this;

    }

    public void execute(){


    }

    /*
    * ALL PRIVATE FIELDS BELOW
    */
    private void setFields(){

        if(clazz.isAnnotationPresent(ApiFields.class)){
            ApiFields ann = (ApiFields)clazz.getAnnotation(ApiFields.class);
            builder.setParameter("fields", encodeFields(ann.fields()));

        }
    }


    private void setPath(){

        if(clazz.isAnnotationPresent(ApiPath.class)){
            ApiPath ann = (ApiPath)clazz.getAnnotation(ApiPath.class);
            builder.setPath(ann.path());

        }


    }

    private String encodeFields(String aFieldString) {


        return aFieldString.replace("{", "%7B").replace("}", "%7D");
    }

}

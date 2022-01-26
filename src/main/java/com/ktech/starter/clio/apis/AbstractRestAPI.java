package com.ktech.starter.clio.apis;

import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.clio.models.CalendarEntry;
import com.ktech.starter.vaults.ClioConfigurationVault;
import org.apache.commons.lang3.StringUtils;

public class AbstractRestAPI {

    protected ClioConfigurationVault vault;

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

    protected <T> String getUrl(Class<T> clazz){

        String host = vault.getAPITarget();
        String path = this.getPathFromClass(CalendarEntry.class);
        String url = host + "/" +  path;
        return url;

    }
}

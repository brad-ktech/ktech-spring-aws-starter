package com.ktech.starter.clio.messages;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class Request<T>{

    @SerializedName("data")
    private T data;


    private Request(T t ){

        this.data = t;
    }

   public static <T> Request<T> of(T t){

       return new Request<>(t);


   }

    public String toJson(){

        Gson g = new Gson();
        return g.toJson(this);

    }

}
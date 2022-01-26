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
@NoArgsConstructor
@AllArgsConstructor
public class Request<T>{

    @SerializedName("data")
    private T data;

    public String toString() {

        return this.toString(0);
    }

    public String toString(int anIndentFactor) {
        JSONObject obj = new JSONObject(this);
        return obj.toString(anIndentFactor);
    }

    public String goJson(){

        Gson g = new Gson();
        return g.toJson(this);

    }

}
package com.ktech.starter.clio.messages;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T>{
    @SerializedName("data")
    private T data;
    @SerializedName("error")
    private ApiError error;

    public Result(T data) {
        this.data = data;
    }

    public Result(ApiError error) {
        this.error = error;
    }
}
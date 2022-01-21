package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


@Getter @Setter
public class IDObject {
  @JsonProperty("id")
  @SerializedName("id")
  private Long id;
  @JsonProperty("etag")
  @SerializedName("etag")
  private String etag;


  public String toJson() {
    JSONObject obj = new JSONObject(this);
    return obj.toString();

  }



}

  

package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NameValuePair{

  @SerializedName("name")
  private String name;
  @SerializedName("value")
  private String value;
 
}

  

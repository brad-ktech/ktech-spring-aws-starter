package com.ktech.starter.clio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NamedObject extends IDObject {

  @JsonProperty("name")
  @SerializedName("name")
  private String name;
 
}

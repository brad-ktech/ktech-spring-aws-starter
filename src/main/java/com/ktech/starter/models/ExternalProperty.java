package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExternalProperty extends NamedObject {

  @SerializedName("value")
  private String value;
  
}

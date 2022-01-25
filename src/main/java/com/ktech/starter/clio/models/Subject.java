package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Subject extends IDObject {

  @SerializedName("type")
  private String type;
  @SerializedName("identifier")
  private String identifier;
  @SerializedName("secondary_identifier")
  private String secondaryIdentifier;
   
}

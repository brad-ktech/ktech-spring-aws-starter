package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MatterRelationship extends Relationship {
  
  @SerializedName("matter")
  private Matter matter;
  
}

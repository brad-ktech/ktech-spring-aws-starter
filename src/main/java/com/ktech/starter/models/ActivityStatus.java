package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActivityStatus extends IDObject {
  
  @SerializedName("status")
  private String status;
  
}

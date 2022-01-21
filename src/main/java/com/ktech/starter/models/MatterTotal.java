package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MatterTotal extends IDObject {

  @SerializedName("amount")
  private Double amount;
  
}

package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountBalance extends NamedObject {

  @SerializedName("balance")
  private Double balance;
  @SerializedName("type")
  private String type;

}

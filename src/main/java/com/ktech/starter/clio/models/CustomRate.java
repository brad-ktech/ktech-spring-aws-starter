package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomRate{
 
  @SerializedName("type")
  private String type;
  @SerializedName("on_invoice")
  private String onInvoice;
  @SerializedName("rates")
  private Rate[] rates;
  
}

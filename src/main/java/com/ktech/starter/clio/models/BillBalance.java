package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BillBalance extends IDObject {

  @SerializedName("type")
  private String type;
  @SerializedName("amount")
  private Double amount;
  @SerializedName("interest_amount")
  private Double interestAmount;
  @SerializedName("due")
  private Double due;
  
}

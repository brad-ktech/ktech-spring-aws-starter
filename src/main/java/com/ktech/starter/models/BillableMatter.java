package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BillableMatter extends Matter {
  
  @SerializedName("unbilled_hours")
  private Double unbilledHours;
  @SerializedName("unbilled_amount")
  private Double unbilledAmount;
  @SerializedName("amount_in_trust")
  private Double amountInTrust;
  
}

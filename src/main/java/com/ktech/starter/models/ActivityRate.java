package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActivityRate {
  private Double amount;
  @JsonProperty("non_billable_amount")
  @SerializedName("non_billable_amount")
  private Double nonBillableAmount;
  private String type;
}

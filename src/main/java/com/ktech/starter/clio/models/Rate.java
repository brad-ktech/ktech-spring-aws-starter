package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Rate{

  @SerializedName("rate")
  private Double rate;
  @SerializedName("award")
  private Double award;
  @SerializedName("note")
  private String note;
  @SerializedName("date")
  private Date date;
  @SerializedName("user")
  private User user;
  @SerializedName("group")
  private Group group;
  @SerializedName("activityDescription")
  private ActivityDescriptionStub activityDescritpion;
  
}

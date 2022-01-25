package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Reminder extends NamedObject {

  @SerializedName("duration")
  private Double duration;
  @SerializedName("next_delivery_at")
  private Date nextDeliveryAt;
  @SerializedName("state")
  private String state;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
    
}

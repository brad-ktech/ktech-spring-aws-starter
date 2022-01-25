package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Timer extends IDObject {

  @SerializedName("start_time")
  private Date startTime;
  @SerializedName("elapsed_time")
  private Integer elapsedTime;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("update_at")
  private Date updatedAt;
  
}

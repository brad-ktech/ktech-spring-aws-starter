package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostBulkResult<T>{
  @SerializedName("data")
  private PostBulkRecord<T>[] data;

  @SerializedName("status")
  private String status;

  @SerializedName("requested")
  private Integer requested;

  @SerializedName("performed")
  private Integer performed;
}

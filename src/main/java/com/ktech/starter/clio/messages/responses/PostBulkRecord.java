package com.ktech.starter.clio.messages.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostBulkRecord<T> {
  @SerializedName("status")
  private Integer status;

  @SerializedName("data")
  private T data;

  @SerializedName("error")
  private BulkErrorResponse error;
}

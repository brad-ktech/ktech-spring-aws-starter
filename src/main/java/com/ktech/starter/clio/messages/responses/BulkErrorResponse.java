package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BulkErrorResponse {
  @SerializedName("message")
  private String message;

  @SerializedName("type")
  private String type;
}

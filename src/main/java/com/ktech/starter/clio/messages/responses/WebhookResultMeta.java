package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WebhookResultMeta {

  @SerializedName("event")
  private String event;

}

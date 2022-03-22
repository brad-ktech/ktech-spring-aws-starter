package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import com.ktech.starter.clio.messages.Result;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WebhookResult<T> extends Result<T> {

  @SerializedName("meta")
  private WebhookResultMeta meta;

}

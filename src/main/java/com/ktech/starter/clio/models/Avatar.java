package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Avatar extends IDObject {
  @SerializedName("url")
  private String url;
}

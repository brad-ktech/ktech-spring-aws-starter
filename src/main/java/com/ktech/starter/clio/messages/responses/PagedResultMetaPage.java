package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PagedResultMetaPage{// extends BaseResultMeta {

  @SerializedName("previous")
  private String previous;
  @SerializedName("next")
  private String next;

}

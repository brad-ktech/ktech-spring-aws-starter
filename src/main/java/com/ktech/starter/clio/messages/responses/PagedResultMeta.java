package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PagedResultMeta {

  @SerializedName("paging")
  private PagedResultMetaPage paging;
  @SerializedName("records")
  private long records;
  
}

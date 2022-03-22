package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BulkRecord<T> {

  @SerializedName("data")
  private T[] data;

}

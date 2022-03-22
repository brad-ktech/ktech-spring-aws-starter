package com.ktech.starter.clio.messages.responses;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BulkResult<T> {

  @SerializedName("data")
  private BulkRecord<T>[] data;

  public List<T> getDataList() {
    List<T> dataList = new ArrayList<T>();

    if (data != null && data[0] != null && data[0].getData() != null) {
      for (int i = 0; i < data[0].getData().length; i++) {
        dataList.add(data[0].getData()[i]);
      }
    }
    return dataList;
  }
  
  public List<T> asList() {
    return getDataList();
  }
}

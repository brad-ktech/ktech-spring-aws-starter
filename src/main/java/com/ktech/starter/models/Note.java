package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Note extends IDObject {

  @SerializedName("type")
  private String type;  
  @SerializedName("subject")
  private String subject;
  @SerializedName("detail")
  private String detail;
  @SerializedName("date")
  private Date date;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("update_at")
  private Date updatedAt;
  @SerializedName("time_entries_count")
  private Integer timeEntriesCount;
  private Contact contact;
  private Matter matter;
}

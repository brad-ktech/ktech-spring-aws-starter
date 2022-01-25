package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class DocumentTemplate extends IDObject {
  @SerializedName("size")
  private String size;
  @SerializedName("content_type")
  private String contentType;
  @SerializedName("filename")
  private String filename;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
}

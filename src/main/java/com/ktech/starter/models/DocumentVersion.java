package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class DocumentVersion extends IDObject {

  @SerializedName("uuid")
  private String uuid;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("deleted_at")
  private Date deletedAt;
  @SerializedName("filename")
  private String filename;
  @SerializedName("size")
  private Integer size;
  @SerializedName("version_number")
  private Integer versionNumber;
  @SerializedName("content_type")
  private String contentType;
  @SerializedName("received_at")
  private Date receiveAt;
  @SerializedName("put_url")
  private String putUrl;
  @SerializedName("fully_uploaded") @JsonProperty("fully_uploaded")
  private Boolean fullyUploaded;
  @SerializedName("put_headers")
  private NameValuePair[] putHeaders;
}

package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Webhook extends IDObject {

  @SerializedName("url")
  private String url;
  @SerializedName("fields")
  private String fields;
  @SerializedName("shared_secret")
  private String sharedSecret;
  @SerializedName("model")
  private String model;
  @SerializedName("status")
  private String status;
  @SerializedName("events")
  private String[] events;
  @SerializedName("expires_at")
  private Date expiresAt;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("user")
  private User user;

}

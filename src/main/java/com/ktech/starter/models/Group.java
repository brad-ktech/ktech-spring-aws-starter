package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Group extends NamedObject {
  
  @SerializedName("type")
  private String type;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("client_connect_user")
  private Boolean clientConnectUser;
  @SerializedName("users")
  private User[] users;

}

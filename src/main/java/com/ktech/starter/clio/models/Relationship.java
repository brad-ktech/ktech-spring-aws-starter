package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Relationship extends IDObject {
  
  @SerializedName("description")
  private String description;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("contact")
  private Contact contact;
  @SerializedName("matter")
  private Matter matter;
}

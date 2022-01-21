package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Calendar extends IDObject {

  @SerializedName("name")
  private String name;
  @SerializedName("type")
  private String type;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @JsonProperty("visible")
  @SerializedName("visible")
  private boolean isVisible;
  private String color;
}

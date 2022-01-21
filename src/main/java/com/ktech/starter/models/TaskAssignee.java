package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TaskAssignee extends IDObject {

  @SerializedName("type")
  private String type;
  @SerializedName("identifier")
  private String identifier;
  @SerializedName("secondary_identifier")
  private String secondaryIdentifier;
  @SerializedName("enabled")
  private Boolean enabled;
  @SerializedName("name")
  private String name;
  @SerializedName("avatar")
  private Avatar avatar;
  @SerializedName("initials")
  private String initials;
}

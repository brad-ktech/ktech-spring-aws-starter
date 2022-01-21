package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomFieldPicklistOption extends IDObject {

  @SerializedName("option")
  private String option;
  @SerializedName("deleted_at")
  private Date deletedAt;
  
}

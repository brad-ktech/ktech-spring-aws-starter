package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CustomFieldSetAssociation extends IDObject {

  @SerializedName("display_order")
  private Integer displayOrder;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  
  @SerializedName("custom_field_set")
  private CustomFieldSet customFieldSet;

}

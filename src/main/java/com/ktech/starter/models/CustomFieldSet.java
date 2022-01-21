package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomFieldSet extends NamedObject {
    
  @SerializedName("custom_fields")
  private CustomField[] customFields;
  
}

package com.ktech.starter.clio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CustomFieldValue extends CustomObject {
    @JsonProperty("field_name")
    @SerializedName("field_name")
    public String fieldName;
    @JsonProperty("value")
    @SerializedName("value")
    public String value;
    @JsonProperty("created_at")
    @SerializedName("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    @SerializedName("updated_at")
    private Date updatedAt;
    @JsonProperty("custom_field")
    @SerializedName("custom_field")
    public CustomField customField;
    @JsonProperty("picklist_option")
    @SerializedName("picklist_option")
    public CustomFieldPicklistOption picklistOption;
}

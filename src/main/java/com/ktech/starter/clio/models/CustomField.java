package com.ktech.starter.clio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CustomField extends NamedObject {

    @SerializedName("created_at")
    @JsonProperty("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    @JsonProperty("updated_at")
    private Date updatedAt;
    @SerializedName("parent_type")
    @JsonProperty("parent_type")
    private String parentType;
    @SerializedName("field_type")
    @JsonProperty("field_type")
    private String fieldType;
    @SerializedName("displayed")
    @JsonProperty("displayed")
    private Boolean displayed;
    @SerializedName("deleted")
    @JsonProperty("deleted")
    private Boolean deleted;
    @SerializedName("required")
    @JsonProperty("required")
    private Boolean required;
    @SerializedName("display_order")
    @JsonProperty("display_order")
    private String displayOrder;
    @SerializedName("picklist_options")
    @JsonProperty("picklist_options")
    private CustomFieldPicklistOption[] picklistOptions;

    public CustomField(Long id) {
        super();
        setId(id);
    }
}

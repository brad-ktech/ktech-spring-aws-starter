package com.ktech.starter.clio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import lombok.*;

import java.util.Date;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiPath(path="contacts")
@ApiFields(fields ="id,etag,name,first_name,middle_name,last_name,type,created_at,updated_at,prefix,title,"
        + "initials,primary_phone_number,primary_email_address,secondary_email_address,related_contacts{id},"
        + "is_client,is_bill_recipient,custom_field_values{id,etag,field_name,value,created_at,updated_at,"
        + "custom_field,picklist_option}")
public class Contact extends NamedObject {

  @JsonProperty("first_name")
  @SerializedName("first_name")
  private String firstName;
  @SerializedName("middle_name")
  private String middleName;
  @JsonProperty("last_name")
  @SerializedName("last_name")
  private String lastName;
  @SerializedName("type")
  private String type;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("prefix")
  private String prefix;
  @SerializedName("title")
  private String title;
  @SerializedName("initials")
  private String initials;  
  @SerializedName("primary_phone_number")
  private String primaryPhoneNumber;
  @JsonProperty("primary_email_address")
  @SerializedName("primary_email_address")
  private String primaryEmailAddress;
  @SerializedName("secondary_email_address")
  private String secondaryEmailAddress;
  @SerializedName("related_contacts")
  private Contact[] relatedContacts;
  @SerializedName("is_client")
  private Boolean isClient;
  @JsonProperty("is_bill_recipient")
  @SerializedName("is_bill_recipient")
  private Boolean isBillRecipient;
  @JsonProperty("custom_field_values")
  @SerializedName("custom_field_values")
  private List<CustomFieldValue> customFieldValues;
  private List<Address> addresses;
  @JsonProperty("email_addresses")
  @SerializedName("email_addresses")
  private List<ContactEmailAddress> emailAddressPostObject;
  @JsonProperty("phone_numbers")
  @SerializedName("phone_numbers")
  private List<ContactPhoneNumber> phoneNumberPostObject;
  @JsonProperty("web_sites")
  @SerializedName("web_sites")
  private List<ContactWebsite> websitePostObject;
  private IDObject company;


  public CustomFieldValue getCustomFieldValue(long anId) {
    for (CustomFieldValue value : customFieldValues) {
      if (value.getCustomField() != null && value.getCustomField().getId() == anId)
        return value;
    }
    return null;
  }
}

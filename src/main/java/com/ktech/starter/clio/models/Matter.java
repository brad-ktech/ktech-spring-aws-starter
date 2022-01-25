package com.ktech.starter.clio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
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
@ApiPath(path="matters")
@ApiFields(fields ="id,etag,number,display_number,custom_number,description,status,location,client_reference,"
        + "billable,maildrop_address,billing_method,open_date,close_date,pending_date,created_at,updated_at,shared,client,"
        + "contingency_fee,custom_rate{type,on_invoice,rates},folder{id,etag,created_at,updated_at,deleted_at,type,locked,name,root},"
        + "group{id,etag,name,type,created_at,updated_at,client_connect_user},matter_budget,originating_attorney{id,name},practice_area,"
        + "responsible_attorney{id,name},statute_of_limitations,user,account_balances{id,type,name,balance},custom_field_values{id,etag,field_name,value,"
        + "created_at,updated_at,custom_field,picklist_option},custom_field_set_associations,relationships{id,etag,description,contact},task_template_list_instances")
public class Matter extends IDObject {
  
  @SerializedName("number")
  private Long number;
  @SerializedName("display_number")
  private String displayNumber;
  @SerializedName("custom_number")
  private String customNumber;
  @SerializedName("description")
  private String description;
  @SerializedName("status")
  private String status;
  @SerializedName("location")
  private String location;
  @SerializedName("client_reference")
  private String clientReference;
  @SerializedName("billable")
  private Boolean billable;
  @SerializedName("maildrop_address")
  private String mailDropAddress;
  @SerializedName("billing_method")
  private String billingMethod;
  @SerializedName("open_date")
  private Date openDate;
  @SerializedName("close_date")
  private Date closeDate;
  @SerializedName("pending_date")
  private Date pendingDate;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("shared")
  private Boolean shared;
  @SerializedName("legal_aid_uk_options")
  private String legalAidUKOptions;
  
  @SerializedName("client")
  private Client client;
  @SerializedName("contingency_fee")
  private ContingencyFee contingencyFee;
  @SerializedName("custom_rate")
  private CustomRate customRate;
  @SerializedName("folder")
  private Folder folder;
  @SerializedName("group")
  private Group group;
  @SerializedName("matter_budget")
  private MatterBudget matterBudget;

  @SerializedName("originating_attorney")
  private User originatingAttorney;
  @SerializedName("practice_area")
  private PracticeArea practiceArea;
  @JsonProperty("responsible_attorney")
  @SerializedName("responsible_attorney")
  private User responsibleAttorney;

  @SerializedName("statute_of_limitations")
  private StatuteOfLimitations statuteOfLimitations;

  @SerializedName("user")
  private User user;
  @SerializedName("account_balances")
  private AccountBalance[] accountBalances;

  @JsonProperty("custom_field_values")
  @SerializedName("custom_field_values")
  private CustomFieldValue[] customFieldValues;
  @SerializedName("custom_field_set_associations")
  private CustomFieldSetAssociation[] customFieldSetAssociations;
  @SerializedName("relationships")
  private Relationship[] relationships;
  @SerializedName("task_template_list_instances")
  private TaskTemplateListInstance[] taskTemplateListInstances;

  public Client getClient(boolean lazy) {
    if (client == null && lazy)
      setClient(new Client());
    return client;
  }

  public ContingencyFee getContingencyFee(boolean lazy) {
    if (contingencyFee == null)
      setContingencyFee(new ContingencyFee());
    return contingencyFee;
  }

  public User getOriginatingAttorney(boolean lazy) {
    if (originatingAttorney == null)
      setOriginatingAttorney(new User());
    return originatingAttorney;
  }

  public User getResponsibleAttorney(boolean lazy) {
    if (responsibleAttorney == null)
      setResponsibleAttorney(new User());
    return responsibleAttorney;
  }

  public StatuteOfLimitations getStatuteOfLimitations(boolean lazy) {
    if (statuteOfLimitations == null)
      setStatuteOfLimitations(new StatuteOfLimitations());
    return statuteOfLimitations;
  }

  public Folder getFolder(boolean lazy) {
    if (folder == null && lazy)
      setFolder(new Folder());
    return folder;
  }

  public Group getGroup(boolean lazy) {
    if (group == null && lazy)
      setGroup(new Group());
    return group;
  }

  public User getUser(boolean lazy) {
    if (user == null && lazy)
      setUser(new User());
    return user;
  }

  public PracticeArea getPracticeArea(boolean lazy) {
    if (practiceArea == null && lazy)
      setPracticeArea(new PracticeArea());
    return practiceArea;
  }

  public CustomFieldValue getCustomFieldValue(String aName) {
    for (CustomFieldValue value : customFieldValues) {
      if (value.getFieldName().equals(aName))
        return value;
    }
    return null;
  }

  public CustomField getCustomField(long anId) {
    for (CustomFieldValue value : customFieldValues) {
      if (value.getCustomField() != null && value.getCustomField().getId() == anId)
        return value.getCustomField();
    }
    return null;
  }  
  
  public CustomFieldValue getCustomFieldValue(long anId) {
    for (CustomFieldValue value : customFieldValues) {
      if (value.getCustomField() != null && value.getCustomField().getId() == anId)
        return value;
    }
    return null;
  }

  public String getCustomFieldValueData(long anId) {
    return getCustomFieldValueData(anId, null);
  }

  public String getCustomFieldValueData(long anId, String ifNull) {
    CustomFieldValue value = this.getCustomFieldValue(anId);
    return (value == null) ? ifNull : value.getValue();
  }

  public void setCustomFieldValueData(long anId, String value) {
    CustomFieldValue customFieldValue = this.getCustomFieldValue(anId);
    if (customFieldValue != null) {
      customFieldValue.setValue(value);
    } else {
      CustomField customField = new CustomField();
      customField.setId(anId);

      customFieldValue = new CustomFieldValue();
      customFieldValue.setCustomField(customField);
      customFieldValue.setValue(value);

      List<CustomFieldValue> customFieldValuesList = new ArrayList<>(Arrays.asList(customFieldValues));

      customFieldValuesList.add(customFieldValue);

      customFieldValues = customFieldValuesList.toArray(customFieldValues);
    }
  }

  public String getCustomFieldValueOption(long anId) {
    return getCustomFieldValueOption(anId, null);
  }

  public String getCustomFieldValueOption(long anId, String ifNull) {
    CustomFieldValue cfv = this.getCustomFieldValue(anId);
    return (cfv == null) ? null : cfv.getPicklistOption().getOption();
  }
  
  public List<AccountBalance> getAccountBalances(String aType) {
    List<AccountBalance> balances = new ArrayList<AccountBalance>();
    for (AccountBalance balance: this.getAccountBalances()) {
      if (aType.equals(balance.getType())) {
        balances.add(balance);
      }
    }
    return balances;
  }
  
  public double getAccountBalanceTotal(String aType) {
    double total = 0.0;
    for (AccountBalance balance: this.getAccountBalances(aType)) {
      total += balance.getBalance();
    }
    return total;
  }  
}

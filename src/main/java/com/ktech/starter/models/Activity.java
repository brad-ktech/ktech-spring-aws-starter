package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.apache.commons.lang3.BooleanUtils;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity extends TimeEntry {

  @SerializedName("activity_description") @JsonProperty("activity_description")
  private ActivityDescription activityDescription;
  @SerializedName("expense_category") @JsonProperty("expense_category")
  private ExpenseCategory expenseCategory;
  @SerializedName("bill")
  private Bill bill;
  @SerializedName("communication")
  private Communication communication;
  @SerializedName("matter")
  private Matter matter;
  @SerializedName("matter_note")
  private Note matterNote;
  @SerializedName("contact_note")
  private Note contactNote;
  @SerializedName("subject")
  private Subject subject;
  @SerializedName("timer")
  private Timer timer;
  @SerializedName("user")
  private User user;
  @SerializedName("utbms_expense")
  private UTBMSExpense utbmsStub;
  @SerializedName("vendor")
  private Contact vendor;
  @SerializedName("calendar_entry")
  private CalendarEntry calendarEntry;
  @SerializedName("task")
  private Task task;
  @SerializedName("document_version")
  private DocumentVersion documentVersion;

  public ActivityDescription getActivityDescription(boolean lazy) {
    if (activityDescription == null)
      setActivityDescription(new ActivityDescription());
    return getActivityDescription();
  }

  public ExpenseCategory getExpenseCategory(boolean lazy) {
    if (expenseCategory == null)
      setExpenseCategory(new ExpenseCategory());
    return expenseCategory;
  }
  
  public Bill getBill(boolean lazy) {
    if (bill == null)
      setBill(new Bill());
    return bill;
  }  
  
  public Communication getCommunication(boolean lazy) {
    if (communication == null)
      setCommunication(new Communication());
    return communication;
  }

  
  public Matter getMatter(boolean lazy) {
    if (matter == null)
      setMatter(new Matter());
    return matter;
  }
  
  public User getUser(boolean lazy) {
    if (user == null)
      setUser(new User());      
    return user;
  }
  
  public String getStatus() {
	if (BooleanUtils.isTrue(getNonBillable())) {
	  return "Non-billable";
    }
	  
    if (BooleanUtils.isTrue(getBilled())) {
	  return "Billed";
	}
    
    if (BooleanUtils.isTrue(getOnBill())) {
    	return "Draft";
    }
    
    return "Unbilled";
  }
}

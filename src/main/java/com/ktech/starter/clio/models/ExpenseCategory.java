package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ExpenseCategory extends NamedObject {

  @SerializedName("rate")
  private Double rate;
  @SerializedName("entry_type")
  private String entryType;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("xero_expense_code")
  private String xeroExpenseCode;
  @SerializedName("accessible_to_user")
  private Boolean accessibleToUser;  
  
}

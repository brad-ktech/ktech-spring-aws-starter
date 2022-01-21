package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BillingActivity extends CustomObject {
  public static final String EXPENSE_ENTRY_TYPE = "ExpenseEntry";
  public static final String TIME_ENTRY_TYPE = "TimeEntry";

  @SerializedName("etag")
  private String etag;
  @SerializedName("client_id")
  private String clientId;
  @SerializedName("client_name")
  private String clientName;
  @SerializedName("client_reference")
  private String clientReference;
  @SerializedName("practice_area")
  private String practiceArea;
  @SerializedName("workday_cust_id")
  private String workdayCustomerId;
  @SerializedName("pillar")
  private String pillar;
  @SerializedName("matter_number")
  private String matterNumber;
  @SerializedName("matter_description")
  private String matterDescription;
  @SerializedName("date")
  private Date date;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("billing_status")
  private String billingStatus;
  @SerializedName("activity_status")
  private String activityStatus;
  @SerializedName("user_name")
  private String userName;
  @SerializedName("activity_type")
  private String activityType;
  @SerializedName("description")
  private String description;
  @SerializedName("note")
  private String note;
  @SerializedName("quantity")
  private Double quantity;
  @SerializedName("price")
  private Double price;  
  @SerializedName("total")
  private Double total;
  @SerializedName("action")
  private String action;
  @SerializedName("activity_description")
  private CustomObject activityDescription;
  @SerializedName("plantiff_defense")
  private String plantiffDefense;
  @SerializedName("unbilled_amount")
  private Double unbilledAmount;
  @SerializedName("amount_in_trust")
  private Double amountInTrust;
  @SerializedName("outstanding")
  private Double outstanding;
  @SerializedName("responsible_attorney")
  private String responsibleAttorney;
}

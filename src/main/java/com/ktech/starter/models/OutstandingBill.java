package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class OutstandingBill extends IDObject {

  @SerializedName("number")
  private String number;
  @SerializedName("issued_at")
  private Date issuedAt;  
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("due_at")
  private Date dueAt;
  @SerializedName("tax_rate")
  private Double taxRate;
  @SerializedName("secondary_tax_rate")
  private Double secondaryTaxRate;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("subject")
  private String subject;
  @SerializedName("purchase_order")
  private String purchaseOrder;
  @SerializedName("type")
  private String type;
  @SerializedName("memo")
  private String memo;
  @SerializedName("start_at")
  private Date startAt;
  @SerializedName("end_at")
  private Date endAt;
  @SerializedName("balance")
  private Double balance;
  @SerializedName("state")
  private String state;
  @SerializedName("kind")
  private String kind;
  @SerializedName("total")
  private Double total;
  @SerializedName("paid")
  private Double paid;
  @SerializedName("paid_at")
  private Date paidAt;
  @SerializedName("pending")
  private Double pending;
  @SerializedName("due")
  private Double due;
  @SerializedName("discount_services_only")
  private String discountServicesOnly;
  @SerializedName("can_update")
  private Boolean canUpdate;
  @SerializedName("credits_issued")
  private Double creditsIssued;
  @SerializedName("shared")
  private Boolean shared;
  @SerializedName("last_sent_at")
  private Date lastSentAt;
  @SerializedName("services_secondary_tax")
  private Double servicesSecondaryTax;
  @SerializedName("services_sub_total")
  private Double servicesSubtotal;
  @SerializedName("services_tax")
  private Double servicesTax;
  @SerializedName("services_taxable_sub_total")
  private Double servicesTaxableSubtotal;
  @SerializedName("services_secondary_taxable_sub_total")
  private Double servicesSecondaryTaxableSubtotal;
  @SerializedName("taxable_sub_total")
  private Double taxableSubtotal;
  @SerializedName("secondary_taxable_sub_total")
  private Double secondaryTaxableSubtotal;
  @SerializedName("sub_total")
  private Double subtotal;
  @SerializedName("tax_sum")
  private Double taxSum;
  @SerializedName("secondary_tax_sum")
  private Double secondaryTaxSum;
  @SerializedName("available_state_transitions")
  private String[] availableStateTransitions;
  
  
}

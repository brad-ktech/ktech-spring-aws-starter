package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class OutstandingClientBalance extends IDObject {
  
  @SerializedName("last_payment_date")
  private Date lastPaymentDate;
  @SerializedName("last_shared_date")
  private Date lastSharedDate;
  @SerializedName("newest_issued_bill_due_date")
  private Date newestIssuedBillDueDate;
  @SerializedName("pending_payments_total")
  private Double pendingPaymentsTotal;
  @SerializedName("reminders_enabled")
  private Boolean remindersEnabled;
  @SerializedName("total_outstanding_balance")
  private Double totalOutstandingBalance;
  @SerializedName("associated_matter_ids")
  private Long[] associatedMatterIds;
  @SerializedName("outstanding_bills")
  private OutstandingBill[] outstandingBills;
  @SerializedName("contact")
  private Contact contact;
  @SerializedName("currency")
  private Currency currency;
  
}


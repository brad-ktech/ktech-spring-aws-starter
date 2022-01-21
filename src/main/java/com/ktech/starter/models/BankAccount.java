package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BankAccount extends IDObject {

  @SerializedName("account_number")
  private String accountNumber;
  @SerializedName("balance")
  private Double balance;
  @SerializedName("bank_transactions_count")
  private Integer bankTransactionsCount;
  @SerializedName("clio_payment_page_link")
  private String clioPaymentPageLink;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("currency")
  private String currency;
  @SerializedName("default_account")
  private Boolean defaultAccount;
  @SerializedName("domicile_branch")
  private String domicileBranch;
  @SerializedName("general_ledger_number")
  private String generalLedgerNumber;
  @SerializedName("holder")
  private String holder;
  @SerializedName("institution")
  private String institution;
  @SerializedName("name")
  private String name;
  @SerializedName("swift")
  private String swift;
  @SerializedName("transit_number")
  private String transitNumber;  
  @SerializedName("type")
  private String type;
  @SerializedName("updated_at")
  private Date updatedAt;
  
}

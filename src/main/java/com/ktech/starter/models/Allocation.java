package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Allocation extends IDObject {

  @SerializedName("date")
  private Date date;
  @SerializedName("amount")
  private Double amount;
  @SerializedName("interest")
  private Boolean interest;
   @SerializedName("voided_at")
  private Date voidedAt;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;  
  @SerializedName("description")
  private String description;
  @SerializedName("has_online_payment")
  private Boolean hasOnlinePayment;
  @SerializedName("destroyable")
  private Boolean destroyable;
  @SerializedName("payment_type")
  private String paymentType;    
  @SerializedName("bill")
  private Bill bill;
  @SerializedName("source_bank_account")
  private BankAccount sourceBankAccount;
  @SerializedName("destination_bank_account")
  private BankAccount destinationBankAccount;
  @SerializedName("matter")
  private Matter matter;
  @SerializedName("contact")
  private Contact contact;
  @SerializedName("parent")
  private AllocationParent parent;

  public Bill getBill(boolean lazy) {
    if (bill == null && lazy)
      setBill(new Bill());
    return bill;
  }
  
  public BankAccount getSourceBankAccount(boolean lazy) {
    if (sourceBankAccount == null && lazy)
      setSourceBankAccount(new BankAccount());
    return sourceBankAccount;
  }
  
  public BankAccount getDestinationBankAccount(boolean lazy) {
    if (destinationBankAccount == null && lazy)
      setDestinationBankAccount(new BankAccount());
    return destinationBankAccount;
  }
  
  public Matter getMatter(boolean lazy) {
    if (matter == null && lazy)
      setMatter(new Matter());
    return matter;
  }
  
  public Contact getContact(boolean lazy) {
    if (contact == null && lazy)
      setContact(new Contact());
    return contact;
  }  
  
  public AllocationParent getParent(boolean lazy) {
    if (parent == null && lazy)
      setParent(new AllocationParent());
    return parent;
  }  
  
}

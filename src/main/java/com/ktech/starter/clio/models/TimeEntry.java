package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Getter @Setter
public class TimeEntry extends IDObject {

  @SerializedName("type")
  private String type;
  @SerializedName("date")
  private Date date;
  @SerializedName("quantity_in_hours")
  private Double quantityInHours;
  @SerializedName("rounded_quantity_in_hours")
  private Double roundedQuantityInHours;
  @SerializedName("quantity")
  private Double quantity;
  @SerializedName("rounded_quantity")
  private Double roundedQuantity;
  @SerializedName("price")
  private Double price;
  @SerializedName("note")
  private String note;
  @SerializedName("flat_rate")
  private Boolean flatRate;
  @SerializedName("billed")
  private Boolean billed;
  @SerializedName("on_bill")
  private Boolean onBill;
  @SerializedName("total")
  private Double total;
  @SerializedName("contingency_fee")
  private Boolean contingencyFee;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("reference")
  private String reference;
  @SerializedName("non_billable")
  private Boolean nonBillable;
  @SerializedName("non_billable_total")
  private Double nonBillableTotal;

  public String getDate() {
    if (date == null) {
      return null;
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    return df.format(date);
  }

  //@XmlTransient
  public Date getDateAsDate() {
    return date;
  }

  public boolean getNon_billable() {
    return BooleanUtils.isTrue(nonBillable);
  }
}

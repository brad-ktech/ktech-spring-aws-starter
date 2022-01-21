package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Date;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem extends IDObject {
    private LineItemType type;
    private String description;
    private String note;
    private double price;
    private double quantity;
    private double total;
    private Date date;
    @SerializedName("matter_id")
    @JsonProperty("matter_id")
    private Long matterId;
    private Bill bill;
    private Matter matter;
    private Activity activity;
    private User user;
}

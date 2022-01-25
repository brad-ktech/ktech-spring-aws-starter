package com.ktech.starter.clio.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @NoArgsConstructor @Data
public class Address extends IDObject {
    private String street;
    private String city;
    @SerializedName("province")
    @JsonProperty("province")
    private String state;
    private String postal_code;
    private String country;
    private String name;
    private Date created_at;
    private Date updated_at;
    private boolean primary;
}

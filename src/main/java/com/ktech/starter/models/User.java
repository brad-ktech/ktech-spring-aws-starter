package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString(callSuper = true)
public class User extends NamedObject {

  @SerializedName("enabled")
  private Boolean enabled;
  @SerializedName("first_name")
  private String firstName;
  @SerializedName("last_name")
  private String lastName;
  @SerializedName("initials")
  private String initials;
  @SerializedName("email")
  private String email;
  @SerializedName("phone_number")
  private String phoneNumber;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("job_title")
  private JobTitle jobTitle;
  @SerializedName("rate")
  private Double rate;
  @SerializedName("roles")
  private String[] roles;
  @SerializedName("time_zone")
  private String timeZone;
   
}

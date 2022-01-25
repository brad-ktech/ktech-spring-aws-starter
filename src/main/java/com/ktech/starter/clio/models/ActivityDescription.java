package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ActivityDescription extends ActivityDescriptionStub {

  @SerializedName("visible_to_co_counsel")
  private Boolean visibleToCoCounsel;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("default")
  private Boolean defaultFlag;
  @SerializedName("type")
  private String type;

  @SerializedName("utbms_activity_id")
  private Long utbmsActivityId;
  @SerializedName("utbms_task_name")
  private String utbmsTaskName;
  @SerializedName("utbms_task_id")
  private String utbmsTaskId;

  @SerializedName("xero_service_code")
  private String xeroServiceCode;
  @SerializedName("accessible_to_user")
  private Boolean accessibleToUser;
  @SerializedName("category_type")
  private String categoryType;
  private ActivityRate rate;
}

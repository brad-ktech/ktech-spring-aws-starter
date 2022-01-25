package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Task extends NamedObject {
  
  public static final String TASK_STATUS_PENDING = "pending";
  public static final String TASK_STATUS_IN_PROCESS = "in_process";
  public static final String TASK_STATUS_IN_REVIEW = "in_review";
  public static final String TASK_STATUS_COMPLETE = "complete";
    
  @SerializedName("status")
  private String status;
  @SerializedName("description")
  private String description;
  @SerializedName("priority")
  private String priority;  
  @SerializedName("due_at")
  private Date dueAt;
  @SerializedName("permission")
  private String permission;
  @SerializedName("completed_at")
  private Date completedAt;
  @SerializedName("notify_assignee")
  private Boolean notifyAssignee;
  @SerializedName("notify_completion")
  private Boolean notifyCompletion;
  @SerializedName("statute_of_limitations")
  private Boolean statuteOfLimitations;  
  @SerializedName("time_estimated")
  private Double timeEstimated;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("time_entries_count")
  private Double roundedQuantityInHours;
  @SerializedName("task_type")
  private TaskType taskType;
  @SerializedName("assignor")
  private User assignor;
  @SerializedName("matter")
  private Matter matter;
  @SerializedName("assignee")
  private TaskAssignee assignee;
  @SerializedName("time_entries")
  private TimeEntry[] timeEntries;
  
  public boolean complete() {
    return (""+status).equals("complete");
  }

  public boolean pending() {
    return (""+status).equals("pending");
  }
}

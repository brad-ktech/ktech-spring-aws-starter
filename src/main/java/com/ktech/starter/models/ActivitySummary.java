package com.ktech.starter.models;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class ActivitySummary extends Summary {

  private List<Activity> activities;

  public ActivitySummary() {
    this(new ArrayList<Activity>());
  }

  public ActivitySummary(List<Activity> anActivityList) {
    this.setActivities(anActivityList);
  }

  public void addActivity(Activity anActivity) {
    activities.add(anActivity);
  }
  
  public Double getBilledTime() {
    double total = 0.0;
    for (Activity activity : activities) {
      total += (activity.getBilled() ? activity.getRoundedQuantityInHours() : 0.0);
    }
    return total;
  }
  
  public Double getUnbilledTime() {
    double total = 0.0;
    for (Activity activity : activities) {
      total += (activity.getBilled() ? 0.0 : activity.getRoundedQuantityInHours());
    }
    return total;
  }

  public Double getBillableSubtotal() {
    double total = 0.0;
    for (Activity activity : activities) {
      total += (activity.getTotal() != null) ? activity.getTotal() : 0.0;
    }
    return total;
  }

  public Double getNonBillableSubtotal() {
    double total = 0.0;
    for (Activity activity : activities) {
      total += (activity.getNonBillableTotal() != null) ? activity.getNonBillableTotal() : 0.0;
    }
    return total;
  }

  public Double getBillableTotal() {
    return this.getBillableSubtotal();
  }

  public Double getNonBillableTotal() {
    return this.getNonBillableSubtotal();
  }

  public Map<String, ActivitySummary> buildActivitySummariesByUser() {
    Map<String, ActivitySummary> summaryMap = new TreeMap<String, ActivitySummary>();
    for (Activity activity : activities) {
      ActivitySummary userSummary = summaryMap.get(activity.getUser().getName());
      if (userSummary == null) {
        userSummary = new ActivitySummary();
        summaryMap.put(activity.getUser().getName(), userSummary);
      }
      userSummary.addActivity(activity);
    }
    return summaryMap;
  }

  public Date getLastActivityDate() {
    List<Date> dates = new ArrayList<Date>();
    for (Activity activity : activities) {
      dates.add(activity.getDateAsDate());
    }
    if (dates.size() > 0) {
      Collections.sort(dates);
      return dates.get(dates.size() - 1);
    }
    return null;

  }
}

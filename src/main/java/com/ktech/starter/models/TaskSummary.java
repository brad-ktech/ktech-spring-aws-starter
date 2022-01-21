package com.ktech.starter.models;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class TaskSummary extends Summary {

  private List<Task> tasks;

  public TaskSummary() {

    this(new ArrayList<Task>());
  }
  public TaskSummary(List<Task> aTaskList) {

    this.setTasks(aTaskList);
  }  
    
  public void addTask(Task aTask) {
    this.tasks.add(aTask);
  }
  
  public Map<String,TaskSummary> buildTaskSummariesByStatus() {
    Map<String,TaskSummary> summaryMap = new LinkedHashMap<String,TaskSummary>();
    summaryMap.put(Task.TASK_STATUS_PENDING, new TaskSummary());
    summaryMap.put(Task.TASK_STATUS_IN_PROCESS, new TaskSummary());
    summaryMap.put(Task.TASK_STATUS_IN_REVIEW, new TaskSummary());
    summaryMap.put(Task.TASK_STATUS_COMPLETE, new TaskSummary());    
    for (Task task: tasks) {
      TaskSummary summary = summaryMap.get(task.getStatus());
      summary.addTask(task);
    }
    return summaryMap;    
  }
  
  public Long getOpenTaskCount() {

  return  tasks.stream()
            .filter(t -> !Task.TASK_STATUS_COMPLETE.equals(t.getStatus()))
            .count();


  }
  
  public Map<Long, TaskAssignee> getUsersWithOpenTasks() {
    Map<Long, TaskAssignee> users = new HashMap<>();
    for (Task task: tasks) {
      if (!Task.TASK_STATUS_COMPLETE.equals(task.getStatus()))
          users.put(task.getAssignee().getId(),task.getAssignee());
    }    
    return users;
  }
}
  

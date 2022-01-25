package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Communication extends IDObject {

  @SerializedName("subject")
  private String subject;
  @SerializedName("body")
  private String body;
  @SerializedName("type")
  private String type;
  @SerializedName("date")
  private Date date;
  @SerializedName("time_entries_count")
  private Integer timeEntriesCount;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("received_at")
  private Date receivedAt;
  @SerializedName("user")
  private User user;
  @SerializedName("matter")
  private Matter matter;
  @SerializedName("senders")
  private Contact[] senders;
  @SerializedName("receivers")
  private Contact[] receivers;
  @SerializedName("time_entries")
  private Activity[] timeEntries;
  @SerializedName("documents")
  private Document[] documents;
 
  public User getUser(boolean lazy) {
    if (user == null)
      this.setUser(new User());
    return user;
  }
  
  public Matter getMatter(boolean lazy) {
    if (matter == null)
      this.setMatter(new Matter());
    return matter;
  }
  
}

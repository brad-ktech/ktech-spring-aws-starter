package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Folder extends NamedObject {

  @SerializedName("type")
  private String type;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("deleted_at")
  private Date deletedAt;
  @SerializedName("locked")
  private String locked;
  @SerializedName("root")
  private Boolean root;
  @SerializedName("parent")
  private Folder parent;
  @SerializedName("matter")
  private Matter matter;
  @SerializedName("contact")
  private Contact contact;
  @SerializedName("document_category")
  private DocumentCategory documentCategory;
  @SerializedName("creator")
  private User creator;
  @SerializedName("latest_document_version")
  private DocumentVersion latestDocumentVersion;
  @SerializedName("external_properties")
  private ExternalProperty[] externalPropoerties;
  
}

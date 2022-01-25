package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Document extends NamedObject {

  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("deleted_at")
  private Date deletedAt;
  @SerializedName("received_at")
  private Date receivedAt;
  @SerializedName("type")
  private String type;
  @SerializedName("locked")
  private Boolean locked;
  @SerializedName("filename")
  private String filename;
  @SerializedName("size")
  private Long size;
  @SerializedName("content_type")
  private String contentType; 
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
  @SerializedName("document_versions")
  private DocumentVersion[] documentVersions;
}

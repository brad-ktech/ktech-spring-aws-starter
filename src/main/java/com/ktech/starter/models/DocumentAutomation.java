package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class DocumentAutomation extends IDObject {
  @SerializedName("state")
  private String state;
  @SerializedName("export_formats")
  private String[] exportFormats;
  @SerializedName("filename")
  private String filename;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("matter")
  private Matter matter;
  @SerializedName("document_template")
  private DocumentTemplate documentTemplate;
  @SerializedName("documents")
  private Document[] documents;
}

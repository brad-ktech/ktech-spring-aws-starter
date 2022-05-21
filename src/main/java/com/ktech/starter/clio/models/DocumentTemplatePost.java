package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import com.ktech.starter.annotations.ApiPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ApiPath(path="document_templates")
@ToString
public class DocumentTemplatePost extends IDObject {
  @SerializedName("document_category")
  private DocumentCategory documentCategory;
  @SerializedName("file") // as base64 encoded string
  private String file;
  @SerializedName("filename")
  private String filename;
}

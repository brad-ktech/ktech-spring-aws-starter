package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import com.ktech.starter.annotations.ApiFieldList;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.annotations.EncodedFields;
import com.ktech.starter.clio.messages.FieldList;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@ApiPath(path="document_templates")
@ApiFieldList
public class DocumentTemplate extends IDObject {
  @SerializedName("size")
  private String size;
  @SerializedName("content_type")
  private String contentType;
  @SerializedName("filename")
  private String filename;
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;
  @SerializedName("document_category")
  private DocumentCategory documentCategory;

  @EncodedFields
  public String getFields(){
    return DocumentTemplateDefaultFields.toEncodedString();
  }

  static FieldList DocumentTemplateDefaultFields;

  static {
    FieldList fl = new FieldList();
    fl.addField("id");
    fl.addField("etag");
    fl.addField("size");
    fl.addField("content_type");
    fl.addField("filename");
    fl.addField("created_at");
    fl.addField("updated_at");

    FieldList.FieldSet fs = fl.addFieldSet("document_category");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("name");
    fs.addField("created_at");
    fs.addField("updated_at");

    fs = fl.addFieldSet("last_modified_by");
    fs.addField("account_owner");
    fs.addField("clio_connect");
    fs.addField("court_rules_default_attendee");
    fs.addField("default_calendar_id");
    fs.addField("email");
    fs.addField("enabled");
    fs.addField("etag");
    fs.addField("first_name");
    fs.addField("id");
    fs.addField("initials");
    fs.addField("last_name");
    fs.addField("name");
    fs.addField("phone_number");
    fs.addField("rate");
    fs.addField("subscription_type");
    fs.addField("time_zone");
    fs.addField("created_at");
    fs.addField("updated_at");

    DocumentTemplateDefaultFields = fl;
  }
}

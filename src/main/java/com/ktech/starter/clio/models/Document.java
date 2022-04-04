package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import com.ktech.starter.annotations.ApiFieldList;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.annotations.EncodedFields;
import com.ktech.starter.clio.messages.FieldList;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter @Setter
@ApiPath(path="documents")
@ApiFieldList
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
  private ExternalProperty[] externalProperties;
  @SerializedName("document_versions")
  private DocumentVersion[] documentVersions;

  @EncodedFields
  public String getFields(){
    return DocumentDefaultFields.toEncodedString();
  }


  static FieldList FolderDefaultFields;
  static FieldList DocumentDefaultFields;

  static {

    FieldList fl = new FieldList();
    fl.addField("id");
    fl.addField("etag");
    fl.addField("created_at");
    fl.addField("updated_at");
    fl.addField("deleted_at");
    fl.addField("type");
    fl.addField("locked");
    fl.addField("name");

    FieldList.FieldSet fs = fl.addFieldSet("parent");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("deleted_at");
    fs.addField("type");
    fs.addField("locked");
    fs.addField("name");
    fs.addField("root");

    fs = fl.addFieldSet("matter");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("number");
    fs.addField("display_number");
    fs.addField("custom_number");
    fs.addField("description");
    fs.addField("status");
    fs.addField("location");
    fs.addField("client_reference");
    fs.addField("billable");
    fs.addField("maildrop_address");
    fs.addField("billing_method");
    fs.addField("open_date");
    fs.addField("close_date");
    fs.addField("pending_date");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("shared");
    fs.addField("has_tasks");

    fs = fl.addFieldSet("contact");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("name");
    fs.addField("first_name");
    fs.addField("middle_name");
    fs.addField("last_name");
    fs.addField("type");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("prefix");
    fs.addField("title");
    fs.addField("initials");
    fs.addField("clio_connect_email");
    fs.addField("locked_clio_connect_email");
    fs.addField("client_connect_user_id");
    fs.addField("primary_email_address");
    fs.addField("secondary_email_address");
    fs.addField("primary_phone_number");
    fs.addField("secondary_phone_number");
    fs.addField("ledes_client_id");
    fs.addField("is_client");
    fs.addField("is_co_counsel");
    fs.addField("has_clio_for_clients_permission");
    fs.addField("is_bill_recipient");

    fs = fl.addFieldSet("document_category");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("name");
    fs.addField("created_at");
    fs.addField("updated_at");

    fs = fl.addFieldSet("creator");
    fs.addField("account_owner");
    fs.addField("clio_connect");
    fs.addField("court_rules_default_attendee");
    fs.addField("default_calendar_id");
    fs.addField("email");
    fs.addField("enabled");
    fs.addField("id");
    fs.addField("type");
    fs.addField("initials");
    fs.addField("first_name");
    fs.addField("last_name");
    fs.addField("name");
    fs.addField("phone_number");
    fs.addField("rate");
    fs.addField("subscription_type");
    fs.addField("time_zone");
    fs.addField("roles");
    fs.addField("created_at");
    fs.addField("updated_at");

    fs = fl.addFieldSet("latest_document_version");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("uuid");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("filename");
    fs.addField("size");
    fs.addField("version_number");
    fs.addField("content_type");
    fs.addField("received_at");
    fs.addField("put_url");
    fs.addField("fully_uploaded");
    fs.addField("put_headers");

    fs = fl.addFieldSet("external_properties");
    fs.addField("id");
    fs.addField("name");
    fs.addField("value");

    FolderDefaultFields = fl;

    fl = new FieldList();
    fl.addField("id");
    fl.addField("etag");
    fl.addField("created_at");
    fl.addField("updated_at");
    fl.addField("deleted_at");
    fl.addField("type");
    fl.addField("locked");
    fl.addField("name");
    fl.addField("received_at");
    fl.addField("filename");
    fl.addField("size");
    fl.addField("content_type");

    fs = fl.addFieldSet("parent");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("deleted_at");
    fs.addField("type");
    fs.addField("locked");
    fs.addField("name");
    fs.addField("root");

    fs = fl.addFieldSet("matter");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("number");
    fs.addField("display_number");
    fs.addField("custom_number");
    fs.addField("description");
    fs.addField("status");
    fs.addField("location");
    fs.addField("client_reference");
    fs.addField("billable");
    fs.addField("maildrop_address");
    fs.addField("billing_method");
    fs.addField("open_date");
    fs.addField("close_date");
    fs.addField("pending_date");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("shared");
    fs.addField("has_tasks");

    fs = fl.addFieldSet("contact");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("name");
    fs.addField("first_name");
    fs.addField("middle_name");
    fs.addField("last_name");
    fs.addField("type");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("prefix");
    fs.addField("title");
    fs.addField("initials");
    fs.addField("clio_connect_email");
    fs.addField("locked_clio_connect_email");
    fs.addField("client_connect_user_id");
    fs.addField("primary_email_address");
    fs.addField("secondary_email_address");
    fs.addField("primary_phone_number");
    fs.addField("secondary_phone_number");
    fs.addField("ledes_client_id");
    fs.addField("is_client");
    fs.addField("is_co_counsel");
    fs.addField("has_clio_for_clients_permission");
    fs.addField("is_bill_recipient");

    fs = fl.addFieldSet("document_category");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("name");
    fs.addField("created_at");
    fs.addField("updated_at");

    fs = fl.addFieldSet("creator");
    fs.addField("account_owner");
    fs.addField("clio_connect");
    fs.addField("court_rules_default_attendee");
    fs.addField("default_calendar_id");
    fs.addField("email");
    fs.addField("enabled");
    fs.addField("id");
    fs.addField("type");
    fs.addField("initials");
    fs.addField("first_name");
    fs.addField("last_name");
    fs.addField("name");
    fs.addField("phone_number");
    fs.addField("rate");
    fs.addField("subscription_type");
    fs.addField("time_zone");
    fs.addField("roles");
    fs.addField("created_at");
    fs.addField("updated_at");

    fs = fl.addFieldSet("latest_document_version");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("uuid");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("filename");
    fs.addField("size");
    fs.addField("version_number");
    fs.addField("content_type");
    fs.addField("received_at");
    fs.addField("put_url");
    fs.addField("fully_uploaded");
    fs.addField("put_headers");

    fs = fl.addFieldSet("document_versions");
    fs.addField("id");
    fs.addField("etag");
    fs.addField("uuid");
    fs.addField("created_at");
    fs.addField("updated_at");
    fs.addField("filename");
    fs.addField("size");
    fs.addField("version_number");
    fs.addField("content_type");
    fs.addField("received_at");
    fs.addField("put_url");
    fs.addField("fully_uploaded");

    fs = fl.addFieldSet("external_properties");
    fs.addField("id");
    fs.addField("name");
    fs.addField("value");


    DocumentDefaultFields = fl;

  }


}

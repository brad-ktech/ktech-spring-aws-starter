package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import com.ktech.starter.annotations.ApiFieldList;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.annotations.EncodedFields;
import com.ktech.starter.clio.messages.FieldList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ApiPath(path="document_categories")
@ApiFieldList
@ToString
public class DocumentCategory extends NamedObject {
  @SerializedName("created_at")
  private Date createdAt;
  @SerializedName("updated_at")
  private Date updatedAt;

  @EncodedFields
  public String getFields(){
    return DocumentCategoryDefaultFields.toEncodedString();
  }

  static FieldList DocumentCategoryDefaultFields;

  static {
    FieldList fl = new FieldList();
    fl.addField("id");
    fl.addField("etag");
    fl.addField("name");
    fl.addField("created_at");
    fl.addField("updated_at");

    DocumentCategoryDefaultFields = fl;
  }
}

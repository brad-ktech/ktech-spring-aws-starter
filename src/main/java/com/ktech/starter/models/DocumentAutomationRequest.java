package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DocumentAutomationRequest{
  @SerializedName("formats")
  private String[] formats;
  @SerializedName("filename")
  private String filename;
  @SerializedName("matter")
  private Matter matter;
  @SerializedName("document_template") @JsonProperty("document_template")
  private DocumentTemplate documentTemplate;
}

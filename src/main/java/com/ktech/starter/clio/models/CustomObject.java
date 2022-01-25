package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter @Setter
public class CustomObject{

  @SerializedName("id")
  public String id;
  
  public String toString() {


    return this.toString(0);
  }    
  
  public String toString(int anIndentFactor) {
    JSONObject obj = new JSONObject(this);
    return obj.toString(anIndentFactor);
  }      
  
}

package com.ktech.starter.clio.models;

import org.json.JSONObject;

public class Summary {

  public String toString() {
    return this.toString(0);
  }    
  
  public String toString(int anIndentFactor) {
    JSONObject obj = new JSONObject(this);
    return obj.toString(anIndentFactor);
  }    
  
}

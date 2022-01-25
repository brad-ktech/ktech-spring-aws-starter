package com.ktech.starter.clio.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContingencyFee extends IDObject {

  @SerializedName("show_contingency_award")
  public String showContingencyAward;
  
}

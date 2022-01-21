package com.ktech.starter.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@Getter @Setter @ToString(callSuper = true)
public class AuthToken{

  @SerializedName("token_type")
  private String tokenType;
  @SerializedName("access_token")
  private String accessToken;
  @SerializedName("expires_in")
  private String expiresIn;
  @SerializedName("refresh_token")
  private String refreshToken;


  public String toJson() {
    JSONObject obj = new JSONObject(this);
    return obj.toString();

  }

}

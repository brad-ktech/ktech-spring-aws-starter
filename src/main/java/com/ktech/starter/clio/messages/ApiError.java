package com.ktech.starter.clio.messages;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiError {
    @SerializedName("type")
    protected String type;
    @SerializedName("message")
    protected String message;
}

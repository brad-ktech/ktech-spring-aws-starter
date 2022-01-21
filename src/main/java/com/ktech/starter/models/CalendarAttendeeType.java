package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CalendarAttendeeType {
    @SerializedName("Calendar")
    @JsonProperty("Calendar")
    CALENDAR("Calendar"),
    @SerializedName("Contact")
    @JsonProperty("Contact")
    CONTACT("Contact");

    private final String clioName;
}

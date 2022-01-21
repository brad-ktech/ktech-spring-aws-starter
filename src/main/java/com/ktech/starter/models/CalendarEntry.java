package com.ktech.starter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@ToString(callSuper = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiFields(fields="id,etag,summary,description,location,start_at,end_at,all_day,calendar_owner_id,calendar_owner{id}")
@ApiPath(path="calendar_entries")
public class CalendarEntry extends IDObject {
    @SerializedName("summary")
    private String summary;
    @SerializedName("description")
    private String description;
    @SerializedName("location")
    private String location;
    @SerializedName("permission")
    private String permission;
    @SerializedName("start_at")
    @JsonProperty("start_at")
    private String startAt;
    @SerializedName("end_at")
    @JsonProperty("end_at")
    private String endAt;
    @SerializedName("created_at")
    @JsonProperty("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    @JsonProperty("updated_at")
    private String updatedAt;
    @SerializedName("all_day")
    @JsonProperty("all_day")
    private boolean allDay;
    @SerializedName("matter")
    private Matter matter;
    @SerializedName("recurrence_rule")
    @JsonProperty("recurrence_rule")
    private String recurrenceRule;
    @SerializedName("parent_calendar_entry_id")
    @JsonProperty("parent_calendar_entry_id")
    private Long parentCalendarEntryId;
    @SerializedName("parent_calendar_entry")
    @JsonProperty("parent_calendar_entry")
    private CalendarEntry parentCalendarEntry;
    @SerializedName("calendar_owner")
    @JsonProperty("calendar_owner")
    private Calendar calendar;
    private List<CalendarAttendee> attendees;
    @SerializedName("external_properties")
    @JsonProperty("external_properties")
    private List<ExternalProperty> externalProperties;
}

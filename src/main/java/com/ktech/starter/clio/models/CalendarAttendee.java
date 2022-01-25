package com.ktech.starter.clio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CalendarAttendee {
    private long id;
    private String name;
    private boolean enabled;
    private String email;
    private CalendarAttendeeType type;
}

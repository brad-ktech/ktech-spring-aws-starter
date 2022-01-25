package com.ktech.starter.clio.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum CalendarType {
    AD_HOC("AdhocCalendar"),
    ACCOUNT("AccountCalendar"),
    USER("UserCalendar");

    private final String clioName;
}

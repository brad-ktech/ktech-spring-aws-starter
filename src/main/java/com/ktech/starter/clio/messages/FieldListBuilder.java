package com.ktech.starter.clio.messages;

public class FieldListBuilder {
    private final FieldList fieldList;

    public FieldListBuilder() {
        this.fieldList = new FieldList();
    }

    public FieldListBuilder field(String name) {
        fieldList.addField(name);
        return this;
    }

    public FieldList build() {
        return fieldList;
    }

    public static FieldListBuilder start(String name) {
        return new FieldListBuilder().field(name);
    }

}

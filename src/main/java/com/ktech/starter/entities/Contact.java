package com.ktech.starter.entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="contacts")
@Getter
@Setter
public class Contact extends BaseEntity{


    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="primary_email")
    private String primaryEmail;

    @Column(name="secondary_email")
    private String secondaryEmail;


    @Column(name="is_bill_recipient")
    private String billRecipient;


    public boolean isBillRecipient(){

        return BooleanUtils.toBoolean(billRecipient);
    }


}

package com.ktech.starter.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="matters")
@Getter
@Setter
public class Matter extends BaseEntity{


    @Column(name="DISPLAY_NUMBER")
    private String displayNumber;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="status")
    private String status;

}

package com.ktech.starter.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.number.money.MonetaryAmountFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="bills")
@Getter
@Setter
public class Bill extends BaseEntity{

    @Column(name="number")
    private String number;








}

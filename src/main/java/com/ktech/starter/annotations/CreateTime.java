package com.ktech.starter.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Add to a field in your entity class and the system will
 * autopopulate the field with the datetime of the creation of the row 
 * in the database.  
 * Fires only on insert, not on update
 * @author bradj
 *
 */


@Retention(RUNTIME)
@Target(FIELD)
public @interface CreateTime {

}

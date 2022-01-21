package com.ktech.starter.enums;

/**
 * <p>
 * Base enumeration interface for building enums that have a value associated
 * with them.
 * <p>
 * 
 *
 * @param <T>
 */
public interface BaseEnum<T> {

	public T getValue();

	public final static String VALUE_FIELD_NAME = "value";

}

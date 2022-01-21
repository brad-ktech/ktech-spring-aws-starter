package com.ktech.starter.dao;

import com.ktech.starter.annotations.CreateTime;
import com.ktech.starter.annotations.EditTime;
import com.ktech.starter.annotations.UUIDByRandom;
import com.ktech.starter.annotations.UUIDByTime;
import com.ktech.starter.utilities.Reflectotron;
import com.ktech.starter.utilities.UUIDGenerator;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public class PersistenceListener {

	@PrePersist
	public void prePersist(Object target) {

		populateUUIDFields(target);
		populateCreateFields(target);
		populateEditFields(target);

	}
	
	@PreUpdate
	public void preUpdate(Object target) {
		
		populateEditFields(target);
		
	}



	private void populateUUIDFields(Object target) {

		List<Field> timez = Reflectotron.getFieldsWithAnnotation(target.getClass(), UUIDByTime.class);
		timez.stream().forEach(uu -> {
			UUID uuid = UUIDGenerator.getTimeBasedUUID();
			populateUUIDField(target, uu, uuid);

		});

		List<Field> randomz = Reflectotron.getFieldsWithAnnotation(target.getClass(), UUIDByRandom.class);
		randomz.stream().forEach(uu -> {
			UUID uuid = UUIDGenerator.getTimeBasedUUID();
			populateUUIDField(target, uu, uuid);

		});

	}

	private void populateUUIDField(Object target, Field field, UUID uuid) {

		field.setAccessible(true);
		try {

			if (field.getClass().isAssignableFrom(UUID.class)) {

				field.set(target, uuid);

			} else if (field.getClass().isAssignableFrom(String.class)) {
				field.set(target, uuid.toString());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		field.setAccessible(false);

	}

	private void populateCreateFields(Object target) {

		populateTimeFields(target, CreateTime.class);
	}
	
	private void populateEditFields(Object target) {
		
		populateTimeFields(target, EditTime.class);
	}
	
	private void populateTimeFields(Object target, Class annotation) {
		
		try {
			List<Field> times = Reflectotron.getFieldsWithAnnotation(target.getClass(), annotation);
			LocalDateTime now = LocalDateTime.now();
			for (Field field : times) {
				field.setAccessible(true);
				field.set(target, now);
				field.setAccessible(false);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	

}

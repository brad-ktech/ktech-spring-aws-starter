package com.ktech.starter.dao;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


public class AutoDaoService extends BaseDaoService{

	
	private EntityManager em;
	
	private Logger logger =  LogManager.getLogger(AutoDaoService.class);
	
	public AutoDaoService(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
		
	}

	@Override
	public Logger getLogger() {
		return logger;
	}



}

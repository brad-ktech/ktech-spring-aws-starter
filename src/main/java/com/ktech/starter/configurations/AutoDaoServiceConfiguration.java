package com.ktech.starter.configurations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ktech.starter.dao.AutoDaoService;
import com.ktech.starter.dao.DaoAccelerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
		value="rds.enabled",
		havingValue = "true",
		matchIfMissing = true)
public class AutoDaoServiceConfiguration {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Bean
	public AutoDaoService getDao() {
		
		return new AutoDaoService(em);
	}

	@Bean
	public DaoAccelerator getAccelorator(@Autowired AutoDaoService dao){

		return new DaoAccelerator(dao);
	}
	
	

}

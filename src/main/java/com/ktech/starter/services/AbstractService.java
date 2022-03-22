package com.ktech.starter.services;

import com.ktech.starter.dao.AutoDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(
        value="rds.enabled",
        havingValue = "true",
        matchIfMissing = true)
@Slf4j
public class AbstractService {

    @Autowired(required=false)
    protected AutoDaoService dao;



}

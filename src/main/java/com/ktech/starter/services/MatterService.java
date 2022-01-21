package com.ktech.starter.services;


import com.ktech.starter.dao.AutoDaoService;
import com.ktech.starter.entities.Matter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatterService {

    private AutoDaoService dao;

    public MatterService(@Autowired AutoDaoService dao){

        this.dao = dao;
    }

    public Optional<Matter> getMatter(Long id){


        return  dao.find(Matter.class, id);


    }

    }

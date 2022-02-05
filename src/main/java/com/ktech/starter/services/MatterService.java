package com.ktech.starter.services;


import com.ktech.starter.entities.Matter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatterService extends AbstractService{



    public Optional<Matter> getMatter(Long id){


        return  dao.find(Matter.class, id);


    }

    }

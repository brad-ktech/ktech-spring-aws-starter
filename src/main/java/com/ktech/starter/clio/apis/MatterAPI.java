package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.Matter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class MatterAPI extends AbstractRestAPI{
    public Matter getMatter(Long id) {
        return doGet(Matter.class, id);
    }
}

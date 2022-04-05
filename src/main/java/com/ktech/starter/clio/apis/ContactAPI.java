package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.Contact;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class ContactAPI extends AbstractRestAPI{
    public Contact getContact(Long id) {
        return doGet(Contact.class, id);
    }
}

package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.CalendarEntry;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
@RequestScope
public class CalendarAPI extends AbstractRestAPI{


    public <T> void saveCalendarEntry(CalendarEntry ce) throws IOException, URISyntaxException {

            doPost(ce);

    }




}

package com.ktech.starter.apis;


import com.ktech.starter.models.CalendarEntry;
import com.ktech.starter.models.Request;
import com.ktech.starter.services.ClioApi;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CalendarEntryApi extends ClioApi {




    public CalendarEntry createCalendarEntry(CalendarEntry ca) throws URISyntaxException {

        synchronized (lock) {
            URIBuilder builder = new URIBuilder();
            URI uri = builder.setScheme("https")
                             .setHost(vault.getAPITarget())
                             .setPath(getPathFromClass(ca.getClass()))
                             .setParameter("fields", encodeFields(getFieldsFromClass(ca.getClass()))).build();

            Request<CalendarEntry> request = new Request<>(ca);
            return rest.patchForObject(uri, request, CalendarEntry.class);



        }


    }
}

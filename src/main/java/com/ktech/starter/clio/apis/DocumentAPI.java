package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.Document;
import com.ktech.starter.clio.models.Folder;
import com.ktech.starter.clio.models.NameValuePair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequestScope
public class DocumentAPI extends AbstractRestAPI {
    public Boolean uploadDocument(String documentName, Long matterId, byte[] fileByteArray) throws UnsupportedEncodingException {
        Document document = postDocument(documentName, matterId);

        if(document == null) {
            return false;
        }

        int statusCode = putDocument(document, fileByteArray);

        if(statusCode != 200) {
            return false;
        }

        finalizeDocument(document);

        return true;
    }

    private Document postDocument(String documentName, Long matterId) {
        Folder parent = new Folder();
        parent.setType("Matter");
        parent.setId(matterId);
        Document document = new Document();
        document.setParent(parent);
        document.setName(documentName);

        return doPost(document);
    }

    private int putDocument(Document document, byte[] formBytes) throws UnsupportedEncodingException {
        String url = document.getLatestDocumentVersion().getPutUrl();
        url = java.net.URLDecoder.decode(url, StandardCharsets.UTF_8.name());
        NameValuePair[] headerValues = document.getLatestDocumentVersion().getPutHeaders();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        for(NameValuePair headerValue : headerValues) {
            headers.add(headerValue.getName(), headerValue.getValue());
        }

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(formBytes, headers);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        return responseEntity.getStatusCode().value();
    }

    private void finalizeDocument(Document document) {
        document.getLatestDocumentVersion().setFullyUploaded(true);
        doPatch(document, document.getId(), document.getLatestDocumentVersion());
    }
}

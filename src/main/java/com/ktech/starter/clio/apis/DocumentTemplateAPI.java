package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class DocumentTemplateAPI extends AbstractRestAPI {
    @Autowired
    private DocumentCategoryAPI documentCategoryAPI;

    public DocumentTemplate getDocumentTemplate(Long id) {
        return doGet(DocumentTemplate.class, id);
    }

    public Optional<DocumentTemplate> getDocumentTemplateByName(String documentTemplateName) {
        List<DocumentTemplate> documents = getAllDocumentTemplates();

        return documents.stream().filter(d -> d.getFilename().equals(documentTemplateName)).findFirst();
    }

    public List<DocumentTemplate> getAllDocumentTemplates() {
        return doGetList(DocumentTemplate.class).getData();
    }

    public void uploadDocumentTemplate(String filename, String category, byte[] byteArray) {
        String base64Data = Base64.getEncoder().encodeToString(byteArray);

        DocumentTemplatePost documentTemplatePost = new DocumentTemplatePost();
        documentTemplatePost.setFilename(filename);
        documentTemplatePost.setFile(base64Data);

        if(category != null) {
            Optional<DocumentCategory> opt = documentCategoryAPI.getDocumentCategoryByName(category);
            opt.ifPresent(documentTemplatePost::setDocumentCategory);
        }

        doPost(documentTemplatePost);
    }

    public void updateDocumentTemplate(Long id, String category, byte[] byteArray) {
        DocumentTemplatePost documentTemplatePost = new DocumentTemplatePost();
        documentTemplatePost.setId(id);

        if(category != null) {
            Optional<DocumentCategory> opt = documentCategoryAPI.getDocumentCategoryByName(category);
            opt.ifPresent(documentTemplatePost::setDocumentCategory);
        }

        if(byteArray != null) {
            String base64Data = Base64.getEncoder().encodeToString(byteArray);

            documentTemplatePost.setFile(base64Data);
            documentTemplatePost.setFilename(getDocumentTemplate(id).getFilename());
        }

        doPatch(documentTemplatePost, id, documentTemplatePost);
    }

    public byte[] getDocumentTemplateAsByteArray(Long anId) throws IOException {
        return doGetDownload(DocumentTemplate.class, anId);
    }

    public boolean deleteDocumentTemplate(DocumentTemplate documentTemplate) {
        return doDel(DocumentTemplate.class, documentTemplate.getId());
    }
}

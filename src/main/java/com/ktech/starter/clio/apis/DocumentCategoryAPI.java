package com.ktech.starter.clio.apis;


import com.ktech.starter.clio.models.DocumentCategory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;

@Service
@RequestScope
public class DocumentCategoryAPI extends AbstractRestAPI {
    public DocumentCategory getDocumentCategory(Long id) {
        return doGet(DocumentCategory.class, id);
    }

    public Optional<DocumentCategory> getDocumentCategoryByName(String documentCategoryName) {
        List<DocumentCategory> documentCategories = getAllDocumentCategories();

        return documentCategories.stream().filter(d -> d.getName().equals(documentCategoryName)).findFirst();
    }

    public List<DocumentCategory> getAllDocumentCategories() {
        return doGetList(DocumentCategory.class).getData();
    }
}

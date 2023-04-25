package com.visual.face.search.engine.model;


import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private List<SearchDocument> documents = new ArrayList<>();

    public SearchResult(){}

    public SearchResult(List<SearchDocument> documents) {
        if(null != documents){
            this.documents = documents;
        }
    }

    public static SearchResult build(List<SearchDocument> documents){
        return new SearchResult(documents);
    }

    public List<SearchDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<SearchDocument> documents) {
        this.documents = documents;
    }
}

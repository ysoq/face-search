package com.visual.face.search.engine.model;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

    private SearchStatus status;

    private List<SearchResult> result = new ArrayList<>();

    public SearchResponse(){}

    public SearchResponse(SearchStatus status, List<SearchResult> result) {
        this.status = status;
        if(null != result){
            this.result = result;
        }
    }

    public static SearchResponse build(SearchStatus status, List<SearchResult> result){
        return new SearchResponse(status, result);
    }

    public SearchStatus getStatus() {
        return status;
    }

    public void setStatus(SearchStatus status) {
        this.status = status;
    }

    public List<SearchResult> getResult() {
        return result;
    }

    public void setResult(List<SearchResult> result) {
        this.result = result;
    }
}

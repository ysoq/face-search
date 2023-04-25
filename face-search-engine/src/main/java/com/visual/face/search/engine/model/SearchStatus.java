package com.visual.face.search.engine.model;

public class SearchStatus {
    private  int code;
    private String reason;

    public SearchStatus(){}

    public SearchStatus(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public static SearchStatus build(int code, String reason){
        return new SearchStatus(code, reason);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean ok() {
        return this.code == 0;
    }
}

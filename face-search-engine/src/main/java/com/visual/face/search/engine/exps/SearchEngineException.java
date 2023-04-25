package com.visual.face.search.engine.exps;


public class SearchEngineException extends RuntimeException{

    public SearchEngineException() {
    }

    public SearchEngineException(String message) {
        super(message);
    }

    public SearchEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchEngineException(Throwable cause) {
        super(cause);
    }

    public SearchEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

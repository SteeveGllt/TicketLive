package com.steeve.ticketlive.exception;

import lombok.Getter;

public class ConcertNotFoundException extends Exception {

    public enum ConcertNotFoundError {
        CONCERT_NOT_FOUND("Concert not found", 404);

        private final String message;
        private final int code;

        ConcertNotFoundError(String message, int code){
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }

    }

    private ConcertNotFoundError error;
    private String message;

    public ConcertNotFoundException(ConcertNotFoundError error, String message){
        this.error = error;
        this.message = message;
    }

    public ConcertNotFoundError getError() {
        return error;
    }


    public void setError(ConcertNotFoundError error) {
        this.error = error;
    }

    @Override
    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

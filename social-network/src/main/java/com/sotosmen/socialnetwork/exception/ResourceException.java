package com.sotosmen.socialnetwork.exception;

import org.springframework.http.HttpStatus;

public class ResourceException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7503719110260925768L;
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

    public ResourceException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }

}

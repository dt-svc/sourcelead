package com.bis.sourcelead.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * <b>Purpose:</b><br>
 * 
 * Exception Class for any given SourceLeadObject id not found.
 * Generic Exception class used across all the getById methods in dao layer.
 * Contains slf4j logger for the logging the user logs.
 * Uses two property files exception.properties application.properties for managing the exception messages.   
 * 
 
 * 
 * <b>RevisionHistory:</b>
 * 
 * <pre>
 * <b>
 * Sl No   Modified Date        Author</b>
 * ==============================================
 * 1.        22-07-2016            Srinivas Vemula
 *      -- Base Release
 * 2		
 * 
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Wrong Input") //404
public class SourceLeadConstraintViolationException extends Exception {
	

	private static final long serialVersionUID = -6135788866589258575L;
	private String errorMessage;
	private String errorCode;
	private String rootCause;
    
    public SourceLeadConstraintViolationException(String id){
        super("SourceLeadConstraintViolationException "+id);
    }

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}
    
    
    
}
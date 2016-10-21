package com.bis.sourcelead.exception;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
 
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Table does not contain any records") //404
public class NoRecordsFoundException extends Exception {
 
	private static final long serialVersionUID = 3111011359484623261L;
	private String errorMessage;
	private String errorCode;
	private String rootCause;

    public NoRecordsFoundException(String id){
        super("NoRecordsFoundException "+id);
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
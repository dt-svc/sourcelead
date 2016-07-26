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
 
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Parent or Child Key does not Exists") //404
public class ParentChildRelationShipException extends Exception {
 
   
	private static final long serialVersionUID = 1401126131746737051L;
	private String errorMessage;
	private String errorCode;
	private String rootCause;

    public ParentChildRelationShipException(String id){
        super("Child or Parent Object Not Found "+id);
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
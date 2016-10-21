package com.bis.sourcelead.beans;

import org.springframework.stereotype.Component;
/**
 * 
 * <b>Purpose:</b><br>
 * 
 * Generic Exception Class for all the  SourceLeadExceptionObjects.
 * Class used to extend for the user defined Exceptions.  
 *  
 * @author: Srinivas Vemula 
 * @date: 22nd July 2016 
 * <br>
 * 
 * <b>RevisionHistory:</b>
 * 
 * <pre>
 * 
 * <b>
 * Sl No   Modified Date        Author</b>
 * ==============================================
 * 1.        22-07-2016            Srinivasa Vemula
 *      -- Base Release
 * 2		
 * </pre>
 */
@Component
public class SourceLeadExceptionObject {
	private String errorMessage;
	private String errorCode;
	private String rootCause;
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

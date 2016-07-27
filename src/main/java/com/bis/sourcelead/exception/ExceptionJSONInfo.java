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
public class ExceptionJSONInfo {
	 
    private String url;
    private String message;
     
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
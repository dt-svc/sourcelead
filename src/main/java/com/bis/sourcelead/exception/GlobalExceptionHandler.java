package com.bis.sourcelead.exception;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.beans.SourceLeadExceptionObject;
/**
 * <b>Purpose:</b><br>
 * 
 * we can use with any class to define our global exception handler.
 * The handler methods in Global Controller Advice is same as Controller based exception handler methods and
 * used when controller class is not able to handle the exception
 * allows you to define ONE controller class to handle ALL exceptions that could occur (no specific exceptions defined needed)
 * controls the HTTP status code returned
 * controls the json message returned by grabbing the exception message from the specific exception that occurred
 * we have defined all the Exception class, then all the exceptions thrown by our request handler method will have handled.
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
@ControllerAdvice
public class GlobalExceptionHandler {
 
	@Autowired
	SourceLeadExceptionObject sourceLeadExceptionObject;
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public SourceLeadExceptionObject handleSQLException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex);
        sourceLeadExceptionObject.setErrorMessage(ex.getMessage());
       // sourceLeadExceptionObject.setRootCause(ex.toString());
        return sourceLeadExceptionObject;
    }
    
    @ResponseBody
    @ExceptionHandler(org.springframework.orm.hibernate4.HibernateJdbcException.class)
    public SourceLeadExceptionObject handleRuntimeException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex);
        sourceLeadExceptionObject.setErrorMessage( ex.getCause().getCause().getMessage());
       // sourceLeadExceptionObject.setRootCause(ex.toString());
      
        return sourceLeadExceptionObject;
    }
   
    @ResponseBody
    @ExceptionHandler( org.hibernate.exception.GenericJDBCException.class)
    public SourceLeadExceptionObject handleJDBCException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex);
        sourceLeadExceptionObject.setErrorMessage(ex.getMessage());
      //  sourceLeadExceptionObject.setRootCause(ex.toString());

        return sourceLeadExceptionObject;
    }
    @ResponseBody
    @ExceptionHandler(SourceLeadObjectStatusException.class)
    public SourceLeadExceptionObject handleSourceLeadStatusException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex);
        sourceLeadExceptionObject.setErrorMessage(ex.getMessage());
        sourceLeadExceptionObject.setRootCause(ex.toString());

        return sourceLeadExceptionObject;
    }  
    @ResponseBody
    @ExceptionHandler(NoRecordsFoundException.class)
    public SourceLeadExceptionObject handleNoRecordsFoundException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex);
        sourceLeadExceptionObject.setErrorMessage(ex.getMessage());
        sourceLeadExceptionObject.setRootCause(ex.toString());

        return sourceLeadExceptionObject;
    }  
    
    @ResponseBody
    @ExceptionHandler(SourceLeadObjectNotFoundException.class)
    public SourceLeadExceptionObject handleSourceLeadObjectNotFoundException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex);
        sourceLeadExceptionObject.setErrorMessage(ex.getMessage());
        sourceLeadExceptionObject.setRootCause(ex.toString());

        return sourceLeadExceptionObject;
    }   
    
    /*@ResponseBody
    @ExceptionHandler({ParentChildRelationShipException.class,SourceLeadConstraintViolationException.class,SourceLeadObjectNotFoundException.class,
    					NoRecordsFoundException.class,SourceLeadObjectStatusException.class})
    public SourceLeadExceptionObject handleParentChildRelationShipException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex);
        sourceLeadExceptionObject.setErrorMessage(ex.getMessage());
        //sourceLeadExceptionObject.setRootCause(ex.toString());

        return sourceLeadExceptionObject;
    }  */ 
    
    @ResponseBody
    @ExceptionHandler(SourceLeadConstraintViolationException.class)
    public SourceLeadExceptionObject handleSourceLeadConstraintViolationException(HttpServletRequest request, Exception ex){
        logger.error("Requested URL="+request.getRequestURL());
        logger.error("Exception Raised="+ex.getMessage());
        sourceLeadExceptionObject.setErrorMessage(ex.getMessage());
        sourceLeadExceptionObject.setRootCause(ex.getMessage());
        return sourceLeadExceptionObject;
    }
}
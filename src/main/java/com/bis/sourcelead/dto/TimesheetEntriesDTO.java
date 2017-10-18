package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.TimesheetSubmissions;

public class TimesheetEntriesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long Id;
	
	private TimesheetSubmissions timesheetSubmissions;
	
	private ProjectTasks projectTask;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer totalHours;
	 
	private Integer submittedHours;
	  
	private String entryStatus;
	
    private String monHours;
	  
    private String moncomments;
	  
	private String tueHours;
	  
	private String tuecomments;
	  
	private String wedHours;
	  
	private String wedcomments;
	  
	private String thuHours;
	  
	private String thucomments;
	  
	private String friHours;
	  
	private String fricomments;
	  
	private String satHours;
	  
	private String satcomments;
	
	private String sunHours;
	  
	private String suncomments;
	  
	private String entryType;
	  
	private String status;
	  
	private Date createdDate;
		
	private String createdBy;
		
    private Date updatedDate; 
		
	private String updatedBy;
   
	private String weekcomments;

	

	
}

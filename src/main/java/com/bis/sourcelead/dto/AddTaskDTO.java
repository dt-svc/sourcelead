package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

public class AddTaskDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Required(desc="assignTo")
	private Long assignTo;
	
	private String priority;
	
	@Required(desc="taskDatpicker")
	private String taskDatpicker;
	
	@Required(desc="timepicker")
	private String timepicker;
	
	private String repeat;
	
	@CleanHtml(escape=false)
	private String description;
	
	@Required(desc="subject")
	private String subject;
	
	private String email;
	
	private String notification;

	public Long getAssignTo()
	{
		return assignTo;
	}

	public void setAssignTo(Long assignTo)
	{
		this.assignTo = assignTo;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getTaskDatpicker()
	{
		return taskDatpicker;
	}

	public void setTaskDatpicker(String taskDatpicker)
	{
		this.taskDatpicker = taskDatpicker;
	}

	public String getTimepicker()
	{
		return timepicker;
	}

	public void setTimepicker(String timepicker)
	{
		this.timepicker = timepicker;
	}

	public String getRepeat()
	{
		return repeat;
	}

	public void setRepeat(String repeat)
	{
		this.repeat = repeat;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getNotification()
	{
		return notification;
	}

	public void setNotification(String notification)
	{
		this.notification = notification;
	}
	
	
}

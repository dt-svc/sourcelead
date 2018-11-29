package com.bis.sourcelead.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.persistence.Table;

/**
 * 
 * @author rajat rana
 *
 */

@Entity
@Table(name = "CRON_TRACKER")
public class CronTracker implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "MODULE_NAME")
    private String moduleName;
	
	@Column(name ="METHOD_NAME")
	private String methodName;

	@Column(name = "CRON_EXPRESSION")
    private String cronExpression;
	
	@Column(name = "CRON_TYPE")
	private String crontype;
	
	@Column(name ="CRON_RUN_DATE_TIME")
	private Date cronRunDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getCrontype() {
		return crontype;
	}

	public void setCrontype(String crontype) {
		this.crontype = crontype;
	}

	public Date getCronRunDateTime() {
		return cronRunDateTime;
	}

	public void setCronRunDateTime(Date cronRunDateTime) {
		this.cronRunDateTime = cronRunDateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}					
}

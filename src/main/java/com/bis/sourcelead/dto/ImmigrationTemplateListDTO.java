package com.bis.sourcelead.dto;

import java.io.Serializable;



public class ImmigrationTemplateListDTO extends SearchDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private Integer status;
	private Long docsCount;
	private Long formsCount;
	private Long tasksCount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getDocsCount() {
		return docsCount;
	}
	public void setDocsCount(Long docsCount) {
		this.docsCount = docsCount;
	}
	public Long getFormsCount() {
		return formsCount;
	}
	public void setFormsCount(Long formsCount) {
		this.formsCount = formsCount;
	}
	public Long getTasksCount() {
		return tasksCount;
	}
	public void setTasksCount(Long tasksCount) {
		this.tasksCount = tasksCount;
	}


}
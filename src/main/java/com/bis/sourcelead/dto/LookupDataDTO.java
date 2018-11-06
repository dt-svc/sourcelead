package com.bis.sourcelead.dto;

public class LookupDataDTO{
	
	private long id;
	
	private String name;
	
	private String refer;
	
	private String description;
	
	private Long referId;
	
	private Long lookupId;
	
	private String taskType;
	
	private String colorPic;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRefer() {
		return refer;
	}

	public String getDescription() {
		return description;
	}

	public Long getReferId() {
		return referId;
	}

	public Long getLookupId() {
		return lookupId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setReferId(Long referId) {
		this.referId = referId;
	}

	public void setLookupId(Long lookupId) {
		this.lookupId = lookupId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getColorPic() {
		return colorPic;
	}

	public void setColorPic(String colorPic) {
		this.colorPic = colorPic;
	}
}

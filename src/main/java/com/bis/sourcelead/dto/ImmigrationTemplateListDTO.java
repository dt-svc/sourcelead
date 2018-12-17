package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class ImmigrationTemplateListDTO extends SearchDTO implements Serializable {

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
    private Long updatedDate;
	private String petitionType;
	private String country;
	private String rejectReason;
	private String templateUUID;
	

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

    /**
     * @return the updatedDate
     */
    public Long getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getTemplateUUID() {
		return templateUUID;
	}

	public void setTemplateUUID(String templateUUID) {
		this.templateUUID = templateUUID;
	}

}

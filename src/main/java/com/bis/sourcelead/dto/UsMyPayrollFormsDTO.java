package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class UsMyPayrollFormsDTO extends SearchDTO implements Serializable {
	private String id;
	private String orgLocationId;
	private String candidate;
	private String formName;
	private String fileContentType;
	private String fileName;
	private String fileUrl;
	private int status;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private String uuid;
	private String staticPath;
	private List<FileInfo> fileInfo;
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	
	public String getCandidate()
	{
		return candidate;
	}
	
	public void setCandidate(String candidate)
	{
		this.candidate = candidate;
	}
	
	public String getFormName()
	{
		return formName;
	}
	
	public void setFormName(String formName)
	{
		this.formName = formName;
	}
	
	public String getFileContentType()
	{
		return fileContentType;
	}
	
	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getFileUrl()
	{
		return fileUrl;
	}
	
	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public String getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public String getUpdatedDate()
	{
		return updatedDate;
	}
	
	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	
	public String getUuid()
	{
		return uuid;
	}
	
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public List<FileInfo> getFileInfo()
	{
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo)
	{
		this.fileInfo = fileInfo;
	}

	public String getStaticPath()
	{
		return staticPath;
	}

	public void setStaticPath(String staticPath)
	{
		this.staticPath = staticPath;
	}
}

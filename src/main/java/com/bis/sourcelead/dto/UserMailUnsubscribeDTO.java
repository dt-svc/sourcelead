package com.bis.sourcelead.dto;

public class UserMailUnsubscribeDTO {

	private Long id;
	
	private String userId;
	
	private String userLocationId;
	
	private String tabIds ;
	
	private String updatedBy;
	
	private String createdDate;
	
    private String updatedDate;
    
    private String uniqueToken;
    
    private String email;
    
    private String credential;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserLocationId()
	{
		return userLocationId;
	}

	public void setUserLocationId(String userLocationId)
	{
		this.userLocationId = userLocationId;
	}

	public String getTabIds()
	{
		return tabIds;
	}

	public void setTabIds(String tabIds)
	{
		this.tabIds = tabIds;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUniqueToken()
	{
		return uniqueToken;
	}

	public void setUniqueToken(String uniqueToken)
	{
		this.uniqueToken = uniqueToken;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getCredential()
	{
		return credential;
	}

	public void setCredential(String credential)
	{
		this.credential = credential;
	}
    
}

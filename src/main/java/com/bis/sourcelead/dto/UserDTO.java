package com.bis.sourcelead.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
	private long id;
	private String name;
	private String designation;
	private String profileImage;
	private long parentId;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDesignation()
	{
		return designation;
	}
	
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	
	public String getProfileImage()
	{
		return profileImage;
	}
	
	public void setProfileImage(String profileImage)
	{
		this.profileImage = profileImage;
	}
	
	public long getParentId()
	{
		return parentId;
	}
	
	public void setParentId(long parentId)
	{
		this.parentId = parentId;
	}
}

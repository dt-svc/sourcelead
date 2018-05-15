package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CONTACTS_GROUP")
public class ContactsGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "GROUP_NAME", columnDefinition = "TEXT default NULL")
	private String groupName;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_USER", nullable = false)
   	private User createdUser;
	
	@Column(name="STATUS")
	private Integer status;
	
	
	@JoinTable(name = "REFER_CONTACTS_GROUP", joinColumns = {
    @JoinColumn(name = "CONTACT_GROUP_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
    @JoinColumn(name = "CONTACT_ID", referencedColumnName = "ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<ReferContacts> contactsList;
	 
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public User getCreatedUser()
	{
		return createdUser;
	}

	public void setCreatedUser(User createdUser)
	{
		this.createdUser = createdUser;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public List<ReferContacts> getContactsList()
	{
		return contactsList;
	}

	public void setContactsList(List<ReferContacts> contactsList)
	{
		this.contactsList = contactsList;
	}
	
}

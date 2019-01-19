package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "CAMPAIGN_SENDER")
public class CreateSender  {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocations;
	
	@Column(name = "FROM_NAME")
	private String fromName;
	
	
	@Column(name = "FROM_MAIL")
	private String fromMail;
	
	@Column(name = "REPLY_TO")
	private String replyTo;
	
	
	@Column(name = "NICK_NAME")
	private String nickName;
	
	@Column(name = "IS_SENDER_EMAIKL_VERIFIED")
	private String isSenderEmaiklVerified;
	
	@Column(name = "SENDER_ID")
	private String senderId;
	
	@Column(name = "SELECTED_SENDER")
	private String selectedSender;
	
	
	@Column(name = "CITY", columnDefinition = "LONGTEXT default NULL")
	private String city;
	
	@Column(name = "ZIP")
	private String zip;
	
	@Column(name = "COUNTRY", columnDefinition = "LONGTEXT default NULL")
	private String country;
	
	@Column(name = "STATE", columnDefinition = "LONGTEXT default NULL")
	private String state;
	
	
	@Column(name = "ADDRESS", columnDefinition = "LONGTEXT default NULL")
	private String address;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public String getSelectedSender()
	{
		return selectedSender;
	}

	public void setSelectedSender(String selectedSender)
	{
		this.selectedSender = selectedSender;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public OrgLocations getOrgLocations()
	{
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations)
	{
		this.orgLocations = orgLocations;
	}

	public String getFromName()
	{
		return fromName;
	}

	public void setFromName(String fromName)
	{
		this.fromName = fromName;
	}

	public String getReplyTo()
	{
		return replyTo;
	}

	public void setReplyTo(String replyTo)
	{
		this.replyTo = replyTo;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getIsSenderEmaiklVerified()
	{
		return isSenderEmaiklVerified;
	}

	public void setIsSenderEmaiklVerified(String isSenderEmaiklVerified)
	{
		this.isSenderEmaiklVerified = isSenderEmaiklVerified;
	}
	

	public String getSenderId()
	{
		return senderId;
	}

	public void setSenderId(String senderId)
	{
		this.senderId = senderId;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
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

	public User getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(User createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
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

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getFromMail()
	{
		return fromMail;
	}

	public void setFromMail(String fromMail)
	{
		this.fromMail = fromMail;
	}

	
	

}

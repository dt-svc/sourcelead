package com.bis.sourcelead.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "MOBILE_PUSH_NOTIFICATION")
public class MobilePushNotification implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User userId;
    
    @ManyToOne
	@JoinColumn(name = "DEVICE_ID", nullable = false)
	private MobileDeviceTokenInformaion deviceId;
    
    @Column(name = "NOTIFICATION_DESCRPTION",columnDefinition = "LONGTEXT default NULL")
   	private String notificationDescritpion;
    
    @Column(name = "NOTIFICATION_STATUS")
   	private Integer status;
   		
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;

   	@Column(name = "CREATED_BY")
   	private Long createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public MobileDeviceTokenInformaion getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(MobileDeviceTokenInformaion deviceId) {
		this.deviceId = deviceId;
	}

	public String getNotificationDescritpion() {
		return notificationDescritpion;
	}

	public void setNotificationDescritpion(String notificationDescritpion) {
		this.notificationDescritpion = notificationDescritpion;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

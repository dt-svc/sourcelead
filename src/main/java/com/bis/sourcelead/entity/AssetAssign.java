package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author Laxman
 *
 */
@Entity
@Table(name = "ASSET_ASSIGN")
public class AssetAssign {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ASSET_ID")        
    private Asset asset;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USER_ROLE_ID")        
    private UserRole userRole;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="SHIFT_ID")        
    private Shift shift;
	
	@Column(name = "ASSIGNED_ON")
	private String assignedOn;
	
	@Column(name = "RETURNED_ON")
	private String returnedOn;
	
	@Column(name = "FILE_URL")
	@Type(type="text")
	private String fileURL;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "STATUS")
	private int status;

	public Long getId() {
		return id;
	}

	public Asset getAsset() {
		return asset;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public String getFileURL() {
		return fileURL;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public int getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAssignedOn() {
		return assignedOn;
	}

	public String getReturnedOn() {
		return returnedOn;
	}

	public void setAssignedOn(String assignedOn) {
		this.assignedOn = assignedOn;
	}

	public void setReturnedOn(String returnedOn) {
		this.returnedOn = returnedOn;
	}
}

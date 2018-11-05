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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private Date assignedOn;
	
	@Column(name = "RETURNED_ON")
	private Date returnedOn;
	
	@Column(name = "FILE_URL")
	@Type(type="text")
	private String fileURL;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "STATUS")
	private int status;

	@Transient
    @JsonProperty
    private String assignedOnStr;

	@Transient
    @JsonProperty
    private String returnedOnStr;
	
	@Transient
    @JsonProperty
    private String employeeId;
	
	@Transient
    @JsonProperty
    private String employeeName;
	
	@Transient
    @JsonProperty
    private String shiftName;
	
	@Transient 
	@JsonProperty
	private String categoryName;
	
	@Transient
    @JsonProperty
    private String assetName;
	
	@Transient
    @JsonProperty
    private String serialNumber;
	
	@Transient
    @JsonProperty
    private String assetUUID;
	
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

	public Date getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(Date assignedOn) {
		this.assignedOn = assignedOn;
	}

	public Date getReturnedOn() {
		return returnedOn;
	}

	public void setReturnedOn(Date returnedOn) {
		this.returnedOn = returnedOn;
	}

	public String getAssignedOnStr() {
		return assignedOnStr;
	}

	public String getReturnedOnStr() {
		return returnedOnStr;
	}

	public void setAssignedOnStr(String assignedOnStr) {
		this.assignedOnStr = assignedOnStr;
	}

	public void setReturnedOnStr(String returnedOnStr) {
		this.returnedOnStr = returnedOnStr;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getShiftName() {
		return shiftName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getAssetName() {
		return assetName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAssetUUID() {
		return assetUUID;
	}

	public void setAssetUUID(String assetUUID) {
		this.assetUUID = assetUUID;
	}
}

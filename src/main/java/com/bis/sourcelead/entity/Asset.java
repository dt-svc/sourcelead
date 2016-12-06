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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "ASSET", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "asset")
public class Asset implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@Column(name = "SL_NO")
	private String slno;
	
	
	@Column(name = "NAME")
	private String name;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ASSET_CATEGORY_ID")
	private AssetCategory assetCategory;
	
	@Column(name = "MAKE")
	private String make;
	
	@Column(name = "MODEL")
	private String model;
	
	@Column(name = "MEMRORY")
	private String memory;
	
	
	@Column(name = "WORKING_STATUS")
	private String workingStatus;
	
	@Column(name = "CPU")
	private String cpu;
	
	@Column(name = "HARD_DISK")
	private String hardDisk;
	
	@Column(name = "MONITOR")
	private String monitor;
	

	@Column(name = "ATTACHMENT",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SHIFT_A_EMPLOYEE")
	private Employees shiftAEmployee;
	
	@Column(name = "SHIFT_A_ATTACHMENT",columnDefinition="mediumblob")
	@Lob
	private  byte[] shiftADocFile;
		
	@Column(name = "SHIFT_A_FILE_CONTENT_TYPE")
	private String shiftAFileContentType;
	
	@Column(name = "SHIFT_A_FILE_NAME")
	private String shiftAFileName;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SHIFT_B_EMPLOYEE")
	private Employees shiftBEmployee;
	
	@Column(name = "SHIFT_B_ATTACHMENT",columnDefinition="mediumblob")
	@Lob
	private  byte[] shiftBDocFile;
		
	@Column(name = "SHIFT_B_FILE_CONTENT_TYPE")
	private String shiftBFileContentType;
	
	@Column(name = "SHIFT_B_FILE_NAME")
	private String shiftBFileName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocations;
	
	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String shiftAEmpName;
	
	@Transient 
	private String shiftBEmpName;
	
	@Transient 
	private String categoryName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AssetCategory getAssetCategory() {
		return assetCategory;
	}

	public void setAssetCategory(AssetCategory assetCategory) {
		this.assetCategory = assetCategory;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}


	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getHardDisk() {
		return hardDisk;
	}

	public void setHardDisk(String hardDisk) {
		this.hardDisk = hardDisk;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public Employees getShiftAEmployee() {
		return shiftAEmployee;
	}

	public void setShiftAEmployee(Employees shiftAEmployee) {
		this.shiftAEmployee = shiftAEmployee;
	}

	public byte[] getShiftADocFile() {
		return shiftADocFile;
	}

	public void setShiftADocFile(byte[] shiftADocFile) {
		this.shiftADocFile = shiftADocFile;
	}

	public String getShiftAFileContentType() {
		return shiftAFileContentType;
	}

	public void setShiftAFileContentType(String shiftAFileContentType) {
		this.shiftAFileContentType = shiftAFileContentType;
	}

	public String getShiftAFileName() {
		return shiftAFileName;
	}

	public void setShiftAFileName(String shiftAFileName) {
		this.shiftAFileName = shiftAFileName;
	}

	public Employees getShiftBEmployee() {
		return shiftBEmployee;
	}

	public void setShiftBEmployee(Employees shiftBEmployee) {
		this.shiftBEmployee = shiftBEmployee;
	}

	public byte[] getShiftBDocFile() {
		return shiftBDocFile;
	}

	public void setShiftBDocFile(byte[] shiftBDocFile) {
		this.shiftBDocFile = shiftBDocFile;
	}

	public String getShiftBFileContentType() {
		return shiftBFileContentType;
	}

	public void setShiftBFileContentType(String shiftBFileContentType) {
		this.shiftBFileContentType = shiftBFileContentType;
	}

	public String getShiftBFileName() {
		return shiftBFileName;
	}

	public void setShiftBFileName(String shiftBFileName) {
		this.shiftBFileName = shiftBFileName;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getShiftAEmpName() {
		return shiftAEmpName;
	}

	public void setShiftAEmpName(String shiftAEmpName) {
		this.shiftAEmpName = shiftAEmpName;
	}

	public String getShiftBEmpName() {
		return shiftBEmpName;
	}

	public void setShiftBEmpName(String shiftBEmpName) {
		this.shiftBEmpName = shiftBEmpName;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}

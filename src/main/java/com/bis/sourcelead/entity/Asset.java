package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ASSET")
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
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Column(name = "ISSUE_DATE")
	private Date issueDate;

	@Column(name = "ATTACHMENT",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "MAC_ADDRESS")
	private String macAddress;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_URL")
	private String fileUrl;
	
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocations;
	
	
	@OneToMany(mappedBy="asset",cascade=CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<AssetAssign> assetAssign;
	
	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
	
	@Column(name = "ASSET_UUID")
	private String assetUUID;
	
	@Column(name = "PURCHASE_DATE")
	private Date purchaseDate;
	
	@Column(name = "DEPRECIATION_DATE")
	private Date depreciationDate;
	
	@Column(name = "OPERATING_SYSTEM")
	private String operatingSystem;
	
	@Column(name = "OS_KEY")
	private String osKey;
	
	@Column(name = "ANTI_VIRUS")
	private String antiVirus;
	
	@Column(name = "COST")
	private BigDecimal cost;
	
	@Column(name = "DEPRECIATION_AMOUNT")
	private BigDecimal depreciationAmount;
	
	@Column(name = "VENDOR_DETAILS")
	private String vendorDetails;
	
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
	@JsonProperty
	private String categoryName;
	
	@Transient
    @JsonProperty
    private String issueDateStr;
  
	@Transient 
	private List<Employees> employeesList;

	@Transient
    @JsonProperty
    private String assetTags;
	
	@Transient
	@JsonProperty
    private Set<String> tagsList;
	
	
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
		if(assetCategory !=null) {
			categoryName = assetCategory.getCategoryName();
		}
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Employees> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<Employees> employeesList) {
		this.employeesList = employeesList;
	}

	public byte[] getDocFile()
	{
		return docFile;
	}

	public void setDocFile(byte[] docFile)
	{
		this.docFile = docFile;
	}

	 
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Set<AssetAssign> getAssetAssign() {
		return assetAssign;
	}

	public void setAssetAssign(Set<AssetAssign> assetAssign) {
		this.assetAssign = assetAssign;
	}

	public String getIssueDateStr() {
		if(issueDate != null) {
			SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
			issueDateStr = dateFormat.format(issueDate);	
		}
		return issueDateStr;
	}

	public void setIssueDateStr(String issueDateStr) {
		this.issueDateStr = issueDateStr;
	}

	public String getAssetTags() {
		return assetTags;
	}

	public Set<String> getTagsList() {
		return tagsList;
	}

	public void setAssetTags(String assetTags) {
		this.assetTags = assetTags;
	}

	public void setTagsList(Set<String> tagsList) {
		this.tagsList = tagsList;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public Date getDepreciationDate() {
		return depreciationDate;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public String getOsKey() {
		return osKey;
	}

	public String getAntiVirus() {
		return antiVirus;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public BigDecimal getDepreciationAmount() {
		return depreciationAmount;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setDepreciationDate(Date depreciationDate) {
		this.depreciationDate = depreciationDate;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public void setOsKey(String osKey) {
		this.osKey = osKey;
	}

	public void setAntiVirus(String antiVirus) {
		this.antiVirus = antiVirus;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public void setDepreciationAmount(BigDecimal depreciationAmount) {
		this.depreciationAmount = depreciationAmount;
	}

	public String getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(String vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

	public String getAssetUUID() {
		return assetUUID;
	}

	public void setAssetUUID(String assetUUID) {
		this.assetUUID = assetUUID;
	}
}

package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrglocationFormFieldsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long sectionId;
	private SourceleadSectionDTO sectionData;
	private Long orglocationId;
	private Long orgId;
	private String labelName;
	private String fieldName;
	private String labelinfo;
	private String validationType;
	private String validationRange;
	private String validation;
	//private String optionLabelName;
	private List<OrglocationFormSubFieldsDTO> optionData;
	private String fieldType;
	private String minDate;
	private String maxDate;
	private String placeHolder;
	private String fieldDescription;
	private int isRequired;
	private Long slDependedntField;
	private Long customDependentField;
	private int status;
	private String customEditType;
	private String imagePathUrl;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public Long getOrglocationId() {
		return orglocationId;
	}
	public void setOrglocationId(Long orglocationId) {
		this.orglocationId = orglocationId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLabelinfo() {
		return labelinfo;
	}
	public void setLabelinfo(String labelinfo) {
		this.labelinfo = labelinfo;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getPlaceHolder() {
		return placeHolder;
	}
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}
	public String getFieldDescription() {
		return fieldDescription;
	}
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}
	public int getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	public Long getSlDependedntField() {
		return slDependedntField;
	}
	public void setSlDependedntField(Long slDependedntField) {
		this.slDependedntField = slDependedntField;
	}
	public Long getCustomDependentField() {
		return customDependentField;
	}
	public void setCustomDependentField(Long customDependentField) {
		this.customDependentField = customDependentField;
	}
	public List<OrglocationFormSubFieldsDTO> getOptionData() {
		return optionData;
	}
	public void setOptionData(List<OrglocationFormSubFieldsDTO> optionData) {
		this.optionData = optionData;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public SourceleadSectionDTO getSectionData() {
		return sectionData;
	}
	public void setSectionData(SourceleadSectionDTO sectionData) {
		this.sectionData = sectionData;
	}
	public String getCustomEditType() {
		return customEditType;
	}
	public void setCustomEditType(String customEditType) {
		this.customEditType = customEditType;
	}
	public String getValidationType() {
		return validationType;
	}
	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}
	public String getValidationRange() {
		return validationRange;
	}
	public void setValidationRange(String validationRange) {
		this.validationRange = validationRange;
	}
	public String getImagePathUrl() {
		return imagePathUrl;
	}
	public void setImagePathUrl(String imagePathUrl) {
		this.imagePathUrl = imagePathUrl;
	}
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	
	
}

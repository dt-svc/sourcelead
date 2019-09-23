package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

	
	@Entity
	@Table(name = "ASSET_CUSTOM_FIELD_DATA")
	public class AssetCustomFieldData implements Serializable{
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID", nullable = false)
		private Long id;
		
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="ASSET_ID", nullable=false )        
	    private Asset assets;
		
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="CUSTOM_FORM_FIELD_ID", nullable=false )        
	    private OrglocationFormFields orglocationsFormFields;
		
		@Column(name = "CUSTOM_FORM_FIELD_DATA",columnDefinition = "TEXT default NULL")
		private String customFormFieldData;
		
		@Column(name = "FORM_FIELD_ADDITIONAL_DATA_1",columnDefinition = "TEXT default NULL")
		private String customFormFieldData1;
		
		@Column(name = "FORM_FIELD_ADDITIONAL_DATA_2",columnDefinition = "TEXT default NULL")
		private String customFormFieldData2;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public Asset getAssets() {
			return assets;
		}

		public void setAssets(Asset assets) {
			this.assets = assets;
		}

		public OrglocationFormFields getOrglocationsFormFields() {
			return orglocationsFormFields;
		}

		public void setOrglocationsFormFields(OrglocationFormFields orglocationsFormFields) {
			this.orglocationsFormFields = orglocationsFormFields;
		}

		public String getCustomFormFieldData() {
			return customFormFieldData;
		}

		public void setCustomFormFieldData(String customFormFieldData) {
			this.customFormFieldData = customFormFieldData;
		}

		public String getCustomFormFieldData1() {
			return customFormFieldData1;
		}

		public void setCustomFormFieldData1(String customFormFieldData1) {
			this.customFormFieldData1 = customFormFieldData1;
		}

		public String getCustomFormFieldData2() {
			return customFormFieldData2;
		}

		public void setCustomFormFieldData2(String customFormFieldData2) {
			this.customFormFieldData2 = customFormFieldData2;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	
	
	
}

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
@Table(name = "SOURCELEAD_TAX_CHALLAN_ITEM_SUB_FORMFIELD")
public class SourceleadTaxChallanItemSubFormField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOURCELEAD_TAX_CHALLAN_ITEM_ID", nullable = false)
	private SourceleadTaxChallanItem sourceleadTaxChallanItem;

	@Column(name = "LABEL_NAME")
	private String labelName;

/*	@Column(name = "FIELD_NAME")
	private String fieldName;

	@Column(name = "FIELD_DESC", columnDefinition = "TEXT default NULL")
	private String fieldDesc;

	@Column(name = "ACTIVE")
	private boolean active;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SourceleadTaxChallanItem getSourceleadTaxChallanItem() {
		return sourceleadTaxChallanItem;
	}

	public void setSourceleadTaxChallanItem(SourceleadTaxChallanItem sourceleadTaxChallanItem) {
		this.sourceleadTaxChallanItem = sourceleadTaxChallanItem;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}

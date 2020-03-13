package com.bis.sourcelead.bgc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
@Entity
@Table(name = "BGC_COMPONENT_JSONMAP")
public class BgcComponentJsonMap implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_ID")
	private BgcTemplate bgcTemplate;
	
	@Column(name = "FORM_ID ")
	private String formId;;
	
	@Column(name = "SENT_TO ")
	private String sentTo;;
	
	
	@Column(name = "JSON_STRING", columnDefinition = "TEXT default NULL")
	private String jsonString ;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BgcTemplate getBgcTemplate() {
		return bgcTemplate;
	}

	public void setBgcTemplate(BgcTemplate bgcTemplate) {
		this.bgcTemplate = bgcTemplate;
	}

	public String getFormId() {
		return formId;
	}

	public String getSentTo() {
		return sentTo;
	}

	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}

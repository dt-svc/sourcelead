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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "IMMIGRATON_LIST_VIEW", schema = "SOURCELEAD")
public class ImmigrationListView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "CURRENT_USCIS_STATUS ")
	private Long currUscisStatus;

	@Column(name = "PETITON_TYPE")
	private String petitionType;
	
	@Column(name = "OPEN_DATE")
	private Date openDate;

	@Column(name = "FIRS_TNAME")
	private String firstName;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE ")
	private String phone;

	@Column(name = "ORG_NAME ")
	private String orgName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrUscisStatus() {
		return currUscisStatus;
	}

	public void setCurrUscisStatus(Long currUscisStatus) {
		this.currUscisStatus = currUscisStatus;
	}

	public String getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(String petitionType) {
		this.petitionType = petitionType;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
}

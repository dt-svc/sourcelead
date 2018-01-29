package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="REGISTER_OPTIONAL_SERVICES")
public class RegistrationOptionalService {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false)
	private Long id;
	
	@Column(name ="REGISTER_NAME")
	private String registerName;
	
	@Column(name ="REGISTER_TYPE")
	private String registerType;
	
	@Column(name ="REGISTER_STATUS")
	private int registerStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public int getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(int registerStatus) {
		this.registerStatus = registerStatus;
	}
	
		
}

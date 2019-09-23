package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Shivayogi
 */

@Entity
@Table(name = "PAYROLL")
public class Payroll implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	
	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;

	@Column(name = "CHECK_DATE")
	private Date checkDate;

	@Column(name = "CHECK_NUMBER")
	private String checkNumber;

	@Column(name = "GROSS")
	private String gross;

	@Column(name = "FWT")
	private String fwt;
	
	
	@Column(name = "SSEC")
	private String ssec;

	@Column(name = "MED")
	private String med;
	
	@Column(name = "SWT")
	private String swt;
	
	@Column(name = "SDI_SUI")
	private String sdisui;
	
	@Column(name = "LOCAL")
	private String local;
	
	@Column(name = "DEDS")
	private String deds;
	
	@Column(name = "NET")
	private String net;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getGross() {
		return gross;
	}

	public void setGross(String gross) {
		this.gross = gross;
	}

	public String getFwt() {
		return fwt;
	}

	public void setFwt(String fwt) {
		this.fwt = fwt;
	}

	public String getSsec() {
		return ssec;
	}

	public void setSsec(String ssec) {
		this.ssec = ssec;
	}

	public String getMed() {
		return med;
	}

	public void setMed(String med) {
		this.med = med;
	}

	public String getSwt() {
		return swt;
	}

	public void setSwt(String swt) {
		this.swt = swt;
	}

	public String getSdisui() {
		return sdisui;
	}

	public void setSdisui(String sdisui) {
		this.sdisui = sdisui;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getDeds() {
		return deds;
	}

	public void setDeds(String deds) {
		this.deds = deds;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
		
}

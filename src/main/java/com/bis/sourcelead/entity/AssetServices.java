package com.bis.sourcelead.entity;

import java.math.BigDecimal;
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
@Table(name = "ASSET_SERVICE")
public class AssetServices {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ASSET_ID")        
    private Asset asset;
	
	@Column(name = "SERVICE_DATE")
	private Date serviceDate;
	
	@Column(name = "RECEIVED_DATE")
	private Date recivedDate;
	
	@Column(name = "COST")
	private BigDecimal cost;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name = "STATUS")
	private int status;

	@Transient
    @JsonProperty
    private String serviceDateStr;

	@Transient
    @JsonProperty
    private String receivedDateStr;
	
	public Long getId() {
		return id;
	}

	public Asset getAsset() {
		return asset;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public Date getRecivedDate() {
		return recivedDate;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public String getDescription() {
		return description;
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

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public void setRecivedDate(Date recivedDate) {
		this.recivedDate = recivedDate;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getServiceDateStr() {
		return serviceDateStr;
	}

	public String getReceivedDateStr() {
		return receivedDateStr;
	}

	public void setServiceDateStr(String serviceDateStr) {
		this.serviceDateStr = serviceDateStr;
	}

	public void setReceivedDateStr(String receivedDateStr) {
		this.receivedDateStr = receivedDateStr;
	}
}

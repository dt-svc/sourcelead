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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author Shivayogi
 */
@Entity
@Table(name = "CANDIDATE_CERTIFICATIONS_HISTORY")
public class CandidateCertificationsHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_CERTIFICATIONS", nullable = false)
	private CandidateCertifications candidateCertifications;
	@Column(name = "NAME")
	private String name;
	@Column(name = "PROVIDER")
	private String provider;
	@Column(name = "SCORE")
	private String score;
	@Column(name = "SCORE_TYPE")
	private String scoreType;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "VALIDFROM")
	private Date validFrom;
	@Column(name = "VALIDTO")
	private Date validTo;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = " CREATED_BY")
	private String createdBy;
	@Column(name = " STATUS")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_USER")
	private User createdUser;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getProvider()
	{
		return provider;
	}
	
	public void setProvider(String provider)
	{
		this.provider = provider;
	}
	
	public String getScore()
	{
		return score;
	}
	
	public void setScore(String score)
	{
		this.score = score;
	}
	
	public String getScoreType()
	{
		return scoreType;
	}
	
	public void setScoreType(String scoreType)
	{
		this.scoreType = scoreType;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public Date getValidFrom()
	{
		return validFrom;
	}
	
	public void setValidFrom(Date validFrom)
	{
		this.validFrom = validFrom;
	}
	
	public Date getValidTo()
	{
		return validTo;
	}
	
	public void setValidTo(Date validTo)
	{
		this.validTo = validTo;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	public CandidateCertifications getCandidateCertifications()
	{
		return candidateCertifications;
	}
	
	public void setCandidateCertifications(CandidateCertifications candidateCertifications)
	{
		this.candidateCertifications = candidateCertifications;
	}
	
	public User getCreatedUser()
	{
		return createdUser;
	}
	
	public void setCreatedUser(User createdUser)
	{
		this.createdUser = createdUser;
	}
}

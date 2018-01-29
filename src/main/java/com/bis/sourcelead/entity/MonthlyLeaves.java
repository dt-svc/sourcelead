package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MONTHLY_LEAVES")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "monthlyLeaves")
public class MonthlyLeaves {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JsonBackReference
	@JoinColumn(name = "LEAVE_ID", nullable = false)
	private Leave leaveId;
	
	@Column(name = "JANUARY_LEAVES")
	private int januaryLeaves;
	
	@Column(name = "FEBRUARY_LEAVES")
	private int februaryLeaves;
	
	@Column(name = "MARCH_LEAVES")
	private int marchLeaves;
	
	@Column(name = "APRIL_LEAVES")
	private int aprilLeaves;
	
	@Column(name = "MAY_LEAVES")
	private int mayLeaves;
	
	@Column(name = "JUNE_LEAVES")
	private int juneLeaves;
	
	@Column(name = "JULY_LEAVES")
	private int julyLeaves;
	
	@Column(name = "AUGUST_LEAVES")
	private int augustLeaves;
	
	@Column(name = "SEPTEMBER_LEAVES")
	private int septLeaves;
	
	@Column(name = "OCTOBER_LEAVES")
	private int octoberLeaves;
	
	@Column(name = "NOVEMBER_LEAVES")
	private int novLeaves;
	
	@Column(name = "DECEMBER_LEAVES")
	private int decLeaves;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Leave getLeaveId()
	{
		return leaveId;
	}

	public void setLeaveId(Leave leaveId)
	{
		this.leaveId = leaveId;
	}

	public int getJanuaryLeaves()
	{
		return januaryLeaves;
	}

	public void setJanuaryLeaves(int januaryLeaves)
	{
		this.januaryLeaves = januaryLeaves;
	}

	public int getFebruaryLeaves()
	{
		return februaryLeaves;
	}

	public void setFebruaryLeaves(int februaryLeaves)
	{
		this.februaryLeaves = februaryLeaves;
	}

	public int getMarchLeaves()
	{
		return marchLeaves;
	}

	public void setMarchLeaves(int marchLeaves)
	{
		this.marchLeaves = marchLeaves;
	}

	public int getAprilLeaves()
	{
		return aprilLeaves;
	}

	public void setAprilLeaves(int aprilLeaves)
	{
		this.aprilLeaves = aprilLeaves;
	}

	public int getMayLeaves()
	{
		return mayLeaves;
	}

	public void setMayLeaves(int mayLeaves)
	{
		this.mayLeaves = mayLeaves;
	}

	public int getJuneLeaves()
	{
		return juneLeaves;
	}

	public void setJuneLeaves(int juneLeaves)
	{
		this.juneLeaves = juneLeaves;
	}

	public int getJulyLeaves()
	{
		return julyLeaves;
	}

	public void setJulyLeaves(int julyLeaves)
	{
		this.julyLeaves = julyLeaves;
	}

	public int getAugustLeaves()
	{
		return augustLeaves;
	}

	public void setAugustLeaves(int augustLeaves)
	{
		this.augustLeaves = augustLeaves;
	}

	public int getSeptLeaves()
	{
		return septLeaves;
	}

	public void setSeptLeaves(int septLeaves)
	{
		this.septLeaves = septLeaves;
	}

	public int getOctoberLeaves()
	{
		return octoberLeaves;
	}

	public void setOctoberLeaves(int octoberLeaves)
	{
		this.octoberLeaves = octoberLeaves;
	}

	public int getNovLeaves()
	{
		return novLeaves;
	}

	public void setNovLeaves(int novLeaves)
	{
		this.novLeaves = novLeaves;
	}

	public int getDecLeaves()
	{
		return decLeaves;
	}

	public void setDecLeaves(int decLeaves)
	{
		this.decLeaves = decLeaves;
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

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	
}

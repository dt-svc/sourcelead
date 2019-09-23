package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "MONTHLY_LEAVES")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "monthlyLeaves")
public class MonthlyLeaves {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JsonBackReference
	@JoinColumn(name = "LEAVE_ID", nullable = false)
	private Leave leaveId;
	
	@Column(name = "JANUARY_LEAVES")
	private float januaryLeaves;
	
	@Column(name = "FEBRUARY_LEAVES")
	private float februaryLeaves;
	
	@Column(name = "MARCH_LEAVES")
	private float marchLeaves;
	
	@Column(name = "APRIL_LEAVES")
	private float aprilLeaves;
	
	@Column(name = "MAY_LEAVES")
	private float mayLeaves;
	
	@Column(name = "JUNE_LEAVES")
	private float juneLeaves;
	
	@Column(name = "JULY_LEAVES")
	private float julyLeaves;
	
	@Column(name = "AUGUST_LEAVES")
	private float augustLeaves;
	
	@Column(name = "SEPTEMBER_LEAVES")
	private float septLeaves;
	
	@Column(name = "OCTOBER_LEAVES")
	private float octoberLeaves;
	
	@Column(name = "NOVEMBER_LEAVES")
	private float novLeaves;
	
	@Column(name = "DECEMBER_LEAVES")
	private float decLeaves;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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

	public float getJanuaryLeaves()
	{
		return januaryLeaves;
	}

	public void setJanuaryLeaves(float januaryLeaves)
	{
		this.januaryLeaves = januaryLeaves;
	}

	public float getFebruaryLeaves()
	{
		return februaryLeaves;
	}

	public void setFebruaryLeaves(float februaryLeaves)
	{
		this.februaryLeaves = februaryLeaves;
	}

	public float getMarchLeaves()
	{
		return marchLeaves;
	}

	public void setMarchLeaves(float marchLeaves)
	{
		this.marchLeaves = marchLeaves;
	}

	public float getAprilLeaves()
	{
		return aprilLeaves;
	}

	public void setAprilLeaves(float aprilLeaves)
	{
		this.aprilLeaves = aprilLeaves;
	}

	public float getMayLeaves()
	{
		return mayLeaves;
	}

	public void setMayLeaves(float mayLeaves)
	{
		this.mayLeaves = mayLeaves;
	}

	public float getJuneLeaves()
	{
		return juneLeaves;
	}

	public void setJuneLeaves(float juneLeaves)
	{
		this.juneLeaves = juneLeaves;
	}

	public float getJulyLeaves()
	{
		return julyLeaves;
	}

	public void setJulyLeaves(float julyLeaves)
	{
		this.julyLeaves = julyLeaves;
	}

	public float getAugustLeaves()
	{
		return augustLeaves;
	}

	public void setAugustLeaves(float augustLeaves)
	{
		this.augustLeaves = augustLeaves;
	}

	public float getSeptLeaves()
	{
		return septLeaves;
	}

	public void setSeptLeaves(float septLeaves)
	{
		this.septLeaves = septLeaves;
	}

	public float getOctoberLeaves()
	{
		return octoberLeaves;
	}

	public void setOctoberLeaves(float octoberLeaves)
	{
		this.octoberLeaves = octoberLeaves;
	}

	public float getNovLeaves()
	{
		return novLeaves;
	}

	public void setNovLeaves(float novLeaves)
	{
		this.novLeaves = novLeaves;
	}

	public float getDecLeaves()
	{
		return decLeaves;
	}

	public void setDecLeaves(float decLeaves)
	{
		this.decLeaves = decLeaves;
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

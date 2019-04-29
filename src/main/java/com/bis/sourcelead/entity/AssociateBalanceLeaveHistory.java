package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSOCIATE_BALANCE_LEAVE_HISTORY")
public class AssociateBalanceLeaveHistory implements Serializable{
  
  
  private static final long serialVersionUID = 1L;
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
  private Long id;
  
  @Column(name = "USER_ID")
  private Long user;
  
  @Column(name = "ORG_LOCATION_ID")
  private Long orgLocationId;
  
  @Column (name = "START_DATE")
  private Date startDate;
  
  @Column (name = "END_DATE")
  private Date endDate;
  
  @Column (name = "YEAR")
  private Integer year;

  @Column (name = "MONTH")
  private String month;
  
  @Column (name = "TOTAL_BALANCE_LEAVES")
  private Integer totalBalanceLeaves;
  
  @Column (name = "LEAVE_TYPE_ID")
  private Long leaveType;
  
  @Column(name = "CREATED_DATE")
  private Date createdDate;
  
  @Column(name = "CREATED_BY")
  private Long createdBy;
  
  @Column(name = "UPDATED_DATE")
  private Date updatedDate;
  
  @Column(name = "UPDATED_BY")
  private Long updatedBy;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser() {
    return user;
  }

  public void setUser(Long user) {
    this.user = user;
  }

  public Long getOrgLocationId() {
    return orgLocationId;
  }

  public void setOrgLocationId(Long orgLocationId) {
    this.orgLocationId = orgLocationId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public Integer getTotalBalanceLeaves() {
    return totalBalanceLeaves;
  }

  public void setTotalBalanceLeaves(Integer totalBalanceLeaves) {
    this.totalBalanceLeaves = totalBalanceLeaves;
  }

  public Long getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(Long leaveType) {
    this.leaveType = leaveType;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Long getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public Long getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(Long updatedBy) {
    this.updatedBy = updatedBy;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  
}
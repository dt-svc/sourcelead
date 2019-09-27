

package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EXPENSE_APPOVER_VIEW")
public class ExpensePendingApproveReportView {


@Id
@Column(name = "EXPENSE_ID", nullable = false)
private Long expenseId;

@Column(name = "EMP_ID" ,columnDefinition = "TEXT default NULL")
private Long empId;

@Column (name = "EMPLOYMENT_TYPE")
private String employmentType;

@Column(name = "EMP_NAME",columnDefinition = "TEXT default NULL")
private String empName;

@Column (name = "PROJECT_NAME")
private String projectName;

@Column (name = "PROJECT_ID")
private Long projectId;

@Column (name = "DURATION")
private String duration;

@Column (name = "APPROVAL_MECHANISM")
private String approvalMechanism;

@Column (name = "LEVEL_1_NAME",columnDefinition = "TEXT default NULL")
private String level1Name;

@Column (name = "LEVEL_2_NAME",columnDefinition = "TEXT default NULL")
private String level2Name;

@Column (name = "ACTION_PERFORMED_BY",columnDefinition = "TEXT default NULL")
private String actionPerformedby;

@Column (name = "SUBMITTED_DATE")
private Date submittedDate;


@Column (name = "SUBMITTED_Amount")
private BigDecimal submittedAmount;


public BigDecimal getSubmittedAmount() {
return submittedAmount;
}

 

public void setSubmittedAmount(BigDecimal submittedAmount) {
this.submittedAmount = submittedAmount;
}

 

public Long getProjectId() {
return projectId;
}

 

public void setProjectId(Long projectId) {
this.projectId = projectId;
}

 

public Long getExpenseId() {
return expenseId;
}

 

public String getEmploymentType() {
return employmentType;
}

public String getEmpName() {
return empName;
}

public String getProjectName() {
return projectName;
}

public String getDuration() {
return duration;
}

public String getApprovalMechanism() {
return approvalMechanism;
}

public String getLevel1Name() {
return level1Name;
}

public String getLevel2Name() {
return level2Name;
}

public String getActionPerformedby() {
return actionPerformedby;
}


public void setExpenseId(Long expenseId) {
this.expenseId = expenseId;
}

 

public Long getEmpId() {
return empId;
}

 

public void setEmpId(Long empId) {
this.empId = empId;
}

 

public void setEmploymentType(String employmentType) {
this.employmentType = employmentType;
}

public void setEmpName(String empName) {
this.empName = empName;
}

public void setProjectName(String projectName) {
this.projectName = projectName;
}

public void setDuration(String duration) {
this.duration = duration;
}

public void setApprovalMechanism(String approvalMechanism) {
this.approvalMechanism = approvalMechanism;
}

public void setLevel1Name(String level1Name) {
this.level1Name = level1Name;
}

public void setLevel2Name(String level2Name) {
this.level2Name = level2Name;
}

public void setActionPerformedby(String actionPerformedby) {
this.actionPerformedby = actionPerformedby;
}

 

public Date getSubmittedDate() {
return submittedDate;
}

 

public void setSubmittedDate(Date submittedDate) {
this.submittedDate = submittedDate;
}


}

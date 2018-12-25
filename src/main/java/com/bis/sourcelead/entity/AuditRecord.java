package com.bis.sourcelead.entity;

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

@Entity
@Table(name = "AUDIT")
public class AuditRecord {
	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "TABLE_NAME")
	private String tableName;
	@Column(name = "OLD_DATA")
	private String oldData;
	@Column(name = "NEW_DATA")
	private String newData;
	@Column(name = "ACTION_PERFORMED")
	private String ActionPerformed;
	@Column(name = "ACTION_TIME")
	private Date actionTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User userId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public String getActionPerformed() {
		return ActionPerformed;
	}

	public void setActionPerformed(String actionPerformed) {
		ActionPerformed = actionPerformed;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date updatedDate) {
		this.actionTime = updatedDate;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User updatedBy) {
		this.userId = updatedBy;
	}
}

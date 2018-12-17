
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
 * @author BIS115
 *
 */
@Entity
@Table(name = "SOURCELEAD_FORM_SECTIONS")
public class SourceleadFormSections implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TAB_ID", nullable=false )        
    private Tabs tabs;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SOURCELEAD_ACTION_ID", nullable=false )        
    private SourceleadActions sourceleadActions;

	@Column(name = "SECTION_NAME")
	private String sectionName;
	
	@Column(name = "ORDER")
	private long order;

	@Column(name = "SECTION_DESC",columnDefinition = "TEXT default NULL")
	private String sectionDesc;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name="IS_DEFAULT_VISIBLE")
	private String isDefaultVisible;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CREATED_BY", nullable=false )
	private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UPDATED_BY", nullable=false )
	private User updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tabs getTabs() {
		return tabs;
	}

	public void setTabs(Tabs tabs) {
		this.tabs = tabs;
	}

	public SourceleadActions getSourceleadActions() {
		return sourceleadActions;
	}

	public void setSourceleadActions(SourceleadActions sourceleadActions) {
		this.sourceleadActions = sourceleadActions;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public long getOrder() {
		return order;
	}
	public void setOrder(long order) {
		this.order = order;
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getIsDefaultVisible() {
		return isDefaultVisible;
	}

	public void setIsDefaultVisible(String isDefaultVisible) {
		this.isDefaultVisible = isDefaultVisible;
	}
	
	
}

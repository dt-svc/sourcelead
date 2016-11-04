package com.bis.sourcelead.entity;

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

@Entity
@Table(name = "MODULE_ACTIONS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="moduleActions")
public class ModuleActions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MODULE_ID")
	private Modules modules;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACTION_ID")
	private SourceleadActions sourceleadActions;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Modules getModules() {
		return modules;
	}

	public void setModules(Modules modules) {
		this.modules = modules;
	}

	public SourceleadActions getSourceleadActions() {
		return sourceleadActions;
	}

	public void setSourceleadActions(SourceleadActions sourceleadActions) {
		this.sourceleadActions = sourceleadActions;
	}

}

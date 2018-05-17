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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "PAYROLL_PAYHEAD")
public class PayrollPayhead {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SHORT_CODE")
	private String shortCode;
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "PAY_TYPE")
    private PayrollPaytype payType;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "FORMULA")
	private String formula;
	
	@Column(name = "FORM_16")
	private String form16;
	
	@Column(name = "TAX")
	private String tax;
	
	@Column(name = "APPLICABLE_FOR")
	private String applicableFor;
	
	@Column(name = "COMPONENT")
	private String component;
	
	@Column(name = "PAID_COMPONENT")
	private String paidComponent;
	
	@Column(name = "PART_OF_A_CTC")
	private String partOfCTC;
	
	@Column(name = "FBP_COMPONENT")
	private String fbpComponent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORGLOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "STATUS")
	private Integer status;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
    
    @Column(name = "CREATED_DATE")
  	private Date createdDate;
    
    @Column(name = "UPDATED_DATE")
   	private Date updatedDate;
    
    
    

}

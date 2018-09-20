package com.bis.sourcelead.payroll.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAY_ADJUSTMENT_PAYITEM_HISTORY")
public class PayAdjustmentPayItemHistory {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PAY_ADJUSTMENT_HISTORY_ID", nullable = false)
	private PayAdjustmentHistory payAdjustmentHistory;
	
	@Column (name="PAYITEM_ID")
	private Long payitemId;
	
	@Column (name="PRE_PAYITEM_NAME")
	private String prePayItemName;
	
	@Column (name="PRE_PAYITEM_BLOCK")
	private String prePayItemBlock;
	
	@Column (name="PRE_PAYITEM_AMOUNT")
	private BigDecimal prePayItemAmt;
	
	@Column (name="PAYITEM_NAME")
	private String payItemName;
	
	@Column (name="PAYITEM_BLOCK")
	private String payItemBlock;
	
	@Column (name="PAYITEM_AMOUNT")
	private BigDecimal payItemAmt;
	
	

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public PayAdjustmentHistory getPayAdjustmentHistory()
	{
		return payAdjustmentHistory;
	}

	public Long getPayitemId()
	{
		return payitemId;
	}

	public String getPrePayItemName()
	{
		return prePayItemName;
	}

	public String getPrePayItemBlock()
	{
		return prePayItemBlock;
	}

	public BigDecimal getPrePayItemAmt()
	{
		return prePayItemAmt;
	}

	public String getPayItemName()
	{
		return payItemName;
	}

	public String getPayItemBlock()
	{
		return payItemBlock;
	}

	public BigDecimal getPayItemAmt()
	{
		return payItemAmt;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setPayAdjustmentHistory(PayAdjustmentHistory payAdjustmentHistory)
	{
		this.payAdjustmentHistory = payAdjustmentHistory;
	}

	public void setPayitemId(Long payitemId)
	{
		this.payitemId = payitemId;
	}

	public void setPrePayItemName(String prePayItemName)
	{
		this.prePayItemName = prePayItemName;
	}

	public void setPrePayItemBlock(String prePayItemBlock)
	{
		this.prePayItemBlock = prePayItemBlock;
	}

	public void setPrePayItemAmt(BigDecimal prePayItemAmt)
	{
		this.prePayItemAmt = prePayItemAmt;
	}

	public void setPayItemName(String payItemName)
	{
		this.payItemName = payItemName;
	}

	public void setPayItemBlock(String payItemBlock)
	{
		this.payItemBlock = payItemBlock;
	}

	public void setPayItemAmt(BigDecimal payItemAmt)
	{
		this.payItemAmt = payItemAmt;
	}
	

}

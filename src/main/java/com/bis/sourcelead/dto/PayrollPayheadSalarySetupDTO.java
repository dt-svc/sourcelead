package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.bis.sourcelead.annotations.Required;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayrollPayheadSalarySetupDTO implements Serializable{
	
	 	private static final long serialVersionUID = 1L;
	 	
	 	private Long id;
	 	
	 	private Long payhead;
	 	
		private Integer formulaPercentage;
		
		private Long formulaPayhead;
	 	
	 	private String method;
		
		private String formula;
		
	    private String orgLocationId;
		
		private String uuid;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getPayhead() {
			return payhead;
		}

		public void setPayhead(Long payhead) {
			this.payhead = payhead;
		}

		public Integer getFormulaPercentage() {
			return formulaPercentage;
		}

		public void setFormulaPercentage(Integer formulaPercentage) {
			this.formulaPercentage = formulaPercentage;
		}

		public Long getFormulaPayhead() {
			return formulaPayhead;
		}

		public void setFormulaPayhead(Long formulaPayhead) {
			this.formulaPayhead = formulaPayhead;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getFormula() {
			return formula;
		}

		public void setFormula(String formula) {
			this.formula = formula;
		}

		public String getOrgLocationId() {
			return orgLocationId;
		}

		public void setOrgLocationId(String orgLocationId) {
			this.orgLocationId = orgLocationId;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
}

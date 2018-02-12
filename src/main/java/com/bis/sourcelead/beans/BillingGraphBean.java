package com.bis.sourcelead.beans;

import java.math.BigDecimal;

public class BillingGraphBean {
		private String date;
		private BigDecimal income;
		private BigDecimal expenses;
		
		 
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public BigDecimal getIncome()
		{
			return income;
		}
		public void setIncome(BigDecimal income)
		{
			this.income = income;
		}
		public BigDecimal getExpenses()
		{
			return expenses;
		}
		public void setExpenses(BigDecimal expenses)
		{
			this.expenses = expenses;
		}
}

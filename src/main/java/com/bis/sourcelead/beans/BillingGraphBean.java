package com.bis.sourcelead.beans;

public class BillingGraphBean {
		private String date;
		private double income;
		private double expenses;
		
		public void setExpenses(double expenses) {
			this.expenses = expenses;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		
		public double getIncome() {
			return income;
		}
		public void setIncome(double income) {
			this.income = income;
		}
		public double getExpenses() {
			return expenses;
		}
}

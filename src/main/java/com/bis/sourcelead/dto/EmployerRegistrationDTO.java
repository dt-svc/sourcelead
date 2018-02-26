package com.bis.sourcelead.dto;

import java.util.List;

public class EmployerRegistrationDTO {
     private String firstName;
     private String lastName;
     private String phoneNumber;
     private String email;
     private String companyName;
     private String companyWebsite;
     private String companyAddress;
     private String country;
     private String modulesList;
     
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public String getEmail() {
			return email;
		}
		public String getCompanyName() {
			return companyName;
		}
		public String getCompanyWebsite() {
			return companyWebsite;
		}
		public String getCompanyAddress() {
			return companyAddress;
		}
		public String getCountry() {
			return country;
		}
		 
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public void setCompanyWebsite(String companyWebsite) {
			this.companyWebsite = companyWebsite;
		}
		public void setCompanyAddress(String companyAddress) {
			this.companyAddress = companyAddress;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getModulesList() {
			return modulesList;
		}
		public void setModulesList(String modulesList) {
			this.modulesList = modulesList;
		}
}
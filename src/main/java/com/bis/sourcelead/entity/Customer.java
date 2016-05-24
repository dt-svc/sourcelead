package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "CUSTOMER")
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="product")
public class Customer {
	
	
	 	@Id
	    @Column(name = "id", nullable = false)
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	 
	    @Column(name = "customerName")
	    private String customerName;
	 
	    @Column(name = "customerAddress")
	    private String customerAddress;
	 
	    @Column(name = "city")
	    private String city;
	    
	    @Column(name = "zipcode")
	    private String zipcode;
	 

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getCustomerAddress() {
			return customerAddress;
		}

		public void setCustomerAddress(String customerAddress) {
			this.customerAddress = customerAddress;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}


}

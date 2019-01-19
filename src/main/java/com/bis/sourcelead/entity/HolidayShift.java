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

@Entity
@Table(name = "HOLIDAY_SHIFT")
public class HolidayShift implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOLIDAY_ID", nullable = false)
	private Holiday holiday;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "SHIFT_ID", nullable = false)
   	private Shift shift;
   		
	public Long getId() {
		return id;
	}

	public Holiday getHoliday() {
		return holiday;
	}

	public Shift getShift() {
		return shift;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	 
}

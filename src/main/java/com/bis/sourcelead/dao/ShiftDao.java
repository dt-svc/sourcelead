package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Shift;

public interface ShiftDao {
	
	public Shift saveShift(Shift shift) throws Exception;
	
	public List getShiftList(Long orgLocationId);
	
	public List<Shift> getShiftById(String shiftId);
	
}

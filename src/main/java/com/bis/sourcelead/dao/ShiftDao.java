package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Shift;

public interface ShiftDao {
	
	public Shift saveShift(Shift shift) throws Exception;
	
	public List<Shift> getShiftList(Long orgLocationId);
	
	public Shift getShiftById(String shiftId);

	public Shift deleteShift(Shift deleteShift);
	
	public Shift getShiftListByShiftNameAndOrglocationId(String ShiftName,Long orgLocationId);
	
	public List<String> getAssociatesShiftListByOrgLocationId(Long orgLocationId);
}

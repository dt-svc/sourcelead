package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ShiftDTO;
import com.bis.sourcelead.entity.Shift;

public interface ShiftService {
	
	public Shift saveShift(ShiftDTO shiftDTO) throws Exception;

	public List<Shift> getShiftList(Long orgLocationId);

	public List<Shift> getShiftById(String shiftId);
	
}

package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProposeTime;

public interface ProposeTimeService {

	ProposeTime saveProposeTime(ProposeTime proposeTime) throws Exception;
	
	ProposeTime getProposeTimeById(Long proposeTimetId) throws Exception;
	
	List<ProposeTime> getProposeTimeByInterViewId(Long interviewId) throws Exception;
}

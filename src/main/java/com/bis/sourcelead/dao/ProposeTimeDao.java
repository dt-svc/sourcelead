package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProposeTime;

public interface ProposeTimeDao {
	
	/**
	 * @author Laxman
	 * @param proposeTime
	 * @return
	 * @throws Exception
	 */
	ProposeTime saveProposeTime(ProposeTime proposeTime) throws Exception;
	
	ProposeTime getProposeTimeById(Long proposeTimetId) throws Exception;
	
	List<ProposeTime> getProposeTimeByInterViewId(Long interviewId) throws Exception;
}

package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class PerformanceGeneralTasksDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer tasksCount;
	
	private Integer openTasksCount;
	
	private Integer inprogressCount;
	
	private Integer completedCount;
	
	private Integer overdueCount;

	public Integer getTasksCount() {
		return tasksCount;
	}

	public void setTasksCount(Integer tasksCount) {
		this.tasksCount = tasksCount;
	}

	public Integer getInprogressCount() {
		return inprogressCount;
	}

	public void setInprogressCount(Integer inprogressCount) {
		this.inprogressCount = inprogressCount;
	}

	public Integer getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(Integer completedCount) {
		this.completedCount = completedCount;
	}

	public Integer getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(Integer overdueCount) {
		this.overdueCount = overdueCount;
	}

	public Integer getOpenTasksCount() {
		return openTasksCount;
	}

	public void setOpenTasksCount(Integer openTasksCount) {
		this.openTasksCount = openTasksCount;
	}
	

}

package com.bis.sourcelead.beans;

public class TasksReportDTO {
	
	private int newCount;
	
	private int inProgressCount;
	
	private int onHoldCount;
	
	private int doneCount;
	
	private TaskPriorityReportDTO newCounts;
	
	private TaskPriorityReportDTO inprogressCounts;
	
	private TaskPriorityReportDTO onHoldCounts;
	
	private TaskPriorityReportDTO doneCounts;

	public int getNewCount() {
		return newCount;
	}

	public int getInProgressCount() {
		return inProgressCount;
	}

	public int getOnHoldCount() {
		return onHoldCount;
	}

	public int getDoneCount() {
		return doneCount;
	}

	public TaskPriorityReportDTO getNewCounts() {
		return newCounts;
	}

	public TaskPriorityReportDTO getInprogressCounts() {
		return inprogressCounts;
	}

	public TaskPriorityReportDTO getOnHoldCounts() {
		return onHoldCounts;
	}

	public TaskPriorityReportDTO getDoneCounts() {
		return doneCounts;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	public void setInProgressCount(int inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public void setOnHoldCount(int onHoldCount) {
		this.onHoldCount = onHoldCount;
	}

	public void setDoneCount(int doneCount) {
		this.doneCount = doneCount;
	}

	public void setNewCounts(TaskPriorityReportDTO newCounts) {
		this.newCounts = newCounts;
	}

	public void setInprogressCounts(TaskPriorityReportDTO inprogressCounts) {
		this.inprogressCounts = inprogressCounts;
	}

	public void setOnHoldCounts(TaskPriorityReportDTO onHoldCounts) {
		this.onHoldCounts = onHoldCounts;
	}

	public void setDoneCounts(TaskPriorityReportDTO doneCounts) {
		this.doneCounts = doneCounts;
	}

}
package com.bis.sourcelead.beans;

public class TasksGraphBean {
		
		private Integer New;
		
		private Integer InProgress;
		
		private Integer OnHold;
		
		private Integer Done;


		public Integer getInProgress() {
			return InProgress;
		}

		public Integer getOnHold() {
			return OnHold;
		}

		public Integer getDone() {
			return Done;
		}


		public void setNew(Integer new1) {
			New = new1;
		}

		public void setInProgress(Integer inProgress) {
			InProgress = inProgress;
		}

		public void setOnHold(Integer onHold) {
			OnHold = onHold;
		}

		public void setDone(Integer done) {
			Done = done;
		}
		
}

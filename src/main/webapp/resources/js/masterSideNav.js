var masterSideNav = new buildMasterSideNavPanel();
function buildMasterSideNavPanel(){
	 this.masterSideNavPanel = function(){
	     var sideNavHtml='';
	      sideNavHtml = sideNavHtml+'<nav class="newMenu">';
	      sideNavHtml = sideNavHtml+'<ul>';
	       
	       if(tabAccessArray.indexOf("Dashboard") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class="navActive"><a href="/app/dashboard/3" title="Dashboard"><span class="navIcons"></span><b>Dashboard</b></a></li>';
	        }
	       
	       if(tabAccessArray.indexOf("Associates") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/usersList" title="Associates"><span class="navIcons assIcon"></span><b>Associates</b></a></li>';
	       }
	        
	       if(tabAccessArray.indexOf("Clients") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/clientDashboard" title="Clients"><span class="navIcons cliIcon"></span><b>Clients</b></a></li>';
	       }
	        
	       if(tabAccessArray.indexOf("Jobs") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/jobs" title="Jobs"><span class="navIcons jobIcon"></span><b>Jobs</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Vendors") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/vendorsList" title="Vendors"><span class="navIcons venIcon"></span><b>Vendors</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Candidates") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/candidatesList" title="Candidates"><span class="navIcons canIcon"></span><b>Candidates</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("On & Off Boarding") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/candidateOnBoardingList" title="On / Off Boarding"><span class="navIcons onoffIcon"></span><b>On / Off Board</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Projects") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/projectDashboard" title="Projects"><span class="navIcons projIcon"></span><b>Projects</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("My Timesheets") > -1 && tabAccessArray.indexOf("Timesheet Approval") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="timesheetLi" title="Timesheets"><span class="navIcons timIcon"></span><b>Timesheets</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	    	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/timesheet" title="My TimeSheets"><i class="fa fa-chevron-right"></i> Self</a></li>';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/timesheetApproval" title="TimeSheet Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>';
	    	   sideNavHtml = sideNavHtml+'</ul>';
	    	   sideNavHtml = sideNavHtml+'</li>';
	       }
	       
	       if(tabAccessArray.indexOf("My Timesheets") > -1 && tabAccessArray.indexOf("Timesheet Approval") == -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/timesheet" title="Timesheets"><i class="navIcons timIcon"></i>Timesheets</a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("My Timesheets") == -1 && tabAccessArray.indexOf("Timesheet Approval") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/timesheetApproval" title="Timesheets"><i class="navIcons timIcon"></i>Timesheets</a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("My Expenses") > -1 && tabAccessArray.indexOf("Expenses Approval") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="expencesLi" title="Expenses"> <span class="navIcons expIcon"></span><b>Expenses</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	    	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/expenses" title="My Expenses"><i class="fa fa-chevron-right"></i> Self</a></li>';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/expensesApprovalList" title="Expenses Approval"><i class="fa fa-chevron-right"></i> Employees</a></li>';
	    	   sideNavHtml = sideNavHtml+'</ul>';
	    	   sideNavHtml = sideNavHtml+'</li>';
	       }
	       
	       if(tabAccessArray.indexOf("My Expenses") > -1 && tabAccessArray.indexOf("Expenses Approval") == -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/expenses" title="My Expenses"><i class="navIcons expIcon"></i>Expenses</a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("My Expenses") == -1 && tabAccessArray.indexOf("Expenses Approval") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/expensesApprovalList" title="Expenses Approval"><i class="navIcons expIcon"></i>Expenses Approval</a></li>'; 
	       }
	       
	       if(tabAccessArray.indexOf("Asset Management") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/assetManagement" title="Asset Management"><span class="navIcons asstIcon"></span><b>Asset Management</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Contracts") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/contractListbyStatus" title="Contracts"><span class="navIcons contIcon"></span><b>Contracts</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Receivable") > -1 && tabAccessArray.indexOf("Payables") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="billingLi" title="Finance"> <span class="navIcons finIcon"></span><b>Finance</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	    	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/invoiceList" title="Invoices"><i class="fa fa-chevron-right"></i> Invoices</a></li>';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/payablesList" title="Bills"><i class="fa fa-chevron-right"></i> Bills</a></li>';
	    	   sideNavHtml = sideNavHtml+'</ul>';
	    	   sideNavHtml = sideNavHtml+'</li>';
	       }
	       
	       if(tabAccessArray.indexOf("Receivable") > -1 && tabAccessArray.indexOf("Payables") == -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/invoiceList" title="Invoices"><span class="navIcons invoIcon"></span><b>Invoices</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Receivable") == -1 && tabAccessArray.indexOf("Payables") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/payablesList" title="Bills"><span class="navIcons invoIcon"></span><b>Bills</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Campaigns") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/campaigns/hotcampaigns" title="Campaigns"><span class="navIcons campIcon"></span><b>Campaigns</b></a></li>'; 
	       }
	       
	       if(tabAccessArray.indexOf("Leave Management") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/leaveManagementReports" title="Leave Management"><span class="navIcons leaveIcon"></span><b>Leave Management</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Run Payroll") > -1 && tabAccessArray.indexOf("My Payroll") > -1){
	    	   if(country == 'India'){
	    		   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="payrollli" title="Pay Roll"> <span class="navIcons payIcon"></span><b>Pay Roll</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	        	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	        	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/myPayrollInd" title="My Pays"><i class="fa fa-chevron-right"></i>My Payroll</a></li>';
	        	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/runIndianPayRoll" title="Payroll"><i class="fa fa-chevron-right"></i>Run Payroll</a></li>';
	        	   sideNavHtml = sideNavHtml+'</ul>';
	        	   sideNavHtml = sideNavHtml+'</li>';
	    	   }
	    	   
	    	   if(country == 'United States'){
	    		   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="payrollli" title="Pay Roll"> <span class="navIcons payIcon"></span><b>Pay Roll</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	        	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	        	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/payrollList" title="Run Payroll"><i class="fa fa-chevron-right"></i>Run Payroll</a></li>';
	        	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/userPay" title="Legacy Payroll"><i class="fa fa-chevron-right"></i>Legacy Payroll</a></li>';
	        	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/myPayroll" title="My Payroll"><i class="fa fa-chevron-right"></i>My Payroll</a></li>';
	        	   sideNavHtml = sideNavHtml+'</ul>';
	        	   sideNavHtml = sideNavHtml+'</li>';
	    	   }
	    	  
	       }
	       
	       if(tabAccessArray.indexOf("Run Payroll") > -1 && tabAccessArray.indexOf("My Payroll") == -1){
	    	   if(country == 'India'){
	    		   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/runIndianPayRoll" title="Run Payroll"><i class="navIcons payIcon"></i>Run Payroll</a></li>';
	    	   }
	    	   
	    	   if(country == 'United States'){
	    		   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/payrollList" title="Run Payroll"><i class="navIcons payIcon"></i>Run Payroll</a></li>';
	    	   }
	       }
	       
	       if(tabAccessArray.indexOf("Run Payroll") == -1 && tabAccessArray.indexOf("My Payroll") > -1){
	    	   if(country == 'India'){
	    		   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/myPayrollInd" title="My Payroll"><i class="navIcons payIcon"></i>My Payroll</a></li>';
	    	   }
	    	   
	    	   if(country == 'United States'){
	    		   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="payrollli" title="Pay Roll"> <span class="navIcons payIcon"></span><b>Pay Roll</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	        	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	         	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/userPay" title="Legacy Payroll"><i class="fa fa-chevron-right"></i>Legacy Payroll</a></li>';
	        	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/myPayroll" title="My Payroll"><i class="fa fa-chevron-right"></i>My Payroll</a></li>';
	        	   sideNavHtml = sideNavHtml+'</ul>';
	        	   sideNavHtml = sideNavHtml+'</li>';
	    	   }
	       }
	       
	       if(tabAccessArray.indexOf("Immigrations") > -1 && tabAccessArray.indexOf("My Immigration") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="expencesLi" title="Pay Roll"> <span class="navIcons immIcon"></span><b>Immigration</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	    	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	     	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/immigration/myImmigration" title="Self"><i class="fa fa-chevron-right"></i>Self</a></li>';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/immigration/viewImmigrationList" title="Employees"><i class="fa fa-chevron-right"></i>Employees</a></li>';
	    	   sideNavHtml = sideNavHtml+'</ul>';
	    	   sideNavHtml = sideNavHtml+'</li>';
	       }
	       
	       if(tabAccessArray.indexOf("Immigrations") > -1 && tabAccessArray.indexOf("My Immigration") == -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/immigration/viewImmigrationList" title="Immigrations"><i class="navIcons immIcon"></i>Immigrations</a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Immigrations") == -1 && tabAccessArray.indexOf("My Immigration") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/immigration/myImmigration" title="My Immigration"><i class="navIcons immIcon"></i>My Immigration</a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Support") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/sourceleadTicketList" title="Support"><span class="navIcons supIcon"></span><b>Support</b></a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Blogs") > -1 && tabAccessArray.indexOf("User Manuals") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""> <a href="#" class="resourcesli" title="Resources"> <span class="navIcons resIcon"></span><b>Resources</b> <i class="fa fa-fw fa-caret-down"></i> </a>';
	    	   sideNavHtml = sideNavHtml+'<ul class="collapses" style="display: none">';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/blogs" title="Blogs"><i class="fa fa-chevron-right"></i> Blogs</a></li>';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/userManuals" title="User Manuals"><i class="fa fa-chevron-right"></i> User Manuals</a></li>';
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/releaseNote" title="Realease Note"><i class="fa fa-chevron-right"></i> Realease Note</a></li>';
	    	   sideNavHtml = sideNavHtml+'</ul>';
	    	   sideNavHtml = sideNavHtml+'</li>';
	       }
	       
	       if(tabAccessArray.indexOf("Blogs") > -1 && tabAccessArray.indexOf("User Manuals") == -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/blogs" title="Blogs"><i class="navIcons resIcon"></i>Blogs</a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("Blogs") == -1 && tabAccessArray.indexOf("User Manuals") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/userManuals" title="User Manuals"><i class="navIcons resIcon"></i>User Manuals</a></li>';
	       }
	       
	       if(tabAccessArray.indexOf("ROLE_SOURCELEAD_ADMIN") > -1){
	    	   sideNavHtml = sideNavHtml+'<li class=""><a href="/app/stateTax/list" title="Taxes"><span class="navIcons finIcon"></span><b>Taxes</b></a></li>';
	       } 
	       
	        sideNavHtml = sideNavHtml+'</ul>';
	        sideNavHtml = sideNavHtml+'</nav>';
	        $("#masterSideNav").append(sideNavHtml);
	        sideNavigtionListener();
	        sideNewMenuActionListener();
	}
} 
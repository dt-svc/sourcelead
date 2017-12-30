<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Report(s) Dashboard</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Reports Dashboard</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content mt mb col-md-12">
          <div class="leaveConfigure clerfix">
             <div class="col-md-4 mb">
                 
                 <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default" type="button" id="menu1" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
				<li><a href='${appPath}/recruitersReports'>Recruiters Report</a></li>
				<li><a href="${appPath}/vendorsReports">Vendors Report</a></li>
				<li><a href="${appPath}/clientReports">Client Report</a></li>
				<li><a href="#">New Customer</a></li>
				<li><a href="${appPath}/applicantDeclineReports">Applicant Decline</a></li>
				<li><a href="${appPath}/applicantsBackoutReport">Applicant Backout</a></li>
				<li><a href="${appPath}/jobPipelineReports">Jobs Pipeline</a></li>
				<li><a href="${appPath}/applicantBySource">Applicant Source</a></li>
				<li><a href="${appPath}/timeToFill">Time to Fill</a></li>
				<li><a href="#">Retention Rate</a></li>
				<li><a href="#">Applicant Received</a></li>
				<li><a href="#">Sales Performance</a></li>
					<li><a href="${appPath}/timeSheetReports">Timesheet Report</a></li>
				</ul>
			</div>
                 <!-- Dropdown -->
                  <div class="clearfix text-center mb10">
                      <i class="fa fa-dashboard fa-4x text-info"></i>
                  </div>
                  <div class="clearfix text-center">
                      <h4 class="text-info"><a href="#">Performance Report</a></h4>
                      <!-- <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10 hidden">
                          <a href="pr-Reportsdashboard.html" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
              </div>
              <div class="col-md-4 mb">
                 <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default" type="button" id="menu2" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu2">
				<li><a href="#">Invoice</a></li>
				<li><a href="#">Billing</a></li>
				<li><a href="#">Invoice Report By Customer / Project</a></li>
				<li><a href="#">Bill Report By Customer / Project</a></li>
				<li><a href="${appPath}/expensesReportsList">Expenses Report</a></li>
				<li><a href='${appPath}/arAgingReport'>A/R Aging</a></li>
				<li><a href='${appPath}/apAgingReport'>A/P Aging</a></li>
				<li><a href="#">Profit &amp; Loss</a></li>
				<li><a href="#">Balance Sheet</a></li>
				<li><a href="#">Client Balance</a></li>
				<li><a href="#">Unpaid Bills</a></li>
				<li><a href="#">Paid Bills</a></li>
				<li><a href="#">Bad debt</a></li>
				<li><a href="#">Cashflow projection</a></li>
				<li><a href="#">Cashflow Requirement</a></li>
				<li><a href='${appPath}/missingInvoiceReport'>Missing Invoice</a></li>
				<li><a href='${appPath}/missingBillReport'>Missing Bill</a></li>
				<li><a href="javascript:void(0)">Associate Billed vs Paid Report</a></li>
				
				</ul>
			</div>
                 <!-- Dropdown -->
                 
                  <div class="clearfix text-center mb10">
                      <i class="fa fa-money fa-4x text-muted"></i>
                  </div>
                  <div class="clearfix text-center">
                      <h4 class="text-muted"><a href="#">Financial Report</a></h4>
                      <!-- <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10 hidden">
                          <a href="financial-Reportsdashboard.html" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
               <div class="col-md-4 mb">
                 
                 <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default" type="button" id="menu3" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
				<li><a href="#">Employment Verficication</a></li>
				<li><a href="#">Wage and Tax</a></li>
				<li><a href="${appPath}/timeSheetReports">Timesheet Report</a></li>
				<li><a href="#">Associate Onboard</a></li>
				<li><a href="#">Associate Offboard</a></li>
				<li><a href="#">Expenses</a></li>
				</ul>
			</div>
                 <!-- Dropdown -->
                 
                  <div class="clearfix text-center mb10">
                      <i class="fa fa-pie-chart fa-4x text-info"></i>
                  </div>
                  <div class="clearfix text-center">
					  <h4 class="text-primary"><a href="#">HR Report</a></h4>
                      <!-- <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10 hidden">
                          <a href="billing-report.html" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                  </div>
              
                  <div class="col-md-4 mb">
                  <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default" type="button" id="menu4" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
				<li><a href="#">Bank Report</a></li>
					<li><a href="#">Performance</a></li>
					<li><a href="#">Payrol</a></li>
					<li><a href="#">Associate ROI</a></li>
					<li><a href="#">All Associate ROI</a></li>
					<li><a href="#">360 View</a></li>
					<li><a href="#">Pay History</a></li>
					<li><a href="#">Bill Histroy</a></li>
				</ul>
			</div>
                 <!-- Dropdown -->
                  
                  
                  
                  <div class="clearfix text-center mb10">
                      <i class="fa fa-users fa-4x text-danger"></i>
                  </div>
                  <div class="clearfix text-center">
					  <h4 class="text-danger"><a href="#">Associate Report</a></h4>
                      <!-- <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10 hidden">
                          <a href="expenses-report.html" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                      </div>
              <div class="col-md-4 mb">
                 <!-- DropDown -->
			<div class="dropdown pull-right report-dropdown">
				<span class="dropdown-toggle btn btn-sm btn-default" type="button" id="menu5" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
				<ul class="dropdown-menu" role="menu" aria-labelledby="menu3">
				<li><a href="#">BBR Report</a></li>
				</ul>
			</div>
                 <!-- Dropdown -->
                 
                 
                  <div class="clearfix text-center mb10">
                      <i class="fa fa-university fa-4x text-warning"></i>
                  </div>
                  <div class="clearfix text-center">
					  <h4 class="text-warning"><a href="#">Banking Report</a></h4>
                      <!-- <small class="text-muted">Loremipsum Loremipsum</small> -->
                      <div class="clearfix mt10 hidden"><a href="timesheet-report.html" class="btn btn-primary btn-sm" title="View">View</a>
                      </div>
                  </div>
                      </div>
                      
	<div class="col-md-4 mb">
		<!-- DropDown -->
		<div class="dropdown pull-right report-dropdown">
		<span class="dropdown-toggle btn btn-sm btn-default" type="button" id="menu6" data-toggle="dropdown">
			<span class="caret cursorPointer"></span>
		</span>
			<ul class="dropdown-menu">
			<li><a href="#">Project Summary</a></li>
			<li><a href="#">Task Performance</a></li>
			<li><a href="#">Task Overdue</a></li>
			<li><a href="#">Top Performers</a></li>
			<li><a href="#">Budget Vs Acutal Time</a></li>
			<li><a href="#">Budget Vs Acutal Funds</a></li>
			<li><a href="#">Overtime</a></li>
			<li><a href="#">Resource Request</a></li>
			<li><a href="#">ROI</a></li>
			<li><a href="#">Year to Date</a></li>
			</ul>
		</div>
		<!-- Dropdown -->
		<div class="clearfix text-center mb10">
			<i class="fa fa-bar-chart fa-4x text-success"></i>
		</div>
		<div class="clearfix text-center">
			<h4 class="text-success"><a href="#">Projects Report</a></h4>
			<!-- <small class="text-muted">Loremipsum Loremipsum</small> -->
			<div class="clearfix mt10 hidden"><a href="jobs-report.html" class="btn btn-primary btn-sm" title="View">View</a>
			</div>
		</div>
	</div>
                    

<div class="col-md-4 mb">
		<!-- DropDown -->
		<div class="dropdown pull-right report-dropdown">
		<span class="dropdown-toggle btn btn-sm btn-default" type="button" id="menu7" data-toggle="dropdown">
			<span class="caret cursorPointer"></span>
		</span>
			<ul class="dropdown-menu">
			<li><a href="#">Company Snapshot</a></li>
			</ul>
		</div>
		<!-- Dropdown -->
		<div class="clearfix text-center mb10">
			<i class="fa fa-building-o fa-4x text-primary"></i>
		</div>
		<div class="clearfix text-center">
			<h4 class="text-success"><a href="#">Company Snapshot</a></h4>
			<!-- <small class="text-muted">Loremipsum Loremipsum</small> -->
			<div class="clearfix mt10 hidden"><a href="#" class="btn btn-primary btn-sm" title="View">View</a>
			</div>
		</div>
	</div>                    
                                                            
      </div>
        </div>
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />
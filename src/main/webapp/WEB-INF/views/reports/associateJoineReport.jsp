<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    	<div class="pull-left">
				<h2>Associate Joined Report</h2>
		</div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/associateReportsList'/>">Associate Reports</a> &raquo; Applicants Joined Report  </div>
    </section>
       
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
	      <div class="positionRelative reportHead clearfix">
	      
	      	<div class="select-fild col-md-2 mt10">
                    <select id="araginglistSel">
                        <option value="associateJoined" >Associate Joined Reports</option>
                        <option value="associateTerminated" sel>Associate Terminated Report</option>
                    </select>
                </div>
<!-- 	      <span class="rAct" title="Graphical View" id="GView"><i class="fa fa-pie-chart"></i></span>
 -->	      
 <div class="listGridWrap reqListBtn">
        	<button type="button" title="List View" id="ListView" value="list" class="btn btn-primary"><i class="fa fa-list"></i></button>
			<button type="button" title="Reports View" id="GraphView" value="grid" class="btn btn-default" onclick=""><i class="fa fa-pie-chart"></i></button>
		</div>
 
 <span class="pull-left refreIcon" id="advanceSearchDiv">
<!-- 	      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetApplicantFilter();"></a> -->
	            		<a href="javascript:void(0)" onclick="columPop('joined');" title="Column Chooser" class="columIcon" style="display: block;" id="joinedCustomFields"></a>
	            		<a href="javascript:void(0)" onclick="columPop('terminated');" title="Column Chooser" class="columIcon" style="display: none;" id="terminatedCustomFields"></a>
	            		
	            		 <div class="colPop" id="joinedColumnPopup">
	<div class="colPopMid coldropdown-parent" style="display: block;" id="joinedCustomFieldscolPopMid">
	<div class="clearfix">
		<div class="row">
				<div class="col-md-10 mb10 mt5 col-md-offset-2">
<!-- 				<span class="checkbox-new  pull-left mr">
					<input id="columnsPickerUnCheckAll" class="checkbox-custom toggle-vis" name="columnsPicker" onclick="checkAllColumns(this);"  type="checkbox">
					<label for="columnsPickerUnCheckAll" class="checkbox-custom-label">Uncheck All</label>
				</span> -->
				<span class="checkbox-new pull-left">
					<input id="joinedColumnsPickerCheckAll" class="checkbox-custom toggle-vis" name="columnsPicker" onclick="checkAllJoinedColumns(this);"  type="checkbox">
					<label for="joinedColumnsPickerCheckAll" class="checkbox-custom-label" >Check All</label>
				</span>
			</div>
		</div>
	<div class="clearfix"></div>
		<div class="coldropdown-inner">
		<ul id="joinedCustomColumns">
			<li>
				<span class="checkbox-new clearfix">
					<input id="a" class="checkbox-custom toggle-vis" name="paidComponent" data-column="0" type="checkbox" checked="" data-name="joinedempId">
					<label for="a" class="checkbox-custom-label" >Associate ID</label>
				</span>
			</li>
			
						<li>
				<span class="checkbox-new clearfix">
					<input id="b" class="checkbox-custom toggle-vis" name="paidComponent" data-column="1" type="checkbox" checked="" data-name="joinedEmpName">
					<label for="b" class="checkbox-custom-label" >Associate Name</label>
				</span>
			</li>
			
									<li>
				<span class="checkbox-new clearfix">
					<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" data-name="joinedEmpGender">
					<label for="c" class="checkbox-custom-label" >Gender</label>
				</span>
			</li>
			
												<li>
				<span class="checkbox-new clearfix">
					<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="" data-name="joinedEmpType">
					<label for="d" class="checkbox-custom-label" >Emp Type</label>
				</span>
			</li>
			
															<li>
				<span class="checkbox-new clearfix">
					<input id="e" class="checkbox-custom toggle-vis" name="paidComponent" data-column="4" type="checkbox"  data-name="joinedDeptName">
					<label for="e" class="checkbox-custom-label" >Department</label>
				</span>
			</li>
			
																		<li>
				<span class="checkbox-new clearfix">
					<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox"  data-name="joinedUserRole">
					<label for="f" class="checkbox-custom-label" >Role</label>
				</span>
			</li>
			
																					<li>
				<span class="checkbox-new clearfix">
					<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox" data-name="joinedRate">
					<label for="g" class="checkbox-custom-label" >Rate</label>
				</span>
			</li>
			
																								<li>
				<span class="checkbox-new clearfix">
					<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox" checked="" data-name="joinedFromDate">
					<label for="h" class="checkbox-custom-label" >Date Of Join</label>
				</span>
			</li>
			
						
																								<li>
				<span class="checkbox-new clearfix">
					<input id="i" class="checkbox-custom toggle-vis" name="paidComponent" data-column="8" type="checkbox" checked="" data-name="joinedReportingManager">
					<label for="i" class="checkbox-custom-label">Manager</label>
										</span>
			</li>
									
																								<li>
				<span class="checkbox-new clearfix">
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="9" type="checkbox" checked="" data-name="joinedShift">
					<label for="j" class="checkbox-custom-label">Shift</label>
										</span>
			</li>
																											<li>
				<span class="checkbox-new clearfix">
					<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" data-column="10" type="checkbox" checked="" data-name="probationStatus">
					<label for="k" class="checkbox-custom-label">Probation Status</label>
										</span>
			</li>
		</ul>
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="joinedColumHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
	</div>
</div>			
			</div> 
			<div class="colPop" id="terminatedColumnPopup">
			
	<div class="colPopMid coldropdown-parent" style="display: none;" id="terminatedCustomFieldscolPopMid">
	<div class="clearfix">
		<div class="row">
				<div class="col-md-10 mb10 mt5 col-md-offset-2">
<!-- 				<span class="checkbox-new  pull-left mr">
					<input id="columnsPickerUnCheckAll" class="checkbox-custom toggle-vis" name="columnsPicker" onclick="checkAllColumns(this);"  type="checkbox">
					<label for="columnsPickerUnCheckAll" class="checkbox-custom-label">Uncheck All</label>
				</span> -->
				<span class="checkbox-new pull-left">
					<input id="columnsPickerCheckAll" class="checkbox-custom toggle-vis" name="columnsPicker" onclick="checkAllColumns(this);"  type="checkbox">
					<label for="columnsPickerCheckAll" class="checkbox-custom-label" >Check All</label>
				</span>
			</div>
		</div>
	<div class="clearfix"></div>
		<div class="coldropdown-inner">
		<ul id="terminatedCustomColumns">
			<li>
				<span class="checkbox-new clearfix">
					<input id="k" class="checkbox-custom toggle-vis" name="paidComponent" data-column="0" type="checkbox" checked="" data-name="terminatedempId">
					<label for="k" class="checkbox-custom-label" >Associate ID</label>
				</span>
			</li>
			
						<li>
				<span class="checkbox-new clearfix">
					<input id="l" class="checkbox-custom toggle-vis" name="paidComponent" data-column="1" type="checkbox" checked="" data-name="empName">
					<label for="l" class="checkbox-custom-label" >Employee Name</label>
				</span>
			</li>
															<li>
				<span class="checkbox-new clearfix">
					<input id="m" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" checked="" data-name="empType">
					<label for="m" class="checkbox-custom-label" >Emp Type</label>
				</span>
			</li>
			
						
																								<li>
				<span class="checkbox-new clearfix">
					<input id="n" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="" data-name="fromDate">
					<label for="n" class="checkbox-custom-label" >Date Of Join</label>
				</span>
			</li>
			
																											<li>
				<span class="checkbox-new clearfix">
					<input id="o" class="checkbox-custom toggle-vis" name="paidComponent" data-column="4" type="checkbox" checked="" data-name="reportingManager">
					<label for="o" class="checkbox-custom-label">Manager</label>
										</span>
			</li>
			
			
												<li>
				<span class="checkbox-new clearfix">
					<input id="p" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox" checked="" data-name="terminatedDate">
					<label for="p" class="checkbox-custom-label">Terminated On</label>
										</span>
			</li>
									<li>
				<span class="checkbox-new clearfix">
					<input id="q" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox" checked="" data-name="terminatedReason">
					<label for="q" class="checkbox-custom-label">Reason</label>
										</span>
			</li>
			
																					<li>
				<span class="checkbox-new clearfix">
					<input id="r" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox"  data-name="userRole">
					<label for="r" class="checkbox-custom-label" >Role</label>
				</span>
			</li>
			
			
			
									<li>
				<span class="checkbox-new clearfix">
					<input id="s" class="checkbox-custom toggle-vis" name="paidComponent" data-column="8" type="checkbox" data-name="gender">
					<label for="s" class="checkbox-custom-label" >Gender</label>
				</span>
			</li>
			
			
						<li>
				<span class="checkbox-new clearfix">
					<input id="t" class="checkbox-custom toggle-vis" name="paidComponent" data-column="9" type="checkbox" checked="" data-name="shift">
					<label for="t" class="checkbox-custom-label">Shift</label>
										</span>
			</li>
			
															<li>
				<span class="checkbox-new clearfix">
					<input id="u" class="checkbox-custom toggle-vis" name="paidComponent" data-column="10" type="checkbox"  data-name="deptName">
					<label for="u" class="checkbox-custom-label" >Department</label>
				</span>
			</li>
																					<li>
				<span class="checkbox-new clearfix">
					<input id="v" class="checkbox-custom toggle-vis" name="paidComponent" data-column="11" type="checkbox" data-name="rate">
					<label for="v" class="checkbox-custom-label" >Rate</label>
				</span>
			</li>

				<span class="checkbox-new clearfix">
					<input id="w" class="checkbox-custom toggle-vis" name="paidComponent" data-column="12" type="checkbox" checked="" data-name="probationStatus">
					<label for="w" class="checkbox-custom-label">Probation Status</label>
										</span>
			</li>
			
			</ul>
		</div>
	</div>
	<div class="colPopFot">
		<a href="javascript:void(0)" onclick="columHide();">Apply</a>
		<a href="javascript:void(0)" onclick="columClose();">Cancel</a>
	</div>
</div>	
			</div>
	      </span>
	      <ul class="pull-right list-unstyled list-inline">
	      	  <li>
	      	  <div class="dropdown-select positionRelative repSelWrap pull-left ml">
          <select class="form-control repSelFiled" id="reportTypeId" onChange="showCustomFields(this); flushCustom()">
			  <!-- <option value="today">Today</option>
			  <option value="yesterday">Yesterday</option> -->
			  <option selected="selected" value="today">Today</option>
			  <option value="Last_7_days">Last 7 days</option>
			  <option value="Last_15_days">Last 15 days</option>
			  <option value="1_month">Last 1 month</option>
			  <option value="3_months">Last 3 months</option>
			  <option value='YTD' >YTD</option>
	          <option value='Last_Year'>Last Year</option>
	          <option value="custom">Custom</option> 
			</select>
		<div id="runButtonDiv" class="repSelBtn">
			<button type="button" class="btn btn-primary btn-sm" id="customDateApplicant" onclick="getAssociatesJoinedList();" style="display: block">Run</button>
	        <button type="button" class="btn btn-primary btn-sm" id="customDateRecurter" onclick="filter(); getAssociatesTerminatedList();" style="display: none">Run</button>
        </div> 
          
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="ReportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date *">
				  <p id="reportsStartDate"></p>
              </div>
              
              <div class="repCustomDateInn">
               <input type="text"  class="form-control calendar" value="" id="ReportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date *">
				 <p id="reportsEndDate"></p>
              </div>
              
              <div class="repCustomDateBtn"><button type="button" id="recurter" class="btn btn-primary btn-sm" onclick="getAssociatesJoinedList();" style="display: block"><i class="fa fa-search"></i></button></div>
                       <div class="repCustomDateBtn"><button type="button" id="applicant" class="btn btn-primary btn-sm" onclick="getAssociatesJoinedList();" style="display: none"><i class="fa fa-search"></i></button></div>
          </div>
          
        </div>
		          <select class="form-control repSelFiled" name="year" id="year"  style="display: none;" onchange="getAssociatesJoinedGraph();">
		            <option value=''>--Select Year--</option>
		            <c:forEach var="option" items="${yearList}">
						<option value="${option.year}">${option.year}</option>
					</c:forEach>
		          </select>          
<!-- 				<div class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="getAssociatesJoinedList();" style="display: none;">Run</button></div>  -->
	      	  </li>
					
					<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="exportReportOptions();"></a>
						<div class="newActPop" style="display: none;">
							<span>
			           	   		<a href="javascript:void(0)" id="associateTeminateExportPdf" onclick="exportAssociateTerminatedSheet('Pdf');" title="PDF" style="display: none">Export PDF</a>
								<a href="javascript:void(0)" id="associateTeminateExportExcel" onclick="exportAssociateTerminatedSheet('Excel');" title="Excel" style="display: none">Export Excel</a>  
								
								<a href="javascript:void(0)" id="associateJoinExportPdf" onclick="exportAssociateJoinedSheet('Pdf');" title="PDF" style="display: block">Export PDF</a>
								<a href="javascript:void(0)" id="associateJoinExportExcel" onclick="exportAssociateJoinedSheet('Excel');" title="Excel" style="display: block">Export Excel</a>           
							</span>
						</div>
					</li> 
					
			  <li class="" id="associateTeminateSearchId" style="display: none" ><input type="text" class="repSearchBar" placeholder="Search" id="associateTerminatedListDataTableSearch" ><span class="repSearchIcon" onclick="getAssociatesTerminatedList();"></span></li>
	     	 <li class="" id="associateJoinSearchId" style="display: block;"><input type="text" class="repSearchBar" placeholder="Search" id="associateJoinedListDataTableSearch"><span class="repSearchIcon" onclick="getAssociatesJoinedList();"></span></li>
	        </ul>
	      </div>
        <div class="timesheet_head">
      
       </div>
       <div id="emptyPopup"></div>
        <div class="clients_wrap_table datatable_filter">
       
         <div id="emptyJoinedPopup"></div>
          
           <div id="emptyTerminatedPopup"></div>
         
        <div id="applicantJoinedDiv" style="display: block"></div>
	           <div id="associateTerminatedDiv" style="display: none"></div>
				<input type="hidden" id="noApplicantJoinedList" name="noApplicantJoinedList"  value="0"/>
			    <input type="hidden" id="noRecruiterApplicantJoinedList" name="noRecruiterApplicantJoinedList"  value="0"/>        
        </div>
        
        
        <div class="GraView" style="display: none;">
      <div class="clearfix row mt">
        <div class="col-md-12">
        <div class="projectDashboad clearfix positionRelative">
			<div id="associateJoineereport" style="width: 100%; height: 375px;" class="amchartAnchor-Hide"></div>
	    <div class="clearfix">
			<hr>
			<div class="clearfix row">
			<div class="col-md-3">
				<div class="form-group">
					<label class="textBold width90px">Joined:</label>
					<span class="textBold text-green" id="ttJoinedCount"></span>
				</div>							
				<div class="clearfix">
					<div class="clearfix row">
						<label class="col-md-5 textBold text-primary">Full Time:</label>
						<div class="col-md-7">
						   <span id="fullTimeCount"></span>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="fullTimebg clearfix mb10">
						<div class="pull-left joinedTime" id="fullTimeMaleProgress"></div>
						<div class="pull-left joinedValue" id="fullTimeFeMaleProgress"></div>
					</div>
					<div class="col-md-6 text-center">
						<img src="/resources/images/male.svg" style=" width: 50px; height: 50px;" alt="womanimage">
						<div class="clearfix mt5"></div>
						<span class="textBold clearfix" id="ftMale"></span>
					</div>
					<div class="col-md-6 text-center">
						<img src="/resources/images/woman.svg" style=" width: 50px; height: 50px;" alt="womanimage">
						<div class="clearfix mt5"></div>
						<span class="textBold clearfix" id="ftFeMale"></span>
					</div>
				</div>
				
				<div class="clearfix">
					<hr>
					<div class="clearfix row">
						<label class="col-md-5 textBold text-danger">Part Time:</label>
						<div class="col-md-7">
						   <span id="partTimeCount"></span>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="fullTimebg clearfix mb10">
						<div class="pull-left joinedTime" id="partTimeMaleProgress"></div>
						<div class="pull-left joinedValue" id="partTimeFeMaleProgress"></div>
					</div>
					<div class="col-md-6 text-center">
						<img src="/resources/images/male.svg" style=" width: 50px; height: 50px;" alt="womanimage">
						<div class="clearfix mt5"></div>
						<span class="textBold clearfix" id="ptMale"></span>
					</div>
					<div class="col-md-6 text-center">
						<img src="/resources/images/woman.svg" style=" width: 50px; height: 50px;" alt="womanimage">
						<div class="clearfix mt5"></div>
						<span class="textBold clearfix" id="ptFemale"></span>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label class="textBold">Terminated:</label>
					<span class="textBold colorRed" id="ttTerminatedCount"></span>
				</div>
			</div>
				</div>
		</div>
		</div>
        </div>
      </div>
    </div>
        
        
        
        
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
  <script>
  function exportReportOptions(){
		$(".newActPop").slideToggle(200);
		$(".action-pop").slideUp(200);
	}
 $(document).ready(function() {
	 getAssociatesJoinedList();
//	 getAssociatesTerminatedList();
// 	 getClientList();
// 	 getTeamsList();
 });
 
 </script>
 <script type="text/javascript">
function exportAssociateTerminatedSheet(exportType){
	var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#associateTerminatedListDataTableSearch").val();
	 var timePeriod = $("#reportTypeId").val();
	 var  noRecruiterApplicantJoinedList=$("#noRecruiterApplicantJoinedList").val(); 
	 var flag = true;
	 var startDate = '';
	 var endDate = '';
   if(timePeriod == 'custom'){
   	startDate = $("#ReportStartDate").val();
		  endDate = $("#ReportEndDate").val();
		  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
			  if(startDate == null || startDate == ''){
				  document.getElementById("reportsStartDate").style.display="block";
				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
			  }
			  
			  if(endDate == null || endDate == ''){
				  document.getElementById("reportsEndDate").style.display="block";
				  $("#reportsEndDate").empty().append("End Date is Required") ; 
			  }
			  flag = false;
		  }
	 }else{
		 document.getElementById("reportsStartDate").style.display="none";
		 document.getElementById("reportsEndDate").style.display="none";
	 } 
   var orgTeams = [];
	$('input:checkbox[name=teamNameById]:checked').each(function() 
		    {
		orgTeams.push($(this).val());
    });
		var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).val());
		});
		if(noRecruiterApplicantJoinedList !=0){
		if(flag){
			 searchValue = searchValue ? searchValue : "";
			 window.location.href = sourceleadUrl+"/associateTerminatedReportExport?frequency="+timePeriod+"&fromDate="+startDate+"&toDate="+endDate+"&orgTeams="+orgTeams+"&clients="+clients+"&searchValue="+searchValue+"&exportType="+exportType;
		 }
		}else{
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='terminatedAssociateCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='terminatedAssociateCancel();'></b>";
			result = result +"	</div>";
			$("#emptyTerminatedPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
}
function terminatedAssociateCancel(){
	$("#emptyTerminatedPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
 
  <script>
function getAssociatesTerminatedList(){
	var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#associateTerminatedListDataTableSearch").val();
	 var timePeriod = $("#reportTypeId").val();
	 var  noRecruiterApplicantJoinedList=$("#noRecruiterApplicantJoinedList").val(); 
	 var flag = true;
	 var startDate = '';
	 var endDate = '';
    if(timePeriod == 'custom'){
    	startDate = $("#ReportStartDate").val();
		  endDate = $("#ReportEndDate").val();
		  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
			  if(startDate == null || startDate == ''){
				  document.getElementById("reportsStartDate").style.display="block";
				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
			  }
			  
			  if(endDate == null || endDate == ''){
				  document.getElementById("reportsEndDate").style.display="block";
				  $("#reportsEndDate").empty().append("End Date is Required") ; 
			  }
			  flag = false;
		  }
	 }else{
		 document.getElementById("reportsStartDate").style.display="none";
		 document.getElementById("reportsEndDate").style.display="none";
	 } 
    var orgTeams = [];
	$('input:checkbox[name=teamNameById]:checked').each(function() 
		    {
		orgTeams.push($(this).val());
     });
		var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).val());
		});
	 if(flag){
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/associateTerminatedReport?frequency="+timePeriod+"&fromDate="+startDate+"&toDate="+endDate+"&orgTeams="+orgTeams+"&clients="+clients+"&searchValue="+searchValue;
			associateTerminatedReportList(url);

	 }
}
</script>	
 <script>
function associateTerminatedReportList(url){
	var result = "";
		result = result	+ '<div id="associateTerminated" class="project_tabs_inn_table project_tabs_inn_table_a">';
		result = result + '<table id="recruiterReportsListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate ID</th>';
		result = result + '<th>Associate Name</th>';
		result = result + '<th>Emp Type</th>';
		result = result + '<th>Date Of Join</th>';
		result = result + '<th>Manager</th>';
		result = result + '<th>Terminated On</th>';
		result = result + '<th>Reason</th>';
		result = result + '<th>Role</th>';
		result = result + '<th>Gender</th>';
		result = result + '<th>Shift</th>';
		result = result + '<th>Department</th>';
		result = result + '<th>Rate</th>';
		result = result + '<th>Probation Status</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#associateTerminatedDiv").empty();
		$("#associateTerminatedDiv").append(result);
		$("#noReports").val(result.length);
		daywiseLeaveReportsListPagination(url);
}
</script>

<script>
var table = '';
function daywiseLeaveReportsListPagination(url){
	var colIndex = 0;
	table = $('#recruiterReportsListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "searchHighlight": true,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 10
        }),
        "columns": [
            { "data": "terminatedempId" },
            { "data": "empName"},
            { "data": "empType"},
            { "data": "DateOfJoin"},
            { "data": "manager"},
            { "data": "terminatedOn"},
            { "data": "reason"},
            { "data": "Role"},
            { "data": "gender"},
            { "data": "shift"},
            { "data": "Department"},
            { "data": "Rate"},
            { "data": "probationStatus"},
            
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "terminatedempId",
    	  	"sortable": true,
    	  	"visible": $("input[data-name='terminatedempId']").is(":checked"),
    	  	"render": function(data, type, opt, meta) {
    	  		 $("#noRecruiterApplicantJoinedList").val(1); 
    	  		var result = "";
    	  		 if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
           		  result = result + '<td><div><span title='+opt.employeeId+'>'+opt.employeeId+'</span></div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
	             return result;
    	  	}
      },{
    	  "targets": colIndex++,
          "data": "empName",
          "visible": $("input[data-name='empName']").is(":checked"),
          "render": function ( data, type, opt, meta ) {
        	  var result = "";
        	  if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
        		
            		  result = result + '<td><div><span title='+opt.empName+'>'+opt.empName+'</span></div></td>';
        		  }
        		else{
					result = result + ' <td><div>--</div></td>';
				}
        	  return result;
          		
          }
          },
          
          {
        	  "targets": colIndex++,
              "data": "empType",
              "visible": $("input[data-name='empType']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.empType !=null && opt.empType !='' && opt.empType !='undefined'){
            		
                		  result = result + '<td><div><span title='+opt.empType+'>'+opt.empType+'</span></div></td>';
            		}else{
    					result = result + ' <td><div>--</div></td>';
    				}
            	  return result;
              		
              }
              },
              {
                  "targets": colIndex++,
                  "data": "DateOfJoin",
                  "visible": $("input[data-name='fromDate']").is(":checked"),
                  "render": function ( data, type, opt, meta ) {
                  		var result = "";
                  		if(opt.fromDate !=null && opt.fromDate !='' && opt.fromDate !='undefined'){
  							result = result + ' <td><div><span>'+opt.fromDate+'</span></div></td>';
  						}else{
  							result = result + ' <td><div><span>---</span></div></td>';
  						}
      	              return result;
                  }
            },
            {
                "targets": colIndex++,
                "data": "manager",
                "visible": $("input[data-name='reportingManager']").is(":checked"),
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.reportingManager !=null && opt.reportingManager !='' && opt.reportingManager !='undefined'){
      						result = result + ' <td><div><span>'+opt.reportingManager+'</span></div></td>';
      					}else{
      						result = result + ' <td><div><span>---</span></div></td>';
      					}
      	              return result;
                }
          },
          {
              "targets": colIndex++,
              "data": "terminatedOn",
              "visible": $("input[data-name='terminatedDate']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
              		var result = "";
              		if(opt.terminateStringDate !=null && opt.terminateStringDate !='' && opt.terminateStringDate !='undefined'){
  						result = result + ' <td><div><span>'+opt.terminateStringDate+'</span></div></td>';
  					}else{
  						result = result + ' <td><div><span>---</span></div></td>';
  					}
  	              return result;
              }
        },
        {
            "targets": colIndex++,
            "data": "reason",
            "visible": $("input[data-name='terminatedReason']").is(":checked"),
            "render": function ( data, type, opt, meta ) {
            		var result = "";
            		if(opt.terminatedReason !=null && opt.terminatedReason !='' && opt.terminatedReason !='undefined'){
						result = result + ' <td><div><span>'+opt.terminatedReason+'</span></div></td>';
					}else{
						result = result + ' <td><div><span>---</span></div></td>';
					}
	              return result;
            }
      },
      {
          "targets": colIndex++,
          "data": "Role",
          "visible": $("input[data-name='userRole']").is(":checked"),
          "render": function ( data, type, opt, meta ) {
        	  var result = "";
    	  		   if(opt.userRole != null && opt.userRole !='' && opt.userRole != 'undefined'){
    	  			result = result + ' <td><div><span>'+opt.userRole+'</span></div></td>';
    	  		   }else{
    	  			result = result + ' <td><div><span>---</span></div></td>';
    	  		   }
    	  		return result;
          }
        },
          {
              "targets": colIndex++,
              "data": "gender",
              "visible": $("input[data-name='gender']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
              		var result = "";
              		if(opt.gender !=null && opt.gender !='' && opt.gender !='undefined'){
  							result = result + ' <td><div><span>'+opt.gender+'</span></div></td>';
  						}else{
  							result = result + ' <td><div><span>---</span></div></td>';
  						}
  	              return result;
              }
        },
        {
            "targets": colIndex++,
            "data": "shift",
            "visible": $("input[data-name='shift']").is(":checked"),
            "render": function ( data, type, opt, meta ) {
            		var result = "";
            		if(opt.shiftName !=null && opt.shiftName !='' && opt.shiftName !='undefined'){
    						result = result + ' <td><div><span>'+opt.shiftName+'</span></div></td>';
    					}else{
    						result = result + ' <td><div><span>---</span></div></td>';
    					}
                  return result;
            }
      },
 {
        "targets": colIndex++,
        "data": "Department",
        "visible": $("input[data-name='deptName']").is(":checked"),
        "render": function ( data, type, opt, meta ) {
        	var result = "";
        	
				if(opt.deptName != null && opt.deptName != '' && opt.deptName !='undefined'){
					result = result + '<td><div><span>'+opt.deptName+' </span></div></td>';
        	 }else{
        		 result = result + '<td><div><span>---</span></div></td>';
        	 }
				return result;
        }
      } ,
        {
            "targets": colIndex++,
            "data": "Rate",
            "visible": $("input[data-name='rate']").is(":checked"),
            "render": function ( data, type, opt, meta ) {
          	  var result = "";
	    	  		   if(opt.rate != null && opt.rate !='' && opt.rate != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.currency+'/'+opt.rate+'/'+opt.ratePer+'</span></div></td>';
	    	  		   }else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		   }
	    	  		return result;
            }
          }	,
          {
              "targets": colIndex++,
              "data": "probationStatus",
              "visible": $("input[data-name='probationStatus']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
              		var result = "";
              		if(opt.probationStatus !=null && opt.probationStatus !='' && opt.probationStatus !='undefined'){
    							result = result + ' <td><div><span>'+opt.probationStatus+'</span></div></td>';
    						}else{
    							result = result + ' <td><div><span>---</span></div></td>';
    						}
    	              return result;
              }
        }],
		    "scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
    } );
    $("#recruiterReportsListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    initializeTerminatedTable();
}

$("#associateTerminatedListDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getAssociatesTerminatedList();
	   }
	});
</script>
 
 <script type="text/javascript">
function exportAssociateJoinedSheet(exportType){
	var sourceleadUrl ='${sourceleadUrl}';
	   var tab = '${tab}';
	   var frequency=$("#reportTypeId").val();
	   var graphFrequency=$("#year").val();
	   
	  var  noApplicantJoinedList=$("#noApplicantJoinedList").val(); 
	    var fromDate='';
	    var toDate='';
		var searchValue = $("#associateJoinedListDataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var employmentType = [];
		 $('input:checkbox[name=employmentType]:checked').each(function() 
		 {
			employmentType.push($(this).val());
		 });
		 var clientNameById = [];
		 $('input:checkbox[name=clientNameById]:checked').each(function() 
		 {
			 clientNameById.push($(this).val());
		 });
		 var teamNameById = [];
		 $('input:checkbox[name=teamNameById]:checked').each(function() 
		 {
			 teamNameById.push($(this).val());
		 });
		 var incentivizedType = [];
		 $('input:checkbox[name=incentivizedType]:checked').each(function() 
		 {
			 incentivizedType.push($(this).val());
		 });
		 var searchType = $('input[name=searchType]:checked').val();
		 if(noApplicantJoinedList !=0){
	    if(frequency == "custom"){
	        var fromDate = $("#ReportStartDate").val();
	  		  var toDate = $("#ReportEndDate").val();
	    	if(fromDate == ''){
	    		  document.getElementById("reportsStartDate").style.display="block";
				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
	        }
	        if(toDate == ''){
	        	 document.getElementById("reportsEndDate").style.display="block";
			  $("#reportsEndDate").empty().append("End Date is Required") ; 
	        }
		window.location.href = sourceleadUrl+"/associateJoinedReportExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&graphFrequency="+graphFrequency+"&exportType="+exportType+"&type=applicantReport";
}else{
	 document.getElementById("reportsStartDate").style.display="none";
	 document.getElementById("reportsEndDate").style.display="none";
	 window.location.href = sourceleadUrl+"/associateJoinedReportExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&graphFrequency="+graphFrequency+"&exportType="+exportType+"&type=applicantReport";
} 
}else{
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>List is Empty</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='joinedAssociateCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='joinedAssociateCancel();'></b>";
	result = result +"	</div>";
	$("#emptyJoinedPopup").append(result);
	$("#candidate_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
}
function joinedAssociateCancel(){
	$("#emptyJoinedPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
 
 <script type="text/javascript">
       function getAssociatesJoinedList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency=$("#reportTypeId").val();
    	   var graphFrequency=$("#year").val();
    	   
    	  var  noApplicantJoinedList=$("#noApplicantJoinedList").val(0); 
    	    var fromDate='';
    	    var toDate='';
    		var searchValue = $("#associateJoinedListDataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		searchValue = searchValue ? searchValue : "";
    		var employmentType = [];
    		 $('input:checkbox[name=employmentType]:checked').each(function() 
			 {
    			employmentType.push($(this).val());
			 });
    		 var clientNameById = [];
    		 $('input:checkbox[name=clientNameById]:checked').each(function() 
			 {
    			 clientNameById.push($(this).val());
			 });
    		 var teamNameById = [];
    		 $('input:checkbox[name=teamNameById]:checked').each(function() 
			 {
    			 teamNameById.push($(this).val());
			 });
    		 var incentivizedType = [];
    		 $('input:checkbox[name=incentivizedType]:checked').each(function() 
    		 {
    			 incentivizedType.push($(this).val());
    		 });
    		 var searchType = $('input[name=searchType]:checked').val();
    	    if(frequency == "custom"){
    	        var fromDate = $("#ReportStartDate").val();
    	  		  var toDate = $("#ReportEndDate").val();
    	    	if(fromDate == ''){
    	    		  document.getElementById("reportsStartDate").style.display="block";
    				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
    	        }
    	        if(toDate == ''){
    	        	 document.getElementById("reportsEndDate").style.display="block";
   				  $("#reportsEndDate").empty().append("End Date is Required") ; 
    	        }
    	        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
        	        var url = sourceleadUrl+"/associateJoinedReport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&graphFrequency="+graphFrequency+"&type=applicantReport";
        		    prepareOfferList(url);
    	        }
    	    }else{
    	    	 document.getElementById("reportsStartDate").style.display="none";
    			 document.getElementById("reportsEndDate").style.display="none";
    	        var url = sourceleadUrl+"/associateJoinedReport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&graphFrequency="+graphFrequency+"&type=applicantReport";
    		    prepareOfferList(url);
    	    } 
 
}
</script> 
 
<script>
function prepareOfferList(url){
	var result = "";
        result = result +'<div id="associateJoined" class="require_wrap_table tableFirstSecond clearfix">';
        result = result +'<table id="offerpagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Associate ID</th>';
		result = result + '<th>Associate Name</th>';
		result = result + '<th>Gender</th>';
		result = result + '<th>Emp Type</th>';
		result = result + '<th>Department</th>';
		result = result + '<th>Role</th>';
		result = result + '<th>Rate</th>';
		result = result + '<th>Date Of Join</th>';
		result = result + '<th>Manager</th>';
		result = result + '<th>Shift</th>';
		result = result + '<th>Probation Status</th>';
		
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        result = result +'</div>';
        $("#applicantJoinedDiv").empty();
        $("#applicantJoinedDiv").append(result);
         offerpagination(url);
}
</script>

<script>
var joinedtable = '';
function offerpagination(url){
	var colIndex = 0;
	joinedtable = $('#offerpagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "joinedempId" },
            { "data": "joinedEmpName"},
            { "data": "joinedEmpGender"},
            { "data": "joinedEmpType"},
            { "data": "joinedDeptName"},
            { "data": "joinedUserRole"},
            { "data": "joinedRate"},
            { "data": "joinedFromDate"},
            { "data": "joinedReportingManager"},
            { "data": "joinedShift"},
            { "data": "probationStatus"},
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "joinedempId",
	    	  	"sortable": true,
	    	  	"visible": $("input[data-name='joinedempId']").is(":checked"),
	    	  	"render": function(data, type, opt, meta) {
	    	  		 $("#noApplicantJoinedList").val(1); 
	    	  		var result = "";
	    	  		 if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
               		  result = result + '<td><div><span title='+opt.employeeId+'>'+opt.employeeId+'</span></div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "joinedEmpName",
              "visible": $("input[data-name='joinedEmpName']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
            		
                		  result = result + '<td><div><span title='+opt.empName+'>'+opt.empName+'</span></div></td>';
            		  }
            		else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },
	          {
	              "targets": colIndex++,
	              "data": "joinedEmpGender",
	              "visible": $("input[data-name='joinedEmpGender']").is(":checked"),
	              "render": function ( data, type, opt, meta ) {
	              		var result = "";
	              		if(opt.gender !=null && opt.gender !='' && opt.gender !='undefined'){
	  							result = result + ' <td><div><span>'+opt.gender+'</span></div></td>';
	  						}else{
	  							result = result + ' <td><div><span>---</span></div></td>';
	  						}
	  	              return result;
	              }
	        },
	      
	      {
	    	  "targets": colIndex++,
              "data": "joinedEmpType",
              "visible": $("input[data-name='joinedEmpType']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.empType !=null && opt.empType !='' && opt.empType !='undefined'){
            		
                		  result = result + '<td><div><span title='+opt.empType+'>'+opt.empType+'</span></div></td>';
            		}else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "joinedDeptName",
            "visible": $("input[data-name='joinedDeptName']").is(":checked"),
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	
   				if(opt.deptName != null && opt.deptName != '' && opt.deptName !='undefined'){
   					result = result + '<td><div><span>'+opt.deptName+' </span></div></td>';
            	 }else{
            		 result = result + '<td><div><span>---</span></div></td>';
            	 }
   				return result;
            }
          } ,{
              "targets": colIndex++,
              "data": "joinedUserRole",
              "visible": $("input[data-name='joinedUserRole']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	    	  		   if(opt.userRole != null && opt.userRole !='' && opt.userRole != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.userRole+'</span></div></td>';
	    	  		   }else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		   }
	    	  		return result;
              }
            },
            {
                "targets": colIndex++,
                "data": "joinedRate",
                "visible": $("input[data-name='joinedRate']").is(":checked"),
                "render": function ( data, type, opt, meta ) {
              	  var result = "";
  	    	  		   if(opt.rate != null && opt.rate !='' && opt.rate != 'undefined'){
  	    	  			result = result + ' <td><div><span>'+opt.currency+'/'+opt.rate+'/'+opt.ratePer+'</span></div></td>';
  	    	  		   }else{
  	    	  			result = result + ' <td><div><span>---</span></div></td>';
  	    	  		   }
  	    	  		return result;
                }
              },
          {
                "targets": colIndex++,
                "data": "joinedFromDate",
                "visible": $("input[data-name='joinedFromDate']").is(":checked"),
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.fromDate !=null && opt.fromDate !='' && opt.fromDate !='undefined'){
							result = result + ' <td><div><span>'+opt.fromDate+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
    	              return result;
                }
          },
          {
              "targets": colIndex++,
              "data": "joinedReportingManager",
              "visible": $("input[data-name='joinedReportingManager']").is(":checked"),
              "render": function ( data, type, opt, meta ) {
              		var result = "";
              		if(opt.reportingManager !=null && opt.reportingManager !='' && opt.reportingManager !='undefined'){
							result = result + ' <td><div><span>'+opt.reportingManager+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
  	              return result;
              }
        },
        {
            "targets": colIndex++,
            "data": "joinedShift",
            "visible": $("input[data-name='joinedShift']").is(":checked"),
            "render": function ( data, type, opt, meta ) {
            		var result = "";
            		if(opt.shiftName !=null && opt.shiftName !='' && opt.shiftName !='undefined'){
							result = result + ' <td><div><span>'+opt.shiftName+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
	              return result;
            }
      },
      {
          "targets": colIndex++,
          "data": "ProbationStatus",
          "visible": $("input[data-name='probationStatus']").is(":checked"),
          "render": function ( data, type, opt, meta ) {
          		var result = "";
          		if(opt.probationStatus !=null && opt.probationStatus !='' && opt.probationStatus !='undefined'){
							result = result + ' <td><div><span>'+opt.probationStatus+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
	              return result;
          }
    }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
// 	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#offerpagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#associateJoinedListDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getAssociatesJoinedList();
    	   }
    	});
    initializeJoinedTable();
}

</script>
  


<script>
function showCustomFields(e){
    var frequency = e.value;
    if(frequency=="custom"){
    	document.getElementById("customSearch").style.display="block";
		 document.getElementById("runButtonDiv").style.display="none";
	}
	if(frequency != "custom"){
		document.getElementById("customSearch").style.display="none";
	    document.getElementById("reportsStartDate").style.display="none";
		document.getElementById("reportsEndDate").style.display="none";
		document.getElementById("runButtonDiv").style.display="block";
	}
    
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
      var finalDateFormatEndDate = '';
      if(locationDate.match("yyyy-MM-dd")){
          finalDateFormatEndDate = 'yy-mm-dd';
      }if(locationDate.match("dd-MM-yyyy")){
          finalDateFormatEndDate = 'dd-mm-yy'; 
      }if(locationDate.match("MM-dd-yyyy")){
          finalDateFormatEndDate = 'mm-dd-yy'; 
      }if(locationDate.match("yyyy-dd-MM")){
          finalDateFormatEndDate = 'yy-dd-mm'; 
      }

      $( "#custFromDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
            maxDate: new Date() ,
            onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custToDate").datepicker("option", "minDate", dt);
             removeStartValid();
            } 
      
      });
      
      $( "#custToDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
          	maxDate: new Date() ,
      		onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custFromDate").datepicker("option", "maxDate", dt);
             removeEndValid();
            }});
}
function removeStartValid(){
    $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
}
function removeEndValid(){
    $('#custToDateDiv').find('p.jquery_form_error_message').remove();
}

</script>
<script type="text/javascript">
	function flushCustom() {
		$('#ReportStartDate').val('');
		$('#ReportEndDate').val('');
		$.datepicker._clearDate('#ReportStartDate');
		$.datepicker._clearDate('#ReportEndDate'); 
	}
</script>

 
   <!-- ========================== Filter popup Written By Laxman ===============================  -->
<script>
function closeApplicantFilterPop(){
	$("#invoiceFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openFilterPop(){
	 $("#invoiceFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function filter(){
	$("#associateJoinedListDataTableSearch").val('');
}
function resetApplicantFilter(){
	$("#myClientInput").val('');
	$("ul#empFilter > li input:checkbox").prop("checked",false);
	$("ul#searchTypeFilter > li input:radio").prop("checked",false);
	$("ul#durationFilter > li input:checkbox").prop("checked",false);
	$("ul#myClinetListUL > li input:checkbox").prop("checked",false);
	$("ul#myTeamsListUL > li input:checkbox").prop("checked",false);
	$("ul#incentivizedFilter  > li input:checkbox").prop("checked",false);
	
	getAssociatesJoinedList();
}
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	



</script>
 


<script type="text/javascript">
function candidtaeCancel(){
	$("#emptyPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
function addValidClass(){
	 var StartDate = document.getElementById("ReportStartDate").value;
	 if(StartDate){
		 document.getElementById("reportsStartDate").style.display="block";
	 }
	 
	 var EndDate = document.getElementById("ReportEndDate").value;
	
	 if(EndDate){
		 document.getElementById("reportsEndDate").style.display="block";
	 }
}
</script>
<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatRepo = getJSDateFormat(locationDate);
		  
		$('#ReportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			onSelect : function(selected) {
				/* var dt = new Date(selected);
				//var dt = toDate("#ReportStartDate");
				dt.setDate(dt.getDate() + 1);
				$("#ReportEndDate").datepicker("option", "minDate", dt); */
				$("#ReportEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#ReportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				/* //var dt = toDate("#ReportEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#ReportStartDate").datepicker("option", "maxDate", dt);
				 $( "#ReportStartDate" ).datepicker({  maxDate: new Date() }); */
				 $("#ReportStartDate").datepicker("option", "maxDate", selected);
				addValidClass();
			}
		});
		
	});
</script>	
<script>
$(function() {
    $('#araginglistSel').change(function(){
    	var optval = $(this).val();
		if(optval === "associateTerminated") {
			getAssociatesTerminatedList();
			$("#associateJoined").hide();
			$("#associateTerminated").show();
			
			$("#customDateRecurter").show();
			$("#customDateApplicant").hide();

			$("#applicantJoinedDiv").hide();
			$("#associateTerminatedDiv").show();
			$("#associateJoinSearchId").hide();
			$("#associateTeminateSearchId").show();
			
			$("#joinedCustomFields").hide();
			$("#terminatedCustomFields").show();
			$("#joinedCustomFieldscolPopMid").hide();
			$("#terminatedCustomFieldscolPopMid").show();
			$("#associateTeminateExportPdf").show();
			$("#associateTeminateExportExcel").show();
			$("#associateJoinExportPdf").hide();
			$("#associateJoinExportExcel").hide();
		}else if(optval === "associateJoined"){
			getAssociatesJoinedList();
			$("#recruiter_search_requiremet").hide();
			$("#applicant_search_requiremet").show();
			$("#associateTeminateExportPdf").hide();
			$("#associateTeminateExportExcel").hide();
			$("#associateJoinExportPdf").show();
			$("#associateJoinExportExcel").show();
			
			$("#associateJoinSearchId").show();
			$("#associateTeminateSearchId").hide();
			
			
			$("#joinedCustomFields").show();
			$("#terminatedCustomFields").hide();
			$("#joinedCustomFieldscolPopMid").show();
			$("#terminatedCustomFieldscolPopMid").hide();
			
//				$("#associateTerminatedDiv").empty();
			$("#customDateRecurter").hide();
			$("#customDateApplicant").show();
			$("#associateJoined").show();
			$("#associateTerminatedDiv").hide();
			$("#customDatesDropdown").hide();
			$("#recurter").hide();
			$("#applicant").show();
			$("#associateTerminatedDiv").hide();
			$("#applicantJoinedDiv").show();
		}
    });
});
</script> 

<script type="text/javascript">

	function columPop(type){
		if(type === "joined") {
			$("#joinedColumnPopup").show();
		} else {
			$("#terminatedColumnPopup").show();
		}
	}
	function columClose(){
		$(".colPop").hide();
	}
	$("html").click(function(){
		$(".colPop").hide();
	});
	$(".refreIcon, .colPop").click(function(e){
	 	e.stopPropagation();
	});		
		
	function joinedColumHide(){
		hideColumns=[]; // reset the array object.
		$("#joinedCustomColumns input").each(function() {
			joinedtable.column($(this).attr('data-column'))
		});
		
		$("#joinedCustomColumns input").each(function() {
			columClose();// 
		   var chkdVal= $(this).prop('checked');
// 		alert("chkdVal "+chkdVal);
		   var column = joinedtable.column($(this).attr('data-column'));
			  if(chkdVal == true){
			         column.visible(true);
			    }
			    else{
			      column.visible(false);
			      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
			  }
	    });
		$(".colPop").hide();
	}
	function columHide(){
		$("#terminatedCustomColumns input").each(function() {
			columClose();// 
		   var chkdVal= $(this).prop('checked');
// 		alert("chkdVal "+chkdVal);
		   var column = table.column($(this).attr('data-column'));
			  if(chkdVal == true){
			         column.visible(true);
			    }
			    else{
			      column.visible(false);
			      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
			  }
	    });
		$(".colPop").hide();
	   
	}
	function checkAllJoinedColumns(obj){
		var id = obj.id;
// 		var chkdVal= $('.toggle-vis:input:checkbox').prop('checked');
		var chkdVal = $(obj).is(":checked");
		if(chkdVal == true){
		if(id =='joinedColumnsPickerCheckAll'){
			$('#joinedCustomColumns input:checkbox').prop('checked',true);
		}
		}
		else{
			if(id =='joinedColumnsPickerCheckAll'){
				$('#joinedCustomColumns input:checkbox').prop('checked',false);
			}
		} 
	}
	function checkAllColumns(obj){
		var id = obj.id;
		//var chkdVal= $("#terminatedCustomColumns").find("input[class='toggle-vis']").prop('checked');
		var chkdVal = $(obj).is(":checked");
		if(chkdVal == true){
		if(id =='columnsPickerCheckAll'){
			$('#terminatedCustomColumns input:checkbox').prop('checked',true);
		}
		}
		else{
			if(id =='columnsPickerCheckAll'){
				$('#terminatedCustomColumns input:checkbox').prop('checked',false);
			}
		} 
	}
	</script>
	<script>
/* 	$(document).on( 'init.dt.dth', function (e, settings, json) {
    table = new $.fn.dataTable.Api( settings );
    var body = $( table.table().body() );
//     if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
	
// 	}
} ); */
	$(document).on( 'init.dt.dth', function (e, settings, json) {
		joinedtable = new $.fn.dataTable.Api( settings );
	    var body = $( joinedtable.table().body() );
	    
	    table = new $.fn.dataTable.Api( settings );
	    
//	     if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
		
//	 	}
	} );
	
	var tableSettings = ""
	$(document).on( 'init.dt.dth', function (e, settings, json) {
		tableSettings = settings;
	});
	
	function initializeJoinedTable() {
		joinedtable = new $.fn.dataTable.Api( tableSettings );
	    var body = $( joinedtable.table().body() );
	}
	
	function initializeTerminatedTable() {
		 table = new $.fn.dataTable.Api( tableSettings );
		 var body = $( table.table().body() );
	}
	
	</script>
	
	
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="js/jquery.timepicker.js"></script> 
<script src="js/jquery-1.11.3.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/jquery-ui.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 

<script type="text/javascript" src="js/jquery.mousewheel.js"></script> 
<script type="text/javascript" src="js/jquery.jscrollpane.min.js"></script> 
<!-- amCharts javascript sources --> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script>
	
	
	<script type="text/javascript">
       function getAssociatesJoinedGraph(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var graphFrequency=$("#year").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
        	    	$.ajax({
        	    		type : "POST",
        	    		url : sourceleadUrl+"/getAssociatesJoinedGraph?graphFrequency="+graphFrequency,
        	    		success : function(data) {
        	    			if(data.length > 0) {
        	    				
	        	    			associateGraph(data)
        	    			
	        	    			var fullTimeCount = data[0].empFullTimeCountTotal;
	        	    			var partTimeCount = data[0].empPartTimeCountTotal;
	        	    			var ftMaleCount = data[0].empFtMaleCountTotal;
	        	    			var ftFeMaleCount = data[0].empFtFeMaleCountTotal;
	        	    			var ptMaleCount = data[0].empPtMaleCountTotal;
	        	    			var ptFeMaleCount = data[0].empPtFeMaleCountTotal;
	        	    			var totalJoinedCount = data[0].totalJoinedCount;
	        	    			var totalTerminatedCount = data[0].totalTerminatedCount;
	        	    			

	        	    			
	        	    			$("#partTimeCount").text(partTimeCount);
	        	    			$("#ptMale").text(ptMaleCount);
	        	    			$("#ptFemale").text(ptFeMaleCount);
	        	    			$("#ftMale").text(ftMaleCount);
	        	    			$("#ftFeMale").text(ftFeMaleCount);
	        	    			$("#fullTimeCount").text(fullTimeCount);
	        	    			$("#ttJoinedCount").text(totalJoinedCount);	
	        	    			$("#ttTerminatedCount").text(totalTerminatedCount);
	        	    			
	        	    			var fullTimeSize = 100 / fullTimeCount;
	        	    			var partTimeSize = 100 / partTimeCount;
	        	    			
	        	    			
	        	    			$("#fullTimeMaleProgress").attr("style", "width:" + (ftMaleCount * fullTimeSize) + "%");
	        	    			$("#fullTimeFeMaleProgress").attr("style", "width:" + (ftFeMaleCount * fullTimeSize) + "%");
	        	    			$("#partTimeMaleProgress").attr("style", "width:" + (ptMaleCount * partTimeSize) + "%");
	        	    			$("#partTimeFeMaleProgress").attr("style", "width:" + (ptFeMaleCount * partTimeSize) + "%");

        	    			}
        	    		}
        	    	});
 
}
</script> 
	
<script type="text/javascript">
function associateGraph(data){
	
	var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        		date: opt.month,
	        		column2: opt.terminatedCount,
	        		column1: opt.hiredCount
	       });
	   }
	   
	AmCharts.makeChart("associateJoineereport",
			{
				"type": "serial",
				"categoryField": "date",
				"dataDateFormat": "YYYY-MM",
				"theme": "default",
				"categoryAxis": {
					"minPeriod": "MM",
					"parseDates": true,
					"twoLineMode": true,
					"axisColor": "#AAB3B3",
					"gridThickness": 0,
					"markPeriodChange": false
				},
				"trendLines": [],
				"graphs": [
					{
						"balloonText": "[[title]]:  [[value]]",
						"bullet": "round",
						"bulletSize": 10,
						"id": "joined",
						"legendAlpha": 1,
						"legendColor": "#2ECC71",
						"lineColor": "#2ECC71",
						"markerType": "circle",
						"title": "Joined",
						"type": "smoothedLine",
						"valueField": "column1"
					},
					{
						"balloonText": "[[title]]:  [[value]]",
						"bullet": "round",
						"bulletSize": 10,
						"id": "terminated",
						"legendAlpha": 1,
						"legendColor": "#FF0000",
						"lineColor": "#FF0000",
						"markerType": "circle",
						"title": "Terminated",
						"type": "smoothedLine",
						"valueField": "column2"
					}
				],
				"guides": [],
				"valueAxes": [
					{
						"id": "ValueAxis-1",
						"axisColor": "#AAB3B3",
						"gridThickness": 0,
						"title": "",
						"titleBold": false
					}
				],
				"allLabels": [],
				"balloon": {},
				"legend": {
					"enabled": true,
					"equalWidths": false,
					"markerBorderAlpha": 0,
					"markerSize": 12,
					"useGraphSettings": true
				},
				"titles": [],
				"dataProvider": chartData
			}
		);
	}
			
</script>
	

		<script>
		$(function(){
		$("#GraphView").click(function() {
			$(this).removeClass("btn-default").addClass("btn-primary");
			$("#ListView").removeClass("btn-primary").addClass("btn-default");
			$("#applicantJoinedDiv").hide();
			$(".GraView").show();
			$("#araginglistSel").hide();
			$("#joinedCustomFields").hide();
			$("#associateJoinSearchId").hide();
			$("#reportTypeId").hide();
			$("#customDateRecurter").hide();
			$("#year").show();
			$("#customize_btn").show();
// 			$("#reportTypeId").val(null);
			$("#associateTerminatedDiv").hide();
			
			$("#terminatedCustomFields").hide();
			$("#customDateApplicant").hide();
				$("#terminatedCustomFieldscolPopMid").hide();
				$("#associateTeminateSearchId").hide();
				$("#customDateRecurter").hide();
				$("#customDatesDropdown").hide();
				$("#associateTerminatedDiv").hide();


		});
		
		$("#ListView").click(function() {
			$(this).removeClass("btn-default").addClass("btn-primary");
			$("#GraphView").removeClass("btn-primary").addClass("btn-default");
// 			$("#applicantJoinedDiv").show();
			$(".GraView").hide();
			$("#araginglistSel").show();
// 			$("#joinedCustomFields").show();
// 			$("#associateJoinSearchId").show();
// 			$("#reportTypeId").show();
// 			$("#customDateRecurter").show();
			$("#year").hide();
			$("#customize_btn").hide();
			
			
			var optval=$("#araginglistSel").val();
			if(optval === "associateTerminated") {
				getAssociatesTerminatedList();
				$("#associateJoined").hide();
				$("#associateTerminated").show();
				

				$("#applicantJoinedDiv").hide();
				$("#associateTerminatedDiv").show();
				$("#associateJoinSearchId").hide();
				$("#associateTeminateSearchId").show();
				
				$("#joinedCustomFields").hide();
				$("#terminatedCustomFields").show();
				$("#joinedCustomFieldscolPopMid").hide();
				$("#terminatedCustomFieldscolPopMid").show();
				$("#reportTypeId").show();
				$("#customDateRecurter").show();
				$("#customDateApplicant").hide();

			}else if(optval === "associateJoined"){
				getAssociatesJoinedList();
				
				$("#associateJoinSearchId").show();
				$("#associateTeminateSearchId").hide();
				
				
				$("#joinedCustomFields").show();
				$("#terminatedCustomFields").hide();
				$("#joinedCustomFieldscolPopMid").show();
				$("#terminatedCustomFieldscolPopMid").hide();
				
//					$("#associateTerminatedDiv").empty();
				$("#customDateRecurter").hide();
				$("#customDateApplicant").show();
				$("#associateJoined").show();
				$("#associateTerminatedDiv").hide();
				$("#customDatesDropdown").hide();
				$("#associateTerminatedDiv").hide();
				$("#applicantJoinedDiv").show();
				$("#reportTypeId").show();
			}
		});
		});
		</script>
<jsp:include page="../masterFooter.jsp" />

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
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Applicants Joined Report  </div>
    </section>
       
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
	      <div class="positionRelative reportHead clearfix">
	      
	      	<div class="select-fild col-md-2 mt10">
                    <select id="araginglistSel">
                        <option value="associateJoined" >Associate Joine Reports</option>
                        <option value="associateTerminated" sel>Associate Terminated Report</option>
                    </select>
                </div>
	      
	      <span class="pull-left refreIcon" id="advanceSearchDiv">
<!-- 	      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetApplicantFilter();"></a> -->
	            		<a href="javascript:void(0)" onclick="columPop();" title="Column Chooser" class="columIcon"></a>
	            		
	            		 <div class="colPop">
	<div class="colPopMid coldropdown-parent">
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
		<ul id="jobCustomColumns">
			<li>
				<span class="checkbox-new clearfix">
					<input id="a" class="checkbox-custom toggle-vis" name="paidComponent" data-column="0" type="checkbox" checked="" data-name="empId">
					<label for="a" class="checkbox-custom-label" >Employee ID</label>
				</span>
			</li>
			
						<li>
				<span class="checkbox-new clearfix">
					<input id="b" class="checkbox-custom toggle-vis" name="paidComponent" data-column="1" type="checkbox" checked="" data-name="empName">
					<label for="b" class="checkbox-custom-label" >Employee Name</label>
				</span>
			</li>
			
									<li>
				<span class="checkbox-new clearfix">
					<input id="c" class="checkbox-custom toggle-vis" name="paidComponent" data-column="2" type="checkbox" data-name="gender">
					<label for="c" class="checkbox-custom-label" >Gender</label>
				</span>
			</li>
			
												<li>
				<span class="checkbox-new clearfix">
					<input id="d" class="checkbox-custom toggle-vis" name="paidComponent" data-column="3" type="checkbox" checked="" data-name="empType">
					<label for="d" class="checkbox-custom-label" >Emp Type</label>
				</span>
			</li>
			
															<li>
				<span class="checkbox-new clearfix">
					<input id="e" class="checkbox-custom toggle-vis" name="paidComponent" data-column="4" type="checkbox"  data-name="deptName">
					<label for="e" class="checkbox-custom-label" >Department</label>
				</span>
			</li>
			
																		<li>
				<span class="checkbox-new clearfix">
					<input id="f" class="checkbox-custom toggle-vis" name="paidComponent" data-column="5" type="checkbox"  data-name="userRole">
					<label for="f" class="checkbox-custom-label" >Role</label>
				</span>
			</li>
			
																					<li>
				<span class="checkbox-new clearfix">
					<input id="g" class="checkbox-custom toggle-vis" name="paidComponent" data-column="6" type="checkbox" data-name="rate">
					<label for="g" class="checkbox-custom-label" >Rate</label>
				</span>
			</li>
			
																								<li>
				<span class="checkbox-new clearfix">
					<input id="h" class="checkbox-custom toggle-vis" name="paidComponent" data-column="7" type="checkbox" checked="" data-name="fromDate">
					<label for="h" class="checkbox-custom-label" >Date Of Join</label>
				</span>
			</li>
			
						
																								<li>
				<span class="checkbox-new clearfix">
					<input id="i" class="checkbox-custom toggle-vis" name="paidComponent" data-column="8" type="checkbox" checked="" data-name="reportingManager">
					<label for="i" class="checkbox-custom-label">Manager</label>
										</span>
			</li>
									
																								<li>
				<span class="checkbox-new clearfix">
					<input id="j" class="checkbox-custom toggle-vis" name="paidComponent" data-column="9" type="checkbox" checked="" data-name="shift">
					<label for="j" class="checkbox-custom-label">Shift</label>
										</span>
			</li>

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
			<button type="button" class="btn btn-primary btn-sm" id="customDateApplicant" onclick="getApplicantsJoinList();" style="display: none">Run</button>
	        <button type="button" class="btn btn-primary btn-sm" id="customDateRecurter" onclick="filter(); getAssociatesJoinedList();" style="display: block">Run</button>
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
	      	  </li>
					<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openFilterPop();"></a></li>
					
					<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
						<div class="newActPop" style="display: none;">
							<span>
			           	   		<a href="javascript:void(0)" id="applicantExportPdf" onclick="projectExport('Pdf');" title="PDF" style="display: none">Export PDF</a>
								<a href="javascript:void(0)" id="applicantExportExcel" onclick="projectExport('Excel');" title="Excel" style="display: none">Export Excel</a>  
								
								 		<a href="javascript:void(0)" id="recurterExportPdf" onclick="exportToPdf();" title="PDF" style="display: block">Export PDF</a>
								<a href="javascript:void(0)" id="recurterExportExcel" onclick="exportUserData();" title="Excel" style="display: block">Export Excel</a>           
							</span>
						</div>
					</li>
			  <li class="" id="associateTeminateSearchId" style="display: none" ><input type="text" class="repSearchBar" placeholder="Search" id="dataTableSearch" ><span class="repSearchIcon" onclick="getAssociatesTerminatedList();"></span></li>
	     	 <li class="" id="associateJoinSearchId" style="display: block;"><input type="text" class="repSearchBar" placeholder="Search" id="associateJoinedListDataTableSearch"><span class="repSearchIcon" onclick="getAssociatesJoinedList();"></span></li>
	        </ul>
	      </div>
        <div class="timesheet_head">
      
       </div>
       <div id="emptyPopup"></div>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="applicantJoinedDiv" style="display: block"></div>
	           <div id="associateTerminatedDiv" style="display: none"></div>
<input type="hidden" id="noApplicantJoinedList" name="noApplicantJoinedList"  value="0"/>
<input type="hidden" id="noRecruiterApplicantJoinedList" name="noRecruiterApplicantJoinedList"  value="0"/>        
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
  <script>
 $(document).ready(function() {
// 	 getAssociatesTerminatedList();
	 getAssociatesJoinedList();
	 getAssociatesTerminatedList();
// 	 getClientList();
// 	 getTeamsList();
 });
 
 </script>
 
 
  <script>
function getAssociatesTerminatedList(){
	var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#dataTableSearch").val();
	 var timePeriod = $("#reportTypeId").val();
	 var  noRecruiterApplicantJoinedList=$("#noRecruiterApplicantJoinedList").val(0); 
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
// 		 usersListPagination(url,sourceleadUrl);
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
            { "data": "empID" },
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
            
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "empID",
    	  	"sortable": true,
//     	  	"visible": $("input[data-name='empId']").is(":checked"),
    	  	"render": function(data, type, opt, meta) {
    	  		 $("#noApplicantJoinedList").val(1); 
    	  		var result = "";
    	  		 if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
           		  result = result + '<td><div><span title='+opt.empId+'>'+opt.empId+'</span></div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
	             return result;
    	  	}
      },{
    	  "targets": colIndex++,
          "data": "empName",
//           "visible": $("input[data-name='empName']").is(":checked"),
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
//               "visible": $("input[data-name='empType']").is(":checked"),
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
//                   "visible": $("input[data-name='fromDate']").is(":checked"),
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
//                 "visible": $("input[data-name='reportingManager']").is(":checked"),
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
//               "visible": $("input[data-name='fromDate']").is(":checked"),
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
//             "visible": $("input[data-name='fromDate']").is(":checked"),
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
//           "visible": $("input[data-name='userRole']").is(":checked"),
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
//               "visible": $("input[data-name='gender']").is(":checked"),
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
//             "visible": $("input[data-name='shift']").is(":checked"),
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
//         "visible": $("input[data-name='deptName']").is(":checked"),
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
//             "visible": $("input[data-name='rate']").is(":checked"),
            "render": function ( data, type, opt, meta ) {
          	  var result = "";
	    	  		   if(opt.rate != null && opt.rate !='' && opt.rate != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.currency+'/'+opt.rate+'/'+opt.ratePer+'</span></div></td>';
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
}

$("#associateJoinedListDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getAssociatesTerminatedList();
	   }
	});
</script>
 
 
 <script type="text/javascript">
       function getAssociatesJoinedList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency=$("#reportTypeId").val();
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
        	        var url = sourceleadUrl+"/associateJoinedReport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&type=applicantReport";
        		    prepareOfferList(url);
    	        }
    	    }else{
    	    	 document.getElementById("reportsStartDate").style.display="none";
    			 document.getElementById("reportsEndDate").style.display="none";
    	        var url = sourceleadUrl+"/associateJoinedReport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&type=applicantReport";
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
function offerpagination(url){
	var colIndex = 0;
    $('#offerpagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "empID" },
            { "data": "empName"},
            { "data": "gender"},
            { "data": "empType"},
            { "data": "Department"},
            { "data": "Role"},
            { "data": "Rate"},
            { "data": "DateOfJoin"},
            { "data": "manager"},
            { "data": "shift"},
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empID",
	    	  	"sortable": true,
	    	  	"visible": $("input[data-name='empId']").is(":checked"),
	    	  	"render": function(data, type, opt, meta) {
	    	  		 $("#noApplicantJoinedList").val(1); 
	    	  		var result = "";
	    	  		 if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
               		  result = result + '<td><div><span title='+opt.empId+'>'+opt.empId+'</span></div></td>';
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
	          },{
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
          } ,{
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
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getAssociatesJoinedList();
    	   }
    	});
}

</script>
  
<script>
function viewInterviewListResumes(resumeUUID,title){
	if(resumeUUID != null && resumeUUID != ""){
		window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
	}  
}
</script>

<script>
function viewOffersListAttachment(offerId,title){
	if(offerId != null && offerId != ""){
		window.open(sourceleadUrl+'/viewOffersListAttachment/'+title+'?offerId='+offerId,title,"height=600,width=600");
	}  
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
   
   <div class="advancedsearch-mask"></div>
          <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="invoiceFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeApplicantFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-3 hidden">
						<div class="row">
						<!-- <div class="col-md-12 mb">
								<label><strong>Search for</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="searchTypeFilter">
									     <li class="radioIput positionRelative" ><input  type="radio" name="searchType"  value ="MY" ><label for="Yes">My Jobs</label></li>
										 <li class="radioIput positionRelative" ><input  type="radio" name="searchType" value ="ALL" checked ><label for="No">All Jobs</label></li>
				                    </ul>
								 </div>
						   </div> -->
						  <%--   <div class="col-md-12 mb">
								<label><strong>Duration</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="durationFilter">
									<c:if test="${tab == 'today'}">
									<li class="radioIput positionRelative" ><input type="radio"  value="today" checked  name="customDates" checked onclick="showCustomFields(this);" ><label for="applicant">Today</label></li>
									</c:if>
									<c:if test="${tab != 'today'}">
									<li class="radioIput positionRelative" ><input type="radio"  value="today"  name="customDates" checked onclick="showCustomFields(this);" ><label for="applicant">Today</label></li>
									</c:if>
									<li class="radioIput positionRelative" ><input type="radio"  value="Last_7_days"   name="customDates" onclick="showCustomFields(this);" ><label for="applicant">Last 7 days</label></li>
	                                <li class="radioIput positionRelative" ><input type="radio"  value="Last_15_days" name="customDates" onclick="showCustomFields(this);"><label for="applicant">Last 15 days</label></li>
	                                <c:if test="${tab == 'previous'}">
	                                <li class="radioIput positionRelative" ><input type="radio"  value="1_month" name="customDates" checked onclick="showCustomFields(this);"><label for="applicant">1 month</label></li>
	                                </c:if>
	                                <c:if test="${tab != 'previous'}">
	                                <li class="radioIput positionRelative" ><input type="radio"  value="1_month" name="customDates" onclick="showCustomFields(this);"><label for="applicant">1 month</label></li>
	                                </c:if>
	                                <li class="radioIput positionRelative" ><input type="radio" value="3_months" name="customDates" onclick="showCustomFields(this);"><label for="applicant">3 months</label></li>
   	                                <li class="radioIput positionRelative" ><input type="radio" value="Last_Year" name="customDates" onclick="showCustomFields(this);"><label for="applicant">Last Year</label></li>
   	                                <li class="radioIput positionRelative" ><input type="radio" value="Custom" name="customDates" onclick="showCustomFields(this);"><label for="applicant">Custom</label></li>
	                                
			                        </ul>
							      </div>
						     </div> --%> 
						  </div>
						  <div class="clearfix pull-left" id="custom-selects" style="display: none; width: 346px;" >
							<div class="pull-left">
							<div class="pull-left mr5" style="width: 140px" id="custFromDateDiv">
							<input value="" name="Date" id="custFromDate" placeholder="From" class="form-control calendar input-sm margin-top0" type="text">
							</div>
							<div class="pull-left mr5" style="width: 140px" id="custToDateDiv">
							<input value="" name="Date" id="custToDate" placeholder="To" class="form-control calendar input-sm margin-top0" type="text">
							</div>
							</div>
					    </div>
						</div>
						
						 
						<div class="col-md-3 hidden">
						<div class="row">
						  <!--   <div class="col-md-12 mb">
								<label><strong>Employment Type</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="empFilter">
									<li class="checkBox positionRelative" ><input type="checkbox" value="C2C" name="employmentType"><label for="applicant">C2C</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="W-2" name="employmentType"><label for="applicant">W-2</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="C2H" name="employmentType"><label for="applicant">C2H</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  value="Contract" name="employmentType"><label for="applicant">Contract</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="Part Time" name="employmentType"><label for="applicant">Part Time</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="Full Time" name="employmentType"><label for="applicant">Full Time</label></li>
			                        </ul>
							      </div>
						     </div> -->
						 <!--     <div class="col-md-12 mb">
								<label><strong>Incentivized</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="incentivizedFilter">
									     <li class="checkBox positionRelative" ><input  type="checkbox" name="incentivizedType"  value ="Y" ><label for="Yes">Yes</label></li>
										 <li class="checkBox positionRelative" ><input  type="checkbox" name="incentivizedType" value ="N" ><label for="No">No</label></li>
				                    </ul>
								 </div>
						   </div>  -->
						  </div>
						</div>
	                 
						<div class="col-md-3 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
																<input type="text" id="myTeamsInput" onkeyup="myTeamsFunction()" placeholder="Teams" title="Search teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
									<ul class="list-unstyled positionRelative filterSearch mt5" id="myTeamsListUL">
			                        </ul>
							 </div> 
						</div>
						
						<div class="col-md-3 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myClientInput" onkeyup="myClientFunction()" placeholder="Clients" title="Type in a client">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClinetListUL">
								</ul>
							 </div> 
						</div>
					  </div>
					  
					  
					  
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="applicant_search_requiremet" onclick="closeApplicantFilterPop();getAssociatesJoinedList();" style="display: none" class="btn btn-sm btn-success mr" />
							<input type="button" value="Apply" id="recruiter_search_requiremet" onclick="closeApplicantFilterPop();getAssociatesTerminatedList();" style="display: inline-block" class="btn btn-sm btn-success mr" />
							<input type="button" value="Reset" id="clear_search" onclick="resetApplicantFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>

<script>
function getClientList(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getClientList?${_csrf.parameterName}=${_csrf.token}",
		success : function(data) {
			var result = "";
			  for (var i=0; i<data.length; i++)
				{
				var report = data[i];
				result = result + '<li class="checkBox positionRelative">';
				result = result + '<input  type="checkbox" name="clientNameById" value ='+report.orgRelId+'><label>'+report.clientName+'</label>';
				result = result + '</li>';
				}
			  $("#myClinetListUL").empty();
			  $("#myClinetListUL").append(result);

		}
	});
}
function myClientFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myClientInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myClinetListUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

function getTeamsList(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getTeamsListForLoginUser?${_csrf.parameterName}=${_csrf.token}",
		success : function(data) {
			var result = "";
			  for (var i=0; i<data.length; i++)
				{
					var report = data[i];
					result = result + '<li class="checkBox positionRelative">';
					result = result + '<input  type="checkbox" name="teamNameById" value ='+report.id+'><label>'+report.name+'</label>';
					result = result + '</li>';
				}
			  $("#myTeamsListUL").empty();
			  $("#myTeamsListUL").append(result);
		}
	});
}

function myTeamsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTeamsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTeamsListUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
</script>


<!-- =============== Pdf and  Excel Export scripts writen by Laxman =================== -->
<script>
function projectExport(type){
	if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	 var sourceleadUrl ='${sourceleadUrl}';
	 var frequency=$("#reportTypeId").val();
	 var noApplicantJoinedList= $("#noApplicantJoinedList").val();
	   var tab = '${tab}';
    	    var fromDate='';
    	    var toDate='';
		var searchValue = $("#dataTableSearch").val();
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
   	    		 window.location.href = sourceleadUrl+"/applicantsJoinedListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&exportType="+exportType+"&type=applicantReport";
    	        }
    	    }else{
      	        if(noApplicantJoinedList !=	 0){
      	        	document.getElementById("reportsStartDate").style.display="none";
       			 document.getElementById("reportsEndDate").style.display="none";
      	 		 window.location.href = sourceleadUrl+"/applicantsJoinedListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&exportType="+exportType+"&type=applicantReport";
      	        }
      	        else{
      	        	var result = "";
      				result = result +"	<div class='project_inn_mask'></div>";
      				result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
      				result = result +"		<p>Applicants List is Empty</p>";
      				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
      				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
      				result = result +"	</div>";
      				$("#emptyPopup").append(result);
      				$("#candidate_user_pop").slideDown(200);
      				$(".project_inn_mask").fadeIn(200);
      	        }
    	    	 
    	    } 
// 		 window.location.href = sourceleadUrl+"/applicantsJoinedListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&exportType="+exportType;
	     
}
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
			$("#associateJoined").hide();
			$("#associateTerminated").show();
			

			$("#applicantJoinedDiv").hide();
			$("#associateTerminatedDiv").show();
			$("#associateJoinSearchId").hide();
			$("#associateTeminateSearchId").show();
		}else if(optval === "associateJoined"){
			$("#recruiter_search_requiremet").hide();
			$("#applicant_search_requiremet").show();
			$("#recurterExportPdf").hide();
			$("#recurterExportExcel").hide();
			$("#applicantExportPdf").show();
			$("#applicantExportExcel").show();
			
			$("#associateJoinSearchId").show();
			$("#associateTeminateSearchId").hide();
			
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
<script>
function exportToPdf(){
	var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#associateJoinedListDataTableSearch").val();
	 var timePeriod = $("#reportTypeId").val();
	 var  noRecruiterApplicantJoinedList=$("#noRecruiterApplicantJoinedList").val(); 
	var exportType = "pdf";
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
	$('input:checkbox[name=orgTeams]:checked').each(function() 
		    {
		orgTeams.push($(this).val());
    });
	 var orgTeams = [];
		$('input:checkbox[name=teamNameById]:checked').each(function() 
			    {
			orgTeams.push($(this).val());
	     });
		var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).val());
		});
	if(noRecruiterApplicantJoinedList != 0){
		window.location.href = sourceleadUrl + "/exportAllApplicantJoinedList?searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&orgTeams="+orgTeams+"&clients="+clients+"&exportType="+exportType;

	}else{
	  	var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Applicants List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>
<script>
function exportUserData(){
	var sourceleadUrl ='${sourceleadUrl}';
	var searchValue = $("#associateJoinedListDataTableSearch").val();
	 var timePeriod = $("#reportTypeId").val();
	 var  noRecruiterApplicantJoinedList=$("#noRecruiterApplicantJoinedList").val();
	var exportType = "xls";
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
	$('input:checkbox[name=orgTeams]:checked').each(function() 
		    {
		orgTeams.push($(this).val());
    });
	 var orgTeams = [];
		$('input:checkbox[name=teamNameById]:checked').each(function() 
			    {
			orgTeams.push($(this).val());
	     });
		var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).val());
		});
	if(noRecruiterApplicantJoinedList != 0){
		window.location.href = sourceleadUrl + "/exportAllApplicantJoinedList?searchValue="+searchValue+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&orgTeams="+orgTeams+"&clients="+clients+"&exportType="+exportType;

	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Applicants List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
		result = result +"	</div>";
		$("#emptyPopup").append(result);
		$("#candidate_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>
<script type="text/javascript">

	function columPop(){
		$(".colPop").show();
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
		
	function columHide(){
		hideColumns=[]; // reset the array object.
		$(".toggle-vis").each(function() {
			columClose();// 
		   var chkdVal= $(this).prop('checked');
// 		alert("chkdVal "+chkdVal);
		   var column = table.column($(this).attr('data-column'));
// 		   alert("column "+column);
			  if(chkdVal == true){
// 				  alert("1");
			         column.visible(true);
			    }
			    else{
// 			    	 alert("2");
			      column.visible(false);
			      hideColumns.push($(this).attr('data-column')); // used for hiding the columns when user searched the data.
			  }
	    });
		$(".colPop").hide();
	   
	}
	function checkAllColumns(obj){
		var id = obj.id;
		var chkdVal= $('.toggle-vis:input:checkbox').prop('checked');
		if(chkdVal == true){
		if(id =='columnsPickerCheckAll'){
			$('.toggle-vis:input:checkbox').prop('checked',true);
		}
		}
		else{
			if(id =='columnsPickerCheckAll'){
				$('.toggle-vis:input:checkbox').prop('checked',false);
			}
		} 
			
	}
	</script>
	<script>
	$(document).on( 'init.dt.dth', function (e, settings, json) {
    table = new $.fn.dataTable.Api( settings );
    var body = $( table.table().body() );
//     if ( $( table.table().node() ).hasClass( 'searchHighlight' ) || settings.oInit.searchHighlight ||  $.fn.dataTable.defaults.searchHighlight ) {
	
// 	}
} );
	</script>
<jsp:include page="../masterFooter.jsp" />

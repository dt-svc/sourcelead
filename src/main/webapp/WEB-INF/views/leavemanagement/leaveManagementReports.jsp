<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/amcharts.js"></script>
<script src="/resources/js/serial.js"></script>
<script src="/resources/js/pie.js"></script>

 <%-- <script src="resources/js/pie.js?staticTime=${staticTime}"></script> 
	 <script src="resources/js/amcharts.js?staticTime=${staticTime}"></script> 
	 <script src="resources/js/serial.js?staticTime=${staticTime}"></script> 
	 <script src="resources/reports/export.min.js?staticTime=${staticTime}"></script> 
	 <script src="resources/js/themes/light.js?staticTime=${staticTime}"></script> 

<jsp:include page="topLeaveTakers.jsp" />
<script src="resources/js/pie.js?staticTime=${staticTime}"></script>
<script src="resources/js/amcharts.js?staticTime=${staticTime}"></script> --%>
<script type="text/javascript">
		 $(document).ready(function(){
			 getHolidaysList();
			 getEmployeeLeavesList();
			 getPieChartBasedOnUser();
			 
			 	getEmployeeTotalCounts();
			 	getSelfTotalCounts();
			
			});
</script>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<script type="text/javascript">
//script for upload file
document.getElementById("uploadBtn").onchange = function () {
	document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();
}
</script>

<style type="text/css">
.timeBalance-scroll .col-md-2 {
border: 1px solid #ccc; padding-top:15px; margin:0 15px 15px; text-transform:capitalize;
background-color: #fcfcfc;
}
</style>


<input type="hidden" id="leaveEmptyMessage" name="leaveEmptyMessage" />
<div id="popUp"></div>
<div id="loading"></div>
 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Management</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Leave Management</div>
    </section>
    
    <div class="project_wrap">
      <div class="clients_side_wrap_inn no_pad">
        <div class="project_side_wrap_inn_tabs" id="new-id">
        
         <sec:authorize access="hasRole('ROLE_APPLY_LEAVE')"> 
             <div class="pull-right tblminwidth-125 paddingTop mr">
             <div class="success_pop" id="msgDiv" style="display: none;"></div>
              <form:form id="upload_leave_excel_form" name="upload_leave_excel_form" enctype="multipart/form-data">
                 <div title="Import" class="positionRelartive pull-left zIndex mr">
                 			<sec:authorize access="hasRole('ROLE_IMPORT_BALANCE_LEAVES')">
					        	<a onclick="leaveImport();"><i class="glyphicon glyphicon-import expIcon mt5"></i>&nbsp;</a> 
					 		 </sec:authorize>      	
						        	<div class="dowload_pop">
					        			<div class="modal-content">
						        			<div class=modal-header>
							        			 <button type="button" class="close" data-dismiss="modal" onClick="closeImportFile();">&times;</button>
						          				<h4 class="modal-title">Leave Import</h4>
						        			</div>
						        			<div class="modalBody">
						        				<div class="row">
						        					<div class="col-md-12 mb5 clearfix">
						        					<a href="<c:url value='${SourceLeadWeb_pmsUrl}/downloadLeaveTemplate'/>" class="text-primary">
						        						Download Template :
														<i class="ml fa fa-download"></i>
														</a>
						        					</div>
						        					<div class="clearfix"></div>
						        					
						        					
						        					<div class="col-md-12">
						        					<div class="uploadTempalte" id="validation">
						        					<input type="file" class="margin-top0" id="leaveInputFileId" name="file" data-validation="required validate_xls_importFile" field-name="File">
						        					</div>
						        					</div>
						        				</div>
						        			</div>
						        			<div class="clearfix modalFooter">
						        				<div class="pull-right">
						        						<button type="button" class="btn btn-success btnSuccess btn-sm mr" onclick="importLeaves();">Import</button>
						        						<button type="button" class="btn btn-default btn-sm mr" title="Reset" onclick="resetUpload();">Reset</button>
						        				</div>
						        			</div>
					        			</div>
						        	</div>
						        </div>
						        </form:form>
			        <div class="pull-left">
		                 <a href="/pms/applyLeave" title="Apply Leave" class="btn btn-primary btn-sm zindex">Apply Leave</a>
		            </div>
             </div>
         </sec:authorize>  
          <ul class="project-tabs-menu projecttabs-custom">
            <li class="current"><a href="#tab-1">Self</a></li>
            <sec:authorize access="hasAnyRole(\'ROLE_APPROVE_LEAVE\')">
            	<li><a href="#tab-2" onclick="getEmployeeLeavesList();getTopLeaveTakers();getLeaveTakersBarChat();">Employees</a></li>
            </sec:authorize>
            <div class="clearfix"></div>
          </ul>
           
          <div class="project_tabs projecttabs-custom"> 
            
            <!-- Details Tab -->
            <div id="tab-1" class="project_tabs_inn" style="display: block">
              <div class="clearfix">
                <div class="row">
                
                
                <!--  New Div for counts -->
                <div class="clearfix">
                	
                	<div id="self" class="clearfix">
              <div class="col-md-4">
                        <label class="pull-left mr">Balance Leaves</label>
                        <div class="color_white" title="Balance Leaves"><span class="label label-info pull-left" id="selfBalancedCounts"></span></div>
              </div>
              <div class="clearfix"></div>
              <div class="db-widget-sm clearfix">
                <div class="width100percnt clearfix">
                  <div id="empselfLeaves" style="width: 60%; height: 100px;" class="amchartAnchor-Hide pull-left hidden"> </div>
                    <div class="clearfix"></div>
              <div class="mt5 clearfix">
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon text-muted" style="width:30%;"> <i class="fa fa-clock-o fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                    <p class="info-box-number"><span id="selfPendingCounts"></span></p>
                    <p class="info-box-text text-muted mt5" id="selfpend"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon  text-muted" style="width:30%;"> <i class="fa fa-check-circle-o fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                   <p class="info-box-number"><span id="selfApprovedCounts"></span></p>
                   <p class="info-box-text text-muted mt5" id="selfapp"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon text-muted" style="width:30%;"> <i class="fa fa-times fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                  <p class="info-box-number"><span id="selfRejectedCounts"></span></p>
                  <p class="info-box-text text-muted mt5" id="selfrej"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon text-muted" style="width:30%;"> <i class="fa fa-ban fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                  <p class="info-box-number"><span id="selfCancelledCounts"></span></p>
                  <p class="info-box-text text-muted mt5" id="selfcanc"></p>
                  </div>
                </div>
              </div>
              
              </div>
            
                <ul class="list-unstyled  clearfix hidden">
                  
                </ul>
                    
                </div>
              </div>
              
              <div class="col-xs-12 col-sm-4 mb hidden">
                <div id="selfApproved" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="leavetype-approvedcount.html" id="approved" title="Approved">20</a> </div>
              </div>
              <div class="col-xs-12 col-sm-4 mb hidden">
                <div id="selfRejected" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="leavetype-rejectedcount.html" id="rejected" title="Rejected">10</a> </div>
              </div>
            </div>
            </div>
            <!--  New Div ends for counts -->
            
                  <div class="clearfix"></div>
                  
                  <div class="col-md-12">
                    <h4 class="leaves-heading">Leave Balance</h4>
                    <div class="time-balance">
                    <div id="pichartLeaveTypeIdDiv" class="timeBalance-scroll clearfix row"></div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                    <div class="col-md-12 mt10"><hr></div>
                  <div class="col-md-10 col-md-offset-1 listof-holidays datatable_filter">
                        <div class="row">
                            <div class="col-md-5"><strong class="leaves-heading">List of Holidays</strong></div>
                            <div class="col-md-3 noPad dropdown-select">
                                 <select class="form-control" id="reportYear" name="reportYear" onChange="getHolidaysList(this);">
                                    <option value="">--Select Year--</option>
                    			<c:forEach var="year" items="${yearsList}">
	                    			<option value="${year}">${year}</option>
                     			</c:forEach> 
                                </select> 
                            </div>
                            <div class="col-md-4 noPad">&nbsp</div>
                            </div>
                            <div class="clients_wrap_table datatable_filter"></div>
                            <div id="holidayListDiv"></div>
                        
                    </div>
                </div>
              </div>
              <div class="clearfix"></div>
              
            </div>
            <div id="tab-2"  class="project_tabs_inn" style="display: none">
            	<div class="mask_time"></div> 
                <div class="row clearfix">
                
                <!--  New Div for counts -->
                <div class="col-md-12 clearfix mb">
                
                <div class="db-widget-sm clearfix">
                <div class="width100percnt clearfix">
                <div class="clearfix"></div>
                <div class="mt5 clearfix row">
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon text-muted" style="width:30%;"> <i class="fa fa-clock-o fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                    <p class="info-box-number"><span id="employeeRequestedCounts"></span></p>
                    <p class="info-box-text text-muted mt5" id="empReq"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon text-muted" style="width:30%;"> <i class="fa fa-check-circle-o fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                    <p class="info-box-number"><span id="employeeApprovedCounts"></span></p>
                    <p class="info-box-text text-muted mt5" id="empApp"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon text-muted" style="width:30%;"> <i class="fa fa-times fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                    <p class="info-box-number"><span id="employeeRejectedCounts"></span></p>
                    <p class="info-box-text text-muted mt5" id="empRej"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="lm-portlet"> <span class="info-box-icon text-muted" style="width:30%;"> <i class="fa fa-ban fa-2x" aria-hidden="true"></i> </span>
                  <div class="info-box-content">
                    <p class="info-box-number"><span id="employeeCancelledCounts"></span></p>
                    <p class="info-box-text text-muted mt5" id="empCanc"></p>
                  </div>
                </div>
              </div>
              
              </div>
                
                
                
                  <!-- <div id="employeeLeaves" style="width: 60%; height: 105px;" class="amchartAnchor-Hide pull-left"> </div> -->
                  <div style="width: 40%; height: 105px;" class="countList pull-left hidden">
                <ul class="list-unstyled list-group clearfix mb5">
                    <li class="clearfix">
                        <label class="pull-left">Requested Leaves</label>
                        <a href="/pms/employeeRequestedLeaves" class="color_white" id="empReq" title="Requested Leaves"><span class="label label-primary pull-right" id="employeeRequestedCounts"></span>></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Approved Leaves</label>
                        <a href="/pms/employeeApprovedLeaves" class="color_white" id="empApp" title="Approved Leaves"><span class="label label-success pull-right" id="employeeApprovedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Rejected Leaves</label>
                        <a href="/pms/employeeRejectedLeaves" class="color_white" id="empRej" title="Rejected Leaves"><span class="label label-danger pull-right" id="employeeRejectedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Cancelled Leaves</label>
                        <a href="/pms/employeeCancelLeaves" class="color_white" id="empCanc" title="Cancelled Leaves"><span class="label label-warning pull-right" id="employeeCancelledCounts"></span></a>
                    </li>
                </ul>
                      
                    </div>
                </div>
              </div> 
                
                
                </div>
                <div class="cleaarfix"></div>
                
                
                    <div class="clearfix mb5">
                        <div class="col-md-6">
                            <h4 class="leaves-heading"><span class="pull-left">Employee Leaves</span></h4>
                        </div>
                        <div class="clearfix col-md-6 pull-right">
                        
							<ul class="col-md-5 empUlAli mb0 mt5">
	                            <li id="filterDepartmentPop"><i class="fa fa-filter"></i> <span>Department</span><i class="fa fa-caret-down"></i>
	                            <div class="statusFiler-1">
	                            <ul>
		                            <c:forEach var="report" items="${departmentList}">
		                            	<li class="clearfix mb5"><input type="checkbox" name="department" value="${report.id}" onclick="getEmployeeLeavesList();" class="pull-left"><label class="tbldata-dotdot-vsm pull-left mt5" title="${report.departmentName}">${report.departmentName}</label></li>
		                            </c:forEach>
	                            </ul>
	                            </div>
	                            </li>
                            </ul> 
                        	
                        	<div class=" col-md-7 noPad"> 
                            <div class="form-inline leaves-heading">
                                <div class="pull-right">
                                <div class="form-group">
                            <input type="text" class="calendar form-control input-sm" id="leavesCalendar">
                                    </div>
                                <div class="form-group">
                                <button type="button" class="btn btn-primary btn-sm" title="Clear" onClick="clearLeaveCalender();">Clear</button>
<!--                             <button type="button" class="btn btn-primary btn-sm" title="Custom" onClick="customizeEmployee();">Custom</button> -->
                                </div>
                                    </div>
                                </div>
                                <div class="clearfix customizeEmployee" style="display: none;">
                                <div class="clearfix">
                                <div class="col-md-5 noPad mR5">
                                <input name="Date" placeholder="From" class="form-control calendar input-sm" id="empCustFromDate" type="text">
                                </div>
                                <div class="col-md-5 noPad mR5">
                                <input type="text" name="Date" placeholder="To" id="empCustToDate" class="form-control calendar input-sm">
                                </div>
                                <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
                                </div>
                                </div>
                                </div>
                        </div>
                    </div>
                    <div class="clearfix ptagmrng-none">
                    <div class="col-md-12" id="employeeEmptylist" style="display:none">
		        		<div class="media media-custom mb dataTables_empty"></div>
		            </div>
                    	<div id="employeelistDiv"></div>
                      </div>
                </div>
                <div class="clearfix"></div>
               <!--  <div class="row">
                  <div class="col-md-6">
                 
                 <div class="clearfix mt5">
                   <h4 class="heading col-md-4">Top leave Takers</h4>
                    <div class="timesheet_head clearfix col-md-8">
                    <ul class="list-unstyled noPad pull-left">
                    	<li title="Export" class="mrPOP" id="mypop"><i class="glyphicon glyphicon-export"></i>
					        	<div id="exportPop" class="ExpPop">
					              <ul>
					                <li onclick="leavePdfExport();"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>
					                <li onclick="leaveExcelExport();"><i class="fa fa-file-excel-o text-primary"></i>&nbsp;&nbsp;Excel</li>
					              </ul>
					            </div>
					          </li>  
                    </ul>
                          <div class="dropdown-select">
                            <div class="form-inline pull-right">
                            <div class="form-group">
                            <select class="form-control pull-right" name="topLeaveTakersreportYear" id="topLeaveTakersreportYear" onchange="getTopLeaveTakers();">
                              <option value="">Year</option>
                              <option value="2017">2017</option>
                              <option value="2018">2018</option>
                              <option value="2019">2019</option>
                              <option value="2020">2020</option>
                              <option value="2021">2021</option>
                            </select>
    </div>
    <div class="form-group">
    <select class="form-control pull-right" name="reportMonth" id="reportMonth" onchange="getTopLeaveTakers();">
                              <option value="">Month</option>
                              <option value="0">January</option>
                              <option value="1">February</option>
                              <option value="2">March</option>
                              <option value="3">April</option>
                              <option value="4">May</option>
                              <option value="5">June</option>
                              <option value="6">July</option>
                              <option value="7">August</option>
                              <option value="8">September</option>
                              <option value="9">October</option>
                              <option value="10">November</option>
                              <option value="11">December</option>
                            </select>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" id="customLeaves" onClick="showCustom()">Custom</button>
  </div>
  <div class="clearfix pull-lefet col-md-6 paddingRight customLeaves" style="display: none;">
  <div id="customDateDivId"></div>
  </div>
  
Top Leave Takers Custom Date
    
Top Leave Takers Custom Date    
                        </div>
                        </div>
                 </div>
                     
                      <div class="clearfix project_wrap_table mt5 datatable_filter">
                       <div id="topLeaveTakersDiv"></div>
                    </div>
                  </div>

                  <div class="col-md-6">
                    <div class="clearfix mt5">
                   <h4 class="heading col-md-4">Leaves Report</h4>
                    <div class="timesheet_head clearfix col-md-8">
                          <div class="dropdown-select">
                            <div class="form-inline pull-right">
                            <div class="form-group">
                            <select class="form-control pull-right" name="barChartYear" id="barChartYear" onchange="getLeaveTakersBarChat();">
                              <option value="">Year</option>
                              <option value="2017">2017</option>
                              <option value="2018">2018</option>
                              <option value="2019">2019</option>
                              <option value="2020">2020</option>
                              <option value="2021">2021</option>
                              <option value="2022">2022</option>
                            </select>
                            </div>
                            <div class="form-group">
                            <select class="form-control pull-right" name="barCharMonth" id="barCharMonth" onchange="getLeaveTakersBarChat();">
                              <option value="">Month</option>
                              <option value="0">January</option>
                              <option value="1">February</option>
                              <option value="2">March</option>
                              <option value="3">April</option>
                              <option value="4">May</option>
                              <option value="5">June</option>
                              <option value="6">July</option>
                              <option value="7">August</option>
                              <option value="8">September</option>
                              <option value="9">October</option>
                              <option value="10">November</option>
                              <option value="11">December</option>
                            </select>
    </div>
    <button type="submit" class="btn btn-primary btn-sm" id="showLeaveReport" onClick="showLeaveReport()">Custom</button>
  </div>
Top Leave Takers Custom Date
    <div class="clearfix pull-lefet col-md-6 paddingRight showLeaveReport" style="display: none;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value=""  placeholder="From" class="form-control calendar input-sm" type="text" id="barChartFromDate" name="barChartFromDate">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value=""  placeholder="To" class="form-control calendar input-sm" type="text" id="barChartToDate" name="barChartToDate">
    </div>
    <button type="button" class="btn btn-primary btn-sm" onclick="getLeaveTakersBarChat();"><i class="fa fa-search"></i></button>
    </div>
    </div>
Top Leave Takers Custom Date    
                        </div>
                        </div>
                 </div>
                      <div id="empLeavereport" class="amchartAnchor-Hide mt5" style="width: 100%; height: 250px;"></div>
                  <div id="empLeavereportEmptyDiv" class="dataTables_empty noRecBor" style="display:none"></div>
                    </div>
                    </div> -->
              
              </div>
          </div>
        </div>
      </div>
      
      <!-- Project --> 
    </div>
  </section>


<script type="text/javascript">
    //Chosen script
    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
});
</script> 

<script>
function clearLeaveCalender(){
	$("#leavesCalendar").val('');
	getEmployeeLeavesList();
}
</script>

<script>
$(document).ready(function() {
$("#tab-1").show();
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
    // jQuery Ui Calendar
    $(function() {
    $( "#scheduleCalendar" ).datepicker({
	  changeMonth: true,
      changeYear: true,
        onSelect : function(selected) {
            var tet = $("<div></div>").text("Today No Events").appendTo("#ui-datepicker-div")
        }
    });
  });
</script> 


<script>
function getHolidaysList(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	var fromType = "dashboard";
	var reportYear = "";
	if($("#reportYear").val() !=null && $("#reportYear").val() != 'undefined' && $("#reportYear").val() != ''){
		reportYear = $("#reportYear").val();
	}
	$.get(pmsUrl+"/getHolidaysList?orgLocationId="+orgLocationId+"&reportYear="+reportYear+"&fromType="+fromType, function(response) {
		if (response.response === "AWKWARD") {
		    location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
		var result = "";				        	
				result = result + '<table id="viewHolidaysTable" class="display table table-bordered" cellspacing="0" width="100%" >';
				result = result + '<thead>';
				result = result + '<tr>';
				result = result +'<th>S.No</th>';
				result = result +'<th>Name</th>';
				result = result +'<th>Date</th>';
				result = result + '<th>Day of the Week</th>';
				result = result + '</tr>';
				result = result + '</thead>	';
				result = result + '<tbody>';
				if(response !=null && response !=''){
					var j = 1;
					 for (var i=0; i<response.data.length; i++)
						{
			        		 var opt = response.data[i];
			        		 if(opt.isFuture == 'Y'){
			        			 result = result + ' <tr class="text-success">';
			        		 }else{
			        			 result = result + ' <tr class="text-danger">';
			        		 }
			        		 
			        		 result = result + ' <td>'+j+'</td>';
			        		 if(opt.holidayName !=null && opt.holidayName !='' && opt.holidayName !='undefined'){
			        			 result = result + '<td title="'+opt.holidayName+'">'+opt.holidayName+'</td>'; 
			        		 }else{
			        			 result = result + '<td>---</td>';
			        		 }
			        		 result = result + ' <td title="'+opt.fromDate+' - '+opt.toDate+'">'+opt.fromDate+' - '+opt.toDate+'</td>';
			        		 result = result + ' <td title="'+opt.dayOfTheWeek+'">'+opt.dayOfTheWeek+'</td>';
			        		 result = result + '  </tr>';
			        		 j++;
						}
				}
	        	
				result = result + '  </tbody>';
				result = result + ' </table>';
				$("#holidayListDiv").empty();
				$("#holidayListDiv").append(result);
				viewHolidaysTable();
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(pmsUrl);
		} else {
		    showError("Operation failed, please try again");
		}
		}); 
}
</script>

<script>
function viewHolidaysTable(){ 
    $('#viewHolidaysTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"columnDefs": [ { orderable: false, targets: [2]}],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
}
</script>

<script>
$("#filterDepartmentPop").click(function(){
	$(".statusFiler-1").toggle();
	//$(".statusFiler-2, .statusFiler-3").hide();
});

$("html").click(function() {
    $(".statusFiler-1").hide();
});	
$("#filterDepartmentPop, .statusFiler-1").click(function(e) {
    e.stopPropagation();
});	
</script>

<script>
function getEmployeeLeavesList(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	var date = $("#leavesCalendar").val();
	var department = [];
	$('input:checkbox[name=department]:checked').each(function() 
    {
		department.push($(this).val());
    });
	$.get(pmsUrl+"/getEmployeeLeavesList?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&department="+department+"&leaveDateStr="+date, function(data) {
		var result = "";	
		 $("#employeelistDiv").empty();
		if(data != '' && data != null){
			$("#employeeEmptylist").hide();
			for (var i=0; i<data.length; i++){
				var opt = data[i];
				result += '<div class="col-md-3">';
		        result += '<div class="media media-custom mb">';
		        result += '    <div class="media-left">';
		        result += '        <div class="empimg-parent">';
		        if(opt.empProfileImageURL != null && opt.empProfileImageURL != '' && opt.empProfileImageURL != 'undefined'){
		        	result += '          <img onerror="predefineProfileImage(this);" src="'+opt.empProfileImageURL+'"  class="">';
		        }else{
		        	result += '          <img onerror="predefineProfileImage(this);" src=""  class="">';
		        }
		        result += '        </div>';
		        result += '    </div>';
		        result += '    <div class="media-body text-left">';
		        if(opt.empName != null && opt.empName != '' && opt.empName != 'undefined'){
		        	result += '      <h5 class="media-heading tbldata-dotdot">'+opt.empName+'</h5>';
		        }
		        
		        if(opt.empType != null && opt.empType != '' && opt.empType != 'undefined'){
		        	result += '      <p class="small text-muted">'+opt.empType+'</p>';
		        }else{
		        	result += '      <p class="small text-muted">NA</p>';
		        }
		        result += '        <ul class="list-unstyled list-inline mrng0 mt5 empLeave-li">';
		        if(opt.isSunday == "Y" && opt.isSunday != null && opt.isSunday != '' && opt.isSunday != 'undefined'){
		        	if((opt.isSundayHalfDay == "Y" && opt.isSundayHalfDay != null && opt.isSundayHalfDay != '' && opt.isSundayHalfDay != 'undefined') || opt.duration == "Half Day"){
		        		result += '            <li title="Day" class="halfday">S</li>';
		        	}else{
		        		result += '            <li title="Day" class="fullday">S</li>';
		        	}
		        }else{
		        	result += '            <li title="Day">S</li>';
		        }
		        if(opt.isMonday == "Y" && opt.isMonday != null && opt.isMonday != '' && opt.isMonday != 'undefined'){
		        	if((opt.isMondayHalfDay == "Y" && opt.isMondayHalfDay != null && opt.isMondayHalfDay != '' && opt.isMondayHalfDay != 'undefined') || opt.duration == "Half Day"){
		        		result += '            <li title="Day" class="halfday">M</li>';
		        	}else{
		        		result += '            <li title="Dy" class="fullday">M</li>';
		        	}
		        }else{
		        	result += '            <li title="Day">M</li>';
		        }
		        
		        if(opt.isTuesday == "Y" && opt.isTuesday != null && opt.isTuesday != '' && opt.isTuesday != 'undefined'){
		        	if((opt.isTuesdayHalfDay == "Y" && opt.isTuesdayHalfDay != null && opt.isTuesdayHalfDay != '' && opt.isTuesdayHalfDay != 'undefined') || opt.duration == "Half Day"){
		        		result += '            <li title="Day" class="halfday">T</li>';
		        	}else{
		        		result += '            <li title="Day" class="fullday">T</li>';
		        	}
		        }else{
		        	result += '            <li title="Day">T</li>';
		        }
		        
		        if(opt.isWednesday == "Y" && opt.isWednesday != null && opt.isWednesday != '' && opt.isWednesday != 'undefined'){
		        	if((opt.isWednesdayHalfDay == "Y" && opt.isWednesdayHalfDay != null && opt.isWednesdayHalfDay != '' && opt.isWednesdayHalfDay != 'undefined') || opt.duration == "Half Day"){
		        		result += '            <li title="Day" class="halfday">W</li>';
		        	}else{
		        		result += '            <li title="Day" class="fullday">W</li>';
		        	}
		        }else{
		        	result += '            <li title="Day">W</li>';
		        }
		        
		        if(opt.isThursday == "Y" && opt.isThursday != null && opt.isThursday != '' && opt.isThursday != 'undefined'){
		        	if((opt.isThursdayHalfDay == "Y" && opt.isThursdayHalfDay != null && opt.isThursdayHalfDay != '' && opt.isThursdayHalfDay != 'undefined') || opt.duration == "Half Day"){
		        		result += '            <li title="Day" class="halfday">T</li>';
		        	}else{
		        		result += '            <li title="Day" class="fullday">T</li>';
		        	}
		        }else{
		        	result += '            <li title="Day">T</li>';
		        }
		        
		        if(opt.isFriday == "Y" && opt.isFriday != null && opt.isFriday != '' && opt.isFriday != 'undefined'){
		        	if((opt.isFridayHalfDay == "Y" && opt.isFridayHalfDay != null && opt.isFridayHalfDay != '' && opt.isFridayHalfDay != 'undefined') || opt.duration == "Half Day"){
		        		result += '            <li title="Day" class="halfday">F</li>';
		        	}else{
		        		result += '            <li title="Day" class="fullday">F</li>';
		        	}
		        }else{
		        	result += '            <li title="Day">F</li>';
		        }
		        
		        if(opt.isSaturday == "Y" && opt.isSaturday != null && opt.isSaturday != '' && opt.isSaturday != 'undefined'){
		        	if((opt.isSaturdayHalfDay == "Y" && opt.isSaturdayHalfDay != null && opt.isSaturdayHalfDay != '' && opt.isSaturdayHalfDay != 'undefined') || opt.duration == "Half Day"){
		        		result += '            <li title="Day" class="halfday">S</li>';
		        	}else{
		        		result += '            <li title="Day" class="fullday">S</li>';
		        	}
		        }else{
		        	result += '            <li title="Day">S</li>';
		        }
		        
		        result += '        </ul>';
		        result += '    </div>';
		        result += '</div>';
		        result += '</div>';
		        $("#employeelistDiv").empty();
		        $("#employeelistDiv").append(result);
			}
		}else{
			$("#employeeEmptylist").show();
		}
		
		
	}); 
	
}
</script>



<script type="text/javascript">
function getLeaveTakersBarChat(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	var barChartYear ="";
	var barCharMonth = "";
	var barChartFromDate = "";
	var barChartToDate = "";
	 if($("#barChartYear").val() !=null && $("#barChartYear").val() != 'undefined' && $("#barChartYear").val() != ''){
		barChartYear = $("#barChartYear").val();
	}
	if($("#barCharMonth").val() !=null && $("#barCharMonth").val() != 'undefined' && $("#barCharMonth").val() != ''){
		barCharMonth = $("#barCharMonth").val();	
	}
	if($("#barChartFromDate").val() !=null && $("#barChartFromDate").val() != 'undefined' && $("#barChartFromDate").val() != ''){
		var barChartFromDate = $("#barChartFromDate").val();	
	}
	if($("#barChartToDate").val() !=null && $("#barChartToDate").val() != 'undefined' && $("#barChartToDate").val() != ''){
		var barChartToDate = $("#barChartToDate").val();	
	} 
	$.ajax({
	   	type: "GET",
	   	url: pmsUrl+"/getLeaveTakersBarChat?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&barChartYear="+barChartYear+"&barCharMonth="+barCharMonth+"&barChartFromDate="+barChartFromDate+"&barChartToDate="+barChartToDate,	    	   
	    success: function(data) {
   			 totalLeaveByEmployeeBarChart(data);
	    }
   });
}
</script>

<script>
function totalLeaveByEmployeeBarChart(data){
	  var chartData = [];
	   for (var i = 0; i < data.length; i++) { 
	 		 var opt = data[i]; 
	         chartData.push({
	        	 category: opt.leaveType,
	        	 leaveType: opt.totalLeaves,
	         	 color: opt.colorCode
	       });
	   } 
	 if(chartData == ""){
		 $("#empLeavereport").hide();
		 $("#empLeavereportEmptyDiv").show();
	 }else{
		 $("#empLeavereport").show();
		 $("#empLeavereportEmptyDiv").hide();
	 }
	AmCharts.makeChart("empLeavereport",
			{
				"type": "serial",
				"categoryField": "category",
				"columnSpacing": 20,
				"columnWidth": 0.5,
				"autoMarginOffset": 20,
				"marginBottom": 5,
				"marginLeft": 0,
				"marginRight": 5,
				"marginTop": 10,
				"minMarginBottom": 0,
				"minMarginLeft": 0,
				"minMarginRight": 0,
				"minMarginTop": 0,
				"startDuration": 1,
				"autoDisplay": true,
				"touchClickDuration": 10,
				"categoryAxis": {
                    "autoRotateAngle": 40,
                    "autoRotateCount": 0,					
					"gridPosition": "start",
					"titleFontSize": 0
				},
				"trendLines": [],
				"graphs": [
					{
						"balloonText": "[[category]] [[value]]",
						"colorField": "color",
						"fillAlphas": 1,
						"id": "AmGraph-1",
						"lineColorField": "color",
						"title": "graph 1",
						"type": "column",
						"valueField": "leaveType"
					}
				],
				"guides": [],
				"valueAxes": [
					{
						"stackType": "regular"
					}
				],
				"allLabels": [],
				"balloon": {
					"offsetY": 4,
					"pointerWidth": 10,
					"verticalPadding": 5
				},
				"titles": [],
				"dataProvider": chartData
			}
		);
}
</script>

<script>
function getPieChartBasedOnUser(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
        type : "GET",
         url: pmsUrl+"/getLeaveTypeReport?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,    
         success :function(data) {
       	  var chartData = [];
       		 var result = "";
       		 if(data.length > 0){
       			for (var i = 0; i < data.length; i++) {
      		 		 var opt = data[i];
      		 		 var numberOfDaysCount = 0;
      		 		 if(opt.totalNoOfDays != null && opt.totalNoOfDays != "" && opt.totalNoOfDays != 'undefined'){
      		 			numberOfDaysCount = opt.totalNoOfDays 
      		 		 }
      		 		result = result +'  <div class="col-md-2 mb">';
      	            result = result +'   <h4 class="text-center tbldata-dotdot-vsm" title="'+opt.name+'">'+opt.name+'</h4>';
      	            //result = result +'   <div id="vacationDonut'+opt.leaveTypeId+'" class="amchartAnchor-Hide" style="width: 100%; height: 125px;"> </div>';
      	            result = result +'   <div class="text-center">';
      	            result = result +'     <h2>';
      	            //result = result +'       <label class="small text-muted">Remaining:</label>';
      	            result = result +'       <label class="clearfix width100percnt mt5">'+numberOfDaysCount.toFixed(2)+' / '+opt.noOfLeavesPerYear+'</label>';
      	            result = result +'     </h2>';
      	            result = result +'   </div>';
      	            result = result +' </div>';
      	            
      	           /*   AmCharts.makeChart("vacationDonut"+opt.leaveTypeId+"", {
      						"type": "pie",
      						"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
      						"innerRadius": "60%",
      						"pieX": "50%",
      						"pieY": "50%",
      						"radius": "48%",
      						"colorField": "color",
      						"colors": [],
      						"labelsEnabled": false,
      						"marginBottom": 0,
      						"marginTop": 0,
      						"titleField": "category",
      						"valueField": "column-1",
      						"allLabels": [],
      						"balloon": {},
      						"titles": [],
      						"dataProvider": [
      							{
      								"category": "Approved",
      								"column-1": opt.approvedCount,
      								"color": "#5cb85c"
      							},
      							{
      								"category": "Pending",
      								"column-1": opt.pendingCount,
      								"color": "#337ab7"
      							},
      							{
      								"category": "Rejected",
      								"column-1": opt.rejectedCount,
      								"color": "#d9534f"
      							},
      							{
      								"category": "Cancelled",
      								"column-1": opt.cancelCount,
      								"color": "#f0ad4e"
      							}
      						]
      					} 
      							 
      					); */
      		   }
       		 }else{
       			result = result + '<div class="dataTables_empty center" ></div>';
       		 }
       	            $("#pichartLeaveTypeIdDiv").empty();
       	            $("#pichartLeaveTypeIdDiv").append(result);
       	  
       	  
        },
         
    }); 
}
</script>

<script>
    /*------My Timeoff Balance Piecharts------*/
    //vacationDonut
        AmCharts.makeChart("vacationDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#337ab7",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Vacation",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
        //sickDonut
        AmCharts.makeChart("sickDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#e53935",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Sick Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
        //casualDonut
                AmCharts.makeChart("casualDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#ffa726",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Casual Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
                        AmCharts.makeChart("otherDonut",
				{
					"type": "pie",
					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
                    //"balloonText": "",
					"innerRadius": "60%",
                    "minRadius":0,
                    "pieY": "50%",
					"pullOutRadius": 0,
					"radius": "45%",
					"startAngle": 0,
					"colors": [
						"#5c6bc0",
						"#ddd"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"allLabels": [],
					"balloon": {
						"fixedPosition": false
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Other Leaves",
							"column-1": "80"
						},
						{
							"category": "Pending Leaves",
							"column-1": "20"
						}
					]
				}
			);
    //Custom buttons
   
    $("html").click(function() {
    	$(".customLeaves").hide();
    })
    $("#customLeaves, .customLeaves, .ui-datepicker").click(function(e) {
    	e.stopPropagation();
    })
    
    function showLeaveReport(){
        $(".showLeaveReport").toggle();
    }
    $("html").click(function() {
        $(".showLeaveReport").hide();
    });	
    $("#showLeaveReport, .showLeaveReport").click(function(e) {
        e.stopPropagation();
    });
    //jQuery UI Calendar
//     $("#leavesCalendar").datepicker();
    </script>
    
		
    <script>
    function customizeEmployee(){
        $(".customizeEmployee").fadeToggle();
    }
    </script>
    
    <script>
    function showCustom(){
     
    	$(".customLeaves").toggle();
        var result = ""
        	result = result + '<form id="customDate-form" name="customDate-form">';
           result = result + '<div class="pull-left">';
           result = result + '<div class="pull-left mr5" style="width: 140px" id="startErr">';
           result = result + '<input value="" name="reportFromDate" id="reportFromDate" placeholder="From" class="form-control calendar input-sm" type="text" data-validation="required" field-name="Start Date">';
           result = result + '</div>';
           result = result + '<div class="pull-left mr5" style="width: 140px" id="endErr">';
           result = result + '<input value="" name="reportEndDate" id="reportEndDate" placeholder="To" class="form-control calendar input-sm" type="text" data-validation="required" field-name="End Date">';
           result = result + '</div>';
           result = result + '<button type="button" class="btn btn-primary btn-sm" onclick="customDatesearchin();"><i class="fa fa-search"></i></button>';
           result = result + '</div>';
           result = result + '</form>';
        
           $("#customDateDivId").empty();
		   $("#customDateDivId").append(result);
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
 		$('#reportFromDate').datepicker({
 			dateFormat : finalDateFormatEndDate,
 			changeMonth: true,
 			changeYear: true, 
 			onSelect : function(selected) {
 			var dt = $(this).datepicker('getDate', '+1d');
 			dt.setDate(dt.getDate());
 			$("#reportEndDate").datepicker("option", "minDate", dt);
 			removeStartDateValidation();
 			}
 		});
 		
 	  $('#reportEndDate').datepicker({
 			dateFormat : finalDateFormatEndDate,
 			changeMonth: true,
 			changeYear: true, 
 			onSelect : function(selected) {
 			var dt = $(this).datepicker('getDate', '-1d');
 			dt.setDate(dt.getDate());
 			$("#reportFromDate").datepicker("option", "maxDate", dt);
 			removeEndDateValidation();
 			}
 		});
    }
    function removeStartDateValidation(){
    	$('#startErr').find('p.jquery_form_error_message').remove();
    }
    function removeEndDateValidation(){
    	$('#endErr').find('p.jquery_form_error_message').remove();
    }
    function customDatesearchin(){
    	if ($('#customDate-form').validate(false, validationSettings)) {
    		getTopLeaveTakers();
    		$(".customLeaves").hide();
    	}else {
			return false;
		}
    }
    $('body').on('blur', '#customDate-form', function() {
		$("#customDate-form").showHelpOnFocus();
		$("#customDate-form").validateOnBlur(false, validationSettings);
	});
    </script>
    
 <script>
	function getTopLeaveTakers() {
		
		var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		var reportYear = "";
		var reportMonth = "";
		var reportFromDate = "";
		if($("#topLeaveTakersreportYear").val() !=null && $("#topLeaveTakersreportYear").val() != 'undefined' && $("#topLeaveTakersreportYear").val() != ''){
			reportYear = $("#topLeaveTakersreportYear").val();
		}
		if($("#reportMonth").val() !=null && $("#reportMonth").val() != 'undefined' && $("#reportMonth").val() != ''){
			reportMonth = $("#reportMonth").val();	
		}
		if($("#reportFromDate").val() !=null && $("#reportFromDate").val() != 'undefined' && $("#reportFromDate").val() != ''){
			var reportFromDate = $("#reportFromDate").val();	
		}
		if($("#reportEndDate").val() !=null && $("#reportEndDate").val() != 'undefined' && $("#reportEndDate").val() != ''){
			var reportEndDate = $("#reportEndDate").val();	
		}

		var searchValue = $("#dataTableSearch").val();
  		searchValue = searchValue ? searchValue : "";
  		var url = pmsUrl+"/getTopLeavetakers?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&searchValue="+searchValue+"&userId="+userId+"&topLeaveTakersreportYear="+reportYear+"&reportMonth="+reportMonth+"&reportFromDate="+reportFromDate+"&reportEndDate="+reportEndDate;
  		prepareTopLeaveTakersList(url); 
	}
	
</script>	

<script>	
	function prepareTopLeaveTakersList(url) {
		$("#leaveEmptyMessage").val(0);
		$("#topLeaveTakersDiv").empty();
		var result = "";
		result = result + "<table id='topLeaveTakersTable' class='display' cellspacing='0' width='100%' >";
		result = result + "<thead>";
		result = result + "  <tr>";
		result = result + "    <th>Employee Name</th>";
		result = result + "    <th>Number Of Days</th>";
		result = result + "    <th>Status</th>";
		result = result + "  </tr>";
		result = result + "</thead>";
		result = result + "<tbody>";
		result = result + " </tbody>";
		result = result + "</table>";
		$("#topLeaveTakersDiv").append(result);
		topLeaveTakersPagination(url);
	}

</script>


<script>
function topLeaveTakersPagination(url){
	var colIndex = 0;
		 $('#topLeaveTakersTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'POST',
	            pages: 10
	        }),
	        "columns": [
	            { "data": "empName" },
	            { "data": "noOfDays"},
	            { "data": "status" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "empName",
		    	  	"render": function(data, type, report, meta) {
		    	  		$("#leaveEmptyMessage").val(1);
		    	  		var result = "";
		    	  		result = result + '<td>';
		    	  		result = result + '<div class="tbldata-dotdot-big" title="">';
		    	  		result = result + '<a href="javascript:void(0)">'+report.empName+'</a>';
		    	  		result = result + '</div>';
		    	  		result = result + '</td>';
		    	  		return  result; 
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "noOfDays",
	            "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    result = result + '<td>';
	            	    result = result + '<div class="tbldata-dotdot-100">'+report.noOfDays+'</div>';
	            	    result = result + '</td>';
		              return result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "status",
	              "render": function ( data, type, report, meta ) {
	            	   var result = "";
	            	   result = result + '<td><div class="tblminwidth-75">'+report.status+'</div></td>';
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
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
	    $("#topLeaveTakersTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	   /*  $("#dataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getClientList();
	    	   }
	    	}); */
}
</script>


<script>
	$(function() {
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
		$('#reportFromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#reportEndDate").datepicker("option", "minDate", dt);
			}
		});
		
	  $('#reportEndDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#reportFromDate").datepicker("option", "maxDate", dt);
			}
		});
	  
	  $('#leavesCalendar').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			getEmployeeLeavesList();
			}
		});
	  
	  $('#empCustFromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#empCustToDate").datepicker("option", "minDate", dt);
			}
		});
		
	  $('#empCustToDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#empCustFromDate").datepicker("option", "maxDate", dt);
			}
		});
		
	  $('#barChartFromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#toDate").datepicker("option", "minDate", dt);
			removeStartValid();
			}
		});
		
	  $('#barChartToDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#fromdate").datepicker("option", "maxDate", dt);
			removeEndValid();
			}
		});
	  
	});
</script>


<script>
$('#mypop').click(function(){
		$("#exportPop").toggle();
 });
 $("html").click(function(){
	$("#exportPop, .ExpPop").hide(200);
 });
 $(".billing_dashboard, .nav-tabs").click(function(){
	$("#exportPop, .ExpPop").hide(200);
 });
 
 $("#exportPop, #mypop, .nav-tabs, .billing_dashboard").click(function(e) {  
     e.stopPropagation();
 });
</script>


<script>
function leavePdfExport(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var reportYear = "";
	var reportMonth = "";
	var reportFromDate = "";
	var reportEndDate = "";
	var emptyExport = $("#leaveEmptyMessage").val();
	
	if($("#topLeaveTakersreportYear").val() !=null && $("#topLeaveTakersreportYear").val() != 'undefined' && $("#topLeaveTakersreportYear").val() != ''){
		reportYear = $("#topLeaveTakersreportYear").val();
	}
	if($("#reportMonth").val() !=null && $("#reportMonth").val() != 'undefined' && $("#reportMonth").val() != ''){
		reportMonth = $("#reportMonth").val();	
	}
	if($("#reportFromDate").val() !=null && $("#reportFromDate").val() != 'undefined' && $("#reportFromDate").val() != ''){
		 reportFromDate = $("#reportFromDate").val();	
	}
	if($("#reportEndDate").val() !=null && $("#reportEndDate").val() != 'undefined' && $("#reportEndDate").val() != ''){
		 reportEndDate = $("#reportEndDate").val();	
	}

	if(emptyExport > 0){
	window.location.href = pmsUrl+"/getLeaveExportList?"+tokenParamName+"="+tokenValue+"&topLeaveTakersreportYear="+reportYear+"&reportMonth="+reportMonth+"&reportFromDate="+reportFromDate+"&reportEndDate="+reportEndDate+"&exportType=PDF";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='empty_leave_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Leaves List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#empty_leave_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}

function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function leaveExcelExport(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var reportYear = "";
	var reportMonth = "";
	var reportFromDate = "";
	var reportEndDate = "";
	var emptyExport = $("#leaveEmptyMessage").val();
	
	if($("#topLeaveTakersreportYear").val() !=null && $("#topLeaveTakersreportYear").val() != 'undefined' && $("#topLeaveTakersreportYear").val() != ''){
		reportYear = $("#topLeaveTakersreportYear").val();
	}
	if($("#reportMonth").val() !=null && $("#reportMonth").val() != 'undefined' && $("#reportMonth").val() != ''){
		reportMonth = $("#reportMonth").val();	
	}
	if($("#reportFromDate").val() !=null && $("#reportFromDate").val() != 'undefined' && $("#reportFromDate").val() != ''){
		 reportFromDate = $("#reportFromDate").val();	
	}
	if($("#reportEndDate").val() !=null && $("#reportEndDate").val() != 'undefined' && $("#reportEndDate").val() != ''){
		 reportEndDate = $("#reportEndDate").val();	
	}
	if(emptyExport > 0){
	window.location.href = pmsUrl+"/getLeaveExportList?"+tokenParamName+"="+tokenValue+"&topLeaveTakersreportYear="+reportYear+"&reportMonth="+reportMonth+"&reportFromDate="+reportFromDate+"&reportEndDate="+reportEndDate+"&exportType=EXCEL";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='empty_leave_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Leaves List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#empty_leave_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
</script>

<script>
function leaveImport(){
	resetUpload();
	$(".dowload_pop").toggle();
}
function closeImportFile(){
	$(".dowload_pop").hide();
}
function leaveImpPopClose(){
	$(".side_mask").fadeOut(200);
	$(".DocAccPopup").fadeOut(100);	
}
</script>


<script type="text/javascript">
function importLeaves(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
if ($('#upload_leave_excel_form').validate(false, validationSettings)){
	var frm = $('#upload_leave_excel_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	
	
	//It will escape all the special characters
	var jsonData = encodeURIComponent(con);
	var form = $('#upload_leave_excel_form')[0];
	var formData = new FormData(form); 
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
		   	type: "POST",
		   	data: formData,
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
    	    dataType : 'json',
		    url : pmsUrl+"/uploadLeaveType?"+tokenParamName+"="+tokenValue+"", 	   
 		    success : function(json) {
 		    		$("#loading").removeAttr("style");
 		    	    if(json.updateCount != null && json.updateCount != '')
 		    	    $("#msgDiv").removeAttr("style");
 		 			$("#msgDiv").css({ display: "block" });
 		 			$("#msgDiv").html(json.updateCount+ " Associates are updated ..!");
 			    	msgFadein();
 		    	    closeImportFile();
 		    	   resetUpload();
		 		    },
		 		   error: function(e){
			        }
			 });
			 
		}
}
</script>

<script>
function resetUpload(){
	$("#leaveInputFileId").val("");
	$("#validation").find('p.jquery_form_error_message').remove();
}
</script>


<script>
	function msgFadein() {
		$("#msgDiv").fadeIn(200);
        $('#msgDiv').delay(2000).fadeOut('slow');
	}
</script> 


<script>
function getEmployeeTotalCounts(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: pmsUrl+"/totalCountsForEmployeeLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,  
	       success :function(report) { 
	    	   if(report.employeeRequestedCounts != null && report.employeeRequestedCounts != '' && report.employeeRequestedCounts != 'undefined'){
	    		   if(report.employeeRequestedCounts == 0){
	      				$('#empReq').html("Requested");
	      			   }else{
	      				 $('#empReq').html("<a href='/pms/employeeRequestedLeaves'>Requested</a>"); 
	      			   }
    			   $("#employeeRequestedCounts").html(report.employeeRequestedCounts); 
    		   }
    		   if(report.employeeApprovedCounts != null && report.employeeApprovedCounts != '' && report.employeeApprovedCounts != 'undefined'){
    			   if(report.employeeApprovedCounts == 0){
    	   				$('#empApp').html("Approved");
    	   			   }else{
    	   				$('#empApp').html("<a href='/pms/employeeApprovedLeaves'>Approved</a>"); 
    	   			   }
    			   $("#employeeApprovedCounts").html(report.employeeApprovedCounts); 
    		   }
    		   if(report.employeeRejectedCounts != null && report.employeeRejectedCounts != '' && report.employeeRejectedCounts != 'undefined'){
    			   if(report.employeeRejectedCounts == 0){
    	   				$('#empRej').html("Rejected");
    	   			   }else{
    	   				$('#empRej').html("<a href='/pms/employeeRejectedLeaves'>Rejected</a>");
    	   			   }
    			   $("#employeeRejectedCounts").html(report.employeeRejectedCounts); 
    		   }
    		   if(report.employeeCancelledCounts != null && report.employeeCancelledCounts != '' && report.employeeCancelledCounts != 'undefined'){
    			   if(report.employeeCancelledCounts == 0){
    				    $('#empCanc').html("Cancelled");
    	   			   }else{
    	   				$('#empCanc').html("<a href='/pms/employeeCancelLeaves'>Cancelled</a>");
    	   			   }
    			   $("#employeeCancelledCounts").html(report.employeeCancelledCounts); 
    		   }
    		   
	       },
		  });
}
 
</script>

<script>
function getSelfTotalCounts(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: pmsUrl+"/totalCountsForSelfLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,  
	       success :function(report) { 
	   	   if(report.selfPendingCounts != null && report.selfPendingCounts != '' && report.selfPendingCounts != 'undefined'){
	   		if(report.selfPendingCounts == 0){
   				$("#selfpend").html("Pending"); 
   			   }else{
   				$("#selfpend").html("<a href='/pms/pendingLeaves'>Pending</a>"); 
   			   }
   			   $("#selfPendingCounts").html(report.selfPendingCounts); 
   		   }
   		   if(report.selfApprovedCounts != null && report.selfApprovedCounts != '' && report.selfApprovedCounts != 'undefined'){
   			if(report.selfApprovedCounts == 0){
   				$("#selfapp").html("Approved"); 
   			   }else{
   				$("#selfapp").html("<a href='/pms/approvedLeaves'>Approved</a>");
   			   }
   			   $("#selfApprovedCounts").html(report.selfApprovedCounts); 
   		   }
   		   if(report.selfRejectedCounts != null && report.selfRejectedCounts != '' && report.selfRejectedCounts != 'undefined'){
   			   if(report.selfRejectedCounts == 0){
   				$("#selfrej").html("Rejected"); 
   			   }else{
   				$("#selfrej").html("<a href='/pms/rejectedLeaves'>Rejected</a>");
   			   }
   			   $("#selfRejectedCounts").html(report.selfRejectedCounts); 
   		   }
   		   if(report.selfCancelledCounts != null && report.selfCancelledCounts != '' && report.selfCancelledCounts != 'undefined'){
   			   
   			if(report.selfCancelledCounts == 0){
   				$('#selfcanc').html("Cancelled");
   			   }else{
   				$("#selfcanc").html("<a href='/pms/cancelLeaves'>Cancelled</a>");
   			   }
   			   $("#selfCancelledCounts").html(report.selfCancelledCounts); 
   		   }
   			if((report.totalBalLeaves != null && report.totalBalLeaves != '' && report.totalBalLeaves != 'undefined') || report.totalBalLeaves == '0'){
   			   $("#selfBalancedCounts").html(report.totalBalLeaves); 
   		   }
   		 
	       },
		  });
}
</script>

<jsp:include page="../masterFooter.jsp" />
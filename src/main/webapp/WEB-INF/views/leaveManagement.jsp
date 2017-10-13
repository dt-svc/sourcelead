<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script> 
<%-- <section class="content-wraper"> --%>
  <!-- <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Dashboard :: Leave Management</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Dashboard</div>
  </section> -->
  <!-- Tasks and and Row -->
  
<script type="text/javascript">
$(document).ready(function(){
	getEmployeeTotalCounts();
	getSelfTotalCounts();
});
</script>
  
  <div class="clearfix mb">
    <div class="col-md-6">
      <div class="dashboard-widget">
          <h4 class="pull-left"><a href="<c:url value='/leaveManagementReports'/>" title="Leave Management">Leaves</a></h4>
        <div class="clearfix require_wrap_table dashboard-data">
          <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#self">Self</a></li>
            <sec:authorize access="hasAnyRole(\'ROLE_APPROVE_LEAVE\')">
            	<li><a data-toggle="tab" href="#employee">Employee</a></li>
            </sec:authorize>
          </ul>
          <div class="tab-content clearfix mt10">
            <div id="self" class="tab-pane fade in active">
              <div class="db-widget-sm clearfix">
                <div class="width100percnt clearfix">
                  <div id="empselfLeaves" style="width: 60%; height: 100px;" class="amchartAnchor-Hide pull-left"> </div>
                  <div style="width: 40%; height: 105px;" class="countList pull-left">
                <ul class="list-unstyled list-group clearfix mb5">
                    <li class="clearfix">
                        <label class="pull-left">Pending Leaves</label>
                        <a href="<c:url value='/pendingLeaves'/>" class="color_white" id="selfpend" title="Pending Leaves"><span class="label label-primary pull-right" id="selfPendingCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Approved Leaves</label>
                        <a href="<c:url value='/approvedLeaves'/>" class="color_white" id="selfapp" title="Approved Leaves"><span class="label label-success pull-right" id="selfApprovedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Rejected Leaves</label>
                        <a href="<c:url value='/rejectedLeaves'/>" class="color_white" id="selfrej" title="Rejected Leaves"><span class="label label-danger pull-right" id="selfRejectedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Cancelled Leaves</label>
                        <a href="<c:url value='/cancelLeaves'/>" class="color_white" id="selfcanc" title="Cancelled Leaves"><span class="label label-warning pull-right" id="selfCancelledCounts"></span></a>
                    </li>
                </ul>
                    </div>
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
          
            <div id="employee" class="tab-pane fade">
                
            <div class="db-widget-sm clearfix">
                <div class="width100percnt clearfix">
                  <div id="employeeLeaves" style="width: 60%; height: 105px;" class="amchartAnchor-Hide pull-left"> </div>
                  <div style="width: 40%; height: 105px;" class="countList pull-left">
                <ul class="list-unstyled list-group clearfix mb5">
                    <li class="clearfix">
                        <label class="pull-left">Requested Leaves</label>
                        <a href="<c:url value='/employeeRequestedLeaves'/>" class="color_white" id="empReq" title="Pending Leaves"><span class="label label-primary pull-right" id="employeeRequestedCounts"></span>></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Approved Leaves</label>
                        <a href="<c:url value='/employeeApprovedLeaves'/>" class="color_white" id="empApp" title="Approved Leaves"><span class="label label-success pull-right" id="employeeApprovedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Rejected Leaves</label>
                        <a href="<c:url value='/employeeRejectedLeaves'/>" class="color_white" id="empRej" title="Rejected Leaves"><span class="label label-danger pull-right" id="employeeRejectedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Cancel Leaves</label>
                        <a href="<c:url value='/employeeCancelLeaves'/>" class="color_white" id="empCanc" title="Cancel Leaves"><span class="label label-warning pull-right" id="employeeCancelledCounts"></span></a>
                    </li>
                </ul>
                      
                    </div>
                </div>
              </div>    
                
              <div class="db-widget-sm hidden">
                <div class="col-xs-12 col-sm-4 mb">
                  <div id="empRequested" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                  <div class="leavescount text-center"> <a href="javascript:void(0)" id="requested" title="Requested">70</a> </div>
                </div>
              
              <div class="col-xs-12 col-sm-4 mb">
                <div id="empApproved" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="javascript:void(0)" id="empApproved2" title="Approved">20</a> </div>
              </div>
              <div class="col-xs-12 col-sm-4 mb">
                <div id="empRejected" style="width: 100%; height: 150px;" class="amchartAnchor-Hide"> </div>
                <div class="leavescount text-center"> <a href="javascript:void(0)" id="empRejected2" title="Rejected">10</a> </div>
              </div>
                  </div>
            </div>
            
            </div>
        </div>
      </div>
    </div>
    <!-- Requirement ends --> 
  </div>
  <!-- Tasks and Timesheets columns ends --> 
<%-- </section> --%>

		
<script>
function getEmployeeTotalCounts(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/totalCountsForEmployeeLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,  
	       success :function(report) { 
	    	   if(report.employeeRequestedCounts != null && report.employeeRequestedCounts != '' && report.employeeRequestedCounts != 'undefined'){
	    		   if(report.employeeRequestedCounts == 0){
	      				$('#empReq').removeAttr("href");
	      			   }
    			   $("#employeeRequestedCounts").html(report.employeeRequestedCounts); 
    		   }
    		   if(report.employeeApprovedCounts != null && report.employeeApprovedCounts != '' && report.employeeApprovedCounts != 'undefined'){
    			   if(report.employeeApprovedCounts == 0){
    	   				$('#empApp').removeAttr("href");
    	   			   }
    			   $("#employeeApprovedCounts").html(report.employeeApprovedCounts); 
    		   }
    		   if(report.employeeRejectedCounts != null && report.employeeRejectedCounts != '' && report.employeeRejectedCounts != 'undefined'){
    			   if(report.employeeRejectedCounts == 0){
    	   				$('#empRej').removeAttr("href");
    	   			   }
    			   $("#employeeRejectedCounts").html(report.employeeRejectedCounts); 
    		   }
    		   if(report.employeeCancelledCounts != null && report.employeeCancelledCounts != '' && report.employeeCancelledCounts != 'undefined'){
    			   if(report.employeeCancelledCounts == 0){
    	   				$('#empCanc').removeAttr("href");
    	   			   }
    			   $("#employeeCancelledCounts").html(report.employeeCancelledCounts); 
    		   }
    		   
    		   //pie chart count
    		   AmCharts.makeChart("employeeLeaves",
    					{
    						"type": "pie",
    						"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
    						"innerRadius": "50%",
    						"labelRadius": 0,
    						"minRadius": 0,
    						"pieX": "50%",
    						"pieY": "50%",
    						"pullOutRadius": 0,
    						"radius": "49%",
    						"startAngle": 0,
    						"colors": [
    							"#337ab7",
    							"#66bb6a",
    							"#ef5350",
    							"#ff5722",
    							"#ededed"
    						],
    						"labelsEnabled": false,
    						"marginBottom": 0,
    						"marginTop": 0,
    						"outlineThickness": 0,
    						"titleField": "category",
    						"valueField": "column-1",
    						"fontSize": 12,
    						"allLabels": [],
    						"balloon": {
    							"fixedPosition": false
    						},
    						"titles": [],
    						"dataProvider": [
    							{
    								"category": "Requested",
    								"column-1": report.employeeRequestedCounts
    							},
    							{
    								"category": "Approved",
    								"column-1": report.employeeApprovedCounts
    							},
    							{
    								"category": "Rejected",
    								"column-1": report.employeeRejectedCounts
    							},
    							{
    								"category": "Canceled",
    								"column-1": report.employeeCancelledCounts
    							} 
    						]
    					}
    				);
    		   
	       },
		  });
}
 
</script>

<script>
function getSelfTotalCounts(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/totalCountsForSelfLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,  
	       success :function(report) { 
	   	   if(report.selfPendingCounts != null && report.selfPendingCounts != '' && report.selfPendingCounts != 'undefined'){
	   		if(report.selfPendingCounts == 0){
   				$('#selfpend').removeAttr("href");
   			   }
   			   $("#selfPendingCounts").html(report.selfPendingCounts); 
   		   }
   		   if(report.selfApprovedCounts != null && report.selfApprovedCounts != '' && report.selfApprovedCounts != 'undefined'){
   			if(report.selfApprovedCounts == 0){
   				$('#selfapp').removeAttr("href");
   			   }
   			   $("#selfApprovedCounts").html(report.selfApprovedCounts); 
   		   }
   		   if(report.selfRejectedCounts != null && report.selfRejectedCounts != '' && report.selfRejectedCounts != 'undefined'){
   			   if(report.selfRejectedCounts == 0){
   				$('#selfrej').removeAttr("href");
   			   }
   			   $("#selfRejectedCounts").html(report.selfRejectedCounts); 
   		   }
   		   if(report.selfCancelledCounts != null && report.selfCancelledCounts != '' && report.selfCancelledCounts != 'undefined'){
   			if(report.selfCancelledCounts == 0){
   				$('#selfcanc').removeAttr("href");
   			   }
   			   $("#selfCancelledCounts").html(report.selfCancelledCounts); 
   		   }
   		   
   		 AmCharts.makeChart("empselfLeaves",
 				{
 					"type": "pie",
 					"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
 					"innerRadius": "50%",
 					"labelRadius": 0,
 					"minRadius": 0,
 					"pieX": "50%",
 					"pieY": "50%",
 					"pullOutRadius": 0,
 					"radius": "49%",
 					"startAngle": 0,
 					"colors": [
 						"#5c6bc0",
 						"#8bc34a",
 						"#ef5350",
 						"#ffc107",
 						"#ededed"
 					],
 					"labelsEnabled": false,
 					"marginBottom": 0,
 					"marginTop": 0,
 					"outlineThickness": 0,
 					"titleField": "category",
 					"valueField": "column-1",
 					"fontSize": 12,
 					"allLabels": [],
 					"balloon": {
 						"fixedPosition": false
 					},
 					"titles": [],
 					"dataProvider": [
 						{
 							"category": "Pending",
 							"column-1": report.selfPendingCounts
 						},
 						{
 							"category": "Approved",
 							"column-1": report.selfApprovedCounts
 						},
 						{
 							"category": "Rejected",
 							"column-1": report.selfRejectedCounts
 						},
 						{
 							"category": "Canceled",
 							"column-1": report.selfCancelledCounts
 						} 
 					]
 				}
 			); 
   		 
	       },
		  });
}
</script>
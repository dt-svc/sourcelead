<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <script src="/resources/amcharts/amcharts.js"></script> -->
<!-- <script src="/resources/amcharts/pie.js"></script> -->

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
	getSelfTotalCounts();
});
</script>
  
    <div class="col-md-6 mb">
      <div class="dashboardWidget-new">
          <h4 class="pull-left"><a href="<c:url value='${appPath}/leaveManagementReports'/>" title="Leave Management">Leaves</a></h4>
        <div class="clearfix"></div>
        <div class="clearfix">
          <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#self">Self</a></li>
            <sec:authorize access="hasAnyRole(\'ROLE_APPROVE_LEAVE\')">
            	<li><a data-toggle="tab" href="#employee" onclick="getEmployeeTotalCounts();">Employees</a></li>
            </sec:authorize>
          </ul>
          <div class="tab-content clearfix">
            <div id="self" class="tab-pane fade in active">
              <div class="col-md-4" style="position:absolute; top:16px; right:15px;">
                        <label class="pull-left mr">Balance Leaves</label>
                        <div class="color_white" title="Balance Leaves"><span class="label label-info pull-right" id="selfBalancedCounts"></span></div>
              </div>
              <div class="db-widget-sm clearfix row">
                <div class="width100percnt clearfix">
                  <div id="empselfLeaves" style="width: 60%; height: 100px;" class="amchartAnchor-Hide pull-left hidden"> </div>
                    <div class="clearfix"></div>
              <div class="mt5 clearfix">
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                 <!-- <span class="info-box-icon bg-blue"> <i class="fa fa-clock-o" aria-hidden="true"></i> </span> -->
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><span id="selfPendingCounts"></span></p>
                    <p class="info-box-text mb0 text-muted mt2" id="selfpend"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                 <!-- <span class="info-box-icon bg-green"> <i class="fa fa-check-circle-o" aria-hidden="true"></i> </span> -->
                  <div class="info-box-content text-center">
                   <p class="info-box-number"><span id="selfApprovedCounts"></span></p>
                   <p class="info-box-text mb0 text-muted mt2" id="selfapp"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                <!-- <span class="info-box-icon bg-orange"> <i class="fa fa-times" aria-hidden="true"></i></span> -->
                  <div class="info-box-content text-center">
                  <p class="info-box-number"><span id="selfRejectedCounts"></span></p>
                  <p class="info-box-text mb0 text-muted mt2" id="selfrej"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                <!-- <span class="info-box-icon bg-red"> <i class="fa fa-ban" aria-hidden="true"></i> </span> -->
                  <div class="info-box-content text-center">
                  <p class="info-box-number"><span id="selfCancelledCounts"></span></p>
                  <p class="info-box-text mb0 text-muted mt2" id="selfcanc"></p>
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
          
            <div id="employee" class="tab-pane fade">
                
            <div class="db-widget-sm clearfix">
                <div class="width100percnt clearfix">
                <div class="clearfix"></div>
                <div class="mt5 clearfix row">
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                 <!-- <span class="info-box-icon bg-blue"> <i class="fa fa-clock-o" aria-hidden="true"></i></span> -->
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><span id="employeeRequestedCounts"></span></p>
                    <p class="info-box-text mb0 text-muted mt2" id="empReq"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                <!-- <span class="info-box-icon bg-green"> <i class="fa fa-check-circle-o" aria-hidden="true"></i></span> -->
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><span id="employeeApprovedCounts"></span></p>
                    <p class="info-box-text mb0 text-muted mt2" id="empApp"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                <!-- <span class="info-box-icon bg-orange"> <i class="fa fa-times" aria-hidden="true"></i></span> -->
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><span id="employeeRejectedCounts"></span></p>
                    <p class="info-box-text mb0 text-muted mt2" id="empRej"></p>
                  </div>
                </div>
              </div>
              <div class="col-xs-12 col-sm-3">
                <div class="clearfix">
                <!-- <span class="info-box-icon bg-red"> <i class="fa fa-ban" aria-hidden="true"></i></span> -->
                  <div class="info-box-content text-center">
                    <p class="info-box-number"><span id="employeeCancelledCounts"></span></p>
                    <p class="info-box-text mb0 text-muted mt2" id="empCanc"></p>
                  </div>
                </div>
              </div>
              
              </div>
                
                
                
                  <!-- <div id="employeeLeaves" style="width: 60%; height: 105px;" class="amchartAnchor-Hide pull-left"> </div> -->
                  <div style="width: 40%; height: 105px;" class="countList pull-left hidden">
                <ul class="list-unstyled list-group clearfix mb5">
                    <li class="clearfix">
                        <label class="pull-left">Requested Leaves</label>
                        <a href="<c:url value='${appPath}/employeeRequestedLeaves'/>" class="color_white" id="empReq" title="Requested Leaves"><span class="label label-primary pull-right" id="employeeRequestedCounts"></span>></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Approved Leaves</label>
                        <a href="<c:url value='${appPath}/employeeApprovedLeaves'/>" class="color_white" id="empApp" title="Approved Leaves"><span class="label label-success pull-right" id="employeeApprovedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Rejected Leaves</label>
                        <a href="<c:url value='${appPath}/employeeRejectedLeaves'/>" class="color_white" id="empRej" title="Rejected Leaves"><span class="label label-danger pull-right" id="employeeRejectedCounts"></span></a>
                    </li>
                    <li class="clearfix">
                        <label class="pull-left">Cancelled Leaves</label>
                        <a href="<c:url value='${appPath}/employeeCancelLeaves'/>" class="color_white" id="empCanc" title="Cancelled Leaves"><span class="label label-warning pull-right" id="employeeCancelledCounts"></span></a>
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
// 	      				$('#empReq').removeAttr("href");
	      				$('#empReq').html("Requested");
	      			   }else{
	      				 $('#empReq').html("<a href='/app/employeeRequestedLeaves'>Requested</a>"); 
	      			   }
    			   $("#employeeRequestedCounts").html(report.employeeRequestedCounts); 
    		   }
    		   if(report.employeeApprovedCounts != null && report.employeeApprovedCounts != '' && report.employeeApprovedCounts != 'undefined'){
    			   if(report.employeeApprovedCounts == 0){
//     	   				$('#empApp').removeAttr("href");
    	   				$('#empApp').html("Approved");
    	   			   }else{
    	   				$('#empApp').html("<a href='/app/employeeApprovedLeaves'>Approved</a>"); 
    	   			   }
    			   $("#employeeApprovedCounts").html(report.employeeApprovedCounts); 
    		   }
    		   if(report.employeeRejectedCounts != null && report.employeeRejectedCounts != '' && report.employeeRejectedCounts != 'undefined'){
    			   if(report.employeeRejectedCounts == 0){
//     	   				$('#empRej').removeAttr("href");
    	   				$('#empRej').html("Rejected");
    	   			   }else{
    	   				$('#empRej').html("<a href='/app/employeeRejectedLeaves'>Rejected</a>");
    	   			   }
    			   $("#employeeRejectedCounts").html(report.employeeRejectedCounts); 
    		   }
    		   if(report.employeeCancelledCounts != null && report.employeeCancelledCounts != '' && report.employeeCancelledCounts != 'undefined'){
    			   if(report.employeeCancelledCounts == 0){
//     	   				$('#empCanc').removeAttr("href");
    				    $('#empCanc').html("Cancelled");
    	   			   }else{
    	   				$('#empCanc').html("<a href='/app/employeeCancelLeaves'>Cancelled</a>");
    	   			   }
    			   $("#employeeCancelledCounts").html(report.employeeCancelledCounts); 
    		   }
    		   
    		   //pie chart count
    		/*    AmCharts.makeChart("employeeLeaves",
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
    							"#f0ad4e"
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
    								"category": "Cancelled",
    								"column-1": report.employeeCancelledCounts
    							} 
    						]
    					} 
    				);*/
    		   
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
//    				$('#selfapp').removeAttr("href");
   				$("#selfpend").html("Pending"); 
   			   }else{
   				$("#selfpend").html("<a href='/app/pendingLeaves'>Pending</a>"); 
   			   }
   			   $("#selfPendingCounts").html(report.selfPendingCounts); 
   		   }
   		   if(report.selfApprovedCounts != null && report.selfApprovedCounts != '' && report.selfApprovedCounts != 'undefined'){
   			if(report.selfApprovedCounts == 0){
//    				$('#selfrej').removeAttr("href");
   				$("#selfapp").html("Approved"); 
   			   }else{
   				$("#selfapp").html("<a href='/app/approvedLeaves'>Approved</a>");
   			   }
   			   $("#selfApprovedCounts").html(report.selfApprovedCounts); 
   		   }
   		   if(report.selfRejectedCounts != null && report.selfRejectedCounts != '' && report.selfRejectedCounts != 'undefined'){
   			   if(report.selfRejectedCounts == 0){
//    				$('#selfrej').removeAttr("href");
   				$("#selfrej").html("Rejected"); 
   			   }else{
   				$("#selfrej").html("<a href='/app/rejectedLeaves'>Rejected</a>");
   			   }
   			   $("#selfRejectedCounts").html(report.selfRejectedCounts); 
   		   }
   		   if(report.selfCancelledCounts != null && report.selfCancelledCounts != '' && report.selfCancelledCounts != 'undefined'){
   			   
   			if(report.selfCancelledCounts == 0){
//    				$('#selfrej').removeAttr("href");
   				$('#selfcanc').html("Cancelled");
   			   }else{
   				$("#selfcanc").html("<a href='/app/cancelLeaves'>Cancelled</a>");
   			   }
   			   $("#selfCancelledCounts").html(report.selfCancelledCounts); 
   		   }
   			if((report.totalBalLeaves != null && report.totalBalLeaves != '' && report.totalBalLeaves != 'undefined') || report.totalBalLeaves == '0'){
   			   $("#selfBalancedCounts").html(report.totalBalLeaves); 
   		   }
   		   
   		/*  AmCharts.makeChart("empselfLeaves",
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
						"#f0ad4e",
						"#5bc0de"
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
 							"category": "Cancelled",
 							"column-1": report.selfCancelledCounts
 						},
 						{
 							"category": "Balance",
 							"column-1": report.selfBalancedCounts
 						} 
 					]
 				}
 			); */ 
   		 
	       },
		  });
}
</script>
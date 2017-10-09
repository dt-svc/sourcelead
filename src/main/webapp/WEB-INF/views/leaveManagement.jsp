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
  
  <div class="clearfix row mb">
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
          <div class="tab-content clearfix mt">
            <div id="self" class="tab-pane fade in active">
              <div class="db-widget-sm">
                <div class="width100percnt">
                  <div id="empselfLeaves" style="width: 60%; height: 150px;" class="amchartAnchor-Hide pull-left"> </div>
                  <div style="width: 40%; height: 150px;" class="countList pull-left">
                <ul class="list-unstyled list-group clearfix">
                    <li class="clearfix">
                        <label class="pull-left">Pending Leaves</label>
                        <a href="<c:url value='/pendingLeaves'/>" class="color_white" title="Pending Leaves"><span class="label label-primary pull-right" id="selfPendingCounts"></span></a>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Approved Leaves</label>
                        <a href="<c:url value='/approvedLeaves'/>" class="color_white" title="Approved Leaves"><span class="label label-success pull-right" id="selfApprovedCounts"></span></a>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Rejected Leaves</label>
                        <a href="<c:url value='/rejectedLeaves'/>" class="color_white" title="Rejected Leaves"><span class="label label-danger pull-right" id="selfRejectedCounts"></span></a>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Cancelled Leaves</label>
                        <a href="<c:url value='/cancelLeaves'/>" class="color_white" title="Cancel Leaves"><span class="label label-warning pull-right" id="selfCancelledCounts"></span></a>
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
                
            <div class="db-widget-sm">
                <div class="width100percnt">
                  <div id="employeeLeaves" style="width: 60%; height: 150px;" class="amchartAnchor-Hide pull-left"> </div>
                  <div style="width: 40%; height: 150px;" class="countList pull-left">
                <ul class="list-unstyled list-group clearfix">
                    <li class="clearfix">
                        <label class="pull-left">Requested Leaves</label>
                        <a href="<c:url value='/employeeRequestedLeaves'/>" class="color_white" title="Pending Leaves"><span class="label label-primary pull-right" id="employeeRequestedCounts"></span>></a>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Approved Leaves</label>
                        <a href="<c:url value='/employeeApprovedLeaves'/>" class="color_white" title="Approved Leaves"><span class="label label-success pull-right" id="employeeApprovedCounts"></span></a>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Rejected Leaves</label>
                        <a href="<c:url value='/employeeRejectedLeaves'/>" class="color_white" title="Rejected Leaves"><span class="label label-danger pull-right" id="employeeRejectedCounts"></span></a>
                    </li>
                    <li class="clearfix mt10">
                        <label class="pull-left">Cancel Leaves</label>
                        <a href="<c:url value='/employeeCancelLeaves'/>" class="color_white" title="Cancel Leaves"><span class="label label-warning pull-right" id="employeeCancelledCounts"></span></a>
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

<!-- amCharts javascript code --> 
<script type="text/javascript">
    //self Leaves
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
							"category": "Pending",
							"column-1": "30"
						},
						{
							"category": "Approved",
							"column-1": "10"
						},
						{
							"category": "Rejected",
							"column-1": "12"
						},
						{
							"category": "Canceled",
							"column-1": "10"
						},
						{
							"category": "Total Leaves",
							"column-1": "10"
						}
					]
				}
			);
    //employee Leaves
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
							"category": "Requested",
							"column-1": "30"
						},
						{
							"category": "Approved",
							"column-1": "10"
						},
						{
							"category": "Rejected",
							"column-1": "12"
						},
						{
							"category": "Canceled",
							"column-1": "10"
						},
						{
							"category": "Total Leaves",
							"column-1": "10"
						}
					]
				}
			); 
    
    
            //Pending Chart
			AmCharts.makeChart("selfPending",
				{
					"type": "pie",
					"adjustPrecision": true,
					//"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
                    "balloonText": "",
					"innerRadius": "80%",
					"labelRadius": 0,
					"pieX": "50%",
					"pieY": "50%",
					"pullOutRadius": "5%",
					"colors": [
						"#42a5f5",
						"#eeeeee"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"creditsPosition": "bottom-left",
					"allLabels": [
						{
							"align": "center",
							"color": "#BBB9B9",
							"id": "pending",
							"size": 16,
							"text": "Pending",
							"x": 0,
							"y": "40%"
						}
					],
					"balloon": {
                      "fixedPosition": false
                    },
					"titles": [],
					"dataProvider": [
						{
							"category": "Pending",
							"column-1": "80"
						},
						{
							"category": "Total Leaves",
							"column-1": "20"
						}
					]
				}
			);
                        

            //Approved Chart
			AmCharts.makeChart("selfApproved",
				{
					"type": "pie",
					"adjustPrecision": true,
					//"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
                    "balloonText": "",
					"innerRadius": "80%",
					"labelRadius": 0,
					"pieX": "50%",
					"pieY": "50%",
					"pullOutRadius": "5%",
					"colors": [
						"#66bb6a",
						"#eeeeee"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"creditsPosition": "bottom-left",
					"allLabels": [
						{
							"align": "center",
							"color": "#BBB9B9",
							"id": "approved",
							"size": 16,
							"text": "Approved",
							"x": 0,
							"y": "40%"
						}
					],
					"balloon": {
                      "fixedPosition": false
                    },
					"titles": [],
					"dataProvider": [
						{
							"category": "Approved",
							"column-1": "80"
						},
						{
							"category": "Total Leaves",
							"column-1": "20"
						}
					]
				}
			);
                        //Pending Chart
			AmCharts.makeChart("selfRejected",
				{
					"type": "pie",
					"adjustPrecision": true,
					//"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
                    "balloonText": "",
					"innerRadius": "80%",
					"labelRadius": 0,
					"pieX": "50%",
					"pieY": "50%",
					"pullOutRadius": "5%",
					"colors": [
						"#ef5350",
						"#eeeeee"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"creditsPosition": "bottom-left",
					"allLabels": [
						{
							"align": "center",
							"color": "#BBB9B9",
							"id": "rejected",
							"size": 16,
							"text": "Rejected",
							"x": 0,
							"y": "40%"
						}
					],
					"balloon": {
                      "fixedPosition": false
                    },
					"titles": [],
					"dataProvider": [
						{
							"category": "Rejected",
							"column-1": "80"
						},
						{
							"category": "Total Leaves",
							"column-1": "20"
						}
					]
				}
			);
    
    //Employee Requested Chart
			AmCharts.makeChart("empRequested",
				{
					"type": "pie",
					"adjustPrecision": true,
					//"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
                    "balloonText": "",
					"innerRadius": "80%",
					"labelRadius": 0,
					"pieX": "50%",
					"pieY": "50%",
					"pullOutRadius": "5%",
					"colors": [
						"#42a5f5",
						"#eeeeee"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"creditsPosition": "bottom-left",
					"allLabels": [
						{
							"align": "center",
							"color": "#BBB9B9",
							"id": "requested",
							"size": 16,
							"text": "Requested",
							"x": 0,
							"y": "40%"
						}
					],
					"balloon": {
                      "fixedPosition": false
                    },
					"titles": [],
					"dataProvider": [
						{
							"category": "Requested",
							"column-1": "80"
						},
						{
							"category": "Total Leaves",
							"column-1": "20"
						}
					]
				}
			);
    
        //Employee Approved Chart
			AmCharts.makeChart("empApproved",
				{
					"type": "pie",
					"adjustPrecision": true,
					//"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
                    "balloonText": "",
					"innerRadius": "80%",
					"labelRadius": 0,
					"pieX": "50%",
					"pieY": "50%",
					"pullOutRadius": "5%",
					"colors": [
						"#66bb6a",
						"#eeeeee"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"creditsPosition": "bottom-left",
					"allLabels": [
						{
							"align": "center",
							"color": "#BBB9B9",
							"id": "approved",
							"size": 16,
							"text": "Approved",
							"x": 0,
							"y": "40%"
						}
					],
					"balloon": {
                      "fixedPosition": false
                    },
					"titles": [],
					"dataProvider": [
						{
							"category": "Requested",
							"column-1": "80"
						},
						{
							"category": "Total Leaves",
							"column-1": "20"
						}
					]
				}
			);
            //Employee Rejected Chart
			AmCharts.makeChart("empRejected",
				{
					"type": "pie",
					"adjustPrecision": true,
					//"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
                    "balloonText": "",
					"innerRadius": "80%",
					"labelRadius": 0,
					"pieX": "50%",
					"pieY": "50%",
					"pullOutRadius": "5%",
					"colors": [
						"#ef5350",
						"#eeeeee"
					],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"creditsPosition": "bottom-left",
					"allLabels": [
						{
							"align": "center",
							"color": "#BBB9B9",
							"id": "rejected",
							"size": 16,
							"text": "Rejected",
							"x": 0,
							"y": "40%"
						}
					],
					"balloon": {
                      "fixedPosition": false
                    },
					"titles": [],
					"dataProvider": [
						{
							"category": "Rejected",
							"column-1": "80"
						},
						{
							"category": "Total Leaves",
							"column-1": "20"
						}
					]
				}
			);
		</script>
		
		
<script>
function getEmployeeTotalCounts(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/totalCountsForEmployeeLeaves?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,  
	       success :function(report) { 
	    	   if(report.employeeRequestedCounts != null && report.employeeRequestedCounts != '' && report.employeeRequestedCounts != 'undefined'){
    			   $("#employeeRequestedCounts").html(report.employeeRequestedCounts); 
    		   }else{
    			   $("#employeeRequestedCounts").html(0); 
    		   }
    		   if(report.employeeApprovedCounts != null && report.employeeApprovedCounts != '' && report.employeeApprovedCounts != 'undefined'){
    			   $("#employeeApprovedCounts").html(report.employeeApprovedCounts); 
    		   }else{
    			   $("#employeeApprovedCounts").html(0); 
    		   }
    		   if(report.employeeRejectedCounts != null && report.employeeRejectedCounts != '' && report.employeeRejectedCounts != 'undefined'){
    			   $("#employeeRejectedCounts").html(report.employeeRejectedCounts); 
    		   }else{
    			   $("#employeeRejectedCounts").html(0); 
    		   }
    		   if(report.employeeCancelledCounts != null && report.employeeCancelledCounts != '' && report.employeeCancelledCounts != 'undefined'){
    			   $("#employeeCancelledCounts").html(report.employeeCancelledCounts); 
    		   }else{
    			   $("#employeeCancelledCounts").html(0); 
    		   }
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
   			   $("#selfPendingCounts").html(report.selfPendingCounts); 
   		   }else{
   			   $("#selfPendingCounts").html(0); 
   		   }
   		   if(report.selfApprovedCounts != null && report.selfApprovedCounts != '' && report.selfApprovedCounts != 'undefined'){
   			   $("#selfApprovedCounts").html(report.selfApprovedCounts); 
   		   }else{
   			   $("#selfApprovedCounts").html(0); 
   		   }
   		   if(report.selfRejectedCounts != null && report.selfRejectedCounts != '' && report.selfRejectedCounts != 'undefined'){
   			   $("#selfRejectedCounts").html(report.selfRejectedCounts); 
   		   }else{
   			   $("#selfRejectedCounts").html(0); 
   		   }
   		   if(report.selfCancelledCounts != null && report.selfCancelledCounts != '' && report.selfCancelledCounts != 'undefined'){
   			   $("#selfCancelledCounts").html(report.selfCancelledCounts); 
   		   }else{
   			   $("#selfCancelledCounts").html(0); 
   		   } 
	       },
		  });
}
</script>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	getBalanceLeaveReportList();
});
</script>


<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Balances</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Leave Balances</div>
    </section>
    <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="timesheet_head table_head">
          <div class="pull-left ml mt5 mr">
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" onclick="resetCandidateFilter();" title="Refresh"> <span class="fa fa-refresh"></span> </button>
          </div>
          <div class="pull-left mt5"> <a href="javascript:void(0)" class="btn btn-default btn-sm anchorBlack text-center" title="Filters" onclick="openPop();"><i class="fa fa-filter faFilter padding_right"></i> </a> </div>
        </div>
  <!--       <div class="require_wrap_table tableFirstSecond clearfix">
          <table>
            <thead>
              <tr>
                <th>Employee Name</th>
                <th>Emp Type</th>
                <th>Sick Leave</th>
                <th>Vacation Leave</th>
                <th>Total Balance Leaves</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><div class="tbldata-dotdot">Employee1</div></td>
                <td><div class="tblminwidth-50">Fulltime</div></td>
                <td><div class="tblminwidth-50">4</div></td>
                <td><div class="tblminwidth-50">4</div></td>
                <td><div class="tblminwidth-50">8</div></td>
              </tr>
            </tbody>
          </table>
        </div> -->
          <div class="clients_wrap_table datatable_filter">
    <div id="balanceLeaveReportsList"></div>
  </div>
      </div>
    </div>
  </section>


<script>
function getBalanceLeaveReportList(){
	var fromDate = $("#fromDate").val();
	var searchValue = $("#employeeDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	//var url = sourceleadUrl+"/getDaywiseLeaveReportList?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate;
	var url = sourceleadUrl+"/getLeaveBalanceReportList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue;
	prepareBalanceLeaveReportList(url);
	
}
</script>	

<script>
function prepareBalanceLeaveReportList(url){
$("#balanceLeavesTableSearch").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.get(url,
						function(response) {
							if (response.response === "AWKWARD") {
								location.href = "/errorPage";
							} else if (response.response === "SUCCESS") {
								var result = "";
								result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
								result = result + '<table id="dynamicBalanceLeaveReportsListView" class="display" cellspacing="0" width="100%" >';
								result = result + '<thead>';
								result = result + '<tr>';
								result = result + '<th>Employee Name</th>';
							 	result = result + '<th>Emp Type</th>';
							 	for (i = 0; i < response.data.length; i++) {
									var report = response.data[i];
								if(report.leaveTypeNames !=null && report.leaveTypeNames !='' && report.leaveTypeNames !='undefined')
								result = result + '<th>'+report.leaveTypeNames+'</th>';
							 	}
								result = result + '<th>Total Balance Leaves</th>';
								result = result + '</tr>';
								result = result + '</thead>';
								result = result + '<tbody>';
								 for (i =0; i < response.data.length; i++) {
									var report = response.data[i];
									result = result + '<tr>';
									 if(report.empName !=null && report.empName !='' && report.empName !='undefined'){
										result = result + ' <td><div class="tbldata-dotdot-100" title = "'+report.empName+'">'+report.empName+'</div></td>';
									}else{
										result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
									} 
									 if(report.empType !=null && report.empType !='' && report.empType !='undefined'){
											result = result + ' <td><div class="tbldata-dotdot-vsm" title = "'+report.empType+'">'+report.empType+'</div></td>';
										}else{
											result = result + ' <td><div class="tbldata-dotdot-vsm">--</div></td>';
										}
									 var k="";
									 var v="";
								for (var a in report.map)
									{
									k=report.map[a];
									v=a;
									}
//  									 for (i = 0; i < report.leaveTypeNames.length; i++)
//  										 {
//  										 if(report.leaveTypeNames ==v)
										result = result + '    <td>';
											result = result + '<div class="tbldata-dotdot-100">'+k+'</div>';
										result = result + '    </td>'; 
//  										 }
// 								 }
										result = result + '    <td>';
											result = result + '<div class="tbldata-dotdot-100">'+v+'</div>';
										result = result + '    </td>'; 
									result = result + '    </tr>';
								} 
										result = result + '  </tbody>                                                                                                        ';
										result = result + '</table> ';
							
							$("#balanceLeaveReportsList").empty();
							$("#balanceLeaveReportsList").append(result);
							$("#balanceLeavesTableSearch").removeAttr('style');
							listTypePagination();
							$(".proj_show").click(
									function() {
										$(".new_side_wrap").addClass(
												"new_side_wrap_active");
										$(".side_mask").fadeIn(200);
										$("body").css('overflow-y',
												'hidden');
									});

						}else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
							showError("Operation failed, please try again");
						}
       });
}
					
</script>
<script>
	function listTypePagination() {
		$('#dynamicBalanceLeaveReportsListView').DataTable({
			"scrollCollapse" : false,
			"lengthMenu" : [ [ 10, 15, 20, -1 ], [ 10, 15, 20, "All" ] ],
			"bLengthChange" : true,
			"bDestroy" : true,
			"ordering" : true,
			"mark" : true,
			"oLanguage" : {
				"sSearch" : "",
				"sZeroRecords" : "",
				"sEmptyTable" : ""
			}
		});
		$("#dynamicBalanceLeaveReportsListView").wrap("<div class='overflow_Auto' style='width:100%;'></div>");

	}
</script>

<!-- <script>
function prepareAllDaywiseLeaveReportList(url){
	 	var result = "";
		result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
		result = result + '<table id="dynamicDaywiseLeaveReportsListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Emp Type</th>';
		result = result + '<th>Leave Type</th>';
		result = result + '<th>Type</th>';
		result = result + '<th>Duration</th>';
		result = result + '<th>Leave Period</th>';
		result = result + '<th>Reason</th>';
		result = result + '<th>Approver</th>';
		result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#daywiseLeaveReportsList").empty();
		$("#daywiseLeaveReportsList").append(result);
		daywiseLeaveReportsListPagination(url);
}
</script>

<script>
var table = '';
function daywiseLeaveReportsListPagination(url){
	var colIndex = 0;
	table = $('#dynamicDaywiseLeaveReportsListView').DataTable( { 
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
        	{ "data": "empName" },
            { "data": "employmentType" },
            { "data": "leaveTypeNames"},
            { "data": "vacationLeave"},
            { "data": "totalBalanceLeaves"}
            
        ],
		 "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "empName",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = '';
		    	  		 if(report.empName !=null && report.empName !='' && report.empName !='undefined'){
								result = result + ' <td><div class="tbldata-dotdot-100" title = "'+report.empName+'">'+report.empName+'</div></td>';
							}else{
								result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
							}
			             return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
		          "data": "name",
		          "render": function ( data, type, report, meta ) {
		        	  var result = '';
		          if(report.employmentType !=null && report.employmentType !='' && report.employmentType !='undefined'){
						 result = result +" <a href='<c:url value='/app/reportSummary/"+report.employmentType+"'/>'' title = '"+report.employmentType+"' class='reportTextLeft'>"+report.employmentType+"</a>";
		          }else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
		              return result;
		          		
		          }
		          },{
		        "targets": colIndex++,
		        "data": "leaveTypeNames",
		        "render": function ( data, type, report, meta ) {
		        	  var result = '';
		        	  if(report.leaveTypeNames !=null && report.leaveTypeNames !='' && report.leaveTypeNames !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-vsm" title = "'+report.leaveTypeNames+'">'+report.leaveTypeNames+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-vsm">--</div></td>';
						}
		        	 return result;
		        }
		      }, {
		          "targets": colIndex++,
		          "data": "mobilePhone",
		          "render": function ( data, type, report, meta ) {
		        	  var result = '';
		        	  if(report.mobilePhone !=null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
							result = result + ' <td><div class="tblminwidth-100" title = "'+report.mobilePhone+'">'+report.mobilePhone+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-100">--</div></td>';
						}

		        	  return result;
		          }
		        }, {
		            "targets": colIndex++,
		            "data": "employmentType",
		            "sortable": false,
		            "render": function ( data, type, report, meta ) {
		            	var result = '';
 		            	if(report.employmentType !=null && report.employmentType !='' && report.employmentType !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+report.employmentType+'">'+report.employmentType+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
 
			              return result;
		            }
		      },
		      {
		          "targets": colIndex++,
		          "data": "fromDate",
		          "sortable": false,
		          "render": function ( data, type, report, meta ) {
		          	var result = '';
		          	if(report.dateofJoin !=null && report.dateofJoin !='' && report.dateofJoin !='undefined'){
						result = result + ' <td><div class="tblminwidth-100" title = "'+report.dateofJoin+'">'+report.dateofJoin+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-100">--</div></td>';
					}

			              return result;
		          }
		    },
		    {
		        "targets": colIndex++,
		        "data": "toDate",
		        "sortable": false,
		        "render": function ( data, type, report, meta ) {
		        	var result = '';
		        	 if(report.dateOfTermination !=null && report.dateOfTermination !='' && report.dateOfTermination !='undefined'){
						result = result + ' <td><div class="tbldata-dotdot" title = "'+report.dateOfTermination+'">'+report.dateOfTermination+'</div></td>';
					}else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
		              return result;
		        }
		    },
		    {
			          "targets": colIndex++,
			          "data": "designation",
			          "sortable": false,
			          "render": function ( data, type, report, meta ) {
			          	var result = '';
			         	if(report.designation !=null && report.designation !='' && report.designation !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+report.designation+'">'+report.designation+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						} 
				        return result;
			          }
			},{
			          "targets": colIndex++,
			          "data": "actions",
			          "sortable": false,
			          "render": function ( data, type, report, meta ) {
			          	var result = '';
					       result = result + '      <td><div class="tblminwidth-75 text-center">             ';
				           result = result + '      <div class="positionRelative actionList-vdse-div">';
				           result = result + '        <ul class="list-unstyled list-inline clearfix actionList-vdse">';
				           result = result + '          <li><a href="javascript:void(0)" title="Print" class="fa fa-print" onClick="printEVR();commonview('+report.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="Download" class="fa fa-download" onClick="downloadEVR();commonview('+report.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="Share" class="fa fa-share-alt" onClick="shareEVR();commonview('+report.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="View" class="fa fa-eye" onClick="viewEVR();commonview('+report.employeeId+')"></a></li>';
				           result = result + '        </ul>';
				           result = result + '      </div>';
				           result = result + '    </div>';
				           result = result + '    </td>';
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
    $("#dynamicEmployeeListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}

$("#employeeDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getAllEmployeesList();
	   }
	});
</script>
 -->
 
<script>
function viewCompleteTemplate(temp){
	var templateUUID = $("#empTemplateId").val();
	var toAddress = $("#toAddress").val();
	var employeeId = $("#hiddenreportId").val();
	var winPrint = window.open(sourceleadUrl+'/previewCompleteTemplateByTemplateUUID?&templateUUID='+templateUUID+'&toAddress='+toAddress+'&employeeId='+employeeId,'_newtab');
	if(temp == 'print')
		winPrint.print();
}
</script> 


<script type="text/javascript">
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
			})
</script>

<jsp:include page="../masterFooter.jsp" />
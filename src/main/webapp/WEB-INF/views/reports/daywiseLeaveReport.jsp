<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	getDaywiseLeaveReportList();
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
          <h2>Day Wise Report </h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo; DayWise Leave Report</div>
    </section>
     <div class="timesheet_out">
      <div class="vendor_wrap">
        <div class="timesheet_head table_head">
          <div class="pull-left ml mt5 mr">
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" onclick="resetCandidateFilter();" title="Refresh"> <span class="fa fa-refresh"></span> </button>
          </div>
          <div class="pull-left mt5"> <a href="javascript:void(0)" class="btn btn-default btn-sm anchorBlack text-center" title="Filters" onclick="openPop();"><i class="fa fa-filter faFilter padding_right"></i> </a> </div>
          <div class="pull-left ml mt5">
            <div class="pull-left mr5 width150px">
              <input value="" name="fromDate" id="fromDate" placeholder="Date" class="form-control calendar input-sm margin-top0" type="text">
            <p id="fromDateDiv" class="ReError"></p>
            </div>
            <button type="button" class="btn btn-primary btn-sm"><i class="glyphicon glyphicon-search" onclick="searchThroughDates();"></i></button>
          </div>
          <div class="remove_search pull-right">
          <input type="text" placeholder="Search" id="daywiseReportDataTableSearch">
           <span class="cursorPointer" title="Search"><i onclick="getDaywiseLeaveReportList();"></i></span>    
<!--         <span class="cursorPointer" title="Search"><i class="fa fa-search" onclick="getDaywiseLeaveReportList();"></i></span>   -->
<!--             <input type="text" placeholder="Search"/> -->
          </div>
        </div>
        
          <div class="candidate_wrap_table datatable_filter">
	           <div id="daywiseLeaveReportsList"></div>
          </div>
        
       <!--  <div class="require_wrap_table tableFirstSecond clearfix">
          <table>
            <thead>
              <tr>
                <th>Employee Name</th>
                <th>Emp Type</th>
                <th>Leave Type</th>
                <th>Type</th>
                <th>Duration</th>
                <th>Leave Period</th>
                <th>Reason</th>
                <th>Approver</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><div class="tbldata-dotdot-vsm"> Employee1 </div></td>
                <td><div class="tblminwidth-50">Fulltime</div></td>
                <td><div class="tblminwidth-50">Sick Leave</div></td>
                <td><div class="tblminwidth-50">Billable</div></td>
                <td><div class="tblminwidth-50">Full Day</div></td>
                <td><div class="tblminwidth-50">01/01/2008</div>
                  <div class="tblminwidth-50">01/03/2008</div></td>
                <td><div class="tbldata-dotdot"> Reason for Dailywise report </div></td>
                <td><div class="tbldata-dotdot-sm"> Johnsmith </div></td>
                <td><div class="tblminwidth-50">Approved</div></td>
              </tr>
            </tbody>
          </table>
        </div> -->
<!--         <nav>
          <ul id="paging" class="pagination">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav> -->
      </div>
    </div>
  </section>


<script>
function getDaywiseLeaveReportList(){
	var fromDate = $("#fromDate").val();
	var searchValue = $("#daywiseReportDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getDaywiseLeaveReportList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&fromDate="+fromDate;
	prepareAllDaywiseLeaveReportList(url);
}
</script>	

<script>
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
            { "data": "name" },
            { "data": "employmentType"},
            { "data": "leaveTypeName"},
            { "data": "type"},
            { "data": "duration"},
            { "data": "leavePeriod"},
            { "data": "reason"},
            { "data": "approver"},
            { "data": "status"}
        ],
		 "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "name",
		    	  	"render": function(data, type, user, meta) {
			        	  var result = '';
				          if(user.name !=null && user.name !='' && user.name !='undefined'){
								 result = result + user.name;
				          }else{
								result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
							}
				              return result;
				          		
				          }
		    	  	
		      },{
		        "targets": colIndex++,
		        "data": "employmentType",
		        "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  if(user.employmentType !=null && user.employmentType !='' && user.employmentType !='undefined'){
							 result = result + user.employmentType;
			          }else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
		        	 return result;
		        }
		      }, {
		          "targets": colIndex++,
		          "data": "leaveTypeName",
		          "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  if(user.leaveTypeName !=null && user.leaveTypeName !='' && user.leaveTypeName !='undefined'){
							 result = result + user.leaveTypeName;
			          }else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
		        	  return result;
		          }
		        }, {
		            "targets": colIndex++,
		            "data": "type",
		            "sortable": false,
		            "render": function ( data, type, user, meta ) {
		            	var result = '';
		            	 if(user.leaveTypeBillable !=null && user.leaveTypeBillable !='' && user.leaveTypeBillable !='undefined'){
							 result = result + user.leaveTypeBillable;
			          }else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
			              return result;
		            }
		      },
		      {
		          "targets": colIndex++,
		          "data": "duration",
		          "sortable": false,
		          "render": function ( data, type, user, meta ) {
		          	var result = '';
		          	if(user.duration !=null && user.duration !='' && user.duration !='undefined'){
						 result = result + user.duration;
		          }else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
			              return result;
		          }
		    },
		    {
		        "targets": colIndex++,
		        "data": "leavePeriod",
		        "sortable": false,
		        "render": function ( data, type, user, meta ) {
		        	var result = '';
		        	if(user.fromDateStr !=null && user.fromDateStr !='' && user.fromDateStr !='undefined'){
						 result = result + user.fromDateStr+'To'+user.toDateStr;
		          }else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
		              return result;
		        }
		    },
		    {
			          "targets": colIndex++,
			          "data": "reason",
			          "sortable": false,
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
			          	if(user.reason !=null && user.reason !='' && user.reason !='undefined'){
							 result = result + user.reason;
			          }else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
				        return result;
			          }
			}, {
		          "targets": colIndex++,
		          "data": "approver",
		          "sortable": false,
		          "render": function ( data, type, user, meta ) {
		          	var result = '';
		          	if(user.approver !=null && user.approver !='' && user.approver !='undefined'){
						 result = result + user.approver;
		          }else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
			        return result;
		          }
		},{
			          "targets": colIndex++,
			          "data": "status",
			          "sortable": false,
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
			          	if(user.status !=null && user.status !='' && user.status !='undefined'){
							 result = result + user.status;
			          }else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
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
    $("#dynamicDaywiseLeaveReportsListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}

$("#daywiseReportDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getDaywiseLeaveReportList();
	   }
	});
</script>


<script>
function viewCompleteTemplate(temp){
	var templateUUID = $("#empTemplateId").val();
	var toAddress = $("#toAddress").val();
	var employeeId = $("#hiddenUserId").val();
	var winPrint = window.open(sourceleadUrl+'/previewCompleteTemplateByTemplateUUID?&templateUUID='+templateUUID+'&toAddress='+toAddress+'&employeeId='+employeeId,'_newtab');
	if(temp == 'print')
		winPrint.print();
}
</script> 


<script>
 $( function() {
   /*  $("#fromDate").datepicker(
		{changeMonth: true});
	 $("#toDate").datepicker(
	 {changeMonth: true}); */
	 
	 
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
	 
	 $( "#fromDate" ).datepicker({
	     dateFormat : finalDateFormatEndDate,
	      changeMonth: true,
	      changeYear: true,
// 	      minDate: new Date() ,
	      onSelect : function(selected) {
	      var dt = $(this).datepicker('getDate', '+1d');
	      dt.setDate(dt.getDate());
	      $("#toDate").datepicker("option", "minDate", dt);
	       removeStartValid();
	      } 

	});
	 
	 
  });
 
</script> 
<script>
	function searchThroughDates(){
		alert("222##");
		var fromDate = $("#fromDate").val();
		
		$('#fromDateDiv').empty();
		
		if(fromDate == null || fromDate == '' || fromDate == 'undefined'){
			$("#fromDateDiv").append("Start Date is Required") ; 
		}
		
		if(fromDate != null && fromDate != '' && fromDate != 'undefined'){
			getDaywiseLeaveReportList(fromDate)
		}
	}
	
	
	function removeStartValid(){
		$('#fromDateDiv').empty();
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
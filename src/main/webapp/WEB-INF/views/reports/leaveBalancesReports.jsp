<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	getDaywiseLeaveReportList();
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
          <div class="remove_search pull-right">
            <input type="text" placeholder="Search"/>
          </div>
        </div>
        <div class="require_wrap_table tableFirstSecond clearfix">
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
        </div>
        <nav>
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
        </nav>
      </div>
    </div>
  </section>


<script>
function getDaywiseLeaveReportList(){
	var fromDate = $("#fromDate").val();
	var searchValue = $("#employeeDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getDaywiseLeaveReportList?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate;
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
        	{ "data": "employeeID" },
            { "data": "name" },
            { "data": "email"},
            { "data": "mobilePhone"},
            { "data": "employmentType"},
            { "data": "fromDate"},
            { "data": "toDate"},
            { "data": "designation"},
            { "data": "actions"}
        ],
		 "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "employeeID",
		    	  	"render": function(data, type, user, meta) {
		    	  		var result = '';
		    	  		 if(user.employeeId !=null && user.employeeId !='' && user.employeeId !='undefined'){
								result = result + ' <td><div class="tbldata-dotdot-100" title = "'+user.employeeId+'">'+user.employeeId+'</div></td>';
							}else{
								result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
							}
			             return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
		          "data": "name",
		          "render": function ( data, type, user, meta ) {
		        	  var result = '';
		          if(user.userName !=null && user.userName !='' && user.userName !='undefined'){
						 result = result +" <a href='<c:url value='/app/userSummary/"+user.userCode+"'/>'' title = '"+user.userName+"' class='UserTextLeft'>"+user.userName+"</a>";
		          }else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
		              return result;
		          		
		          }
		          },{
		        "targets": colIndex++,
		        "data": "email",
		        "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  if(user.email !=null && user.email !='' && user.email !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-vsm" title = "'+user.email+'">'+user.email+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-vsm">--</div></td>';
						}
		        	 return result;
		        }
		      }, {
		          "targets": colIndex++,
		          "data": "mobilePhone",
		          "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  if(user.mobilePhone !=null && user.mobilePhone !='' && user.mobilePhone !='undefined'){
							result = result + ' <td><div class="tblminwidth-100" title = "'+user.mobilePhone+'">'+user.mobilePhone+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-100">--</div></td>';
						}

		        	  return result;
		          }
		        }, {
		            "targets": colIndex++,
		            "data": "employmentType",
		            "sortable": false,
		            "render": function ( data, type, user, meta ) {
		            	var result = '';
 		            	if(user.employmentType !=null && user.employmentType !='' && user.employmentType !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+user.employmentType+'">'+user.employmentType+'</div></td>';
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
		          "render": function ( data, type, user, meta ) {
		          	var result = '';
		          	if(user.dateofJoin !=null && user.dateofJoin !='' && user.dateofJoin !='undefined'){
						result = result + ' <td><div class="tblminwidth-100" title = "'+user.dateofJoin+'">'+user.dateofJoin+'</div></td>';
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
		        "render": function ( data, type, user, meta ) {
		        	var result = '';
		        	 if(user.dateOfTermination !=null && user.dateOfTermination !='' && user.dateOfTermination !='undefined'){
						result = result + ' <td><div class="tbldata-dotdot" title = "'+user.dateOfTermination+'">'+user.dateOfTermination+'</div></td>';
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
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
			         	if(user.designation !=null && user.designation !='' && user.designation !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+user.designation+'">'+user.designation+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						} 
				        return result;
			          }
			},{
			          "targets": colIndex++,
			          "data": "actions",
			          "sortable": false,
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
					       result = result + '      <td><div class="tblminwidth-75 text-center">             ';
				           result = result + '      <div class="positionRelative actionList-vdse-div">';
				           result = result + '        <ul class="list-unstyled list-inline clearfix actionList-vdse">';
				           result = result + '          <li><a href="javascript:void(0)" title="Print" class="fa fa-print" onClick="printEVR();commonview('+user.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="Download" class="fa fa-download" onClick="downloadEVR();commonview('+user.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="Share" class="fa fa-share-alt" onClick="shareEVR();commonview('+user.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="View" class="fa fa-eye" onClick="viewEVR();commonview('+user.employeeId+')"></a></li>';
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
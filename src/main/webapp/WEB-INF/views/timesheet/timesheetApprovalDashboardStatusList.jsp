<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="/resources/js/jquery.simplePagination.js"></script>

<!--###############################  Inner Jsp for Common operation in approval and dashboardcountlist page ############################### -->
<jsp:include page="timesheetApprovalCommonScripts.jsp" />



<div id="TimeshgeetStatusListId"></div>
 <body  onclick="bodyClick();">
 
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <input type="hidden" id="timesheetPageStatus" value="${status}"/>
            <h2> ${status == 'SUBMITTED' ? 'Waiting For Approval' : (status == 'APPROVED' ? 'Approved List' :(status == 'REJECTED' ? 'Rejected List' :(status == 'DRAFT' ? 'Draft List' :'Timesheet Approval')))}</h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  ${status == 'SUBMITTED' ? 'Wait For Approval' : (status == 'APPROVED' ? 'Approved List' :(status == 'REJECTED' ? 'Rejected List' :(status == 'DRAFT' ? 'Draft List' :'Timesheet Approval')))}</div>
            <div class="clearfix"></div>
        </section>
	<div class="timesheet-content">
	<div class="timesheet_out">
<div id="TimeshgeetStatusListIdLoading"></div>
			<div class="timesheet_appr_wrap">
			 
			<div class="timesheet_head">
			<div class="pull-left ml mt5" id="hedderAcceptRejectButton" style="display: none"><input class="btn btn-success btn-sm" type="button" value="Approve" onclick="acceptTimeSheetSubmissionBySubmissionId('dashboard');"><input class="btn btn-danger btn-sm ml10" type="button" value="Reject" onclick="rejectTimeSheetSubmissionBySubmissionId('dashboard');"></div>
	        	 <ul>
	        	 	<li>&nbsp;</li>
	            </ul> 
	<div class="remove_search">
       					<input type="text" placeholder="Search..." value="" id="dataTableSearchExpenses123">
        			</div>	            
	            <div class="clearfix"></div>
	        </div>
	        <div id="rejectedPopup"></div> 
			<div class="timesheet_appr_table datatable_filter pt5" id="tableDivId">
					<table id='example'  style='width:100%' >
						 <thead id="timesheetApprHead">                                
						    <tr> 
						        <c:if test="${status eq 'SUBMITTED'}">
						        	<th><input type="checkbox" id="selectAllTimeSheetCheckBoxForSubmitForApproval"></th>
						        </c:if>
						        <c:if test="${status ne 'SUBMITTED'}">
						        	<th><input type="checkbox" disabled="disabled"></th>
						        </c:if>                          
						        <th>Employee</th>              
						        <th>Project</th>               
						        <th>Client</th>                
						        <th>Frequency</th>             
						        <th>Date</th>                  
						        <th>Total</th>                 
						        <th>Stage</th>                 
						        <th>Action</th>                
						    </tr>                              
						   </thead>   
						   <!--###############################  list of assigned projectmembers for to fill/approve/reject their timesheets ############################### -->                            
							<tbody id="timesheetApprList">
								<jsp:include page="timesheetDashboardTableData.jsp" />
						   </tbody>                              
				</table>
		    </div>
		<div id= "paginationDiv" class="clearfix">
	<div class="col-md-6">
		<div class="pull-left mt5">
		    <input type="hidden" id="currentPageNumber" />
			<label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
				<div class="dropdown-select pull-left mr5 tblminwidth-50">
					<select id="pageSize" onchange="getTableDataByPageSize()">
						<option value="10" ${timesheetApprovalDTO.pageSize == 10 ? 'selected' : ''}>10</option>
		  				<option value="15" ${timesheetApprovalDTO.pageSize == 15 ? 'selected' : ''}>15</option>
		 				<option value="20" ${timesheetApprovalDTO.pageSize == 20 ? 'selected' : ''}>20</option>
					</select>
				</div> <span class="pull-left mt10">entries</span> </label>
				 <span class="pull-left mt10 ml" id="pageMsg"></span>
		</div>
	</div>
	<div id="paginationholder" class="col-md-6 pagiPad">
		<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
	</div>
</div>
		    
		</div>
		
	</div>
	</div>
	
</section>
</body>


<script type="text/javascript">
window.onload = function () {
	getTimesheetApprovalListByStatus();
 };
</script>

<script>
	$("#dataTableSearchExpenses123").keyup(function(event) {
	    if (event.keyCode === 13) {
	    	var searchData = $('#dataTableSearchExpenses123').val();
	    	getStatusListData(1,0,searchData)
	    	$('#dataTableSearchExpenses123').val(searchData);
	    }
	});
</script>
 <script type="text/javascript">
function getTimesheetApprovalListByStatus(){
	var status = '${status}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 var pageSize = $("#pageSize").val();
		$("#TimeshgeetStatusListIdLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getTimesheetListByStatus?${_csrf.parameterName}=${_csrf.token}&dashboardTimeStatus="+status,
		        success: function(data) {
		        	   if(data != null && data != 'undefined' && data != ""){
			                	 $("#timesheetApprList").empty();
			                     $("#timesheetApprList").append(data); 
			                     	$(".checkBoxForSubmitForApproval").click(function () {
				             	    	selectedRecords[$(this).val()] = $(this).is(":checked");
				             	    });
			                     $("#TimeshgeetStatusListIdLoading").empty();
			                     $("#TimeshgeetStatusListIdLoading").removeAttr('style');
			                     $("#timesheetApprHead").removeAttr('style');
			                     preparePagination(1);
			                     
			                     
		                 }
		                
		        },
		    });
		} 
</script>



<script>		
	function getTableDataByPageSize(){
		  getStatusListData(1,0,"");
		}
	
				function getStatusListData(pageNo,noOfRecords,searchValue){
					 var status = '${status}';
					 var sourceleadUrl ='${sourceleadUrl}';
					 var pageSize = $("#pageSize").val();
					 var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
					if(searchValue == null || searchValue == '' || searchValue == 'undefined'){
						searchValue = '';
					}
					$("#TimeshgeetStatusListIdLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
					$.ajax({
						    	type: "POST",
						        url: sourceleadUrl+"/getTimesheetListByStatus?${_csrf.parameterName}=${_csrf.token}&dashboardTimeStatus="+status+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&totalRows="+noOfRecords+"&searchValue="+searchValue,
						        success: function(data) {
						        	   if(data != null && data !='undefined' && data != ''){
							                	 $("#timesheetApprList").empty();
							                     $("#timesheetApprList").append(data); 
							                     $("#selectAllTimeSheetCheckBoxForSubmitForApproval").removeProp('checked');
							                     $(".checkBoxForSubmitForApproval").each(function() {
							                    	 for(var e in selectedRecords) {
							                    		 if($(this).val() === e) {
							                    			 if(selectedRecords[e])
							                    			 	$(this).prop('checked', selectedRecords[e]);
							                    			 break;
							                    		 }
							                    	 }
							         	        });

							             	    $(".checkBoxForSubmitForApproval").click(function () {
							             	    	selectedRecords[$(this).val()] = $(this).is(":checked");
							             	    });
							                     $("#TimeshgeetStatusListIdLoading").empty();
							                     $("#TimeshgeetStatusListIdLoading").removeAttr('style');
							                     $("#timesheetApprHead").removeAttr('style');
							                     preparePagination(pageNo);
							                     
						                 }
						                
						        },
						    });
						} 
	
		function preparePagination(pageNo) {
				  var totalRows =  $('#totalRows').val();
				  var pageCount = $('#pageCount').val();
				  var pageSize = $("#pageSize").val();
				  $("#currentPageNumber").val(pageNo);
				  var searchData = $('#searchData').val();
			      if(totalRows !=null && totalRows != '' && totalRows != 'undefined' && totalRows != 0){
					  $('#paginationDiv').show();
					  $('#dataTableSearchExpenses123').val('') ;
					  $('#dataTableSearchExpenses123').val(searchData); 
					  var startingRecord = parseInt((pageNo*pageSize-pageSize)+1);
					  var endingRecord = parseInt(pageNo*pageSize);
					  if(endingRecord > totalRows){
						  endingRecord = totalRows;
					  }
				    var options = {
						      currentPage:pageNo,
						      items: totalRows,
				              itemsOnPage: pageSize,
				              ellipsePageSet :false,
				              cssStyle: 'light-theme',
				               onPageClick: function (pageNumber, event) {
				            	   getStatusListData(""+pageNumber+"",""+totalRows+"",searchData);
				              }
			      };
			      $("#pagination-demo").pagination(options);
				    
				    var pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
			}else{
				  $("#timesheetApprHead").removeAttr('style');
				  $("#timesheetApprHead").attr('style','display:none;');
				  $("#timesheetApprList").empty();
				  $("#timesheetApprList").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
				  $('#dataTableSearchExpenses123').val('') ;
				  $('#dataTableSearchExpenses123').val(searchData);
				  $('#paginationDiv').hide();
				  
				  
			}
			    $('#pageMsg').html('');
			    $('#pageMsg').html(pageMsg);
			}

</script>


 <script>
 var selectedRecords = {};
  $(document).ready(function () {
	    $("#selectAllTimeSheetCheckBoxForSubmitForApproval").click(function () {
	        $(".checkBoxForSubmitForApproval").prop('checked', $(this).prop('checked'));
	        $(".checkBoxForSubmitForApproval").each(function() {
	        	selectedRecords[$(this).val()] = $(this).is(":checked");
	        });
	        displayAcceptRejectButtonOnHedder();
	    });
	});
 </script>
 <script type="text/javascript">
function displayAcceptRejectButtonOnHedder() {
	if($('[name="checkBoxForSubmitForApproval"]:checked').length > 0){
		$('#hedderAcceptRejectButton').removeAttr('style');
	}else{
		$('#hedderAcceptRejectButton').attr('style','display:none');
	}
	
}
</script>

<jsp:include page="../masterFooter.jsp" />








 
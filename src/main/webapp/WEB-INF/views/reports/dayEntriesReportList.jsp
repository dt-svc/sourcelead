<%-- <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" /> --%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/resources/js/jquery.simplePagination.js"></script>


<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Daywise Timesheet Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> » <a href="${SourceLeadWeb_sourceleadUrl}/daywiseTimeSheetReports">Daywise Timesheet Report</a> » Daywise Timesheet Report List</div>
    </section>
    <div id="loadingId"></div>
    <div class="clearfix"></div>
      <div class="tablelist-wrap">
        <div class="timesheetHead minHeight40 mt minHeight40">
          <div class="col-md-6">
            <div class="row tableborder-txt">
              <div class="col-md-6">
                <div class="row">
                  <label class="col-md-5 paddingRight">From Date</label>
                  <div class="col-md-6 paddingLeft">${startDate}</div>
                </div>
                <div class="row mt2">
                  <label class="col-md-5 paddingRight">To Date</label>
                  <div class="col-md-6 paddingLeft">${endDate}</div>
                </div>
              </div>
              <%-- <div class="col-md-6">
                <div class="row">
                  <label class="col-md-5 paddingRight">Billable Hrs</label>
                  <div class="col-md-6 paddingLeft">${totalBillable}</div>
                </div>
                <div class="row mt2">
                  <label class="col-md-5 paddingRight">No-Billable Hrs</label>
                  <div class="col-md-6 paddingLeft">${totalNonBillable}</div>
                </div>
              </div> --%>
            </div>
          </div>
          <div class="pull-right mt10">
            <ul class="list-unstyled list-inline">
              <li class="hidden"><i class="fa fa-download"></i> </li>
              <li><a href="javascript:void(0)" class="text-danger" title="Download PDF" onclick="runExport('pdf');"><i class="fa fa-file-pdf-o"></i></a></li>
              <li><a href="javascript:void(0)" class="text-success" title="Download Excel" onclick="runExport('excel');"><i class="fa fa-file-excel-o"></i></a></li>
              <li>
                <!-- <div class="dynamic_search mr10">
                 		 <input type='text' placeholder="Search" value="" id='searchEntryData'/>
                  <i class="fa fa-search"></i> </div> -->
              </li>
            </ul>
          </div>
        </div>
        <div class="clearfix"></div>
        <div class="styleTables overflow_Auto datatable_filter">
          <table id="dayEntriesTable">
            <thead>
              <tr>
                <th>Date</th>
                <th>Employee Id</th>
                <th>Employee Name</th>
                <th>Employee Email</th>
                <th>Ticket No</th>
                <th>Project Name</th>
                <th>Task</th>
                <th>Billable<br>Hrs</th>
                <th>Non<br>Billable<br>Hrs</th>
                <th>Status</th>
                <!-- <th>Submitted On</th>
                <th>Approved On</th> -->
              </tr>
            </thead>
            <tbody>
            <c:forEach var="entryList" items="${dayWiseTimesheetReoprtsList}">
	            <tr>
	                <td><div class="tblminwidth-75">${entryList.timesheetDate}</div></td>
	                <td><div class="tbldata-dotdot-vsm" title="${entryList.employeeId}">${entryList.employeeId != null && entryList.employeeId != '' ? entryList.employeeId : "----"}</div></td>
	                <td><div class="tbldata-dotdot-vsm" title="${entryList.employeeName}">${entryList.employeeName}</div></td>
	                <td><div class="tbldata-dotdot-vsm" title="${entryList.employeeEmail}">${entryList.employeeEmail}</div></td>
	                <td><div class="tblminwidth-75">${!empty entryList.ticketNo ? entryList.ticketNo : '--'}</div></td>
	                <td><div class="tbldata-dotdot-100" title="${!empty entryList.projectName ? entryList.projectName : ''}">${!empty entryList.projectName ? entryList.projectName : '--'}</div></td>
	                <td><div class="tbldata-dotdot-100" title="${entryList.task}">${entryList.task}</div></td>
	                <td><div class="tblminwidth-50">${!empty entryList.billableHrs ? entryList.billableHrs : '--'}</div></td>
	                <td><div class="tblminwidth-50">${!empty entryList.nonBillableHrs ? entryList.nonBillableHrs : '--'}</div></td>
	                <td><div class="tblminwidth-50" title="${!empty entryList.status ? entryList.status : ''}">${!empty entryList.status ? entryList.status : '--'}</div></td>
	               <%--  <td><div class="tblminwidth-50" title="${entryList.submittedOn}">${entryList.submittedOn}</div></td>
	                <td><div class="tblminwidth-50" title="${entryList.approvedOn}">${entryList.approvedOn}</div></td> --%>
	              </tr>
            </c:forEach>
              
            </tbody>
          </table>
        <div id= "paginationDiv" class="borTop clearfix">
			<div class="col-md-6">
				<div class="pull-left mt5">
					<label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
						<div class="dropdown-select pull-left mr5 tblminwidth-50">
							<select id="pageSize" onchange="getTableDataByPageSizeEmployee()">
								<option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
								<option value="15" ${pageSize == 15 ? 'selected' : ''}>15</option>
								<option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
							</select>
						</div> 
						 <span class="pull-left mt10">entries</span> </label>
						 <span class="pull-left mt10 ml" id="pageMsg"></span>
				</div>
			</div>
	<div id="paginationholder" class="col-md-6 pagiPad">
		<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
	</div>
</div>
      <div style="display: none;">
      	<input type="hidden" value="${exportDto.projectIds}" id="projectIdsDto">
      	<input type="hidden" value="${exportDto.employmentType}" id="employmentTypeDto">
      	<input type="hidden" value="${exportDto.projectMemberListId}" id="projectMemberListIdDto">
      	<input type="hidden" value="${exportDto.startDate}" id="startDateDto">
      	<input type="hidden" value="${exportDto.endDate}" id="endDateDto">
      	<input type="hidden" id="totalRows" name="totalRows" value="${totalRows}">
	    <input type="hidden" id="pageCount" name="totalRowsPage" value="${pageCount}">
      </div>
    </div>  
  </section>
<script>
$("#searchEntryData").keyup(function(event) {
    if (event.keyCode === 13) {
    	$('#searchEntryData').parent().find('p.jquery_form_error_message').remove();
    	var searchData = $('#searchEntryData').val();
    	var regex =  new RegExp(/[\\]/g);
    	if(regex.test(searchData) == false){
    		getTableDataTimeSheetReports(1);
    	}else{
    		$("#searchEntryData").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
    	}
    	$('#searchEntryData').val(searchData);
    }
});
</script>

<script>
function getTableDataByPageSizeEmployee(){
	getTableDataTimeSheetReports(1);
}
</script> 

  <script>
  function runExport(type){
	  var projectIds  = ''
	  var employmentType  = '';
	  var projectMemberList  = '';
	  var projectIdDto = $('#projectIdsDto').val();
	  var employmentTypeDto = $('#employmentTypeDto').val();
	  var projectMemberListIdDto = $('#projectMemberListIdDto').val();
	  var startDateDto = $('#startDateDto').val();
	  var endDateDto = $('#endDateDto').val();
	  var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	  
	  if(projectIdDto != null && projectIdDto !='' && projectIdDto !='undefined'){
		  projectIds  =  JSON.parse(projectIdDto);  ;
	  }
	  if(employmentTypeDto != null && employmentTypeDto !='' && employmentTypeDto	!='undefined'){
		   employmentType  = employmentTypeDto;
	  }
	  if(projectMemberListIdDto != null && projectMemberListIdDto !='' && projectMemberListIdDto !='undefined' &&  projectMemberListIdDto!='[]'){
		  projectMemberList  = JSON.parse(projectMemberListIdDto);
	  }
	  var startDate = startDateDto;
	  var endDate = endDateDto;
	  var exportType = type;
		
		if(startDate != null && startDate !='' && startDate !='undefined' && endDate != null && endDate !='' && endDate !='undefined'){
			window.location.href = sourceleadUrl+"/exportDaywiseTimesheetReport?&startDate="+startDate+"&endDate="+endDate+"&projectIds="+projectIds+"&exportType="+exportType+"&employmentType="+employmentType+"&projectMemberListId="+projectMemberList;
		}
	}
 </script>
 
 <script>
function getTableDataTimeSheetReports(pageNo){
	 var projectIds  = ''
		  var employmentType  = '';
		  var projectMemberList  = '';
		  var projectIdDto = $('#projectIdsDto').val();
		  var employmentTypeDto = $('#employmentTypeDto').val();
		  var projectMemberListIdDto = $('#projectMemberListIdDto').val();
		  var startDateDto = $('#startDateDto').val();
		  var endDateDto = $('#endDateDto').val();
		  var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';  
		  if(projectIdDto != null && projectIdDto !='' && projectIdDto !='undefined'){
			  projectIds  =  JSON.parse(projectIdDto);  ;
		  }
		  if(employmentTypeDto != null && employmentTypeDto !='' && employmentTypeDto	!='undefined'){
			   employmentType  = employmentTypeDto;
		  }
		  if(projectMemberListIdDto != null && projectMemberListIdDto !='' && projectMemberListIdDto !='undefined' &&  projectMemberListIdDto!='[]'){
			  projectMemberList  = JSON.parse(projectMemberListIdDto);
		  }
		  var startDate = startDateDto;
		  var endDate = endDateDto;
		  var pageSize = $("#pageSize").val();
		  var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
	      var searchData = $("#searchEntryData").val();
	      if(searchData != null && searchData !='' && searchData!='undefined'){
	    	searchValue = searchData;
	      }else{
	    	searchValue = '';
	      }
		  $.ajax({
	            url: sourceleadUrl+"/dayEntriesReportList?&startDate="+startDate+"&endDate="+endDate+"&projectIds="+projectIds+"&employmentType="+employmentType+"&projectMemberListId="+projectMemberList+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&searchValue="+searchValue,
	            type: "POST",
	            success: function (data) {
	            	 $("#dayEntryformData").hide();
	            	 $("#dayWiseExportListDiv").show();
	            	 $("#expensesExportListDiv").empty();
	    			 $("#expensesExportListDiv").append(data);
	    			 $("#loadingId").removeAttr('style');
	    			 preparePaginationEmployee(pageNo);
	            }
	        });
		  
}

function preparePaginationEmployee(pageNo) {
	var totalRows =  $('#totalRows').val();
	  var pageMsg = '';
	  var searchData = '${searchValue}';
	  if(totalRows !=null && totalRows != '' && totalRows != 'undefined' && totalRows > 0){
		  $('#paginationDiv').show();
		  var pageCount = $('#pageCount').val();
		  var pageSize = $("#pageSize").val();
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
		            	  $("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		            	  getTableDataTimeSheetReports(""+pageNumber+"");
		              }
	      };
	      $("#pagination-demo").pagination(options);
	      pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
	  }else{
		  $("#dayWiseExportListDiv").empty();
  	  	  $("#dayWiseExportListDiv").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
		 /*  $('#searchEntryData').val('') ;
		  $('#searchEntryData').val(searchData); */
		  $('#paginationDiv').hide();
	  } 
    $('#pageMsg').html('');
    $('#pageMsg').html(pageMsg);
}





var validationSettings = {
    errorMessagePosition : 'element',
    borderColorOnError : '',
	scrollToTopOnError : true,
	dateFormat : 'yyyy/mm/dd' 
};

</script>
  

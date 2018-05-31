<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Project</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Project</div>
    </section>
    
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
              <div class="col-md-6">
                <div class="row">
                  <label class="col-md-5 paddingRight">Billable Hrs</label>
                  <div class="col-md-6 paddingLeft">${totalBillable}</div>
                </div>
                <div class="row mt2">
                  <label class="col-md-5 paddingRight">No-Billable Hrs</label>
                  <div class="col-md-6 paddingLeft">${totalNonBillable}</div>
                </div>
              </div>
            </div>
          </div>
          <div class="pull-right mt10">
            <ul class="list-unstyled list-inline">
              <li class="hidden"><i class="fa fa-download"></i> </li>
              <!-- <li><a href="javascript:void(0)" class="text-danger" title="Download PDF" onclick="runExport('pdf');"><i class="fa fa-file-pdf-o"></i></a></li> -->
              <li><a href="javascript:void(0)" class="text-success" title="Download Excel" onclick="runExport('excel');"><i class="fa fa-file-excel-o"></i></a></li>
              <li>
                <div class="dynamic_search mr10">
                  <input placeholder="Search" type="text">
                  <i class="fa fa-search"></i> </div>
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
                <th>Employee Name</th>
                <th>Employee Email</th>
                <th>Ticket No</th>
                <th>Project Name</th>
                <th>Task</th>
                <th>Billable<br>
                  Hrs</th>
                <th>Non<br>
                  Billable<br>
                  Hrs</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="entryList" items="${dayWiseTimesheetReoprtsList}">
	            <tr>
	                <td><div class="tblminwidth-75">${entryList.timesheetDate}</div></td>
	                <td><div class="tbldata-dotdot-vsm" title="Srinivas Lankalapalli">${entryList.employeeName}</div></td>
	                <td><div class="tbldata-dotdot-vsm" title="srininivas.l@businessintelli.com">${entryList.employeeEmail}</div></td>
	                <td><div class="tblminwidth-75">${entryList.ticketNo}</div></td>
	                <td><div class="tbldata-dotdot-100" title="Srinivas Lankalapalli">${entryList.projectName}</div></td>
	                <td><div class="tbldata-dotdot-100" title="Srinivas Lankalapalli">${entryList.task}</div></td>
	                <td><div class="tblminwidth-50">${entryList.billableHrs}</div></td>
	                <td><div class="tblminwidth-50">${entryList.nonBillableHrs}</div></td>
	                <td><div class="tblminwidth-50">${entryList.status}</div></td>
	              </tr>
            </c:forEach>
              
            </tbody>
          </table>
        </div>
        <!-- <nav class="clearfix">
          <ul id="paging" class="pagination pull-right mr">
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
  </section>
  <!-- Content Area Ends--> 
  <jsp:include page="../masterFooter.jsp" />

<script>  
$(document).ready(function() {
    $('#dayEntriesTable').DataTable({
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"aaSorting": [[1,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    });
});
</script> 

  <script>
  function runExport(type){
	  if('${exportDto.projectIds}' != null && '${exportDto.projectIds}' !='' && '${exportDto.projectIds}' !='undefined'){
		  var projectIds  = ${exportDto.projectIds};
	  }else{
		  var projectIds  = '';  
	  }
	  if('${exportDto.employmentType}' != null && '${exportDto.employmentType}' !='' && '${exportDto.employmentType}'	!='undefined'){
		  var employmentType  = '${exportDto.employmentType}';
	  }else{
		  var employmentType  = '';  
	  }
	  if('${exportDto.projectMemberListId}' != null && '${exportDto.projectMemberListId}' !='' && '${exportDto.projectMemberListId}'	!='undefined'){
		  var projectMemberList  = ${exportDto.projectMemberListId};
	  }else{
		  var projectMemberList  = '';  
	  }
		var templateType = "";
		var startDate = '${exportDto.startDate}'.toString();
		var endDate = '${exportDto.endDate}'.toString();
		var exportType = type;
		
		if(startDate != null && startDate !='' && startDate !='undefined' && endDate != null && endDate !='' && endDate !='undefined'){
			window.location.href = sourceleadUrl+"/exportDaywiseTimesheetReport?&startDate="+startDate+"&endDate="+endDate+"&projectIds="+projectIds+"&exportType="+exportType+"&employmentType="+employmentType+"&projectMemberListId="+projectMemberList;
		}
	}
 </script>
  

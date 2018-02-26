<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
$(document).ready(function(){
	var orgRelationUUID = '${orgRelationUUID}';
	var orgSpocUUID = '${orgSpocUUID}';
	var invoiceType = '${invoiceType}';
	getClientreportSummary(orgRelationUUID,orgSpocUUID,invoiceType);
});
</script>

<section class="content-wraper">
   
    <section class="content-header clearfix">
    
     <c:if test="${type == 'byClient'}">
      <div class="pull-left">
        <h2>Client Report: <span class="text-muted" id="clientNameId"></span></h2>
      </div>
<!--       <div class="pull-right brud-crum">Home &raquo; Client Report</div> -->

       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="${appPath}/perfomanceClientReports">Client Report</a></div>
      
      </c:if>
       <c:if test="${type == 'byContact'}">
      <div class="pull-left">
        <h2>Contact Report: <span class="text-muted" id="clientContactNameId"></span></h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Contact Report</div>
      </c:if>
    </section>
    
    <div class="content-area">
      <div class="clearfix row">
        <div class="col-md-3">
          <h4 class="text-center text-muted">Receivables</h4>
          <div class="vendorrep_div">
            <div class="vendorrep_innerdiv positionRelative text-center">
              <h2 class="text-danger"><span id="totalRecevableAmount">0</span></h2>
<!--               <p class="mt5 clearfix vendorrep-count">No. of Paid Bills:<span class="paddingLeft" id="totalPaidCount">0</span></p> -->
            </div>
            <ul class="list-unstyled vendorrepList">
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Draft</a></div>
                <span class="pull-right" id="draftAmountId"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
				  <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Open</a></div>
                <span class="pull-right" id="openAmountId"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
				  <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Overdue</a></div>
                <span class="pull-right" id="overdueAmountId"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
				  <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Paid</a></div>
                <span class="pull-right" id="paidAmountId"><b class="paddingRight text-muted"></b>0</span> </li>
            </ul>
          </div>
        </div>
        <div class="col-md-3">
          <h4 class="text-center text-muted">Contracts</h4>
          <div class="vendorrep_div">
            <div class="vendorrep_innerdiv positionRelative text-center">
              <h2 class="text-info"><span id="finalizedCount">0</span></h2>
<!--               <p class="mt5 clearfix vendorrep-count">Total Contracts:<span class="paddingLeft">550</span></p> -->
            </div>
            <ul class="list-unstyled vendorrepList">
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Draft</a></div>
                <span class="pull-right" id="draftCount"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Waiting</a></div>
                <span class="pull-right" id="waitingCount"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Sign</a></div>
                <span class="pull-right" id="signCount"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Counter Sign</a></div>
                <span class="pull-right" id="counterSignCount"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Expired</a></div>
                <span class="pull-right" id="expiredCount"><b class="paddingRight text-muted"></b>0</span> </li>
              <li class="clearfix">
				  <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Rejected</a></div>
                <span class="pull-right" id="rejectedCount"><b class="paddingRight text-muted"></b>0</span> </li>
            </ul>
          </div>
        </div>
        <div class="col-md-3">
          <h4 class="text-center text-muted">Hired</h4>
          <div class="vendorrep_div">
            <div class="vendorrep_innerdiv positionRelative text-center">
              <h2 class="text-success"><span id="hiredCount">0</span></h2>
<!--               <p class="mt5 clearfix vendorrep-count">Total Hired:<span class="paddingLeft">750</span></p> -->
            </div>
            <ul class="list-unstyled vendorrepList">
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Applicants</a></div>
                <span class="pull-right" id="applicantCountID">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Shortlist</a></div>
                <span class="pull-right" id="shortListCountID">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Submit To Client</a></div>
                <span class="pull-right" id="submittoClientCountID">0</span> </li>
                <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Interviewed</a></div>
                <span class="pull-right" id="interviewdCountID">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Offered</a></div>
                <span class="pull-right" id="offeredCountID">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Offer Accepted</a></div>
                <span class="pull-right" id="offerAcceptedCountID">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Rejected</a></div>
                <span class="pull-right" id="rejectedCountID">0</span> </li>
            </ul>
          </div>
        </div>
        <div class="col-md-3">
          <h4 class="text-center text-muted">Jobs</h4>
          <div class="vendorrep_div">
            <div class="vendorrep_innerdiv positionRelative text-center">
              <h2 class="text-warning"><span id="totalPostedCount">0</span></h2>
<!--               <p class="mt5 clearfix vendorrep-count">Total Assigned Jobs:<span class="paddingLeft">100</span></p> -->
            </div>
            <ul class="list-unstyled vendorrepList">
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Open</a></div>
                <span class="pull-right" id="openCount">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">On Hold</a></div>
                <span class="pull-right" id="onholdCount">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Cancel</a></div>
                <span class="pull-right" id="cancelledCount">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Filled Closed</a></div>
                <span class="pull-right" id="filledCount">0</span> </li>
              <li class="clearfix">
                <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">UnFilled Closed</a></div>
                <span class="pull-right" id="unfilledCount">0</span> </li>
              <li class="clearfix">
				  <div class="tblminwidth-175 pull-left"><a href="javascript:void(0)">Closed</a></div>
                <span class="pull-right" id="closedCount">0</span> </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="clearfix mb"></div>
      <div class="project_wrap_table mb hidden">
        <h4 class="header-align">Top 10 Vendors</h4>
        <table width="100%" cellpadding="0" cellspacing="0" class="mt10">
          <thead>
            <tr>
              <th>Vendor Name</th>
              <th>Jobs</th>
              <th>Submission</th>
              <th>Hired</th>
              <th>Contracts</th>
              <th>Expenses</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><div class="tbldata-dotdot-me">Vendor Name</div></td>
              <td><div class="tbldata-dotdot-100">Jobs</div></td>
              <td><div class="tbldata-dotdot-100">Submission</div></td>
              <td><div class="tbldata-dotdot-100">Hired</div></td>
              <td><div class="tbldata-dotdot-100">Contracts</div></td>
              <td><div class="tbldata-dotdot-100">Expenses</div></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="clearfix mb"></div>
    </div>
  </section>
  
  
  <script>
  function getClientreportSummary(orgRelationUUID,orgSpocUUID,invoiceType){
	  
	  var url = "";
	  if(orgRelationUUID != null && orgRelationUUID != '' && orgRelationUUID != 'undefined'){
		  url += sourceleadUrl+"/getClientreportSummary?&orgRelationUUID="+orgRelationUUID+"&invoiceType="+invoiceType;
	  }else if(orgSpocUUID != null && orgSpocUUID != '' && orgSpocUUID != 'undefined'){
		  url += sourceleadUrl+"/getClientreportSummary?&orgSpocUUID="+orgSpocUUID+"&invoiceType="+invoiceType;
	  }
		  $.ajax({
				type : "POST",
				url : url,
			    success: function(data) {
			    	$("#draftAmountId").html(data.totalDraftAmount);
			    	$("#openAmountId").html(data.totalSentAmount);
			    	$("#overdueAmountId").html(data.totalOverdueAmount);
			    	$("#paidAmountId").html(data.totalPaidAmount);
			    	$("#totalPaidCount").html(data.totalPaidbillCount);
			    	$("#totalRecevableAmount").html(data.totalReceivableAmount);
			    	$("#draftCount").html(data.draftCount);
			    	$("#waitingCount").html(data.waitingCount);
			    	$("#signCount").html(data.signCount);
			    	$("#counterSignCount").html(data.counterSignCount);
			    	$("#expiredCount").html(data.expiredCount);
			    	$("#rejectedCount").html(data.rejectedCount);
			    	$("#finalizedCount").html(data.finalizedCount);
			    	
			    	$("#hiredCount").html(data.offeracceptedCount);
			    	$("#applicantCountID").html(data.applicantsCount);
			    	$("#shortListCountID").html(data.shortlistedCount);
			    	$("#submittoClientCountID").html(data.submitToClientCount);
			    	$("#interviewdCountID").html(data.interviewsCount);
			    	$("#offeredCountID").html(data.offeredCount);
			    	$("#offerAcceptedCountID").html(data.offeracceptedCount);
			    	$("#rejectedCountID").html(data.applicantRejectedCount);
			    	
			    	$("#totalPostedCount").html(data.totalPostedCount);
			    	$("#openCount").html(data.openCount);
			    	$("#onholdCount").html(data.onholdCount);
			    	$("#cancelledCount").html(data.cancelledCount);
			    	$("#filledCount").html(data.filledClosedCount);
			    	$("#unfilledCount").html(data.unfilledClosedCount);
			    	$("#closedCount").html(data.closedCount);
			    	
			    	$("#clientNameId").html(data.clientName);
			    	$("#clientContactNameId").html(data.clientContactName);
			    	
		 	    }
			});
  }
  
  </script>

<jsp:include page="../masterFooter.jsp" />
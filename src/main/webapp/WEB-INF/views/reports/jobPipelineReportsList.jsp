<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function() {
	getJobPipelineReportsList();
});	
</script>
 
<script>
function getCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 
	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
}

</script>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Jobs Pipeline List</h2>
      </div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>"> Jobs Pipeline List</a></div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix">
      <div class="ReqRepHead clearfix">
    		      <div class="col-md-2 pull-left reqListBtn">
          <a href="${appPath}/jobPipelineReports" title="Reports View" id="GView" class="btn btn-default"><i class="fa fa-pie-chart"></i></a>
          <a href="${appPath}/jobPipelineReportsList" title="List View" id="LView" class="btn btn-default"><i class="fa fa-list"></i></a>
    		      </div>
       
        <div class="col-md-4">
    <div class="dropdown-scroll">
		<select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
            <c:forEach var="option" items="${requirementsList}">
				<option value="${option.requirementId}">${option.jobTitle}</option>
			</c:forEach>
          </select>
	</div>
        </div>
        <div class="col-md-3 dropdown-select">
        <div class="dropdown-select positionRelative">
        <select class="form-control" name="dateSelection" id="dateSelection" onchange="getCustomDates();">
            <option value=''>Select Date</option>
            <option value='Last_7_days' selected>Last 7 days</option>
            <option value='Last_15_days'>Last 15 days</option>
            <option value='1_month'>1 month</option>
            <option value='3_months'>3 months</option>
            <option value='YTD' >YTD</option>
            <option value='Last_Year'>Last Year</option>
            <option value="custom">Custom</option>
          </select>
        <!-- Top Leave Takers Custom Date -->
          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
            <div class="pull-left">
              <div class="pull-left mr5" style="width: 140px" id="errMsgFrm">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
              </div>
              
              <div class="pull-left mr5" style="width: 140px" id="errMsgTo">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
              </div>
              <button type="button" class="btn btn-primary btn-sm" onclick="getJobPipelineReportsList();"><i class="fa fa-search"></i></button>
            </div>
          </div>
<!-- Top Leave Takers Custom Date --> 
      </div>

        </div>
		        <div class="col-md-2">
        <button type="button" class="btn btn-primary btn-sm	mt2" id="jobReportList_custome" onclick="getJobPipelineReportsList();">Run</button>
		</div>
         
         <div class="col-md-1">
          <div class="btn-group bootstrap_dropdownMenu dropdownPadding">
            <button type="button" class="btn btn-primary btn-sm" title="Reload" onclick="relaod();"><i class="fa fa-retweet"></i></button>
          </div>
        </div>
      </div>
    </div>
    
   <div class="vendor_wrap clearfix mt">
        <div class="clearfix"></div>
        <div class="clerfix jobpipline-count col-md-8 col-md-offset-2">
          <ul class="list-unstyled list-inline">
            <li> <a href="javascript:void(0)"><span id="allJobsCount"></span></a> <span>All</span> </li>
            <li> <a href="javascript:void(0)"><span id="openJobsCount"></span></a> <span>Open</span> </li>
            <li> <a href="javascript:void(0)"><span id="closedJobsCount"></span></a> <span>Closed</span> </li>
            <li> <a href="javascript:void(0)"><span id="onHoldJobsCount"></span></a> <span>Onhold</span> </li>
            <li> <a href="javascript:void(0)"><span id="cancelledJobsCount"></span></a> <span>Cancelled</span> </li>
          </ul>
        </div>
        <div class="clearfix"></div>
        
        <div class="clearfix mt5">
        	
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	 <div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Applicants:
        	 	<span class="text-right" id="applicantCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted">Moved forward :</label>
        	    	<span>100 %</span>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="applicantCountDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Request Sent:
        	 	<span class="text-right" id="requestSentCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted">Moved forward :</label>
        	    	<span>100 %</span>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="requestSentDays"></span><span class="paddingLeft"> days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Shortlisted:
        	 	<span class="text-right" id="shortListedCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="shortListedMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="shortListedDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Submit to Client:
        	 	<span class="text-right" id="submitToClientCount">60</span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="submitToClientMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="submitToClientDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Interview:
        	 	<span class="text-right" id="interviewCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="interviewMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="interviewDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Offer:
        	 	<span class="text-right" id="offerCount">20</span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Offer Accepted:
        	 	<span class="text-right" id="offerAcceptedCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerAcceptedMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="offerAcceptedDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	 <div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Backout:
        	 	<span class="text-right" id="backoutCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="backoutMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="backoutDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	
        	<div class="col-md-4 mb">
        	<div class="cardModel-widget">
        	<div class="clearfix row cardModelwidget-header">
        		<h4 class="col-md-12 text-center">Rejected:
        	 	<span class="text-right" id="rejectedCount"></span>
        	 	</h4>
        	 </div>
        	    <div class="clearfix row">
        	    <div class="col-md-6 paddingRight">
        	    	<label class="text-muted pull-left">Moved forward :</label>
        	    	<div class="pull-left paddingLeft"><span id="rejectedMove"></span><span class="paddingLeft">%</span></div>
        	    </div>
        	     <div class="col-md-6 paddingLeft">
        	    	<label class="text-muted pull-left">Avg TTF :</label>
        	    	<div class="pull-left paddingLeft"><span id="rejectedDays"></span><span class="paddingLeft">days</span></div>
        	    </div>
				</div>
        	</div>
        	</div>
        	
        </div>
        <div class="clearfix mt mb"></div>
      </div>
    
  </section>
  <script>
  function relaod(){
	  location.reload();
  }
  
  </script>
<script>
function getJobPipelineReportsList(){
	
	  var flag = true;
	  var jobIds = $("#jobIds").val();
	  if(jobIds == null){
		  jobIds = '';
	  }
	  var dateSelection = $("#dateSelection").val(); 
	  var fromDate = '';
	  var toDate = '';
	  if(dateSelection == 'custom'){
		   fromDate = $("#fromDate").val();
		   toDate = $("#toDate").val();
		  
		  if(fromDate == '' || toDate == ''){
			  flag = false;
			  if(fromDate == ''){
				  document.getElementById("fromDateDiv").style.display="block";
			  }else{
				  document.getElementById("fromDateDiv").style.display="none";
			  }
			  if(toDate == ''){
				  document.getElementById("toDateDiv").style.display="block";
			  }else{
				  document.getElementById("toDateDiv").style.display="none";
			  }
		  }else{
			  document.getElementById("fromDateDiv").style.display="none";
			  document.getElementById("toDateDiv").style.display="none";
		  }
		  
	  }
	  if(flag){
		  $.ajax({ 
			   	type: "POST",
			   	url: sourceleadUrl+"/getAllJobsPipelineReoportsList?${_csrf.parameterName}=${_csrf.token}&jobIds="+jobIds+"&dateSelection="+dateSelection+"&fromDate="+fromDate+"&toDate="+toDate,
			    success: function(data) {
			    	//Aplicant Details
			    	$("#applicantCount").text(data.applicantsCount);
			    	$("#applicantCountDays").text(data.applicantsDays);
			    	
			    	//request sent details
			    	$("#requestSentCount").text(data.requestSentCount);
			    	$("#requestSentDays").text(0);
			    	 
			    	//shortlisted details
			    	$("#shortListedCount").text(data.shortlistedCount);
			    	$("#shortListedDays").text(data.shortlistedDays);
			    	$("#shortListedMove").text(data.shortlistedMove);
			    	
			    	//interview details
			    	$("#interviewCount").text(data.interviewsCount);
			    	$("#interviewMove").text(data.interviewsMove);
			    	$("#interviewDays").text(data.interviewsDays);
			    	
			    	
			    	//submit to client
			    	$("#submitToClientCount").text(data.submitToClientCount);
			    	$("#submitToClientMove").text(data.submitToClientMove);
			    	$("#submitToClientDays").text(data.submitToClientDays)
			    	
			    	//offer details
			    	$("#offerCount").text(data.offeredCount)
			    	$("#offerDays").text(data.offeredDays)
			    	$("#offerMove").text(data.offeredMove)
			    	
			    	//offer Accepted details
			    	$("#offerAcceptedCount").text(data.offeredAcceptedCount)
			    	$("#offerAcceptedDays").text(data.offeredAcceptedDays)
			    	$("#offerAcceptedMove").text(data.offeredAcceptedMove)
			    	
			    	//backout details
			    	$("#backoutCount").text(data.backoutCount);
			    	$("#backoutDays").text(data.backoutDays);
			    	$("#backoutMove").text(data.backoutMove);
			    	
			    	//rejected details
			    	$("#rejectedCount").text(data.rejectedCount);
			    	$("#rejectedMove").text(data.rejectedMove);
			    	$("#rejectedDays").text(data.rejectedDays);
			    	
			    	//jobs count
			    	$("#allJobsCount").text(data.allJobsCount);
			    	$("#openJobsCount").text(data.openJobsCount);
			    	$("#closedJobsCount").text(data.closedJonbsCount);
			    	$("#onHoldJobsCount").text(data.onHoldJobsCount);
			    	$("#cancelledJobsCount").text(data.cancelledjobsCount);
			    	
			     
			    	
			    	
			    }
			 });
	  }
	  
	  
}

</script>
<script>
  $( function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatProjectSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatProjectSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatProjectSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatProjectSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatProjectSave = 'yy-dd-mm'; 
		  }
    
	  $('#fromDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#toDate").datepicker("option", "minDate", dt);
				removeErrsgFrom();
				
			}
		});
	  
	  $('#toDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate());
				$("#fromDate").datepicker("option", "maxDate", dt);
				removeErrTo();
			}
		});
  } );
  
  function removeErrsgFrom (){
	  document.getElementById("fromDateDiv").style.display="none";
	  
  }
  function removeErrTo(){
	  document.getElementById("toDateDiv").style.display="none";
  }
  </script>

<jsp:include page="../masterFooter.jsp" />
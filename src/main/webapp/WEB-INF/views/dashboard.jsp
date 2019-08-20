<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <link href="/resources/css/jquery-ui.css" rel="stylesheet"> -->

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
     <script src="/resources/js/sourcelead.common.js"></script> 

 <script type="text/javascript">
 $( document ).ready(function() {
	 var settingsConfigured = '${SourceLeadWeb_settingsConfigured}';
	 if(settingsConfigured == "N"){
		configureDiv();
	 }
	});
</script> 


<form:form class="login-form clearfix"  id="configure_form"  name="configure_form" method="post" >
   <div id ="configurePopUpDiv"></div>
</form:form>

<script>
function logoutForConfigure() {
	document.getElementById("logoutForm").submit();
}
</script>


<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<section class="content-wraper">
		<div class="clearfix">
		<div class="mt mspdashboard-tasli">
			<ul id="dashboardEventsul" class="list-unstyled	">
				<li id="PrimaryDashboard"><a class="active-mspli" >Primary Dashboard</a></li>
				<li id="MSPDashboard"><a>MSP Dashbaord</a></li>
				<div class="clearfix"></div>
			</ul>
		</div>
			<div class="clearfix"></div>
			<div id="primaryDashboard-div" class="clearfix">

	    <section class="content-header clearfix">
	      
	      <c:if test="${!empty param.invitation}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>You accepted invitation successfully.</span>
			</div>
	     </c:if>
	     
	     <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector1"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	    
	      <div class="pull-left">
	        <h2>Dashboard<a  onclick="$('#configureDashboard').show();$('.advancedsearch-mask').fadeIn();"><i class="fa fa-cog ml"></i></a></h2>
	      </div>
	    </section>
	    
	    
	    <div class="clearfix row">
	    	
	      <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
			   <jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
	      </sec:authorize>
	      <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
	      	<c:if test="${userTabs == 'Jobs'}">
				 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
						<jsp:include page="dashboardRequirement.jsp"></jsp:include> 
			     </sec:authorize>
		     </c:if>
		   </c:forEach>
			 <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES','ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">   
				     <jsp:include page="expensesDashboardCounts.jsp"></jsp:include>
			 </sec:authorize>  	
			
			<sec:authorize access="hasAnyRole('ROLE_APPLY_LEAVE','ROLE_APPLY_BEHALF_LEAVE','ROLE_APPROVE_LEAVE')">
				<jsp:include page="leaveManagement.jsp"></jsp:include>
			</sec:authorize>
			
	    		<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
	    		<c:if test="${userTabs == 'Jobs'}">
	    			<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
					<div class="col-md-12"><jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include> </div>
					</sec:authorize>
				</c:if>	
				</c:forEach>
					<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
					<div class="col-md-12"><jsp:include page="onboardingCandidatesList.jsp"></jsp:include></div> 
					</sec:authorize>
					
					<!-- Candidates for Invoice -->
			<sec:authorize access="hasAnyRole('ROLE_ADD_INVOICE','ROLE_VIEW_REQUIREMENT')">
				<jsp:include page="candidatesForInvoiceDashboard.jsp"></jsp:include>
			</sec:authorize> 
					
					<sec:authorize access="hasRole('ROLE_RESOURCE_REQUEST_APPROVER')">
					   <%-- <jsp:include page="resourceRequest.jsp"></jsp:include>  --%>
					    <div class="col-md-12"><jsp:include page="projectResourceRequest.jsp"></jsp:include></div>  
					    <jsp:include page="projectNewResourceRequest.jsp"></jsp:include>  
					 </sec:authorize>
					
					<sec:authorize access="hasRole('ROLE_BUDGET_APPROVER')">
					   <jsp:include page="budgetRequest.jsp"></jsp:include>
					</sec:authorize>
					
			    
			    <c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
			    	<c:if test="${userTabs == 'Jobs'}">
				    	 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
				   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
				   	 	 </sec:authorize>
			   	 	 </c:if>	
			   	 </c:forEach>
		     	
		     	<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
			     	<c:if test="${userTabs == 'Jobs'}">
			     		<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			     		<jsp:include page="dashboardApplicants.jsp"></jsp:include>
			     		</sec:authorize>
			     	</c:if>
		     	</c:forEach>
		     
		     <div class="clearfix"></div>
	     
<%--        <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_INTERVIEW')">
			<div class='dashNewWrap mb'> 
				<div class='blank-pages col-md-12'>
			        <p><i class='fa fa-5x fa-bar-chart'></i></p>
		        	<p>No Records Found</p>
				 </div>
			</div>
		</sec:authorize>  --%>
	    
	    <div class="clearfix"> 
	     	<div class="col-md-12"><jsp:include page="dashboardEvents.jsp"></jsp:include></div>   
	   </div>  	
	     	<sec:authorize access="hasAnyRole('ROLE_VIEW_CONTRACT')">
				<div class="col-md-12"><jsp:include page="dashboardContracts.jsp"></jsp:include> </div>
			</sec:authorize>

		<jsp:include page="dashboard_configure_popup.jsp"></jsp:include>
		</div>
		</div>
			<div id="mspDashbaord-div" class="clearfix mt" style="display:none;">
			
			
			<div class="row">
			<div class="col-md-3">
            <div class="mspportlet-widget">
                  
                <div class="clearfix mb positionRelative">
                  <h4 class="overrightTooltip">Select Client<span class="colorRed ml5">*</span> <i data-toggle="tooltip" class="cursorPointer text-primary fa fa-info-circle ml10" title="Selet Client to get data"></i></h4>
                  <div class="clearfix mt5">        
                  <select data-style="btn-primary" id ="clientList" class="selectpicker width100percnt" data-live-search="true" data-live-search-placeholder="Search Client" data-title="Select Client" onchange="mspJobsList('empty');getJobsList('');getMspVendorList();">
                          </select>
                        </div>
                    </div>
                    
                    
                    <div class="clearfix mb positionRelative">
                  <h4 class="overrightTooltip">Select Vendor</h4>
                  <div class="clearfix mt5">        
                  <select data-style="btn-primary" id ="vendorList" class="selectpicker width100percnt" data-live-search="true" data-live-search-placeholder="Search Vendor" data-title="Select Vendor" onchange="mspJobsList('empty');getJobsList('');" >
                          </select>
                        </div>
                    </div>

                    <div class="clearfix mb">
                        <h4>Posted on</h4>
                        <div class="clearfix mt5 positionRelative">
                                <select data-style="btn-info" id="dateSelection" name="dateSelection" class="selectpicker width100percnt" data-live-search="true" data-live-search-placeholder="Search Posted on" data-title="Posted on" onchange="mspVendorDatesSearch();">
                                    <option value="today">Today</option>
                                    <option value="Last_7_days">Last 7 days</option>
						            <option value="Last_15_days">Last 15 days</option>
						            <option value="1_month" selected="">1 month</option>
						            <option value="3_months">3 months</option>
						            <option value="YTD">YTD</option>
						            <option value="Last_Year">Last Year</option>
						            <option value="custom">Custom</option>
                                </select>
                          
                          
                          <div class="clearfix customLeaves-shadow mt10" id="customSearch" style="right:0px; display:none">
				              <div class="clearfix">
				              <div class="col-md-5 padding_right padding_left" id="errMsgFrm">
				                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
				                <p id="fromDateDiv" class="colorRed small mt5 mb0">From Date Required</p>
				              </div>
				              
				              <div class="col-md-5 padding_left padding_right ml5" id="errMsgTo">
				                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
				                <p id="toDateDiv" class="colorRed small mt5 mb0">To Date Required</p>
				              </div>
				              <div class="col-md-1 padding_left padding_right ml5 mt2">
				              <button type="button" class="btn btn-primary btn-sm" onclick=" customeDateSearchResult();"><i class="fa fa-search"></i></button>
				          	  </div>
				              
				          </div>
				          </div>
				          
				          </div>
                          </div>

                <!-- Jobs List -->
                <div class="clearfix mt positionRelative">
                    <h4>Jobs List</h4>
                  <ul class="list-unstyled mspportlet-list  mspportlet-list-customcheckbox mt10 mb0" >
                    <div id="mspAllJobsLoading"></div>
                   <li id="mspAllJobsList"></li>
                  <li>
                  <div class="clearfix" id="moreAList" style="display: none" >
                    <a onClick="morejobListPOP();" title="More job List">+<span id="moreListSize" class="mr5"></span>more</a>
                    <div class="clearfix"></div>
                  </div>
                </li>
                </ul>
                </div>
            </div>
          </div>
          
          <div class="col-md-9">
              <div class="mspportlet-widget clearfix positionRelative">
                <div class="row">
                  <div class="col-md-6">
                  <div class="mspportlet-header clearfix">
                    <h4>Applicant Status</h4>
                  </div>
                  <div id="mspApplicantPieChartLoading"></div>
                      <div id="applicantsStatus" style="width: 100%; height: 260px;"></div>
                  </div>
                  <div class="col-md-6 mspportlet-widget-leftborder">
                    <div class="clearfix">
                        <div class="mspportlet-header clearfix">
                      <div class="col-md-9 noPad">
                        <h4 class="pull-left"><span id="joblisthdrvalue" class="mr5 text-primary">Top 10</span>Job List</h4>
                        <div class="jobsearch-search pull-right">
                          <span class="pull-left"><input type="search" placeholder="Search here..." class="searchhere" style="display:block !important;" id="serachMspJob"></span><span class="pull-left searchSVGIC" onclick="mspJobsList('empty');"><img src="/resources/images/search.svg"></span>
                        </div>
                      </div>
                      
                      <div class="col-md-3 noPad">
                        <div class="pull-right">
                          <a href="javascript:void(0)" id="top10" class="btn btn-primary btn-sm pull-left" data-value="10">Top 10</a>
                          <a href="javascript:void(0)" id="top100"class="btn btn-default btn-sm  pull-left" data-value="100">Top 100</a>
                        </div>
                        </div>
                      </div.>
                    </div>
                    
                      <div class="clearfix positionRelative mt">
                            <div class="clearfix"><div id="mspVendorJobsDashbordLoading"></div>
                              <div id="top10-div">
                                    <div class="mspportlet-table" >
                                      <table class="table table-bordered mb0">
                                            <thead>
                                              <tr>
                                              <th>Job Title</th>
                                              <th class="text-center">Offered</th>
                                              <th class="text-center">Joined</th>
                                            </tr>
                                            </thead>
                                            <tbody id="mspJobsList">
                                            </tbody>
                                      </table>
                                    </div>
                              </div>
                            </div>
                          </div>
                    </div>
                    </div>
                </div>
          </div>
      </div>
      </div>
	
	</div>
</div>
</section>

<div id="morejobListPOP" class="morejoblist-popdiv">
  <div class="morejoblist-popdiv-hdr">Jobs List <i class="closePop pull-right morejoblist-popdiv-close"></i></div>
  <div class="morejoblist-popdiv-inner">
      <div class="clearfix">
        <ul class="morejoblist-selectlist list-unstyled">
          <li><a id="all" onclick="alphabetSerach('none');">All</a></li>
			<li> <a onclick="alphabetSerach('A');">A</a> </li> 
			<li> <a onclick="alphabetSerach('B');">B</a> </li> 
			<li> <a onclick="alphabetSerach('C');">C</a> </li> 
			<li> <a onclick="alphabetSerach('D');">D</a> </li> 
			<li> <a onclick="alphabetSerach('E');">E</a> </li> 
			<li> <a onclick="alphabetSerach('F');">F</a> </li> 
			<li> <a onclick="alphabetSerach('G');">G</a> </li> 
			<li> <a onclick="alphabetSerach('H');">H</a> </li> 
			<li> <a onclick="alphabetSerach('I');">I</a> </li> 
			<li> <a onclick="alphabetSerach('J');">J</a> </li> 
			<li> <a onclick="alphabetSerach('K');">K</a> </li> 
			<li> <a onclick="alphabetSerach('L');">L</a> </li> 
			<li> <a onclick="alphabetSerach('M');">M</a> </li> 
			<li> <a onclick="alphabetSerach('N');">N</a> </li> 
			<li> <a onclick="alphabetSerach('O');">O</a> </li> 
			<li> <a onclick="alphabetSerach('P');">P</a> </li> 
			<li> <a onclick="alphabetSerach('Q');">Q</a> </li> 
			<li> <a onclick="alphabetSerach('R');">R</a> </li> 
			<li> <a onclick="alphabetSerach('S');">S</a> </li> 
			<li> <a onclick="alphabetSerach('T');">T</a> </li> 
			<li> <a onclick="alphabetSerach('U');">U</a> </li> 
			<li> <a onclick="alphabetSerach('V');">V</a> </li> 
			<li> <a onclick="alphabetSerach('W');">W</a> </li> 
			<li> <a onclick="alphabetSerach('X');">X</a> </li> 
			<li> <a onclick="alphabetSerach('Y');">Y</a> </li> 
			<li> <a onclick="alphabetSerach('Z');">Z</a> </li> 
        </ul>
      </div>
      <div class="clearfix mt">
          <ul class="list-unstyled mspportlet-list mt10 mb0">
              <li id="moreJobsList"></li>
            </ul>
      </div>
  </div>
  <div class="morejoblist-popdiv-footer">
      <div class="pull-right">
        <button type="button" class="btn btn-sm btn-primary mr" onclick="mspJobsList('empty')">Search</button><button type="button" class="btn btn-sm btn-default morejoblist-popdiv-close">Clear All</button>
      </div>
  </div>
</div>

<script>
function mspVendorDatesSearch(){
	var dateSelection = $("#dateSelection").val();
	$("#serachMspJob").val('');
	if(dateSelection == 'custom'){
		/* $(".customLeaves").show(); */
		 removeErrsgFrom();
		 removeErrTo();
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 
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
	  
		 
	 }else{
		 /* $(".customLeaves").hide(); */
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
		 customeDateSearchResult();
	 }
}
function removeErrsgFrom (){
	  document.getElementById("fromDateDiv").style.display="none";
	  
}
function removeErrTo(){
	  document.getElementById("toDateDiv").style.display="none";
}
function  customeDateSearchResult(){
	mspJobsList('empty');getJobsList('');
}
</script>

<script>
$("#top10").click(function(){
  var hdrtext = $(this).text();
  $("#joblisthdrvalue").text(hdrtext);
  $("#top10").addClass("btn-primary").removeClass("btn-default");
  $("#top100").removeClass("btn-primary").addClass("btn-default");
  mspJobsList('empty');
  getJobsList('');
});

$("#top100").click(function(){
  var hdrtext = $(this).text();
  $("#joblisthdrvalue").text(hdrtext);
  $(this).removeClass("btn-default").addClass("btn-primary");
  $("#top10").removeClass("btn-primary").addClass("btn-default");
  mspJobsList('empty');
  getJobsList('');
});


function alphabetSerach(type){
// 	   mspJobsList(type);
// 	  getJobsList(type);
	  getMoreJobsList(type);
}
 
$('ul.morejoblist-selectlist a').click(function(e) {
	  e.preventDefault();
	  $("ul.morejoblist-selectlist a").removeClass("morejoblist-active-anchor");
		$(this).toggleClass("morejoblist-active-anchor");
	});
</script>
<script>
  function morejobListPOP(){
	  getMoreJobsList('none');
    $(".mask, #morejobListPOP").show();
	$("ul.morejoblist-selectlist a").removeClass("morejoblist-active-anchor");
	$("#all").toggleClass("morejoblist-active-anchor");
  }
  $(".morejoblist-popdiv-close").click(function(){
    $(".mask, #morejobListPOP").fadeOut();
    $("ul.morejoblist-selectlist a").removeClass("morejoblist-active-anchor");
	$(this).toggleClass("morejoblist-active-anchor");
	alphabetSerach("none")
	$('#moreJobsList').find('input[type=checkbox]:checked').removeAttr('checked');
  })
</script>
<script>
$(function(){
	$("li#PrimaryDashboard > a").click(function(){
		$("#primaryDashboard-div").show(); $("#mspDashbaord-div").hide();
		$(this).addClass('active-mspli');
		$("li#MSPDashboard a").removeClass('active-mspli');v
	});
	$("li#MSPDashboard > a").click(function(){
		$(this).addClass('active-mspli');
		$("li#PrimaryDashboard a").removeClass('active-mspli');
		$("#primaryDashboard-div").hide(); $("#mspDashbaord-div").show();
		$("#clientList").val('');
		getClientList();
		mspJobsList('empty');
	})
})
</script>
<jsp:include page="masterFooter.jsp" />
<jsp:include page="dashboard_scripts.jsp"/>





<!-- ============================================ MSP Vendor Related Code ========================================== -->
<script>
function getClientList(){
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	$.ajax({
		type : "POST",
		url: sourceleadUrl+"/getMSPClientList?"+tokenParamName+"="+tokenValue+"",
		success :function(json) {
		var output = "";
		output = output + "<option value=''>Select Client</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
			output = output + "<option value='" +opt.orgLocationId+ "'>"+opt.clientName+"</option>";
 		}
		
		$("#clientList").empty();
		$("#clientList").append(output);
		$('#clientList').selectpicker('refresh');
	},
	});
}
</script>


<script>
$("#serachMspJob").keyup(function(e) {
	   if(e.which === 13) {
		   mspJobsList('empty');
	   }
});
 
function mspJobsList(alphabetType){
	var flag = true;
	var formData = {};
	var jobLength = $("#top10").hasClass("btn-primary") ? $("#top10").data("value") : $("#top100").data("value");
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var orgRelationId = $("#clientList").val();
	var dateSelection = $("#dateSelection").val(); 
	var searchData = $("#serachMspJob").val();
	var mspOrgRelationId = $("#vendorList").val();
	 
	var jobIds = [];
	$('input:checkbox[name=jobIds]:checked').each(function() 
    {
		jobIds.push($(this).val());
    });
	
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
	  formData["orgRelationId"] = orgRelationId;
// 	  formData["requirementIds"] = jobIds;
	  formData["dateSelection"] = dateSelection;
	  formData["jobLength"] = jobLength;
	  formData["searchData"] = searchData;
	  formData["mspOrgRelationId"] = mspOrgRelationId;
	  formData["alphabet"] = alphabetType;	 
	  formData["fromDate"] = fromDate;	 
	  formData["toDate"] = toDate;	 
	  
   if(flag){
	if(orgRelationId != ""){
	$("#mspVendorJobsDashbordLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$("#mspApplicantPieChartLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		type : "POST",
		data: formData,
		url: sourceleadUrl+"/mspVendorJobsDashbordList?requirementIds="+jobIds,
		success :function(json) {
			$(".mask, #morejobListPOP").fadeOut();
		    $("ul.morejoblist-selectlist a").removeClass("morejoblist-active-anchor");
		    $('#moreJobsList').find('input[type=checkbox]:checked').removeAttr('checked');
			var result = "";
			if(json.mspReqList.length > 0){
					for (var i=0; i<json.mspReqList.length; i++)
					{
						var report = json.mspReqList[i];
						result = result +"<tr>";
						result = result +" <td>";
						result = result +"   <div class='tbldata-dotdot-200 text-left' title=\""+report.requirementName+"\">"+report.requirementName+"</div>";
						result = result +" </td>";
						result = result +" <td>";
						result = result +"   <div class='tblminwidth-75'> "+report.offersCount+" </div>";
						result = result +" </td>";
						result = result +" <td>";
						result = result +"     <div class='tblminwidth-75'> "+report.joinedCount+"  </div>";
						result = result +"   </td>";
						result = result +" </tr>";
					}
					
			}else{
				result = result +"<tr>";
				result = result +" <td colspan='3' class = 'dataTables_empty'></td>";
				result = result +" </tr>";
			}		
			$("#mspJobsList").empty();
			$("#mspJobsList").append(result);
			$("#mspVendorJobsDashbordLoading").removeAttr('style');
			applicantPieChart(json);
		}
	  });
	}else{
		var result = "";
		result = result +"<tr>";
		result = result +" <td colspan='3' class = 'dataTables_empty'></td>";
		result = result +" </tr>";
		
		$("#mspJobsList").empty();
		$("#mspJobsList").append(result);

		var result = ""; 
		result = result +" <div class = 'dataTables_empty'></div>";
		document.getElementById("moreAList").style.display = "none";
		$("#mspAllJobsList").empty();
		$("#mspAllJobsList").append(result); 
		
		var result = "";
		result = result +" <div class = 'dataTables_empty'></div>";
		$("#applicantsStatus").empty();
		$("#applicantsStatus").append(result);
	}
}
}




function getJobsList(alphabetType){
	var jobLength = $("#top10").hasClass("btn-primary") ? $("#top10").data("value") : $("#top100").data("value");
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var orgRelationId = $("#clientList").val();
	var dateSelection = $("#dateSelection").val(); 
	var mspOrgRelationId = $("#vendorList").val();
	
	  var formData = {};
	  var flag = true;
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
	  
	  formData["orgRelationId"] = orgRelationId;
	  formData["dateSelection"] = dateSelection;
	  formData["jobLength"] = jobLength;
	  formData["mspOrgRelationId"] = mspOrgRelationId;
	  formData["alphabet"] = alphabetType;	 
	  formData["fromDate"] = fromDate;	 
	  formData["toDate"] = toDate;	 
	  
	
	if(flag){
	if(orgRelationId != ""){
	$("#mspAllJobsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		type : "POST",
		data: formData,
		url: sourceleadUrl+"/mspVendorJobsDashbordList",
		success :function(json) {
			var result = "";
			var empty = "";
			var length = json.mspReqList.length;
			if(json.mspReqList.length > 0){
				var limit = length >= 5 ? 5 : length;
				for (var len = 0; len < limit; len++)
				{
					var report = json.mspReqList[len];
					result = result + "<div class='checkbox-new clearfix'>";
					result = result + ' <input id="mspjobIds'+report.requirementId+'" class="checkbox-custom" name="jobIds" type="checkbox" value='+report.requirementId+' onclick="mspJobsList(\''+empty+'\')" >';
					result = result + " <label for='mspjobIds"+report.requirementId+"' class='checkbox-custom-label'><span class='ml5'>"+report.requirementName+"</span></label>";
					result = result + "</div>";
				}
				if(limit === 5){
				$("#moreAList").show();
				$("#moreListSize").text(json.mspReqList.length - limit)
				}else{
				document.getElementById("moreAList").style.display = "none";
				} 
			}else{
				document.getElementById("moreAList").style.display = "none";
				result = result +" <div class = 'dataTables_empty'></div>";
			}
			$("#mspAllJobsList").empty();
			$("#mspAllJobsList").append(result);
			$("#mspAllJobsLoading").removeAttr('style');
		}
	});
	}
  }	
}

function getMoreJobsList(alphabetType){
	var jobLength = $("#top10").hasClass("btn-primary") ? $("#top10").data("value") : $("#top100").data("value");
	var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	var orgRelationId = $("#clientList").val();
	var dateSelection = $("#dateSelection").val(); 
	var mspOrgRelationId = $("#vendorList").val();
	
	
	  var formData = {};
	  var flag = true;
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
	  
	  formData["orgRelationId"] = orgRelationId;
	  formData["dateSelection"] = dateSelection;
	  formData["jobLength"] = jobLength;
	  formData["mspOrgRelationId"] = mspOrgRelationId;
	  formData["alphabet"] = alphabetType;	 
	  formData["fromDate"] = fromDate;	 
	  formData["toDate"] = toDate;	
	if(flag){ 
	if(orgRelationId != ""){
	$.ajax({
		type : "POST",
		data : formData,
		url: sourceleadUrl+"/mspVendorJobsDashbordList",
		success :function(json) {
			var result = "";
			if(json.mspReqList.length > 0){
				for (var i=0; i<json.mspReqList.length; i++)
				{
					var report = json.mspReqList[i];
					result = result + '<div class="checkbox-new clearfix">';
					result = result + ' <input id="moreMspjobIds'+report.requirementId+'" class="checkbox-custom" name="jobIds" type="checkbox" value='+report.requirementId+'  >';
					result = result + ' <label for="moreMspjobIds'+report.requirementId+'" class="checkbox-custom-label"><span class="ml5">'+report.requirementName+'</span></label>';
					result = result + '</div>';
				}
			}else{
				result = result +" <div class = 'dataTables_empty'></div>";
			}
			$("#moreJobsList").empty();
			$("#moreJobsList").append(result);
			
			
		}
	});
   }
  }
}

function getMspVendorList(){
	var orgRelationId = $("#clientList").val();
		var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		if(orgRelationId != ""){
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/mspVendorList?&mspOrgRelationId="+orgRelationId,	    	   
		    success: function(data) {
		    	var result = "";
		    	 for(var i=0;i<data.length;i++){
		    	       	var report = data[i];
				        result = result +'  <option  value='+report.id+' >'+report.orgName+'</option>';
		    	 }
		    	 $("#vendorList").empty();
		    	 $("#vendorList").append(result);
				 $("#vendorList").selectpicker('refresh');
		    }
		   }); 
		}
	}
</script>

<script>
$(document).ready(function(){
  $('[data-toggle="tooltip"]').tooltip();   
});
</script>
 
<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/pie.js"></script>
 
<script type="text/javascript">
function applicantPieChart(json){
	
	if(json.applicantCount > 0){
		$("#mspApplicantPieChartLoading").removeAttr('style');
		AmCharts.makeChart("applicantsStatus",
				  {
					"type": "pie",
					"adjustPrecision": true,
					"balloonText": "[[title]]: <span style='font-size:14px'><b>[[value]]</b><br>([[percents]]%)</span>",
					"innerRadius": "50%",
					"labelRadius": 50,
					"pieX": "55%",
					"pieY": "50%",
					"pullOutRadius": "5%",
					"startRadius": "5%",
					"baseColor": "undefined",
					"colorField": "colors",
					"colors": [],
					"labelsEnabled": false,
					"marginBottom": 0,
					"marginTop": 0,
					"titleField": "category",
					"valueField": "column-1",
					"autoDisplay": true,
					"allLabels": [],
					"balloon": {},
					"legend": {
						"enabled": true,
						"align": "center",
						"autoMargins": false,
						"combineLegend": true,
						"marginLeft": 0,
						"marginRight": 0,
						"markerBorderAlpha": 0,
						"markerBorderColor": "undefined",
						"markerLabelGap": 10,
						"markerSize": 15,
						"markerType": "circle",
						"position": "left",
						"verticalGap": 15
					},
					"titles": [],
					"dataProvider": [
						{
							"category": "Applicants",
							"column-1": json.applicantCount,
							"colors": "#89c4f4  "
						},
						{
							"category": "Shortlisted",
							"column-1": json.shortlistCount,
							"colors": "#bdc3c7"
						},
						{
							"category": "Submit to Client",
							"column-1": json.submitToClientCount,
							"colors": "#f2d984"
						},
						{
							"category": "Interview",
							"column-1": json.interviewCount,
							"colors": "#f0f0d6"
						},
						{
							"category": "Offered",
							"column-1": json.offersCount,
							"colors": "#d5b8ff"
						},
						{
							"category": "Joined",
							"column-1": json.joinedCount,
							"colors": "#67809f"
						}
					]
				}
			);
	}else{
		var result = "";
		result = result +" <div class = 'dataTables_empty'></div>";
		$("#applicantsStatus").empty();
		$("#applicantsStatus").append(result);
		$("#mspApplicantPieChartLoading").removeAttr('style');

	}
	
}
</script>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />
  
<script>
$(document).ready(function() {
	getJobPipelineReports();
	getJobsList();
});	
</script>

<script>
function getJobsReportCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 removeErrsgFrom();
		 removeErrTo();
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 document.getElementById("runPipelineDiv").style.display="none";
		 
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
		 document.getElementById("runPipelineDiv").style.display="block";
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
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
        <h2>Job(s) Pipeline</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>"> Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Job(s) Pipeline
       </div>
    </section>
    
    <!-- hiring Process  -->
    
    <div class="vendor_wrap clearfix">
    
	<div class="reportHead clearfix">
		<div class="listGridWrap reqListBtn">
			<a title="Reports View" id="GraphView" class="btn btn-default rAct"><i class="fa fa-pie-chart"></i></a>
        	<a title="List View" id="ListView" class="btn btn-default"><i class="fa fa-list"></i></a>
		</div>
	
    	<ul class="clearfix pull-right overIndex">
    	<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openFilterPop();"></a></li>
    		<li>
				<div class="ReqRepHead-chosen repChosen choseMini">
					<select class="chosen-select form-control" multiple data-placeholder="Select Job" name="jobIds" id="jobIds">
			            <%-- <c:forEach var="option" items="${requirementsList}">
							<option value="${option.requirementId}">${option.generatedId} - ${option.jobTitle}</option>
						</c:forEach> --%>
			          </select>
		        </div>    		
    		</li>
    		<li>
        <div class="dropdown-select positionRelative repSelWrap clearfix">
	        <select class="form-control repSelFiled" name="dateSelection" id="dateSelection" onchange="getJobsReportCustomDates();">
	            <option value=''>Select Date</option>
	            <option value='Last_7_days' selected >Last 7 days</option>
	            <option value='Last_15_days'>Last 15 days</option>
	            <option value='1_month'>1 month</option>
	            <option value='3_months'>3 months</option>
	            <option value='YTD' >YTD</option>
	            <option value='Last_Year'>Last Year</option>
	            <option value="custom">Custom</option>
	        </select>   
	        
		<div class="repSelBtn" id="runPipelineDiv"><button type="button" class="btn btn-primary btn-sm" onclick="getJobPipelineReports();">Run</button></div> 
          
                    <!-- Custom Date -->
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
              <div class="repCustomDateInn" id="errMsgFrm">
                <input type="text" class="form-control calendar" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
                <p id="fromDateDiv">From Date Required</p>
              </div>
              
              <div class="repCustomDateInn" id="errMsgTo">
                <input type="text" class="form-control calendar" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
                <p id="toDateDiv">To Date Required</p>
              </div>
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="getJobPipelineReports();"><i class="fa fa-search"></i></button></div>
          </div>                    
          <!-- Custom Date --> 
          
        </div>    		
    		</li> 
    		
    	</ul>
    </div>     
    
    
    <div class="dashboard-widget clearfix amchartTitile-hide" id="jobPipelineReportDivId" style="display:block"> 
       <div id="jobspipline" style="width: 100%; height: 350px;"></div>
    </div>
    
    
    
    
    <div class="clearfix backWhite" id="jobPipelineListDivId" style="display:none">
            
<div class="reportsCount reportsCount5">
		<ul>
			<li title="All" id="applicantsli">
				<span><i class="fa fa-globe"></i></span><b id="allJobsCount">0</b>
			</li>
			<li title="Open" class="colorTwo">
				<span><i class="fa fa-folder-open"></i></span><b id="openJobsCount">0</b>
			</li>
			<li title="Closed">
				<span><i class="fa fa-star"></i></span><b id="closedJobsCount">0</b>
			</li>
			<li title="Onhold" class="colorTwo">
				<span><i class="fa fa-hand-rock-o"></i></span><b id="onHoldJobsCount">0</b>
			</li>
			<li title="Cancelled">
				<span><i class="fa fa-times"></i></span><b id="cancelledJobsCount">0</b>
			</li>
			<div class="clearfix"></div>
		</ul>
	</div>        
        
        
        <div class="clearfix mt">
        	
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
      
</div>      
    
  </section>
  <div class="advancedsearch-mask"></div>
  <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="jobPipelineFilterId">
				<h5 class="headerAdsrch">Filter
					<a href="javascript:void(0)" class="close-div" onClick="closeFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					  <div class="col-md-4">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myClientInput" onkeyup="myClientFunction()" placeholder="Clients" title="Type in a Client">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClientUL">
									<c:forEach var="clientsList" items="${orgClientList}">
									
									<li class="checkBox positionRelative"><input  type="checkbox" name="clientNameById" value ="${clientsList.id}" ><label>${clientsList.responderLocationId.org.orgName}</label></li>
									
									</c:forEach>
								</ul>
							 </div> 
						</div>
						
						<%-- <div class="col-md-4">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myTeamInput" onkeyup="myTeamInputFunction()" placeholder="Teams" title="Type in a team">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myTeamUL">
									<c:forEach var="teams" items="${teamsList}">
									
									<li class="checkBox positionRelative"><input  type="checkbox" name="teamNameById" value ="${teams.id}" ><label>${teams.name}</label></li>
									
									</c:forEach>
								</ul>
							 </div> 
						</div> --%>
						
						
						<div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Job Posted From</strong></label>
								 <div class="clearfix positionRelative">
									<ul class="list-unstyled" id="postedDateFilter">
                                    <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" checked="checked" id="jobDateRadio" value="month" name="jobCustomDays"><label for="onboard2">Last 30 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="twomonths" name="jobCustomDays"><label for="onboard2">Last 60 Days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="threemonths" name="jobCustomDays"><label for="onboard2">Last 90 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="sixmonths" name="jobCustomDays"><label for="onboard2">Last 180 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="oneyear" name="jobCustomDays"><label for="onboard2">Last 365 days</label></li>
	                                <li class="checkBox positionRelative" ><input type="radio" onclick="customJobsList();" value="custom" name="jobCustomDays"><label for="onboard2">Custom</label></li>
			                        </ul>
						              <div class="custom_search popCusDate">
						            </div>
							      </div>
						     </div> 
						  </div>
						</div>
					  
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="getJobsList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetFilterPopup();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
  
  <!-- ========================== Filter popup ===============================  -->
<script>
function showStartDate(){
    
    var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
    var finalDateFormat = getJSDateFormat(locationDate);
	
	$('#startDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormat,
		onSelect : function(selected) {
			 var dt = $(this).datepicker('getDate', '+1d');
	            dt.setDate(dt.getDate() + 1);
			$("#endDate").datepicker("option", "minDate", dt);
		}
	}).datepicker('show');;
}
	
function showEndDate(){
   var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
   var finalDateFormat = getJSDateFormat(locationDate);
   
	$('#endDate').datepicker({
		changeMonth: true,
		changeYear: true, 
		dateFormat : finalDateFormat,
		beforeShow: function(){
	           this.value='';
	     },
		onSelect : function(selected) {
			   var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
			$("#startDate").datepicker("option", "maxDate", dt);
		}
	}).datepicker('show');;
}


function customJobsList() {
    var selectVal = $('input[name=jobCustomDays]:checked').val();
    if (selectVal == "custom") {
    	var result = '';
    	result  = result + '<div class="col-md-6 paddingLeft" id="errStartDate">';                                                                       
    	result = result +'     <input type="text" class="form-control input-sm calendar" onclick="showStartDate();" id="startDate" placeholder="Start Date"> ';
    	result = result +'   </div> ';
    	result = result +'   <div class="col-md-6 noPad" id="errEndDate">';
    	result = result +'     <input type="text" class="form-control input-sm calendar" onclick="showEndDate();" id="endDate" placeholder="End Date">';
    	result = result +'   </div>';
        $(".custom_search").empty().append(result);
        $(".custom_search").show();
    }else {
        $(".custom_search").empty().hide();
    }
}

function closeFilterPop(){
	$("#jobPipelineFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openFilterPop(){
	 $("#jobPipelineFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetFilterPopup(){
	$("ul#myClientUL > li input:checkbox").prop("checked",false);
	document.getElementById("jobDateRadio").checked = true;
	$(".custom_search").empty().hide();
	//getInvoiceList();
	
}

function myClientFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myClientInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myClientUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

function myTeamInputFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTeamInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTeamUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}
</script>
<script type="text/javascript">
function getJobsList(){
var jobCreatedDateRange = null;
var startDateStr = null;
var endDateStr = null;
var clientIds =[];
var returnStat = true;
	$('input:radio[name=jobCustomDays]:checked').each(function() {
		jobCreatedDateRange = $(this).val();
		if(jobCreatedDateRange == 'custom'){
			startDateStr = $("#startDate").val();
			endDateStr = $("#endDate").val();
			$("#errStartDate").find('p.jquery_form_error_message').remove();
			$("#errEndDate").find('p.jquery_form_error_message').remove();
			if(startDateStr == null || startDateStr == '' || startDateStr == 'undefined'){
				$("input#startDate") .after( "<p class='jquery_form_error_message'>Start Date is Required</span>");
				returnStat =  false;
			}
			if(endDateStr == null || endDateStr == '' || endDateStr == 'undefined'){
				$("input#endDate") .after( "<p class='jquery_form_error_message'>End Date is Required</span>");
				returnStat =  false;
			}
		}
	});
	$('input:checkbox[name=clientNameById]:checked').each(function() 
		    {
		clientIds.push($(this).val());
		    });
	if(returnStat){
	$.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getJobsForPipelineReoport?${_csrf.parameterName}=${_csrf.token}&jobCreatedDateRange="+jobCreatedDateRange+"&startDateStr="+startDateStr+"&endDateStr="+endDateStr+"&clientIds="+clientIds,
	    success: function(data) {
	    	var json = JSON.parse(data);
        	$('#jobIds').empty();
            for(var i=0;i<json.length;i++){
            	  var opt = json[i];
            		$('#jobIds').append('<option value='+opt.requirementId+' >'+opt.generatedId+' - '+opt.jobTitle+'</option>');
            }
            $('#jobIds').trigger("chosen:updated");
            closeFilterPop();
        }
	 });
	}
	return returnStat;
}

</script>
<script>
function reload(){
	location.reload();
}
</script>
<script>
function getJobPipelineReports(){
	
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
		    success: function(response) {
		    	getJobsPipelineAmcahrt(response);
	    		getJobPipelineReportsList(response);
		    	if(response.object.applicantsCount>0){
		    		var listView = document.getElementById("ListView");
		        	 listView.addEventListener("click", function() {
		        		 getJobPipelineReportsList(response);
		        		 document.getElementById("jobPipelineListDivId").style.display="block";
		        		 document.getElementById("jobPipelineReportDivId").style.display="none";
					    	var element = document.getElementById("ListView");
					    	var grid = document.getElementById("GraphView");
					    	element.classList.add("rAct");
					    	grid.classList.remove("rAct");
					  });
		        	 
		        	 var graphicView = document.getElementById("GraphView");
		        	 graphicView.addEventListener("click", function() {
		        		 getJobsPipelineAmcahrt(response);
		        		 document.getElementById("jobPipelineListDivId").style.display="none";
		        		 document.getElementById("jobPipelineReportDivId").style.display="block";
					    	var element = document.getElementById("ListView");
					    	var grid = document.getElementById("GraphView");
					    	element.classList.remove("rAct");
					    	grid.classList.add("rAct");
		        		 
		        	 });
		        	 
		    	}else{
		    		var result = '';
	 	   			result = result+"<div class='dataTables_empty'>"; 
	 	   			result = result+"</div> ";
	 	   	        $("#jobspipline").empty();
	                $("#jobspipline").append(result);
		    	}
		    }
		 });
	  }	  
}
function getJobPipelineReportsList(response){
	//Aplicant Details
	$("#applicantCount").text(response.object.applicantsCount);
	$("#applicantCountDays").text(response.object.applicantsDays);
	
	//request sent details
	$("#requestSentCount").text(response.object.requestSentCount);
	$("#requestSentDays").text(0);
	 
	//shortlisted details
	$("#shortListedCount").text(response.object.shortlistedCount);
	$("#shortListedDays").text(response.object.shortlistedDays);
	$("#shortListedMove").text(response.object.shortlistedMove);
	
	//interview details
	$("#interviewCount").text(response.object.interviewsCount);
	$("#interviewMove").text(response.object.interviewsMove);
	$("#interviewDays").text(response.object.interviewsDays);
	
	
	//submit to client
	$("#submitToClientCount").text(response.object.submitToClientCount);
	$("#submitToClientMove").text(response.object.submitToClientMove);
	$("#submitToClientDays").text(response.object.submitToClientDays)
	
	//offer details
	$("#offerCount").text(response.object.offeredCount)
	$("#offerDays").text(response.object.offeredDays)
	$("#offerMove").text(response.object.offeredMove)
	
	//offer Accepted details
	$("#offerAcceptedCount").text(response.object.offeredAcceptedCount)
	$("#offerAcceptedDays").text(response.object.offeredAcceptedDays)
	$("#offerAcceptedMove").text(response.object.offeredAcceptedMove)
	
	//backout details
	$("#backoutCount").text(response.object.backoutCount);
	$("#backoutDays").text(response.object.backoutDays);
	$("#backoutMove").text(response.object.backoutMove);
	
	//rejected details
	$("#rejectedCount").text(response.object.rejectedCount);
	$("#rejectedMove").text(response.object.rejectedMove);
	$("#rejectedDays").text(response.object.rejectedDays);
	
	//jobs count
	$("#allJobsCount").text(response.object.allJobsCount);
	$("#openJobsCount").text(response.object.openJobsCount);
	$("#closedJobsCount").text(response.object.closedJonbsCount);
	$("#onHoldJobsCount").text(response.object.onHoldJobsCount);
	$("#cancelledJobsCount").text(response.object.cancelledjobsCount);
}
</script>
<script>
  function removeErrsgFrom (){
	  document.getElementById("fromDateDiv").style.display="none";
	  
  }
  function removeErrTo(){
	  document.getElementById("toDateDiv").style.display="none";
  }
  </script>
<script>
function getJobsPipelineAmcahrt(response){
	var mainChar = [];
	if(response.object.applicantsCount>0){
		mainChar.push( {
			"category": "Applicant(s)",
			"column-1": response.object.applicantsCount,
			"Color": "#90CAF9",
			"Days": response.object.applicantsDays,
			"moveforward": "100"
		    } );
	}
	
	if(response.object.shortlistedCount>0){
		mainChar.push( {
			"category": "Shortlisted",
			"column-1": response.object.shortlistedCount,
			"Color": "#F48FB1",
			"Days": response.object.shortlistedDays,
			"moveforward": response.object.shortlistedMove
		    } );
	}
	
	if(response.object.submitToClientCount>0){
		mainChar.push( {
			"category": "Submit To Client",
			"column-1": response.object.submitToClientCount,
			"Color": "#EAEA23",
			"Days": response.object.submitToClientDays,
			"moveforward": response.object.submitToClientMove
		    } );
	}
	if(response.object.interviewsCount>0){
		mainChar.push( {
			"category": "Interviewed",
		    "column-1": response.object.interviewsCount,
		    "Color": "#FFCC80",
		    "Days": response.object.interviewsDays,
		    "moveforward": response.object.interviewsMove
		} );
	}
	
	if(response.object.offeredCount>0){
		mainChar.push({
			"category": "Offered",
			"column-1": response.object.offeredCount,
			"Color": "#B39DDB",
			"Days": response.object.offeredDays,
			"moveforward": response.object.offeredMove
		});
	}
	if(response.object.offeredAcceptedCount>0){
		mainChar.push({
			"category": "Offers Accepted",
			"column-1": response.object.offeredAcceptedCount,
			"Color": "#81C784",
			"Days": response.object.offeredAcceptedDays,
			"moveforward": response.object.offeredAcceptedMove
		});
	}
	
	if(response.object.backoutCount>0){
		mainChar.push({
			"category": "Backout(s)",
			"column-1": response.object.backoutCount,
			"Color": "#E57373",
			"Days": response.object.backoutDays,
			"moveforward": 84
		});
	}
	
	
	AmCharts.makeChart("jobspipline",
			{
				"type": "serial",
				"categoryField": "category",
				"rotate": true,
				"marginBottom": 0,
				"marginLeft": 0,
				"marginRight": 30,
				"marginTop": 0,
				"colors": [],
				"startDuration": 1,
				"fontSize": 10,
				"categoryAxis": {
					"gridPosition": "start",
					"axisThickness": 0,
					"gridThickness": 0,
					"tickLength": 10,
					"titleRotation": -1
				},
				"export": {
				    "enabled": true,
				    "pageOrigin": false,
				    "fileName":"Jobs Pipeline"
				},
				"trendLines": [],
				"graphs": [
					{
						"descriptionField": "Days",
						"fillAlphas": 1,
						"fillColorsField": "Color",
						"id": "AmGraph-1",
						"labelPosition": "right",
						"labelText": "[[value]] <br> [[Days]] Day(s) <br>[[moveforward]]% Moveforward",
						"lineColorField": "Color",
						"lineThickness": 0,
						"maxBulletSize": 0,
						"title": "graph 1",
						"type": "column",
						"valueField": "column-1"
					},
					{
						"id": "AmGraph-2",
						"title": "graph 2",
						"valueField": "Color"
					}
				],
				"guides": [],
				"valueAxes": [
					{
						"id": "ValueAxis-1",
						"axisThickness": 0,
						"gridThickness": 0,
						"labelsEnabled": false,
						"title": ""
					}
				],
				"allLabels": [],
				"balloon": {},
				"titles": [
					{
						"id": "Title-1",
						"size": 15,
						"text": "Jobs Pipeline"
					}
				],
				"dataProvider": mainChar
            }
		); 
    
        jQuery("a[title='JavaScript charts']").remove();
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
    });
    $('#priority').trigger('chosen:updated');
    $(function() {
    });
</script> 
<jsp:include page="../masterFooter.jsp" />
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
		window.onload = function () {
			getRequestedAssociateListDiv();
			var requestUUID = '${requestUUID}';
			if(requestUUID != null && requestUUID != '' && requestUUID != 'undefined'){
				loadRequestDetails();
			}else{
				getApproversList();
			}
		 };
   </script>

<section class="content-wraper" id="themeroller">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Request Associates</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/projectDashboard'/>">Home</a>
			&raquo; <a
				href="<c:url value='${appPath}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=projectMembers'/>">Planning</a>
			&raquo; Request Associates
		</div>
	</section>

	<div class="content-area content-new clearfix">
		<div class="row form-horizontal mt myForm">
			<div id="choicepopUp"></div>
			<div id="associateRequestLoading"></div>

			<form:form id="requestAssociates_form" name="requestAssociates_form"
				method="post">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
							<div class="form-group">
								<label class="col-md-3">Request Id <span
									class="text-danger paddingLeft">*</span></label>
								<div class="col-md-9">
									<div class="form-icon">
										<input type="text" class="form-control pull-left"
											name="requestId" id="requestId" data-validation="required validate_Space validate_length length1-15 validate_int"
											field-name="Request Id"> <i
											class="fa fa-refresh btn btn-default formic1"
											title="Request Id" onclick='$("#requestId").val("");'></i>
										<div class="clearfix"></div>
										<span class="hidden">Request Id</span>
									</div>
								</div>
							</div>
							<input type="hidden" name="savedPlanningRequestUUID"
								id="savedPlanningRequestUUID">

							<!-- <div class="form-group">
								<label class="col-md-3">Approver <span
									class="text-danger paddingLeft">*</span><i class='fa fa-info-circle' title='The person who will approve there source request'></i></label>
								<div class="col-md-9">
									<select class="chosen-select form-control"
										data-validation="required" id="approver" name="approver"
										field-name="Approver">
									</select>
								</div>
							</div> -->

							<div class="form-group">
								<label class="col-md-3">Description <span
									class="text-danger paddingLeft">*</span></label>
								<div class="col-md-9">
									<textarea cols="1" class="form-control"
										field-name="Description" id="description" name="description"
										data-validation="required validate_length length2-5000"></textarea>
								</div>
							</div>

						</div>
					</div>

					<div class="clearfix mt"></div>
					<div id="acceptedAssociatesListDiv" class="col-md-12"></div>
					<div class="clearfix mt"></div>

					<div class="project_side_wrap_inn_tabs clearfix">
						<div class="project_tabs_inn_head">
							<div class="project_tabs_inn_head mt25">
								<a href="javascript:void(0)" title="Search Associates"
									onclick="getAssociates();" id="addDiv"
									class="btn btn-primary btn-sm anchorWhite"> <i
									class="glyphicon glyphicon-search"></i> Search Associates
								</a>
								<div class="clearfix mb10"></div>
							</div>
							<div id="resourceListLoading"></div>
							<div id="searchAssociatesList"></div>
						</div>
					</div>

					<div class="form-footer clearfix mt">
						<div class="col-lg-12">
							<div class="pull-right">
								<input type="button" value="Draft" class="btn btn-warning mr"
									onclick="requestAssociate('Draft');"> <input
									type="button" value="Send" class="btn btn-primary"
									onclick="requestAssociate('Send');"> <input
									type="button" value="Cancel"
									onClick="javascript:window.location.href = '<c:url value='${appPath}/projectSummary?projectUUID=${projectUUID}&projectClientUUID=${projectClientUUID}&tabType=projectMembers'/>'"
									class="btn btn-default ml">
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</section>

<jsp:include page="../masterFooter.jsp" />

<input type="hidden"  name="associateResources[REPLACEROWID].validateRow" value="true">
<div id="divTaskTd1" style="display: none; float: left;">
	<div class="width215px">
		<div id="REPLACEROWIDcandidateName" class="tbldata-dotdot pull-left">
		</div>

		<input type="hidden" class="form-control input-sm" id="REPLACEROWIDcandidateFullName" name="associateResources[REPLACEROWID].candidateFullName" /> <input type="hidden"
			class="form-control input-sm" id="REPLACEROWIDcandidateId" name="associateResources[REPLACEROWID].candidateId" /> 
		<input type="hidden" class="form-control input-sm" id="REPLACEROWIDresourceRequestId" name="associateResources[REPLACEROWID].resourceRequestId"> </input>

		<div class="show_Empdetails" id="REPLACEROWIDresourceRejection" style="display: none;">
			<i class="fa fa-ellipsis-v text-danger"></i>
			<div class="associate-rejects">
				<p id="REPLACEROWIDresourceRejectedReason"></p>
			</div>
		</div>
	</div>
</div>


<div id="divTaskTd2" style="display: none; float: left;">

<div class="changeRate_div changeRateDiv">
                  <div class="pull-left dropdown-select drpdownCurrency">
                      <select id="REPLACEROWIDcandidatePayRateCurrency" name="associateResources[REPLACEROWID].candidatePayRateCurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDcandidatePayRate" name="associateResources[REPLACEROWID].candidatePayRate" field-name="Pay Rate"  data-validation="required validate_Space validate_float validate_length length1-15">
                  </div>
                  
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDcandidatePayRateType" name="associateResources[REPLACEROWID].candidatePayRateType">
	                    <option value="Hourly">Hourly</option>
	                    <option value="Weekly">Weekly</option>
	                    <option value="Bi-weekly">Bi-weekly</option>
	                    <option value="Semi-Monthly">Semi-Monthly</option>
	                    <option value="Monthly">Monthly</option>
	                    <option value="Bi-Monthly">Bi-Monthly</option>
	                    <option value="Yearly">Yearly</option>
                      </select>
                  </div>
     </div>

</div>


<div id="divTaskTd3" style="display: none; float: left;">
	<div class="changeRate_div changeRateDiv">
		<div class="pull-left dropdown-select drpdownCurrency">
			<select id="REPLACEROWIDbillRateCurrency"
				name="associateResources[REPLACEROWID].billRateCurrency">
				<option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
				<option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
				<option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
				<option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
				<option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
				<option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
			</select>
		</div>
		<div class="pull-left inputRate">
			<input class="form-control input-sm" type="text"
				id="REPLACEROWIDbillRate" name="associateResources[REPLACEROWID].billRate"
				data-validation-optional="true"
				data-validation="validate_Space validate_float validate_length length1-15">
		</div>
		<div class="pull-left drpdownMonthly dropdown-select">
			<select id="REPLACEROWIDbillRateRateType"
				name="associateResources[REPLACEROWID].billRateRateType">
				<option value="Hourly">Hourly</option>
				<option value="Weekly">Weekly</option>
				<option value="Bi-weekly">Bi-weekly</option>
				<option value="Semi-Monthly">Semi-Monthly</option>
				<option value="Monthly">Monthly</option>
				<option value="Bi-Monthly">Bi-Monthly</option>
				<option value="Yearly">Yearly</option>
			</select>
		</div>
	</div>
</div>


<div id="divTaskTd6" style="display: none; float: left;">

	<select id="REPLACEROWIDconsultingType"
		name="associateResources[REPLACEROWID].consultingType" data-validation="required"
		field-name="Consulting">
		<option value="Offsite" selected>Off Site</option>
		<option value="Onsite">On Site</option>
		<option value="Onsite">Near Shore</option>
		<option value="Onsite">Off Shore</option>
		<option value="Onsite">Hybrid</option>
	</select>

</div>

<div id="divTaskTd7" style="display: none; float: left;">
	<div class="tbldata-dotdot-100">
		<input class="form-control input-sm" type="text"
			id="REPLACEROWIDestHours" name="associateResources[REPLACEROWID].estHours"
			data-validation-optional="true"
			onblur="calculateTotalEstimateHours();"
			data-validation="validate_Space validate_int" field-name="Est Hours">
	</div>
</div>

<div id="divTaskTd8" style="display: none; float: left;">
	<div class="changeRate_div estRate_div">
		<div class="pull-left mt10">
			<input type="hidden" name="associateResources[REPLACEROWID].estimatedBudgetCurrencyType" value="${SourceLeadWeb_currencyCode}">
		
			<div id="REPLACEROWIDestBudgetCurrencyType"
				name="associateResources[REPLACEROWID].estBudgetCurrencyType"
				value='${SourceLeadWeb_currencyCode}'>${SourceLeadWeb_currencyCode}</div>
		</div>

		<div class="pull-left inputRate">
			<input class="form-control input-sm" type="text"
				id="REPLACEROWIDestBudget" name="associateResources[REPLACEROWID].estBudget"
				data-validation-optional="true"
				onblur="calculateTotalEstimateBudget();"
				data-validation="validate_int validate_Space validate_length length1-15"
				field-name="Est Budget">
		</div>

	</div>
</div>

  <div id="divTaskTd9" style="display: none; float: left;">
	<div class="changeRate_div estRate_div">
         <div class="pull-left inputRate">
         	<input class="form-control input-sm" type="text"  id="REPLACEROWIDpercentageOfWork" name="associateResources[REPLACEROWID].percentageOfWork" 
         	data-validation-optional="true"  data-validation="validate_Space validate_percentage_of_work" field-name="percentageOfWork" onblur="calculatePercentageOfWork();">
         </div>
	</div>
</div>



<div id="divTaskTd10" style="display: none; float: left;">
	<a class="text-danger tblminwidth-50" title="Remove row"
		id="REPLACEROWIDremoveLineItem" onclick="removeRow(REPLACEROWID)"><i
		class="fa fa-remove"></i></a>
</div>




<script>
function getRequestedAssociateListDiv(){
	
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  reqAssociateDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  reqAssociateDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  reqAssociateDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  reqAssociateDate = 'YYYY-DD-MM'; 
	  }  
	
	
	 var result = '';
	 
	 
	 result = result +'<div class="require_wrap_table">';
	 result = result +'  <input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />';
	 result = result +'  <input type="hidden" id="associatesRowsCount" name="associatesRowsCount" value="0" />';
	 result = result +'  <input type="hidden" id="estimatedBudgetCurrencyType" name="estimatedBudgetCurrencyType" value="${SourceLeadWeb_currencyCode}" />';
	 result = result +'<table>';
	 result = result +'<thead id="addRequestTbl">';
	 result = result +'<tr id="divTsRow0">';
	 result = result +'<th>Name</th>';
	 result = result +'<th>Pay Rate</th>';
	 result = result +'<th>Bill Rate</th>';
	 result = result +'<th>Start Date <br>(<small class="custom-small">'+reqAssociateDate+'</small>)</th>';
	 result = result +'<th>End Date <br>(<small class="custom-small">'+reqAssociateDate+'</small>)</th>';
	 result = result +'<th>Consulting</th>';
	 result = result +'<th>Est Hours</th>';
	 result = result +'<th>Est Budget</th>';
	 result = result +'<th>%Of Work</th>';
	 result = result +'<th>Action</th>';
	 result = result +'</tr>';
	 result = result +'</thead>';
	 
	 result = result +'<tfoot class="table_footer">';
	 
	 result = result +'<tr>';

	 result = result +'<td colspan="6"></td>';
	 result = result +'<td><div class="tblminwidth-150"><span class="pull-left paddingRight"></span>Hours<input type="text" value = "0" id="estimatedHrs" name="estimatedHrs" class="ReadTextFli" readonly></div>';
	 result = result +'</td>';
	 
	 
	 result = result +'<td><div class="tblminwidth-150"><span class="pull-left paddingRight">'+currencyCode+'</span><input type="text" value = "0" id="estimatedBudget" name="estimatedBudget" class="ReadTextFli" readonly></div>';
	 result = result +'</td>';
	 result = result +'<td><div class="tblminwidth-175"><span class="pull-left paddingRight">%OfWork</span><input type="text" value = "0" id="totalPercentageOfWork" name="percentageOfWork" class="ReadTextFli" readonly></div></td>';
	 result = result +'</tr>';
	 result = result +'</tfoot>';
	 
	 result = result +'</table>';  
	 result = result +'</div>';   
	 
	 result = result +'<div id="norecordsfound"></div>'; 
	 
	 $("#searchAssociatesList").empty();
     $("#searchAssociatesList").append(result);
     
//      emptyRecordsList();
}
</script>



<script>
function emptyRecordsList(){
	 var result = '';
	 result = result+"   <div class='row'>"; 
	 result = result+"	<div class='col-lg-12'>"; 
	 result = result+"		<div class='dataTables_empty'>"; 
	 result = result+"		</div>"; 
	 result = result+"	</div>"; 
	 result = result+"  </div> ";
	 
	 $("#norecordsfound").empty();
     $("#norecordsfound").append(result);
}
</script>


<script>
function getApproversList(approverId){
var sourceleadUrl ='${sourceleadUrl}';
var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
var actionType = "ROLE_RESOURCE_REQUEST_APPROVER";
	$.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getSourceLeadActionApproversList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&actionType="+actionType,
	    		success: function(json) {
	     		var output = "";
	     			output = output + "<option value=''>--Select Approver--</option>";
	     		for (var i=0; i<json.length; i++)
	     		{
		     		var opt = json[i];
		     		
		     		if(approverId != null && approverId != '' && approverId != 'undefined' && approverId == opt.id){
		     			var selected = 'selected';
		     			output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.name+" ("+opt.userRole+")</option>";
		     		}else{
		     			output = output + "<option value='" +opt.id+ "'>"+opt.name+" ("+opt.userRole+")</option>";
		     		} 
	     		}
	     		$("#approver").empty();
	     		$("#approver").append(output);
	     		$('#approver').trigger("chosen:updated");
	     		
	        	 
	         }
	 });
}
</script>

<script>
function loadRequestDetails(){
	var requestUUID = '${requestUUID}';
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getResourceRequestDetailsByRequestUUID?${_csrf.parameterName}=${_csrf.token}&requestUUID="+requestUUID, 
	         success: function(json) {
	        	 $("#requestId").val(json.requestId);
	        	 $("textarea[name = 'description']").val(json.description);
	        	 getApproversList(json.approverId);
	        	 $("#savedPlanningRequestUUID").val(json.requestUUID);
	        	 
	        	 loadResourceRequestsList(json.resourceRequestList);
	        	 loadApprovedResourceRequestsList(json.resourceRequestList,json.requestStatus);
	        	 
	         }
	 });
	
}
</script>

<script>
function loadApprovedResourceRequestsList(resourceRequestList,requestStatus){
	
	            var approvedCount = 0;
				var result = '';
				result = result +'	     <h4 class="clearfix">Approved Resources</h4>';
			    result = result +'	     <div class="require_wrap_table invoiceGridview clearfix">';
				result = result +'         <table width="100%" cellpadding="0" cellspacing="0">';
				result = result +'             <thead>';
				result = result +'                 <tr>';
				result = result +'                     <th>Name</th>';
				result = result +'                     <th>Pay Rate</th>';
				result = result +'                     <th>Bill Rate</th>';
				result = result +'                     <th>Start Date</th>';
				result = result +'                     <th>End Date</th>';
				result = result +'                     <th>Consulting</th>';
				result = result +'                     <th>Est Hours</th>';
				result = result +'                     <th>Est Budget</th>';
				result = result +'                 </tr>';
				result = result +'             </thead>';
				result = result +'             <tbody>';
				
					 
					  for(var i=0; i<resourceRequestList.length; i++){
						 var opt = resourceRequestList[i];
						 if(opt.resourceStatus != null && opt.resourceStatus == 'Approved'){
							 var startDate = '';
							 var endDate = '';
							 var payRate = ''
							 var estimatedHours = '';
							 var estimatedBudget = '';
							
							 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
								 startDate = opt.startDateStr;
							 }else{
								 startDate = '-----';
							 }
							 
							 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
								 endDate = opt.endDateStr;
							 }else{
								 endDate = '-----';
							 }
							 
							 if(opt.payRate != null && opt.payRate != '' && opt.payRate != 'undefined'){
								 payRate = opt.payRateCurrency+" "+opt.payRate+" / "+opt.payRateType;
							 }else{
								 payRate = '-----';
							 }
							 
							 var billRate = opt.billRateCurrency+" "+opt.billRate+" / "+opt.billRateType;
							 
							 if(opt.estimatedHours != null && opt.estimatedHours != '' && opt.estimatedHours != 'undefined'){
								 estimatedHours = opt.estimatedHours;
							 }else{
								 estimatedHours = '-----';
							 }
							 
							 if(opt.estimatedBudget != null && opt.estimatedBudget != '' && opt.estimatedBudget != 'undefined'){
								 estimatedBudget = opt.estimatedBudget;
							 }else{
								 estimatedBudget = '-----';
							 }
							 
							 if(opt.percentageOfWork != null && opt.percentageOfWork != '' && opt.percentageOfWork != 'undefined'){
								 percentageOfWork = opt.percentageOfWork;
							 }else{
								 percentageOfWork = '-----';
							 }
							 
							    result = result +'<tr>';
							    
							    result = result +'<td>';
							 	result = result +'<div class="tbldata-dotdot" title="'+opt.candidateName+'">'+opt.candidateName+'</div>';
								result = result +'</td>'; 
								
								result = result +'<td>';
							 	result = result +'<div class="tbldata-dotdot-vsm" title="'+payRate+'">'+payRate+'</div>';
								result = result +'</td>';
								
								result = result +'<td>';
							 	result = result +'<div class="tbldata-dotdot-vsm" title="'+billRate+'">'+billRate+'</div>';
								result = result +'</td>';
								
								result = result +'<td>';
							 	result = result +'<div class="tblminwidth-50" title="'+startDate+'">'+startDate+'</div>';
								result = result +'</td>';
								
								result = result +'<td>';
							 	result = result +'<div class="tblminwidth-50" title="'+endDate+'">'+endDate+'</div>';
								result = result +'</td>';
								
								result = result +'<td>';
							 	result = result +'<div class="tbldata-dotdot_80" title="'+opt.consultingType+'">'+opt.consultingType+'</div>';
								result = result +'</td>';
								
								result = result +'<td>';
							 	result = result +'<div class="tbldata-dotdot_80" title="'+estimatedHours+'">'+estimatedHours+'</div>';
								result = result +'</td>';
								
								result = result +'<td>';
							 	result = result +'<div class="tbldata-dotdot_80" title="'+estimatedBudget+'">'+estimatedBudget+'</div>';
								result = result +'</td>';
								
								result = result +'<td>';
							 	result = result +'<div class="tbldata-dotdot_80" title="'+percentageOfWork+'">'+percentageOfWork+'</div>';
								result = result +'</td>';
								
							    result = result +'</tr>';
							    
							    approvedCount = approvedCount+1;
					 }
				 } 
				
				
				result = result +'</tbody>';
				result = result +'</table>';
				result = result +' </div>';
				result = result +'<div id="norecordsfound"></div>'; 
				
				$("#acceptedAssociatesListDiv").empty();  
			    $("#acceptedAssociatesListDiv").append(result);
			    
			    if(approvedCount == 0){
			    	emptyRecordsList();
			    }
}
</script>

<script>
function loadResourceRequestsList(resourceRequestList,estimatedHours,estimatedBudget){
	 var estimatedHours = 0;
	 var estimatedBudget = 0;
	 
	for(var i=0; i<resourceRequestList.length; i++){
		 var opt = resourceRequestList[i];
		    if(opt.resourceStatus == 'Draft' || opt.resourceStatus == 'Rejected'){
			    var checkMaxTsRowNum = $("#maxTsRowNum").val();
				var tbl = document.getElementById("addRequestTbl");
				var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
				$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
				
				var associatesRowsCount = $("#associatesRowsCount").val();
				var rowsCount = parseInt($("input[name = 'associatesRowsCount']").val());
				$("input[name = 'associatesRowsCount']").val(rowsCount + 1);
				
				
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "divTsRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divTaskTd1Str = document.getElementById("divTaskTd1").innerHTML;
				divTaskTd1Str = divTaskTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTaskTd1Str = divTaskTd1Str.replace(/PRICECALCULATION/g,("")); 
				cell0.innerHTML = divTaskTd1Str;
				
				$('#'+maxRowNum+'candidateId').val(opt.requestedCandidateId);
				$('#'+maxRowNum+'candidateName').html(opt.candidateName);
				$('#'+maxRowNum+'candidateFullName').val(opt.candidateName);
				$('#'+maxRowNum+'resourceRequestId').val(opt.id);
				
				if(opt.resourceStatus != 'Approved' && opt.rejectedReason != null){
					document.getElementById(maxRowNum+"resourceRejection").style.display="";
					$('#'+maxRowNum+'resourceRejectedReason').html("Rejected Reason : "+opt.rejectedReason);
				}
				
				
			    var cell1 = row.insertCell(1);
				var divTaskTd2Str = document.getElementById("divTaskTd2").innerHTML;
				divTaskTd2Str = divTaskTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTaskTd2Str = divTaskTd2Str.replace(/PRICECALCULATION/g,("")); 
				cell1.innerHTML = divTaskTd2Str;
				
				
				if(opt.payRate != null && opt.payRate != '' && opt.payRate != 'undefined'){
					$('#'+maxRowNum+'payRateCurrency').html(opt.payRateCurrency);
					$('#'+maxRowNum+'payRate').html(opt.payRate);
					$('#'+maxRowNum+'payRateType').html(opt.payRateType);	
				}else{
					$('#'+maxRowNum+'payRate').html("---------");
				}
				
				$('#'+maxRowNum+'candidatePayRateCurrency').val(opt.payRateCurrency);
				$('#'+maxRowNum+'candidatePayRate').val(opt.payRate);
				$('#'+maxRowNum+'candidatePayRateType').val(opt.payRateType);
				
				var cell2 = row.insertCell(2);
				var divTaskTd3Str = document.getElementById("divTaskTd3").innerHTML;
				divTaskTd3Str = divTaskTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTaskTd3Str = divTaskTd3Str.replace(/PRICECALCULATION/g,("")); 
				cell2.innerHTML = divTaskTd3Str;
				
				$('#'+maxRowNum+'billRateCurrency').val(opt.billRateCurrency);
				$('#'+maxRowNum+'billRate').val(opt.billRate);
				$('#'+maxRowNum+'billRateRateType').val(opt.billRateType);
				
				
				var startDateText = "";
				var cell3 = row.insertCell(3);
				startDateText = startDateText+"<div  class='tbldata-dotdot-100'>";
				startDateText = startDateText+'<input type="text" class="form-control input-sm calendar" id="'+maxRowNum+'sdate-input" value="'+opt.startDateStr+'"  name="associateResources['+maxRowNum+'].requestStartDate">';
				startDateText = startDateText+'</div>';
				cell3.innerHTML = startDateText;
				
				
				var endDateText = "";
				var cell4 = row.insertCell(4);
				endDateText = endDateText+"<div  class='tbldata-dotdot-100'>";
				endDateText = endDateText+'<input type="text" class="form-control input-sm calendar" id="'+maxRowNum+'edate-input" value="'+opt.endDateStr+'"   name="associateResources['+maxRowNum+'].requestEndDate" >';
				endDateText = endDateText+'</div>';
				cell4.innerHTML = endDateText;
				
				
				
				$( "#"+maxRowNum+"sdate-input" ).datepicker({
					  changeMonth: true,
				      changeYear: true,
				      dateFormat : 'dd-mm-yy',
				      minDate : 0,
					  onSelect : function(selected) {
						  var id = $(this).attr('id');
						  var rowId = id.split("sdate-input")[0];
						  var dt = toDate("#"+rowId+"sdate-input");
						  dt.setDate(dt.getDate() + 1);
						  $("#"+rowId+"edate-input").datepicker("option", "minDate", dt);
						  getEstimatedHours(rowId);
				 	  }
				    });
				
				$( "#"+maxRowNum+"edate-input" ).datepicker({
					  changeMonth: true,
				      changeYear: true,
				      dateFormat : 'dd-mm-yy',
				      minDate : 0,
					  onSelect : function(selected) {
						  var id = $(this).attr('id');
						  var rowId = id.split("edate-input")[0];
						   var dt = toDate("#"+rowId+"edate-input");
							dt.setDate(dt.getDate() - 1);
							$("#"+rowId+"sdate-input").datepicker("option", "maxDate", dt);
							getEstimatedHours(rowId)
				 	  }
				    });
				
				
				var cell5 = row.insertCell(5);
				var divTaskTd6Str = document.getElementById("divTaskTd6").innerHTML;
				divTaskTd6Str = divTaskTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTaskTd6Str = divTaskTd6Str.replace(/PRICECALCULATION/g,("")); 
				cell5.innerHTML = divTaskTd6Str;
				
				$('#'+maxRowNum+'consultingType').val(opt.consultingType);
				
				var cell6 = row.insertCell(6);
				var divTaskTd7Str = document.getElementById("divTaskTd7").innerHTML;
				divTaskTd7Str = divTaskTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTaskTd7Str = divTaskTd7Str.replace(/PRICECALCULATION/g,("")); 
				cell6.innerHTML = divTaskTd7Str;
				
				$('#'+maxRowNum+'estHours').val(opt.estimatedHours);
				
				
				var cell7 = row.insertCell(7);
				var divTaskTd8Str = document.getElementById("divTaskTd8").innerHTML;
				divTaskTd8Str = divTaskTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTaskTd8Str = divTaskTd8Str.replace(/PRICECALCULATION/g,("")); 
				cell7.innerHTML = divTaskTd8Str;
				
				$('#'+maxRowNum+'estBudget').val(opt.estimatedBudget);
				
				var cell8 = row.insertCell(8);
				var divTaskTd9Str = document.getElementById("divTaskTd9").innerHTML;
				divTaskTd9Str = divTaskTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
				divTaskTd9Str = divTaskTd9Str.replace(/PRICECALCULATION/g,("")); 
				cell8.innerHTML = divTaskTd9Str;
				
				estimatedHours = estimatedHours+parseInt(opt.estimatedHours);
				estimatedBudget = estimatedBudget+parseInt(opt.estimatedBudget);
	  }
	 }
	 
	 $('#estimatedHrs').val(estimatedHours);
	 $('#estimatedBudget').val(estimatedBudget);
	 $("#norecordsfound").empty();
}
</script>



<script type="text/javascript">
	function removeRow(rowId) {
		
		var associatesRowsCount = $("#associatesRowsCount").val();
		var rowsCount = parseInt($("input[name = 'associatesRowsCount']").val());
		$("input[name = 'associatesRowsCount']").val(rowsCount - 1);
		
		associatesRowsCount = $("#associatesRowsCount").val();
		if(associatesRowsCount == 0){
			emptyRecordsList();
		}
		
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		
		var r = document.getElementById('addRequestTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
			calculateTotalEstimateHours();
			calculateTotalEstimateBudget();
			calculatePercentageOfWork();
		}
		
		
		
	}
</script>


<script>
function calculateTotalEstimateHours(){
	 var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	  if(maxTsRowNum > 1){
		  var totalHours = 0;
		  for(var i=1; i<maxTsRowNum; i++){
			  if(document.getElementById(i+"estHours")){
				 var estimatedHours = document.getElementById(i+"estHours").value;
				  if(estimatedHours != null && estimatedHours != '' && estimatedHours != 'undefined'){
					  totalHours = totalHours+parseInt(document.getElementById(i+"estHours").value);
				  }
			  }
		  }
		  
		  $("#estimatedHrs").val(totalHours);
	  }
}
</script>

<script>
function calculateTotalEstimateBudget(){
	 var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	  if(maxTsRowNum > 1){
		  var totalBudget = 0;
		  for(var i=1; i<maxTsRowNum; i++){
			  if(document.getElementById(i+"estBudget")){
				 var estimatedBuget = document.getElementById(i+"estBudget").value;
				  if(estimatedBuget != null && estimatedBuget != '' && estimatedBuget != 'undefined'){
					  totalBudget = totalBudget+parseInt(document.getElementById(i+"estBudget").value);
				  }
			  }
		  }
		  
		  $("#estimatedBudget").val(totalBudget);
	  }
}
</script>
<script>
function calculatePercentageOfWork(){
	 var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	  if(maxTsRowNum > 1){
		  var totalWork = 0;
		  for(var i=1; i<maxTsRowNum; i++){
			  if(document.getElementById(i+"percentageOfWork")){
				 var perWork = document.getElementById(i+"percentageOfWork").value;
				  if(perWork != null && perWork != '' && perWork != 'undefined'){
					  totalWork = totalWork+parseInt(document.getElementById(i+"percentageOfWork").value);
				  }
			  }
		  }
		  
		  $("#totalPercentageOfWork").val(totalWork);
	  }
}
</script>

<div class="side_mask">
	<div class="proj_close"></div>
</div>
<div id="associatesList"></div>

<script>
function getAssociates(){
	var result = '';
	
	result = result +'<div class="clients_side_wrap">';
	result = result +'<div id="associatesListLoader"></div>';
	result = result +'<div class="new_side_wrap_inn">';
	result = result +'<div class="timesheet_head">';
	      
	result = result +'<ul class="pull-right">';
	

	        
	result = result +'<li class="dropdown">';
	result = result +'<span class="dropdown-toggle" id="menu3" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Role</span><i class="glyphicon glyphicon-play"></i></span>|';
	          
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<div id="orgLocationRoles"></div>';  
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';  
	
	result = result +'<li class="dropdown">';
	result = result +'<span class="dropdown-toggle" id="menu1" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Location</span><i class="glyphicon glyphicon-play"></i></span>|';
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu bootstrap_dropdownMenu-right" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<c:forEach var="locations" items="${orgLocationsList}">';
	result = result +'<li>';
	result = result +'<input class="ml10" value="${locations.id}" onclick="getOrgLocationRoles();"  name="orgLocations" ${SourceLeadWeb_orgLocations.id  == locations.id ? "checked" : ''} type="radio">';
	result = result +'<label class="radioAlign"><img src="/resources/images/${locations.country}.gif">${locations.orgLocationName}</label>';
	result = result +'</li>';
	result = result +'</c:forEach>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';
	          
	result = result +'<li class="dropdown">';
	result = result +'<span class="dropdown-toggle" id="menu2" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play"></i></span>|';
	          
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu2">';
	result = result +'<ul class="resourcetype-filter">';
	result = result +'<li>';
	result = result +'<input value="hotlist" id="hotlist" name="resourceType" type="checkbox" onclick="getAssociatesFilter()" >';
	result = result +'<label for="hotlist">Hotlist</label>';
	result = result +'</li>';
	result = result +'<li>';
	result = result +'<input  value="assigned" id="Assigned" name="resourceType" type="checkbox" onclick="getAssociatesFilter()" >';
	result = result +'<label for="Assigned">Assigned</label>';
	result = result +'</li>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</ul>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</div>';
	
	result = result +'<div id="associatesListFilter"></div>';

	result = result +'</div>';
	result = result +'</div>';
	
	 $("#associatesList").empty();
     $("#associatesList").append(result);
    
    getOrgLocationRoles();
 	
}
</script>


<script>
function getOrgLocationRoles(){
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
	    });
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getOrgLocationRoles?${_csrf.parameterName}=${_csrf.token}&orgLocations="+orgLocations, 
	        success: function(json) {
	        	var output = "";
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + '<li><input value="'+opt.id+'" name="roles" checked type="checkbox" onclick="getAssociatesFilter()"><label>'+opt.displayName+'</label></li>';
				}
	        	$("#orgLocationRoles").empty();
		        $("#orgLocationRoles").append(output);
		        getAssociatesFilter();
	        }
	        
	 });
}
</script>


<script>
function getAssociatesFilter(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='candidateDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="clearfix popupFilter">'; 
	 	
  		result = result +'<div class="require_wrap_table datatable_filter overflow_inherint">';
    	result = result +'<table id="associatesListPagination" cellspacing="0" width="100%">';
  		result = result +'<thead>';
    	result = result +'<tr>';
    	result = result +'<th><input type="checkbox" id="checkAllAssociates" onclick="toggleAssociates(this);"></th>';
    	result = result +'<th>Name</th>';
    	result = result +'<th>Primary Skill</th>';
    	result = result +'<th>Rate</th>';
    	result = result +'<th>Emp. Type</th>';
    	result = result +'<th>Manager</th>';
        result = result +'<th>Resume</th>'; 
    	result = result +'<th>Projects</th>';
    	result = result +'</tr>';
    	result = result +'</thead>';
    	result = result +"</table>";
	    
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'   <div class="modal-footer">';
     	result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="addRequestedResources();">Add</button>';
     	result = result +'   </div>'; 
     	$("#associatesListFilter").empty();
	    $("#associatesListFilter").append(result);
	    $("#associatesListLoader").removeAttr("style"); 
	    checkedAssociatedUsers = {};
	    candidatesDataTableList();
}
</script>


<script>
function candidatesDataTableList(){
	var projectUUID = '${projectUUID}';
	var roles = [];
	$('input:checkbox[name=roles]:checked').each(function() 
		    {
		roles.push($(this).val());
	    });
	
	var orgLocationId
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocationId = $(this).val();
	    });
	
	var resourceType = [];
	$('input:checkbox[name=resourceType]:checked').each(function() 
		    {
		resourceType.push($(this).val());
    });
	
	
	 var maxTsRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
	  var selectedAssociates = '';
	  if(maxTsRowNum > 1){
		  for(var i=1; i<maxTsRowNum; i++){
			  if(document.getElementById(i+"candidateId")){
				  var associateId = document.getElementById(i+"candidateId").value;
				  if(selectedAssociates == ''){
					  selectedAssociates = associateId;
				  }else{
					  selectedAssociates = selectedAssociates+","+associateId;
				  } 
			  }
		  }
	  }
	  var searchValue = $("#candidateDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getRequestedAssociatesList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&roles="+roles+"&resourceType="+resourceType+"&selectedAssociates="+selectedAssociates+"&projectUUID="+projectUUID;
		 candidatesPagination(sourceleadUrl,url);
	  
	
}
</script>

<script>
function candidatesPagination(sourceleadUrl,url){
	var colIndex = 0;
    $('#associatesListPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 4
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "name" },
            { "data": "primarySkill"},
            { "data": "rate"},
            { "data": "employmentType"},
            { "data": "manager"},
            { "data": "resume"},
            { "data": "assignedProjectsCount"}
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, report, meta) {
    	  		 var result = "";
    	  		 var candidate = '';
        		 var currentRate = '';
        		 var currentRateType = '';
        		 var currentRateCurrency = '';
        		 var payrate = ''
        		 var flag = false;
        			 if(report.lastName != null && report.lastName != '' && report.lastName != 'undefined'){
 	        			candidate = report.firstName+" "+report.lastName;
 	        		}else{
 	        			candidate = report.firstName;
 	        		}
 	        		
 	        		if(report.currentRate != null && report.currentRate != '' && report.currentRate != 'undefined'){
 	        			currentRate = report.currentRate;
 	        			currentRateType = report.currentRateType;
 	        			currentRateCurrency = report.currentRateCurrency
 	        			payrate = report.currentRate;
 	        		}else{
 	        			currentRate = "-----";
 	        		}
 	        		
 	        		 for(var e in checkedAssociatedUsers) {
 	     	    	   if(!e || !checkedAssociatedUsers[e] || checkedAssociatedUsers[e] === "")
 	     				   continue;
 	     	    	   if(e === report.id+"") {
 	     	    		   if(!$("#checkAllAssociates").is(":checked"))
 	     	    		 	checkOrUncheckParent($('#associatesListPagination').DataTable().data());
 	     	    	   	flag = true;
 	     	    	   	break;
 	     	    	   }
 	        		 }
 	        		 if(!flag)
 	        			$("#checkAllAssociates").removeAttr('checked');
 	    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+report.id+'" value="'+report.id+','+candidate+','+payrate+','+currentRateCurrency+','+currentRateType+'"  data-id="'+report.id+'"  onchange="checkAssociateUsers(this)"  type="checkbox" name="associates[]"  class="myAssociatesCheckbox" ></td>';
 		              return result;
 		              
    	  		 
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "name",
	    	  	"render": function(data, type, report, meta) {
	    	  		
	    	  		var candidate = "";
	    	  		 if(report.lastName != null && report.lastName != '' && report.lastName != 'undefined'){
	 	        			candidate = report.firstName+" "+report.lastName;
	 	        		}else{
	 	        			candidate = report.firstName;
	 	        		}
	    	  		
	    	  		var result = "";
	    	  		result = result +'<td>';
		        	result = result +'<div title="'+candidate+'">';
		        	
		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+candidate+'</p>';
		        	result = result +'<div class="show_Empdetails">';
		        	result = result +'<i class="fa fa-ellipsis-v"></i>';

		        	result = result +'<div class="emailvalues">';
		        	result = result +'<p title="'+report.userRole+'"><i class="fa fa-user"></i>'+report.userRole+'</p>';
		        	result = result +'<p title="'+report.email+'"><i class="fa fa-envelope"></i>'+report.email+'</p>';
		        	if(report.mobilePhone != null && report.mobilePhone != '' && report.mobilePhone != 'undefined'){
		        		result = result +'<p title="'+report.mobilePhone+'"><i class="fa fa-phone"></i>'+report.mobilePhone+'</p>';
		        	}
		        	result = result +'</div>';
		        	result = result +'</div>';

		        	result = result +'</div>';
		        	result = result +'</td>';
		            return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "primarySkill",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  var primarySkill = "";
            	  if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefined'){
	        			primarySkill = report.primarySkill;
	        		}else{
	        			primarySkill = "-----"
	        		}
            	  
            	    result = result +'<td>';
		        	result = result +'<div title="'+primarySkill+'" class="tbldata-dotdot_80">'+primarySkill+'</div>';
		        	result = result +'</td>';
	                return result;
              		
              }
          },{
	    	  "targets": colIndex++,
              "data": "rate",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  var currentRate = '';
         		 var currentRateType = '';
         		 var currentRateCurrency = '';
         		 var payrate = ''
            	  if(report.currentRate != null && report.currentRate != '' && report.currentRate != 'undefined'){
	        			currentRate = report.currentRate;
	        			currentRateType = report.currentRateType;
	        			currentRateCurrency = report.currentRateCurrency
	        			payrate = report.currentRate;
	        		}else{
	        			currentRate = "-----";
	        		}
            	  
            	    result = result +'<td>';
		        	result = result +'<div class="tbldata-dotdot-200">';
		        	result = result +'<small class="pull-left">'+currentRateCurrency+'</small>';
		        	result = result +'<div class="tbldata-dotdot-100 pull-left">'+currentRate+'</div>';
		        	result = result +'<small class="pull-right">'+currentRateType+'</small>';
		        	result = result +'</div>';
		        	result = result +'</td>';
	                return result;
              		
              }
          },{
		    	  "targets": colIndex++,
	              "data": "employmentType",
	              "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    var designationType = "";
	            		if(report.designationType != null && report.designationType != '' && report.designationType != 'undefined'){
		        			designationType = report.designationType;
		        		}else{
		        			designationType = "-----"
		        		}
	            		
	            	    result = result +'<td>';
			        	result = result +'<div title="'+designationType+'" class="tbldata-dotdot_80">'+designationType+'</div>';
			        	result = result +'</td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "manager",
	              "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    result = result +'<td>';
			        	result = result +'<div title="'+report.reportingManager+'" class="tbldata-dotdot_80">'+report.reportingManager+'</div>';
			        	result = result +'</td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "resume",
	              "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    var candidateResume = "";
	            	    
	            	    if(report.resumeUUID != null && report.resumeUUID != '' && report.resumeUUID != 'undefined'){
		        			candidateResume = '<a  class="fa fa-download" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"title="Download"></a>';
		        		}else{
		        			candidateResume = "-----";
		        		} 
	            	    
	            	    result = result +'<td>';
			        	result = result +candidateResume;
			        	result = result +'</td>'; 
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "assignedProjectsCount",
	              "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    result = result +'<td>';
			        	result = result +'<div title="Development" class="tblminwidth-50 projects_div">  ';                
			        	result = result +report.assignedProjectsCount;
			        	result = result +'</div>';
			        	result = result +'</td>';
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
	     "initComplete": function( settings, json ) {
	    	 checkOrUncheckParent(json.data);
	     }
    } );
    $("#associatesListPagination").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#candidateDataTableSearch").keyup(function(e) {
    	   
    	   if(e.which === 13) {
      		    $('#candidateDataTableSearch').parent().find('p.jquery_form_error_message').remove();
      	    	var searchData = $('#candidateDataTableSearch').val();
      	    	var regex =  new RegExp(/[\\-]/g);
      	    	if(regex.test(searchData) == false){
      	    		candidatesDataTableList();
      	    	}else{
      	    		$("#candidateDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
      	    	}
      	    	$('#candidateDataTableSearch').val(searchData);
       	   }
    	   
    	});
}

function checkOrUncheckParent(data) {
	$("#checkAllAssociates").removeProp("checked");
	   var count =0;
	 for(var e in checkedAssociatedUsers) {
	   if(!e || !checkedAssociatedUsers[e] || checkedAssociatedUsers[e] === "")
		   continue;
	   for(var f in data) {
   	   if(e === data[f].id+"") {
   		count ++;
   	   }
		}
	 }
	 if(count == 10) {
		$("#checkAllAssociates").prop("checked", "checked");
	 }
}
</script>

<script>
var checkedAssociatedUsers = {};
function checkAssociateUsers(elem) {
	if($(elem).is(":checked")) {
		checkedAssociatedUsers[$(elem).data("id")] = $(elem).val();
	} else{
		$("#checkAllAssociates").removeProp('checked');
		checkedAssociatedUsers[$(elem).data("id")] = "";
	}
}
</script>


<script>
function toggleAssociates(source) {
  var checkboxes = document.querySelectorAll('input[class="myAssociatesCheckbox"]');
  if($(source).is(":checked"))
	  $('input[class="myAssociatesCheckbox"]').prop('checked', 'checked');
  else 
	  $('input[class="myAssociatesCheckbox"]').removeProp('checked');
  
  $('input[class="myAssociatesCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
	      checkedAssociatedUsers[$(this).data("id")] = $(this).val();
	  } else {
		  checkedAssociatedUsers[$(this).data("id")] = "";
	  }
  });
}
</script>

<script>
function pagination(){  
    $('#associatesListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 30, 50, -1], [10, 15, 30, 50, "All"]],
		"aaSorting": [[4,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#associatesListPagination').wrap("<div class='overflow_Auto'></div>");
}
</script>


<script>
function requestAssociate(saveType){
	 if ($('#requestAssociates_form').validate(false, validationSettings)){ 
 		var associatesRowsCount = document.getElementById("associatesRowsCount").value;
 		if(associatesRowsCount == 0){
 			alertMessage();
 			 return false;
 		}else{
 			var form = $('#requestAssociates_form')[0];
 		    var formData = new FormData(form);
 		  	
      		var sourceleadUrl ='${sourceleadUrl}';
      		var projectUUID = '${projectUUID}';
      		var userId = '${SourceLeadWeb_user.id}';
      		var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
      		var projectClientUUID = '${projectClientUUID}';
      		
      		$("#associateRequestLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      		$.ajax({
					type : "POST",
	  	    	    data: formData,
	  		        processData: false,
	  		        contentType:false, 
					url : sourceleadUrl+"/saveResourceRequest?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&saveType="+saveType+"&loginuserId="+userId+"&loginOrglocationId="+orgLocationId, 
					success : function(result) {
						if (result && result.response === "INVALID_DATA") {
								$("#associateRequestLoading").removeAttr("style");
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
								location.replace(sourceleadUrl+"/projectSummary?projectUUID="+projectUUID+"&projectClientUUID="+projectClientUUID+"&tabType=resources");
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    showError("Operation failed, please try again");
			                }
						
					},
				});  
      		 return true;
         	   
 		}
	 }else{
			return false;
	  }
	 
	  $('body').on('blur', '#requestAssociates_form', function() {
   		$("#requestAssociates_form").showHelpOnFocus();
   		$("#requestAssociates_form").validateOnBlur(false, validationSettings);
   	});
}
</script>

<script type="text/javascript">
function alertMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Select atleast one associate</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script>



<script type="text/javascript">
	function addRequestedResources() {
		var projectStartDate = '${projectStartDate}';
		var projectEndDate = '${projectEndDate}';
		
		var oTable = $('#associatesListPagination').dataTable();
		var rowcollection =  oTable.$("input[name='associates[]']:checked", {"page": "all"});
		
		   for(var e in checkedAssociatedUsers) {
			   
			   if(!e || !checkedAssociatedUsers[e] || checkedAssociatedUsers[e] === "")
				   continue;
			   
		   var checkbox_value = checkedAssociatedUsers[e].split(",");
		   
			var checkMaxTsRowNum = $("#maxTsRowNum").val();
			var tbl = document.getElementById("addRequestTbl");
			var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
			$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
			
			var associatesRowsCount = $("#associatesRowsCount").val();
			var rowsCount = parseInt($("input[name = 'associatesRowsCount']").val());
			$("input[name = 'associatesRowsCount']").val(rowsCount + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divTsRow" + maxRowNum);
			
			var cell0 = row.insertCell(0);
			var divTaskTd1Str = document.getElementById("divTaskTd1").innerHTML;
			divTaskTd1Str = divTaskTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd1Str = divTaskTd1Str.replace(/PRICECALCULATION/g,("")); 
			cell0.innerHTML = divTaskTd1Str;
			
			$('#'+maxRowNum+'candidateId').val(checkbox_value[0]);
			$('#'+maxRowNum+'candidateName').html(checkbox_value[1]);
			$('#'+maxRowNum+'candidateFullName').val(checkbox_value[1]);
			
			
			var cell1 = row.insertCell(1);
			var divTaskTd2Str = document.getElementById("divTaskTd2").innerHTML;
			divTaskTd2Str = divTaskTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd2Str = divTaskTd2Str.replace(/PRICECALCULATION/g,("")); 
			cell1.innerHTML = divTaskTd2Str;
			
			if(checkbox_value[3] != null && checkbox_value[3] != '' && checkbox_value[3] != 'undefined'){
				$('#'+maxRowNum+'payRateCurrency').val(checkbox_value[3]);
			}
			
			if(checkbox_value[2] != null && checkbox_value[2] != '' && checkbox_value[2] != 'undefined'){
				$('#'+maxRowNum+'payRate').val(checkbox_value[2]);
			}
			
			
			if(checkbox_value[4] != null && checkbox_value[4] != '' && checkbox_value[4] != 'undefined'){
				$('#'+maxRowNum+'payRateRateType').val(checkbox_value[4]);
			}
			
			
			/* if(checkbox_value[2] != null && checkbox_value[2] != '' && checkbox_value[2] != 'undefined'){
				$('#'+maxRowNum+'payRateCurrency').html(checkbox_value[3]);
				$('#'+maxRowNum+'payRate').html(checkbox_value[2]);
				$('#'+maxRowNum+'payRateType').html(checkbox_value[4]);	
			}else{
				$('#'+maxRowNum+'payRate').html("---------");
			}
			
			$('#'+maxRowNum+'candidatePayRateCurrency').val(checkbox_value[3]);
			$('#'+maxRowNum+'candidatePayRate').val(checkbox_value[2]);
			$('#'+maxRowNum+'candidatePayRateType').val(checkbox_value[4]); */
			
			var cell2 = row.insertCell(2);
			var divTaskTd3Str = document.getElementById("divTaskTd3").innerHTML;
			divTaskTd3Str = divTaskTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd3Str = divTaskTd3Str.replace(/PRICECALCULATION/g,("")); 
			cell2.innerHTML = divTaskTd3Str;
			
			
			
			var startDateText = "";
			var cell3 = row.insertCell(3);
			startDateText = startDateText+"<div  class='tbldata-dotdot-100'>";
			startDateText = startDateText+'<input type="text" class="form-control input-sm calendar" id="'+maxRowNum+'sdate-input"  value="'+projectStartDate+'" name="associateResources['+maxRowNum+'].requestStartDate" readonly >';
			startDateText = startDateText+'</div>';
			cell3.innerHTML = startDateText;
			
			
			var endDateText = "";
			var cell4 = row.insertCell(4);
			endDateText = endDateText+"<div  class='tbldata-dotdot-100'>";
			endDateText = endDateText+'<input type="text" class="form-control input-sm calendar" id="'+maxRowNum+'edate-input"  value="'+projectEndDate+'" name="associateResources['+maxRowNum+'].requestEndDate" readonly>';
			endDateText = endDateText+'</div>';
			cell4.innerHTML = endDateText;
			
			var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
			  var finalDateFormatResourceReqSave = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatResourceReqSave = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatResourceReqSave = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatResourceReqSave = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatResourceReqSave = 'yy-dd-mm'; 
			  }
			  var from = projectStartDate.split("-");
		        var datepickerStartDate =  new Date(from[2], from[1] - 1, from[0]);
		        datepickerStartDate.setDate(datepickerStartDate.getDate() + 1);
							if(projectEndDate !=  null && projectEndDate != '' && projectEndDate != 'undefined'){
								$("#"+maxRowNum+"sdate-input").datepicker({
									dateFormat : finalDateFormatResourceReqSave,
									changeMonth: true,
									changeYear: true, 
									minDate:projectStartDate,
									maxDate:projectEndDate,
							        onClose : function(selected) {
							        	var dt = $(this).datepicker('getDate', '+1d');
										dt.setDate(dt.getDate() + 1);
										$("#"+maxRowNum+"edate-input").datepicker("option", "minDate", dt);
						                removeStartDateValidation(maxRowNum);
									}
								});

								$("#"+maxRowNum+"edate-input").datepicker({
									dateFormat : finalDateFormatResourceReqSave,
									changeMonth: true,
									changeYear: true, 
									maxDate:projectEndDate,
									minDate: datepickerStartDate,
							        onClose : function(selected) {
							        	 var dt = $(this).datepicker('getDate', '-1d');
											dt.setDate(dt.getDate() - 1);
											$("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
									}
								});
								
							}else{
								$("#"+maxRowNum+"sdate-input").datepicker({
									dateFormat : finalDateFormatResourceReqSave,
									changeMonth: true,
									changeYear: true, 
									minDate:projectStartDate,
							        onClose : function(selected) {
							        	var dt = $(this).datepicker('getDate', '+1d');
										dt.setDate(dt.getDate() + 1);
										$("#"+maxRowNum+"edate-input").datepicker("option", "minDate", dt);
						                removeStartDateValidation(maxRowNum);
									}
								});

								$("#"+maxRowNum+"edate-input").datepicker({
									dateFormat : finalDateFormatResourceReqSave,
									changeMonth: true,
									changeYear: true, 
									minDate:datepickerStartDate,
							        onClose : function(selected) {
							        	 var dt = $(this).datepicker('getDate', '-1d');
											dt.setDate(dt.getDate() - 1);
											$("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
									}
								});
							}
						
			 if(projectStartDate != null && projectStartDate != 'undefined' && projectStartDate != '' && projectEndDate != null && projectEndDate != 'undefined' && projectEndDate != ''){
				   getEstimatedHours(maxRowNum)
		      }	
			 
			var cell5 = row.insertCell(5);
			var divTaskTd6Str = document.getElementById("divTaskTd6").innerHTML;
			divTaskTd6Str = divTaskTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd6Str = divTaskTd6Str.replace(/PRICECALCULATION/g,("")); 
			cell5.innerHTML = divTaskTd6Str;
			
			
			var cell6 = row.insertCell(6);
			var divTaskTd7Str = document.getElementById("divTaskTd7").innerHTML;
			divTaskTd7Str = divTaskTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd7Str = divTaskTd7Str.replace(/PRICECALCULATION/g,("")); 
			cell6.innerHTML = divTaskTd7Str;
			
			var cell7 = row.insertCell(7);
			var divTaskTd8Str = document.getElementById("divTaskTd8").innerHTML;
			divTaskTd8Str = divTaskTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd8Str = divTaskTd8Str.replace(/PRICECALCULATION/g,("")); 
			cell7.innerHTML = divTaskTd8Str;
			
			
			var cell8 = row.insertCell(8);
			var divTaskTd9Str = document.getElementById("divTaskTd9").innerHTML;
			divTaskTd9Str = divTaskTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd9Str = divTaskTd9Str.replace(/PRICECALCULATION/g,("")); 
			cell8.innerHTML = divTaskTd9Str;
			
			
			var cell9 = row.insertCell(9);
			var divTaskTd10Str = document.getElementById("divTaskTd10").innerHTML;
			divTaskTd10Str = divTaskTd10Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd10Str = divTaskTd10Str.replace(/PRICECALCULATION/g,("")); 
			cell9.innerHTML = divTaskTd10Str;
			
			 $("#norecordsfound").empty();
			
			
		   }
		

 		
 		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
	}
  </script>

<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>


<script>
function getEstimatedHours(rowId){
	var startDate = document.getElementById(rowId+"sdate-input").value;
	var endDate = document.getElementById(rowId+"edate-input").value;
	var locationId = '${SourceLeadWeb_orgLocationId}';
	if(startDate && endDate){
		 var sourceleadUrl ='${sourceleadUrl}';
		 var averageWorkingHours = '${SourceLeadWeb_orgLocations.averageWorkingHours}';
		 $("#resourceListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getEstimatedHoursForResourceRequest?${_csrf.parameterName}=${_csrf.token}&startDate="+startDate+"&endDate="+endDate+"&averageWorkingHours="+averageWorkingHours+"&locationId="+locationId, 
		        success: function(estimatedHours) {
		        	
		        	$('#'+rowId+'estHours').val(estimatedHours);
		        	calculateTotalEstimateHours();
		        	 $("#resourceListLoading").removeAttr("style"); 
		        	
		        }
		        
		 });
	}
}
</script>






<script type="text/javascript">
	$(".AdRes").click(function(){
		$(".addResource").slideToggle(0);
	});
	$(".addResClo").click(function(){
		$(".addResource").slideUp(0);
	});		
	</script>

<script type="text/javascript">
// project side bar tabs 
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});	
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
</script>

<script type="text/javascript">
    $(document).ready(function() {
    $("#addDiv").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
	});	
});

</script>

<script type="text/javascript">
		function closePop(){
			$("div.cand-advsrch-popup").hide();
		}
  </script>

<script type="text/javascript">
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
		}
	</script>
	

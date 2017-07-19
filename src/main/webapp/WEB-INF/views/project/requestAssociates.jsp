<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <script type="text/javascript">
		window.onload = function () {
			getRequestedAssociateListDiv();
		 };
   </script>

<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Request Associates</h2>
      </div>
		<div class="pull-right brud-crum"><a href="dashboard_us.html">Home</a> &raquo; <a href="project_Planning_US.html">Planning</a> &raquo; Request Associates</div>
    </section>
    
<div class="content-area content-new clearfix">
    <div class="row form-horizontal mt myForm">
      <div id="choicepopUp"></div>
      <div id="associateRequestLoading"></div>
      
      <form:form id="requestAssociates_form" name="requestAssociates_form" method="post">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
         <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Request Id </label>
				<div class="col-md-9">
                  <div class="form-icon">
		                <input type="text" class="form-control pull-left" name="requestId" id="requestId" data-validation="required" field-name="Project Id">
		                  <i class="fa fa-refresh btn btn-default formic1" title="Project Id"></i>
		                  <div class="clearfix"></div>
		                <span class="hidden">Request Id</span> 
	                </div>
                  </div>            
            </div>
            <div class="form-group">
              <label class="col-md-3">Description <span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="description" name="description" data-validation="required" field-name="Description">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Approver <span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <select class="chosen-select form-control" data-validation="required" field-name="Approver">
                <option selected value=''>--Select Approver--</option>
	                <c:forEach var="approver" items="${approversList}">
	                    <option value="${approver.id}">${approver.fullName} (${approver.userType})</option>
	                </c:forEach>
                </select>
              </div>
            </div>
            </div>
          </div>

<div class="project_side_wrap_inn_tabs clearfix">
    <div class="project_tabs_inn_head">
        	<div class="project_tabs_inn_head">
        	   <a href="javascript:void(0)" class="" title="Search Associates" onclick="getAssociates();" id="addDiv">
                   <i class="glyphicon glyphicon-plus"></i>
                   Search Associates
               </a>
			</div>
		<div id="searchAssociatesList"></div>
    </div>
</div>        

	<div class="form-footer clearfix mt">
		  <div class="col-lg-12">
			<div class="pull-right">
			  <input type="submit" value="Save" class="btn btn-primary" id="save_request_associate">
			  <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/projectSummary?projectUUID=${projectUUID}&tabType=resources'/>'" class="btn btn-default ml">
			</div>
		  </div>
	</div>        
   </div> 
    </form:form>
      </div>
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />	

<div id="divTaskTd1" style="display: none; float: left;">
	<div class="tbldata-dotdot-100">
		<div id="REPLACEROWIDcandidateName">
		</div>
		
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidateFullName" name="REPLACEROWIDcandidateFullName" />
		
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidateId" name="REPLACEROWIDcandidate" />
		
		<!-- <input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDlineItemId" name="REPLACEROWIDlineItemId">
		</input> -->
	</div>
</div>


<div id="divTaskTd2" style="display: none; float: left;">
	<div class="tbldata-dotdot-sm">
         <small class="pull-left"><div id="REPLACEROWIDpayRateCurrency"></div></small>
         <div class="tbldata-dotdot-100 pull-left" id="REPLACEROWIDpayRate" ></div>
         <small class="pull-right"><div id="REPLACEROWIDpayRateType" ></div></small>
	</div>
	
	 <input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidatePayRateCurrency" name="REPLACEROWIDcandidatePayRateCurrency" />
			
			<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidatePayRate" name="REPLACEROWIDcandidatePayRate" />
			
			<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidatePayRateType" name="REPLACEROWIDcandidatePayRateType" />
</div>


<div id="divTaskTd3" style="display: none; float: left;">
	<div class="changeRate_div changeRateDiv">
                  <div class="pull-left dropdown-select drpdownCurrency">
                      <select id="REPLACEROWIDcurrency" name="REPLACEROWIDcurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDbillRate" name="REPLACEROWIDbillRate"  data-validation="validate_Space validate_int validate_length length1-15">
                  </div>
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDrateType" name="REPLACEROWIDrateType">
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

	<select id="REPLACEROWIDconsultingType" name="REPLACEROWIDconsultingType" data-validation="required" field-name="Consulting">
                      <option value="">--Select--</option>
                      <option value="Offsite">Off Site</option>
                      <option value="Onsite">On Site</option>
                      <option value="Onsite">Near Shore</option>
                      <option value="Onsite">Off Shore</option>
                    <option value="Onsite">Hybrid</option>
   </select>
		
</div>

<div id="divTaskTd7" style="display: none; float: left;">
	<div class="tbldata-dotdot-100">
         <input class="form-control input-sm" type="text" id="REPLACEROWIDestHours" name="REPLACEROWIDestHours" data-validation-optional="true" data-validation="validate_Space validate_int" field-name="Est Hours">
	</div>
</div>

<div id="divTaskTd8" style="display: none; float: left;">
	<div class="changeRate_div estRate_div">
          
           <div class="pull-left dropdown-select drpdownCurrency">
                      <select id="REPLACEROWIDestBudgetCurrencyType" name="REPLACEROWIDestBudgetCurrencyType">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
         </div>
          
         <div class="pull-left inputRate">
         	<input class="form-control input-sm" type="text" id="REPLACEROWIDestBudget" name="REPLACEROWIDestBudget" data-validation-optional="true"  data-validation="validate_int validate_Space validate_length length1-15" field-name="Est Budget">
         </div>
        
	</div>
</div>
                  
 <div id="divTaskTd9" style="display: none; float: left;">
       <a class="text-danger tblminwidth-50" title="Remove row"
		id="REPLACEROWIDremoveLineItem" onclick="removeRow(REPLACEROWID)"><i
		class="fa fa-remove"></i></a>
 </div>          
                        
                    



<script>
function getRequestedAssociateListDiv(){
	
	 var result = '';
	 
	 result = result +'<div class="require_wrap_table mt10">';
	 result = result +'  <input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />';
	 result = result +'  <input type="hidden" id="associatesRowsCount" name="associatesRowsCount" value="0" />';
	 result = result +'<table>';
	 result = result +'<tbody>';
	 result = result +'<thead id="addRequestTbl">';
	 result = result +'<tr id="divTsRow0">';
	 result = result +'<th>Name</th>';
	 result = result +'<th>Pay Rate</th>';
	 result = result +'<th>Bill Rate</th>';
	 result = result +'<th>Start Date</th>';
	 result = result +'<th>End Date</th>';
	 result = result +'<th>Consulting</th>';
	 result = result +'<th>Est Hours</th>';
	 result = result +'<th>Est Budget</th>';
	 result = result +'<th>Action</th>';
	 result = result +'</tr>';
	 result = result +'  </thead>';
	 result = result +'</tbody>';
	 result = result +'</table>';  
	 result = result +'</div>';   
	 
	 result = result +'<div id="norecordsfound"></div>'; 
	 
	 $("#searchAssociatesList").empty();
     $("#searchAssociatesList").append(result);
     
     emptyRecordsList();
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
	result = result +'<label class="radioAlign"><img src="resources/images/${locations.country}.gif">${locations.orgLocationName}</label>';
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
	result = result +'<input value="hotlist" id="hotlist" name="resourceType" checked type="checkbox" onclick="getAssociatesFilter()" >';
	result = result +'<label for="hotlist">Hotlist</label>';
	result = result +'</li>';
	result = result +'<li>';
	result = result +'<input  value="assigned" id="Assigned" name="resourceType" checked type="checkbox" onclick="getAssociatesFilter()" >';
	result = result +'<label for="Assigned">Assigned</label>';
	result = result +'</li>';
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</ul>';
	result = result +'<div class="clearfix"></div>';
	result = result +'</div>';
	     
	result = result +'<div id="associatesListLoader"></div>';
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
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
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
	  
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#associatesListLoader").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getRequestedAssociatesList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&roles="+roles+"&resourceType="+resourceType+"&selectedAssociates="+selectedAssociates, 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	
	        	var result = '';
	        	result = result +'<div class="require_wrap_table datatable_filter overflow_inherint">';
	        	result = result +'<table id="associatesListPagination" cellspacing="0" width="100%">';
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result +'<th><input type="checkbox" id="checkAllAssociates" onclick="toggleAssociates(this);"></th>';
	        	/* result = result +'<th>ID</th>'; */
	        	result = result +'<th>Name</th>';
	        	result = result +'<th>Primary Skill</th>';
	        	result = result +'<th>Rate</th>';
	        	result = result +'<th>Emp. Type</th>';
	        	result = result +'<th>Manager</th>';
	            result = result +'<th>Resume</th>'; 
	        	result = result +'<th>Projects</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';
	        	
	        	for (var i=0; i<json.length; i++){
	        		var report = json[i];
	        		
	        		var candidate = '';
	        		var primarySkill = '';
	        		var currentRate = '';
	        		var currentRateType = '';
	        		var currentRateCurrency = '';
	        		var designationType = '';
	        		var candidateResume = '';
	        		var payrate = ''
	        		
	        		
	        		if(report.lastName != null && report.lastName != '' && report.lastName != 'undefined'){
	        			candidate = report.firstName+" "+report.lastName;
	        		}else{
	        			candidate = report.firstName;
	        		}
	        		
	        		if(report.primarySkill != null && report.primarySkill != '' && report.primarySkill != 'undefined'){
	        			primarySkill = report.primarySkill;
	        		}else{
	        			primarySkill = "-----"
	        		}
	        		
	        		if(report.designationType != null && report.designationType != '' && report.designationType != 'undefined'){
	        			designationType = report.designationType;
	        		}else{
	        			designationType = "-----"
	        		}
	        		
	        		if(report.currentRate != null && report.currentRate != '' && report.currentRate != 'undefined'){
	        			currentRate = report.currentRate;
	        			currentRateType = report.currentRateType;
	        			currentRateCurrency = report.currentRateCurrency
	        			payrate = report.currentRate;
	        		}else{
	        			currentRate = "-----";
	        		}
	        		
	        		 
	        		if(report.resumeUUID != null && report.resumeUUID != '' && report.resumeUUID != 'undefined'){
	        			candidateResume = '<a  class="fa fa-download" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"title="Download"></a>';
	        		}else{
	        			candidateResume = "-----";
	        		} 
	        		
	        		    result = result +'<tr>';
			        	result = result +'<td><input id="'+report.id+'" value="'+report.id+','+candidate+','+payrate+','+currentRateCurrency+','+currentRateType+'"  type="checkbox" name="associates[]" class="myCheckbox" >';
			        	/*result = result +'<td>';
			        	 result = result +'<div><span title="0025896">0025896</span></div>'; 
			        	result = result +'</td>';*/
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
			      
			        	result = result +'<td>';
			        	result = result +'<div title="'+primarySkill+'" class="tbldata-dotdot_80">'+primarySkill+'</div>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<div class="tbldata-dotdot-sm">';
			        	result = result +'<small class="pull-left">'+currentRateCurrency+'</small>';
			        	result = result +'<div class="tbldata-dotdot-100 pull-left">'+currentRate+'</div>';
			        	result = result +'<small class="pull-right">'+currentRateType+'</small>';
			        	result = result +'</div>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<div title="'+designationType+'" class="tbldata-dotdot_80">'+designationType+'</div>';
			        	result = result +'</td>';
			        	result = result +'<td>';
			        	result = result +'<div title="'+report.reportingManager+'" class="tbldata-dotdot_80">'+report.reportingManager+'</div>';
			        	result = result +'</td>';
			        	
			        	result = result +'<td>';
			        	result = result +candidateResume;
			        	result = result +'</td>'; 
			        
			        	
			        	result = result +'<td>';
			        	result = result +'<div title="Development" class="tblminwidth-50 projects_div">  ';                
			        	result = result +report.assignedProjectsCount;
			        	result = result +'</div>';
			        	result = result +'</td>';
			        	result = result +'</tr>';
	        	}
	        	
	        	        result = result +'</tbody>';
					    result = result +'</table>';
					    result = result +'</div>';
					    
					    result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="addRequestedResources();">Add</button>';
	                 	 result = result +'   </div>'; 
					
						$("#associatesListFilter").empty();
					    $("#associatesListFilter").append(result);
					    $("#associatesListLoader").removeAttr("style"); 
					    pagination();
					    
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



<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#requestAssociates_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		
        		  
        		var associatesRowsCount = document.getElementById("associatesRowsCount").value;
        		if(associatesRowsCount == 0){
        			alertMessage();
        			 return false;
        		}else{
        			
        			e.preventDefault();
                	
            		var frm = $('#requestAssociates_form').serializeFormJSON();
                	var con = JSON.stringify(frm);
    			    con = con.replace(/[{}]/g, ""); 
    			    var jsonData = encodeURIComponent(con);
             		var sourceleadUrl ='${sourceleadUrl}';
             		
             		$("#associateRequestLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
             		
             		/* $.ajax({
              	      type: "POST",
              	      contentType : 'application/json; charset=utf-8',
              	      dataType : 'json',
                    	  url: sourceleadUrl+"/companyRegistration?&${_csrf.parameterName}=${_csrf.token}&modulesList="+modulesList+"&submodulesList="+submodulesList+"&json="+jsonData,
                    			  
                    			  
                      	success: function(result) {
    			        	if((result == 'orgList')){
    			        		location.replace(sourceleadUrl+"/enrolledOrgsList");  
    			        	}
    			        	else if((result == 'Invalid Sub Modules')){
    			        		$("#loading").removeAttr("style");
    			        		document.getElementById("subModule").style.display="";
    			        	}
    			        	
    			        },  
                   }); */  
             		 return true;
                	   
        		}
        	
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#requestAssociates_form', function() {
     		$("#requestAssociates_form").showHelpOnFocus();
     		$("#requestAssociates_form").validateOnBlur(false, validationSettings);
     	});
     
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
		
		var oTable = $('#associatesListPagination').dataTable();
		var rowcollection =  oTable.$("input[name='associates[]']:checked", {"page": "all"});
		
		   var internalOutput = "";
		   rowcollection.each(function(index,elem){
			   
		   var checkbox_value = $(elem).val().split(",");
		   
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
			
			
			if(checkbox_value[2] != null && checkbox_value[2] != '' && checkbox_value[2] != 'undefined'){
				$('#'+maxRowNum+'payRateCurrency').html(checkbox_value[3]);
				$('#'+maxRowNum+'payRate').html(checkbox_value[2]);
				$('#'+maxRowNum+'payRateType').html(checkbox_value[4]);	
			}else{
				$('#'+maxRowNum+'payRate').html("---------");
			}
			
			$('#'+maxRowNum+'candidatePayRateCurrency').val(checkbox_value[3]);
			$('#'+maxRowNum+'candidatePayRate').val(checkbox_value[2]);
			$('#'+maxRowNum+'candidatePayRateType').val(checkbox_value[4]);
			
			var cell2 = row.insertCell(2);
			var divTaskTd3Str = document.getElementById("divTaskTd3").innerHTML;
			divTaskTd3Str = divTaskTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd3Str = divTaskTd3Str.replace(/PRICECALCULATION/g,("")); 
			cell2.innerHTML = divTaskTd3Str;
			
			
			
			var startDateText = "";
			var cell3 = row.insertCell(3);
			startDateText = startDateText+"<div  class='tbldata-dotdot-100'>";
			startDateText = startDateText+'<input type="text" class="form-control input-sm calendar" id="'+maxRowNum+'sdate-input" name="'+maxRowNum+'requestStartDate" >';
			startDateText = startDateText+'</div>';
			cell3.innerHTML = startDateText;
			
			
			var endDateText = "";
			var cell4 = row.insertCell(4);
			endDateText = endDateText+"<div  class='tbldata-dotdot-100'>";
			endDateText = endDateText+'<input type="text" class="form-control input-sm calendar" id="'+maxRowNum+'edate-input" name="'+maxRowNum+'requestEndDate" >';
			endDateText = endDateText+'</div>';
			cell4.innerHTML = endDateText;
			
			
			
			$( "#"+maxRowNum+"sdate-input" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : 'dd-mm-yy',
				  onSelect : function(selected) {
					  var dt = toDate("#"+maxRowNum+"sdate-input");
						dt.setDate(dt.getDate() + 1);
						$("#"+maxRowNum+"edate-input").datepicker("option", "minDate", dt);
			 	  }
			    });
			
			$( "#"+maxRowNum+"edate-input" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : 'dd-mm-yy',
				  onSelect : function(selected) {
					  var dt = toDate("#"+maxRowNum+"edate-input");
						dt.setDate(dt.getDate() - 1);
						$("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
			 	  }
			    });
			
			
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
			
			 $("#norecordsfound").empty();
			
			
		   });
		

 		
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
	function toggleAssociates(source) {
	    var checkboxes = document.querySelectorAll('input[name="associates[]"]');
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i] != source)
	            checkboxes[i].checked = source.checked;
	        
	    }
	$('input:checkbox:not(#checkAllAssociates)').click(function() {
	    if(!this.checked) {
	        $('#checkAllAssociates').removeAttr('checked');
	    }
	});
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
		
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
		}
	</script>
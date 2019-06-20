
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-b" class="newPrTabs_inn">
  <div id="choicepopUp"></div>
  <div id="associateRequestLoading"></div>
<form:form id="assignMembers_form" name="assignMembers_form" method="post">
	<div class="project_tabs_inn_head">
		<a href="javascript:void(0)" onclick="getAssociates();" id="addDiv" class="btn btn-primary btn-sm anchorWhite">
		<i class="glyphicon glyphicon-search"></i>Search Associates</a> 
		<div class="clearfix mb10"></div>
		<div id="resourceListLoading"></div>
		<div id="searchAssociatesList"></div>
		
	</div>
	
<div class="form-footer clearfix miniFoot">
  <div class="col-lg-12">
	<div class="pull-right">
	  <input type="button" value="Cancel"  onClick="javascript:window.location.href = '<c:url value='${appPath}/projectDashboard'/>'"  class="btn btn-danger">
	  <input type="button" value="Save & Continue" onClick="assignMembers()" class="btn btn-primary ml10">
	</div>
  </div>
</div>
</form:form>
</div> 
	
<div id="divTaskTd1" style="display: none; float: left;">
	<div class="width215px">
		<div id="REPLACEROWIDcandidateName" class="tbldata-dotdot pull-left">
		</div>
		
		<input type="hidden" class="form-control input-sm" id="REPLACEROWIDcandidateId" name="projectMembers[REPLACEROWID].candidateId" />
		<input type="hidden"  name="projectMembers[REPLACEROWID].validateRow" value="true">
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDprojectMemberId" name="projectMembers[REPLACEROWID].projectMemberId">
		
	</div>
</div>


<div id="divTaskTd2" style="display: none; float: left;">
	<div class="changeRate_div changeRateDiv">
                  <div class="pull-left dropdown-select drpdownCurrency">
                      <select id="REPLACEROWIDpayRateCurrency" name="projectMembers[REPLACEROWID].payRateCurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate positionRelative errorField-am">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDpayRate" name="projectMembers[REPLACEROWID].payRate" field-name="Pay Rate"  data-validation="required validate_Space validate_float validate_length length1-15">
                  </div>
                  
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDpayRateRateType" name="projectMembers[REPLACEROWID].payRateRateType">
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
                      <select id="REPLACEROWIDbillRateCurrency" name="projectMembers[REPLACEROWID].billRateCurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDbillRate" name="projectMembers[REPLACEROWID].billRate" field-name="Bill Rate" value="0" data-validation="required validate_Space validate_float validate_length length1-15">
                  </div>
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDbillRateRateType" name="projectMembers[REPLACEROWID].billRateRateType">
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
	<div class="tbldata-dotdot-100">
         <input class="form-control input-sm timeicon" type="text" id="REPLACEROWIDestHours" name="projectMembers[REPLACEROWID].estHours" data-validation-optional="true" onblur="calculateTotalEstimateHours();" data-validation="validate_Space validate_int" field-name="Est Hours">
	</div>
</div>

<div id="divTaskTd7" style="display: none; float: left;">
	<div class="changeRate_div estRate_div">
          
          <div class="pull-left mt10">
                 <div id="REPLACEROWIDestBudgetCurrencyType" name="projectMembers[REPLACEROWID].estBudgetCurrencyType" value='${SourceLeadWeb_currencyCode}'>${SourceLeadWeb_currencyCode}</div>
          </div> 
		  
         <div class="pull-left inputRate">
         	<input class="form-control input-sm" type="text" id="REPLACEROWIDestBudget" name="projectMembers[REPLACEROWID].estBudget" data-validation-optional="true" onblur="calculateTotalEstimateBudget();"  data-validation="validate_int validate_Space validate_length length1-15" field-name="Est Budget">
         </div>
        
	</div>
</div>
    
    <div id="divTaskTd9" style="display: none; float: left;">
	<div class="changeRate_div estRate_div">
      
         <div class="pull-left inputRate">
         	<input class="form-control input-sm" type="text" id="REPLACEROWIDpercentageOfWork" name="projectMembers[REPLACEROWID].percentageOfWork" data-validation-optional="true" data-validation="validate_Space validate_percentage_of_work" field-name="percentageOfWork">
         </div>
        
	</div>
</div>
                  
 <div id="divTaskTd8" style="display: none; float: left;">
       <a class="text-danger tblminwidth-50" title="Remove row"
		id="REPLACEROWIDremoveLineItem" onclick="removeRow(REPLACEROWID)"><i
		class="fa fa-remove"></i></a>
 </div> 
	
		
		
	

	


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
	result = result +'<span class="dropdown-toggle" id="menu4" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Team</span><i class="glyphicon glyphicon-play"></i></span>|';
	          
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<div id="orgLocationTeams"></div>';  
	result = result +'</ul>';
	result = result +'</div>';
	result = result +'</li>'; 
	        
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
	result = result +'<span class="dropdown-toggle" id="menu2" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play"></i></span>|';
	          
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu2">';
	result = result +'<ul class="resourcetype-filter">';
	result = result +'<li>';
	result = result +'<input value="hotlist" id="hotlist" name="resourceType"  type="checkbox" onclick="getAssociatesFilter()" >';
	result = result +'<label for="hotlist">Hotlist</label>';
	result = result +'</li>';
	result = result +'<li>';
	result = result +'<input  value="assigned" id="Assigned" name="resourceType"  type="checkbox" onclick="getAssociatesFilter()" >';
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
     getOrgLocationTeams();
 	
}
</script>


<script>
function getOrgLocationRoles(){
	var orgLocations = '${SourceLeadWeb_orgLocations.id}';
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

function getOrgLocationTeams(){
	var orgLocations = '${SourceLeadWeb_orgLocations.id}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getOrgLocationTeamList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,  
	        success: function(json) {
	        	
	        	var output = "";
	         	if(json.length > 0){
	        		for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + '<li><input value="'+opt.id+'" name="teams"  type="checkbox" onclick="getAssociatesFilter()"><label>'+opt.name+'</label></li>';
					}
	        	}else{
	        		output = output + '<li><label>&nbsp;&nbsp;&nbsp;<font color="red">No teams configured</font></label></li>';
	        	}
	        	$("#orgLocationTeams").empty();
		        $("#orgLocationTeams").append(output);
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
     	result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="this.disabled=true;addRequestedResources();$(this).blur();">Add</button>';
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
	var sourceleadUrl ='${sourceleadUrl}';
	var projectUUID =  document.getElementById("projectUUID").value;
	var roles = [];
	$('input:checkbox[name=roles]:checked').each(function() 
		    {
		roles.push($(this).val());
	    });
	
	var teams = [];
	$('input:checkbox[name=teams]:checked').each(function() 
		    {
		teams.push($(this).val());
	    });
	
	var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
	
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
		 var url = sourceleadUrl+"/getRequestedAssociatesList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&roles="+roles+"&teams="+teams+"&resourceType="+resourceType+"&selectedAssociates="+selectedAssociates+"&projectUUID="+projectUUID;
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
    		   candidatesDataTableList();
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


<script type="text/javascript">
	function addRequestedResources() {
		var projectStartDate = '${projectStartDate}';
		var projectEndDate = '${projectEndDate}';
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		
		if(projectStartDate == ""){
		  projectStartDate = $("#startDateStr").val();
		}
		
		if(projectEndDate == ""){
		  projectEndDate = $("#endDateStr").val();
		}
		
	  
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
			
			
			var cell2 = row.insertCell(2);
			var divTaskTd3Str = document.getElementById("divTaskTd3").innerHTML;
			divTaskTd3Str = divTaskTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd3Str = divTaskTd3Str.replace(/PRICECALCULATION/g,("")); 
			cell2.innerHTML = divTaskTd3Str;
			
			var startDateText = "";
			var cell3 = row.insertCell(3);
			startDateText = startDateText+"<div  class='tbldata-dotdot-100' id='"+maxRowNum+"startDate' >";
			startDateText = startDateText+'<input type="text" class="form-control input-sm calendar" onchange="getEstimatedHours('+maxRowNum+')"  data-validation="required" field-name="Start Date" placeholder="Start Date" field-name="Start Date" id="'+maxRowNum+'memberStartDate"  name="projectMembers['+maxRowNum+'].memberStartDate" readonly>';
			startDateText = startDateText+'</div>';
			cell3.innerHTML = startDateText;
			
			
			var endDateText = "";
			var cell4 = row.insertCell(4);
			endDateText = endDateText+"<div  class='tbldata-dotdot-100'>";
			endDateText = endDateText+'<input type="text" class="form-control input-sm calendar" onchange="getEstimatedHours('+maxRowNum+')"  id="'+maxRowNum+'memberEndDate" value="'+projectEndDate+'" name="projectMembers['+maxRowNum+'].memberEndDate" readonly>';
			endDateText = endDateText+'</div>';
			cell4.innerHTML = endDateText;
			
			
			  var finalDateFormatAssignEmpUpdate = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatAssignEmpUpdate = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatAssignEmpUpdate = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatAssignEmpUpdate = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatAssignEmpUpdate = 'yy-dd-mm'; 
			  }
			
			
			var from = projectStartDate.split("-");
	        var datepickerStartDate =  new Date(from[2], from[1] - 1, from[0]);
	        datepickerStartDate.setDate(datepickerStartDate.getDate() + 1);
						if(projectEndDate !=  null && projectEndDate != '' && projectEndDate != 'undefined'){
							$("#"+maxRowNum+"memberStartDate").datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:projectStartDate,
								maxDate:projectEndDate,
						        onClose : function(selected) {
						        	var dt = $(this).datepicker('getDate', '+1d');
									dt.setDate(dt.getDate() + 1);
									$("#"+maxRowNum+"memberEndDate").datepicker("option", "minDate", dt);
					                removeStartDateValidation(maxRowNum);
									//getEstimatedHours(maxRowNum)
								}
							});

							$("#"+maxRowNum+"memberEndDate").datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								maxDate:projectEndDate,
								minDate: datepickerStartDate,
						        onClose : function(selected) {
						        	 var dt = $(this).datepicker('getDate', '-1d');
										dt.setDate(dt.getDate() - 1);
										$("#"+maxRowNum+"memberStartDate").datepicker("option", "maxDate", dt);
					                //getEstimatedHours(maxRowNum)
								}
							});
							
						}else{
							$("#"+maxRowNum+"memberStartDate").datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:projectStartDate,
						        onClose : function(selected) {
						        	var dt = $(this).datepicker('getDate', '+1d');
									dt.setDate(dt.getDate() + 1);
									$("#"+maxRowNum+"memberEndDate").datepicker("option", "minDate", dt);
					                removeStartDateValidation(maxRowNum);
									//getEstimatedHours(maxRowNum)
								}
							});

							$("#"+maxRowNum+"memberEndDate").datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:datepickerStartDate,
						        onClose : function(selected) {
						        	 var dt = $(this).datepicker('getDate', '-1d');
										dt.setDate(dt.getDate() - 1);
										$("#"+maxRowNum+"memberStartDate").datepicker("option", "maxDate", dt);
									 
					                //getEstimatedHours(maxRowNum)
								}
							});
						}
						
			  if(projectStartDate != null && projectStartDate != 'undefined' && projectStartDate != '' 
					  && projectEndDate != null && projectEndDate != 'undefined' && projectEndDate != ''){
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
			var divTaskTd9Str = document.getElementById("divTaskTd9").innerHTML;
			divTaskTd9Str = divTaskTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd9Str = divTaskTd9Str.replace(/PRICECALCULATION/g,("")); 
			cell7.innerHTML = divTaskTd9Str;
			
			var cell8 = row.insertCell(8);
			var divTaskTd8Str = document.getElementById("divTaskTd8").innerHTML;
			divTaskTd8Str = divTaskTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd8Str = divTaskTd8Str.replace(/PRICECALCULATION/g,("")); 
			cell8.innerHTML = divTaskTd8Str;
			
			 $("#norecordsfound").empty();
			
			
		   }
		

 		
 		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
	}
  </script>
  
  
  
  <script>
  function loadProjectAssignedMembers(projectId){
	  var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getProjectAssignedMembesListByProjectId?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 
		        success: function(json) {
		        	if(json.length > 0){
		        		
		        		for(var i=0; i<json.length; i++){
		        			var opt = json[i];
		        			
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
		      				
		      				$('#'+maxRowNum+'candidateId').val(opt.candidateId);
		      				$('#'+maxRowNum+'candidateName').html(opt.candidateName);
		      				$('#'+maxRowNum+'projectMemberId').val(opt.id);
		      				
		      				var cell1 = row.insertCell(1);
		      				var divTaskTd2Str = document.getElementById("divTaskTd2").innerHTML;
		      				divTaskTd2Str = divTaskTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		      				divTaskTd2Str = divTaskTd2Str.replace(/PRICECALCULATION/g,("")); 
		      				cell1.innerHTML = divTaskTd2Str;
		      		
		      				if(opt.payRate != null && opt.payRate != '' && opt.payRate != 'undefined'){
		      					$('#'+maxRowNum+'payRateCurrency').val(opt.payRateCurrency);
		      					$('#'+maxRowNum+'payRate').val(opt.payRate);
		      					$('#'+maxRowNum+'payRateRateType').val(opt.payRateType);
		      				}
		      				
		      				var cell2 = row.insertCell(2);
		      				var divTaskTd3Str = document.getElementById("divTaskTd3").innerHTML;
		      				divTaskTd3Str = divTaskTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		      				divTaskTd3Str = divTaskTd3Str.replace(/PRICECALCULATION/g,("")); 
		      				cell2.innerHTML = divTaskTd3Str;
		      				
		      				if(opt.currentBillRate != null && opt.currentBillRate != '' && opt.currentBillRate != 'undefined'){
		      					$('#'+maxRowNum+'billRateCurrency').val(opt.billRateCurrency);
		      					$('#'+maxRowNum+'billRate').val(opt.currentBillRate);
		      					$('#'+maxRowNum+'billRateRateType').val(opt.rateType);
		      				}
		      				
		      				
		      				
		      				var startDateText = "";
		    				var cell3 = row.insertCell(3);
		    				startDateText = startDateText+"<div  class='tbldata-dotdot-100'>";
		    				startDateText = startDateText+'<input type="text" class="form-control input-sm calendar" onchange="getEstimatedHours('+maxRowNum+')"  id="'+maxRowNum+'memberStartDate" value="'+opt.startDateStr+'"  name="projectMembers['+maxRowNum+'].memberStartDate" readonly>';
		    				startDateText = startDateText+'</div>';
		    				cell3.innerHTML = startDateText;
		    				
		    				
		    				var endDateText = "";
		    				var cell4 = row.insertCell(4);
		    				endDateText = endDateText+"<div  class='tbldata-dotdot-100'>";
		    				endDateText = endDateText+'<input type="text" class="form-control input-sm calendar" onchange="getEstimatedHours('+maxRowNum+')"  id="'+maxRowNum+'memberEndDate" value="'+opt.endDateStr+'"  name="projectMembers['+maxRowNum+'].memberEndDate" readonly>';
		    				endDateText = endDateText+'</div>';
		    				cell4.innerHTML = endDateText;
		    				
		    				
		    				var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		    				  var finalDateFormatAssignEmpUpdate = '';
		    				  if(locationDate.match("yyyy-MM-dd")){
		    					  finalDateFormatAssignEmpUpdate = 'yy-mm-dd';
		    				  }if(locationDate.match("dd-MM-yyyy")){
		    					  finalDateFormatAssignEmpUpdate = 'dd-mm-yy'; 
		    				  }if(locationDate.match("MM-dd-yyyy")){
		    					  finalDateFormatAssignEmpUpdate = 'mm-dd-yy'; 
		    				  }if(locationDate.match("yyyy-dd-MM")){
		    					  finalDateFormatAssignEmpUpdate = 'yy-dd-mm'; 
		    				  }
		    				  
		    				var from = opt.startDateStr.split("-");
		    		        var datepickerStartDate =  new Date(from[2], from[1] - 1, from[0]);
		    		        datepickerStartDate.setDate(datepickerStartDate.getDate() + 1);
		    							if(opt.endDateStr !=  null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
		    								$("#"+maxRowNum+"memberStartDate").datepicker({
		    									dateFormat : finalDateFormatAssignEmpUpdate,
		    									changeMonth: true,
		    									changeYear: true, 
		    									minDate:opt.startDateStr,
		    									maxDate:opt.endDateStr,
		    							        onClose : function(selected) {
		    							        	var dt = $(this).datepicker('getDate', '+1d');
		    										dt.setDate(dt.getDate() + 1);
		    										$("#"+maxRowNum+"memberEndDate").datepicker("option", "minDate", dt);
		    						                removeStartDateValidation(maxRowNum);
		    										//getEstimatedHours(maxRowNum)
		    									}
		    								});

		    								$("#"+maxRowNum+"memberEndDate").datepicker({
		    									dateFormat : finalDateFormatAssignEmpUpdate,
		    									changeMonth: true,
		    									changeYear: true, 
		    									maxDate:opt.endDateStr,
		    									minDate: datepickerStartDate,
		    							        onClose : function(selected) {
		    							        	var dt = $(this).datepicker('getDate', '-1d');
		    										dt.setDate(dt.getDate() - 1);
		    										$("#"+maxRowNum+"memberStartDate").datepicker("option", "maxDate", dt);
		    										 
		    						                //getEstimatedHours(maxRowNum)
		    									}
		    								});
		    								
		    							}else{
		    								$("#"+maxRowNum+"memberStartDate").datepicker({
		    									dateFormat : finalDateFormatAssignEmpUpdate,
		    									changeMonth: true,
		    									changeYear: true, 
		    									minDate:opt.startDateStr,
		    									 
		    							        onClose : function(selected) {
		    							        	var dt = $(this).datepicker('getDate', '+1d');
		    										dt.setDate(dt.getDate() + 1);
		    										$("#"+maxRowNum+"memberEndDate").datepicker("option", "minDate", dt);
		    										 
		    						                removeStartDateValidation(maxRowNum);
		    										//getEstimatedHours(maxRowNum)
		    									}
		    								});

		    								$("#"+maxRowNum+"memberEndDate").datepicker({
		    									dateFormat : finalDateFormatAssignEmpUpdate,
		    									changeMonth: true,
		    									changeYear: true, 
		    									minDate:datepickerStartDate,
		    							        onClose : function(selected) {
		    							        	var dt = $(this).datepicker('getDate', '-1d');
		    										dt.setDate(dt.getDate() - 1);
		    										$("#"+maxRowNum+"memberStartDate").datepicker("option", "maxDate", dt);
		    						                //getEstimatedHours(maxRowNum)
		    									}
		    								});
		    							}
		    							
		    				

		    				var cell5 = row.insertCell(5);
		    				var divTaskTd6Str = document.getElementById("divTaskTd6").innerHTML;
		    				divTaskTd6Str = divTaskTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
		    				divTaskTd6Str = divTaskTd6Str.replace(/PRICECALCULATION/g,("")); 
		    				cell5.innerHTML = divTaskTd6Str;
		    				
		    				if(opt.estimatedHours != null && opt.estimatedHours != '' && opt.estimatedHours != 'undefined'){
		      					$('#'+maxRowNum+'estHours').val(opt.estimatedHours);
		    				}
		    				
		    				var cell6 = row.insertCell(6);
		    				var divTaskTd7Str = document.getElementById("divTaskTd7").innerHTML;
		    				divTaskTd7Str = divTaskTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
		    				divTaskTd7Str = divTaskTd7Str.replace(/PRICECALCULATION/g,("")); 
		    				cell6.innerHTML = divTaskTd7Str;
		    				
		    				if(opt.estimatedBudget != null && opt.estimatedBudget != '' && opt.estimatedBudget != 'undefined'){
		      					$('#'+maxRowNum+'estBudget').val(opt.estimatedBudget);
		    				}
		    				
		    				var cell7 = row.insertCell(7);
		    				var divTaskTd9Str = document.getElementById("divTaskTd9").innerHTML;
		    				divTaskTd9Str = divTaskTd9Str.replace(/REPLACEROWID/g, (maxRowNum));
		    				divTaskTd9Str = divTaskTd9Str.replace(/PRICECALCULATION/g,("")); 
		    				cell7.innerHTML = divTaskTd9Str;
		    				
		    				if(opt.percentageOfWork != null && opt.percentageOfWork != '' && opt.percentageOfWork != 'undefined'){
		      					$('#'+maxRowNum+'percentageOfWork').val(opt.percentageOfWork);
		    				}
		    				
		    				var cell8 = row.insertCell(8);
		    				var divTaskTd8Str = document.getElementById("divTaskTd8").innerHTML;
		    				divTaskTd8Str = divTaskTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
		    				divTaskTd8Str = divTaskTd8Str.replace(/PRICECALCULATION/g,("")); 
		    				cell8.innerHTML = divTaskTd8Str;
		    				
		    				
		        		}
		        		
		        	}else{
		        		emptyRecordsList();
		        	}
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
function removeStartDateValidation(rowId){
	$('#'+rowId+'startDate').find('p.jquery_form_error_message').remove();
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
	var startDate = document.getElementById(rowId+"memberStartDate").value;
	var endDate = document.getElementById(rowId+"memberEndDate").value;
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
		        	 $("#resourceListLoading").removeAttr("style"); 
		        	
		        }
		        
		 });
	}
}
</script>

<script>
function getAssignedMembers(){
	
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var projectId = document.getElementById("projectId").value;
	
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
	 result = result +'<table id="projectAssignedMembers">';
	 result = result +'<thead id="addRequestTbl">';
	 result = result +'<tr id="divTsRow0">';
	 result = result +'<th>Name</th>';
	 result = result +'<th>Pay Rate</th>';
	 result = result +'<th>Bill Rate</th>';
	 result = result +'<th>Start Date <br>(<small class="custom-small">'+reqAssociateDate+'</small>)</th>';
	 result = result +'<th>End Date <br>(<small class="custom-small">'+reqAssociateDate+'</small>)</th>';
	 result = result +'<th>Est Hours</th>';
	 result = result +'<th>Est Budget</th>';
	 result = result +'<th>% Of Work</th>';
	 result = result +'<th>Action</th>';
	 result = result +'</tr>';
	 result = result +'</thead>';
	 result = result +'</table>';  
	 result = result +'</div>';   
	 
	 result = result +'<div id="norecordsfound"></div>'; 
	 
	 $("#searchAssociatesList").empty();
     $("#searchAssociatesList").append(result);
     
     if(projectId != null && projectId != '' && projectId != 'undefined'){
    	 loadProjectAssignedMembers(projectId);
     }
     
     
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

<script>
function assignMembers(){
	
	 if ($('#assignMembers_form').validate(false, validationSettings)){ 
		 
 	/* 	var associatesRowsCount = document.getElementById("associatesRowsCount").value;
 		if(associatesRowsCount == 0){
 			 alertMessage();
 			 return false;
 		}else{
 			 */
 			
 			var checkMaxTsRowNum = $("#maxTsRowNum").val();
 	      	
 	        var formData = getFormData("assignMembers_form");
 	        
      		var sourceleadUrl ='${sourceleadUrl}';
      		var projectUUID =  document.getElementById("projectUUID").value;
      		var userId = '${SourceLeadWeb_user.id}';
      		var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
      		
       		$("#associateRequestLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      		$.ajax({
					type : "POST",
	  	    	    data : formData,
					url : sourceleadUrl+"/assignProjectMembersToProject?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&userId="+userId+"&orgLocationId="+orgLocationId, 
					success : function(result) {
						if (result && result.response === "INVALID_DATA") {
								$("#associateRequestLoading").removeAttr("style");
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
							 $("#associateRequestLoading").removeAttr("style");
							 var additionalDetails = document.getElementById("additionalDetails");
							 document.getElementById("additionalDetails").removeAttribute("class")
							 additionalDetails.href="#tab-c"; 
							 $("#additionalDetails").click();
			                }else if (result.indexOf('Sign In') !== -1) {
  								location.replace(sourceleadUrl);
  							} else {
  			                    showError("Operation failed, please try again");
  			                }
					},
				}); 
      		 return true;
         	   
 		/* } */
	 }else{
			return false;
	  }
	 
	  $('body').on('blur', '#assignMembers_form', function() {
   		$("#assignMembers_form").showHelpOnFocus();
   		$("#assignMembers_form").validateOnBlur(false, validationSettings);
   	});
}
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
function alertMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Add atleast one project member</p>";
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
function getTeamMembers(projectId,startDateStr,endDateStr) {
	if(projectId == null || projectId == '' || projectId == 'undefined'){
		projectId  = document.getElementById("projectId").value;
		startDateStr  = document.getElementById("startDateStr").value;
	}
	var sourceleadUrl ='${sourceleadUrl}';
	var startDateStr = startDateStr;
	if(endDateStr == null ||  endDateStr == "" || endDateStr == "undefined"){
		endDateStr = "";
	}
	var searchByName = $("#searchByName").val();
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  teamMembersDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  teamMembersDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  teamMembersDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  teamMembersDate = 'YYYY-DD-MM'; 
	  }  
	
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTeamMembersList?${_csrf.parameterName}=${_csrf.token}&ProjectId="+projectId+"&searchByName="+searchByName, 
        success: function(data) {
        	var json = JSON.parse(data);
        	var output = "";
        	output = output+' <div class="wrap_employeeTable">';
        	output = output+' <table cols="0" id="assignEmployeePagination" class="table table-bordered table-striped  clearfix">';
            output = output+' <thead>';
            output = output+' <tr>';
            output = output+' <th><input type="checkbox" name="emp" id="empChekId" onclick="toggleCheck();"></th>';
            output = output+' <th>Name</th>';
            output = output+' <th>Email</th>';
            output = output+' <th>Role</th>';
            output = output+' <th>Start Date <br>(<small class="custom-small">'+teamMembersDate+'</small>)</th>';
            output = output+' <th>End Date <br>(<small class="custom-small">'+teamMembersDate+'</small>)</th>';
            output = output+' </tr>';
            output = output+' </thead>';
            output = output+' <tbody>';
        	for (var i=0; i<json.length; i++)
			{
     	       // var projectEndDate = $('#EndDate').val();
	        		var opt = json[i];
	        		var role = '';
	        		if(opt.userType != null && opt.userType != '' && opt.userType != 'undefined'){
	        			role = opt.userType; 
	        		}else{
	        			role = 'Employee';
	        		}
	        		output = output + "<tr>";
	        		//output = output + '<td><input type="checkbox"  class="submissionsChecks" name="submissionList[]" ></td>';
	        		output = output + "<td><input type='hidden' id='projectStartDateStr'  value="+startDateStr+"><input type='hidden' id='projectEndDateStr'  value="+endDateStr+"><input class='myCheckbox' id="+opt.id+" type='checkbox' name='team"+i+"' value="+opt.id+"></td>";
	        		if(opt.user.lastName != null && opt.user.lastName !='Undefined' && opt.user.lastName != ''){
						output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"&nbsp;"+opt.user.lastName+"'>" + opt.user.firstName+ "&nbsp;" + opt.user.lastName+ "</span></td>";	
					}else{
						output = output + "<td><span class='tbldata-dotdot-sm' title='"+opt.user.firstName+"'>" + opt.user.firstName+ "</span></td>";
					}
	        		output = output +"<td><span class='tbldata-dotdot-vsm' title='"+opt.user.email+"'>"+opt.user.email+"</span></td>";
	        		output = output +"<td><span class='tbldata-dotdot-vsm' title='"+role+"'>"+role+"</span></td>";
	        		output = output +"<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	        		output = output +"<input  id='startDate"+ opt.id+ "'   type='text' name='Date' value='"+startDateStr+"' class='form-control clsDatePicker' readonly='readonly'>";
	        		output = output + "</div></td>";
	        		output = output +"<td><div class='input-group'> <span class='input-group-addon'><i class='fa fa-calendar'></i></span>";
	        		output = output +"<input id='endDate" + opt.id + "' value='"+endDateStr+"'  type='text' name='Date' value='' class='form-control clsDatePicker' readonly='readonly'>";
	        		output = output + "</div></td>";
	        		output = output + "</tr>";
			}
        	output = output+' </tbody>';
	        output = output+' </table>';
	        output = output+' </div>';
        	$("#employeesList").empty();
	        $("#employeesList").append(output);
	        
	        assignEmployeespagination();
	        
	        //StartDate And EndDate Pickers
	     	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  	  var finalDateFormatAssignEmpUpdate = '';
		  	  if(locationDate.match("yyyy-MM-dd")){
		  		  finalDateFormatAssignEmpUpdate = 'yy-mm-dd';
		  	  }if(locationDate.match("dd-MM-yyyy")){
		  		finalDateFormatAssignEmpUpdate = 'dd-mm-yy'; 
		  	  }if(locationDate.match("MM-dd-yyyy")){
		  		finalDateFormatAssignEmpUpdate = 'mm-dd-yy'; 
		  	  }if(locationDate.match("yyyy-dd-MM")){
		  		finalDateFormatAssignEmpUpdate = 'yy-dd-mm'; 
		  	  }
	        
	        var from = startDateStr.split("-");
	        var datepickerStartDate =  new Date(from[2], from[1] - 1, from[0]);
	        datepickerStartDate.setDate(datepickerStartDate.getDate() + 1);
		       if(json.length > 0){
			    	for (var i = 0; i <json.length; i++) {
						var opt = json[i];
						if(endDateStr !=  null && endDateStr != '' && endDateStr != 'undefined'){
							$('#startDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:startDateStr,
								maxDate:endDateStr,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('startDate')[1];
									//var dt = toDate('#startDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '+1d');
									dt.setDate(dt.getDate() + 1);
									$("#endDate"+actualId).datepicker("option", "minDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
								}
							});

							$('#endDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								maxDate:endDateStr,
								minDate: datepickerStartDate,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('endDate')[1];
								//	var dt = toDate('#endDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '-1d');
									dt.setDate(dt.getDate() - 1);
									$("#startDate"+actualId).datepicker("option", "maxDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
								}
							});
							
						}else{
							$('#startDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:startDateStr,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('startDate')[1];
									//var dt = toDate('#startDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '+1d');
									dt.setDate(dt.getDate() + 1);
									$("#endDate"+actualId).datepicker("option", "minDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
									
								}
							});

							$('#endDate'+opt.id).datepicker({
								dateFormat : finalDateFormatAssignEmpUpdate,
								changeMonth: true,
								changeYear: true, 
								minDate:datepickerStartDate,
								showOn: 'focus',
						        showButtonPanel: true,
						        closeText: 'Clear',
						        onClose : function(selected) {
									var actualId = $(this).attr('id').split('endDate')[1];
									//var dt = toDate('#endDate'+actualId);
									var id = $(this).attr('id');
									  var dt = $(this).datepicker('getDate', '-1d');
									dt.setDate(dt.getDate() - 1);
									$("#startDate"+actualId).datepicker("option", "maxDate", dt);
									var event = arguments.callee.caller.caller.arguments[0];
					                // If "Clear" gets clicked, then really clear it
					                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
					                    $(this).val('');
					                }
								}
							});
						}
						
						
					}
		      }
        },
        error: function(e){
        }
    });
	$('#firstName').val('');
	$('#lastName').val('');
	$('#employeeEmail').val('');
</script>

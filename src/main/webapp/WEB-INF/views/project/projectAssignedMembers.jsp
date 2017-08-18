
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-b" class="newPrTabs_inn">
  <div id="choicepopUp"></div>
  <div id="associateRequestLoading"></div>
<form:form id="assignMembers_form" name="assignMembers_form" method="post">
	<div class="project_tabs_inn_head">
		<a href="javascript:void(0)" onclick="getAssociates();" id="addDiv">
		<i class="glyphicon glyphicon-plus"></i>Assign Members</a> 
		
		<div id="resourceListLoading"></div>
		<div id="searchAssociatesList"></div>
		
	</div>
	
<div class="form-footer clearfix miniFoot">
  <div class="col-lg-12">
	<div class="pull-right">
	  <input type="button" value="Cancel"  onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'"  class="btn btn-danger">
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
		
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidateFullName" name="REPLACEROWIDcandidateFullName" />
		
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidateId" name="REPLACEROWIDcandidateId" />
		
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDresourceRequestId" name="REPLACEROWIDresourceRequestId">
		</input>
		
	</div>
</div>


<div id="divTaskTd2" style="display: none; float: left;">
	<div class="changeRate_div changeRateDiv">
                  <div class="pull-left dropdown-select drpdownCurrency">
                      <select id="REPLACEROWIDpayRateCurrency" name="REPLACEROWIDpayRateCurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDpayRate" name="REPLACEROWIDpayRate" field-name="Pay Rate" data-validation="required validate_Space validate_int validate_length length1-15">
                  </div>
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDpayRateRateType" name="REPLACEROWIDpayRateRateType">
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
                      <select id="REPLACEROWIDbillRateCurrency" name="REPLACEROWIDbillRateCurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDbillRate" name="REPLACEROWIDbillRate" data-validation-optional="true"  data-validation="validate_Space validate_int validate_length length1-15">
                  </div>
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDbillRateRateType" name="REPLACEROWIDbillRateRateType">
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
         <input class="form-control input-sm timeicon" type="text" id="REPLACEROWIDestHours" name="REPLACEROWIDestHours" data-validation-optional="true" onblur="calculateTotalEstimateHours();" data-validation="validate_Space validate_int" field-name="Est Hours">
	</div>
</div>

<div id="divTaskTd7" style="display: none; float: left;">
	<div class="changeRate_div estRate_div">
          
          <div class="pull-left mt10">
                 <div id="REPLACEROWIDestBudgetCurrencyType" name="REPLACEROWIDestBudgetCurrencyType" value='${SourceLeadWeb_currencyCode}'>${SourceLeadWeb_currencyCode}</div>
          </div> 
		  
         <div class="pull-left inputRate">
         	<input class="form-control input-sm" type="text" id="REPLACEROWIDestBudget" name="REPLACEROWIDestBudget" data-validation-optional="true" onblur="calculateTotalEstimateBudget();"  data-validation="validate_int validate_Space validate_length length1-15" field-name="Est Budget">
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
</script>

<script>
function getAssociatesFilter(){
	
	var projectUUID =  document.getElementById("projectUUID").value;
	var roles = [];
	$('input:checkbox[name=roles]:checked').each(function() 
		    {
		roles.push($(this).val());
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
	  
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#associatesListLoader").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getRequestedAssociatesList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&roles="+roles+"&resourceType="+resourceType+"&selectedAssociates="+selectedAssociates+"&projectUUID="+projectUUID, 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	
	        	var result = '';
	        	result = result +'<div class="require_wrap_table datatable_filter overflow_inherint">';
	        	result = result +'<table id="associatesListPagination" cellspacing="0" width="100%">';
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result +'<th><input type="checkbox" id="checkAllAssociates" onclick="toggleAssociates();"></th>';
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
	
			
			$('#'+maxRowNum+'payRateCurrency').val(checkbox_value[3]);
			$('#'+maxRowNum+'payRate').val(checkbox_value[2]);
			$('#'+maxRowNum+'payRateRateType').val(checkbox_value[4]);
			
			var cell2 = row.insertCell(2);
			var divTaskTd3Str = document.getElementById("divTaskTd3").innerHTML;
			divTaskTd3Str = divTaskTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd3Str = divTaskTd3Str.replace(/PRICECALCULATION/g,("")); 
			cell2.innerHTML = divTaskTd3Str;
			
			var startDateText = "";
			var cell3 = row.insertCell(3);
			startDateText = startDateText+"<div  class='tbldata-dotdot-100' id='"+maxRowNum+"startDate' >";
			startDateText = startDateText+'<input type="text" class="form-control input-sm calendar" data-validation="required" field-name="Start Date" id="'+maxRowNum+'sdate-input"  name="'+maxRowNum+'requestStartDate" >';
			startDateText = startDateText+'</div>';
			cell3.innerHTML = startDateText;
			
			
			var endDateText = "";
			var cell4 = row.insertCell(4);
			endDateText = endDateText+"<div  class='tbldata-dotdot-100'>";
			endDateText = endDateText+'<input type="text" class="form-control input-sm calendar" id="'+maxRowNum+'edate-input"  name="'+maxRowNum+'requestEndDate" >';
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
			
			$( "#"+maxRowNum+"sdate-input" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : finalDateFormatResourceReqSave,
			      minDate : 0,
				  onSelect : function(selected) {
					  var dt = $(this).datepicker('getDate', '+1d');
						dt.setDate(dt.getDate() + 1);
						$("#"+maxRowNum+"edate-input").datepicker("option", "minDate", dt);
						removeStartDateValidation(maxRowNum);
						getEstimatedHours(maxRowNum)
			 	  }
			    });
			
			$( "#"+maxRowNum+"edate-input" ).datepicker({
				  changeMonth: true,
			      changeYear: true,
			      dateFormat : finalDateFormatResourceReqSave,
			      minDate : 0,
				  onSelect : function(selected) {
					   var dt = $(this).datepicker('getDate', '-1d');
						dt.setDate(dt.getDate() - 1);
						$("#"+maxRowNum+"sdate-input").datepicker("option", "maxDate", dt);
						getEstimatedHours(maxRowNum)
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
			
			
			
			 $("#norecordsfound").empty();
			
			
		   });
		

 		
 		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
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
	var startDate = document.getElementById(rowId+"sdate-input").value;
	var endDate = document.getElementById(rowId+"edate-input").value;
	var locationId = '${SourceLeadWeb_orgLocationId}';
	if(startDate && endDate){
		 var sourceleadUrl ='${sourceleadUrl}';
		 var averageWorkingHours = '${SourceLeadWeb_orgLocations.averageWorkingHours}';
		 $("#resourceListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
	 result = result +'<th>Est Hours</th>';
	 result = result +'<th>Est Budget</th>';
	 result = result +'<th>Action</th>';
	 result = result +'</tr>';
	 result = result +'</thead>';
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

<script>
function assignMembers(){
	
	 if ($('#assignMembers_form').validate(false, validationSettings)){ 
		 
 		var associatesRowsCount = document.getElementById("associatesRowsCount").value;
 		if(associatesRowsCount == 0){
 			 alertMessage();
 			 return false;
 		}else{
 			
 			var frm = $('#assignMembers_form').serializeFormJSON();
 			var jsonData = JSON.stringify(frm);
 		
 			
 			var form = $('#assignMembers_form')[0];
 		    var formData = new FormData(form);
 		  	formData.append("json",jsonData);
 		  	
      		var sourceleadUrl ='${sourceleadUrl}';
      		var projectUUID =  document.getElementById("projectUUID").value;
      		var userId = '${SourceLeadWeb_user.id}';
      		var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
      		
      		
      /* 		$("#associateRequestLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      		$.ajax({
					type : "POST",
	  	    	    data: formData,
	  	    	    contentType : 'application/json; charset=utf-8',
	  		        processData: false,
	  		        contentType:false, 
	  	    	    dataType : 'json',
					url : sourceleadUrl+"/saveResourceRequest?${_csrf.parameterName}=${_csrf.token}&projectUUID="+projectUUID+"&saveType="+saveType+"&userId="+userId+"&orgLocationId="+orgLocationId, 
					success : function(result) {
						location.replace(sourceleadUrl+"/projectSummary?projectUUID="+projectUUID+"&tabType=resources");
					},
				}); */  
      		 return true;
         	   
 		}
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
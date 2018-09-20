<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript">
		window.onload = function () {
			getAssociatesList();
		 };
</script>
	
<section class="content-wraper">
       <section class="content-header clearfix">
         <div class="pull-left">
           <h2>Add Associate</h2>
         </div>
         <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/usersList'/>">Associates List</a> &raquo; Add Associate </div>
       </section>
      
  <div class="content-area clearfix">
        <div id="addLocAssociatesLoading"></div> 
      <div id="choicepopUp"></div>
	<form:form id="addAssociate_form" name="addAssociate_form" method="post">
		<div class="project_tabs_inn_head">
			<a href="javascript:void(0)" onclick="getAssociates();" id="addDiv" class="btn btn-primary btn-sm anchorWhite">
			<i class="glyphicon glyphicon-search"></i>Search Associates</a> 
			<div class="clearfix mb10"></div>
			<div id="resourceListLoading"></div>
			<div id="searchAssociatesList"></div>
		</div>
		
	<div class="form-footer clearfix row">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Save" id="create_user" onclick="createAssociate();" class="btn btn-primary">
                  <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/usersList'/>'"  class="btn btn-default ml">
                </div>
              </div>
    </div>
	
	</form:form>
  </div>
</section>


<div class="side_mask">
    <div class="proj_close"></div>
</div> 
<div id="associatesList"></div>


<div id="divTaskTd1" style="display: none; float: left;">
	<div class="width215px">
		<div id="REPLACEROWIDcandidateName" class="tbldata-dotdot pull-left">
		</div>
		
		<input type="hidden" class="form-control input-sm"
			id="REPLACEROWIDcandidateId" name="locationAssociates[REPLACEROWID].candidateId" />
	</div>
</div>



<div id="divTaskTd2" style="display: none; float: left;">
	<div class="changeRate_div changeRateDiv">
                  <div class="pull-left dropdown-select drpdownCurrency">
                      <select id="REPLACEROWIDpayRateCurrency" name="locationAssociates[REPLACEROWID].payRateCurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate positionRelative errorField-am">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDpayRate" name="locationAssociates[REPLACEROWID].payRate" field-name="Pay Rate" data-validation-optional="true" data-validation="validate_Space validate_float validate_length length1-15">
                  </div>
                  
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDpayRateRateType" name="locationAssociates[REPLACEROWID].payRateRateType">
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
                      <select id="REPLACEROWIDbillRateCurrency" name="locationAssociates[REPLACEROWID].billRateCurrency">
	                     <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
		                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
		                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
		                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
                     </select>
                  </div>
                  <div class="pull-left inputRate">
                      <input class="form-control input-sm" type="text" id="REPLACEROWIDbillRate" name="locationAssociates[REPLACEROWID].billRate" data-validation-optional="true"  data-validation="validate_Space validate_float validate_length length1-15">
                  </div>
                  <div class="pull-left drpdownMonthly dropdown-select">
                  <select id="REPLACEROWIDbillRateRateType" name="locationAssociates[REPLACEROWID].billRateRateType">
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


<div id="divTaskTd4" style="display: none; float: left;">
	<div class="width150px">
		<div class="dropdown-select">
                  <select id="REPLACEROWIDassociateRole" name="locationAssociates[REPLACEROWID].associateRole" onchange="getReportingManager(REPLACEROWID)" data-validation="required" field-name="Role">
		                 <option value='' selected>-Select Role-</option>
			            <c:forEach var="option" items="${rolesList}">
							<option value="${option.id}">${option.displayName}</option>
						</c:forEach>
                 </select>
         </div>
	</div>
</div>


<div id="divTaskTd5" style="display: none; float: left;">
	<div class="width150px">
		<div class="dropdown-select">
                  <select id="REPLACEROWIDassociateManager" name="locationAssociates[REPLACEROWID].associateManager" data-validation="required" field-name="Manager">
		                 <option value='' selected>-Select Manager-</option>
                 </select>
         </div>
	</div>
</div>

<div id="divTaskTd6" style="display: none; float: left;">
       <a class="text-danger tblminwidth-50" title="Remove row"
		id="REPLACEROWIDremoveLineItem" onclick="removeRow(REPLACEROWID)"><i
		class="fa fa-remove"></i></a>
 </div> 



 <jsp:include page="../masterFooter.jsp" />
 
 
 
 <script type="text/javascript">
 function getReportingManager(rowId) {
	 var role = $("#"+rowId+"associateRole").val();
	 if(role != null && role != "" && role != 'undefined'){
		 $("#addLocAssociatesLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAssociateReportingManager?"+tokenParamName+"="+tokenValue+"&role="+role, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Manager --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.lastName !=null && opt.lastName !='undefined' && opt.lastName !=''){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"&nbsp;("+opt.userType+")</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+"&nbsp;("+opt.userType+")</option>";
			        		}
			        		
					}
		        	$("#"+rowId+"associateManager").empty();
		        	$("#"+rowId+"associateManager").append(output);
		        	$("#"+rowId+"associateManager").trigger("chosen:updated");
		        },
		    });
		 
		 $("#addLocAssociatesLoading").removeAttr("style"); 
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Manager --</option>"; 
     	$("#"+rowId+"associateManager").empty();
     	$("#"+rowId+"associateManager").append(output);
     	$("#"+rowId+"associateManager").trigger("chosen:updated");
	 } 
	
 }
</script>
 
 <script>
function getAssociatesList(){
	
	
	 var result = '';
	 
	 result = result +'<div class="require_wrap_table">';
	 result = result +'  <input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />';
	 result = result +'  <input type="hidden" id="associatesRowsCount" name="associatesRowsCount" value="0" />';
	 result = result +'<table id="projectAssignedMembers">';
	 result = result +'<thead id="addRequestTbl">';
	 result = result +'<tr id="divTsRow0">';
	 result = result +'<th>Name</th>';
	 result = result +'<th>Pay Rate</th>';
	 result = result +'<th>Bill Rate</th>';
	 result = result +'<th>Role</th>';
	 result = result +'<th>Manager</th>';
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
function getAssociates(){
	var result = '';
	
	result = result +'<div class="clients_side_wrap">';
	result = result +'<div id="associatesListLoader"></div>';
	result = result +'<div class="new_side_wrap_inn">';
	result = result +'<div class="timesheet_head">';
	      
	result = result +'<ul class="pull-right">';
	
	result = result +'<li class="dropdown pull-left">';
	result = result +'<span class="dropdown-toggle" id="menu1" data-toggle="dropdown">';
	result = result +'<i class="fa fa-filter"></i><span>Location</span><i class="glyphicon glyphicon-play"></i></span>|';
	result = result +'<div class="dropdown-menu bootstrap_dropdownMenu bootstrap_dropdownMenu-right" role="menu" aria-labelledby="menu1">';
	result = result +'<ul class="location-filter">';
	result = result +'<c:forEach var="locations" items="${orgLocationsList}">';
	result = result +'<li>';
	result = result +'<input class="ml10" value="${locations.id}" onclick="getOrgLocationRoles();"  checked  name="orgLocations"  type="radio">';
	result = result +'<label class="radioAlign"><img src="/resources/images/${locations.country}.gif">${locations.orgLocationName}</label>';
	result = result +'</li>';
	result = result +'</c:forEach>';
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
    	result = result +'<th>Email</th>';
    	result = result +'<th>Role</th>';
    	result = result +'<th>Pay Rate</th>';
    	result = result +'<th>Manager</th>';
    	result = result +'<th>Resume</th>'; 
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
	
	var roles = [];
	$('input:checkbox[name=roles]:checked').each(function() 
		    {
		roles.push($(this).val());
	    });
	
	var orgLocations = [];
	$('input:radio[name=orgLocations]:checked').each(function() 
		    {
		orgLocations.push($(this).val());
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
		 var url = sourceleadUrl+"/getOrgLocationAssociatesList?searchValue="+searchValue+"&orgLocationId="+orgLocations+"&roles="+roles+"&selectedAssociates="+selectedAssociates;
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
            { "data": "email"},
            { "data": "role"},
            { "data": "rate"},
            { "data": "manager"},
            { "data": "resume"}
           
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
        			
        		 candidate = report.candidateName;
 	        		
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
	    	  		
	    	  			    	  		
	    	  		var result = "";
	    	  		result = result +'<td>';
		        	result = result +'<div title="'+report.candidateName+'">';
		        	
		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+report.candidateName+'</p>';

		        	result = result +'</div>';
		        	result = result +'</td>';
		            return result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "email",
	    	  	"render": function(data, type, report, meta) {
	    	  		
	    	  			    	  		
	    	  		var result = "";
	    	  		result = result +'<td>';
		        	result = result +'<div title="'+report.email+'">';
		        	
		        	result = result + '<p class="tbldata-dotdot-me pull-left">'+report.email+'</p>';

		        	result = result +'</div>';
		        	result = result +'</td>';
		            return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "role",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  var primarySkill = "";
            	  
            	    result = result +'<td>';
		        	result = result +'<div title="'+report.userRole+'" class="tbldata-dotdot_80">'+report.userRole+'</div>';
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
	function addRequestedResources() {
			   
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
			
			var cell3 = row.insertCell(3);
			var divTaskTd4Str = document.getElementById("divTaskTd4").innerHTML;
			divTaskTd4Str = divTaskTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd4Str = divTaskTd4Str.replace(/PRICECALCULATION/g,("")); 
			cell3.innerHTML = divTaskTd4Str;
			
			var cell4 = row.insertCell(4);
			var divTaskTd5Str = document.getElementById("divTaskTd5").innerHTML;
			divTaskTd5Str = divTaskTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd5Str = divTaskTd5Str.replace(/PRICECALCULATION/g,("")); 
			cell4.innerHTML = divTaskTd5Str;
			
			var cell5 = row.insertCell(5);
			var divTaskTd6Str = document.getElementById("divTaskTd6").innerHTML;
			divTaskTd6Str = divTaskTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divTaskTd6Str = divTaskTd6Str.replace(/PRICECALCULATION/g,("")); 
			cell5.innerHTML = divTaskTd6Str;
			 
			$("#norecordsfound").empty();
			
			
		   }
		

 		
 		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
        $('.addcandidate-popup').fadeOut(200);
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
function createAssociate(){
	
	 if ($('#addAssociate_form').validate(false, validationSettings)){ 
		 
 		var associatesRowsCount = document.getElementById("associatesRowsCount").value;
 		if(associatesRowsCount == 0){
 			 alertMessage();
 			 return false;
 		}else{
 			
 		    var formData = getFormData("addAssociate_form");
      		var sourceleadUrl ='${sourceleadUrl}';
      		
       		$("#addLocAssociatesLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      		$.ajax({
					type : "POST",
	  	    	    data : formData,
					url : sourceleadUrl+"/saveLocationAssociate?${_csrf.parameterName}=${_csrf.token}", 
					success : function(result) {
						location.replace(sourceleadUrl+"/usersList");
					},
				}); 
      		 return true;
         	   
 		}
	 }else{
			return false;
	  }
	 
	  $('body').on('blur', '#addAssociate_form', function() {
   		$("#addAssociate_form").showHelpOnFocus();
   		$("#addAssociate_form").validateOnBlur(false, validationSettings);
   	});
}
</script>


<script type="text/javascript">
function alertMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Add atleast one associate</p>";
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
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
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
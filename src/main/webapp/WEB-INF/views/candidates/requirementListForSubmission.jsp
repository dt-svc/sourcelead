<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/resources/css/style.css" />
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<script>
$(document).ready(function() {
	getcites('apiLocation','city','recommendation');
});
</script>


		<div class="cand-advsrch-popup advancedsearch-popup" id="requirementFilterPopup">
				<h5 class="headerAdsrch">Advanced Search
					<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
						<div class="clearfix">
							<div class="col-md-4">
								<label>Generated ID</label>
								<div class="clearfix">
									<select class="chosen-select form-control input-sm" multiple
										data-placeholder="Generated ID" id="generatedId" name="generatedId">
									</select>
								</div>
							</div>
							
							<div class="col-md-4">
								<label>Title</label>
								<div class="clearfix">
									<select class="chosen-select form-control input-sm" multiple
										data-placeholder="Job Title" id="requirementTitle"
										name="requirementTitle">
									</select>
								</div>
							</div>
							
							<div class="col-md-4">
							<label>Notice Period</label>
							<div class="clearfix">
								<select multiple class="chosen-select form-control"
									id="noticePeriod" name="noticePeriod"
									data-validation-optional="true"
									data-placeholder="Select Notice Period">
									<option value="Immediately Available">Immediately Available</option>
									<option value="15 Days">15 Days</option>
									<option value="1 Month">1 Month</option>
									<option value="2 Months">2 Months</option>
									<option value="3 Months">3 Months</option>
								</select>
							</div>
						  </div>
						  
						  <div class="col-md-4">
								<label>Client</label>
								<div class="clearfix">
									<select class="chosen-select" id="client" name="client"
										data-placeholder="Select Client" multiple>
										<c:forEach var="report" items="${clientsList}">
											<option value="${report.orgRelId}">${report.clientName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
						<div class="col-md-4">
								<label>Posted On</label>
								<div class="clearfix">
									<select class="chosen-select" id="updatedOn" name="updatedOn"
										data-placeholder="Posted On">
										<option>All</option>
										<option value="today">Today</option>
										<option value="yesterday">1 Day Ago</option>
										<option value="1_week">1 Week Ago</option>
										<option value="Last_15_days">2 Weeks Ago</option>
										<option value="1_month">1 Month Ago</option>
										<option value="3_months">3 Months Ago</option>
									</select>
								</div>
							</div>
						
						<div class="col-md-4">
							<label>Skills</label>
							<div class="clearfix">
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Skills" id="requirementSkills" name="requirementSkills">
								<c:forEach var="skill" items="${skillsList}">
								<option value="${skill.name}">${skill.name}</option>
								</c:forEach>
								</select>
							 </div>
						</div>
						  <div class="clearfix"></div>
						  <div class="col-md-4">
								<label>Choose City</label>
								<div class="clearfix">
								<div class="col-md-14">
							      <input type="text" class="form-control positionRelative"  multiple data-placeholder="select city" multiple name="apiLocation"  id="apiLocation">
								  <i class="fa fa-refresh btn btn-primary refresh-icon refreshIcon mr ref-top" title="Refresh" onClick="refreshGoogleApiCity('apiLocation');"></i>
								 </div>
								</div>
							</div>
							
							<div class="col-md-8">
								<label>Selected Cities</label>
								<div class="clearfix">
								   <select class="chosen-select form-control input-sm" multiple
										data-placeholder="sletected cites" id="city" name="city">
									</select>
								</div>
							</div>
							
						</div>
					  </div>
				  </div>
				  
				  <div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Search" id="search_requiremet"
								onclick="getRequirementTableList();closePop();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="clearJobAdvSearch();getRequirementTableList();" class="btn btn-sm btn-primary" />
						</div>
					</div>
					
			</div>

<script type="text/javascript" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/submissionFailedCandidatesList.js"></script>

<script>
function clearJobAdvSearch(){
	 $("#reqDataTableSearch").val('');
	$('#client').val('').trigger('chosen:updated');
	$('#country').val('').trigger('chosen:updated');
	$('#requirementSkills').val('').trigger('chosen:updated');
	$('#noticePeriod').val('').trigger('chosen:updated');
	$('#requirementTitle').val('').trigger('chosen:updated');
	$('#generatedId').val('').trigger('chosen:updated');
	$('#updatedOn').val('').trigger('chosen:updated');
	$('#city').val('').trigger('chosen:updated');
	refreshGoogleApiCity('apiLocation');
}
</script>

<script>
  $(function() { 
	       //Adding Customized Job Title list this method is slead-common.js
		   tagIt("requirementTitle", 'Press Enter-Key Or Comma(,) Or Tab To Add Title:', 'tagItBykeyForSkills','skills','Job Title');
	       
		   
		 //Adding Customized Job Id list this method is slead-common.js
		   tagIt("generatedId", 'Press Enter-Key Or Comma(,) Or Tab To Add Add Generated ID:', 'tagItBykeyForSkills','skills','Generated ID');
		 
		 //Adding Customized Job Skills list this method is slead-common.js
		   tagIt("requirementSkills", 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:', 'tagItBykeyForSkills','skills','Skills');

		   tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','sletected cites');

		   
  });
  </script>
  
<script>
function submitCandidatesToJob(){
	
	
	 var candidateIds = new Array();
	 for(var e in shareCandidates) {
		 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
			   continue;
		 candidateIds.push(shareCandidates[e]);
	 } 
	 
	 if(candidateIds.length > 0){ 
		$('.action-pop').slideUp();
		$(".new_side_wrap").addClass("new_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		var result = "";
		result = result + '<h3>Select Job</h3>';
		result = result + '<div id="choicepopUp"></div>';
		result = result + '<div id="requirementsListLoading"></div>';
	    result = result +" <div class='mr pull-left'>";
		result = result +" <button type='button' onclick='jobAdvSearch()' class='btn btn-primary btn-sm'>Advanced Search&nbsp;&nbsp;<i class='fa fa-search'></i></button>";
		result = result +" <button type='button' onclick='clearJobAdvSearch();getRequirementTableList()' class='btn btn-primary btn-sm' title='Refresh'><i class='fa fa-refresh'></i></button>";
		result = result +"</div>"; 
		result = result +" <div class='pull-right'>";
		
		 result = result +" <div class='mr pull-left mt5'>";
    	 result = result +'<label class="switch mr pull-left">';
    	 result = result +'<input type="checkbox" checked onclick="checkResume();" value="Y" name="candidateResume"  id="candidateResume"   >';
    	 result = result +'<div class="slider round" title="Attach Applicants Resume"></div>';
    	 result = result +'</label>';
    	 result = result +"<span class='paddingLeft mt2'>Attach Resume</span></div>";
		
		result = result +" <div class='mr pull-left'>";
		result = result +" <input type='button' value='Submit' onclick='submitCandidtesToJob()' class='btn btn-primary btn-sm'>";
		result = result +"</div>";
		result = result +" <div class='dynamic_search pull-left'>";
	 	result = result +" <input type='text' placeholder='Search' id='reqDataTableSearch'/>";
	 	result = result +" <span class='cursorPointer faSearch' title='Search'><i class='fa fa-search' onClick='getRequirementTableList();'></i></span>";
	 	result = result +"</div>";
	 	result = result +"</div>";
	 	result = result +"<div class='clearfix'></div>";
	 	result = result + '<table id="reqListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'  <th>&nbsp;</th>';
		result = result +'	<th>Generated Id</th>';
		result = result +'	<th>Title</th>';
		result = result +'	<th>Profile (%)</th>';
		result = result +'	<th>Client</th>';
		result = result +'  <th>Posted On<br>(<small class="custom-small">YYYY-MM-DD</small>)</th>';
	  	result = result +'  <th>Location</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
		$("#addApplicantDiv").empty();
		$("#addApplicantDiv").append(result);
		getRequirementTableList();
	 }else{
		    var result = "";
			result = result +"	<div class='project_inn_mask'></div>"; 
			result = result +"	   <div id='shareCandidate' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Please select at least one Candidate</p>";
			result = result + ' <div id="reasonForInvoiceDiv"/>';
			result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelShareCandidate();"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelShareCandidate();"></b>';
			result = result +"	</div>";
			$("#shareEmptypopUp").empty();
			$("#shareEmptypopUp").append(result);
			$("#shareCandidate").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	 }
}

</script>

<script>
function checkResume(){
	  var flag = document.getElementById("candidateResume").checked;
	 if(flag == true){
		 $("#candidateResume").val('Y'); 
	 }else{
		 $("#candidateResume").val('N'); 
	 } 
}
</script>

<script>
function getRequirementTableList(){
	
	var searchData = $('#reqDataTableSearch').val();
   	if(searchData != '' && searchData != 'undefined' && (searchData.endsWith("-") || searchData.endsWith("\\"))){
   	    $('#reqDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   		$("#reqDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	}else{
   	 $('#reqDataTableSearch').parent().find('p.jquery_form_error_message').remove();
	 $("#addApplicantDivLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 var generatedId = "";
	 var requirementTitle = "";
	 var noticePeriod = "";
	 var client = "";
	 var updatedOn = "";
	 var requirementSkills = "";
	 var city = "";
	 
	 
	 
	 if($("#generatedId").val() != null && $("#generatedId").val() != 'undefined'){
		  generatedId = $("#generatedId").val();
	 }
	
	 if($("#requirementTitle").val() != null && $("#requirementTitle").val() != 'undefined'){
		 var requirementTitle = $("#requirementTitle").val();
	 }
	
	 if($("#noticePeriod").val() != null && $("#noticePeriod").val() != 'undefined'){
		 var noticePeriod = $("#noticePeriod").val();
	 }
	
	 if($("#client").val() != null && $("#client").val() != 'undefined'){
		 var client = $("#client").val();
	 }
	
	 if($("#updatedOn").val() != null && $("#updatedOn").val() != 'undefined'){
		 var updatedOn = $("#updatedOn").val();
	 }
	
	 if($("#requirementSkills").val() != null && $("#requirementSkills").val() != 'undefined'){
		 var requirementSkills = $("#requirementSkills").val();
	 }
	
	 if($("#city").val() != null && $("#city").val() != 'undefined'){
		 var city = $("#city").val();
	 }
	
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#reqDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = atsUrl+"/getRequirementListForCandidateBulkSubmission?searchValue="+searchValue+"&generatedId="+generatedId+"&jobTitle="+requirementTitle+"&noticePeriod="+noticePeriod+"&client="+client+"&createdDate="+updatedOn+"&skills="+requirementSkills+"&city="+city;
	 requirementListPagination(url,atsUrl);
	 $("#addApplicantDivLoading").removeAttr("style");
   	}
}
</script>

<script>
function requirementListPagination(url,atsUrl){
	
	var colIndex = 0;
    $('#reqListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 5
        }),
        "columns": [
            { "data": "action" },
            { "data": "generatedId"},
            { "data": "jobTitle"},
            { "data": "profilePercentage"},
            { "data": "clientName"},
            { "data": "createdDate" },
            { "data": "location"}
        ],
        "columnDefs": [ {
        	"targets": colIndex++,
    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		   result = result +'  <td id="jobRadios"><input type="radio" id="radio'+opt.id+'" value="'+opt.id+'" name="jobRadio" /></td>';
		               return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "generatedId",
              "sortable": true,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
 		  		 result = result +"<td>";
 		  	  	 if(opt.generatedId != '' && opt.generatedId != null){
 		  	  		
 		  		   	 result = result +"<div class='tbldata-dotdot_50 tblminwidth-75' title='"+opt.generatedId+"'>"+opt.generatedId+"</span></div>";
 		  	    	}else{
 		  	  		 result = result +"<div>-----</div>";
 		  	  	 }
 		  		 result = result +"</td>";
 	             return  result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "jobTitle",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='tbldata-dotdot_140'><span  title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "profilePercentage",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='tbldata-dotdot_50'><span  title='"+opt.profilePercentage+"'>"+opt.profilePercentage+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "client",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
	            		  result = result +"<td><div class='tbldata-dotdot_140'><span  title='"+opt.clientName+"'>"+opt.clientName+"</span></div></td>";
	            	  }else{
	            		  result = result +"<div>-----</div>";
	            	  }
	            	  
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "createdDate",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div class='tblminwidth-50'>"+opt.createdDate+"</div></td>";
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "location",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div class='tbldata-dotdot-me' title='"+opt.location+"'>"+opt.location+"</div></td>";
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"aaSorting": [[5,'desc']],
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#reqListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#reqDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#reqDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#reqDataTableSearch').val();
   	    	if(searchData.endsWith("-") || searchData.endsWith("\\")){
   	    		$("#reqDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}else{
   	    		getRequirementTableList();
   	    	}
   	    	$('#reqDataTableSearch').val(searchData);
    	   }
    	});
}
</script>


<script>
function jobAdvSearch(){
	     $("#requirementFilterPopup").toggle();
		 $(".advancedsearch-mask").fadeIn();
	 
}
</script>

<script>
function submitCandidtesToJob(){
	 
	 var jobTypeRadio = $('input[name=jobRadio]');
	 var jobId = jobTypeRadio.filter(':checked').val();
	 
	 if(jobId){
		  
		 var candidateIds = new Array();
		 for(var e in shareCandidates) {
			 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
				   continue;
			 candidateIds.push(shareCandidates[e]);
		   } 
		 
		 var candidateResume = $("#candidateResume").val();
		 
		  var candidateTypeRadio = $('input[name=candidateTypeFilter]');
		  var candidateTypeFilter = candidateTypeRadio.filter(':checked').val();
			
		 $("#requirementsListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			   $.ajax({
		          type : "POST",
		          url: atsUrl+"/submitRecommendationCandidates?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+jobId+"&candidateResume="+candidateResume+"&candidateType="+candidateTypeFilter,    
		           success :function(data) {
		        	   if (data.response === "FAILED") {
		        		   $("#requirementsListLoading").removeAttr("style");
				    		showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    		location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
				    		$("#requirementsListLoading").removeAttr("style");
			       			document.getElementById("radio"+jobId).checked = false;
			       			activeTab();
			       			clearJobAdvSearch();
			       			$(".clients_side_wrap").removeClass("clients_side_wrap_active");
			       			$(".side_mask").fadeOut(200);
			       			$("body").css('overflow-y', 'auto');
			       			msgFadein();
			       			var submittedlength = '';
			       			if(data.responseObject.length > 0){
			       				 submittedlength = candidateIds.length - data.responseObject.length;
			       				failedCandidates.getFailuredCandidates(data.responseObject);
			       			}else{
			       				 submittedlength = candidateIds.length;
			       			}
			       			
			       			if(submittedlength > 0){
			       				$("#candMsgDiv").html(submittedlength+" Candidate(s) Submitted Successfully...");
			       			}
			       			
				    	}else if (data.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    		showError("Operation failed, please try again");
				    	}
		        	   
		        	   
		           },
		   });  
		 
		 
	 }else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Select Job</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='okPopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPopUp();'></b>";
			result = result +"	</div>";
			$("#choicepopUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	 }
	 
}

</script>


<script type="text/javascript">
function okPopUp(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}

function cancelPopUp(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	activeTab();
	clearJobAdvSearch();
	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
}
</script>


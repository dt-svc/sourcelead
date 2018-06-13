<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/resources/css/style.css" />
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<script src="/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->

<style>
.refresh-icon {
 top: 16px !important;
}
</style>
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
									<option>Immediately Available</option>
									<option>15-Days</option>
									<option>1-Month</option>
									<option>2-Month</option>
									<option>3-Month</option>
								</select>
							</div>
						  </div>
						  
						  <div class="col-md-4">
								<label>Client</label>
								<div class="clearfix">
									<select class="chosen-select" id="client" name="client"
										data-placeholder="Select Client" multiple>
										<c:forEach var="report" items="${add_clientList}">
											<c:if test="${report.locationId.id == loginOrgLocId}">
												<option
													value="${report.responderLocationId.id}"
													${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.responderLocationId.org.orgName}</option>
											</c:if>
											<c:if
												test="${report.responderLocationId.id == loginOrgLocId}">
												<option
													value="${report.locationId.id}"
													${report.id  == requirement.clientId.id ? 'selected' : ''}>${report.locationId.org.orgName}</option>
											</c:if>
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
										<option>1 Day Ago</option>
										<option>3 Days Ago</option>
										<option>1 Week Ago</option>
										<option>2 Weeks Ago</option>
										<option>1 Month Ago</option>
										<option>3 Months Ago</option>
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
								  <i class="fa fa-refresh btn btn-primary refresh-icon mr ref-top" title="Refresh" onClick="refreshGoogleApiCity('apiLocation');"></i>
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
								onclick="getRequirementTableList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="clearJobAdvSearch();" class="btn btn-sm btn-primary" />
						</div>
					</div>
					
			</div>


<script>
function clearJobAdvSearch(){
	$('#client').val('').trigger('chosen:updated');
	$('#country').val('').trigger('chosen:updated');
	$('#requirementSkills').val('').trigger('chosen:updated');
	$('#noticePeriod').val('').trigger('chosen:updated');
	$('#requirementTitle').val('').trigger('chosen:updated');
	$('#generatedId').val('').trigger('chosen:updated');
	$('#updatedOn').val('').trigger('chosen:updated');
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
		
		 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  createdDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  createdDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  createdDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  createdDate = 'YYYY-DD-MM'; 
		  }
		  
		var result = "";
		result = result + '<h3>Requirement List</h3>';
		result = result + '<div id="choicepopUp"></div>';
		result = result + '<div id="requirementsListLoading"></div>';
		result  = result + '<div class="success_pop" id="selector4">';
		result  = result + '<i class="fa fa-check-circle"></i><span id="successMsgDiv"></span>';
		result = result +"</div>";
		/* result = result +" <div class='mr pull-left'>";
		result = result +" <button type='button' onclick='jobAdvSearch()' class='btn btn-primary btn-sm'>Advanced Search&nbsp;&nbsp;<i class='fa fa-search'></i></button>";
		result = result +"</div>"; */
		result = result +" <div class='pull-right'>";
		result = result +" <div class='mr pull-left'>";
		result = result +" <input type='button' value='Submit To Job' onclick='submitCandidtesToJob()' class='btn btn-primary btn-sm'>";
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
		result = result +'  <th>Posted On<br>(<small class="custom-small">'+createdDate+'</small>)</th>';
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
function getRequirementTableList(){
/* 	var generatedId = $("#generatedId").val();
	var requirementTitle = $("#requirementTitle").val();
	var noticePeriod = $("#noticePeriod").val();
	var client = $("#client").val();
	var updatedOn = $("#updatedOn").val();
	var requirementSkills = $("#requirementSkills").val();
	var city = $("#city").val(); */
	 $("#addApplicantDivLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#reqDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getRequirementListForCandidateBulkSubmission?searchValue="+searchValue;
	 requirementListPagination(url,sourceleadUrl);
	 $("#addApplicantDivLoading").removeAttr("style");
}
</script>

<script>
function requirementListPagination(url,sourceleadUrl){
	
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
	            	  result = result +"<td><div class='proj_show tbldata-dotdot_140'><span class='pull-left' title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
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
	    	"aaSorting": [[3,'desc']],
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
   	    	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		getRequirementTableList();
   	    	}else{
   	    		$("#reqDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}
   	    	$('#reqDataTableSearch').val(searchData);
    	   }
    	});
}
</script>


<script>
function jobAdvSearch(){
	  $("#reqDataTableSearch").val('');
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
			
		 $("#requirementsListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			   $.ajax({
		          type : "POST",
		          url: sourceleadUrl+"/submitRecommendationCandidates?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+jobId,    
		           success :function(data) {
		        	   if (data.response === "FAILED") {
		        		   $("#requirementsListLoading").removeAttr("style");
				    		showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    		location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
				    		$("#requirementsListLoading").removeAttr("style");
			       			$("#successMsgDiv").html("Great ! you have successfully submitted to this job");
			       			successMsgFadeinFunction();
			       			document.getElementById("radio"+jobId).checked = false;
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
			result = result +"		<p>Select Requirement</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='okPopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPopUp();'></b>";
			result = result +"	</div>";
			$("#choicepopUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	 }
	 
}
</script>

<script>
	function successMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
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
	$(".clients_side_wrap").removeClass("clients_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
}
</script>


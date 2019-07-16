<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/resources/css/style.css" />
<link rel="stylesheet" href="/resources/css/chosen.min.css">
<script src="/resources/js/submissionFormBuilder.js" type="text/javascript" ></script> <!-- form builder -->
<script src="/resources/js/submissionForm-html-generator.js" type="text/javascript" ></script> <!-- form generator -->
<script src="/resources/js/slead-common.js" type="text/javascript" ></script> <!-- form generator -->

<div id="candidateRejectedDiv" class="project_tabs clearfix"></div> 
<div class="proMask">
<div class="progress progress-striped active">
    <div class="progress-bar progress-bar-success" data-values="100">0</div>
</div>
</div>

<div class="advancedsearch-mask"></div>
<div class="cand-advsrch-popup advancedsearch-popup" id="candidateFilterPopup">
				<h5 class="headerAdsrch">Advance Search
					<a href="javascript:void(0)" class="close-div" onClick="closePop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
						<div class="clearfix">
							<div class="col-md-4">
								<label>Primary Skill</label>
								<div class="clearfix">
									<select class="chosen-select form-control input-sm" multiple
										data-placeholder="Primary Skill" id="primarySkill" name="primarySkill">
									</select>
								</div>
							</div>
							
						 <div class="col-md-4">
						    <label>Employment Type</label>
						     <div class="clearfix">
						             <select class="chosen-select form-control" id="designationType" name="designationType">
						                <option value="">--Select--</option>
						                 <option>C2C</option>
						                 <option>W-2</option>
						                 <option>C2H</option>
						                 <option>Part Time</option>
						                 <option>Full Time</option>
						                 <option>Other</option>
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
								<label>Vendor</label>
								<div class="clearfix">
									<select class="chosen-select" id="vendor" name="vendor"
										data-placeholder="Select Vendor" multiple>
										<c:forEach var="report" items="${add_VendorList}">
											<option value="${report.orgRelId}">${report.vendorName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
						<div class="col-md-4">
								<label>Created On</label>
								<div class="clearfix">
									<select class="chosen-select" id=createdDate name="createdDate"
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
								<select class="chosen-select form-control input-sm" multiple data-placeholder="Skills" id="candidateSkills" name="candidateSkills">
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
								  <i class="fa fa-refresh btn btn-primary refresh-icon refreshIcon mr ref-top refreshIcontop16" title="Refresh" onClick="refreshGoogleApiCity('apiLocation');"></i>
								 </div>
								</div>
							</div>
							
							<div class="col-md-8">
								<label>Selected Cities</label>
								<div class="clearfix">
								   <select class="chosen-select form-control input-sm" multiple
										data-placeholder="selected cites" id="city" name="city">
									</select>
								</div>
							</div>
							
						</div>
					  </div>
				  </div>
				  
				  <div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Search" id="search_requiremet"
								onclick="getCandidatesTableList();closePop();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="clearCandAdvSearch();getCandidatesTableList();" class="btn btn-sm btn-primary" />
						</div>
					</div>
					
			</div>
	
	
 
  
  <script>
  
function closePop(){
	 $("div.cand-advsrch-popup").hide();
	 $(".advancedsearch-mask").fadeOut();
}
	
function clearCandAdvSearch(){
	$("#candDataTableSearch").val('');
	$('#primarySkill').val('').trigger('chosen:updated');
	$('#designationType').val('').trigger('chosen:updated');
	$('#vendor').val('').trigger('chosen:updated');
	$('#candidateSkills').val('').trigger('chosen:updated');
	$('#noticePeriod').val('').trigger('chosen:updated');
	$('#createdDate').val('').trigger('chosen:updated');
	$('#city').val('').trigger('chosen:updated');
	refreshGoogleApiCity('apiLocation');
}
</script>
  
  		
			
<script>
	function candidateAdvSearch(){
		 getcites('apiLocation','city','recommendation');
	     $("#candidateFilterPopup").toggle();
		 $(".advancedsearch-mask").fadeIn();
		 
		 //Adding Customized Job Title list this method is slead-common.js
		   tagIt("primarySkill", 'Press Enter-Key Or Comma(,) Or Tab To Add Title:', 'tagItBykeyForSkills','skills','Job Title');
	       
		 //Adding Customized Job Skills list this method is slead-common.js
		   tagIt("candidateSkills", 'Press Enter-Key Or Comma(,) Or Space To Add New Skills:', 'tagItBykeyForSkills','skills','Skills');

		   tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','selected cites');
 
	}
</script>
			
<script>
function submitCandidatesToJob(){
	    shareCandidates = {};
		$('.action-pop').slideUp();
		$(".submission_side_wrap").addClass("submission_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		var result = "";
		result = result + '<h3>Candidate List</h3>';
		result = result + '<div id="candidatesChoicepopUp"></div>';
		result = result + '<div id="candidatesListLoading"></div>';
	    result = result +" <div class='mr pull-left'>";
		result = result +" <button type='button' onclick='candidateAdvSearch()' class='btn btn-primary btn-sm'>Advanced Search&nbsp;&nbsp;<i class='fa fa-search'></i></button>";
		result = result +" <button type='button' onclick='clearCandAdvSearch();getCandidatesTableList()' class='btn btn-primary btn-sm' title='Refresh'><i class='fa fa-refresh'></i></button>";
		result = result +"</div>"; 
		result = result +" <div class='pull-right'>";
		
		 result = result +" <div class='mr pull-left mt5'>";

    	 result = result +'<label class="switch mr pull-left ml">';
    	 result = result +'<input type="checkbox" checked onclick="checkCandidateResume();" value="Y" name="candidateResume"  id="candidateResume"   >';

    	 result = result +'<div class="slider round" title="Attach Applicants Resume"></div>';
    	 result = result +'</label>';
    	 result = result +"<span class='paddingLeft mt4'>Attach Resume</span></div>";
    	 
		result = result +" <div class='mr pull-left'>";
		result = result +" <input type='button' value='Submit' onclick='submitApplicantsToJob()' class='btn btn-primary btn-sm'>";
		result = result +"</div>";
		result = result +" <div class='dynamic_search pull-left'>";
	 	result = result +" <input type='text' placeholder='Search' id='candDataTableSearch'/>";
	 	result = result +" <span class='cursorPointer faSearch' title='Search'><i class='fa fa-search' onClick='getCandidatesTableList();'></i></span>";
	 	result = result +"</div>";
	 	result = result +"</div>";
	 	result = result +"<div class='clearfix'></div>";
	 	result = result + '<table id="candListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'  <th class="text-center"><input type="checkbox" id="sharedcheckbox" onclick="toggleShareCandidates(this);"/></th>'; 
		result = result +'	<th>Candidate Name</th>';
		result = result +'	<th>Email</th>';
		result = result +'	<th>Primary Skill</th>';
		result = result +'	<th>Employment Type</th>';
		result = result +'	<th>Vendor</th>';
		result = result +'  <th>Updated Date<br>(<small class="custom-small">YYYY-MM-DD</small>)</th>';
	  	result = result +'  <th>Location</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
		$("#addJobApplicantsDiv").empty();
		$("#addJobApplicantsDiv").append(result);
		getCandidatesTableList();
	
}

</script>

<script>
function checkCandidateResume(){
	 var flag = document.getElementById("candidateResume").checked;
	 if(flag == true){
		 $("#candidateResume").val('Y'); 
	 }else{
		 $("#candidateResume").val('N'); 
	 }
}
</script>

<script>
function toggleShareCandidates(source){
	  var checkboxes = document.querySelectorAll('input[class="contactscheckbox"]');
	  if($(source).is(":checked"))
		  $('input[class="contactscheckbox"]').prop('checked', 'checked');
	  else 
		  $('input[class="contactscheckbox"]').removeProp('checked');
	  
	  $('input[class="contactscheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
		      shareCandidates[$(this).data("id")] = $(this).val();
		  } else {
			  shareCandidates[$(this).data("id")] = "";
		  }
	  });
}

function checkOrUncheckParent(tableId, selectedItems, checkboxId) {
	$("#"+checkboxId).removeProp("checked");
	   var count = 0;
	   var data = $("#"+tableId).DataTable().data();
	 for(var e in selectedItems) {
	   if(!e || !selectedItems[e] || selectedItems[e] === "")
		   continue;
	   
	   for(var f in data) {
 	   if(e === data[f].id+"") {
 		count ++;
 	   }
		}
	 }
	 if(count == data.count()) {
		$("#"+checkboxId).prop("checked", "checked");
	 }
}
</script>
<script>
var shareCandidates = {};
function checkShareCandidates(elem) {
	if($(elem).is(":checked")) {
		shareCandidates[$(elem).data("id")] = $(elem).val();
	} else{
		$("#sharedcheckbox").removeProp('checked');
		shareCandidates[$(elem).data("id")] = "";
	}
}
</script>

<script>
function getCandidatesTableList(){
	
	
	var searchData = $('#candDataTableSearch').val();
   	if(searchData != '' && searchData != 'undefined' && (searchData.endsWith("-") || searchData.endsWith("\\"))){
   	 $('#candDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	 $("#candDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	}else{
   	 $('#candDataTableSearch').parent().find('p.jquery_form_error_message').remove();
	 var primarySkill = "";
	 var designationType = "";
	 var noticePeriod = "";
	 var vendor = "";
	 var createdDate = "";
	 var candidateSkills = "";
	 var city = "";
	 
	 if($("#primarySkill").val() != null && $("#primarySkill").val() != 'undefined'){
		 primarySkill = $("#primarySkill").val();
	 }
	
	 if($("#designationType").val() != null && $("#designationType").val() != 'undefined'){
		 var designationType = $("#designationType").val();
	 }
	
	 if($("#noticePeriod").val() != null && $("#noticePeriod").val() != 'undefined'){
		 var noticePeriod = $("#noticePeriod").val();
	 }
	
	 if($("#vendor").val() != null && $("#vendor").val() != 'undefined'){
		 var vendor = $("#vendor").val();
	 }
	
	 if($("#createdDate").val() != null && $("#createdDate").val() != 'undefined'){
		 var createdDate = $("#createdDate").val();
	 }
	
	 if($("#candidateSkills").val() != null && $("#candidateSkills").val() != 'undefined'){
		 var candidateSkills = $("#candidateSkills").val();
	 }
	
	 if($("#city").val() != null && $("#city").val() != 'undefined'){
		 var city = $("#city").val();
	 }
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#candDataTableSearch").val();
	 var requirementId = '${requirementId}';
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getCandidatesListForJobBulkSubmission?searchValue="+searchValue+"&primarySkill="+primarySkill+"&designationType="+designationType+"&noticePeriod="+noticePeriod+"&vendor="+vendor+"&createdDate="+createdDate+"&candidateSkills="+candidateSkills+"&city="+city+"&requirementId="+requirementId;
	 candidatesListPagination(url,sourceleadUrl);
	}
}
</script>

<script>
function candidatesListPagination(url,sourceleadUrl){
	
	var colIndex = 0;
    $('#candListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 3
        }),
        "columns": [
            { "data": "action" },
            { "data": "candidateName"},
            { "data": "email"},
            { "data": "primarySkill" },
            { "data": "employementType" },
            { "data": "vendorName" },
            { "data": "updatedDate" },
            { "data": "location"}
        ],
        "columnDefs": [ {
        	"targets": colIndex++,
    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 var shareflag = false;
	        		 for(var e in shareCandidates) {
   	     	    	   if(!e || !shareCandidates[e] || shareCandidates[e] === "")
   	     				   continue;
   	     	    	   if(e === opt.id+"") {
   	     	    		    if(!$("#sharedcheckbox").is(":checked"))
   	     	    		 	checkOrUncheckParent("candListTable",shareCandidates, "sharedcheckbox"); 
   	     	    		    shareflag = true;
   	     	    	   	break;
   	     	    	   }
   	        		 }
	        		 if(!shareflag)
   	        			$("#sharedcheckbox").removeAttr('checked');
	        		   result = result +"<div class='tblminwidth-30 pull-left ml'>";
	        			result = result +'<div class="pull-left mt3"><td><input'+(shareflag ? " checked='checked'" : "")+' class="contactscheckbox" name="sharedcheckbox"  type="checkbox" id="'+opt.id+'" data-id="'+opt.id+'" value="'+opt.id+'" onchange="checkShareCandidates(this)"/></td></div>';
    	  		
	        			if(opt.isAssociate == 'Y'){
			            	  result = result +"<i class='fa fa-black-tie pull-right mt4' title='Added as Associate'></i> ";  
			             }
	        			 result = result +"</div>";
	        			return result; 
	    	  	}
	      },{
		    	  "targets": colIndex++,
	              "data": "candidateName",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='tbldata-dotdot_140'><span class='pull-left' title='"+opt.candidateName+"'>"+opt.candidateName+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "email",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div class='tbldata-dotdot_140'><span class='pull-left' title='"+opt.email+"'>"+opt.email+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "primarySkill",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.primarySkill != null && opt.primarySkill != '' && opt.primarySkill != "undefined"){
	            	    	 result = result +"<td><div class='tbldata-dotdot_50' title='"+opt.primarySkill+"'>"+opt.primarySkill+"</div></td>";
	            	    }else{
	            	    	 result = result +"<div>----</div>";
	            	    }
	            	   
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "employementType",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.employementType != null && opt.employementType != '' && opt.employementType != "undefined"){
	            	    	 result = result +"<td><div class='tblminwidth-50' title='"+opt.employementType+"'>"+opt.employementType+"</div></td>";
	            	    }else{
	            	    	 result = result +"<div>----</div>";
	            	    }
	            	   
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "vendorName",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.vendorName != null && opt.vendorName != '' && opt.vendorName != 'undefined'){
	            		  result = result +"<td><div class='tbldata-dotdot_140'><span  title='"+opt.vendorName+"'>"+opt.vendorName+"</span></div></td>";
	            	  }else{
	            		  result = result +"<div>----</div>";
	            	  }
	            	 
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "updatedDate",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.updatedDate != null && opt.updatedDate != '' && opt.updatedDate!= 'undefined'){
	            	    	result = result +"<td><div class='tbldata-dotdot_140' title='"+opt.updatedDate+"'>"+opt.updatedDate+"</div></td>";
	            	    }else{
	            	    	result = result +"<td><div class='tbldata-dotdot_140' title='"+opt.createdDate+"'>"+opt.createdDate+"</div></td>";
	            	    }
	            	    
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "location",
	              "sortable": true,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.location != null && opt.location != '' && opt.location != "undefined"){
	            	    	result = result +"<td><div class='tbldata-dotdot_140' title='"+opt.location+"'>"+opt.location+"</div></td>";
	            	    }else if(opt.country != null && opt.country != '' && opt.country != "undefined"){
	            	    	result = result +"<td><div class='tbldata-dotdot_140' title='"+opt.country+"'>"+opt.country+"</div></td>";
	            	    }else{
	            	    	result = result +"<div>----</div>";
	            	    }
	            	    
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"aaSorting": [[6,'desc']],
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#candListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#candDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#candDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#candDataTableSearch').val();
   	    /* 	var regex =  new RegExp(/[\\-]/g);
   	    	if(regex.test(searchData) == false){
   	    		getCandidatesTableList();
   	    	}else{
   	    		$("#candDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	} */
   	    	
   	    	if(searchData.endsWith("-") || searchData.endsWith("\\")){
   	    		$("#candDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
   	    	}else{
   	    		getCandidatesTableList();
   	    	}
   	    	
   	    	$('#candDataTableSearch').val(searchData);
    	   }
    	});
}
</script>



<script>
function submitApplicantsToJob(){
	 
	 var jobId = '${requirementId}';
	 
	 var candidateIds = new Array();
	 for(var e in shareCandidates) {
		 if(!e || !shareCandidates[e] || shareCandidates[e] === "")
			   continue;
		 candidateIds.push(shareCandidates[e]);
	   } 
	 
	 var candidateResume = $("#candidateResume").val();
	 
	 if(candidateIds.length > 0){
		 
		 getProgressBar();
		 
		 $("#candidatesListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			   $.ajax({
		          type : "POST",
		          url: sourceleadUrl+"/submitRecommendationCandidates?"+tokenParamName+"="+tokenValue+"&candidateIds="+candidateIds+"&requirementId="+jobId+"&candidateResume="+candidateResume,    
		           success :function(data) {
		        	   if (data.response === "FAILED") {
		        		   $("#candidatesListLoading").removeAttr("style");
				    		showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    		location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
				    		$("#candidatesListLoading").removeAttr("style");
				    		candMsgFadein();
				    		shareCandidates = {};
				    		$(".proMask").hide();
				    		$('.action-pop').slideUp();
				    		$(".submission_side_wrap").removeClass("submission_side_wrap_active");
				    		$(".side_mask").fadeOut(200);
				    		$("body").css('overflow-y', 'auto');
				    		requirementPipeline(jobId,'all');
				    		requirementStatusBar(jobId);
				    		
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
			result = result +"	<div class='comMask'></div>";
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Select Candidate For Submission</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='okPopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPopUp();'></b>";
			result = result +"	</div>";
			$("#candidatesChoicepopUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".comMask").fadeIn(200);
	 }
	 
}
</script>

<script>
$(document).ready(function() {
	$(".proMask").hide();
	
});
</script>

<script>
function getProgressBar(){
	$(".proMask").show();
	$(".progress div").each(function () {
	    var display = $(this),
	        currentValue = parseInt(display.text()),
	        nextValue = $(this).attr("data-values"),
	        diff = nextValue - currentValue,
	        step = (0 < diff ? 1 : -1);
	    if (nextValue == "0") {
	        $(display).css("padding", "0");
	    } else {
	        $(display).css("color", "#fff").animate({
	            "width": nextValue + "%"
	        }, "slow");
	    }

	    for (var i = 0; i < Math.abs(diff); ++i) {
	        setTimeout(function () {
	            currentValue += step
	            display.html(currentValue + "%");
	        }, 200 * i);
	    }
	});
	
	
}
</script>

<script type="text/javascript">
function okPopUp(){
	$("#candidatesChoicepopUp").empty();
	$(".comMask").fadeOut(200);
}

function cancelPopUp(){
	$("#candidatesChoicepopUp").empty();
	clearCandAdvSearch();
	$('.action-pop').slideUp();
	$(".submission_side_wrap").removeClass("submission_side_wrap_active");
	$(".side_mask").fadeOut(200);
	$("body").css('overflow-y', 'auto');
	$(".comMask").fadeOut(200);
}
</script>

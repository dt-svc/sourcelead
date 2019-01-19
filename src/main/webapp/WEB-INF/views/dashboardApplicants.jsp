<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">


 <!-- Applicant Authorization -->
 <div class="col-md-6 mb"> 
        <div class="dashNewWrap mb10">
        
        	<div class="dashNewWrapHead clearfix">
				<h4 class="pull-left">Applicant Permission</h4>
	            <ul class="nav nav-tabs pull-right">
	              <li class="active" id="acceptedTab"><a data-toggle="tab" href="javascript:void(0)" onclick="ApplicantAuthorization('requestSent')">Sent</a></li>
	              <li id="requestSentTab"><a data-toggle="tab" href="javascript:void(0)" onclick="ApplicantAuthorization('accepted')">Accepted</a></li>
	              <li id="rejectedTab"><a data-toggle="tab" onclick="ApplicantAuthorization('REJECTED');">Rejected</a></li>
	            </ul>			
        	</div>         
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
          <div class="clearfix project_tabs_inn_table dashboard-data newDashPadSearch dataSeaNew">
         <div class="success_pop" id="selector4" style="display: none;"><i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span></div>
            <div class="tab-content clearfix">
              <div id="accepted" class="tab-pane fade in active"></div>
              <div id="requestSent" class="tab-pane fade"></div>
            </div> 
          </div>
      </div>
</div>
<!-- Applicant Authorization Ends --> 


<script>

$(document).ready(function(){
	ApplicantAuthorization('requestSent');
});
</script>
<script>
function ApplicantAuthorization(status){
	var sourceleadUrl = '${sourceleadUrl}';
	var loginUser = '${SourceLeadWeb_userName}';
	//$("#accepted").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  dashBoardDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  dashBoardDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  dashBoardDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  dashBoardDate = 'YYYY-DD-MM'; 
	  }  
	
	$.ajax({
			type : "POST",
			url : sourceleadUrl+"/getApplicantsByStatus?${_csrf.parameterName}=${_csrf.token}&status="+status,
			success : function(data) {
					if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
	            	 	 var json = data.data;
	              			$("#accepted").empty();
	        	        	$("#requestSent").empty();
	              			$("#accepted").removeAttr("style");
	              			var resultToday ="";
	              			//if(json.length > 0){
	              				resultToday = resultToday+'<div id="rejectApplicantAlert" ></div>  ';
	              				resultToday = resultToday+'	<table id="applicantsPagination" class="display" cellspacing="0" width="99.5%">';
	            				resultToday = resultToday+'	<thead>';
	            				resultToday = resultToday+'	  <tr>';
	            				resultToday = resultToday+'	    <th>Applicant</th>';
	            				resultToday = resultToday+'	    <th>Job</th>';
	            				resultToday = resultToday+'	    <th>Team Member</th>';
	            				if(status == 'accepted'){
	            					resultToday = resultToday+'	    <th>Accepted By</th>';
		            				resultToday = resultToday+'	    <th><div class="tblminwidth-100">Accepted On<br>(<small class="custom-small">'+dashBoardDate+'</small>)</div></th>';
	            				}
	            				if(status == 'REJECTED'){
	            					resultToday = resultToday+'	    <th>Rejected By</th>';
		            				resultToday = resultToday+'	    <th><div class="tblminwidth-100">Rejected On<br>(<small class="custom-small">'+dashBoardDate+'</small>)</div></th>';
	            				}
	            				if(status == 'requestSent'){
	            					resultToday = resultToday+'	    <th>Status</th>';
	            					resultToday = resultToday+'	    <th>Action</th>';
	            				}
	            				resultToday = resultToday+'	  </tr>';
	            				resultToday = resultToday+'	</thead>';
	            				resultToday = resultToday+'	<tbody>';
	            				
	            			     for (var i=0; i<json.length; i++){
                                       var report = json[i];
                                      resultToday = resultToday+'      <tr>';
                                     
                                      if(report.candidateName != '' && report.candidateName != null && report.candidateName !='undefined'){
                                          resultToday  = resultToday + '           <td><div><span title="'+report.candidateName+'">'+report.candidateName+'</span></div></td>';
                                      }else{
                                          resultToday  = resultToday + '           <td><div>-----</div></td>';
                                      }
                                      if(report.requirementName != '' && report.requirementName != null && report.requirementName !='undefined'){
                                          resultToday = resultToday +'<td><div class="tbldata-dotdot-sm"> <a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+report.requirementUUID+'"><span title="'+report.requirementName+'">'+report.requirementName+'</span></a></div></td>';

                                      }else{
                                          resultToday  = resultToday + '           <td><div>-----</div></td>';
                                      }
                                      if(report.submittedBy != '' && report.submittedBy != null && report.submittedBy !='undefined'){
                                          resultToday  = resultToday + '           <td><div><span title="'+report.submittedBy+'">'+report.submittedBy+'</span></div></td>';
                                      }else{
                                          resultToday  = resultToday + '           <td><div>-----</div></td>';
                                      }
                                      if(status == 'accepted'){
                                    	  if(report.submissionRequestUser != null && report.submissionRequestUser != 'undefined'){
                                    	  resultToday  = resultToday + '           <td><div><span title="'+report.submissionRequestUser+'">'+report.submissionRequestUser+'</span></div></td>';
                                    	  }else{
                                        	  resultToday  = resultToday + '           <td><div><span title="'+report.candidateName+'">'+report.candidateName+'</span></div></td>';

                                    	  }
                                    		  
                                    	  resultToday  = resultToday + '           <td><div class="tblminwidth-100"><span title="'+report.submittedOn+'">'+report.submittedOn+'</span></div></td>';
                                      }
                                      if(status == 'REJECTED'){
                                    	  if(report.submissionRequestUser != null && report.submissionRequestUser != 'undefined'){
                                    	  resultToday  = resultToday + '           <td><div><span title="'+report.submissionRequestUser+'">'+report.submissionRequestUser+'</span></div></td>';
                                    	  }else{
                                        	  resultToday  = resultToday + '           <td><div><span title="'+report.candidateName+'">'+report.candidateName+'</span></div></td>';

                                    	  }
                                    		  
                                    	  resultToday  = resultToday + '           <td><div class="tblminwidth-100"><span title="'+report.submittedOn+'">'+report.submittedOn+'</span></div></td>';
                                      }

                                      if(status == 'requestSent'){ 
                                    	    resultToday  = resultToday + '<td><div>Received</div></td>';
                                    	    resultToday = resultToday +"<td>";
                                    	     if(report.submissionRateStatus == 'REQUEST' && report.createdUser.trim() != loginUser.trim()){
                                    	    	resultToday = resultToday + '<a  class="paddingRight"  href="javascript:void(0)" title="Accept"  onClick="acceptCandidateFromDashboard(\''+report.id+'\', \'accept\');"><i class="fa fa-check text-success"></i></a>';
                                    	    	resultToday = resultToday + '<a class="paddingLeft" href="javascript:void(0)" title="Reject"   onClick="rejectApplicantAlertMessage(\''+report.id+'\', \'rejected\');"><i class="fa fa-close text-danger"></i></a>';
                                    	    }else{
                                    	    resultToday = resultToday + '---';
                                    	    } 
                                    	    resultToday = resultToday +"</td>";
                                      }
                                      resultToday = resultToday+'     </tr>';
	            			                                }
	            					resultToday = resultToday+'	 </tbody>';
	            					resultToday = resultToday+'	</table>';											
	            			//}
	            	        if(status == 'accepted'){
	            	            $("#accepted").append(resultToday);
	            	            applicantsPagination();
	            	            $("#accepted").addClass("tab-pane fade in active");
	            				$("#requestSent").removeClass("tab-pane fade in active");
	            	        }else if(status == 'requestSent'){
	            				$("#requestSent").append(resultToday);
	            				applicantsPagination();
	            				$("#accepted").removeClass("tab-pane fade in active");
	            				$("#requestSent").addClass("tab-pane fade in active");
	            	        }else{
	            	        	$("#accepted").append(resultToday);
	            	        	applicantsPagination();
	            	            $("#acceptedTab").removeClass("active");
	            				$("#requestSentTab").removeClass("active");
	            				
	            	        } 
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
			},
	});
	return true;
}
</script>
<script type="text/javascript">
function rejectApplicantAlertMessage(submissionId, status){
	var result = "";
	result = result +"	<div class='vendor_wrap_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	 result = result +"<form id='rejected_from_dashboard' name='rejected_from_dashboard'>";
	 result = result +"    <div class='col-md-12 inputOther mt10 inputOther'>";
     result = result +"    <div class='dropdown-select mb10' id='rejectedCategorytDiv'>";
     result = result +"<select id='rejectedCategory' name='rejectedCategory' class='form-control' onchange='getRejectedCategory()' data-validation='required' field-name='Category'>";
     result = result +"<option selected value=''>--Select Category--</option>";
     result = result +"<option value='Wrong Skill Sets'>Wrong Skill Sets</option>";
     result = result +"<option value='Unsuitable Personality'>Unsuitable Personality</option>";
     result = result +"<option value='Bad Fit'>Bad Fit</option>";
     result = result +"<option value='Salary'>Salary</option>";
     result = result +"<option value='Interview Rejection'>Interview Rejection</option>";
     result = result +"<option value='Other'>Other</option>";
     result = result +"</select>";
     result = result +"    </div>";
    
     
     result = result +" <div id='rejectOtherCategoryDiv' style='display: none' class='mb10'>";
     result = result +" <input type='text' id='rejectedCategoryOther' name='rejectedCategoryOther' class='form-control' data-validation-optional='true' field-name='Other' >";
     result = result +" </div>";
     
     
     //result = result +"        <p>Applicant reject message here</p>";
     result = result +"    <div class='clearfix'>";
     result = result +"        <textarea  id='comment' class='form-control txtarea_action' name='comment'  placeholder='Comments here' onkeypress='dashboardReasonForRejectRemoveSpaces();' data-validation='required validate_Space validate_length length2-500' field-name='Reason'></textarea>";
     result = result +" </div>";
     result = result +" </div>";
     result = result + ' <div id="reasonForApplicantDiv"/>';
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='acceptRejectCandidateFromDashboard(\" "+submissionId+"\", \"rejected\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	  result = result +"</form>";
	result = result +"	</div>";
	$("#rejectApplicantAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
   function dashboardReasonForRejectRemoveSpaces() {
	    var output = document.getElementById("reasonForReject").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForReject").removeAttr("data-validation");
	    }
	}
</script>
<script type="text/javascript">
    function acceptCandidateFromDashboard(submissionId, status){
		if ($('#rejected_from_dashboard').validate(false, validationSettings)) {
	  		var formData = getFormData('rejected_from_dashboard'); 
			$.ajax({
				type : "POST",
	    	    data: formData,
			    url: sourceleadUrl+"/acceptRejectCandidateFromDashboard?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&status="+status,	    	   
			    success: function(result) {
				    	if (result.response === "FAILED") {
		  		    		showError(result.responseMessage);
		  		    	} else if (result.response === "AWKWARD") {
		  		    		location.href = "/errorPage";
		  		    	} else if (result.response === "SUCCESS") {
		  		    		ApplicantAuthorization('requestSent');
                       	 	$("#selector4").removeAttr("style");
		            		$("#selector4").css({ display: "block" });
	            			$("#eduMsgDiv").html("Accepted");
                        	eduMsgFadeinFunction();
		  		    	}else if (result.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    		showError("Operation failed, please try again");
				    	}
                     },
                });return true;
		}else{
			return false;
		}
    }
</script>

<script>
function getRejectedCategory(){
	var rejectedCategory = document.getElementById("rejectedCategory").value;
	if(rejectedCategory == 'Other'){
		 document.getElementById("rejectOtherCategoryDiv").style.display="block";
	    $("#rejectedCategoryOther").removeAttr("data-validation-optional");
		$('#rejectedCategoryOther').attr("data-validation","required validate_Space validate_length length2-100 "); 
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}else{
		$("#rejectedCategoryOther").attr("data-validation-optional","true");
		$("#rejectedCategoryOther").removeAttr("data-validation");
		$("#rejectedCategoryOther").val('');
		document.getElementById("rejectOtherCategoryDiv").style.display="none";
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}
	
}
</script>
<script>
	function eduMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
	}
</script> 

<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function cancelAlert(){ 
	$("#rejectApplicantAlert").empty();
	$(".vendor_wrap_mask").fadeOut(200);
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
<script type="text/javascript">
	function acceptRejectCandidateFromDashboard(submissionId, status){
		
		if ($('#rejected_from_dashboard').validate(false, validationSettings)) {
			
	  		var formData = getFormData('rejected_from_dashboard'); 
			$.ajax({
				type : "POST",
	    	    data: formData,
			    url: sourceleadUrl+"/acceptRejectCandidateFromDashboard?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&status="+status,	    	   
			    success: function(result) {
			    	if (result.response === "FAILED") {
	  		    		showError(result.responseMessage);
	  		    	} else if (result.response === "AWKWARD") {
	  		    		location.href = "/errorPage";
	  		    	} else if (result.response === "SUCCESS") {
	  		    		$("#popUp").empty();
				    	$("#rejectApplicantAlert").empty();
				    	$(".vendor_wrap_mask").fadeOut(200);
				    	ApplicantAuthorization('requestSent');
				    	$("#selector4").removeAttr("style");
	         			$("#selector4").css({ display: "block" });
	     				$("#eduMsgDiv").html("Rejected");
	              		eduMsgFadeinFunction();
	  		    	}else if (result.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
			    }
		   });
			return true;
		}else{
			return false;
		}
	}
	$('body').on('blur', '#rejected_from_dashboard', function() {
		$("#rejected_from_dashboard").showHelpOnFocus();
		$("#rejected_from_dashboard").validateOnBlur(false, validationSettings);
	});
		
		
		
	
		
</script>

<script>
function applicantsPagination(){
    $('#applicantsPagination').DataTable( {
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $('#applicantsPagination').wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
 

      
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

 <!-- Applicant Authorization -->
      <div class="col-md-6">
        <div class="dashboard-widget">
          <h4 class="clearfix">Applicant Authorization
          </h4>
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
          <div class="clearfix require_wrap_table dashboard-data filter_position">
            <ul class="nav nav-tabs">
              <li class="active" id="acceptedTab"><a data-toggle="tab" href="javascript:void(0)" onclick="ApplicantAuthorization('requestSent')">Request Sent</a></li>
              <li id="requestSentTab"><a data-toggle="tab" href="javascript:void(0)" onclick="ApplicantAuthorization('accepted')">Accepted</a></li>
            </ul>
            
            <div class="tab-content clearfix mt">
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
	//$("#accepted").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
			type : "POST",
			url : sourceleadUrl+"/getApplicantsByStatus?${_csrf.parameterName}=${_csrf.token}&status="+status,
			success : function(data) {
				var json = JSON.parse(data);
				/* if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
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
	            				resultToday = resultToday+'	    <th>Requirement</th>';
	            				resultToday = resultToday+'	    <th>Team Member</th>';
	            				if(status == 'accepted'){
	            					resultToday = resultToday+'	    <th>Accepted By</th>';
		            				resultToday = resultToday+'	    <th>Accepted On</th>';
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
                                    		  
                                    	  resultToday  = resultToday + '           <td><div><span title="'+report.submittedOn+'">'+report.submittedOn+'</span></div></td>';
                                      }
                                      if(status == 'requestSent'){ 
                                    	    resultToday  = resultToday + '<td><div>'+report.submissionRateStatus+'</div></td>';
                                    	    resultToday = resultToday +"<td>";
                                    	    if(report.submissionRateStatus == 'REQUEST'){
                                    	    	resultToday = resultToday + '<a  href="javascript:void(0)"  onClick="acceptRejectCandidateFromDashboard(\''+report.id+'\', \'accept\');"><i class="fa fa-check text-success"></i></a>';
                                    	    	resultToday = resultToday + '<a  href="javascript:void(0)"   onClick="rejectApplicantAlertMessage(\''+report.id+'\', \'rejected\');"><i class="fa fa-close text-danger"></i></a>';
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
					/* }else{
	               	 	$("#accepted").removeAttr("style");
	               	 	$("#accepted").empty();
     	        		$("#requestSent").empty();
	 	    	        var result ="";
						result = result+"			<div class='blank-pages'>";
						result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
						result = result+"			        <p>No Applicants Found</p>";
						result = result+"			 </div>";
					    $("#accepted").append(result); 
	               } */
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
	 result = result +"    <div class='col-md-12 inputOther mt10 inputOther'>";
     result = result +"    <div class='dropdown-select mb10' id='rejectedCategorytDiv'>";
     result = result +"<select id='rejectedCategory' name='rejectedCategory' class='form-control' onchange='getRejectedCategory()' data-validation='true'>";
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
     result = result +" <input type='text' id='rejectedCategoryOther' name='rejectedCategoryOther' class='form-control' data-validation-optional='true' >";
     result = result +" </div>";
     
     result = result +" </div>";
     
     //result = result +"        <p>Applicant reject message here</p>";
     result = result +"        <textarea  id='reasonForReject' class='form-control' name='reasonForReject'  placeholder='Comments here'></textarea>";
     result = result + ' <div id="reasonForApplicantDiv"/>';
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='acceptRejectCandidateFromDashboard(\" "+submissionId+"\", \"rejected\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	$("#rejectApplicantAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
function getRejectedCategory(){
	var rejectedCategory = document.getElementById("rejectedCategory").value;
	if(rejectedCategory == 'Other'){
		 document.getElementById("rejectOtherCategoryDiv").style.display="block";
	    $("#rejectedCategoryOther").removeAttr("data-validation-optional");
		$('#rejectedCategoryOther').attr("data-validation","required"); 
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
	function acceptRejectCandidateFromDashboard(submissionId, status){
		 var sourceleadUrl = '${sourceleadUrl}';
		 var reason= document.getElementById("reasonForReject").value;
		 var rejectedCategoryType= document.getElementById("rejectedCategory").value; 
		 var rejectedCategoryOther= document.getElementById("rejectedCategoryOther").value;
		 if((reason == null || reason == '' || reason == 'undefined') 
				 || (rejectedCategoryType == null || rejectedCategoryType == '' || rejectedCategoryType == 'undefined') 
				 || ( (rejectedCategoryType != null && rejectedCategoryType != '' && rejectedCategoryType != 'undefined' && rejectedCategoryType == 'Other') 
						  &&  (rejectedCategoryOther == null || rejectedCategoryOther == '' || rejectedCategoryOther == 'undefined')
					 )
		     ){
			 if(reason != null && reason != '' && reason != 'undefined'){
				 $("#reasonForApplicantDiv").find('p.jquery_form_error_message').remove();
			 }else{
					 $("#reasonForApplicantDiv").find('p.jquery_form_error_message').remove();
			  	    $("#reasonForApplicantDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
			 }
			 if(rejectedCategoryType != null && rejectedCategoryType != '' && rejectedCategoryType != 'undefined'){
				 $("#rejectedCategorytDiv").find('p.jquery_form_error_message').remove();
			 }else{
					 $("#rejectedCategorytDiv").find('p.jquery_form_error_message').remove();
			  	  $("#rejectedCategorytDiv").append("<p class='jquery_form_error_message'>Category Is Required</p>");
			 }
			 if(rejectedCategoryOther.match(/^[^\s-](.*)*$/) != null && rejectedCategoryType == 'Other' && rejectedCategoryOther != null && rejectedCategoryOther != '' && rejectedCategoryOther != 'undefined'){
				 $("#rejectOtherCategoryDiv").find('p.jquery_form_error_message').remove();
			 }
			 else{
				 
				 $("#rejectOtherCategoryDiv").find('p.jquery_form_error_message').remove();
				 $("#rejectOtherCategoryDiv").append("<p class='jquery_form_error_message'>Other Type Is Required</p>");
		  	 
		      }
			}
			
		 else{
				$("#reasonForApplicantDiv").find('p.jquery_form_error_message').remove();
			  $("#rejectedCategorytDiv").find('p.jquery_form_error_message').remove();
			 $.ajax({
					 type : "POST",
					 url : sourceleadUrl+"/acceptRejectCandidateFromDashboard?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&status="+status+"&reason="+reason+"&rejectedCategoryType="+rejectedCategoryType+"&rejectedCateoryOther="+rejectedCategoryOther,
					 success : function(result) {
								 if( result.indexOf('Sign In') >= 0){
						    		location.replace(sourceleadUrl);
						    	 }
			            		 else if (result.indexOf('exception') >= 0) {
			            			 location.replace(sourceleadUrl+"/errorPage");
			            		 }
			            		 else{
			            			 ApplicantAuthorization('requestSent');
			            			/*  $('#accpet-div').fadeOut();
			            			 $('#reject').fadeOut(); */
			            		 }
					 },
				}); 
		 }
	}
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
    } );
}
</script>
 

      
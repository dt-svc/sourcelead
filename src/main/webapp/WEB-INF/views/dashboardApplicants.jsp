 <!-- Applicant Authorization -->
      <div class="col-md-6">
        <div class="dashboard-widget">
          <h4 class="clearfix">Applicant Authorization
          </h4>
          <!-- <a href="javascript:void(0)" class="collpase-div pull-right" title="SlideUp/SlideDown"><i aria-hidden="true" class="fa fa-angle-down"></i></a> -->
          <div class="clearfix require_wrap_table dashboard-data clients_wrap_table">
            <ul class="nav nav-tabs">
              <li class="active" id="acceptedTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); ApplicantAuthorization('requestSent')">Request Sent</a></li>
              <li id="requestSentTab"><a data-toggle="tab" href="javascript:void(0)" onclick="flushDate(); ApplicantAuthorization('accepted')">Accepted</a></li>
            </ul>
            
            <div class="tab-content clearfix mt custom-datatble">
              <div id="accepted" class="tab-pane fade in active"></div>
              <div id="requestSent" class="tab-pane fade"></div>
            </div> 
          </div>
        </div>
      </div>
<!-- Applicant Authorization Ends --> 

<script>
window.onload = ApplicantAuthorization('requestSent');
function ApplicantAuthorization(status){
	var resultToday ="";
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
	              			//if(json.length > 0){
	              				resultToday = resultToday+'<div id="rejectApplicantAlert" ></div>  ';
	              				resultToday = resultToday+'	<table id="applicantsPagination" class="display" cellspacing="0" width="100%">';
	            				resultToday = resultToday+'	<thead>';
	            				resultToday = resultToday+'	  <tr>';
	            				resultToday = resultToday+'	    <th>Applicant</th>';
	            				resultToday = resultToday+'	    <th>Requirement</th>';
	            				resultToday = resultToday+'	    <th>Team Member</th>';
	            				if(status == 'accepted'){
	            					resultToday = resultToday+'	    <th>Accepted by</th>';
		            				resultToday = resultToday+'	    <th>Accepted on</th>';
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
                                          resultToday  = resultToday + '           <td><div><span title='+report.candidateName+'>'+report.candidateName+'</span></div></td>';
                                      }else{
                                          resultToday  = resultToday + '           <td><div>-----</div></td>';
                                      }
                                      if(report.requirementName != '' && report.requirementName != null && report.requirementName !='undefined'){
                                          resultToday = resultToday +'<td><div class="tbldata-dotdot-sm"> <a href="'+sourceleadUrl+'/requirementSummary?requirementUUID='+report.requirementUUID+'"><span title='+report.requirementName+'>'+report.requirementName+'</span></a></div></td>';

                                      }else{
                                          resultToday  = resultToday + '           <td><div>-----</div></td>';
                                      }
                                      if(report.submittedBy != '' && report.submittedBy != null && report.submittedBy !='undefined'){
                                          resultToday  = resultToday + '           <td><div><span title='+report.submittedBy+'>'+report.submittedBy+'</span></div></td>';
                                      }else{
                                          resultToday  = resultToday + '           <td><div>-----</div></td>';
                                      }
                                      if(status == 'accepted'){
                                    	  resultToday  = resultToday + '           <td><div><span title='+report.submissionRequestUser+'>'+report.submissionRequestUser+'</span></div></td>';
                                          resultToday  = resultToday + '           <td><div><span title='+report.submittedOn+'>'+report.submittedOn+'</span></div></td>';
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
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='acceptRejectCandidateFromDashboard(\" "+submissionId+"\", \"rejected\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	$("#rejectApplicantAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
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
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script type="text/javascript">
	function acceptRejectCandidateFromDashboard(submissionId, status){
			 var sourceleadUrl = '${sourceleadUrl}';
			 var reason;
			 if ($('#rejectReason').length)
			 {
				 reason = document.getElementById("rejectReason").value; 
			 }
			 else
			 {
				 reason = '';
			 }
			 $.ajax({
					 type : "POST",
					 url : sourceleadUrl+"/acceptRejectCandidateFromDashboard?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&status="+status+"&reason="+reason,
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
</script>

<script>
function applicantsPagination(){
    $('#applicantsPagination').DataTable( {
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": ""
       }
    } );
}
</script>
 

      
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div class="col-md-6 mb"> 
        <div class="dashNewWrap mb10">
        
        	<div class="dashNewWrapHead clearfix">
				<h4 class="pull-left">Job Approvals</h4>
	            <ul class="nav nav-tabs pull-right">
	              <li class="active" id="waitingTab"><a data-toggle="tab" onclick="requirementApproverList('SUBMITTED');">Waiting </a></li>
	              <li id="approvedTab"><a data-toggle="tab" onclick="requirementApproverList('APPROVED');">Approved </a></li>
	              <li id="rejectedTab"><a data-toggle="tab" onclick="requirementApproverList('REJECTED');">Rejected</a></li>
	            </ul>				
        	</div>        
          <div class="clearfix project_tabs_inn_table dashboard-data newDashPadSearch dataSeaNew">
           	<div class="success_pop" id="selector4" style="display: none;"><i class="fa fa-check-circle"></i><span id="eduMsgDiv"></span></div>
          	<div id="requirementApproverList" class=""></div>
          </div>
        <!-- Pagenation Ends --> 
      </div>
      </div>
      <!-- Requirement Approvals Ends --> 

<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in requirementApproverListRestController.
--%>

  <script> 
$(document).ready(function(){
	requirementApproverList('SUBMITTED');
});
</script>


<script>

function requirementApproverList(statusType){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  //$("#requirementApproverList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/getApproverRequirementApproverListByOrgLocationIDAndUserId?statusType="+statusType,    
            success :function(data) {
	            	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
	            	 	 var json = data.data;
			    	     $("#requirementApproverList").removeAttr("style");
	                	 $("#requirementApproverList").empty();
	                	 var result = "";
	                	 result = result+'<div id="rejectRequirementAlert" ></div>  ';
	                	 result = result +"<table id='requirementApproverPagination' class='display' cellspacing='0' width='99.5%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>";  
	                     result = result +"    <th>ID-Title</th>"; 
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Exp</th>"; 
	                     
	                     if(statusType == "APPROVED"){
	                    	 result = result +"    <th>Approved By</th>";
	                     }else if( statusType == "REJECTED"){
	                    	 result = result +"    <th>Rejected By</th>";
	                     }else{
	                    	 result = result +"    <th>Action</th>";
	                     }
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 result = result +"<tr>";
	                    	 if(opt.jobId!=null && opt.jobId !='' && opt.jobId !='undefined'){
		                    	 result = result +"<td><div class='tbldata-dotdot-vsm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title = '"+opt.jobId+"-"+opt.jobTitle+"'>"+opt.jobId+"-"+opt.jobTitle+"</span></a></div></td>";
		                     }else{
		                    	 result = result +"<td><div class='tbldata-dotdot-vsm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title = '"+opt.jobTitle+"'>"+opt.jobTitle+"</span></a></div></td>";
		                     }
		                     if(opt.rate!=null && opt.rate !='' && opt.rate !='undefined' && opt.maxRate != null && opt.maxRate != '' && opt.maxRate !='undefined'){
		                    	 result = result +"<td><div><span title = '"+opt.currencyCode+"-"+opt.rate+"-"+opt.maxRate+"-"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"-"+opt.maxRate+"&nbsp;"+opt.rateType+"</span></div></td>";
		                     }else if(opt.rate!=null && opt.rate !='' && opt.rate !='undefined'){
		                    	 result = result +"<td><div><span title = '"+opt.currencyCode+"-"+opt.rate+"-"+opt.rateType+"'>"+opt.currencyCode+"&nbsp;"+opt.rate+"&nbsp;"+opt.rateType+"</span></div></td>";
		                     }
		                     else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     var exp='';
		                     
		                     if(opt.maxExp !=null && opt.maxExp !='' && opt.maxExp !='undefined'){
		                    	 exp =opt.maxExp;
		                     }
		                     
		                     if(opt.minExp !=null && opt.minExp !='' && opt.minExp !='undefined'){
		                    	 exp =opt.minExp; 
		                     }
		                     if(opt.maxExp !=null && opt.maxExp !='' && opt.maxExp !='undefined' &&  opt.minExp !=null && opt.minExp !='' && opt.minExp !='undefined'){
		                    		 exp = opt.minExp+" - "+opt.maxExp;
		                     } 
		                     
                                  
		                     result = result +"<td><div><span title='"+exp+"'>"+exp+"</span></div></td>";
	                    	  
                             var APPROVED = "APPROVED";     
 				             var REJECTED = "REJECTED";
 				            result = result +"<td>";
				             if(opt.requirementSubmissionStatus  !='DRAFT'  && opt.requirementSubmissionStatus  !='OPEN'  &&  opt.requirementApproverHierarchyStatus !='APPROVED' &&  opt.requirementApproverHierarchyStatus != 'REJECTED' && opt.requirementApproverHierarchyStatus !='' && opt.requirementApproverHierarchyStatus !='undefined' && opt.requirementApproverHierarchyStatus !=null){
				            	
				            	 result = result + '<a class="paddingRight" href="javascript:void(0)" title="Accept"  onClick="updateReuqirementStatusAccept(\''+ APPROVED+ '\','+opt.id+');"   ><i class="fa fa-check text-success"></i></a>';
		                    	 result = result + '<a class="paddingLeft" href="javascript:void(0)" title="Reject"  onClick="rejectRequirementAlertMessage(\''+ REJECTED+ '\','+opt.id+');"  ><i class="fa fa-close text-danger"></i></a>';
   				           	 
				             }else if((opt.requirementApproverHierarchyStatus == 'APPROVED' ||  opt.requirementSubmissionStatus == 'OPEN')  || (opt.requirementApproverHierarchyStatus !='' && opt.requirementApproverHierarchyStatus !='undefined' && opt.requirementApproverHierarchyStatus !=null)){
				            	 result = result +'<div  id="requirementStatus"  ><span title ="'+opt.approvedBy+'">'+opt.approvedBy+'</span></div>';
    				         }else if( opt.requirementSubmissionStatus  !='DRAFT'  && opt.requirementApproverHierarchyStatus == 'REJECTED'  && opt.requirementApproverHierarchyStatus !='' && opt.requirementApproverHierarchyStatus !='undefined' && opt.requirementApproverHierarchyStatus !=null){
    				        	 result = result +'<div  id="requirementStatus" ><span title = "'+opt.approvedBy+'">'+opt.approvedBy+'</span></div>';

		                     }else{
		                    	 result = result +"<div>-----</div>";
		                     }  
				             result = result +"</td>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#requirementApproverList").append(result); 
	                     requirementApprovalPagination(); 
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
          },
            
       }); 
  } 
</script>   

<script>
function requirementApprovalPagination(){ 
    $('#requirementApproverPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for update Requirement.
     1.usage     :  used in Requirement jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
	function eduMsgFadeinFunction() {
		$("#selector4").fadeIn(200);
        $('#selector4').delay(2000).fadeOut('slow');
	}
</script> 
<script>
function updateReuqirementStatusAccept(status,requirementId){
    var sourceleadUrl = '${sourceleadUrl}';
    var requirementId = requirementId;
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
      url: sourceleadUrl+"/requirementsAcceptOrReject?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status+"&reason="+reason,
              success :function(result) {  
            	if (result.response === "FAILED") {
  		    		showError(result.responseMessage);
  		    	} else if (result.response === "AWKWARD") {
  		    		location.href = "/errorPage";
  		    	} else if (result.response === "SUCCESS") {
              	 	var data = result.object;
		            if(data != null && data != 'undefined' && data != ""){  
	                      requirementApproverList('SUBMITTED');
	                      if(status=='APPROVED'){
	         				$("#selector4").removeAttr("style");
	            			$("#selector4").css({ display: "block" });
            				$("#eduMsgDiv").html("Approved");
	               			eduMsgFadeinFunction();
	         			 }
		            }
  		    	}else if (result.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
       		},
      });
}
</script>


<script>
function updateReuqirementStatus(status,requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	var requirementId = requirementId;
	 var reason = document.getElementById("rejectReason").value;
	  if(reason.match(/^[^\s-](.*)*$/) != null){
	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/requirementsAcceptOrReject?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status+"&reason="+reason, 
    		  success :function(result) {
    			if (result.response === "FAILED") {
  		    		showError(result.responseMessage);
  		    	} else if (result.response === "AWKWARD") {
  		    		location.href = "/errorPage";
  		    	} else if (result.response === "SUCCESS") {
              	 var data = result.object;
		            if(data != null && data != 'undefined' && data != ""){  
	          			 requirementApproverList('SUBMITTED');
	          			 if(status=='REJECTED'){
	            			$("#selector4").removeAttr("style");
	            			$("#selector4").css({ display: "block" });
         					$("#eduMsgDiv").html("Rejected");
	               			eduMsgFadeinFunction();
	         			 }
		            }
  		    	}else if (result.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
       		},
	  }); 
	
	  }
	  else{
		  if(reason){
				$("#reasonDasbordDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
			}else{
				$("#reasonDasbordDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
			}
	  }
}
</script>




<%-- 
     @author : Venkat Kesav
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function rejectRequirementAlertMessage(status,requirementId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Reject Reason' rows='2'></textarea>";
	result = result +"	<div id='reasonDasbordDiv'/>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='updateReuqirementStatus(\""+ status+"\",\""+requirementId+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlertDashboard();'></b>";
	result = result +"	</div>";
	$("#rejectRequirementAlert").append(result);
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
function cancelAlertDashboard(){ 
	$("#rejectRequirementAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

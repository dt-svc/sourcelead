<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="/resources/css/style.css" />


<script>
function candidateShortList(requirementId){
	$('.action-pop').slideUp();
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var result = "";
	var status = "SUBMISSION";
	result = result + '<div class="add_inter_head"> <span>Shortlist</span> <span class="text-success col-md-offset-1">No Of Candidates Selected: <b id="checkedshortlistedCount">0</b></span><a href="javascript:void(0)" onclick="shortlist('+requirementId+');" class="sub-btn1">Shortlist</a>';
	result = result + ' <div class="clearfix"></div>';
	result = result + ' </div>';
	
	 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getSubmissionCandidates?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status,
	    			
	    	success: function(data) {
	    		if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
			    	if(data.data != null && data.data != 'undefined' && data.data != ""){
			    	   var json = data.data;
			           result = result + '     <div class="clearfix"></div>';
			           result = result + '   </div>';
			           result = result + '   <div class="require_wrap_table requiretable-firstchild datatable_filter scroll_table mt40">';
			           result = result + '     <table id="shortlistedPagination"  class="display" cellspacing="0" width="100%">';
			           result = result + '       <thead>';
			           result = result + '         <tr>';
			           result = result + ' <th><input type="checkbox" onclick="selectAllShortlisted();"  id="selectallSL" ></th>';
			           result = result + '           <th>Name</th>';
			           result = result + '           <th>Email</th>';
			           result = result + '           <th>Phone</th>';
			           result = result + '           <th>Resume</th>';
			           result = result + '           <th>Applied On</th>';
			           result = result + '           <th>Rate</th>';
			           result = result + '           <th>Source</th>';
			           result = result + '           <th>Experience</th>';
			           result = result + '         </tr>';
			           result = result + '       </thead>';
			           result = result + '       <tbody>';
				        
				    	if(json.length > 0)
				   		{
				    		for(var i=0; i<json.length; i++){
				    			var report = json[i];
				    			var colorHint = "";
			   	        		if(report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != "" && report.lastRateStage != "undefined" && report.lastRateIniLocId != "undefined" && report.lastRateIniType != "undefined" && report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null){
				   	        		if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
				    			
				    			if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
				    			
			    			    result = result + '<tr>';
			    			    result = result + '<td><input type="checkbox"  class="submissionsChecks" value='+report.id+' onclick="getCheckedCandidatesCount();"  name="submissionList[]" ></td>';
			    			    
					            result = result + '<td><div class="tbldata-dotdot-100" title = "'+report.candidateName+'">'+report.candidateName+'</div></td>';
					           
					           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
					        	   result = result + '<td><div  class="tbldata-dotdot-100" title = "'+report.candidateEmail+'">'+report.candidateEmail+'</div></td>';
						           }
						       else{
						        	result = result + '<td><div><span>--</span></div></td>';
					    	     }
					           
					           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
					        	   result = result + '<td><div class="tbldata-dotdot-100">'+report.phoneNumber+'</div></td>';
						           }
						       else{
						        	result = result + '<td><div><span>--</span></div></td>';
					    	     }
					           
					           if(report.fileURL != null && report.fileURL != '' && report.fileURL != 'undefined'){
					        	   result = result + '<td><div class="tbldata-dotdot_80"><a download class="glyphicon glyphicon-download-alt" href=\"'+report.fileURL+'\"></a><a class="fa fa-eye ml10" onclick="viewFile(\''+report.fileURL+'\',\''+report.fileName+'\',\''+report.fileContentType+'\');"></a></span></div></td>';
						           }
						       else{
						    	   result = result + '<td><div>--</div></td>';
					    	     }
					           
					           if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
					        	   result = result + '<td><div class="tbldata-dotdot_80">'+report.createdDateStr+'</div></td>';
						           }
						       else{
						        	result = result + '<td><div><span>--</span></div></td>';
					    	     }
					           
					           if(report.expectedCTC != null && report.expectedCTC != '' && report.expectedCTC != 'undefined'){
					        	   result = result + '<td><div><span class="showRate '+colorHint+'" style="cursor:auto">'+report.expectedCTC+'</span></div></td>';
						           }
						       else{
						    	   result = result + '<td><div><span class="showRate '+colorHint+'" style="cursor:auto"><i class="glyphicon fa fa-money" style="cursor:auto"></i></span></div></td>';
					    	     }
					           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
					        	   result = result + '<td><div class="tbldata-dotdot_80" title = "'+report.createdUser+''+orgName+'">'+report.createdUser+''+orgName+'</div></td>';
						           }
						       else{
						        	result = result + '<td><div><span>--</span></div></td>';
					    	     }
					           
					           if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
					        	   result = result + '<td><div class="tbldata-dotdot_80">'+report.workExperience+'</div></td>';
						           }
						       else{
						        	result = result + '<td><div><span>--</span></div></td>';
					    	     }
					           
					           result = result + '       </tr>';
				    		}
				    		
				    		   result = result + '       </tbody>';
				    		   result = result + '       </table>';
		// 			           result = result + '</div><div id="popUp">';
					           result = result + '</div><div id="loading">';
				    			
				    			$("#shortlistDiv").empty();
				    			$("#shortlistDiv").append(result);
				    			shortListPagination();
				   		}
				   		}else{
				    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-user'></i><h3>Submission List is Empty</h3></div>";
				    		$('#shortlistDiv').empty();
				    		$('#shortlistDiv').append(result);
				    	}
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
	    	}
	    }); 
	

	    $(".shortlist-wrap").addClass("shortlist-wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden')
	 
}
</script>

<script>
function viewResumeRequirementSummary(resumeUUID,title){
	if(resumeUUID != null && resumeUUID != ""){
		window.open(sourceleadUrl+'/viewProfessionalCandidateResume/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
	}  
}
</script>


<script>
function shortListPagination(){
    $('#shortlistedPagination').DataTable( { 
    	"scrollCollapse": false,
    	"bPaginate": true,
    	"bLengthChange": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bDestroy": true,
    	"aaSorting": [[1,'desc']],
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
    	"oLanguage": {
     	"sSearch": "",
     	 "sZeroRecords": "", "sEmptyTable": ""
     	}
		
    } );
}
</script>

<!-- For add New Applicant -->
<script>
    $(".shortlist_close, .side_mask").click(function() {
    	$(".shortlist-wrap").removeClass("shortlist-wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<script>
function selectAllShortlisted(){
	
	var checked = document.getElementById("selectallSL").checked  
	
        if(checked) {
            $('.submissionsChecks').each(function() {
                this.checked = true;   
            });
        }else{
            $('.submissionsChecks').each(function() {
                this.checked = false;      
            });         
        } 
	
	 var selectedsubmissionslists = [];
	 var oTable = $('#shortlistedPagination').dataTable();
	 var rowcollection =  oTable.$("input[name='submissionList[]']:checked", {"page": "all"});
	 rowcollection.each(function(index,elem){
		 selectedsubmissionslists[index] = $(elem).val();
	 });
	 
	$("#checkedshortlistedCount").html(selectedsubmissionslists.length);
	  
}
</script> 


<script>
function getCheckedCandidatesCount(){
	 var selectedsubmissionslists = [];
	 var oTable = $('#shortlistedPagination').dataTable();
	 var rowcollection =  oTable.$("input[name='submissionList[]']:checked", {"page": "all"});
	 rowcollection.each(function(index,elem){
		 selectedsubmissionslists[index] = $(elem).val();
	 });
	 
	$("#checkedshortlistedCount").html(selectedsubmissionslists.length);
	 
}
</script>


<script>
function shortlist(requirementId){
	 var selectedsubmissionslists = [];
	 var oTable = $('#shortlistedPagination').dataTable();
	 var rowcollection =  oTable.$("input[name='submissionList[]']:checked", {"page": "all"});
	 rowcollection.each(function(index,elem){
		 selectedsubmissionslists[index] = $(elem).val();
	 });
	 
	  if(selectedsubmissionslists.length > 0){
		  
		  if(selectedsubmissionslists.length <= 50){
				 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
				 $.ajax({ 
				    	type: "POST",
				    	url: sourceleadUrl+"/candidateShortList?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionsList="+selectedsubmissionslists,
				        success: function(data) {
				        	if (data.response === "FAILED") {
					    		showError(data.responseMessage);
					    	} else if (data.response === "AWKWARD") {
					    		location.href = "/errorPage";
					    	} else if (data.response === "SUCCESS") {
					        	$(".shortlist-wrap").removeClass("shortlist-wrap_active");
				        		$(".side_mask").fadeOut(200);
				        		$("body").css('overflow-y', 'auto');
				        		var allApplicants = 'all';
					    		requirementPipeline(requirementId,allApplicants); 
					    		pipelineList(requirementId);
					    	}else if (data.indexOf('Sign In') !== -1) {
					    		location.replace(sourceleadUrl);
					    	} else {
					    		showError("Operation failed, please try again");
					    	}
				        },
				 });   
		  }else{
			  alertMessage("<b>Selected Candidates should not more than 50</b>");
		  }
	 }else{
		 alertMessage("<b>Select Candidate for Shortlist</b>");
	 } 
}
</script>

<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
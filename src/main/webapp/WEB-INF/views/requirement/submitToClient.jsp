<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script>
function submitToClient(requirementId){
	
	
	var sourceleadUrl ='${sourceleadUrl}';
	var result = "";
	var status = "SHORTLIST";
	result = result + '<div class="add_inter_head"> <span>Submissions</span><a href="javascript:void(0)" onclick="clientSubmissions('+requirementId+');" class="sub-btn1">Submit to client</a>';
	result = result + ' <div class="clearfix"></div>';
	result = result + ' </div>';
	
	 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getSubmissionCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status,
	    			
	    	success: function(data) {
		       
	    	   var json = JSON.parse(data);
		        
	           result = result + '     <div class="clearfix"></div>';
	           result = result + '   </div>';
	           result = result + '   <div class="require_wrap_table">';
	           result = result + '     <table id="shortlistedPagination"  class="display" cellspacing="0" width="100%">';
	           result = result + '       <thead>';
	           result = result + '         <tr>';
	           result = result + ' <th><input type="checkbox" onclick="selectAllSubmitToClient();"  id="selectallSTC" ></th>';
	           result = result + '           <th>Name</th>';
	           result = result + '           <th>Email</th>';
	           result = result + '           <th>Ph. No</th>';
	           result = result + '           <th>Resume</th>';
	           result = result + '           <th>Applied on</th>';
	           result = result + '           <th>Rate</th>';
	           result = result + '           <th>Source</th>';
	           result = result + '           <th>Experince</th>';
	           result = result + '         </tr>';
	           result = result + '       </thead>';
	           result = result + '       <tbody>';
		        
		    	if(json.length > 0)
		   		{
		    		for(var i=0; i<json.length; i++){
		    			var report = json[i];
		    			
	    			    result = result + '<tr>';
	    			    result = result + '<td><input type="checkbox"  class="shortlistedChecks" value='+report.id+' name="shortlist[]" ></td>';
	    			    
			            result = result + '<td><div><span>'+report.candidateName+'</span></div></td>';
			           
			           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
			        	   result = result + '<td><div><span>'+report.candidateEmail+'</span></div></td>';
				           }
				       else{
				        	result = result + '<td><div><span>--</span></div></td>';
			    	     }
			           
			           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
			        	   result = result + '<td><div><span>'+report.phoneNumber+'</span></div></td>';
				           }
				       else{
				        	result = result + '<td><div><span>--</span></div></td>';
			    	     }
			           
			           if(report.resumeName != null && report.resumeName != '' && report.resumeName != 'undefined'){
			        	   result = result + '<td><div><span><a href="javascript:void(0)"><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeId='+report.id+'\"></a>'+report.resumeName+'</span></div></td>';
				           }
				       else{
				    	   result = result + '<td><div>--</div></td>';
			    	     }
			           
			           if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
			        	   result = result + '<td><div><span>'+report.createdDateStr+'</span></div></td>';
				           }
				       else{
				        	result = result + '<td><div><span>--</span></div></td>';
			    	     }
			           
			           if(report.expectedCTC != null && report.expectedCTC != '' && report.expectedCTC != 'undefined'){
			        	   result = result + '<td><div><span>'+report.expectedCTC+'</span></div></td>';
				           }
				       else{
				        	result = result + '<td><div><span>--</span></div></td>';
			    	     }
			           
			           if(report.userName != null && report.userName != '' && report.userName != 'undefined'){
			        	   result = result + '<td><div><span>'+report.userName+'</span></div></td>';
				           }
				       else{
				        	result = result + '<td><div><span>--</span></div></td>';
			    	     }
			           
			           if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
			        	   result = result + '<td><div><span>'+report.workExperience+'</span></div></td>';
				           }
				       else{
				        	result = result + '<td><div><span>--</span></div></td>';
			    	     }
			           
			           result = result + '       </tr>';
			           
			           result = result + '       </tbody>';
			           result = result + '</div><div id="loading">';
			           result = result + '</div><div id="popUp">';
		    			
		    			$("#submitToClientDiv").empty();
		    			$("#submitToClientDiv").append(result);
		    			
		    		}
		   		}else{
		    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-user'></i><h3>Shortlisted List is Empty</h3></div>";
		    		$('#submitToClientDiv').empty();
		    		$('#submitToClientDiv').append(result);
		    	}
	    	}
	    	
	    		
	    }); 
	

	$(".submitnew-wrap").addClass("submitnew-wrap_active");
	$(".side_mask").fadeIn(200);
	$("body").css('overflow-y', 'hidden');
	
}
</script>




<script>
function pagination(){  
    $('#shortlistedPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     	"sSearch": ""
     	}
		
    } );
}
</script>

<!-- For add New Applicant -->
<script>
    $(".submitToClient_close, .side_mask").click(function() {
    	$(".submitnew-wrap").removeClass("submitnew-wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>


<script>
function selectAllSubmitToClient(){
	
	var checked = document.getElementById("selectallSTC").checked  
        if(checked) {
            $('.shortlistedChecks').each(function() {
                this.checked = true;   
            });
        }else{
            $('.shortlistedChecks').each(function() {
                this.checked = false;      
            });         
        } 
}
</script> 


<script>
function clientSubmissions(requirementId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var selectedShortlists = [];
	 $("input[name='shortlist[]']:checked").each( function (i) {
		 selectedShortlists[i] = $(this).val();
	});
	 
	 if(selectedShortlists.length > 0){
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		 $.ajax({ 
		    	type: "POST",
		    	url: sourceleadUrl+"/submitShortlistedToClient?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionsList="+selectedShortlists,
		        success: function(result) {
		        	$(".submitnew-wrap").removeClass("submitnew-wrap_active");
		    		$(".side_mask").fadeOut(200);
		    		$("body").css('overflow-y', 'auto');
		    		requirementPipeline(requirementId); 
		        },
		 }); 
	 }else{
		 alertMessage("<b>Select candidate for Client Submission</b>");
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
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script>
function candidateShortList(requirementId){
	
	var sourceleadUrl ='${sourceleadUrl}';
	var result = "";
	var status = "SUBMISSION";
	
	result = result + '<div class="add_inter_head"> <span>Shortlist</span><a href="javascript:void(0)" onclick="shortlist('+requirementId+');" class="sub-btn1">Shortlist</a>';
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
	           result = result + ' <th><input type="checkbox" onclick="selectAllShortlisted();"  id="selectallSL" ></th>';
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
	    			    result = result + '<td><input type="checkbox"  class="submissionsChecks" value='+report.id+' name="submissionList[]" ></td>';
	    			    
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
			        	   result = result + '<td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeId='+report.id+'\"></a>'+report.resumeName+'</span></div></td>';
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
		    			
		    			$("#shortlistDiv").empty();
		    			$("#shortlistDiv").append(result);
		    			
		    		}
		   		}else{
		    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-user'></i><h3>Submission List is Empty</h3></div>";
		    		$('#shortlistDiv').empty();
		    		$('#shortlistDiv').append(result);
		    	}
	    	}
	    	
	    		
	    }); 
	

	    $(".shortlist-wrap").addClass("shortlist-wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden')
	 
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
}
</script> 


<script>
function shortlist(requirementId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var selectedsubmissionslists = [];
	 $("input[name='submissionList[]']:checked").each( function (i) {
		 selectedsubmissionslists[i] = $(this).val();
	});
	 
	  if(selectedsubmissionslists.length > 0){
		 $.ajax({ 
		    	type: "POST",
		    	url: sourceleadUrl+"/candidateShortList?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionsList="+selectedsubmissionslists,
		        success: function(result) {
		        	if((result == 'success')){
		        		location.replace(sourceleadUrl+"/requirementSummary?requirementId="+requirementId); 
			        } 
		        },
		 }); 
	 } 
	
}
</script>
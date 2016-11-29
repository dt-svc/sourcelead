<!-- <link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet"> -->
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<style type="text/css">
.glyphicon-none:before {
    content: "\2122";
    color: transparent !important;
}
.timesheet-content {
background: #fff;
min-height: 100px;
clear:both;
display: inline-block;
width:100%;
}
.timesheet-content #example_filter {
position: relative !important;
top:0px !important;
}
</style>

<script>
	function predefineProfileImage(imageId,userId){
			$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
$(document).ready(function() {
      approvalList();
});
</script>

<script>
  function approvalList(){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#timesheetApprList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	
	  var employeeId = [];
		$('input:checkbox[name=employeeById]:checked').each(function() 
		{
			employeeId.push($(this).val());
	   });
		
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getArrovalSubmissions?${_csrf.parameterName}=${_csrf.token}&employeeId="+employeeId,        
            success :function(data) {
                 if(data != null && data != 'undefined' && data != ""){
               		if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	}
               		else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		}
               		else{
			    		 $("#timesheetApprList").removeAttr("style");
	                	 $("#timesheetApprList").empty();
	                     $("#timesheetApprList").append(data); 
	                     pagination();
			    	}
                 }else{
                	 $("#timesheetApprList").removeAttr("style");
  	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Timesheets Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#timesheetApprList").empty();
				    $("#timesheetApprList").append(result);
                 }
                 /* var json = JSON.parse(data); */
                 /* if(json.length > 0){
                	 
                 var output = "";
                 output = output+' <table>';
                 output = output+' <tbody>';
                 output = output+' <thead>';
                 output = output+'     <tr>';
                 output = output+'         <th>Employee</th>';
                 output = output+'         <th>Frequency</th>';
                 output = output+'         <th>Total hours</th>';
                 output = output+'         <th>Stage</th>';
                 output = output+'         <th>Action</th>';
                 output = output+'     </tr>';
                 output = output+' </thead>';
                 
                 
                 for (var i=0; i<json.length; i++)
                 {
                         var approval = json[i];
                         output = output+'<tr>';
                         output = output+'<td><div class="time_appr_user">';
                         output = output+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/'+approval.userId+'"/>" alt="Profile Image" class="img-circle img-responsive">'; 
                         output = output+    '<span><b>'+approval.fullName+'</b></span></div></div>';
                         output = output+'</td>';
                         output = output+'<td><div> '+approval.startDateStr+'  To  ';
                         output = output+         ''+approval.endDateStr+'';
                         output = output+     '</div>';
                         output = output+ '</td>';
                         output = output+ '<td><div><a onclick="myTimesheet(\''+approval.employeeId+'\',\''+approval.startDateStr+'\',\''+approval.endDateStr+'\');"  id="timesheetPopup'+approval.Id+'"> '+approval.combinedTotalHours+'</a></div></td>';
                         output = output+'<td><div>'+approval.combinedStatus+'</div></td>';
                         output = output+'<td>';
                           if(approval.combinedStatus == 'NEW' || approval.combinedStatus == 'SUBMITTED FOR APPROVAL'){
                               output = output+'<div><i class="fa fa-check" alt="Approve"  onclick="approveSubmission(\''+approval.employeeId+'\',\''+approval.startDateStr+'\',\''+approval.endDateStr+'\');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejecteSubmission(\''+approval.Id+'\',\''+approval.employeeId+'\',\''+approval.startDateStr+'\',\''+approval.endDateStr+'\');"></i></div>';
                               output = output+'<div id="rejectedPopup"></div>';
                           }
                         if(approval.combinedStatus =='REJECTED'){
                             output = output+'<div><h3 class="reje_color">Rejected</h3>';
                             output = output+'<i class="glyphicon glyphicon-comment" onclick="rejectedMessage(\'rejectedMessage'+approval.Id+'\');">';
                             output = output+  '<strong id="rejectedMessage'+approval.Id+'"><p>'+approval.rejectedReason+'</p></strong>';
                             output = output+ '</i>';
                             output = output+'</div>';
                         }
                         if(approval.combinedStatus =='APPROVED'){
                             output = output+'<div><h3>Approved</h3></div>';
                         }  
                          output = output+'</td>' ;
                          output = output+ '</tr>';
                 }
               
                 output = output+' </tbody>';
                 output = output+' </table>';
                 
                 $("#timesheetApprList").empty();
                 $("#timesheetApprList").append(output);
              }else{
            	     $("#timesheetApprList").removeAttr("style");
  	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Timesheets Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#timesheetApprList").empty();
				    $("#timesheetApprList").append(result); 
              }   */
          }
            
       }); 
  } 
</script>

<script>
function pagination(){ 
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
    		"sSearch": ""
    	}
    } );
}
</script>


<script type="text/javascript">
function dynamicTimesheet(navigation ,startDate,projectMemberUserId,memberId,employeeId){    
			var sourceleadUrl ='${sourceleadUrl}';  
			$("#dynamic").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/dynamicTimesheet?${_csrf.parameterName}=${_csrf.token}&navigation="+navigation+"&startDate="+startDate+"&memberId="+memberId+"&projectMemberUserId="+projectMemberUserId+"&employeeId="+employeeId, 	   
				    success: function(data) {
				    	if( data.indexOf('Sign In') >= 0){
				    		location.replace(sourceleadUrl);
				    	}
	               		else if (data.indexOf('exception') >= 0) {
	               			 location.replace(sourceleadUrl+"/errorPage");
	               		}else{
				    		$("#row"+projectMemberUserId).empty();
					        $("#row"+projectMemberUserId).append(data);
					        /* pagination(); */
				    	}
				        
				    }
		    });
}
</script>	

<!-- Common Scripts for approval timesheets and dashboard count links separated in to timesheetApprovalCommonScripts.jsp   -->
<jsp:include page="timesheetApprovalCommonScripts.jsp" />	

</body>
<jsp:include page="../masterFooter.jsp" />	
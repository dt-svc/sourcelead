<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<c:if test="${staticTime == null || staticTime == 0 }">
	    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
	 </c:if>
	<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>

<style>
.settPop .form-control {
    height: auto !important; 
}
</style>

<div class="mb clearfix col-md-12 noPad"> 
      <div class=" dashNewWrap hdrPosition minHeight_225 mb10">
      
        	<div class="dashNewWrapHead noBor clearfix">
				<h4 class="pull-left">Candidates on-boarding</h4>
        	</div>
        
        <div class="clearfix project_tabs_inn_table dashboard-data newDashPad dataSeaNew"> 
                 <div id="onboardingLoading"></div>
        		 <div id="onboardingCandidatesList"></div>
        		 <div id="rejectedReasonDiv"></div>
        </div>
      </div>
    </div>
    
<script> 
$(document).ready(function(){
	onboardingCandidatesList();
});
</script>

<script>
function onboardingCandidatesList(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:150px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  var userId = '${SourceLeadWeb_userId}';
	  var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  onBoardListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  onBoardListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  onBoardListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  onBoardListDate = 'YYYY-DD-MM'; 
	  }  
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOnboardingCandidatesList?${_csrf.parameterName}=${_csrf.token}",    
            success :function(response) {
            	if (response.response === "AWKWARD") {
            	    location.href = "/errorPage";
            	} else if (response.response === "SUCCESS") {
            		$("#onboardingLoading").removeAttr("style");
               	 $("#onboardingCandidatesList").empty();
               	 var result = "";
               	 result = result +"<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
                    result = result +"<thead>"; 
                    result = result +"  <tr>"; 
                    result = result +"    <th>Candidate Name</th>";
                    result = result +"    <th>Email</th>";
                    result = result +"    <th>Title</th>";
                    result = result +"    <th>DOJ<br>(<small class='custom-small'>"+onBoardListDate+"</small>)</th>"; 
                    result = result +"    <th>Rate</th>"; 
                    result = result +"    <th>Position Type</th>";
                    result = result +"    <th>On-board Status</th>";
                    '<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\')">'
                   	 result = result +"<th>Actions</th>";
                    '</sec:authorize>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                    
                    var json = JSON.parse(response.object);
                    
                   	 for (var i = 0; i < json.length; i++) { 
                   	 var opt = json[i]; 
                   	 result = result +"<tr>";
                   		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.candidateName+"'>"+opt.candidateName+"</span></div></td>";
                   		 result = result +"<td><div><span title='"+opt.candidateEmail+"'>"+opt.candidateEmail+"</span></div></td>";
                   		 if(opt.reqManagerId == userId || opt.reqReleaseAllJobUserStatus == "Y" || loginUserRole == 'ROLE_ADMIN'){
                   		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/requirementSummary?requirementUUID="+opt.requirementUUID+"'><span title='"+opt.requirementName+"'>"+opt.requirementName+"</span></a></div></td>";
                   		 }
                   		 else{
	                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.requirementName+"'>"+opt.requirementName+"</span></div></td>";
                   		 }
                   		 result = result +"<td><div><span>"+opt.offerJoinDate+"</span></div></td>";
                   		 
		                     if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
			                     result = result +"<td><div><span title='"+opt.paymentType+" "+opt.rate+" / "+opt.rateType+"'>"+opt.paymentType+" "+opt.rate+" / "+opt.rateType+"</span></div></td>";
		                     }else{
			                     result = result +"<td><div><span>-----</span></div></td>";
		                     }
	                     
		                     if(opt.positionType != null && opt.positionType != '' && opt.positionType != 'undefined'){
		                    	 result = result +"<td><div><span title='"+opt.positionType+"'>"+opt.positionType+"</span></div></td>";
		                     }else{
			                     result = result +"<td><div><span>-----</span></div></td>";
		                     }
		                     
		                     if(opt.onboardStatus != null && opt.onboardStatus != '' && opt.onboardStatus != 'undefined'){
		                    	 if(opt.onboardStatus == "Finished"){
		                    		 result = result +"<td><div><span title='Sent'>Sent</span></div></td>";
		                    	 }else{
		                    		 result = result +"<td><div><span title='"+opt.onboardStatus+"'>"+opt.onboardStatus+"</span></div></td>";
		                    	 }
		                    	
		                     }else{
			                     result = result +"<td><div><span>Ready to onboard</span></div></td>";
		                     }
		                     
		                     '<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\')">'
			                     if(opt.onboardUUId != null && opt.onboardUUId != '' && opt.onboardUUId != 'undefined'){
			                    	 if(opt.onboardStatus == 'Draft'){
			                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/viewOnboardCandidateDetails?onboardUUID="+opt.onboardUUId+"'><span class='text-success'>View Onboard</span></a></div></td>";
			                    	 }else{
			                    		 result = result +"<td><div class='tbldata-dotdot-sm'> <a href='"+sourceleadUrl+"/viewOnboardCandidateSummary?onboardUUID="+opt.onboardUUId+"'> <span class='text-success'>View Onboard</span></i></a> ";
			                    		 if(opt.onboardStatus == 'Submitted'){
			                    			 result = result	+ '<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a><i class="fa fa-check text-success" title="Accept" onclick="acceptOnboardingCandiate(\''+opt.onboardUUId+'\');"></i></a></span>&nbsp;&nbsp;&nbsp;<span><a><i class="fa fa-close text-danger" title="Reject" onclick="rejectedReason(\''+opt.onboardUUId+'\',\''+opt.candidateName+'\',\''+opt.candidateEmail+'\');"></i></a></span>';
			                    		 }
			                    		 
			                    	 }
			                    	 result = result +"</div></td>";
			                    	
			                    }else{
			                    	result = result +"<td><div class='tbldata-dotdot-sm'><a href='"+sourceleadUrl+"/candidateOnBoardingDetails?candidateUUId="+opt.candidateUUID+"&offerUUID="+opt.offerUUID+"'><span class='text-success'>Onboard</span></a></div></td>";
			                    }
		                     '</sec:authorize>';  

	                     
	                     result = result +"</tr>";
	                     result = result +"</tr>";
                    }
                    result = result +" </tbody>";
                    result = result +"</table>"; 
                    $("#onboardingLoading").removeAttr("style");
                    $("#onboardingCandidatesList").append(result); 
                    
                    onboardingPagenation(); 
            	} else if (response.indexOf('Sign In') !== -1) {
            		location.replace(sourceleadUrl);
            	} else {
            	    showError("Operation failed, please try again");
            	}
          },
       }); 
  } 
</script> 


  <script>
  function acceptOnboardingCandiate(onboardCandidateUUID){
	  var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/acceptOnboardingCandiate?${_csrf.parameterName}=${_csrf.token}&onboardCandidateUUID="+onboardCandidateUUID, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    }); 
  }
  </script> 
  
  <script>
 function rejectedReason(onboardCandidateUUID,candidateName,candidateEmail){
	
	    var result = "";
		result = result + '	<div class="settPop">';
		result = result + '	<div id="loading"></div>';
		result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
		result = result + '<input type="hidden" name="onboardCandidateUUID" value="'+onboardCandidateUUID+'">';
		result = result + '	<h3>Onboard Rejected Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
		result = result + '	<div class="settPopInn"> ';
		
		result = result + '		<div class="clearfix">  ';
		result = result + '		<div class="col-md-12">  ';
		result = result + '		  <label class="col-md-2 paddingLeft" for="Candidate Name">Name :</label> ';
		result = result + '		  <label class="col-md-8 paddingRight">'+candidateName+'</label> ';
		result = result + '		</div>  ';

		result = result + '		<div class="col-md-12">  ';
		result = result + '		  <label class="col-md-2 paddingLeft" for="Email">Email :</label> ';
		result = result + '		  <label class="col-md-8 paddingRight">'+candidateEmail+'</label> ';
		result = result + '		</div>  ';
		
		
		result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
		result = result + '		  <div class="col-md-12 taskdesp mt10">   ';
		result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
		result = result + '			<span class="hidden"></span> </div>  ';
		result = result + '		</div>';
		result = result + '		<div class="col-md-12 mt5 clearfix"> ';
		result = result + '		<div class="pull-right">  ';
		result = result + '		  <input type="submit" value="Save" id="submit_rejectedReason"  class="btn btn-primary" >';
		result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
		result = result + '		</div>';
		result = result + '		</div>';
		result = result + '		<div class="clearfix"></div>   ';
		result = result + '	</div>  ';
		result = result + '</form>';
		result = result + '	</div>';
	    $("#rejectedReasonDiv").empty();
	    $("#rejectedReasonDiv").append(result);
	    
	    $(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	    
 }
 </script> 
 
 
 <script type="text/javascript">
$('body').on('submit', '#rejectedReason_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
     
      var frm = $('#rejectedReason_form').serializeFormJSON();
      var con = JSON.stringify(frm);
      con = con.replace(/[{}]/g, "");
      
      //It will escape all the special characters
      var jsonData = encodeURIComponent(con);
      var sourceleadUrl ='${sourceleadUrl}';
      
        $("#submit_rejectedReason").attr("disabled", "disabled");
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/rejectOnboardedCandidate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		cancelRejectedReason();
			        	location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    }); 
      return true; 
    } else {
    	return false;	
    }
  });

	$('body').on('blur', '#rejectedReason_form', function() {
		$("#rejectedReason_form").showHelpOnFocus();
		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	});
  </script>
 
 
<script>
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script> 

<script>
function onboardingPagenation(){ 
	 $('#onboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
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


<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>




<style>
.settPop .form-control {
    height: auto !important; 
}
</style>

<script> 
	$(document).ready(function(){
		
		$("#onboardCount").html('${onboardCount}');
		$("#offboardCount").html('${offboardCount}');
		
		 '<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\') && !hasRole(\'ROLE_CREATE_OFF_BOARD_USER\')">'
		       getOnboardCandidatesList();
         '</sec:authorize>';
         
         '<sec:authorize access="!hasRole(\'ROLE_CREATE_ON_BOARD_USER\') && hasRole(\'ROLE_CREATE_OFF_BOARD_USER\')">'
	       getOffboardCandidatesList();
	       document.getElementById("offboard").className = 'active'
         '</sec:authorize>';
         
         '<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\') && hasRole(\'ROLE_CREATE_OFF_BOARD_USER\')">'
	       getOnboardCandidatesList();
   		'</sec:authorize>';
         
         
	});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Onboard & Offboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo; Onboard & Offboard</div>
 </section>
 
 <div class="content-area content-new clearfix">
      <div class="onboardDashboard_counts">
        <div class="tabbable positionRelative">
          <div class="onboardcreateButtons"> 
	          
	           <sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_USER')">
		          <a href="<c:url value='/candidateOnBoardCreation'/>" class="btn btn-success btn-sm mr" title="Create onboard">
		          	<i class="fa fa-plus paddingRight"></i>Create onboard
		          </a> 
	          </sec:authorize>
	          
	          <sec:authorize access="hasRole('ROLE_CREATE_OFF_BOARD_USER')">
		          <a href="<c:url value='/candidateOffBoardCreation'/>" class="btn btn-primary btn-sm" title="Create offboard">
		          	<i class="fa fa-plus paddingRight"></i>Create offboard
		          </a>
	          </sec:authorize>
          </div>
          <ul class="nav nav-tabs" id="tab_bar">
            
            <sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_USER')">
	            <li class="active" id="onboard"> <a href="#" data-toggle="tab" class="onboardCount" onclick="getOnboardCandidatesList();" title="Current onboard count">
	              <div class="count" id="onboardCount"></div>
	              <h4>Current onboard</h4>
	              </a> 
	            </li>
            </sec:authorize>
            
            <sec:authorize access="hasRole('ROLE_CREATE_OFF_BOARD_USER')">
	            <li id="offboard"> <a href="#documents" data-toggle="tab" class="offboardCount" onclick="getOffboardCandidatesList();" title="Current offboard count">
	              <div class="count" id="offboardCount"></div>
	              <h4>Current offboard</h4>
	              </a> 
	            </li>
            </sec:authorize>
          </ul>
          </div>
          </div>
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter">
	          <div id="onboardingLoading"></div>
	          <div id="onboardingCandidatesList" class="datatable_filter"></div>
	          <div id="offboardingCandidatesList" class="datatable_filter"></div>
	          
	          <div class="vendor_wrap_mask"></div>
	          <div id="rejectedReasonDiv"></div>
          </div>
        
      </div>
    </div>
 </section>
 
 
 <script>
 function getOnboardCandidatesList(){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  candOnBoardListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  candOnBoardListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  candOnBoardListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  candOnBoardListDate = 'YYYY-DD-MM'; 
	  }
	  
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOnboardCandidateList?${_csrf.parameterName}=${_csrf.token}&locationCode="+locationCode,    
            success :function(data) {
               		
			    	     $("#onboardingLoading").removeAttr("style");
	                	 $("#onboardingCandidatesList").empty();
	                	 $("#offboardingCandidatesList").empty();
	                	 
	                	 var result = "";
	                	 result = result +"<table id='onboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Candidate Name</th>";
	                     result = result +"    <th>Type</th>";
	                     result = result +"    <th>Date Of Join<br>(<small class='custom-small'>"+candOnBoardListDate+"</small>)</th>";
	                     result = result +"    <th>Onboard<br/>Documents</th>";
	                     result = result +"    <th>Onboard Status</th>"; 
	                     result = result +"    <th>Actions</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     
	                    	 for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 var joiningDate = '';
	                    	 
	                    	 if(opt.joiningDateStr != null && opt.joiningDateStr != '' && opt.joiningDateStr != 'undefined'){
	                    		 joiningDate = opt.joiningDateStr;
	                    	 }else{
	                    		 joiningDate = "----";
	                    	 }
	                    	     
	                    	 result = result +"<tr>";
	                    	 
	                    	 result = result +"<td>";
	                    	 result = result +"<div class='width300px'>";
	                    	 result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.candidateName+"'>"+opt.candidateName+"</div>";
	                    	 
	                    	 '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ON_BOARD_USER\')">'
		                    	 if(opt.candidateOnBoardStatus == 'Draft'){
		                    		 result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOnboardCandidateDetails?onboardUUID="+opt.onboardCandidateUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
		                    	 }else if(opt.candidateOnBoardStatus == 'Finished' || opt.candidateOnBoardStatus == 'Submitted' || opt.candidateOnBoardStatus == 'Rejected' || opt.candidateOnBoardStatus == 'Accepted'){
		                    		 result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOnboardCandidateSummary?onboardUUID="+opt.onboardCandidateUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
		                    	 }
	                    	 '</sec:authorize>';
	                    	 
	                    	 result = result +"</td>";
	                    	 
	                    	 
                    		 result = result +"<td><div><span>Onboard</span></div></td>";
                    		 result = result +"<td><div><span>"+joiningDate+"</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.documentsAcceptedCount+" / "+opt.documentsCount+"</span></div></td>";
                    		 
                    		 if(opt.onboardSubmitted != 'Y'){
                    			 if(opt.candidateOnBoardStatus == 'Finished'){
                        			 result = result +"<td><div><span>Sent</span></div></td>";
                        		 }else{
                        			 if(opt.candidateOnBoardStatus == 'Rejected'){
                        				 result = result +"<td><div><span>"+opt.candidateOnBoardStatus+"</span></div><i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i></td>";
                        			 }else if(opt.candidateOnBoardStatus == 'Accepted'){
                            			 result = result +"<td><div>Completed</div></td>";
                            		 }else{
                        				 result = result +"<td><div><span>"+opt.candidateOnBoardStatus+"</span></div></td>";
                        			 }
                        			 
                        		 }
                    		 }else{
                    			 if(opt.candidateOnBoardStatus == 'Accepted'){
                    				 result = result +"<td><div>Completed</div></td>"
                    			 }else{
                    				 result = result +"<td><div><span>"+opt.candidateOnBoardStatus+"</span></div></td>";
                    			 }
                    			 
                    			
                    		 }
                    		 
                    		 
                    		 if(opt.candidateOnBoardStatus == 'Accepted'){
                    			 result = result +"<td><div><span class='text-success'>"+opt.candidateOnBoardStatus+"</span></div></td>";
                    		 }else  if(opt.candidateOnBoardStatus == 'Rejected'){
                    			 result = result +"<td><div><span class='text-danger'>"+opt.candidateOnBoardStatus+"</span></div></td>";
                    		 }else{
                    			 if(opt.onboardSubmitted == 'Y'){
                        			 result = result	+ '<td><div><span><a><i class="fa fa-check text-success" onclick="acceptOnboardingCandiate(\''+opt.onboardCandidateUUID+'\');"></i></a></span>&nbsp;&nbsp;&nbsp;<span><a><i class="fa fa-close text-danger" onclick="rejectedReason(\''+opt.onboardCandidateUUID+'\',\''+opt.candidateName+'\',\''+opt.userEmail+'\');"></i></a></span></div></td>';
                        		 }else{
                        			 result = result	+ '<td><div>-----</div></td>';
                        		 }
                    		 }
                    		
                    
		                     
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#onboardingLoading").removeAttr("style");
	                     $("#onboardingCandidatesList").append(result); 
	                     
	                     onboardingPagenation(); 
                    
          },
            
       }); 
 }
 </script>
 
 
 <script>
 function getOffboardCandidatesList(){
	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}'
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  candOffBoardListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  candOffBoardListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  candOffBoardListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  candOffBoardListDate = 'YYYY-DD-MM'; 
	  }
	  
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getOffboardCandidateList?${_csrf.parameterName}=${_csrf.token}&locationCode="+locationCode,    
            success :function(data) {
               		
			    	     $("#onboardingLoading").removeAttr("style");
	                	 $("#onboardingCandidatesList").empty();
	                	 $("#offboardingCandidatesList").empty();
	                	 
	                	 var result = "";
	                	 result = result +"<table id='offboardingPagination' class='display' cellspacing='0' width='99.9%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Candidate Name</th>";
	                     result = result +"    <th>Type</th>";
	                     result = result +"    <th>End Date<br>(<small class='custom-small'>"+candOffBoardListDate+"</small>)</th>";
	                     result = result +"    <th>Effective Date<br>(<small class='custom-small'>"+candOffBoardListDate+"</small>)</th>";
	                     result = result +"    <th>Stage</th>"; 
	                     result = result +"    <th>Offboard Stage</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     
	                    	 for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 var joiningDate = '';
	                    	 
	                    		                    	     
	                    	 result = result +"<tr>";
	                    	 
	                    	 result = result +"<td>";
	                    	 result = result +"<div class='width300px'>";
	                    	 result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.candidateName+"'>"+opt.candidateName+"</div>";
	                    	 
	                    	 '<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_OFF_BOARD_USER\')">'
		                    	 if(opt.offboardSubmitted == 'N'){
		                    		 result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOffboardCandidateDetails?offboardUUID="+opt.offboardUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
		                    	 }else if(opt.offboardSubmitted == 'Y'){
		                    		 result = result +"<b class='pull-right proj_show'> <a href='"+sourceleadUrl+"/viewOffboardCandidateSummaryDetails?offboardUUID="+opt.offboardUUID+"'> <i class='fa fa-binoculars'></i> </a> </b> </div>";
		                    	 }
	                    	 '</sec:authorize>';
	                    	 
	                    	 result = result +"</td>";
	                    	 
	                    	 
                    		 result = result +"<td><div><span>Offboard</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.endDateStr+"</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.effectiveDateStr+"</span></div></td>";
                    		 result = result +"<td><div><span>"+opt.offBoardStatus+"</span></div></td>";
                    		 
                    		 if(opt.offboardSubmitted == 'Y'){
                    			 result = result +"<td><div><span class='text-success'>Completed</span></div></td>";
                    		 }else{
                    			 result = result +"<td><div><span class='text-warning'>Pending</span></div></td>";
                    		 }
                    
		                     
		                     result = result +"</tr>";
		                     result = result +"</tr>";
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#onboardingLoading").removeAttr("style");
	                     $("#offboardingCandidatesList").append(result); 
	                     
	                     offboardingPagenation(); 
                    
          },
            
       }); 
 }
 </script>
 
 
 <script>
function onboardingPagenation(){ 
	 $('#onboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    } );
}
</script>

<script>
function offboardingPagenation(){ 
	 $('#offboardingPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    } );
}
</script>



<script>
 function rejectedReason(onboardCandidateUUID,candidateName,candidateEmail){
	
	    var result = "";
		result = result + '	<div class="settPop">';
		result = result + '	<div id="loading"></div>';
		result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
		result = result + '<input type="hidden" name="onboardCandidateUUID" value="'+onboardCandidateUUID+'">';
		result = result + '	<h3>Onboard Reject Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
		result = result + '	<div class="settPopInn"> ';
		
		result = result + '		<div class="clearfix">  ';
		result = result + '		<div class="col-md-12">  ';
		result = result + '		  <label class="col-md-2 noPad" for="Candidate Name">Name :</label> ';
		result = result + '		  <label class="col-md-8 paddingRight">'+candidateName+'</label> ';
		result = result + '		</div>  ';

		result = result + '		<div class="col-md-12">  ';
		result = result + '		  <label class="col-md-2 noPad" for="Email">Email :</label> ';
		result = result + '		  <label class="col-md-8 paddingRight">'+candidateEmail+'</label> ';
		result = result + '		</div>  ';
		
		
		result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
		result = result + '		  <div class="col-md-12 taskdesp">   ';
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
 
 
<script>
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>
 
 
<script type="text/javascript">
$(document).ready(function() {
    $(".glyphicon-edit").click(function(){
		$(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".settPop i, .btn-default").click(function(){
		$(".settPop").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});		
	
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
		
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/rejectOnboardedCandidate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(result) {
		        	cancelRejectedReason();
		        	getOnboardCandidatesList();
		        },
		    }); 
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#rejectedReason_form', function() {
		$("#rejectedReason_form").showHelpOnFocus();
		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	});
  </script>
  
  
  
  <script>
  function acceptOnboardingCandiate(onboardCandidateUUID){
	  var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/acceptOnboardingCandiate?${_csrf.parameterName}=${_csrf.token}&onboardCandidateUUID="+onboardCandidateUUID, 
		        success: function(result) {
		        	getOnboardCandidatesList();
		        },
		    }); 
  }
  </script>
  
 <script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>
 
    
 <jsp:include page="../masterFooter.jsp" />
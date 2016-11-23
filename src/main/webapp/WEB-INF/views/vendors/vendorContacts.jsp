<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 	
<style type="text/css">
.collapse-div-adduser select{
width: 100% !important;
    padding-right: 10px !important;
    padding-left: 10px !important;
    border:1px solid #ccc !important
}
</style>
<div id="loading"></div>
<script type="text/javascript">
window.onload = function () {
	cscInit();
 };
</script>

<div id="popUp"></div>	

<script>
function vendorContacts(orgRelationID,clientLocationId){
	
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> ';  
	    result = result + '<ul>';
	    
		'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_VENDOR_CONTACT\')">';
		result = result  +'    	<a href="javascript:void(0)" onClick="addContact('+orgRelationID+','+clientLocationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Contact</a>                                                            ';
		'</sec:authorize>';
		'<sec:authorize access="!hasAnyRole(\'ROLE_CREATE_VENDOR_CONTACT\')">';
		result = result  +'    	<a href="javascript:void(0)"  class="addUser-org" ><i class=""></i></a>                                                            ';
		'</sec:authorize>';
		result = result  +'     <div id="popUpDivContact" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="vendorContactListDiv" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-2").append(result);  
	
    vendorContactList(orgRelationID,clientLocationId);
    
}
</script>

<script type="text/javascript">
	function vendorContactList(orgRelationID,vendorLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationID, 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	$("#vendorContactListDiv").empty();
	    		if(json.length != null && json.length != ''){
	    			var result = "";
	    			result = result +"<table id='vendorContactsPagination' class='display' cellspacing='0' width='100%' >"; 
		    	    result = result +'<thead>';
		    		result = result + '		<tr>';
		            result = result + '	 	<th>Name</th>';
		            result = result + '	     <th>Email</th>';
		            result = result + '	     <th>Ph. No</th>';
		            result = result + '	     <th>Owner</th>';
		            result = result + '	     <th>Status</th>';
		            result = result + '	     <th>Invitation status</th>';
		            result = result + '	     <th>Action</th>';
		            result = result + '	 </tr>';
		            result = result +'</thead>';
		            result = result + '<tbody>';
		            
			          for (var i=0; i<json.length; i++)
					  {        
				        		var opt = json[i];
				        		var selected1 = '';
								var selected2 = '';
								var selected3 = '';
								var selected4 = '';
								var selected5 = '';
				        		
				        		if(opt.spocStatus == 'Active'){
									selected1 = 'selected';
								} 
							
								if(opt.spocStatus == 'In-Active'){
										selected2 = 'selected';
									} 
								
								if(opt.spocStatus == 'New'){
										selected3 = 'selected';
									}
								
								if(opt.spocStatus == 'Negotiation'){
										selected4 = 'selected';
									} 
								
								if(opt.spocStatus == 'Contracted'){
									selected5 = 'selected';
								} 
				        		
								var spocMobilePhone;
			   		    		if(opt.spocMobilePhone != null && opt.spocMobilePhone != '' && opt.spocMobilePhone !='undefined'){
			   		    			 spocMobilePhone = opt.spocMobilePhone;
			   		    		}else{
			   		    			spocMobilePhone = "---";
			   		    		}
				        		
				        		result = result + '	 <tr>';
				                result = result + '	 	<td><div>'+opt.spocName+'</a></div></td>';
				                result = result + '	     <td><div>'+opt.spocEmail+'</div></td>';
				                result = result + '	     <td><div>'+spocMobilePhone+'</div></td>';
				                result = result + '	     <td><div>'+opt.ownerName+'</div></td>';
				                
				                result = result + ' <td><div>';
								result = result + ' <select id="spocStatus'+json[i].id+'" name="spocStatus" onChange="changeSpocStatus('+json[i].id+'\,'+vendorLocationId+');">';
								result = result + ' <option '+selected1+'>Active</option>';
								result = result + ' <option '+selected2+'>In-Active</option>';
								result = result + ' <option '+selected3+'>New</option>';
								result = result + ' <option '+selected4+'>Negotiation</option>';
								result = result + ' <option '+selected5+'>Contracted</option>';
								result = result + ' </select>';
								result = result + ' </div></td>';
				                
				                result = result +'<td data-title="Invitation Status"><div>';
				                if(opt.spocUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){
				   	        		  result = result +' <i class="fa fa-check" alt="Accept" onclick="acceptSpocInvitation('+opt.id+','+orgRelationID+','+vendorLocationId+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectSpocInvitation('+opt.id+','+orgRelationID+','+vendorLocationId+');"></i>';
				   	        		}
				                
					                if(opt.spocUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
				                        result = result +' ---';
				                  	}
				   	        		
				   	        		if(opt.ownerUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
				   	        			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendSpocInvitation('+opt.spocUserId+','+orgRelationID+','+vendorLocationId+','+opt.id+');">Send</a></b>';
				   	        		}
				   	        		if(opt.spocUserId != "${SourceLeadWeb_userId}" && opt.ownerUserId != "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
				   	        			result = result +' ---';
				   	        		}
				   	        		
				   	        		if(opt.spocUserId != "${SourceLeadWeb_userId}" && opt.ownerUserId != "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){ 
				   	        			result = result +' <b class="proj_process">Received</b>';
				   	        		}
				   	        		
				   	        		if(opt.ownerUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){
				   	        			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendSpocInvitation('+opt.spocUserId+','+orgRelationID+','+vendorLocationId+','+opt.id+');">Resend</a></b>';
				   	        		}
				   	        		
				   	        		if(opt.invitationStatus == 'Accepted'){ 
				   	        			result = result +' <b class="proj_active">'+opt.invitationStatus+'</b>';
				   	        		}
				             		
				             		if(opt.invitationStatus == 'Rejected'){
				             			result = result +' <b class="proj_closed">'+opt.invitationStatus+'</b>';
				             		}
			   		    		result = result +'</div></td>';
				                
				                if(json[i].status != null && json[i].status != '' && json[i].status !='undefined' && json[i].status != "INACTIVE"){
				                result = result + '	    <td><div>';
                                       '<sec:authorize access="hasAnyRole(\"ROLE_DELETE_VENDOR_CONTACT\")">';
                                        result = result + '<i class="glyphicon glyphicon-trash" onClick="terminateVendorSpoc('+json[i].id+'\,\''+orgRelationID+'\','+vendorLocationId+')";></i>';
                                        '</sec:authorize>';
                	                    result = result +'</div></td>';
			                     }
			                     else{
			                          result = result + '<td><div><font color="red">Terminated</font></div></td>';
			                     }
				                result = result + '	 </tr>';
						}   
			        	result = result + '	     </tbody>';
			        	result = result +"</table>";
			    	    
			    	    result = result + '<div class="project_inn_mask"></div>';
					    result = result + '<div class="project_error">';
					    result = result + '	<p>User delete message here ....</p>';
					    result = result + '	<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> ';
					    result = result + '</div>';
					    $("#vendorContactListDiv").append(result);
			    	    contactPagination();
			    	    
	        }else{
	        	var result ="";
	    	     result = result+"   <div class='row'>"; 
				 result = result+"	<div class='col-lg-12'>"; 
				 result = result+"		<div class='content-area clearfix'>"; 
				 result = result+"			<div class='blank-pages'>";
				 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
				 result = result+"			        <p>No Contacts Found</p>";
				 result = result+"			 </div>";
				 result = result+"		</div>"; 
				 result = result+"	</div>"; 
				 result = result+"  </div> "; 
   			$("#vendorContactListDiv").empty();
		    $("#vendorContactListDiv").append(result); 
   		 }
	        },
	    });
	}
</script>
<!--  vendor details ends -->
<script>
function changeSpocStatus(spocId,vendorLocationId){
	var spocStatus = $('#spocStatus'+spocId).val();
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	vendorContactList(orgRelationID,vendorLocationId);
	    }
   });
}
</script>
<script>
function terminateVendorSpoc(spocId, orgRelationID,vendorLocationId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>User delete message here</p>";
	result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReasonForVendorSpocTermination("+spocId+","+orgRelationID+","+vendorLocationId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReasonForVendorSpocTermination(spocId, orgRelationID,vendorLocationId){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForSpoc").value;
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#reasonForSpocDiv").empty();
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateSpoc?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&reason="+reason,	    	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   			    vendorContactList(orgRelationID,vendorLocationId);
		    }
	   });
	}else{
		if(reason){
			$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
	  
}
</script>
<script>
function cancelTermination(){
$("#popUp").empty();
$(".project_inn_mask").fadeOut(200);
}
</script>

<!-- This script is for resending the invitation mail -->
<script type="text/javascript">
function resendSpocInvitation(spocUserId,orgRelationID,vendorLocationId,spocId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(spocUserId != null && spocUserId != ""){
	//	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendSpocInvitationEmail?${_csrf.parameterName}=${_csrf.token}&spocUserId="+spocUserId+"&orgSpocId="+spocId+"&responderType=Vendor&orgRelationID="+orgRelationID, 
			        success: function(result) {
			        	//$("#loading").removeAttr("style"); 
			        	vendorContactList(orgRelationID,vendorLocationId);
			        },
			    });
		 }
	}
  
</script>

<script>
function contactPagination(){  
    $('#vendorContactsPagination').DataTable( { 
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

 <!-- This Script is For Popup Div Starts-->  
<script>
function addContact(orgRelationID,clientLocationId) {	
	var result = "";
	result = result  +'	<form id="clinetContact_form" name="clinetContact_form" method="post">';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closePopupDiv();"></i> ';
	result = result  +'	<div class="form-horizontal clearfix">';
	result = result  +'                  </div>  ';      
	
	result = result  +'	 <div class="clearfix mb10" id="EmailIdDiv">';
	result = result  +'	  <div class="col-md-12">';
	result = result  +'	    <input type="text" class="form-control input-sm" name="email" id="EmailId" value=""  onblur=" validateEmail('+orgRelationID+'); getSpocUserDetails('+clientLocationId+','+orgRelationID+');" field-name="Email" placeholder="Email Id *" data-validation="required">';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	<input type="text" class="form-control input-sm" value="" id="firstName" name="firstName" field-name="First name" placeholder="First name *" data-validation="required">';
	result = result  +'	</div>';
	result = result  +'	</div>';
	
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	  <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm"  id="lastName" name="lastName" value="" placeholder="Last name">';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	   <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm" value=""  id="phoneNumber"  name="phoneNumber" placeholder="Phone number">';
	result = result  +'	    </div>';
	result = result  +'	  </div>';
	
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	 <label class="col-md-12">Status</label>';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	  <select class="form-control" id="spocStatus" name="spocStatus">';
	result = result  +'	   <option value="Active">Active</option>';
	result = result  +'	   <option value="In-Active">In-Active</option>';
	result = result  +'	   <option value="New">New</option>';
	result = result  +'	   <option value="Negotiation">Negotiation</option>';
	result = result  +'	   <option value="Contracted">Contracted</option>';
	result = result  +'	  </select>';
	result = result  +'	 </div>';
	result = result  +'	 </div>';
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	 <label class="col-md-12">Owner<span class="text-danger">&nbsp;*</span></label>';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	   <select multiple class="form-contorl chosen-select" name="owner" id="owner" field-name="Owner" data-validation="required">';
	result = result  +'	       <c:forEach var="user" items="${userList}">';
	result = result  +'	           <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ''} >${user.firstName} ${user.lastName}</option>';
	result = result  +'	       </c:forEach>';
    result = result  +'	  </select>';
    result = result  +'	 </div>';
    result = result  +'	 </div>';
	
	
	result = result  +'	 <div class="clearfix mb10" id="roleDiv" style="display: block;">';
	result = result  +'	 <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	       <select class="form-control" id="role" name="roleType" field-name="Role" data-validation="required">';
	result = result  +'	                    <option value="">-Select Role-</option>';
	result = result  +'	 					<option value="1">Admin</option>';
	result = result  +'	 					<option value="5">HR Manager</option>';
	result = result  +'	 					<option value="6">Account Manager</option>';
	result = result  +'	 					<option value="8">Recruiter</option>';
	result = result  +'	        </select>';
	result = result  +'	 </div>';
	result = result  +'	 </div>';
	
	result = result  +'<div class="clearfix mb10" id="userRoleDiv" style="display: none;">';
	result = result  +'<label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>';
	result = result  +'<div class="col-md-12">';
	result = result  +'<input type="text" class="form-control" id="userRoleText"  readonly="readonly">';
	result = result  +'<input type="hidden" id="userRole" name="roleTypeId" value="" />';
	result = result  +'</div>';
	result = result  +'</div>';
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	   <div class="col-md-12 checkbox">';
	result = result  +'	     <label>';
	result = result  +'	      <input type="checkbox" value="Y" name="isMailSent" checked="checked">Send Invitation</label>';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	  <div class="clearfix mr">';
	result = result  +'	   <input type="button" id ="addClientContact" value="Add" onclick="check('+orgRelationID+','+clientLocationId+');" class="btn btn-sm btn-primary pull-right"/>';
	result = result  +'	  </div>';
	
	result = result  +'	 </div>';
	result = result  +'	 </div>';
	result = result  +'	        </form> ';
	$("#popUpDivContact").empty();
	$("#popUpDivContact").append(result);
	
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   });	
	 
	 var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
	 
	}
</script>

<script>
function closePopupDiv(){
	 $("#popUpDivContact").removeClass("edit-details-active");
}
</script>

<!-- <script>
$('html').click(function(){
    $(".edit-details").removeClass("edit-details-active");
   });
</script> -->
 <!-- This Script is For Popup Div Ends --> 
 <!-- to add new contact -->
<script type="text/javascript">
	function check(orgRelationId,vendorLocationId) {
		if ($('#clinetContact_form').validate(false, validationSettings)) {
			var frm = $('#clinetContact_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#addClientContact").attr("disabled", "disabled");
			$("#addClientContact").addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveClinentAndVendorContact?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&orgRelationId="+orgRelationId+"&clientLocationId="+vendorLocationId+"&responderType=Vendor",
						success : function(result) {
							$("#popUpDivContact").removeClass("edit-details-active");
							vendorContactList(orgRelationId,vendorLocationId);
						},
			});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#clinetContact_form', function() {
		$("#clinetContact_form").showHelpOnFocus();
		$("#clinetContact_form").validateOnBlur(false, validationSettings);
	}); 
</script>
<!-- to add new contact --> 
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 


<!-- <script type="text/javascript">
 function validateSpocEmail(orgRelationId) {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $("#addClientContact").attr("disabled", "disabled");
         $("#addClientContact" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateSpocEmailId?${_csrf.parameterName}=${_csrf.token}&email="+EmailId+"&orgRelationId="+orgRelationId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#addClientContact" ).addClass('button').val('Save');
                         $("#addClientContact").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script> -->
 
 
 <script type="text/javascript">
function getSpocUserDetails(orgLocationId,orgRelationID){
	getOwners(orgRelationID);
	var email = document.getElementById("EmailId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSpocUserDetails?${_csrf.parameterName}=${_csrf.token}&email="+email+"&orgLocationId="+orgLocationId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("disabled", true);
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").attr("disabled", false);
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("disabled", true);
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").attr("disabled", false);
	        			    $("#lastName").val('');
	        		 }
		        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#phoneNumber").attr("disabled", true);
		        		    $("#phoneNumber").val(json[2]);
	        		 }else{
	        			    $("#phoneNumber").attr("disabled", false);
	        			    $("#phoneNumber").val('');
	        		 }
	       
	        		 if(json[3] != null && json[3] != ''){
	        			$("#role").removeAttr("data-validation");
	        			$("#role" ).val('');
        			    document.getElementById("roleDiv").style.display="none";
   	        			document.getElementById("userRoleDiv").style.display="";
   	        			$("#userRole").val(json[3]);
   	        			$("#userRoleText").val(json[4]);
	        		 }else{
	        			 $("#role").attr("data-validation", "required");
	        			 $("#userRole" ).val('');
	        			 document.getElementById("roleDiv").style.display="";
	        			 document.getElementById("userRoleDiv").style.display="none";
	        		 }
			      	 
	        	}
			 },
	        error: function(e){
	            alert('Ajax Error: ' + e.value);
	        }
	    });
	}else{
		$("#firstName").attr("disabled", false);
	    $("#firstName").val('');
	    
	    $("#lastName").attr("disabled", false);
	    $("#lastName").val('');
	    
	    $("#phoneNumber").attr("disabled", false);
	    $("#phoneNumber").val('');
	    
	    $("#role").attr("data-validation", "required");
	    $("#userRole" ).val('');
	    document.getElementById("roleDiv").style.display="";
		document.getElementById("userRoleDiv").style.display="none";
	   
	} 
	
}
</script>

<script>
function acceptSpocInvitation(spocId,orgRelationId,clientLocationId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveInvitationStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&invitationStaus=Accepted",	    	   
		    success: function(data) {
		    	vendorContactList(orgRelationId,vendorLocationId);
		    }
	   });
}
</script>

<script>
function rejectSpocInvitation(spocId,orgRelationId,clientLocationId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveInvitationStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&invitationStaus=Rejected",
		    success: function(data) {
		    	vendorContactList(orgRelationId,vendorLocationId);
		    }
	   });
}
</script>

<script type="text/javascript">
 function getOwners(orgRelationID) {
	 var email = document.getElementById("EmailId").value; 
	 var orgRelationId = orgRelationID;
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(email != null && email != "" && email != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllOwners?${_csrf.parameterName}=${_csrf.token}&email="+email+"&orgRelationId="+orgRelationId, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(i == 0){
			        			output = output + "<option value='" +opt.id+ "' selected>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}
			        		
					}
		        	$("#owner").empty();
			        $("#owner").append(output);
			        $('#owner').trigger("chosen:updated");
		        },
		    });
	 }
	
 }
</script>

<script type="text/javascript">
function validateEmail(orgRelationID){
	 getOwners(orgRelationID);
	var email = document.getElementById("EmailId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(email != null){
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/validateClientOrVendorEmail?${_csrf.parameterName}=${_csrf.token}&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#EmailId").after( "<p class='jquery_form_error_message'>" + email + " should not your org users email</span>");
					document.getElementById("EmailId").setAttribute( 'record-exist', 'yes');
					document.getElementById("EmailId").setAttribute( 'record-exist-errorMsg', email +"  should not your org users email");
					
				}else if(result == 'Valid Email') {
					$('input#EmailId').removeAttr("class record-exist record-exist-errormsg");
					$('input#EmailId').attr('class','valid form-control');
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					
				}
			},

		});
	}
}
</script>

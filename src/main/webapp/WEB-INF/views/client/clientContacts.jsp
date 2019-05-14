<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
function clientContacts(orgRelationID,clientLocationId){
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result  = result + '<div class="success_pop" id="clientTerminateMsgDiv" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="clientTerminateMsg"></span>';
    result  = result + '</div>';
	result  = result + '   <div id="clientMsgDiv" ></div>';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> ';  
	    result = result + '<ul>';
	    '<sec:authorize access="hasAnyRole(\'ROLE_CREATE_CLIENT_CONTACT\')">';
		result = result  +'    	<a href="javascript:void(0)" onClick="addContact('+orgRelationID+','+clientLocationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Contact</a>                                                            ';
		'</sec:authorize>';
		result = result  +'     <div id="popUpDivContact" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="clientContactListDiv" class=""></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();
    $("#tab-5").empty(); 
    $("#tab-2").empty(); 
    $("#tab-2").append(result);  
	
    clientContactList(orgRelationID,clientLocationId);
    
}
</script>
				

<!-- client details View starts-->
<script type="text/javascript">
	function clientContactList(orgRelationID,clientLocationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Client", 
	        success: function(json) {
	      
    			$("#clientContactListDiv").empty();
				var result = "";
				result = result +"<table id='clientContactsPagination' class='display' cellspacing='0' width='100%' >"; 
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result + '<th>Name</th>';
	            result = result + '<th>Email</th>';
	            result = result + '<th>Ph. No</th>';
	            result = result + '<th>Owner</th>';
	            result = result + '<th>Status</th>';
	            result = result + '<th>Invitation status</th>';
	            result = result + '<th>Action</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	for (var i=0; i<json.data.length; i++)
				  {	        		
	        		var opt = json.data[i];
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
	                result = result + '	 	<td><div><span title='+opt.spocName+'>'+opt.spocName+'</span></a></div></td>';
	                result = result + '	     <td><div><span title='+opt.spocEmail+'>'+opt.spocEmail+'</span></div></td>';
	                result = result + '	     <td><div>'+spocMobilePhone+'</div></td>';
	                result = result + '	     <td><div><span title='+opt.ownerName+'>'+opt.ownerName+'</span></div></td>';
	                
	                result = result + ' <td><div>';
	                
	                if(opt.invitationStatus == 'Rejected'){
	        			result = result + '<span>'+opt.spocStatus+'</span>';
	        		}else{
	        			result = result + ' <select id="spocStatus'+json.data[i].id+'" name="spocStatus" onChange="changeSpocStatus('+json.data[i].id+'\,'+clientLocationId+');">';
						result = result + ' <option value="Active"  '+selected1+'>Active</option>';
						result = result + ' <option value="In-Active"    '+selected2+'>In-Active</option>';
						/* result = result + ' <option value="New" '+selected3+'>New</option>';
						result = result + ' <option value="Negotiation" '+selected4+'>Negotiation</option>';
						result = result + ' <option value="Contracted" '+selected5+'>Contracted</option>'; */
						result = result + ' </select>';
	        		}
					
					result = result + ' </div></td>';
	                
	                result = result +'<td data-title="Invitation Status"><div>';
	                if(opt.spocUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){
	   	        		  result = result +' <i class="fa fa-check" alt="Accept" onclick="acceptSpocInvitation('+opt.id+','+orgRelationID+','+clientLocationId+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectSpocInvitation('+opt.id+','+orgRelationID+','+clientLocationId+');"></i>';
	   	        		}
	                
		                if(opt.spocUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
	                        result = result +' ---';
	                  	}
	   	        		
	   	        		if(opt.ownerUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
	   	        			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendSpocInvitation('+opt.spocUserId+','+orgRelationID+','+clientLocationId+','+opt.id+');">Send</a></b>';
	   	        		}
	   	        		
	   	        		if(opt.spocUserId != "${SourceLeadWeb_userId}" && opt.ownerUserId != "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
	   	        			result = result +' ---';
	   	        		}
	   	        		
	   	        		if(opt.spocUserId != "${SourceLeadWeb_userId}" && opt.ownerUserId != "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){ 
	   	        			result = result +' <b class="proj_process">Received</b>';
	   	        		}
	   	        		
	   	        		if(opt.ownerUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){
	   	        			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendSpocInvitation('+opt.spocUserId+','+orgRelationID+','+clientLocationId+','+opt.id+');">Resend</a></b>';
	   	        		}
	   	        		
	   	        		if(opt.invitationStatus == 'Accepted'){ 
	   	        			result = result +' <b class="proj_active">'+opt.invitationStatus+'</b>';
	   	        		}
	             		
	             		if(opt.invitationStatus == 'Rejected'){
	             			result = result +' <b class="proj_closed">'+opt.invitationStatus+'</b>';
	             		}
   		    		result = result +'</div></td>';
	                
   		    		'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CLIENT_CONTACT\')">';
	                if(json.data[i].status != null && json.data[i].status != '' && json.data[i].status !='undefined' && json.data[i].status != "INACTIVE"){
	                result = result + '	    <td><div>';
	                                        result = result + '<i class="glyphicon glyphicon-trash" onClick="terminateClientSpoc('+json.data[i].id+'\,\''+orgRelationID+'\',\''+clientLocationId+'\',\''+opt.spocName+'\')"; title = "Remove client contact"></i>';
	                	                    result = result +'</div></td>';
                     }
                     else{
                          result = result + '<td><div><font color="red">Terminated</font></div></td>';
                     }
	                '</sec:authorize>';
	                
	                
	                '<sec:authorize access="!hasAnyRole(\'ROLE_DELETE_CLIENT_CONTACT\')">';
	                if(json.data[i].status != null && json.data[i].status != '' && json.data[i].status !='undefined' && json.data[i].status != "INACTIVE"){
	                						result = result + '	    <td><div>';
	                                        result = result + '--';
	                	                    result = result +'</div></td>';
                     }
	                '</sec:authorize>';
	                result = result + '	 </tr>';
				} 
	        		
	        	
	        	result = result +'</tbody>';
	        	result = result +"</table>";
	        	
	        	result = result + '<div class="project_inn_mask"></div>';
			    result = result + '<div class="project_error">';
			    result = result + '	<p>User delete message here ....</p>';
			    result = result + '	<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> ';
			    result = result + '</div>';
			    $("#clientContactListDiv").append(result); 
	    	    clientMsgFadein();
 				contactPagination();
    /* 		$("#tab-4").empty();  
    		$("#tab-2-addContact").empty();  
    	    $("#tab-2-addContact").append(res);
    		$("#tab-2").empty();  
    	    $("#tab-2").append(result);  
    	    $("#testDiv").show();
    	    
    	    $(".project-tabs-menu a").click(
					function(event) {
						event.preventDefault();
						$(this).parent().addClass("current");
						$(this).parent().siblings().removeClass(
								"current");
						var tab = $(this).attr("href");
						$(".project_tabs_inn").not(tab).css(
								"display", "none");
						$(tab).fadeIn();
					});
    	    contactPagination(); */
	        },
	    });
	}
</script>
<!--  client details ends -->

<script>
function contactPagination(){ 
    $('#clientContactsPagination').DataTable( {
        "scrollCollapse": false,
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "bLengthChange": true,
        "bDestroy": true,
        "ordering": true,
        "aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': [5,6]
			}],
        "oLanguage": {
             "sSearch": "",
             "sZeroRecords": "", "sEmptyTable": ""
         }
       
    });
    $('#clientContactsPagination_wrapper').wrap("<div class='datatable_filter project_tabs_inn_table vendor_wrap_inn_table'></div>");
    $('#clientContactsPagination_filter').attr("style", "position:relative !important; top:0px !important; margin-top:10px !important;");
}
</script>

<div id="popUp"></div>

 <script>
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	
</script> 

<script>
function cancelTerminationResonforClientSpoc(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
	$("#reasonForSpoc").val('');
	$("#reasonForSpocDiv").find("p.jquery_form_error_message").remove();
	$("#reasonForSpocDiv").empty(); 
}
</script>

<script>
function terminateClientSpoc(spocId, orgRelationID,clientLocationId,spocName){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>User delete message here</p>";
	result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Terminate Reason' onkeypress='clientRemoveSpacesForRejectReason();' ></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveSpocTerminateReason("+spocId+","+orgRelationID+","+clientLocationId+",\""+spocName+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTerminationResonforClientSpoc();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
<script>
   function clientRemoveSpacesForRejectReason() {
	    var output = document.getElementById("reasonForSpoc").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForSpoc").removeAttr("data-validation");
	    }
	}
</script>
<script>
function saveSpocTerminateReason(spocId, orgRelationID,clientLocationId,spocName){
	var sourceleadUrl = '${sourceleadUrl}';
	var userId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var reason = document.getElementById("reasonForSpoc").value;
	 reason = reason.replace(/\s/g, "") 
	var reason1 = document.getElementById("reasonForSpoc").value;
	
	if(reason.match(/^[^\s-](.*)*$/) != null){
	      
		  $("#reasonForSpocDiv").empty();
		  var formData = new FormData();
		     formData.append("tokenParamName", tokenValue);
		     formData.append("spocId", spocId);
		     formData.append("reason", reason);
		     formData.append("userId", userId);
		     formData.append("orgLocationId", orgLocationId);
		     formData.append("responderType", "Client");
		     
		$.ajax({
			 type : "POST",
          data: formData,
          processData: false,
          contentType:false, 
     	  url: sourceleadUrl+"/terminateSpoc",
// 		    url: sourceleadUrl+"/terminateSpoc?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&reason="+reason+"&userId="+userId+"&orgLocationId="+orgLocationId+"&responderType=Client",	    	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
	   			$("#clientTerminateMsgDiv").removeAttr("style");
	 			$("#clientTerminateMsgDiv").css({ display: "block" });
	 			$("#clientTerminateMsg").html(""+spocName+" Removed");
		    	clientContactList(orgRelationID,clientLocationId);
		    	clientTerminateMsgFadein();
		    }
	   });
	}else{
		
		if(reason.length == 0 && reason1){
			 $("#reasonForSpocDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			 $("#reasonForSpocDiv").find('p.jquery_form_error_message').remove();
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


<script>
function changeSpocStatus(spocId,clientLocationId){
	var spocStatus = $('#spocStatus'+spocId).val();
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	clientContactList(orgRelationID,clientLocationId);
	    }
   });
}
</script>

<!-- This script is for resending the invitation mail -->
<script type="text/javascript">
function resendSpocInvitation(spocUserId,orgRelationID,clientLocationId,spocId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(spocUserId != null && spocUserId != ""){
		//$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendSpocInvitationEmail?"+tokenParamName+"="+tokenValue+"&spocUserId="+spocUserId+"&orgSpocId="+spocId+"&responderType=Client&orgRelationID="+orgRelationID, 
			        success: function(result) {
			        	clientContactList(orgRelationID,clientLocationId)
			        },
			    });
		 }
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
	
	result = result  +'	 <div class="clearfix mb" id="EmailIdDiv">';
	result = result  +'	  <div class="col-md-12">';
	result = result + '<div class="input-group errorInptgroup">';
	result = result  +'<input type="text" class="form-control input-sm" id="EmailId" name="email"  value="" field-name="Email" placeholder="Email Id *" onblur="validateEmail1('+orgRelationID+'); getSpocUserDetails('+clientLocationId+','+orgRelationID+');" data-validation="required validate_Space validate_length length2-100 validate_email">';
	
	result = result + ' <span id="emailStatusIconDiv" class="input-group-addon">';
	result = result + '<i class="fa fa-check text-muted" style="color:#f6f6f6">';
	result = result + '</i>';
	result = result + '</span>';
	result = result  +'	   </div>';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	<div class="clearfix mb mt">';
	result = result  +'	 <div class="col-md-12 mt2">';
	result = result  +'	<input type="text" class="form-control input-sm" value="" id="firstName" name="firstName" field-name="First name" placeholder="First name *" data-validation="required validate_Space validate_length length1-100">';
	result = result  +'	</div>';
	result = result  +'	</div>';
	
	result = result  +'	<div class="clearfix mb">';
	result = result  +'	  <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm" name="lastName" id="lastName" value="" placeholder="Last name" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	
	result = result  +'	 <div class="clearfix mb">';
	result = result  +'	   <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm" value="" name="phoneNumber" id="phoneNumber" placeholder="Phone number" data-validation="validate_Space validate_phone validate_length length9-20" data-validation-optional="true">';
	result = result  +'	    </div>';
	result = result  +'	  </div>';
	
	
	result = result  +'	 <div class="clearfix mb">';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	  <select class="width100percnt form-control" id="spocStatus" name="spocStatus">';
	result = result  +'	   <option value="Active">Active</option>';
	result = result  +'	   <option value="In-Active">In-Active</option>';
	/* result = result  +'	   <option value="New">New</option>';
	result = result  +'	   <option value="Negotiation">Negotiation</option>';
	result = result  +'	   <option value="Contracted">Contracted</option>'; */
	result = result  +'	  </select>';
	result = result  +'	 </div>';
	result = result  +'	 </div>';
	
	result = result  +'	 <div class="clearfix">';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	   <select multiple class="form-contorl chosen-select" name="owner" id="owner" field-name="Owner" data-validation="required">';
	result = result  +'	       <c:forEach var="user" items="${userList}">';
	result = result  +'	           <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ''} >${user.firstName} ${user.lastName}</option>';
	result = result  +'	       </c:forEach>';
    result = result  +'	  </select>';
    result = result  +'	 </div>';
    result = result  +'	 </div>';
	
	
	/* result = result  +'	 <div class="clearfix mb10" id="roleDiv" style="display: block;" >';
	result = result  +'	 <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	       <select class="form-control" id="role" name="roleType" field-name="Role" data-validation="required">';
	result = result  +'	                    <option value="">-Select Role-</option>';
	result = result  +'	 					<option value="1">Admin</option>';
	result = result  +'	 					<option value="3">Approver/Project Manager</option>';
	result = result  +'	 					<option value="5">HR Manager</option>';
	result = result  +'	 					<option value="8">Recruiter</option>';
	result = result  +'	        </select>';
	result = result  +'	 </div>';
	result = result  +'	 </div>'; */
	
	
	result = result  +'<div class="clearfix mb10" id="userRoleDiv" style="display: none;">';
	result = result  +'<label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>';
	result = result  +'<div class="col-md-12">';
	result = result  +'<input type="text" class="form-control" id="userRoleText"  readonly="readonly">';
	result = result  +'<input type="hidden" id="userRole" name="roleTypeId" value="" />';
	result = result  +'</div>';
	result = result  +'</div>';
	
	
	result = result  +'	 <div class="clearfix">';
	result = result  +'	   <div class="col-md-6 checkbox margin-top0">';
	result = result  +'	     <label>';
	result = result  +'	      <input type="checkbox" value="Y" name="isMailSent">Send Invitation</label>';
	result = result  +'	   </div>';
	
	result = result  +'	   <div class="col-md-6 checkbox margin-top0">';
	result = result  +'	     <label>';
	result = result  +'	      <input type="checkbox" value="N" name="apOrArSent" id="apOrArSent" onclick = "allowApEmailSent();"> AP Email</label>';
	result = result  +'	   </div>';
	
	result = result  +'	   <div class="col-md-6 checkbox margin-top0">';
	result = result  +'	     <label>';
	result = result  +'	      <input type="checkbox" value="N" name="invoiceOrBillSent" id= "invoiceOrBillSent" onclick = "allowInvoiceEmailSent();"> Invoice Email</label>';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	  <div class="clearfix mr">';
	result = result  +'	   <input type="button" id ="addClientContact" value="Add" onclick="checkClientContactAdd('+orgRelationID+','+clientLocationId+');" class="btn btn-sm btn-primary pull-right"/>';
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
				 $("#owner_chosen").find("ul.chosen-choices > li input").attr("placeholder", "Select Owners *");
	}
	
</script>
 <!-- This Script is For Popup Div Ends --> 
 
 <script>
function closePopupDiv(){
	 $("#popUpDivContact").removeClass("edit-details-active");
}

function allowApEmailSent(){
	var checked = document.getElementById("apOrArSent").checked
	if(checked == true){
		$("#apOrArSent").val("Y");
	}else{
		$("#apOrArSent").val("N");
	}
}


function allowInvoiceEmailSent(){
	var checked = document.getElementById("invoiceOrBillSent").checked
	if(checked == true){
		$("#invoiceOrBillSent").val("Y");
	}else{
		$("#invoiceOrBillSent").val("N");
	}
}

</script>

<!-- to add new contact -->
<script type="text/javascript">
	function checkClientContactAdd(orgRelationId,clientLocationId) {
		if ($('#clinetContact_form').validate(false, validationSettings)) {
	  	        var formData = $($('#clinetContact_form')[0]).serialize();
			var sourceleadUrl = '${sourceleadUrl}';
			$("#addClientContact").attr("disabled", "disabled");
			$("#addClientContact").addClass('button').val('Processing..');
			
			$.post(sourceleadUrl+"/saveClinentAndVendorContact?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&clientLocationId="+clientLocationId+"&responderType=Client", formData, function(data) {
				$("#popUpDivContact").removeClass("edit-details-active");
				 $("#clientTerminateMsgDiv").removeAttr("style");
		 			$("#clientTerminateMsgDiv").css({ display: "block" });
		 			$("#clientTerminateMsg").html("Success!! New Contact Added");
				clientContactList(orgRelationId,clientLocationId);
				 clientTerminateMsgFadein();
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
 function validateSpocEmail(orgRelationId) {
	 
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $("#addClientContact").attr("disabled", "disabled");
         $("#addClientContact" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateSpocEmailId?"+tokenParamName+"="+tokenValue+"&email="+EmailId+"&orgRelationId="+orgRelationId, 
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
 </script>
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 


<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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



<script type="text/javascript">
function getSpocUserDetails(orgLocationId,orgRelationID){
	getOwners(orgRelationID);
	var email = document.getElementById("EmailId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSpocUserDetails?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgLocationId="+orgLocationId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("readOnly", true);
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").attr("readOnly", false);
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("readOnly", true);
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").attr("readOnly", false);
	        			    $("#lastName").val('');
	        		 }
		        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#phoneNumber").attr("readOnly", true);
		        		    $("#phoneNumber").val(json[2]);
	        		 }else{
	        			    $("#phoneNumber").attr("readOnly", false);
	        			    $("#phoneNumber").val('');
	        		 }
	        		 
	        		 if(json[3] != null && json[3] != ''){
	        			/* $("#role").removeAttr("data-validation");
	        			$("#role" ).val('');
        			    document.getElementById("roleDiv").style.display="none"; */
   	        			document.getElementById("userRoleDiv").style.display="";
   	        			$("#userRole").val(json[3]);
   	        			$("#userRoleText").val(json[4]);
	        		 }else{
// 	        			 $("#role").attr("data-validation", "required");
	        			 $("#userRole" ).val('');
// 	        			 document.getElementById("roleDiv").style.display="";
	        			 document.getElementById("userRoleDiv").style.display="none";
	        		 }
			      	 
	        	}
			 },
	        error: function(e){
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
		    url: sourceleadUrl+"/saveInvitationStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&invitationStaus=Accepted",	    	   
		    success: function(data) {
		    	$("#clientTerminateMsgDiv").removeAttr("style");
	 			$("#clientTerminateMsgDiv").css({ display: "block" });
	 			$("#clientTerminateMsg").html("Invitation Accepted");
	 			clientContactList(orgRelationId,clientLocationId);
		    	clientTerminateMsgFadein();
		    }
	   });
}
</script>

<script>
function rejectSpocInvitation(spocId,orgRelationId,clientLocationId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveInvitationStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&invitationStaus=Rejected",
		    success: function(data) {
		    	$("#clientTerminateMsgDiv").removeAttr("style");
	 			$("#clientTerminateMsgDiv").css({ display: "block" });
	 			$("#clientTerminateMsg").html("Invitation Rejected");
	 			clientContactList(orgRelationId,clientLocationId);
		    	clientTerminateMsgFadein();
		    }
	   });
}
</script>


<script type="text/javascript">
 function getOwners(orgRelationID) {
	 var email = document.getElementById("EmailId").value; 
	 var orgRelationId = orgRelationID;
	 var sourceleadUrl ='${sourceleadUrl}';
	 var SourceLeadWeb_userId= '${SourceLeadWeb_userId}';
	 if(email != null && email != "" && email != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllOwners?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgRelationId="+orgRelationId+"&type=client",  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	var flag=false;
		        	for (var i=0; i<json.length; i++)
	        		{
	        		var check = json[i];
		        		if(check.id == SourceLeadWeb_userId){
		        			flag=true;
		        		}
	        		}
		        	if(flag){
				        	for (var i=0; i<json.length; i++)
							{
					        	var opt = json[i];
					        	
					        		if(opt.id == SourceLeadWeb_userId){
					        			output = output + "<option value='" +opt.id+ "' selected>"+opt.firstName+" "+opt.lastName+"</option>";
					        		}
					        		else{
					        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
					        		}
			                }
		        	}
		        	if(!flag){
	                	for (var j=0; j<json.length; j++)
						{
				        	var opt1 = json[j];
				        	output = output + "<option value='" +opt1.id+ "'>"+opt1.firstName+" "+opt1.lastName+"</option>";
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
function validateEmail1(orgRelationID){
	 getOwners(orgRelationID);
	var email = document.getElementById("EmailId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(email != null){
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/validateClientOrVendorEmails?"+tokenParamName+"="+tokenValue+"&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#EmailId").after( "<p class='jquery_form_error_message'>should not your org users email</span>");
					document.getElementById("EmailId").setAttribute( 'record-exist', 'yes');
					document.getElementById("EmailId").setAttribute( 'record-exist-errorMsg',"should not your org users email");
					
				}else if(result == 'Valid Email') {
					$('input#EmailId').removeAttr("class record-exist record-exist-errormsg");
					$('input#EmailId').attr('class','valid form-control');
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					validateEmailForExistenceUniversally(EmailId, "input#EmailId");
					
				}
			},

		});
	}else{
		$("#emailStatusIconDiv").find("i").remove();
  		$("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
	}
}
</script>
<!-- Show success message after form submission script -->
<script>
	function clientMsgFadein() {
		$("#clientMsgSelector").fadeIn(200);
        $('#clientMsgSelector').delay(2000).fadeOut('slow');
	}
</script> 
<script>
	function clientTerminateMsgFadein() {
		$("#clientTerminateMsgDiv").fadeIn(200);
        $('#clientTerminateMsgDiv').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->
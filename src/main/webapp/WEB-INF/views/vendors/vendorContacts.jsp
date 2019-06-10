<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style type="text/css">
.collapse-div-adduser select{ width: 100% !important; padding-right: 10px !important; padding-left: 10px !important; border:1px solid #ccc !important }
</style>
<div id="loading"></div>


<div id="popUp"></div>	

<script>
function vendorContacts(orgRelationID,clientLocationId){
	
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result  = result + '<div id="vendorConactMsgDiv" ></div>';
	result  = result + '<div class="success_pop" id="vendorConactTerminateMsgDiv" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="vendorConactTerminateMsg"></span>';
    result  = result + '</div>';
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
		result  = result + '   <div id="vendorContactListDiv" class="datatable_filter"></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

    $("#tab-1").empty();
    $("#tab-3").empty(); 
    $("#tab-4").empty(); 
    $("#tab-6").empty(); 
    $("#tab-7").empty();
    $("#tab-8").empty();
    $("#tab-5").empty(); 
    $("#tab-2").empty(); 
    $("#tab-2").append(result);  
	
    vendorContactList(orgRelationID,clientLocationId);
    
}
</script>

<script type="text/javascript">
	function vendorContactList(orgRelationID,vendorLocationId) {
		var vmsUrl = '${SourceLeadWeb_vmsUrl}';
		$.ajax({
	    	type: "POST",
	    	url : vmsUrl+"/getOrgSpocsList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationID+"&type=Vendor", 
	        success: function(json) {
	        	
	        	
	        	$("#vendorContactListDiv").empty();
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
				                result = result + '	 	<td><div><span title="'+opt.spocName+'">'+opt.spocName+'</span></div></td>';
				                result = result + '	     <td><div><span title="'+opt.spocEmail+'">'+opt.spocEmail+'</span></div></td>';
				                result = result + '	     <td><div>'+spocMobilePhone+'</div></td>';
				                result = result + '	     <td><div><span title="'+opt.ownerName+'">'+opt.ownerName+'</span></div></td>';
				               
				                result = result + ' <td><div>';
				               
				                if(opt.invitationStatus == 'Rejected'){
				        			result = result + '<span>'+opt.spocStatus+'</span>';
				        			 
				        		}else{
									result = result + ' <select id="spocStatus'+json.data[i].id+'" name="spocStatus" onChange="changeSpocStatus('+json.data[i].id+'\,'+vendorLocationId+');">';
									result = result + ' <option '+selected1+'>Active</option>';
									result = result + ' <option '+selected2+'>In-Active</option>';
									/* result = result + ' <option '+selected3+'>New</option>';
									result = result + ' <option '+selected4+'>Negotiation</option>';
									result = result + ' <option '+selected5+'>Contracted</option>'; */
									result = result + ' </select>';
				        		}
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
				                
				                if(json.data[i].status != null && json.data[i].status != '' && json.data[i].status !='undefined' && json.data[i].status != "INACTIVE"){
				                result = result + '	    <td><div>';
                                       '<sec:authorize access="hasAnyRole(\"ROLE_DELETE_VENDOR_CONTACT\")">';
                                        result = result + '<i class="glyphicon glyphicon-trash" onClick="terminateVendorSpoc(\''+json.data[i].id+'\',\''+orgRelationID+'\',\''+vendorLocationId+'\',\''+opt.spocName+'\');" title = "Remove vendor contact"></i>';
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
					    result = result + '	<p>Vendor delete message here ....</p>';
					    result = result + '	<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> ';
					    result = result + '</div>';
					    $("#vendorContactListDiv").append(result);
			    	    contactPagination();
			    	    vendorContactMsgFadein();
	        },
	    });
	}
</script>
<!--  vendor details ends -->
<script>
function changeSpocStatus(spocId,vendorLocationId){
	var spocStatus = $('#spocStatus'+spocId).val();
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	$.ajax({
	   	type: "POST",
	    url: vmsUrl+"/changeSpocStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	vendorContactList(orgRelationID,vendorLocationId);
	    }
   });
}
</script>

<script>
function cancelTerminationResonforSpoc(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
	$("#reasonForSpoc").val('');
	$("#reasonForSpocDiv").find("p.jquery_form_error_message").remove();
	$("#reasonForSpocDiv").empty(); 
}
</script>


<script>
function terminateVendorSpoc(spocId, orgRelationID,vendorLocationId,spocName){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Vendor delete message here</p>";
	result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc' data-validation='required validate_Space validate_Description validate_length length2-250' placeholder='Terminate Reason' onclick='vendorRemoveSpacesForRejectReason();'></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveReasonForVendorSpocTermination("+spocId+","+orgRelationID+","+vendorLocationId+",\""+spocName+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTerminationResonforSpoc();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
<script>
   function vendorRemoveSpacesForRejectReason() {
	    var output = document.getElementById("reasonForSpoc").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForSpoc").removeAttr("data-validation");
	    }
	}
</script>
<script>
function saveReasonForVendorSpocTermination(spocId, orgRelationID,vendorLocationId,spocName){
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	var userId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var reason = document.getElementById("reasonForSpoc").value;
	 reason = reason.replace(/\s/g, "") 
	if(reason.match(/^[^\s-](.*)*$/) != null){
	      
		  $("#reasonForSpocDiv").empty();
		  $("#reasonForSpocDiv").empty();
		  var formData = new FormData();
		     formData.append("tokenParamName", tokenValue);
		     formData.append("spocId", spocId);
		     formData.append("reason", reason);
		     formData.append("userId", userId);
		     formData.append("orgLocationId", orgLocationId);
		     formData.append("responderType", "Vendor");
		$.ajax({
			 type : "POST",
	          data: formData,
	          processData: false,
	          contentType:false, 
	     	  url: vmsUrl+"/terminateSpoc",
// 		   	url: vmsUrl+"/terminateSpoc?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&reason="+reason+"&userId="+userId+"&orgLocationId="+orgLocationId+"&responderType=Vendor",   	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   			    $("#vendorConactTerminateMsgDiv").removeAttr("style");
	 			$("#vendorConactTerminateMsgDiv").css({ display: "block" });
	 			$("#vendorConactTerminateMsg").html(""+spocName+" Removed");
   			    vendorContactList(orgRelationID,vendorLocationId);
   			    vendorConactTerminateMsgFadein();
		    }
	   });
	}else{
		if(reason.length == 0){
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

<!-- This script is for resending the invitation mail -->
<script type="text/javascript">
function resendSpocInvitation(spocUserId,orgRelationID,vendorLocationId,spocId){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(spocUserId != null && spocUserId != ""){
	//	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			    	type: "POST",
			        url: vmsUrl+"/resendSpocInvitationEmail?"+tokenParamName+"="+tokenValue+"&spocUserId="+spocUserId+"&orgSpocId="+spocId+"&responderType=Vendor&orgRelationID="+orgRelationID, 
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
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 6
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
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
	
	result = result  +'	 <div class="clearfix mb" id="EmailIdDiv">';
	result = result  +'	  <div class="col-md-12">';
	result = result + '<div class="input-group errorInptgroup">';
	result = result  +'	    <input type="text" class="form-control input-sm" name="email" id="EmailId" value=""  onblur="validateEmail1('+orgRelationID+'); getSpocUserDetails('+clientLocationId+','+orgRelationID+'); getOwnersList();" field-name="Email" placeholder="Email Id *" data-validation="required validate_Space validate_length length2-100 validate_email">';
	result = result + ' <span id="emailStatusIconDiv" class="input-group-addon">';
	result = result + '<i class="fa fa-check text-muted" style="color:#f6f6f6">';
	result = result + '</i>';
	result = result + '</span>';
	result = result  +'	   </div>';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	 <div class="col-md-12" id="errFirstName">';
	result = result  +'	<input type="text" class="form-control input-sm" value="" id="firstName" name="firstName" field-name="First name" placeholder="First name *" data-validation="required" data-validation="required validate_Space validate_length length1-100">';
	result = result  +'	</div>';
	result = result  +'	</div>';
	
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	  <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm"  id="lastName" name="lastName" value="" placeholder="Last name" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true">';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	   <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm" value=""  id="phoneNumber"  name="phoneNumber" placeholder="Phone number">';
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
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	 <div class="col-md-12" id="errOwner">';
	result = result  +'	   <select multiple data-placeholder="select owner" class="form-contorl chosen-select" name="owner" id="owner" field-name="Owner" data-validation="required">';
	result = result  +'	       <c:forEach var="user" items="${userList}">';
	result = result  +'	           <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? "selected" : ''} >${user.firstName} ${user.lastName}</option>';
	result = result  +'	       </c:forEach>';
    result = result  +'	  </select>';
    result = result  +'	 </div>';
    result = result  +'	 </div>';
	
	
	/* result = result  +'	 <div class="clearfix mb10" id="roleDiv" style="display: block;">';
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
	result = result  +'	      <input type="checkbox" value="N" name="apOrArSent" id="apOrArSent" onclick = "allowApEmailSent();"> AR Email</label>';
	result = result  +'	   </div>';
	
	result = result  +'	   <div class="col-md-6 checkbox margin-top0">';
	result = result  +'	     <label>';
	result = result  +'	      <input type="checkbox" value="N" name="invoiceOrBillSent" id= "invoiceOrBillSent" onclick = "allowInvoiceEmailSent();"> Billing Email</label>';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	  <div class="clearfix mr">';
	result = result  +'	   <input type="button" id ="addClientContact" value="Add" onclick="checkVendorContactAdd('+orgRelationID+','+clientLocationId+');" class="btn btn-sm btn-primary pull-right"/>';
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
	 /* $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   });	 */
	 
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

<script>

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
	function checkVendorContactAdd(orgRelationId,vendorLocationId) {
		if ($('#clinetContact_form').validate(false, validationSettings)) {
			var formData = $($('#clinetContact_form')[0]).serialize();
			var vmsUrl = '${SourceLeadWeb_vmsUrl}';
			$("#addClientContact").attr("disabled", "disabled");
			$("#addClientContact").addClass('button').val('Processing..');
			$.post(vmsUrl+"/saveClinentAndVendorContact?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&clientLocationId="+vendorLocationId+"&responderType=Vendor", formData, function(data) {
				$("#popUpDivContact").removeClass("edit-details-active");
				$("#vendorConactTerminateMsgDiv").removeAttr("style");
	 			$("#vendorConactTerminateMsgDiv").css({ display: "block" });
	 			$("#vendorConactTerminateMsg").html("Success !! New Contact Added");
				vendorContactList(orgRelationId,vendorLocationId);
				vendorConactTerminateMsgFadein();
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
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(EmailId != null && EmailId != ""){
		 $("#addClientContact").attr("disabled", "disabled");
         $("#addClientContact" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/validateSpocEmailId?"+tokenParamName+"="+tokenValue+"&email="+EmailId+"&orgRelationId="+orgRelationId, 
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
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : vmsUrl+"/getSpocUserDetails?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgLocationId="+orgLocationId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("readOnly", true);
	        		    $("#firstName").val(json[0]);
	        		    $('#errFirstName').find('p.jquery_form_error_message').remove();
	        		    
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
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 $.ajax({
		   	type: "POST",
		    url: vmsUrl+"/saveInvitationStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&invitationStaus=Accepted",	    	   
		    success: function(data) {
		    	$("#vendorConactTerminateMsgDiv").removeAttr("style");
	 			$("#vendorConactTerminateMsgDiv").css({ display: "block" });
	 			$("#vendorConactTerminateMsg").html("Invitation Accepted");
	 			vendorContactList(orgRelationId,vendorLocationId);
   			    vendorConactTerminateMsgFadein();
		    }
	   });
}
</script>

<script>
function rejectSpocInvitation(spocId,orgRelationId,clientLocationId){
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 $.ajax({
		   	type: "POST",
		    url: vmsUrl+"/saveInvitationStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&invitationStaus=Rejected",
		    success: function(data) {
		    	$("#vendorConactTerminateMsgDiv").removeAttr("style");
	 			$("#vendorConactTerminateMsgDiv").css({ display: "block" });
	 			$("#vendorConactTerminateMsg").html("Invitation Rejected");
	 			vendorContactList(orgRelationId,vendorLocationId);
   			    vendorConactTerminateMsgFadein();
		    }
	   });
}
</script>

<script type="text/javascript">
 function getOwners(orgRelationID) {
	 var email = document.getElementById("EmailId").value; 
	 var orgRelationId = orgRelationID;
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 var SourceLeadWeb_userId= '${SourceLeadWeb_userId}';
	 if(email != null && email != "" && email != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/getAllOwners?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgRelationId="+orgRelationId+"&type=vendor",  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	/* var output = "";
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(i == 0){
			        			output = output + "<option value='" +opt.id+ "' selected>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.firstName+" "+opt.lastName+"</option>";
			        		}
			        		
					} */
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
					        			$('#errOwner').find('p.jquery_form_error_message').remove();
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
function validateEmail1(orgRelationId){
	 //getOwners(orgRelationID);
	var email = document.getElementById("EmailId").value;
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	if(email != null){
		$.ajax({
			type : "POST",
			url : vmsUrl + "/validateClientOrVendorEmail?"+tokenParamName+"="+tokenValue+"&email="+email+"&orgRelationId="+orgRelationId+"&contactType=vendor",
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
					validateEmailForExistenceUniversally(email, "input#EmailId");
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
	function vendorContactMsgFadein() {
		$("#vendorConactMsgSelector").fadeIn(200);
        $('#vendorConactMsgSelector').delay(2000).fadeOut('slow');
	}
</script> 
<script>
	function vendorConactTerminateMsgFadein() {
		$("#vendorConactTerminateMsgDiv").fadeIn(200);
        $('#vendorConactTerminateMsgDiv').delay(2000).fadeOut('slow');
	}
</script> 

<script type="text/javascript">
 function getOwnersList() {
	 var email = document.getElementById("email").value; 
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(email != null && email != "" && email != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/getOwnersList?"+tokenParamName+"="+tokenValue+"&email="+email+"&type=Vendor", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
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
		        	$("#owner").empty();
			        $("#owner").append(output);
			        $('#owner').trigger("chosen:updated");
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Owner --</option>"; 
     	 $("#owner").empty();
	     $("#owner").append(output);
	     $('#owner').trigger("chosen:updated");
	 }
	
 }
</script>
<!-- End -->

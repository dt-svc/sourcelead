<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


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
<script type="text/javascript">
window.onload = function () {
	cscInit();
 };
</script>

<div id="popUp"></div>	

<script type="text/javascript">
	function vendorContactList(orgRelationID) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationID, 
	        success: function(data) {
	        	
	        	var json = JSON.parse(data);
	        	
	        	var res = "";
				res = res + '<div class="timesheet_head">';
				res = res + '<ul>';
				res = res + '<li><a href="javascript:void(0)" onClick="addContact('+orgRelationID+');" class="addcon-pop">';
				res = res + '<i class="glyphicon glyphicon-plus"></i>Add Contact</a>';
				res = res + '<div class="clearfix"></div>';
				res = res + '</ul>';
				res = res + '<div id="popUpDivContact"> ';
				res = res + '</div>  ';
				res = res + '</div>';
				
	    		if(json.length != null && json.length != ''){
	    			var result = "";
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
								result = result + ' <select id="spocStatus'+json[i].id+'" name="spocStatus" onChange="changeSpocStatus('+json[i].id+'\);">';
								result = result + ' <option '+selected1+'>Active</option>';
								result = result + ' <option '+selected2+'>In-Active</option>';
								result = result + ' <option '+selected3+'>New</option>';
								result = result + ' <option '+selected4+'>Negotiation</option>';
								result = result + ' <option '+selected5+'>Contracted</option>';
								result = result + ' </select>';
								result = result + ' </div></td>';
				                
				                result = result +'<td data-title="Invitation Status"><div>';
				                if(opt.spocUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){
				   	        		  result = result +' <i class="fa fa-check" alt="Accept" onclick="acceptInvitation('+opt.id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectInvitation('+opt.id+');"></i>';
				   	        		}
				   	        		
				   	        		if(opt.ownerUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
				   	        			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendSpocInvitation('+opt.spocUserId+','+orgRelationID+');">Send</a></b>';
				   	        		}
				   	        		if(opt.spocUserId != "${SourceLeadWeb_userId}" && opt.ownerUserId != "${SourceLeadWeb_userId}" && opt.invitationStatus == 'New'){
				   	        			result = result +' ---';
				   	        		}
				   	        		
				   	        		if(opt.spocUserId != "${SourceLeadWeb_userId}" && opt.ownerUserId != "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){ 
				   	        			result = result +' <b class="proj_process">Received</b>';
				   	        		}
				   	        		
				   	        		if(opt.ownerUserId == "${SourceLeadWeb_userId}" && opt.invitationStatus == 'Sent'){
				   	        			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendSpocInvitation('+opt.spocUserId+','+orgRelationID+');">Resend</a></b>';
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
				                                        result = result + '<i class="glyphicon glyphicon-trash" onClick="terminateSpoc('+json[i].id+'\,\''+orgRelationID+'\')";></i>';
				                	                    result = result +'</div></td>';
			                     }
			                     else{
			                          result = result + '<td><div><font color="red">Terminated</font></div></td>';
			                     }
				                result = result + '	 </tr>';
						} 
			        	result = result + '	     </tbody>';
			    	    
			    	    result = result + '<div class="project_inn_mask"></div>';
					    result = result + '<div class="project_error">';
					    result = result + '	<p>User delete message here ....</p>';
					    result = result + '	<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> ';
					    result = result + '</div>';
			    	    
	        }else{
   			 result = result + '<div class="table_nodata">';
   			 result = result + '<div class="table_nodata_inn">';
   			 result = result + '<img src="resources/images/no-data.png" />';
   			 result = result + '<span>Nothing is here to show </b>';
   			 result = result + '</span>';
   			 result = result + '<div class="clearfix"></div>';
   			 result = result + '</div>';
   			 result = result + '</div>';
   		 }
    		$("#tab-2-addContact").empty();  
    	    $("#tab-2-addContact").append(res);
    		$("#tab-2").empty();  
    	    $("#tab-2").append(result);  
    	    contactPagination();
    		
	        },
	    });
	}
</script>
<!--  vendor details ends -->
<script>
function changeSpocStatus(spocId){
	var spocStatus = $('#spocStatus'+spocId).val();
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	vendorContactList(orgRelationID);
	    }
   });
}
</script>
<script>
function terminateSpoc(spocId, orgRelationID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>User delete message here</p>";
	result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReason("+spocId+","+orgRelationID+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReason(spocId, orgRelationID){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForSpoc").value;
	if(reason){
		$("#reasonForSpocDiv").empty();
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateSpoc?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&reason="+reason,	    	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   			    vendorContactList(orgRelationID);
		    }
	   });
	}else{
		$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
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
function resendSpocInvitation(spocUserId,orgRelationID){
	var sourceleadUrl ='${sourceleadUrl}';
	
	if(spocUserId != null && spocUserId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendSpocInvitationEmail?${_csrf.parameterName}=${_csrf.token}&spocUserId="+spocUserId+"&responderType=Vendor&orgRelationID="+orgRelationID, 
			        success: function(result) {
			        	vendorContactList(orgRelationID)
			        },
			    });
		 }
	}
  
</script>

 <!-- This Script is For Popup Div Starts-->  
<script>
function addContact(orgRelationID) {	
	var result = "";
	result = result  +'	<form id="clinetContact_form" name="clinetContact_form" method="post">';
	result = result  +'	<div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>';
	result = result  +'	<div class="form-horizontal clearfix">';
                              
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	<input type="text" class="form-control input-sm" value="" name="firstName" field-name="First name" placeholder="First name *" data-validation="required">';
	result = result  +'	</div>';
	result = result  +'	</div>';
	
	result = result  +'	<div class="clearfix mb10">';
	result = result  +'	  <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm" name="lastName" value="" placeholder="Last name">';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	  <div class="col-md-12">';
	result = result  +'	    <input type="text" class="form-control input-sm" name="email" value="" field-name="Email" placeholder="Email Id *" data-validation="required">';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	   <div class="col-md-12">';
	result = result  +'	     <input type="text" class="form-control input-sm" value="" name="phoneNumber" placeholder="Phone number">';
	result = result  +'	    </div>';
	result = result  +'	  </div>';
	
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	 <label class="col-md-3">Status</label>';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	  <select class="form-control selectpicker" id="spocStatus" name="spocStatus">';
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
	
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	 <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>';
	result = result  +'	 <div class="col-md-12">';
	result = result  +'	       <select class="form-control" id="role" name="roleType" field-name="Role" data-validation="required">';
	result = result  +'	                    <option value="">-Select Role-</option>';
	result = result  +'	 					<option value="1">Admin</option>';
	result = result  +'	 					<option value="3">Approver/Project Manager</option>';
	result = result  +'	 					<option value="5">HR Manager</option>';
	result = result  +'	 					<option value="6">Account Manager</option>';
	result = result  +'	 					<option value="8">Recruiter</option>';
	result = result  +'	        </select>';
	result = result  +'	 </div>';
	result = result  +'	 </div>';
	
	result = result  +'	 <div class="clearfix mb10">';
	result = result  +'	   <div class="col-md-12 checkbox">';
	result = result  +'	     <label>';
	result = result  +'	      <input type="checkbox" value="Y" name="isMailSent" checked="checked">Send Invitation</label>';
	result = result  +'	   </div>';
	result = result  +'	 </div>';
	
	result = result  +'	  <div class="clearfix mr">';
	result = result  +'	   <input type="button" id ="addClientContact" value="Add" onclick="check('+orgRelationID+');" class="btn btn-sm btn-primary pull-right"/>';
	result = result  +'	  </div>';
	
	result = result  +'	 </div>';
	result = result  +'	 </div>';
	result = result  +'	        </form> ';
	$("#popUpDivContact").empty();
	$("#popUpDivContact").append(result);
	
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
	/* $(".addcon-pop").click(function(){
		 $(".collapse-div-adduser").toggleClass("show-div");
		 $(".collapse-div").removeClass("show-div");
	 }); */
	 
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){
		 $(this).closest('.edit-details').removeClass("edit-details-active");
		 });
	 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	};
</script>
<!-- <script>
$('html').click(function(){
    $(".edit-details").removeClass("edit-details-active");
   });
</script> -->
 <!-- This Script is For Popup Div Ends --> 
 <!-- to add new contact -->
<script type="text/javascript">
	function check(orgRelationId) {
		if ($('#clinetContact_form').validate(false, validationSettings)) {
			var frm = $('#clinetContact_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#addClientContact").attr("disabled", "disabled");
			$("#addClientContact").addClass('button').val('Processing..');
			alert("hii");
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveClinentAndVendorContact?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&orgRelationId="+orgRelationId,
						success : function(result) {
							vendorContactList(orgRelationId);
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

<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
	function clientDetailsbySpocId(spocId,ownerId,spocUserId,tempOwnerId,orgRelationId,spocLocationId) {
		
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/getSpocAndOwnerDetailsById?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&ownerId="+ownerId+"&spocUserId="+spocUserId+"&tempOwnerId="+tempOwnerId+"&orgRelationId="+orgRelationId+"&spocLocationId="+spocLocationId+"&type=Client",
					success : function(json) {
						/* var json = JSON.parse(data); */
						getClientSpocname(json.spocName);
						var result = "";
						result  = result + '<div class="success_pop" id="clientEditMsgDiv" style="display: none;">';
					    result  = result + '<i class="fa fa-check-circle"></i><span id="clientEditMsg"></span>';
					    result  = result + '</div>';
						if(json.msg != null && json.msg != "" && json.msg != 'undefined'){
							result = result+ '<div class="success_pop" id="selector1" style="display: block;">';
							result = result+ ' <i class="fa fa-check-circle"></i><span>'+json.msg+'</span>';
							result = result+ '</div>';
						}
						
						result = result+ '<div class="project_tabs_inn_table">';
						result = result + '<div class="project_details">';
						'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CLIENT_CONTACT\')">';
							result = result + ' <div class="useredit useredit_a">';
								result = result + '<a id="edit-client" href="javascript:editVendorContactDetails('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+','+orgRelationId+','+spocLocationId+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
							result = result + ' </div>'; 
						'</sec:authorize>';
							
							result = result + '<div class="project_details_cta">';
								result = result + '<strong>Name:</strong>';
								if(json.spocName !=null && json.spocName !='' && json.spocName !='undefined'){
									result = result + '<span title='+json.spocName+'>'+json.spocName+'</span>';
								}
								result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>EmailId:</strong>';
							if(json.spocEmail !=null && json.spocEmail !='' && json.spocEmail !='undefined'){
								result = result + '<span title='+json.spocEmail+'>'+json.spocEmail+'</span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Phone No:</strong>';
							if(json.spocMobilePhone !=null && json.spocMobilePhone !='' && json.spocMobilePhone !='undefined'){
								result = result + '<span>'+json.spocMobilePhone+'</span>';
							}else{
								result = result + '<span> --- </span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Status:</strong>';
							if(json.spocStatus !=null && json.spocStatus !='' && json.spocStatus !='undefined'){
								result = result + '<span>'+json.spocStatus+'</span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							/* spoc Status */
							
					       /*  result = result + ' <td><div>';
							result = result + ' <select id="spocStatus" name="spocStatus">';
							result = result + ' <option value="">-Select Status-</option>';
							result = result + ' <option '+selected1+'>Active</option>';
							result = result + ' <option '+selected2+'>In-Active</option>';
							result = result + ' <option '+selected3+'>New</option>';
							result = result + ' <option '+selected4+'>Negotiation</option>';
							result = result + ' <option '+selected5+'>Contracted</option>';
							result = result + ' </select>';
							result = result + ' </div></td>'; */
							
							/* Owners */
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Owner :</strong>';
							if(json.ownerName !=null && json.ownerName !='' && json.ownerName !='undefined'){
								result = result + '<span title='+json.ownerName+'>'+json.ownerName+'</span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							/* check boxes start */
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Invoice Email:</strong>';
							if(json.invoiceEmailStatus == 'Y' || json.invoiceEmailStatus == 'N'){
								result = result + '<span>'+json.invoiceEmailStatus+'</span>';
							}else{
								result = result + '<span> N </span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>AP Email:</strong>';
							if(json.apEmailStatus == 'Y' || json.apEmailStatus == 'N'){
								result = result + '<span>'+json.apEmailStatus+'</span>';
							}else{
								result = result + '<span> N </span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							/* check boxes end */
							
							<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
							var sectionId = '${section.id}';
							getCustomDependentFieldClientContact(spocLocationId,orgRelationId,sectionId);
							if("${sectionOrder.index == 1}"){
								result = result + '<div id="customContactDiv${section.id}" class="customClinetContactDet"></div>';

							}
							</c:forEach>
							
							
							result = result + '<div class="clearfix"></div>';
							result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editVendorContactDetailsDiv"></div>';
							result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
						$("#tab-3-addTemplate").empty();
						$("#tab-3").empty();
						$("#tab-1").empty();
						$("#tab-1").append(result);
						$("#tab-1").show();
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
						msgFadeIn();

					}
				});
	}
</script>

<script>
	function clientEditMsgFadein() {
		$("#clientEditMsgDiv").fadeIn(200);
        $('#clientEditMsgDiv').delay(2000).fadeOut('slow');
	}
</script> 
<script type="text/javascript">
	function editVendorContactDetails(spocId,ownerId,spocUserId,tempOwnerId,orgRelationId,spocLocationId) {
		      $.ajax({
						type : "POST",
						url : sourceleadUrl+"/getSpocAndOwnerDetailsById?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&ownerId="+ownerId+"&spocUserId="+spocUserId+"&tempOwnerId="+tempOwnerId+"&spocLocationId="+spocLocationId+"&orgRelationId="+orgRelationId+"&type=Client",
						success : function(json) {
						/* var json = JSON.parse(data); */
						var result = "";
						var selected1 = '';
						var selected2 = '';
						var selected3 = '';
						var selected4 = '';
						var selected5 = '';
						var spocMobilePhone = "";
						var spocExtensionNumber="";
						var invoiceEmailStatus;
						var apEmailStatus;
						if(json.spocMobilePhone != null && json.spocMobilePhone != '' && json.spocMobilePhone != 'undefined'){
							spocMobilePhone = json.spocMobilePhone;
						}else{
							spocMobilePhone = "";
						}
						if(json.spocExtensionNumber != null && json.spocExtensionNumber != '' && json.spocExtensionNumber != 'undefined'){
							spocExtensionNumber = json.spocExtensionNumber;
						}else{
							spocExtensionNumber = "";
						}
						
						if(null != json.spocLastName && json.spocLastName != '' && json.spocLastName != 'undefined'){
							spocLastName = json.spocLastName;
						}else{
							spocLastName = "";
						}
						if(json.spocStatus == 'Active'){
							selected1 = 'selected';
						} 
					
						if(json.spocStatus == 'In-Active'){
								selected2 = 'selected';
							} 
						
						if(json.spocStatus == 'New'){
								selected3 = 'selected';
							}
						if(json.spocStatus == 'Negotiation'){
								selected4 = 'selected';
							} 
						
						if(json.spocStatus == 'Contracted'){
							selected5 = 'selected';
						} 
						/*For checkboxes Edit mode start */
						invoiceEmailStatus = (json.invoiceEmailStatus =='Y' ? true : false);
						apEmailStatus = (json.apEmailStatus =='Y' ? true :false);
						/*For checkboxes Edit mode end */
						<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
						if("${sectionOrder.index == 1}"){
							var sectionId = '${section.id}';
							  preapareClientContactCustomFieldDetails(spocLocationId,orgRelationId,sectionId);
						}
						</c:forEach> 
						
						result = result + ' <form id="editVendorContact_form"  name="editVendorContact_form" method="post">';
						/* readonly="readonly" */
				     	if(json.userState == 'ACTIVE' || json.userState == 'INACTIVE'){
				     		
				     		result = result + '<div class="user_summary_edit_cta" id="vendorContactFirstNameDiv">';
							result = result + '<label>First Name:<span class="text-danger">*</span></label>';
							result = result + '<input type="text"  id="vendorContactFirstName" name="firstName" value="'+json.spocFirstName+'" placeholder="First name" field-name="First name" data-validation="required validate_Space validate_length length2-250" readonly="readonly">';
							result = result + '</div>';
							
							result = result + '<div class="user_summary_edit_cta" id="vendorContactLastNameDiv">';
							result = result + '<label>Last Name:</label>';
							result = result + '<input type="text"  id="vendorContactLastName" name="lastName" value="'+spocLastName+'" placeholder="Last name" field-name="Last name" readonly="readonly">';
							result = result + '</div>';
							
							
							
							result = result + '<div class="user_summary_edit_cta" id="mobilePhone">';
							result = result + ' <div class="row">';
							result = result + ' <div class="col-md-7 phoneValid">';
							result = result + '<label>Phone No:</label>';
							result = result + '<input type="text"  id="mobilePhone" name="phoneNumber" value="'+spocMobilePhone+'" placeholder="Mobile Phone" data-validation="validate_Space validate_phone validate_length length9-20" field-name="Mobile Phone" data-validation-optional="true">';
							result = result + '</div>';
							
							result = result + ' <div class="col-md-5 errBotVal">';
							result = result + ' <label>Extentsion</label>';
							result = result + ' <input type="text" value="'+spocExtensionNumber+'" name="extensionNumber" data-validation="validate_extno validate_length length1-5" data-validation-optional="true">';
							result = result + ' </div>';
							result = result + ' </div>';
							result = result + ' </div>';
							
							
							
					   }else{
						   result = result + '<div class="user_summary_edit_cta" id="vendorContactFirstNameDiv">';
							result = result + '<label>First Name:<span class="text-danger">*</span></label>';
							result = result + '<input type="text"  id="vendorContactFirstName" name="firstName" value="'+json.spocFirstName+'" placeholder="First name" field-name="First name" data-validation="required validate_Space validate_length length2-250" >';
							result = result + '</div>';
							
							result = result + '<div class="user_summary_edit_cta" id="vendorContactLastNameDiv">';
							result = result + '<label>Last Name:</label>';
							result = result + '<input type="text"  id="vendorContactLastName" name="lastName" value="'+spocLastName+'" placeholder="Last name" field-name="Last name">';
							result = result + '</div>';
							
							/* result = result + '<div class="user_summary_edit_cta" id=" id="EmailIdDiv"">';
							result = result + '<label>Email:</label>';
							result = result + '<input type="text" class="form-control" id="email" name="email" value="'+json.spocEmail+'" field-name="Email Id" data-validation="required validate_Space validate_email" onKeyUp="validateClientEmail('+spocId+');">';
							//result = result + '<input type="text"  id="emailId" name="emailId" value="'+json.spocUser.email+'" placeholder="EmailId" field-name="EmialId" onKeyUp="validateClientEmail('+spocId+');">';
							result = result + '</div>'; */
							
							result = result + '<div class="user_summary_edit_cta" id="mobilePhone">';
							result = result + ' <div class="row">';
							result = result + ' <div class="col-md-7">';
							result = result + '<label>Phone No:</label>';
							result = result + '<input type="text"  id="mobilePhone" name="phoneNumber" value="'+spocMobilePhone+'" placeholder="Mobile Phone" field-name="Mobile Phone" data-validation-optional="true" data-validation="validate_length length9-20 validate_phone" >';
							result = result + '</div>';
							
							result = result + ' <div class="col-md-5 errBotVal">';
							result = result + ' <label>Extentsion</label>';
							result = result + ' <input type="text" value="'+spocExtensionNumber+'" name="extensionNumber" data-validation="validate_extno validate_length length1-5" data-validation-optional="true">';
							result = result + ' </div>';
							result = result + ' </div>';
							result = result + ' </div>';
					   }
				     	result = result + '<div class="user_summary_edit_cta" id=" id="EmailIdDiv"">';
						result = result + '<label>Email Id :</label>';
						if(spocUserId != "${SourceLeadWeb_userId}" && ownerId != "${SourceLeadWeb_userId}" && json.invitationStatus == 'Sent'){ 
							result = result + '<input type="text" class="form-control" id="email" name="email" value="'+json.spocEmail+'" field-name="Email Id" data-validation="required validate_Space validate_email" readonly="readonly">';
						}else if(ownerId == "${SourceLeadWeb_userId}" && (json.invitationStatus == 'New' || json.invitationStatus == 'Sent')){
							result = result + '<input type="text" class="form-control" id="email" name="email" value="'+json.spocEmail+'" field-name="Email Id" data-validation="required validate_Space validate_email" readonly="readonly">';
						}else if(json.invitationStatus == 'Accepted'){
							result = result + '<input type="text" class="form-control" id="email" name="email" value="'+json.spocEmail+'" field-name="Email Id" data-validation="required validate_Space validate_email" readonly="readonly">';
						}else if(spocUserId != "${SourceLeadWeb_userId}" && ownerId != "${SourceLeadWeb_userId}" && json.invitationStatus == 'New'){
							result = result + '<input type="text" class="form-control" id="email" name="email" value="'+json.spocEmail+'" field-name="Email Id" data-validation="required validate_Space validate_email" readonly="readonly">';
				   		}else if(json.invitationStatus == 'Rejected'){
				   			result = result + '<input type="text" class="form-control" id="email" name="email" value="'+json.spocEmail+'" field-name="Email Id" data-validation="required validate_Space validate_email" readonly="readonly">';
				   		}
						//result = result + '<input type="text"  id="emailId" name="emailId" value="'+json.spocUser.email+'" placeholder="EmailId" field-name="EmialId" onKeyUp="validateClientEmail('+spocId+');">';
						result = result + '</div>';
						
				     	//to diffentiate temp spoc user
						result = result + '<input type="hidden"  id="existingSpocUserId" name="existingSpocUserId" value="'+json.tempSpocId+'">';
						
						result = result + ' <div class="user_summary_edit_cta" >';
						result = result + '<label>Status :</label>';
						if(json.invitationStatus == 'Rejected'){
		        			result = result + '<span>'+json.spocStatus+'</span>';
		        		}else{
		        			result = result + ' <select id="spocStatus" name="spocStatus">';
							result = result + ' <option '+selected1+'>Active</option>';
							result = result + ' <option '+selected2+'>In-Active</option>';
							/*result = result + ' <option '+selected3+'>New</option>';
							result = result + ' <option '+selected4+'>Negotiation</option>';
							result = result + ' <option '+selected5+'>Contracted</option>';*/
							result = result + ' </select>';
		        		}
						result = result + ' </div>'
						
						result = result  +'	 <div class="clearfix mb10">';
						result = result  +'	 <label class="col-md-12">Owner<span class="text-danger">&nbsp;*</span></label>';
						result = result  +'	 <div class="col-md-12">';
						result = result  +'	   <select  class="form-contorl chosen-select" name="owner" id="owner" field-name="Owner" data-validation="required">';
						for (var j = 0; j < json.userList.length; j++) {
							var user = json.userList[j];
							  if(user.id == tempOwnerId && user.id != json.tempSpocId){
								   selected = 'selected';
								   result = result+"<option value="+user.id+" "+selected+" >"+user.firstName+" "+user.lastName+"</option>";
							  }else if(user.id != json.tempSpocId){
									  result = result+"<option value="+user.id+" >"+user.firstName+" "+user.lastName+"</option>";
			   		 	    	 }  
						}
						
						result = result  +'	  </select>';
					    result = result  +'	 </div>';
					    result = result  +'	 </div>'; 
					    
					    
					    result = result +' <div class="col-md-9">';
			            result = result +' <div class="checkbox checkBox">';
			            result = result +' <label> <input type="checkbox" value="N" name="invoiceOrBillSent" id= "invoiceOrBillSent" onclick = "allowInvoiceEmailSent();"> Invoice Email</label>';
			            result = result +' </div>';
			            result = result +' </div>';
			            
			            result = result +' <div class="col-md-9">';
			            result = result +' <div class="checkbox checkBox">';
			            result = result +' <label> <input type="checkbox" value="N" name="apOrArSent" id="apOrArSent" onclick = "allowApEmailSent();"> AP Email</label>';
			            result = result +' </div>';
			            result = result +' </div>';
						
                        result = result + ' <div class="user_summary_edit_cta NewStyleFullWidth errorInptgroup"  id="customFieldDataId"></div>';

                        
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<input class="us-btn" id="save_vendor" type="button" value="Save" onclick="storeClientContactDetails('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+','+orgRelationId+','+spocLocationId+')"/> <input class="us-btn us-btn_a" type="button" value="Cancel" onclick="clientDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+','+orgRelationId+','+spocLocationId+')"/>';
						result = result + '</div>';		
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>'; 
						$(".user_summary_edit").slideToggle(100);
						$("#editVendorContactDetailsDiv").empty();
				        $("#editVendorContactDetailsDiv").append(result);
				        
				        /*For checkboxes Edit mode start */
				        $("#invoiceOrBillSent").attr('checked', invoiceEmailStatus);
				        $("#invoiceOrBillSent").val(invoiceEmailStatus?'Y':'N');
				        
				        $("#apOrArSent").attr('checked', apEmailStatus);
				        $("#apOrArSent").val(apEmailStatus?'Y':'N');
				        /*For checkboxes Edit mode end */
				        
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
					},
				}); 
		      
		   
	}
</script>

<script type="text/javascript">

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

 function validateClientEmail(spocId) {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 if(EmailId != null && EmailId != ""){
		/*$("#save_client").attr("disabled", "disabled");
         $("#save_client" ).addClass('button').val('Processing..'); */
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getVendorClientEmailVerification?"+tokenParamName+"="+tokenValue+"&EmailId="+EmailId+"&spocId="+spocId, 
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
		        		 $("#save_client" ).addClass('button').val('Save');
                         $("#save_client").removeAttr("disabled");
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
	function storeClientContactDetails(spocId,ownerId,spocUserId,tempOwnerId,orgRelationId,spocLocationId) {
         if ($('#editVendorContact_form').validate(false, validationSettings)){
             var formData = getFormData("editVendorContact_form");
		 $.post(sourceleadUrl+"/vendorContactDetailsUpdateUsingId?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&ownerId="+ownerId+"&spocUserId="+spocUserId+"&type=Client", formData, function(data) {
			 var ownerId = data;
		    	
		    	$("#spocDetailsId").removeAttr("onClick")
		    	$("#spocDetailsId").attr("onclick","clientDetailsbySpocId('"+spocId+"', '"+ownerId+"', '"+spocUserId+"', '"+ownerId+"','"+orgRelationId+"','"+spocLocationId+"')");
		    	$("#clientEditMsgDiv").removeAttr("style");
	 			$("#clientEditMsgDiv").css({ display: "block" });
	 			$("#clientEditMsg").html("Contact Details Updated");
		    	clientDetailsbySpocId(spocId,ownerId,spocUserId,ownerId,orgRelationId,spocLocationId);
		    	clientEditMsgFadein();
		    	/* getVendorContactLists();  */
			});
		 
	     	return true;
         }else{
             return false;
           }
		
    }
	$('body').on('blur', '#editVendorContact_form', function() {
        $("#editVendorContact_form").showHelpOnFocus();
        $("#editVendorContact_form").validateOnBlur(false, validationSettings);  
    });
</script>    

<!-- Show success message after form submission script -->
<script>
	function msgFadeIn() {
	    setTimeout(function(){ 
	    	$('#selector1').fadeOut('slow'); }, 500);
	}
</script> 
<!-- End -->

<script type="text/javascript">
function preapareClientContactCustomFieldDetails(clientLocationId,orgRelationID,sectionId){
	$.ajax({
        type : "POST",
         url: sourceleadUrl+"/getAllASectionCustomFields?&clientLocationId="+clientLocationId+"&orgRelationID="+orgRelationID+"&requestFrom=clientAndVendor"+"&sectionId="+sectionId,        
         success :function(data) {
        	 $('#customFieldDataId').empty();
        	 $('#customFieldDataId').append(data);
           },
     }); 
}
</script>

<script>
function getCustomDependentFieldClientContact(clientLocationId,orgRelationID,sectionId){
	var type= 'view';
	 $.ajax({
			type : "POST" , 
				 url: sourceleadUrl+"/getAllASectionCustomFields?&clientLocationId="+clientLocationId+"&orgRelationID="+orgRelationID+"&requestFrom=clientAndVendor"+"&sectionId="+sectionId+"&requestType="+type,
				 success: function(data) {
		        	$('#customContactDiv'+sectionId).empty();
		        	$('#customContactDiv'+sectionId).append(data);
				 }
		  });
}
</script>

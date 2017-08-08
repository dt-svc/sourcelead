<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
	function vendorEditMsgFadein() {
		$("#vendorEditMsgDiv").fadeIn(200);
        $('#vendorEditMsgDiv').delay(2000).fadeOut('slow');
	}
</script> 
<script type="text/javascript">
	function vendorDetailsbySpocId(spocId,ownerId,spocUserId,tempOwnerId)
	{
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/getSpocAndOwnerDetailsById?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&ownerId="+ownerId+"&spocUserId="+spocUserId+"&tempOwnerId="+tempOwnerId+"&type=Vendor",
					success : function(data) {
						var json = JSON.parse(data);
						getVendorDashboardName(json.spocName);
						var result = "";
						result  = result + '<div class="success_pop" id="vendorEditMsgDiv" style="display: none;">';
					    result  = result + '<i class="fa fa-check-circle"></i><span id="vendorEditMsg"></span>';
					    result  = result + '</div>';
						if(json.msg != null && json.msg != "" && json.msg != 'undefined'){
							result = result+ '<div class="success_pop" id="selector1" style="display: block;">';
							result = result+ ' <i class="fa fa-check-circle"></i><span>'+json.msg+'</span>';
							result = result+ '</div>';
						}
						
						result = result+ '<div class="project_tabs_inn_table">';
						result = result + '<div class="project_details">';
						
						 '<sec:authorize access="hasAnyRole(\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
							result = result + ' <div class="useredit useredit_a">';
								result = result + '<a id="edit-client" href="javascript:editVendorContactDetails('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
							result = result + ' </div>'; 
						'</sec:authorize>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Spoc Name:</strong>';
							if(json.spocName !=null && json.spocName !='' && json.spocName !='undefined'){
								result = result + '<span title="'+json.spocName+'">'+json.spocName+'</span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>EmailId:</strong>';
							if(json.spocEmail !=null && json.spocEmail !='' && json.spocEmail !='undefined'){
								result = result + '<span title="'+json.spocEmail+'">'+json.spocEmail+'</span>';
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
								result = result + '<span title="'+json.ownerName+'">'+json.ownerName+'</span>';
							}
							result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							
							result = result + '<div class="clearfix"></div>';
							result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editVendorContactDetailsDiv"></div>';
							result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
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



<script type="text/javascript">
	function editVendorContactDetails(spocId,ownerId,spocUserId,tempOwnerId) {
		var sourceleadUrl = '${sourceleadUrl}';
		      $.ajax({
						type : "POST",
						url : sourceleadUrl+"/getSpocAndOwnerDetailsById?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&ownerId="+ownerId+"&spocUserId="+spocUserId+"&tempOwnerId="+tempOwnerId+"&type=Vendor",
						success : function(data) {
						var json = JSON.parse(data);
						var result = "";
						var selected1 = '';
						var selected2 = '';
						var selected3 = '';
						var selected4 = '';
						var selected5 = '';				
					    var spocMobilePhone='';
						if(json.spocMobilePhone != null && json.spocMobilePhone != '' && json.spocMobilePhone != 'undefined'){
							spocMobilePhone = json.spocMobilePhone;
						}else{
							spocMobilePhone = "";
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
						result = result + ' <form id="editVendorContact_form"  name="editVendorContact_form" method="post">';
						if(json.userState == 'ACTIVE' || json.userState == 'INACTIVE'){
							result = result + '<div class="user_summary_edit_cta" id="vendorContactFirstNameDiv">';
							result = result + '<label>First Name:<span class="text-danger">*</span></label>';
							result = result + '<input type="text"  id="vendorContactFirstName" name="vendorContactFirstName" value="'+json.spocFirstName+'" placeholder="First name" field-name="First name" data-validation="required validate_Space validate_length length2-250"  readonly="readonly">';
							result = result + '</div>';
							
							result = result + '<div class="user_summary_edit_cta" id="vendorContactLastNameDiv">';
							result = result + '<label>Last Name:</label>';
							result = result + '<input type="text"  id="vendorContactLastName" name="vendorContactLastName" value="'+spocLastName+'" placeholder="Last name" field-name="Last name"  readonly="readonly">';
							result = result + '</div>';
							
							
							
							result = result + '<div class="user_summary_edit_cta" id="mobilePhone">';
							result = result + '<label>Phone No:</label>';
							result = result + '<input type="text"  id="mobilePhone" name="mobilePhone" value="'+spocMobilePhone+'" placeholder="Mobile Phone" field-name="Mobile Phone" data-validation-optional="true" readonly="readonly" >';
							result = result + '</div>';
						}else{
						result = result + '<div class="user_summary_edit_cta" id="vendorContactFirstNameDiv">';
						result = result + '<label>First Name:<span class="text-danger">*</span></label>';
						result = result + '<input type="text"  id="vendorContactFirstName" name="vendorContactFirstName" value="'+json.spocFirstName+'" placeholder="First name" field-name="First name" data-validation="required validate_Space validate_length length2-250" >';
						result = result + '</div>';
						
						result = result + '<div class="user_summary_edit_cta" id="vendorContactLastNameDiv">';
						result = result + '<label>Last Name:</label>';
						result = result + '<input type="text"  id="vendorContactLastName" name="vendorContactLastName" value="'+spocLastName+'" placeholder="Last name" field-name="Last name">';
						result = result + '</div>';
						
						result = result + '<div class="user_summary_edit_cta" id="mobilePhone">';
						result = result + '<label>Phone No:</label>';
						result = result + '<input type="text"  id="mobilePhone" name="mobilePhone" value="'+spocMobilePhone+'" placeholder="Mobile Phone" field-name="Mobile Phone" data-validation-optional="true" data-validation="validate_length length9-20 validate_phone" >';
						result = result + '</div>';
						
						}
						
						result = result + '<div class="user_summary_edit_cta" id=" id="EmailIdDiv"">';
						result = result + '<label>EmailId :</label>';
						result = result + '<input type="text" class="form-control" id="email" name="email" value="'+json.spocEmail+'" field-name="Email Id" data-validation="required validate_Space validate_email"  readonly="readonly">';
						//result = result + '<input type="text"  id="emailId" name="emailId" value="'+json.spocUser.email+'" placeholder="EmailId" field-name="EmialId">';
						result = result + '</div>';
						
						
						//to diffentiate temp spoc user
						result = result + '<input type="hidden"  id="existingSpocUserId" name="existingSpocUserId" value="'+json.tempSpocId+'">';
						
						result = result + ' <div class="user_summary_edit_cta" >';
						result = result + '<label>Spoc Status :</label>';
						if(json.invitationStatus == 'Rejected'){
		        			result = result + '<span>'+json.spocStatus+'</span>';
		        		}else{
		        			result = result + ' <select id="spocStatus" name="spocStatus">';
							result = result + ' <option '+selected1+'>Active</option>';
							result = result + ' <option '+selected2+'>In-Active</option>';
							result = result + ' <option '+selected3+'>New</option>';
							result = result + ' <option '+selected4+'>Negotiation</option>';
							result = result + ' <option '+selected5+'>Contracted</option>';
							result = result + ' </select>';
		        		}
						result = result + ' </div>'
						
						/* result = result + '<div class="user_summary_edit_cta" id="owner">';
						result = result + '<label>Owner :</label>';
						result = result + '<input type="text"  id="owner" name="owner" value="'+json.ownerName+'" placeholder="Owner name" field-name="Owner name">';
						result = result + '</div>'; */
						
						
				 		result = result  +'	 <div class="clearfix mb10">';
						result = result  +'	 <label class="col-md-12">Owner<span class="text-danger">&nbsp;*</span></label>';
						result = result  +'	 <div class="col-md-12">';
						result = result  +'	   <select class="form-contorl chosen-select" name="owner" id="owner" field-name="Owner" data-validation="required">';
						
					    //alert("json.userList.length"+json.userList.length);
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
						
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<input class="us-btn" id="save_vendor" type="button" value="Save" onclick="storeVendorContactDetails('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+')"/> <input class="us-btn us-btn_a" type="button" value="Cancel" onclick="vendorDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+')"/>';
						result = result + '</div>';		
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>'; 
						$(".user_summary_edit").slideToggle(100);
						$("#editVendorContactDetailsDiv").empty();
				        $("#editVendorContactDetailsDiv").append(result);
				        
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
 function validateClientEmail(spocId) {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		/*$("#save_client").attr("disabled", "disabled");
         $("#save_client" ).addClass('button').val('Processing..'); */
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getVendorClientEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId+"&spocId="+spocId, 
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
	function storeVendorContactDetails(spocId,ownerId,spocUserId,tempOwnerId) {
         if ($('#editVendorContact_form').validate(false, validationSettings)){
    		 var frm = $('#editVendorContact_form').serializeFormJSON();
             var con = JSON.stringify(frm);
             con = con.replace(/[{}]/g, "");
             //It will escape all the special characters
             var jsonData = encodeURIComponent(con);
             var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/vendorContactDetailsUpdateUsingId?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&spocId="+spocId+"&ownerId="+ownerId+"&spocUserId="+spocUserId+"&type=Vendor", 	   
	   		    success: function(data) {
	   		    	var newOwner = data;
	   		    	$("#spocDetailsId").removeAttr("onClick")
	   		    	$("#spocDetailsId").attr("onclick","vendorDetailsbySpocId('"+spocId+"', '"+newOwner+"', '"+spocUserId+"', '"+ownerId+"')");
	   		 		$("#vendorEditMsgDiv").removeAttr("style");
		 			$("#vendorEditMsgDiv").css({ display: "block" });
		 			$("#vendorEditMsg").html("Contact Details Updated");
	   		    	vendorDetailsbySpocId(spocId,newOwner,spocUserId,ownerId);
	   		    	//getVendorContactLists();
	   		    	getVendorContactListsELK();
	   		    	vendorEditMsgFadein();
	   		    }
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

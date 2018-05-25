
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
.edit_member_pop {
	top:12% !important; left: 35%; margin: 0px auto; z-index: 15500; width: 520px;
	position: fixed !important; background-color: #f9f9f9; box-shadow: 0px 0px 5px rgba(0,0,0,0.3); padding: 15px;
}
.edit_member_pop .footerBtns input {
    width: 50%; border: none; height: 30px; font-size: 14px; font-weight: 500; color: #FFF; background-color: #ff4b4b;
}
.edit_member_pop .footerBtns input.u_can_color {
    background-color: #375d81;
}
.edit_member_pop h4 {
background-color: #dcdcdc; padding: 10px; margin: 0; margin-bottom: 15px; font-size: 15px;
}
</style>

<div id="myLinkaccounts" class="tab-pane fade">
			  <div class="tab-content panel-body">
                  <div class="clearfix mb">
                      <div class="col-md-5">
				        <h4 class="pull-left">Accounts</h4>
				        	<a href="javascripit:void(0)" onclick="addNewLinkUser();"  class="pull-left ml mt2"><i class="fa fa-plus paddingRight"></i>Add</a>
				        </div>
                  </div>
					
					<div class="clearfix styleTables mt">
						<table width="100%">
							<thead>
								<tr>
								    <th>Email</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${userDTOListSize > 0}">
									
										<c:forEach var="user" items="${userDTOList}">
										<tr>
												<td>${user.email}</td>
												<td>
													<ul class="list-inline list-unstyled">
														<li><a href="javascript:void(0)" onclick="removeLinkedUser('${user.userCode}');"><i class="fa fa-trash text-danger"></i></a></li>
													</ul>
													
												</td>
										</tr>
										</c:forEach>
										
									
									
								</c:if>
							</tbody>
						</table>
						<c:if test="${userDTOListSize == 0}">
						 <div class='row'> 
							 <div class='col-lg-12'> 
								 <div class='dataTables_empty'>
								 </div>
							 </div>
						 </div>
						</c:if>
					</div>
					
				</div>
</div>

<div id="userLinksPopUp"></div>
<div id='linkChoicepopUp'></div>


<script>
 function addNewLinkUser(){
	 var result = "";
		result = result +"	<div class='project_inn_mask'></div>"; 
		result = result +" <div id='edit_member_pop' class='edit_member_pop'>";
		result = result +"	<div id='linkUserLoading'></div>"; 
		result = result +"	<h4>Link Account</h4>";
		result = result + '<div class="form-horizontal clearfix">';
		result = result + '<form id="linkUser_form" name="linkUser_form">';
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6" id="linkedUserNameDiv">';
		result = result + '	      <label class="clearfix">User Name<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField" onblur="validateLinkedUserName();" id="linkedUserName" name="linkedUserName" field-name="User Name"  data-validation="required">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    <div class="clearfix mb10 col-md-6" id="linkedUserPwddiv">';
		result = result + '	      <label class="clearfix">Password<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="password" class="form-control inlineField"  id="linkedUserPwd" name="linkedUserPwd" field-name="Password" onblur="validateLinkUsePwd();" data-validation="required">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		result = result + ' <div class="footerBtns">';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" class="u_can_color" type="button" value="Save" onclick="saveLinkedUser();"><input  type="button"  value="Cancel" onClick="cancelAddDetails();"></b>';
		result = result +"	</div>";
		result = result +"	</div>";
		
		result = result + '</form>';
		
		
		 $("#userLinksPopUp").append(result);
         $("#edit_member_pop").slideDown(200);
         $(".project_inn_mask").fadeIn(200); 
         
         
		
 }
 
 function cancelAddDetails(){
		$("#userLinksPopUp").empty();
		 $(".project_inn_mask").fadeOut(200);
		 $(".vendor_wrap_mask").fadeOut(200);
	}
 
 </script>	
 
 <script>
 function validateLinkedUserName(){
	 var linkedUserName = $("#linkedUserName").val();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(linkedUserName){
		 $("#confirm-id").attr("disabled", "disabled");
		 $("#linkUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
        	      type: "POST",
              	  url: sourceleadUrl+"/getLinkedUserDetailsByUserName?${_csrf.parameterName}=${_csrf.token}&linkedUserName="+linkedUserName,
                	success: function(result) {
                		if(result == 'invalid'){
                			$('#linkedUserNameDiv').find('p.jquery_form_error_message').remove();
                            $("input#linkedUserName").after("<p class='jquery_form_error_message'>User Name must not be logged in user</span>");
                            document.getElementById("linkedUserName").setAttribute('record-exist','yes');
                            document.getElementById("linkedUserName").setAttribute('record-exist-errorMsg',' User Name must not be logged in user');
                            $( "#linkUserLoading" ).removeAttr("style");
                		}else if(result == 'linked'){
                			$('#linkedUserNameDiv').find('p.jquery_form_error_message').remove();
                            $("input#linkedUserName").after("<p class='jquery_form_error_message'>Already linked with another user</span>");
                            document.getElementById("linkedUserName").setAttribute('record-exist','yes');
                            document.getElementById("linkedUserName").setAttribute('record-exist-errorMsg',' Already linked with another user');
                            $( "#linkUserLoading" ).removeAttr("style");
                		}else if(result == 'Not Registered'){
                			$('#linkedUserNameDiv').find('p.jquery_form_error_message').remove();
                            $("input#linkedUserName").after("<p class='jquery_form_error_message'>User is not registered</span>");
                            document.getElementById("linkedUserName").setAttribute('record-exist','yes');
                            document.getElementById("linkedUserName").setAttribute('record-exist-errorMsg',' User is not registered');
                            $( "#linkUserLoading" ).removeAttr("style");
                		}else{
                            $('input#linkedUserName').removeAttr( "record-exist record-exist-errormsg");
                            $('#linkedUserNameDiv').find('p.jquery_form_error_message').remove();
                            $("#confirm-id").removeAttr("disabled");
                            $( "#linkUserLoading" ).removeAttr("style");
                       }
			        },  
             })
	 }
 }
 
 function validateLinkUsePwd(){
	 var linkedUserName = $("#linkedUserName").val();
	 var linkedUserPwd = $("#linkedUserPwd").val();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(linkedUserName && linkedUserPwd){
		 $("#confirm-id").attr("disabled", "disabled");
		 $("#linkUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
        	      type: "POST",
              	  url: sourceleadUrl+"/validateLinkedUserPasswordByUserName?${_csrf.parameterName}=${_csrf.token}&linkedUserName="+linkedUserName+"&linkedUserPwd="+linkedUserPwd,
                	success: function(result) {
                		if(result == 'invalid'){
                			$('#linkedUserPwddiv').find('p.jquery_form_error_message').remove();
                            $("input#linkedUserPwd").after("<p class='jquery_form_error_message'>invalid password</span>");
                            document.getElementById("linkedUserPwd").setAttribute('record-exist','yes');
                            document.getElementById("linkedUserPwd").setAttribute('record-exist-errorMsg',' invalid password');
                            $( "#linkUserLoading" ).removeAttr("style");
                		}else{
                            $('input#linkedUserPwd').removeAttr( "record-exist record-exist-errormsg");
                            $('#linkedUserPwddiv').find('p.jquery_form_error_message').remove();
                            $("#confirm-id").removeAttr("disabled");
                            $( "#linkUserLoading" ).removeAttr("style");
                       }
			        },  
             })
	 }
 }
 
 
 </script>
 
 
 
 <script type="text/javascript">
	function saveLinkedUser() {
		
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};

		if ($('#linkUser_form').validate(false, validationSettings)) {
			 var formData = getFormData1("linkUser_form");
			 var sourceleadUrl ='${sourceleadUrl}';
     		$("#linkUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
						type : "POST",
						data: formData,
		  		        processData: false,
		  		        contentType:false, 
						url : sourceleadUrl+ "/linkUser?${_csrf.parameterName}=${_csrf.token}",
						success : function(result) {
							if (result && result.response === "INVALID_DATA") {
								$("#linkUserLoading").removeAttr("style");
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
							 $("#linkUserLoading").removeAttr("style");
							 location.replace(sourceleadUrl+"/userProfile");
			                } else if (result.indexOf('Sign In') !== -1) {
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
	}
	$('body').on('blur', '#linkUser_form', function() {
		$("#linkUser_form").showHelpOnFocus();
		$("#linkUser_form").validateOnBlur(false, validationSettings);
	});
</script>
 
 
 
 
 <script>
 function removeLinkedUser(userCode){
	    var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	<div id='removeLinkUserLoading'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Are you sure to remove?</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="removelinkUser(\''+userCode+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelLink();"></b>';
		result = result +"	</div>";
		$("#linkChoicepopUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
 }
 
 
 function removelinkUser(userCode){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#removeLinkUserLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
  	      type: "POST",
        	  url: sourceleadUrl+"/removeLinkedUser?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode,
          	success: function(result) {
          		
          		if (result && result.response === "INVALID_DATA") {
                    showError(result.responseMessage);
                } else if (result.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (result.response === "SUCCESS") {
				 location.replace(sourceleadUrl+"/userProfile");
                } else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
                    showError("Operation failed, please try again");
                }
	        },  
       })
 }
 </script>
 
	

<script type="text/javascript">
function cancelLink(){
	$("#linkChoicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
 
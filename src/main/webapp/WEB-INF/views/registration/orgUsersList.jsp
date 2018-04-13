
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="users" class="tab-pane fade">
			 
			 <div class="success_pop" id="mailSendSuccessMsg"  style="display: none;">
	         		<i class="fa fa-check-circle"></i><span>Mail Send Successfully.</span>
		      </div>
			 
			 <div class="clearfix">
			 <a href="javascript:void(0)" class="addcon-pop btn btn-sm btn-primary mb10" onclick="getOrgSubscribedCountries();"><i class="glyphicon glyphicon-plus"></i>&nbsp;Add User</a>
			 <!-- Popup Div -->
                    <div class="collapse-div-adduser orgsummaryuser-pop orgsummary-adduser col-md-4" id="addNewUserPopup"> <i class="fa fa-close close-div closediv-pop" onclick="flushUser();"></i>
                          <div class="form-horizontal clearfix">
                          <div id="userLoading"></div> 
                        <form:form id="adduser_form"  name="adduser_form" method="post">                             
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="userRoleDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Country" id="subscribedCountry" onchange="getLocationRegisteredCompanies();">
                                	 <option value="">-Select Country-</option>
                                </select>
                              </div>
                            </div>
                            
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="userRoleDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Company" onchange="getRegisteredOrgRoles();" id="registeredLocation" name="registeredLocation">
                                	 <option value="">-Select Company-</option>
                                </select>
                              </div>
                            </div>
                            
                            <div class="clearfix mb10">
	                              <div class="col-md-12" id="userEmailDiv">
	                                <input type="text" data-validation="required validate_Space validate_length length2-100 validate_email"  field-name="Email" onblur="getUserByUserEmail(); validateUserEmail();"  name="email" id="email" class="form-control input-sm"  placeholder="Email*">
	                              	<input type="hidden" name="registredOrgId" value="${orgId}">
	                              </div>
                              </div>
                              
                             <div class="clearfix mb10">
	                              <div class="col-md-12" id="firstNameDiv">
	                                <input type="text" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" field-name="First Name" name="firstName" id="firstName" class="form-control input-sm"  placeholder="First Name*">
	                              </div>
                              </div>
                              
                              <div class="clearfix mb10">
	                              <div class="col-md-12" id="lastNameDiv">
	                                <input type="text" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true"  field-name="Last Name"  name="lastName" id="lastName" class="form-control input-sm"  placeholder="Last Name">
	                              </div>
                              </div>
                              
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="userRoleDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Role" id="roleTypeId" name="roleTypeId">
                                	 <option value="">-Select Role-</option>
                                </select>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" id="add_user"  class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </form:form>  
                            
                          </div>
                        </div>
				<!-- Popup Div --> 
				
			 <div id="userList" class="project_tabs_inn_table vendor_wrap_inn_table mt"></div>
			 <div id="loading"></div>
			 </div>
			</div>
			
			
			
<script>

function getOrgSubscribedCountries(){
	 var orgId = '${orgId}';
	 if(orgId != null && orgId != ""){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getOrgSubscribedCountries?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Country --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
					}
		        	$("#subscribedCountry").empty();
			        $("#subscribedCountry").append(output);
			        $('#subscribedCountry').trigger("chosen:updated");
		        },
		    });
	}
	 
}


function getLocationRegisteredCompanies(){
	 var country = document.getElementById("subscribedCountry").value; 
	 var orgId = '${orgId}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 if(orgId != null && orgId != ""){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getLocationEnrolledCompanies?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId+"&country="+country, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Company --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
					}
		        	$("#registeredLocation").empty();
			        $("#registeredLocation").append(output);
			        $('#registeredLocation').trigger("chosen:updated");
			        
					$("#email").val('');
			        
				    $("#firstName").removeAttr("readonly");
	       			$("#firstName").val('');
	       			 
	       			$("#lastName").removeAttr("readonly");
	       			$("#lastName").val('');
		        },
		    });
	}
}
</script>			
			
<script type="text/javascript">
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("email").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(json) {
		        	
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#firstName").val(json.firstName);
				        	$("#firstName").attr("readonly", "readonly");
				        	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
			        		
			        		if(json.lastName != null && json.lastName != ''){
			        			$("#lastName").val(json.lastName);
					        	$("#lastName").attr("readonly", "readonly");
			        		}else{
			        			 $("#lastName").removeAttr("readonly");
			        			 $("#lastName").val('');
			        		}
			        		
			        		
		        		}else{
		        			 $("#firstName").removeAttr("readonly");
		        			 $("#firstName").val('');
		        			 
		        			 $("#lastName").removeAttr("readonly");
		        			 $("#lastName").val('');
		        		}
		        		
		        },
		    });
	 }else{
		 $("#FirstName").attr("disabled", false);
		 $("#FirstName").val('');
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 
		
	 }
 }
 </script>     
 
 <script>
function getUserList(){  
	 flushUser(); 
	 removeAddUserPopUp();
	  var sourceleadUrl ='${sourceleadUrl}';
      var orgId = '${orgId}';
      var registeredOrgLocationId = '${registeredOrgLocationId}';
	  
	  $("#loading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
		  type : "POST",
		  url: sourceleadUrl+"/getOrgRegisteredUsers?${_csrf.parameterName}=${_csrf.token}&registeredOrgLocationId="+registeredOrgLocationId,      
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
			    	     $("#userList").removeAttr("style");
	                	 $("#userList").empty();
	                	 var result = "";
	                	 result = result +"<table id='example' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"<tr>"; 
	                     result = result +'<th>Name</th>';
                		 result = result +'<th>Email</th>';
                		 result = result +'<th>Phone</th>';
                		 result = result +'<th>Role</th>';
                		 result = result +'<th>Status</th>';
                		 result = result +'<th>Action</th>';
	                     result = result +"</tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 var mobilePhone = '';
	                    	 var checked = '';
	                    	 var name = '';
	                    	 
                    		if(opt.isPrimaryAdmin == 'Y'){
    							checked = "checked"+" "+"value = 'Y'"+" "+"disabled = 'disabled'";
    						}else{
    							checked = "value = 'N'";
    						}
	                    	 
	                    	 
	                    	 if(opt.mobilePhone != null && opt.mobilePhone != '' && opt.mobilePhone != 'undefined' ){
	                    		 mobilePhone = opt.mobilePhone;
	                    	 }else{
	                    		 mobilePhone = '------'
	                    	 }
	                    	 
	                    	 if(opt.lastName && opt.lastName != '' && opt.lastName != 'undefined'){
	                    		 name = opt.firstName +" "+ opt.lastName;
	                    	 }
	                    	 else{
	                    		 name = opt.firstName;
	                    	 }
	                    	 
	                    	 result = result +'<tr>';
	                    	 result = result +'<td><div class="tbldata-dotdot-me" title='+name+'>'+name+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-me" title='+opt.email+'>'+opt.email+'</div></td>';
	                    	 result = result +'<td><div class="tbldata-dotdot-100" title='+mobilePhone+'>'+mobilePhone+'</div></td>';
	                    	 result = result +'<td>'+opt.role+'</td>';
	                    	 
	                    	 if(opt.userState == 'NOT_LOGGEDIN'){
				 			    	 result = result +"<td><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+opt.id+");' >&nbsp;&nbsp;Resend</a></td>";
			 			      }else if(opt.userState == 'ACTIVE'){
			 			    	  result = result +'<td>Active</td>';
				 			    }else if(opt.userState == 'INACTIVE'){
				 			    	result = result +'<td>In-active</td>';
				 			    } 
	                    	 
	                    	 if(opt.userState == 'ACTIVE' && opt.role == 'Admin'){
		                    	 result = result +'<td>'
			                    	 result = result +'<div class="col-md-7 orgrole-switch">';
			                    	 result = result +'<label class="switch">';
			                    	 result = result +'<input type="checkbox" onclick="checkUser('+opt.id+',\''+name+'\')"  name="check'+opt.id+'"  id="check'+opt.id+'" '+checked+'  >';
			                    	 result = result +'<div class="slider round" title="set as primary contact"></div>';
			                    	 result = result +'</label>';
			                    	 result = result +'</div>';
		                    	
		                         result = result +'</td>'; 
	                    	 }else{
	                    		 result = result +'<td>-----</td>';
	                    	 }
	                    	 
	                    	 result = result +'</tr>';
	                    	
                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#userList").append(result); 
	                     $("#loading").removeAttr("style");
	                     pagination(); 
                 }else{
                	 $("#loading").removeAttr("style");
  	    	         var result ="";
		    	    
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No User Found</p>";
					 result = result+"			 </div>";
					
					$("#userList").empty();
				    $("#userList").append(result); 
                }
          },
            
       }); 
  } 
</script>


<script>
function checkUser(userId,name){
	var checked = document.getElementById("check"+userId).checked
	if(checked == true){
		alertMessage("Do you want to make "+name+" as admin?",userId);
	}
}
</script>

<script type="text/javascript">
function cancel(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	document.getElementById("check"+userId).checked = false;
}
</script>

<script type="text/javascript">
function alertMessage(message,userId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='makeAsPrimaryAdmin("+userId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel("+userId+");'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script>
function makeAsPrimaryAdmin(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	var orgLocationId = document.getElementById("registeredLocation").value;
	var orgId = '${orgId}';
	
	$("#loading").attr('style','position:absolute; width:100%; height:450px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != "" && orgLocationId != null && orgLocationId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/changePrimaryAdmin?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(result) {
		        	location.replace(sourceleadUrl+"/enrolledOrgSummary?orgId="+orgId);
		        },
		    });
	 }
	
}
</script>



<script type="text/javascript">
function resendInvitation(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgId = '${orgId}';
	if(userId != null && userId != ""){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgId="+orgId, 
			        success: function(result) {
			        	
			        	document.getElementById("mailSendSuccessMsg").style.display = 'block';
			         	$("#mailSendSuccessMsg").fadeIn(150);
			        	$('#mailSendSuccessMsg').delay(1000).fadeOut('slow'); 
			        },
			    });
		 }
	}
  
</script>


<script>
function pagination(){
    $("#example").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<script>
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
</script>			
			
<script type="text/javascript">
 function validateUserEmail() {
	 var emailId = document.getElementById("email").value; 
	 var orgLocationId = document.getElementById("registeredLocation").value;
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(emailId != null && emailId != "" && orgLocationId != "" && orgLocationId != null){
		 $("#add_user").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateRegisteredUserEmail?${_csrf.parameterName}=${_csrf.token}&emailId="+emailId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#userEmailDiv').find('p.jquery_form_error_message').remove();
						  $("input#email").after("<p class='jquery_form_error_message'>"+emailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',emailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#userEmailDiv').find('p.jquery_form_error_message').remove(); 
                         $("#add_user").removeAttr("disabled");
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
         $('body').on('submit', '#adduser_form', function(e){
        	  if ($('#adduser_form').validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var formData = getFormData('adduser_form');
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		var registeredOrgLocationId = document.getElementById("registeredLocation").value;
	          		
	          		 $("#add_user").attr("disabled", "disabled");
	          		 $("#userLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	data : formData,
	     		    	 url: sourceleadUrl+"/createUesrBySourceleadAdmin?${_csrf.parameterName}=${_csrf.token}"+"&registeredOrgLocationId="+registeredOrgLocationId, 
	     		        success: function(data) {
	     		        	if(data != null && data != '' && data == 'success'){
	     		        		$("#userLoading").removeAttr("style");
	     	   	        		getUserList();
	     		        	}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         
         $('body').on('blur', '#adduser_form', function() {
     		$("#adduser_form").showHelpOnFocus();
     		$("#adduser_form").validateOnBlur(false, validationSettings);
     	});
      
</script> 



<script type="text/javascript">
 function getRegisteredOrgRoles() {
	 var sourceleadUrl ='${sourceleadUrl}';
	 var orgLocationId = document.getElementById("registeredLocation").value;
	 if(orgLocationId != null && orgLocationId != '' && orgLocationId != 'undefined'){
	 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRegisteredOrgRoles?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Role --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.displayName+"</option>";
					}
		        	$("#roleTypeId").empty();
			        $("#roleTypeId").append(output);
			        $('#roleTypeId').trigger("chosen:updated");
		        },
		    });
	 }
 }
</script>



<script type="text/javascript">
	function flushUser() {
		$("#add_user").removeAttr("disabled");
		$('#firstName').val('');
		$('#lastName').val('');
		$('#email').val('');
		$('#roleTypeId').val('');
		$(".error").removeClass("error");
		$('#firstNameDiv').find('p.jquery_form_error_message').remove();
		$('#lastNameDiv').find('p.jquery_form_error_message').remove();
		$('#userEmailDiv').find('p.jquery_form_error_message').remove();
		$('#userRoleDiv').find('p.jquery_form_error_message').remove();
		
	}
</script>


<script>
function removeAddUserPopUp(){
	 document.getElementById("addNewUserPopup").removeAttribute("class")
	 document.getElementById('addNewUserPopup').className = 'collapse-div-adduser orgsummaryuser-pop orgsummary-adduser col-md-4'
}
</script>


	<script type="text/javascript">
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

});
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
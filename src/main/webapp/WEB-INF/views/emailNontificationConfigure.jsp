<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <script type="text/javascript">
		window.onload = function () {
			//getUserModules();
			getEmailList();
			var ticketUUID = '${ticketUUID}';
		 };
	</script>

<div id="emailNotifications" class="tab-pane fade"> 
<div id="recommLoading"></div>
                    <div class="accountsettings-tab">
                    
                    <div class="tab-content panel-body">
                            <div class="clearfix mb">
                                <div class="pull-left">
                                    <h4 id="emailHeading">Email Settings</h4>
                                </div>
                            </div>
                            
            <form id="email_configuration_form"  name="email_configuration_form" method="post">
                     <div class="clearfix row">
			
					<div class="col-md-5 minHeight">
		             	 <label>Email<span class="text-danger">&nbsp;*</span></label>
		             	 <input class="form-control" value="${emailSceconf.emailId}" name="emailId" id="emailId"  field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter email" type="text"> 
	                </div> 
		           
		            <div class="col-md-5 minHeight">
		                <label>Password<span class="text-danger">&nbsp;*</span></label>
		                <input class="form-control" value="${emailSceconf.emailPassword}" name="EmailPassword" id="passwordIdddddd"   field-name="Password" data-validation="required validate_Space" placeholder="Enter password" type="password"> 
		            </div>
            
		            <div class="col-md-5 minHeight">
		               <!--  <h4>Sending emails</h4>
		                <p>Sever Type</p> -->
		                    <label>SMTP Mail Server name<span class="text-danger">&nbsp;*</span></label>
		                    <input class="form-control" value="${emailSceconf.serverName}" id="PortId" name="serverName" data-validation="required validate_Space"  field-name="SendServer" placeholder="e.g.domain@gmail.com" type="text">                 
		                </div>
		                <div class="col-md-5 minHeight">
		                    <label>Port<span class="text-danger">&nbsp;*</span></label>
		                    <input class="form-control" value="${emailSceconf.portNumber}" id="PortId" name="portNumber" data-validation="required validate_Space" field-name="SendPort" placeholder="123" type="text">                
		                </div> 
		                 <div class="col-md-5 minHeight">
		                  <label class="clearfix">Encryption Type<span class="text-danger">&nbsp;*</span></label>
		                <select id="encryptId" name="encryptType" field-name="encryptType" data-validation="required" class="sel">
						<option
										${emailSceconf.encryptionType  == 'SSL' ? 'selected' : ''}>SSL</option>
									<option
										${contractsTemplate.templateType  == 'TLS' ? 'selected' : ''}>TLS</option>
						</div> 
					</select>               
		            </div>
		            <div class="clearfix"></div>
			<div class="clearfix">
					<div class="pull-right mb">
						
						<input type="hidden" name="id" id="id" value="${emailSceconf.email_UUID}"/>
							<button type="button" class="btn btn-success btnSuccess mr" onClick="closeEmailNotifications();saveEmailNotification();">Save</button>
							<button type="button" class="btn btn-Default" onClick="closeEmailNotifications();">Cancel</button>
						</div>
					</div>
            </form>
                            
            
			</div>   
			
			<div class="tab-content panel-body">
                     <a href="javascript:void(0)" class="btn btn-primary pull-right" onClick="addEmailPopUp();"><i class="fa fa-plus paddingRight"></i>Add Email</a>   
                     <%-- <a class="btn btn-primary pull-right" title="Add Mail" href="${appPath}/addMailPopUp" ><i class="fa fa-plus paddingRight"></i>Add Email</a> 
                      --%><div id="emailList"></div>  
            </div>
			   
            </div> 
                    
</div>
<!-- -email popup statrs -->
<%-- <form:form id="email_from_form" name="email_from_form"  method="post" >

<div class="modal fade in emailfromMail" id="emailfromMail">
	<div class="modal-dialog">
		<div class="modal-content clearfix">
			<div class="modal-header">
				<a href="javascript:void(0)" class="close" onClick="closeEmailNotifications()">&times;</a>
				<h4 class="modal-title">Add from Email</h4>
			</div>
			<div class="modal-body">
			
				<div class="col-md-12 minHeight">
                <label>Email<span class="text-danger">&nbsp;*</span></label>
                <input class="form-control" value="" name="email" id="fromMail"  field-name="fromMail" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter EMail" type="text"> 
                 
            </div>
            
            <div class="col-md-12 minHeight">
          <select class="chosen-select form-control" multiple data-placeholder="Select Module" name="emailModules" id="emailTabs">
            <c:forEach var="option" items="${modulesList}">
				<option value="${option.id}">${option.tabName}</option>
			</c:forEach>
          </select>
        </div>
             <!-- <div class="col-md-12 minHeight">
		          <label class="clearfix">Module<span class="text-danger paddingLeft">&nbsp;*</span></label>
		          <div class="clearfix">
		            <select class="form-control selectpicker" data-actions-box="true" data-live-search="true" id="emailTabs" name="emailModules" field-name="Module" data-validation="required" multiple>
		            <option selected value="">Select Module</option>
		            </select>
		          </div>
		        </div> -->
		        <div class="clearfix"></div>
			</div>
			<div class="modal-footer clearfix">
				<div>
					<button type="button" class="btn btn-success btnSuccess mr" onClick="closeEmailNotifications();saveConfFromMail();">Save</button>
					<button type="button" class="btn btn-Default">Cancel</button>
				</div>
			</div>
		</div>
	</div>
</div>

 <div id="deleteFromMailDiv" class="datatable_filter"> </div>

</form:form> --%>
<!-- -- email popup ends -->
<script type="text/javascript">
//Email Notifications Script
function addEmail() {
	$(".comMask").fadeIn();
	$(".emailfromMail").fadeIn();
}
function closeEmailNotifications(){
	$(".comMask").fadeOut();
	$(".emailfromMail").fadeOut();
}
</script>

<script>
function saveEmailNotification(){
	if ($('#email_configuration_form').validate(false, validationSettings)) {
		
			$("#save_title").attr("disabled", "disabled");
			$("#save_title").addClass('button').val('Processing..');
		//var formData = $($('#user_manuals_form')[0]).serialize();
		var formData = getFormData1("email_configuration_form");
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/saveEmailNotification?"+tokenParamName+"="+tokenValue,
			  	success : function(result) {
			  		
		    			//location.replace(sourceleadUrl+ "/userManuals");			 			
		    		
			},
		});
		
	}else {
		return false;
	}

}

</script>

<script>
function saveConfFromMail(){
	if ($('#email_from_form').validate(false, validationSettings)) {
		
			$("#save_title").attr("disabled", "disabled");
			$("#save_title").addClass('button').val('Processing..');
		var formData = getFormData1("email_from_form");
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/saveConfigurationFromMail?"+tokenParamName+"="+tokenValue,
			  	success : function(result) {
			  		
		    			//location.replace(sourceleadUrl+ "/userManuals");			 			
		    		
			},
		});
		
	}else {
		return false;
	}

}

</script>
<script>
function saveEditConfFromMail(){
	if ($('#email_from_form').validate(false, validationSettings)) {
		
			$("#save_title").attr("disabled", "disabled");
			$("#save_title").addClass('button').val('Processing..');
		var formData = getFormData1("email_from_form");
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/saveConfigurationFromMail?"+tokenParamName+"="+tokenValue,
			  	success : function(result) {
			  		
		    			//location.replace(sourceleadUrl+ "/userManuals");			 			
		    		
			},
		});
		
	}else {
		return false;
	}

}

</script>

<script>
function getEmailList(){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getConfigFromMailList?${_csrf.parameterName}=${_csrf.token}", 	   
		    success: function(response) {
		    	var result = "";
		    	
		    	result  = result + '	 <div class="clearfix styleTables">';
		    	result  = result + '	<table>								';
		    	result  = result + '<thead>									';
		    	result  = result + '<tr>									';
		    	result  = result + '<th>Email</th>							';
		    	result  = result + '<th>Selected Actions</th>				';
		    	result  = result + '<th>Actions</th>						';
		    	result  = result + '</tr>									';
		    	result  = result + '</thead>								';
		    	result  = result + '<tbody>									';
		    	 for (var i = 0; i < response.data.length; i++) { 
					 var report = response.data[i];
					 
				result  = result + '	 <tr>                                                                                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+report.email+'</div></td>                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+report.emailModules+'</div></td>                                ';
     			result  = result + '	<td>                                                                                                 ';
     			result  = result + '	<div class="tblminwidth-100">                                                                        ';
     			result  = result + '	<ul class="list-unstyled list-inline">                                                               ';
     			result  = result + '	<li>                                                                                                 ';
				result  = result + '	<label class="switch mt10">                                                                          ';
				result  = result + '	 <input type="checkbox" checked>                                                                     ';
				result  = result + '	 <span class="slider round"></span>                                                                  ';
				result  = result + '	</label>                                                                                             ';
     			result  = result + '	</li>                                                                                                ';
     			result  = result + '	<li><a class="fa fa-pencil pull-left ml" title="Edit" onclick="editFromMail(\''+report.email_UUID+'\');"></a> </li>';
     			result  = result + '	<li><a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteFromMail('+report.id+');"></a></li>';
     			result  = result + '	</ul>                                                                                                ';
     			result  = result + '	</div>                                                                                               ';
     			result  = result + '	</td>                                                                                                ';
     			result  = result + '	</tr>                                                                                                ';
		    	 }
		    	
		    	 $("#emailList").empty();
	        	 $("#emailList").append(result);
	        	 
		    },
     });
	
}
</script>
<script type="text/javascript">
function deleteFromMail(emailId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>.Do you want to delete?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteEmailFromMail('+emailId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#deleteFromMailDiv").append(result);
	$("#remove_user_pop").slideDown(200);
}

function deleteEmailFromMail(emailId){
	alert("hello")
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/deleteEmailFromMail?${_csrf.parameterName}=${_csrf.token}&emailId="+emailId,
		
		success : function(response) {
			
			if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
    		    showError(response.responseMessage);
    	  	} else if (response.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (response.response === "SUCCESS") {
    			 $("#deleteFromMailDiv").empty();
 			 	$(".project_inn_mask").fadeOut(200);
 			 	getUserManualList();
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
			
		}
	});
}

function cancelTermination(){
	$("#deleteFromMailDiv").empty();
	 $(".project_inn_mask").fadeOut(200);
}


 </script>
 <div id="popUpEmailConfigDiv" class="modal editContactDiv" style='display: none; top:15%;'></div>
 <script>
 function editFromMail(emailUUID){
	 
	 $.ajax({	type : "POST",
			url : sourceleadUrl+ "/getEmailConfigById?"+tokenParamName+"="+tokenValue+"&emailUUID="+emailUUID,
			success : function(response) {
				
				if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
        		    showError(response.responseMessage);
        	  	} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			
        			var email = "";
        			var emailModules = "";
    				
    				if(response.object.email !=null && response.object.email !='undefined' && response.object.email !=''){
    					email = response.object.email;
    				}
    				if(response.object.emailModules !=null && response.object.emailModules !='undefined' && response.object.emailModules !=''){
    					emailModules = response.object.emailModules;
    				}
    				
    				alert(emailModules)
    				
    				var result = "";
    				
    				
    		result = result  +'<form id="editEmail_form"  name="editEmail_form" method="post">                                                                                                                                             ';
    	result = result  +'<div class="modal fade in editemailfromMail" id="editemailfromMail" style="display:block;">                                                                                                                                                           ';
    	result = result  +'<div class="modal-dialog">                                                                                                                                                                                             ';
    	result = result  +'<div class="modal-content clearfix">                                                                                                                                                                                   ';
    	result = result  +'<div class="modal-header">                                                                                                                                                                                             ';
    	result = result  +'<a href="javascript:void(0)" class="close" onClick="closeEditNotification()">&times;</a>                                                                                                                             ';
    	result = result  +'<h4 class="modal-title">Add from Email</h4>                                                                                                                                                                            ';
    	result = result  +'</div>                                                                                                                                                                                                                 ';
    	result = result  +'<div class="modal-body">                                                                                                                                                                                               ';
    	result = result  +'	                                                                                                                                                                                                                      ';
    	result = result  +'<div class="col-md-12 minHeight">                                                                                                                                                                                      ';
    	result = result  +'<label>Email<span class="text-danger">&nbsp;*</span></label>                                                                                                                                                           ';
    	result = result  +' <input class="form-control" value="'+email+'" name="email" id="fromMail"  field-name="fromMail" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter EMail" type="text">    ';
    	result = result  +'                                                                                                                                                                                                                       ';
    	result = result  +' </div>                                                                                                                                                                                                                ';
    	result = result  +'                                                                                                                                                                                                                       ';
    	result = result  +' <div class="col-md-12 minHeight">                                                                                                                                                                                  ';
    	result = result  +' <label class="col-md-3">Modules</label> '  ;
    	result = result  +' <select class="chosen-select form-control" multiple data-placeholder="Select Module" name="emailModules" id="emailTabs">                                                                                              ';
    	
    	result = result  +'<c:forEach var="modules" items="${modulesNamesList}">                                       ';
    	result = result  +'<c:set var = "NUM" value = "${0}"/>                                                    ';
    	/* 
    	result = result  +'<c:forEach var="existedModules" items="'+emailModules+'">                                  ';
		result = result  +'<c:if test="${existedModules eq modules}">                                             ';
		result = result  +'<c:set var = "NUM" value = "${1}"/>                                                    ';
		result = result  +'<option  selected value="${modules}">${modules}</option>                       ';
		result = result  +'</c:if>                                                                                ';
		result = result  +'</c:forEach>                                                                           '; */
		result = result  +'<c:if test="${NUM == 0}">                                                              ';
		result = result  +'	<option  value="${modules}">${modules}</option>                               ';
		result = result  +'</c:if>                                                                                ';
     	result = result  +'</c:forEach>                                                                            ';
    	
    	/* result = result  +' <c:forEach var="option" items="${modulesList}">                                                                                                                                                                       ';
    	result = result  +'<option value="${option.id}">${option.tabName}</option>                                                                                                                                                                ';
    	result = result  +'</c:forEach>                                                                                                                                                                                                           ';
    	 */
    	 result = result  +'</select>                                                                                                                                                                                                              ';
    	result = result  +'</div>                                                                                                                                                                                                                 ';
    	result = result  +'<div class="clearfix"></div>                                                                                                                                                                                           ';
    	result = result  +'</div>                                                                                                                                                                                                                 ';
    	result = result  +'<div class="modal-footer clearfix">                                                                                                                                                                                    ';
    	result = result  +'<div>                                                                                                                                                                                                                  ';
    	result = result  +'<button type="button" class="btn btn-success btnSuccess mr" onClick="closeEmailNotifications();saveEditConfFromMail();">Save</button>                                                                                      ';
    	result = result  +'<button type="button" class="btn btn-Default">Cancel</button>                                                                                                                                                          ';
    	result = result  +'</div>                                                                                                                                                                                                                 ';
    	result = result  +'</div>                                                                                                                                                                                                                 ';
    	result = result  +'</div>                                                                                                                                                                                                                 ';
    	result = result  +'</div>                                                                                                                                                                                                                 ';
    	result = result  +'</div>                                                                                                             ';
    		result = result  +'</form>';
    		$("#popUpEmailConfigDiv").empty();
    		$("#popUpEmailConfigDiv").append(result);
        		}else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
				
			}
	 
	 
	 });
	 $(".sideMask").fadeIn();
	 $("#popUpEmailConfigDiv").show();
 }
 </script>
  <script>
 function closeEditNotification(){
	 $(".sideMask").fadeOut();
	 $("#popUpEmailConfigDiv").fadeOut();

 }
 </script>
 <div id="emailfromMail" style="display:none" class="popOut">
   <jsp:include page="addMailPopUp.jsp"></jsp:include>
</div>
 <script>
 function addEmailPopUp(){
	 alert("hello")
	 $(".sideMask").fadeIn();
	 $("#emailfromMail").show();
	 addEmailPop();
 }
 </script>
 <script type="text/javascript">
function addEmailPop(){
	$.ajax({	type : "POST",
		url : sourceleadUrl+ "/addMailPopUp?"+tokenParamName+"="+tokenValue,
		success : function(response) {
			 $("#enrolledLocDiv").removeAttr("style");
	    	   $("#enrolledLocDiv").attr("style","display : block");
	    	   $("#emailfromMail").empty();
	    	   $("#emailfromMail").append(response);
		}
	 }); 
}
</script>


          

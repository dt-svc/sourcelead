<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <script type="text/javascript">
		window.onload = function () {
			getEmailList();
			var status ='${emailSceconf.verifiedStatus}';
			if(status == "Y"){
				configuredMail();
			}
			
		 };
	</script>

<div id="emailNotifications" class="tab-pane fade"> 
<div id="recommLoading"></div>
                    <div class="accountsettings-tab">
                    
                    <div class="tab-content panel-body noBar">
                    <div id="emailConfiguredprev" >
                     <div id="ConfigLoading"></div>
                       <div class="clearfix"><h4 class="pull-left" id="emailHeading">Email Settings</h4></div>
                            
            <form id="email_configuration_form"  name="email_configuration_form" method="post">
                     <div class="clearfix row">
			
					<div class="col-md-5 minHeight">
		             	 <label>Email<span class="text-danger">&nbsp;*</span></label>
		             	 <input class="form-control" value="${emailSceconf.emailId}" name="emailId" id="emailId"  field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter email" type="text"> 
	                </div> 
		           
		            <div class="col-md-5 minHeight">
		                <label>Password<span class="text-danger">&nbsp;*</span></label>
		                <input class="form-control" value="${emailSceconf.emailPassword}" name="EmailPassword" id="passwordId"   field-name="Password" data-validation="required validate_Space" placeholder="Enter password" type="password"> 
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
						</select>  
						</div> 
					             
		            </div>
		            
			<div class="clearfix col-md-10 noPad">
					<div class="pull-right mb">
						<input type="hidden" name="id" id="id" value="${emailSceconf.email_UUID}"/>
							<button type="button" id="save" class="btn btn-primary pull-right" onClick="saveEmailNotification();">Save</button>
							<!--  <button type="button" class="btn btn-primary btnsStyles" data-dismiss="modal" onClick="">Cancel</button> -->
						</div>
					</div>
					
            </form>
                            
			</div>  
			</div> 
			<div id="configuredMailDiv"></div> 
			<div id="configuredMailDivTwo"></div> 
			<div class="clearfix">
                  <div class="reportHead clearfix">
                  <h5 class='pull-left ml mt'>Default Email Configuraiton</h5>
                     <ul class="pull-right list-unstyled list-inline">
                     	<li><a href="javascript:void(0)" class="action newAdBtn mr5" onClick="addEmailPopUp();">+ Email</a></li>
                     </ul>
                  </div> 			
                 <div id="emailList"></div>  
            </div>
            
            <!--  Email parsing -->
            
             <div class="clearfix mt">
                  <div class="reportHead clearfix">
                  <h5 class='pull-left ml mt'>Parsing Email Configuraiton</h5>
                     <ul class="pull-right list-unstyled list-inline">
                     	<li><a id= "parserEmailAdd" href="javascript:void(0)" class="action newAdBtn mr5" onClick="addEmailparsingPopUp();">+ Add</a></li>
                     </ul>
                  </div> 			
                 <div id="emailparsingList"></div>  
            </div>
            
            
            <!--  Email parsing -->
            
			   
            </div> 
                    
</div>

<div id="deleteFromMailDiv" class="datatable_filter"> </div>
<div id="deleteParserEmailDiv" class="datatable_filter"> </div>

<div class="success_pop" id="selector2" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgConfigDiv"></span>
</div>

<script type="text/javascript">
//Email Notifications Script
function addEmail() {
	$(".mask").show();
	$(".emailfromMail").fadeIn(100);
}
/* function closeEmailNotifications(){
	$(".mask").hide();
	$(".emailfromMail").fadeOut(100);
} */
</script>

<script>
function saveEmailNotification(){
	if ($('#email_configuration_form').validate(false, validationSettings)) {
		
			//$("#save").attr("disabled", "disabled");
			//$("#save_title").addClass('button').val('Processing..');
		//var formData = $($('#user_manuals_form')[0]).serialize();
		var formData = getFormData1("email_configuration_form");
		$( "#save" ).addClass('button').val('Processing..');
		$("#ConfigLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:10000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/saveEmailNotification?"+tokenParamName+"="+tokenValue,
			  	success : function(result) {
			  		if(result=="success"){
			  			configuredMail();
			  		}else if(result=="failure"){
			  			$("#ConfigLoading").removeAttr("style");
			  			alert("configured mail is wrong.please update the right email")
			  			//$("#msgConfigDiv").html("Configured Mail is wrong.Please update the correct Mail");
			  		}
		    		
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
			  			if(result=="success"){
			  				getEmailList();
			  				closeEmailNotifications();
			  			}
			  		
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
		    	
		    	result  = result + '	 <div class="clearfix project_wrap_table project_tabs_inn_table_a">';
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
					 var emailModules = "";
					 if(report.emailModules != null && report.emailModules != '' && report.emailModules != 'undefined'){
						 emailModules = report.emailModules;
					 }else{
						 emailModules = "--";
					 }
				result  = result + '	 <tr>                                                                                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+report.email+'</div></td>                                ';
     			result  = result + '	<td><div class="tbldata-dotdot">'+emailModules+'</div></td>                                ';
     			result  = result + '	<td>                                                                                                 ';
     			result  = result + '	<div class="tbldata-dotdot">                                                                        ';
     			result  = result + '	<ul class="list-unstyled list-inline noMar">                                                               ';
     			result  = result + '	<li>                                                                                                 ';
				result  = result + '	<label class="switch" style="position:relative; top:5px;">                                                                          ';
				result  = result + '	<input onclick="changeDefaultemailConfig(this, '+report.id+')" type="checkbox" '+(report.status ? 'checked' : '')+'/>                                                                    ';
				result  = result + '	 <span class="slider round"></span>                                                                  ';
				result  = result + '	</label>                                                                                             ';
     			result  = result + '	</li>                                                                                                ';
     			result  = result + '	<li><a class="fa fa-pencil pull-left ml" title="Edit" onclick="editEmailPopUp(\''+report.email_UUID+'\');"></a> </li>';
     			result  = result + '	<li><a class="glyphicon glyphicon-trash text-danger" title="Delete" onclick="deleteFromMail('+report.id+');"></a></li>';
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
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p style='text-align: center;'>.Do you want to delete?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteEmailFromMail('+emailId+')"><input class="u_can_color" type="button"  value="Cancel" onclick="cancelDeleteMail()"></b>';
	result = result +"	</div>";
	$("#deleteFromMailDiv").append(result);
	 $(".mask").show();
	$("#remove_user_pop").slideDown(200);
}

function cancelDeleteMail(){
		$("#deleteFromMailDiv").empty();
		 $(".mask").hide();
	}
	
function deleteEmailFromMail(emailId){
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
 			 	$(".mask").hide();
 			 	getEmailList();
    		}else if (response.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
			
		}
	});
}


function changeDefaultemailConfig(ele, id) {
    $.post(sourceleadUrl+"/changeDefaultEmailConfig", {id: id, defaultAccount: $(ele).is(":checked")}, function(data) {
       // showError(data.responseMessage);
        getEmailList();
    });
}

 </script>
 
 
 <div id="popUpEmailConfigDiv" class="popupWrap popupWrapFixed editContactDiv"></div>
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
    				
    				
    				var result = "";
    				
    				
    		result = result  +'<form id="editEmail_form"  name="editEmail_form" method="post">                                                                                                                                             ';
    	result = result  +'<div class="popupWrap popupWrapFixed" id="editemailfromMail" style="display:block;">                                                                                                                                                           ';
    	
    	result = result  +'<div class="popupHead">Add from Email <i class="closePop pull-right popClose" onclick="closeEditNotification();"></i></div>                                                                                                                                                                                             ';

    	result = result  +'<div class="popupContent">                                                                                                                                                                                               ';
    	result = result  +'	                                                                                                                                                                                                                      ';
    	result = result  +'<div class="col-md-12 minHeight">                                                                                                                                                                                      ';
    	result = result  +'<label>Email <span class="text-danger">&nbsp;*</span></label>                                                                                                                                                           ';
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
    	
    	result = result  +'<div class="popupFoot text-right">                                                                                                                                                                                    ';
    	result = result  +'<button type="button" class="slBtn md colorBtn" onClick="closeEmailNotifications();saveEditConfFromMail();">Save</button>                                                                                      ';
    	result = result  +'<button type="button" class="slBtn md popClose" onclick="closeEditNotification();">Cancel</button>                                                                                                                                                          ';
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
	 $(".mask").show();
	 $("#popUpEmailConfigDiv").fadeIn(100);
 }
 </script>
  <script>
 function closeEditNotification(){
	 $(".mask").hide();
	 $("#popUpEmailConfigDiv").fadeOut(100);

 }
 </script>
 <div id="emailfromMail" class="popupWrap popupWrapFixed">
   <jsp:include page="addMailPopUp.jsp"></jsp:include>
</div>

 <div id="emailparsingPopUp" class="popupWrap">
	 <jsp:include page="addParserMailPopUp.jsp"></jsp:include>
</div>


 <script>
 function addEmailPopUp(){
	 $(".mask").show();
	 $("#emailfromMail").show();
	 addEmailPop();
 }
 
 function addEmailparsingPopUp(){
	 $(".mask").show();
	 $("#emailparsingPopUp").show();
	 acceptAgreement();
	 $('#emailNotify').find('p.jquery_form_error_message').remove();
	 $('#passwordsnotify').find('p.jquery_form_error_message').remove();	
	 $(".colorBtn").removeClass("disableBtn");	  
	
	 var domainName = $('#emailId').val().split("@")[1];
	 if(typeof domainName != 'undefiend' && domainName != ""){
		 $('#parserEmailId').val("@"+domainName);
		 $('#parserPasswordId').val("");
	 }
 }
 
 /* function closeEmailNotifications() {
	 $(".mask").hide();
	 $("#emailparsingPopUp").hide();
 } */
 
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
<script>
 function editEmailPopUp(emailUUID){
	 $(".mask").show();
	 $("#emailfromMail").show();
	 editEmailPop(emailUUID);
 }
 </script>
 <script type="text/javascript">
function editEmailPop(emailUUID){
	$.ajax({	
		type : "POST",
		url : sourceleadUrl+ "/addMailPopUp?"+tokenParamName+"="+tokenValue+"&emailUUID="+emailUUID,
		success : function(response) {
			 $("#enrolledLocDiv").removeAttr("style");
	    	   $("#enrolledLocDiv").attr("style","display : block");
	    	   $("#emailfromMail").empty();
	    	   $("#emailfromMail").append(response);
		}
	 }); 
}
</script>


<script type="text/javascript">
function verifyEmails() {
	var email = document.getElementById("email").value;
	if(email != null && email != ''){
	      validateEmailForExistenceUniversally(email, '#email');
	}else{
		 $("#emailStatusIconDiv").find("i").remove();
   		 $("#emailStatusIconDiv").append('<i class="fa fa-check text-muted" style="color:#f6f6f6"></i>');
	}
}

</script>
<script type="text/javascript">
function configuredMail() {
	$.ajax({	
		type : "POST",
		url : sourceleadUrl+ "/getConfiguredMail?"+tokenParamName+"="+tokenValue,
		success : function(response) {
			var email = "";
			var emailpassword = "";
			
			if(response.emailId !=null && response.emailId !='undefined' && response.emailId !=''){
				email =response.emailId;
			}
			if(response.emailPassword !=null && response.emailPassword !='undefined' && response.emailPassword !=''){
				emailpassword =response.emailPassword;
			}                                                                                                                                                                                                                                         
			                                                                                                                                                                                                                                          
			var result = "";                                                                                                                                                                                                                          
		result = result  +'<div id="emailConfiguration" class="tab-pane">                                                                                                                                                                        ';
		result = result  +'<div id="recommLoading"></div>                                                                                                                                                                                             ';
		result = result  +'<div class="accountsettings-tab">                                                                                                                                                                                          ';
		result = result  +'<div class="tab-content panel-body">                                                                                                                                                                                       ';
		result = result  +'<form id="email_configuration_form"  name="email_configuration_form" method="post">                                                                                                                                        ';
        result = result  +'<div class="clearfix row">                                                                                                                                                                                                 ';
		result = result  +'<div class="col-md-5 minHeight">                                                                                                                                                                                           ';
	    result = result  +'<label>Email<span class="text-danger">&nbsp;*</span></label>                                                                                                                                                               ';
	    result = result  +'<input class="form-control" value="'+email+'" name="emailId" id="emailId"  field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter email" type="text"> ';
        result = result  +'</div>                                                                                                                                                                                                                     ';
	    result = result  +'<div class="col-md-5 minHeight">                                                                                                                                                                                           ';
	    result = result  +'<label>Password<span class="text-danger">&nbsp;*</span></label>                                                                                                                                                            ';
	    result = result  +'<input class="form-control" value="'+emailpassword+'" name="EmailPassword" id="passwordId"   field-name="Password" data-validation="required validate_Space" placeholder="Enter password" type="password">     ';
	    result = result  +'</div>';
	    result = result  +'<div class="col-md-2 minHeight">';
	    result = result  +'<label>&nbsp;</label>';
	    result = result  +'<div>';
	    result = result  +'<button type="button" class="pull-left btn btn-success btnSuccess mr" onClick="editConfiguredMail('+response.id+');">Edit</button>';
	    result = result  +'<i class="pull-left mt2 fa-2x fa fa-check-circle text-success"></i>';
	    result = result  +'</div>';
	    
	    result = result  +'</div>                                                                                                                                                                                                                     ';
	    result = result  +'  </form>                                                                                                                                                                                                                  ';
		result = result  +'  </div>                                                                                                                                                                                                                   ';
		result = result  +'  </div>                                                                                                                                                                                                                   ';
		result = result  +'  </div>';
		
		//$("#emailConfiguredprev").hide();
		document.getElementById("emailConfiguredprev").style.display="none";
		document.getElementById("configuredMailDivTwo").style.display="none";
		document.getElementById("configuredMailDiv").style.display="block";
		$("#configuredMailDiv").empty();
		$("#configuredMailDiv").append(result);
		
		
			
		}
	});
}
</script>
 <!-- <script type="text/javascript">
 function editConfiguredMail(emailId){
	 document.getElementById("emailConfiguredprev").style.display="block"; 
	 document.getElementById("configuredMailDiv").style.display="none";  
 }
 
 </script> -->

 <script type="text/javascript">
function editConfiguredMail() {
	$.ajax({	
		type : "POST",
		url : sourceleadUrl+ "/getConfiguredMail?"+tokenParamName+"="+tokenValue,
		success : function(response) {
			
			var email = "";
			var emailpassword = "";
			
			if(response.emailId !=null && response.emailId !='undefined' && response.emailId !=''){
				email =response.emailId;
			}
			if(response.emailPassword !=null && response.emailPassword !='undefined' && response.emailPassword !=''){
				emailpassword =response.emailPassword;
			}
			
			
			var result = "";                                                                                                                                                                                                                          
			result = result  +'<div id="emailConfigurationtwo" class="tab-pane ">                                                                                                                                                                        ';
			result = result  +'<div id="recommLoading"></div>                                                                                                                                                                                             ';
			result = result  +'<div class="accountsettings-tab">                                                                                                                                                                                          ';
			result = result  +'<div class="tab-content panel-body">                                                                                                                                                                                       ';
			result = result  +'<form id="email_configurationTwo_form"  name="email_configuration_form" method="post">                                                                                                                                        ';
	        result = result  +'<div class="clearfix row">                                                                                                                                                                                                 ';
			result = result  +'<div class="col-md-5 minHeight">                                                                                                                                                                                           ';
		    result = result  +'<label>Email<span class="text-danger">&nbsp;*</span></label>                                                                                                                                                               ';
		    result = result  +'<input class="form-control" value="'+email+'" name="emailId" id="emailId"  field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" placeholder="Enter email" type="text"> ';
	        result = result  +'</div>                                                                                                                                                                                                                     ';
		    result = result  +'<div class="col-md-5 minHeight">                                                                                                                                                                                           ';
		    result = result  +'<label>Password<span class="text-danger">&nbsp;*</span></label>                                                                                                                                                            ';
		    result = result  +'<input class="form-control" value="'+emailpassword+'" name="EmailPassword" id="passwordId"   field-name="Password" data-validation="required validate_Space" placeholder="Enter password" type="password">     ';
		    result = result  +' </div>                     ';
		 result = result  +'<div class="col-md-5 minHeight">                                                                                                                                                                                          ';
         result = result  +'<label>SMTP Mail Server name<span class="text-danger">&nbsp;*</span></label>                                                                                                                                              ';
         result = result  +'<input class="form-control" value="'+response.serverName+'" id="PortId" name="serverName" data-validation="required validate_Space"  field-name="SendServer" placeholder="e.g.domain@gmail.com" type="text">           ';
         result = result  +'</div>                                                                                                                                                                                                                    ';
         result = result  +'<div class="col-md-5 minHeight">                                                                                                                                                                                          ';
         result = result  +'<label>Port<span class="text-danger">&nbsp;*</span></label>                                                                                                                                                               ';
         result = result  +'<input class="form-control" value="'+response.portNumber+'" id="PortId" name="portNumber" data-validation="required validate_Space" field-name="SendPort" placeholder="123" type="text">                               ';
       	 result = result  +' </div>                                                                                                                                                                                                                    ';
         result = result  +' <div class="col-md-5 minHeight">                                                                                                                                                                                         ';
         result = result  +' <label class="clearfix">Encryption Type<span class="text-danger">&nbsp;*</span></label>                                                                                                                                  ';
         result = result  +' <select id="encryptId" name="encryptType" field-name="encryptType" data-validation="required" class="sel">                                                                                                               ';
         if(response.encryptionType == 'SSL'){
        	 result = result  +'<option value="' +response.encryptionType+ '" selected>'+response.encryptionType+' </option>';
			}else{
				 result = result  +'<option value="' +response.encryptionType+ '" selected>'+response.encryptionType+' </option>';
			}
		 result = result  +'	</select>                                                                                                                                                                                                                 ';
		 result = result  +'	</div>                                                                                                                                                                                                                    ';
		    
		    
		    result = result  +'<div class="pull-right mb">';
		    result = result  +'<button type="button" class="btn btn-success btnSuccess mr" onClick="updateEmailNotification();">Save</button>';
		    result = result  +' <button type="button" class="btn btn-primary btnsStyles mr" data-dismiss="modal" onClick="configuredMail();">Cancel</button>';
			
		    result = result  +'</div>';
		    
		    result = result  +'</div>                                                                                                                                                                                                                     ';
		    result = result  +'  </form>                                                                                                                                                                                                                  ';
			result = result  +'  </div>                                                                                                                                                                                                                   ';
			result = result  +'  </div>                                                                                                                                                                                                                   ';
			result = result  +'  </div>';
			document.getElementById("configuredMailDiv").style.display="none";
			document.getElementById("configuredMailDivTwo").style.display="block";
			$("#configuredMailDivTwo").empty();
			$("#configuredMailDivTwo").append(result);
			
		}
	});
}
</script> 

<script>
function updateEmailNotification(){
	if ($('#email_configurationTwo_form').validate(false, validationSettings)) {
		
			$("#save_title").attr("disabled", "disabled");
			$("#save_title").addClass('button').val('Processing..');
		//var formData = $($('#user_manuals_form')[0]).serialize();
		var formData = getFormData1("email_configurationTwo_form");
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/saveEmailNotification?"+tokenParamName+"="+tokenValue,
			  	success : function(result) {
			  		if(result=="success"){
			  			configuredMail();
			  		}else if(result=="failure"){
			  			alert("configured mail is wrong.please update the right email")
			  		}
		    		
			},
		});
		
	}else {
		return false;
	}

}

</script>
		
		
<script type="text/javascript">
function closeEmailNotifications() {
	 $(".mask").hide();
	 $("#emailparsingPopUp").hide();
	 $("#email_parser_form")[0].reset();
	 $("#parserEmailId").val("");
	 $("#parserEmailUUID").val("");
	 $('#popnotify').find('p.jquery_form_error_message').remove();	
	 $('#portnotify').find('p.jquery_form_error_message').remove();
	 $('#parserEncryptId').val("");	
	 $('#usageType').val("");
			
}
</script>		
		
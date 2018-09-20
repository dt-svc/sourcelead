
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="orglocationSignatory" class="tab-pane fade form-horizontal">
  <div class="accountsettings-tab noMar">
	<div class="tab-content panel-body noPad noBor">
	
		 <div class="positionRelative reportHead clearfix">
		<ul class="pull-right list-unstyled list-inline">
			<li><a class="action newAdBtn mr5" href="javascript:void(0)" onclick="addOrgLocationSignatoryDiv()">+ Signatory</a></li>
		</ul>
		</div>
		
		<!--  Table starts -->
		<div class="require_wrap_table datatable_filter">
		<div id="orgLocationSignatoriesListDiv"></div>
        </div>
		<div class="clearfix"></div>
		<!--  Table starts ends -->
	
		
		<div id="signatoryFormDiv" class="popupWrap popupWrapFixed">
		<form:form class="login-form clearfix"  id="signatory_form"  name="signatory_form" method="post" > 
			  <div class="popupHead">Legal Signer Details <i class="closePop pull-right popClose" onclick="signatoryFormDiv();"></i></div>
			  	 <div id="loading"></div> 
			  	 <div id="popUp"></div> 
			  	 
			  	 <div class="popupContent"> 
				     <div class="compSignatoryHelpMes">
						Person who can sign legal binding documents on behalf of your organisation.
					</div>
				    
				      <c:if test="${!empty signatory}">
				      
					      <div class="clearfix mb">
						      <div class="form-group clearfix">
						         <c:if test="${!empty signatory.userId.lastName}">
						         <label class="noPad">User Name : </label> ${signatory.userId.firstName} ${signatory.userId.lastName}
						         </c:if>
						         
						         <c:if test="${empty signatory.userId.lastName}">
						         <label class="noPad">User Name : </label> ${signatory.userId.firstName} 
						         </c:if>
						      </div>
					      </div>
					      
					      <div class="clearfix mb">
						      <div class="form-group clearfix">
						         <label class="noPad">Email : </label> ${signatory.userId.email}
						      </div>
					      </div>
					      
					      <div class="clearfix mb">
						      <div class="form-group clearfix">
						         <label class="noPad">Role : </label> ${role}
						      </div>
					      </div>
					      
					      <c:if test="${!empty signatory.userId.mobilePhone}">
					      <div class="clearfix mb">
						      <div class="form-group clearfix">
						         <label class="noPad">Phone Number : </label> ${signatory.userId.mobilePhone}
						      </div>
					      </div>
					      </c:if>
					      
					      <c:if test="${!empty signatory.userId.ssn}">
					      <div class="clearfix mb">
						      <div class="form-group clearfix">
						         <label class="noPad">SSN : </label> ${signatory.userId.ssn}
						      </div>
					      </div>
					      </c:if>
					      
					      <c:if test="${!empty signatory.designationOtherType}">
					      <div class="clearfix mb">
						      <div class="form-group clearfix">
						         <label class="noPad">Designation : </label> ${signatory.designationOtherType}
						      </div>
					      </div>
					      </c:if>
					      
					      <c:if test="${!empty signatory.designation && empty signatory.designationOtherType}">
					      <div class="clearfix mb">
						      <div class="form-group clearfix">
						         <label class="noPad">Designation : </label> ${signatory.designation}
						      </div>
					      </div>
					      </c:if>
					      
				      
				      
				     </c:if> 

					     <div id="legalUserFormDiv"></div>
					     </div>
						<div class="popupFoot text-right">
							<input type="button" value="Save" id="save_signatory_button" onclick="saveSignatory();" class="slBtn md colorBtn">
							<button type="button" class="slBtn md popClose" onClick="signatoryFormDiv();">Cancel</button>
						</div>
				</form:form>
				     </div>
	 
		</div>
	</div>
  </div>


<script type="text/javascript">
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?&EmailId="+EmailId, 
		        success: function(json) {
		        		
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#firstName").val(json.firstName);
				        	$("#firstName").attr("readonly", "readonly");
				        	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
		        		}else{
		        			 $("#firstName").removeAttr("readonly", "readonly");
		        		}
		        		
		        		if(json.lastName != null && json.lastName != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        			$("#lastName").val(json.lastName);
		        		}else{
		        			 $("#lastName").removeAttr("readonly", "readonly");
		        		}
		        		
		        		if(json.mobilePhone != null && json.mobilePhone != ''){
		        			$("#phone").val(json.mobilePhone);
				        	$("#phone").attr("readonly", "readonly");
		        		}else{
		        			 $("#phone").removeAttr("readonly", "readonly");
		        		}
		        		
		        },
		    });
	 }else{
		 $("#firstName").attr("disabled", false);
		 $("#firstName").val('');
		 $("#firstName").removeAttr("readonly", "readonly");
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 $("#lastName").removeAttr("readonly", "readonly");
		 
		 $("#phone").attr("disabled", false);
		 $("#phone").val('');
		 $("#phone").removeAttr("readonly", "readonly");
	 }
 }
 </script>  
 
 
 <script type="text/javascript">
 function validateUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $("#save_signatory_button").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateUserEmail?&EmailId="+EmailId, 
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
                         $("#save_signatory_button").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>  
 
  
  <script>
  function validateLegalSigner(){
	  
	  getAllSignatory();
	  if('${signatoryUser}' == '${SourceLeadWeb_user.id}'){
		  $("#legalSigner").attr("checked", "checked");

	  } 
	  
// 	  if (document.getElementById('legalSigner').checked) {
// 		  $("#legalUserFormDiv").empty();
// 	  }else{
		  
// 	  }
  }
  </script>
 
 <script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>

<script>
function loadLeagelSignerForm(){
	$("#legalUserFormDiv").empty();
	var result = "";
	result = result +"<div class='popHeadTwo'>Create New Signer</div>";
	
	result = result +"<div class='clearfix mb'>";
	result = result +"<div class='clearfix' id='associateDiv'>";
	result = result +"<label class='noPad'>Associate<span class='text-danger'>&nbsp;*</span></label>";
	
	result = result +"<div class='clearfix' id='editSignerDiv' style='display:block'>";
	result = result +"<select class='chosen-select form-control' type='text' name='associateId' id='signatoryAssociate' field-name='Associate' onchange='validateAssociate();' data-validation='required'>";
	result = result +"</select>";
	result = result +"<input type='hidden' name='uuid' id='signatoryUUID' value=''>";
	result = result +"<input type='hidden' name='signatoryFromType' id='signatoryFromType' value='orgSettings'>";
	result = result +"</div>";
	
	result = result +"<div class='clearfix' id='showSignerDiv' style='display:none'>";
	result = result +"<input class='form-control' type='text' id='showAssociateName' readonly>";
	result = result +"<input type='hidden'  id='showAssociateId' value=''>";
	result = result +"</div>";
	
	result = result +"</div>";
	result = result +"</div>";
	
	result = result +"<div class='clearfix mb'>";
	result = result +"<div class='clearfix' id='authorizedTypeDiv'>";
	result = result +"<label class='noPad'>Authorized Type<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='clearfix'>";
	result = result +"<select multiple class='chosen-select form-control' type='text' name='authorizedType' placeholder='sdsd' id='authorizedType' field-name='Authorised Type' data-validation='required'>";
	result = result +'<option value="OfferLetters">Offer Letters</option>';
	result = result +'<option value="Contracts">Contracts</option>';
	result = result +"<option value='Cheques'>Cheque's</option>";
	result = result +"</select>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	result = result +"<div class='clearfix mb'>";
	result = result +"<div class='clearfix' id='firstNameDiv'>";
	result = result +"<label class='noPad'>Authorised Amount</label>";
	result = result +"<div class='clearfix'>";
	result = result +"<input class='form-control' type='text' name='authorizedAmount' id='signatoryAuthorisedAmount' field-name='Authorised Amount'  maxlength='10' data-validation-optional='true' data-validation=' validate_int validate_length length1-10'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	$("#legalUserFormDiv").append(result);
	getAssociatesForSignatory();
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

function getAssociatesForSignatory(){
	$.get( sourceleadUrl+ "/getAssociatesForSignatory?${_csrf.parameterName}=${_csrf.token}", function(data) {
        var output = "";
		output = output + "<option  value=''>-- Select Associate --</option>";
     	   for(var i=0; i<data.length;i++){
     		  var opt = data[i];
     		  if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
     			 output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+" "+opt.lastName+"</option>"; 
     		  }else{
     			 output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+"</option>";
     		  }
 				
     	   }
     	   $("#signatoryAssociate").empty();
     	  $("#signatoryAssociate").append(output);
     	  $("#signatoryAssociate").trigger("chosen:updated");
     	
     }); 
}

function validateAssociate()
{
	
	 var userId = document.getElementById("signatoryAssociate").value; 
	 var id = document.getElementById("signatoryUUID").value; 
	
	 
	 var sourceleadUrl ='${sourceleadUrl}';
		
		 if(userId != null &&  userId != "" &&  userId != 'undefined'){
			 $("#save_signatory_button").attr("disabled", "disabled");
			 
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/validateAssociate?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&id="+id,
			        success: function(data) {
			        
			        	if(data == true){
			        		  $('#associateDiv').find('p.jquery_form_error_message').remove();
							  $("#signatoryAssociate_chosen").after("<p class='jquery_form_error_message'>User with this name is already Existed</span>");
							 // document.getElementById("signatoryAssociate").setAttribute('record-exist','yes');
							 // document.getElementById("signatoryAssociate").setAttribute('record-exist-errorMsg','User with this name is Already Existed'); 
			        	}
			        	else{
			        		 $('#signatoryAssociate_chosen').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#associateDiv').find('p.jquery_form_error_message').remove(); 
			        		 $("#save_signatory_button" ).addClass('us-btn').val('Save');
	                         $("#save_signatory_button").removeAttr("disabled");
			        	}
			        },
			    });
		 }
	
	
 }

</script>


<script>
function getDesignationType(){
	var designation = document.getElementById("designation").value;
	if(designation == 'Other'){
		 document.getElementById("designationOtherDiv").style.display="";
		 $("#designationOtherType").removeAttr("data-validation-optional");
		 $("#designationOtherType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}else{
		 document.getElementById("designationOtherDiv").style.display="none";
		$("#designationOtherType").removeAttr("data-validation-optional");
		 $("#designationOtherType").attr("data-validation-optional","true");
		 $("#designationOtherType").removeAttr("data-validation");
		 $("#designationOtherType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}
}
</script>


<script type="text/javascript">
function saveSignatory(){
	var associate = $("#showAssociateId").val();
	if(associate !=null && associate!="" && associate!='undefined')
		{
		var associateId=associate;
		}else{
			var associateId=$("#signatoryAssociate").val();
		}
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }
      var sourceleadUrl ='${sourceleadUrl}';
  	  var formData = $($('#signatory_form')[0]).serialize();
  	$.post(sourceleadUrl+"/saveOrgLocationSignatory?"+tokenParamName+"="+tokenValue+"&associateId="+associateId,formData, function(result) {
  		if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
  		    showError(result.responseMessage);
  		    $("#loading").removeAttr('style');
  		}
    	else if(result.response === "AWKWARD"){
    		 location.href = "/errorPage";
    	}
    	else if(result.response === "SUCCESS"){
    		$("#loading").removeAttr('style');
    		getAllSignatory();
			signatoryFormDiv();
		}
    	else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			    $("#loading").removeAttr('style');
			}
  		
	}); 
    	  //saveOrgLocationSignatory(formData);
    
		    
      return true; 
}

	$('body').on('blur', '#signatory_form', function() {
		$("#signatory_form").showHelpOnFocus();
		$("#signatory_form").validateOnBlur(false, validationSettings);
	});
  </script>
  
  
  <script>
  function saveOrgLocationSignatory(formData){
		var sourceleadUrl = '${sourceleadUrl}';
		var uuid = '';
		if('${signatory.uuid}' != 'undefined'){
			uuid = '${signatory.uuid}'
		}
		
		   $.ajax({
		    	type: "POST",
		    	data : formData,
		    	 url: sourceleadUrl+"/saveOrgLocationSignatory?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
		        success: function(data) {
		        	if(data == 'success'){
		        		validateLegalSigner();
// 		        		location.replace(sourceleadUrl+"/orgSettings");
		        		$("#loading").removeAttr("style");
		        	}
		        },
		    });
  }
  </script>
  
  <script>
  function getAllSignatory(){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.get( sourceleadUrl+ "/getAllSignatoriesByLocationId?${_csrf.parameterName}=${_csrf.token}", function(data) {
		  var result = "";
	    	 result += '<table id="orgLocationSignatoriesList" class="display" cellspacing="0" width="100%" >';
	         result += '<thead>';
	         result += '  <tr>';
	         result += '    <th>Name</th>';
	         result += '    <th>Email</th>';
	         result += '    <th>Role</th>';
	         result += '    <th>SSN</th>';
	         result += '    <th>Upto</th>';
	         result += '    <th>Action</th>';
	         result += '  </tr>';
	         result += '</thead>';
	         result += '<tbody>';
	         
	    	 for(i=0;i < data.length;i++){
	    		 var report = data[i];
	    		 result += '<tr>';
		         result += '    <td>';
		         if(report.lastName != null && report.lastName != '' && report.lastName != 'undefined'){
		        	 result += '      <div class="proj_show tbldata-dotdot-big">';
		        	 result += '          <div class="pull-left tbldata-dotdot-me" title="'+report.firstName+' '+report.lastName+'" >'+report.firstName+' '+report.lastName+'</div>';
			         result += '      </div>';
		         } else{
		        	 result += '      <div class="proj_show tbldata-dotdot-big">';
		        	 result += '          <div class="pull-left tbldata-dotdot-me" title="'+report.firstName+'" >'+report.firstName+'</div>';
			         result += '      </div>';
		         }
		         result += '    </td>';
		         result += '    <td>';
		         if(report.email != null && report.email != '' && report.email != 'undefined'){
		        	 result += '        <div title="'+report.email+'" class="tblminwidth-75">'+report.email+'</div>';
		         }
		         result += '    </td>';
		         result += '    <td>';
		         if(report.roleType != null && report.roleType != '' && report.roleType != 'undefined'){
		        	 result += '      <div title="'+report.roleType+'" class="tbldata-dotdot-100">'+report.roleType+'</div>'; 
		         }
		         result += '    </td>';
		         result += '    <td>';
		         if(report.ssn != null && report.ssn != '' && report.ssn != 'undefined'){
		        	 result += '        <div title="'+report.ssn+'" class="tblminwidth-75">'+report.ssn+'</div>'; 
		         }else{
		        	 result += '        <div class="tblminwidth-75">---</div>'; 
		         }
		         result += '    </td>';
		         result += '    <td>';
		         if(report.authorizedAmount != null && report.authorizedAmount != '' && report.authorizedAmount != 'undefined'){
	        		 result += '        <div title="'+report.authorizedAmount+'" class="tblminwidth-75">'+report.authorizedAmount+'</div>'; 
		         }else{
		        	 result += '        <div class="tblminwidth-75"> --- </div>';
		         }
		         result += '    </td>';
		         result += '    <td>';
		         result += '        <div class="tblminwidth-100">';
		         result += '            <span title="Edit" class="paddingRight"><a onclick="editSignatory(\''+report.uuid+'\');"><i class="fa fa-pencil text-primary cursorPointer"></i></a></span> ';
		         if(report.status == 1){
		        	 result += '<label class="switch"><input type="checkbox" checked onclick="deactivateSignatory(\''+report.uuid+'\',\'inactive\');"> <div class="slider round"></div> </label>';
		         }else{
		        	 result += '<label class="switch"><input type="checkbox" onclick="deactivateSignatory(\''+report.uuid+'\',\'active\');"> <div class="slider round"></div> </label>';
		         }
		         
		         result += '        </div>';
		         result += '    </td>';
		         result += '  </tr>';
	    	 }
	    	
	         result += '  </tbody>';
    		 result += '</table> ';
    		 $("#orgLocationSignatoriesListDiv").empty();
    		 $("#orgLocationSignatoriesListDiv").append(result);
    		 $("#loading").removeAttr('style');
    		 signatoryPagination();
	     }); 
  }
  
  function signatoryPagination(){
		$('#orgLocationSignatoriesList').DataTable({ 
			"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    });
		$("#orgLocationSignatoriesList").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

	}
  </script>
  
  <script>
  //addSignatoryDiv
  function addOrgLocationSignatoryDiv(){
	  $("#signatoryFormDiv").fadeIn(100);
	  $(".mask").show();
	  loadLeagelSignerForm();
  }
  function signatoryFormDiv(){
	  $("#signatoryFormDiv").fadeOut(100);
	  $(".mask").hide();  
	  $('#signatory_form')[0].reset();
	  $(".chosen-select").trigger("chosen:updated");
	  $('signatory_form').validate().resetForm();
  }
  </script>
  
  <script>
  function editSignatory(signatoryUUID){
	  addOrgLocationSignatoryDiv();
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.get( sourceleadUrl+ "/getSignatoriesByUUID?${_csrf.parameterName}=${_csrf.token}&uuid="+signatoryUUID, function(data) {
		  if(data != null){
			  $("#signatoryUUID").val(data.uuid);
			  $("#signatoryAssociate").val(data.associateId);
			  $("#signatoryAssociate").trigger("chosen:updated");
			  $("#authorizedType").val(data.authorizedType);
			  $("#authorizedType").trigger("chosen:updated");
			  $("#signatoryAuthorisedAmount").val(data.authorizedAmount);
			  document.getElementById("editSignerDiv").style.display="none";
			  document.getElementById("showSignerDiv").style.display="block";
			  $("#showAssociateName").val(data.firstName);
			  $("#showAssociateId").val(data.associateId);
			  
		  }
	  });
  }
  
  function deactivateSignatory(signatoryUUID,status){
	  
	  $.post( sourceleadUrl+ "/deactivateSignatory?uuid="+signatoryUUID+"&status="+status, function(data) {
		  if(data != "success"){
			  getAllSignatory();
		  }
	  });
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
				})

</script>

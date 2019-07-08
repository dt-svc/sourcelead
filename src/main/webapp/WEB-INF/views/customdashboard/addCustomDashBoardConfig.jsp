<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Team Goals</h2></div>
      <div class="pull-right brud-crum">Home &raquo; Add Team Goals</div>
    </section>
    
    <div class="project_wrap">
 <div class="clearfix newAddWidth">
    <div class="form-horizontal mt myForm">
    <div id="loading"></div>
     <form:form id="dashboard_conf_form" name="dashboard_conf_form">
      <div class="col-md-7 mt"> 
        <div class="clearfix mb">
          <label class="col-md-4">Dashboard Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-8"><input type="text" class="form-control" id="dashBoardName" name="dashBoardName" value = '${customDashBoardConf.dashboardName }' field-name="DashBoard Name" data-validation="required validate_length length1-100"></div>
        <input type="hidden" value="${customDashBoardConf.id }" name="customDashBoardConfId" id="customDashBoardConfId"/>
        </div>

        <div class="clearfix mb">
          <label class="col-md-4">Description</label>
          <div class="col-md-8"><textarea placeholder="Description" class="form-control" cols="3" id="description" name="description">${customDashBoardConf.description }</textarea></div>
        </div>   

        <div class="clearfix mb" id="applicableForErrorDIV">
          <label class="col-md-4">Applicable for <span class="text-danger paddingLeft">&nbsp;*</span></label>
          <div class="col-md-2"><input type="radio" id="applicableForAll" name="applicableFor" value="All" ${customDashBoardConf.applicableFor=='All'?'checked':''}><label for="a" class="paddingLeft" data-validation="required"> All</label> </div>
          <div class="col-md-2"><input type="radio" id="applicableForRoles" name="applicableFor" value="Roles" ${customDashBoardConf.applicableFor=='Roles'?'checked':''}><label for="b" class="paddingLeft" data-validation="required"> Roles</label></div> 
          <div class="col-md-3"><input type="radio" id="applicableForAssociates" name="applicableFor" value="Associates" ${customDashBoardConf.applicableFor=='Associates'?'checked':''}><label for="c" class="paddingLeft" data-validation="required"> Associates</label></div> 
        </div>   

        <div class="clearfix mb" id="RolesShow" style="display: none;">
        <div id ="rolesErrorDIV">
        <label class="col-md-4">Roles</label>
        <div class="col-md-8">
              <select multiple class="chosen-select form-control" id="roles" name="roles" field-name="Roles">
                <c:if test="${not empty selectedRoles}">
                     <c:forEach var="role" items="${roles}">
                             <c:forEach var="selectedRoles" items="${selectedRoles}">
	                     		<option value="${role.id}" ${selectedRoles.roleId  == role.id ? 'selected' : ''} >${role.displayName}</option>
                       		</c:forEach>
                      </c:forEach>
                </c:if>
              <c:if test="${empty selectedRoles}">
                  <c:forEach var="role" items="${roles}">
                      <option value="${role.id}">${role.displayName}</option>
                   </c:forEach>
              </c:if>
              </select>          	
        </div>
        </div>
      </div>  
      
      <div class="clearfix mb" id="AssociatesShow" style="display: none;">
          <label class="col-md-4">Associates</label>
          <div class="col-md-8">
                <select multiple class="chosen-select form-control" id="associates" name="associates" field-name="Associates">
                   
                   <c:if test="${not empty selectedAssociates}">
                   
                          <c:forEach var="userRole" items="${users}">
                     		  <c:forEach var="selectedUser" items="${selectedAssociates}">
                     		  <c:if test="${not empty userRole.user.lastName}">
                        		<option value="${userRole.user.id}" ${selectedUser.userId  == userRole.user.id ? 'selected' : ''}>${userRole.user.firstName} ${userRole.user.lastName}</option>
                    		</c:if>
                    		<c:if test="${empty userRole.user.lastName}">
                      			<option value="${userRole.user.id}" ${selectedUser.userId == userRole.user.id ? 'selected' : ''}>${userRole.user.firstName}</option>
                     		</c:if>
                     		  </c:forEach>
                		</c:forEach> 
                   </c:if>
                   
                    <c:if test="${empty selectedAssociates}">
                    	 <c:forEach var="userRole" items="${users}">
                     		<c:if test="${not empty userRole.user.lastName}">
                        		<option value="${userRole.user.id}">${userRole.user.firstName} ${userRole.user.lastName}</option>
                    		</c:if>
                    		<c:if test="${empty userRole.user.lastName}">
                      			<option value="${userRole.user.id}">${userRole.user.firstName}</option>
                     		</c:if>
                		</c:forEach>
                   </c:if>
                </select>          	
          </div>
        </div>        

        </div>
        </form:form>
    </div>
    
    </div>
    
	<div class="footerstyelsNew clearfix">
	<div class="col-md-6">
		<div class="pull-right">
		  <input value="Save" class="btn btn-primary" type="button" onclick="saveCustomDashBoard();">
			<a href="javascript:void(0)"><input value="Cancel" class="btn btn-default ml" type="button" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/configureCustomDashBoard'"></a>
		</div>
	</div>
	</div>
       	 	
	</div>
  </section>
  
  
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


<script>
 
if ($("#applicableForAssociates").prop("checked") || $("#applicableForAll").prop("checked") || $("#applicableForRoles").prop("checked")) {
	calRadioButton();
	}
	
function calRadioButton(){
	var checkedValue = $("input[name='applicableFor']:checked").val();
	 if (checkedValue == "Roles") {
         $("#AssociatesShow").hide();
         $("#RolesShow").show();
         $('#roles').removeAttr('data-validation-optional');
 		$('#roles').attr('data-validation', "required");
 		
 		
 		$('#associates').removeAttr('data-validation');
 		$('#associates').attr('data-validation-optional', "true");
 		$('#associates').find('p.jquery_form_error_message').remove();
     }
     if (checkedValue == "Associates") {
         $("#AssociatesShow").show();
         $("#RolesShow").hide();
         $('#associates').removeAttr('data-validation-optional');
 		$('#associates').attr('data-validation', "required");
 		
 		
 		$('#roles').removeAttr('data-validation');
 		$('#roles').attr('data-validation-optional', "true");
 		$('#roles').find('p.jquery_form_error_message').remove();
     }
     if (checkedValue == "All") {
         $("#AssociatesShow, #RolesShow").hide();
         $('#associates').removeAttr('data-validation');
 		$('#associates').attr('data-validation-optional', "true");
 		$('#associates').find('p.jquery_form_error_message').remove();
 		$('#roles').removeAttr('data-validation');
 		$('#roles').attr('data-validation-optional', "true");
 		$('#roles').find('p.jquery_form_error_message').remove();
     } 
}
	
    
</script>



<script type="text/javascript">
	function saveCustomDashBoard() {
		var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		 if ($('#dashboard_conf_form').validate(false, validationSettings)) {
			 if(!$("#applicableForAll").is(":checked") && !$("#applicableForRoles").is(":checked") && !$("#applicableForAssociates").is(":checked")){
				 $("#applicableForErrorDIV").after( "<p class='jquery_form_error_message' style='color:#f12929; font-size: 12px;'>Select at least one Field</p>");
			   return false;
			 }else{
				 $('#applicableForErrorDIV').find('p.jquery_form_error_message').remove();
			 }
			 
			var formData = $($('#dashboard_conf_form')[0]).serialize(); //getFormData("dashboard_conf_form");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post( sourceleadUrl+ "/saveCustomDashBoardConfiguration?$ "+tokenParamName+"="+tokenValue, formData, function(result) {
				if (result && result.response === "INVALID_DATA") {
	    		    showError(result.responseMessage);
	    		   } else if (result.response === "AWKWARD") {
	    		        location.href = "/errorPage";
	    		   }  else if (result.response === "SUCCESS") {     
				         $("#loading").removeAttr("style");
                    	  location.replace(sourceleadUrl+"//configureCustomDashBoard");
                       }
				$("#loading").hide();
				$('#save_shift').attr('disabled','disabled');
                    });
            
			return true;
		} else {
// 			$('#submissionOffersListDiv').find('p.text-primary').remove();
			return false;
		}
	}
	$('body').on('blur', '#dashboard_conf_form', function() {
		$("#dashboard_conf_form").showHelpOnFocus();
		$("#dashboard_conf_form").validateOnBlur(false, validationSettings);
	}); 
</script>

<jsp:include page="../masterFooter.jsp" />

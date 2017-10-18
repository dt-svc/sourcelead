<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
 window.onload = function() {
	 var policy = '${leaveType.leavePolicy}';
	 if(policy == 'Y'){
		 leavePolicy();
	 }
	 var applicable = '${leaveType.applicableToUsers}';
	 if(applicable == 'N'){
		 noUsers();
	 }
};
</script>


  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Leave Type</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/leaveConfiguration'/>"> Leave Configuration</a> &raquo;<a href="<c:url value='/viewLeaveTypes'/>">Leave type List</a> &raquo; Add Leave Type</div>
    </section>
    <div class="content-area content-new clearfix">
     <form:form id="addLeaveType_form"  name="addLeaveType_form" method="post">
     <div id="loading"></div>
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9" id="leaveTypeDiv">
              	<input type="hidden" id="leaveTypeId" name="leaveTypeId" value="${leaveType.id}"/>
                <input type="text" class="form-control" id="leaveTypeName" name="name" value="${leaveType.name}" onblur="validateName();" field-name="Name" data-validation="required validate_Space validate_length length2-250">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Duration<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 dropdown-select">
                  <select class="chosen-select form-control" name="duration" field-name="Duration" data-validation="required">
                      <option value="">Select Duration</option>
                      <option ${leaveType.duration  == 'Full Day' ? 'selected' : ''}>Full Day</option>
                      <option ${leaveType.duration  == 'Half Day' ? 'selected' : ''}>Half Day</option>
                  </select>
              </div>
            </div>
              
             <%--  <div class="form-group">
              <label class="col-md-3">Hours</label>
              <div class="col-md-9 dropdown-select">
                  <div class="row">
                      <div class="col-md-9 paddingRight">
                   <input type="text" class="form-control" value="${leaveType.hours}" placeholder="Hours" name="hours">
                          </div>
                      <div class="col-md-3 paddingLeft">
                  <select class="chosen-select form-control" name="hoursType">
                      <option ${leaveType.typeOfHours  == 'AM' ? 'selected' : ''} >Am</option>
                      <option ${leaveType.typeOfHours  == 'PM' ? 'selected' : ''} >Pm</option>
                  </select>
                      </div></div>
              </div>
            </div> --%>
              
            <div class="form-group">
              <label class="col-md-3">Type</label>
              <div class="col-md-9">
            <div class="clearfix mb"> 
              <label class="paddingLeft paddingRight">
              <c:if test="${!empty leaveType.leaveType}">
              	<c:if test="${leaveType.leaveType == 'Billable'}">
              		<input checked="true" type="radio" id="billableId" name="billingType" value="Billable"><span class="paddingLeft">Billable</span>
              		<input type="radio" id="nonBillableId" name="billingType" value="Non Billable"><span class="paddingLeft">Non Billable</span>
              	</c:if>
              	<c:if test="${leaveType.leaveType == 'Non Billable'}">
              		<input type="radio" id="billableId" name="billingType" value="Billable"><span class="paddingLeft">Billable</span>
              		<input checked="true" type="radio" id="nonBillableId" name="billingType" value="Non Billable"><span class="paddingLeft">Non Billable</span>
              	</c:if>
              </c:if>
              
              <c:if test="${empty leaveType.leaveType}">
              		<input checked="true" type="radio" id="billableId" name="billingType" value="Billable"><span class="paddingLeft">Billable</span>
              		<input type="radio" id="nonBillableId" name="billingType" value="Non Billable"><span class="paddingLeft">Non Billable</span>
              </c:if>
                
              </label>
            </div>
                   
              </div>
            </div>
              <div class="form-group">
              <label class="col-md-3">Applicable to All Users</label>
              <div class="col-md-9">
                <div class="clearfix mb"> 
                <c:if test="${!empty leaveType.applicableToUsers}">
                	<c:if test="${leaveType.applicableToUsers == 'Y'}">
                		<label class="paddingLeft paddingRight" onClick="yesUsers();">
	                	<input type="radio"  checked="true" value="Y" name="applicableToUsers">
	                	<span class="paddingLeft">Yes</span></label>
	                	<label class="paddingLeft" onClick="noUsers();">
		                <input type="radio" id="noUsers" value="N" name="applicableToUsers">
		                <span class="paddingLeft">No</span></label>
                	</c:if>
                	
                	<c:if test="${leaveType.applicableToUsers == 'N'}">
                		<label class="paddingLeft paddingRight" onClick="yesUsers();">
	                	<input type="radio" value="Y" name="applicableToUsers">
	                	<span class="paddingLeft">Yes</span></label>
	                	<label class="paddingLeft" onClick="noUsers();">
		                <input type="radio" checked="true" id="noUsers" value="N" name="applicableToUsers">
		                <span class="paddingLeft">No</span></label>
                	</c:if>
               		
                </c:if>
                
                <c:if test="${empty leaveType.applicableToUsers}">
                	<label class="paddingLeft paddingRight" onClick="yesUsers();">
	                	<input type="radio"  checked="true" value="Y" name="applicableToUsers">
	                	<span class="paddingLeft">Yes</span></label>
	              	<label class="paddingLeft" onClick="noUsers();">
		                <input type="radio" id="noUsers" value="N" name="applicableToUsers">
		                <span class="paddingLeft">No</span></label>
                </c:if>
	              
            </div>
              </div>
            </div>
              
              <div class="clearfix" id="noUsersDiv" style="display: none;">
                  <div class="clearfix"></div>
			<div class="form-group" id="departmentDiv">
              <label class="col-md-3">Select Department</label>
              <div class="col-md-9 dropdown-select">
                  <select class="chosen-select form-control" name="department" id="department" field-name="Department" data-validation-optional="true">
                  <option value="">Select Department</option>
                      <c:forEach var="report" items="${departmentList}">
                    	<option value="${report.id}" ${report.id  == departmentId ? 'selected' : ''}>${report.departmentName}</option>
                      </c:forEach> 
                  </select>
              </div>
            </div>
                  <div class="form-group" id="rolesDiv">
              <label class="col-md-3">Select Roles</label>
              <div class="col-md-9 dropdown-select">
                  <select multiple data-placeholder="Select Roles" class="chosen-select form-control" id="roles" name="roles" field-name="Roles" data-validation-optional="true">
                  		<c:forEach var="role" items="${rolesList}">
	                       <c:set var = "NUM" value = "${0}"/>
	                       <c:forEach var="existedRoles" items="${existedRoles}">
							     <c:if test="${existedRoles.id eq role.id}">
							    	<c:set var = "NUM" value = "${1}"/>
							        <option  selected value="${role.id}">${existedRoles.displayName}</option>
								 </c:if> 
							</c:forEach>
							<c:if test="${NUM == 0}">
								<option value="${role.id}">${role.displayName}</option>
							</c:if>
						</c:forEach>
                      <%-- <c:forEach var="report" items="${rolesList}">
                    	<option value="${report.id}">${report.displayName}</option>
                      </c:forEach>  --%>
                  </select>
              </div>
            </div>
                  
              </div>
            <div class="form-group">
              <label class="col-md-3">Leaves Carryforward</label>
              <div class="col-md-9">
                <div class="clearfix mb"> 
	                <c:if test="${!empty leaveType.leavesCarryforward}">
		                <c:if test="${leaveType.leavesCarryforward == 'Y'}">
			                <label class="paddingLeft paddingRight">
			                	<input type="radio" checked value="Y" name="carryforward">
			                	<span class="paddingLeft">Yes</span></label>
			              	<label class="paddingLeft">
			                	<input type="radio" value="N" name="carryforward">
			                	<span class="paddingLeft">No</span></label>
		                </c:if>
		                <c:if test="${leaveType.leavesCarryforward == 'N'}">
			                <label class="paddingLeft paddingRight">
			                	<input type="radio" value="Y" name="carryforward">
			                	<span class="paddingLeft">Yes</span></label>
			              	<label class="paddingLeft">
			                	<input type="radio" checked value="N" name="carryforward">
			                	<span class="paddingLeft">No</span></label>
		                </c:if>
	                </c:if>
	                <c:if test="${empty leaveType.leavesCarryforward}">
	                	<label class="paddingLeft paddingRight">
		                	<input type="radio" value="Y" name="carryforward">
		                	<span class="paddingLeft">Yes</span></label>
		              	<label class="paddingLeft">
		                	<input type="radio" checked value="N" name="carryforward">
		                	<span class="paddingLeft">No</span></label>
	                </c:if>
            	</div>
              </div>
                
            </div>
            <div class="form-group">
              <label class="col-md-3">Number of Leaves per year<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="noOfLeavesPerYear" value="${leaveType.numbefrOfLeavesPeryear}" placeholder="Number of Leaves Per Year" field-name="Leaves per year" data-validation="required validate_Space validate_int validate_length length1-3">
              </div>
            </div> 
            
            <div class="form-group">
              <label class="col-md-3">Number of Leaves per Month</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="noOfLeavesPerMonth" value="${leaveType.numbefrOfLeavesPerMonth}" placeholder="Number of Leaves Per Month" field-name="Leaves per month" data-validation="validate_Space validate_int validate_length length1-2" data-validation-optional="true">
              </div>
            </div>                                   

              <div class="form-group">
              <label class="col-md-3">Description</label>
              <div class="col-md-9">
                  <textarea class="form-control" rows="2" name="description">${leaveType.description}</textarea>
              </div>
            </div>
            
             <div class="form-group">
              <label class="col-md-3">Color Picker<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
					<div id="cp"  class="input-group colorpicker-component errorInptgroup">
						<input type="text" name="colorCode" field-name="Color Picker" placeholder="Select Color Picker" readonly="readonly" value="${leaveType.colorCode}" class="form-control" data-validation="required">
							<span class="input-group-addon"><i><img src="resources/images/theme-icon.png" class="img-responsive" title="Themes"></i></span>
					</div>
              </div>
            </div> 
            
              <div class="clearfix"></div>
            <div class="form-group mt10">
              <div class="col-md-12 checkBox" onClick="leavePolicy();">
	              <c:if test="${!empty leaveType.leavePolicy}">
	              	<input type="checkbox" checked="true" name="leavePolicy" id="leavePolicy" value="Y">Leave Policy
	              </c:if>
	              <c:if test="${empty leaveType.leavePolicy}">
	              	<input type="checkbox" name="leavePolicy" id="leavePolicy" value="Y">Leave Policy
	              </c:if>
              </div>
            </div>
              <div class="clearfix"></div>
						<div class="clearfix req-dtls" id="leavepolicy-div"
							style="display: none;">
							<h3>Leave Policy</h3>
							<div class="form-group">
								<label class="col-md-3">Policy Name<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-9" id=policyNameDiv>
									<input type="text" class="form-control" name="policyName" id="policyName"
										value="${leaveType.policyName}" field-name="Policy Name" data-validation-optional="true">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3"></label>
								<div class="col-md-9 checkBox">
									<c:if test="${!empty leaveType.visibilityToAllUsers}">
										<input type="checkbox" checked="true" name="visibilityToAllUsers" value="Y" >Visiblity
										All Users
									</c:if>
									<c:if test="${empty leaveType.visibilityToAllUsers}">
										<input type="checkbox" name="visibilityToAllUsers" value="Y" >Visiblity
										All Users
									</c:if>
								</div>
							</div>
							<div class="clearfix">
								<label class="col-md-12">Description<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-12 txtEditor_p" id="leavePolicyDescDiv">
									<textarea class="txtEditor" id="txtEditor" name="leavePolicyDescription" field-name="Description" data-validation-optional="true">${leaveType.policyDescription}</textarea>
								</div>
							</div>
						</div>

					</div>
        </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" id="saveLeaveType" value="Save" onclick="submitLeaveType();" class="btn btn-primary">
            <input type="button" value="Cancel" onclick="redirect();" class="btn btn-default ml10">
          </div>
        </div>
      </div>
      </form:form>
      <!-- Content Area Ends--> 
    </div>
  </section>

<script>
function validateName(){
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var name = $("#leaveTypeName").val();
	if(name != null && name != '' && name != 'undefined'){
		$.get(sourceleadUrl+"/validateName?&orgLocationId="+locationId+"&from=LeaveType&name="+name, function(data) {
          	if(data == true){
          		$('#leaveTypeDiv').find('p.jquery_form_error_message').remove();
          		$("#saveLeaveType").removeAttr("disabled");
		        $("#saveLeaveType").attr("disabled", "disabled");
				$("input#leaveTypeName").after( "<p class='jquery_form_error_message'>" + name + " is already existed as Leave Type</span>");
                 }else{
                	$("#saveLeaveType").removeAttr("disabled");
                	$('#leaveTypeDiv').find('p.jquery_form_error_message').remove();
                 }
            });
		}
	}
</script>

<script type="text/javascript">
function submitLeaveType(){
	validateName();
	var ee = $("#txtEditor").summernote('code');
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var loginUserId = '${SourceLeadWeb_user.id}';
		if ($('#addLeaveType_form').validate(false, validationSettings)) {
			var formData = $($('#addLeaveType_form')[0]).serialize();
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            $.post( sourceleadUrl+ "/saveNewLeaveType?"+tokenParamName+"="+tokenValue+"&orgLocationId="+locationId+"&loginUserId="+loginUserId, formData, function(data) {
            	if(data == "success"){
                    	   location.replace(sourceleadUrl+"/viewLeaveTypes");
                       }
                    });
            
			return true;
		} 
}
</script>

<script>
function redirect(){
	window.location.href = sourceleadUrl + "/viewLeaveTypes";
}
</script>


<script>
	$('#txtEditor').summernote({
  toolbar: [
    // [groupName, [list of button]]
    ['style', ['bold', 'italic', 'underline', 'clear']],
    ['font', ['strikethrough', 'superscript', 'subscript']],
    ['fontsize', ['fontsize']],
    ['color', ['color']],
    ['para', ['ul', 'ol', 'paragraph']],
	['link',['link','insert','unlink']],
    ['height', ['height']]
  ],
  callbacks:{
	  onImageUpload: function (data) {
		             	data.pop();
                   	}
	  
  	}
});
</script>
<script>
function leavePolicy(){
   var ckbox = $('#leavePolicy');
   if (ckbox.is(':checked')) {
		$("#leavepolicy-div").show();
		$('#policyName').removeAttr('data-validation-optional');
		$('#policyName').attr('data-validation',"required");
		$('#txtEditor').removeAttr('data-validation-optional');
		$('#txtEditor').attr('data-validation',"required");
		$('#policyNameDiv').find('p.jquery_form_error_message').remove();
		$('#leavePolicyDescDiv').find('p.jquery_form_error_message').remove();
	} else {
		$("#leavepolicy-div").hide();
		$('#policyNameDiv').find('p.jquery_form_error_message').remove();
		$('#leavePolicyDescDiv').find('p.jquery_form_error_message').remove();
		$('#policyName').removeAttr('data-validation');
		$('#policyName').attr('data-validation-optional',"true");
		$('#txtEditor').removeAttr('data-validation');
		$('#txtEditor').attr('data-validation-optional',"true");
	}
}

//Select All Users
function yesUsers(){
	$('#departmentDiv').find('p.jquery_form_error_message').remove();
	$('#rolesDiv').find('p.jquery_form_error_message').remove();
	$('#department').removeAttr('data-validation');
	$('#department').attr('data-validation-optional',"true");
	$('#roles').removeAttr('data-validation');
	$('#roles').attr('data-validation-optional',"true");
	$("#noUsersDiv").hide();
}

function noUsers(){
	$('#department').removeAttr('data-validation-optional');
	$('#department').attr('data-validation',"required");
	$('#roles').removeAttr('data-validation-optional');
	$('#roles').attr('data-validation',"required");
	$("#noUsersDiv").show();
}
</script>


<script>
    //Color Picker
    $(function() {
        $('#cp').colorpicker();
    });
</script>

<jsp:include page="../masterFooter.jsp" />


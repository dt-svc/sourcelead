<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 


<script type="text/javascript">
$(document).ready(function(){
 //getRoles();
});
</script>

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Leave Options</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='${appPath}/leaveConfiguration'/>"> Leave Configuration</a> &raquo;<a href="<c:url value='${appPath}/viewOptions'/>">Leave Options List</a> &raquo; Add Leave Options</div>
    </section>
    <div class="content-area content-new clearfix">
    <div id="loading"></div> 
    <form:form id="add_option_form" name="add_option_form" method="post">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Select Department</label>
              <div class="col-md-9 dropdown-select">
               <select data-placeholder="select Department" class="chosen-select form-control" id="department" name="department" field-name="Department">
                <option value="">--Select Department--</option>
                <c:forEach var="dept" items="${departmentList}">
	                     <option value="${dept.id}" ${dept.id  == departmentId ? 'selected' : ''}>${dept.departmentName}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Select Role</label>
              <div class="col-md-9 dropdown-select">
              <div id="rolesDiv"></div>
                 <select multiple data-placeholder="select owner" class="chosen-select form-control"  id="roles" name="roles" field-name="Role">
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
			    </select> 
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Calendar Month</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control" id="calendarMonth" name="calendarMonth">
                <c:forEach var="months" items="${calendarList}">
               	   <c:set var = "NUM" value = "${0}"/>
                     <c:forEach var="existedMonths" items="${existCalendarList}">
					     <c:if test="${existedMonths eq months}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${months}">${existedMonths}</option>
						 </c:if> 
				     </c:forEach>
					<c:if test="${NUM == 0}">
						<option value="${months}">${months}</option>
					</c:if>
                
                </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Weekends</label>
              <div class="col-md-9 dropdown-select">
               <select multiple data-placeholder="select owner" class="chosen-select form-control"  id="weekends" name="weekends" field-name="Weekends">
	               <c:forEach var="weeks" items="${weekends}">
	               <c:set var = "NUM" value = "${0}"/>
	               <c:forEach var="existedWeeks" items="${existWeekends}">
					     <c:if test="${existedWeeks eq weeks}">
					    	<c:set var = "NUM" value = "${1}"/>
					       <option  selected value="${weeks}">${existedWeeks}</option>
						 </c:if> 
				     </c:forEach>
				     <c:if test="${NUM == 0}">
						<c:if test="${weeks == 'Sunday'}">
				                <option selected value="${weeks}">${weeks}</option>
				            </c:if>
				            <c:if test="${weeks == 'Saturday'}">
				                <option selected value="${weeks}">${weeks}</option>
				            </c:if>
				            <c:if test="${weeks != 'Saturday' && weeks != 'Sunday' }">
				             <option  value="${weeks}">${weeks}</option>
				            </c:if>
					</c:if>
	               </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Halfday Request</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control" id="halfDayRequest" name="halfDayRequest">
                <c:if test="${!empty options.halfdayRequest}">
	                <c:if test="${options.halfdayRequest == 'YES'}">
	                	<option selected value="YES">Yes</option>
	                	<option value="NO">NO</option>
	                </c:if>
	                 <c:if test="${options.halfdayRequest == 'NO'}">
	                 	<option value="YES">Yes</option>
	                	<option selected value="NO">NO</option>
	                </c:if>
                </c:if>
                <c:if test="${empty options.halfdayRequest}">
                	<option selected value="YES">Yes</option>
                 	<option value="NO">No</option>
                </c:if>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Leave Transfer</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control" id="leaveTransfer" name="leaveTransfer">
                <c:if test="${ !empty options.leaveTransfer}">
                <c:if test="${options.leaveTransfer == 'YES'}">
                <option selected value="YES">Yes</option>
                </c:if>
                <c:if test="${options.leaveTransfer == 'NO'}">
                 <option selected value="NO">No</option>
                </c:if>
                 </c:if>
                  <c:if test="${empty options.leaveTransfer}">
                   <option selected value="YES">Yes</option>
                  <option value="NO">No</option>
                  </c:if>
                </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Working Hours</label>
              <div class="col-md-9 dropdown-select">
                <input type="text" class="form-control" value="${options.workingHours}" placeholder="Working Hours" id="workingHours" name="workingHours" field-name="Working Hours"  data-validation="validate_Space validate_length length1-8 validate_int" data-validation-optional="true">
                <input type="hidden" value="${optionId}" name="optionId" id="optionId" />
              </div>
            </div>
          </div>
        </div>
      </div>
    <div class="form-footer clearfix mt">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Save" id="save_leaveoption" onclick="saveLeaveOption();" class="btn btn-primary">
          <input type="button" value="Cancel" onclick="cancel();" class="btn btn-default ml10">
        </div></div></div>
        </form:form>
      </div>
  </section>
  
<script type="text/javascript">
function saveLeaveOption(){
	 if ($('#add_option_form').validate(false, validationSettings)){ 
		 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			var userId = '${SourceLeadWeb_userId}';
		  	/* var formData = getFormData("add_option_form"); */
		  	var formData = $($('#add_option_form')[0]).serialize();
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post(sourceleadUrl+"/saveLeaveOption?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId, formData, function(result) {
				 if (result.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		   }  else if (result.response === "SUCCESS") { 
					location.replace(sourceleadUrl+ "/viewOptions");
				}else if (result.indexOf('Sign In') !== -1) {
           			location.replace(sourceleadUrl);
           		} else {
           		    showError("Operation failed, please try again");
           		}
			$("#loading").hide();
			}); 
			return true;
	 }else{ 
			return false;
	 }   
		
}
$('body').on('blur', '#add_option_form', function() {
	$("#add_option_form").showHelpOnFocus();
	$("#add_option_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
function cancel(){
	location.replace(sourceleadUrl+ "/viewOptions");
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
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script type="text/javascript">
            function leavePolicy(){
                 var ckbox = $('#leavePolicy');
                if (ckbox.is(':checked')) {
           $("#leavepolicy-div").show();
        } else {
            $("#leavepolicy-div").hide();
        }
            }
        </script> 
         <%--  <select multiple data-placeholder="select owner" class="chosen-select form-control"  id="roles" name="roles" field-name="Role">
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
			    </select> --%>
        
 <script>
  function getRoles(){
	    var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var  orgCode ='${SourceLeadWeb_orgCode}';
		var  roleHierarchyId ='${SourceLeadWeb_roleHierarchyId}';
		$.get(sourceleadUrl+"/getRoles?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&orgCode="+orgCode+"&roleHierarchyId="+roleHierarchyId, function(result) {
		  var json = result.data;
		  var output = "";
      	  output = output +'<select multiple data-placeholder="select owner" class="chosen-select form-control"  id="roles" name="roles" field-name="Role">';
      	  for (var i = 0; i < json.length; i++) { 
      		  var role = json[i];
      		  output = output +'<option value='+role.id+'>'+role.displayName+'</option>';
      	  }
      	  output = output +'</select>';
      	  $("#rolesDiv").empty();
      	  $("#rolesDiv").append(output);
      	  /* $('#rolesDiv').chosen();
      	 $("#rolesDiv").trigger("chosen:updated"); */
      	 
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
      				$(document).ready(function(){
      					$("#cd").click(function(){
      						$(".clientDetails").toggleClass('hidden', 500);
      					});

      	});
		}); 
  }
  
 
  </script>
<jsp:include page="../masterFooter.jsp" />

<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
 window.onload = function() {
	 var applicable = '${restrictDay.applicableToAll}';
	 if(applicable == 'N'){
		 $("#selectRoles").show();
	 }
};
</script>


  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Restrict Day</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/leaveConfiguration'/>">Leave Configuration</a> &raquo; <a href="<c:url value='/viewRestrictDay'/>">Restrict Day List</a> &raquo; Add Restrict Day</div>
    </section>
    <div class="content-area content-new clearfix">
    <div id="restrictLoading"></div>
    <form:form id="restrictDay_form" name="restrictDay_form" method="post">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          
    <div class="form-group">
       <label class="col-md-3">Name<span class="text-danger paddingLeft">*</span></label>
       <div class="col-md-9">
       <input type="hidden" id="restrictDayId" name="restrictDayId" value="${restrictDay.id}"/>
       <input type="text" name="restrictName" placeholder="Restrict Name" value="${restrictDay.restrictName}" field-name="Restrict Name" class="form-control" data-validation="required validate_length length1-250">
       </div>
    </div>
    <div class="form-group" id="startDateDiv">
       <label class="col-md-3">Start Date<span class="text-danger paddingLeft">*</span></label>
        <div class="col-md-9">
       <input type="text" id="startDate" name="startDate" placeholder="Start Date" value="${restrictDay.startDateStr}" field-name="Start Date" class="form-control calendar" data-validation="required">
       </div>
    </div>
    <div class="form-group" id="endDateDiv">
       <label class="col-md-3">End Date<span class="text-danger paddingLeft">*</span></label>
        <div class="col-md-9">
       <input type="text" id="endDate" name="endDate" placeholder="End Date" value="${restrictDay.endDateStr}" field-name="End Date" class="form-control calendar" data-validation="required">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Reason</label>
        <div class="col-md-9">
            <textarea class="form-control" name="reason" value="${restrictDay.reason}" rows="2" data-validation="validate_length length1-500" data-validation-optional="true" placeholder="Reason">${restrictDay.reason}</textarea>
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3 paddingRight">Applicable For All</label>
       <div class="col-md-9">
 			<div class="clearfix mb"> 
 			<c:if test="${empty restrictDay.applicableToAll}">
 			<label class="paddingLeft paddingRight">
                <input name="isApllicable" type="radio" id="yesApplica" value="Y" checked>
                <span class="paddingLeft">Yes</span></label>
              <label class="paddingLeft">
                <input name="isApllicable" type="radio" id="noApplica" value="N">
                <span class="paddingLeft">No</span></label>
 			</c:if>
 			<c:if test="${restrictDay.applicableToAll == 'Y'}">
 			<label class="paddingLeft paddingRight">
                <input name="isApllicable" type="radio" id="yesApplica" value="Y" checked>
                <span class="paddingLeft">Yes</span></label>
              <label class="paddingLeft">
                <input name="isApllicable" type="radio" id="noApplica" value="N">
                <span class="paddingLeft">No</span></label>
 			</c:if>
 			<c:if test="${restrictDay.applicableToAll == 'N'}">
 			<label class="paddingLeft paddingRight">
                <input name="isApllicable" type="radio" id="yesApplica" value="Y" >
                <span class="paddingLeft">Yes</span></label>
              <label class="paddingLeft">
                <input name="isApllicable" type="radio" id="noApplica" value="N" checked>
                <span class="paddingLeft">No</span></label>
 			</c:if>
              
            </div>
       </div>
    </div>
              <div class="clearfix"></div>
   <div class="form-group" style="display: none;" id="selectRoles">
       <label class="col-md-3 paddingRight">Select Roles<span class="text-danger paddingLeft">*</span></label>
       <div class="col-md-9">
           <select multiple class="chosen-select form-control" name="rolesList" id="roleId" data-placeholder="Select Roles" field-name="Role" data-validation-optional="true">
               <option>--Select Roles--</option>
               <c:forEach var="role" items="${rolesList}">
               <c:set var = "NUM" value = "${0}"/>
                <c:forEach var="existedRoles" items="${existedRoles}">
                	<c:if test="${existedRoles.id eq role.id}">
						<c:set var = "NUM" value = "${1}"/>
							<option selected value="${role.id}">${existedRoles.displayName}</option>
					</c:if> 
				</c:forEach>
				<c:if test="${NUM == 0}">
						<option value="${role.id}">${role.displayName}</option>
				</c:if>
				</c:forEach>
           </select>
       </div>
    </div>
          </div>
        </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" class="btn btn-primary" onclick="storeRestrictedDay();">
            <input type="button" value="Cancel" class="btn btn-default ml10" onClick="javascript:window.location.href = '<c:url value='/viewRestrictDay'/>'" >
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
      </form:form>
    </div>
  </section>

<script type="text/javascript">
function storeRestrictedDay(){
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
         if ($('#restrictDay_form').validate(false, validationSettings)){  
 	 	  	var formData = $($('#restrictDay_form')[0]).serialize();
		        	  $("#restrictLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.post(sourceleadUrl+"/storeRestrictedDay?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId+"",formData, function(data) {
			  				if(data == "success"){
			  					$("#restrictLoading").removeAttr("style");
	    		     			location.replace(sourceleadUrl+"/viewRestrictDay");
			  				}
			  			   }); 
		    	         return true; 
         }else{
	    		return false;
	    	  } 
}
$('body').on('blur', '#restrictDay_form', function() {
	$("#restrictDay_form").showHelpOnFocus();
	$("#restrictDay_form").validateOnBlur(false, validationSettings);  
}); 
</script>

<!-- start script for datepicker -->
<script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormat = 'yy-dd-mm'; 
	  }
	  $('#startDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  onSelect: function (selected) {
        	var dt = $(this).datepicker('getDate', '+1d');
              dt.setDate(dt.getDate());
              $("#endDate").datepicker("option", "minDate", dt);
              removeStartValid();
          }
	  });
	  
	  $('#endDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	onSelect: function (selected) {
      	  var dt = $(this).datepicker('getDate', '-1d');
            dt.setDate(dt.getDate());
            $("#startDate").datepicker("option", "maxDate", dt);
            removeEndValid();
        }
	  });
	  
  } );
</script>
<!-- end script for datepicker -->

<script>
 
  $(document).ready(function(){
 	$("#noApplica").click(function(){
 		$("#selectRoles").show();
 		$('#roleId').removeAttr('data-validation-optional');
		$('#roleId').attr('data-validation',"required");
 		$("#selectRoles").find('p.jquery_form_error_message').remove();
 	});
 	$("#yesApplica").click(function(){
 		$("#selectRoles").find('p.jquery_form_error_message').remove();
 		$('#roleId').removeAttr('data-validation',"required");
		$('#roleId').attr('data-validation-optional');
 		$("#selectRoles").hide();
 	});	 
 });
 
 </script>
 
<script>
function removeStartValid(){
	$('#startDateDiv').find('p.jquery_form_error_message').remove();
}

function removeEndValid(){
	$('#endDateDiv').find('p.jquery_form_error_message').remove();
}
 </script>


<jsp:include page="../masterFooter.jsp" />

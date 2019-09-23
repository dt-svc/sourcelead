<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
 window.onload = function() {
	 var restrictdayId = '${restrictDay.id}';
		if(restrictdayId != null && restrictdayId != '' && restrictdayId != 'undefined'){
			$("#restrictDayId").val(restrictdayId);
		}else{
			$("#restrictDayId").val(0);
		}
	 
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
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/leaveConfiguration'/>">Leave Configuration</a> &raquo; <a href="<c:url value='${appPath}/viewRestrictDay'/>">Restrict Day List</a> &raquo; Add Restrict Day</div>
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
       <input type="hidden" id="restrictDayId" name="restrictDayId" value=""/>
       <input type="text" name="restrictName" placeholder="Restrict Name" value="${restrictDay.restrictName}" field-name="Restrict Name" class="form-control" data-validation="required validate_length length1-250">
       </div>
    </div>
    <div class="form-group" id="startDateDiv">
       <label class="col-md-3">Start Date<span class="text-danger paddingLeft">*</span></label>
        <div class="col-md-9">
        <div class="input-group errorInptgroup">
       <input type="text" id="startDate" name="startDate" placeholder="Start Date" value="${restrictDay.startDateStr}" field-name="Start Date" class="form-control calendar" data-validation="required" readonly>
       <span class="input-group-addon" onClick="clearFromDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
       </div>
       </div>
    </div>
    <div class="form-group" id="endDateDiv">
       <label class="col-md-3">End Date<span class="text-danger paddingLeft">*</span></label>
        <div class="col-md-9">
        <div class="input-group errorInptgroup">
       <input type="text" id="endDate" name="endDate" placeholder="End Date" value="${restrictDay.endDateStr}" field-name="End Date" class="form-control calendar" data-validation="required" readonly>
        <span class="input-group-addon" onClick="clearToDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
       </div>
        <div id="warnDiv"></div>
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Reason</label>
        <div class="col-md-9">
            <textarea class="form-control" name="reason" value="${restrictDay.reason}" rows="2" data-validation="validate_length length1-500" data-validation-optional="true" placeholder="Reason">${restrictDay.reason}</textarea>
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3 paddingRight">Applicable for all<i class='fa fa-info-circle' title="If 'YES' the Restrict Day will be apllicable for all users .If 'NO' the Restrict Day will be applicable to the selected users"></i></label>
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
            <input type="button" value="Cancel" class="btn btn-default ml10" onClick="javascript:window.location.href = '<c:url value='${appPath}/viewRestrictDay'/>'" >
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
		        	  $("#restrictLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.post(sourceleadUrl+"/storeRestrictedDay?"+tokenParamName+"="+tokenValue+"&userId="+userId+"&orgLocationId="+orgLocationId+"",formData, function(result) {
		        		  if (result && result.response === "INVALID_DATA") {
		  	    		    showError(result.responseMessage);
		  	    		   } else if (result.response === "AWKWARD") {
		  	    		    location.href = "/errorPage";
		  	    		   } else if (result.response === "SUCCESS") {   
		        		        $("#restrictLoading").removeAttr("style");
	    		     			location.replace(sourceleadUrl+"/viewRestrictDay");
			  				} else if (result.indexOf('Sign In') !== -1) {
		               			location.replace(sourceleadUrl);
		               		} else {
		               		    showError("Operation failed, please try again");
		               		}
						$("#restrictLoading").hide();
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
            checkDuplicateRestrictDay();
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
function checkDuplicateRestrictDay(){
	$('.jquery_form_error_message cOrange').hide();
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if(startDate != null && startDate != '' && startDate != 'undefined' && startDate != null && startDate != '' && startDate != 'undefined'){
		$.get(sourceleadUrl + "/checkDuplicateRestrictDay?&orgLocationId="+ orgLocationId+"&startDate="+startDate+"&endDate="+endDate, function(data) {
			if(data == true){
				$("#warnDiv").after("<p class='jquery_form_error_message cOrange'><i class='fa fa-exclamation-circle'></i> Your restrict days are already added.</span>");
			}else{
				$('.jquery_form_error_message cOrange').hide();
			}
		});
	}
}
</script>
 
<script>
function removeStartValid(){
	$('#startDateDiv').find('p.jquery_form_error_message').remove();
}

function removeEndValid(){
	$('#endDateDiv').find('p.jquery_form_error_message').remove();
}
 </script>
<!-- <script>
function clearStartDate(){
	$("#startDate").val("");
	};
function clearEndDate(){
	$("#endDate").val("");
	};
	
</script> -->

<script>
function clearFromDate(){
	$('#endDate').attr('value', '');
	$('#endDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#startDate").val("");
	};
	
function clearToDate(){
	$('#startDate').attr('value', '');
	$('#startDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#endDate").val("");
	};
</script>

<jsp:include page="../masterFooter.jsp" />

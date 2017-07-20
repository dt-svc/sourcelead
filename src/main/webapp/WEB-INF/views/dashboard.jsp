<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <link href="resources/css/jquery-ui.css" rel="stylesheet"> -->



<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
.dashboard-widget {
/* min-height: 400px !important; */
}
.dashboard-widget.dashboard-widget-icons{
min-height: 125px !important;
}
.dashboard-widget .blank-pages{
padding: 0;
min-height: 100px;
}
/* .side-bar .side-nav ul li span {
display: inline-block !important;
float: none !important;
} */
.custom-datatble table{
 margin-top: 20px !important;
 }
 #requirementList {
	border-bottom: 1px solid #eae6e6;
}
 .custom-datatble .dataTables_filter{
   top: -35px !important;
    padding: 0 !important;
 }
 .dashboard-datepkr{
       position:relative;
}
/* .main-header span.logo i{
position: relative;
top:10px;
} */
.ui-timepicker-wrapper {
z-index: 999999;
width: 11.5%;
}
/* .dashboard-datepkr input[type="text"]{
       width:115px;
       border:1px solid #ccc;
       margin-right:5px;
}
.dashboard-datepkr .fa{
   position: absolute;
   right: 10px;
   top: 4px;
} */

</style>

 <style type="text/css">
.clients_wrap_table {
overflow-x: auto;
}
</style>

 <script type="text/javascript">
 $( document ).ready(function() {
	 var settingsConfigured = '${SourceLeadWeb_settingsConfigured}';
	 if(settingsConfigured == "N"){
		configureDiv();
	 }
	 
	});
</script> 

<form:form class="login-form clearfix"  id="configure_form"  name="configure_form" method="post" >
   <div id ="configurePopUpDiv"></div>
</form:form>



  
<!--  configurediv -->
 <%-- <div class="configure_wrap">
     <div id="loading"></div> 
    	<div class="configure_wrap-inner">
        	<h2 class="text-danger">Configure</h2>
            <form:form class="login-form clearfix"  id="forgetPassword_form"  name="forgetPassword_form" method="post" >
	            <div id="forgotPasswordDiv">
	              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
        <div class="form-group">
          <label>Time Zone<span class="text-danger paddingLeft">*</span></label>
          <div class="clearfix">
               <select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Time zone" data-validation="required" id="timeZone" name="timeZone">
                     </select>
                     <span class="hidden">Time Zone</span>
           </div>
        </div>
        <div class="form-group">
          <label>Date Format<span class="text-danger paddingLeft">*</span></label>
          <div class="clearfix dropdown-select">
           <select class="form-control">
            <option selected>---Select Date Formate ---</option>
            <option>DD/MM/YYYY</option>
            <option>DD/MM/YYYY</option>
                                    <option>DD/MM/YYYY</option>
                                                <option>DD/MM/YYYY</option>
           </select>
          </div>
        </div>
        <div class="form-group">
          <label>Organization Short Code<span class="text-danger paddingLeft">*</span></label>
          <div class="clearfix">
            <input type="text" class="form-control" id="" name="" value="">
          </div>
        </div>
         <div class="clearfix">
          <label class="text-primary">Note</label>
          <div class="clearfix">
            <p>Text Note</p>
          </div>
        </div>
      </div> 
	            </div>
	            <div class="clearfix">
	            <div class="col-md-6">
	            <input type="button" value="Proceed" class="btn btn-primary btn-block">
	            </div>
	            <div class="col-md-6">
	            <input type="button" value="Cancel" onclick="logoutForConfigure();" class="btn btn-default btn-block">
	            </div>
	            </div> 
            </form:form>         
        </div>        
    </div>  --%>
<!--  configurediv -->


<script>
		function logoutForConfigure() {
			document.getElementById("logoutForm").submit();
		}
</script>


<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>










  
<section class="content-wraper">
	    <section class="content-header clearfix">
	      
	      <c:if test="${!empty param.invitation}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>You accepted invitation successfully.</span>
			</div>
	     </c:if>
	     
	     <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector1"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	    
	      <div class="pull-left">
	        <h2>Dashboard</h2>
	      </div>
	    </section>
	    
	    <div class="clearfix row mb">
	      <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
			 <c:if test = "${SourceLeadWeb_timesheet == 'Y'}">       
			   <jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
			 </c:if> 
	      </sec:authorize>
			 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
				<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
					<jsp:include page="dashboardRequirement.jsp"></jsp:include> 
				</c:if>
		     </sec:authorize>
		     
	    </div>
	 <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES','ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">   
	    <div class="clearfix row mb">
		     <jsp:include page="expensesDashboardCounts.jsp"></jsp:include>
	    </div>
	 </sec:authorize>  	
	 
	    	<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
	    	
	    			<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
					<jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include> 
					</sec:authorize>
					
					<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
					<jsp:include page="onboardingCandidatesList.jsp"></jsp:include> 
					</sec:authorize>
					
			    <div class="clearfix row mb"> 
			    	 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
			   	 	 </sec:authorize>
			   	 	 <%--  <sec:authorize access="hasAnyRole('ROLE_VIEW_INTERVIEW')">
			    	 <jsp:include page="dashboardInterviews.jsp"></jsp:include>
			    	 </sec:authorize> --%>
			    
		    </c:if>
		      
		     		<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
		     		<jsp:include page="dashboardApplicants.jsp"></jsp:include>
		     		</sec:authorize>
		     </div>
	    
	    
	   
	    
	    <!-- <div class="row clearfix"> -->
	    <!-- <div class="col-md-6 mb">

        <div class="clients_wrap_table dashboard-widget overflow_inherint"  id="assignTaskListDiv" style="display: none;">

           <h4 class="clearfix">Tasks
        <b class="dashboard-datepkr"><input class="input-sm" id="dasboardTaskDatpicker" readonly="readonly" type="text"><i class="fa fa-calendar"></i></b>  
          </h4>	
          <div class="clearfix require_wrap_table dashboard-data Tasks overflow_inherint">
            <div id="assignTaskList"></div>
            
          </div>
        </div>
        Pagenation Ends 
      </div> 
      </div>-->
       <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_INTERVIEW')">
	   				  <div class='row'>
						<div class='col-lg-12'> 
						<div class='content-area clearfix'>
							<div class='blank-pages'>
							        <p><i class='fa fa-5x fa-bar-chart'></i></p>
						        	<p>No Records Found</p>
							 </div>
						</div>
						</div>
					  </div> 
		</sec:authorize> 
	    
	    <div class="clearfix"></div> 
	    
	     	<jsp:include page="dashboardEvents.jsp"></jsp:include>	    
	  
</section>



<jsp:include page="masterFooter.jsp" />
<script type="text/javascript">
$('#dasboardTaskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		assignTasksList();
	   }
	});
</script>

<script type="text/javascript">
	function configureDiv(){
	getDateFormatByOrgLocation();
	var result = '';
	result=result+'<div class="sideMask" style="display:block"></div>';
	result=result+'<div class="configure_wrap">';
	result=result+'	<div id="loading"></div>';
	result=result+'<div class="configure_wrap-inner">';
	result=result+'<h2 class="text-danger">Configure</h2>';
	result=result+'<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">'; 
	result=result+'<div class="form-group">';
	result=result+'<label>Time Zone<span class="text-danger paddingLeft">*</span></label>';
	result=result+'<div class="clearfix">';
	result=result+'<select class="chosen-select form-control" data-placeholder="Time Zone" field-name="Time zone" data-validation="required" id="timeZone" name="timeZone">';
	result=result+'</select>';
	//result=result+'<span class="hidden">Time Zone</span>';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="form-group">';
	result=result+'<label>Date Format</label>';
	result=result+'<div class="clearfix dropdown-select">';
	result=result+'<select class="form-control" name="dateFormatForOrg" id="dateFormatForOrgId" data-validation="required" field-name="Date Format">';
	result=result+'</select>';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="form-group mb10">';
	result=result+'<label>Organization Short Code<span class="text-danger paddingLeft">*</span></label>';
	result=result+'<div class="clearfix" id="generatedUserIdDiv">';
	result=result+'<input type="text" class="form-control" id="generatedUserId" style="text-transform:uppercase" name="generatedUserId" data-validation="required validate_length length3-3" field-name="Organization Short Code" onkeyup="validateOrgShortCode();" maxlength="3">';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="clearfix">';
	result=result+'<p class="text-muted small" style="font-size: 11px;" title="Org Short code cannot be modified once submitted."><i class="fa fa-info-circle"></i> Org Short code cannot be modified once submitted.</p>';
	result=result+'</div>';
	
	result=result+'<div class="form-group">';
	result=result+'<label>Average Working Hours</label>';
	result=result+'<div class="clearfix dropdown-select" id="averageWorkingHoursDiv">';
	result=result+'<input type="text" class="form-control" maxlength="2" name="averageWorkingHours" id="averageWorkingHours" data-validation="required" field-name="Average Working Hours">';
	result=result+'</div>';
	result=result+'</div>';
	
	result=result+'</div>';
	result=result+'</div>';
	result=result+'<div class="clearfix">';
	result=result+'<div class="col-md-6">';
	result=result+'<input type="button" value="Proceed" id="saveOrgConfiguration" class="btn btn-primary btn-block" onclick="storeOrgShortCode();">';
	result=result+'</div>';
	result=result+'<div class="col-md-6">';
	result=result+'<input type="button" value="Cancel" onclick="logoutForConfigure();" class="btn btn-default btn-block">';
	result=result+'</div>';
	result=result+'</div>';
	result=result+'</div>'; 
	result=result+'</div>';
	$("#configurePopUpDiv").empty();
	$("#configurePopUpDiv").append(result);
	getTimezonesList(); 
}
</script>

<script>
function validateOrgShortCode(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgShortId = $("#generatedUserId").val();
	$('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove();
		  $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/validateOrgShortCode?${_csrf.parameterName}=${_csrf.token}&orgShortId="+orgShortId, 
		        success: function(data) {
		        	if(data == true){
		        		$("input#generatedUserId").parent().after("<p class='jquery_form_error_message'><span>Org Short Code Alrady Exists !!!</span>");
		        	}else{
		        		 $('#generatedUserIdDiv').parent().find('p.jquery_form_error_message').remove(); 
		        	}
		        }
		 }); 
}
$(function(){
    $("#generatedUserId").keypress(function(event){
        var ew = event.which;
        if(ew == 32)
            return true;
        if(65 <= ew && ew <= 90)
            return true;
        if(97 <= ew && ew <= 122)
            return true;
        return false;
    });
});
$(function() {
	  $('#averageWorkingHoursDiv').on('keydown', '#averageWorkingHours', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});
$(function(){
    $("#averageWorkingHours").keyup(function(event){
        var ew = $("#averageWorkingHours").val();
        $('#averageWorkingHoursDiv').parent().find('p.jquery_form_error_message').remove(); 
        if(ew > 24){
        	$("#averageWorkingHours").val('');
        	$("input#averageWorkingHours").parent().after("<p class='jquery_form_error_message'><span>Average Working Hours should be less then or equal to 24</span>");
        }
    });
});

</script>


<script type="text/javascript">
function storeOrgShortCode(){
 var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
   };  
   		if ($('#configure_form').validate(false, validationSettings)){   
		var frm = $('#configure_form').serializeFormJSON();
		var jsonData = JSON.stringify(frm);
		var form = $('#configure_form')[0];
  		var formData = new FormData(form);
   		formData.append("json",jsonData);
		var sourceleadUrl = '${sourceleadUrl}';
	 	var locationCode ='${SourceLeadWeb_locationCode}';
		$("#saveOrgConfiguration" ).addClass('button').val('Processing..');
   		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        $.ajax({
       	      type: "POST",
       	      data: formData,
       	      contentType : 'application/json; charset=utf-8',
       	      enctype: 'multipart/form-data',
   	          processData: false,
   	          contentType:false,
       	      dataType : 'json',
            	  url: sourceleadUrl+"/saveOrgShortCodeform?${_csrf.parameterName}=${_csrf.token}&locationCode="+locationCode,
                  success: function(data) {
                	  var result = '';
                	  result=result+'<div class="sideMask" style="display:none"></div>';
                	  $("#configurePopUpDiv").empty();
                	  $("#configurePopUpDiv").append(result);
      			},  
             });
    	    return true;
    	 }else{
    		return false;	
       } 
         $('#configure_form').showHelpOnFocus();
         $('#configure_form').validateOnBlur(false, validationSettings); 
}  
</script>

<script>
function getTimezonesList(){
	var sourceleadUrl ='${sourceleadUrl}';
	var country = '${SourceLeadWeb_orgLocations.country}';
	if(country){
		 $.ajax({
		    	type: "POST",
		    	url : sourceleadUrl+"/getTimezonesList?${_csrf.parameterName}=${_csrf.token}", 
		        success: function(data) {
		        var json = JSON.parse(data);
		        var output = "";
		        output = output + "<option value=''>--Select--</option>";
		        if(country == 'India'){
	    			output = output + "<option value='246' selected> Asia/Kolkata </option>";
	    		}else if(country == 'United States'){
	    			output = output + "<option value='132' selected> America/Chicago </option>";
	    		}else if(country == 'United Kingdom'){
	    			output = output + "<option value='333' selected> Europe/London </option>";
	    		}else if(country == 'Singapore'){
	    			output = output + "<option value='272' selected> Asia/Singapore </option>";
	    		}else if(country == 'France'){
	    			output = output + "<option value='342' selected> Europe/Paris </option>";
	    		}else if(country == 'Australia'){
	    			output = output + "<option value='298' selected> Australia/Adelaide </option>";
	    		}
		        	for (var i=0; i<json.length; i++){
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.timeZoneId+"</option>";
					}
		        	$("#timeZone").empty();
		        	$("#timeZone").append(output);
		        	$("#timeZone").trigger("chosen:updated");
		        }
		 });
	}

}
</script>


<script type="text/javascript">
function getDateFormatByOrgLocation() {
		    var locationCode ='${SourceLeadWeb_locationCode}';
			var sourceleadUrl = '${sourceleadUrl}';
           $.ajax({
       	      type: "GET",
       	      dataType : 'json',
             	url: sourceleadUrl+"/getDateFormatByOrgLocation?${_csrf.parameterName}=${_csrf.token}&locationCode="+locationCode, 
               	success: function(result) {
						$("#dateFormatForOrgId").empty();
						$("#dateFormatForOrgId").append("<option value=''>-- Select Date Format --</option>");
						if(result == 'dd-MM-yyyy'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy' selected>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}else if(result == 'MM-dd-yyyy'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy' selected>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}else if(result == 'yyyy-dd-MM'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM' selected>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}else if(result == 'yyyy-MM-dd'){
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd' selected>yyyy-MM-dd</option>");
						}else{
							$("#dateFormatForOrgId").append("<option value=''>-- Select Date Format --</option>");
							$("#dateFormatForOrgId").append("<option value='dd-MM-yyyy'>dd-MM-yyyy</option>");
						    $('#dateFormatForOrgId').append("<option value='MM-dd-yyyy'>MM-dd-yyyy</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-dd-MM'>yyyy-dd-MM</option>");
	                        $('#dateFormatForOrgId').append("<option value='yyyy-MM-dd'>yyyy-MM-dd</option>");
						}
						
						$('#dateFormatForOrgId').trigger("chosen:updated");
		        },  
           });
           $(function(){
       	    var config = {
       			  '.chosen-select'           : {},
       			  '.chosen-select-deselect'  : {allow_single_deselect:true},
       			  '.chosen-select-no-single' : {disable_search_threshold:10},
       			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
       			  '.chosen-select-width'     : {width:"95%"}
       			}
       	    $("#c2owner").chosen();
       	    $('.chosen-select').each(function() {
       				var $this = $(this);
       				$this.next().css({'width': '100%'});
       		  })
       	});
}
</script>
	
<script type="text/javascript">
	function assignTasksList() {
		var sourceleadUrl ='${sourceleadUrl}';
		var date = $("#dasboardTaskDatpicker").val();
		var status =  "myTask";
		
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getTasksList?${_csrf.parameterName}=${_csrf.token}&status="+status+"&date="+date,
	        success: function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result + '<table id="dasboardTasksPagination">'; 
	        	 result = result + '<thead>';
	         	result = result + '<tr>';
	         	result = result + '<th>Subject</th>';
	         	result = result + '<th>Date &amp; Time</th>';
	         	result = result + '<th>Priority</th>';
	         	result = result + '<th>Status</th>';
	         	result = result + ' </tr>';
	        	result = result + ' </thead>';
	        	result = result + ' <tbody>';
	        	for (var i=0; i<json.length; i++)
				{
	        		var report = json[i];
	        		
	        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
	        			taskDateStr = report.taskDateStr
	        		}else{
	        			taskDateStr = "--";
	        		}
	        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
	        			taskTime = report.taskTime;
	        		}else{
	        			taskTime = "";
	        		}
	        		if(report.priority != null && report.priority != '' &&  report.priority != 'undefined'){
	        			priority = report.priority;
	        		}else{
	        			priority = "--";
	        		}
	        		
	        		result = result + '<tr>';
	        		result = result + ' <td><div title="'+report.subject+'"><span>'+report.subject+'</span></div></td>';
	        		result = result + '<td><div class="text-center">'+taskDateStr+'  '+taskTime+'</div></td>';
	        		result = result + '<td>'+priority+'</td>';
	        		result = result + ' <td>'+report.taskStatus+'</td>';
	        		result = result + ' </tr>';
	               
				}
	        	 result = result + '     </tbody>';
	        	 result = result + '   </table>';
	        	 $("#assignTaskList").empty();
	           	 $("#assignTaskList").append(result); 
	           	 if(json.length > 0){
	           		$("#assignTaskListDiv").css("display", "block");
	           	 }
	           	 
	           	dasboardTasksPagination();
	        }
		});
		
	}

	</script>
	
	<script>
function dasboardTasksPagination(){
    $('#dasboardTasksPagination').DataTable( { 
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
  $("#selector").fadeIn(300);
  $('#selector').delay(3000).fadeOut('slow');
</script> 

<script>
  $("#selector1").fadeIn(300);
  $('#selector1').delay(3000).fadeOut('slow');
</script> 
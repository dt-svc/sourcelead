<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link href="resources/css/register-style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/register-style.css" rel="stylesheet">

<style>
.clsDatePicker {
	z-index: 100000 !important;
}

.project_tabs {
    border:none !important;
    }
    
.disabledLink
{
	color: #333;
	text-decoration : none;
}
</style>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client Project</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo;<a href="<c:url value='/projectDashboard'/>"> Projects List</a>
			&raquo; Client Project
	</section>
	
	<div class="content-area clearfix noPad">
	  <div id="loading"></div>
	  <div class="newPrDetails newPrDetailsPMO"> 
     <div class="clearfix mt"></div>
		
			<form:form id="project_form" name="project_form" method="post">
			 <input type="hidden" name="projectClientId" id="projectClientId" value=""/>
			<div class="col-md-12 clearfix mt5 mb5">
			<div class="col-md-6 col-lg-6">
				<div class="form-group mb10">
					<label class="col-md-3 mt10" for="Project Manager">Manager <span
						class="text-danger">*&nbsp;</span></label>
					<div class="col-md-9">
						<div class="form-icon-min" id="chosen-level1Manager">
							<select class="form-control chosen-select"
								name="level1Manager" id="level1Manager" field-name="Manager"
								data-validation="required" onchange="getLevel2Approvers();">
								<option value="">-- Select Level1 Manager --</option>
							</select> <span class="btn btn-default formic1 addLevel"
								id="level2Toggle" onclick="getLevel2Approvers();">Add
								Level-II</span>
						</div>
						<span class="hidden">Select Project Manager</span>
					</div>
				</div>
				</div>
				
				<div class="col-md-6 col-lg-6">
				<div class="form-group level-second mb10" id="level2Div">
						<label class="col-md-3 mt10" for="Level-II">Level-II</label>
						<div class="col-md-9">
							<select class="form-control chosen-select" name="level2Manager"
								id="level2Manager">
							</select>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-md-12 clearfix mt5 mb5">
			<div class="col-md-6 col-lg-6">
			  <div class="form-group mb10">
					<label class="col-md-3 mt10" for="Project Manager">Select Client<span
						class="text-danger">*&nbsp;</span></label>
					<div class="col-md-9">
						<div class="form-icon-min" id="clientsDiv">
							<select class="form-control chosen-select"
								name="clientOrgLocId" id="clientOrgLocId" field-name="Client"
								data-validation="required" onchange="getEndClientProjects();">
								<option value="">-- Select Client --</option>
								<c:forEach var="report" items="${clientsList}">
								 <c:if test="${report.locationId.id == SourceLeadWeb_orgLocations.id}">
								    <option value="${report.responderLocationId.id}">${report.responderLocationId.org.orgName}</option>
								 </c:if>
								 <c:if test="${report.responderLocationId.id == SourceLeadWeb_orgLocations.id}">
								    <option value="${report.locationId.id}">${report.locationId.org.orgName}</option>
								 </c:if>
								</c:forEach>
							</select> 
						</div>
						<span class="hidden">Select Client</span>
					</div>
				</div>
			</div>
			
			
			<div class="col-md-6 col-lg-6">
				<div class="form-group mb10">
					<label class="col-md-3 paddingRight">Client Project <span class="text-danger">*&nbsp;</span></label>
					<div class="col-md-9">
						<div class="form-icon-min" id="clientProjectDiv">
							<select class="form-control chosen-select"
								name="clientProject" id="clientProject" field-name="Client Project"
								data-validation="required">
								<option value="">-- Select Project --</option>
							</select> 
							<i class='fa fa-info-circle' title='Client projects will be auto populate after selection of client'></i>
						</div>
						<span class="hidden">Select Client Project</span>
					</div>
				</div>
				</div> 
				</div>
              <div class="clearfix"></div>
				<div class="form-footer clearfix mt">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'" class="btn btn-danger ml">
							<input type="button" value="Save & Continue"  id="save_project" onclick="saveClientProject()" class="btn btn-primary">
						</div>
					</div>
				</div>



			</form:form>
   	</div>
	
	
	</div>
	
</section>


<jsp:include page="../masterFooter.jsp" />



<script>
	$(document).ready(function() {
		getProjectLevel1Approvers();
	});
</script>

<script>
function getEndClientProjects(){
	var sourceleadUrl ='${sourceleadUrl}';
	var clientOrgLocationId = document.getElementById("clientOrgLocId").value;
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getEndClientProjects?${_csrf.parameterName}=${_csrf.token}&clientOrgLocationId="+clientOrgLocationId,
	        success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>-- Select Project --</option>";
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		output = output + "<option value='" +opt.id+ "'>"+opt.projectName+"</option>";
				}
	        	
	        	$("#clientProject").empty();
	        	$("#clientProject").append(output);
	        	$("#clientProject").trigger("chosen:updated");
	        }
	    });
}
</script>





<script type="text/javascript">
function getProjectLevel1Approvers(approverUserId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $("#level1Manager").empty();
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getProjectLevel1Approvers?${_csrf.parameterName}=${_csrf.token}",
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Level1 Manager --</option>";
		        	for (var i=0; i<json.length; i++)
					{
		        		var selected = "";
		        		var opt = json[i];
		        		if(approverUserId != null && approverUserId != 'undefied' && approverUserId != '' && approverUserId == opt.id){
		        			selected = "selected";
		        		}
			        		output = output + "<option value='" +opt.id+ ","+opt.userType+"' "+selected+">"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	
			        $("#level1Manager").append(output);
			        $('#level1Manager').trigger("chosen:updated");
		        }
		    });
	 }
</script>


<script type="text/javascript">
function getLevel2Approvers(level1ApproverUser){
	var currentApproverId = '';
	$('#chosen-level1Manager').find('p.jquery_form_error_message').remove();
	if(level1ApproverUser != null && level1ApproverUser != '' && level1ApproverUser != 'undefined'){
		currentApproverId = level1ApproverUser;
	}else{
		currentApproverId = document.getElementById("level1Manager").value;
	}
	var currentUser = currentApproverId.split(',');
	var currentUserId = currentUser[0];
	var currentUserType = currentUser[1];
	var count = 1;
	$(".myCheckbox").each(
					function() {
						var localId = this.id;
						document.getElementById(localId).checked = false; 
	});
	$(".myCheckbox").val('');
	
	$("#level2Manager").empty();    
	$('#level2Manager').val('').trigger('chosen:updated');
	if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType != 'Admin'){
	    $("#level2Toggle").show();
		getChildLevel1Approvers(currentUserId);
	}else if(currentUserType != null && currentUserType != '' && currentUserType !='undefined' && currentUserType == 'Admin'){
	    $("#level2Toggle").hide();   
	    $("#level2Div").removeClass("level-second-active");
	}
	
	
} 
</script>

<script type="text/javascript">
function getChildLevel1Approvers(currentApproverId,level2ApproverUserId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentApproverId,
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	output = output + "<option value=''>-Select Manager-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		var selected = '';
			        		if(level2ApproverUserId != null && level2ApproverUserId != 'undefied' && level2ApproverUserId != '' && level2ApproverUserId == opt.id){
			        			selected = "selected";
			        		}
			        		output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.firstName+" "+opt.lastName+"</option>";
					}
		        	$("#level2Manager").empty();
			        $("#level2Manager").append(output);
			        $('#level2Manager').trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
	 }else{
		 getApproversLevel2();
		
	 }
} 
</script>

<script>
function saveClientProject(){
	 if ($('#project_form').validate(false, validationSettings)){ 
		 
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 var sourceleadUrl ='${sourceleadUrl}';
		 var formData = getFormData("project_form");
		 
	 		$.ajax({
				type : "POST",
  	    	    data : formData,
				url : sourceleadUrl+"/storeClientProject?${_csrf.parameterName}=${_csrf.token}", 
				success : function(project) {
					 location.replace(sourceleadUrl+"/projectSummary?projectUUID="+project.projectUUID+"&tabType=projectMembers");
					 $("#loading").removeAttr("style");
				},
			}); 
  		 return true;
		 
	 }else{
			return false;
	  }
	 
	  $('body').on('blur', '#project_form', function() {
  		$("#project_form").showHelpOnFocus();
  		$("#project_form").validateOnBlur(false, validationSettings);
  	});
}
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
 function prepareChosenScripts(){ 
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
 }
</script>


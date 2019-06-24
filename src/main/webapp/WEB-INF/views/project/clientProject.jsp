<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client Project</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo;<a href="<c:url value='${SourceLeadWeb_pmsUrl}/projectDashboard'/>"> Projects List</a>
			&raquo; Create Client Project
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
					<label class="col-md-3 mt10" for="Project Manager">Select Client<span class="text-danger">*&nbsp;</span></label>
					<div class="col-md-9 chooRadius">
						<div class="input-group col-md-9 pull-left paddingRight" id="clientsDiv">
							<select class="form-control chosen-select"
								name="clientOrgLocId" id="clientOrgLocId" field-name="Client"
								data-validation="required" onchange="getEndClientProjects();">
								<option value="">-- Select Client --</option>
								<c:forEach var="report" items="${clientsList}">
								 	<option value="${report.clientLocationId}">${report.clientName}</option>
								</c:forEach>
							</select> 
                </div>	
                 <div class="col-md-1 noPad"><span class="input-group-addon cursorPointer bg-blue reFreBtn" onclick="refreshClientList(); "><i class="fa fa-refresh "></i></span></div>
              <sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT_CONTACT')"> 
                 <span class="col-md-2 noPad clearfix"><a href="${SourceLeadWeb_vmsUrl}/client" target="_blank"><i class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a> </span>	
               </sec:authorize>              						
					</div>
				</div>
			</div>
			
			
			<div class="col-md-6 col-lg-6">
				<div class="form-group mb10">
					<label class="col-md-3 paddingRight mt10">Client Project <span class="text-danger">*&nbsp;</span></label>
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
							</select>
							
							<span class="btn btn-default formic1 addLevel"
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
					<label class="col-md-3 mt10" for="Project Manager">Expense Approver &nbsp;<i class='fa fa-info-circle' title='Expense Approver for project. If not selected managers will be the default approvers'></i>
					</label>
					<div class="col-md-9">
						<div class="form-icon-min" id="chosen-level1Manager">
							<select class="form-control chosen-select"
											name="level1ExpenseApprover" id="level1ExpenseApprover" field-name="Expense Approver"
											data-validation-optional="true" onchange="getLevel2ExpenseApprovers();">
											<option value="">-- Select Level1 Approver --</option>
											
							</select>
							
							<span class="btn btn-default formic1 addExpenseApproverLevel"
								id="level2ExpenseApproverToggle" onclick="getLevel2ExpenseApprovers();">Add
							Level-II</span>
						</div>
						<span class="hidden">Select Project Manager</span>
					</div>
				</div>
				</div>
				
				<div class="col-md-6 col-lg-6">
				<div class="form-group expenselevel-second" id="level2ExpenseApproverDiv">
								<label class="col-md-3" for="Level-II">Level-II Approver</label>
								<div class="col-md-9">
									<select class="form-control chosen-select" name="level2ExpenseApprover"
										id="level2ExpenseApprover">
									</select>
								</div>
							</div>
				</div>
			</div>
			
			
              <div class="clearfix"></div>
				<div class="form-footer clearfix mt">
					<div class="col-lg-12">
						<div class="pull-right">
							<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/pms/projectDashboard'/>'" class="btn btn-danger ml">
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
		getProjectLevel1ExpenseApprovers();
	});
</script>

<script>
function getEndClientProjects(){
	var clientOrgLocationId = document.getElementById("clientOrgLocId").value;
	 $.ajax({
	    	type: "GET",
	        url: pmsUrl+"/getEndClientProjects?${_csrf.parameterName}=${_csrf.token}&clientOrgLocationId="+clientOrgLocationId,
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
	 $("#level1Manager").empty();
		 $.ajax({
		    	type: "GET",
		        url: pmsUrl+"/getProjectLevel1Approvers?${_csrf.parameterName}=${_csrf.token}",
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
			        		output = output + "<option value='" +opt.id+ ","+opt.userType+"' "+selected+">"+opt.fullName+" ("+opt.userType+")</option>";
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
function getLevel2ExpenseApprovers(level1ExpenseApprover){
	var currentExpenseApproverId = '';
	$('#chosen-level1ExpenseApprover').find('p.jquery_form_error_message').remove();
	if(level1ExpenseApprover != null && level1ExpenseApprover != '' && level1ExpenseApprover != 'undefined'){
		currentExpenseApproverId = level1ExpenseApprover;
	}else{
		currentExpenseApproverId = document.getElementById("level1ExpenseApprover").value;
	}
	
	var currentExpenseApprover = currentExpenseApproverId.split(',');
	var currentExpenseApproverId = currentExpenseApprover[0];
	var currentExpenseApproverType = currentExpenseApprover[1];
	var count = 1;
	$(".myCheckbox").each(
					function() {
						var localId = this.id;
						document.getElementById(localId).checked = false; 
	});
	$(".myCheckbox").val('');
	
	$("#level2ExpenseApprover").empty();    
	$('#level2ExpenseApprover').val('').trigger('chosen:updated');
	if(currentExpenseApproverType != null && currentExpenseApproverType != '' && currentExpenseApproverType !='undefined' && currentExpenseApproverType != 'Admin'){
	    $("#level2ExpenseApproverToggle").show();
		getChildLevel1ExpenseApprovers(currentExpenseApproverId);
	}else if(currentExpenseApproverType != null && currentExpenseApproverType != '' && currentExpenseApproverType !='undefined' && currentExpenseApproverType == 'Admin'){
	    $("#level2ExpenseApproverToggle").hide();   
	    $("#level2ExpenseApproverDiv").removeClass("expenselevel-second-active");
	}
	
	
} 
</script>

<script type="text/javascript">
function getChildLevel1ExpenseApprovers(currentExpenseApproverId,level2ExpenseApproverUserId){
	 if(currentExpenseApproverId != null && currentExpenseApproverId != ''  && currentExpenseApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: pmsUrl+"/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentExpenseApproverId,
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-Select Approver-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		var selected = '';
			        		if(level2ExpenseApproverUserId != null && level2ExpenseApproverUserId != 'undefied' && level2ExpenseApproverUserId != '' && level2ExpenseApproverUserId == opt.id){
			        			selected = "selected";
			        		}
			        		output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.fullName+" ("+opt.userType+")</option>";
					}
		        	$("#level2ExpenseApprover").empty();
			        $("#level2ExpenseApprover").append(output);
			        $('#level2ExpenseApprover').trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
	 }
} 
</script>


<script type="text/javascript">
function getProjectLevel1ExpenseApprovers(approverUserId){
	 $("#level1ExpenseApprover").empty();
		 $.ajax({
		    	type: "GET",
		        url: pmsUrl+"/getProjectLevel1Approvers?${_csrf.parameterName}=${_csrf.token}",
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Level1 Approver --</option>";
		        	for (var i=0; i<json.length; i++)
					{
		        		var selected = "";
		        		var opt = json[i];
		        		if(approverUserId != null && approverUserId != 'undefied' && approverUserId != '' && approverUserId == opt.id){
		        			selected = "selected";
		        		}
			        		output = output + "<option value='" +opt.id+ ","+opt.userType+"' "+selected+">"+opt.fullName+" ("+opt.userType+")</option>";
					}
		        	
			        
			        $("#level1ExpenseApprover").append(output);
			        $('#level1ExpenseApprover').trigger("chosen:updated");
		        }
		    });
	 }
</script>

<script type="text/javascript">
function getChildLevel1Approvers(currentApproverId,level2ApproverUserId){
	 if(currentApproverId != null && currentApproverId != ''  && currentApproverId != 'undefined'){
		 $.ajax({
		    	type: "POST",
		        url: pmsUrl+"/getApproversByUserId?${_csrf.parameterName}=${_csrf.token}&currentApproverId="+currentApproverId,
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-Select Manager-</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
			        			var fullName = opt.firstName +" "+ opt.lastName;
			        		}else{
			        			var fullName = opt.firstName;
			        		}
			        		var selected = '';
			        		if(level2ApproverUserId != null && level2ApproverUserId != 'undefied' && level2ApproverUserId != '' && level2ApproverUserId == opt.id){
			        			selected = "selected";
			        		}
			        		output = output + "<option value='" +opt.id+ "' "+selected+">"+fullName+"  ("+opt.userType+")</option>";
					}
		        	$("#level2Manager").empty();
			        $("#level2Manager").append(output);
			        $('#level2Manager').trigger("chosen:updated");
		        },
		        error: function(e){
		            
		        }
		    });
	 }
} 
</script>

<script>
function saveClientProject(){
	 if ($('#project_form').validate(false, validationSettings)){ 
		 
		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 var formData = getFormData("project_form");
		 
	 		$.ajax({
				type : "POST",
  	    	    data : formData,
				url : pmsUrl+"/storeClientProject?${_csrf.parameterName}=${_csrf.token}", 
				success : function(result) {
					if (result && result.response === "INVALID_DATA") {
							$("#loading").removeAttr("style");
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	var project = result.object;
		                	 location.replace(pmsUrl+"/projectSummary?projectUUID="+project.projectUUID+"&projectClientUUID="+project.projectClientUUID+"&tabType=team");
							 $("#loading").removeAttr("style");
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(pmsUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
					
					
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
<script>
function refreshClientList(){
	location.reload();	
}
</script>

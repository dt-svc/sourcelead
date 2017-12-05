<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link href="/resources/css/register-style.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/resources/css/register-style.css" rel="stylesheet">

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client Project</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo;<a href="<c:url value='${appPath}/projectDashboard'/>"> Projects List</a>
			&raquo; Client Project
	</section>
	
	<div class="content-area clearfix noPad">
	  <div id="loading"></div>
	  <div id="choicepopUp"></div>
	<div class="newPrDetails newPrDetailsPMO"> 
      		<div class="newPrTabs">
   			  <ul class="newPrTabsMenu">
				<li class="current" onclick="getProjectDetails();"><a href="#tab-a" data-toggle="tab">Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="assignedMembers" onclick="getAssignedMembers();">Assign Members</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="additionalDetails" onclick="getAdditionalDetails();">Questionnaire</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
		
		<div class="project_tabs">
		<div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt5" id="tab-a">
			<form:form id="project_form" name="project_form" method="post">
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
					<div class="form-group mb10">
					<label class="col-md-3 mt10" for="Project Manager">Client Project <span
						class="text-danger">*&nbsp;</span></label>
					<div class="col-md-9">
						<div class="form-icon-min" id="clientProjectDiv">
							<select class="form-control chosen-select"
								name="clientProject" id="clientProject" field-name="Client Project"
								data-validation="required"">
								<option value="">-- Select Project --</option>
							</select> 
							<i class='fa fa-info-circle' title='Client projects will be auto populate after selection of end client'></i>
						</div>
						<span class="hidden">Select Client Project</span>
					</div>
				</div>
				</div> 
			</div>
			
			
			<div class="col-md-12 clearfix mt5 mb5">
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
              <div id="clientLevelsDiv"></div>
              
				<div class="form-footer clearfix mt">
					<div class="col-lg-12">
					<i class='fa fa-info-circle' title='Save & Continue will be disabled until you select the end client'></i>
						<div class="pull-right">
							<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/projectDashboard'/>'" class="btn btn-danger ml">
							<input type="button" value="Save & Continue" disabled="disabled" id="save_project" onclick="saveClientProject()" class="btn btn-primary">
						</div>
					</div>
				</div>



			</form:form>
		</div>
	   <jsp:include page="projectAssignedMembers.jsp" />
	   <jsp:include page="projectAdditionalDetails.jsp" />
   </div>
		</div>
   	</div>
	
	
	</div>
	
</section>

<div id="divLevelTd1" style="display: none; float: left;">
	<div class="" id="REPLACEROWIDlevel" name="REPLACEROWIDlevel">
	<input type="hidden" id="REPLACEROWIDlevelValue" name="clientProjectLevel[REPLACEROWID].levelValue" value="" />
	<span class="block mt5" id="REPLACEROWIDlevelName"></span>
	</div>
 </div>
 
 <div id="divLevelTd2" style="display: none; float: left;">
  <input type="hidden" id="REPLACEROWIDclientOrgLocId" name="clientProjectLevel[REPLACEROWID].clientOrgLocId" value="" />
  <div class="dropdown-select">
          <select class="form-control select-bg CHOOSEN-SELECT-CLASS" data-validation="required" field-name="Client" onchange="getClientOrgLocValue();" id="REPLACEROWIDlevelClient" name="clientProjectLevel[REPLACEROWID].levelClient" >
          </select>
  </div>
 </div>
 
 <div id="divLevelTd3" style="display: none; float: left;">
  <div>
   <span class="clientProjSpan mt5">Is end Client ?</span>
     <label class="switch ml10 pull-left mt5">
       <input type="checkbox" id="REPLACEROWIDchecked" name="clientProjectLevel[REPLACEROWID].endClientchecked" onclick="checkEndClient(REPLACEROWID)" value="N">
       <div class="slider round"></div>
       </label>
       <button type="button" class="btn btm-sm btn-primary pull-right" id="REPLACEROWIDaddNewClient" onclick="addNewClient(REPLACEROWID);" style="display : block"><i class="fa fa-plus"></i> Add Client</button>
   </div>
</div>  


<div id="divLevelTd4" style="display: none; float: left;">
  <div>
         <div class="pull-right">
               <button type="button" class="btn btm-sm pull-right mt5" onclick="removeLevel(REPLACEROWID)"><i class="fa fa-remove text-danger"></i></button>
          </div>
   </div>
</div>  

<jsp:include page="../masterFooter.jsp" />
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  



<script>
	$(document).ready(function() {
		$("#tab-a").show();
		getProjectLevel1Approvers();
		getClientLevels();
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
	});
</script>


<script>
function getClientLevels(){
	$("#clientLevelsDiv").empty();
	
	var result = "";
        
	result = result +'<div class="clearfix mb10 mt5">';
	result = result +'<input type="hidden" name="levelMaxRowNumber"  id="levelMaxRowNumber" value="1" />';
	result = result +'<input type="hidden" name="levelMaxValue"  id="levelMaxValue" value="1" />';
	result = result +'<div class="clearfix col-md-12">';
	result = result +'<table class="table table-responsive table-bordered table-hover"  width="100%" id="clientLevelTable" border="0">';
	result = result +'<tbody>';
	result = result +'<tr id="levelTsRow0">';
	result = result +'<div id="0levelFileGroup">';
	
	result = result +'<td width="100px">';
	result = result +'<div class="">';
	result = result +'<input type="hidden" id="0levelValue" name="clientProjectLevel[0].levelValue" value="1" />';
	result = result +'<span class="block mt5">Level 1</span>';
	result = result +'</div>';
	result = result +'</td>';
	
	result = result +'<td width="100px">';
	result = result +'<div class="dropdown-select">';
	
	result = result +'<input type="hidden" id="0clientOrgLocId" name="clientProjectLevel[0].clientOrgLocId" value="" />';
	result = result +'<select class="form-control select-bg chosen-select" data-validation="required" field-name="Client" id="0levelClient" name="clientProjectLevel[0].levelClient" onchange="getClientOrgLocValue();">';
	result = result +'<option value="">--Select Client--</option>';
	<c:forEach var="report" items="${clientsList}">
	    if('${report.locationId.id}' == '${SourceLeadWeb_orgLocations.id}'){
	    	result = result +'<option value="${report.responderLocationId.id}">${report.responderLocationId.org.orgName}</option>';
	    } 
	    if('${report.responderLocationId.id}' == '${SourceLeadWeb_orgLocations.id}'){
	    	result = result +'<option value="${report.locationId.id}">${report.locationId.org.orgName}</option>';
	    }
    </c:forEach> 
 
	result = result +'</select>';
	
	result = result +'</div>';
	result = result +'</td>';
	
	result = result +'<td width="150px">';
	result = result +'<span class="clientProjSpan mt5">Is end Client ?</span>';	
	result = result +'<label class="switch ml10 pull-left mt5">';
	result = result +'<input type="checkbox"  id="0checked" name="clientProjectLevel[0].endClientchecked" onclick="checkEndClient(0)" value="N">';
	result = result +'<div class="slider round"></div>';
	result = result +'</label>';
	
	result = result +'<button type="button" class="btn btm-sm btn-primary pull-right" id="0addNewClient" onclick="addNewClient(0);" style="display : block"><i class="fa fa-plus"></i> Add Client</button>';
	
	result = result +'</td>';
	
	result = result +'<td width="100px">';
	result = result +'<div class="pull-left paddingRight" id="addLevelTxtMsg" style="display : none">';
	result = result +'<i class="fa fa-info-circle clientInfo" title="After selection of end client no new level has added"></i>';
	result = result +'</div>';
		
	
	result = result +'<div class="pull-right">';
	result = result +'<button type="button" class="btn btm-sm pull-right" id="addClientRow" onclick="addLevelRow();"><i id="addButton" class="fa fa-plus text-primary"></i></button>';
	result = result +'</div>';
	
	
	result = result +'</div>';
	result = result +'</td>';
	
	result = result +'</tr>';
	result = result +'</tbody>';
	result = result +'</table>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
	
	$("#clientLevelsDiv").append(result);
	
	prepareChosenScripts();
	
	
}
</script>

<script>
function checkEndClient(id){
	var checked = document.getElementById(id+"checked").checked
	if(checked == true){
		var clientOrgLocId = $("#"+id+"clientOrgLocId").val();
		if(clientOrgLocId){
			$("#"+id+"checked").val("Y");
			$("#addClientRow").attr('disabled', 'disabled');
			 document.getElementById("addButton").removeAttribute("class");
			 document.getElementById('addButton').className = 'fa fa-plus text-success';
			 $("#addClientRow").attr('disabled', 'disabled');
			 document.getElementById("addLevelTxtMsg").style.display="";
			 $("#save_project").removeAttr('disabled');
			 document.getElementById(id+"addNewClient").style.display="none";
			 getEndClientProjects(clientOrgLocId)
			 
		}else{
			alertMessage();
			document.getElementById(id+"checked").checked = false;
		}
		 
	}else{
		$("#"+id+"checked").val("N");
		$("#addClientRow").removeAttr('disabled');
		 document.getElementById("addLevelTxtMsg").style.display="none";
		 document.getElementById("addButton").removeAttribute("class");
		 document.getElementById('addButton').className = 'fa fa-plus text-primary';
		 $("#save_project").attr('disabled', 'disabled');
		 document.getElementById(id+"addNewClient").style.display="";
		 
	    var output = "";
     	output = output + "<option value=''>-- Select Project --</option>";
     	$("#clientProject").empty();
     	$("#clientProject").append(output);
     	$("#clientProject").trigger("chosen:updated");
	}
}
</script>



<script>
function getEndClientProjects(clientOrgLocationId){
	var sourceleadUrl ='${sourceleadUrl}';
	
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



<script>
function getClientOrgLocValue(){
	 var checkMaxTsRowNum = parseInt($("input[name = 'levelMaxRowNumber']").val() - 1);
	 var orgLocId = document.getElementById(checkMaxTsRowNum+"levelClient");
     $("#"+checkMaxTsRowNum+"clientOrgLocId").val(orgLocId.value);
}
</script>



<script type="text/javascript">
	function removeLevel(rowId) {
		 document.getElementById("divRequestedLevelRow"+rowId).style.display = 'none';
		 document.getElementById("divRequestedLevelRow"+rowId).value= '';
		 var  rId = "divRequestedLevelRow"+rowId;
		  var r = document.getElementById("clientLevelTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		        updateLevelName();
		    } 
		 
	}
</script>



<script>
function addLevelRow(){
	var checkMaxTsRowNum = $("#levelMaxRowNumber").val();
	var orgLocId = document.getElementById(checkMaxTsRowNum-1+"clientOrgLocId");
	if(orgLocId != null && (orgLocId.value == null || orgLocId.value == '' || orgLocId.value == 'undefined')){
		alertMessage();
	}else{
		addClientLevelRow();
	}
	
}
</script>

<script type="text/javascript">
function alertMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Client must be selected</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<script>
function addClientLevelRow(){
	
	var checkMaxTsRowNum = $("#levelMaxRowNumber").val();
	var tbl = document.getElementById("clientLevelTable");
	var maxRowNum = parseInt($("input[name = 'levelMaxRowNumber']").val());
	$("input[name = 'levelMaxRowNumber']").val(maxRowNum + 1);
	
	for(i=0; i < maxRowNum; i++){
		$("#"+i+"checked").attr('disabled', 'disabled');
		 document.getElementById(i+"addNewClient").style.display="none";
	}

	var row = tbl.insertRow(maxRowNum);
	row.setAttribute("id", "divRequestedLevelRow" + maxRowNum);
	

	var cell0 = row.insertCell(0);
	var divDocTypeTdStr = document.getElementById("divLevelTd1").innerHTML;
	divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell0.innerHTML = divDocTypeTdStr;
	
	var cell1 = row.insertCell(1);
	var divClientTdStr = document.getElementById("divLevelTd2").innerHTML;
	divClientTdStr = divClientTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	divClientTdStr = divClientTdStr.replace(/CHOOSEN-SELECT-CLASS/g, 'chosen-select');
	cell1.innerHTML = divClientTdStr;
	
	var cell2 = row.insertCell(2);
	var divEndClientTdStr = document.getElementById("divLevelTd3").innerHTML;
	divEndClientTdStr = divEndClientTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell2.innerHTML = divEndClientTdStr; 
	
	
	var cell3 = row.insertCell(3);
	var divRemoveLevelTdStr = document.getElementById("divLevelTd4").innerHTML;
	divRemoveLevelTdStr = divRemoveLevelTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell3.innerHTML = divRemoveLevelTdStr;
	
	prepareChosenScripts();
	
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
    setTimeout(function () {
    	getLevelDetails(maxRowNum);
    	 $("#loading").removeAttr('style');
	 }, 200);

			
	
	
	
	
}
</script>

<script>
function updateLevelName(){
	var checkMaxTsRowNum = $("#levelMaxRowNumber").val();
	var j = 1;
	 for(var i=0; i < checkMaxTsRowNum; i++){
		 var orgLocId = document.getElementById(i+"levelClient");
		 if(orgLocId != null){
			 $("#"+i+"levelName").html("Level "+ parseInt(j));
			 $("#"+i+"levelValue").val(j);
			 j++;
		 }
	 }
	 $("input[name = 'levelMaxValue']").val(j-1);
	 var number = j-2;
	 $("#"+number+"checked").removeAttr('disabled', 'disabled');
	 document.getElementById(number+"addNewClient").style.display="";
	
}
</script>

<script>
function getLevelDetails(rowNum){
	
	var levelMaxValue = parseInt($("input[name = 'levelMaxValue']").val());
	$("#"+rowNum+"levelName").html("Level "+ parseInt(levelMaxValue + 1));
	$("input[name = 'levelMaxValue']").val(levelMaxValue + 1);
	
	$("#"+rowNum+"levelValue").val(levelMaxValue+1);
	
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var orgLocationIds = [];
	 var loginOrgLocationId = '${SourceLeadWeb_orgLocations.id}';
	 orgLocationIds.push(loginOrgLocationId);
	 
	 for(var i=0; i < rowNum; i++){
		 var orgLocId = document.getElementById(i+"levelClient");
		 if(orgLocId != null){
			 orgLocationIds.push(orgLocId.value);
		 }
		
	 }
	
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getSourceLeadRegisteredOrgsForClientProject?${_csrf.parameterName}=${_csrf.token}&orgLocationIds="+orgLocationIds,
	        success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>-- Select Client --</option>";
	        	for (var i=0; i<json.length; i++)
				{
	        		var opt = json[i];
	        		output = output + "<option value='" +opt.id+ "'>"+opt.orgLocationName+"</option>";
				}
	        	
	        	$("#"+rowNum+"levelClient").append(output);
	        	$("#"+rowNum+"levelClient").trigger("chosen:updated");
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
		        success: function(json) {
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
		 
		 var sourceleadUrl ='${sourceleadUrl}';
		 var formData = getFormData("project_form");
		 
	 		$.ajax({
				type : "POST",
  	    	    data : formData,
				url : sourceleadUrl+"/storeClientProject?${_csrf.parameterName}=${_csrf.token}", 
				success : function(result) {

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
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script> 
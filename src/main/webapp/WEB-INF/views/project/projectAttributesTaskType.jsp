<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
var pmsUrl = '${SourceLeadWeb_pmsUrl}';
</script>
<script>
$(document).ready(function(){
	getTaskTypeList();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Task Type</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}"> Home </a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings</a>&raquo; Project Task Type</div>

    </section>
    <div class="content-area content-new clearfix timesheet_out">
      <div class="clearfix mt mb">
        <div class="col-md-2 padding_right">
          <ul class="list-unstyled listStyles">
            <li class="activeList"><a href="${SourceLeadWeb_pmsUrl}/pojrctAttributes">Task Type</a></li>
            <li><a href="${SourceLeadWeb_pmsUrl}/projectAttributeStatus">Status</a></li>
            <li><a href="${SourceLeadWeb_pmsUrl}/projectAttributePriority">Priority</a></li>
            <li><a href="${SourceLeadWeb_pmsUrl}/projectAttributeSeverity">Severity</a></li>
          </ul>
        </div>
        <div class="col-md-10 padding_left">
			<div class="project_tabs">
			   <div id="tab-1">
		          <div class="positionRelative reportHead clearfix">
		            <ul class="pull-right list-unstyled list-inline" style="padding-right:235px">
		              <li><a class="newAdBtn mr5" title="Add" id="addPop" onclick="addTaskType();">+ Add</a></li>
		            </ul>
		          </div>
		          <div class="candidate_wrap_table datatable_filter dataSeaTop" id="taskTypeListDivId"> </div>
			  </div>
				
		  </div>
		  
        </div>
      </div>
    </div>
  </section>


<div class="mask"></div>
<div class="popupWrap" id="editModalTasktype">
  <div id="editTaskTypeDivId"></div>
</div>
<div class="popupWrap" id="modalTasktype">
      <div class="popupHead"><span>Task Type</span><i class="closePop pull-right popClose" onclick="closeTaskType();"></i></div>
        <form id="taskType_form" name="taskType_form" action="" method="post">
        <input type="hidden" name="taskType" value="TaskType">
        <div class="popupContent">
            <div class="clearfix mb">
                <label class="noPad">Name <b class="valStar">*</b></label>
                <div class="clearfix">
                    <input class="form-control" placeholder="Name" type="text" name="name" id="taskType" data-validation="required validate_length length1-100" field-name="Task Type" onblur="validateTaskTypeName('','new');">
                </div>
            </div>
            <div class="clearfix mb">
                <label class="noPad">Refer<b class="valStar">*</b></label>
                <div class="clearfix">
					<select title="Select Refer" class="selectpicker width100percnt" name="referId" id="referId" data-live-search="true" data-validation="required" field-name="Refer">
						<c:forEach items="${referTypeList}" var="refer">
						   <option value="${refer.id}">${refer.name}</option>
						</c:forEach>
					</select>
                </div>
            </div>
            <div class="clearfix mb">
                <label class="noPad">Description</label>
                <div class="clearfix">
					<textarea placeholder="Description" class="form-control" rows="2" name="description" id="taskDescription" data-validation="validate_length length1-1000" data-validation-optional="true" field-name="Descrption"></textarea>
                </div>
            </div>

        </div>
        
        <div class="popupFoot text-right">
          <button type="button" id="add_button" class="slBtn md colorBtn" onclick="saveTaskType();">Save</button>
          <button type="button" class="slBtn md popClose" onclick="closeTaskType();"> Cancel</button>
        </div>
  </form>
</div>
 
<script>
function getTaskTypeList(){
	$.ajax({
		type : "POST",
 		url : pmsUrl+ "/getTaskTypeList",
		success : function(json) {
			var result = "";
	        	result = result +'<table id="taskTypePagination">';
		    	result = result +'<thead>';
		    	result = result +' <tr>';
		    	result = result +'  <th>Id</th>';
		    	result = result +'  <th>Type</th>';
                result = result +'  <th>Description</th>';
                result = result +'  <th>Refer</th>';
                result = result +'  <th>Action</th>';
		    	result = result +' </tr>';
		    	result = result +'</thead>	';
		    	result = result +'<tbody>';
		    	for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
	   		    	result = result +'	<tr>';
	   		    	result = result +'  <td><div class="tblminwidth-150">'+report.id+'</div></td>';
	                result = result +'  <td><div class="tblminwidth-150" title="'+report.name+'">'+report.name+'</div></td>';
	                
	                result = result +'  <td><div class="tbldata-dotdot-big" title="'+report.description+'">'+report.description+'</div></td>';
	                result = result +'  <td>';
	                if(report.referId != null){
	                	 result = result +'  '+report.referName+'';
	                }else{
	                	 result = result + '--';
	                }
	                result = result +'   </td>';
	                result = result +'  <td>';
	                if(report.orgLocationId != null){
	                	result = result +'	  <ul class="list-inline list-unstyled">';
		  				result = result +'		  <li><i class="fa fa-pencil cursorPointer" onclick="eidtTaskType('+report.id+');"></i></li>';
		  				result = result +'		  <li><i class="fa fa-trash cursorPointer" onclick="deleteTaskType('+report.id+');"></i></li>';
		  				result = result +'	  </ul>';
	                }else{
	                	result = result +'	 <ul class="list-inline list-unstyled"> -- </ul>';
	                }
		  			result = result +'	</td>';
		            result = result +'  </tr>';
   				}
   		    	$("#taskTypeListDivId").empty();  
   	    	    $("#taskTypeListDivId").append(result);
   	    	    taskTypePagination();
   	    	 $('#taskTypePagination').DataTable().column(0).visible(false);
		}
	});
}
function taskTypePagination(){
	  $('#taskTypePagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"aaSorting":[[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		"sZeroRecords": "", "sEmptyTable": ""
	     	}
	    } );
}
</script>
<script>
function saveTaskType(){
	if ($('#taskType_form').validate(false, validationSettings)) {
		var pmsUrl = '${SourceLeadWeb_pmsUrl}';
		var formData = getFormData("taskType_form");
		$.ajax({
			type : "POST",
			data: formData,
	 		url : pmsUrl+ "/saveTaskLookupData",
			success : function(data) {
				location.reload();
				editCloseTaskType();
    			closeTaskType();
			},
		});
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#taskType_form', function() {
	$("#taskType_form").showHelpOnFocus();
	$("#taskType_form").validateOnBlur(false, validationSettings);
});

</script>  
  
  
  
<script>
function addTaskType(){
	$(".mask, #modalTasktype").fadeIn();
}
function closeTaskType(){
	$(".mask, #modalTasktype").fadeOut();
	$('#taskType_form')[0].reset();
	//$("taskType_form").validate().resetForm();
	$('#referId').val('').selectpicker('refresh');
}
function editCloseTaskType(){
	$(".mask, #editModalTasktype").fadeOut();
}
function deleteTaskType(lookupId){
	var taskType = "TaskType";
	$.ajax({
		type : "POST",
 		url : pmsUrl+ "/deleteLookupDataByLookupId?&taskType="+taskType+"&lookupId="+lookupId,
		success : function(data) {
			location.reload();
		}
	});
	
}
function eidtTaskType(lookupId){
	$.ajax({
		type : "POST",
 		url : pmsUrl+ "/getLookupDataByLookupId?&lookupId="+lookupId,
		success : function(data) {
			var result = "";
			result = result +'      <div class="popupHead"><span>Edit Task Type</span><i class="closePop pull-right popClose" onclick="editCloseTaskType();"></i></div>';
			result = result +'        <form id="taskType_form" name="editTaskType_form" action="" method="post">';
			result = result +'       <input type="hidden" name="taskType" value="TaskType">';
			result = result +'        <input type="hidden" id="lookupId" name="lookupId" value="'+lookupId+'">';
		 	result = result +'        <div class="popupContent">';
			result = result +'            <div class="clearfix mb">';
			result = result +'                <label class="noPad">Name <b class="valStar">*</b></label>';
			result = result +'                <div class="clearfix">';
			result = result +'                    <input class="form-control" placeholder="Name" type="text" name="name" id="taskType" value="'+data.name+'" data-validation="required validate_length length1-100" field-name="Task Type" onblur="validateTaskTypeName('+data.id+',"edit");">';
			result = result +'                </div>';
			result = result +'            </div>'; 
			
			result = result +'            <div class="clearfix mb">';
			result = result +'                <label class="noPad">Refer <b class="valStar">*</b></label>';
			result = result +'                <div class="clearfix">';
			result = result +'					<select title="Select Refer" class="selectpicker width100percnt" name="referId" id="referId" data-live-search="true" data-validation="required" field-name="Refer">';
			result = result +'						<c:forEach items="${referTypeList}" var="refer">';
			result = result +'						   <option value="${refer.id}"  '+ ('${refer.id}' == data.referId ? "selected" : '') +' >${refer.name}</option>';
			result = result +'						</c:forEach>';
			result = result +'					</select>';
			result = result +'                </div>';
			result = result +'            </div>'; 
			
			 result = result +'            <div class="clearfix mb">';
			result = result +'                <label class="noPad">Description</label>';
			result = result +'                <div class="clearfix">';
			result = result +'					<textarea placeholder="Description" class="form-control" rows="2" name="description" id="taskDescription" value="'+data.description+'" data-validation="validate_length length1-1000" data-validation-optional="true" field-name="Descrption">'+data.description+'</textarea>';
			result = result +'                </div>';
			result = result +'            </div>'; 
			
			result = result +'        </div>';
			result = result +'        <div class="popupFoot text-right">';
			result = result +'          <button type="button" id="update_button" class="slBtn md colorBtn" onclick="saveTaskType();">Update</button>';
			result = result +'          <button type="button" class="slBtn md popClose" onclick="editCloseTaskType();"> Cancel</button>';
			result = result +'        </div>';
			result = result +'  </form>';
			$("#editTaskTypeDivId").empty();
			$("#editTaskTypeDivId").append(result); 
			$(".mask, #editModalTasktype").fadeIn();
			$('.selectpicker').selectpicker('refresh');
		}
	});
}

function validateTaskTypeName(lookupId,type){
	var TaskTypeName = document.getElementById("taskType").value.trim();
	var taskTyePe = "TaskType";
	if (TaskTypeName != null && TaskTypeName != "") {
		$.ajax({
					type : "POST",
					url : pmsUrl+ "/validateTaskTypeName?taskTyePe="+taskTyePe+"&taskTypeName="+TaskTypeName+"&lookupId="+lookupId,
					success : function(data) {
						 if (data == true) {
							$("input#taskType").after("<p class='jquery_form_error_message'>"+ TaskTypeName+ " is Already Existed</span>");
							document.getElementById("taskType").setAttribute('record-exist', 'yes');
							document.getElementById("taskType").setAttribute('record-exist-errorMsg',TaskTypeName+ ' is Already Existed');
							$('input#taskType').removeAttr( "record-exist record-exist-errorMsg");
							if(type == "edit"){
								$("#update_button").attr("disabled", "disabled");
							}else{
								$("#add_button").attr("disabled", "disabled");
							}
						  }else {
							  if(type == "edit"){
									$("#update_button").removeAttr("disabled", "disabled");
								}else{
									$("#add_button").removeAttr("disabled", "disabled");
								}
							$('input#taskType').removeAttr("record-exist record-exist-errormsg");
						} 
					},
				});
	}
	
	
}
</script>
<jsp:include page="../masterFooter.jsp" />	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

    <!--  Popups -->
<div class="mask" ></div>
<div class="addPayrollGroupWrap" id="add_user_group_formPop">
 <form:form id="add_user_group_form"  name="add_user_group_form" method="post">
 <div id="loading"></div>
    <div class="popupHead">Add Group <i class="closePop pull-right popClose" onclick="closeBatch();"></i></div>
    <div class="popupMid overflow_inherint" id="formDiv">
                    <div class="minHeightcol5 clearfix">
                        <div class="col-md-6">
                            <label>Group Id<span class="text-danger">*</span> </label>
                            <div class="clearfix" id="groupIdDiv">
                                <input type="text" class="form-control" placeholder="Enter" id="groupId" name="groupId" value="${userGroup.groupId}" data-validation="required validate_length length1-9" onblur="validateGroupId();" field-name="Group Id">

                                <input type="hidden" id="groupUUID" name="groupUUID" value="${userGroup.groupUUID}"/>
                                <input type="hidden" id="id" name="id" value="${userGroup.id}"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label>Group Name<span class="text-danger">*</span> </label>
                            <div class="clearfix" id="groupNameDiv">
                                <input type="text" class="form-control" placeholder="Enter" id="groupName" name="groupName" value="${userGroup.groupName}" data-validation="required" onblur="validateGroupName();" field-name="Group Name">

                            </div>
                        </div>
                
                       <div class="col-md-6">
                                 <label>Department<span class="text-danger">*</span></label>
                                 <div class="row">
                                 <div class="col-md-9">
                               		 <select class="form-control chosen-select" id="department" name="department" data-validation="required" field-name="Department">
                                     <option value="">Select</option>
                        				  <c:forEach var="dept" items="${departmentList}">
										   <option value="${dept.id}" ${userGroup.departmentId == dept.id ? 'selected' : ''}>${dept.departmentName}</option>
										  </c:forEach>
                               		 </select>
                               		 </div>
                               		 <div class="col-md-3">
                               		 	<span title="Add Department" class="fa fa-plus cursorPointer btn btn-primary mt2 mr" onClick="addDepartmnt();"></span>
                               		 	<span title="Refresh" class="fa fa-refresh cursorPointer btn btn-default mt2" onClick="getDepartmentList();"></span>
                               		 </div>                                  
                                </div>
                        </div> 
          	         <div class="col-md-6">
							<label>Pay Frequency<span class="text-danger">*</span> </label>
							<div class="clearfix" >
								<select class="form-control chosen-select" id="payFrequency" name="payFrequency" data-validation="required" field-name="Pay Frequency">
									<option value="">Select</option>
									<option value="Weekly" ${userGroup.payFrequency == 'Weekly' ? 'selected' : ''}>Weekly</option>
									<option value="Bi-Weekly" ${userGroup.payFrequency == 'Bi-Weekly' ? 'selected' : ''}>Bi-Weekly</option>
									<option value="Monthly" ${userGroup.payFrequency == 'Monthly' ? 'selected' : ''}>Monthly</option>
									<option value="Annually" ${userGroup.payFrequency == 'Annually' ? 'selected'  : ''}>Annually</option>
								</select>
						</div>
						</div>
                
                    <div class="col-md-6">
                            <label>Description</label>
                            <div class="clearfix">
                                <textarea rows="2" class="form-control"  placeholder="Enter" id="description" name="description" data-validation="validate_Space validate_length length1-500" data-validation-optional="true" field-name="Description">${userGroup.description}</textarea>
                            </div>
                        </div>
                    </div>                
    </div>
    <div class="popupFoot text-right">
        <button type="button" class="slBtn colorBtn" id="save_user_group_button" onclick="createUserGroup();">Save</button>
        <button type="button" class="slBtn" onclick="closeBatch();">Cancel</button>
    </div>
    </form:form>
</div>
<!--  Popups -->

<div class="addPayrollGroupWrap" id="addDepartment" style="display:none">
	<form:form id="department_form" name="department_form" method="post">
	<div class="popupHead">Add Department</div>
	<div class="popupMid overflow_inherint">
		<div class="minHeightcol5 clearfix">
			 <div class="col-md-6 mt mb">
                  <div class="" id="departmentNameDiv">
                  	<label class="noPad">Department Name <b class="valStar">*</b></label>
                    <input type="text" class="form-control input-sm" value="" placeholder="Name *" name="departmentName" id="departmentName" data-validation="required validate_length length2-100" field-name="Name" onkeyup="validDepartmentname();" onkeydown="return (event.keyCode!=13);">
                  </div>
                </div>
                <div class="col-md-6 mt mb">
                  <div class="">
                  <label class="noPad">Description</label>
                    <textarea rows="2" placeholder="Description" class="form-control" name="departmentDesc" id="departmentDesc" data-validation="validate_length length2-250"
				data-validation-optional="true"></textarea>
                  </div>
                </div>
		</div>
	</div>
	<div class="popupFoot text-right">
        <button type="button" class="slBtn md colorBtn" onclick="saveDepartment();">Add</button>
						<input type="button" value="Cancel" class="slBtn md popClose" id="popCloseDiv">
    </div>
    </form:form>
</div>

<script>
function addDepartmnt(){
	$("#addDepartment").fadeIn();
	$("#add_user_group_formPop").fadeOut();
}
function saveDepartment(){
	if ($('#department_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData1("department_form");
	 	$.ajax({
			type: "POST",
			data: formData,
		    processData: false, 
	 		contentType: false,
			url : sourceleadUrl+ "/storeDepartment?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
				if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
		        		showError(data.responseMessage);
  	    		    	$("#hierarchyLoading").removeAttr('style');
  	    		}else if (data.response === "AWKWARD") {
            			location.href = "/errorPage";
  	    		}else if(data.response === "SUCCESS"){
            			$("#addDepartment").fadeOut();
            			$("#add_user_group_formPop").fadeIn();
            			$("#departmentName").val('');
            			$("#departmentDesc").val('');
            			$("department_form").validate().resetForm();
            			getDepartmentList();
		        }else if (data.indexOf('Sign In') !== -1) {
            			location.replace(sourceleadUrl);
  				}else {
  					showError("Operation failed, please try again");
  				    $("#hierarchyLoading").removeAttr('style');
  				}
			},
		}); 
		return true;
	}else {
		return false;
	}
}

$("#popCloseDiv").click(function(){
	$("#addDepartment").fadeOut();
	$("#add_user_group_formPop").fadeIn();
	$("#departmentName").val('');
	$("#departmentDesc").val('');
	$("department_form").validate().resetForm();
});

function validDepartmentname(){
	var DepartmentName = document.getElementById("departmentName").value.trim();
	var sourceleadUrl = '${sourceleadUrl}';
	$('#departmentNameDiv').find('p.jquery_form_error_message').remove();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/validateDepartmentname?${_csrf.parameterName}=${_csrf.token}&departmentName="+DepartmentName,
		success : function(data) {
			if (data == true) {
				$("input#departmentName").after("<p class='jquery_form_error_message'>"+ DepartmentName+ " is Already Existed</span>");
				document.getElementById("departmentName").setAttribute('record-exist', 'yes');
				document.getElementById("departmentName").setAttribute('record-exist-errorMsg',DepartmentName+ ' is Already Existed');
			} else {
				$('input#departmentName').removeAttr("record-exist record-exist-errormsg");
			} 
		},
	});
}

function getDepartmentList(){
	var status = "1"; 
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getDepartmentList?${_csrf.parameterName}=${_csrf.token}&departmentStatus="+status,
		success : function(json) {
	        	var result = "";
	        	result = result +'<option value="">Select</option>';
		    	for (var i=0; i<json.data.length; i++)
				{
		    		var report = json.data[i];
		    		result = result +'<option value="'+report.id+'" >'+report.departmentName+'</option>';
				}
		    	$("#department").empty();  
	    	    $("#department").append(result);
	    	    $('#department').trigger("chosen:updated");
		},
	});
}
</script>

<script>
function moveAss() {
       var selectedOpts = $('#assocByDept option:selected');
       if (selectedOpts.length == 0) {
           alert("Nothing to move.");
           e.preventDefault();
       }
       $('#userId').append($(selectedOpts).clone());
       $(selectedOpts).remove();
       e.preventDefault();
   };
</script>

<script>
function validateGroupId(){
	var groupId = $("#groupId").val();
	var id = '${userGroup.id}';
	$.get(sourceleadUrl+"/validateUserGroupIdOrGroupName?"+tokenParamName+"="+tokenValue+"&groupId="+groupId+"&id="+id,  function(result) {
			 if (result.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (result.response === "SUCCESS") {
//                 	$('#groupIdDiv').find('p.jquery_form_error_message').remove();
                	$("#save_user_group_button").attr("disabled", false);
                }  else {
					$('#groupIdDiv').find('p.jquery_form_error_message').remove();
      			  	$("#groupId").after("<p class='jquery_form_error_message'>"+groupId+" already exists..!!</span>");
      			    $("#save_user_group_button").attr("disabled", true);
                }
			
		});
}

function validateGroupName(){
	var groupName = $("#groupName").val();
	var id = '${userGroup.id}';
	$.get(sourceleadUrl+"/validateUserGroupIdOrGroupName?"+tokenParamName+"="+tokenValue+"&groupName="+groupName+"&id="+id,  function(result) {
			 if (result.response === "AWKWARD") {
                    location.href = "/errorPage";
                } else if (result.response === "SUCCESS") {
                	if(groupName.length > 100){
                		$('#groupNameDiv').find('p.jquery_form_error_message').remove();
          			  	$("#groupName").after("<p class='jquery_form_error_message'>The values should be between 1-100 characters</span>");
          			  	$("#save_user_group_button").attr("disabled", true);
                	}else{
                		$('#groupNameDiv').find('p.jquery_form_error_message').remove();
                    	$("#save_user_group_button").attr("disabled", false);
                	}
                }  else {
					$('#groupNameDiv').find('p.jquery_form_error_message').remove();
      			  	$("#groupName").after("<p class='jquery_form_error_message'>"+groupName+" already exists..!!</span>");
      			  	$("#save_user_group_button").attr("disabled", true);
                }
			
		});
}

</script>

 <script type="text/javascript">
 function createUserGroup() {
	 
		 if ($('#add_user_group_form').validate(false, validationSettings)){ 

		 		var fromType = '${fromType}';
			    var formData = getFormData("add_user_group_form");
				$("#save_user_group_button").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#save_user_group_button" ).addClass('button').val('Processing..');
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/createUserGroup?"+tokenParamName+"="+tokenValue,
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#save_user_group_button").removeAttr("disabled");
			                    $("#save_user_group_button").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	$("#loading").removeAttr('style');
			                	 closeBatch();
			                	 if(fromType != null && fromType != 'undefined' && fromType != '' && fromType == 'DASHBOARD'){
			                		 location.replace(sourceleadUrl+"/payrollList");
			                	 }else{
			                		 location.replace(sourceleadUrl+"/userGroupList");
			                	 }
			                	 
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#save_user_group_button").removeAttr("disabled");
			                    $("#save_user_group_button").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
						
					},
				});
	        return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#add_user_group_form', function() {
		$("#add_user_group_form").showHelpOnFocus();
		$("#add_user_group_form").validateOnBlur(false, validationSettings);  
   });
  
 
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

});
 		</script> 
<script>
$("#payFrequency").trigger("chosen:updated");
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});
/* function addbatches() {
  $(".popupWrap, .mask").fadeIn();
} */
function closeBatch() {
	$(".addPayrollGroupWrap, .mask, #add_user_group_formPop").fadeOut();
	 document.getElementById("add_user_group_form").reset(); 
	 $('#add_user_group_form').find('p.jquery_form_error_message').remove();
	 $("#userId").empty();
	 $("#addUserGroupFormDiv").empty();
}
var el = document.getElementById("createbatches")
if(el){
  el.addEventListener("click", function(){
	  $(".addPayrollGroupWrap, .mask").fadeIn();
  });
}

</script> 

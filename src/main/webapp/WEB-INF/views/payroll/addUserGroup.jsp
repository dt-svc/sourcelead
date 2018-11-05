<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

    <!--  Popups -->
<div class="mask" ></div>
<div class="addPayrollGroupWrap">
 <form:form id="add_user_group_form"  name="add_user_group_form" method="post">
 <div id="loading"></div>
    <div class="popupHead">Add Group <i class="closePop pull-right popClose" onclick="closeBatch();"></i></div>
    <div class="popupMid">
                    <div class="minHeightcol5">
                        <div class="col-md-6">
                            <label>Group Id<span class="text-danger">*</span> </label>
                            <div class="clearfix" id="groupIdDiv">
                                <input type="text" class="form-control" placeholder="Enter" id="groupId" name="groupId" value="${userGroup.groupId}" data-validation="required" onblur="validateGroupId();" field-name="Group Id">
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
                               		 <div class="clearfix">
                               		 <select class="form-control chosen-select" onchange="getUsersByDepartment();" id="department" name="department" data-validation="required" field-name="Department">
                                     <option value="">Select</option>
                        				  <c:forEach var="dept" items="${departmentList}">
										   <option value="${dept.id}" ${userGroup.departmentId == dept.id ? 'selected' : ''}>${dept.departmentName}</option>
										  </c:forEach>
                               		 </select>                                    
                                </div>
                        </div> 
          	         <div class="col-md-6">
							<label>Pay Frequencey<span class="text-danger">*</span> </label>
							<div class="clearfix" >
								<select class="form-control sel" id="payFrequency" name="payFrequency" data-validation="required" field-name="Pay Frequency">
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
                                <textarea rows="2" class="form-control"  placeholder="Enter" id="description" name="description" data-validation="validate_Space validate_length length1-100" data-validation-optional="true" field-name="Description">${userGroup.description}</textarea>
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
                	$('#groupIdDiv').find('p.jquery_form_error_message').remove();
                }  else {
					$('#groupIdDiv').find('p.jquery_form_error_message').remove();
      			  	$("#groupId").after("<p class='jquery_form_error_message'>"+groupId+" already exists..!!</span>");
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
                	$('#groupNameDiv').find('p.jquery_form_error_message').remove();
                	$("#save_user_group_button").attr("disabled", false);
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
	$(".addPayrollGroupWrap, .mask").fadeOut();
	 document.getElementById("add_user_group_form").reset(); 
	 $('#add_user_group_form').find('p.jquery_form_error_message').remove();
	 $("#userId").empty();
	 $("#addUserGroupFormDiv").empty();
}
document.getElementById("createbatches").addEventListener("click", function(){
	$(".addPayrollGroupWrap, .mask").fadeIn();
});
</script> 

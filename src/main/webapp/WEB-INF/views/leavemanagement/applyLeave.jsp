<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	  
<script type="text/javascript">
window.onload = function() { 
	getPieChartBasedOnUser();
	getApproversList();
	}
</script>

<!-- Side Nav Bar --> 
    <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Apply Leave</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/leaveManagementReports'/>">Leave Management</a> &raquo; Apply Leave</div>
    </section>
    <div class="content-area content-new clearfix">
     <div id="loading"></div> 
    <form:form id="apply_leave_form" name="apply_leave_form" method="post">
      <div class="clearfix form-horizontal inner-content">
          <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-1">
          <sec:authorize access="hasRole('ROLE_APPLY_BEHALF_LEAVE')"> 
              <div class="form-group">
                    <label class="checkBox pull-left mr10">
					<input id="checkbox-1" class="checkbox-custom checkboxCustom onbehalf" name="checkbox-1" type="checkbox" onClick="onbehalf(); getApproversList();">
					<label for="checkbox-1" class="checkbox-custom-label"></label>
                  </label>
                  <span class="pull-left">On behalf of</span>
              </div>
          </sec:authorize> 
               <div class="form-group" style="display: none" id="onbehalf">
              <label class="col-md-3">Select Employee<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control chosen-select" id="user" name="user" field-name="Employee" onchange="getPieChartBasedOnUser('onbehalf'); getApproversList();">
                   <option value="">--Select Employee--</option>
                	<c:forEach var="user" items="${usersLis}">
	                     <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                	</c:forEach>
                </select>
              </div>
            </div>
              
              
            <div class="form-group">
              <label class="col-md-3">From<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-7 paddingRight" id="fromDateDiv">
                    <input type="text" class="form-control calendar" id="fromDate" name="fromDate" placeholder="From Date" field-name="From Date" data-validation="required" readonly>
                  </div>
                  <div class="col-md-5 paddingLeft"  id="fromHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-2" class="checkbox-custom checkboxCustom" id="fromHalfDay" name="fromHalfDay"  type="checkbox" onclick="checkFromHalfDay();">
					<label for="checkbox-2" class="checkbox-custom-label"></label>
                  </label>
                      Half a Day </label>
                  </div>
                </div>
              </div>
            </div>
              <div class="form-group">
              <label class="col-md-3">To<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-7 paddingRight" id="toDateDiv">
                    <input type="text" class="form-control calendar" id="toDate" name="toDate" placeholder="To Date" field-name="To Date" data-validation="required" readonly>
                  </div>
                  <div class="col-md-5 paddingLeft" id="toHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-3" class="checkbox-custom checkboxCustom" name="toHalfDay" value="NO" type="checkbox" onclick="checkToHalfDay();">
					<label for="checkbox-3" class="checkbox-custom-label"></label>
                  </label>
                      Half a Day </label>
                  </div>
                </div>
              </div>
            </div>
              <div class="form-group">
              <label class="col-md-3">Leave Type<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select">
              <div id="leaveTypeIdDiv"></div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3" id="reasonIdY" style="display:block">Reason<span class="text-danger paddingLeft" >*</span></label>
              <label class="col-md-3" id="reasonIdN" style="display:none">Reason</label>
              <div class="col-md-9" id="reasonId">
                <textarea class="form-control" rows="2" placeholder="Reason" id="reason" name="reason" field-name="Reason" data-validation="required validate_length length1-500"></textarea>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3" id="attachmentY" style="display:block">Attachment<span class="text-danger paddingLeft" >*</span></label>
              <label class="col-md-3" id="attachmentN" style="display:none">Attachment</label>
              <div class="col-md-9 errorInptgroup errorInptgroup-bottom" id="attachmentId">
                  <div style="float:left; width:100%; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#fff; color:#333; ">
                      <input  type="file" class="pull-left valid" id="uploadBtn" field-name="Attachment"  onchange="uploadButton(0);" data-validation="required">
                      <a id="Button0value=">
                      <i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right" title="clear" onClick="removedocfile();"></i>
                      </a>
                  </div>
              </div>
            </div>
<!--             <p class="form-control-static clearfix"><strong>Approval</strong></p> -->
            <div class="form-group">
              <label class="col-md-3">Approval By<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select">
                  <select  multiple data-placeholder="Select Approver" class="chosen-select form-control" id="approverUser" name="approverUser" field-name="Approver" data-validation="required">
<%--                 	<c:forEach var="user" items="${approverList}"> --%>
<%-- 	                	<c:if test="${user.id != SourceLeadWeb_userId}"> --%>
<%-- 	                		<option value="${user.id}">${user.firstName} ${user.lastName} (${user.userType})</option> --%>
<%-- 	                	</c:if> --%>
<%--                 	</c:forEach> --%>
                  </select>
              </div>
            </div>
              
              
              
              
          </div>
          <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <div class="well-sm well well-custom clearfix amchartAnchor-Hide">
                 <h5>Leaves Status</h5>
                <div id="leavestatsu" style="width: 100%; height: 150px;"></div>
                <div class="row">
                <div id="leaveStatusIdDiv"></div>
                </div>
                
            </div>
          </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Apply" id="applyId" class="btn btn-primary ml mr" onclick="applyLeave();">
            <input type="button" value="Cancel" class="btn btn-default" onclick="cancel();">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends-->
      </form:form> 
    </div>
  </section>
  
 <script>
 function removedocfile(){
	 $("#uploadBtn").val('');
 }
 </script> 
 <script>
 function checkFromHalfDay(){
	 $("#checkbox-2").val('YES');
 }
 
 function checkToHalfDay(){
	 $("#checkbox-3").val('YES');
 }
 </script>
  
  
  <script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEndDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEndDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEndDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEndDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEndDate = 'yy-dd-mm'; 
		  }
		$('#fromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true,
			minDate: 0,
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#toDate").datepicker("option", "minDate", dt);
			removeStartValid();
			}
		});
		
	  $('#toDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#fromdate").datepicker("option", "maxDate", dt);
			removeEndValid();
			validateDate();
			 $('#leaveType').prop('disabled', false).trigger("chosen:updated");
			}
		});
		
	});
</script>

<script>
function removeStartValid(){
	$('#fromDateDiv').find('p.jquery_form_error_message').remove();
}

function removeEndValid(){
	$('#toDateDiv').find('p.jquery_form_error_message').remove();
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
 function getPieChartBasedOnUser(onbehalf){
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var userId ='';
	 if(onbehalf !=null && onbehalf !='' && onbehalf !='undefined'){
		 userId = $("#user").val();
	 }else{
		 userId = '${SourceLeadWeb_userId}';
	 }
	 $.ajax({
         type : "GET",
          url: sourceleadUrl+"/getPieChartForLeaveStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,    
          success :function(json) {
        	  var output = "";
        	  output = output +'<select class="form-control" id="leaveType" name="leaveType" field-name="Leave Type" data-validation="required" onchange="getLeaveDetails();validateMaxLeave(this);">';
        	  output = output +' <option value="">--Select Leave Type--</option>';
        	  for (var i = 0; i < json.length; i++) { 
        		  var leave = json[i];
        		  output = output +'<option value='+leave.id+'>'+leave.name+'</option>';
        	  }
        	  output = output +'</select>';
        	  $("#leaveTypeIdDiv").empty();
        	  $("#leaveTypeIdDiv").append(output);
        	  $('#leaveType').chosen();
        	  $("#leaveType").trigger("chosen:updated");
        	  $('#leaveType').prop('disabled', true).trigger("chosen:updated");
        	  
        	  var counts = json[json.length-1];
        	  var totalBalanceCounts = counts.balanceLeaveCounts;
        	  var totalPendingCounts = counts.pendingLeaveCounts;
        	  var totalTakenCounts = counts.takenLeaveCounts;
        	  getpieChar(totalBalanceCounts,totalPendingCounts,totalTakenCounts);
        	  
        	  var result = "";
	    	  result = result +' <ul class="list-unstyled leavelist col-md-10 mt25 col-md-offset-1 list-group">';
	    	  result = result +'			<label>Balance Leaves :</label>';
	          for (var i = 0; i < json.length; i++) { 
	        		  var leaveType = json[i];
				        result = result +'	<li>';
				  		result = result +'			<label>'+leaveType.name+'</label>';
				  		var leaveTypeName = leaveType.name.replace(/ /g,"_");
				  		result = result +'			<span class="label label-info" id="'+leaveTypeName+'Span" style="background-color: '+leaveType.colorCode+';" >'+leaveType.remainingTotalLeaveCounts+'</span>';
				  		result = result +'	</li>'; 
	        	  }
	        	  result = result +'	</ul>'; 
	        	  $("#leaveStatusIdDiv").empty();
	        	  $("#leaveStatusIdDiv").append(result);

	        	  
	        	 
         },
          
     }); 
	 
function getpieChar(totalBalanceCounts,totalPendingCounts,totalTakenCounts){
	//ApplyLeves piechart
	AmCharts.makeChart("leavestatsu",
		{
			"type": "pie",
			"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
			"innerRadius": "40%",
			"minRadius": 0,
			"pieX": "50%",
			"pieY": "50%",
			"pullOutRadius": "0%",
			"startRadius": "0%",
			"colors": [],
			"colorField": "color",
			"labelsEnabled": false,
			"marginBottom": 0,
			"marginLeft": 2,
			"marginTop": 0,
			"maxLabelWidth": 0,
			"titleField": "category",
			"valueField": "column-1",
			"allLabels": [],
			"balloon": {},
			"titles": [],
			"dataProvider": [
				{
					"category": "Pending Leaves",
					"column-1": totalPendingCounts,
					"color"	  : "#337ab7"
				},
				{
					"category": "Balance Leaves",
					"column-1": totalBalanceCounts,
					"color"	  : "#5bc0de"
				},
				{
					"category": "Taken Leaves",
					"column-1": totalTakenCounts,
					"color"	  : "#5cb85c"
				}
			]
		}				
	);
}
 }
</script>
		
		
    <script>
    function approvedlevel(){
        $(".approvedlevel").toggle();
    }
    </script>

<script>
function onbehalf() { 
	var ckbox = $('#checkbox-1');
	if (ckbox.is(':checked')) {
		$("#onbehalf").show();
	    $("#user").attr('data-validation',"required");
	    $("#fromDate").val('');
	    $("#toDate").val('');
	}else{
		$("#onbehalf").hide();
		$("#user").val('');
		$('#user').trigger("chosen:updated");
	    $("#user").removeAttr('data-validation');
	    $("#fromDate").val('');
	    $("#toDate").val('');
	}
    
}
</script>

<script>
$(function() {
	$("#uploadBtn").change(function() {
		fileUpload("uploadBtn", "leaveDocuments", "apply_leave_form");
	});
 });
</script>

<script type="text/javascript">
function applyLeave(){
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		if ($('#apply_leave_form').validate(false, validationSettings)) {
	  	var formData = $($('#apply_leave_form')[0]).serialize();
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/saveApplyLeave?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId, formData, function(data) {
			if(data == true){
				location.replace(sourceleadUrl+ "/leaveManagementReports");
			}
		}); 
		return true;
		} else {
			return false;
		}
}
$('body').on('blur', '#apply_leave_form', function() {
	$("#apply_leave_form").showHelpOnFocus();
	$("#apply_leave_form").validateOnBlur(false, validationSettings);
});
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script>
function cancel(){
	location.replace(sourceleadUrl+ "/leaveManagementReports");
}
</script>

 <!-- Modal -->
  <div class="terms-conditions modal" id="termsModel" style="display:none;">
    <div class="modal-dialog modal-lg">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Terms &amp; Conditions</h4>
        </div>
        <div class="modal-body">
          <p id="termsAndConditions"></p>
        </div>
      </div>
      
    </div>
  </div>
  
 <script>
$(function(){
$(".close").click(function(){
	$(".terms-conditions.modal").fadeOut();
});
  });
</script>

<script>
function getLeaveDetails(){
	var leaveTypeId = $("#leaveType").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	if(leaveTypeId !=null && leaveTypeId != '' && leaveTypeId !='undefined'){
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/leaveTypeSummary?&leaveTypeId="+leaveTypeId,	 
		    success: function(report) {
		    	if(report.duration == "Full Day"){
		    		$("#fromHalfDayDiv").hide();
		    		$("#toHalfDayDiv").hide();
		    	}else{
		    		$("#fromHalfDayDiv").show();
		    		$("#toHalfDayDiv").show();
		    	}
		    	
		    	if(report.reasonRequired == "Y"){
			 		$("#reason").attr('data-validation',"required");
			 		document.getElementById("reasonIdY").style.display = 'block';
			 		document.getElementById("reasonIdN").style.display = 'none';
		    	}else{
		    		document.getElementById("reasonIdY").style.display = 'none';
			 		document.getElementById("reasonIdN").style.display = 'block';
		    		$("#reason").removeAttr('data-validation');
		    	}
		    	
		    	if(report.attachmentRequired == "Y"){
			 		$("#uploadBtn").attr('data-validation',"required");
			 		document.getElementById("attachmentY").style.display = 'block';
			 		document.getElementById("attachmentN").style.display = 'none';
		    	}else{
		    		document.getElementById("attachmentY").style.display = 'none';
		    		document.getElementById("attachmentN").style.display = 'block';
		    		$("#uploadBtn").removeAttr('data-validation');
		    	}
		    	
		    	
		    	if(report.overdrawLeaves != null && report.overdrawLeaves != '' && report.overdrawLeaves != 'undefined' && report.overdrawLeaves == "N"){
		    		validateOverDrawLeaves(report.name);
		    	}else{
		    		$("#applyId").prop("disabled", false);
		    	}
		    	
		    	if(report.priorLeaveApplying == "Y" && fromDate != ""){
		    		validatePriorDays(report.priorDays);
		    		
		    	}
		    	if(report.leaveDuration == "Y" && fromDate != "" && toDate != ""){
		    		validateMinMaxDays(report.minDays, report.maxDays);
		    	}
		    	
		    	if(errorMessage != ""){
		        	$("#applyId").prop("disabled", true);
		        	$("#leaveTypeIdDiv").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span>"+errorMessage+"</span> ");
		        	$(".comMask").fadeIn();
		        	errorMessage = "";
		        }else{
		    		$("#applyId").prop("disabled", false);
		        }
		    	if(report.policyDescription !=null){
	 				$("#termsAndConditions").html(report.policyDescription);
	 				document.getElementById("termsModel").style.display="block";
	 			}
		    }
		});
	}
}

</script>

<script>
function validateOverDrawLeaves(leaveTypeName){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
    var convertFromDate = parseInt(fromDate);
    var convertToDate = parseInt(toDate);
    var dayDiff = (convertToDate - convertFromDate+1);
	
	var typeName = leaveTypeName.replace(/ /g,"_");
	var typeIdBalCount = $("#"+typeName+"Span").text();
	if((dayDiff > typeIdBalCount) || typeIdBalCount ==  0){
		$("#applyId").prop("disabled", true);
		$("#leaveTypeIdDiv").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span>You Have Not Enough Leaves To Apply</span> ");
		$(".comMask").fadeIn();
	}else{
		$("#applyId").prop("disabled", false);
	}
}
</script>
<script>
var errorMessage = "";
function validatePriorDays(priorDays){
	var currentDate = new Date();
    var fromDate = $("#fromDate").val();
    var convertedCurrentDate = parseInt(currentDate.getDate());
    var convertedFromDate = parseInt(fromDate);
    var dayDiff= (convertedFromDate - convertedCurrentDate);
    if(priorDays >= dayDiff){
    	errorMessage += "You need to apply this Leave Type "+priorDays+" earlier.";
    }
}

</script>

<script>
function validateMinMaxDays(minDays, maxDays){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
    var convertFromDate = parseInt(fromDate);
    var convertToDate = parseInt(toDate);
    var dayDiff= (convertToDate - convertFromDate+1);
    if(!(maxDays >= dayDiff && dayDiff >= minDays)){
    	errorMessage += "You need to apply this Leave Type for atleast Min "+minDays+"days and Max "+maxDays+"days.";
    
    }
}

</script>

<script>
function validateMaxLeave(data){
	$('.jquery_form_error_message cOrange').hide();
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	
	var leaveTypeId = $("#leaveType").val();
	if(leaveTypeId !=null && leaveTypeId != '' && leaveTypeId !='undefined'){
		var leaveTypeName = data.options[data.selectedIndex].innerHTML;
		$.get(sourceleadUrl+"/validateMaxLeave?"+tokenParamName+"="+tokenValue+"&leaveTypeId="+leaveTypeId+"&orgLocationId="+orgLocationId+"&userId="+userId, function(data) {
			if(data == true){
				$('.jquery_form_error_message').remove();
				$("#leaveTypeIdDiv").after("<p class='jquery_form_error_message cOrange'>You don't have balance for the '"+leaveTypeName+"'.</span>");
			}else{
				$('.jquery_form_error_message').hide();
			}
		}); 
	}
	
}
</script>
<div class='comMask'></div>
<script>
function validateDate(){
	
	$('.warMess').hide();
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var roleId = '${SourceLeadWeb_loginUserRoleId}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	$.get(sourceleadUrl+"/validateRestrictDate?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&orgLocationId="+orgLocationId+"&userId="+userId, function(data) {
		if(data != null){
			$("#toDate").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span>You cannot apply for leave on restricted/holiday day</span> "+data);
			$(".comMask").fadeIn();
		}else{
			$('.warMess').hide();
		}
	}); 
};

function closedate(){
$(".warMess").fadeOut();
$(".comMask").fadeOut();
};
$(".comMask").click(function()
{
	$(".comMask").fadeOut();
	$(".warMess").fadeOut();
		});


</script>

<script>
function getApproversList(){
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}

	$.get(sourceleadUrl+"/getApproversForLeave?&userId="+userId, function(data) {
		if(data !=null){
		var result = "";
		for(var i=0;i<data.length;i++){
		var opt = data[i];
		if(behalf.is(':checked')){
		result += "<option value='"+opt.id+"'> "+opt.firstName+" "+opt.lastName+" ("+opt.userType+") </option> ";
		}else{
		if(userId != opt.id){
		result += "<option value='"+opt.id+"'> "+opt.firstName+" "+opt.lastName+" ("+opt.userType+") </option> ";
		}
		}
		}
		$("#approverUser").empty();
		$("#approverUser").append(result);
		$('#approverUser').trigger("chosen:updated");
		}
		});
}
</script>



<jsp:include page="../masterFooter.jsp" />

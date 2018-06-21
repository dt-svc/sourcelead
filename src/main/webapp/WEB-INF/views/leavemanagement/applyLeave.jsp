<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	  
<script type="text/javascript">
window.onload = function() { 

	var leaveUUID = '${leaveUUID}';
	if(leaveUUID != null && leaveUUID != '' && leaveUUID != 'undefined'){
		getLeaveDetails();
	}
	
	getPieChartBasedOnUser();
	getApproversList();
   if($("#checkbox-1").is(':checked')){
	   $("#checkbox-1").prop('checked', false);
	}
   if($("#checkbox-2").is(':checked')){
	   $("#checkbox-2").prop('checked', false);
	}
   if($("#checkbox-3").is(':checked')){
	   $("#checkbox-3").prop('checked', false);
	}
   
   var isSuperUser = '${SourceLeadWeb_isSuperUser}';
   if(isSuperUser){
	   $("#approvalDiv").hide();
   }else{
	   $("#approvalDiv").show();
   }
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
     <div id="leaveLoading"></div> 
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
              <label class="col-md-3">Leave Type<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select">
              <div id="leaveTypeIdDiv"></div>
              </div>
             </div>
              
            <div class="form-group">
              <label class="col-md-3">From<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-7 paddingRight" id="fromDateDiv">
                  <div class="input-group errorInptgroup">
                    <input type="text" class="form-control calendar" id="fromDate" value="${fromDate}" name="fromDate" placeholder="From Date" field-name="From Date" data-validation="required" readonly>
                    <span class="input-group-addon" onClick="clearFromDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                   </div> 
                  </div>
                  <div class="col-md-5 paddingLeft"  id="fromHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-2" class="checkbox-custom checkboxCustom" id="fromHalfDay" name="fromHalfDay"  type="checkbox" onclick="checkFromHalfDay();validateFromHalfDay();">
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
                    <div class="input-group errorInptgroup">
                     <input type="text" class="form-control calendar" id="toDate" name="toDate" value="${toDate}" placeholder="To Date" field-name="To Date" data-validation="required" readonly>
                     <span class="input-group-addon" onClick="clearToDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                    </div>
                  </div>
                  <div class="col-md-5 paddingLeft" id="toHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-3" class="checkbox-custom checkboxCustom" name="toHalfDay" value="NO" type="checkbox" onclick="checkToHalfDay();validateToHalfDay();">
					<label for="checkbox-3" class="checkbox-custom-label"></label>
                  </label>
                      Half a Day </label>
                  </div>
                </div>
              </div>
            </div>
              
            <div class="form-group">
              <label class="col-md-3" id="reasonIdY" style="display:block">Reason<span class="text-danger paddingLeft" >*</span></label>
              <label class="col-md-3" id="reasonIdN" style="display:none">Reason</label>
              <div class="col-md-9" id="reasonId">
                <textarea class="form-control" rows="2" placeholder="Reason" id="reason" name="reason" field-name="Reason" data-validation="required validate_length length1-500">${leave.reason}</textarea>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3" id="attachmentY" style="display:block">Attachment<span class="text-danger paddingLeft" >*</span></label>
              <label class="col-md-3" id="attachmentN" style="display:none">Attachment</label>
              <div class="col-md-9 errorInptgroup errorInptgroup-bottom" id="attachmentId">
                  <div class="positionRelative clearfix"><i class="clearUpload" title="clear" onClick="removedocfile();"></i>
                  
                  <div class="input-group ratetype col-md-12 pull-left" title="Document Type: .pdf format only">
	                      <label class="input-group-btn"> 
	                        <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Choose File
	                          <input type="file" id="uploadBtn" style="display: none;" class="upload">
	                        </span> 
	                      </label>
		                      <input type="text" class="form-control" readonly id="uploadFile" value="${leave.fileName}" placeholder="no file chosen" field-name="Attachment" data-validation="required">
	              </div>
                  </div>
              </div>
            </div>
            
<%--             <c:if test="${SourceLeadWeb_isSuperUser != true}"> --%>
            	<div class="form-group" id="approvalDiv">
	              <label class="col-md-3">Approval By<span class="text-danger paddingLeft">*</span></label>
	              <div class="col-md-9 dropdown-select" id="approverUserDiv">
	                  <select  multiple data-placeholder="Select Approver" class="chosen-select form-control" id="approverUser" name="approverUser" field-name="Approver" data-validation="required">
	                  </select>
	              </div>
            	</div>
<%--             </c:if> --%>
              
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
	 $("#uploadFile").val('');
 }
 </script> 
 <script>
 function checkFromHalfDay(){
	
    $("#checkbox-2").val('YES');
 }
 
 function checkToHalfDay(){
	
	 $("#checkbox-3").val('YES');
 }
  function validateFromHalfDay()
  {
	  $("#toHalfDayDiv").hide();
		 var fromDate = $("#fromDate").val();
		 var toDate = $("#toDate").val();
		 if(fromDate.length !=0 && toDate.length!=0)
	    {
		 if(fromDate == toDate)
		 {
			 
			 $("#toHalfDayDiv").hide();
		 }
		else
			{
			 $("#toHalfDayDiv").show();
			}
		 
		
	    }
	  
  }
  function validateToHalfDay()
  {
	  $("#fromHalfDayDiv").hide();
		 var fromDate = $("#fromDate").val();
		 var toDate = $("#toDate").val();

		 if(fromDate.length !=0 && toDate.length!=0)
		    {
		 if(fromDate == toDate)
		 {
			$("#fromHalfDayDiv").hide();
			
		  }
		   else
			{
				$("#fromHalfDayDiv").show();
			}
		 
		
		    } 
   }
 function checkFromDate(){
	 var fromDate = $("#fromDate").val();
	 var toDate = $("#toDate").val();
	 
	 if(fromDate.length !=0 && toDate.length!=0)
	    {
		 if(fromDate == toDate && ($("#checkbox-3").is(':checked')) && ($("#checkbox-2").is(':checked'))){
			 $("#checkbox-3").prop('checked', false);
			 $("#toHalfDayDiv").hide();
		 }else{
			 if(fromDate == toDate && ($("#checkbox-3").is(':checked')) )
			 {
				 $("#fromHalfDayDiv").hide();
			 }
			else
				{
				$("#fromHalfDayDiv").show();
				}
			 if(fromDate == toDate && ($("#checkbox-2").is(':checked')) )
			 {
				 $("#toHalfDayDiv").hide();
			 }
			else
				{
				$("#toHalfDayDiv").show();
				}
		 }
	    }
	 
 }
  function checkToDate()
  {
	  
	  var fromDate = $("#fromDate").val();
	  var toDate = $("#toDate").val();
	  if(fromDate.length !=0 && toDate.length!=0)
	  {
		if(fromDate == toDate && ($("#checkbox-3").is(':checked')) && ($("#checkbox-2").is(':checked'))){
			 $("#checkbox-3").prop('checked', false);
			 $("#toHalfDayDiv").hide();
	  }
	 else
	 {
		 if(fromDate == toDate && ($("#checkbox-2").is(':checked')))
		  {
			 $("#toHalfDayDiv").hide();
		   }
		  else
		  {
			$("#toHalfDayDiv").show();
		  }
		 if(fromDate == toDate && ($("#checkbox-3").is(':checked')))
		 {
			 $("#fromHalfDayDiv").hide();
		 }
		 else
		 {
			$("#fromHalfDayDiv").show();
		 }
	 }
   } 
	  
	  
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
		//to set from date to current month
	       var currentTime = new Date()
	       var minDate = new Date(currentTime.getFullYear(), currentTime.getMonth(), +1);
		$('#fromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true,
// 			minDate: 0,
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#toDate").datepicker("option", "minDate", dt);
			removeStartValid();
			checkFromDate();
			getLeaveDetails(finalDateFormatEndDate);
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
			checkToDate();
			validateDate();
			getLeaveDetails();
// 			 $('#leaveType').prop('disabled', false).trigger("chosen:updated");
			}
		});
	  
	  document.getElementById("uploadBtn").onchange = function () {
  	    document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();;
  	    };
		
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
	 var leaveId = '${leave.leaveType.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var userId ='';
	 if(onbehalf !=null && onbehalf !='' && onbehalf !='undefined'){
		 userId = $("#user").val();
	 }else{
		 userId = '${SourceLeadWeb_userId}';
	 }
	 if(userId != null && userId !='' && userId !='undefined'){
	 $.ajax({
         type : "GET",
          url: sourceleadUrl+"/getPieChartForLeaveStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,    
          success :function(json) {
        	  var output = "";
        	  output = output +'<select class="form-control" id="leaveType" name="leaveType" field-name="Leave Type" data-validation="required" onchange="getLeaveDetails();validateMaxLeave(this);">';
        	  output = output +' <option value="">--Select Leave Type--</option>';
        	  for (var i = 0; i < json.length; i++) { 
        		  var leave = json[i];
        		  if(leave.id == leaveId){
        			  output = output +'<option selected value='+leave.leaveTypeUUID+'>'+leave.name+'</option>';
        		  }else{
        			  output = output +'<option value='+leave.leaveTypeUUID+'>'+leave.name+'</option>';
        		  }
        	  }
        	  output = output +'</select>';
        	  $("#leaveTypeIdDiv").empty();
        	  
        	  $("#leaveTypeIdDiv").append(output);
        	  $('#leaveType').chosen();
        	  $("#leaveType").trigger("chosen:updated");
//         	  $('#leaveType').prop('disabled', true).trigger("chosen:updated");
        	  
        	  if(json.length > 0){
        		  var counts = json[json.length-1];
            	  var totalBalanceCounts = counts.totalBalanceLeaveCounts;
            	  var totalPendingCounts = counts.pendingLeaveCounts;
            	  var totalTakenCounts = counts.takenLeaves;
        		  getpieChar(totalBalanceCounts,totalPendingCounts,totalTakenCounts);
        		  
        		  
        		  var result = "";
    	    	  result = result +' <ul class="list-unstyled leavelist col-md-10 mt25 col-md-offset-1 list-group">';
    	    	  result = result +'			<label>Balance Leaves :</label>';
    	          for (var i = 0; i < json.length; i++) { 
    	        		  var leaveType = json[i];
    				        result = result +'	<li>';
    				  		result = result +'			<label>'+leaveType.name+'</label>';
    				  		var leaveTypeName = leaveType.name.replace(/ /g,"_");
    				  		result = result +'			<span class="label label-info pull-right" id="'+leaveTypeName+'Span" style="background-color: '+leaveType.colorCode+';" >'+leaveType.leaveTypeBalanceLeaveCount+'</span>';
    				  		result = result +'	</li>'; 
    	        	  }
   	        	  result = result +'	</ul>'; 
   	        	  $("#leaveStatusIdDiv").empty();
   	        	  $("#leaveStatusIdDiv").append(result);
        		  
        	  }else{
        		  var res = ""
	        		  res = res +'<div class="text-center mt25 well"><i class="fa fa-calendar-minus-o fa-5x text-muted"></i>';
	        		  res = res +'<p class="clearfix mt text-muted small">No Active Leave Types Found !</p>';
	        		  res = res +'</div>';
        		  $("#leavestatsu").empty();
        		  $("#leavestatsu").append(res);
        	  }
        	  
         },
          
     });
	}
	 
function getpieChar(totalBalanceCounts,totalPendingCounts,totalTakenCounts){
	//ApplyLeves piechart
	//
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
	    var isSuperUser = '${SourceLeadWeb_isSuperUser}';
	    if(isSuperUser){
	 	   $("#approvalDiv").hide();
	    }else{
	 	   $("#approvalDiv").show();
	    }
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
		var leaveId = '${leave.id}';
		var currentStage = '${currentStage}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		if ($('#apply_leave_form').validate(false, validationSettings)) {
	  	var formData = $($('#apply_leave_form')[0]).serialize();
		$("#leaveLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/saveApplyLeave?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveId="+leaveId+"&currentStage="+currentStage, formData, function(result) {
			if (result && result.response === "INVALID_DATA") {
    		    showError(result.responseMessage);
    		} else if (result.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (result.response === "SUCCESS") {
    			location.replace(sourceleadUrl+ "/leaveManagementReports");
    		} else if (result.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
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
function getLeaveDetails(finalDateFormatEndDate){
	var editLeaveTypeUUID = '${leave.leaveType.leaveTypeUUID}';
	var leaveTypeUUID = '';
	if(editLeaveTypeUUID != null && editLeaveTypeUUID != '' && editLeaveTypeUUID != 'undefined'){
		 leaveTypeUUID = editLeaveTypeUUID;
	}else{
		 leaveTypeUUID = $("#leaveType").val();
	}
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var skipPolicy = false;
	var policy = $("#termsAndConditions").text();
	if(policy != null && policy != '' && policy !='undefined'){
		skipPolicy = true;
	}
	var isSuperUser = '${SourceLeadWeb_isSuperUser}';
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	
	if(leaveTypeUUID !=null && leaveTypeUUID != '' && leaveTypeUUID !='undefined'){
		$.ajax({
		   	type: "POST",
		   	url: sourceleadUrl+"/leaveTypeSummary?&leaveTypeUUID="+leaveTypeUUID+"&loginUserId="+userId,	 
		    success: function(result) {
		    	if (result && result.response === "INVALID_DATA") {
          		    showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          		    location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			var report = result.object;
          			
          			if(report.duration == "Half Day"){
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
    		    	
    		    	if(report.userGender == "Not Allow"){
    		    		errorMessage += "Selected LeaveType is not allowed to apply for your Gender";
    		    	}
    		    	
    		    	if(report.autoApprove == "Y"){
    		    		$("#approvalDiv").hide();
    		    	}else{
    		    		if(isSuperUser == true){
   	   		    		   $("#approvalDiv").hide();
   	   		    	   }else{
   	   		    		   $("#approvalDiv").show();
   	   		    	   }
    		    	}
    		    	
    		    	
    		    	if(report.overdrawLeaves != null && report.overdrawLeaves != '' && report.overdrawLeaves != 'undefined' && report.overdrawLeaves == "N"){
    		    		validateOverDrawLeaves(report.name);
    		    	}else{
    		    		$("#applyId").prop("disabled", false);
    		    	}
    		    	if(report.priorLeaveApplying == "Y" && fromDate != ""){
    		    		validatePriorDays(report.priorDays,finalDateFormatEndDate);
    		    		
    		    	}
    		    	if(report.leaveDuration == "Y" && fromDate != "" && toDate != ""){
    		    		validateMinMaxDays(report.minDays, report.maxDays);
    		    	}
    		    	
    		    	if(errorMessage != ""){
    		        	$("#applyId").prop("disabled", true);
    		        	$("#leaveTypeIdDiv").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span>"+errorMessage+"</p> ");
    		        	$(".warMess").fadeIn();
    		        	$(".comMask").fadeIn();
    		        	errorMessage = "";
    		        }else{
    		    		$("#applyId").prop("disabled", false);
    		        }
    		    	if(skipPolicy != true){
    		    		if(report.policyDescription !=null && report.policyDescription !='' && report.policyDescription !='undefined'){
    		 				$("#termsAndConditions").html(report.policyDescription);
    		 				document.getElementById("termsModel").style.display="block";
    		 			}
    		    	}
          		} else if (result.indexOf('Sign In') !== -1) {
          			location.replace(sourceleadUrl);
          		} else {
          		    showError("Operation failed, please try again");
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
function validatePriorDays(priorDays,finalDateFormatEndDate){
	var currentDate = new Date();
	
	$( "#fromDate" ).datepicker({ dateFormat: finalDateFormatEndDate });
	var start = $('#fromDate').datepicker('getDate');
    var diffDays = parseInt((start - currentDate) / (1000 * 60 * 60 * 24)); 
    if(priorDays > diffDays){
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
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var leaveTypeId = $("#leaveType").val();
	if(userId != null && userId !='' && userId !='undefined'){
	if(leaveTypeId !=null && leaveTypeId != '' && leaveTypeId !='undefined'){
		var leaveTypeName = data.options[data.selectedIndex].innerHTML;
		$.get(sourceleadUrl+"/validateMaxLeave?"+tokenParamName+"="+tokenValue+"&leaveTypeId="+leaveTypeId+"&orgLocationId="+orgLocationId+"&userId="+userId+"&fromDate="+fromDate+"&toDate="+toDate, function(data) {
			if(data == true){
				$('.jquery_form_error_message').remove();
				$("#leaveTypeIdDiv").after("<p class='jquery_form_error_message cOrange'>You don't have balance for the '"+leaveTypeName+"'.</span>");
			}else{
				$('.jquery_form_error_message').hide();
			}
		}); 
	}
	
  }
	
}
</script>

<script>
function validateCarryForwardLeaves(){
	$('.jquery_form_error_message cOrange').hide();
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var leaveTypeId = $("#leaveType").val();
	if(userId != null && userId !='' && userId !='undefined'){
	if(leaveTypeId !=null && leaveTypeId != '' && leaveTypeId !='undefined'){
		$.get(sourceleadUrl+"/validateCarryForwardLeaves?"+tokenParamName+"="+tokenValue+"&leaveTypeId="+leaveTypeId+"&userId="+userId+"&fromDate="+fromDate+"&toDate="+toDate, function(data) {
			if(data == true){
				$('.jquery_form_error_message').remove();
				$("#leaveTypeIdDiv").after("<p class='jquery_form_error_message cOrange'>You don't have balance for the '"+leaveTypeName+"'.</span>");
			}else{
				$('.jquery_form_error_message').hide();
			}
		}); 
	}
	
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
	if(userId != null && userId != '' && userId != 'undefined' && fromDate != null && fromDate != '' && fromDate != 'undefined' && toDate != null && toDate != '' && toDate != 'undefined'){
		$.get(sourceleadUrl+"/validateRestrictDate?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&orgLocationId="+orgLocationId+"&userId="+userId, function(data) {
			if(data != null){
				$("#toDate").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span></span>"+data);
				$(".comMask").fadeIn();
				$("#applyId").prop("disabled", true);
			}else{
				$('.warMess').hide();
			}
		});
	}
	 
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
   if(userId != null && userId !='' && userId !='undefined'){
	$.get(sourceleadUrl+"/getApproversForLeave?&userId="+userId, function(data) {
		if(data !=null){
			var result = "";
			var opt = data[0];
			result += '<input type="text" class="form-control" id="approverUser" readOnly value="'+opt.firstName+' '+opt.lastName+' ('+opt.userType+')"/>';
			result += '<input type="hidden" class="form-control" name="approverUser" readOnly value="'+opt.id+'"/>';
			$("#approvalDiv").show();
			$("#approverUserDiv").empty();
			$("#approverUserDiv").append(result);
	// 		$('#approverUser').trigger("chosen:updated");
		}
		});
   }
}
</script>

<script>
function clearFromDate(){
	$("#fromDate").val("");
	};
	
function clearToDate(){
	$("#toDate").val("");
	};
</script>

<jsp:include page="../masterFooter.jsp" />

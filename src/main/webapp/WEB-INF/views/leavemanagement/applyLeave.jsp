<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	  
<script type="text/javascript">
window.onload = function() { 
	getPieChartBasedOnUser();
	}
</script>

<!-- Side Nav Bar --> 
    <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Apply Leave</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/leaveManagementReports'/>">Leave Management</a> &raquo; Apply Leave</div>
    </section>
    <div class="content-area content-new clearfix">
     <div id="loading"></div> 
    <form:form id="apply_leave_form" name="apply_leave_form" method="post">
      <div class="clearfix form-horizontal inner-content">
          <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-1">
          <sec:authorize access="hasRole('ROLE_APPLY_BEHALF_LEAVE')"> 
              <div class="form-group">
                    <label class="checkBox pull-left mr10">
					<input id="checkbox-1" class="checkbox-custom checkboxCustom onbehalf" name="checkbox-1" type="checkbox" onClick="onbehalf()">
					<label for="checkbox-1" class="checkbox-custom-label"></label>
                  </label>
                  <span class="pull-left">On behalf of</span>
              </div>
          </sec:authorize> 
               <div class="form-group" style="display: none" id="onbehalf">
              <label class="col-md-3">Select Employee</label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control chosen-select" id="user" name="user">
                   <option value=" ">--Select Employee--</option>
                	<c:forEach var="user" items="${usersLis}">
                	<%-- ${dept.id  == departmentId ? 'selected' : ''} --%>
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
                    <input type="text" class="form-control calendar" id="fromDate" name="fromDate" placeholder="From Date" field-name="From Date" data-validation="required">
                  </div>
                  <div class="col-md-5 paddingLeft">
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
                    <input type="text" class="form-control calendar" id="toDate" name="toDate" placeholder="To Date" field-name="To Date" data-validation="required">
                  </div>
                  <div class="col-md-5 paddingLeft">
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
                <%-- <select class="form-control" id="leaveType" name="leaveType" field-name="Leave Type" data-validation="required" onchange="getLeavePolicy();">
                  <option value="">--Select Leave Type--</option>
                	<c:forEach var="leave" items="${leaveTypeList}">
                	${dept.id  == departmentId ? 'selected' : ''}
	                     <option value="${leave.id}">${leave.name}</option>
                	</c:forEach>
                </select> --%>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Reason<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <textarea class="form-control" rows="2" placeholder="Reason" id="reason" name="reason" field-name="Reason" data-validation="required"></textarea>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3">Attachment</label>
              <div class="col-md-9">
                  <div style="float:left; width:100%; border:1px solid #ddd; margin:0 15px 15px 0; padding:5px;overflow: hidden; background-color:#fff; color:#333; ">
                      <input  type="file" class="pull-left valid" id="uploadBtn" onchange="uploadButton(0);">
                      <a id="Button0value=">
                      <i class="fa fa-times btn btn-danger btn-sm btn-sm2 pull-right"></i>
                      </a>
                  </div>
              </div>
            </div>
            <p class="form-control-static clearfix"><strong>Approval</strong></p>
            <div class="form-group">
              <label class="col-md-3">Approved By<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select">
                  <select  multiple data-placeholder="Select Approver" class="chosen-select form-control" id="approverUser" name="approverUser" field-name="Approver" data-validation="required">
                	<c:forEach var="user" items="${approverList}">
	                	<c:if test="${user.id != SourceLeadWeb_userId}">
	                		<option value="${user.id}">${user.firstName} ${user.lastName} (${user.userType})</option>
	                	</c:if>
                	</c:forEach>
                  </select>
              </div>
            </div>
              
              
              
              
          </div>
          <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
            <div class="well-sm well well-custom clearfix amchartAnchor-Hide">
                 <h5>Leave Status</h5>
                <div id="leavestatsu" style="width: 100%; height: 150px;"></div>
                <div class="row">
                <div id="leaveStatusIdDiv"></div>
                <%-- <ul class="list-unstyled leavelist col-md-10 mt25 col-md-offset-1 list-group">
                        <c:forEach var="leaveType" items="${leaveTypeList}">
                        	<li>
									<label>${leaveType.name}</label>
									<span class="label label-info" style="background-color: ${leaveType.colorCode};">${leaveType.remainingTotalLeaveCounts}</span>
							</li>
						</c:forEach>
              	</ul> --%>
                </div>
                
            </div>
          </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Apply" class="btn btn-primary ml mr" onclick="applyLeave();">
            <input type="button" value="Cancel" class="btn btn-default" onclick="cancel();">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends-->
      </form:form> 
    </div>
  </section>
  
  
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
 function getPieChartBasedOnUser(){
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
	 $.ajax({
         type : "GET",
          url: sourceleadUrl+"/getPieChartForLeaveStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,    
          success :function(json) {
        	  var output = "";
        	  output = output +'<select class="form-control" id="leaveType" name="leaveType" field-name="Leave Type" data-validation="required" onchange="getLeavePolicy();validateMaxLeave(this);">';
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
				  		result = result +'			<span class="label label-info" style="background-color: '+leaveType.colorCode+';" >'+leaveType.remainingTotalLeaveCounts+'</span>';
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
					"color"	  : "#FF4500"
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
    $("#onbehalf").toggle();
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
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/applyLeave?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId, formData, function(data) {
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
function getLeavePolicy(){
	var leaveTypeId = $("#leaveType").val();
	$.get(sourceleadUrl+"/getLeavePolicy?"+tokenParamName+"="+tokenValue+"&leaveTypeId="+leaveTypeId, function(data) {
		if(data !=null){
			$("#termsAndConditions").html(data);
			document.getElementById("termsModel").style.display="block";
		}
	}); 
}
</script>

<script>
function validateMaxLeave(data){
	$('.warMess').hide();
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	var leaveTypeId = $("#leaveType").val();
	var leaveTypeName = data.options[data.selectedIndex].innerHTML;
	$.get(sourceleadUrl+"/validateMaxLeave?"+tokenParamName+"="+tokenValue+"&leaveTypeId="+leaveTypeId+"&orgLocationId="+orgLocationId+"&userId="+userId, function(data) {
		if(data == true){
			$("#leaveTypeIdDiv").after("<p class='warMess'>You don't have balance for the '"+leaveTypeName+"'.</span>");
		}else{
			$('.warMess').hide();
		}
	}); 
	
}
</script>

<script>
function validateDate(){
	$('.warMess').hide();
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var roleId = '${SourceLeadWeb_loginUserRoleId}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	$.get(sourceleadUrl+"/validateRestrictDate?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&orgLocationId="+orgLocationId+"&roleId="+roleId, function(data) {
		if(data == true){
			$("#toDate").after("<p class='warMess'>You cannot apply for leave on restricted/holiday day</span>");
		}else{
			$('.warMess').hide();
		}
	}); 
}
</script>

<jsp:include page="../masterFooter.jsp" />

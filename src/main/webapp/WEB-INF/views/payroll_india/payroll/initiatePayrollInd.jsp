<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="timesheet_out" id="reviewPayrollDiv">
<div class="clearfix">
	<div id="reviewPayrollloader"></div>
	<p id='processingRequest' class='text-center' style="position: absolute; z-index: 111; color:#909090; width: 100%; bottom: 30px;"></p>
</div>
<div class="clearfix">
<div class="timesheet_head minHeight_225">
<form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
<div class="col-md-10 mt">
	<div class="row">
		<div class="col-md-3">
			<div class="dropdown-select">
			<select id="groupId" name="groupId" onchange="getGroupFrequency();" data-placeholder="Select Pay Group" class="chosen-select" data-validation="required" field-name="Pay Group">
                    <option value=""></option>
                    <c:forEach var="ug" items="${userGroupList}">
					<option value="${ug.groupUUID},${ug.id}">${ug.groupName}</option>
				   </c:forEach>
			</select>
			</div>
		</div>
		<div class="col-md-3 paddingLeft paddingRight">
			<select id="payPeriod" name="payPeriod" data-validation="required" data-placeholder="Select Pay Period" field-name="Pay Period" class="chosen-select">
			</select>
		</div>
		<div class="col-md-2 paddingLeft paddingRight" id="payTypeIdDiv">
			<select id="payType" name="payType" data-validation="required" data-placeholder="Select Pay type" field-name="Pay Type" class="chosen-select" onchange="errPayType();">
				 <!--  <option value=""></option>
				 <option value="All">All</option>
				 <option value="HourlyRate">Hourly Rate</option> -->
                 <option value = "Salary">Salary</option>
			</select>
		</div>
		
		<div class="col-md-2 paddingRight">
			<input name="" class="form-control calendar" placeholder="Check Date" id="payrollPayDate" name="payrollPayDate" type="text" data-validation="required" field-name="Pay Date" readonly>
		</div>
		<div class="col-md-2">
			<a  class="btn btn-success btnSuccess btn-sm anchorWhite" id="generateUSPayroll" onclick="runINDPayroll()">Initiate Payroll</a>
		</div>
	</div>						
</div>
<div class="clearfix"></div>
</form:form>


<div class="clearfix" id="initiatePayrollEmptyDiv" style="display:none;">
	<div class="dataTables_empty"></div>
</div>

<div class="clearfix" id="preCheckPayrollDiv" style="display:none;">
	<div class="clearfix text-center mt25">	
			<p class="paddngRight textBold text-orange" id="failureInfo"></p>
			<div class="clearfix text-center mb10 mt5">
			<a href="${SourceLeadWeb_sourceleadUrl}/addSourceleadTicket" class="text-primary textBold" title="Help desk">
				<i class="helpDeskSVG"></i>
				<div class="clearfix mt5"></div>
					<h5>Contact Sourcelead Help Desk</h5>
				</a>
			</div>
	</div>
</div>

</div>
</div>
</div>
<script>
function getGroupFrequency(){
	var groupUUID = $("#groupId").val();
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	 $.ajax({
	    	type: "GET",
	        url: pmsUrl+"/getGroupPayPeriod?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID                  , 
	        success: function(json) {
	        var output = "";
			for (var i=0; i<json.length; i++)
			{
				var date = json[i].payPeriod.replace(",", " to ");
				output = output + "<option value='" +json[i].payFrequency+ "'>"+date+"</option>";
			}
			$("#payPeriod").empty();
			$("#payPeriod").append(output);
			$('#payPeriod').trigger("chosen:updated"); 
	        },
	    });
}
</script>
<script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormat = getJSDateFormat(locationDate);
		  $('#payrollPayDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		  });
	});
</script>
<script type="text/javascript">
         function prepareChosenScripts(){ 
        	      var config = {
        			  '.chosen-select'           : {},
        			  '.chosen-select-deselect'  : {allow_single_deselect:true},
        			  '.chosen-select-no-single' : {disable_search_threshold:10},
        			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        			  '.chosen-select-width'     : {width:"95%"}
        			};
        		   for (var selector in config) {
        			  $(selector).chosen(config[selector]);
        			};
        			$('.chosen-select').each(function() {
        			var $this = $(this);
        			$this.next().css({'width': '100%'});
        			})
           };
         
</script> 

<script>
function runINDPayroll(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	if ($('#generate_payroll_form').validate(false, validationSettings)){  
		$('#preCheckPayrollDiv').hide();
		$('#initiatePayrollEmptyDiv').hide();
		$("#processingRequest").text("");
 	    $('#failureInfo').text("");
		$("#reviewPayrollloader").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$("#processingRequest").text("Initializing Payroll, please wait...");
		var groupId = $("#groupId").val();
		var payPeriod = $("#payPeriod").val();
		var payType = $("#payType").val();
		var payrollPayDate = $("#payrollPayDate").val();
		 $.ajax({
		    	   type: "POST",
		           url: pmsUrl+"/generateINDPayroll?groupId="+groupId+"&payPeriod="+payPeriod+"&payrollPayDate="+payrollPayDate+"&paytype="+payType,                 
		           success: function(data) {
		        	   $("#reviewPayrollloader").removeAttr("style");
		        	   $("#processingRequest").text("");
		        	  if(data != null && data != 'undefined' && data != '' && data.paySheetFrequencyUUID != null){
		        		  $(location).attr('href', 'employeePayrollList?paySheetFrequencyUUID='+data.paySheetFrequencyUUID+'');
		        	  }else if((data != null && data != 'undefined' && data != '' && data.preCheckINDPayrollDTO != null) && (data.preCheckINDPayrollDTO.isPTConfigured && data.preCheckINDPayrollDTO.isTDSConfigured)){
		        		  $('#initiatePayrollEmptyDiv').show();
		        	  }else if(data != null && data != 'undefined' && data != '' && data.preCheckINDPayrollDTO.isPTConfigured == false && data.preCheckINDPayrollDTO.isTDSConfigured == false){
		        		  $('#preCheckPayrollDiv').show();
		        		  $('#failureInfo').text("Initialization of Payroll is failed, due to PT and TDS configuration missed.");
		        	  }else if(data != null && data != 'undefined' && data != '' && data.preCheckINDPayrollDTO.isPTConfigured == false){
		        		  $('#preCheckPayrollDiv').show();
		        		  $('#failureInfo').text("Initialization of Payroll is failed, due to PT configuration missed.");
		        	  }else if(data != null && data != 'undefined' && data != '' && data.preCheckINDPayrollDTO.isTDSConfigured == false){
		        		  $('#preCheckPayrollDiv').show();
		        		  $('#failureInfo').text("Initialization of Payroll is failed, due to TDS configuration missed.");
		        	  }else{
		        		  $('#initiatePayrollEmptyDiv').show();
		        	  }
		        	},
		    });
		 
		return true; 
    }else{
   return false;
   	  } 
	}
	
$('body').on('blur', '#generate_payroll_form', function() {
	$("#generate_payroll_form").showHelpOnFocus();
	$("#generate_payroll_form").validateOnBlur(false, validationSettings);  
});
</script>

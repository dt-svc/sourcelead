 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  
<!-- <script>
$(document).ready(function(){
   getPayItemList();
});
</script>
 -->
  <div id="tab-6" class="tabsMenu_tabs_inn clearfix">
 	<div id="loading"></div>
 	 <form:form id="associate_salary_form" name="associate_salary_form" method="post">
            <div class="clearfix row minHeightcol5">
              <div class="col-md-12 mb">
                <div class="clearfix row">
                  <div class="col-md-5">
                    <label>Pay Type</label>
                    <div>
                      <select class="sel" id="payType" name="payType" data-validation="required" onchange="showHideSalaryDIV();" field-name="Pay Type">
                        <option value="">--Select--</option>
                        <option value="HourlyRate">Hourly Rate</option>
                        <option value = "Salary">Salary</option>
                        <option value = "LcaWage">LCA Wage</option>
                        <!-- <option value= "Commission Only">Commission Only</option> -->
                      </select>
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Frequency</label>
                    <div>
                      <select class="sel" id="payFrequency" name="payFrequency" onchange="getPayFreqHours();getPayGroups();" data-validation="required" field-name="Pay Frequency">
                        <option value="">--Select--</option>
                        <option value="Annual">Annually</option>
                        <option value="Monthly">Monthly</option>
                        <option value="Bi-Weekly">Bi-Weekly</option>
                        <option value="Weekly">Weekly</option>
                      </select>
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Group</label>
                    <div>
                      <select class="sel" id="payGroupId" name="payGroup" data-validation="required" field-name="Pay Group">
                      </select>
                    </div>
                  </div>
                  
                  <div id="salaryDIV">
                  <div class="col-md-5">
                    <label>Standard Hours</label>
                    <div>
                      <input type="text" class="form-control"  id="standardHours" name="standardHours" data-validation="required validate_float" field-name="Standard Hours" onblur="calculateSal();">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Annual Salary</label>
                    <div id="AnnualSal">
                      <input type="text" class="form-control" id="annualSal" name="annualSal" data-validation="required validate_float" field-name="Annual Salary" onblur="calculateSal();">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Pay Per Salary</label>
                    <div id="PerPaySal">
                      <input type="text" class="form-control" id="perPaySal" name="perPaySal" data-validation="required validate_float" field-name="Pay Per Salary" onblur = "calculatePayPerSal();">
                    </div>
                  </div>  
                   <div class="col-md-5">
                    <label>Hourly Rate</label>
                    <div id="HourlyRate">
                      <input type="text" class="form-control" id="hourlyRate" name="hourlyRate" data-validation="required validate_float" field-name="Hourly Rate" onblur = "calculateHourlyRateSal();">
                    </div>
                  </div>
                  </div>
                  
                   <div class="col-md-5">
                    <label>&nbsp;</label>
                    <div>
						<div class="checkbox-new pull-left">
							<input id="overtimePay" class="checkbox-custom" name="allowOverTimePay" id="allowOverTimePay" value="YES" type="checkbox">
							<label for="overtimePay" class="checkbox-custom-label"><span class="ml10">Allow Overtime Pay</span></label>
						</div>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                  <h4 class="col-md-12">Pay Item</h4>
                 <div class="clearfix col-md-12">
                 <div id="payItemDiv"></div>
                 	
                 </div>
					<div class="clearfix"></div>
                  <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <input type="button" value="Previous" onclick="shiftToAssociateTab();" class="btn btn-default">
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                   <input type="button" onclick="skipSalaryJsp();" class="btn btn-default mr10" value="Skip">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" id="save_asso_sal_button" type="button" onclick="saveAssocioateSal();">
                    <input value="Cancel" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
                </div>
              </div>
            </div>
            </form:form>
          </div>
          

<script>
function showHideSalaryDIV(){
	var payType = $("#payType").val();
	 if(payType == "HourlyRate" || payType == "LcaWage"){
		$("#salaryDIV").hide();
// 		$("#standardHours").val('');    
		$("#annualSal").val('');
		$("#perPaySal").val('');
		$("#hourlyRate").val('');
		$("#standardHours").removeAttr('data-validation');
		$('#standardHours').attr('data-validation-optional',"true");
		$("#annualSal").removeAttr('data-validation');
		$('#annualSal').attr('data-validation-optional',"true");
		$("#perPaySal").removeAttr('data-validation');
		$('#perPaySal').attr('data-validation-optional',"true"); 
		$("#hourlyRate").removeAttr('data-validation');
		$('#hourlyRate').attr('data-validation-optional',"true");
	}else{
		$("#salaryDIV").show();
		$("#standardHours").removeAttr('data-validation-optional');
		$('#standardHours').attr('data-validation',"required validate_float");
		$("#annualSal").removeAttr('data-validation-optional ');
		$('#annualSal').attr('data-validation',"required validate_float");
		$("#perPaySal").removeAttr('data-validation-optional');
		$('#perPaySal').attr('data-validation',"required validate_float");
		$("#hourlyRate").removeAttr('data-validation-optional');
		$('#hourlyRate').attr('data-validation',"required validate_float");
	}
}
</script>



<script>
function getPayFreqHours(){
	$('#standardHours').parent().find('p.jquery_form_error_message').remove();
	var payFrequency = $("#payFrequency").val();
	//Annual Monthly Bi-Weekly Weekly
	if(payFrequency === 'Annual'){
		$("#standardHours").val('2088');
	}
	if(payFrequency === 'Monthly'){
		$("#standardHours").val('176');
	}
	if(payFrequency === 'Bi-Weekly'){
		$("#standardHours").val('80');
	}
	if(payFrequency === 'Weekly'){
		$("#standardHours").val('40');
	}
	calculateSal();
	/* calculatePayPerSal() */
}
</script>


<script>
function calculateSal(){
	var payFrequency = $("#payFrequency").val();
	var annualSal = $("#annualSal").val();
	var hours = $("#standardHours").val();
	var hourlyRate = $("#hourlyRate").val();
	 if(annualSal != null && annualSal != '' && annualSal != 'undefined')
		{
		$('#AnnualSal').find('p.jquery_form_error_message').remove(); 
		}
	
	if(annualSal != null && annualSal != '' && annualSal != 'undefined' && hours != null && hours != '' && hours != 'undefined'){
		$('#PerPaySal').find('p.jquery_form_error_message').remove(); 
		$('#HourlyRate').find('p.jquery_form_error_message').remove();
		var rate = "";
		if(payFrequency === 'Annual'){
			rate = parseFloat(annualSal) / 1;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		if(payFrequency === 'Monthly'){
			rate = parseFloat(annualSal) / 12;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		if(payFrequency === 'Bi-Weekly'){
			rate = parseFloat(annualSal) / 26;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		if(payFrequency === 'Weekly'){
			rate = parseFloat(annualSal) / 52;
			$("#perPaySal").val(rate.toFixed(2));
			/* var hourRate = rate/hours;
			$("#hourlyRate").val(hourRate.toFixed(2)); */
		}
		var hourRate = rate/hours;
		$("#hourlyRate").val(hourRate.toFixed(2));
	}
}
</script>

<script>
function calculatePayPerSal(){
	var payFrequency = $("#payFrequency").val();
	var perPaySal = $("#perPaySal").val();
 	var hourlyRate = $("#hourlyRate").val();
	var standardHours = $("#standardHours").val();
	var annualSal = $("#annualSal").val();

	if(perPaySal !='' && perPaySal != 'undefined'){
		$('#AnnualSal').find('p.jquery_form_error_message').remove(); 
		$('#PerPaySal').find('p.jquery_form_error_message').remove();
		$('#HourlyRate').find('p.jquery_form_error_message').remove();
		
		if(payFrequency === 'Annual'){
			var annualRate = parseFloat(perPaySal) * 1;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Monthly'){
			var annualRate = parseFloat(perPaySal) * 12;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Bi-Weekly'){
			var annualRate = parseFloat(perPaySal) * 26;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		if(payFrequency === 'Weekly'){
			var annualRate = parseFloat(perPaySal) * 52;
			$("#annualSal").val(annualRate.toFixed(2));
		}
		
// 		if(hourlyRate != null && hourlyRate != '' && hourlyRate != 'undefined'){
			var hourlyrate = parseFloat(perPaySal) / standardHours;
			$("#hourlyRate").val(hourlyrate.toFixed(2));
// 		}
	}
	
	
}
</script>

<script>
function calculateHourlyRateSal(){
	
	var payFrequency = $("#payFrequency").val();
	var perPaySal = $("#perPaySal").val();
	var hourlyRate = $("#hourlyRate").val();
	var standardHours = $("#standardHours").val();
	var annualSal = $("#annualSal").val();
	
	if(hourlyRate != null && hourlyRate != '' && hourlyRate != 'undefined'){
		$('#AnnualSal').find('p.jquery_form_error_message').remove(); 
		$('#PerPaySal').find('p.jquery_form_error_message').remove(); 
		 $('#HourlyRate').find('p.jquery_form_error_message').remove(); 
		var perPaySal = parseFloat(hourlyRate) * standardHours;
		$("#perPaySal").val(perPaySal.toFixed(2));
		calculatePayPerSal();
	}
}
</script>




<script>
  function getPayItemList(){
	    var associateEmpType = $("#empType").val();
		$.get(sourceleadUrl+"/getPayItemList?"+tokenParamName+"="+tokenValue+"&associateEmpType="+associateEmpType, function(response) {
			  if (response.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var result = "";
				var allCheck =response.data.length;
				result = result +'<table class="table">';
				result = result +'<thead>';
				result = result +'<tr>';
				result = result +'<th>';
				result = result +'	<div class="checkbox-new">';
				result = result +'	<input id="all" class="checkbox-custom" name="paidComponent" type="checkbox" onclick="selectAllCheckBox('+allCheck+');">';
				result = result +'	<label for="all" class="checkbox-custom-label">';
				result = result +'</label>';
				result = result +'</div>';
				result = result +'	</th>';
				result = result +'	<th>Pay Items</th>';
				result = result +'	<th>Schedule</th>';
				result = result +'	<th>Override</th>';
				result = result +'	<th>Effective Date</th>';
				result = result +'	<th>End Date</th>';
				result = result +'<th>Amount</th>';
				result = result +'	<th>Action</th>';
				result = result +'	</tr>';
				result = result +'</thead>';
         	
				result = result +'<tbody>';
				if(response !=null && response !=''){
					 for (var i=0; i<response.data.length; i++)
						{
			        		 var opt = response.data[i];
			        		result = result +' <tr id="'+i+'">';
			        		result = result +' <td>';
			        		result = result +' 	<div class="checkbox-new">';
			        		result = result +' 	<input class="checkbox-custom" id="'+i+'payItemCheck" name="associatePayItemDto['+i+'].payItemCheck" type="checkbox" onclick="selectCheckBox('+i+');">';
			        		result = result +' 	<label for="'+i+'payItemCheck" class="checkbox-custom-label"></label>';
			        		result = result +' </div>';
			        		result = result +' <input type="hidden" id="'+i+'payItemId" name="associatePayItemDto['+i+'].payItemId" value="'+opt.id+'">';
			        		result = result +' </td>';
			        		result = result +' <td>';
			        		/* result = result +' <div class="tblminwidth-100 text-left" id="'+i+'payItemName" name="associatePayItemDto['+i+'].payItemName" >'+opt.payItemName+'</div>'; */
			        		result = result +' <input type="text" class="form-control tabField" id="'+i+'payItemName" name="associatePayItemDto['+i+'].payItemName" value=\''+opt.payItemName+'\'>';
			        		result = result +' </td>';
			        		result = result +' <td>';
			        		if(opt.paySchedule != '' && opt.paySchedule != 'undefined' && opt.paySchedule != null){
			        			result = result +' <input type="text" class="form-control tabField" id="'+i+'payItemSchedule" name="associatePayItemDto['+i+'].payItemSchedule" value='+opt.paySchedule+'>';
			        		}else{
			        			result = result +' <div class="tblminwidth-100 text-left">--</div>';
			        		}
			        		
			        		result = result +' </td>';
			        		result = result +'<td>';
			        		result = result +'<div class="tblminwidth-125">';
			        		result = result +'	<select class="sel" id="'+i+'overRide" name="associatePayItemDto['+i+'].overRide">';
			        		result = result +'	<option value="">--Select--</option>';
			        		result = result +'	<option value="YES">Yes</option>';
			        		result = result +'	<option value="NO">No</option>';
			        		result = result +'	</select>';
			        		result = result +'</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'	<div class="tblminwidth-50">';
							result = result +'		<input class="form-control calendar" placeholder="Effective Date" field-name="Effective Date" id="'+i+'effectiveDate" name="associatePayItemDto['+i+'].effectiveDate" type="text" readonly="readonly">';
							result = result +'	</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'	<div class="tblminwidth-50">';
							result = result +'		<input class="form-control calendar" placeholder="End Date" id="'+i+'endDate" name="associatePayItemDto['+i+'].endDate" type="text" readonly="readonly">';
							result = result +'	</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'<div class="tblminwidth-50">';
							result = result +'	<input class="form-control" id="'+i+'payItemAmount" name="associatePayItemDto['+i+'].payItemAmount"  type="text">';
							result = result +'</div>';
							result = result +'</td>';
							result = result +'<td>';
							result = result +'	<ul class="list-inline list-unstyled tblminwidth-50 mt5">';
							result = result +'		<li><a href="javascript:void(0)" title="Save" class="anchor-black"><i class="fa fa-floppy-o"></i></a></li>';
							result = result +'		<li><a href="javascript:void(0)" title="Reset" class="anchor-black"><i class="fa fa-refresh"></i></a></li>';
							result = result +'	</ul>';
							result = result +'</td>';
							result = result +'</tr>';
							 
						}
					 
				}
				result = result +'</tbody>';
				result = result +'</table>';
				result = result +'</tbody>';
				result = result +'</table>';
				$("#payItemDiv").empty();
				$("#payItemDiv").append(result);
				
				
				if(response !=null && response !=''){
				var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
				var finalDateFormat = getJSDateFormat(locationDate);
				 for (var i=0; i<response.data.length; i++)
					{
					  $("#"+i+"effectiveDate").datepicker({
						 dateFormat: finalDateFormat,
						 changeMonth: true,
						  changeYear: true,
						  onSelect : function(selected) {
							  var dt = $(this).datepicker('getDate', '+1d');
							  dt.setDate(dt.getDate() +1);
							  var id = $(this).closest('tr').attr("id");
							  $("#"+id+"endDate").datepicker("option", "minDate", dt);
						  }
						 });
						 $("#"+i+"endDate").datepicker({
							 dateFormat: finalDateFormat,
							 changeMonth: true,
						  	  changeYear: true,
						 });  
						 
						}
				 
				} 
				 
         }else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			} 
		}); 
  }
  
  </script>
  
  
 <script type="text/javascript">
 function saveAssocioateSal() {
	 if($("#allowEmployeeToFill").is(':checked')){
		 $("#save_associate_bank_data_button").val("Proceed To Next Step");
			$(".infoMask").show();
		}else{
			$(".infoMask").hide();
			$("#save_associate_bank_data_button").val("Save & Continue");
		}
	 var userId = $("#associateId").val();
	 var empType = $("#empType").val(); 

	 if ($('#associate_salary_form').validate(false, validationSettings)){ 
			   var formData = $($('#associate_salary_form')[0]).serialize();
				$("#saveAssocioateSal").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#saveAssocioateSal" ).addClass('button').val('Processing..');
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/addAssociateSal?"+tokenParamName+"="+tokenValue+"&userId="+userId,
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#saveAssocioateSal").removeAttr("disabled");
			                    $("#saveAssocioateSal").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
								var associateBanking = document.getElementById("associateBanking");
								document.getElementById("associateBanking").removeAttribute("class"); 
								associateBanking.href="#tab-2"; 
								$("#associateBanking").click();
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#saveAssocioateSal").removeAttr("disabled");
			                    $("#saveAssocioateSal").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
						
					},
				});
		
	        return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#associate_salary_form', function() {
		$("#associate_salary_form").showHelpOnFocus();
		$("#associate_salary_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>


<script>
function shiftToAssociateTab(){
	var associates = document.getElementById("associates");
	document.getElementById("associates").removeAttribute("class"); 
	associates.href="#tab-1"; 
	$("#loading").removeAttr('style');
	$("#create_user" ).addClass('button').val('Save & Continue');
    $("#create_user").removeAttr("disabled");
    $("#associates").click();
}
</script>

<script>
function selectCheckBox(i){
	var isChecked = $("#"+i+"payItemCheck").is(':checked');
	if(isChecked){
		$("#"+i+"effectiveDate").removeAttr('data-validation-optional');
		$("#"+i+"effectiveDate").attr('data-validation', "required");
	}else{
		$("#"+i+"effectiveDate").removeAttr('data-validation');
		$("#"+i+"effectiveDate").attr('data-validation-optional', "true");
	}
	$("#"+i+"payItemCheck").val(isChecked);
}
</script>  

<script>
function selectAllCheckBox(allCheck){
	var isChecked = $("#all").is(':checked');
	for(var j=0;j<allCheck;j++){
	if(isChecked){
		$("#"+j+"effectiveDate").removeAttr('data-validation-optional');
		$("#"+j+"effectiveDate").attr('data-validation', "required");
		$("#"+j+"payItemCheck").prop("checked","checked");
		
	}else{
		$("#"+j+"effectiveDate").removeAttr('data-validation');
		$("#"+j+"effectiveDate").attr('data-validation-optional', "true");
		$("#"+j+"payItemCheck").removeProp("checked");
	}
}
}
</script> 

<script>
function skipSalaryJsp(){
	if($("#allowEmployeeToFill").is(':checked')){
		$("#save_associate_bank_data_button").val("Proceed To Next Step");
		$(".infoMask").show();
	}else{
		$(".infoMask").hide();
		$("#save_associate_bank_data_button").val("Save & Continue");
	}
	var associateBanking = document.getElementById("associateBanking");
	document.getElementById("associateBanking").removeAttribute("class"); 
	associateBanking.href="#tab-2"; 
	$("#associateBanking").click();
}
</script>

<script>
function getPayGroups(){
	var payFrequency = $("#payFrequency").val();
	var userId = $("#associateId").val();
		 $.ajax({
				type : "GET",
				url : sourceleadUrl+"/getPayGroupsByPayFrequency?"+tokenParamName+"="+tokenValue+"&payFrequency="+payFrequency+"&userId="+userId,
				success : function(result) {
				var output = "";
				output = output + "<option value=''>Select Group</option>";
				for (var i=0; i<result.length; i++)
				{
					var opt = result[i];
					output = output + "<option value='" +opt.groupUUID+ "'>"+opt.groupName+"</option>";
				}
				$("#payGroupId").empty();
				$("#payGroupId").append(output);
				$('#payGroupId').trigger("chosen:updated");
					
				}
		 });
}
</script>
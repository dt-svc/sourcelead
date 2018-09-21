<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	var deductionType = "${payrollDeduction.payrollDeductionType }";
    findAllDeductionType(deductionType);
});

</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
  
  
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Deduction</h2>
      </div>
      <div class="success_pop" id="selector2" style="display: none;">
   <i class="fa fa-check-circle"></i><span id="addDeductionMsgDiv"></span>
 </div>
      <!-- <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Add Deduction</div> -->
       <div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo;Add Deduction
		</div>
    </section>
    <div id="addDeductionLoading"></div>
    <form:form id="add_deduction_form" name="add_deduction_form" method="post">
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix form-horizontal minHeight_225">
        <div class="clearfix row">
            <div class="form-group col-md-5">
              <label class="col-md-12 paddingRight">Deduction Type<span class="text-danger paddingLeft">&nbsp;*</span></label>
				<div id="deductionNameDive">
				<div class="col-md-12">
					<select class="form-control chosen-select" id="deductionType" name="payrollDeductionType" data-validation="required" onchange="validateDeductionType();" field-name="Deduction Type">
					</select>
					<input type="hidden" value="${payrollDeduction.deductionUUID }" id="deductionUUID" name="deductionUUID"/>
				</div>
				</div>
            </div>
            <div class="form-group col-md-5">
              <label class="col-md-12">Method<span class="text-danger paddingLeft">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control sel" id="payrollDeductionMethod" name="payrollDeductionMethod" data-validation="required" onChange="validateDeductionMethodType();" field-name="Method">
					    <option value = "">Select Method</option>
						 <option ${payrollDeduction.payrollDeductionMethod == 'Fixed' ? 'selected' : ''} value = "Fixed">Fixed</option>
						  <option ${payrollDeduction.payrollDeductionMethod == 'Percent' ? 'selected' : ''} value = "Percent">Percent</option>
					</select>
              </div>
            </div>
            
             <div class="form-group col-md-5">
              <label class="col-md-12">Amount per pay<span class="text-danger paddingLeft">&nbsp;*</span></label>
              <div class="col-md-12" id="deductionAmountDiv">
                <input type="text" class="form-control" value="${payrollDeduction.deductionAmount }" id="deductionAmount" name="deductionAmount" data-validation="required validate_float" onblur="validateDeductionMethodType();" field-name="Amount">
              </div>
            </div>
             <div class="form-group col-md-5">
              <label class="col-md-12">Scheduled<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control sel" id="payrollScheduled" name="payrollScheduled" data-validation="required" field-name="Scheduled">
					    <option value = "">Select Scheduled</option>
						 <option ${payrollDeduction.payrollScheduled == 'EVERY_PAY' ? 'selected' : ''} value = "EVERY_PAY">Every Pay</option>
						  <option ${payrollDeduction.payrollScheduled == 'MONTHLY' ? 'selected' : ''} value = "MONTHLY">Monthly</option>
						 <option ${payrollDeduction.payrollScheduled == 'QUARTERLY' ? 'selected' : ''} value = "QUARTERLY">Quarterly</option>
						 <option ${payrollDeduction.payrollScheduled == 'SEMI_ANNUALLY' ? 'selected' : ''} value = "SEMI_ANNUALLY">Semi-Annually</option>
						 <option ${payrollDeduction.payrollScheduled == 'ANNUALLY' ? 'selected' : ''} value = "ANNUALLY">Annually</option>
					</select>
              </div>
            </div>
            
            <div class="form-group col-md-5">
              <label class="col-md-12">Description<span class="text-danger paddingLeft">&nbsp;*</span></label>
              <div class="col-md-12">
               <textarea rows="2" class="form-control" id="description" name="description" data-validation="validate_length length1-250">${payrollDeduction.description }</textarea>
              </div>
            </div>
      </div>
</div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Save" class="btn btn-success btnSuccess" id="save_deductionType" type="button" onclick="saveDeduction();">
            <input value="Cancel" class="btn btn-defualt ml10" onclick="window.location='${appPath}/payrollConfiguration'" type="button">
          </div>
        </div>
      </div>      
		
      <!-- Content Area Ends--> 
    </div>
    </form:form>
  </section>
  

<script>
function findAllDeductionType(deductionType){
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/findAllLookUpDeductionTypes?"+tokenParamName+"="+tokenValue+"",
		success :function(data) {
		var json = data.data;
		var output = "";
		output = output + "<option value=''>Select Deduction Type</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		 if(deductionType == opt.payrollDeductionTypeName){
			var selected = 'selected';
			output = output + "<option value='" +opt.payrollDeductionTypeName+ "'"+selected+">"+opt.payrollDeductionTypeName+"</option>";
		}else{ 
			output = output + "<option value='" +opt.payrollDeductionTypeName+ "'>"+opt.payrollDeductionTypeName+"</option>";
	}
		
		}
		$("#deductionType").empty();
		$("#deductionType").append(output);
		$('#deductionType').trigger("chosen:updated");
	},
	});
}

</script>

<script>
function validateDeductionMethodType() {
	$('#deductionAmountDiv').parent().find('p.jquery_form_error_message').remove(); 
	 var selectedMethod = document.getElementById("payrollDeductionMethod").value;
	 var enteredValue = document.getElementById("deductionAmount").value;
	 var nameErrorDiv = $('#deductionNameDive p').length > 0;
	 if(selectedMethod =='Percent'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(enteredValue.match(floatNum)){
			  if(!nameErrorDiv){
				  $("#save_deductionType").removeAttr("disabled", "disabled");
			  }
			  return true;
		  }		  
		 else{
				$("#deductionAmountDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Percentage</span>");
				$("#save_deductionType").attr("disabled", "disabled");
				return false; 
		 	 }
	  }if(selectedMethod =='Fixed'){
		  var floatNum = /^(\d{0,10}(\.\d{0,10})?|100)$/
			  if(enteredValue.match(floatNum)){
				  if(!nameErrorDiv){
					  $("#save_deductionType").removeAttr("disabled", "disabled");
				  }
				  return true;
			  }		  
			 else{
					$("#deductionAmountDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Percentage</span>");
					$("#save_deductionType").attr("disabled", "disabled");
					return false; 
			 	 }
// 		  if(!nameErrorDiv)
// 			  $("#save_deductionType").removeAttr("disabled", "disabled");
		  }		  
}
</script>

<script>
function saveDeduction(){
	if ($('#add_deduction_form').validate(false, validationSettings)){  
 	 	    var formData = getFormData("add_deduction_form");
		        	  $("#addDeductionLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.post(sourceleadUrl+"/savePayrollDeduction?"+tokenParamName+"="+tokenValue, formData, function(data) {
		        		  $("#addDeductionLoading").removeAttr("style");
		        		  location.replace(sourceleadUrl+ "/payrollDeductionList");
		        		  
		        		    $("#selector2").removeAttr("style");
			     			 $("#selector2").css({ display: "block" });
			     			 $("#addDeductionMsgDiv").html("Added Successfully");
			     			addDeductionMsgFadein();
		  			   }); 
		    	         return true; 
         }else{
	    		return false;
	    	  } 
}

$('body').on('blur', '#add_deduction_form', function() {
	$("#add_deduction_form").showHelpOnFocus();
	$("#add_deduction_form").validateOnBlur(false, validationSettings);  
}); 
</script>

<script>
	function addDeductionMsgFadein() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script>

<script>
function validateDeductionType(){
	var payrollDeductionType = $("#deductionType").val();
	$('#deductionNameDive').find('p.jquery_form_error_message').remove();
	var isDisabled = $('#save_contributionType').is('[disabled=disabled]');
	if (payrollDeductionType != null && payrollDeductionType != '') {
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/isValidDeductionType?"+tokenParamName+"="+tokenValue+"&payrollDeductionType="+payrollDeductionType,
			success : function(data) {
				if (data == true) {
// 					$("#save_deductionType").removeAttr("disabled");
			        $("#save_deductionType").attr("disabled", "disabled");
					$("#deductionNameDive").append( "<p class='jquery_form_error_message'>" + payrollDeductionType + " is Existed</span>");
					$('#deductionNameDive').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					if(!isDisabled)
						$("#save_deductionType").removeAttr("disabled");
					$('#deductionNameDive').find('p.jquery_form_error_message').remove();
// 					$("#save_deductionType").click(function() {
// 					});
					
				}
				
			},

		});
	}
}

</script>

<jsp:include page="../masterFooter.jsp" />
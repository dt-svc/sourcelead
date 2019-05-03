<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript">
	$(window).load(function() {
		getYearsDropDownCount();
		getMonthsDropDownCount();
		
		if('${loanAndAdv.applicableFor}'){
			applicableType();
		}
	});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Loan & Advances</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a>  &raquo; Loan & Advances</div>
    </section>
    
    <div class="content-area content-new clearfix backgroundWhite">
   <div id="loading"></div> 
    <form:form class="login-form clearfix"  id="createloanAndAdv_form"  name="createloanAndAdv_form" method="post" > 
      <div class="row minHeightcol5">
      	<div class="clearfix col-md-10">
      		
      		<div class="col-md-5 clearfix mt10">
              <label class="clearfix">Type<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
                 <div class="clearfix dropdown-select">
							<select name="type" id="loanAdvType" data-validation="required"  field-name="Type" onchange="validateName();">
							  <option selected value=''>Select Type</option>
							  <option  ${loanAndAdv.type  == 'Loan' ? 'selected' : ''}>Loan</option>
							  <option  ${loanAndAdv.type  == 'Advance' ? 'selected' : ''}>Advance</option>
							</select>
						</div>
              </div>
            </div>
            
            <div class="col-md-5 mt10" id="loandAndAdvNameDiv">
              <label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
                 <input type="text" class="form-control"  name="name" value="${loanAndAdv.name}" onblur="validateName();" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250" field-name="Name" id="loanAdvName">
              </div>
            </div>
            
            
            
            <div class="col-md-5">
              <label class="clearfix">Amount</label>
              <div class="clearfix">
               <div class="row">
				   <div class="col-md-6" id="minAmountDiv">
				   	  <input type="text" class="form-control" value="${loanAndAdv.minAmount}" name="minAmount" data-validation-optional="true" data-validation="validate_Space validate_float validate_length length1-8" onblur="validateMinAmount()" field-name="Min Amount" id="minAmount" placeholder="Min">
				   </div>   	
				   <div class="col-md-6" id="maxAmountDiv">
				   	<input type="text" class="form-control" value="${loanAndAdv.maxAmount}" name="maxAmount" data-validation-optional="true" data-validation="validate_Space validate_float validate_length length1-8" onblur="validateMaxAmount()" field-name="Max Amount" id="maxAmount" placeholder="Max">
				   </div>   	
               </div>
              <div class="row" id="rateValidateId">
                  <input type="hidden" name="rateValidation" id="rateValidation">
              </div>
              </div>
            </div>
            
            <div class="col-md-5">
              <label class="clearfix">Interest Rate (%)<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
                 <input type="text" class="form-control"  name="interestRate" value="${loanAndAdv.interestRate}" onblur="validateInterestRate();" data-validation="required validate_Space validate_float" field-name="Interest Rate" id="interestRate">
              </div>
              <div class="row mxrateerror" id="interestRateValidateId">
                  <input type="hidden" name="interestRateValidation" id="interestRateValidation">
              </div>
            </div>
            
            <c:if test="${SourceLeadWeb_loginCountryName != 'United States'}">
	            <div class="col-md-5 clearfix mt10">
	              <label class="clearfix">Pay Head</label>
	              <div class="clearfix">
	                 <div class="clearfix dropdown-select">
								<select  class="form-control select" name="payHeadId" id="payrollPayHead" data-validation-optional="true"  field-name="Pay Head">
		                			<c:forEach var="list" items="${payheadList}">
		                				<c:if test="${list.shortCode == 'L&D'}">
		                					<option value="${list.id}" ${list.shortCode == 'L&D' ? 'selected' : ''} >${list.name}</option>
		                				</c:if>
									</c:forEach>
								</select>
							</div>
	              </div>
            </div>
            
            </c:if>
            
            <div class="col-md-5 clearfix mt10">
				<label class="clearfix">Applicable For</label>
				<div class="clearfix">
				<div class="clearfix">
						<span class="radioStyles mr">
						<input id="applicableForAll" name="applicableFor" ${loanAndAdv.applicableFor  == 'All' ? 'checked' : ''} value="All" type="radio" onClick="applicableType();">
						<label for="applicableForAll">All</label>
						</span>
					<span class="radioStyles">
						<input id="experienceRadio" name="applicableFor" ${loanAndAdv.applicableFor  == 'Experienced' ? 'checked' : ''} value="Experienced" type="radio" onClick="applicableType();">
						<label for="experienceRadio">Experienced</label>
					</span>
				</div>
				</div>
			</div>
			
			
			<div class="col-md-5" style="display: none" id="experienceDiv">
              <label class="clearfix">Experience<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
               <div class="row">
				   <div class="col-md-6" id="experienceYearsDiv">
				   	 <div class="clearfix dropdown-select">
					  <select name="experienceYears" id="experienceYears" data-validation-optional="true" field-name="Years">
				   	  <option selected value=''>Select Years</option>
				   	  </select>
				     </div> 
				   </div>  	
				   <div class="col-md-6" id="experienceMonthsDiv">
				   	 <div class="clearfix dropdown-select">
					  <select name="experienceMonths" id="experienceMonths" data-validation-optional="true" field-name="Months">
				   	  <option selected value=''>Select Months</option>
				   	  </select>
				     </div> 
				   </div>   	
               </div>
              </div>
              
            </div>
            
         
               
            
        <div class="clearfix"></div>
        <div class="col-md-10">
              <label class="clearfix">Description</label>
              <div class="clearfix">
				  <textarea class="form-control" data-validation="validate_space"  field-name="Description" id="description" name="description" rows="5">${loanAndAdv.description}</textarea>
              </div>
            </div>
            <div class="clearfix"></div>
      </div>
      	</div>
      <div class="form-footer clearfix mt">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="submit" value="Save" id="loan_adv"  class="btn btn-primary"> 
						<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/payrollLoanAndAdvancesList'/>'" class="btn btn-default ml">
					</div>
				</div>
			</div>
      </form:form>
      </div>
      
</section>

<script>
function validateName(){
	var loanAdvType = $("#loanAdvType").val();
	var loanAdvName = $("#loanAdvName").val();
	if(loanAdvType && loanAdvName){
		var uuid = '';
		if('${uuid}' != null && '${uuid}' != '' && '${uuid}' != 'undefined'){
			uuid = '${uuid}'
		}
		 $("#loan_adv").attr("disabled", "disabled");
		$('#loandAndAdvNameDiv').find('p.jquery_form_error_message').remove();
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateLoandAndAdvanceName?loanAdvType="+loanAdvType+"&loanAdvName="+loanAdvName+"&uuid="+uuid, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#loandAndAdvNameDiv').find('p.jquery_form_error_message').remove();
						  $("input#loanAdvName").after("<p class='jquery_form_error_message'>"+loanAdvName+" is Already Existed</span>");
						  document.getElementById("loanAdvName").setAttribute('record-exist','yes');
						  document.getElementById("loanAdvName").setAttribute('record-exist-errorMsg',loanAdvName+' is Already Existed'); 
						  $("#loading").removeAttr('style');
		        	}
		        	else{
		        		 $('input#loanAdvName').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#loandAndAdvNameDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#loading").removeAttr('style');
		        		 $("#loan_adv").removeAttr("disabled");
		        	}
		        },
		    });
	}
	
}
</script>

<script>
function applicableType(){
	 var applicableForRadio = $('input[name=applicableFor]');
     var applicableForType = applicableForRadio.filter(':checked').val();
     if(applicableForType == 'Experienced'){
    	 $('#experienceYears').removeAttr('data-validation-optional');
 		 $('#experienceYears').attr("data-validation","required");
 		 
 		 $('#experienceMonths').removeAttr('data-validation-optional');
 		 $('#experienceMonths').attr("data-validation","required");
 		 document.getElementById("experienceDiv").style.display="";
 		 
 		if('${loanAndAdv.experienceYears}'){
 			$('#experienceYears').val('${loanAndAdv.experienceYears}');
 		}
 		
 		if('${loanAndAdv.experienceMonths}'){
 			$('#experienceMonths').val('${loanAndAdv.experienceMonths}');
 		}
 		
     }else{
    	 $('#experienceYears').val('');
    	 $('#experienceMonths').val('');
    	 
    	 $('#experienceYears').attr('data-validation-optional');
 		 $('#experienceYears').removeAttr("data-validation","required");
 		 
 		 $('#experienceMonths').attr('data-validation-optional');
 		 $('#experienceMonths').removeAttr("data-validation","required"); 
 		 
 		 $('#experienceYearsDiv').find('p.jquery_form_error_message').remove(); 
 		 $('#experienceMonthsDiv').find('p.jquery_form_error_message').remove(); 
 		 document.getElementById("experienceDiv").style.display="none";
     }
}

function getYearsDropDownCount(){
	var i = "";
	var experienceYears = '${loanAndAdv.experienceYears}';
	result = "";
	result = result
	+ '("<option selected value="">Select Years</option>")';
	for (var i = 0; i <= 50; i++) {
		if(experienceYears){
			var selected = '';
			if(i == experienceYears){
				selected = 'selected';
			}
			result = result + '("<option '+selected+' value='+i+'>' + i + '</option>")';
		}else{
			result = result + '("<option value='+i+'>' + i + '</option>")';
		}
		
	}
	result = result + '</select>';
	$('#experienceYears').empty();
	$('#experienceYears').append(result);
}

function getMonthsDropDownCount(){
	var i = "";
	result = "";
	var experienceMonths = '${loanAndAdv.experienceMonths}';
	result = result
	+ '("<option selected value="">Select Months</option>")';
	for (var i = 0; i <= 12; i++) {
		if(experienceMonths){
			var selected = '';
			if(i == experienceMonths){
				selected = 'selected';
			}
			result = result + '("<option '+selected+' value='+i+'>' + i + '</option>")';
		}else{
			result = result + '("<option value='+i+'>' + i + '</option>")';
		}
		
	}
	result = result + '</select>';
	$('#experienceMonths').empty();
	$('#experienceMonths').append(result);
}

function validateMinAmount(){
	 var minRate = ($('#minAmount').val());
	 if(minRate){
		 $('#minAmountDiv').find('p.jquery_form_error_message').remove(); 
		 $('#maxAmount').removeAttr('data-validation-optional');
 		 $('#maxAmount').attr("data-validation","required"); 
 		 minMaxRate();
	 }else{
		 $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
		 $('#maxAmount').attr('data-validation-optional',"true");
 		 $('#maxAmount').removeAttr("data-validation"); 
	 }
}

function validateMaxAmount(){
	 var maxRate = ($('#maxAmount').val());
	 if(maxRate){
		 $('#maxAmountDiv').find('p.jquery_form_error_message').remove(); 
		 $('#minAmount').removeAttr('data-validation-optional');
		 $('#minAmount').attr("data-validation","required"); 
		 minMaxRate();
	 }else{
		 $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
		 $('#minAmount').attr('data-validation-optional',"true");
		 $('#minAmount').removeAttr("data-validation"); 
	 }
}

function minMaxRate(){
    var maxRate = ($('#maxAmount').val());
    var minRate = ($('#minAmount').val());
    
    if (parseFloat(maxRate) < parseFloat(minRate)){
        $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
		$('input#rateValidation').attr('class','error signin_fild');
		$("input#rateValidation").after("<p class='jquery_form_error_message'>Max Amount should be greater than Min Rate</p>");
		$('input#rateValidation').attr('record-exist','yes');
		$('input#rateValidation').attr('record-exist-errorMsg','Max Amount should be greater than Min Rate'); 
    }  else{
    	$('#rateValidateId').find('p.jquery_form_error_message').remove(); 
    	$('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#rateValidateId').find('p.jquery_form_error_message').remove();
    }
}

function validateInterestRate(){
	var interestRate = $('#interestRate').val();
	
	 if(interestRate > 100 || interestRate < 0){
		 if(interestRate < 0){
			    $('#interestRateValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#interestRateValidation').attr('class','error signin_fild');
				$("input#interestRateValidation").after("<p class='jquery_form_error_message'>Interest Rate should not in negitive</p>");
				$('input#interestRateValidation').attr('record-exist','yes');
				$('input#interestRateValidation').attr('record-exist-errorMsg','Min Rate should not in negitive'); 
		 }else{
			 $('#interestRateValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#interestRateValidation').attr('class','error signin_fild');
				$("input#interestRateValidation").after("<p class='jquery_form_error_message'>Interest Rate should not more than 100</p>");
				$('input#interestRateValidation').attr('record-exist','yes');
				$('input#interestRateValidation').attr('record-exist-errorMsg','Min Rate should not more than 100'); 
		 }
	 }else{
		 
	     $('#interestRateValidateId').find('p.jquery_form_error_message').remove(); 
    	 $('input#interestRateValidation').removeAttr( "class record-exist record-exist-errormsg");
		 $('#interestRateValidateId').find('p.jquery_form_error_message').remove();
	 }
}


/* function getPayHeadList(){
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getPayheadList?${_csrf.parameterName}=${_csrf.token}",
	        success: function(json) {
	        	var output = "";
	        	output = output + "<option value=''>Select Pay Head</option>"; 
	        	for (var i=0; i<json.response.length; i++)
				{
		        		var opt = json.response[i];
		        		var payHeadId = '${loanAndAdv.payHeadId}';
		        		
		        		if(payHeadId != null && payHeadId != 'undefined' && payHeadId != ''){
		        			
		        			var selected = '';
		        			if(payHeadId == opt.id){
		        				selected = 'selected';
		        			}
		        			
		        			output = output + "<option "+selected+" value='" +opt.id+ "'>"+opt.name+"</option>";
		        		}else{
		        			output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
		        		} 
		        		
				}
	        	$("#payrollPayHead").empty();
		        $("#payrollPayHead").append(output);
		        $('#payrollPayHead').trigger("chosen:updated");
	        },
	    });
} */

</script>

<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#createloanAndAdv_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		var formData = getFormData("createloanAndAdv_form");
         		var sourceleadUrl ='${sourceleadUrl}';
         		var uuid = '${uuid}';
         		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 	
          		  $.ajax({
          	      type: "POST",
          	    	data: formData,
                	  url: sourceleadUrl+"/savePayrollLoanAndAdvance?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,
                  	success: function(result) {
                  		if (result && result.response === "INVALID_DATA") {
                  			$("#loading").removeAttr('style');
                            showError(result.responseMessage);
                        }else if (result.response === "AWKWARD") {
    		        	    location.href = "/errorPage";
    		        	} else if (result.response === "SUCCESS") {
    		        		 location.replace(sourceleadUrl + "/payrollLoanAndAdvancesList");
    		        	} else if (result.indexOf('Sign In') !== -1) {
    		        		location.replace(sourceleadUrl);
    		        	} else {
    		        		$("#loading").removeAttr('style');
    		        	    showError("Operation failed, please try again");
    		        	}
			        },  
               });    
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#createbonus_form', function() {
     		$("#createloanAndAdv_form").showHelpOnFocus();
     		$("#createloanAndAdv_form").validateOnBlur(false, validationSettings);
     	});
     
	</script>


<jsp:include page="../masterFooter.jsp" />
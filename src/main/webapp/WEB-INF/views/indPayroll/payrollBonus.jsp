<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Bonus Setup</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a>  &raquo; <a href="${appPath}/payrollBonusList">Bonus List</a> &raquo; Bonus Setup</div>
    </section>
  <div class="content-area content-new clearfix">
   <div id="loading"></div> 
    <form:form class="login-form clearfix"  id="createbonus_form"  name="createbonus_form" method="post" > 
      <div class="row minHeightcol5">
      	<div class="clearfix col-md-10">
      		
      		<div class="col-md-5 clearfix mt10">
              <label class="clearfix">Bonus Payment Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix" id="payDateDiv">
                 <input type="text" value="${payrollBonus.paymentDateStr}" name="paymentDate" class="form-control calendar" id="paymentDate" data-validation="required" field-name="Bonus Payment Date" readonly="readonly">
              </div>
            </div>
            
            <div class="col-md-5  mt10">
              <label class="clearfix">Bonus Percentage (%)<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
               <div class="row">
				   <div class="col-md-6">
				   	  <input type="text" class="form-control" value="${payrollBonus.minPercentage}"  name="minPercentage" data-validation="required validate_Space validate_float" onblur="validateMinRate()" field-name="Min Percentage" id="minPercentage" placeholder="Min %" >
				   </div>   	
				   <div class="col-md-6">
				   	<input type="text" class="form-control" value="${payrollBonus.maxPercentage}" name="maxPercentage" data-validation="required validate_Space validate_float" onblur="validateMaxRate()" field-name="Max Percentage" id="maxPercentage" placeholder="Max %" >
				   </div>   	
               </div>
              </div>
              
              <div class="row mxrateerror" id="rateValidateId">
                  <input type="hidden" name="rateValidation" id="rateValidation">
              </div>
              
            </div>
            
            <div class="col-md-5">
             <div class="row">
				<div class="col-md-6">
					<label class="clearfix">From Month</label>
						<div class="clearfix dropdown-select">
							<select name="fromMonth" id="fromMonth" data-validation-optional="true" onchange="validateToMonth();" field-name="From Month">
							  <option selected value=''>Select Month</option>
							  <option  ${payrollBonus.fromMonth  == 'January' ? 'selected' : ''}>January</option>
							  <option  ${payrollBonus.fromMonth  == 'February' ? 'selected' : ''}>February</option>
							  <option  ${payrollBonus.fromMonth  == 'March' ? 'selected' : ''}>March</option>
							  <option  ${payrollBonus.fromMonth  == 'April' ? 'selected' : ''}>April</option>
							  <option  ${payrollBonus.fromMonth  == 'May' ? 'selected' : ''}>May</option>
							  <option  ${payrollBonus.fromMonth  == 'June' ? 'selected' : ''}>June</option>
							  <option  ${payrollBonus.fromMonth  == 'July' ? 'selected' : ''}>July</option>
							  <option  ${payrollBonus.fromMonth  == 'August' ? 'selected' : ''}>August</option>
							  <option  ${payrollBonus.fromMonth  == 'September' ? 'selected' : ''}>September</option>
							  <option  ${payrollBonus.fromMonth  == 'October' ? 'selected' : ''}>October</option>
							  <option  ${payrollBonus.fromMonth  == 'November' ? 'selected' : ''}>November</option>
							  <option  ${payrollBonus.fromMonth  == 'December' ? 'selected' : ''}>December</option>
							</select>
						</div>
				</div>
             	<div class="col-md-6">
					<label class="clearfix">To Month</label>
						<div class="clearfix dropdown-select">
							<select name="toMonth" id="toMonth" data-validation-optional="true" onchange="validateFromMonth()" field-name="To Month">
							  <option selected value=''>Select Month</option>
							  <option  ${payrollBonus.toMonth  == 'January' ? 'selected' : ''}>January</option>
							  <option  ${payrollBonus.toMonth  == 'February' ? 'selected' : ''}>February</option>
							  <option  ${payrollBonus.toMonth  == 'March' ? 'selected' : ''}>March</option>
							  <option  ${payrollBonus.toMonth  == 'April' ? 'selected' : ''}>April</option>
							  <option  ${payrollBonus.toMonth  == 'May' ? 'selected' : ''}>May</option>
							  <option  ${payrollBonus.toMonth  == 'June' ? 'selected' : ''}>June</option>
							  <option  ${payrollBonus.toMonth  == 'July' ? 'selected' : ''}>July</option>
							  <option  ${payrollBonus.toMonth  == 'August' ? 'selected' : ''}>August</option>
							  <option  ${payrollBonus.toMonth  == 'September' ? 'selected' : ''}>September</option>
							  <option  ${payrollBonus.toMonth  == 'October' ? 'selected' : ''}>October</option>
							  <option  ${payrollBonus.toMonth  == 'November' ? 'selected' : ''}>November</option>
							  <option  ${payrollBonus.toMonth  == 'December' ? 'selected' : ''}>December</option>
							</select>
						</div>
				</div>
             </div>
              <label class="clearfix"></label>
            </div>
            
            <div class="col-md-5">
              <label class="clearfix">Max Salary</label>
              <div class="clearfix">
               <div class="row">
				   <div class="col-md-6">
				   	   <input type="text" class="form-control" value="${payrollBonus.maxSalary}" name="maxSalary" id="maxSalary" field-name="Max Salary" onblur="validatePaymentFrequency()" data-validation-optional="true" data-validation="validate_Space validate_length length2-8 validate_float">
				   </div>   	
				   <div class="col-md-6">
						<div class="clearfix dropdown-select">
							<select name="paymentFrequency" id="paymentFrequency" data-validation-optional="true" onchange="validateMaxSalary()" field-name="Payment Frequency">
								<option selected value=''>Select Frequency</option>
		                        <option  ${payrollBonus.paymentFrequency  == 'Daily' ? 'selected' : ''}>Daily</option>
		                        <option  ${payrollBonus.paymentFrequency  == '>Weekly' ? 'selected' : ''}>Weekly</option>
		                        <option  ${payrollBonus.paymentFrequency  == 'Bi-Weekly' ? 'selected' : ''}>Bi-Weekly</option>
		                        <option  ${payrollBonus.paymentFrequency  == 'Semi-Monthly' ? 'selected' : ''}>Semi-Monthly</option>
		                        <option  ${payrollBonus.paymentFrequency  == 'Bi-Monthly' ? 'selected' : ''}>Bi-Monthly</option>
		                        <option  ${payrollBonus.paymentFrequency  == 'Monthly' ? 'selected' : ''}>Monthly</option>
		                        <option  ${payrollBonus.paymentFrequency  == 'Yearly' ? 'selected' : ''}> Yearly</option>
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
				  <textarea class="form-control" data-validation="validate_space"  field-name="Description" id="description" name="description" rows="5">${payrollBonus.description}</textarea>
              </div>
            </div>
            <div class="clearfix"></div>
      </div>
      	</div>
      <div class="form-footer clearfix mt">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="submit" value="Save" class="btn btn-primary"> 
						<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/payrollBonusList'/>'" class="btn btn-default ml">
					</div>
				</div>
			</div>
      </form:form>
      </div>
    
  </section>

<jsp:include page="../masterFooter.jsp" />



<script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormat = getJSDateFormat(locationDate);
	  $('#paymentDate').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	  	  onClose : function(selected) {
                removePayDateValidation();
			}
	  });
	  
  } );
</script>

<script>
function removePayDateValidation(){
	$('#payDateDiv').find('p.jquery_form_error_message').remove();
}
</script>

<script>

function validateMinRate(){
	 var minRate = ($('#minPercentage').val());
	 if(minRate > 100 || minRate < 0){
		 if(minRate < 0){
			    $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#rateValidation').attr('class','error signin_fild');
				$("input#rateValidation").after("<p class='jquery_form_error_message'>Min Rate should not in negitive</p>");
				$('input#rateValidation').attr('record-exist','yes');
				$('input#rateValidation').attr('record-exist-errorMsg','Min Rate should not in negitive'); 
		 }else{
			 $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#rateValidation').attr('class','error signin_fild');
				$("input#rateValidation").after("<p class='jquery_form_error_message'>Min Rate should not more than 100</p>");
				$('input#rateValidation').attr('record-exist','yes');
				$('input#rateValidation').attr('record-exist-errorMsg','Min Rate should not more than 100'); 
		 }
	 }else{
		 
	     $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
    	 $('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		 $('#rateValidateId').find('p.jquery_form_error_message').remove();
		 minMaxRate();
	 }
}


function validateMaxRate(){
	 var maxRate = ($('#maxPercentage').val());
	 if(maxRate > 100 || maxRate < 0){
		 if(maxRate < 0){
			    $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#rateValidation').attr('class','error signin_fild');
				$("input#rateValidation").after("<p class='jquery_form_error_message'>Max Rate should not in negitive</p>");
				$('input#rateValidation').attr('record-exist','yes');
				$('input#rateValidation').attr('record-exist-errorMsg','Max Rate should not in negitive'); 
		 }else{
			    $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
				$('input#rateValidation').attr('class','error signin_fild');
				$("input#rateValidation").after("<p class='jquery_form_error_message'>Max Rate should not more than 100</p>");
				$('input#rateValidation').attr('record-exist','yes');
				$('input#rateValidation').attr('record-exist-errorMsg','Max Rate should not more than 100'); 
		 }
	 }else{
	    $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
    	$('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#rateValidateId').find('p.jquery_form_error_message').remove();
		 minMaxRate();
	 }
}

function minMaxRate(){
    var maxRate = ($('#maxPercentage').val());
    var minRate = ($('#minPercentage').val());
    
    if (parseFloat(maxRate) <= parseFloat(minRate)){
        $('#rateValidateId').find('p.jquery_form_error_message').remove(); 
		$('input#rateValidation').attr('class','error signin_fild');
		$("input#rateValidation").after("<p class='jquery_form_error_message'>Max Rate should be greater than Min Rate</p>");
		$('input#rateValidation').attr('record-exist','yes');
		$('input#rateValidation').attr('record-exist-errorMsg','Max Rate should be greater than Min Rate'); 
    }  else{
    	$('#rateValidateId').find('p.jquery_form_error_message').remove(); 
    	$('input#rateValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#rateValidateId').find('p.jquery_form_error_message').remove();
    }
}


function  validateToMonth(){
	var fromMonth = ($('#fromMonth').val());
	if(fromMonth == null || fromMonth == '' || fromMonth == 'undefined'){
		$('#toMonth').val('')
		$('#toMonth').removeAttr('data-validation');
		$('#toMonth').attr("data-validation-optional","true");
		$('#fromMonth').removeAttr('data-validation');
		$('#fromMonth').attr("data-validation-optional","true");
	}else{
		$('#toMonth').removeAttr('data-validation-optional');
		$('#toMonth').attr("data-validation","required");
	}
}

function validateFromMonth(){
	var toMonth = ($('#toMonth').val());
	if(toMonth == null || toMonth == '' || toMonth == 'undefined'){
		$('#fromMonth').removeAttr('data-validation');
		$('#fromMonth').attr("data-validation-optional","true");
	}else{
		$('#fromMonth').removeAttr('data-validation-optional');
		$('#fromMonth').attr("data-validation","required");
	}
}

function validatePaymentFrequency(){
	var maxSalary = $('#maxSalary').val();
	if(maxSalary){
		$('#paymentFrequency').removeAttr('data-validation-optional');
		$('#paymentFrequency').attr("data-validation","required");
	}else{
		$('#paymentFrequency').val('');
		$('#paymentFrequency').removeAttr('data-validation');
		$('#paymentFrequency').attr("data-validation-optional","true");
	}
}

function validateMaxSalary(){
	var paymentFrequency = $('#paymentFrequency').val();
	if(paymentFrequency){
		var maxSalary = $('#maxSalary').val();
		if(maxSalary == '' || maxSalary == null || maxSalary == 'undefined'){
			$('#maxSalary').removeAttr('data-validation-optional');
			$('#maxSalary').attr("data-validation","required");
		}else{
			$('#maxSalary').removeAttr('data-validation');
			$('#maxSalary').attr("data-validation-optional","true");
		}
	}else{
		$('#maxSalary').removeAttr('data-validation');
		$('#maxSalary').attr("data-validation-optional","true");
	}
}

</script>


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#createbonus_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		var formData = getFormData("createbonus_form");
         		var sourceleadUrl ='${sourceleadUrl}';
         		var uuid = '${uuid}';
         		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 	
          		 $.ajax({
          	      type: "POST",
          	    	data: formData,
                	  url: sourceleadUrl+"/saveBonus?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,
                  	success: function(result) {
                  		if (result && result.response === "INVALID_DATA") {
                  			$("#loading").removeAttr('style');
                            showError(result.responseMessage);
                        }else if (result.response === "AWKWARD") {
                        	$("#loading").removeAttr('style');
    		        	    location.href = "/errorPage";
    		        	} else if (result.response === "SUCCESS") {
    		        		 location.replace(sourceleadUrl + "/payrollBonusList");
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
     		$("#createbonus_form").showHelpOnFocus();
     		$("#createbonus_form").validateOnBlur(false, validationSettings);
     	});
     
	</script>
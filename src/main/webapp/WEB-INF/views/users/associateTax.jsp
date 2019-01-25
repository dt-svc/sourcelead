 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  
  <script>
  $(document).ready(function(){
	   $("#localTaxMessageDiv").hide();
	});
  </script>
  
  <div id="loading"></div>
   <div id="tab-3" class="tabsMenu_tabs_inn clearfix">
            <div class="clearfix row minHeightcol5">
             <form:form id="associate_tax_form"  name="associate_tax_form" method="post">
              <div class="col-md-12 mb">
                <div class="clearfix row positionRelative">
                  <h4 class="col-md-12">Federal Tax</h4>
                  <div class="col-md-5">
                    <label>Filling Status<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <select class="sel" id="federaltaxFillingStatus" name="federaltaxFillingStatus" data-validation="required" field-name="Filling Status">
                        <option value="">--Select--</option>
                        <option value="Single">Single</option>
                        <option value="Married">Married</option>
                        <option value="Married_Single_Income">Married Single Income</option>
                        <option value="Married_Dual_Income">Married Dual Income</option>
                        <option value="Head_of_Household">Head of Household</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Allowances<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <input type="text" class="form-control" name="federaltaxAllowances" id="federaltaxAllowances" value="0" data-validation="required validate_Space validate_length length1-100 validate_int" field-name="Allowances">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Additional withholding<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <input type="text" class="form-control" name="federaltaxAdditionalWithHolding" id="federaltaxAdditionalWithHolding" value="0" data-validation="required validate_Space validate_length length1-100 validate_int" field-name="Additional with holding">
                    </div>
                  </div>
               <!--    <div class="clearfix"></div>
                  <h4 class="col-md-12">State Tax</h4>
                  <div class="col-md-5">
                    <label>Filling Status<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <select class="sel" name="stateTaxFillingStatus" id="stateTaxFillingStatus" data-validation="required" field-name="State Tax"> 
                        <option value="">--Select--</option>
                        <option value="Single">Single</option>
                        <option value="Married">Married</option>
                        <option value="Married_Single_Income">Married Single Income</option>
                        <option value="Married_Dual_Income">Married Dual Income</option>
                        <option value="Head_of_Household">Head of Household</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Allowances<span class="text-danger">&nbsp;*</span></label>
                    <div> 
                      <input type="text" class="form-control" name="stateTaxAllowances" id="stateTaxAllowances" value="0" data-validation="required validate_Space validate_length length1-100 validate_int" field-name="Allowances">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Additional withholding<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <input type="text" class="form-control" name="stateTaxAdditionalWithHolding" id="stateTaxAdditionalWithHolding" value="0" data-validation="required validate_Space validate_length length1-100 validate_int" field-name="Additional with holding">
                    </div>
                  </div> -->
                  
                  <div class="clearfix"></div>
                  <h4 class="col-md-12">Residence Location</h4>
                  <div class="col-md-5">
                    <label>Filling Status<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <select class="sel" name="residenceLocationFillingStatus" id="residenceLocationFillingStatus" data-validation="required" field-name="Filling Status"> 
                        <option value="">--Select--</option>
                        <option value="Single">Single</option>
                        <option value="Married">Married</option>
                        <option value="Married_Single_Income">Married Single Income</option>
                        <option value="Married_Dual_Income">Married Dual Income</option>
                        <option value="Head_of_Household">Head of Household</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Allowances<span class="text-danger">&nbsp;*</span></label>
                    <div> 
                      <input type="text" class="form-control" name="residenceLocationAllowances" id="residenceLocationAllowances" value="0" data-validation="required validate_Space validate_length length1-100 validate_int" field-name="Allowances">
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Additional withholding<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <input type="text" class="form-control" name="residenceLocationAdditionalWithHolding" id="residenceLocationAdditionalWithHolding" value="0" data-validation="required validate_Space validate_length length1-100 validate_int" field-name="Additional with holding">
                    </div>
                  </div>
                   <div class="col-md-5">
                    <label>Supplement Type<span class="text-danger">&nbsp;*</span></label>
                    <div>
                      <select class="sel" id="residenceLocationSupplementType" name="residenceLocationSupplementType" data-validation="required" field-name="Supplement Type"> 
                        <option value="">--Select--</option>
                        <option value="BONUS">Bonus</option>
                        <option value="COMMISSION">Commission</option>
                        <option value="NONE">None</option>
                      </select>
                    </div>
                  </div>
                    <div class="col-md-5">
                    <label>Local tax</label>
                    <div>
                      <select class="chosen-select" id="localTax" name="localTax" data-validation="required" field-name="State Tax" onchange="isLocalTaxYes();"> 
<!--                         <option value="">--Select--</option> -->
<!--                         <option selected value="NO_TAX">No Tax</option> -->
<%--                         <option value='${location }'>${location }</option> --%>
                      </select>
                    </div>
<!--                     <p class="text-success form-control-static clearfix" id="localTaxMessageDiv"> </p> -->
                  </div>
                  
                  <div class="clearfix"></div>
                  <h4 class="col-md-12">Additional</h4>
                  <div class="col-md-5">
                    <label>Federal</label> 
                    <div>
                      <label class="radioIput mr">
                        <input type="radio" name="federalExempt" id="federalExempt" value="true">
                        Exempt</label>
                      <label class="radioIput">
                        <input type="radio" name="federalExempt" id="federalExempt" value="false">
                        Nonexempt</label>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Mediacare</label>
                    <div>
                      <label class="radioIput mr">
                        <input type="radio" name="medicareExempt" id="medicareExempt" value="true">
                        Exempt</label>
                      <label class="radioIput">
                        <input type="radio" name="medicareExempt" id="medicareExempt" value="false">
                        Nonexempt</label>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>Social Security</label>
                    <div>
                      <label class="radioIput mr">
                        <input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="true">
                        Exempt</label>
                      <label class="radioIput">
                        <input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="false">
                        Nonexempt</label>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>FUTA</label>
                    <div>
                      <label class="radioIput mr">
                        <input type="radio" name="futaExempt" id="futaExempt" value="true">
                        Exempt</label>
                      <label class="radioIput">
                        <input type="radio" name="futaExempt" id="futaExempt" value="false">
                        Nonexempt</label>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <label>SUTA</label>
                    <div>
                      <label class="radioIput mr">
                        <input type="radio" name="sutaExempt" id="sutaExempt" value="true">
                        Exempt</label>
                      <label class="radioIput">
                        <input type="radio" name="sutaExempt" id="sutaExempt" value="false">
                        Nonexempt</label>
                    </div>
                  </div>
                  
                  <div class="infoMask infoMask1">
               		<i class="fa fa-info-circle"></i>Associate will update information...
               </div>
               
               </div>
                  
                  
					<div class="clearfix"></div>
                  <div class="clearfix footerstyelsNew">
                  <div class="col-md-6">
                 <input type="button" value="Previous" onclick="shiftToBankingTab();" class="btn btn-default">
			     </div> 
                  <div class="col-md-6">
                   <div class="pull-right">
                   <input value="Skip" class="btn btn-default mr10" onClick="skipAssociateTaxJsp();" type="button">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" id="associate_tax_button" type="button" onclick="saveAssociateTax();">
                    <input value="Cancel" class="btn btn-default ml10" onclick="javascript:window.location.href = '/app/usersList'" type="button">
                    </div>
                  </div>
                </div>
              </div>
              </form:form>
            </div>
          </div>
                 
          
 <script type="text/javascript">
 function saveAssociateTax() {
	 var userId = $("#associateId").val();  
	 var assTaxButton = $("#associate_tax_button").val();
	 if(assTaxButton == 'Proceed To Next Step'){
		 var associateDeduction = document.getElementById("associateDeduction");
			document.getElementById("associateDeduction").removeAttribute("class"); 
			associateDeduction.href="#tab-4"; 
			$("#associateDeduction").click();
			$("#associate_tax_button").removeAttr("disabled");
			//$("#associate_tax_button").addClass('button').val('Save & Continue');
	 }else{
		 if ($('#associate_tax_form').validate(false, validationSettings)){ 
			   var formData = getFormData("associate_tax_form");
				$("#associate_tax_button").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
//	            $( "#associate_tax_button" ).addClass('button').val('Processing..');
	            
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/saveAssociateTax?"+tokenParamName+"="+tokenValue+"&userId="+userId,
					success : function(result) {
						 if (result && result.response === "INVALID_DATA") {
			                    $("#associate_tax_button").removeAttr("disabled");
			                    $("#associate_tax_button").removeAttr('button').val('Save');
			                    showError(result.responseMessage);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	/* $("#associateId").val(result.object.userId); */
								var associateDeduction = document.getElementById("associateDeduction");
								document.getElementById("associateDeduction").removeAttribute("class"); 
								associateDeduction.href="#tab-4"; 
								$("#associateDeduction").click();
								$("#associate_tax_button").removeAttr("disabled");
								$("#associate_tax_button").addClass('button').val('Save & Continue');
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    $("#associate_tax_button").removeAttr("disabled");
			                    $("#associate_tax_button").addClass('button').val('Save');
			                    showError("Operation failed, please try again");
			                }
							 
					},
				});
	            
		        return true;
			
		  }
		else{
			return false;
		  } 
	  }
    }
 
   $('body').on('blur', '#associate_tax_form', function() {
		$("#associate_tax_form").showHelpOnFocus();
		$("#associate_tax_form").validateOnBlur(false, validationSettings);  
   });  
</script>

<script>
function shiftToBankingTab(){
	var associates = document.getElementById("associateBanking");
	document.getElementById("associateBanking").removeAttribute("class"); 
	associates.href="#tab-2"; 
	$("#loading").removeAttr('style');
	if($("#allowEmployeeToFill").is(':checked')){
	    $("#save_associate_bank_data_button" ).addClass('button').val('Proceed To Next Step');
	}
	
    $("#save_associate_bank_data_button").removeAttr("disabled");
    $("#associateBanking").click();
}
</script>




<script>
function isLocalTaxYes(){
		   var localTax = $("#localTax").val();
		   if(localTax != 'NO_TAX'){
			$.post(sourceleadUrl+"/isLocalTaxYes?"+tokenParamName+"="+tokenValue+"&localTax="+localTax, function(data) {
				var locationTax = "";
				if(data.object.isLocalTaxYes === 'YES'){
					locationTax = locationTax + data.object.state;
					locationTax = locationTax + ",";
					locationTax = locationTax + data.object.city;
					locationTax = locationTax + "(localTax YES)";
					$("#localTaxMessageDiv").text(locationTax);
					$("#localTaxMessageDiv").show();
				}else{
					locationTax = locationTax + data.object.state;
					locationTax = locationTax + ",";
					locationTax = locationTax + data.object.city;
					locationTax = locationTax + "(localTax NO)";
					$("#localTaxMessageDiv").text(locationTax);
					$("#localTaxMessageDiv").show();
				}
			}); 
		   }
}

</script>

<script>
function skipAssociateTaxJsp(){
	/* $("#associateId").val(result.object.userId); */
	var associateDeduction = document.getElementById("associateDeduction");
	document.getElementById("associateDeduction").removeAttribute("class"); 
	associateDeduction.href="#tab-4"; 
	$("#associateDeduction").click();
}
</script>

<script>
function getResidenceLocation(){
	var userId = $("#associateId").val();  
	$.ajax({
		type : "GET",
		url : sourceleadUrl+"/getResidenceLocation?"+tokenParamName+"="+tokenValue+"&userId="+userId,
		success : function(result) {
			var output = "";
			var selected = "selected";
			if(result != null && result != '' && result != 'undefined'){
				output += "<option selected value='"+result+"'>"+result+"</option>";
				selected = "";
			}
			output += "<option "+selected+" value='NO_TAX'>No Tax</option>";
			$("#localTax").empty();
			$("#localTax").append(output);
			$('#localTax').trigger("chosen:updated");
       }
	});
}
</script>
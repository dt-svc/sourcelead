  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateTaxSummary(userId){  
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateTaxSummaryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            var result = "";
       		result = result + '<div id="editButtonDiv" class="col-md-12">';
       		result = result + ' <a onclick="editAssociateSummary('+userId+');" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
       		result = result + '</div>';
       		result = result + '<div id="summaryBodyDiv">';
       		result = result + ' <div class="user_details_cta user_detailsCta userDetails-cta">';
       		result = result + ' <h4>Federal Tax</h4>';
       		result = result + ' <div class="project_details_cta"> <strong>Filling Status:</strong> <span>';
       		result = result + '  <input type="text" value='+json.federaltaxFillingStatus+' placeholder="Project name" class="project_detfield">';
       		result = result + '  </span>';
       		result = result + '   <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '   <div class="project_details_cta"> <strong>Allowances:</strong> <span>';
       		result = result + '     <input type="text" value='+json.federaltaxAllowances+' placeholder="First name" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '     <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>Additional with holding:</strong> <span>';
       		result = result + '      <input type="text" value='+json.federaltaxAdditionalWithHolding+' placeholder="Client" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '    </div>';
                         
       		result = result + '     <div class="user_details_cta user_detailsCta  userDetails-cta">';
       		result = result + '      <h4>State Tax</h4>';
       		result = result + '    <div class="project_details_cta"> <strong>Filling Status:</strong> <span>';
       		result = result + '     <input type="text" value='+json.stateTaxFillingStatus+' placeholder="DD/MM/YY" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>Allowances:</strong>';
       		result = result + '    <span class="fix">';
       		result = result + '      <input type="text" value='+json.stateTaxAllowances+' placeholder="DD/MM/YY" class="project_detfield">';
       		result = result + '        </span>';
       		result = result + '     <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '   <div class="project_details_cta"> <strong>Additional with holding:</strong> <span>';
       		result = result + '     <input type="text" value='+json.stateTaxAdditionalWithHolding+' placeholder="DD/MM/YY" class="project_detfield">';
       		result = result + '    </span>';
       		result = result + '    <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + ' </div>';
       		result = result + '   <div class="user_details_cta user_detailsCta userDetails-cta">';
                        
       		result = result + '    <h4>Residence Location</h4>';
                         
       		result = result + '   <div class="project_details_cta"> <strong>Filling Status:</strong> <span>';
       		result = result + '     <input type="text" value='+json.residenceLocationFillingStatus+' placeholder="Project name" class="project_detfield">';
       		result = result + '      </span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '     <div class="project_details_cta"> <strong>Allowances:</strong> <span>';
       		result = result + '       <input type="text" value='+json.residenceLocationAllowances+' placeholder="First name" class="project_detfield">';
       		result = result + '         </span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '      </div>';
       		result = result + '      <div class="project_details_cta"> <strong>Additional with holding:</strong> <span>';
       		result = result + '        <input type="text" value='+json.residenceLocationAdditionalWithHolding+' placeholder="Client" class="project_detfield">';
       		result = result + '        </span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     </div>';
       				
       		result = result + ' <div class="user_details_cta user_detailsCta  userDetails-cta">';
       		result = result + '   <h4>Additional</h4>';
       		result = result + '     <div class="project_details_cta"> <strong>Federal:</strong><span>'+json.federalExempt+'</span>';
       		result = result + '       <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     <div class="project_details_cta"> <strong>Mediacare:</strong>';
       		result = result + '      <span>'+json.medicareExempt+'</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '       <div class="project_details_cta"> <strong>Socail Security:</strong>';
       		result = result + '        <span>'+json.socailSecurityExempt+'</span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     <div class="project_details_cta"> <strong>FUTA:</strong>';
       		result = result + '      <span>'+json.futaExempt+'</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>SUTA:</strong>';
       		result = result + '     <span>'+json.sutaExempt+'</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '      </div>';
       		result = result + '    </div>';
       		result = result + ' </div>';
       		result = result + ' </div>';
       		result = result + '<div id="editBodyDiv" class="col-md-12"></div>';
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-22").empty(); 
                $("#tab-22").append(result); 
          },
            
       }); 
  } 
</script>
 
 
 <script>
 function editAssociateSummary(userId){
	 $.ajax({
         type : "GET",
         url: sourceleadUrl+"/findAssociateTaxSummaryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
          success :function(json) {
         
        	  var result = "";
        	  result = result + '<div id="loading"></div>';
        	  result = result + '<div class="clearfix row minHeightcol5">';
        	  result = result + '<form id="associate_tax_form"  name="associate_tax_form" method="post">';
        	  result += '<input type="hidden" value='+json.id+' name="id" id="id"/>';
        	  result = result + '<div class="col-md-12 mb">';
        	  result = result + ' <div class="clearfix row">';
        	  result = result + '  <h4 class="col-md-12 mt">Federal Tax</h4>';
        	  result = result + '  <div class="col-md-5">';
        	  result = result + '  <label>Filling Status<span class="text-danger">&nbsp;*</span></label>';
        	  result = result + '   <div>';
        	  result = result + '  <select class="sel" id="federaltaxFillingStatus" name="federaltaxFillingStatus" data-validation="required" field-name="Filling Status">';
        	  result = result + '     <option value="">--Select--</option>';
        	  result = result + '    <option '+ (json.federaltaxFillingStatus == "Single" ? "selected" : '') +' value="Single">Single</option>';
        	  result = result + '    <option '+ (json.federaltaxFillingStatus == "Married" ? "selected" : '') +' value="Married">Married</option>';
        	  result = result + '     <option '+ (json.federaltaxFillingStatus == "Married_Single_Income" ? "selected" : '') +' value="Married_Single_Income">Married Single Income</option>';
        	  result = result + '     <option '+ (json.federaltaxFillingStatus == "Married_Dual_Income" ? "selected" : '') +' value="Married_Dual_Income">Married Dual Income</option>';
        	  result = result + '     <option '+ (json.federaltaxFillingStatus == "Head_of_Household" ? "selected" : '') +' value="Head_of_Household">Head of Household</option>';
        	  result = result + '    </select>';
        	  result = result + '  </div>';
        	  result = result + '  </div>';
        	  result = result + '  <div class="col-md-5">';
        	  result = result + '    <label>Allowances<span class="text-danger">&nbsp;*</span></label>';
        	  result = result + '     <div>';
        	  result = result + '     <input type="text" class="form-control" name="federaltaxAllowances" value='+json.federaltaxAllowances+' id="federaltaxAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">';
        	  result = result + '    </div>';
        	  result = result + '  </div>';
        	  result = result + '   <div class="col-md-5">';
        	  result = result + '    <label>Additional with holding<span class="text-danger">&nbsp;*</span></label>';
              result = result + '    <div>';
              result = result + '     <input type="text" class="form-control" name="federaltaxAdditionalWithHolding" value='+json.federaltaxAdditionalWithHolding+' id="federaltaxAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">';
              result = result + '    </div>';
              result = result + '  </div>';
              result = result + '  <div class="clearfix"></div>';
              result = result + '  <h4 class="col-md-12">State Tax</h4>';
              result = result + ' <div class="col-md-5">';
              result = result + '     <label>Filling Status<span class="text-danger">&nbsp;*</span></label>';
               result = result + '    <div>';
              result = result + '     <select class="sel" name="stateTaxFillingStatus" id="stateTaxFillingStatus" data-validation="required" field-name="State Tax"> ';
              result = result + '      <option value="">--Select--</option>';
              result = result + '       <option '+ (json.stateTaxFillingStatus == "Single" ? "selected" : '') +' value="Single">Single</option>';
              result = result + '    <option '+ (json.stateTaxFillingStatus == "Married" ? "selected" : '') +' value="Married">Married</option>';
              result = result + '  <option '+ (json.stateTaxFillingStatus == "Married_Single_Income" ? "selected" : '') +' value="Married_Single_Income">Married Single Income</option>';
              result = result + '    <option '+ (json.stateTaxFillingStatus == "Married_Dual_Income" ? "selected" : '') +' value="Married_Dual_Income">Married Dual Income</option>';
              result = result + '    <option '+ (json.stateTaxFillingStatus == "Head_of_Household" ? "selected" : '') +' value="Head_of_Household">Head of Household</option>';
              result = result + '  </select>';
              result = result + ' </div>';
              result = result + '  </div>';
              result = result + '  <div class="col-md-5">';
              result = result + '    <label>Allowances<span class="text-danger">&nbsp;*</span></label>';
              result = result + '    <div> ';
              result = result + '      <input type="text" class="form-control" name="stateTaxAllowances" value='+json.stateTaxAllowances+' id="stateTaxAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">';
              result = result + '     </div>';
              result = result + '   </div>';
              result = result + '   <div class="col-md-5">';
              result = result + '     <label>Additional with holding<span class="text-danger">&nbsp;*</span></label>';
              result = result + '<div>';
              result = result + '     <input type="text" class="form-control" name="stateTaxAdditionalWithHolding" value='+json.stateTaxAdditionalWithHolding+' id="stateTaxAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">';
              result = result + '  </div>';
              result = result + '  </div>';
                   
               result = result + '  <div class="clearfix"></div>';
              result = result + '  <h4 class="col-md-12">Residence Location</h4>';
              result = result + '  <div class="col-md-5">';
              result = result + '    <label>Filling Status<span class="text-danger">&nbsp;*</span></label>';
              result = result + '   <div>';
              result = result + '     <select class="sel" name="residenceLocationFillingStatus" id="residenceLocationFillingStatus" data-validation="required" field-name="Filling Status"> ';
              result = result + '       <option value="">--Select--</option>';
              result = result + '       <option '+ (json.residenceLocationFillingStatus == "Single" ? "selected" : '') +' value="Single">Single</option>';
              result = result + '       <option '+ (json.residenceLocationFillingStatus == "Married" ? "selected" : '') +' value="Married">Married</option>';
              result = result + '        <option '+ (json.residenceLocationFillingStatus == "Married_Single_Income" ? "selected" : '') +' value="Married_Single_Income">Married Single Income</option>';
              result = result + '        <option '+ (json.residenceLocationFillingStatus == "Married_Dual_Income" ? "selected" : '') +' value="Married_Dual_Income">Married Dual Income</option>';
              result = result + '        <option '+ (json.residenceLocationFillingStatus == "Head_of_Household" ? "selected" : '') +' value="Head_of_Household">Head of Household</option>';
              result = result + '       </select>';
              result = result + '    </div>';
              result = result + '    </div>';
              result = result + '  <div class="col-md-5">';
              result = result + ' <label>Allowances<span class="text-danger">&nbsp;*</span></label>';
              result = result + '  <div> ';
              result = result + '  <input type="text" class="form-control" name="residenceLocationAllowances" value='+json.residenceLocationAllowances+' id="residenceLocationAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">';
              result = result + ' </div>';
              result = result + ' </div>';
           result = result + '  <div class="col-md-5">';
              result = result + '    <label>Additional with holding<span class="text-danger">&nbsp;*</span></label>';
              result = result + '     <div>';
              result = result + '       <input type="text" class="form-control" name="residenceLocationAdditionalWithHolding"  value='+json.residenceLocationAdditionalWithHolding+' id="residenceLocationAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">';
              result = result + '     </div>';
              result = result + '  </div>';
              result = result + '   <div class="col-md-5">';
              result = result + '    <label>Supplement Type<span class="text-danger">&nbsp;*</span></label>';
              result = result + '    <div>';
              result = result + '      <select class="sel" id="residenceLocationSupplementType" name="residenceLocationSupplementType" value='+json.residenceLocationSupplementType+'  data-validation="required" field-name="Supplement Type"> ';
              result = result + '        <option  value="">--Select--</option>';
              result = result + '        <option '+ (json.residenceLocationSupplementType == "BONUS" ? "selected" : '') +' value="BONUS">Bonus</option>';
              result = result + '        <option '+ (json.residenceLocationSupplementType == "COMMISSION" ? "selected" : '') +' value="COMMISSION">Commission</option>';
              result = result + '        <option '+ (json.residenceLocationSupplementType == "NONE" ? "selected" : '') +' value="NONE">None</option>';
              result = result + '    </select>';
              result = result + '    </div>';
              result = result + '   </div>';
              result = result + '   <div class="col-md-5">';
              result = result + '    <label>Local tax</label>';
              result = result + '   <div>';
              result = result + '  <select class="sel" id="localTax" name="localTax" data-validation="required" field-name="State Tax" onchange="isLocalTaxYes();"> ';
              result = result + '     <option value="">--Select--</option>';
              if(json.localTax == 'NO'){
            	  result = result + ' <option selected value="NO_TAX">No Tax</option>';
              }else{
            	  result = result + '     <option selected value='+json.localTaxState+'>'+json.localTax+'</option>';
              }
              result = result + ' </select>';
              result = result + '  </div>';
              result = result + ' <p class="text-success form-control-static clearfix" id="localTaxMessageDiv"> </p>';
              result = result + ' </div>';
                   
              result = result + ' <div class="clearfix"></div>';
              result = result + '  <h4 class="col-md-12">Additional</h4>';
              result = result + '  <div class="col-md-5">';
              result = result + '   <label>Federal</label> ';
              result = result + '   <div>';
              if(json.federalExempt == 'YES'){
            	  result = result + '  <label class="radioIput mr">';
                  result = result + '    <input type="radio" checked name="federalExempt" id="federalExempt"  value="true">';
                  result = result + '   Exempt</label>';
                  
                  result = result + '   <label class="radioIput">';
                  result = result + '    <input type="radio" name="federalExempt" id="federalExempt" value="false">';
                  result = result + '   Notexempt</label>';
              }else{
            	  result = result + '    <input type="radio"  name="federalExempt" id="federalExempt"  value="true">';
                  result = result + '   Exempt</label>';
                  
                  result = result + '   <label class="radioIput">';
                  result = result + '    <input type="radio" checked name="federalExempt" id="federalExempt" value="false">';
                  result = result + '   Notexempt</label>';
              }
             
              
              result = result + '  </div>';
              result = result + '</div>';
              result = result + ' <div class="col-md-5">';
              result = result + ' <label>Mediacare</label>';
              result = result + ' <div>';
              if(json.medicareExempt == 'YES'){
            	  result = result + '  <label class="radioIput mr">';
                  result = result + '    <input type="radio" checked name="medicareExempt" id="medicareExempt" value="true">';
                  result = result + '   Exempt</label>';
                  
                  result = result + '  <label class="radioIput">';
                  result = result + '    <input type="radio" name="medicareExempt" id="medicareExempt" value="false">';
                  result = result + '      Notexempt</label>';
              }else{
            	  result = result + '  <label class="radioIput mr">';
                  result = result + '    <input type="radio" name="medicareExempt" id="medicareExempt" value="true">';
                  result = result + '   Exempt</label>';
                  
                  result = result + '  <label class="radioIput">';
                  result = result + '    <input type="radio" checked name="medicareExempt" id="medicareExempt" value="false">';
                  result = result + '      Notexempt</label>';
              }
              
              result = result + '</div>';
              result = result + '</div>';
              result = result + ' <div class="col-md-5">';
              result = result + '   <label>Socail Security</label>';
              result = result + '   <div>';
             if(json.socailSecurityExempt == 'YES'){
            	 result = result + '  <label class="radioIput mr">';
                 result = result + '    <input type="radio" checked name="socailSecurityExempt" id="socailSecurityExempt" value="true">';
                 result = result + '    Exempt</label>';
                 result = result + ' <label class="radioIput">';
                 result = result + '   <input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="false">';
                 result = result + '   Notexempt</label>'; 
             }else{
            	 result = result + '  <label class="radioIput mr">';
                 result = result + '    <input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="true">';
                 result = result + '    Exempt</label>';
                 result = result + ' <label class="radioIput">';
                 result = result + '   <input type="radio" checked name="socailSecurityExempt" id="socailSecurityExempt" value="false">';
                 result = result + '   Notexempt</label>'; 
             }
              
              
              result = result + '   </div>';
              result = result + ' </div>';
              result = result + ' <div class="col-md-5">';
              result = result + '   <label>FUTA</label>';
              result = result + '   <div>';
              if(json.futaExempt == 'YES'){
            	  result = result + '    <label class="radioIput mr">';
                  result = result + '      <input type="radio" checked name="futaExempt" id="futaExempt" value="true">';
                  result = result + '     Exempt</label>';
                  result = result + '   <label class="radioIput">';
                  result = result + '      <input type="radio" name="futaExempt" id="futaExempt" value="false">';
                  result = result + '      Notexempt</label>'; 
              }else{
            	  result = result + '    <label class="radioIput mr">';
                  result = result + '      <input type="radio" name="futaExempt" id="futaExempt" value="true">';
                  result = result + '     Exempt</label>';
                  result = result + '   <label class="radioIput">';
                  result = result + '      <input type="radio" checked name="futaExempt" id="futaExempt" value="false">';
                  result = result + '      Notexempt</label>';
              }
             
              
              result = result + '   </div>';
              result = result + '  </div>';
              result = result + '  <div class="col-md-5">';
              result = result + '  <label>SUTA</label>';
              result = result + '   <div>';
              if(json.sutaExempt == 'YES'){
            	  result = result + '   <label class="radioIput mr">';
                  result = result + '      <input type="radio" checked name="sutaExempt" id="sutaExempt" value="true">';
                  result = result + ' Exempt</label>';
                  
                  result = result + ' <label class="radioIput">';
                  result = result + '   <input type="radio" name="sutaExempt" id="sutaExempt" value="false">';
                  result = result + '    Notexempt</label>';
              }else{
            	  result = result + '   <label class="radioIput mr">';
                  result = result + '      <input type="radio" name="sutaExempt" id="sutaExempt" value="true">';
                  result = result + ' Exempt</label>';
                  
                  result = result + ' <label class="radioIput">';
                  result = result + '   <input type="radio" checked name="sutaExempt" id="sutaExempt" value="false">';
                  result = result + '    Notexempt</label>';
              }
             
              
              result = result + '  </div>';
              result = result + ' </div>';
                 
              result = result + '	<div class="clearfix"></div>';
              result = result + ' <div class="clearfix footerstyelsNew">';
                
              result = result + ' <div class="col-md-12">';
              result = result + '   <div class="pull-right">';
              result = result + ' <input value="Save" class="btn btn-success btnSuccess" id="associate_tax_button" type="button" onclick="saveAssociateTax('+userId+');">';
              result = result + ' <input value="Cancel" class="btn btn-default ml10" onclick="javascript:window.location.href = "/app/usersList"" type="button">';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </form>';
              result = result + ' </div>';
              
              $("#editButtonDiv").hide();
              $("#summaryBodyDiv").hide();
              $("#editBodyDiv").show();
              $("#editBodyDiv").append(result);
        },
          
     }); 
	 
	 
 }
 </script>
 
 
 
 <script type="text/javascript">
 function saveAssociateTax(userId) {
	 alert("userId");
		 if ($('#associate_tax_form').validate(false, validationSettings)){ 
			   var formData = getFormData("associate_tax_form");
				$("#associate_tax_button").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#associate_tax_button" ).addClass('button').val('Processing..');
	            
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
			                	 $("#editButtonDiv").show();
			                     $("#summaryBodyDiv").show();
			                     $("#editBodyDiv").hide();
			                     associateTaxSummary(userId);
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
			
		  }else{
			return false;
		  } 
    }
 
   $('body').on('blur', '#associate_tax_form', function() {
		$("#associate_tax_form").showHelpOnFocus();
		$("#associate_tax_form").validateOnBlur(false, validationSettings);  
   });  
</script>
 
 

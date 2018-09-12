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
       		result = result + ' <div class="project_details_cta"> <strong>Filling Status:</strong>';
       		if(json.federaltaxFillingStatus != null && json.federaltaxFillingStatus != '' && json.federaltaxFillingStatus != 'undefined')
       			result = result + '  <span title="'+json.federaltaxFillingStatus+'" >'+json.federaltaxFillingStatus+'</span>';
        	else
        		result = result + ' <span>---</span>';
       		
       		result = result + '   <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '   <div class="project_details_cta"> <strong>Allowances:</strong>';
       		if(json.federaltaxAllowances != null && json.federaltaxAllowances != '' && json.federaltaxAllowances != 'undefined')
       			result = result + '  <span title="'+json.federaltaxAllowances+'">'+json.federaltaxAllowances+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '     <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>Additional with holding:</strong>';
       		if(json.federaltaxAdditionalWithHolding != null && json.federaltaxAdditionalWithHolding != '' && json.federaltaxAdditionalWithHolding != 'undefined')
       			result = result + '  <span title="'+json.federaltaxAdditionalWithHolding+'">'+json.federaltaxAdditionalWithHolding+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '    </div>';
                         
       	/* 	result = result + '     <div class="user_details_cta user_detailsCta  userDetails-cta">';
       		result = result + '      <h4>State Tax</h4>';
       		result = result + '    <div class="project_details_cta"> <strong>Filling Status:</strong>';
       		if(json.stateTaxFillingStatus != null && json.stateTaxFillingStatus != '' && json.stateTaxFillingStatus != 'undefined')
       			result = result + '  <span title="'+json.stateTaxFillingStatus+'"> '+json.stateTaxFillingStatus+' </span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>Allowances:</strong>';
       		if(json.stateTaxAllowances != null && json.stateTaxAllowances != '' && json.stateTaxAllowances != 'undefined')
       			result = result + '  <span title="'+json.stateTaxAllowances+'"> '+json.stateTaxAllowances+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '     <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + '   <div class="project_details_cta"> <strong>Additional with holding:</strong>';
       		if(json.stateTaxAdditionalWithHolding != null && json.stateTaxAdditionalWithHolding != '' && json.stateTaxAdditionalWithHolding != 'undefined')
       			result = result + '  <sapn title="'+json.stateTaxAdditionalWithHolding+'">'+json.stateTaxAdditionalWithHolding+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '    <div class="clearfix"></div>';
       		result = result + '   </div>';
       		result = result + ' </div>';
       		result = result + '   <div class="user_details_cta user_detailsCta userDetails-cta">'; */
                        
       		result = result + '    <h4>Residence Location</h4>';
                         
       		result = result + '   <div class="project_details_cta"> <strong>Filling Status:</strong>';
       		if(json.residenceLocationFillingStatus != null && json.residenceLocationFillingStatus != '' && json.residenceLocationFillingStatus != 'undefined')
       			result = result + '  <span title="'+json.residenceLocationFillingStatus+'">'+json.residenceLocationFillingStatus+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '     <div class="project_details_cta"> <strong>Allowances:</strong>';
       		if(json.residenceLocationAllowances != null && json.residenceLocationAllowances != '' && json.residenceLocationAllowances != 'undefined')
       			result = result + '  <span title="'+json.residenceLocationAllowances+'">'+json.residenceLocationAllowances+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '      </div>';
       		result = result + '      <div class="project_details_cta"> <strong>Additional with holding:</strong>';
       		if(json.residenceLocationAdditionalWithHolding != null && json.residenceLocationAdditionalWithHolding != '' && json.residenceLocationAdditionalWithHolding != 'undefined')
       			result = result + '  <span title="'+json.residenceLocationAdditionalWithHolding+'">'+json.residenceLocationAdditionalWithHolding+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     </div>';
       				
       		result = result + ' <div class="user_details_cta user_detailsCta  userDetails-cta">';
       		result = result + '   <h4>Additional</h4>';
       		result = result + '     <div class="project_details_cta"> <strong>Federal:</strong>';
       		if(json.federalExempt != null && json.federalExempt != '' && json.federalExempt != 'undefined')
       			result = result + '  <span title="'+json.federalExempt+'" >'+json.federalExempt+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '       <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     <div class="project_details_cta"> <strong>Mediacare:</strong>';
       		if(json.medicareExempt != null && json.medicareExempt != '' && json.medicareExempt != 'undefined')
       			result = result + '  <span title="'+json.medicareExempt+'" >'+json.medicareExempt+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '       <div class="project_details_cta"> <strong>Socail Security:</strong>';
       		if(json.socailSecurityExempt != null && json.socailSecurityExempt != '' && json.socailSecurityExempt != 'undefined')
       			result = result + '  <span title="'+json.socailSecurityExempt+'" >'+json.socailSecurityExempt+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '        <div class="clearfix"></div>';
       		result = result + '     </div>';
       		result = result + '     <div class="project_details_cta"> <strong>FUTA:</strong>';
       		if(json.futaExempt != null && json.futaExempt != '' && json.futaExempt != 'undefined')
       			result = result + '  <span title="'+json.futaExempt+'" >'+json.futaExempt+'</span>';
        	else
        		result = result + '  <span>---</span>';
       		result = result + '      <div class="clearfix"></div>';
       		result = result + '    </div>';
       		result = result + '    <div class="project_details_cta"> <strong>SUTA:</strong>';
       		if(json.sutaExempt != null && json.sutaExempt != '' && json.sutaExempt != 'undefined')
       			result = result + '  <span title="'+json.sutaExempt+'" >'+json.sutaExempt+'</span>';
        	else
        		result = result + '  <span>---</span>';
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
                $("#tab-25").empty();
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
        	  var id = "";
              if(json.id != null && json.id != '' && json.id != 'undefined'){
            	  id = json.id;
              }else{
            	  id = "";
              }
        	  result += '<input type="hidden" value=\''+id+'\' name="id" id="id"/>';
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
        	  result = result + '     <option '+ (json.federaltaxFillingStatus == "Married_Use_Single_Rate" ? "selected" : '') +' value="Married_Use_Single_Rate">Married Use Single Rate</option>';
        	  result = result + '     <option '+ (json.federaltaxFillingStatus == "NonResident_Alien" ? "selected" : '') +' value="NonResident_Alien">Non Resident Alien</option>';
        	  result = result + '    </select>';
        	  result = result + '  </div>';
        	  result = result + '  </div>';
        	  result = result + '  <div class="col-md-5">';
        	  result = result + '    <label>Allowances<span class="text-danger">&nbsp;*</span></label>';
        	  result = result + '     <div>';
        	  var federaltaxAllowances = "";
              if(json.federaltaxAllowances != null && json.federaltaxAllowances != '' && json.federaltaxAllowances != 'undefined'){
            	  federaltaxAllowances = json.federaltaxAllowances;
              }else{
            	  federaltaxAllowances = "";
              }
        	  result = result + '     <input type="text" class="form-control" name="federaltaxAllowances" value=\''+federaltaxAllowances+'\' id="federaltaxAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">';
        	  result = result + '    </div>';
        	  result = result + '  </div>';
        	  result = result + '   <div class="col-md-5">';
        	  result = result + '    <label>Additional withholding<span class="text-danger">&nbsp;*</span></label>';
              result = result + '    <div>';
              var federaltaxAdditionalWithHolding = "";
              if(json.federaltaxAdditionalWithHolding != null && json.federaltaxAdditionalWithHolding != '' && json.federaltaxAdditionalWithHolding != 'undefined'){
            	  federaltaxAdditionalWithHolding = json.federaltaxAdditionalWithHolding;
              }else{
            	  federaltaxAdditionalWithHolding = "";
              }
              result = result + '     <input type="text" class="form-control" name="federaltaxAdditionalWithHolding" value=\''+federaltaxAdditionalWithHolding+'\' id="federaltaxAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">';
              result = result + '    </div>';
              result = result + '  </div>';
             /*  result = result + '  <div class="clearfix"></div>';
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
              var stateTaxAllowances = "";
              if(json.stateTaxAllowances != null && json.stateTaxAllowances != '' && json.stateTaxAllowances != 'undefined'){
            	  stateTaxAllowances = json.stateTaxAllowances;
              }else{
            	  stateTaxAllowances = "";
              }
              result = result + '      <input type="text" class="form-control" name="stateTaxAllowances" value=\''+stateTaxAllowances+'\' id="stateTaxAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">';
              result = result + '     </div>';
              result = result + '   </div>';
              result = result + '   <div class="col-md-5">';
              result = result + '     <label>Additional withholding<span class="text-danger">&nbsp;*</span></label>';
              result = result + '<div>';
              var stateTaxAdditionalWithHolding = "";
              if(json.stateTaxAdditionalWithHolding != null && json.stateTaxAdditionalWithHolding != '' && json.stateTaxAdditionalWithHolding != 'undefined'){
            	  stateTaxAdditionalWithHolding = json.stateTaxAdditionalWithHolding;
              }else{
            	  stateTaxAdditionalWithHolding = "";
              }
              result = result + '     <input type="text" class="form-control" name="stateTaxAdditionalWithHolding" value=\''+stateTaxAdditionalWithHolding+'\' id="stateTaxAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">';
              result = result + '  </div>';
              result = result + '  </div>'; */
                   
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
              var residenceLocationAllowances = "";
              if(json.residenceLocationAllowances != null && json.residenceLocationAllowances != '' && json.residenceLocationAllowances != 'undefined'){
            	  residenceLocationAllowances = json.residenceLocationAllowances;
              }else{
            	  residenceLocationAllowances = "";
              }
              result = result + '  <input type="text" class="form-control" name="residenceLocationAllowances" value=\''+residenceLocationAllowances+'\' id="residenceLocationAllowances" data-validation="required validate_Space validate_length length1-100" field-name="Allowances">';
              result = result + ' </div>';
              result = result + ' </div>';
       		  result = result + '  <div class="col-md-5">';
              result = result + '    <label>Additional withholding<span class="text-danger">&nbsp;*</span></label>';
              result = result + '     <div>';
              var residenceLocationAdditionalWithHolding = "";
              if(json.residenceLocationAdditionalWithHolding != null && json.residenceLocationAdditionalWithHolding != '' && json.residenceLocationAdditionalWithHolding != 'undefined'){
            	  residenceLocationAdditionalWithHolding = json.residenceLocationAdditionalWithHolding;
              }else{
            	  residenceLocationAdditionalWithHolding = "";
              }
              result = result + '       <input type="text" class="form-control" name="residenceLocationAdditionalWithHolding"  value=\''+residenceLocationAdditionalWithHolding+'\' id="residenceLocationAdditionalWithHolding" data-validation="required validate_Space validate_length length1-100" field-name="Additional with holding">';
              result = result + '     </div>';
              result = result + '  </div>';
              result = result + '   <div class="col-md-5">';
              result = result + '    <label>Supplement Type<span class="text-danger">&nbsp;*</span></label>';
              result = result + '    <div>';
              result = result + '      <select class="sel" id="residenceLocationSupplementType" name="residenceLocationSupplementType"  data-validation="required" field-name="Supplement Type"> ';
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
              }else if(json.localTax != null && json.localTax != '' && json.localTax != 'undefined'){
            	  result = result + '     <option selected value='+json.localTaxState+'>'+json.localTax+'</option>';
              }else{
            	  result = result + '     <option selected value='+json.location+'>'+json.location+'</option>';
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
                  result = result + '   Nonexempt</label>';
              }else{
            	  result = result + '    <input type="radio"  name="federalExempt" id="federalExempt"  value="true">';
                  result = result + '   Exempt</label>';
                  
                  result = result + '   <label class="radioIput">';
                  result = result + '    <input type="radio" checked name="federalExempt" id="federalExempt" value="false">';
                  result = result + '   Nonexempt</label>';
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
                  result = result + '      Nonexempt</label>';
              }else{
            	  result = result + '  <label class="radioIput mr">';
                  result = result + '    <input type="radio" name="medicareExempt" id="medicareExempt" value="true">';
                  result = result + '   Exempt</label>';
                  
                  result = result + '  <label class="radioIput">';
                  result = result + '    <input type="radio" checked name="medicareExempt" id="medicareExempt" value="false">';
                  result = result + '      Nonexempt</label>';
              }
              
              result = result + '</div>';
              result = result + '</div>';
              result = result + ' <div class="col-md-5">';
              result = result + '   <label>Social Security</label>';
              result = result + '   <div>';
             if(json.socailSecurityExempt == 'YES'){
            	 result = result + '  <label class="radioIput mr">';
                 result = result + '    <input type="radio" checked name="socailSecurityExempt" id="socailSecurityExempt" value="true">';
                 result = result + '    Exempt</label>';
                 result = result + ' <label class="radioIput">';
                 result = result + '   <input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="false">';
                 result = result + '   Nonexempt</label>'; 
             }else{
            	 result = result + '  <label class="radioIput mr">';
                 result = result + '    <input type="radio" name="socailSecurityExempt" id="socailSecurityExempt" value="true">';
                 result = result + '    Exempt</label>';
                 result = result + ' <label class="radioIput">';
                 result = result + '   <input type="radio" checked name="socailSecurityExempt" id="socailSecurityExempt" value="false">';
                 result = result + '   Nonexempt</label>'; 
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
                  result = result + '      Nonexempt</label>'; 
              }else{
            	  result = result + '    <label class="radioIput mr">';
                  result = result + '      <input type="radio" name="futaExempt" id="futaExempt" value="true">';
                  result = result + '     Exempt</label>';
                  result = result + '   <label class="radioIput">';
                  result = result + '      <input type="radio" checked name="futaExempt" id="futaExempt" value="false">';
                  result = result + '      Nonexempt</label>';
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
                  result = result + '    Nonexempt</label>';
              }else{
            	  result = result + '   <label class="radioIput mr">';
                  result = result + '      <input type="radio" name="sutaExempt" id="sutaExempt" value="true">';
                  result = result + ' Exempt</label>';
                  
                  result = result + ' <label class="radioIput">';
                  result = result + '   <input type="radio" checked name="sutaExempt" id="sutaExempt" value="false">';
                  result = result + '    Nonexempt</label>';
              }
             
              
              result = result + '  </div>';
              result = result + ' </div>';
                 
              result = result + '	<div class="clearfix"></div>';
              result = result + ' <div class="clearfix footerstyelsNew">';
                
              result = result + ' <div class="col-md-12">';
              result = result + '   <div class="pull-right">';
              result = result + ' <input value="Save" class="btn btn-success btnSuccess" id="associate_tax_button" type="button" onclick="saveAssociateTax('+userId+');">';
              result = result + ' <input value="Cancel" class="btn btn-default ml10" onclick="cancelButton();" type="button">';
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

<script>
function cancelButton(){ 
	location.replace(sourceleadUrl+ "/usersList");
}
</script>


 
 

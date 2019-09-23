  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateTaxSummary(userId){  
	  $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateTaxSummaryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            var result = "";
            result = result + '<div id="blankDiv" class="col-md-12 mt minHeight_225" style="display:none;">';
            result = result + '<div class="pull-left">';
       		result = result + ' <a class="anchor-black" target="_self" onclick="backToSummary();" title="Back"><i class="fa fa-angle-double-left paddingRight"></i>Back</a>';
       		result = result + '</div>';
            result = result + '<div class="dataTables_empty"></div>';
            result = result + '</div>';
       		result = result + '<div id="editButtonDiv" class="col-md-12 useredit mt">';
       		result = result + '<div class="pull-right">';
       		result = result + ' <a onclick="editAssociateSummary('+userId+');" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
       		result = result + ' <a onclick="getAssociateTaxHistory('+userId+');" class="anchor-black pull-right" target="_self" title="History"><i class="fa fa-history paddingRight"></i>History</a>';
       		result = result + '</div>';
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
       		result = result + ' </div>';*/
       		
       		result = result + '   <div class="user_details_cta user_detailsCta userDetails-cta">'; 
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
       		result = result + '     <div class="project_details_cta"> <strong>Medicare:</strong>';
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
       		result = result + '<div id="historyDiv" class="col-md-12"></div>';
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
function getAssociateTaxHistory(userId){
	$.ajax({
		type : "GET",
		url : sourceleadUrl+"/getAssociateTaxHistoryByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,
		success : function(result) {
			var json = result.data;
			var result = "";
			if(json.length >= 1){
					result += '<div class="clearfix positionRelative" id="assTaxHisViewDiv">';
					result = result + '<div class="clearfix mt">';
		       		result = result + ' <a class="anchor-black" target="_self" onclick="backToSummary();" title="Back"><i class="fa fa-angle-double-left paddingRight"></i>Back</a>';
		       		result = result + '</div>';
		            result += '<h4>Timeline</h4>';
					result += '	<ul class="timelineDots clearfix">';
					
					for(var i=0; i<json.length;i++){
						var opt = json[i];
						result += ' <li>';
						result += '<div class="media">';
						result += '<div class="media-left">';
						result += '	<div class="media-object">';
						if(opt.profileURL != null && opt.profileURL != '' && opt.profileURL != 'undefined'){
							result += ' <img src="'+opt.profileURL+'" class="" style="width:50px" alt="Image">';
						}else{
							result += ' <img src="" class="" style="width:50px" alt="Image">';
						}
						result += '	</div>';
						result += ' </div>';
						result += ' <div class="media-body">';
						result += '	<div class="row">';
						result += ' <div class="col-md-9">';
						result += '		<p class="media-heading textBold">'+opt.userName+' has updated the Tax Details</p>';
						result += '	</div>';
						result += '	<div class="col-md-3">';
						result += '	  <ul class="pull-right small text-muted list-unstyled list-inline">';
						var dateStr = opt.updatedDate.split("#");
						result += '		<li class="mr"><i class="fa fa-calendar paddingRight"></i>'+dateStr[0]+'</li>';
						result += '		<li><i class="fa fa-clock-o paddingRight"></i>'+dateStr[1]+'</li>';
						result += '	  </ul>  ';
						result += '	<div class="clearfix"></div>';
						result += '	</div>';
						result += '	</div>';
						
						result += '	<div class="row mt5">';
						
						if(opt.previousFederalTaxFilliingStatus != null && opt.previousFederalTaxFilliingStatus != '' && opt.previousFederalTaxFilliingStatus != 'undefined'
								&& opt.federalTaxFilliingStatus != null && opt.federalTaxFilliingStatus != '' && opt.federalTaxFilliingStatus != 'undefined'){
							if(opt.previousFederalTaxFilliingStatus != opt.federalTaxFilliingStatus){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Federal Filling Status:</label>';
								result += '			<span class="col-md-10">'+opt.previousFederalTaxFilliingStatus+' to '+opt.federalTaxFilliingStatus+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousFederalTaxAllowances != null && opt.previousFederalTaxAllowances != '' && opt.previousFederalTaxAllowances != 'undefined'
							&& opt.federalTaxAllowances != null && opt.federalTaxAllowances != '' && opt.federalTaxAllowances != 'undefined'){
							if(opt.previousFederalTaxAllowances != opt.federalTaxAllowances){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Federal Allowances:</label>';
								result += '			<span class="col-md-10">'+opt.previousFederalTaxAllowances+' to '+opt.federalTaxAllowances+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousFederalTaxAdditionnalWithHolding != null && opt.previousFederalTaxAdditionnalWithHolding != '' && opt.previousFederalTaxAdditionnalWithHolding != 'undefined'
							&& opt.federalTaxAdditionnalWithHolding != null && opt.federalTaxAdditionnalWithHolding != '' && opt.federalTaxAdditionnalWithHolding != 'undefined'){
							if(opt.previousFederalTaxAdditionnalWithHolding != opt.federalTaxAdditionnalWithHolding){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Federal Additional with holding:</label>';
								result += '			<span class="col-md-10">'+opt.previousFederalTaxAdditionnalWithHolding+' to '+opt.federalTaxAdditionnalWithHolding+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousResidenceTaxFilliingStatus != null && opt.previousResidenceTaxFilliingStatus != '' && opt.previousResidenceTaxFilliingStatus != 'undefined'
							&& opt.residenceTaxFilliingStatus != null && opt.residenceTaxFilliingStatus != '' && opt.residenceTaxFilliingStatus != 'undefined'){
							if(opt.previousResidenceTaxFilliingStatus != opt.residenceTaxFilliingStatus){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Residence Filliing Status:</label>';
								result += '			<span class="col-md-10">'+opt.previousResidenceTaxFilliingStatus+' to '+opt.residenceTaxFilliingStatus+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousResidenceTaxAllowances != null && opt.previousResidenceTaxAllowances != '' && opt.previousResidenceTaxAllowances != 'undefined'
							&& opt.residenceTaxAllowances != null && opt.residenceTaxAllowances != '' && opt.residenceTaxAllowances != 'undefined'){
							if(opt.previousResidenceTaxAllowances != opt.residenceTaxAllowances){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Residence Tax Allowances:</label>';
								result += '			<span class="col-md-10">'+opt.previousResidenceTaxAllowances+' to '+opt.residenceTaxAllowances+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousResidenceTaxAdditionnalWithHolding != null && opt.previousResidenceTaxAdditionnalWithHolding != '' && opt.previousResidenceTaxAdditionnalWithHolding != 'undefined'
							&& opt.residenceTaxAdditionnalWithHolding != null && opt.residenceTaxAdditionnalWithHolding != '' && opt.residenceTaxAdditionnalWithHolding != 'undefined'){
							if(opt.previousResidenceTaxAdditionnalWithHolding != opt.residenceTaxAdditionnalWithHolding){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Residence Additionnal WithHolding:</label>';
								result += '			<span class="col-md-10">'+opt.previousResidenceTaxAdditionnalWithHolding+' to '+opt.residenceTaxAdditionnalWithHolding+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousSupplementType != null && opt.previousSupplementType != '' && opt.previousSupplementType != 'undefined'
							&& opt.supplementType != null && opt.supplementType != '' && opt.supplementType != 'undefined'){
							if(opt.previousSupplementType != opt.supplementType){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">SupplementType:</label>';
								result += '			<span class="col-md-10">'+opt.previousSupplementType+' to '+opt.supplementType+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousFederalExempt != null && opt.previousFederalExempt != '' && opt.previousFederalExempt != 'undefined'
							&& opt.federalExempt != null && opt.federalExempt != '' && opt.federalExempt != 'undefined'){
							if(opt.previousFederalExempt != opt.federalExempt){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Federal Exempt:</label>';
								if(opt.previousFederalExempt == 'true'){
									result += '			<span class="col-md-10">Exempt to Non-Exempt</span>';
								}
								else{
									result += '			<span class="col-md-10">Non-Exempt to Exempt</span>';
								}
								result += '		</div>  ';
							}
						}
						
						if(opt.previousMediacareExempt != null && opt.previousMediacareExempt != '' && opt.previousMediacareExempt != 'undefined'
							&& opt.mediacareExempt != null && opt.mediacareExempt != '' && opt.mediacareExempt != 'undefined'){
							if(opt.previousMediacareExempt != opt.mediacareExempt){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Medicare Exempt:</label>';
								if(opt.previousMediacareExempt == 'true'){
									result += '			<span class="col-md-10">Exempt to Non-Exempt</span>';
								}
								else{
									result += '			<span class="col-md-10">Non-Exempt to Exempt</span>';
								}
								result += '		</div>  ';
							}
						}
						
						if(opt.previousSocialSecurityExempt != null && opt.previousSocialSecurityExempt != '' && opt.previousSocialSecurityExempt != 'undefined'
							&& opt.socialSecurityExempt != null && opt.socialSecurityExempt != '' && opt.socialSecurityExempt != 'undefined'){
							if(opt.previousSocialSecurityExempt != opt.socialSecurityExempt){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Social Security Exempt:</label>';
								if(opt.previousSocialSecurityExempt == 'true'){
									result += '			<span class="col-md-10">Exempt to Non-Exempt</span>';
								}
								else{
									result += '			<span class="col-md-10">Non-Exempt to Exempt</span>';
								}
								result += '		</div>  ';
							}
						}
						
						if(opt.previousFutaExempt != null && opt.previousFutaExempt != '' && opt.previousFutaExempt != 'undefined'
							&& opt.futaExempt != null && opt.futaExempt != '' && opt.futaExempt != 'undefined'){
							if(opt.previousFutaExempt != opt.futaExempt){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Futa Exempt:</label>';
								if(opt.previousFutaExempt == 'true'){
									result += '			<span class="col-md-10">Exempt to Non-Exempt</span>';
								}
								else{
									result += '			<span class="col-md-10">Non-Exempt to Exempt</span>';
								}
								result += '		</div>  ';
							}
						}
						
						if(opt.previousSutaExempt != null && opt.previousSutaExempt != '' && opt.previousSutaExempt != 'undefined'
							&& opt.sutaExempt != null && opt.sutaExempt != '' && opt.sutaExempt != 'undefined'){
							if(opt.previousSutaExempt != opt.sutaExempt){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Suta Exempt:</label>';
								if(opt.previousSutaExempt == 'true'){
									result += '			<span class="col-md-10">Exempt to Non-Exempt</span>';
								}
								else{
									result += '			<span class="col-md-10">Non-Exempt to Exempt</span>';
								}
									
								result += '		</div>  ';
							}
						}
						
						/* if(opt.previousLocalTax != null && opt.previousLocalTax != '' && opt.previousLocalTax != 'undefined'
							&& opt.localTax != null && opt.localTax != '' && opt.localTax != 'undefined'){
							if(opt.previousLocalTax != opt.localTax){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Local Tax:</label>';
								result += '			<span class="col-md-10">'+opt.previousLocalTax+' to '+opt.localTax+'</span>';
								result += '		</div>  ';
							}
						}
						
						if(opt.previousLocalTaxState != null && opt.previousLocalTaxState != '' && opt.previousLocalTaxState != 'undefined'
							&& opt.localTaxState != null && opt.localTaxState != '' && opt.localTaxState != 'undefined'){
							if(opt.previousLocalTaxState != opt.localTaxState){
								result += '		<div class="clearfix">';
								result += '			<label class="col-md-2 text-muted">Local Tax State:</label>';
								result += '			<span class="col-md-10">'+opt.previousLocalTaxState+' to '+opt.localTaxState+'</span>';
								result += '		</div>  ';
							}
						} */
						result += '</div>';
						result += '</div>';
						result += '</div>';
						result += '</li>';
					}
					
					result += '</ul>';
				  	result += '</div>';
				
			}else{
				$("#blankDiv").show();
			}
		  	 $("#editButtonDiv").hide();
             $("#summaryBodyDiv").hide();
             $("#editBodyDiv").hide();
             $("#historyDiv").empty();
             $("#historyDiv").append(result);
             $("#historyDiv").show();
             
       }
	});
}

function backToSummary(){
	$("#editButtonDiv").show();
    $("#summaryBodyDiv").show();
    $("#editBodyDiv").hide();
    $("#historyDiv").hide();
	$("#blankDiv").hide();
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
        	  result = result + '    <option '+ (json.federaltaxFillingStatus == "Married_Single_Income" ? "selected" : '') +' value="Married_Single_Income">Married Single Income</option>';
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
//               result = result + '   <div class="col-md-5">';
//               result = result + '    <label>Local tax</label>';
//               result = result + '   <div>';
//               result = result + '  <select class="sel" id="localTax" name="localTax" data-validation="required" field-name="State Tax" onchange="isLocalTaxYes();"> ';
//               result = result + '     <option value="">--Select--</option>';
//               var noSelected = "";
//               var yesSelected = "";
//               alert(json.location)
//               if(json.localTaxState == 'NO_TAX'){
//             	  noSelected = "selected";
//               }else{
//             	  yesSelected = "selected";
//               }
            	  
//             	  result = result + ' <option value="NO_TAX" '+noSelected+'>No Tax</option>';
//               }else if(json.localTax != null && json.localTax != '' && json.localTax != 'undefined'){
//             	  result = result + '     <option selected value="'+json.localTaxState+'" '+yesSelected+'>'+json.localTaxState+'</option>';
//               }else{
//             	  result = result + '     <option selected value='+json.location+'>'+json.location+'</option>';
//               }
//               result = result + ' </select>';
//               result = result + '  </div>';
//               result = result + ' <p class="text-success form-control-static clearfix" id="localTaxMessageDiv"> </p>';
//               result = result + ' </div>';
                   
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
              result = result + ' <label>Medicare</label>';
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
              result = result + ' <input value="Cancel" class="btn btn-default ml10" onclick="cancelButtonTax('+userId+');" type="button">';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </div>';
              result = result + '  </form>';
              result = result + ' </div>';
              
//               var loc = json.localTax;
         	 getResidenceLocation(userId,json.localTax);
         	 
              $("#editButtonDiv").hide();
              $("#summaryBodyDiv").hide();
              $("#editBodyDiv").show();
              $("#editBodyDiv").append(result);
        },
          
     }); 
	 
 }
 </script>
 
 <script>
function getResidenceLocation(userId,localTax){
// 	var localTax = $("#localTax").val();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+"/getResidenceLocation?"+tokenParamName+"="+tokenValue+"&userId="+userId,
		success : function(result) {
			var output = "";
			var noSelected = "";
			var yesSelected = "";
			if(localTax == 'NO'){
          	  noSelected = "selected";
            }else{
          	  yesSelected = "selected";
            }
			if(result != null && result != '' && result != 'undefined'){
				output += "<option "+yesSelected+" value='"+result+"'>"+result+"</option>";
				selected = "";
			}
			output += "<option "+noSelected+" value='NO_TAX'>No Tax</option>";
			$("#localTax").empty();
			$("#localTax").append(output);
			$('#localTax').trigger("chosen:updated");
       }
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
function cancelButtonTax(userId){ 
	$("#editBodyDiv").hide();
	associateTaxSummary(userId);
	/* location.replace(sourceleadUrl+ "/usersList"); */
}
</script>


 
 

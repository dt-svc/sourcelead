
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <div id="federalAndStateTaxes" class="tab-pane fade form-horizontal">
  <div class="accountsettings-tab">
	<div class="tab-content panel-body">
		
		<form:form class="login-form clearfix"  id="fedralAndTax_form"  name="fedralAndTax_form" method="post" > 
		<div class="comLogContent">
			  <div id="taxPopUp" ></div>	
			    <div id="loading"></div> 
			         <div class="clearfix">
			         <h3 class=".margin-top0">Federal Taxes</h3>
		         			<div class="col-md-6">
					   		
					   		<div class="form-group clearfix">
								<label class="col-md-4 noPad">Federal EIN<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								 <input class=" form-control" type="text" name="fedralEIN" id="fedralEIN" value="${SourceLeadWeb_orgLocations.federalEin}" field-name="fedralEIN"  data-validation="required validate_space validate_length length9-9" maxlength="9">
								</div>
							</div>
							
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Deposit Schedule<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								<select  class="chosen-select form-control" name="depositeSchedule" id="depositeSchedule"  field-name="Deposit Schedule"  data-validation="required ">
											<option value="">--Select Deposit Schedule--</option>
											<option value="Monthly"  ${SourceLeadWeb_orgLocations.depositSchedule  == 'Monthly' ? 'selected' : ''}>Monthly</option>
											<option value="Semi Monthly" ${SourceLeadWeb_orgLocations.depositSchedule  == 'Semi Monthly' ? 'selected' : ''}>Semi Monthly</option>
					                 </select>
								</div>
							</div>
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Filling form<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								<select  class="chosen-select form-control" name="fillingForm" id="fillingForm" field-name="Filling Form"  data-validation="required ">
									<option value="">--Select Filling form --</option>
									<option value="Quarterly[940]"  ${SourceLeadWeb_orgLocations.fillingForm  == 'Quarterly[940]' ? 'selected' : ''}>Quarterly[940]</option>
									<option value="Annuallu[944]"  ${SourceLeadWeb_orgLocations.fillingForm  == 'Annuallu[944]' ? 'selected' : ''}>Annually[944]</option>
									<option value="House hold[H]"  ${SourceLeadWeb_orgLocations.fillingForm  == 'House hold[H]' ? 'selected' : ''}>House hold[H]</option>
								  </select>
								</div>
							</div>
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Unemployment Tax?</label>
								<div class="col-md-8 padding_right">
					              
					              <c:if test="${empty SourceLeadWeb_orgLocations.unemploymentTax}">
					                <label class="col-md-5 noPad">
					                <input name="unemploymentTax" type="radio" value="Exempt"  >
					                <strong class="paddingLeft">Exempt</strong></label>
					                <label class="col-md-5 noPad">
					                <input name="unemploymentTax" type="radio" value="Unexempt" checked >
					                <strong class="paddingLeft">Unexempt</strong></label>
					              </c:if>
					              
					              <c:if test="${!empty SourceLeadWeb_orgLocations.unemploymentTax}">
					                <label class="col-md-5 noPad">
					                <input name="unemploymentTax" type="radio" value="Exempt" ${SourceLeadWeb_orgLocations.unemploymentTax  == 'Exempt' ? 'checked' : ''} >
					                <strong class="paddingLeft">Exempt</strong></label>
					                <label class="col-md-5 noPad">
					                <input name="unemploymentTax" type="radio" value="Unexempt" ${SourceLeadWeb_orgLocations.unemploymentTax  == 'Unexempt' ? 'checked' : ''} >
					                <strong class="paddingLeft">Unexempt</strong></label>
					              </c:if>
					            
					            </div>
							</div>
			  </div>		
					
					<div class="row clearfix">
					<div class="col-md-10">	
					<h3>State Taxes</h3>
					<div id="stateTaxesList"></div>
		         	</div>
		         	</div>
		         	
		         	<h3 class=".margin-top0">Tax Deposit</h3>
		         	
		         	<h3 class=".margin-top0">FederaL Taxes</h3>
		         			<div class="col-md-6">
		         			
		         			<div class="form-group clearfix">
								<label class="col-md-4 noPad">Filler Type</label>
								<div class="col-md-8 padding_right">
					              
					              <c:if test="${empty SourceLeadWeb_orglocationDepositTaxes.fillerType}">
					                <label class="col-md-5 noPad">
					                <input name="fillerType" type="radio" value="940" >
					                <strong class="paddingLeft">940</strong></label>
					                <label class="col-md-5 noPad">
					                <input name="fillerType" type="radio" value="941" >
					                <strong class="paddingLeft">941</strong></label>
					              </c:if>
					              
					              <c:if test="${!empty SourceLeadWeb_orglocationDepositTaxes.fillerType}">
					               <label class="col-md-5 noPad">
					                <input name="fillerType" type="radio" value="940" ${SourceLeadWeb_orglocationDepositTaxes.fillerType  == '940' ? 'checked' : ''} >
					                <strong class="paddingLeft">940</strong></label>
					                <label class="col-md-5 noPad">
					                <input name="fillerType" type="radio" value="941" ${SourceLeadWeb_orglocationDepositTaxes.fillerType  == '941' ? 'checked' : ''} >
					                <strong class="paddingLeft">941</strong></label> 
					              </c:if>
					            
					            </div>
							</div>
					   		
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Federal Deposit</label>
								<div class="col-md-8 padding_right">
								<select  class="chosen-select form-control" name="federalDeposit" id="federalDeposit"  field-name="Federal Deposit">
											<option value="">--Select Federal Deposit--</option>
											<option value="Semi weekly" ${SourceLeadWeb_orglocationDepositTaxes.federalDeposit  == 'Semi weekly' ? 'selected' : ''}>Semi weekly</option>
											<option value="Semi Monthly" ${SourceLeadWeb_orglocationDepositTaxes.federalDeposit  == 'Semi Monthly' ? 'selected' : ''}>Monthly</option>
											<option value="Quarterly" ${SourceLeadWeb_orglocationDepositTaxes.federalDeposit  == 'Quarterly' ? 'selected' : ''}>Quarterly</option>
					                 </select>
								</div>
							</div>
							
							
							<h3 class=".margin-top0">State Taxes</h3>
							
							<div class="form-group clearfix">
								<label class="col-md-4 noPad">Deposit Schedule</label>
								<div class="col-md-8 padding_right">
								<select  class="chosen-select form-control" name="taxDepositSchedule" id="depositSchedule" field-name="Deposit Schedule">
									<option value="">--Select Deposit Schedule--</option>
									<option value="Monthly"  ${SourceLeadWeb_orglocationDepositTaxes.taxDepositSchedule  == 'Monthly' ? 'selected' : ''}>Monthly</option>
									<option value="Quarterly"  ${SourceLeadWeb_orglocationDepositTaxes.taxDepositSchedule  == 'Quarterly' ? 'selected' : ''}>Quarterly</option>
									<option value="Semi Annually" ${SourceLeadWeb_orglocationDepositTaxes.taxDepositSchedule  == 'Semi Annually' ? 'selected' : ''}>Semi Annually</option>
								  </select>
								</div>
							</div>
							
			  </div>		
		         	
		         	
		       </div>
			       
			         
	         <div class="clearfix">
				<div class="pull-right">
				<input type="button" value="Cancel" class="btn btn-default" onClick="self.location='/app/dashboard/${SourceLeadWeb_orgLocationId}'">
				</div>
				<div class="pull-right">
				<input type="submit" value="Save" id="save_federal_button" class="btn btn-primary mr">
				</div>
			</div>
			         
       		  </div>
       		  </form:form>
       		  
		</div>
	</div>
 </div>
 <script src="/resources/js/formvalidator.js"></script> 
 
 <div id="divDocsTd1" style="display: none; float: left;">
		  <div class="chosentext-left">
		              <select class="form-control select-bg CHOOSEN-SELECT-CLASS" data-validation="required" field-name="State" id="REPLACEROWIDstateName" name="stateTaxesList[REPLACEROWID].stateName">
		                <option selected value="">-- Select State --</option>
		                <c:forEach var="state" items="${statesList}">
							<option value="${state.stateName}" >${state.stateName}</option>
		    			</c:forEach> 
		              </select>
		  </div>
 	</div>
					  
	  <div id="divDocsTd2" style="display: none; float: left;">
	  <div class="clearfix mt5" id="REPLACEROWIDstateUnemploymentTax" name="REPLACEROWIDstateUnemploymentTax">
		<input type="text" class="form-control input-sm" placeholder="State Unemployment Tax" data-validation="required validate_space validate_length length9-9" maxlength="9" field-name="State Unemployment Tax" name="stateTaxesList[REPLACEROWID].stateUnemploymentTax" id="REPLACEROWIDstateUnemploymentTax">
	  </div>
	 </div>
	  
	 
	  <div id="divDocsTd3" style="display: none; float: left;">
	  <div class="clearfix mt5" id="REPLACEROWIDstateTaxAccountNumber" name="REPLACEROWIDstateTaxAccountNumber">
		<input type="text" class="form-control input-sm" placeholder="Sate Tax Account Number" data-validation="required validate_space validate_length length9-9" maxlength="9" field-name="Sate Tax Account Number" name="stateTaxesList[REPLACEROWID].stateTaxAccountNumber" id="REPLACEROWIDstateTaxAccountNumber">
	 </div>
	 </div>
	  
	  <div id="divDocsTd4" style="display: none; float: left;">
      <div class="pull-right">
	       <button type="button" class="btn btm-sm pull-right mt5" onclick="removeStatesTaxRow(REPLACEROWID)"><i class="fa fa-remove text-danger"></i></button>
      </div>
	</div> 
	
	
	<script type="text/javascript">
	function removeStatesTaxRow(rowId) {
		
		var maxRowNumber = parseInt($("input[name = 'maxRowNumber']").val());
		$("input[name = 'maxRowNumber']").val(maxRowNumber - 1);
		var maxRow = maxRowNumber-1;
		if(maxRow == 0){
			$("#0stateName").removeAttr("data-validation"); 
			$("#0stateName").attr("data-validation-optional","true");
			 
			 $("#0stateUnemploymentTax").attr("data-validation-optional","true"); 
			 $("#0stateUnemploymentTax").removeAttr("data-validation");
			 $("#0stateUnemploymentTax").attr("data-validation","validate_space validate_length length9-9");
			
			 $("#0stateTaxAccountNumber").attr("data-validation-optional","true"); 
			 $("#0stateTaxAccountNumber").removeAttr("data-validation");
			 $("#0stateTaxAccountNumber").attr("data-validation","validate_space validate_length length9-9");
		 }
		
		 document.getElementById("divStatesTaxRow"+rowId).style.display = 'none';
		 document.getElementById("divStatesTaxRow"+rowId).value= '';
		 var  rId = "divStatesTaxRow"+rowId;
		  var r = document.getElementById("statesTaxTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>
 
 
 <script>
function addStatesTaxRow(){
	var checkMaxTsRowNum = $("#stateTaxMaxRowNumber").val();
	var tbl = document.getElementById("statesTaxTable");
	var maxRowNum = parseInt($("input[name = 'stateTaxMaxRowNumber']").val());
	$("input[name = 'stateTaxMaxRowNumber']").val(maxRowNum + 1);
	
	
	var maxRowNumber = parseInt($("input[name = 'maxRowNumber']").val());
	$("input[name = 'maxRowNumber']").val(maxRowNumber + 1);
	
	var maxRow = maxRowNumber+1;
	 if(maxRow == 1){
		 $("#0stateName").removeAttr("data-validation-optional");
		 $("#0stateName").attr("data-validation","required");
		 
		 $("#0stateUnemploymentTax").removeAttr("data-validation-optional"); 
		 $("#0stateUnemploymentTax").removeAttr("data-validation");
		 $("#0stateUnemploymentTax").attr("data-validation","required validate_space validate_length length9-9");
		
		 $("#0stateTaxAccountNumber").removeAttr("data-validation-optional"); 
		 $("#0stateTaxAccountNumber").removeAttr("data-validation");
		 $("#0stateTaxAccountNumber").attr("data-validation","required validate_space validate_length length9-9");
	 }
	
	
	var row = tbl.insertRow(maxRowNum);
	row.setAttribute("id", "divStatesTaxRow" + maxRowNum);
	
	
	var cell0 = row.insertCell(0);
	var divDocTypeTdStr = document.getElementById("divDocsTd1").innerHTML;
	divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	divDocTypeTdStr = divDocTypeTdStr.replace(/CHOOSEN-SELECT-CLASS/g, 'chosen-select');
	cell0.innerHTML = divDocTypeTdStr;
	
	 var cell1 = row.insertCell(1);
	var divDocDescTdStr = document.getElementById("divDocsTd2").innerHTML;
	divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell1.innerHTML = divDocDescTdStr; 
	
	var cell2 = row.insertCell(2);
	var divDocDescTdStr = document.getElementById("divDocsTd3").innerHTML;
	divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell2.innerHTML = divDocDescTdStr; 
	
	var cell3 = row.insertCell(3);
	var divDocDescTdStr = document.getElementById("divDocsTd4").innerHTML;
	divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell3.innerHTML = divDocDescTdStr; 
	
	prepareChosenScripts();
}
</script>

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>


<script>
function getStateTaxes(){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getOrgLocationsStatesTaxList?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(json) {
	        	var result = "";
	        	
	        	if(json.length > 0){
	        	 result = result +'<div class="companyOnboardStateTaxTable">';
        		 result = result +'<table class="table table-responsive table-bordered table-hover" width="100%" id="savedStatesTaxTable" border="0">';
        		 result = result +'<tbody>';
        		
        		 for (var i = 0; i < json.length; i++) { 
	        		 var opt = json[i]; 
	        		 
	        		 result = result +'<tr id="stateTaxRow'+opt.uuid+'">';
	        		 result = result +'<td width="500px">';
	 	     		 result = result +'<div class="chosentext-left">';
	 	     		
	 	     		 result = result +'<select class="form-control chosen-select" data-validation="required" field-name="State"  name="savedStateTaxesList['+i+'].stateName">';
	 	     		 result = result +'<option selected value="">-- Select State --</option>';
	 	     			<c:forEach var="state" items="${statesList}">
	 	     			if('${state.stateName}' == opt.stateName){
	 	     				 result = result +'<option value="${state.stateName}" selected>${state.stateName}</option>';
	 	     			}else{
	 	     				result = result +'<option value="${state.stateName}">${state.stateName}</option>';
	 	     			}
	 	     			 
	 	     			</c:forEach>
	 	     		 result = result +'</select>'; 

	 	     		 result = result +'</div>';
	 	     		 result = result +'</td>';
	        		
	 	     		result = result +"<input type='hidden' name='savedStateTaxesList["+i+"].uuid'  value='"+opt.uuid+"' />";
	        		result = result +'<td width="500px">';
	 	     		result = result +'<div class="">';
	 	     		result = result +'<input type="text" class="form-control input-sm" placeholder="State Unemployment Tax" value="'+opt.stateUnemploymentTax+'" data-validation="required validate_space validate_length length9-9" maxlength="9" field-name="State Unemployment Tax" name="savedStateTaxesList['+i+'].stateUnemploymentTax" >';
	 	     		result = result +'</div>';
	 	     		result = result +'</td>';
	 	     		
	 	     		result = result +'<td width="500px">';
		     		result = result +'<div class="">';
		     		result = result +'<input type="text" class="form-control input-sm" placeholder="Sate Tax Account Number" value="'+opt.stateTaxAccountNumber+'" data-validation="required validate_space validate_length length9-9" maxlength="9" field-name="Sate Tax Account Number" name="savedStateTaxesList['+i+'].stateTaxAccountNumber" >';
		     		result = result +'</div>';
		     		result = result +'</td>';
		     		
		     		result = result +'<td width="50px">';
		     		result = result +'<div class="">';
		     		result = result +'<div class="pull-right">';
		     		result = result +"<button type='button' class='btn btm-sm pull-right mt5' onClick='deleteStatesTaxRow(\""+opt.uuid+"\",\""+opt.stateName+"\");' ><i class='fa fa-trash text-danger'></i></button>";
		     		
		     		result = result +'</div>';
		     		result = result +'</div>';
		     		result = result +'</td>';
		     		
		     		result = result +'</tr>';
	        		
	        	 }
        		
        		 result = result +'</tbody>';
    			 result = result +'</table>';
	     		 result = result +'</div>';
	        	}
	        	
	        	result = result +'<div class="clearfix mb10 mt5 requestdocmntTable companyOnboardStateTaxTable">';
	     		result = result +'<input type="hidden" name="stateTaxMaxRowNumber"  id="stateTaxMaxRowNumber" value="1" />';
	     		result = result +'<input type="hidden" name="maxRowNumber"  id="maxRowNumber" value="0" />';
	     		result = result +'<table class="table table-responsive table-bordered table-hover" width="100%" id="statesTaxTable" border="0">';
	     		result = result +'<tbody>';
	     		result = result +'<tr id="requestedDocumentsTsRow0">';
	     		result = result +'<div id="0requestedDocumentsFileGroup">';
	     		
	     		
	     		result = result +'<td width="500px">';
	     		result = result +'<div class="chosentext-left">';
	     		
	     		result = result +'<select class="form-control chosen-select" data-validation-optional="true" field-name="State" id="0stateName" name="stateTaxesList[0].stateName">';
	     		result = result +'<option selected value="">-- Select State --</option>';
	     			<c:forEach var="state" items="${statesList}">
	     			result = result +'<option value="${state.stateName}">${state.stateName}</option>';
	     			</c:forEach>
	     		result = result +'</select>';

	     		result = result +'</div>';
	     		result = result +'</td>';
	     		
	     		result = result +'<td width="500px">';
	     		result = result +'<div class="">';
	     		result = result +'<input type="text" class="form-control input-sm" placeholder="State Unemployment Tax" data-validation-optional="true" data-validation="validate_space validate_length length9-9" maxlength="9" field-name="State Unemployment Tax" name="stateTaxesList[0].stateUnemploymentTax" id="0stateUnemploymentTax">';
	     		result = result +'</div>';
	     		result = result +'</td>';
	     		
	     		result = result +'<td width="500px">';
	     		result = result +'<div class="">';
	     		result = result +'<input type="text" class="form-control input-sm" placeholder="Sate Tax Account Number" data-validation-optional="true" data-validation="validate_space validate_length length9-9" maxlength="9" field-name="Sate Tax Account Number" name="stateTaxesList[0].stateTaxAccountNumber" id="0stateTaxAccountNumber">';
	     		result = result +'</div>';
	     		result = result +'</td>';
	     		
	     		result = result +'<td width="50px">';
	     		result = result +'<div class="">';
	     		
	     		result = result +'<div class="pull-right">';
	     		result = result +'<button type="button" class="btn btm-sm pull-right" onclick="addStatesTaxRow();"><i class="fa fa-plus text-primary"></i></button>';
	     		result = result +'</div>';
	     		result = result +'</div>';
	     		result = result +'</td>';
	     		result = result +'</div>';
	     		result = result +'</tr>';
	     		result = result +'</tbody>';
	     		result = result +'</table>';
	     		result = result +'</div>';
	     		result = result +'</div>';
	     		result = result +'</div>';
	     	$("#stateTaxesList").empty();
	     	$("#stateTaxesList").append(result);
	     	prepareChosenScripts();
	        },
	    });
}
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




<script type="text/javascript">
$('body').on('submit', '#fedralAndTax_form', function(e){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	if ($('#fedralAndTax_form').validate(false, validationSettings)){
      e.preventDefault();
      var sourceleadUrl ='${sourceleadUrl}';
      var formData = getFormData("fedralAndTax_form");
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	       $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/saveOrgLocationsFederalAndStatesTax?${_csrf.parameterName}=${_csrf.token}",		
		        success: function(result) {
		        	
		        	if(result == 'success'){
		        		getStateTaxes();
		        		$("#loading").removeAttr("style");
		        	}else{
		        		$("#loading").removeAttr("style"); 
		        	}
		        },
		    });   
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#fedralAndTax_form', function() {
		$("#fedralAndTax_form").showHelpOnFocus();
		$("#fedralAndTax_form").validateOnBlur(false, validationSettings);
	});
  </script>
  

 
  
<script type="text/javascript">
function deleteStatesTaxRow(uuid,stateName){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are your sure to delete <strong>"+stateName+"</strong> state tax?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='deleteTaxRow(\""+uuid+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTaxPopUp();'></b>";
	result = result +"	</div>";
	$("#taxPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script> 

<script>
function deleteTaxRow(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	   $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/deleteOrgLocationStateTaxByDocumentUUID?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
	        success: function(data) {
	        	if(data == 'success'){
	        		document.getElementById("stateTaxRow"+uuid).remove();
	        		$("#loading").removeAttr("style"); 
	        		cancelTaxPopUp();
	        	}
	        },
	    });
	 
}
</script>

<script type="text/javascript">
function cancelTaxPopUp(){
	$("#taxPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>  
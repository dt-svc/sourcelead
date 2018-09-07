<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<script>
$(document).ready(function(){
   var deductionType = "${deductionType }";
   getAllDeductionType(deductionType);
});
</script>

  <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Contribution</h2>
      </div>
      
      <div class="success_pop" id="selector2" style="display: none;">
   <i class="fa fa-check-circle"></i><span id="addContributeMsgDiv"></span>
 </div>
   <!--    <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Add Contribution</div> -->
      
      <div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo;Add Contribution
		</div>
      
      
    </section>
      <div id="addContributionLoading"></div>
      <form:form id="add_contribution_form" name="add_contribution_form" method="post">
      <div class="contentareaInner-styles clearfix">
      <div class="clearfix minHeightcol5 row">
          <div class="col-md-5">
				<label class="clearfix">For Deduction<span class="text-danger paddingLeft">&nbsp;*</span></label>
				<div id="contributionNameDive">
				<div class="clearfix">
				<select class="form-control chosen-select" id="deductionTypeId" name="deductionTypeId" data-validation="required" onchange="isValidContributionType();" field-name="Deduction Type">
				</select>
				<input type="hidden" value="${payrollContribution.contributionUUID }" id="contributionUUID" name="contributionUUID"/>
				</div>
				</div>
			</div>          
			
			
			<div class="col-md-5">
			<label class="clearfix">Method<span class="text-danger paddingLeft">&nbsp;*</span></label>
			<div class="clearfix dropdown-select">
				<select class="form-control" id="payrollContributionMethod" name="payrollContributionMethod" data-validation="required" onchange="validateContributionMethodType();" field-name="Method">
				<option value = "">Select Method</option>
				 <option ${payrollContribution.payrollContributionMethod == 'Fixed' ? 'selected' : ''} value = "Fixed">Fixed</option>
				  <option ${payrollContribution.payrollContributionMethod == 'Percent' ? 'selected' : ''} value = "Percent">Percent</option>
				</select>
			</div>
			</div>
			
			<div class="col-md-5">
              <label class="clearfix">Amount<span class="text-danger paddingLeft">&nbsp;*</span></label>
              <div class="clearfix" id="contributionAmountDiv">
                <input type="text" class="form-control" value="${payrollContribution.contributionAmount}" id="contributionAmount" name="contributionAmount" data-validation="required validate_float" onblur="validateContributionMethodType();" field-name="Amount">
              </div>
            </div>
            
			<div class="col-md-5">
			<label class="clearfix">Description<span class="text-danger paddingLeft">&nbsp;*</span></label>
			<div class="clearfix">
			<textarea rows="2" class="form-control" id="description" name="description" data-validation="validate_length length1-250">${payrollContribution.description  }</textarea>
			</div>
			</div>
			
        
        </div>
        <div class="clearfix footerstyelsNew">
	        <div class="col-lg-12">
	          <div class="pull-right">
	            <input value="Save" class="btn btn-success btnSuccess" id="save_contributionType" type="button" onclick="saveContribution();">
	            <input value="Cancel" class="btn btn-default ml10" onclick="window.location='${appPath}/payrollConfiguration';" type="button">
	          </div>
	        </div>
      	</div>   
      </div>
      </form:form>   
      <!-- Content Area Ends--> 
    
  </section>
  
  
  <script>
function getAllDeductionType(deductionType){
	$.ajax({
		type : "POST",
		url: sourceleadUrl+"/getAllDeductionType?"+tokenParamName+"="+tokenValue+"",
		success :function(data) {
		var json = data.data;
		var output = "";
		output = output + "<option value=''>Select Deduction Type</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		 if(deductionType == opt.payrollDeductionType){
			var selected = 'selected';
			output = output + "<option value='" +opt.id+ "'"+selected+">"+opt.payrollDeductionType+"</option>";
		}else{ 
			output = output + "<option value='" +opt.id+ "'>"+opt.payrollDeductionType+"</option>";
		}
		
		}
		$("#deductionTypeId").empty();
		$("#deductionTypeId").append(output);
		$('#deductionTypeId').trigger("chosen:updated");
	},
	});
}

</script>


<script>
function saveContribution(){
	if ($('#add_contribution_form').validate(false, validationSettings)){  
 	 	    var formData = getFormData("add_contribution_form");
		        	  $("#addContributionLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.post(sourceleadUrl+"/savePayrollContribution?"+tokenParamName+"="+tokenValue, formData, function(data) {
		        		  $("#addContributionLoading").removeAttr("style");
		        		  location.replace(sourceleadUrl+ "/payrollContribution");
		        		  $("#selector2").removeAttr("style");
			     		  $("#selector2").css({ display: "block" });
			     		  $("#addContributeMsgDiv").html("Added Successfully");
			     		   addContributeMsgFadein();
		  			   }); 
		    	         return true; 
               }else{
	    		return false;
	    	  } 
}

$('body').on('blur', '#add_contribution_form', function() {
	$("#add_contribution_form").showHelpOnFocus();
	$("#add_contribution_form").validateOnBlur(false, validationSettings);
});

</script>

<script>
function validateContributionMethodType() {
	$('#contributionAmountDiv').parent().find('p.jquery_form_error_message').remove(); 
	 var selectedMethod = document.getElementById("payrollContributionMethod").value;
	 var enteredValue = document.getElementById("contributionAmount").value;
	 var isDisabled = $('#save_contributionType').is('[disabled=disabled]');
	 var nameErrorDiv = $('#contributionNameDive p').length > 0;
	 if(selectedMethod =='Percent'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(enteredValue.match(floatNum)){
			  if(!nameErrorDiv){
				  $("#save_contributionType").removeAttr("disabled", "disabled");
			  }
			  return true;
		  }		  
		 else{
			 $("#contributionAmountDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Percentage</span>");
			 $("#save_contributionType").attr("disabled", "disabled");
			 return false; 
	 	 }
	  }if(selectedMethod =='Fixed'){
		  if(!nameErrorDiv)
			  $("#save_contributionType").removeAttr("disabled", "disabled");
		  }		  
}
</script>

<script>
	function addContributeMsgFadein() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script>
function isValidContributionType(){
	var deductionTypeId = $("#deductionTypeId").val();
	var deductionTypeName = $('#deductionTypeId').find(":selected").text();
	var isDisabled = $('#save_contributionType').is('[disabled=disabled]');
	$('#contributionNameDive').find('p.jquery_form_error_message').remove();
	if (deductionTypeId != null && deductionTypeId != '') {
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/isValidContributionType?"+tokenParamName+"="+tokenValue+"&deductionTypeId="+deductionTypeId,
			success : function(data) {
				if (data == true) {
// 					$("#save_contributionType").removeAttr("disabled");
			        $("#save_contributionType").attr("disabled", "disabled");
					$("#contributionNameDive").append( "<p class='jquery_form_error_message'>"+deductionTypeName+" is already Existed</span>");
					$('#contributionNameDive').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					if(!isDisabled)
						$("#save_contributionType").removeAttr("disabled");
					$('#contributionNameDive').find('p.jquery_form_error_message').remove();
// 					$("#save_contributionType").click(function() {
// 					});
					
				}
				
			},

		});
	}
}
</script>

<jsp:include page="../masterFooter.jsp" />
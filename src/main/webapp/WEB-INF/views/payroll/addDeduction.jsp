<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
 findAllDeductionType();
});

</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Deduction</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Add Deduction</div>
    </section>
    <div id="addDeductionLoading"></div>
    <form:form id="add_deduction_form" name="add_deduction_form" method="post">
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix form-horizontal minHeight_225">
        <div class="clearfix row">
          <div class="col-md-5">
           
            <div class="form-group">
              <label class="col-md-12 paddingRight">Deduction Type<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control chosen-select" id="deductionType" name="payrollDeductionType" data-validation="required" field-name="Deduction Type">
					</select>
				</div>
            </div>
            <div class="form-group">
              <label class="col-md-12">Method</label>
              <div class="col-md-12">
                <select class="form-control sel" id="payrollDeductionMethod" name="payrollDeductionMethod">
					    <option value = "">Select Method</option>
						<option  value = "Fixed">Fixed</option>
						<option  value = "Percent">Percent</option>
					</select>
              </div>
            </div>
			</div>
            <div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12">Amount per pay</label>
              <div class="col-md-12">
                <input type="text" class="form-control" id="deductionAmount" name="deductionAmount" data-validation="required validate_int" field-name="Amount">
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-12">Scheduled</label>
              <div class="col-md-12">
                <select class="form-control sel" id="payrollScheduled" name="payrollScheduled" data-validation="required" field-name="Scheduled">
					    <option value = "">Select Scheduled</option>
						<option  value = "EVERY_PAY">Every Pay</option>
						<option  value = "QUARTERLY">Quarterly</option>
						<option  value = "SEMI_ANNUALLY">Semi-Annually</option>
						<option  value = "ANNUALLY">Annually</option>
					</select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-12">Description</label>
              <div class="col-md-12">
               <textarea rows="2" class="form-control" id="description" name="description"></textarea>
              </div>
            </div>
        </div>
      </div>
</div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Save" class="btn btn-success btnSuccess" type="button" onclick="saveDeduction();">
            <input value="Cancel" class="btn btn-defualt ml10" type="button">
          </div>
        </div>
      </div>      
		
      <!-- Content Area Ends--> 
    </div>
    </form:form>
  </section>
  

<script>
function findAllDeductionType(){
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
		/* if(orgRelationId == opt.id){
			var selected = 'selected';
			output = output + "<option value='" +opt.payrollDeductionTypeName+ "'"+selected+">"+opt.payrollDeductionTypeName+"</option>";
		}else{ */
			output = output + "<option value='" +opt.payrollDeductionTypeName+ "'>"+opt.payrollDeductionTypeName+"</option>";
		//}
		
		}
		$("#deductionType").empty();
		$("#deductionType").append(output);
		$('#deductionType').trigger("chosen:updated");
	},
	});
}

</script>

<script>
function saveDeduction(){
	if ($('#add_deduction_form').validate(false, validationSettings)){  
 	 	    var formData = getFormData("add_deduction_form");
		        	  $("#addDeductionLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.post(sourceleadUrl+"/savePayrollDeduction?"+tokenParamName+"="+tokenValue, formData, function(data) {
		        		  $("#addDeductionLoading").removeAttr("style");
		        		  location.replace(sourceleadUrl+ "/payrollConfigure");
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

<jsp:include page="../masterFooter.jsp" />
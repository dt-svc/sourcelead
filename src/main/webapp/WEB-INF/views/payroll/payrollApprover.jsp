<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
$(document).ready(function(){
  getApproverList();
});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Approval</h2>
      </div>
      <div class="pull-right brud-crum">
                <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo;
                <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
                	<a href="<c:url value='${appPath}/indPayrollConfiguration'/>"> Payroll Configuration</a>
                </c:if>
                <c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
                	<a href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>
                </c:if>
                &raquo; Approval
       </div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <form:form id="payroll_approver_form" name="payroll_approver_form" method="post">
     <div id="approverLoading"></div>
      <div class="clearfix form-horizontal">
         <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Approvers</label>
				<div class="col-md-12">
					<select class="form-control chosen-select" multiple data-placeholder="Select Approvers" id="approver" name="approver" field-name="Approver" data-validation="required">
					</select>
				</div>
            </div>
            <input type="hidden" value='' name="confAprrover" id="confAprrover" />
			  </div>
            <div class="col-md-5">
            <div class="form-group">
         <label class="col-md-12">&nbsp;</label>
		<div class="col-md-12">
            <input value="Save" class="btn btn-success btnSuccess" type="button" onclick="savePayrollApprover();">
            <input value="Cancel" class="btn btn-defualt ml10" type="button" onclick="cancelButton();">
		</div>
            </div>
			</div>
      </div>
	</div>
	<div class="clearfix"></div>
    <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group clearfix">
            	<div class="row">
             <label class="col-md-12">&nbsp;</label>
              <label class="col-md-7">Automatic Payroll</label>
				<div class="col-md-5">
				<label class="switch">
			  <input checked="" type="checkbox" name="automaticPayroll" id="automaticPayroll">
			  <span class="slider round"></span>
			</label>
				</div>
            </div>
            </div>
		</div>
		</div>
		
		 <div class="clearfix row"> 
          <div class="col-md-5">
            <div class="form-group clearfix">
              <label class="clearfix">Payroll Frequency</label>
				<div class="clearfix">
					<select class="sel" id="payrollFrequency" name="payrollFrequency" field-name="Payroll Frequency" data-validation="required">
							<option value="Weekly">Weekly</option>
							<option value="Bi-Weekly">Bi-Weekly</option>
							<option value="Monthly">Monthly</option>
							<option value="Annually">Annually</option>
					</select>
				</div>
            </div>
			</div>
      </div>

         <div class="clearfix row"> 
          <div class="col-md-5">
            <div class="form-group clearfix">
              <label class="clearfix">Payroll Type</label>
				<div class="clearfix">
					<select class="sel" id="payrollType" name="payrollType" field-name="Payroll Type" data-validation="required">
						<option value="">Select</option>
						<option value="Work Sheet">Work Sheet</option>
						<option value="General">General</option>
					</select>
				</div>
            </div>
			</div>
      </div>
     </form:form>
      <!-- Content Area Ends--> 
    </div>
    
  </section>
  <!-- Footer Area -->
  
  <script>
  function getApproverList(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getPayrollApprover?"+tokenParamName+"="+tokenValue                 , 
		        success: function(data) {
		         var output = "";
		         var json = data.user;
				for (var i=0; i<json.length; i++)
				{
					var opt = json[i];
							if(opt.isPayRollApprover == 'Y'){
								output = output + "<option selected value='" +opt.id+ "'>"+opt.fullName+"  ("+opt.role+")</option>";
							}else{
								output = output + "<option value='" +opt.id+ "'>"+opt.fullName+"  ("+opt.role+")</option>";
							}
				}
				$("#approver").empty();
				$("#approver").append(output);
				$('#approver').trigger("chosen:updated");
				if(data.payrollType != null && data.payrollType != 'undefined'){
					 $("#payrollType").val(data.payrollType);
				}
				if(data.automaticPayroll != null && data.automaticPayroll != 'undefined'){
					if(data.automaticPayroll == 'NO'){
						$("#automaticPayroll").prop('checked', false); 
					} 
				}
				if(data.confAprrover != null && data.confAprrover != 'undefined'){
					$("#confAprrover").val(data.confAprrover);
				}
				if(data.automaticFrequency != null && data.automaticFrequency != ''){
					$("#payrollFrequency").val(data.automaticFrequency);
				}
		        },
		    });
  }
  </script>
  
  
  <script type="text/javascript">
function savePayrollApprover(){
		if ($('#payroll_approver_form').validate(false, validationSettings)) {
	/*   	var formData = getFormData('payroll_approver_form'); */
	  	var formData = $($('#payroll_approver_form')[0]).serialize();
		$("#approverLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/savePayrollApprover?"+tokenParamName+"="+tokenValue, formData, function(result) {
			if (result && result.response === "INVALID_DATA") {
    		    showError(result.responseMessage);
    		} else if (result.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (result.response === "SUCCESS") {
    			$("#approverLoading").removeAttr('style');
    			 location.replace(sourceleadUrl+ "//payrollApprover");
    		} else if (result.indexOf('Sign In') !== -1) {
    			location.replace(sourceleadUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
		}); 
		return true;
		} else {
			return false;
		}
}
$('body').on('blur', '#payroll_approver_form', function() {
	$("#payroll_approver_form").showHelpOnFocus();
	$("#payroll_approver_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
function cancelButton(){
	location.replace(sourceleadUrl+ "/payrollConfiguration");
}
</script>



<jsp:include page="../masterFooter.jsp" />


 		
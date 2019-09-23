<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div id="tab-6" class="tabsMenu_tabs_inn project_wrap noPad" style="display: block;">
<div class="reportHead clearfix">
	<ul class="pull-right list-unstyled list-inline">
		<li><a  onclick="showIndiaPaymentAccountPopup();"  class="newAdBtn mr5">+ Add</a></li>
	</ul>
</div>
<div class="clearfix project_tabs_inn_table overflow_Auto">
	<table width="100%">
		<thead>
			<tr>
			  <th>Type</th>           
			  <th>Bank Name</th>         
			  <th>Account Number</th>     
			  <th>Routing Number</th>     
			  <th>First Name</th>        
			  <th>Last Name</th>         
			  <th>Default</th>           
			  <th>Active</th>           
			  <th>Action</th>                  
			</tr>
		</thead>
		<tbody>
		<c:set var="hasCash" value="0" scope="page" />
		<c:set var="hasCheque" value="0" scope="page" />
		<c:if test="${paymentListSize > 0}">
		   <c:forEach var="payment" items="${paymentList}">
			   	<c:if test="${payment.accountType == 'CASH'}">
			   		<c:set var="hasCash" value="${hasCash + 1}" scope="page"/>
			   	</c:if>
			   	<c:if test="${payment.accountType == 'CHEQUE'}">
			   		<c:set var="hasCheque" value="${hasCheque + 1}" scope="page"/>
			   	</c:if>
				<tr>
					<td><div class="tblminwidth-50">${!empty payment.accountType ? payment.accountType : "--"}</div></td>
				    <td><div class="tbldata-dotdot-100" title="${payment.bankName}">${!empty payment.bankName ? payment.bankName : "--"}</div></td>
				    <td><div class="tblminwidth-30" title="${payment.accountNumber}" >${!empty payment.accountNumber ? payment.accountNumber : "--"}</div></td>
				    <td><div class="tblminwidth-30" title="${payment.routingNumber}">${!empty payment.routingNumber ? payment.routingNumber : "--"}</div></td>
				    <td><div class="tbldata-dotdot-100" title="${payment.firstName}">${!empty payment.firstName ? payment.firstName : "--"}</div></td>
					<td><div class="tbldata-dotdot-100" title="${payment.lastName}">${!empty payment.lastName ? payment.lastName : "--"}</div></td>
					<td><div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeDefaultPaymentAccount(this, '${payment.id}')" type="checkbox" ${payment.defaultAccount ?  'checked' : ''}/> <div class="slider round" style="position:absolute !important;"></div> </label> </div></div> </td>
					<td><div class="tblminwidth-50"><div class="toggle-button"><label class="switch" style="top: 5px;"><input onclick="changeActivePaymentAccount('${payment.id}')" type="checkbox" ${!payment.disabled ?  'checked' : '' }/> <div class="slider round" style="position:absolute !important;"></div> </label> </div></div></td>
					<td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="editPaymentAccount('${payment.accountType}','${payment.id}','${payment.orgLocationId}','${payment.firstName}','${payment.lastName}','${payment.accountNumber}','${payment.routingNumber}','${payment.bankName}','${payment.bsrNumber}','${payment.micrCode}')">Edit</a></div></td>
				</tr>
			</c:forEach>
		</c:if>
		</tbody>
		</table>
		<c:if test="${paymentListSize == 0}">
		 <div class='row'> 
			 <div class='col-lg-12'> 
				 <div class='dataTables_empty'>
				 </div>
			 </div>
		 </div>
		</c:if>
		</div>
</div>


<div class="modal paymentaccount-modal" id="paymentAccountPopup" role="dialog">
    <div class="modal-dialog modal-lg">
     <div id="accountDetailsLoading"></div>
      <!-- Modal content-->
      <form:form id="associate_bankingDetails_form" name="associate_bankingDetails_form" method="post">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="closePaymentAccountPopup();" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add/Edit Payment Account</h4>
        </div>
        <div class="modal-body minHeightcol5">
		   <input type="hidden" name="paymentAccountId" id="paymentAccountId"/>
		   <div class="clearfix row minHeightcol5">
		 		<div class="col-md-5" id="accountTypeDiv">
		                <label>Payment Mode<span class="text-danger">&nbsp;*</span></label>
		                <div>
		                  <select class="form-control" id="accountType" name="accountType" data-validation="required" field-name="Payment Mode" onChange="changeAccType();">
		                        <option value="">--Select Payment Mode--</option>
			                    <option value="SAVINGS" data-type="1">Savings</option>
			                    <option value="CHEQUE" data-type="2">Cheque</option>
			                    <option value="CASH" data-type="3">Cash</option>
		                  </select>
		                </div>
		         </div>
		         
		         <div class="col-md-5" id="bankNameDiv">
		                  <label>Bank Name<span class="text-danger">&nbsp;*</span></label>
		                  <div>
		                   <input type="text" id="bankName" name="bankName"  class="form-control" data-validation="required validate_Space validate_length length1-50" field-name="Bank Name"/>
		                  </div>
		          </div>
		          
		          <div class="col-md-5" id="accountNumberDiv">
		                  <label>Account Number<span class="text-danger">&nbsp;*</span></label>
		                  <div>
		                   <input type="text" id="accountNumber"  field-name="Account Number"  name="accountNumber" class="form-control" data-validation="required validate_Space validate_int validate_length length9-15"/>
		                  </div>
		          </div>
		          
		          <div class="col-md-5" id="bsrNumberDiv">
		                  <label>BSR Number</label>
		                  <div>
		                   <input type="text" id="bsrNumber"  field-name="BSR Number"  name="bsrNumber" class="form-control" data-validation="validate_Space validate_int validate_length length4-15" data-validation-optional="true"/>
		                  </div>
		          </div>
		          
		          <div class="col-md-5" id="micrCodeDiv">
		                  <label>MICR Code</label>
		                  <div>
		                  <input type="text" id="micrCode"  field-name="MICR Code" name="micrCode" class="form-control" data-validation="validate_Space validate_int validate_length length9-15" data-validation-optional="true"/>
		                  </div>
		          </div>
		          
		          <div class="col-md-5" id="routingNumberDiv">
		                  <label>IFSC Code<span class="text-danger">&nbsp;*</span></label>
		                  <div>
		                  <input type="text" id="routingNumber" field-name="IFSC Code" name="routingNumber" class="form-control" data-validation="required validate_Space validate_Alphanumeric fixed_length length11"/>
		                  </div>
		          </div>
		          
		          <div class="col-md-5" id="firstNameDiv">
		                  <label>First Name<span class="text-danger">&nbsp;*</span></label>
		                  <div>
		                  <input type="text" id="firstName" name="firstName" pattern="[A-Za-z\s]*" field-name="First Name" class="form-control" value="${canFirstName}" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100"/>
		                  </div>
		          </div>
		          
		          <div class="col-md-5" id="lastNameDiv">
		                  <label>Last Name<span class="text-danger">&nbsp;*</span></label>
		                  <div>
		                  <input type="text" id="lastName" name="lastName" pattern="[A-Za-z\s]*" field-name="Last Name" class="form-control" value="${canLastName}" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100"/>
		                  </div>
		          </div>
		          
		            <div class="clearfix"></div>
		  </div>
          
        </div>
        <div class="clearfix"></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success btnSuccess" id="savePaymentButton" onclick="savePaymentAccount();" >Save</button>
          <button type="button" class="btn btn-default ml10" onclick="closePaymentAccountPopup();" >Cancel</button>
        </div>
      </div>
       </form:form>
    </div>
  </div>

<script type="text/javascript">
 function savePaymentAccount(){
	 var userId = '${userId}';
	 var candidateId = '${candidateId}';
		if ($('#associate_bankingDetails_form').validate(false, validationSettings)) {
			var formData = getFormData("associate_bankingDetails_form");
			$("#accountDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
				 type : "POST",
				 data: formData,
			     url : sourceleadUrl+"/save-ind-associate-payment-account?userId="+userId,
				 success : function(result) {
					 if (result && result.response === "INVALID_DATA") {
							$("#accountDetailsLoading").removeAttr("style");
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	 var paymentAccount = result.object;
		                	 $("#paymentAccountId").val(paymentAccount.paymentAccountId);
						     $("#accountDetailsLoading").removeAttr("style");
						     closePaymentAccountPopup();
						     associateBankingDetails(candidateId);
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
				  		
				 }
			});
		}
	}
 
   $('body').on('blur', '#associate_bankingDetails_form', function() {
		$("#associate_bankingDetails_form").showHelpOnFocus();
		$("#associate_bankingDetails_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>

<script>
function editPaymentAccount(type, id, locationId, firstName, lastName, accountNumber, routingNumber, bankName,bsrNumber,micrCode) {
	$(".modal.paymentaccount-modal").fadeIn();
	$(".comMask").fadeIn();
	$("#paymentAccountId").val(id);
	$("select[name=accountType]").val(type);
	$("#firstName").val(firstName);
	$("#lastName").val(lastName);
	$("#bankName").val(bankName);
	$("#accountNumber").val(accountNumber);
	$("#bsrNumber").val(bsrNumber);
	$("#micrCode").val(micrCode);
	$("#routingNumber").val(routingNumber);
	changeAccType();
}

function clearPaymentAccount() {
	$("#paymentAccountId").val('');
	$("select[name=accountType]").val('');
	$("#firstName").val('');
	$("#lastName").val('');
	$("#bankName").val('');
	$("#accountNumber").val('');
	$("#bsrNumber").val('');
	$("#micrCode").val('');
	$("#routingNumber").val('');
	
	$('#accountTypeDiv').find('p.jquery_form_error_message').remove();
	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
	$('#lastNameDiv').find('p.jquery_form_error_message').remove();
	$('#bankNameDiv').find('p.jquery_form_error_message').remove();
	$('#accountNumberDiv').find('p.jquery_form_error_message').remove();
	$('#bsrNumberDiv').find('p.jquery_form_error_message').remove();
	$('#micrCodeDiv').find('p.jquery_form_error_message').remove();
	$('#routingNumberDiv').find('p.jquery_form_error_message').remove();
}

</script>

<script>
function changeDefaultPaymentAccount(ele, id) {
	candidateId = '${candidateId}';
    $.post(sourceleadUrl+"/change-associate-default-payment-account", {id: id, candidateId: candidateId, defaultAccount: $(ele).is(":checked")}, function(data) {
    	 var candidateId = '${candidateId}';
    	 associateBankingDetails(candidateId);
        showError(data.responseMessage);
    });
}
function changeActivePaymentAccount(id){
	 var candidateId = '${candidateId}';
	 $.post(sourceleadUrl+"/change-active-payment-account", {id: id}, function(data) {
	        showError(data.responseMessage);
	        associateBankingDetails(candidateId);
	    });
}
</script>

<script>
var sourceleadUrl = '${sourceleadUrl}';
function showIndiaPaymentAccountPopup() {
    $(".modal.paymentaccount-modal").fadeIn();
    $(".comMask").fadeIn();
    if('${hasCheque}' > 0){
    	$("#accountType option[value='CHEQUE']").attr("disabled",true);
    }else{
    	$("#accountType option[value='CHEQUE']").attr("disabled",false);
    }
	if('${hasCash}' > 0){
		$("#accountType option[value='CASH']").attr("disabled",true);
    }else{
    	$("#accountType option[value='CASH']").attr("disabled",false);
    }
	changeAccType();
}
</script>

<script>
function closePaymentAccountPopup() {
    $(".modal.paymentaccount-modal").fadeOut();
    $(".comMask").fadeOut();
    clearPaymentAccount();
}
</script>

<script>
function changeAccType()
	{
 		if($('#accountType').val() == 'CHEQUE' || $('#accountType').val() == 'CASH'){
 			$('#bankNameDiv').hide();
 			$('#accountNumberDiv').hide();
 			$('#bsrNumberDiv').hide();
 			$('#micrCodeDiv').hide();
 			$('#routingNumberDiv').hide();
 			$('#firstNameDiv').hide();
 			$('#lastNameDiv').hide();
 			
 			$('#bankName').val('');
 			$('#accountNumber').val('');
 			$('#bsrNumber').val('');
 			$('#micrCode').val('');
 			$('#routingNumber').val('');
 			$('#firstName').val('');
 			$('#lastName').val('');
 			
 			$('#bankName').attr('data-validation-optional','true');
 			$('#accountNumber').attr('data-validation-optional','true');
 			$('#routingNumber').attr('data-validation-optional','true');
 			$('#firstName').attr('data-validation-optional','true');
 			$('#lastName').attr('data-validation-optional','true');
 		}else{
 			$('#bankNameDiv').show();
 			$('#accountNumberDiv').show();
 			$('#bsrNumberDiv').show();
 			$('#micrCodeDiv').show();
 			$('#routingNumberDiv').show();
 			$('#firstNameDiv').show();
 			$('#lastNameDiv').show();
 			
 			$('.col-md-5').find('p.jquery_form_error_message').remove();
 			
 			$('#bankName').removeAttr('data-validation-optional');
 			$('#accountNumber').removeAttr('data-validation-optional');
 			$('#routingNumber').removeAttr('data-validation-optional');
 			$('#firstName').removeAttr('data-validation-optional');
 			$('#lastName').removeAttr('data-validation-optional');
 		}
	}
</script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<style>
.edit_member_pop {
	top:12% !important; left: 35%; margin: 0px auto; z-index: 15500; width: 520px;
	position: fixed !important; background-color: #f9f9f9; box-shadow: 0px 0px 5px rgba(0,0,0,0.3); padding: 15px;
}
.edit_member_pop .footerBtns input {
    width: 50%; border: none; height: 30px; font-size: 14px; font-weight: 500; color: #FFF; background-color: #ff4b4b;
}
.edit_member_pop .footerBtns input.u_can_color {
    background-color: #375d81;
}
.edit_member_pop h4 {
background-color: #dcdcdc; padding: 10px; margin: 0; margin-bottom: 15px; font-size: 15px;
}
</style>


<div class="clearfix styleTables mt">
						<table width="100%">
							<thead>
								<tr>
								    <th>Bank Name</th>
									<th>Account type</th>
									<th>Account Name</th>
									<th>Account Number</th>
									<th>Routing Number</th>
									<th>Address</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${bankingDetailsDTOListSize > 0}">
									
										<c:forEach var="bankDetails" items="${bankingDetailsDTOList}">
										<tr>
												<td>${!empty bankDetails.bankName ? bankDetails.bankName : '---'}</td>
											    <td>${bankDetails.accountType}</td>
												<td>${bankDetails.accountName}</td>
												<td>${bankDetails.accountNumber}</td>
												<td>${bankDetails.routingNumber}</td>
												<td class="tbldata-dotdot mt" title="${!empty bankDetails.bankAddress ? bankDetails.bankAddress : ''}">${!empty bankDetails.bankAddress ? bankDetails.bankAddress : '---'}</td>
												<td>
													<ul class="list-inline list-unstyled">
														<li><a href="javascript:void(0)" onclick="addNewBankingDetails('${bankDetails.uuid}');"><i class="fa fa-pencil"></i></a></li>
														<li><a href="javascript:void(0)" onclick="deleteBankingDetails('${bankDetails.uuid}');"><i class="fa fa-trash text-danger"></i></a></li>
													</ul>
													
												</td>
										</tr>
										</c:forEach>
										
									
									
								</c:if>
							</tbody>
						</table>
						<c:if test="${bankingDetailsDTOListSize == 0}">
						 <div class='row'> 
							 <div class='col-lg-12'> 
								 <div class='dataTables_empty'>
								 </div>
							 </div>
						 </div>
						</c:if>
					</div>
					
					
					
	<script>
 function addNewBankingDetails(uuid){
	 var result = "";
		result = result +"	<div class='project_inn_mask'></div>"; 
		result = result +" <div id='edit_member_pop' class='edit_member_pop'>";
		result = result +"	<div id='detailsLoading'></div>"; 
		result = result +"	<h4>Add Banking Details</h4>";
		result = result + '<div class="form-horizontal clearfix">';
		result = result + '<form id="bankingDetails_form" name="bankingDetails_form">';
		result = result + '<input type="hidden" id="uuid" name="uuid" value="">';
		
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Bank Name<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="bankName" name="bankName" field-name="Bank Name"  data-validation="required validate_Space validate_length length2-250">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    <div class="clearfix mb10 col-md-6 ">';
		result = result + '	    <label class="clearfix">Account Type<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="dropdown-select">';
		result = result + '		<select id="accountType" name="accountType" field-name="Account Type" data-validation="required">';
		result = result + '		<option value="">--Select--</option>';
		result = result + '		<option value="Savings">Savings</option>';
		result = result + '		<option value="Checking">Checking</option>';
		result = result + '		</select>';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Account Name<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="accountName" name="accountName" field-name="Account Name"  data-validation="required validate_Space validate_length length2-250">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Account Number<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="accountNumber" name="accountNumber" field-name="Account Number"  data-validation="required validate_Space validate_int validate_length length9-18">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		result = result + '	    <div class="row">';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Address<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="bankAddress" name="bankAddress" field-name="Address"  data-validation="required validate_space">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    <div class="clearfix mb10 col-md-6">';
		result = result + '	      <label class="clearfix">Routing Number<span class="text-danger">&nbsp;*</span></label>';
		result = result + '	      <div class="">';
		result = result + '	        <input type="text" class="form-control inlineField"  id="routingNumber" name="routingNumber" field-name="Routing Number"  data-validation="required validate_space validate_length length9-9" maxlength="9">';
		result = result + '	      </div>';
		result = result + '	    </div>';
		
		result = result + '	    </div>';
		
		
		result = result + ' <div id="reasonForProjectDiv" class="footerBtns">';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" class="u_can_color" type="submit" value="Save"><input  type="button"  value="Cancel" onClick="cancelAddDetails();"></b>';
		result = result +"	</div>";
		result = result +"	</div>";
		
		result = result + '</form>';
		
		
		 $("#bankingDetailsPopUp").append(result);
         $("#edit_member_pop").slideDown(200);
         $(".project_inn_mask").fadeIn(200); 
         
         
		if(uuid != null && uuid != 'undefined' && uuid != ''){
			 $("#detailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  setTimeout(function () {
				  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				    	  url: sourceleadUrl+"/getOrgLocationsBankingDetailsByUUId?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,		
				        success: function(result) {
				        	if(result.bankName != null && result.bankName != '' && result.bankName != 'undefined'){
				        		$("#bankName").val(result.bankName);
				        	}
				        	
				        	$("#bankName").val(result.bankName);
				        	$("#accountType").val(result.accountType);
				        	$("#accountName").val(result.accountName);
				        	$("#routingNumber").val(result.routingNumber);
				        	$("#bankAddress").val(result.bankAddress);
				        	$("#accountNumber").val(result.accountNumber);
				        	$("#uuid").val(result.uuid);
				        	
				        },
				    });  
				  
				  $("#detailsLoading").removeAttr('style');
		 			}, 1000);
		}
 }
 
 function cancelAddDetails(){
		$("#bankingDetailsPopUp").empty();
		 $(".project_inn_mask").fadeOut(200);
		 $(".vendor_wrap_mask").fadeOut(200);
	}
 
 </script>				
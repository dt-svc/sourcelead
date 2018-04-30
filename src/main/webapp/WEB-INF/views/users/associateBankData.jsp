 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  
  <div id="loading"></div> 
  <div id="tab-2" class="tabsMenu_tabs_inn clearfix">
            <div class="clearfix row minHeightcol5">
                 <form:form id="associate_bank_data_form"  name="associate_bank_data_form" method="post">
                 <div class="col-md-5">
                  <label>Account Type<span class="text-danger">&nbsp;*</span></label>
                  <div>
                    <select class="form-control chosen-select" id="accountType" name ="accountType" data-validation="required" field-name="Account Type">
                      <option value="">Bank Transfer</option>
                      <option value="Direct Deposit">Direct Deposit</option>
                      <option value="Cheque">Cheque</option>
                      <option value="Cash">Cash</option>
                    </select>
                  </div>
                </div>
               
                 <div class="col-md-5">
                  <label>Bank Name<span class="text-danger">&nbsp;*</span></label>
                  <div>
                    <input type="text" class="form-control" id="bankName" name="bankName" value="" data-validation="required validate_Space validate_length length2-100" field-name="Bank Name">
                  </div>
                </div>

               <div class="col-md-5">
                  <label>Account Number<span class="text-danger">&nbsp;*</span></label>
                  <div>
                    <input type="text" class="form-control" id="accountNumber" name="accountNumber" value="" data-validation="required validate_Space validate_int validate_length length2-100" field-name="Account Number">
                  </div>
                </div>
                
                <div class="col-md-5">
                  <label>Routing Number<span class="text-danger">&nbsp;*</span></label>
                  <div>
                    <input type="text" class="form-control" id="routingNumber" name="routingNumber" value="" data-validation="required validate_space validate_int validate_length length9-9" field-name="Routing Number">
                  </div>
                </div>
                
                 <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                  <div class="col-md-6">
                 <a href="javascript:void(0)" class="anchorBlack" onclick="skipAssociateBankDataJsp();">Skip</a>
			     </div> 
                  <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" id="save_associate_bank_data_button" onclick="saveAssociateBankData();" type="button">
                    <input value="Cancel" onclick="javascript:window.location.href = '/app/usersList'" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
              </form:form>
            </div>
          </div>
          
          
<script type="text/javascript">
 function saveAssociateBankData() {
	 var userId = $("#associateId").val();
		 if ($('#associate_bank_data_form').validate(false, validationSettings)){ 
			   var formData = getFormData("associate_bank_data_form");
				$("#save_associate_bank_data_button").attr("disabled", "disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	            $( "#save_associate_bank_data_button" ).addClass('button').val('Processing..');
	            
	            $.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/saveAssociateBankData?"+tokenParamName+"="+tokenValue+"&userId="+userId,
					success : function(result) {
						if (result && result.response === "INVALID_DATA") {
		                    $("#save_associate_bank_data_button").removeAttr("disabled");
		                    $("#save_associate_bank_data_button").removeAttr('button').val('Save');
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	/* $("#associateId").val(result.object.userId); */
							var associateBanking = document.getElementById("associateTax");
							document.getElementById("associateTax").removeAttribute("class"); 
							associateTax.href="#tab-3"; 
							$("#associateTax").click();
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    $("#save_associate_bank_data_button").removeAttr("disabled");
		                    $("#save_associate_bank_data_button").addClass('button').val('Save');
		                    showError("Operation failed, please try again");
		                }
							/*  location.replace(sourceleadUrl+"/usersList");  */
					},
				});
	            
		        return true;
			
		  }else{
			return false;
		  }  
	
    }
 
   $('body').on('blur', '#associate_bank_data_form', function() {
		$("#associate_bank_data_form").showHelpOnFocus();
		$("#associate_bank_data_form").validateOnBlur(false, validationSettings);  
   });
  
</script>  
<script>
function skipAssociateBankDataJsp(){
	var associateBanking = document.getElementById("associateTax");
	document.getElementById("associateTax").removeAttribute("class"); 
	associateTax.href="#tab-3"; 
	$("#associateTax").click();
}
</script>
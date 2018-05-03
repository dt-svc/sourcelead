  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
 
 <script>
function associateBankData(userId){  
	$.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateBankDataByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            	var result = "";
            	result = result + '<div id="editTest" class="col-md-12">';
            	result = result + '<a onclick="editAssociateBankDataFun('+userId+');" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
            	result = result + '</div>';
            	result = result + ' <div id="test" class="user_details_cta user_detailsCta">';
            	result = result + '<div class="project_details_cta"> <strong>Account Type:</strong>';
            	//result = result + ' <input type="text" value='+json.accountType+' placeholder="Project  class="project_detfield">';
            	result = result + '  <span>'+json.accountType+'</span>';
            	result = result + '  <div class="clearfix"></div>';
            	result = result + ' </div>';
            	result = result + ' <div class="project_details_cta"> <strong>Bank Name:</strong>';
            	result = result + '  <span>'+json.bankName+'</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '<div class="project_details_cta"> <strong>Account Number:</strong>';
                result = result + '  <span>'+json.accountNumber+'</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '<div class="project_details_cta"> <strong>Routing Number:</strong>';
                 result = result + '  <span>'+json.routingNumber+'</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '</div>';
                result = result + '<div id="finalTest"></div>';
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-21").append(result); 
          },
            
       }); 
  } 
</script>

<input type="hidden"/>
<script>

function editAssociateBankDataFun(userId){
	  $.ajax({
          type : "GET",
          url: sourceleadUrl+"/findAssociateBankDataByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
           success :function(json) {
        	   var result = "";
        	   result += '<div class="clearfix row minHeightcol5 col-md-12 mt">';
              result += '<form id="associate_bank_data_form"  name="associate_bank_data_form" method="post">'; 
              result += '<input type="hidden" value='+json.id+' name="id" id="id"/>';
               result += '<div class="col-md-5">';
               result += ' <label>Account Type<span class="text-danger">&nbsp;*</span></label>';
               result += ' <div class="clearfix">';
               result += '   <select class="form-control sel" id="accountType" name ="accountType" data-validation="required" field-name="Account Type">';
              /*  <option ${payrollDeduction.payrollDeductionMethod == 'Fixed' ? 'selected' : ''} value = "Fixed">Fixed</option> */
               result += '     <option value="">Bank Transfer</option>';
               result += '     <option '+ (json.accountType == "Direct Deposit" ? "selected" : '') +' value="Direct Deposit">Direct Deposit</option>';
               result += '     <option '+ (json.accountType == "Cheque" ? "selected" : '') +' value="Cheque">Cheque</option>';
               result += '     <option '+ (json.accountType == "Cash" ? "selected" : '') +' value="Cash">Cash</option>';
               result += '   </select>';
               result += ' </div>';
               result += '</div>';
               result += '<div class="col-md-5">';
               result += ' <label>Bank Name<span class="text-danger">&nbsp;*</span></label>';
               result += ' <div>';
               result += '   <input type="text" class="form-control" id="bankName" name="bankName" value='+json.bankName+' data-validation="required validate_Space validate_length length2-100" field-name="Bank Name">';
               result += ' </div>';
               result += '</div>';
               result += '<div class="col-md-5">';
               result += ' <label>Account Number<span class="text-danger">&nbsp;*</span></label>';
               result += ' <div>';
               result += '   <input type="text" class="form-control" id="accountNumber" name="accountNumber" value='+json.accountNumber+' data-validation="required validate_Space validate_int validate_length length2-100" field-name="Account Number">';
               result += ' </div>';
               result += '</div>';
               result += '<div class="col-md-5">';
               result += ' <label>Routing Number<span class="text-danger">&nbsp;*</span></label>';
               result += ' <div>';
               result += '   <input type="text" class="form-control" id="routingNumber" name="routingNumber" value='+json.routingNumber+' data-validation="required validate_space validate_int validate_length length9-9" field-name="Routing Number">';
               result += ' </div>';
               result += '</div>';
               
               
              result += '<div class="clearfix"></div>';
                 result += '<div class="clearfix footerstyelsNew">';
                  result += ' <div class="col-md-10">';
                result += '  <div class="pull-right">';
               result += '   <input value="Save" class="btn btn-success btnSuccess" id="save_associate_bank_data_button" onclick="saveAssociateBankData('+userId+');" type="button">';
                 result += '   <input value="Cancel" onclick="javascript:window.location.href = "/app/usersList"" class="btn btn-default ml10" type="button">';
                 result += '   </div>';
               result += ' </div>';
               result += '</div>';
               result += '</form>';
               result += '</div>'; 
        	   
        	  $("#editTest").hide();
         	  $("#test").hide();
         	  $("#finalTest").show();
         	  $("#finalTest").append(result);
           },
	  });
}
</script>
 

 <script type="text/javascript">
 function saveAssociateBankData(userId) {
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
		                	associateBankData(userId);
		                	 $("#editTest").show();
		                	  $("#test").show();
		                	  $("#finalTest").hide();
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

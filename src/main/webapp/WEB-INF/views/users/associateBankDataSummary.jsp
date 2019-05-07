  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 

 <script>
function associateBankData(userId){  
	$.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateBankDataByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            	  var result = "";
            	  result += '<div class="reportHead clearfix">';
            		  result += '<ul class="pull-right list-unstyled list-inline">';
            			  result += '<li><a onclick="addAssociateBankPopUp();" class="newAdBtn mr5">+ Add</a></li>';
            				  result += '</ul>';
            					  result += '</div> ';
            					  
     	    	 result += '<table id="associateBankDataList" class="display" cellspacing="0" width="100%" >';
     	         result += '<thead>';
     	         result += '  <tr>';
     	         result += '    <th>Account Type</th>';
     	         result += '    <th>Bank Name</th>';
     	         result += '    <th>Account Number</th>';
     	         result += '    <th>Routing Number</th>';
     	      	result += '    <th>Active</th>';
     	      	result += '    <th>Action</th>';
     	         result += '  </tr>';
     	         result += '</thead>';
     	         result += '<tbody>';
     	    	 for(i=0;i < json.length;i++){
     	    		 var report = json[i];
     	    		 result += '<tr>';
     	    		 if(report.accountType != null){
     	    			result += '<td>'+report.accountType+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		if(report.bankName != null){
     	    			result += '<td>'+report.bankName+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		if(report.accountNumber != null){
     	    			result += '<td>'+report.accountNumber+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		if(report.routingNumber != null){
     	    			result += '<td>'+report.routingNumber+'</td>';
					 }else{
						 result += '<td>--</td>';
					 }
     	    		 
     		        result += '<td>';
					result += '<label class="switch">';
					if(report.defaultAccount == true){
						result += '<input type="checkbox" checked class="toggle-button" id="defaultAccount'+report.id+'" name="defaultAccount" onclick="changeActiveAccount('+report.id+','+report.candidate.id+','+userId+')">';
					}else{
						result += '<input type="checkbox" class="toggle-button" id="defaultAccount'+report.id+'" name="defaultAccount" onclick="changeActiveAccount('+report.id+','+report.candidate.id+','+userId+')">';
					}
					result += '<div class="slider round"></div>';
					result += '</label>';
					result += '</div>';
					result += '</td>'; 
					 result += '<td>';
						result += '<div class="tblminwidth-50">';
						result += '<div title="Edit" class="paddingRight pull-left"><a herf="#"><i class="fa fa-pencil text-primary cursorPointer" onclick="editBankDetail(\''+report.associateBankUuid+'\')"></i></a></div>';
						result += '</td>'; 
					     result += '  </tr>';
     	    	 }
     	    	
     	         result += '  </tbody>';
         		 result += '</table> ';
         		 
            	/* var result = "";
            	result = result + '<div id="editTest" class="col-md-12">';
            	result = result + '<a onclick="editAssociateBankDataFun('+userId+');" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
            	result = result + '</div>';
            	result = result + ' <div id="test" class="user_details_cta user_detailsCta">';
            	result = result + '<div class="project_details_cta"> <strong>Account Type:</strong>';
            	//result = result + ' <input type="text" value='+json.accountType+' placeholder="Project  class="project_detfield">';
            	if(json.accountType != null && json.accountType != '' && json.accountType != 'undefined')
            		result = result + '  <span>'+json.accountType+'</span>';
            	else
            		result = result + '  <span>---</span>';
            	result = result + '  <div class="clearfix"></div>';
            	result = result + ' </div>';
            	result = result + ' <div class="project_details_cta"> <strong>Bank Name:</strong>';
            	if(json.bankName != null && json.bankName != '' && json.bankName != 'undefined')
            		result = result + '  <span>'+json.bankName+'</span>';
            	else
            		result = result + '  <span>---</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '<div class="project_details_cta"> <strong>Account Number:</strong>';
                if(json.accountNumber != null && json.accountNumber != '' && json.accountNumber != 'undefined')
            		result = result + '  <span>'+json.accountNumber+'</span>';
            	else
            		result = result + '  <span>---</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '<div class="project_details_cta"> <strong>Routing Number:</strong>';
                if(json.routingNumber != null && json.routingNumber != '' && json.routingNumber != 'undefined')
            		result = result + '  <span>'+json.routingNumber+'</span>';
            	else
            		result = result + '  <span>---</span>';
                result = result + '<div class="clearfix"></div>';
                result = result + '</div>';
                result = result + '</div>';
                result = result + '<div id="finalTest"></div>'; */
                $("#tab-18").empty(); 
                $("#tab-17").empty(); 
                $("#tab-19").empty(); 
                $("#tab-20").empty(); 
                $("#candidateSideDiv").empty(); 
                $("#tab-20").empty();
                $("#tab-21").empty(); 
                $("#tab-25").empty();
                $("#tab-21").append(result); 
                assoBankDatapagination();
          },
            
       }); 
  } 
</script>

<script>

function assoBankDatapagination(){
		$('#associateBankDataList').DataTable({ 
			"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"searching": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	}
	    });
		$("#associateBankDataList").wrap("<div class='overflow_Auto require_wrap_table' style='width:100%;'></div>");  

	}
</script>


<script type="text/javascript">
function changeActiveAccount(associateBankId,candidateId,userId) {
	  $.ajax({
          type : "GET",
          url: sourceleadUrl+"/changeActiveAccount?"+tokenParamName+"="+tokenValue+"&associateBankId="+associateBankId+"&candidateId="+candidateId,    
           success :function(json) {
        	   associateBankData(userId);
           },
	  });
	
}
</script>
<div class="modal" id="adEdBankingDetails" style="display:none;">
    <div class="modal-dialog modal-lg">
     <div id="accountDetailsLoading"></div>
      <!-- Modal content-->
      <form:form id="associate_bank_data_form" name="associate_bank_data_form" method="post">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="closeadEdBankingDetailsPop();" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add/Edit Banking Details</h4>
        </div>
        <div class="modal-body minHeightcol5">
		   <input type="hidden" name="paymentAccountId" id="paymentAccountId"/>
		   <div class="clearfix row minHeightcol5">
		 		<div class="col-md-6" id="">
		                <label>Account Type<span class="text-danger">&nbsp;*</span></label>
		                <div>
		                  <select class="sel width100Percnt" id="accountType" name ="accountType" data-validation="required" onchange="showHideAccountTypes(); resetValues();" field-name="Account Type">
		                        <option value="">--Select Account Type--</option>
			                    <option value="Direct Deposit" data-type="1">Direct Deposit</option>
			                    <option value="Cheque" data-type="2">Cheque</option>
			                     <option value="Cash" data-type="3">Cash</option>
		                  </select>
		                  <input type="hidden" name="id" id="id"/>
		                </div>
		         </div>
		         
		         <div class="col-md-6" id="bankNameDiv">
		                  <label>Bank Name<span id="bankNameSpanId" class="text-danger">&nbsp;*</span></label>
		                  <div>
		                   <input type="text" class="form-control" id="bankName" name="bankName" data-validation="required validate_Space validate_length length2-100" field-name="Bank Name"/>
		                  </div>
		          </div>
		          <div class="clearfix"></div>
		          <div class="col-md-6" id="">
		                  <label>Account Number<span id="accountNumberSpanId" class="text-danger">&nbsp;*</span></label>
		                  <div>
		                   <input type="text" class="form-control" id="accountNumber" name="accountNumber" data-validation="required validate_Space validate_int validate_length length2-100" field-name="Account Number"/>
		                  </div>
		          </div>
		          
		          <div class="col-md-6" id="">
		                 <label>Routing Number<span id="routingNumberSpanId" class="text-danger">&nbsp;*</span></label>
		                  <div>
		                   <input type="text" class="form-control" id="routingNumber" name="routingNumber"  data-validation="required validate_space validate_int validate_length length9-9" field-name="Routing Number"/>
		                  </div>
		          </div>
		            <div class="clearfix"></div>
		  </div>
          
        </div>
        <div class="clearfix"></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success btnSuccess" id="save_associate_bank_data_button" onclick="saveAssociateBankData();">Save</button>
          <button type="button" class="btn btn-default ml10" onClick="closeadEdBankingDetailsPop();">Cancel</button>
        </div>
      </div>
       </form:form>
    </div>
  </div>
  
  <script>
  
  function editAssociateBankPopUp(){
	  $("#adEdBankingDetails, .mask").fadeIn();
	  $("#save_associate_bank_data_button").removeAttr("disabled");
  }
  
  function addAssociateBankPopUp(){
	  $("#adEdBankingDetails, .mask").fadeIn();
	  $("#save_associate_bank_data_button").removeAttr("disabled");
	  resetValuesWhileAdd();
  }
  
  	function closeadEdBankingDetailsPop(){
  		$("#adEdBankingDetails, .mask").fadeOut();
  	}
  </script>
  
  
  
  
  <script>
  function editBankDetail(associateBankUUID){
	  $.ajax({
          type : "GET",
          url: sourceleadUrl+"/findAssociateBankDataByUUID?"+tokenParamName+"="+tokenValue+"&associateBankUUID="+associateBankUUID,    
           success :function(json) {
        	   $("#id").val(json.id);
        	   $("#accountType").val(json.accountType);
        	   $("#bankName").val(json.bankName);
        	   $("#accountNumber").val(json.accountNumber);
        	   $("#routingNumber").val(json.routingNumber);
        	   editAssociateBankPopUp();
        	   showHideAccountTypes();
           },
	  });
  }
  </script>
  
<script>
function showHideAccountTypes(){
	var accountType = $("#accountType").val();
	if(accountType == "Direct Deposit"){
		$("#bankName").val('');
		$("#accountNumber").val('');
		$("#routingNumber").val('');
		
		$("#bankName").removeAttr('data-validation-optional');
		$('#bankName').attr('data-validation',"required");
		$("#accountNumber").removeAttr('data-validation-optional');
		$('#accountNumber').attr('data-validation',"required");
		$("#routingNumber").removeAttr('data-validation-optional');
		$('#routingNumber').attr('data-validation',"required validate_int validate_length length9-9");
		
		$('#bankNameSpanId').attr('style',"display:inline-block");
		$('#accountNumberSpanId').attr('style',"display:inline-block");
		$('#routingNumberSpanId').attr('style',"display:inline-block");
		
	}else{
		$("#bankName").val('');
		$("#accountNumber").val('');
		$("#routingNumber").val('');
		$("#bankName").removeAttr('data-validation');
		$('#bankName').attr('data-validation-optional',"true");
		$("#accountNumber").removeAttr('data-validation');
		$('#accountNumber').attr('data-validation-optional',"true");
		$("#routingNumber").removeAttr('data-validation');
		$('#routingNumber').attr('data-validation-optional',"true");
		
		$('#bankNameSpanId').attr('style',"display:none");
		$('#accountNumberSpanId').attr('style',"display:none");
		$('#routingNumberSpanId').attr('style',"display:none");
		
		$('#bankName').parent().find('p.jquery_form_error_message').remove();
		$('#accountNumber').parent().find('p.jquery_form_error_message').remove();
		$('#routingNumber').parent().find('p.jquery_form_error_message').remove();
		
	}
}
</script> 

<script>
function resetValues(){
	$("#bankName").val('');
	$("#accountNumber").val('');
	$("#routingNumber").val('');
// 	$("#id").val('');
}
</script>

<script>
function resetValuesWhileAdd(){
	$("#accountType").val('');
	$("#bankName").val('');
	$("#accountNumber").val('');
	$("#routingNumber").val('');
// 	$("#id").val('');
}
</script>

 <script type="text/javascript">
 function saveAssociateBankData() {
	 var userId = '${userId}';
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
		                	closeadEdBankingDetailsPop();
		                	associateBankData(userId);
		                	/*  $("#editTest").show();
		                	  $("#test").show();
		                	  $("#finalTest").hide(); */
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
function cancelButtonBank(userId){
	 $("#finalTest").hide();
	associateBankData(userId);
}
</script>

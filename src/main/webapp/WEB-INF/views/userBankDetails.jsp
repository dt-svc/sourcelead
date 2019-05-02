
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="myBankDetails" class="tab-pane fade">
			  
			  
<div id="loading"></div> 
  <div  class="tabsMenu_tabs_inn clearfix">
            <div class="clearfix row minHeightcol5" id="associateBankDetails">
                 <form:form id="associate_bank_data_form"  name="associate_bank_data_form" method="post">
                 <div class="col-md-5">
                  <label>Account Type<span class="text-danger">&nbsp;*</span></label>
                  <div>
                    <select class="form-control chosen-select" id="accountType" name ="accountType" data-validation="required" onchange="showHideAccountTypes();" field-name="Account Type">
<!--                       <option value="Bank Transfer">Bank Transfer</option> -->
                      <option value="Direct Deposit" ${SourceLeadWeb_associateBankData.accountType  == 'Direct Deposit' ? 'selected' : ''}>Direct Deposit</option>
                      <option value="Cheque" ${SourceLeadWeb_associateBankData.accountType  == 'Cheque' ? 'selected' : ''}>Cheque</option>
                      <option value="Cash" ${SourceLeadWeb_associateBankData.accountType  == 'Cash' ? 'selected' : ''}>Cash</option>
                    </select>
                  </div>
                </div>
               
               <div id="accountTypeChildDivs">
	               <div class="col-md-5">
	                  <label>Bank Name<span id="bankNameSpanId" class="text-danger">&nbsp;*</span></label>
	                  <div>
	                    <input type="text" class="form-control" id="bankName" name="bankName" value="" data-validation="required validate_Space validate_length length2-100" field-name="Bank Name">
	                  </div>
	                </div>
	
	               <div class="col-md-5">
	                  <label>Account Number<span id="accountNumberSpanId" class="text-danger">&nbsp;*</span></label>
	                  <div>
	                    <input type="text" class="form-control" id="accountNumber" name="accountNumber" value="" data-validation="required validate_Space validate_int validate_length length2-100" field-name="Account Number">
	                  </div>
	                </div>
	                
	                <div class="col-md-5">
	                  <label>Routing Number<span id="routingNumberSpanId" class="text-danger">&nbsp;*</span></label>
	                  <div>
	                    <input type="text" class="form-control" id="routingNumber" name="routingNumber" data-validation="required validate_Space validate_int validate_length length9-9" field-name="Routing Number">
	                  </div>
	                </div>
               </div>
               
                
                 <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                  <div class="col-md-6">
<!--                  <input type="button" value="Previous" onclick="shiftToSalaryTab();" class="btn btn-default"> -->
			     </div> 
                  <div class="col-md-6">
                   <div class="pull-right">
<!--                    <input type="button" onclick="skipAssociateBankDataJsp();" class="btn btn-default mr10" value="Skip"> -->
                    <input value="Save" class="btn btn-success btnSuccess" id="save_associate_bank_data_button" onclick="saveAssociateBankData();" type="button">
                    <input value="Cancel" onclick="javascript:window.location.href = '/app/userProfile'" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
                
              </form:form>
            </div>
          </div>
</div>

<div id="associateBankData"></div>

<script type="text/javascript">
 function saveAssociateBankData() {
	 var userId = '${SourceLeadWeb_user.id}';
	    	 if ($('#associate_bank_data_form').validate(false, validationSettings)){ 
				   var formData = getFormData("associate_bank_data_form");
					$("#save_associate_bank_data_button").attr("disabled", "disabled");
					$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
// 		            $( "#save_associate_bank_data_button" ).addClass('button').val('Processing..');
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
			                	$("#associateId").val(result.object.userId); 
								$("#save_associate_bank_data_button").removeAttr("disabled");
// 								location.replace(sourceleadUrl+"/userProfile");
								associateBankData(userId);
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
function associateBankData(){  
	var userId = '${SourceLeadWeb_user.id}';
	 $.ajax({
           type : "GET",
           url: sourceleadUrl+"/findAssociateBankDataByUserId?"+tokenParamName+"="+tokenValue+"&userId="+userId,    
            success :function(json) {
            	if(json != null && json !='' && json !='undefined'){
            		var result = "";
                	result = result + '<div id="editTest" class="col-md-12">';
//                 	result = result + '<a onclick="editAssociateBankDataFun('+userId+');" class="anchor-black pull-right" target="_self" title="Edit"><i class="fa fa-pencil paddingRight"></i>Edit</a>';
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
                    $("#associateBankDetails").hide();
                    $('#associateBankData').show();
                    $("#associateBankData").empty();
                    $("#associateBankData").append(result);
                    $("#loading").hide();
              }else{
           	   $("#myBankDetails").show();
            }
           },
            	
            
       });  
  } 
</script>

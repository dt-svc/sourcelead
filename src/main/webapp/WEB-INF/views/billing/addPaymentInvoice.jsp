<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script src="/resources/js/jquery.mask.min.js"></script>
<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>

<section class="content-wraper">
  <section class="content-header clearfix">
     <div class="pull-left">
     <h2>Add Payment</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/invoiceList'/>">Invoice List</a> &raquo;  Add Payment </div>
    
    </section>
    <form id= "paymentForm" name="paymentForm" method="post">
    <div class="content-area content-new clearfix">
    	 	<div class="row">
    	 		<div class="form-group clearfix mt">
    	 			<div class="col-md-6 mb">
    	 					<label class="col-md-3">Client<span class="text-danger paddingLeft">*</span></label>
    	 					<div class="col-md-9">
    	 						<select class="form-control chosen-select" id="client" name="client" field-name="Client" onchange="getInvoiceList();getInvoiceTemplateList();" data-validation="required" >
		                        <option value="">-Select Client-</option>
		                        <c:forEach var="clients" items="${clientsList}">
									<option value="${clients.id}" data-responder_location_id="${clients.responderId}">${clients.orgName}</option>
								</c:forEach>
			              </select>
    	 				</div>
    	 			</div>
    	 		</div>
    	 		<div class="clearfix"></div>
    	 	</div>
    	 	
    	 	<div class="row">
    	 		<div class="col-md-6 minHeight50 mb">
    	 			<label class="col-md-3">Payment Date<span class="text-danger paddingLeft">*</span></label>
    	 			<div class="col-md-9" id= "paymentDateDiv">
    	 				<input class="form-control calendar" placeholder="Payment Date" type="text" value = "" id="paymentDate" name="paymentDate" data-validation="required" field-name="Payment Date" readonly>
    	 			</div>
    	 		</div>
    	 		<div class="col-md-6 minHeight50 mb">
    	 			<label class="col-md-3 paddingRight">Payment Method<span class="text-danger paddingLeft">*</span></label>
    	 			<div class="col-md-9">
						<select class="form-control sel" id="paymentMethod" name="paymentMethod" data-validation="required" field-name="Payment Method" onchange="removeErr();"> 
						   <option selected="" disabled="">-- Select Method --</option>
						   <option value="Cash">Cash</option>   
						   <option value="Cheque">Cheque</option> 
						   <option value="CreditCard">Credit Card</option>
						</select> 
    	 			</div>
    	 		</div>
    	 		
    	 		<div class="col-md-6 minHeight50 mb">
	    	 		<label class="col-md-3">Reference No<span class="text-danger paddingLeft">*</span></label>
	    	 		 <div class="col-md-9">
	    	 		 	<input class="form-control" placeholder="Reference No" type="text" id="referenceNumber" name="referenceNumber" data-validation="required validate_length length2-250"  field-name="Reference No"> 
	    	 		</div>
	    	 		<input type="hidden" id = "paymentAmount" name = "paymentAmount" value ="total">
    	 		</div>
    	 		
    	 		<div class="col-md-6 minHeight50 mb">
    	 				<label class="col-md-3 paddingRight">Amount Received<span class="text-danger paddingLeft">*</span></label>
	    	 			<div class="clearfix">
	    	 			<div class="col-md-6">
	    	 				<input type="text" value="" placeholder="Total Due" class="form-control" id="totalAmount" name="totalAmount" data-validation="required validate_length length2-250" field-name ="Amount Received">
	    	 			</div>
	    	 			<div class="col-md-3">
	    	 				<input type="text" class="form-control" id="currencyType" name = "currencyType" readonly="readonly">
	    	 			</div>
	    	 			</div>
    	 		</div>
				<div class="col-md-6 minHeight50 mb">
					<label class="col-md-3">Memo</label>
					<div class="col-md-9">
						<textarea rows="2" class="form-control" placeholder="Memo" id="paymentMemo" name="paymentMemo" data-validation="validate_length length2-500" data-validation-optional="true"></textarea>
					</div>
				</div>
				
	 	 		<div class="col-md-6 minHeight50 mb errorInptgroup">
					<label class="col-md-3">Attachment</label>
					<div class="col-md-9 zindex positionRelative">
							<div class="input-group col-md-12" id="uploadFileGroup"	style="margin: 0 auto">
								<label class="input-group-btn"> <span class="btn btn-primary" onchange="getFleInformation();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadBtn"	class="upload"> </span>
								</label> 
							<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload File" field-name="Upload File" data-validation="validatedoc" data-validation-optional="true">
							</div>
					</div>
				</div>
				
				<div class="col-md-6 minHeight50 mb">
    	 			<label class="col-md-3 paddingRight">Invoice Template</label>
    	 			<div class="col-md-9" id="invoiceTemplateIdDiv">
						<select class="form-control sel" id="invoiceTemplateId" field-name="invoiceTemplateId Name" name="invoiceTemplateId"> 
						   <option value="0">--Select Invoice Template--</option>
						</select> 
    	 			</div>
    	 		</div>
    	 		</div>
    	 		<div class="clearfix"></div>
    	 		
    	 		<!--  Table html -->
    	 		
    	 		<div class="candidate_wrap_table datatable_filter positionRelative mt40">
	                  <div id="invoiceList"></div>
    	 		</div>
    	 		<!--  Table html -->
    	 		</div>
    	 		</form>
    </section>

<div id="validateErrorMsgDivId"></div>

<script type="text/javascript">
$(document).ready(function() {
	getInvoiceList();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	$("#currencyType").val(currencyCode);
	
});


</script>

<script type="text/javascript">

function validDateClass(){
	 var paymentDateVal = document.getElementById("paymentDate").value;
	 if(paymentDateVal){
		 $('#paymentDateDiv').find('p.jquery_form_error_message').remove(); 
	 }
}

    function getFleInformation() {
    	fileUpload("uploadBtn", "incoiceDocuments", "paymentForm");	
	}
	

/* upload file script */
document.getElementById("uploadBtn").onchange = function () {
    $('#uploadFile').val($("#uploadBtn").val().replace(/C:\\fakepath\\/i, '')); 
};
</script>

<script type="text/javascript">
var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
// written common method in slead-common.js for date formats start
var finalDateFormat = getJSDateFormat(locationDate);
$('#paymentDate').datepicker({
    dateFormat: finalDateFormat,
    changeMonth: true,
	  changeYear: true, 
	  minDate:0,
	  onSelect : function(selected) {
			 validDateClass();
		}
	 
    //minDate: 0  this line for to restrict selection of previous dates
});
</script>
<script>
  function getInvoiceList(){
	  var orgRelationId = $("#client").val();
	   $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getInvoiceListForPayment?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&invoiceType=receivable",
	        success: function(json) {
	        	
	        	var result = '';
         		//result = result +'     <form id ="paymentForm" name ="paymentForm" method="post">'; 
         		result = result +'     <div class="modal-bodyCustom clearfix popupFilter clients_wrap_table">'; 
         	    result = result +'       <table id="invoiceListTable" class="clearfix" cellspacing="0" width="100%" >';
         		result = result +'           <thead>';
         		result = result +'           <tr>';
         		result = result +'           <th><input type="checkbox" id="checkAllRecords" onclick="toggleInvoices(this);"></th>';
         		result = result +'         <th>Reference Id</th>                    ';
         		result = result +'         <th>Project Name</th>           ';
         		result = result +'         <th>Invoice Date</th>               ';
         		result = result +'         <th>Due Date</th>';
         		result = result +'         <th>Total</th>                 ';
         		result = result +'         <th>Due</th>                   ';
         		result = result +'         <th>Status</th>                ';
         		result = result +'         <th>Payment</th>                ';
         		result = result +'           </tr>';
         		result = result +'           </thead>';
                result = result +"<tbody>"; 
                
	        	if(json.data != null && json.data != 'undefined' && json.data != "" && json.data != '[]'){
	        		
	                    for (var i = 0; i < json.data.length; i++) { 
	                    	 var opt = json.data[i]; 
	                    	 result = result +'<tr>';
	                    	 result = result +'<td><input id= "'+opt.id+'" value="'+opt.dueAmount+'" onclick="checkSingleInvoice(\''+opt.id+'\',\''+opt.dueAmount+'\');"  type="checkbox" name="invoices" class="myCheckbox" >';
	                    	 result = result +'<td>'+opt.invoiceId+'</td>';
	                    	 var projectName = '';
	                    	 if(opt.projectName != null && opt.projectName !='' && opt.projectName !="undefined"){
	                    		  projectName = opt.projectName;
	                    	 }else{
	                    		 projectName = '--';
	                    	 }
	                    	 result = result +'<td>'+projectName+'</td>';
	                    	 result = result +'<td>'+opt.invoiceDateStr+'</td>';
	                    	 result = result +'<td>'+opt.dueDateStr+'</td>';
	                    	 result = result +'<td>'+opt.total+'</td>';
	                    	 result = result +'<td>'+opt.dueAmount+'</td>';
	                    	 result = result +'<td>'+opt.invoiceStatus+'</td>';
	                    	 result = result +'<td><div><input type="text" value="" placeholder="Total Due" onkeyup="amountInputVal1(this);validateNumber(this);validateDueAmount(this,'+opt.dueAmount+');" onfocusout ="validateDueAmount(this,'+opt.dueAmount+');"  class="form-control mt5 mb5" id="totalAmountRow'+opt.id+'" field-name ="Total Due"></div></td>';
	                    	 result = result +'</tr>';
	                    }
	                     result = result +" </tbody>";
	              		result = result +'           <tfoot>';
	             		result = result +'           <tr>';
	         			result = result +'           <td colspan="8"><div class="pull-right">Total</div></td>';
	         			result = result +'           <td colspan="1"><div id="grandTotalAmount"></div></td>';
	             		result = result +'           </tr>';
	             		result = result +'           </tfoot>';
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" id="Submit_Button" onClick="addInvoice();">Add</button>';
	                 	 result = result +'   </div>'; 
	                 	//result = result +'     </form>'; 
	                 	 $("#invoiceList").empty(); 
	                     $("#invoiceList").append(result); 
	                     ListPagination();  
	        		
	        	} else{
 					$("#invoiceList").empty();
 				    $("#invoiceList").append(result); 
 				   ListPagination();  
	        	} 
	        },
	        
	    }); 
	    
	  $(".new_side_wrap").addClass("new_side_wrap_active");
		$(".side_mask").fadeIn(200);
		
  }
  
  </script>
  <script type="text/javascript">
  
  // allow only number
  $("#totalAmount").on("keypress keyup blur",function (event) {
	$(this).val($(this).val().replace(/[^0-9\.]/g,''));
      if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
          event.preventDefault();
      }
  });
  
    function validateNumber(object){ 
	   object.value = object.value.replace(/[^0-9\.]/g,'');
	} 
  
function addInvoice(){
	// check if dueamount is valid or not
	var Duevalidate = true;
	$("input[id^=totalAmountRow]").each(function() {
		 if($(this).next('p').length >0){
			 Duevalidate = false;
			 console.log(" inside Duevalidate"+Duevalidate)
			 return false;
		  }	 
	  });
	 
 	 if ($('#paymentForm').validate(false, validationSettings) && Duevalidate) {
		 var grandDueTotal = $("#grandTotalAmount").text();
		 var grandTotalReceived = $("#totalAmount").val();
		 var finalAmountReceived =parseFloat(grandTotalReceived);
		 if(grandDueTotal == finalAmountReceived.toFixed(3)){
		 var invoiceIds= []
		 var amount = "";
		 $('input:checkbox[name=invoices]:checked').each(function() {
				//invoiceIdsMap[this.id] =  $("#totalAmountRow"+this.id).val();
				invoiceIds.push(this.id+"_"+$("#totalAmountRow"+this.id).val()); // pushing values as id_invoice amount to array.
				amount = $("#totalAmountRow"+this.id).val();
		     }); 
		 if(amount !=null && amount !="" && amount !="undefined"){
		 var formData = getFormData("paymentForm");
		 $.post(sourceleadUrl+"/saveInvoiceBulkPayment?"+tokenParamName+"="+tokenValue+"&invoiceIds="+invoiceIds,formData,function(result) {
			 	if (result && result.response === "INVALID_DATA") {
          		    showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          		    location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			location.replace(sourceleadUrl+"/invoiceList");
          		} else if (result.indexOf('Sign In') !== -1) {
          			location.replace(sourceleadUrl);
          		} else {
          		    showError("Operation failed, please try again");
          		}
	 });
		 }else{
			// alert("please select at least one record / Amount should not Empty");
			 showCheckBoxDivPopup();
		 }
		}else{
			 //alert("Amount Received and Due Total should be same");
			 showAmountWarningPopup();
		 }
	 } 
}
$('body').on('blur', '#paymentForm', function() {
	$("#paymentForm").showHelpOnFocus();
	$("#paymentForm").validateOnBlur(false, validationSettings);
	console.log("inisde last form action");
	
	// using for adding error message for input filed again.
	$("input[id^=totalAmountRow]").each(function() {
		validateDueAmount(this, $(this).closest('td').prev().prev().text());
	  });
});
</script>

<script type="text/javascript">
function getInvoiceTemplateList() {
	var orgRelationId = $("#client").val();
	var responderLocationId = $("#client :selected").data("responder_location_id");
	$("#invoiceTemplateId").empty("");
	$.ajax({
		type : "POST",
		url: sourceleadUrl+"/invoiceTemplateList?orgRelationId=" + orgRelationId + "&orgLocationId=" + responderLocationId,
		success : function(data) {
			$("#invoiceTemplateId").append("<option value='0'>-- Select Invoice Template --</option>");
			$("#invoiceTemplateId").append("<option value='-1' selected>Default Template</option>");
			if(data && data.data != null && data.data.length > 0) {
				for(var e in data.data) {
					$("#invoiceTemplateId").append("<option value=" + data.data[e].id + ">" + data.data[e].name + "</option>");
				}
			}
		}
	});
}

function showAmountWarningPopup(){ // multiWarnMsg populated when validated form and docs required upload docs info.
	$("#validateErrorMsgDivId").val('');
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a modal-body'>";
	result = result + " <b class='mt mb'>Amount Received and Due Total should be same</b>";
	   result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result	+ "		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='closePop()'></b>";
		result = result + "	</div>";
		$("#validateErrorMsgDivId").append(result);
		$("#remove_immigration_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
function showCheckBoxDivPopup(){ 
	$("#validateErrorMsgDivId").val('');
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_immigration_pop' class='remove_user_pop remove_user_pop_a modal-body'>";
	result = result + " <b class='mt mb'>Please select at least one record / Due Amount should not Empty</b>";
	   result = result + ' <div id="reasonForTemplateDiv"/>';
		result = result	+ "		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='closePop()'></b>";
		result = result + "	</div>";
		$("#validateErrorMsgDivId").append(result);
		$("#remove_immigration_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}

	function closePop() {
		$("#remove_immigration_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
		$(".project_inn_mask").remove();
		$("#remove_immigration_pop").remove();
	}
</script>

  <script>
  function amountInputVal1(obj){
	  // this is for total value updation based our input
	  var totalValue=0.00;
	  $("input[id^=totalAmountRow]").each(function() {
	       var value = $(this).val();
	    if($.isNumeric(value)){
	       totalValue= totalValue+parseFloat(value);
	  	} 
	  });
	  $("#grandTotalAmount").text(totalValue.toFixed(3));
	// For innerTable scrool we have to main this script, Please don't remove.. ask @Ajit
	  $(".dataTables_scrollFoot").find("#grandTotalAmount").text(totalValue.toFixed(3));
  }
  
  
  function validateDueAmount(obj,dueAmount){
	  var adjusteVal= $(obj).val();
	  if($.isNumeric(adjusteVal)){
		  if(adjusteVal > dueAmount){
			  $('#'+obj.id).parent().find('p.jquery_form_error_message').remove();
			 $('#'+obj.id).parent().append("<p class='jquery_form_error_message'>Entered amount should not greater the due amount</p>");
		  }else{
			  $('#'+obj.id).parent().find('p.jquery_form_error_message').remove();
		  }
	 } 
  }
  
  </script>
   <script>
	function toggleInvoices(source) {
	    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	    var total = 0.000;
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i] != source){
	            checkboxes[i].checked = source.checked;
	            if(checkboxes[i].checked){
	        		$("#totalAmountRow"+checkboxes[i].id).val(checkboxes[i].value);
	        		if($.isNumeric(checkboxes[i].value))
	        		total += parseFloat(checkboxes[i].value);
	            }else{
	            	$("#totalAmountRow"+checkboxes[i].id).val('');
	            	total = 0.000;
	            }
	    	}
	        $("#grandTotalAmount").text(total.toFixed(3));
	     // For innerTable scrool we have to main this script, Please don't remove.. ask @Ajit
			  $(".dataTables_scrollFoot").find("#grandTotalAmount").text(total.toFixed(3));
	    }

	$('input:checkbox:not(#checkAllRecords)').click(function() {
	    if(!this.checked) {
	        $('#checkAllRecords').removeAttr('checked');
	    }
	});
	} 
	</script>
	
	<script>
	function checkSingleInvoice(id, amount){
		var totalAmount = $("#grandTotalAmount").text();
		if(totalAmount == ''){
			totalAmount = 0.000;
		}
		var grandTotalAmount = 0.000;
		  if($("#"+id).is(':checked')){
			  $("#totalAmountRow"+id).val(amount);
			 grandTotalAmount =  parseFloat(totalAmount)+parseFloat(amount);
		  }else{
			  var inputAmount = $("#totalAmountRow"+id).val();
			  $("#totalAmountRow"+id).val('');
			  grandTotalAmount =  parseFloat(totalAmount)-parseFloat(inputAmount);
		  }
		  $("#grandTotalAmount").text(grandTotalAmount.toFixed(3));
		// For innerTable scrool we have to main this script, Please don't remove.. ask @Ajit
		  $(".dataTables_scrollFoot").find("#grandTotalAmount").text(grandTotalAmount.toFixed(3));
	  }
	</script>
<script>
 function ListPagination(){ 
	
	 $("#invoiceListTable").DataTable( {
		    "scrollY":"350px",
	        "scrollCollapse": true,
	        "paging":         false,
	        "fixedHeader" : true,
	    	"lengthMenu": [[10000, 15, 20, -1], [10000, 15, 20, "All"]],
	    	"bLengthChange": false,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"aaSorting": [[1,'desc']],
	    	"aoColumnDefs": [{
		           'bSortable': false,
		           'aTargets': 0
				}], 
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": ""
	     	},
	     	// Datatable: Hidding duplicate Header in IE-11(edge)
	 "initComplete" : function () {
		         $('.dataTables_scrollBody thead tr').addClass('hidden');
		     }
	    });
	
}
</script>
<jsp:include page="../masterFooter.jsp" />
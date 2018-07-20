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
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/payablesList'/>">Bill List</a> &raquo;  Add Payment </div>
    
    </section>
    <form id= "paymentForm" name="paymentForm" method="post">
    <div class="content-area content-new clearfix">
    	 	<div class="row">
    	 		<div class="form-group clearfix mt">
    	 			<div class="col-md-6 mb">
    	 					<label class="col-md-3">Vendor<span class="text-danger paddingLeft">*</span></label>
    	 					<div class="col-md-9">
    	 						<select class="form-control chosen-select" id="vendor" name="vendor" field-name="Vendor" onchange="getPaybleList()" data-validation="required" >
		                        <option value="">-Select Vendor-</option>
		                        <c:forEach var="vendors" items="${vendorsList}">
									<option value="${vendors.id}">${vendors.locationId.org.orgName}</option>
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
    	 			<div class="col-md-9">
    	 				<input class="form-control calendar" placeholder="Payment Date" type="text" id="paymentDate" name="paymentDate" data-validation="required" field-name="Payment Date" readonly>
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
				
 	 		<div class="col-md-6 minHeight50 mb">
		<label class="col-md-3">Attachment</label>
			<div class="col-md-9 zindex positionRelative">
				<div class="input-group col-md-12" id="uploadFileGroup"	style="margin: 0 auto"> 
				<label class="input-group-btn"> <span class="btn btn-primary" onchange="getFleInformation();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" id="uploadBtn" class="upload"> </span>
				</label> 
				<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Document" field-name="Upload Resume" data-validation="validatedoc" data-validation-optional="true">
					<div class="clearUpload"></div>
				</div>
			</div>
		</div>
    	 		</div>
    	 		<div class="clearfix"></div>
    	 		
    	 		<!--  Table html -->
    	 		
    	 		<div class="candidate_wrap_table datatable_filter">
	                  <div id="invoiceList"></div>
    	 		</div>
    	 		<!--  Table html -->
    	 		</div>
    	 		</form>
    </section>

<script type="text/javascript">
$(document).ready(function() {
	getPaybleList();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	$("#currencyType").val(currencyCode);
	
});
</script>

<script type="text/javascript">
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
	  minDate:0
    //minDate: 0  this line for to restrict selection of previous dates
});
</script>
<script>
  function getPaybleList(){
	  var orgRelationId = $("#vendor").val();
	   $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getInvoiceListForPayment?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&invoiceType=payables",
	        success: function(json) {
	        	
	        	var result = '';
         		result = result +'     <form id ="paymentForm" name ="paymentForm" method="post">'; 
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
         		result = result +'           <tfoot>';
         		result = result +'           <tr>';
     			result = result +'           <td colspan="8"><div class="pull-right">Total</div></td>';
     			result = result +'           <td colspan="1"><span id="grandTotalAmount"></span></td>';
         		result = result +'           </tr>';
         		result = result +'           </tfoot>';
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
	                    	 result = result +'<td><input type="text" value="" placeholder="Total Due" onkeyup="amountInputVal2(this);" class="form-control mt5 mb5" id="totalAmountRow'+opt.id+'"></td>';
	                    	 result = result +'</tr>';
	                    }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     result = result +'     </div>'; 
	                 	 result = result +'     <div class="clearfix">';
	                 	
	                 	 result = result +'       </div>'; 
	                 	 result = result +'   </div>'; 
	                 	 result = result +'   <div class="modal-footer">';
	                 	 result = result +'     <button data-dismiss="modal" class="btn btn-primary" type="button" onClick="addInvoice();" >Add</button>';
	                 	 result = result +'   </div>'; 
	                 	result = result +'     </form>'; 
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
function addInvoice(){
	 if ($('#paymentForm').validate(false, validationSettings)) {
		 var invoiceIdsMap = new Object();
		 $('input:checkbox[name=invoices]:checked').each(function() {
				invoiceIdsMap[this.id] = $("#totalAmountRow"+this.id).val();
		     });
		 var formData = getFormData("paymentForm");
		  $.post(sourceleadUrl+"/saveInvoiceBulkPayment?"+tokenParamName+"="+tokenValue+"&invoiceIds="+JSON.stringify(invoiceIdsMap), formData, function(result) {
			 
		 }); 
		  invoiceIdsMap = {};
	 }
	
}
$('body').on('blur', '#paymentForm', function() {
	$("#paymentForm").showHelpOnFocus();
	$("#paymentForm").validateOnBlur(false, validationSettings);
});
</script>
  <script>
  function amountInputVal2(obj){
	  // this is for total value updation based our input
	  var totalValue=0.00;
	  $("input[id^=totalAmountRow]").each(function() {
	       var value = $(this).val();
	    if($.isNumeric(value)){
	       totalValue= totalValue+parseFloat(value);
	  	} 
	  });
	  $("#grandTotalAmount").text(totalValue.toFixed(2));
	// For innerTable scrool we have to main this script, Please don't remove.. ask @Ajit
	  $(".dataTables_scrollFoot").find("#grandTotalAmount").text(totalValue.toFixed(2));
  }
  </script>
   <script>
	function toggleInvoices(source) {
	    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	    var total = 0.00;
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i] != source){
	            checkboxes[i].checked = source.checked;
	            if(checkboxes[i].checked){
	        		$("#totalAmountRow"+checkboxes[i].id).val(checkboxes[i].value);
	        		if($.isNumeric(checkboxes[i].value))
	        		total += parseFloat(checkboxes[i].value);
	            }else{
	            	$("#totalAmountRow"+checkboxes[i].id).val('');
	            	total = 0.00;
	            }
	    	}
	        $("#grandTotalAmount").text(total.toFixed(2));
	     // For innerTable scrool we have to main this script, Please don't remove.. ask @Ajit
			  $(".dataTables_scrollFoot").find("#grandTotalAmount").text(total.toFixed(2));
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
			totalAmount = 0.00;
		}
		var grandTotalAmount = 0.00;
		  if($("#"+id).is(':checked')){
			  $("#totalAmountRow"+id).val(amount);
			 grandTotalAmount =  parseFloat(totalAmount)+parseFloat(amount);
		  }else{
			  $("#totalAmountRow"+id).val('');
			  grandTotalAmount =  parseFloat(totalAmount)-parseFloat(amount);
		  }
		  $("#grandTotalAmount").text(grandTotalAmount.toFixed(2));
		// For innerTable scrool we have to main this script, Please don't remove.. ask @Ajit
		  $(".dataTables_scrollFoot").find("#grandTotalAmount").text(grandTotalAmount.toFixed(2));
	  }
	</script>
	
<script>
function ListPagination(){ 
	
	 $("#invoiceListTable").DataTable( { 
		 	"scrollY":"350px",	
		 	"scrollCollapse": false,
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
	     	}
	    } );
	
}

</script>
<jsp:include page="../masterFooter.jsp" />
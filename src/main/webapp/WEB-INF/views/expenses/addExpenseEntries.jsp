<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script>
function loadCategoryDiv(){
	var result = "";
	result = result + '<div class="modal-dialog modal-lg">';
	
	result = result + '	<div class="modal-content">';
	result = result + '		<div class="modal-header">';
	result = result + '			<button type="button" class="close" data-dismiss="modal">&times;</button>';
	result = result + '			<h4 class="modal-title">Add Expense Category</h4>';
	result = result + '		</div>';
	result = result + ' <form:form id="entity_form" name="entity_form" method="post">';
	result = result + '		<div class="modal-body clearfix expPopCon">';
	result = result + '			<div class="row">';
	result = result + '				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">';
    
	result = result + '					<div class="form-group">';
	result = result + '						<label class="col-md-4">Category Type<span class="text-danger">&nbsp;*</span></label>';
	result = result + '						<div class="col-md-8">';
	result = result + '							<select class="chosen-select form-control" id="categoryType" name="categoryType" field-name="Category Type" data-validation="required">';
	result = result + '								<option value="">Category Type</option>';
	result = result + '								<option>Category-1</option>';
	result = result + '								<option>Category-2</option>';
	result = result + '								<option>Category-3</option>';
	result = result + '								<option>Others</option>';
	result = result + '							</select>';
	result = result + '						</div>';
	result = result + '					</div>';
    
	result = result + '					<div class="form-group">';
	result = result + '						<label class="col-md-4">Date<span class="text-danger">&nbsp;*</span></label>';
	result = result + '						<div class="col-md-8">';
	result = result + '							<input type="text" class="form-control calendar" value="" name="categoryDate" id="categoryDate" placeholder="Date" field-name="Date" data-validation="required">';
	result = result + '						</div>';
	result = result + '					</div>';
    
	result = result + '					<div class="form-group">';
	result = result + '						<label class="col-md-4">Receipt Issued by<span class="text-danger">&nbsp;*</span></label>';
	result = result + '						<div class="col-md-8">';
	result = result + '							<input type="text" class="form-control" name="receiptIssuedBy" id="receiptIssuedBy" value="" field-name="Receipt Issued By" data-validation="required">';
	result = result + '						</div>';
	result = result + '					</div>';
    
	result = result + '					<div class="form-group">';
	result = result + '						<label class="col-md-4">Payment Mode<span class="text-danger">&nbsp;*</span></label>';
	result = result + '						<div class="col-md-8 dropdown-select">';
	result = result + '							<select class="form-control" name="paymentMode" id="paymentMode" field-name="Payment Mode" data-validation="required">';
	result = result + '								<option value="">Select Mode</option>';
	result = result + '								<option>Mode-1</option>';
	result = result + '								<option>Mode-2</option>';
	result = result + '								<option>Mode-3</option>';
	result = result + '								<option>Mode</option>';
	result = result + '							</select>';
	result = result + '						</div>';
	result = result + '					</div>';
    
	result = result + '					<div class="form-group">';
	result = result + '						<label class="col-md-4">Amount<span class="text-danger">&nbsp;*</span></label>';
	result = result + '						<div class="col-md-8">';
	result = result + '						<div class="input-group er ratetype">';
	result = result + '							<div class="input-group-btn tblminwidth-75" style="min-width: 65px !important;">';
	result = result + '								<select class="selectpicker" id="expenseRateType" name="expenseRateType" field-name="Rate Type" field-name="Rate Type" data-validation="required">';
	result = result + '									<option ${currencyCode  == "INR" ? "selected" : ""}>INR</option>';
	result = result + '									<option ${currencyCode  == "AUD" ? "selected" : ""}>AUD</option>';
	result = result + '									<option ${currencyCode  == "USD" ? "selected" : ""}>USD</option>';
	result = result + '									<option ${currencyCode  == "GBP" ? "selected" : ""}>GBP</option>';
	result = result + '									<option ${currencyCode  == "AUD" ? "selected" : ""}>SGD</option>';
	result = result + '									<option ${currencyCode  == "EUR" ? "selected" : ""}>EUR</option>';
	result = result + '								</select>';
	result = result + '							</div>';
	result = result + '							<input type="text" class="form-control"  name="expenseAmount" id="expenseAmount" value="" field-name="Amount" data-validation="required validate_int">';
	result = result + '						</div>';
	result = result + '						</div>';
	result = result + '					</div>';
    
	result = result + '					<div class="form-group" id="divAttachment1" style="display: block;">';
	result = result + '						<label class="col-md-4">Attachment</label>';
	result = result + '						<div class="col-md-8 clearfix">';
	result = result + '							<div class="input-group col-md-10 pull-left" id="uploadFileGroup1">';
	result = result + '								<label class="input-group-btn"> <span class="btn btn-primary" title="Browse File"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File <input type="file" style="display: none;" multiple id="uploadBtn1" class="upload"> </span></label>';
	result = result + '								<input type="text" class="form-control" readonly id="uploadFile1" type="file" field-name="Attachment" data-validation="validatedoc " data-validation-optional="true" placeholder="Attachment">';
	result = result + '							</div>';
	result = result + '							<div class="input-group col-md-2 pull-right text-right">';
	result = result + '								<button class="btn btn-primary">';
	result = result + '									<i class="fa fa-plus"></i>';
	result = result + '								</button>';
	result = result + '							</div>';
	result = result + '						</div>';
	result = result + '					</div>';
	result = result + '					<div class="form-group">';
	result = result + '						<label class="col-md-4">Description</label>';
	result = result + '						<div class="col-md-8">';
	result = result + '							<textarea rows="2" class="form-control" name="entryDes" id="entryDes" placeholder="Description"></textarea>';
	result = result + '						</div>';
	result = result + '					</div>';
	result = result + '</form:form>';
	result = result + '				</div>';
	result = result + '			</div>';
	result = result + '		</div>';
    
	result = result + '		<div class="modal-footer">';
	result = result + '			<span class="col-md-4 jquery_form_error_message" id="exceedSpan"></span>';
	result = result + '			<button type="button" class="btn btn-primary btn-sm" onclick="addEntity();">Add</button>';
	result = result + '			<button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="clearValidations();">Close</button>';
	result = result + '		</div>';
	result = result + '	</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	$("#addExpenseEntity").empty();
	$("#addExpenseEntity").append(result);
	$("#expenseRateType").selectpicker("refresh");
	
	$('#categoryDate').datepicker({
	      dateFormat: 'dd-mm-yy',
	      changeMonth: true,
	  	  changeYear: true 
	  });
	
	var selectedFile1 = document.getElementById("uploadBtn1");
    selectedFile1.addEventListener("change", function() {
    	 $("#uploadFile1").focus();
    	 if(validate_fileupload(this.value)){
    		 var docSize = this.files[0].size;
   	      $("#uploadFileGroup1").find('p.jquery_form_error_message').remove();
   	      if(docSize > 2097152){
   	    	  document.getElementById("uploadFile1").value = '';
   	    	  $('#uploadFileGroup1').find('p.jquery_form_error_message').remove();
   			  $("input#uploadFile1").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
   			  document.getElementById("uploadFile1").setAttribute('record-exist','yes');
   			  document.getElementById("uploadFile1").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
   	      }else{
   	    	 $('input#uploadFile1').removeAttr( "record-exist record-exist-errormsg");
      		 $('#uploadFileGroup1').find('p.jquery_form_error_message').remove(); 
    	    }
    	 }
    });
   /* upload file script */
   document.getElementById("uploadBtn1").onchange = function () {
   	$('#uploadFile1').val($(this).val().replace(/C:\\fakepath\\/i, ''));
   };
   
	var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
			});
$("#categoryType").trigger("chosen:updated");
}

</script>


<script>
window.recCount = 1;
function addEntity(){
	
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
	
	
	
	var entriesCount = $('#entriesTable >tbody >tr').length;
	if(parseInt(entriesCount , 10) >= parseInt('5' , 10)){
		$("#exceedSpan").text("Your entries limit is exceed");
		$(this).hide();
	}else{
		
		if ($('#entity_form').validate(false, validationSettings)) {
			var frm = $('#entity_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
		    var form = $('#entity_form')[0];
		    var formData = new FormData(form);
		  	formData.append("json",jsonData)
		
				var categoryType = $("#categoryType").val();
				var categoryDate = $("#categoryDate").val();
				var receiptIssuedBy = $("#receiptIssuedBy").val();
				var paymentMode = $("#paymentMode").val();
				var expenseRateType = $("#expenseRateType").val();
				var expenseAmount = $("#expenseAmount").val();
				var descriptions = $("#entryDes").val();
				
				var result = "";
				result = result + '<tr id="'+recCount+'">';
				result = result + '<td><input type="hidden" name="categoryType'+recCount+'" id="categoryType'+recCount+'" value="2"><div>'+categoryType+'</div></td>';
				result = result + '<td><input type="hidden" name="categoryDate'+recCount+'" id="categoryDate'+recCount+'" value="'+categoryDate+'"><div>'+categoryDate+'</div></td>';
				result = result + '<td><input type="hidden" name="receiptIssuedBy'+recCount+'" id="receiptIssuedBy'+recCount+'" value="'+receiptIssuedBy+'"><div>'+receiptIssuedBy+'</div></td>';
				result = result + '<td><input type="hidden" name="expenseRateType'+recCount+'" id="expenseRateType'+recCount+'" value="'+expenseRateType+'"><div>'+expenseRateType+'</div></td>';
				result = result + '<td><input type="hidden" name="expenseAmount'+recCount+'" id="expenseAmount'+recCount+'" value="'+expenseAmount+'"><div>'+expenseAmount+'</div></td>';
				result = result + '<input type="hidden" name="paymentMode'+recCount+'" id="paymentMode'+recCount+'" value="'+paymentMode+'">';
				result = result + '<td><input type="hidden" class="tbldata-dotdot-me" name="entryDes'+recCount+'" id="entryDes'+recCount+'" value="'+descriptions+'"><div>'+descriptions+'</div></td>';
				result = result + '<td><div class="tbldata-dotdot_50"><i class="fa fa-pencil-square-o"></i> <i class="glyphicon glyphicon-trash" onclick="deleteEntityRec('+recCount+');"></i></div></td>';
				result = result + '</tr>';
				
				$("#divBody").append(result);
				$(".modal-backdrop").fadeOut(200);
				$(".modal").slideUp(200);
				$(".modal-dialog").slideUp(300);
				
				recCount = recCount + 1;
				
				$("#addExpenseEntity").empty();
				$("#addExpenseEntity").append("");
			}
		}
	}

function clearValidations(){
	
}

function deleteEntityRec(trId){
	$('table#entriesTable tr#'+trId).remove();
}

</script>



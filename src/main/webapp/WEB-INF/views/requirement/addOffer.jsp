<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="resources/css/style.css" />

<script>
		$(document).ready(function() {
			NotifyToApplicant();
			NotifyToVendor();
		});
</script>



<!-- For add New Interview -->
<script>
function addOffer(requirementId){
	$('.action-pop').slideUp();
	var clientSubmissions = '${clientSubmissions}';
	
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getRequirementWhichIsReleasedOrNot?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	
				var result = "";
				result= result + '	          <div class="add_inter">';
				
				result = result + '<form id="addOffer_form" name="addOffer_form" enctype="multipart/form-data" method="post" >';
				result = result + '<input type="hidden" id="maxOfferRowNum" name="maxOfferRowNum" value="1" />';
				result = result + '<input type="hidden" id="reqId" name="requirementId" value='+requirementId+'>';
				
				result = result + '<input type="hidden" id="isFlushCandidateRequired" name="isFlushCandidateRequired">';
				
				/* result= result + '	            <div class="add_inter_head"> <span>Add Offer</span><a href="javascript:void(0)" onClick="addOfferRow(this);"><i class="fa fa-plus"></i> Add row</a>'; */
				result= result + '	            <div class="add_inter_head"> <span>Generate Offer</span><span class="text-success col-md-offset-1">No Of Candidates Selected: <b id="selectedOfferApplicantsCount">0</b></span>';
				result= result + '	              <div class="clearfix"></div>';
				result= result + '	            </div>';
				
				if(clientSubmissions == 'Y'){
				    result = result + '<input type="checkbox" name="isClientOffer" id="isClientOffer" class="isClient" onclick="myOfferFunction();">';
			        result = result + '<label>&nbsp;&nbsp;<strong>Is Client Offer</strong>&nbsp;&nbsp;</label>';
				} 
				
				result= result + '	            <table cellpadding="0" cellspacing="0" id="addOfferTbl">';
				result= result + '	              <tbody>';
				result= result + '	                <tr id="divTsRow0">';
				result= result + '	                  <th>Applicant</th>';
				result= result + '	                  <th>Date of Joining</th>';
				result= result + '	                  <th>Rate / Salary</th>';
				result= result + '	                  <th>Employment Type</th>';
				result= result + '	                  <th>Attachment</th>';
				/* result = result + '<th class="action">Action</th>'; */
				result= result + '	                </tr>';
				
				result= result + '	              </tbody>';
				result= result + '	            </table>';
				
				result= result + ' <div class="row pad2">';
				result = result + '<div class="col-md-12 mt10">';
				result = result + '<div class="ne-ch">';
				
				result= result + ' <label>';
				result= result + '<input type="checkbox" checked onclick="NotifyToApplicant();" id="notifyToApplicant" name="notifyToApplicant">&nbsp;Notify To Applicant</label>';
			   
				if(data =="true"){
					result= result + '<input type="hidden" value="Y"  id="notifyToVendor" name="notifyToVendor"/>';
				} 
				
				result= result + '</div>';
			    result= result + '</div>';
			    result= result + '	              <div class="clearfix"></div>';
			    result= result + '</div>';
				result= result + '	              <div class="clearfix"></div>';
				result= result + '<div class="clearfix">';
				result = result + '<div class="pull-right">';
				result = result +'				  <button type="submit" id="add_offer" onclick="validateRateAndRateType();" class="btn btn-sm btn-primary mr">Submit</button>';
				result= result + '	              <input type="button" class="btn btn-sm btn-danger" value="Cancel" onclick="cancelAddOffer();" >';
				result= result + '	            </div>';
				result= result + '	          </div>';
				result= result + '</div>';
				
				result = result + '</form>';
				result = result + '<div id="offerLoading"></div>';
				result = result + '</div><div id="offerPopUp">';
				result= result + '	          </div>';
				
										
				$("#addOfferDiv").empty();
				$("#addOfferDiv").append(result);
				
				
				$(".add_offer_wrap").addClass("add_offer_wrap_active");
				$(".side_mask").fadeIn(200);
				$("body").css('overflow-y', 'hidden');
				
				addOfferRow();
    
	        },
	    });
	 
	 
	
}
</script>


<script>
    $(".addOffer_close, .side_mask").click(function() {
    	$(".add_offer_wrap").removeClass("add_offer_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
</script>

<script>
function loadRemiderDiv(){
	$(".reminder-div").slideToggle(100);
}
</script>

<script>
function selectOfferApplicants(){
	var selectedOfferApplicants =  $('#1applicantId_chosen  span').size();
	$("#selectedOfferApplicantsCount").html(selectedOfferApplicants);
	$('#1applicantId_chosen').next("p").remove();
	
	$('.search-choice-close').click(function() {
		var offerApplicants =  $('#1applicantId_chosen  span').size();
		$('#1applicantId_chosen').next("p").remove();
		if(offerApplicants <1){
			$('#1applicantId_chosen').after('<p class="jquery_form_error_message">Applicant is Required</p>');
		}
		$("#selectedOfferApplicantsCount").html(offerApplicants);
	});
}
</script>

<div id="divOfferTd1" style="display: none; float: left;">
       <select multiple class="blminwidth-50"  id="REPLACEROWIDapplicantId" onchange="selectOfferApplicants()" data-placeholder="select Apllicants" name="REPLACEROWIDapplicantId" field-name="Applicant" data-validation="required" >
			
	  </select>
</div>


<div id="divOfferTd3" style="display: none; float: left;">
   	 <div class="positionRelative float select-field">
           <select class="form-control" id="REPLACEROWIDPaymentType" name="REPLACEROWIDPaymentType" style="height: 30px;">
	          		 <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
	                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
	                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
           </select>
     </div>
     <div class="positionRelative float select-field">
     	  <input type="text" value="" class="form-control input-sm" id="REPLACEROWIDAmount" name="REPLACEROWIDpaymentTypAmount" field-name="Rate" data-validation="required validate_float validate_length length1-8" onclick="validateRateAndRateType();">
     </div>
   	 <div class="positionRelative float select-rateper select-field">
           <select class="form-control" id="REPLACEROWIDPaymentMode" name="REPLACEROWIDPaymentMode" field-name="Rate Per" style="height: 30px;" onchange="validateRateAndRateType();">
	                 <option value="">Rate Per</option>
	                 <option value="Hourly">Hourly</option>
	                 <option value="Weekly">Weekly</option>
	                 <option value="Bi-weekly">Bi-Weekly</option>
	                 <option value="Monthly">Monthly</option>
	                 <option value="Bi-Monthly">Bi-Monthly</option>
	                 <option value="Semi-Monthly">Semi-Monthly</option>
	                 <option value="Yearly">Yearly</option>
           </select>
     </div>
</div> 

<div id="divOfferTd4" style="display: none; float: left;" class="select-field">
 	<select class="form-control" id="REPLACEROWIDPositionType" name="REPLACEROWIDPositionType" onchange="otherJobType(this);">
 	 				 <option value="" disabled selected>Select Type</option>
	                 <option  ${requirement.jobType  == 'C2C' ? 'selected' : ''}>C2C</option>
	                 <option  ${requirement.jobType  == 'W-2' ? 'selected' : ''}>W-2</option>
	                 <option  ${requirement.jobType  == 'C2H' ? 'selected' : ''}>C2H</option>
	                 <option  ${requirement.jobType  == 'Contract' ? 'selected' : ''}>Contract</option>
	                 <option  ${requirement.jobType  == 'Part Time' ? 'selected' : ''}>Part Time</option>
	                 <option  ${requirement.jobType  == 'Full Time' ? 'selected' : ''}>Full Time</option>
	                 <option  ${requirement.jobType  == 'Other' ? 'selected' : ''}>Other</option>
	                      
	</select>
	<div id="REPLACEROWIDPositionTypeCheck" style="display: none; "><input value=""  type="text" id="REPLACEROWIDJobOtherType" name="REPLACEROWIDJobOtherType" class="form-control input-sm pavInp" placeholder="Other type" field-name="Other type"></div>
</div>	  

<!-- <div id="divOfferTd6" style="display: none; float: left;">
       <select multiple class="blminwidth-50"  id="REPLACEROWIDJobOtherType" name="REPLACEROWIDJobOtherType" field-name="Other">
			
	  </select>
</div> -->
            
<div id="divOfferTd5" style="display: none; float: left;">
	<div class="input-group" id="OfferAttachmentuploadFileGroup">
        <label class="input-group-btn"> <span class="btn btn-primary file-btn"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
          <input type="file" style="display: none;"  multiple id="OfferAttachmentuploadBtn"  class="upload" id="REPLACEROWIDOfferAttachment" name="REPLACEROWIDOfferAttachment" onblur="validateDoc(this);"></span>
		</label> 
		<input type="text" class="form-control file1" readonly id="OfferAttachmentuploadFile" placeholder="Upload File."  >
     </div>                 
</div>

 <!-- <div id="divOfferTd6" style="display: none; float: left;">
	 <div>
         <a href="#" onclick="removeOfferRow(REPLACEROWID);"><i class="glyphicon glyphicon-trash deme"></i></a>
    </div>
</div> --> 
	

<script type="text/javascript">
	function addOfferRow() {
		
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatOffer = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatOffer = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatOffer = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatOffer = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatOffer = 'yy-dd-mm'; 
		  }
		
		$("#isFlushCandidateRequired").val('N');
		
		var checkMaxTsRowNum = $("#maxOfferRowNum").val();
		var tbl = document.getElementById("addOfferTbl");
		var maxRowNum = parseInt($("input[name = 'maxOfferRowNum']").val());
		$("input[name = 'maxOfferRowNum']").val(maxRowNum + 1);
		
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);
		
		var cell0 = row.insertCell(0);
		var divTsTd1Str = document.getElementById("divOfferTd1").innerHTML;
		divTsTd1Str = divTsTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell0.innerHTML = divTsTd1Str;
		
		var cell1 = row.insertCell(1);
		cell1.innerHTML = "<div class='positionRelative date' id='offerDatePic'><input class='form-control input-sm Datepicker' placeholder='Date' name='"+maxRowNum+"joiningDate' id='"+maxRowNum+"joiningDate'  readonly='readonly'  type='text' data-validation='required' field-name='Joining Date' > <span class='input-group-addon input-groupAddon'><i class='fa fa-calendar'></i></span> </div>";
		
		var cell2 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divOfferTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell2.innerHTML = divTsTd3Str; 
		
		var cell3 = row.insertCell(3);
		var divTsTd4Str = document.getElementById("divOfferTd4").innerHTML;
		divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell3.innerHTML = divTsTd4Str; 
			
		
		var cell4 = row.insertCell(4);
		var divTsTd5Str = document.getElementById("divOfferTd5").innerHTML;
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell4.innerHTML = divTsTd5Str; 
		
		/* var cell5 = row.insertCell(5);
		var divTsTd6Str = document.getElementById("divOfferTd6").innerHTML;
		divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell5.innerHTML = divTsTd6Str; */
		
		$("#"+maxRowNum+"applicantId").chosen();
		
		$('.Datepicker').datepicker({
			dateFormat : finalDateFormatOffer,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				removevalidation();
			}
		});
		
		
		
		$(".add_offer_wrap").addClass("add_offer_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		
		getShortListAndSubmittedAndInterviewedCandidates(maxRowNum);
		

		
	   /*  $("#"+maxRowNum+"applicantId").on('change', function (e) {
	    	  getUpdatedSubmittedAndShortListedAndInterviewedCandidates();
	    });  */
		
}		
</script>
<script>
function removevalidation(){
	$('#offerDatePic').find('p.jquery_form_error_message').remove();	
}
</script>
<script type="text/javascript">
	function removeOfferRow(rowId) {
		var rowNum = $("#divTsRow"+rowId).val();
		var maxRowNum = parseInt(rowNum);
		 document.getElementById("divTsRow"+rowId).style.display = 'none';
		 document.getElementById("divTsRow"+rowId).value= '';
		 var  rId = "divTsRow"+rowId;
		  var r = document.getElementById("addOfferTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
	}
</script>


<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 


<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<script>
function cancelAddOffer(){
	 $(".add_offer_wrap").removeClass("add_offer_wrap_active");
	 $(".side_mask").fadeOut(200);
 	 $("body").css('overflow-y', 'auto');
}

</script>  

<script type="text/javascript">
function validateRateAndRateType(){
	
	var offerAmount = document.getElementById("1Amount").value;
		var paymentModeType = $("#1PaymentMode").val();
	 /* if(paymentModeType != null && paymentModeType != "" && paymentModeType != 'undefined'){
			$('#1Amount').removeAttr('data-validation-optional');
			 $('#1Amount').attr("data-validation","required validate_Space validate_float validate_length length1-8"); 
		 }else{
			 $('#1Amount').removeAttr('data-validation');
			 $('#1Amount').attr("data-validation-optional","true"); 
		 } */
		 
	 if(offerAmount != null && offerAmount != ""){
		 $('#1PaymentMode').find('p.jquery_form_error_message').remove();	
		 $('#1PaymentMode').removeAttr('data-validation-optional');
		 $('#1PaymentMode').attr("data-validation","required");
	 }else{
		 $('#1Amount').append('p.jquery_form_error_message');	
		 $('#1PaymentMode').removeAttr('data-validation');
		 $('#1PaymentMode').attr("data-validation-optional","true"); 
	 }
}

</script>

<script type="text/javascript">

   	$('body').on('submit', '#addOffer_form', function(e){  
	 if ($(this).validate(false, validationSettings)){
        e.preventDefault();
        var frm = $('#addOffer_form').serializeFormJSON();
        var con = JSON.stringify(frm);
        con = con.replace(/[{}]/g, "");
        var jsonData = encodeURIComponent(con);
        var requirementId = '${requirementId}';
        
 		var form = $('#addOffer_form')[0];
  		var formData = new FormData(form); 
  		
  		var selectedOfferApplicants =  $('#1applicantId_chosen  span').size();
  		var isContinue = true;
  		
  		var offerAmount = document.getElementById("1Amount").value;
  		var paymentModeType = document.getElementById("1PaymentMode").value;
  		if(offerAmount != '' && paymentModeType.length == 1){
  			isContinue = false;
  		}
  		
//   	 if(isContinue){
  	  if(selectedOfferApplicants <= 50){
  		$("#add_offer").attr("disabled", "disabled");
  		$("#add_offer" ).addClass('button').val('Processing..');  
  		
  		 $("#offerLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
	    	type: "POST",
 	    	data: formData,
 	   		contentType : 'application/json; charset=utf-8',
 	    	enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
 	    	dataType : 'json',
	        url: sourceleadUrl+"/saveOffer?${_csrf.parameterName}=${_csrf.token}&jsonData="+jsonData,
	        success: function(result) {
	        	$(".add_offer_wrap").removeClass("add_offer_wrap_active");
     		$(".side_mask").fadeOut(200);
     		$("body").css('overflow-y', 'auto');
     		var allApplicants = 'all';
	    		requirementPipeline(requirementId,allApplicants); 
	    		pipelineList(requirementId);
	        },
	    });
  	  }else{
  		alertOfferMessage("<b>Selected Applicants should not more than 50</b>");
  	  }
  	  
//   	 }else{
//   		alertOfferMessage("<b>Rate Per is Required for given Rate</b>");
//   	 }
   		   
        return true;
      }
      else{
      	return false;	
         } 
    });
    	
    $('#addOffer_form').showHelpOnFocus();
    $('#addOffer_form').validateOnBlur(false, validationSettings);  
  
  </script>
  
  
  <script type="text/javascript">
function alertOfferMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelOfferPopup();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelInterviewPopup();'></b>";
	result = result +"	</div>";
	$("#offerPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelOfferPopup(){
	$("#offerPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
 
<!-- <script type="text/javascript">
 function getShortListAndSubmittedAndInterviewedCandidates(rowId) { 
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicantId").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllSubmittedAndShortListAndInterviewededCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
					}
		        	$("#"+rowId+"applicantId").empty();
		        	$("#"+rowId+"applicantId").append(output);
		        	$("#"+rowId+"applicantId").trigger("chosen:updated");
			       
		        },
		    });
	
 }
</script>  -->

<script type="text/javascript">
 function getShortListAndSubmittedAndInterviewedCandidates(rowId) { 
	 var clientSubmissions = '${clientSubmissions}';
	 var isClientOffer = "";
	 if(clientSubmissions == 'Y'){
		 if(rowId == null || rowId == "undefined" || rowId == ""){
	     	rowId = $("#maxOfferRowNum").val() - 1;
	     }
		 if(document.getElementById('isClientOffer').checked) {
			 isClientOffer = 'Y';
	     }else{
	    	 isClientOffer = 'N';
	     }
	 }
	 
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicantId").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	
	 var requirementId = $("#reqId").val();
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllSubmittedAndShortListAndInterviewededCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds+"&isClientOffer="+isClientOffer, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+" ("+opt.createdUser+")</option>";
					}
		        	
		        	var isFlushCandidateRequired = $("#isFlushCandidateRequired").val();
		        	if(clientSubmissions == 'Y' && isFlushCandidateRequired == 'Y'){
		        		for(i=rowId ; i > 0; i--){
		       	        	$("#"+(i-1)+"applicantId").empty();
		       	        	$("#"+(i-1)+"applicantId").append(output);
		       	        	$("#"+(i-1)+"applicantId").trigger("chosen:updated");
			       	    }
		        	}
		        	
		        	$("#"+rowId+"applicantId").empty();
		        	$("#"+rowId+"applicantId").append(output);
		        	$("#"+rowId+"applicantId").trigger("chosen:updated");
			       
		        },
		    });
	
 }
</script> 

<script type="text/javascript">
 function getUpdatedSubmittedAndShortListedAndInterviewedCandidates() { 
	 var rowId = $("#maxOfferRowNum").val();
	 var existingApplicantsIds = [];    
	 for(i=rowId ; i > 0; i--){
		 var applicant = $("#"+(i)+"applicantId").val();
		 if(applicant != null && applicant != '' && applicant != 'undefined'){
			 existingApplicantsIds.push(applicant);
		 }
	 }
	 var requirementId = $("#reqId").val();
	 
	 var clientSubmissions = '${clientSubmissions}';
	 var isClientOffer = "";
	 if(clientSubmissions == 'Y'){
		 if(document.getElementById('isClientOffer').checked) {
			 isClientOffer = 'Y';
	     }else{
	    	 isClientOffer = 'N';
	     }
	 }
     
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getAllSubmittedAndShortListAndInterviewededCandidates?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds+"&isClientOffer="+isClientOffer, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
					}
		        	for(var i=rowId-1 ; i > 0; i--){
		        		 var internalOutput = "";
		        		 var applicantIdList= $("#"+(i)+"applicantId").val();
		        		 var arr = jQuery.makeArray(applicantIdList);
                         for(var j = 0; j < arr.length; j++) {
                        	 var label = $("#"+(i)+"applicantId option[value='"+arr[j]+"']").text();
                        	 internalOutput = internalOutput+"<option value='" +arr[j]+ "' selected>"+label+"</option>";
                         }
		        		$("#"+(i)+"applicantId").empty();
		        		$("#"+(i)+"applicantId").append(internalOutput);
			        	$("#"+(i)+"applicantId").append(output);
			        	$("#"+(i)+"applicantId").trigger("chosen:updated");
		       	    }
			       
		        },
		    });
 }
</script>    
  
  
<script>

function NotifyToVendor(){
	
	 if (document.getElementById('notifyToVendor').checked){
	
		 $("#notifyToVendor").val('Y');	
	}else {
		
		 $("#notifyToVendor").val('N');
    
	} 
}

</script>


<script>
function NotifyToApplicant(){
	 if (document.getElementById('notifyToApplicant').checked){
		 $("#notifyToApplicant").val('Y');	
	}else {
		 $("#notifyToApplicant").val('N');
    
	} 
}

</script>


<!-- 
<script type="text/javascript">
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	       
	      
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
});
</script>
 -->

<script>
function myOfferFunction(){
	$("#isFlushCandidateRequired").val('Y');
	getShortListAndSubmittedAndInterviewedCandidates();
}
</script>

<script>
function otherJobType(id){
	var jobType = id.value;
	if(jobType !=null && jobType != "" && jobType != "undefined" && jobType == 'Other'){
		 document.getElementById(id.id+'Check').style.display="block";
		 $('#'+id.id+'JobOtherType').removeAttr('data-validation-optional');
		 $('#'+id.id+'JobOtherType').attr("data-validation","required validate_length length1-50");  
	}else{
		document.getElementById(id.id+'Check').style.display="none";
		 $('#'+id.id+'JobOtherType').removeAttr("data-validation");
		 $('#'+id.id+'JobOtherType').attr('data-validation-optional',"true");
	}
	
}
</script>

<<script type="text/javascript">
$(document.body).on('change', '#OfferAttachmentuploadBtn' ,function(){
	 $('#OfferAttachmentuploadFile').val(this.value.split( '\\' ).pop());
	 $("#OfferAttachmentuploadFile").focus();
	 if(validate_fileupload(this.value)){
	 var docFileSize = $('#OfferAttachmentuploadFile');
		 var docSize = this.files[0].size;
	      $("#OfferAttachmentuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $('#OfferAttachmentuploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#uploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  document.getElementById("OfferAttachmentuploadFile").setAttribute('record-exist','yes');
			  document.getElementById("OfferAttachmentuploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#OfferAttachmentuploadFile').removeAttr( "record-exist record-exist-errormsg");
  		     $('#OfferAttachmentuploadFileGroup').find('p.jquery_form_error_message').remove();
	      }
	 }
});
</script>
  
<jsp:include page="../masterFooter.jsp" />
<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/style.css" />

<script>
		$(document).ready(function() {
			NotifyToApplicant();
			NotifyToVendor();
		});
</script>



<!-- For add New Interview -->
<script>
function addOffer(requirementId){
	 $(".clearUpload").hide();
	$('.action-pop').slideUp();
	var clientSubmissions = '${clientSubmissions}';
	 $.ajax({
	    	type: "POST",
	        url: atsUrl+"/getRequirementWhichIsReleasedOrNot?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	
				var result = "";
				result= result + '	          <div class="clearfix">';
				
				result = result + '<form id="addOffer_form" name="addOffer_form" enctype="multipart/form-data" method="post" >';
				result = result + '<input type="hidden" id="maxOfferRowNum" name="maxOfferRowNum" value="1" />';
				result = result + '<input type="hidden" id="reqId" name="requirementId" value='+requirementId+'>';
				
				result = result + '<input type="hidden" id="isFlushCandidateRequired" name="isFlushCandidateRequired">';
				result = result + '<input type="hidden" id="clientOffer" name="clientOffer" value="N">';
				
				/* result= result + '	            <div class="add_inter_head"> <span>Add Offer</span><a href="javascript:void(0)" onClick="addOfferRow(this);"><i class="fa fa-plus"></i> Add row</a>'; */
				result= result + '	            <div class="add_inter_head mt10 ml10"> <span>Generate Offer</span><span class="text-success col-md-offset-1">Total candidates selected: <b id="selectedOfferApplicantsCount">0</b></span>';
				result= result + '	              <div class="clearfix"></div>';
				result= result + '	            </div>';
				
				if(clientSubmissions == 'Y'){
					result= result + '<div class="clearfix ml checkBox">';
				    result = result + '<input type="checkbox" name="isClientOffer" id="isClientOffer" class="isClient" onclick="myOfferFunction();">';
			        result = result + '<label>&nbsp;&nbsp;<strong>Clients Offer</strong>&nbsp;&nbsp;</label>';
			        result= result + '</div>';
				} 
				
				result= result + '	            <table cellpadding="0" cellspacing="0" id="addOfferTbl" class="hidden">';
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
				
				
			    result= result + '<div class="project_wrap clearfix">                                              ';
				result= result + '<div class="clearfix mt ">                                                       ';
				result= result + '<div class="clearfix minHeightcol6">';
				result= result + '<div class="col-md-12 mb clearfix">  ';
				result= result + '<label class="clearfix">Applicants<span class="text-danger">&nbsp;*</span></label>';
				result= result + '<div class="clearfix reports-head">';
				result= result + '<select multiple class="blminwidth-50"  id="1applicantId" onchange="selectOfferApplicants()" data-placeholder="Select Applicants" name="1applicantId" field-name="Applicant" data-validation="required" >';
				result= result + '</select>';
				result= result + '</div>';
				result= result + '</div>';
				
				result= result + '<div class="col-md-6 clearfix">';
				result= result + '<label class="clearfix">Date of Joining<span class="text-danger">&nbsp;*</span></label>';
				result= result + '<div class="clearfix">';
				result= result + '<input class="form-control Datepicker calendar" placeholder="Date" name="1joiningDate" id="1joiningDate"  readonly="readonly"  type="text" data-validation="required" field-name="Joining Date" >';
				result= result + '</div>';
				result= result + '</div>';

				result= result + '<div class="col-md-6 clearfix">';
				result= result + '<label class="clearfix">Rate / Salary<span class="text-danger">&nbsp;*</span></label> ';
				result= result + '<div class="clearfix row">';
				result= result + '<div class="col-md-3">';
				result= result + '<div class="clearfix">';
				result= result + '      <select class="sel width100percnt" id="1aymentType" name="1PaymentType">';
				result= result + '         		 <option ${SourceLeadWeb_currencyCode ==  "INR" ? "selected" : ""}>INR</option>   ';
				result= result + '                <option ${SourceLeadWeb_currencyCode == "AUD" ? "selected" : ""}>AUD</option>  ';
				result= result + '                <option ${SourceLeadWeb_currencyCode == "USD" ? "selected" : ""}>USD</option>  ';
				result= result + '                <option ${SourceLeadWeb_currencyCode == "GBP" ? "selected" : ""}>GBP</option>  ';
				result= result + '                <option ${SourceLeadWeb_currencyCode == "SGD" ? "selected" : ""}>SGD</option>  ';
				result= result + '                <option ${SourceLeadWeb_currencyCode == "EUR" ? "selected" : ""}>EUR</option>  ';
				result= result + '      </select>';
				result= result + '</div>';
				result= result + '</div>                                                                           ';

				result= result + '<div class="col-md-5 padding_left padding_right">                                ';
				result= result + '<input type="text" value="" class="form-control" id="1Amount" name="1paymentTypAmount" field-name="Rate" data-validation="required validate_float validate_length length1-8" onclick="validateRateAndRateType();">';
				result= result + '</div>                                                                           ';
				
				result= result + '<div class="col-md-4">                                                           ';
				result= result + '<select class="sel width100percnt" id="1PaymentMode" name="1PaymentMode" field-name="Rate Per" onchange="validateRateAndRateType();">';
				result= result + '   <option value="">Rate Per</option>                    ';
				result= result + '   <option value="Hourly">Hourly</option>                ';
				result= result + '   <option value="Weekly">Weekly</option>                ';
				result= result + '   <option value="Bi-weekly">Bi-Weekly</option>          ';
				result= result + '   <option value="Monthly">Monthly</option>              ';
				result= result + '   <option value="Bi-Monthly">Bi-Monthly</option>        ';
				result= result + '   <option value="Semi-Monthly">Semi-Monthly</option>    ';
				result= result + '   <option value="Yearly">Yearly</option>                ';
				result= result + '</select>';
				result= result + '</div>                                                                           ';
				result= result + '</div>                                                                           ';
				result= result + '</div>                                                                           ';
				result= result + '<div class="clearfix"></div>							   ';
				result= result + '<div class="col-md-6 clearfix mt10">';
				result= result + '<label class="clearfix">Employment Type</label>';
				result= result + '<div class="clearfix">                                                           ';
				result= result + '<select class="sel width100percnt" id="1PositionType" name="1PositionType" onchange="otherJobType(this);">';
				result= result + ' 	 <option value="" disabled selected>Select Type</option>';
				result= result + '     <option  ${requirement.jobType  == "C2C" ? "selected" : ""}>C2C</option>';
				result= result + '     <option  ${requirement.jobType  == "W-2" ? "selected" : ""}>W-2</option>';
				result= result + '     <option  ${requirement.jobType  == "C2H" ? "selected" : ""}>C2H</option>';
				result= result + '     <option  ${requirement.jobType  == "Contract" ?  "selected" : ""}>Contract</option>';
				result= result + '     <option  ${requirement.jobType  == "Part Time" ? "selected" : ""}>Part Time</option>';
				result= result + '     <option  ${requirement.jobType  == "Full Time" ? "selected" : ""}>Full Time</option>';
				result= result + '     <option  ${requirement.jobType  == "Other" ? "selected" : ""}>Other</option>';
				result= result + '</select>';
				result= result + '</div>                                                                           ';
				result= result + '</div>                                                                           ';
					
				result= result + '<div class="col-md-6 clearfix mt10" id="1PositionTypeCheck" style="display: none;">';
				result= result + '<label class="clearfix">Other Type</label>';
				result= result + '<div calss="clearfix"><input value=""  type="text" id="1JobOtherType" name="1JobOtherType" class="form-control" placeholder="Other type" field-name="Other type"></div>';
				result= result + '</div>';
				
				result= result + '<div class="col-md-6 clearfix mt10">                                                  ';
				result= result + '<label class="clearfix">Attachment</label>                                       ';
				result= result + '<div class="clearfix">                                                           ';
				result= result + '	<div class="input-group errorInptgroup width100percnt" id="OfferAttachmentuploadFileGroup">';
				result= result + '    <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				result= result + '      <input type="file" style="display: none;" id="OfferAttachmentuploadBtn"  class="upload" id="REPLACEROWIDOfferAttachment" name="REPLACEROWIDOfferAttachment" onblur="validateDoc(this);"></span>';
				result= result + '	</label> ';
				result= result + '	<input type="text" class="form-control" readonly id="OfferAttachmentuploadFile" placeholder="Upload File."  >';
				result= result + '	<div class="clearUpload"></div>';
				result= result + ' </div>    ';
				     
				result= result + '</div>                                                                           ';
				result= result + '</div>                                                                           ';

				result= result + '<div class="col-md-6 clearfix mt10">                                                  ';
				result= result + '<label class="clearfix">Offer Expired Date</label>                               ';
				result= result + '<div class="clearfix">                                                           ';
				result= result + '<input type="text" class="form-control calendar" id="offerExpireDate" name="offerExpireDate" placeholder="Offer Expire Date" readonly>                                ';
				result= result + '</div>                                                                           ';
				result= result + '</div>                                                                           ';

				result= result + '<div class="col-md-6 clearfix mt10">';
				result= result + '<label class="clearfix">Offer Letter Template</label>                            ';
				result= result + '<div class="clearfix">';
				result= result + '<select class="form-control"  id="offerTemplateId"  data-placeholder="Offer Letter Template" name="offerTemplateId">';
				result= result + '</select>';
				result= result + '</div>';
				result= result + '</div>';
				
				result= result + '<div class="clearfix mb"></div>';
				
				result= result + '</div>';
				result= result + '</div>';
				result= result + '</div>';
				
				
				
				
				result= result + ' <div class="row pad2">';
				result = result + '<div class="col-md-12 mt10">';
				result = result + '<div class="ml10 checkBox">';
				
				result= result + ' <label>';
				result= result + '<input type="checkbox" onclick="NotifyToApplicant();" id="notifyToApplicant" value="Y" name="notifyToApplicant">&nbsp;Notify To Applicant</label>';
			   
				if(data =="true"){
					result= result + '<input type="hidden" value="Y"  id="notifyToVendor" name="notifyToVendor"/>';
				} 
				
				result= result + '</div>';
			    result= result + '</div>';
			    result= result + '	              <div class="clearfix"></div>';
			    result= result + '</div>';
				result= result + '	              <div class="clearfix"></div>';
				result= result + '<div class="clearfix mr mb">';
				result = result + '<div class="pull-right">';
				result = result +'				  <button type="submit" id="add_offer" onclick="validateRateAndRateType();" class="btn btn-sm btn-primary mr">Submit</button>';
				result= result + '	              <input type="button" class="btn btn-sm btn-default" value="Cancel" onclick="cancelAddOffer();" >';
				result= result + '	            </div>';
				result= result + '	          </div>';
				result= result + '</div>';
				
				result = result + '</form>';
				result = result + '<div id="offerLoading"></div>';
				result = result + '</div><div id="offerPopUp">';
				result= result + '	          </div>';
				
										
				$("#addOfferDiv").empty();
				$("#addOfferDiv").append(result);
				
				getOfferLetterTemplateList();
				$(".add_offer_wrap").addClass("add_offer_wrap_active");
				$(".side_mask").fadeIn(200);
				$("body").css('overflow-y', 'hidden');
				
				addOfferRow();
				
				
				$(".clearUpload").click(function(){
					$("#OfferAttachmentuploadFileGroup").find("input#OfferAttachmentuploadFile").val("");
					$("#OfferAttachmentuploadFileGroup").find("input#OfferAttachmentuploadBtn").val("");
			        $("#OfferAttachmentuploadFileGroup").find("p.jquery_form_error_message").remove();
					$(".clearUpload").hide();
					$("#OfferAttachmentuploadFile").val('');
				    $("#addOffer_form input[data-id*=OfferAttachmentuploadBtn]").remove();
				    $("#OfferAttachmentuploadFile").removeAttr('record-exist');
				    $("#OfferAttachmentuploadFile").removeAttr('record-exist-errormsg');   
				});
				
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
       <!-- <select multiple class="blminwidth-50"  id="REPLACEROWIDapplicantId" onchange="selectOfferApplicants()" data-placeholder="select Applicants" name="REPLACEROWIDapplicantId" field-name="Applicant" data-validation="required" >
			
	  </select> -->
</div>


<div id="divOfferTd3" style="display: none; float: left;">
   	 <div class="float select-field tblminwidth-75">
           <%-- <select class="form-control" id="REPLACEROWIDPaymentType" name="REPLACEROWIDPaymentType" style="height: 30px;">
	          		 <option ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
	                 <option ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
	                 <option ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
	                 <option ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
           </select> --%>
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
<!-- 	<div id="REPLACEROWIDPositionTypeCheck" style="display: none; "><input value=""  type="text" id="REPLACEROWIDJobOtherType" name="REPLACEROWIDJobOtherType" class="form-control input-sm pavInp" placeholder="Other type" field-name="Other type"></div> -->
</div>	  

<!-- <div id="divOfferTd6" style="display: none; float: left;">
       <select multiple class="blminwidth-50"  id="REPLACEROWIDJobOtherType" name="REPLACEROWIDJobOtherType" field-name="Other">
			
	  </select>
</div> -->
            
<div id="divOfferTd5" style="display: none; float: left;">
	<div class="input-group errorInptgroup" id="OfferAttachmentuploadFileGroup">
        <label class="input-group-btn"> <span class="btn btn-primary file-btn"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
          <input type="file" style="display: none;" id="OfferAttachmentuploadBtn"  class="upload" id="REPLACEROWIDOfferAttachment" name="REPLACEROWIDOfferAttachment" onblur="validateDoc(this);"></span>
		</label> 
		<input type="text" class="form-control file1" readonly id="OfferAttachmentuploadFile" placeholder="Upload File."  >
		<div class="clearUpload"></div>
     </div>                 
</div>

 <!-- <div id="divOfferTd6" style="display: none; float: left;">
	 <div>
         <a href="#" onclick="removeOfferRow(REPLACEROWID);"><i class="glyphicon glyphicon-trash deme"></i></a>
    </div>
</div> --> 
	

<script type="text/javascript">
	function addOfferRow() {
		
		var locationDate ='${SourceLeadWeb_dateFormat}'; 
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
		
		 
		
		$("#"+maxRowNum+"applicantId").chosen();
		
		$('.Datepicker').datepicker({
			dateFormat : finalDateFormatOffer,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				removevalidation();
			}
		});
		
		$('#offerExpireDate').datepicker({
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
  		
  		 $("#offerLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
	    	type: "POST",
 	    	data: formData,
 	   		contentType : 'application/json; charset=utf-8',
 	    	enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
 	    	dataType : 'json',
	        url: atsUrl+"/saveOffer?"+tokenParamName+"="+tokenValue+"&jsonData="+jsonData,
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
	result = result +"	<div class='comMask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelOfferPopup();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelOfferPopup();'></b>";
	result = result +"	</div>";
	$("#offerPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".comMask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelOfferPopup(){
	$("#offerPopUp").empty();
	$(".comMask").fadeOut(200);
}
</script>
 

<script type="text/javascript">
 function getShortListAndSubmittedAndInterviewedCandidates(rowId) { 
	 var isWorkflowConfigured = '${workflowConfigured}';
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
		        url: atsUrl+"/getAllSubmittedAndShortListAndInterviewededCandidates?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds+
		        		"&isClientOffer="+isClientOffer+"&isWorkflowConfigured="+isWorkflowConfigured, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	var output = "";
		        	
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		if(opt.createdUser !=null && opt.createdUser !='null' && opt.createdUser !='' && opt.createdUser != 'undefined'){
			        			output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+" ("+opt.createdUser+")</option>";	
			        		}else{
			        			output = output + "<option value='" +opt.id+ "'>"+opt.candidateName+"</option>";
			        		}
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
		        	$("#"+rowId+"applicantId").attr("data-placeholder", json.length + " Applicants available");
		        	$("#"+rowId+"applicantId").append(output);
		        	$("#"+rowId+"applicantId").trigger("chosen:updated");
			       
		        },
		    });
	
 }
</script> 

<script type="text/javascript">
 function getUpdatedSubmittedAndShortListedAndInterviewedCandidates() { 
	 var isWorkflowConfigured = '${workflowConfigured}';
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
		        url: atsUrl+"/getAllSubmittedAndShortListAndInterviewededCandidates?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&existingApplicantsIds="+existingApplicantsIds+
		        		"&isClientOffer="+isClientOffer+"&isWorkflowConfigured="+isWorkflowConfigured, 
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
	
	if($("#notifyToVendor").is(':checked')){
		$("#notifyToVendor").val('Y');	
		}else {

		$("#notifyToVendor").val('N');

		}
}

</script>


<script>
function NotifyToApplicant(){
	if($("#notifyToApplicant").is(':checked')){
		$("#notifyToApplicant").val('Y');	
		}else {
		$("#notifyToApplicant").val('N');

		}
}

</script>

<script>
function myOfferFunction(){
	$("#isFlushCandidateRequired").val('Y');
	if(document.getElementById('isClientOffer').checked) {
		 $("#clientOffer").val("Y");
    }else{
    	$("#clientOffer").val("N");
    }
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

<script type="text/javascript">
$(document.body).on('change', '#OfferAttachmentuploadBtn' ,function(){
	 $(".clearUpload").show();
	 $('#OfferAttachmentuploadFile').val(this.value.split( '\\' ).pop());
	 $("#OfferAttachmentuploadFile").focus();
	 if(validate_fileupload(this.value)){
	 var docFileSize = $('#OfferAttachmentuploadFile');
		 var docSize = this.files[0].size;
	      $("#OfferAttachmentuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  //document.getElementById("OfferAttachmentuploadFile").value = '';
	    	  $('#OfferAttachmentuploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#OfferAttachmentuploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  document.getElementById("OfferAttachmentuploadFile").setAttribute('record-exist','yes');
			  document.getElementById("OfferAttachmentuploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#OfferAttachmentuploadFile').removeAttr( "record-exist record-exist-errormsg");
  		     $('#OfferAttachmentuploadFileGroup').find('p.jquery_form_error_message').remove();
	      }
	 }
});
</script>
<script>
function getOfferLetterTemplateList(){
	 $.ajax({
	    	type: "POST",
	        url: atsUrl+"/getOfferLetterTemplateList",
	        success: function(data) {
	        	var output = "";
	        	output = output + "<option value=''>Select Offer Letter Template</option>";
	        	for (var i=0; i<data.length; i++)
				{
		        		var opt = data[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.templateName+"</option>";
				}
	        	$("#offerTemplateId").empty();
	        	$("#offerTemplateId").append(output);
	        	$("#offerTemplateId").chosen();
	        	$("#offerTemplateId").trigger("chosen:updated");
	        }
	 });
	 
} 
</script>
  
<jsp:include page="../masterFooter.jsp" />
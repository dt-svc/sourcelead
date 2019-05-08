<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.mask.min.js"></script>
<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Project Bill</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/payablesList'/>">Bill List</a> &raquo; Project Bill</div>
    <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
       <div id="invoiceLoading"></div>
			<form:form id="project_payable_invoice_form"
				name="project_payable_invoice_form" method="post">
				<input type="hidden" name="project" />
				<!-- <input type="hidden" name="frequency"/> -->
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<div class="form-group">
							<label class="col-md-3">Vendor<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 chooRadius errorInptgroup">
								<div class="input-group col-md-10 pull-left">
									<select class="chosen-select form-control"
										id="vendorOrgRelationId" name="vendorOrgRelationId"
										onchange="getVendorContacts();flushValues();getPaymentTerms();getPaymentAccountList();"
										data-validation="required" field-name="Vendor">
									</select> <span class="input-group-addon cursorPointer bg-blue"
										onclick="getVendors();creatProPayInvoiceEmptyVendorContact();"><i
										class="fa fa-refresh "></i></span>
								</div>
								<sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')">
									<a href="<c:url value="/app/addVendor" />" target="_blank"><i
										class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
								</sec:authorize>
								<input type="hidden" name="savedInvoiceId" id="savedInvoiceId">
								<input type="hidden" name="vendorCurrency" id="vendorCurrency">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Vendor Contact<span
								class="text-danger">*</span></label>
							<div class="col-md-9 chooRadius errorInptgroup chosenmbNone">
								<div class="col-md-9 padding_left padding_right">
									<select class="chosen-select form-control" id="vendorContact"
										multiple="multiple" name="vendorContact"
										data-validation="required" field-name="Vendor Contact">
									</select>
								</div>
								<span class="col-md-3 noPad clearfix"> <span
									class="btn btn-primary btn-sm"
									onclick="getVendorContactList();"><i
										class="fa fa-refresh "></i></span> <sec:authorize
										access="hasAnyRole('ROLE_CREATE_VENDOR_CONTACT')">
										<a href="javascript:void(0)" onclick="selectedVendor();"><i
											class="fa fa-plus btn btn-default pull-right minHeBtn"></i></a>
									</sec:authorize>
								</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Bill Id<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9" id="invoiceIdDiv"
								onkeyup="validateBillId();">
								<input class="form-control" id="invoiceId" name="invoiceId"
									placeholder="Bill Id" type="text"
									data-validation="required validate_Alphanumeric validate_length length2-100"
									field-name="Bill Id">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Bill Date<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 select-field" id="errInvoiceDate">
								<div class="input-group">
									<input class="form-control calendar" id="invoiceDate"
										name="invoiceDate" placeholder="Bill Date"
										value="${currentDate}" type="text" data-validation="required"
										field-name="Bill Date" readonly> <span
										class="input-group-addon" onClick="clearInvoiceDate();"><i
										class="colorRed fa fa-times cursorPointer"></i></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Payment Term</label>
							<div class="col-md-9 select-field">
								<select class="chosen-select form-control" id="paymentFrequency"
									name="paymentFrequency" onchange="getDueDateByFrequency();">
									<option value=''>--Select Payment Term--</option>
									<option>10 days</option>
									<option>15 days</option>
									<option>20 days</option>
									<option>30 days</option>
									<option>60 days</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Due Date<span class="text-danger">&nbsp;*
							</span><i class='fa fa-info-circle'
								title='The deadline for the bill payment'></i></label>
							<div class="col-md-9 errorInptgroup" id="errDuedate">
								<div class="input-group">
									<input class="form-control calendar" placeholder="Due Date"
										id="dueDate" name="dueDate" type="text"
										data-validation="required" field-name="Due Date" readonly>
									<span class="input-group-addon" onClick="clearDueDate();"><i
										class="colorRed fa fa-times cursorPointer"></i></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Project<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 select-field">
								<c:if test="${!empty isDraft}">
									<select class="chosen-select form-control" id="projectId"
										name="projectId" onchange="getProjectFrequency();"
										data-validation="required" field-name="Project Name"
										disabled="disabled">
									</select>
								</c:if>
								<c:if test="${empty isDraft}">
									<select class="chosen-select form-control" id="projectId"
										name="projectId" onchange="getProjectFrequency();"
										data-validation="required" field-name="Project Name">
									</select>
								</c:if>
								<!-- <input type="hidden" id="clientProjectIdHidden" value="" name="projectId"> -->
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3">Frequency<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9 select-field">
								<c:if test="${! empty isDraft}">
									<select class="chosen-select form-control" id="frequency"
										name="frequency" data-validation="required"
										field-name="Frequency" onchange="getProjectAssignedMem();"
										disabled="disabled">
									</select>
								</c:if>
								<c:if test="${empty isDraft}">
									<select class="chosen-select form-control" id="frequency"
										name="frequency" data-validation="required"
										field-name="Frequency" onchange="getProjectAssignedMem();">
									</select>
								</c:if>
								<!-- <input type="hidden" id="clientProjectFrequencyHidden" value="" name="frequency"> -->
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-3">Payment Account</label>
							<div class="col-md-9">
								<select class="sel width100percnt" id="paymentAccountId"
									field-name="paymentAccount Name" name="paymentAccount">
									<option value="">--Select Account--</option>
								</select>
							</div>
						</div>

						<div class="clearfix"></div>

						<div class="form-group">
							<label class="col-md-3">Billing Address</label>
							<div class="col-md-9">
								<textarea class="form-control" rows="4"
									placeholder="Billing Address" id="billingAddress"
									name="billingAddress"></textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3">Notify To Others</label>
							<div class="col-md-9">
								<textarea class="form-control" rows="4" id="notifyToOthers"
									name="notifyToOthers" field-name="NotifyTo"
									placeholder="Enter multiple Email Id's separated by commas(,)"></textarea>
							</div>
							<div class="clearfix"></div>
						</div>


					</div>
				</div>
				<div class="clearfix"></div>
				<!-- Drap and Drop Table Row -->
				<!-- Table starts here-->
				<div
					class="project_wrap_table mt25 invoice_table table_custompadding"
					id="invoiceTable">
					<div id="tableBody"></div>
				</div>
				<!-- Table Ends here-->

				<!-- Adding Content Starts herer -->
				<div id="bottomGrid" style="display: none;" class="mt">
					<div class="clearfix invoice_other">
						<div class="col-md-5">

							<div class="clearfix mb">
								<label><strong>Message</strong></label>
								<textarea class="form-control" rows="2" id="comment"
									name="comment"></textarea>
							</div>
							<div class="clearfix mb invoiceAttachment">
								<input type='hidden' name='invoiceAttcahmentMaxRowNumber'
									id='invoiceAttcahmentMaxRowNumber' value='1' />
								<table width="100%" width="100%" cellpadding="0" cellspacing="0"
									id="invoiceAttachmentTbl">
									<tbody>
										<tr id='divAttachmentTsRow0'>
											<td><label><strong>Attachments</strong></label>
												<div id="timesheetDocumentsId">
													<div class="timesheet_week_comm invoiceDocs width100percnt">
														<span><div id="savedAttachments"></div></span>
													</div>
												</div>
												<div
													class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add width100percnt">
													<div class="form-group" id='0invoicedocFileuploadFileGroup'>
														<span> <input type='file' name='0invoicedocFile'
															id='0invoicedocFile' field-name='Attachment'
															class='timesheetAttachmentValidation'
															onchange="attachFile(0);"><i
															class='fa fa-times mt5' mouseOver="Clear" title="Clear"
															onClick='remove()'></i><i class='fa fa-plus'
															mouseOver="Add" title="Add"
															onClick='addInvoiceAttachment()'></i>
														</span>
													</div>
												</div></td>
										</tr>
									</tbody>
								</table>

							</div>
						</div>
						<div class="col-md-7">
							<div class="form-horizontal">
								<div class="form-group hidden">
									<label class="control-label col-md-7"><strong>Subtotal</strong></label>
									<div class="col-md-5 text-right">
										<input type="text" value="00:00" id="subTotal" name="subTotal"
											class="ReadTextFli">
									</div>
								</div>
								<!-- Discount UI -->
								<div class="form-group">
									<div class="col-md-7 select-field">
										<div class="row">
											<div class="col-md-7 paddingRight">
												<select class="form-control input-sm" id="disCount"
													name="disCount"
													onchange="calculateDisCount();validateDiscount();">
													<option value="percentage">Discount %</option>
													<option value="flat">Discount Value</option>
												</select>
											</div>
											<div class="col-md-5 paddingLeft" id="discountValueDiv">
												<input class="form-control input-sm allownumericwithdecimal"
													id="discountValue" name="discountValue"
													placeholder="Flat / Discount" type="text"
													onblur="calculateDisCount();" onkeyup="validateDiscount();">
											</div>
										</div>
									</div>
									<div class="col-md-5 text-right">
										<input class="form-control input-sm" id="discountTotal"
											name="discountTotal" type="text" placeholder="00.00" readonly>
									</div>

								</div>
								<div class="form-group">
									<label class="col-md-7 text-right"><strong>After
											Discount Total</strong></label>
									<div class="col-md-5 text-right">
										<span class="pull-left">${SourceLeadWeb_currencyCode}</span><input
											type="text" id="afterDiscountTotal" name="afterDiscountTotal"
											value="00.00" class="ReadTextFli" readonly>
									</div>
								</div>

								<div id="lineItemAddRows"></div>
								<div class="form-group mt">
									<label class="col-md-7 text-right"><strong>Total</strong></label>
									<div class="col-md-5 text-right">
										<span class="pull-left">${SourceLeadWeb_currencyCode}</span><input
											type="text" value="00:00" id="grandTotal" name="grandTotal"
											class="ReadTextFli" readonly></span>
									</div>
								</div>

								<div id="conversionId"></div>

							</div>
						</div>
					</div>
				</div>
				<!-- Adding Content Starts herer -->
				<div id="lineItemPopUp"></div>
				<!-- Drap and Drop Table Row -->
				<div id="payableAlertPopUp"></div>

				<footer class="form-footer">
					<div class="pull-right mr">
						<button type="button" class="btn btn-sm btn-primary"
							id="saveInvoice" onclick="saveBill('DRAFT');">Save</button>
						<button type="button" class="btn btn-sm btn-success ml"
							id="sendInvoice" onclick="saveBill('SENT')">Send</button>
						<button type="button" class="btn btn-sm btn-default ml" onClick="javascript:window.location.href = '<c:url value='${appPath}/payablesList'/>'">Cancel</button>
					</div>
				</footer>
			</form:form>
		</div>
    </div>
  </section>
  
  <script>
  function remove(){
	  $('#0invoicedocFile').val("");
  }
  </script>
    
<!-- Diplays client contacts details when clien selected -->

<script>
function clearInvoiceDate(){
	$("#invoiceDate").val("");
	};
	
function clearDueDate(){
	$("#dueDate").val("");
	};
</script>

<script>
function getProjectsList(projectName){
	var orgRelationId = $("#vendorOrgRelationId").val();
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientProjectsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
	success :function(json) {
	//var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>--Select Project--</option>";
	for (var i=0; i<json.data.length; i++){
	var opt = json.data[i];
	/* output = output + "<option value='" +opt.id+ "'>"+opt.projectName+"</option>"; */
	var selected = "";
	if(projectName != null && projectName != "" && projectName !="undefined" && projectName == opt.projectName){
		selected = 'selected';
	}
	output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.projectName+"</option>";
	}
	$("#projectId").empty();
	$("#projectId").append(output);
	$('#projectId').trigger("chosen:updated");
	},
	});

}
</script>

<!-- Get Project Frequency Based on project id -->
<script type="text/javascript">
function getProjectFrequency(invoiceFrequency){
	var projectId = null;
	projectId = $("#projectId").val();
	 if(projectId == null){
	projectId = '${projectId}';
	} 
	if(projectId != null && projectId != '' && projectId != 'undefined'){
			$.ajax({
			type : "GET",
			url: sourceleadUrl+"/getProjectFrequency?"+tokenParamName+"="+tokenValue+"&projectId="+projectId,
			success :function(json) {
// 				var json = JSON.parse(data);
				var output = "";
				output = output + "<option value=''>Select Frequency</option>";
				for (var i=0; i<json.data.length; i++)
				{
				var opt = json.data[i];
				/* output = output + "<option value='" +opt+ "'>"+opt+"</option>"; */
				var selected = "";
				if(invoiceFrequency == opt){
					selected = 'selected';
					/* getProjectAssignedMem1();  */
				}
				output = output + "<option value='" +opt+ "' "+selected+">"+opt+"</option>";
				}
				$("#frequency").empty();
				$("#frequency").append(output);
				$('#frequency').trigger("chosen:updated");
			},
			});
	 }
	else{
		$("#frequency").empty();
		var output = "";
		output = output + "<option value=''>Select Frequency</option>";
		$("#frequency").append(output);
		$('#frequency').trigger("chosen:updated");
	}

}
</script>


<!-- Get all assigned members for the project based on project id -->
<script type="text/javascript">
function getProjectAssignedMem(){
	var frequency = $("#frequency").val();
	var projectId = $("#projectId").val();
	var orgRelationId = $("#vendorOrgRelationId").val();
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getAllProjectVendorAssignedMembers?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&frequency="+frequency+"&orgRelationId="+orgRelationId+"&contractType=vendorSow",
	success :function(json) {
		populateInlineItemsGrid(json);
	},
	}); 

}
</script>

<script>
function getProjectAssignedMem1(invoiceLineItemsList){
	 populateInlineItemsGrid1(invoiceLineItemsList); 

}

</script>
<script>
function populateInlineItemsGrid(json){
	var currencyCode = '${SourceLeadWeb_currencyCode}';
    if(json.data != '' && json.data !=null){
//         var json = JSON.parse(data);
        var result = '';
            result = result +'    <table width="100%">';
            result = result +' <thead>';
            result = result +' <tr>';
            result = result +' <th>Date</th>';
            result = result +' <th>Associate</th>';
            result = result +' <th>Description</th>;'
            result = result +' <th>SOW / PO</th>';
            result = result +' <th>Rate Hourly</th>';
            result = result +' <th>Total Hours</th>';
            result = result +' <th>Amount</th>';
            result = result +' </tr>';
            result = result +' </thead>';
            result = result +' <tbody>';
            result = result +' <input type="hidden" value='+json.data.length+' id="maxTsRowNum" name="maxTsRowNum">';
        for (var i = 0; i < json.data.length; i++) { 
             var opt = json.data[i]; 
             result = result +'<tr>';
             result = result +' <td>';
             result = result +'<div class="inputCustom">';
             result = result +' <input class="calendar paddingRight" id="'+i+'sdate-input" name="lineItems['+i+'].invoiceStartDate" placeholder="Start Date" value='+opt.startDate+' type="text" readonly>';
             result = result +'<input class="calendar paddingLeft" id="'+i+'edate-input" name="lineItems['+i+'].invoiceEndDate" placeholder="End Date" value='+opt.endDate+' type="text" readonly>';
             result = result +' <input type="hidden" value='+opt.submissionApproverHierarchyId+' id="'+i+'submissionApproverHierarchyId" name="lineItems['+i+'].submissionApproverHierarchyId">';
             result = result +' <input type="hidden" value='+opt.submissionInvoiceId+' id="'+i+'submissionInvoiceId" name="lineItems['+i+'].submissionInvoiceId">';
             result = result +'</div>';
             result = result +' </td>';
             result = result +'<td>';
             result = result +' <div class="tbldata-dotdot-vsm select-field" id="'+i+'candidateName" name="lineItems['+i+'].candidateName" data-validation="required" field-name="Candidate Name">'+opt.candidateName+'</div>';
             result = result +' <input type="hidden" value='+opt.candidateId+' id="'+i+'candidate" name="lineItems['+i+'].candidate">';
            /*  result = result +' <input type="hidden" value='+opt.userId+' id="'+i+'user" name="lineItems['+i+'].user">'; */
             result = result +' </td>';
             result = result +'  <td class="tblminwidth-150">';
             result = result +' <textarea type="text" class="form-control input-sm" placeholder="Description" id="'+i+'Description" name="lineItems['+i+'].Description" data-validation="validate_length length1-100" field-name="Description"></textarea>';
             result = result +'  </td>';
             result = result +'  <td>';
             result = result +' <div class="tbldata-dotdot-100 select-field">';
             result = result +'<select class="form-control input-sm" id="'+i+'sow" name="lineItems['+i+'].sow" onchange="getSowRate('+i+');">';
             result = result +'<option value="">select Sow</option>';
             if(opt.contractsList != '' && opt.contractsList != null && opt.contractsList != 'undefined'){
                 for (var j = 0; j < opt.contractsList.length; j++) { 
                     var opt1 = opt.contractsList[j]; 
                     result = result +'<option value='+opt1.id+'>'+opt1.contractTitle+'</option>';
                 } 
             }
             result = result +' </select>';
             result = result +'</div>';
             result = result +'</td>';
             result = result +'<td>';
             result = result +'<div class="tbldata-dotdot_80">';
             if(opt.rate != null && opt.rate !='' && opt.rate !='undefined'){
                 result = result +'<input class="form-control input-sm allownumericwithdecimal"  id="'+i+'rate" name="lineItems['+i+'].rate" value='+opt.rate+' placeholder="Rate" type="text" data-validation="required validate_float" field-name="Rate" onblur="totalHoursCalculateAmount('+i+');">'; 
             }else{
                 result = result +'<input class="form-control input-sm allownumericwithdecimal" id="'+i+'rate" name="lineItems['+i+'].rate"  data-validation="required validate_float" field-name="Rate" placeholder="Rate" type="text" onblur="totalHoursCalculateAmount('+i+');">';
             }
             result = result +'</div>';
             result = result +'</td>';
             result = result +' <td>';
             result = result +'<div class="tbldata-dotdot-vsm">';
             result = result +'  <input class="form-control input-sm inlineField" id="'+i+'totalHours" name="lineItems['+i+'].totalHours" placeholder="HHH:MM" value='+opt.totalHours+' data-validation="required" field-name="Total Hours" type="text" onblur="totalHoursCalculateAmount('+i+');">';
             result = result +'</div>';
             result = result +'</td>';
             result = result +'<td>';
             result = result +'<input class="form-control formControl-styles input-sm text-right" id="'+i+'amount" name="lineItems['+i+'].amount" value="0.0" placeholder="Amount" type="text" readonly="readonly">';
             result = result +'</td>';
             result = result +'</tr>';
             /* getSow(i,opt.candidateId); */
        }
        result = result +' <tfoot class="table_footer">';
        result = result +' <tr>';
        result = result +' <td colspan="5"></td>';
        result = result +'<td><strong>Sub Total</strong></td>';
        result = result +' <td><div class="tblminwidth-150"> <span class="pull-left paddingRight">'+currencyCode+'</span> <input type="text" value = "00:00" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli"></div></td>';
        result = result +' </tr>';
        result = result +' </tfoot> ';
        result = result +' </tbody>';
        result = result +' </table>';
        $("#tableBody").empty();
        $("#tableBody").append(result)
        maskme();
        $("#currencyCode").html(currencyCode);
        document.getElementById("bottomGrid").style.display="block";
        document.getElementById("bottomGrid").style.display="block";
        document.getElementById("lineItemAddRows").style.display="block";
        document.getElementById("tableBody").style.display="block";
        document.getElementById("saveInvoice").disabled = false;
        document.getElementById("sendInvoice").disabled = false;
        addTaxDivs();    
        addConversionDiv();
    }else{
        document.getElementById("bottomGrid").style.display="none";
        document.getElementById("lineItemAddRows").style.display="none";
        document.getElementById("tableBody").style.display="none";
        document.getElementById("saveInvoice").disabled = true;
        document.getElementById("sendInvoice").disabled = true;
//         alert("No MembersFound");
		alertMessageInPayable()
    }
    
}
</script>

<script type="text/javascript">
function addConversionDiv(){
	var result = "";
	var vendorCurrency = $("#vendorCurrency").val();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	if(vendorCurrency != currencyCode){
		result = result +'<div class="row">';
		result = result +'<div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
		result = result +'<div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span class="pull-left" id="currencyCode"></span><div class="pull-right"><input type="text" id="conversionFrom" placeholder="00:00" class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +' <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div  class="paddingLeft col-md-6"><span id="vendorCurrencyCode"></span><div id="exchangeCurrency" class="pull-right"><input  class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div  class="paddingLeft col-md-6"><span id="convertedCurrencyCode"></span><div id="exchangeRate" class="pull-right"><input class="ReadTextFli ReadTextFli90" readonly="" type="text"></div></div></div>';
		result = result +'</div>';
		result = result +'</div>';
		$("#conversionId").empty();
		$("#conversionId").append(result);
	}
}
</script>

<script type="text/javascript">
function addConversionDivEditMode(){
	var result = "";
	var vendorCurrency = $("#vendorCurrency").val();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	if(vendorCurrency != currencyCode){
		result = result +'<div class="row">';
		result = result +'<div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
		result = result +'<div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span class="pull-left" id="currencyCode">'+currencyCode+'</span><div class="pull-right"><input type="text" id="conversionFrom" placeholder="00:00" class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +' <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div  class="paddingLeft col-md-6"><span id="vendorCurrencyCode">'+vendorCurrency+'</span><div id="exchangeCurrency" class="pull-right"><input  class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div  class="paddingLeft col-md-6"><span id="convertedCurrencyCode">'+currencyCode+' to '+vendorCurrency+'</span><div id="exchangeRate" class="pull-right"><input class="ReadTextFli ReadTextFli90" readonly="" type="text"></div></div></div>';
		result = result +'</div>';
		result = result +'</div>';
		$("#conversionId").empty();
		$("#conversionId").append(result);
	}
}
</script>

<script type="text/javascript">
function alertMessageInPayable(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>No Members Found</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelPayableAlert();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPayableAlert();'></b>";
	result = result +"	</div>";
	$("#payableAlertPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelPayableAlert(){
	$("#payableAlertPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>



<script>
function populateInlineItemsGrid1(data){
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	var result = '';
		result = result +'	<table width="100%">';
	    result = result +' <thead>';
	    result = result +' <tr>';
	    result = result +' <th>Date</th>';
	    result = result +' <th>Associate</th>';
	    result = result +' <th>Description</th>';
	    result = result +' <th>SOW / PO</th>';
	    result = result +' <th>Rate Hourly</th>';
	    result = result +' <th>Total Hours</th>';
	    result = result +' <th>Amount</th>';
	    result = result +' </tr>';
	    result = result +' </thead>';
	    result = result +' <tbody>';
	    result = result +' <input type="hidden" value='+data.length+' id="maxTsRowNum" name="maxTsRowNum">';
    for (var i = 0; i < data.length; i++) { 
    	 var opt = data[i]; 
    	 result = result +'<tr>';
    	 result = result +' <input type="hidden" value='+opt.id+' id="lineItems['+i+'].lineItemId"   name="'+i+'lineItemId">';
    	 result = result +' <td>';
    	 result = result +'<div class="inputCustom">';
    	 result = result +' <input class="calendar paddingRight" id="'+i+'sdate-input" name="lineItems['+i+'].invoiceStartDate" placeholder="Start Date" value='+opt.startDateStr+' type="text" readonly>';
    	 result = result +'<input class="calendar paddingLeft" id="'+i+'edate-input" name="lineItems['+i+'].invoiceEndDate" placeholder="End Date" value='+opt.endDateStr+' type="text" readonly>';
    	 result = result +' <input type="hidden" value='+opt.submissionApproverHierarchyId+' id="'+i+'submissionApproverHierarchyId" name="lineItems['+i+'].submissionApproverHierarchyId">';
    	 result = result +'</div>';
    	 result = result +' </td>';
    	 result = result +'<td>';
    	 result = result +' <div class="tbldata-dotdot-vsm select-field" id="'+i+'candidateName" name="'+i+'candidateName"  data-validation="required" field-name="Candidate Name">'+opt.candidateName+'</div>';
    	 result = result +' <input type="hidden" value='+opt.candidateId+' id="'+i+'candidate" name="lineItems['+i+'].candidate">';
    	/*  result = result +' <input type="hidden" value='+opt.userId+' id="'+i+'user" name="lineItems['+i+'].user">'; */
    	 result = result +' </td>';
    	 result = result +'  <td>';
    	 if(opt.inlineDescription !=null && opt.inlineDescription != '' && opt.inlineDescription !='undefined'){
    		 result = result +' <textarea type="text" class="form-control input-sm tbldata-dotdot" placeholder="Description" id="'+i+'Description" name="lineItems['+i+'].Description" data-validation="required validate_length length1-100" field-name="Description">'+opt.inlineDescription+'</textarea>';
    	 }else{
    		 result = result +' <textarea type="text" class="form-control input-sm tbldata-dotdot-me" placeholder="Description" id="'+i+'Description" name="lineItems['+i+'].Description" data-validation=" required validate_length length1-100" field-name="Description"></textarea>';
    	 }
         
         result = result +'  </td>';
    	 result = result +'  <td>';
    	 result = result +' <div class="tbldata-dotdot-100 select-field">';
    	 result = result +'<select class="form-control input-sm" id="'+i+'sow" name="lineItems['+i+'].sow" onchange="getSowRate('+i+');">';
    	 result = result +'<option value="">select Sow</option>';
    	 if(opt.contractList != '' && opt.contractList != null && opt.contractList != 'undefined'){
             for (var j = 0; j < opt.contractList.length; j++) { 
                 var opt1 = opt.contractList[j]; 
                 var selected = '';
                 if(opt.sowContractName !='' && opt.sowContractName != null && opt.sowContractName != 'undefined'){
                	 selected = 'selected';
                 }
                 result = result +'<option value=' +opt1.id+ ' '+selected+'>'+opt1.contractTitle+'</option>';
             } 
         }
    	 result = result +' </select>';
    	 result = result +'</div>';
    	 result = result +'</td>';
    	 result = result +'<td>';
    	 result = result +'<div class="tbldata-dotdot_80 validationTxt">';
    	 if(opt.rate != null && opt.rate !='' && opt.rate !='undefined'){
    		 result = result +'<input class="form-control input-sm allownumericwithdecimal"  id="'+i+'rate" name="lineItems['+i+'].rate" value='+opt.rate+' placeholder="Rate"  data-validation="required" field-name="Rate" type="text" onblur="totalHoursCalculateAmount('+i+');">'; 
    	 }else{
    		 result = result +'<input class="form-control input-sm allownumericwithdecimal" id="'+i+'rate" name="lineItems['+i+'].rate"  placeholder="Rate" data-validation="required" field-name="Rate" type="text" onblur="totalHoursCalculateAmount('+i+');">';
    	 }
    	 /* result = result +'<input class="form-control input-sm" id="'+i+'rate" name="'+i+'rate" value='+opt.rate+' placeholder="Rate" type="text" onblur="calculateTotalAmount('+i+');">'; */
    	 result = result +'</div>';
    	 result = result +'</td>';
    	 result = result +' <td>';
    	 result = result +'<div class="tbldata-dotdot-100">';
    	 result = result +'  <input class="form-control input-sm inlineField" id="'+i+'totalHours" name="lineItems['+i+'].totalHours" value='+opt.totalHours+' placeholder="HHH:MM" value='+opt.totalHours+' data-validation="required" field-name="Total Hours" type="text" onblur="totalHoursCalculateAmount('+i+');">';
    	 result = result +'</div>';
    	 result = result +'</td>';
    	 result = result +'<td>';
    	 if(opt.amount !=null && opt.amount !='' && opt.amount !='undefined'){
    		 result = result +'<input class="form-control formControl-styles input-sm text-right" id="'+i+'amount" name="lineItems['+i+'].amount" value='+opt.amount+' placeholder="Amount" type="text" readonly="readonly">';	 
    	 }else{
    		 result = result +'<input class="form-control formControl-styles input-sm text-right" id="'+i+'amount" name="lineItems['+i+'].amount"  placeholder="Amount" type="text" readonly="readonly">';
    	 }
    	
    	 result = result +'</td>';
    	 result = result +'</tr>';
    	/* getSow(i,opt.candidateId,opt.contractId); */
    }
    result = result +' <tfoot class="table_footer">';
    result = result +' <tr>';
    result = result +' <td colspan="5"></td>';
    result = result +'<td><strong>Total</strong></td>';
    result = result +' <td><div class="tblminwidth-150"> <span class="pull-left paddingRight">'+currencyCode+'</span> <input type="text" value = "00.00" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli"></div></td>';
    result = result +' </tr>';
    result = result +' </tfoot> ';
    result = result +' </tbody>';
    result = result +' </table>';
    $("#tableBody").empty();
    $("#tableBody").append(result)
    maskme();

    document.getElementById("bottomGrid").style.display="block";
	document.getElementById("bottomGrid").style.display="block";
	document.getElementById("lineItemAddRows").style.display="block";
	document.getElementById("tableBody").style.display="block";
    addTaxDivs();
    addConversionDivEditMode();
}
</script>

<!-- Get contract sow between client and the associate NOT USING AS OF NOW-->
<script>
  function getSow(contractId){
	  var orgRelationId = $("#vendorOrgRelationId").val();
	  var maxRowNum = $("#maxTsRowNum").val();
		  for(var id=0;id < maxRowNum;id++){
			     var result = "";
	        	result = result + "<option value='' >select sow</option>"
			  var candidateId = $("#"+id+'candidate').val();
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getSowcontractListByAssociateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&orgRelationId="+orgRelationId+"&contractType=vendorSow",
			        success: function(json) {
			        	
			        	if(json.data != null && json.data != ''){
// 			        		var json = JSON.parse(data);
				        	 for (var i=0; i<json.data.length; i++)
							{
					        		var opt = json.data[i];
					        		var selected = '';
					        		 if(contractId != null && contractId != "" && contractId !="undefined" && contractId == opt.id){
					        			selected = 'selected';
					        		} 
					        		 result = result +"<option value="+opt.id+" >"+opt.contractTitle+"</option>";
							} 
			        	}
			        	 $('#'+id+'sow').empty();
			        	 $('#'+id+'sow').append(result);
			        },
			        
			    });
		 } 
	 
	 
  }
  </script> 
 
  
<script>
function getSowRate(id){
	var contractId = $("#"+id+'sow').val();
	if(contractId !='' && contractId != 'undefined' && contractId != null){
	$.ajax({
    	type: "GET",
    	url: sourceleadUrl+"/getSowRateByContractId?"+tokenParamName+"="+tokenValue+"&contractId="+contractId,
        success: function(json) {
        	 if(json.object != null && json.object != ''){
//         		var json = JSON.parse(data);
	        	 $ ('#'+id+'rate').val(json.object.billingAmount);
	        	 totalHoursCalculateAmount(id);
        	} 
        },
        
    });
}else{
	 $ ('#'+id+'rate').val(0);
	 totalHoursCalculateAmount(id);
}
}
</script>
  
  
  <script>
  function totalHoursCalculateAmount(rowId){
	  var rate = $("#"+rowId+"rate").val();
	  var totalHours = $("#"+rowId+"totalHours").val();
	     var split = totalHours.split(':');
	     var hours = split[0];
 	     var minute = split[1];
 	     
 	     var hours1 = hours.charAt(0);
		 var hours2 = hours.charAt(1);
		 var hours3 = hours.charAt(2);
		 var min1 = minute.charAt(0);
		 var min2 = minute.charAt(1);
		
		 if(!isNaN(hours1) && isNaN(hours2) && isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours1+":00");
				 calAddRowAmount("00"+hours1,"00",rowId); 
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours1+":0"+min1);
				 calAddRowAmount("00"+hours1,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours1+":0"+min2);
				 calAddRowAmount("00"+hours1,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("00"+hours1+":"+minute);
				 calAddRowAmount("00"+hours1,minute,rowId);
			 }
		 }
		 else if(isNaN(hours1) && !isNaN(hours2) && isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours2+":00");
				 calAddRowAmount("00"+hours2,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours2+":0"+min1);
				 calAddRowAmount("00"+hours2,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours2+":0"+min2);
				 calAddRowAmount("00"+hours2,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("00"+hours2+":"+minute);
				 calAddRowAmount("00"+hours2,minute,rowId);
			 }
		 }
		 else if(isNaN(hours1) && isNaN(hours2) && !isNaN(hours3)){
			if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours3+":00");
				 calAddRowAmount("00"+hours3,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours3+":0"+min1);
				 calAddRowAmount("00"+hours3,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("00"+hours3+":0"+min2);
				 calAddRowAmount("00"+hours3,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("00"+hours3+":"+minute);
				 calAddRowAmount("00"+hours3,minute,rowId);
			 }
		 }
		 else if(!isNaN(hours1) && !isNaN(hours2) && isNaN(hours3)){
			if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":00");
				 calAddRowAmount("0"+hours1+hours2,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":0"+min1);
				 calAddRowAmount("0"+hours1+hours2,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":0"+min2);
				 calAddRowAmount("0"+hours1+hours2,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("0"+hours1+hours2+":"+minute);
				 calAddRowAmount("0"+hours1+hours2,minute,rowId);
			 }
			
		 }
		 else if(isNaN(hours1) && !isNaN(hours2) && !isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":00");
				 calAddRowAmount("0"+hours2+hours3,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":0"+min1);
				 calAddRowAmount("0"+hours2+hours3,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":0"+min2);
				 calAddRowAmount("0"+hours2+hours3,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("0"+hours2+hours3+":"+minute);
				 calAddRowAmount("0"+hours2+hours3,minute,rowId);
			 }
		 }
		 else if(!isNaN(hours1) && isNaN(hours2) && !isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":00");
				 calAddRowAmount("0"+hours1+hours3,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":0"+min1);
				 calAddRowAmount("0"+hours1+hours3,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":0"+min2);
				 calAddRowAmount("0"+hours1+hours3,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val("0"+hours1+hours3+":"+minute);
				 calAddRowAmount("0"+hours1+hours3,minute,rowId);
			 }
		 }
		 else if(!isNaN(hours1) && !isNaN(hours2) && !isNaN(hours3)){
			 if(isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val(hours+":00");
				 calAddRowAmount(hours,"00",rowId);
			 }
			 else if(!isNaN(min1) && isNaN(min2)){
				 $("#"+rowId+"totalHours").val(hours+":0"+min1);
				 calAddRowAmount(hours,"0"+min1,rowId);
			 }
			 else if(isNaN(min1) && !isNaN(min2)){
				 $("#"+rowId+"totalHours").val(hours+":0"+min2);
				 calAddRowAmount(hours,"0"+min2,rowId);
			 }
			 else{
				 $("#"+rowId+"totalHours").val(hours+":"+minute);
				 calAddRowAmount(hours,minute,rowId);
			 }
		 }else{
			 calAddRowAmount("000","00",rowId);
		 }
		 conversion();
  }
  
  function  conversion(){
	   var orgRelationId = $("#vendorOrgRelationId").val();
	   var currencyCode = '${SourceLeadWeb_currencyCode}';
	   var grandTotal = $("#grandTotal").val();
	   if(grandTotal != '' && grandTotal != 'undefined'){
		   $.ajax({
				type : "GET",
				url: sourceleadUrl+"/getConversionExchangeRate?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&grandTotal="+grandTotal,
				success :function(response) {
					if(response.object !='' && response.object != 'undefined' && response.object != null){
// 						var json = JSON.parse(data);
						$("#exchangeRate").html(response.object.exchangeRate);
						$("#exchangeCurrency").html(response.object.exchangeCurrency);
						$("#vendorCurrencyCode").html(response.object.vendorCurrencyCode);
						$("#convertedCurrencyCode").html(currencyCode +' to '+ response.object.vendorCurrencyCode);
					}
				},
				});   
	   }
	  
		
}
  
  function calAddRowAmount(hours,minute,rowId){
	  var rate = $("#"+rowId+"rate").val();
	  var split = hours.split(':');
	  var splitHours = split[0];
	  var hourminits = splitHours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
		 setHours(finalmin,rowId);
		 var perMinit = rate / 60;
	     var totlalAmount = (perMinit * finalmin);
	     $('#'+rowId+'amount').empty();
	     $('#'+rowId+'amount').val(totlalAmount.toFixed(3));
	     calculateSum();
  }
  </script>


<!-- Converting minutes to hours if minutes >60 -->
<script>
function setHours(finalmin,rowId){
	 var actualHours = Math.floor(finalmin / 60); 
     var actualMinutes = finalmin % 60;
     var actualTotal = 0;
     if (actualHours >= 10 && actualHours < 100) {
			actualHours = '0' + actualHours;
	  }
     if (actualHours < 10) {
			actualHours = '00' + actualHours;
	  }
	 if (actualMinutes < 10) {
		actualMinutes = '0' + actualMinutes;
	 }
	 if(!isNaN(actualHours) && !isNaN(actualMinutes)){
		 actualTotal = actualHours +":"+ actualMinutes;
	 }else{
		 actualTotal =  "000:00";
	 }
	 $('#'+rowId+'totalHours').empty();
     $('#'+rowId+'totalHours').val(actualTotal);
}
</script>

<!-- calculating sub total with all row total amount -->
<script>
function calculateSum(){
	 var maxRowNum = $("#maxTsRowNum").val();
	 var sum = 0;
	  for(var i=0;i < maxRowNum;i++){
		  if(document.getElementById(i+"amount")){
	    		 if(document.getElementById(i+"amount").value){
	    			var rowAmount = $('#'+i+'amount').val();
		 	    	sum = parseFloat(sum) + parseFloat(rowAmount);
	    		 }
	    		
	    	 } 
	 }  
	  $("#inoviceTotal").val(sum.toFixed(3));
	  $("#subTotal").val(sum.toFixed(3));
	  $("#grandTotal").val(sum.toFixed(3));
	  $("#conversionFrom").val(sum.toFixed(3));
	  $("#afterDiscountTotal").val(sum.toFixed(3));
	  calculateDisCount();
	  calculateTaxDiscount();
}
	
</script>
  
 <!-- Calculationg Total Discount With Sub Total --> 
<script type="text/javascript">
function calculateDisCount(){
	
	var discountType = $("#disCount").val();
	var subTotal = $("#subTotal").val();
	var discountValue = $("#discountValue").val();
	var total ='';
	if(discountType == 'flat'){
		if(discountValue && subTotal > 0){
			total = subTotal-discountValue;
			$("#discountTotal").val(discountValue);
			$("#grandTotal").val(total);
			$("#conversionFrom").val(total);
			$("#afterDiscountTotal").val(total.toFixed(2));
		}else{
			$("#discountTotal").val("00.000");
			$("#grandTotal").val(subTotal);
			$("#conversionFrom").val(subTotal);
			$("#afterDiscountTotal").val(subTotal);
		}
		
	}
	if(discountType == 'percentage'){
		if(discountValue && subTotal > 0){
			var perCentValue = (subTotal * discountValue)/100;
			total = subTotal - perCentValue;
			$("#discountTotal").val(perCentValue.toFixed(3));
			$("#grandTotal").val(total);
			$("#conversionFrom").val(total);
			$("#afterDiscountTotal").val(total.toFixed(3));
		}else{
			$("#discountTotal").val("00.000");
			$("#conversionFrom").val(subTotal);
			$("#afterDiscountTotal").val(subTotal);
		}
		
	}
	 calculateTaxDiscount();
	 conversion();
	
	
/* 	var discountValue = $("#discountValue").val();
	if(discountValue !='' && discountValue !='undefined' ){
		var subTotal = $("#subTotal").val();
		var disCountType = $("#disCount").val();
					 	 if(disCountType == "flat"){
					 		 var total = subTotal-discountValue;
					 		 $("#discountTotal").val(discountValue); 
					 		$("#grandTotal").val(total);
					 		$("#conversionFrom").val(total);
					 		$("#afterDiscountTotal").val(total.toFixed(2));
					 		
					 	  }
					 	  if(disCountType == "percentage"){
					 		var perCentValue = (subTotal * discountValue)/100;
					 		 var total = subTotal - perCentValue;
					 		 $("#discountTotal").val(perCentValue);
					 		$("#grandTotal").val(total);
					 		$("#conversionFrom").val(total);
					 		$("#afterDiscountTotal").val(total.toFixed(2));
					 	 }

					 	 calculateTaxDiscount();
					 	conversion();
} */

}
</script>

<!-- Adding Dynamic Tax Div Ids -->
<!-- ********************************************* Add Rows AddTax Table functionality start  ************************************************************ -->	
  
  <div id="divTaxTd1" style="display: none; float: left;">
	<div class="select-field tblminwidth-75">
		  <div class="tblminwidth-50">
		        <input type="text" class="form-control input-sm" value="" placeholder="Tax type" name='invoiceTax[REPLACEROWID].taxType' id='REPLACEROWIDtaxType' data-validation="required validate_length length2-100" field-name="Tax Type">
		        <input type="hidden" name="invoiceTax[REPLACEROWID].invoiceTaxId" id="REPLACEROWIDinvoiceTaxId">
		  
		  </div>
	 </div>
</div>

  <div id="divTaxTd2" style="display: none; float: left;">
	 <div class="btn-group tblminwidth-75">
	 <select class="form-control input-sm" id="REPLACEROWIDtaxdiscountType" name="invoiceTax[REPLACEROWID].taxdiscountType" onchange="calculateTaxDiscount();validateRowDiscount(REPLACEROWID);">
		 <option value="percentage">Percentage</option>
		 <option value="flat">Flat</option>
 		  </select>
	  </div>
  </div>
  
  <div id="divTaxTd3" style="display: none; float: left;">
     <div class="tblminwidth-75" id="REPLACEROWIDdiscountRowDiv">
        <input type="text" class="form-control input-sm allownumericwithdecimal" value="" placeholder="Flat / Discount" name='invoiceTax[REPLACEROWID].taxdiscountvalue' id='REPLACEROWIDtaxdiscountvalue' onblur="calculateTaxDiscount();" onkeyup="validateRowDiscount(REPLACEROWID);">
    </div>
  </div>
  
  <div id="divTaxTd4" style="display: none; float: left;">
	  <div class="tblminwidth-75">  
	       <input type="text" class="form-control input-sm" value ="00.00" placeholder="Total Value" name='invoiceTax[REPLACEROWID].taxTotal' id='REPLACEROWIDtaxTotal' readonly="readonly">
	   </div>
  </div>
  <div id="divTaxTd5" style="display: none; float: left;">
         <a class="text-danger tblminwidth-50" title="Remove row" onclick="removeTaxRow(REPLACEROWID)"><i class="fa fa-remove"></i></a>
  </div>
 <script>
	function addTax(){  
				var checkMaxTsRowNum = $("#maxTaxRowNum").val();
				var tbl = document.getElementById("addTaxTbl");
				var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
				$("input[name = 'maxTaxRowNum']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum+1);
				row.setAttribute("id", "divTaxRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divTaxTdStr = document.getElementById("divTaxTd1").innerHTML;
				divTaxTdStr = divTaxTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divTaxTdStr;
				
				var cell1 = row.insertCell(1);
				var divTaxTdStr1 = document.getElementById("divTaxTd2").innerHTML;
				divTaxTdStr1 = divTaxTdStr1.replace(/REPLACEROWID/g, (maxRowNum));
				cell1.innerHTML = divTaxTdStr1;
				
				var cell2 = row.insertCell(2);
				var divTaxTdStr2 = document.getElementById("divTaxTd3").innerHTML;
				divTaxTdStr2 = divTaxTdStr2.replace(/REPLACEROWID/g, (maxRowNum));
				cell2.innerHTML = divTaxTdStr2;
				
				
				var cell3 = row.insertCell(3);
				var divTaxTdStr3 = document.getElementById("divTaxTd4").innerHTML;
				divTaxTdStr3 = divTaxTdStr3.replace(/REPLACEROWID/g, (maxRowNum));
				cell3.innerHTML = divTaxTdStr3;
				
				var cell4 = row.insertCell(4);
				var divTaxTdStr4 = document.getElementById("divTaxTd5").innerHTML;
				divTaxTdStr4 = divTaxTdStr4.replace(/REPLACEROWID/g, (maxRowNum));
				cell4.innerHTML = divTaxTdStr4;
				 maskme();
				
	}			
</script>
<!-- Removing Tax Rows -->
<script type="text/javascript">
	function removeTaxRow(rowId) {
		
		//After removing the row calculate the amount and append the grand total
		var grandTotal =  $("#grandTotal").val();
		var taxTotal = $("#"+rowId+"taxTotal").val(); 
		var first=parseFloat(grandTotal);
        var second=parseFloat(taxTotal);
        total = (first-second);
        $("#grandTotal").val(total); 
        $("#conversionFrom").val(total);
       //=======================================
        conversion();
		document.getElementById('divTaxRow' + rowId).style.display = 'none';
		var r = document.getElementById('addTaxTbl').rows[rowId+1];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
		
	}
</script>

<script type="text/javascript">
function singleRowTaxDiscount(){
	var taxType = $("#0taxdiscountType").val();
    var taxValue = $("#0taxdiscountvalue").val();
    var afterDiscountTotal = $("#afterDiscountTotal").val();
     var total ='';
    if(taxType == 'flat'){
        total = parseInt(afterDiscountTotal) + parseInt(taxValue);
        $("#0taxTotal").val(taxValue);
        $("#grandTotal").val(total);
        $("#conversionFrom").val(total);
    }
    if(taxType == 'percentage'){
        var perCentValue = (afterDiscountTotal * taxValue)/100;
        var first=parseFloat(perCentValue.toFixed(3));
        var second=parseFloat(afterDiscountTotal);
        total = (first+second).toFixed(2);
        $("#0taxTotal").val(first);
        $("#grandTotal").val(total);
        $("#conversionFrom").val(total);
    }   
}
</script>

<!-- Calculating Tax discount -->
 <script type="text/javascript">
function calculateTaxDiscount(){
	
	var maxTaxRowNum = $("#maxTaxRowNum").val();
	var afterDiscountTotal = $("#afterDiscountTotal").val();
	
	for(var i=0; i<maxTaxRowNum; i++){
		if($("#"+i+"taxdiscountType").val() != '' && $("#"+i+"taxdiscountType").val() != null && $("#"+i+"taxdiscountType").val() != 'undefined'){
			var taxType = $("#"+i+"taxdiscountType").val();
		    var taxValue = $("#"+i+"taxdiscountvalue").val();
		   
		    if(taxType == 'flat'){
		    	if(taxValue){
		    		first = parseFloat(taxValue);
			        $("#"+i+"taxTotal").val(first.toFixed(3));
		    	}
		    	
		    }
		    
		    else if(taxType == 'percentage'){
		        var perCentValue = (afterDiscountTotal * taxValue)/100;
		        first = parseFloat(perCentValue);
		        $("#"+i+"taxTotal").val(first.toFixed(3));
		    }   
		}
	}
	
	var taxGrandTotal = 0;
	for(var i=0; i<maxTaxRowNum; i++){
		if($("#"+i+"taxTotal").val() != null && $("#"+i+"taxTotal").val() != '' && $("#"+i+"taxTotal").val() != 'undefined'){
			taxGrandTotal = parseFloat(taxGrandTotal + parseFloat($("#"+i+"taxTotal").val()));			
		}
	}
	
	var grandTotal = parseFloat(afterDiscountTotal) + parseFloat(taxGrandTotal)
	 $("#grandTotal").val(grandTotal.toFixed(3));
   	 $("#conversionFrom").val(grandTotal.toFixed(3)); 
   	conversion();
}
</script>


<!-- Adding Attachment Div Ids -->

<!-- ********************************************* Add Rows resume Table functionality start  ************************************************************ -->
  
  <div id="divInvoiceAttachmentTd1" style="display: none; float: left;">
  			<div class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add width100percnt">
                <div class="form-group" id="REPLACEROWIDinvoicedocFileuploadFileGroup"> 
               		 <span> 
               		 	<input type="file" name='REPLACEROWIDinvoicedocFile' id='REPLACEROWIDinvoicedocFile' onchange='onchange="attachFile(REPLACEROWID);"validateContractsUploadFile(REPLACEROWID)'>
               		    <i class="fa fa-times mt5" mouseOver="Delete" title="Delete" onclick="removeInvoiceAttachmentRow(REPLACEROWID);"></i>
               		   </span>
                </div>
             </div>
</div>


<script>
	function addInvoiceAttachment(){  
				var checkMaxTsRowNum = $("#invoiceAttcahmentMaxRowNumber").val();
				var tbl = document.getElementById("invoiceAttachmentTbl");
				var maxRowNum = parseInt($("input[name = 'invoiceAttcahmentMaxRowNumber']").val());
				$("input[name = 'invoiceAttcahmentMaxRowNumber']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "divAttachmentContractsRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divAttachmentTdStr = document.getElementById("divInvoiceAttachmentTd1").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divAttachmentTdStr;
				
	}			
</script>

<!-- Remove attachment Div Ids -->
<script type="text/javascript">
	function removeInvoiceAttachmentRow(rowId) {
		 document.getElementById("divAttachmentContractsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentContractsRow"+rowId).value= '';
		 var  rId = "divAttachmentContractsRow"+rowId;
		  var r = document.getElementById("invoiceAttachmentTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>

<script type="text/javascript">
 function validateBillId() {
	 var invoiceId = document.getElementById("invoiceId").value; 
	
	 if(invoiceId != null && invoiceId != ""){
		
         //$("#create_user" ).addClass('button').val('Processing..');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateInvoiceId?"+tokenParamName+"="+tokenValue+"&invoiceId="+invoiceId+"&invoiceType=payables", 
		        success: function(data) {
		        	 if(data == true){
		        		 $("#saveInvoice").attr("disabled", "disabled");
						 $("#sendInvoice").attr("disabled", "disabled"); 
		        		 $('#invoiceIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#invoiceId").after("<p class='jquery_form_error_message'>"+invoiceId+" is Already Existed</span>");
						  document.getElementById("invoiceId").setAttribute('record-exist','yes');
						  document.getElementById("invoiceId").setAttribute('record-exist-errorMsg',invoiceId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#invoiceId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#invoiceIdDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#saveInvoice" ).addClass('us-btn').val('Save');
                         $("#saveInvoice").removeAttr("disabled");
                         $("#sendInvoice" ).addClass('us-btn').val('Send');
                         $("#sendInvoice").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>  


<script>
function saveBill(saveType){
         
         if ($('#project_payable_invoice_form').validate(false, validationSettings)){
        /* 	 $("#clientProjectIdHidden").val('');
        	var  pmIdValueClient = $("#projectId option").eq($("#projectId").prop("selectedIndex")).val();
  			$("#clientProjectIdHidden").val(pmIdValueClient);
  			
  			var  frequencyvalue = $("#frequency option").eq($("#frequency").prop("selectedIndex")).val();
  			$("#clientProjectFrequencyHidden").val(frequencyvalue); */
        	 
 	 	     var formData = getFormData1("project_payable_invoice_form");
  			
  			if ($("#notifyToOthers").val() !== "") {
  				for (var e in $("#notifyToOthers").val().split(",")) {
  				if (!jQueryFormUtils.validateEmail($("#notifyToOthers").val().split(",")[e].replace(/\s+/g, ''))) {
  				flag = false;
  				$("#notifyToOthers").parent().append("<p class='jquery_form_error_message'>Enter valid email id</p>");
  				return;
  				}
  				}
  				}
		        	  $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		    	     
		           	  $.ajax({
			        	  url: sourceleadUrl+"/saveInvoice?"+tokenParamName+"="+tokenValue+"&saveType="+saveType+"&invoiceType=payable",
		        		  type: "POST",
		        		  data: formData,
		        		  processData: false,
		        		  contentType: false,
		        		  success: function (result) {
// 		        				 $.post(sourceleadUrl+"/saveInvoice?"+tokenParamName+"="+tokenValue+"&saveType="+saveType+"&invoiceType=payable", formData, function(result) {
		        	 		  if (result && result.response === "INVALID_DATA") {
			          		    showError(result.responseMessage);
			          		} else if (result.response === "AWKWARD") {
			          		    location.href = "/errorPage";
			          		} else if (result.response === "SUCCESS") {
			          			$("#invoiceLoading").removeAttr("style");
	    		     			location.replace(sourceleadUrl+"/payablesList");
	    		     			$("#selector1").removeAttr("style");
					    		$("#selector1").css({ display: "block" });
					    		if(saveType == 'SENT'){
					    			$("#msgDiv").html("Successfully you have sent a Bill..");
					    		}else if(saveType == 'DRAFT'){
					    			$("#msgDiv").html("Bill saved Successfully..");
					    		}
	    		     			msgFadein();
			          		} else if (result.indexOf('Sign In') !== -1) {
			          			location.replace(sourceleadUrl);
			          		} else {
			          		    showError("Operation failed, please try again");
			          		}
		        		  $("#invoiceLoading").hide();
		        	  }
		  			   }); 
		        	  
		    	         return true; 
	        }else{
	    		return false;
	    	  } 
	   
}

$('body').on('blur', '#project_payable_invoice_form', function() {
	$("#project_payable_invoice_form").showHelpOnFocus();
	$("#project_payable_invoice_form").validateOnBlur(false, validationSettings);  
}); 

function msgFadein() {
	$("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
}
</script>
 
<script>
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>
 
 <!-- Adding Date Pickers -->
  <script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatProjectPayableSave = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatProjectPayableSave = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatProjectPayableSave = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatProjectPayableSave = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatProjectPayableSave = 'yy-dd-mm'; 
		  }
		$('#invoiceDate').datepicker({
			dateFormat : finalDateFormatProjectPayableSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				/* var dt = toDate("#invoiceDate");
				dt.setDate(dt.getDate() + 1);
				$("#dueDate").datepicker("option", "minDate", dt); */
				
				  var dt = $(this).datepicker('getDate', '+1d');
		            dt.setDate(dt.getDate() + 1);
		            $("#dueDate").datepicker("option", "minDate", dt);
				
						errInvoiceDate();
						getDueDateByFrequency();
			}
		});
	  $('#dueDate').datepicker({
			dateFormat : finalDateFormatProjectPayableSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				/* var dt = toDate("#dueDate");
				dt.setDate(dt.getDate() - 1);
				$("#invoiceDate").datepicker("option", "maxDate", dt); */
				
			    var dt = $(this).datepicker('getDate', '-1d');
	            dt.setDate(dt.getDate() - 1);
	            $("#invoiceDate").datepicker("option", "maxDate", dt);
					errDueDate();
			}
		});
		
	});
</script>
<script>
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}
</script>
<!-- To Get the Existed invoice details to Edit -->
<script>
	function loadInvoiceDetails(invoiceUUID){
		 
		 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getInvoiceDetailsByInvoiceUUID?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
		         success: function(data) {
		        	 var json = JSON.parse(data);
		        	 var result = '';
		        	 if(json.paymentAccountId > 0) {
		        		 getPaymentAccountList(json.vendorOrgRelationId,json.paymentAccountId)
		        	 }
		        	 var selected = 'selected';
		        	 result = result +'<option disabled selected>--Select Vendor--</option>';
		        	 result = result +'<option value='+json['vendorOrgRelationId']+' '+selected+' >'+json['orgName']+'</option>';
		        	 $("#vendorOrgRelationId").empty();
		           	 $("#vendorOrgRelationId").append(result); 
		           	 $('#vendorOrgRelationId').trigger("chosen:updated");
		           	 
		           	 $('#vendorCurrency').val(json['vendorCurrency']);
// 		           	getVendorContacts(json['vendorContactId'],json['projectName']);
					getVendorContacts(json['contactUsers'],json['projectName']);
// 		           	getProjectsList(json['projectName']);
		           	getProjectFrequency(json['invoiceFrequency'],json['id']);
		           	getProjectAssignedMem1(json['invoiceLineItemsList']);
		           	$("#invoiceId").val(json['invoiceId']);
		           	
		           	if(json['paymentFrequency'] != null && json['paymentFrequency'] != '' && json['paymentFrequency'] != 'undefined'){
		           		$("#paymentFrequency").val(json['paymentFrequency']);
		           	    $('#paymentFrequency').trigger("chosen:updated");
		           	}
		           	
		           	$("#invoiceDate").val(json['invoiceDateStr']);
		           	$("#dueDate").val(json['dueDateStr']);
		           	
		           	if(json['billingAddress'] != null && json['billingAddress'] != '' && json['billingAddress'] != 'undefined'){
		           		$("#billingAddress").val(json['billingAddress']);
		           	}
		           	
		           	if(json['notifyToOthers'] != null && json['notifyToOthers'] != '' && json['notifyToOthers'] != 'undefined'){
		           		$("#notifyToOthers").text(json['notifyToOthers']);
		           	}
		           	
		        	if(json['accountDetails'] != null && json['accountDetails'] != '' && json['accountDetails'] != 'undefined'){
		           		$("#accountDetails").val(json['accountDetails']);
		           	}	
		           	/* buildLineItemAndTaxAddRows(); */
		           	
		           	/* editInvoice(json['invoiceLineItemsList']);*/
		        	$("#inoviceTotal").val(json['subTotal']);
		        	$("#subTotal").val(json['subTotal']);
		        	$("#disCount").val(json['discountType']);
		        	$("#discountValue").val(json['discountAmount']);
		        	$("#discountTotal").val(json['discountValue']);
		        	$("#comment").val(json['invoiceComment']);
		        	$("#afterDiscountTotal").val(json['afterDiscountValue']);http://localhost:8080/app/getReceivableInvoiceByUUID?invoiceUUID=sTljRS5n&projectId=15
		        	
		        	editTax(json['invoiceTaxList'])
		        	
		        	getSavedAttachments(json['invoiceDocumentsList']);
		        	
		        	$("#grandTotal").val(json['total']);
		        	$("#conversionFrom").val(json['total']); 
		        	 $("#exchangeCurrency").html(json['convertAmount']);
		        	 $("#exchangeRate").html(json['exchangeRate']);
		        	
		        	$("#savedInvoiceId").val(json['id']); 
		        	
		        	var test = json['invoiceParentId'];
		        	if(json['invoiceStatus'] == "Rejected" && test != null){
			   			 document.getElementById("sendInvoice").style.display="none";
			   			 document.getElementById("saveInvoice").style.display="none";
			       	}else{
			       		document.getElementById("sendInvoice").style.display="inline-block";
			   			document.getElementById("saveInvoice").style.display="inline-block";
			       	}
		        	
		        } 
    		});
	}
	</script>
	
	 <script type="text/javascript">
$(document).ready(function(){
	var invoiceUUID = '${invoiceUUID}';
	if(invoiceUUID != null && invoiceUUID != '' && invoiceUUID != 'undefined'){
		loadInvoiceDetails(invoiceUUID);
	}else{
		getVendors();
// 		getProjectsList();
		$("#invoiceId").val("");
		$("#paymentFrequency").val("");
		$("#paymentFrequency").trigger("chosen:updated");
		$("#dueDate").val("");
	}
	
	
});
</script>
  
<!-- Get alla clients when editing -->
<script>
function getVendors(){
	var orgRelationId ='${orgRelationId}';
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getPayableVendorsList?"+tokenParamName+"="+tokenValue+"",
		success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Vendor</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		if(orgRelationId == opt.id){
			var selected = 'selected';
			output = output + "<option value='" +opt.id+ "'"+selected+">"+opt.orgName+"</option>";
		}else{
			output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
		}
		}
		$("#vendorOrgRelationId").empty();
		$("#vendorOrgRelationId").append(output);
		$('#vendorOrgRelationId').trigger("chosen:updated");
		
		if(orgRelationId != null && orgRelationId != '' && orgRelationId != 'undefined'){
			getVendorContacts();
// 			flushValues();
			getPaymentTerms();
		}
	},
	});

	}
</script>

 <!--show tax values in Edit mode  -->
<script>
	function editTax(invoiceTaxList){  
		for(var i=0; i<invoiceTaxList.length; i++){
			var opt = invoiceTaxList[i];
				 
				 var checkMaxTsRowNum = $("#maxTaxRowNum").val();
					var tbl = document.getElementById("addTaxTbl");
					var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
					$("input[name = 'maxTaxRowNum']").val(maxRowNum + 1);
					
					var row = tbl.insertRow(maxRowNum+1);
					row.setAttribute("id", "divTaxRow" + maxRowNum);
					
					var cell0 = row.insertCell(0);
					var divTaxTdStr = document.getElementById("divTaxTd1").innerHTML;
					divTaxTdStr = divTaxTdStr.replace(/REPLACEROWID/g, (maxRowNum));
					cell0.innerHTML = divTaxTdStr;
					
					var cell1 = row.insertCell(1);
					var divTaxTdStr1 = document.getElementById("divTaxTd2").innerHTML;
					divTaxTdStr1 = divTaxTdStr1.replace(/REPLACEROWID/g, (maxRowNum));
					cell1.innerHTML = divTaxTdStr1;
					
					var cell2 = row.insertCell(2);
					var divTaxTdStr2 = document.getElementById("divTaxTd3").innerHTML;
					divTaxTdStr2 = divTaxTdStr2.replace(/REPLACEROWID/g, (maxRowNum));
					cell2.innerHTML = divTaxTdStr2;
					
					
					var cell3 = row.insertCell(3);
					var divTaxTdStr3 = document.getElementById("divTaxTd4").innerHTML;
					divTaxTdStr3 = divTaxTdStr3.replace(/REPLACEROWID/g, (maxRowNum));
					cell3.innerHTML = divTaxTdStr3;
					
					var cell4 = row.insertCell(4);
					var divTaxTdStr4 = document.getElementById("divTaxTd5").innerHTML;
					divTaxTdStr4 = divTaxTdStr4.replace(/REPLACEROWID/g, (maxRowNum));
					cell4.innerHTML = divTaxTdStr4;
					/* maskme(); */
					
					$('#'+maxRowNum+'taxType').val(opt.taxName);
					$('#'+maxRowNum+'taxdiscountType').val(opt.taxType);
					$('#'+maxRowNum+'taxdiscountvalue').val(opt.taxValue);
					$('#'+maxRowNum+'taxTotal').val(opt.taxAmount);
					$('#'+maxRowNum+'invoiceTaxId').val(opt.id);
					
		}
	}			
</script>
<!-- show existed docs in edit mode -->
<script>
	function getSavedAttachments(invoiceDocumentsList){
		var result = '';
		if(invoiceDocumentsList.length > 0){
			
			 for (var i = 0; i < invoiceDocumentsList.length; i++) { 
				 var opt = invoiceDocumentsList[i]; 
	    		
				 result = result +"<strong id='invoiceDocument"+opt.id+"'>";
	    		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\'"+opt.fileUrl+"\' title='download' download></a></i>";
					 result = result +"<input type='button' value='"+opt.fileName+"'>";
					 result = result +"</a>";
					 result = result +"<input class='dele' type='button' onClick='deleteDocument("+opt.id+");' >";
					 result = result +"</strong>";
					
	    	 }
			 
			 $("#savedAttachments").empty();
			 $("#savedAttachments").append(result);
		}
    	
	}
	
     function deleteDocument(id){
	    var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Are you sure to delete Attachment</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteInvoiceDocument("+id+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
		result = result +"	</div>";
		$("#lineItemPopUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
   }
	
	
	function deleteInvoiceDocument(documentId){
		
		if(documentId){
			$.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/deleteInvoiceDocumentBydocumentId?"+tokenParamName+"="+tokenValue+"&documentId="+documentId, 
		         success: function(data) {
		        	 document.getElementById("invoiceDocument"+documentId).remove();
		        	 $("#lineItemPopUp").empty();
		        	 $(".project_inn_mask").fadeOut(200);
		         }
			});
			
	     }
	  }
	
	</script>
	
	<script type="text/javascript">
function cancel(){
	$("#lineItemPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function addTaxDivs(){
	var result = "";
	result = result +'  <div class="addtax_table">';
    result = result +'  <input type="hidden" id="maxTaxRowNum" name="maxTaxRowNum" value="0" />';
    result = result +'  <table width="100%" cellpadding="0" cellspacing="0" class="table-bordered table-striped table-responsive">';
    result = result +'  <thead id="addTaxTbl">';
    result = result +'  <tr>';
    result = result +'  <th colspan="5">';
    result = result +'  Add Tax';
    result = result +'  <button type="button" class="btn btn-sm btn-primary buttonCustom" title="Add Tax" onclick="addTax()">';
    result = result +'  <i class="fa fa-plus"></i></button>';
    result = result +'  </th>';
    result = result +'  </tr>';
    result = result +'  </thead>';
    result = result +'  <tbody>';
    result = result +'  <tr id="divTaxTsRow0">';
    result = result +'  </tr>';
    result = result +'  </tbody>';
    result = result +'  </table>';
    result = result +'  </div>';
    $("#lineItemAddRows").empty();
    $("#lineItemAddRows").append(result);
    maskme();
}
</script>

<script> 
function getPaymentTerms(){
	var orgRelationId = $("#vendorOrgRelationId").val();
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getPaymentTermsAndBillindAddress?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
		success :function(data) {
			 var json = JSON.parse(data);
			 for (var i=0; i<json.length; i++)
				{
				var repot = json[i];
				 	if(repot.paymentTerms != null && repot.paymentTerms != '' && repot.paymentTerms != 'undefined'){
		           		$("#paymentFrequency").val(repot.paymentTerms);
		           	    $('#paymentFrequency').trigger("chosen:updated");
		           	  getDueDateByFrequency();
		           	}else{
		           		$("#paymentFrequency").val('');
		           	    $('#paymentFrequency').trigger("chosen:updated");
		           	}
		           	
		           	if(repot.billingAddress != null && repot.billingAddress != '' && repot.billingAddress != 'undefined'){
		           		$("#billingAddress").val(repot.billingAddress);
		           	}else{
		           		$("#billingAddress").val('');
		           	}
				}
			
		}
	});
}
</script>
<script>
function getDueDateByFrequency(){
	 var invoiceDate = document.getElementById("invoiceDate").value;
	 var paymentFrequency = document.getElementById("paymentFrequency").value;
	 var locationCode ='${SourceLeadWeb_locationCode}';
	 if(paymentFrequency && invoiceDate){
	    $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
	    $.ajax({
			type : "GET",
			url: sourceleadUrl+"/getDueDateByFrequency?"+tokenParamName+"="+tokenValue+"&paymentFrequency="+paymentFrequency+"&invoiceDate="+invoiceDate+"&locationCode="+locationCode,
			success :function(dueDateStr) {
				
				$("#dueDate").val(dueDateStr);
				$("#invoiceLoading").removeAttr("style");
			}
		});
		
	}else{
		$("#dueDate").val('');
	}
}
</script> 


<script>
function flushValues(){
	$("#invoiceId").val('');
	/* $("#invoiceDate").val(''); */
	$("#dueDate").val('');
	$("#paymentFrequency").val('');
	$("#frequency").val('').trigger("chosen:updated");
	$("#billingAddress").val('');
	$("#projectId").val('').trigger("chosen:updated");
	$("#comment").val('');
	$("#discountValue").val('');
	$("#discountTotal").val('');
	$("#subTotal").val('0.0');
	$("#afterDiscountTotal").val('0.0');
	$("#grandTotal").val('0.0');
	$("#conversionFrom").val('0.0'); 
	
}
function errDueDate(){
	$('#errDuedate').find('p.jquery_form_error_message').remove();
}
function errInvoiceDate(){
	$('#errInvoiceDate').find('p.jquery_form_error_message').remove();
} 
</script>

<!-- Get Vendor contacts based on vendor -->
<script>
function getVendorContacts(vendorContactId,projectName){
	var orgRelationId = $("#vendorOrgRelationId").val();
	$("#vendorContact").empty();
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getPayableVendorContactsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,

		success :function(json) {
			
		var output = "";
			for (var i=0; i<json.data.length; i++)
			{
				var opt = json.data[i];
				if(vendorContactId != null && vendorContactId != "" && vendorContactId !="undefined"){
					var array = vendorContactId.split(",");
					if(array.includes(opt.spocUserId.toString())){
						output = output + "<option value='"+opt.spocUserId+"' selected >"+opt.ownerName+"("+opt.spocName+")</option>";
					}else{
						output = output + "<option value='"+opt.spocUserId+"' >"+opt.ownerName+"("+opt.spocName+")</option>";
					}
				}else{
					output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+"("+opt.spocName+")</option>";
				}
				$("#vendorCurrency").val(opt.vendorCurrency);
			}

		$("#vendorContact").empty();
		$("#vendorContact").append(output);
		$('#vendorContact').trigger("chosen:updated");
		if(vendorContactId == null || vendorContactId == '' || vendorContactId == 'undefined'){
			buildLineItemAndTaxAddRows();
		}
	},
	});
	document.getElementById("bottomGrid").style.display="none";
	document.getElementById("lineItemAddRows").style.display="none";
	document.getElementById("tableBody").style.display="none";
	getProjectsList(projectName);
	}
</script>

<script>
$(document).ready(function() {  
	maskme();    
 });
</script>
<script>
function maskme(){

	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $(".inlineField").mask("Hhh:Mm"); 
	
    $(".allownumericwithdecimal").on("keypress keyup blur",function (evt) {
    	
    	var charCode = (evt.which) ? evt.which : evt.keyCode;
    	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
    	return false;

    	return true;
   	         
//     $(this).val($(this).val().replace(/[^0-9\.]/g,''));
//         if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
//             event.preventDefault();
//         }
    });
}
</script>

<script type="text/javascript">
function attachFile(id){
	fileUpload(""+id+"invoicedocFile", "invoiceDocuments", "project_payable_invoice_form","fileInfo");
}
</script>

<!-- <script>
function clearDate(){
	$("#dueDate").val("");
	};	
</script> -->

<script>

function creatProPayInvoiceEmptyVendorContact(){
	$("#vendorContact").empty();
	 $('#vendorContact').append('<option value="">--select Vendor contact--</option>');
   $('#vendorContact').trigger("chosen:updated");
}
</script>
<script>
	 function selectedVendor(){
		 var vid =  $("#vendorOrgRelationId").val();
		 window.open(sourceleadUrl+"/addVendorContact?vid="+vid,'_blank');
	 }
</script>

<script>
function validateDiscount() {
	$('#discountValueDiv').parent().find('p.jquery_form_error_message').remove(); 
	 var selectedTextInvoice = document.getElementById("disCount").value;
	 var enteredValue = document.getElementById("discountValue").value;
	 
	 if(selectedTextInvoice =='percentage'){
		  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
		  if(enteredValue.match(floatNum)){
			  $("#saveInvoice").removeAttr("disabled", "disabled");
				$("#sendInvoice").removeAttr("disabled", "disabled");
			   return true;
		  }		  
		 else{
				$("input#discountValue").after("<p class='jquery_form_error_message'><span>Enter Valid Percentage</span>");
				$("#saveInvoice").attr("disabled", "disabled");
				$("#sendInvoice").attr("disabled", "disabled");
				return false; 
		 	 }
	  }if(selectedTextInvoice =='flat'){

		  var numaricNum = /^[0-9]*$/
		  if(enteredValue.match(numaricNum)){
		  $("#saveInvoice").removeAttr("disabled", "disabled");
		  $("#saveInvoice").removeAttr("disabled", "disabled");
		  return true;
		  }else{
		  $("#discountValueDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Discount</span>");
		  $("#saveInvoice").attr("disabled", "disabled");
		  $("#saveInvoice").attr("disabled", "disabled");
		  return false; 
		  }

		  }		  
}
</script>

<script>
function validateRowDiscount(maxRowNum) {
$('#'+maxRowNum+'discountRowDiv').parent().find('p.jquery_form_error_message').remove();
var selectedTextInvoice = $("#"+maxRowNum+"taxdiscountType").val();
var enteredValue = $("#"+maxRowNum+"taxdiscountvalue").val();

if(selectedTextInvoice =='percentage'){
	  var floatNum = /^(\d{0,2}(\.\d{0,2})?|100)$/
	  if(enteredValue.match(floatNum)){
		  $("#saveInvoice").removeAttr("disabled", "disabled");
			$("#sendInvoice").removeAttr("disabled", "disabled");
		   return true;
	  }		  
	 else{
			$("#"+maxRowNum+"discountRowDiv").after("<p class='jquery_form_error_message'><span>Enter Valid Percentage</span>");
			$("#saveInvoice").attr("disabled", "disabled");
			$("#sendInvoice").attr("disabled", "disabled");
			return false; 
	 	 }
}if(selectedTextInvoice =='flat'){
	/* var floatNum = /^[0-9]*$/
		if(enteredValue.match(floatNum)){
			  $("#saveButtonId").attr("disabled", "disabled");
				$("#sendButtonId").attr("disabled", "disabled");
			   return false;
		  }
		else{ */
			$("#saveInvoice").removeAttr("disabled", "disabled");
			  $("#sendInvoice").removeAttr("disabled", "disabled");
// 		}
	  }		
	  
 
}
</script>

<script>
function getPaymentAccountList(dbOrgRelationId,paymentAccoutId){
	var	orgRelationId = $("#vendorOrgRelationId").val();
	if(orgRelationId == null){
		orgRelationId = dbOrgRelationId;
	}
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getAllPaymentAccountList?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
		success :function(json) {
			var result = "";
			   result = result +"<option value=''>-- Select Account --</option>";
			   for (var i=0; i<json.length; i++)
				{
				  var report = json[i];
				  var selected = '';
					if((paymentAccoutId != null && paymentAccoutId != "" && paymentAccoutId !="undefined" && paymentAccoutId == report.id) || (report.defaultAccount == 1)){
						selected = 'selected';
					}
					if(report.accountType === 'WEPAY'){
						  result = result +'<option value='+report.id+' '+selected+'> '+report.email+'</option>';
					  }else{
						  result = result +'<option value='+report.id+' '+selected+'>'+report.accountNumber+' ('+report.bankName+')</option>';
					  }
				}
			   
			     $("#paymentAccountId").empty();
		         $("#paymentAccountId").append(result);
		}
	});
}
</script>

 <jsp:include page="../masterFooter.jsp" />	
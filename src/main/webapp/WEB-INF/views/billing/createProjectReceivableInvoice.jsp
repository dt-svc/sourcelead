<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/jquery.mask.min.js"></script>
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>

<style type="text/css">
.inline_field, .inline_field:focus {
	width:100px !important; text-align:center;
}
.form-control.formControl {
border:none; background: none; font-size:14px; font-weight:600; box-shadow: none;}
</style>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Project Invoice</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/invoiceList'/>">Receivable List</a> &raquo; Project Based Invoice</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal inner-content">
       <div id="invoiceLoading"></div>
       <form:form id="project_invoice_form"  name="project_invoice_form" method="post"  enctype='multipart/form-data'> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field">
                 <c:if test="${isGenerateAutomaticInvoice == 'Y'}">
                <select class="chosen-select form-control" id="clientOrgRelationId" name="clientOrgRelationId" onchange="getClientContacts();getPaymentTerms();flushValues();" data-validation="required" field-name="Client" disabled>
                   <%--  <option value=''>--Select Clinet--</option>
                   <c:forEach var="orgrelations" items="${clientList}">
                         <option value="${orgrelations.id}">${orgrelations.orgName}</option>
                  </c:forEach> --%>
                </select>
                </c:if>
                <c:if test="${empty isGenerateAutomaticInvoice}">
                <select class="chosen-select form-control" id="clientOrgRelationId" name="clientOrgRelationId" onchange="getClientContacts();getPaymentTerms();flushValues();" data-validation="required" field-name="Client">
               </select>
               </c:if>
              </div>
               <input type="hidden" name="savedInvoiceId" id="savedInvoiceId">
               <input type="hidden" name="clientCurrency" id="clientCurrency">
            </div>
              
            <div class="form-group">
              <label class="col-md-3">Client Contact<span class="text-danger">*</span></label>
              <div class="col-md-9 select-field">					   
                <select class="chosen-select form-control" id="clientContact" name="clientContact" data-validation="required" field-name="Client Contact">
                </select>
              </div>
            </div>
              
            <div class="form-group">
              <label class="col-md-3">Invoice Id<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input class="form-control" id="invoiceId" name="invoiceId" placeholder="Invoice Id" type="text" data-validation="required" field-name="Invoice Id">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Invoice Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field" id="errInvoiceDate">
                <input class="form-control calendar" id="invoiceDate" name="invoiceDate" value="${currentDate}" placeholder="Invoice Date"  type="text" data-validation="required" field-name="Invoice Date">
              </div>
            </div>
              
           <div class="form-group">
              <label class="col-md-3">Payment Term</label>
              <div class="col-md-9 select-field">
                <select class="chosen-select form-control" id="paymentFrequency" name="paymentFrequency" onchange="getDueDateByFrequency();">
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
              <label class="col-md-3">Due Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9" id="errDuedate">
                <input class="form-control calendar" placeholder="Due Date" id="dueDate" name="dueDate" type="text" data-validation="required" field-name="Due Date" readonly>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3">Project<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field">
               <c:if test="${isGenerateAutomaticInvoice == 'Y'}"> 
                <select class="chosen-select form-control" id="projectId" name="projectId"  onchange="getProjectFrequency();" data-validation="required" field-name="Project Name" disabled> 
                  <%--  <option value=''>--Select Clinet--</option>
                   <c:forEach var="projects" items="${projectsList}">
                         <option value="${projects.id}">${projects.projectName}</option>
                  </c:forEach> --%>
                  </select>
                  </c:if>
                  <c:if test="${empty isGenerateAutomaticInvoice}"> 
                <select class="chosen-select form-control" id="projectId" name="projectId"  onchange="getProjectFrequency();" data-validation="required" field-name="Project Name">
              </select>
                  </c:if>
              </div>
            </div>
           <div class="form-group">
              <label class="col-md-3">Frequency<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field">
               <c:if test="${isGenerateAutomaticInvoice == 'Y'}"> 
                <select class="chosen-select form-control" id="frequency" name="frequency" data-validation="required" field-name="Frequency" onchange="getProjectAssignedMem();" disabled>
                 <!--  <option value=''>--Select Payment Term--</option>
                  <option>10 days</option>
                  <option>15 days</option>
                  <option>20 days</option>
                  <option>30 days</option>
                  <option>60 days</option>-->
                  </select> 
                  </c:if>
                  <c:if test="${empty isGenerateAutomaticInvoice}"> 
                <select class="chosen-select form-control" id="frequency" name="frequency" data-validation="required" field-name="Frequency" onchange="getProjectAssignedMem();">
              </select> 
                  </c:if>
              </div>
            </div>
<!-- 	   
	       <div class="form-group">
              <label class="col-md-3">Invoice Frequency</label>
              <div class="col-md-9">
                <input class="form-control" id="invoiceFrequency" name="invoiceFrequency" placeholder="Invoice Frequency" type="text">
              </div>
            </div> -->
              
            <div class="form-group">
              <label class="col-md-3">Invoice Address<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <textarea class="form-control" rows="2" placeholder="Invoice Address" id="billingAddress"
                    name="billingAddress" data-validation="required" field-name="Invoice Address"></textarea>
              </div>
            </div>
            <div class="form-group">
							<label class="col-md-3">Account Details<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-9">
								<textarea class="form-control" rows="2" id="accountDetails"
									name="accountDetails" placeholder="Account Details" data-validation="required" field-name="Account Details"></textarea>
							</div>
			</div>
          </div>
        </div>
        <div class="clearfix"></div>
        <!-- Drap and Drop Table Row -->
        <!-- Table starts here-->
          <div class="project_wrap_table mt25 invoice_table table_custompadding" id="invoiceTable">
            <div id="tableBody"></div>
        </div>
        <!-- Table Ends here-->
          
          <!-- Adding Content Starts herer -->
           <div id="bottomGrid" style="display: none;" class="mt">
          <div class="clearfix invoice_other">
          <div class="col-md-5">
           <!--  <div class="clearfix mb">
              <button type="button" class="btn btn-sm btn-primary mr" onclick="cloneRow()"><i class="fa fa-plus paddingRight"></i>Add</button>
              <button type="button" class="btn btn-sm btn-default" onclick="deleteRow()"><i class="fa fa-close paddingRight"></i>Clear</button>
            </div> -->
            <!--   <div class="clearfix mb"> <p>Do you want to raise separate invoice for each line item:</p>
              <label class="paddingLeft paddingRight">
                <input  type="radio" value="Y" id="isSeparateInvoice" name="isSeparateInvoice"><strong class="paddingLeft">Yes</strong></label>
              <label class="paddingLeft">
                <input type="radio" id="isSeparateInvoice" name="isSeparateInvoice"><strong class="paddingLeft">No</strong></label>
            </div> -->
            <div class="clearfix mb">
              <label><strong>Message</strong></label>
              <textarea class="form-control" rows="2" id="comment" name="comment"></textarea>
            </div>
            <div class="clearfix mb invoiceAttachment">
            <input type='hidden' name='invoiceAttcahmentMaxRowNumber'  id='invoiceAttcahmentMaxRowNumber' value='1' />
            <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="invoiceAttachmentTbl">
            <tbody>
              <tr id='divAttachmentTsRow0'>
            	<td>
              	<label><strong>Attachments</strong></label>
              	<div id="timesheetDocumentsId"><div class="timesheet_week_comm invoiceDocs">
                <span><div id="savedAttachments"></div></span></div> </div>
	              <div class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add" >
	                <div class="form-group" id='0invoicedocFileuploadFileGroup'> 
	                <span>
	                  <input type='file' name='0invoicedocFile' id='0invoicedocFile' field-name='Attachment'  class='timesheetAttachmentValidation' onchange="attachFile(0);"><i class='fa fa-times' mouseOver="Clear" title="Clear" onClick='remove()'></i><i class='fa fa-plus' mouseOver="Add" title="Add" onClick='addInvoiceAttachment()'></i>
	                 </span> </div>
	              </div>
            	</td>
            </tr>
            </tbody>
            </table>
            
            </div>
          </div>
          <div class="col-md-7">
            <div class="form-horizontal">
              <div class="form-group hidden">
                <label class="control-label col-md-7"><strong>Subtotal</strong></label>
                <div class="col-md-5 text-right"><input type="text" value = "00:00" id="subTotal" name="subTotal" class="ReadTextFli"></div>
              </div>
              <!-- Discount UI -->
              <div class="form-group">
                <div class="col-md-7 select-field">
                  <div class="row">
                    <div class="col-md-7 paddingRight">
                      <select class="form-control input-sm" id="disCount" name="disCount" onchange="calculateDisCount();">
                        <option value="percentage">Discount %</option>
                        <option value="flat">Discount Value</option>
                      </select>
                    </div>
                    <div class="col-md-5 paddingLeft">
                      <input class="form-control input-sm allownumericwithdecimal" id="discountValue" placeholder="Flat / Discount" name="discountValue" type="text" onblur="calculateDisCount();">
                    </div>
                  </div>
                </div>
                <div class="col-md-5 text-right">
                  <input class="form-control input-sm"  id="discountTotal" name="discountTotal" type="text" placeholder="00.00" readonly>
                </div>

              </div>
                 <div class="form-group">
                <label class="col-md-7 text-right"><strong>After Discount Total</strong></label>
                <div class="col-md-5 text-right" ><span class="pull-left">${SourceLeadWeb_currencyCode}</span><input type="text"  id="afterDiscountTotal" name="afterDiscountTotal"  value="00.00" class="ReadTextFli"></div>
              </div>              
            
             <div id="lineItemAddRows"></div>
                <div class="form-group mt">
                <label class="col-md-7 text-right"><strong>Total</strong></label>
                    <div class="col-md-5 text-right"><span class="pull-left">${SourceLeadWeb_currencyCode}</span><input type="text" value = "00:00" id="grandTotal" name="grandTotal" class="ReadTextFli"></span></div>
              </div>
              
             <!--  Added Rajashekar -->
                <div id="conversionId"></div>
                  <!--  End Rajashekar -->
              
              </div>
          </div>
        </div>
        </div> 
        <!-- Adding Content Starts herer -->
        <div id="lineItemPopUp"></div>
          <!-- Drap and Drop Table Row --> 
          <div id="receivableAlertPopUp"></div>
      
          <footer class="form-footer">
              <div class="pull-right mr">
                  <button type="button" class="btn btn-sm btn-primary" onclick="saveInvoice('DRAFT');">Save</button>
                  <button type="button" class="btn btn-sm btn-success ml" onclick="saveInvoice('SENT')">Send</button>
                 <button type="button" class="btn btn-sm btn-default ml" onClick="javascript:window.location.href = '<c:url value='/invoiceList'/>'">Cancel</button>
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
function getClientContacts(){
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientContactsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Client Contact</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
	// need to use in edit
	//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
	output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+"("+opt.spocName+")</option>";
	$("#clientCurrency").val(opt.clientCurrency);
	}
	$("#clientContact").empty();
	$("#clientContact").append(output);
	$('#clientContact').trigger("chosen:updated");
	},
	});
	
	document.getElementById("bottomGrid").style.display="none";
	document.getElementById("lineItemAddRows").style.display="none";
	document.getElementById("tableBody").style.display="none";
	getProjectsList();
	}
</script>


<script>
function getProjectsList(projectName){
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientProjectsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Project</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
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
	var sourceleadUrl ='${sourceleadUrl}';
	
	if(projectId != null && projectId != "" && projectId != 'undefined'){
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getProjectFrequency?"+tokenParamName+"="+tokenValue+"&projectId="+projectId,
	success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Frequency</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		var convertOpt = opt.replace(/\//g, " to ");
		/* output = output + "<option value='" +opt+ "'>"+opt+"</option>"; */
		var selected = "";
		if(invoiceFrequency != null && invoiceFrequency != "" && invoiceFrequency !="undefined" && invoiceFrequency == opt){
			selected = 'selected';
			/* getProjectAssignedMem1();  */
		}
		output = output + "<option value='" +opt+ "' "+selected+">"+convertOpt+"</option>";
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
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getAllProjectAssignedMembers?"+tokenParamName+"="+tokenValue+"&projectId="+projectId+"&frequency="+frequency+"&orgRelationId="+orgRelationId+"&contractType=clientSow",
	success :function(data) {
		populateInlineItemsGrid(data);
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
function populateInlineItemsGrid(data){
	var currencyCode = '${SourceLeadWeb_currencyCode}';
    if(data != '' && data !=null){
        var json = JSON.parse(data);
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
            result = result +' <input type="hidden" value='+json.length+' id="maxTsRowNum" name="maxTsRowNum">';
        for (var i = 0; i < json.length; i++) { 
             var opt = json[i]; 
             result = result +'<tr>';
             result = result +' <td>';
             result = result +'<div class="inputCustom">';
             result = result +' <input class="calendar paddingRight" id="'+i+'sdate-input" name="lineItems['+i+'].invoiceStartDate" placeholder="Start Date" value='+opt.startDate+' type="text" readonly>';
             result = result +'<input class="calendar paddingLeft" id="'+i+'edate-input" name="lineItems['+i+'].invoiceEndDate" placeholder="End Date" value='+opt.endDate+' type="text" readonly>';
             result = result +' <input type="hidden" value='+opt.submissionApproverHierarchyId+' id="lineItems['+i+'].submissionApproverHierarchyId" name="'+i+'submissionApproverHierarchyId">';
             result = result +'</div>';
             result = result +' </td>';
             result = result +'<td>';
             result = result +' <div class="tbldata-dotdot-vsm select-field" id="'+i+'candidateName" name="lineItems['+i+'].candidateName" data-validation="required" field-name="Candidate Name">'+opt.candidateName+'</div>';
             result = result +' <input type="hidden" value='+opt.candidateId+' id="'+i+'candidate" name="lineItems['+i+'].candidate">';
             result = result +' <input type="hidden" value='+opt.userId+' id="'+i+'user" name="lineItems['+i+'].user">';
             result = result +' </td>';
             result = result +'  <td class="tblminwidth-150">';
             result = result +' <textarea type="text" class="form-control input-sm" placeholder="Description" id="'+i+'Description" name="lineItems['+i+'].Description" data-validation="validate_length length1-100" field-name="Description"></textarea>';
             result = result +'  </td>';
             result = result +'  <td>';
             result = result +' <div class="tbldata-dotdot-100 select-field">';
             result = result +'<select class="form-control input-sm" id="'+i+'sow" name="lineItems['+i+'].sow" onchange="getSowRate('+i+')">';
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
             result = result +'<div class="tbldata-dotdot-100">';
             if(opt.rate != null && opt.rate !='' && opt.rate !='undefined'){
                 result = result +'<input class="form-control input-sm allownumericwithdecimal" id="'+i+'rate" name="lineItems['+i+'].rate" value='+opt.rate+' placeholder="Rate" data-validation="required" field-name="Rate" type="text" onblur="totalHoursCalculateAmount('+i+');">'; 
             }else{
                 result = result +'<input class="form-control input-sm allownumericwithdecimal" id="'+i+'rate" name="lineItems['+i+'].rate"  placeholder="Rate" data-validation="required" field-name="Rate" type="text" onblur="totalHoursCalculateAmount('+i+');">';
             }
             result = result +'</div>';
             result = result +'</td>';
             result = result +' <td>';
             result = result +'<div class="tbldata-dotdot-vsm">';
             result = result +'  <input class="form-control input-sm inline_field" id="'+i+'totalHours" name="lineItems['+i+'].totalHours" placeholder="HHH:MM"  value='+opt.totalHours+' type="text" data-validation="required" field-name="Total Hours" onblur="totalHoursCalculateAmount('+i+');">';
             result = result +'</div>';
             result = result +'</td>';
             result = result +'<td>';
             result = result +'<input class="form-control formControl input-sm text-right" id="'+i+'amount" name="lineItems['+i+'].amount" value="0.0" placeholder="Amount" type="text" readonly="readonly">';
             result = result +'</td>';
             result = result +'</tr>';
        }
        result = result +' <tfoot class="table_footer">';
        result = result +' <tr>';
        result = result +' <td colspan="5"></td>';
        result = result +'<td><strong>Sub Total</strong></td>';
        result = result +' <td><div class="tblminwidth-150"><span class="pull-left paddingRight">'+currencyCode+'</span> <input type="text" value = "00.00" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli"></div></td>';
        result = result +' </tr>';
        result = result +' </tfoot> ';
        result = result +' </tbody>';
        result = result +' </table>';
        $("#tableBody").empty();
        $("#tableBody").append(result);
        maskme();
        $("#currencyCode").html(currencyCode);
        document.getElementById("bottomGrid").style.display="block";
        document.getElementById("bottomGrid").style.display="block";
        document.getElementById("lineItemAddRows").style.display="block";
        document.getElementById("tableBody").style.display="block";
        addTaxDivs();   
        addConversionDiv();
        /* getSow(); */
    }else{
        document.getElementById("bottomGrid").style.display="none";
        document.getElementById("lineItemAddRows").style.display="none";
        document.getElementById("tableBody").style.display="none";
//         alert("No MembersFound");
        /* getProjectsList(); */
        alertMessageInReceivable()
    }
    
}
</script>

<script>
function addConversionDiv(){
	var clientCurrency = $("#clientCurrency").val();
	var currencyCode = '${SourceLeadWeb_currencyCode}';
	if(clientCurrency != currencyCode){
		var result = "";
		result = result +' <div class="row">';
		result = result +' <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
		result = result +' <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span class="pull-left" id="currencyCode"></span><div class="pull-right"><input type="text" id="conversionFrom" placeholder="00:00" class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div  class="paddingLeft col-md-6"><span id="vendorCurrencyCode"></span><div id="exchangeCurrency" class="pull-right"><input  class="ReadTextFli" readonly="" type="text"></div></div></div>';
		result = result +' <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div  class="paddingLeft col-md-6"><span id="convertedCurrencyCode"></span><div id="exchangeRate" class="pull-right"><input class="ReadTextFli ReadTextFli90" readonly="" type="text"></div></div></div>';
		result = result +'  </div>';
		result = result +'  </div>';
		$("#conversionId").empty();
		$("#conversionId").append(result);
	}
}
</script>

<script type="text/javascript">
function alertMessageInReceivable(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>No Members Found</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancelReceivableAlert();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelReceivableAlert();'></b>";
	result = result +"	</div>";
	$("#receivableAlertPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancelReceivableAlert(){
	$("#receivableAlertPopUp").empty();
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
	    result = result +' <th>Description</th>;'
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
    	 result = result +' <input type="hidden" value='+opt.id+' id="'+i+'lineItemId"   name="lineItems['+i+'].lineItemId">';
    	 result = result +' <td>';
    	 result = result +'<div class="inputCustom">';
    	 result = result +' <input class="calendar paddingRight" id="'+i+'sdate-input" name="lineItems['+i+'].invoiceStartDate" placeholder="Start Date" value='+opt.startDateStr+' type="text" readonly>';
    	 result = result +'<input class="calendar paddingLeft" id="'+i+'edate-input" name="lineItems['+i+'].invoiceEndDate" placeholder="End Date" value='+opt.endDateStr+' type="text" readonly>';
    	 result = result +' <input type="hidden" value='+opt.submissionApproverHierarchyId+' id="'+i+'submissionApproverHierarchyId" name="lineItems['+i+'].submissionApproverHierarchyId">';
    	 result = result +'</div>';
    	 result = result +' </td>';
    	 result = result +'<td>';
    	 result = result +' <div class="tbldata-dotdot-vsm select-field" id="'+i+'candidateName" name="lineItems['+i+'].candidateName" data-validation="required" field-name="Candidate Name">'+opt.candidateName+'</div>';
    	 result = result +' <input type="hidden" value='+opt.candidateId+' id="'+i+'candidate" name="lineItems['+i+'].candidate">';
    	  result = result +' <input type="hidden" value='+opt.userId+' id="'+i+'user" name="lineItems['+i+'].user">';
    	 result = result +' </td>';
    	 
    	 result = result +'  <td>';
    	 if(opt.inlineDescription !=null && opt.inlineDescription != '' && opt.inlineDescription !='undefined'){
    		 result = result +' <textarea type="text" class="form-control input-sm tbldata-dotdot" placeholder="Description" id="'+i+'Description" name="lineItems['+i+'].Description" data-validation="validate_length length1-100" field-name="Description">'+opt.inlineDescription+'</textarea>';
    	 }else{
    		 result = result +' <textarea type="text" class="form-control input-sm tbldata-dotdot" placeholder="Description" id="'+i+'Description" name="lineItems['+i+'].Description" data-validation="validate_length length1-100" field-name="Description"></textarea>';
    	 }
         
         result = result +'  </td>';
    	 
    	 result = result +'  <td>';
    	 result = result +' <div class="tbldata-dotdot-100 select-field">';
    	 result = result +'<select class="form-control input-sm" id="'+i+'sow" name="lineItems['+i+'].sow" onchange="getSowRate('+i+')">';
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
    	 result = result +'<div class="tbldata-dotdot_80">';
    	 if(opt.rate != null && opt.rate !='' && opt.rate !='undefined'){
    		 result = result +'<input class="form-control input-sm allownumericwithdecimal" id="'+i+'rate" name="lineItems['+i+'].rate" value='+opt.rate+' placeholder="Rate" data-validation="required" field-name="Rate" type="text" onblur="totalHoursCalculateAmount('+i+');">'; 
    	 }else{
    		 result = result +'<input class="form-control input-sm allownumericwithdecimal" id="'+i+'rate" name="lineItems['+i+'].rate"  placeholder="Rate" data-validation="required" field-name="Rate" type="text" onblur="totalHoursCalculateAmount('+i+');">';
    	 }
    	 result = result +'</div>';
    	 result = result +'</td>';
    	 result = result +' <td>';
    	 result = result +'<div class="tbldata-dotdot-vsm">';
    	 result = result +'  <input class="form-control input-sm inline_field" id="'+i+'totalHours" name="lineItems['+i+'].totalHours" placeholder="HHH:MM" value='+opt.totalHours+' data-validation="required" field-name="Total Hours" type="text" onblur="totalHoursCalculateAmount('+i+');">';
    	 result = result +'</div>';
    	 result = result +'</td>';
    	 result = result +'<td>';
    	 if(opt.rate != null && opt.rate !='' && opt.rate !='undefined'){
    		 result = result +'<input class="form-control formControl input-sm text-right" id="'+i+'amount" name="lineItems['+i+'].amount" value='+opt.amount+' placeholder="Amount" type="text" readonly="readonly">';
    	 }else{
    		 result = result +'<input class="form-control formControl input-sm text-right" id="'+i+'amount" name="lineItems['+i+'].amount" value="0.00" placeholder="Amount" type="text" readonly="readonly">';
    	 }
    	 
    	 result = result +'</td>';
    	 result = result +'</tr>';
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
    addConversionDiv();
    /* getSow(); */
}
</script>

<!-- Get contract sow between client and the associate NOT USING AS OF NOW-->
  <script>
  function getSow(contractId){
	  var orgRelationId = $("#clientOrgRelationId").val();
	  var sourceleadUrl ='${sourceleadUrl}';
	  var maxRowNum = $("#maxTsRowNum").val();
		  for(var id=0;id < maxRowNum;id++){
			    var result = "";
	        	result = result + "<option value='' >select sow</option>"
			  var candidateId = $("#"+id+'candidate').val();
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getSowcontractListByAssociateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&orgRelationId="+orgRelationId+"&contractType=clientSow",
			        success: function(data) {
			        	if(data != null && data != ''){
			        		var json = JSON.parse(data);
				        	 for (var i=0; i<json.length; i++)
							{
					        		var opt = json[i];
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
	var sourceleadUrl ='${sourceleadUrl}';
	if(contractId !='' && contractId != 'undefined' && contractId != null){
		$.ajax({
	    	type: "GET",
	    	url: sourceleadUrl+"/getSowRateByContractId?"+tokenParamName+"="+tokenValue+"&contractId="+contractId,
	        success: function(data) {
	        	 if(data != null && data != ''){
	        		var json = JSON.parse(data);
		        	 $ ('#'+id+'rate').val(json.billingAmount);
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
  
  <!-- calculating row total with rate and total hours -->
  <!-- <script>
  function calculateTotalAmount(rowId){
	 totalHoursCalculateAmount(rowId);
	 var totalHours =  $('#'+rowId+'totalHours').val();
	 var rate =  $('#'+rowId+'rate').val();
	/*  if(totalHours != ''){
		 var split = totalHours.split(':');
	     var hours = split[0];
 	     var minute = split[1];
		 var hourminits = hours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
	     var perMinute = rate / 60;
	     var totlalAmount = (perMinute * finalmin);
	     $('#'+rowId+'amount').val(totlalAmount.toFixed(2));
	 }else{
		 $('#'+rowId+'amount').val('');
	 } */
	 calculateSum();
	 
	 
  }
  
  </script> -->
  
  
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
	   var orgRelationId = $("#clientOrgRelationId").val();
	   var currencyCode = '${SourceLeadWeb_currencyCode}';
	   var grandTotal = $("#grandTotal").val();
	   var sourceleadUrl ='${sourceleadUrl}';
	   if(grandTotal != '' && grandTotal != 'undefined'){
		   $.ajax({
				type : "GET",
				url: sourceleadUrl+"/getConversionExchangeRate?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&grandTotal="+grandTotal,
				success :function(data) {
					if(data !='' && data != 'undefined' && data != null){
						var json = JSON.parse(data);
						$("#exchangeRate").html(json.exchangeRate);
						$("#exchangeCurrency").html(json.exchangeCurrency);
						$("#vendorCurrencyCode").html(json.vendorCurrencyCode);
						$("#convertedCurrencyCode").html(currencyCode +' to '+ json.vendorCurrencyCode);
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
	     $('#'+rowId+'amount').val(totlalAmount.toFixed(2));
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
	  $("#inoviceTotal").val(sum.toFixed(2));
	  $("#subTotal").val(sum.toFixed(2));
	  $("#grandTotal").val(sum.toFixed(2));
	  $("#conversionFrom").val(sum.toFixed(2));
	  $("#afterDiscountTotal").val(sum.toFixed(2));
	  calculateDisCount();
	  calculateTaxDiscount();
	  /* =====================  After adding the rows calculate the tax start  ===================== */
	  /* var maxTaxRowNum = $("#maxTaxRowNum").val();
		var afterDiscountTotal = $("#afterDiscountTotal").val();
		
		for(var i=1; i<maxTaxRowNum; i++){
			if($("#"+i+"taxdiscountType").val() != '' && $("#"+i+"taxdiscountType").val() != null && $("#"+i+"taxdiscountType").val() != 'undefined'){
				var taxType = $("#"+i+"taxdiscountType").val();
			    var taxValue = $("#"+i+"taxdiscountvalue").val();
			    var first = '0';
			   
			    if(taxType == 'flat'){
			    	if(taxValue){
			    		first = parseFloat(taxValue);
				        $("#"+i+"taxTotal").val(first);
			    	}else{
				        $("#"+i+"taxTotal").val(0);
			    	}
			    	
			    }
			    
			    else if(taxType == 'percentage'){
			        var perCentValue = (afterDiscountTotal * taxValue)/100;
			        first = parseFloat(perCentValue.toFixed(2));
			        $("#"+i+"taxTotal").val(first);
			    }   
			}
		}
		
		var taxGrandTotal = 0;
		for(var i=0; i<maxTaxRowNum; i++){
			taxGrandTotal = parseFloat(taxGrandTotal + parseFloat($("#"+i+"taxTotal").val()));
		}
		
		var grandTotal = parseFloat(afterDiscountTotal) + parseFloat(taxGrandTotal)
		 $("#grandTotal").val(grandTotal.toFixed(2)); */
}
	
</script>
  
 <!-- Calculationg Total Discount With Sub Total --> 
<script type="text/javascript">
function calculateDisCount(){
	var discountValue = $("#discountValue").val();
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
					 	
}
}
</script>

<!-- Adding Dynamic Tax Div Ids -->
<!-- ********************************************* Add Rows AddTax Table functionality start  ************************************************************ -->	
  
  <div id="divTaxTd1" style="display: none; float: left;">
	<div class="select-field tblminwidth-75">
		  <div class="tblminwidth-50">
		        <input type="text" class="form-control input-sm" value="" placeholder="Tax type" name='invoiceTax[REPLACEROWID].taxType' id='REPLACEROWIDtaxType'>
		         <input type="hidden" name="invoiceTax[REPLACEROWID].invoiceTaxId" id="REPLACEROWIDinvoiceTaxId">
		  </div>
	 </div>
</div>

  <div id="divTaxTd2" style="display: none; float: left;">
	 <div class="btn-group tblminwidth-75">
	 <select class="form-control input-sm" id="REPLACEROWIDtaxdiscountType" name="invoiceTax[REPLACEROWID].taxdiscountType" onchange="calculateTaxDiscount();">
		 <option value="percentage">Percentage</option>
		 <option value="flat">Flat</option>
 		  </select>
	  </div>
  </div>
  
  <div id="divTaxTd3" style="display: none; float: left;">
     <div class="tblminwidth-75">
        <input type="text" class="form-control input-sm allownumericwithdecimal" value="" placeholder="Flat / Discount" name='invoiceTax[REPLACEROWID].taxdiscountvalue' id='REPLACEROWIDtaxdiscountvalue' onblur="calculateTaxDiscount();">
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
		var r = document.getElementById('addTaxTbl').rows[rowId];
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
        var first=parseFloat(perCentValue.toFixed(2));
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
			        $("#"+i+"taxTotal").val(first.toFixed(2));
		    	}
		    	
		    }
		    
		    else if(taxType == 'percentage'){
		        var perCentValue = (afterDiscountTotal * taxValue)/100;
		        first = parseFloat(perCentValue);
		        $("#"+i+"taxTotal").val(first.toFixed(2));
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
	 $("#grandTotal").val(grandTotal.toFixed(2));
	$("#conversionFrom").val(grandTotal.toFixed(2));
	conversion();
}
</script>

<!-- Adding Attachment Div Ids -->

<!-- ********************************************* Add Rows resume Table functionality start  ************************************************************ -->
  
  <div id="divInvoiceAttachmentTd1" style="display: none; float: left;">
  			<div class="timesheet_week_comm col-md-12 invoiceDocs invoiceDocs_add">
                <div class="form-group" id="REPLACEROWIDinvoicedocFileuploadFileGroup"> 
               		 <span> 
               		 	<input type="file" name='REPLACEROWIDinvoicedocFile' id='REPLACEROWIDinvoicedocFile' onchange='attachFile(REPLACEROWID);validateContractsUploadFile(REPLACEROWID)'>
               		    <i class="fa fa-times" mouseOver="Delete" title="Delete" onclick="removeInvoiceAttachmentRow(REPLACEROWID);"></i>
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

<!-- Saving Project Invoice --> 
<script>
function saveInvoice(saveType){
         
         if ($('#project_invoice_form').validate(false, validationSettings)){  
 	 	    var sourceleadUrl ='${sourceleadUrl}';
 	 	    var formData = getFormData("project_invoice_form");
		        	  $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		        	  $.post(sourceleadUrl+"/saveInvoice?"+tokenParamName+"="+tokenValue+"&saveType="+saveType+"&invoiceType=Receivable", formData, function(data) {
			  				if(data == "success"){
			  					$("#invoiceLoading").removeAttr("style");
	    		     			location.replace(sourceleadUrl+"/invoiceList");
			  				}
			  			   }); 
		        	  /*  $.ajax({
		    		    	 type: "POST",
		    		    	 data: formData,
		    			     contentType : 'application/json; charset=utf-8',
		    			     enctype: 'multipart/form-data',
		    		         processData: false,
		    		         contentType:false,
		    			     dataType : 'json',
		     		    	 url: sourceleadUrl+"/saveInvoice?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&saveType="+saveType+"&invoiceType=Receivable", 
		    		        success: function(result) {
		    		     		$("#invoiceLoading").removeAttr("style");
		    		     			location.replace(sourceleadUrl+"/invoiceList");
		    		        },
		    		    });  */
		    	         return true; 
         }else{
	    		return false;
	    	  } 
	   
}

$('body').on('blur', '#project_invoice_form', function() {
	$("#project_invoice_form").showHelpOnFocus();
	$("#project_invoice_form").validateOnBlur(false, validationSettings);  
}); 
</script>
<!-- <script>
function saveProjectInvoice(saveType){
          if ($('#project_invoice_form').validate(false, validationSettings)){   
 		    var frm = $('#project_invoice_form').serializeFormJSON();
 	        var con = JSON.stringify(frm);
 	        con = con.replace(/[{}]/g, "");
 	        //It will escape all the special characters
 	        var jsonData = encodeURIComponent(con);
 	        
 	        var form = $('#project_invoice_form')[0];
 	 	    var formData = new FormData(form); 
 	 	    var sourceleadUrl ='${sourceleadUrl}';
	    	        
	    	        $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    	        $.ajax({
	    		    	 type: "POST",
	    		    	 data: formData,
	    			     contentType : 'application/json; charset=utf-8',
	    			     enctype: 'multipart/form-data',
	    		         processData: false,
	    		         contentType:false,
	    			     dataType : 'json',
	     		    	 url: sourceleadUrl+"/saveProjectInvoice?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&saveType="+saveType+"&invoiceType=receivable", 
	    		        success: function(result) {
	    		     		$("#invoiceLoading").removeAttr("style");
	    		     		var invoiceUUID = '${invoiceUUID}';
	    		     		if(saveType == 'DRAFT' && invoiceUUID != null && invoiceUUID != '' && invoiceUUID != 'undefined'){
	    		     			location.replace(sourceleadUrl+"/getReceivableInvoiceByUUID?invoiceUUID="+invoiceUUID);
	    		     		}else{
	    		     			location.replace(sourceleadUrl+"/invoiceList");
	    		     		}
	    		     		
	    		        },
	    		    });  
	    	          return true;  
          }else{
	    		return false;
	    	  }  
	   
}

 $('body').on('blur', '#project_invoice_form', function() {
	$("#project_invoice_form").showHelpOnFocus();
	$("#project_invoice_form").validateOnBlur(false, validationSettings);  
});  
</script> -->
 
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
			   var finalDateFormatproject = '';
			  if(locationDate.match("yyyy-MM-dd")){
				  finalDateFormatproject = 'yy-mm-dd';
			  }if(locationDate.match("dd-MM-yyyy")){
				  finalDateFormatproject = 'dd-mm-yy'; 
			  }if(locationDate.match("MM-dd-yyyy")){
				  finalDateFormatproject = 'mm-dd-yy'; 
			  }if(locationDate.match("yyyy-dd-MM")){
				  finalDateFormatproject = 'yy-dd-mm'; 
			  }
			  
			$('#invoiceDate').datepicker({
				dateFormat : finalDateFormatproject,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
					var dt = $(this).datepicker('getDate', '+1d');
					dt.setDate(dt.getDate() + 1);
					$("#dueDate").datepicker("option", "minDate", dt);
							errInvoiceDate();
							getDueDateByFrequency();
				}
			});
		  $('#dueDate').datepicker({
				dateFormat : finalDateFormatproject,
				changeMonth: true,
				changeYear: true, 
				onSelect : function(selected) {
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
		 var sourceleadUrl ='${sourceleadUrl}';
		 
		 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getInvoiceDetailsByInvoiceUUID?"+tokenParamName+"="+tokenValue+"&invoiceUUID="+invoiceUUID, 
		         success: function(data) {
		        	 var json = JSON.parse(data);
		        	 var result = '';
		        	 var selected = 'selected';
		        	 result = result +'<option disabled selected>--Select Client--</option>';
		        	 result = result +'<option value='+json['vendorOrgRelationId']+' '+selected+' >'+json['orgName']+'</option>';
		        	 $("#clientOrgRelationId").empty();
		           	 $("#clientOrgRelationId").append(result); 
		           	 $('#clientOrgRelationId').trigger("chosen:updated");
		           	$("#clientCurrency").val(json['clientCurrency']);
		           	getClientContactsExisted(json['vendorContactId']);
		           	getProjectsList(json['projectName']);
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
		        	$("#afterDiscountTotal").val(json['afterDiscountValue']);http://localhost:8080/sourcelead/getReceivableInvoiceByUUID?invoiceUUID=sTljRS5n&projectId=15
		        	
		        	editTax(json['invoiceTaxList'])
		        	
		        	getSavedAttachments(json['invoiceDocumentsList']);
		        	
		        	$("#grandTotal").val(json['total']);
		        	$("#conversionFrom").val(json['total']);
		        	 $("#exchangeCurrency").html(json['convertAmount']);
		        	 $("#exchangeRate").html(json['exchangeRate']);
		        	  
		        	
		        	$("#savedInvoiceId").val(json['id']); 
		        	
		        	
		        	
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
		getClients();
	}
	
});
</script>
  
<script>
function getClientContactsExisted(clientContactId){
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientContactsByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Client Contact</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
	var selected = "";
	if(clientContactId != null && clientContactId != "" && clientContactId !="undefined" && clientContactId == opt.spocUserId){
		selected = 'selected';
	}
	output = output + "<option value='" +opt.spocUserId+ "' "+selected+">"+opt.ownerName+"</option>";
	}
	
	$("#clientContact").empty();
	$("#clientContact").append(output);
	$('#clientContact').trigger("chosen:updated");
	},
	});

	}
</script>

<!-- Get alla clients when editing -->
<script>
function getClients(){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgRelationId ='${orgRelationId}';
	$.ajax({
		type : "GET",
		url: sourceleadUrl+"/getClientList?"+tokenParamName+"="+tokenValue+"",
		success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Client</option>";
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
		$("#clientOrgRelationId").empty();
		$("#clientOrgRelationId").append(output);
		$('#clientOrgRelationId').trigger("chosen:updated");
		
		if(orgRelationId != null && orgRelationId != '' && orgRelationId != 'undefined'){
			getClientContacts();
			getPaymentTerms();
// 			flushValues();
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
					/*  maskme();  */
					
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
		var sourceleadUrl = '${sourceleadUrl}';
		if(invoiceDocumentsList.length > 0){
			
			 for (var i = 0; i < invoiceDocumentsList.length; i++) { 
				 var opt = invoiceDocumentsList[i]; 
	    		
				 result = result +"<strong id='invoiceDocument"+opt.id+"'>";
	    		 result = result +"<i class=''><a class='glyphicon glyphicon-save' href=\""+sourceleadUrl+"/downloadInvoiceDocument?documentId="+opt.id+"\"></a></i>";
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
			var sourceleadUrl = '${sourceleadUrl}';
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
    $(".inline_field").mask("Hhh:Mm"); 
	
    $(".allownumericwithdecimal").on("keypress keyup blur",function (event) {
    $(this).val($(this).val().replace(/[^0-9\.]/g,''));
        if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
    });
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
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
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
	    var sourceleadUrl ='${sourceleadUrl}';
	    $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    
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
	$("#invoiceDate").val('');
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

<script type="text/javascript">
function attachFile(id){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload(sourceleadUrl, ""+id+"invoicedocFile", "invoiceDocuments", "project_invoice_form","fileInfo");
}
</script>

 <jsp:include page="../masterFooter.jsp" />	
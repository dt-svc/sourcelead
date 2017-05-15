<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/jquery.mask.min.js"></script>
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Create Bill</h2>
      </div>
      <div class="pull-right brud-crum">Home » Bill</div>
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
                <select class="chosen-select  form-control" id="orgRelationId" name="orgRelationId" onchange="getClientContacts();" field-name="Client Name">
                    <option value=''>--Select Clinet--</option>
                   <c:forEach var="orgrelations" items="${clientList}">
                         <option value="${orgrelations.id}">${orgrelations.orgName}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
              
            <div class="form-group">
              <label class="col-md-3">Client Contact</label>
              <div class="col-md-9 select-field">
                <select class=" chosen-select form-control" id="clientContact" name="clientContact" field-name="Client Contact Name">
                </select>
              </div>
            </div>
              
            <div class="form-group">
              <label class="col-md-3">Invoice Id</label>
              <div class="col-md-9">
                <input class="form-control" id="invoiceId" name="invoiceId" placeholder="Invoice Id" type="text">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Invoice Date</label>
              <div class="col-md-9 select-field">
                <input class="form-control calendar" id="invoiceDate" name="invoiceDate" placeholder="Invoice Date"  type="text">
              </div>
            </div>
              
           <div class="form-group">
              <label class="col-md-3">Payment Term</label>
              <div class="col-md-9 select-field">
                <select class=" chosen-select form-control" id="paymentTerm" name="paymentTerm">
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
              <label class="col-md-3">Due Date</label>
              <div class="col-md-9">
                <input class="form-control calendar" placeholder="Due Date" id="dueDate" name="dueDate" type="text">
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3">Project</label>
              <div class="col-md-9 select-field"> 
                <select class="form-control" id="projectId" name="projectId" field-name="Project Name" onchange="getProjectFrequency();"> 
                  <%--  <option value=''>--Select Clinet--</option>
                   <c:forEach var="projects" items="${projectsList}">
                         <option value="${projects.id}">${projects.projectName}</option>
                  </c:forEach> --%>
                  </select>
              </div>
            </div>
           <div class="form-group">
              <label class="col-md-3">Frequency</label>
              <div class="col-md-9 select-field">
                <select class="form-control" id="frequency" name="frequency" onchange="getProjectAssignedMem();">
                 <!--  <option value=''>--Select Payment Term--</option>
                  <option>10 days</option>
                  <option>15 days</option>
                  <option>20 days</option>
                  <option>30 days</option>
                  <option>60 days</option>-->
                  </select> 
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
              <label class="col-md-3">Invoice Address</label>
              <div class="col-md-9">
                <textarea class="form-control" rows="2" placeholder="Billing Address" id="invoiceAddress" name="invoiceAddress"></textarea>
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix"></div>
        <!-- Drap and Drop Table Row -->
        <!-- Table starts here-->
          <div class="project_wrap_table mt25 invoice_table" id="invoiceTable">
            <div id="tableBody"></div>
        </div>
        <!-- Table Ends here-->
          
          <!-- Adding Content Starts herer -->
           <div id="bottomGrid" style="display: none;">
          <div class="clearfix invoice_other">
          <div class="col-md-5">
           <!--  <div class="clearfix mb">
              <button type="button" class="btn btn-sm btn-primary mr" onclick="cloneRow()"><i class="fa fa-plus paddingRight"></i>Add</button>
              <button type="button" class="btn btn-sm btn-default" onclick="deleteRow()"><i class="fa fa-close paddingRight"></i>Clear</button>
            </div> -->
              <div class="clearfix mb"> <p>Do you want to raise separate invoice for each line item:</p>
              <label class="paddingLeft paddingRight">
                <input  type="radio" value="Y" id="isSeparateInvoice" name="isSeparateInvoice"><strong class="paddingLeft">Yes</strong></label>
              <label class="paddingLeft">
                <input type="radio" id="isSeparateInvoice" name="isSeparateInvoice"><strong class="paddingLeft">No</strong></label>
            </div>
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
	              <div class="timesheet_week_comm col-md-12 " >
	                <div class="form-group" id='0invoicedocFileuploadFileGroup'> 
	                <span>
	                  <input type='file' name='0invoicedocFile' id='0invoicedocFile' field-name='Attachment'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addInvoiceAttachment()'></i>
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
              <div class="form-group">
                <label class="control-label col-md-7"><strong>Subtotal</strong></label>
                <div class="col-md-5 text-right"><input type="text" value = "00:00" id="subTotal" name="subTotal" class="ReadTextFli"></div>
              </div>
              <!-- Discount UI -->
              <div class="form-group">
                <div class="col-md-7 select-field">
                  <div class="row">
                    <div class="col-md-7 paddingRight">
                      <select class="form-control input-sm" id="disCountType" name="disCountType">
                        <option value="percentage">Discount %</option>
                        <option value="flat">Discount Value</option>
                      </select>
                    </div>
                    <div class="col-md-5 paddingLeft">
                      <input class="form-control input-sm" id="discountValue" name="discountValue" type="text" onblur="calculateDisCount();">
                    </div>
                  </div>
                </div>
                <div class="col-md-5 text-right">
                  <input class="form-control input-sm"  id="totalDiscountAmt" name="totalDiscountAmt" type="text">
                </div>

              </div>
                 <div class="form-group">
                <label class="col-md-7 text-right"><strong>After Discount Total</strong></label>
                <div class="col-md-5 text-right" ><input type="text"  id="afterDiscountTotal" name="afterDiscountTotal"  value="00.00" class="ReadTextFli" readinly></div>
              </div>              
              <!-- Discount UI -->
                <!-- AddTax Table Starts here -->
                 <div class="addtax_table">
              <input type="hidden" id="maxTaxRowNum" name="maxTaxRowNum" value="1" />
                    <table width="100%" cellpadding="0" cellspacing="0" class="table-bordered table-striped table-responsive">
                        <thead id="addTaxTbl">
                            <tr>
                                <th colspan="5">
                                Add Tax
                                    <button type="button" class="btn btn-sm btn-primary buttonCustom" title="Add Tax" onclick="addTax()">
                                         <i class="fa fa-plus"></i></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr id='divTaxTsRow0'>
                                <td>
                                   <div class="select-field tblminwidth-75">
                                       <div class="tblminwidth-50">
                                        <input type="text" class="form-control input-sm" value="" placeholder="Tax type" id="0taxType" name="0taxType">
                                    </div>
                                   </div>
                                </td>
                                <td>
                                 <div class="btn-group tblminwidth-75">
                                 <select class="form-control input-sm" id="0taxdiscountType" name="0taxdiscountType">
									 <option value="percentage">Percentage</option>
									 <option value="flat">Flat</option>
							 	</select>
                                </div>
                                </td>
                                <td>
                                    <div class="tblminwidth-75">
                                        <input type="text" class="form-control input-sm allownumericwithdecimal" value="" placeholder="Flat / Discount" id="0taxdiscountvalue" name="0taxdiscountvalue" onblur="calculateTaxDiscount();">
                                    </div>
                                </td>
                                <td>
                                    <div class="tblminwidth-75">
                                        <input type="text" class="form-control input-sm" value="" placeholder="00.00" id="0taxTotal" name="0taxTotal" readonly>
                                    </div>
                                </td>
                                <td>
                                    <span class="text-danger"><i class="fa fa-remove"></i></span>
                                </td>
                            </tr>
                          
                        </tbody>
                    </table>
                    <!-- AddTax Table Starts here -->
            </div>
                <div class="form-group mt">
                <label class="col-md-7 text-right"><strong>Total</strong></label>
                    <div class="col-md-5 text-right"><input type="text" value = "00:00" id="grandTotal" name="grandTotal" class="ReadTextFli"></span></div>
              </div>
              </div>
          </div>
        </div>
        </div> 
        <!-- Adding Content Starts herer -->
          <!-- Drap and Drop Table Row --> 
      
          <footer class="form-footer">
              <div class="pull-right mr">
                  <button type="button" class="btn btn-sm btn-primary" onclick="saveProjectInvoice('DRAFT');">Draft</button>
                  <button type="button" class="btn btn-sm btn-success ml" onclick="saveProjectInvoice('SENT')">Send</button>
                 <button type="button" class="btn btn-sm btn-default ml" onClick="javascript:location.href = '<c:url value='/invoiceList'/>'">Cancel</button>
              </div>
          </footer>
          </form:form>
     </div>
    </div>
  </section>
    
<!-- Diplays client contacts details when clien selected -->
<script>
function getClientContacts(){
	var orgRelationId = $("#orgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientContactsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Client Contact</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
	// need to use in edit
	//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
	output = output + "<option value='" +opt.spocUserId+ "'>"+opt.ownerName+"</option>";
	}
	$("#clientContact").empty();
	$("#clientContact").append(output);
	$('#clientContact').trigger("chosen:updated");
	},
	});
	getProjectsList();
	}
</script>


<script>
function getProjectsList(){
	var orgRelationId = $("#orgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientProjectsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Project</option>";
	for (var i=0; i<json.length; i++)
	{
	var opt = json[i];
	// need to use in edit
	//output = output + "<option value='" +opt.vendorUser.id+ "' selected>"+opt.spocName+"</option>";
	output = output + "<option value='" +opt.id+ "'>"+opt.projectName+"</option>";
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
function getProjectFrequency(){
	var projectId = $("#projectId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getProjectFrequency?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId,
	success :function(data) {
		var json = JSON.parse(data);
		var output = "";
		output = output + "<option value=''>Select Frequency</option>";
		for (var i=0; i<json.length; i++)
		{
		var opt = json[i];
		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
		}
		$("#frequency").empty();
		$("#frequency").append(output);
		$('#frequency').trigger("chosen:updated");
	},
	});

}
</script>

<!-- Get all assigned members for the project based on project id -->
<script type="text/javascript">
function getProjectAssignedMem(){
	var frequency = $("#frequency").val();
	var projectId = $("#projectId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getAllProjectAssignedMembers?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&frequency="+frequency,
	success :function(data) {
		var json = JSON.parse(data);
		var result = '';
			result = result +'	<table width="100%">';
		    result = result +' <thead>';
		    result = result +' <tr>';
		    result = result +' <th><i class="fa fa-ellipsis-h"></i></th>';
		    result = result +' <th>Date</th>';
		    result = result +' <th>Candiate</th>';
		    result = result +' <th>SOW / PO</th>';
		    result = result +' <th>Rate</th>';
		    result = result +' <th>Total Hours</th>';
		    result = result +' <th>Amount</th>';
		    result = result +' </tr>';
		    result = result +' </thead>';
		    result = result +' <tbody>';
		    result = result +' <input type="hidden" value='+json.length+' id="maxRowNum" name="maxRowNum">';
        for (var i = 0; i < json.length; i++) { 
        	 var opt = json[i]; 
        	 result = result +'<tr>';
        	 result = result +' <td><a href="javascript:void(0)"><i class="fa fa-ellipsis-h"></i></a></td>';
        	 result = result +' <td>';
        	 result = result +'<div class="inputCustom">';
        	 result = result +' <input class="calendar paddingRight" id="'+i+'sdate-input" name="'+i+'sdate" placeholder="Start Date" value='+opt.startDate+' type="text" readonly>';
        	 result = result +'<input class="calendar paddingLeft" id="'+i+'edate-input" name="'+i+'edate" placeholder="End Date" value='+opt.endDate+' type="text" readonly>';
        	 result = result +'</div>';
        	 result = result +' </td>';
        	 result = result +'<td>';
        	 result = result +' <div class="tbldata-dotdot-vsm select-field" opt.userId id="'+i+'associateName" name="'+i+'associateName">'+opt.empName+'</div>';
        	 result = result +' <input type="hidden" value='+opt.userId+' id="'+i+'candidateId" name="'+i+'candidateId">';
        	 result = result +' </td>';
        	 result = result +'  <td>';
        	 result = result +' <div class="tbldata-dotdot-100 select-field">';
        	 result = result +'<select class="form-control input-sm" id="'+i+'sow" name="'+i+'sow">';
        	 result = result +'<option>Sow</option>';
        	 result = result +' </select>';
        	 result = result +'</div>';
        	 result = result +'</td>';
        	 result = result +'<td>';
        	 result = result +'<div class="tbldata-dotdot-100">';
        	 result = result +'<input class="form-control input-sm" id="'+i+'rate" name="'+i+'rate" placeholder="Rate" type="text" onblur="calculateTotalAmount('+i+');">';
        	 result = result +'</div>';
        	 result = result +'</td>';
        	 result = result +' <td>';
        	 result = result +'<div class="tbldata-dotdot-vsm">';
        	 result = result +'  <input class="form-control input-sm" id="'+i+'totalHours" name="'+i+'totalHours" placeholder="Total Hours" value='+opt.totalHours+' type="text" onblur="calculateTotalAmount('+i+');">';
        	 result = result +'</div>';
        	 result = result +'</td>';
        	 result = result +'<td>';
        	 result = result +'<input class="form-control input-sm" id="'+i+'totalAmount" name="'+i+'totalAmount" value="0.0" placeholder="Amount" type="text" readonly="readonly">';
        	 result = result +'</td>';
        	 result = result +'</tr>';
        	/*  getSow(i) */
        }
        result = result +' <tfoot class="table_footer">';
        result = result +' <tr>';
        result = result +' <td colspan="5"></td>';
        result = result +'<td><strong>Total</strong></td>';
        result = result +' <td><div class="tblminwidth-75">  <input type="text" value = "00:00" id="rowTotal" name="rowTotal" class="ReadTextFli"></div></td>';
        result = result +' </tr>';
        result = result +' </tfoot> ';
        result = result +' </tbody>';
        result = result +' </table>';
        $("#tableBody").empty();
        $("#tableBody").append(result)
        
      /*   for (var i = 0; i < json.length; i++) { 
        	$('#'+i+'sdate-input').datepicker({
    			dateFormat : 'dd-mm-yy',
    			changeMonth: true,
    			changeYear: true, 
    			onSelect : function(selected) {
    			}
    		});
    	  $('#'+i+'edate-input').datepicker({
    			dateFormat : 'dd-mm-yy',
    			changeMonth: true,
    			changeYear: true, 
    			onSelect : function(selected) {
    			}
    		});
        } */
        document.getElementById("bottomGrid").style.display="block";
	},
	}); 

}
</script>

<!-- Get contract sow between client and the associate -->
  <script>
  function getSow(rowId){
	  var candidateId = $('#'+rowId+'candidateId').val();
	  var orgRelationId = $("#orgRelationId").val();
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getSowcontractListByAssociateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&orgRelationId="+orgRelationId,
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
		        	 for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		$('#'+rowId+'sow').empty();
			        		$('#'+rowId+'sow').append('<option value='+opt.id+' >'+opt.contractTitle+'</option>');
					} 
	        	}else{
	        		
	        	}
	        },
	        
	    });
  }
  
  </script>
  
  <!-- calculating row total with rate and total hours -->
  <script>
  function calculateTotalAmount(rowId){
	 var totalHours =  $('#'+rowId+'totalHours').val();
	 var rate =  $('#'+rowId+'rate').val();
	 if(totalHours != ''){
		 var split = totalHours.split(':');
	     var hours = split[0];
 	     var minute = split[1];
		 var hourminits = hours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
	     var perMinute = rate / 60;
	     var totlalAmount = (perMinute * finalmin);
	     $('#'+rowId+'totalAmount').val(totlalAmount.toFixed(2));
	 }else{
		 $('#'+rowId+'totalAmount').val('');
	 }
	 calculateSum();
	 
  }
  
  </script>
	
	<!-- calculating sub total with all row total amount -->
<script>
function calculateSum(){
	 var maxRowNum = $("#maxRowNum").val();
	 var sum = 0;
	  for(var i=0;i < maxRowNum;i++){
		  if(document.getElementById(i+"totalAmount")){
	    		 if(document.getElementById(i+"totalAmount").value){
	    			var rowAmount = $('#'+i+'totalAmount').val();
		 	    	sum = parseFloat(sum) + parseFloat(rowAmount);
	    		 }
	    		
	    	 } 
	 }  
	  $("#rowTotal").val(sum.toFixed(2));
	  $("#subTotal").val(sum.toFixed(2));
	  $("#grandTotal").val(sum.toFixed(2));
	  $("#afterDiscountTotal").val(sum.toFixed(2));
	  calculateDisCount();
	  
	  /* =====================  After adding the rows calculate the tax start  ===================== */
	  var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
	   var afterDiscountTotal = $("#afterDiscountTotal").val();
	   
		    for(var i=0; i< maxRowNum; i++){
		         var taxType = $("#"+i+"taxdiscountType").val();
		         var taxValue = $("#"+i+"taxdiscountvalue").val();
		         var taxTotal = $("#"+i+"taxTotal").val();
		            
	              if(taxValue == '' && taxTotal==''){
	            	  $("#grandTotal").val(afterDiscountTotal.toFixed(2));
	              }else{
	            	  var tot = $("#grandTotal").val();
	  	            var first=parseFloat(tot);
	  	             var second=parseFloat(taxTotal); 
	  	             var total  = (first+second);
	  	           $("#grandTotal").val(total.toFixed(2));
	              }
		            
		    }
}
	
</script>
  
 <!-- Calculationg Total Discount With Sub Total --> 
<script type="text/javascript">
function calculateDisCount(){
	if(discountValue !='' && discountValue !='undefined'){
		var discountValue = $("#discountValue").val();
		var subTotal = $("#subTotal").val();
		var disCountType = $("#disCountType").val();
					 	 if(disCountType == "flat"){
					 		 var total = subTotal-discountValue;
					 		 $("#totalDiscountAmt").val(discountValue); 
					 		$("#grandTotal").val(total);
					 		$("#afterDiscountTotal").val(total.toFixed(2));
					 		
					 	  }
					 	  if(disCountType == "percentage"){
					 		var perCentValue = (subTotal * discountValue)/100;
					 		 var total = subTotal - perCentValue;
					 		 $("#totalDiscountAmt").val(perCentValue);
					 		$("#grandTotal").val(total);
					 		$("#afterDiscountTotal").val(total.toFixed(2));
					 	 }	 
}
}
</script>

<!-- Adding Dynamic Tax Div Ids -->
<!-- ********************************************* Add Rows AddTax Table functionality start  ************************************************************ -->	
  
  <div id="divTaxTd1" style="display: none; float: left;">
	<div class="select-field tblminwidth-75">
		  <div class="tblminwidth-50">
		        <input type="text" class="form-control input-sm" value="" placeholder="Tax type" name='REPLACEROWIDtaxType' id='REPLACEROWIDtaxType'>
		  </div>
	 </div>
</div>

  <div id="divTaxTd2" style="display: none; float: left;">
	 <div class="btn-group tblminwidth-75">
	 <select class="form-control input-sm" id="REPLACEROWIDtaxdiscountType" name="REPLACEROWIDtaxdiscountType">
		 <option value="percentage">Percentage</option>
		 <option value="flat">Flat</option>
 		  </select>
	  </div>
  </div>
  
  <div id="divTaxTd3" style="display: none; float: left;">
     <div class="tblminwidth-75">
        <input type="text" class="form-control input-sm allownumericwithdecimal" value="" placeholder="Flat / Discount" name='REPLACEROWIDtaxdiscountvalue' id='REPLACEROWIDtaxdiscountvalue' onblur="calculateTaxDiscount();">
    </div>
  </div>
  
  <div id="divTaxTd4" style="display: none; float: left;">
	  <div class="tblminwidth-75">  
	       <input type="text" class="form-control input-sm" value ="00.00" placeholder="Total Value" name='REPLACEROWIDtaxTotal' id='REPLACEROWIDtaxTotal' readonly="readonly">
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
				
				var row = tbl.insertRow(maxRowNum);
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
       //=======================================
        
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
    }
    if(taxType == 'percentage'){
        var perCentValue = (afterDiscountTotal * taxValue)/100;
        var first=parseFloat(perCentValue.toFixed(2));
        var second=parseFloat(afterDiscountTotal);
        total = (first+second).toFixed(2);
        $("#0taxTotal").val(first);
        $("#grandTotal").val(total);
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
	 $("#grandTotal").val(grandTotal.toFixed(2));
  
}
	
	/* var taxType = $("#"+rowId+"taxdiscountType").val();
    var taxValue = $("#"+rowId+"taxdiscountvalue").val();
    var afterDiscountTotal = $("#afterDiscountTotal").val();
    var grandTotal =  $("#grandTotal").val();
     var total ='';
    if(taxType == 'flat'){
    	 var first=parseFloat(taxValue);
         var second=parseFloat(grandTotal);
         total = (first+second);
         
        $("#"+rowId+"taxTotal").val(first);
        $("#grandTotal").val(total);
    }
    if(taxType == 'percentage'){
        var perCentValue = (afterDiscountTotal * taxValue)/100;
        var first=parseFloat(perCentValue.toFixed(2));
        var second=parseFloat(grandTotal);
        total = (first+second);
        $("#"+rowId+"taxTotal").val(first.toFixed(2));
        $("#grandTotal").val(total.toFixed(2)); 
    } */ 
	

</script>

<!-- Adding Attachment Div Ids -->

<!-- ********************************************* Add Rows resume Table functionality start  ************************************************************ -->
  
  <div id="divInvoiceAttachmentTd1" style="display: none; float: left;">
  			<div class="timesheet_week_comm col-md-12 ">
                <div class="form-group" id="REPLACEROWIDinvoicedocFileuploadFileGroup"> 
               		 <span> 
               		 	<input type="file" name='REPLACEROWIDinvoicedocFile' id='REPLACEROWIDinvoicedocFile' onchange='validateContractsUploadFile(REPLACEROWID)'>
               		    <i class="fa fa-times" onclick="removeInvoiceAttachmentRow(REPLACEROWID);"></i>
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
function saveProjectInvoice(saveType){
        /*  if ($('#invoice_form').validate(false, validationSettings)){   */
 		    var frm = $('#project_invoice_form').serializeFormJSON();
 	        var con = JSON.stringify(frm);
 	        con = con.replace(/[{}]/g, "");
 	        //It will escape all the special characters
 	        var jsonData = encodeURIComponent(con);
 	        
 	        var form = $('#project_invoice_form')[0];
 	 	    var formData = new FormData(form); 
 	 	    var sourceleadUrl ='${sourceleadUrl}';
	    	        
	    	        $("#invoiceLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	    	        $.ajax({
	    		    	 type: "POST",
	    		    	 data: formData,
	    			     contentType : 'application/json; charset=utf-8',
	    			     enctype: 'multipart/form-data',
	    		         processData: false,
	    		         contentType:false,
	    			     dataType : 'json',
	     		    	 url: sourceleadUrl+"/saveProjectInvoice?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&saveType="+saveType+"&invoiceType=receivable", 
	    		        success: function(result) {
	    		     		$("#invoiceLoading").removeAttr("style");
	    		     		location.replace(sourceleadUrl+"/invoiceList");
	    		     		
	    		        },
	    		    });  
	    	         /* return true;  */
         /* }else{
	    		return false;
	    	  }  */
	   
}

/* $('body').on('blur', '#project_invoice_form', function() {
	$("#project_invoice_form").showHelpOnFocus();
	$("#project_invoice_form").validateOnBlur(false, validationSettings);  
});  */
</script>
 
 <!-- Adding Date Pickers -->
  <script>
	$(function() {
		$('#invoiceDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
					errInvoiceDate();
			}
		});
	  $('#dueDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				errDueDate();
			}
		});
		
	});
</script>
  
 <jsp:include page="../masterFooter.jsp" />	
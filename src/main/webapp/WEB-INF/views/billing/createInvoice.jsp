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
<script type="text/javascript">
$(document).ready(function(){
	getUserList("0");
});
</script>
<script>
function errRemoveClient(){
	$("#errClient").find("p.jquery_form_error_message").remove();
}
function errRemoveClientContact(){
	$("#errClientContact").find("p.jquery_form_error_message").remove();
}

</script>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Invoice</h2>
      </div>
      <div class="pull-right brud-crum">Home » Invoice</div>
    </section>
    <div class="content-area content-new clearfix">
    <div id="invoiceLoading"></div>
     <form:form id="invoice_form"  name="invoice_form" method="post"  enctype='multipart/form-data'> 
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3">Client<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field" id="errClient">
                <select class="chosen-select form-control" id="clientOrgRelationId" name="clientOrgRelationId" onchange="getClientContacts();errRemoveClient();" data-validation="required" field-name="Client Name">
                 <option value=''>--Select Clinet--</option>
                   <c:forEach var="orgrelations" items="${clientList}">
                         <option value="${orgrelations.id}">${orgrelations.orgName}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-3">Client Contact<span class="text-danger">*</span></label>
              <div class="col-md-9" id="errClientContact">
                  <select class="chosen-select form-control" id="clientContact" name="clientContact" data-validation="required" field-name="Vendor Contact" onchange="errRemoveClientContact();">
                  </select>
              </div>
            </div>
            <!-- <div class="form-group">
              <label class="col-md-3">Client Contact</label>
              <div class="col-md-9">
                <input type="text" class="form-control"id="clientContact" name="clientContact" value="" placeholder="Client Contact">
              </div>
            </div> -->
            <div class="form-group">
              <label class="col-md-3">Invoice Id<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="invoiceId" name="invoiceId" value="" placeholder="Invoice Id" data-validation="required" field-name="Invoice Id">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Invoice Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field" id="errInvoiceDate">
                <input type="text" class="form-control calendar" id="invoiceDate" name="invoiceDate" value="" placeholder="Invoice Date" id="invoiceDate" readonly data-validation="required" field-name="Invoice Date">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Payment terms</label>
              <div class="col-md-9" id="errPayment">
                  <select class="chosen-select form-control" id="paymentFrequency" name="paymentFrequency" >
                    <option value="">Select frequency</option>
                    <option value="10 days">10 days</option>
                    <option value="15 days">15 days</option>
                    <option value="30 days">30 days</option>
                    <option value="45 days">45 days</option>
                     <option value="60 days">60 days</option>
                  </select>
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Due Date<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9" id="errDuedate">
                <input type="text" class="form-control calendar" id="dueDate" name="dueDate" value="" placeholder="Invoice Date" id="dueDate" readonly data-validation="required" field-name="Due Date">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Billing Address</label>
              <div class="col-md-9">
                <textarea class="form-control" rows="2" id="billingAddress" name="billingAddress" placeholder="Billing Address"></textarea>
              </div>
            </div>
            
             
           <!--  <div class="form-group">
              <label class="col-md-3">Preojects<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 select-field">
                <select class="form-control" id="projectId" name="projectId" onchange="getProjects();">
                </select>
              </div>
            </div> -->
          </div>
        </div>
       
        <div class="clearfix"></div>
        <!-- Drap and Drop Table Row -->
        
        
        <!-- Table starts here-->
          <div class="project_wrap_table mt25 invoice_table" id="invoiceTable">
          <input type="hidden" id="maxTsRowNum" name="maxTsRowNum" value="1" />
          <table width="100%">
            <thead id="addInvoiceTbl">
              <tr id="divTsRow0">
                <!-- <th><i class="fa fa-ellipsis-h"></i></th> -->
                <th>Date</th>
                <th>Associate</th>
                <th>Sow</th>
                <th>Rate</th>
                <th>Total Hours</th>
                <th>Amount</th>
                <th><i class="fa fa-remove"></i></th>
              </tr>
            </thead>
            <tbody class="ui-sortable">
              <tr>
                <!-- <td><a href="javascript:void(0)"><i class="fa fa-ellipsis-h"></i></a></td> -->
                <td>
                  <div class="inputCustom">
                    <input type="text" class="calendar paddingRight" id="0sdate-input" placeholder="Start Date" name="0invoiceStartDate">
                    <input type="text" class="calendar paddingLeft" id="0edate-input" placeholder="End Date" name="0invoiceEndDate">
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-vsm select-field">
                    <select class="form-control input-sm" id="0candidateId" name="0candidate">
                    <option value="">--selcet Associate--</option>
                    </select>
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-100 select-field">
                    <select class="form-control input-sm" id="0sow" name="0sow">
                      <option value="">selcet Sow</option>
                    </select>
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-100">
                    <input type="text" class="form-control input-sm" placeholder="Rate" name="0rate" id="0rate">
                  </div>
                </td>
                <td>
                  <div class="tbldata-dotdot-vsm">
                    <input type="text" class="form-control input-sm inline_field" placeholder="HH:MM" id="0totalHours" name="0totalHours" onblur="singleRowTotalhour();" >
                  </div>
                </td>
                <td><div class="tbldata-dotdot-100 text-right"  ><input type="text" name="0amount" id="0amount" placeholder = "00:00" class="ReadTextFli" readinly> </div></td>
                <td><a class="text-danger tblminwidth-50" title="Remove row"><i class="fa fa-remove"></i></a></td>
              </tr>
               
            </tbody>
            <tfoot class="table_footer">
              <tr>
                <td colspan="4"></td>
                <td><strong>Subtotal</strong></td>
                <td><div>  <input type="text" value = "00:00" id="inoviceTotal" name="inoviceTotal" class="ReadTextFli"></div>
                <input type="hidden" value = "" id="hiddenTotal">
                <td>&nbsp;</td>
              </tr>
            </tfoot>
          </table>
        </div>
        <!-- Table Ends here-->
          
          <!-- Adding Content Starts herer -->
          <div class="clearfix invoice_other">
          <div class="col-md-5">
            <div class="clearfix mb">
              <button type="button" class="btn btn-sm btn-primary mr" onclick="addInvoice();"><i class="fa fa-plus paddingRight"></i>Add</button>
              <!-- <button type="button" class="btn btn-sm btn-default" onclick="deleteRow()"><i class="fa fa-close paddingRight"></i>Clear</button> -->
            </div>
           <!--  <div class="clearfix mb"><p>Do you want to raise separate invoice for each item</p>
              <label class="paddingRight">
                <input type="radio" value="Y" id="seperateInvoice" name="seperateInvoice">
                <strong class="paddingLeft">Yes</strong></label>
              <label class="paddingLeft">
                <input type="radio" value="N" id="seperateInvoice" name="seperateInvoice">
                <strong class="paddingLeft">No</strong></label>
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
                <div class="col-md-7 select-field">
                  <div class="row">
                    <div class="col-md-7 paddingRight">
                      <select class="form-control input-sm" id="disCount" name="disCount">
                        <option value="percentage">Discount %</option>
                        <option value="flat">Discount Value</option>
                      </select>
                    </div>
                    <div class="col-md-5 paddingLeft">
                      <input type="text" class="form-control input-sm" id="discountValue" name="discountValue" placeholder="Enter value" onblur="calculateDiscount();">
                    </div>
                  </div>
                </div>
                <div class="col-md-5 text-right">
                  <input type="text" class="form-control input-sm" id="discountTotal" name="discountTotal" placeholder="00.00" readonly>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-7 text-right"><strong>After Discount Total</strong></label>
                <div class="col-md-5 text-right" ><input type="text"  id="afterDiscountTotal" name="afterDiscountTotal"  value="00.00" class="ReadTextFli" readinly></div>
              </div>
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
                                        <input type="text" class="form-control input-sm" value="" placeholder="Flat / Discount" id="0taxdiscountvalue" name="0taxdiscountvalue" onblur="singleRowTaxDiscount();">
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
               <div class="col-md-5 text-right"> <input type="text" value = "00.00" id="grandTotal" name="grandTotal" class="ReadTextFli"></div>
                   <!--  <div  id="grandTotal" name="grandTotal"><span><strong>00.00</strong></span></div> -->
              </div>
            </div>
          </div>
        </div>
        <!-- Adding Content Starts herer -->
          <!-- Drap and Drop Table Row --> 
      <footer class="form-footer">
              <div class="pull-right mr">
                  <button type="button" class="btn btn-sm btn-primary" onclick="saveInvoice('DRAFT')">Save</button>
                  <button type="button" class="btn btn-sm btn-success ml">Send</button>
                  <button type="button" class="btn btn-sm btn-default ml" onClick="javascript:location.href = '<c:url value='/invoiceList'/>'">Cancel</button>
              </div>
          </footer>
      </div>
      </form:form>
    </div>
  </section>
  
  

<!-- ********************************************* Add Rows Invoice Table functionality start  ************************************************************ -->	
	<div id="divTaskTd2" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm select-field">
       <select class="form-control input-sm" id="REPLACEROWIDcandidateId" name="REPLACEROWIDcandidate" onchange="getSow(REPLACEROWID)"> </select>
       </div>
	</div>
	
	<div id="divTaskTd3" style="display: none; float: left;">
       <div class="tbldata-dotdot-100 select-field">
       <select class="form-control input-sm" id="REPLACEROWIDsow" name="REPLACEROWIDsow">
          <option value=''>-Select sow-</option>
        </select>
       </div>
	</div>
	
	<div id="divTaskTd4" style="display: none; float: left;">
       <div class="tbldata-dotdot-100">
       <input type="text" class="form-control input-sm" placeholder="Rate" id="REPLACEROWIDrate" name="REPLACEROWIDrate" >
       </div>
	</div>

	<div id="divTaskTd5" style="display: none; float: left;">
	<div class="tbldata-dotdot-vsm">
	     <input type="text" class="form-control input-sm inline_field" placeholder="HH:MM" id="REPLACEROWIDtotalHours" name="REPLACEROWIDtotalHours" onblur="calculateAmount(REPLACEROWID);">
	     </div>
	</div>
	
	<div id="divTaskTd6" style="display: none; float: left;">
	<div class="tbldata-dotdot-100 text-right"  ><input type="text" name="REPLACEROWIDamount" id="REPLACEROWIDamount" placeholder = "00:00" class="ReadTextFli" > </div>
<!-- 	     <div class="tbldata-dotdot-100 text-right" id="REPLACEROWIDamount" name="REPLACEROWIDamount"><input type="text" value = "00:00" class="ReadTextFli"></div> -->
	</div>
	
	<div id="divTaskTd7" style="display: none; float: left;">
	  <a class="text-danger tblminwidth-50" title="Remove row" onclick="removeRow(REPLACEROWID)"><i class="fa fa-remove"></i></a>
	</div>
	
<script type="text/javascript">
	function removeRow(rowId) {
		 var rate = $("#"+rowId+"rate").val();
		 var totalHours = $("#"+rowId+"totalHours").val();
		 var subTotal = $("#inoviceTotal").val();
		 
	     var split = totalHours.split(':');
	     var hours = split[0];
 	     var minute = split[1];
		 var hourminits = hours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
	     var perMinit = rate / 60;
	     var totlalAmount = (perMinit * finalmin);
	     var subtractTotal = subTotal - totlalAmount.toFixed(2)
	     $('#inoviceTotal').val(subtractTotal);
	    /*  $("#grandTotal").val(subtractTotal); */
	     $("#hiddenTotal").val(subtractTotal);
	     
	     //==============
	     var discountValue = $("#discountValue").val();
	     var discountType = $("#disCount").val();
	     var subtractValueFromRow = $("#hiddenTotal").val();
	     var total ='';
	 	 if(discountType == 'flat'){
	 		total = subtractValueFromRow-discountValue;
	 		$("#discountTotal").val(discountValue);
	 		$("#grandTotal").val(total);
	 		$("#afterDiscountTotal").val(total);
	 		
	 	  }
	 	  if(discountType == 'percentage'){
	 		var perCentValue = (subtractValueFromRow * discountValue)/100;
	 		total = subtractValueFromRow - perCentValue;
	 		$("#discountTotal").val(perCentValue);
	 		$("#grandTotal").val(total);
	 		$("#afterDiscountTotal").val(total);
	 	 }	    	 
	    	 
	    	 //===================
	    
	     var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
	     var afterDiscountTotal = $("#afterDiscountTotal").val();
	     
		    for(var i=0; i< maxRowNum; i++){
		         var taxType = $("#"+i+"taxdiscountType").val();
		         var taxValue = $("#"+i+"taxdiscountvalue").val();
		         var taxTotal = $("#"+i+"taxTotal").val();
		         
		            var tot = $("#grandTotal").val();
		             
		            var first=parseFloat(tot);
		             var second=parseFloat(taxTotal); 
		             var total  = (first+second);
	                
		             $("#grandTotal").val(total);
		    }
		
		document.getElementById('divTsRow' + rowId).style.display = 'none';
		var r = document.getElementById('addInvoiceTbl').rows[rowId];
		var count = 2 * (r.cells.length);
		for (i = 0; i <= count; i++) {
			r.deleteCell(0);
		}
		
		
		
	
	}
</script>

  <script type="text/javascript">
	function addInvoice() {
		var checkMaxTsRowNum = $("#maxTsRowNum").val();
		var tbl = document.getElementById("addInvoiceTbl");
		var maxRowNum = parseInt($("input[name = 'maxTsRowNum']").val());
		$("input[name = 'maxTsRowNum']").val(maxRowNum + 1);
  
		
		var row = tbl.insertRow(maxRowNum);
		row.setAttribute("id", "divTsRow" + maxRowNum);
		
		var text = "";
		var cell0 = row.insertCell(0);
		text = text+"<div  class='inputCustom'>";
		text = text+'<input type="text" class="calendar paddingRight" id="'+maxRowNum+'sdate-input" name="'+maxRowNum+'invoiceStartDate" placeholder="Start Date">';
		text = text+'<input type="text" class="calendar paddingLeft" id="'+maxRowNum+'edate-input" name="'+maxRowNum+'invoiceEndDate" placeholder="End Date">';
		text = text+'</div>';
		cell0.innerHTML = text;
		
		$( "#"+maxRowNum+"sdate-input" ).datepicker({
			  changeMonth: true,
		      changeYear: true,
		      dateFormat : 'dd-mm-yy',
		      minDate: 0  ,
			  onSelect : function(selected) {
		 	  }
		    });
		
		$( "#"+maxRowNum+"edate-input" ).datepicker({
			  changeMonth: true,
		      changeYear: true,
		      dateFormat : 'dd-mm-yy',
		      minDate: 0  ,
			  onSelect : function(selected) {
		 	  }
		    });
		
		
		var cell1 = row.insertCell(1);
		var divTsTd2Str = document.getElementById("divTaskTd2").innerHTML;
		divTsTd2Str = divTsTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
		divTsTd2Str = divTsTd2Str.replace(/PRICECALCULATION/g,("")); 
		cell1.innerHTML = divTsTd2Str;
		
		var cell1 = row.insertCell(2);
		var divTsTd3Str = document.getElementById("divTaskTd3").innerHTML;
		divTsTd3Str = divTsTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd3Str;
		
		var cell1 = row.insertCell(3);
		var divTsTd4Str = document.getElementById("divTaskTd4").innerHTML;
		divTsTd4Str = divTsTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd4Str;
		
		var cell1 = row.insertCell(4);
		var divTsTd5Str = document.getElementById("divTaskTd5").innerHTML;
		divTsTd5Str = divTsTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd5Str;
		
		
		var cell1 = row.insertCell(5);
		var divTsTd6Str = document.getElementById("divTaskTd6").innerHTML;
		divTsTd6Str = divTsTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd6Str;
		
		var cell1 = row.insertCell(6);
		var divTsTd7Str = document.getElementById("divTaskTd7").innerHTML;
		divTsTd7Str = divTsTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
		cell1.innerHTML = divTsTd7Str;
		getUserList(maxRowNum);
		maskme(); 
	}
  </script>
  
  
  <script>
  function getUserList(rowId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getAssociatesList?${_csrf.parameterName}=${_csrf.token}",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-Select Associates-</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		if(opt.lastName != null && opt.lastName != '' && opt.lastName !='undefined'){
		        			lastName = opt.lastName;
		        		}else{
		        			lastName = "";
		        		}
		        		
		        		$('#'+rowId+'candidateId').append('<option value='+opt.id+' >'+opt.firstName+' '+lastName+'</option>');
				}
	        	
	        },
	        
	    });
  
  }
  </script>
  <script>
  function getSow(rowId){
	  var candidateId = $('#'+rowId+'candidateId').val();
	  var orgRelationId = $("#clientOrgRelationId").val();
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
  
  <script>
  function calculateAmount(rowId){
	  var rate = $("#"+rowId+"rate").val();
	  var totalHours = $("#"+rowId+"totalHours").val();
	     var split = totalHours.split(':');
	     var hours = split[0];
 	     var minute = split[1];
 	     
 	     var hours1 = hours.charAt(0);
		 var hours2 = hours.charAt(1);
		 var min1 = minute.charAt(0);
		 var min2 = minute.charAt(1);
		
		 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
			 $("#"+rowId+"totalHours").val("0"+hours1+":00");
			calAddRowAmount("0"+hours1,"00",rowId);
		 }
		 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
			 $("#"+rowId+"totalHours").val("0"+hours2+":00");
			calAddRowAmount("0"+hours2,"00",rowId);
		 }
		 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
			 $("#"+rowId+"totalHours").val("00:"+min1+"0");
			calAddRowAmount("00",min1+"0",rowId);
		 }
		 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
			 $("#"+rowId+"totalHours").val("00:0"+min2);
			calAmount("00","0"+min2,rowId);
		 }
		 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
			 $("#"+rowId+"totalHours").val(hours+":00");
			calAddRowAmount(hours,"00",rowId);
		 }
		 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
			 $("#"+rowId+"totalHours").val(hours+":"+"0"+min1);
			calAddRowAmount(hours+":","0"+min1,rowId);
		 }
		 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
			 $("#"+rowId+"totalHours").val(hours+":"+"0"+min2);
			calAddRowAmount(hours+":","0"+min2,rowId);
		 }
		 else if(!isNaN(hours)&& !isNaN(minute)){
			 calAddRowAmount(hours,minute,rowId);
		 }
	    
	  
  }
  function calAddRowAmount(hours,minute,rowId){
	  var rate = $("#"+rowId+"rate").val();
	  var hourminits = hours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
	     var perMinit = rate / 60;
	     var totlalAmount = (perMinit * finalmin);
	     $('#'+rowId+'amount').empty();
	     $('#'+rowId+'amount').val(totlalAmount.toFixed(2));
	     calTotalamount(rowId,totlalAmount);
  }
  </script>
  <script>
  function  calTotalamount(rowId,totlalAmount){
	  
   var hiddenTotal = +document.getElementById("hiddenTotal").value;
   var grandTotal = parseFloat(hiddenTotal) + parseFloat(totlalAmount);
   var arroundGrand = grandTotal.toFixed(2)
   $("#inoviceTotal").val(arroundGrand);
   $("#hiddenTotal").val(arroundGrand);
   $("#grandTotal").val(arroundGrand);
   $("#afterDiscountTotal").val(arroundGrand);
   
   
   /* =====================  After adding the rows calculate the discount start  ===================== */
   var discountValue =  $("#discountValue").val();
   var discountType =  $("#disCount").val();
  
   if(discountValue != null && discountValue != ''){
		var total ='';
		if(discountType == 'flat'){
			total = arroundGrand-discountValue;
			$("#discountTotal").val(discountValue);
			$("#grandTotal").val(total);
			$("#afterDiscountTotal").val(total);
			
		}
		if(discountType == 'percentage'){
			var perCentValue = (arroundGrand * discountValue)/100;
			total = arroundGrand - perCentValue;
			$("#discountTotal").val(perCentValue);
			$("#grandTotal").val(total);
			$("#afterDiscountTotal").val(total);
		}
   }
   
   /* =====================  After adding the rows calculate the discount end  ===================== */
   
   /* =====================  After adding the rows calculate the tax start  ===================== */
   
   var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
   var afterDiscountTotal = $("#afterDiscountTotal").val();
   
	    for(var i=0; i< maxRowNum; i++){
	         var taxType = $("#"+i+"taxdiscountType").val();
	         var taxValue = $("#"+i+"taxdiscountvalue").val();
	         var taxTotal = $("#"+i+"taxTotal").val();
	         
	            var tot = $("#grandTotal").val();
	             
	            var first=parseFloat(tot);
	             var second=parseFloat(taxTotal); 
	             var total  = (first+second);
              
	             $("#grandTotal").val(total);
	    }
   /* =====================  After adding the rows calculate the tax end  ===================== */
   
   
  }
  </script>
  
<script type="text/javascript">
function calculateDiscount(){
	var discountType = $("#disCount").val();
	var subTotal = $("#inoviceTotal").val();
	var discountValue = $("#discountValue").val();
	var total ='';
	if(discountType == 'flat'){
		total = subTotal-discountValue;
		$("#discountTotal").val(discountValue);
		$("#grandTotal").val(total);
		$("#afterDiscountTotal").val(total);
		
	}
	if(discountType == 'percentage'){
		var perCentValue = (subTotal * discountValue)/100;
		total = subTotal - perCentValue;
		$("#discountTotal").val(perCentValue);
		$("#grandTotal").val(total);
		$("#afterDiscountTotal").val(total);
	}
	var maxRowNum = parseInt($("input[name = 'maxTaxRowNum']").val());
	if(maxRowNum > 1){
	    for(var i=1;i<=maxRowNum;i++){
	         document.getElementById("divTaxRow"+i+"").style.display="none";
	    }
	    $("#grandTotal").val(subTotal);
	}
}
</script>

<script>
  function singleRowTotalhour(){
	     var rate = $("#0rate").val();
	     var totalHours = $("#0totalHours").val(); 
	     var split = totalHours.split(':');
	     var hours = split[0];
 	     var minute = split[1];
 	     
 	     var hours1 = hours.charAt(0);
 		 var hours2 = hours.charAt(1);
 		 var min1 = minute.charAt(0);
 		 var min2 = minute.charAt(1);
 		
 		 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
 			$("#0totalHours").val("0"+hours1+":00");
 			calAmount("0"+hours1,"00");
 		 }
 		 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
 			$("#0totalHours").val("0"+hours2+":00");
 			calAmount("0"+hours2,"00");
 		 }
 		 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
 			$("#0totalHours").val("00:"+min1+"0");
 			calAmount("00",min1+"0");
 		 }
 		 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
 			$("#0totalHours").val("00:0"+min2);
 			calAmount("00","0"+min2);
 		 }
 		 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
 			$("#0totalHours").val(hours+":00");
 			calAmount(hours,"00");
 		 }
 		 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
 			$("#0totalHours").val(hours+":"+"0"+min1);
 			calAmount(hours+":","0"+min1);
 		 }
 		 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
 			$("#0totalHours").val(hours+":"+"0"+min2);
 			calAmount(hours+":","0"+min2);
 		 }
 		 else if(!isNaN(hours)&& !isNaN(minute)){
 			calAmount(hours,minute);
 		 }
 	     
		
  }
  function calAmount(hours,minute){
	  var rate = $("#0rate").val();
	     var parshours = parseInt(hours);
	     var hourminits = parshours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
	     var perMinit =   rate / 60;
	     var singleRowTotlalAmount = (perMinit * finalmin).toFixed(2);
	     $('#0amount').val(singleRowTotlalAmount);
	     $('#inoviceTotal').val(singleRowTotlalAmount);
	     $("#hiddenTotal").val(singleRowTotlalAmount);
	     $("#grandTotal").val(singleRowTotlalAmount);
	     $("#afterDiscountTotal").val(singleRowTotlalAmount); 
  }
  </script>
  


  
  <!-- ********************************************* Add Rows Invoice Table functionality End  ************************************************************ -->
  
  
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
  
  <!-- ********************************************* Add Rows resume Table functionality End  ************************************************************ -->
  
  
  
  
  
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
        <input type="text" class="form-control input-sm" value="" placeholder="Flat / Discount" name='REPLACEROWIDtaxdiscountvalue' id='REPLACEROWIDtaxdiscountvalue' onblur="calculateTaxDiscount(REPLACEROWID);">
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
				
	}			
</script>
  
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
function calculateTaxDiscount(rowId){
	var taxType = $("#"+rowId+"taxdiscountType").val();
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
        $("#"+rowId+"taxTotal").val(first);
        $("#grandTotal").val(total);
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
  
  
  
  <!-- ********************************************* Add Rows AddTax Table functionality end  ************************************************************ -->	
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
<script>
function errInvoiceDate(){
	$('#errInvoiceDate').find('p.jquery_form_error_message').remove(); 
}
function errDueDate(){
	$('#errDuedate').find('p.jquery_form_error_message').remove(); 
}

</script>
   
	<script>
	 /*  $(function() {
	    $( "#invoiceDate, #dueDate" ).datepicker({
	    	dateFormat : 'dd-mm-yy',
		  changeMonth: true,
	      changeYear: true,
	    });
	  }); */
	  
	  $( "#0sdate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : 'dd-mm-yy',
	      minDate: 0  ,
		  onSelect : function(selected) {
	 	  }
	    });
	
	$( "#0edate-input" ).datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat : 'dd-mm-yy',
	      minDate: 0  ,
		  onSelect : function(selected) {
	 	  }
	    });
	  </script> 
	<script type="text/javascript">
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
	    })
	    $(document).ready(function(){
	    $("#cd").click(function(){
	    $(".clientDetails").toggleClass('hidden', 500);
	    });
	    });
	</script> 
	<script>
	$("#invoiceTable tbody").sortable({
	    items: "> tr:not(:first)",
	    appendTo: "parent",
	    helper: "clone"
	}).disableSelection();
	    </script>
	    <script>
	    function cloneTax(){
	    }
	    </script>
	    
	    
	    
<!-- added by Rajashekar -->
<script>
function getClientContacts(){
	var orgRelationId = $("#clientOrgRelationId").val();
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

	}
</script>



<script>
function getProjectsList(){
	var orgRelationId = $("#clientOrgRelationId").val();
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	type : "GET",
	url: sourceleadUrl+"/getClientProjectsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId,
	success :function(data) {
	var json = JSON.parse(data);
	var output = "";
	output = output + "<option value=''>Select Client Contact</option>";
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
<script>
function saveInvoice(saveType){
	/* var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };  */
	
	   if ($('#invoice_form').validate(false, validationSettings)){  
		    var frm = $('#invoice_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var form = $('#invoice_form')[0];
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
		    	 url: sourceleadUrl+"/saveInvoice?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&saveType="+saveType, 
		        success: function(result) {
		     		$("#invoiceLoading").removeAttr("style");
		     		location.replace(sourceleadUrl+"/invoiceList");
		     		
		        },
		    });  
	         return true; 
		
	   }else{
		return false;
	  } 
}

$('body').on('blur', '#invoice_form', function() {
	$("#invoice_form").showHelpOnFocus();
	$("#invoice_form").validateOnBlur(false, validationSettings);  
}); 
</script>
<!-- End by Rajashekar -->
<!-- <script>
$(document).ready(function(){
	$(".dateMask").mask('9999:99');
});
</script> -->
<script>
$(document).ready(function() {  
	maskme();    
 });
</script>  	
<script>
function maskme(){
	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $(".inline_field").mask("Hh:Mm"); 
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
  
 <jsp:include page="../masterFooter.jsp" />	
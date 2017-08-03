<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function(){
	getViewInvoice();
	var invoiceType = '${invoiceType}';
	if(invoiceType == "Receivable"){
		document.getElementById("invoiceIdHidden").style.display="block";
		document.getElementById("BillIdHidden").style.display="none";
		
		document.getElementById("invoiceIdBr").style.display="block";
		document.getElementById("BillIdBr").style.display="none";
		
	}
	if(invoiceType == "Payables"){
		document.getElementById("BillIdHidden").style.display="block";
		document.getElementById("invoiceIdHidden").style.display="none";
		
		document.getElementById("invoiceIdBr").style.display="none";
		document.getElementById("BillIdBr").style.display="block";
		
		
	}
	
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left" id="invoiceIdHidden" style="display:none">
          <h2>Invoice Id: <span><input class="ReadTextFliA" id="invoiceIdDiv" readonly="readonly"></span></h2>
      </div>
      
      <div class="pull-left" id="BillIdHidden" style="display:none">
          <h2>Bill Id: <span><input class="ReadTextFliA" id="billIdDiv" readonly="readonly"></span></h2>
      </div>
      
      <div class="pull-right brud-crum" style="display:none" id="invoiceIdBr"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='/invoiceList'/>">Receivable List</a> » Invoice View</div>
      
      <div class="pull-right brud-crum" style="display:none" id="BillIdBr" ><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='/payablesList'/>">Payable List</a> » Bill View</div>
      
    </section>
    <div class="content-area content-new clearfix">
      <div class="client_invoice">
        <div class="tabbable positionRelative">
          
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active">
              <a href="#bill" data-toggle="tab" title="Bill" aria-expanded="true">
                <h5>Details</h5>
              </a>
            </li>
            <li class="">
            <sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')">
              <a href="#paymentHistory" data-toggle="tab" title="Payment History" aria-expanded="false" onclick="paymentHistroy();">
              </sec:authorize>
                <h5>Payment History</h5>
              </a>
            </li>
            
            
              <li class="">
<%--             <sec:authorize access="hasAnyRole(\'ROLE_BILL_PAYMENT_HISTORY\')"> --%>
              <a href="#notesList" data-toggle="tab" title="Payment History" aria-expanded="false" onclick="invoiceNotesList();">
<%--               </sec:authorize> --%>
                <h5>Notes</h5>
              </a>
            </li>
            
          </ul>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active in" id="bill">
              <div id="viewInvoiceId"></div>
              </div>
          
            <div class="tab-pane fade positionRelative" id="paymentHistory">
                <div class="invoiceview_area clearfix candidate_wrap_table filter_position">
                  <div id="paymentHistorytId"></div>
                    
              </div>
            </div>
            
            <div class="tab-pane fade positionRelative" id="notesList">
                <div class="invoiceview_area clearfix candidate_wrap_table filter_position">
                  <div id="notesListId"></div>
                    
              </div>
            </div>
            
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>
  
<script>
function getViewInvoice(){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var invoiceUUID ='${invoiceUUID}';
	  var invoiceType = '${invoiceType}';
	  var currencyCode = '${SourceLeadWeb_currencyCode}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getViewInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceUUID="+invoiceUUID,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var result = "";
	        	for (var i=0; i<json.length; i++)
	    		{  
	        		var report = json[i];
	        		$("#invoiceIdDiv").val(report.invoiceId)
	        		$("#billIdDiv").val(report.invoiceId)
	        		if(report.dueDateStr != null && report.dueDateStr != '' && report.dueDateStr !='undefined'){
	        			dueDateStr = report.dueDateStr;
	        		}else{
	        			dueDateStr = "";
	        		}
	        		
	        		if(report.invoiceDateStr != null && report.invoiceDateStr != '' && report.invoiceDateStr != 'undefined'){
	        			invoiceDateStr = report.invoiceDateStr;
	        		}else{
	        			invoiceDateStr = "";
	        		}
	        		if(report.paymentFrequency != null && report.paymentFrequency != '' && report.paymentFrequency != 'undefined'){
	        			paymentFrequency = report.paymentFrequency;
	        		}else{
	        			paymentFrequency = "";
	        		}
	        		if(report.billingAddress != null && report.billingAddress != '' && report.billingAddress != 'undefined'){
	        			billingAddress = report.billingAddress
	        		}else{
	        			billingAddress = "";
	        		}
	        		if(report.clientContactEmail != null && report.clientContactEmail != '' && report.clientContactEmail != 'undefined'){
	        			clientContactEmail = report.clientContactEmail;
	        		}else{
	        			clientContactEmail = "";
	        		}
	        		if(report.convertAmount != null && report.convertAmount != '' && report.convertAmount != 'undefined'){
	        			convertAmount = report.convertAmount;
	        		}else{
	        			convertAmount = "--";
	        		}
	        		
	        		if(report.convertCurrencyCode != null && report.convertCurrencyCode != '' && report.convertCurrencyCode != 'undefined'){
	        			convertCurrencyCode = report.convertCurrencyCode;
	        		}else{
	        			convertCurrencyCode = "";
	        		}
	        		
	        		if(report.exchangeRate != null && report.exchangeRate != '' && report.exchangeRate != 'undefined'){
	        			exchangeRate = report.exchangeRate;
	        		}else{
	        			exchangeRate = "--";
	        		}
	        		
	        		if(report.currencyCode != null && report.currencyCode != '' && report.currencyCode != 'undefined'){
	        			currencyCode = report.currencyCode;
	        		}else{
	        			currencyCode = "--";
	        		}
	        		
	        		
					result = result +'		<div class="invoiceview_area clearfix">                                      ';
					result = result +'          <div class="form-horizontal row">                                        ';
					result = result +'            <div class="col-md-6">                                                 ';
					if(report.invoiceType == "Receivable"){
						result = result +'              <label class="col-md-4 control-label">Client :</label>                ';
					}
					if(report.invoiceType == "Payables"){
						result = result +'              <label class="col-md-4 control-label">Vendor :</label>                ';
					}
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                  <label><strong>'+report.clientName+'</strong></label>  ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Contact Name :</label>         ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+report.clientContactName+'</label>';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Contact Email :</label>           ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+clientContactEmail+'</label>                         ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					if(invoiceType == "Payables"){
						result = result +'            <div class="col-md-6">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Bill Id :</label>             ';
						result = result +'              <div class="col-md-8">                                               ';
						result = result +'                <label>'+report.invoiceId+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
						result = result +'            <div class="col-md-6">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Bill Date :</label>           ';
						result = result +'              <div class="col-md-8">                                               ';
						result = result +'                <label>'+invoiceDateStr+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
					}else{
						result = result +'            <div class="col-md-6">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Invoice Id :</label>             ';
						result = result +'              <div class="col-md-8">                                               ';
						result = result +'                <label>'+report.invoiceId+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
						result = result +'            <div class="col-md-6">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Invoice Date :</label>           ';
						result = result +'              <div class="col-md-8">                                               ';
						result = result +'                <label>'+invoiceDateStr+'</label>                                          ';
						result = result +'              </div>                                                               ';
						result = result +'            </div>                                                                 ';
					}
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Payment Term :</label>           ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+paymentFrequency+'</label>                                             ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Due Date :</label>               ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+dueDateStr+'</label>                                          ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					if(invoiceType == "Payables"){
						result = result +'            <div class="col-md-6">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Billing Address :</label>        ';
						result = result +'              <div class="col-md-8 addressInvoice">                                ';
						result = result +'                <p>'+billingAddress+'</p>';
						result = result +'              </div>';
						result = result +'            </div>';
					}else{
						result = result +'            <div class="col-md-6">                                                 ';
						result = result +'              <label class="col-md-4 control-label">Invoice Address :</label>        ';
						result = result +'              <div class="col-md-8 addressInvoice">                                ';
						result = result +'                <p>'+billingAddress+'</p>';
						result = result +'              </div>';
						result = result +'            </div>';
					}
				
					result = result +'          </div>';
					result = result +'      </div>';
					
					
					
					/* ======================== InvoiceItem List start============================ */
					result = result +'	     <div class="invoiceGridview clearfix">                                  ';
					result = result +'         <table width="100%" cellpadding="0" cellspacing="0">                  ';
					result = result +'             <thead>                                                           ';
					result = result +'                 <tr>                                                          ';
					result = result +'                     <th>Id</th>                                               ';
					result = result +'                     <th>Associates</th>';
					result = result +'                     <th>Description</th>';
					result = result +'                     <th>SOW/PO</th>                                          ';
					result = result +'                     <th>Date</th>                                             ';
					result = result +'                     <th>Rate Hourly</th>                                             ';
					result = result +'                     <th>Total Hours</th>                                      ';
					result = result +'                     <th>Total Amount</th>                                     ';
					result = result +'                 </tr>                                                         ';
					result = result +'             </thead>                                                          ';
					result = result +'             <tbody>                                                           ';
					for (var j=0; j<report.invoiceLineItemsList.length; j++)
		    		{
						var items = report.invoiceLineItemsList[j]; 
						if(items.candidateName != null && items.candidateName != '' && items.candidateName != 'undefined'){
							candidateName = items.candidateName;
						}else{
							candidateName = "--";
						}
						if(items.sowName != null && items.sowName != '' && items.sowName != 'undefined'){
							sowName = items.sowName;
						}else{
							sowName = "--";
						}
						if(items.startDateStr != null && items.startDateStr != '' && items.startDateStr != 'undefined'){
							startDateStr = items.startDateStr +' To';
						}else{
							startDateStr = "-";
						}
						
						if(items.endDtaeStr != null && items.endDtaeStr != '' && items.endDtaeStr != 'undefined'){
							endDtaeStr = items.endDtaeStr;
						}else{
							endDtaeStr = "-";
						}
						if(items.rate != null && items.rate != '' && items.rate != 'undefined'){
							rate = items.rate;
						}else{
							rate = "--";
						}
						
						if(items.totalHours != null && items.totalHours != '' && items.totalHours != 'undefined'){
							totalHours = items.totalHours;
						}else{
							totalHours = "--";
						}
						if(items.amount != null && items.amount != '' && items.amount != 'undefined'){
							amount = items.amount;
						}else{
							amount = "--";
						}
						if(items.employeeId != null && items.employeeId != '' && items.employeeId != 'undefined'){
							employeeId = items.employeeId;
						}else{
							employeeId = "--";
						}
						result = result +'                 <tr>                                                          ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_50" title="'+employeeId+'">'+employeeId+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot-sm" title="'+candidateName+'">'+candidateName+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                     ';
						result = result +'                         <div class="tbldata-dotdot_80" title="'+items.inlineDescription+'">'+items.inlineDescription+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_80" title="'+sowName+'">'+sowName+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_80" title="'+startDateStr+'  '+endDtaeStr+'" >'+startDateStr+' &nbsp; '+endDtaeStr+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_50" title="'+rate+'">'+rate+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_80">'+totalHours+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_80" title="'+amount+'">'+amount+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                 </tr>                                                         ';
	    		}
				result = result +'</tbody>';
				result = result +'</table>';
				result = result +' </div>';
				/* ======================== InvoiceItem List End============================ */
				
				if(report.invoiceComment != null && report.invoiceComment != '' && report.invoiceComment != 'undefine'){
					invoiceComment = report.invoiceComment;
				}else{
					invoiceComment = "--";
				}
				if(report.discountAmount != null && report.discountAmount != '' && report.discountAmount != 'undefine'){
					discountAmount = report.discountAmount;
				}else{
					discountAmount = "0";
				}
				
				if(report.discountValue != null && report.discountValue != '' && report.discountValue != 'undefine'){
					discountValue = '-'+report.discountValue;
				}else{
					discountValue = "0";
				}
				
				result = result +'			<div class="row">';
				result = result +'	          <div class="col-md-5">';
				result = result +'	              <div class="col-md-12 mt">';
				result = result +'	                  <label><strong>Message</strong></label>  ';
				result = result +'	                  <div class="clearfix">                   ';
				result = result +'	                      <p>'+invoiceComment+'</p>   ';
				result = result +'	                  </div>                                                                                  ';
				result = result +'	              </div>                                                                                      ';
				result = result +'	              <div class="col-md-12 mt">                                                                  ';
				result = result +'	                <label><strong>Attachment:</strong></label>                                               ';
				if(report.invoiceDocumentsList != null && report.invoiceDocumentsList != '' && report.invoiceDocumentsList != 'undefined'){
					for (var j=0; j<report.invoiceDocumentsList.length; j++)
		    		{
						var doc = report.invoiceDocumentsList[j]; 
						
						result = result +'	                  <div class="clearfix attachfile_invoice">                                               ';
						result = result +'	                      <p class="pull-left" title="'+doc.fileName+'">'+doc.fileName+'</p>                                         ';
						result = result +" <span class='pull-right'> <i class=''><a class='glyphicon glyphicon-save' href=\""+sourceleadUrl+"/downloadInvoiceDocument?documentId="+doc.id+"\"></a></i>";
						result = result +'	                  </div>                                                                                  ';
		    		}
				}
				
				result = result +'	              </div>                                                                                      ';
				result = result +'	          </div>                                                                                          ';
				result = result +'	                                                                                                          ';
				result = result +'	        <div class="col-md-7">                                                                            ';
				result = result +'	            <div class="form-horizontal billsubTotal">                                                    ';
				result = result +'	              <div class="form-group">                                                                    ';
				result = result +'	                <label class="col-md-7 text-right"><strong>Subtotal</strong></label>                   ';
				result = result +'	                <div class="col-md-5 text-right"><span class="pull-left">'+currencyCode+'</span><span><strong>'+report.subTotal+'</strong></span></div>                             ';
				result = result +'	              </div>                                                                                      ';
				result = result +'	              <!-- Discount UI -->                                                                        ';
				result = result +'	              <div class="form-group">                                                                    ';
				result = result +'	                <div class="col-md-7 select-field">                                                       ';
				result = result +'	                  <div class="row">                                                                       ';
				result = result +'	                    <div class="col-md-7 paddingRight">                                                   ';
				if(report.discountType=="percentage"){
					result = result +'	                        <label><strong>Discount %</strong></label>                                        ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                    <div class="col-md-5 paddingLeft">                                                    ';
					result = result +'	                      <label>'+discountAmount+'%</label>                                                                   ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                  </div>                                                                                  ';
				}else{
					result = result +'	                        <label><strong>Discount Value</strong></label>                                        ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                    <div class="col-md-5 paddingLeft">                                                    ';
					result = result +'	                      <label>'+discountAmount+'</label>                                                                   ';
					result = result +'	                    </div>                                                                                ';
					result = result +'	                  </div>                                                                                  ';
				}
				
				
				
				result = result +'	                </div>                                                                                    ';
				result = result +'	                <div class="col-md-5 text-right">                                                         ';
				result = result +'	                  <label>'+discountValue+'</label>                                                               ';
				result = result +'	                </div>                                                                                    ';
				result = result +'	              </div>                                                                                      ';
				
				
				if(report.invoiceTaxList != null && report.invoiceTaxList != '' && report.invoiceTaxList != 'undefined' ){
				
				result = result +'	                <div class="addtax_table">';
				result = result +'	                    <table class="table-bordered table-striped table-responsive" width="100%" cellspacing="0" cellpadding="0">';
				
				result = result +'	                    <thead>';
				result = result +'	                   <tr>                                                                   ';
				result = result +'                    	 <th class="text-center">Service Type</th>                                               ';
				result = result +'                       <th class="text-center">Flat / Percentage</th> ';
				result = result +'                     	 <th class="text-center">Value</th>           ';
				result = result +'                    	 <th class="text-center">Amount</th>          ';
				result = result +'	                  </tr>';
				result = result +'	                        </thead>';
				result = result +'	                        <tbody>';
				
				for (var k=0; k<report.invoiceTaxList.length; k++)
	    		{
					var tax = report.invoiceTaxList[k]; 
					if(tax.taxName != null && tax.taxName != '' && tax.taxName != 'undefined'){
						taxName = tax.taxName;
					}else{
						taxName = "--";
					}
					if(tax.taxValue != null && tax.taxValue !='' && tax.taxValue != 'undefined'){
						taxValue = tax.taxValue;
					}else{
						taxValue = "0"
					}
					
					if(tax.taxAmount != null && tax.taxAmount !='' && tax.taxAmount != 'undefined'){
						taxAmount = tax.taxAmount;
					}else{
						taxAmount = "0"
					}
					
					result = result +'	                            <tr>                                                                   ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                   <div class="select-field tblminwidth-75">                       ';
					result = result +'	                                       <div class="tblminwidth-50">                                ';
					result = result +'	                                        '+taxName+'                                               ';
					result = result +'	                                    </div>                                                         ';
					result = result +'	                                   </div>                                                          ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                 <div class="btn-group tblminwidth-75">                            ';
					if(tax.taxType =="percentage"){
						result = result +'	                                        <label>Percentage</label>                               ';
					}else{
						result = result +'	                                        <label>Flat</label>                               ';
					}
					result = result +'	                                </div>                                                             ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                    <div class="tblminwidth-75">                                   ';
					result = result +'	                                        <label>'+taxValue+'</label>                               ';
					result = result +'	                                    </div>                                                         ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                                <td>                                                               ';
					result = result +'	                                    <div class="tblminwidth-75">                                   ';
					result = result +'	                                         <label>'+taxAmount+'</label>                                ';
					result = result +'	                                    </div>                                                         ';
					result = result +'	                                </td>                                                              ';
					result = result +'	                            </tr>                                                                  ';
	    		}
				result = result +'	                        </tbody>                                                                   ';
				result = result +'	                    </table>                                                                       ';
				result = result +'	            </div>                                                                                 ';
				
				}
				
				result = result +'	                <div class="form-group mt">                                                        ';
				result = result +'	                <label class="col-md-7 text-right"><strong>Total</strong></label>                  ';
				result = result +'	                    <div class="col-md-5 text-right"><span class="pull-left">'+currencyCode+'</span><span><strong>'+report.total+'</strong></span></div> ';
				result = result +'	              </div>                                                                               ';
				if(convertCurrencyCode != null && convertCurrencyCode != '' && convertCurrencyCode != 'undefined'){
					 result = result +'  <div class="row">';
	                  result = result +'  <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
	                  
	                  result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span>'+currencyCode+'</span><div class="pull-right"><input class="ReadTextFli" readonly="" type="text">'+report.total+'</div></div></div>';
	                  result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div class="paddingLeft col-md-6"><span>'+convertCurrencyCode+'</span><div class="pull-right"><input  class="ReadTextFli" readonly="" type="text">'+convertAmount+'</div></div></div>';
	                  result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div class="paddingLeft col-md-6"><span>'+currencyCode+' 1 = '+convertCurrencyCode+' </span><div class="pull-right"><input  class="ReadTextFli ReadTextFli90" readonly="" type="text">'+exchangeRate+'</div></div></div>';
	                  
	                  result = result +'  </div>';
	                  result = result +'  </div>'; 	
				}
				result = result +'	              </div>                                                                               ';
				
                  
				result = result +'	          </div>                                                                                   ';
				result = result +'	            </div>   ';
					
				 
	    		}
				$("#viewInvoiceId").empty();  
			    $("#viewInvoiceId").append(result)
	        },
	        
	    });
}

</script>

<script>
function invoiceNotesList(){
	var result = "";
	result = result  +'<div class="timesheet_head">';
	result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
	result  = result + '<div class="success_pop" id="vendorDocSelector" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="vendorDocMsgDiv"></span>';
    result  = result + '</div>';
	    result = result + '<ul class="padding_right">';
	   result = result  +'    	<a href="javascript:void(0)" onClick="addNotes();" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Note</a>                                                            ';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		
		result = result + '</ul>';
		result = result  +'  <div class="clearfix"></div> ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="vendorNotesListDiv" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';
	
	$("#notesListId").empty();  
    $("#notesListId").append(result)
    invoiceNotes();
    
}
</script>
<script>
function invoiceNotes(){
	var urlForm = "";
	  var invoiceUUID ='${invoiceUUID}';
    	urlForm = "/getNotesInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceUUID="+invoiceUUID;
	
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
	var output = "";
    if(data != null && data != 'undefined' && data != "" && data != '[]'){
    var json = JSON.parse(data);
//     '${SourceLeadWeb_user.id}' 

	 output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	  output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
     for(var i=0;i<json.length;i++){
       	var report = json[i];
       	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_user.id}' == report.userIdStr)){
   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    output = output+'        	<img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+report.commentAuthorUserId+'"/>';
   	    output = output+'        </div>';
   	    output = output+'    </div>';
   	    output = output+'     <div class="comments-content">';
   	    output = output+'     <div class="commentsNotes_inner">';
   	    output = output+'         <p class="comment-by">';
   	    output = output+'            By <span class="text-danger" title = "'+report.userName+'">'+report.userName+'</span>';
   	 
   	    output = output+'            <span class="small pull-right">'+report.createdDateString+'</span></p>';
   	    output = output+'         <p class="comment_text pull-left">'+report.comment+'</p>';
   	    output = output+'     </div>';
   	    output = output+'     </div>';
   	    output = output+'</div>';
    	}
     }
}else{
     var output ="";
    output = output+"   <div class='dataTables_empty'>"; 
	 output = output+"  </div> ";
}
output  = output + '              </div>';
output  = output + '        </div>';
	$("#vendorNotesListDiv").empty(); 
    $("#vendorNotesListDiv").append(output);
 },
         
      });
}
</script>

<script>
function closeDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<script>
function addNotes() {	
	 var invoiceUUID ='${invoiceUUID}';
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'          <form role="form"  id="invoiceNotes_form" name="invoiceNotes_form" method="post" > ';
	result = result  +'          <input type="hidden" id="invoicdeId" name="invoiceUUID" value="'+invoiceUUID+'"> ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="Billing"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result+'	                  <div class="clearfix">';
	 result = result +" <div class='col-md-12 inputOther inputOther'>";
    
	
     result = result +"    <div class='clearfix'>";
     result = result +"    <div class='dropdown-select mb10' id='clientCategoryContactTypeListDivDiv'>";
     result = result +"    </div>";
 	result = result  +'                <div class="form-horizontal clearfix"> ';
	result = result+'	                  <div class="clearfix">';
	result = result+'	                      <textarea rows="2" placeholder="Note" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length1-5000"  field-name="Note" class="form-control"></textarea>';
	result = result+'	                  </div>';
	result = result  +'                  </div>';
	
	result = result+' <div class="row clearfix mt5"> ';
	result = result+' <div class="col-md-9 checkBox"> ';
	result = result+'   <input type="checkbox" value= "Y" id="allowAll" class="pull-left" name = "allowAll" checked="checked">';
	result = result + '	                    <p class="pull-left mt5">Allow all users</p>';
	result = result+'	                  </div>';
	
	result = result  +'                  <div class="col-md-3" id="docAddButton"> ';
	result = result  +'                  <div class="pull-right"> ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary" onClick ="storeInvoiceNotes();" />'; 
  /*   result = result +'                     <input type="button" value="Cancel" name="btnAddPr" id="save_note" class="btn btn-sm btn-default" onClick ="storeClientNotes('+orgRelationId+');" />'; */ 
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	
	
	
	result = result  +'                </div>';
	
	result = result  +'                </form>';
	
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	  $(".edit-details").toggleClass("edit-details-active");
		 $(".close-div").click(function(){ 
			 $(this).parent("div.edit-details").removeClass("edit-details-active");
			 });
		 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
			 e.stopPropagation();
		 });
		 
				
				
	}
</script>
<script type="text/javascript">
	function storeInvoiceNotes(){
		if ($('#invoiceNotes_form').validate(false, validationSettings)){ 
				var frm = $('#invoiceNotes_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				//con = con.replace(/[{}]/g, "");
				var form = $('#invoiceNotes_form')[0];
	      		var formData = new FormData(form);
	      		formData.append("json",con);
	      		
	      		var userId = '${SourceLeadWeb_user.id}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      			
				var sourceleadUrl = '${sourceleadUrl}';
				$("#save_note").attr("disabled", "disabled");
         		$("#save_note" ).addClass('button').val('Processing..');
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeNotes?${_csrf.parameterName}=${_csrf.token}",
						success : function(result) {
							closeDocPopup();
							invoiceNotes();
							
						},
					});
					$('.add-comment').toggle(200);
					return true;
			 
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#invoiceNotes_form', function() {
		$("#invoiceNotes_form").showHelpOnFocus();
		$("#invoiceNotes_form").validateOnBlur(false, validationSettings);
		
	});
</script>
<script>
function paymentHistroy(){
	 var sourceleadUrl ='${sourceleadUrl}';
	  var invoiceUUID ='${invoiceUUID}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getViewPaymentHistory?${_csrf.parameterName}=${_csrf.token}&invoiceUUID="+invoiceUUID,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var result = "";                                                                                                
				result = result +'       	<table width="100%" cellpadding="0" cellspacing="0" id="paymentHistoryPagination"> ';
				result = result +'           <thead>                                ';
				result = result +'               <tr>                               ';
				result = result +'                   <th> Date</th>                 ';
				result = result +'                   <th>  Payment Method </th>     ';
				result = result +'                   <th> Reference No </th>        ';
				result = result +'                   <th> Amount Received </th>     ';
				result = result +'                   <th> Attachment  </th>         ';
				result = result +'                   <th> Memo </th>                ';
				result = result +'                   <th> Created By </th>                ';
				result = result +'               </tr>                              ';
				result = result +'           </thead>                               ';
				result = result +'           <tbody>                                ';
				for (var i=0; i<json.length; i++)
	    		{  
	        		var report = json[i];
	        		if(report.paymentAmount != null && report.paymentAmount !='' && report.paymentAmount != 'undefined'){
	        			paymentAmount = report.paymentAmount;
	        		}else{
	        			paymentAmount = "0"
	        		}
	        		if(report.fileName != null && report.fileName != '' && report.fileName != 'undefined'){
	        			fileName = report.fileName;
	        		}else{
	        			fileName = "--"
	        		}
	        		if(report.paymentComment != null && report.paymentComment != '' && report.paymentComment != 'undefined'){
	        			paymentComment = report.paymentComment;
	        		}else{
	        			paymentComment = "--";
	        		}
					result = result +'           <tr>   ';
					result = result +'              <td><div class="tbldata-dotdot-100">'+report.paymentDateStr+' </div></td>     ';
					result = result +'              <td><div class="tbldata-dotdot-50">'+report.paymentMethod+'</div></td>            ';
					result = result +'              <td><div class="tbldata-dotdot-50" title="'+report.paymentReferenceNumber+'">'+report.paymentReferenceNumber+'</div>              ';
					result = result +'              </td><td><div class="tbldata-dotdot-50" title="'+report.currencyCode+' '+paymentAmount+'">'+report.currencyCode+' &nbsp;&nbsp; '+paymentAmount+'</div></td> ';
					result = result +'              <td>                                                          ';
					result = result +'                <a class="tbldata-dotdot-vsm download_attachment" title="Download Attachment">      ';
// 					result = result +'                <p class="pull-left">'+fileName+'</p>                           ';
					if(report.fileName != null && report.fileName != '' && report.fileName != 'undefined'){
					result = result +" <span class='pull-right'> <i class=''><a class='glyphicon glyphicon-save' href=\""+sourceleadUrl+"/downloadInvoicePaymentDocument?paymentHistoryId="+report.id+"\"></a></i>";
					}else{
						result = result +" --";	
					}
					result = result +'                </a>                                ';
					result = result +'              </td>                                 ';
					result = result +'              <td><div class="tbldata-dotdot-me">   ';
					result = result +'                      <a class="memo_text">         ';
					result = result +'                          <p class="pull-left" title="LoremIpsumhasbeentheindustry" title="'+paymentComment+'">'+paymentComment+'</p>';
					result = result +'                         <i class="fa fa-comments pull-right" title="Memo"></i>  ';
					result = result +'                      </a> ';
					result = result +'                  </div>   ';
					result = result +'              </td>        ';
					
					result = result +'              <td><div class="tbldata-dotdot-100">'+report.createdByName+' </div></td>     ';
					result = result +'            </tr>          '; 
	    		}
				result = result +'           </tbody>        ';
				result = result +'       </table>  ';     
				$("#paymentHistorytId").empty();  
			    $("#paymentHistorytId").append(result)
			    paymentHistoryPagination()
	        }
	  });
}

</script>
<script>
function paymentHistoryPagination(){  
    $('#paymentHistoryPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
 <jsp:include page="../masterFooter.jsp" />	
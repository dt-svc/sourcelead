<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
#scheduleDateValidation {
	padding: 0px 0px 0px 0px !important; margin: 0px; font-size: 11px; color: #f12929; font-weight: 500; text-align: center;
}
</style>
<script type="text/javascript">
var vmsUrl ='${SourceLeadWeb_vmsUrl}';
var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
$(document).ready(function(){
	var noteType = '${noteType}';
	getViewCreditDebitNote();
	if(noteType == "Credit"){
		$("#creditNoteBrudCrum").show();
		$("#debitNoteBrudCrum").hide();
	}else{
		$("#creditNoteBrudCrum").hide();
		$("#debitNoteBrudCrum").show();
	}
	
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
     <div class="pull-right brud-crum" style="display: none" id ="creditNoteBrudCrum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_vmsUrl}/creditNoteList'/>">Credit Note List</a> &raquo; Credit Note View</div>
     <div class="pull-right brud-crum" style="display: none" id ="debitNoteBrudCrum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_vmsUrl}/debitNoteList'/>">Debit Note List</a> &raquo; Debit Note View</div>
     <h2 id="creditDebitNoteDivId" class="pull-left"></h2>
    </section>
    
    
    <div class="content-area content-new clearfix">
      <div class="client_invoice">
        <div class="tabbable positionRelative">
          
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active">
              <a href="#bill" data-toggle="tab" title="Details" aria-expanded="true">
                <h5>Details</h5>
              </a>
            </li>
              <!-- <li class="">
              <a href="#notesList" data-toggle="tab" title="Notes" aria-expanded="false" onclick="invoiceNotesList();">
                <h5>Notes</h5>
              </a>
            </li>
            
             <li class="">
              <a href="#invoiceTimeline" data-toggle="tab" title="Notes" aria-expanded="false" onclick="timelineHistoryFunction('', 'desc', null, null, null);">
                <h5>Timeline</h5>
              </a>
            </li>      -->   
            
          </ul>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active in" id="bill">
              <div id="viewCreditDebitId" class="project_tabs_inn_table"></div>
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
        </div>
      </div>
    </div>
  </section>
  
  
<script type="text/javascript">
function getViewCreditDebitNote(){
	  var creditDebitUUID ='${creditDebitUUID}';
	  var noteType = '${noteType}';
	  var currencyCode = '${SourceLeadWeb_currencyCode}';
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  var viewInvoiceDate = getOrgDateFormat(locationList)
	  $.ajax({
	    	type: "GET",
	        url: vmsUrl+"/getCreditDebitNoteDetails?"+tokenParamName+"="+tokenValue+"&creditDebitUUID="+creditDebitUUID,
	        success: function(response) {
 	        	//var json = JSON.parse(data);
	        	var result = "";
	        	if(response.object !=null){
	        		$("#creditDebitNoteDivId").text(response.object.generatedId);
	        		result = result +' <div class="tab-pane active" id="details">								';
	        		result = result +' <div class="invoiceview_area clearfix">										';
	        		result = result +' <div class="form-horizontal row">											';
					
	        		result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Reference Id :</label>         ';
					result = result +'              <div class="col-md-8">                                               ';
					if(response.object.referenceNumber !=null && response.object.referenceNumber !='' && response.object.referenceNumber !='undefined'){
					result = result +'                <label>'+response.object.referenceNumber+'</label>                     ';
					}else{
					result = result +'                <label>---</label>                     ';
					}
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Credit Note Date :</label>         ';
					result = result +'              <div class="col-md-8">                                               ';
					if(response.object.creditDebitNoteDateStr !=null && response.object.creditDebitNoteDateStr !='' && response.object.creditDebitNoteDateStr !='undefined'){
					result = result +'                <label>'+response.object.creditDebitNoteDateStr+'</label>                     ';
					}else{
					result = result +'                <label>---</label>                     ';
					}
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Billing Address :</label>      ';
					result = result +'              <div class="col-md-8">                                               ';
					if(response.object.billingAddress !=null && response.object.billingAddress !='' && response.object.billingAddress !='undefined'){
					result = result +'                <label>'+response.object.billingAddress+'</label>                      ';
					}else{
					result = result +'                <label>---</label>                      ';
					}
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Shipping Address :</label>     ';
					result = result +'              <div class="col-md-8">                                               ';
					if(response.object.shippingAddress !=null && response.object.shippingAddress !='' && response.object.shippingAddress !='undefined'){
					result = result +'                <label>'+response.object.shippingAddress+'</label>                     ';
					}
					else{
					result = result +'                <label>---</label>                     ';
					}
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Company Name :</label>     ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+response.object.clientOrVendorName+'</label>                  ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Invoices :</label>     ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+response.object.invoiceMultipleIds+'</label>                  ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					if(response.object.approverCheckBox == "Y"){
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Approvers :</label>     ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+response.object.approversMultipleNames+'</label>                  ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					}
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Reason :</label>     ';
					result = result +'              <div class="col-md-8">                                               ';
					if(response.object.reason !=null && response.object.reason !='' && response.object.reason !='undefined'){
						result = result +'                <label>'+response.object.reason+'</label>                  ';

					}else{
						result = result +'                <label>---</label>                  ';
	
					}
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Contact Names :</label>     ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+response.object.contactsMultipleNames+'</label>                  ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Contact Emails :</label>     ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+response.object.contactsMultipleEmails+'</label>                  ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            </div>                                                                 ';
					result = result +'            </div>                                                                 ';
					
					/* ======================== CreditDebit LineItems List start============================ */
					result = result +'	     <div class="clearfix">                                  ';
					result = result +'         <table width="100%" cellpadding="0" cellspacing="0">                  ';
					result = result +'             <thead>                                                           ';
					
					result = result +'                 <tr>                                                          ';
	                result = result +'<th>Date</th>																';
	                result = result +'<th>Description</th>															';
	                result = result +'<th>Associate</th>														';
	                result = result +'<th>Quantity</th>																';
	                result = result +'<th>Price</th>															';
	                result = result +'<th>Tax Type</th>															';
	                result = result +'<th>Tax Amount</th>															';
	                result = result +'<th>Total Amount</th>															';
					result = result +'                 </tr>                                                         ';
					if(response.object.listOfCreditDebitLineItems.length > 0){
						for (var i=0; i<response.object.listOfCreditDebitLineItems.length; i++)
			    		{
							var items = response.object.listOfCreditDebitLineItems[i];
							result = result +'                 <tr>                                                          ';
							
							result = result +'<td>'																			;
							if(items.lineItemDateStr !=null && items.lineItemDateStr !='' && items.lineItemDateStr !='undefined'){
                            result = result +'<div title="'+items.lineItemDateStr+'">'+items.lineItemDateStr+'</div>'							;
							}else{
	                         result = result +'<div>---</div>'							;
							}
                            result = result +'</td>																			';
                            
                            result = result +'<td>'																			;
							if(items.inlineDescription !=null && items.inlineDescription !='' && items.inlineDescription !='undefined'){
                            result = result +'<div title="'+items.inlineDescription+'">'+items.inlineDescription+'</div>'							;
							}else{
	                         result = result +'<div>---</div>'							;
							}
                            result = result +'</td>																			';
                            
                            result = result +'<td>'																			;
							if(items.associateName !=null && items.associateName !='' && items.associateName !='undefined'){
                            result = result +'<div title="'+items.associateName+'">'+items.associateName+'</div>'							;
							}else{
		                    result = result +'<div>---</div>'							;
							}
                            result = result +'</td>																			';
                            
                            result = result +'<td>'																			;
                            result = result +'<div title="'+items.quantity+'">'+items.quantity+'</div>'							;
                            result = result +'</td>																			';
                            
                            result = result +'<td>';
                            result = result +'<div title="'+items.price+'">'+items.price+'</div>';
                            result = result +'</td>';
                            
                            result = result +'<td>';
                            if(items.taxAmount !=null && items.taxAmount !='' && items.taxAmount !='undefined'){
                            result = result +'<div title="'+items.taxType+'">'+items.taxType+'</div>';
                            }else{
                            result = result +'<div>---</div>';	
                            }
                            result = result +'</td>';
                            
                            result = result +'<td>';
                            if(items.taxAmount !=null && items.taxAmount !=''  && items.taxAmount !='undefined'){
                            result = result +'<div title="'+items.taxAmount+'">'+items.taxAmount+'</div>';
                            }else{
                            result = result +'<div>---</div>';		
                            }
                            result = result +'</td>';
                            
                            result = result +'<td>'																			;
                            result = result +'<div title="'+items.totalAmount+'">'+items.totalAmount+'</div>'							;
                            result = result +'</td>																			';
							
							result = result +'                 </tr>                                                          ';
							
			    		}
					}
					
					result = result +'             </thead>                                                          ';
					result = result +'             <tbody>                                                           ';
					
					result = result +'</tbody>																		';
					result = result +'</table>																		';
					result = result +' </div>																		';
					/* ======================== InvoiceItem List End============================ */
	        	}
	        	var discountValue ='';
	        	var discountTotal ='';
	        	var discountType = '';
	        	if(response.object.discountValue !=null && response.object.discountValue !='' && response.object.discountValue !='undefined'){
	        		discountValue = 	response.object.discountValue;
	        	}else{
	        		discountValue ='--';
	        	}
	        	if(response.object.discountTotal !=null && response.object.discountTotal !='' && response.object.discountTotal !='undefined'){
	        		discountTotal = 	response.object.discountTotal;
	        	}else{
	        		discountTotal ='--';
	        	}
	        	if(response.object.discountType !=null && response.object.discountType !='' && response.object.discountType !='undefined'){
	        		discountType = 	response.object.discountType;
	        	}else{
	        		discountType =' ';
	        	}
	        	result = result +'<div class="row">                                                                                                   ';
                result = result +'<div class="col-md-7 col-md-offset-5">                                                                              ';
                result = result +'  <div class="form-horizontal billsubTotal">                                                                        ';
                result = result +'    <div class="form-group">                                                                                        ';
                result = result +'      <label class="control-label col-md-7"><strong>Sub Total</strong></label>   ';
                result = result +'      <div class="col-md-5 text-right">'+response.object.subTotal+'</div>                                                 ';
                result = result +'    </div>                                                                                                          ';
                result = result +'    <!-- Discount UI -->                                                                                            ';
                result = result +'    <div class="form-group">                                                                                        ';
                result = result +'      <div class="col-md-7 select-field">                                                                           ';
                result = result +'        <div class="row">                                                                                           ';
                result = result +'          <div class="col-md-7 paddingRight">                                                                       ';
                result = result +'            <label><strong>Discount</strong> '+discountType+'</label>                                                              ';
                result = result +'          </div>                                                                                                    ';
                result = result +'          <div class="col-md-5 paddingLeft">                                                                        ';
                result = result +'            <label>'+discountValue+'</label>                                                         ';
                result = result +'          </div>                                                                                                    ';
                result = result +'        </div>                                                                                                      ';
                result = result +'      </div>                                                                                                        ';
                
                result = result +'      <div class="col-md-5 text-right">                                                                             ';
                result = result +'        <div class="row">';
                result = result +'      <label class="control-label col-md-7"><strong>Discount Total</strong></label>                                       ';
                result = result +'      <div class="col-md-5 text-right">'+discountTotal+'</div>                                                 ';
                result = result +'      </div>                                                                                                        ';
                result = result +'    </div>                                                                                                          ';
                result = result +'    </div>';
                result = result +'    <div class="addtax_table">                                                                                      ';
                result = result +'      <table class="table-bordered table-striped table-responsive" width="100%" cellspacing="0" cellpadding="0">    ';
                result = result +'        <tbody>                                                                                                     ';
                result = result +'          <tr> </tr>                                                                                                ';
                result = result +'        </tbody>                                                                                                    ';
                result = result +'      </table>                                                                                                      ';
                result = result +'    </div>                                                                                                          ';
                result = result +'    <div class="form-group mt">                                                                                     ';
                
                result = result +'  <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
                result = result +'      <label class="col-md-6 text-right"><strong>Total</strong></label> ';
                result = result +'      <div class="col-md-6 text-right paddingLeft"><span class="pull-left">'+response.object.loginCurrencyCode+'</span><div class="pull-right">'+response.object.total+'</div></div>';
                if(response.object.vendorCurrencyCode != null && response.object.vendorCurrencyCode != '' && response.object.vendorCurrencyCode != 'undefined'){
                result = result +'  </div>';
                
                result = result +'  <div class="clearfix">';
                result = result +'  <div class="clearfix mt col-md-offset-2 col-md-10" style="padding-right:0;">';
                
                result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion From</strong></label><div class="paddingLeft col-md-6"><span>'+currencyCode+'</span><div class="pull-right"><input class="ReadTextFli" readonly="" type="text">'+response.object.total+'</div></div></div>';
                result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Conversion To</strong></label><div class="paddingLeft col-md-6"><span>'+response.object.vendorCurrencyCode+'</span><div class="pull-right"><input  class="ReadTextFli" readonly="" type="text">'+response.object.exchangeCurrencyRate+'</div></div></div>';
                result = result +'  <div class="clearfix"><label class="col-md-6 text-right"><strong>Exchange Rate</strong></label><div class="paddingLeft col-md-6"><span>'+currencyCode+' to '+response.object.vendorCurrencyCode+' </span><div class="pull-right"><input  class="ReadTextFli ReadTextFli90" readonly="" type="text">'+response.object.afterExchangeGrandTotal+'</div></div></div>';
                
                result = result +'  </div>';
                result = result +'  </div>'; 	
				}
                result = result +'    </div>                                                                                                          ';
                result = result +'  </div>                                                                                                            ';
                result = result +'</div>                                                                                                              ';
                result = result +'				</div>                                                                                                ';
	        	
	        	
	        	result = result +' </div>																		';
				
				$("#viewCreditDebitId").empty();  
			    $("#viewCreditDebitId").append(result)
	        },
	        
	    });
}

</script>



<jsp:include page="../masterFooter.jsp" />	
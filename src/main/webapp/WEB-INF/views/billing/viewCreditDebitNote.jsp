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
$(document).ready(function(){
	getViewCreditDebitNote();
	
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
     <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/invoiceList'/>">Receivable List</a> » Invoice View</div>
      
      <div class="" id="creditDivIdHidden" style="display:none">
          <h2>Invoice Id: <span><input class="ReadTextFliA" id="invoiceIdDiv" readonly="readonly"></span></h2>
<!--      =================== Tags related html code written by Laxman Start ============================ -->
			  <div class="newTagsWrap">
				    <ul class="clearfix tagsWrap tagsWrapBor">
					<select multiple class="chosen-select form-control" name="invoiceTagName" id="invoiceTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
					</select>
					</ul>
					<div class="clearfix"></div>
			  </div>
		   <div class="success_pop tagStyles" id="addInvoiceTagAlert">
				<i class="fa fa-check-circle"></i>
		   		<span></span>
		   </div>
		   <div class="remove_pop tagStyles" id="deleteInvoiceTagAlert">
				<i class="fa fa-times"></i>
		   		<span></span>
		   </div>
<!--      =================== Tags related html code written by Laxman Ends ============================ -->
		  
      </div>
      
      <div class="" id="debitDivIdHidden" style="display:none">
          <h2>Bill Id: <span><input class="ReadTextFliA" id="billIdDiv" readonly="readonly"></span></h2>
 <!--      =================== Tags related html code written by Laxman Start ============================ -->
          <div class="cearfix">
			  <div class="newTagsWrap">
				    <ul class="clearfix tagsWrap tagsWrapBor">
					<select multiple class="chosen-select form-control" name="billstagName" id="billingTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
					</select>
					</ul>
					<div class="clearfix"></div>
			  </div>
		 </div>
		   <div class="success_pop tagStyles" id="addBillingTagAlert">
				<i class="fa fa-check-circle"></i>
		   		<span></span>
		   </div>
		   <div class="remove_pop tagStyles" id="deleteBillingTagAlert">
				<i class="fa fa-times"></i>
		   		<span></span>
		   </div>
<!--      =================== Tags related html code written by Laxman Ends ============================ -->
      </div>
      
     
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
	        url: sourceleadUrl+"/getCreditDebitNoteDetails?"+tokenParamName+"="+tokenValue+"&creditDebitUUID="+creditDebitUUID,
	        success: function(response) {
 	        	//var json = JSON.parse(data);
	        	var result = "";
	        	if(response.object !=null){
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
	                result = result +'<th>Amount</th>															';
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
                            
                            result = result +'<td>'																			;
                            result = result +'<div title="'+items.price+'">'+items.price+'</div>'							;
                            result = result +'</td>																			';
                            
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
	        		subTotal = 	response.object.discountValue;
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
                result = result +'      <label class="control-label col-md-7"><strong>Subtotal</strong></label>                                       ';
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
                result = result +'      <label class="control-label col-md-7"><strong>Discount Total</strong></label>                                       ';
                result = result +'      <div class="col-md-5 text-right">'+discountTotal+'</div>                                                 ';
                result = result +'      </div>                                                                                                        ';
                result = result +'    </div>                                                                                                          ';
                result = result +'    <div class="addtax_table">                                                                                      ';
                result = result +'      <table class="table-bordered table-striped table-responsive" width="100%" cellspacing="0" cellpadding="0">    ';
                result = result +'        <tbody>                                                                                                     ';
                result = result +'          <tr> </tr>                                                                                                ';
                result = result +'        </tbody>                                                                                                    ';
                result = result +'      </table>                                                                                                      ';
                result = result +'    </div>                                                                                                          ';
                result = result +'    <div class="form-group mt">                                                                                     ';
                result = result +'      <label class="col-md-7 text-right"><strong>Total</strong></label>                                             ';
                result = result +'      <div class="col-md-5 text-right">'+response.object.total+'</div>                                ';
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
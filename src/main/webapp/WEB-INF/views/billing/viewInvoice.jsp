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
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
          <h2>Inovice Id: <span>002345</span></h2>
      </div>
      <div class="pull-right brud-crum">Home » Receivables</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="client_invoice">
        <div class="tabbable positionRelative">
          
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active">
              <a href="#bill" data-toggle="tab" title="Bill" aria-expanded="true">
                <h5>Bill</h5>
              </a>
            </li>
            <li class="">
              <a href="#paymentHistory" data-toggle="tab" title="Payment History" aria-expanded="false">
                <h5>Payment History</h5>
              </a>
            </li>
            
          </ul>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active in" id="bill">
              <div id="viewInvoiceId"></div>
     
              
      <!-- View part total area -->
      <div class="row"> 
    
          <div class="col-md-5">
              
              <div class="col-md-12 mt">
                  <label><strong>Message</strong></label>
                  <div class="clearfix">
                      <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                  </div>
              </div>
              <div class="col-md-12 mt">
                <label><strong>Attachment:</strong></label>
                  <div class="clearfix attachfile_invoice">
                      <p class="pull-left">fileattachment.xls</p>
                      <span class="pull-right"><i class="fa fa-download"></i></span>
                  </div>
                   
              </div>              
          </div>
          
        <div class="col-md-7">
            <div class="form-horizontal billsubTotal">
              <div class="form-group">
                <label class="control-label col-md-7"><strong>Subtotal</strong></label>
                <div class="col-md-5 text-right"><span>120000.00</span></div>
              </div>
              <!-- Discount UI -->
              <div class="form-group">
                <div class="col-md-7 select-field">
                  <div class="row">
                    <div class="col-md-7 paddingRight">
                        <label><strong>Discount %</strong></label>
                    </div>
                    <div class="col-md-5 paddingLeft">
                      <label>5%</label>
                    </div>
                  </div>
                </div>
                <div class="col-md-5 text-right">
                  <label>210,000.00</label>
                </div>
              </div>
                <div class="addtax_table">
                    <table class="table-bordered table-striped table-responsive" width="100%" cellspacing="0" cellpadding="0">
                        <tbody>
                            <tr>
                                <td>
                                   <div class="select-field tblminwidth-75">
                                       <div class="tblminwidth-50">
                                        Service Tax
                                    </div>
                                   </div>
                                </td>
                                <td>
                                 <div class="btn-group tblminwidth-75">
                                     <label>Flat</label>
                                </div>
                                </td>
                                <td>
                                    <div class="tblminwidth-75">
                                        <label>Flat Discount</label>
                                    </div>
                                </td>
                                <td>
                                    <div class="tblminwidth-75">
                                         <label>Total Value</label>
                                    </div>
                                </td>
                            </tr>
                            <tr class="hide-row">
                                <td colspan="2">
                                </td>
                                <td>
                                <strong>Total: </strong>
                                </td>
                                <td colspan="2" align="right"><strong>120000000.00</strong></td>
                            </tr>
                        </tbody>
                    </table>
            </div>
                <div class="form-group mt">
                <label class="col-md-7 text-right"><strong>Total</strong></label>
                    <div class="col-md-5 text-right"><span><strong>120000.00</strong></span></div>
              </div>
              </div>
          </div>
            </div>    
                <!-- View part total area -->
            
              </div>
          
            <div class="tab-pane fade positionRelative" id="paymentHistory">
                <div class="invoiceview_area clearfix candidate_wrap_table">
                  
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <thead>
                            <tr>
                                <th>
                                   Date 
                                </th>
                                <th>
                                   Payment Method 
                                </th>
                                <th>
                                   Reference No 
                                </th>
                                <th>
                                   Amount Received 
                                </th>
                                <th>
                                   Attachment 
                                </th>
                                <th>
                                   Memo 
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                        <tr>
                                <td>
                                    <div class="tbldata-dotdot-80">
                                    22/05/2017 
                                    </div>
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-50">
                                        Cash
                                    </div>
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-50">
                                        2020524
                                    </div>
                                </td>
                                <td>
                                     <div class="tbldata-dotdot-50">
                                        100250.00
                                    </div>
                                </td>
                            <td>
                                  <a class="tbldata-dotdot-vsm download_attachment" title="Download Attachment">
                                  <p class="pull-left">LoremIpsumhasbeentheindustry.pdf</p>
                                  <span class="pull-right">
                                      <i class="fa fa-download"></i>
                                      </span>
                  </a>  
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-me">
                                        <a class="memo_text">
                                            <p class="pull-left" title="LoremIpsumhasbeentheindustry">LoremIpsumhasbeentheindustry</p>
                                           <i class="fa fa-comments pull-right" title="Memo"></i>
                                        </a>
                                    </div>
                                </td>
                                
                            </tr>
                            <tr>
                                <td>
                                    <div class="tbldata-dotdot-80">
                                    22/05/2017 
                                    </div>
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-50">
                                        Cash
                                    </div>
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-50">
                                        2020524
                                    </div>
                                </td>
                                <td>
                                     <div class="tbldata-dotdot-50">
                                        100250.00
                                    </div>
                                </td>
                            <td>
                                  <a class="tbldata-dotdot-vsm download_attachment" title="Download Attachment">
                                  <p class="pull-left">LoremIpsumhasbeentheindustry.pdf</p>
                                  <span class="pull-right">
                                      <i class="fa fa-download"></i>
                                      </span>
                  </a>  
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-me">
                                        <a class="memo_text">
                                            <p class="pull-left" title="LoremIpsumhasbeentheindustry">LoremIpsumhasbeentheindustry</p>
                                           <i class="fa fa-comments pull-right" title="Memo"></i>
                                        </a>
                                    </div>
                                </td>
                                
                            </tr>
                            <tr>
                                <td>
                                    <div class="tbldata-dotdot-80">
                                    22/05/2017 
                                    </div>
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-50">
                                        Cash
                                    </div>
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-50">
                                        2020524
                                    </div>
                                </td>
                                <td>
                                     <div class="tbldata-dotdot-50">
                                        100250.00
                                    </div>
                                </td>
                            <td>
                                  <a class="tbldata-dotdot-vsm download_attachment" title="Download Attachment">
                                  <p class="pull-left">LoremIpsumhasbeentheindustry.pdf</p>
                                  <span class="pull-right">
                                      <i class="fa fa-download"></i>
                                      </span>
                  </a>  
                                </td>
                                <td>
                                    <div class="tbldata-dotdot-me">
                                        <a class="memo_text">
                                            <p class="pull-left" title="LoremIpsumhasbeentheindustry">LoremIpsumhasbeentheindustry</p>
                                           <i class="fa fa-comments pull-right" title="Memo"></i>
                                        </a>
                                    </div>
                                </td>
                                
                            </tr>
                        </tbody>
                    </table>
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
	  var invoiceId ='${invoiceId}';
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getViewInvoice?${_csrf.parameterName}=${_csrf.token}&invoiceId="+invoiceId,
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var result = "";
	        	for (var i=0; i<json.length; i++)
	    		{  
	        		var report = json[i];
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
					result = result +'		<div class="invoiceview_area clearfix">                                      ';
					result = result +'          <div class="form-horizontal row">                                        ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Client </label>                ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                  <label><strong>'+report.clientName+'</strong></label>  ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Contact Name</label>         ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+report.clientContactName+'</label>';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Contact Email</label>           ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+clientContactEmail+'</label>                         ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Invoice Id</label>             ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+report.invoiceId+'</label>                                          ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Invoice Date</label>           ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+invoiceDateStr+'</label>                                          ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Payment Term</label>           ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+paymentFrequency+'</label>                                             ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Due Date</label>               ';
					result = result +'              <div class="col-md-8">                                               ';
					result = result +'                <label>'+dueDateStr+'</label>                                          ';
					result = result +'              </div>                                                               ';
					result = result +'            </div>                                                                 ';
					result = result +'            <div class="col-md-6">                                                 ';
					result = result +'              <label class="col-md-4 control-label">Invoice Address</label>        ';
					result = result +'              <div class="col-md-8 addressInvoice">                                ';
					result = result +'                <p>'+billingAddress+'</p>';
					result = result +'              </div>';
					result = result +'            </div>';
					result = result +'          </div>';
					result = result +'      </div>';
					
					
					/* ======================== InvoiceItem List start============================ */
					result = result +'	     <div class="invoiceGridview clearfix">                                  ';
					result = result +'         <table width="100%" cellpadding="0" cellspacing="0">                  ';
					result = result +'             <thead>                                                           ';
					result = result +'                 <tr>                                                          ';
					result = result +'                     <th>id</th>                                               ';
					result = result +'                     <th>Candidate</th>                                        ';
					result = result +'                     <th>SOW/POW</th>                                          ';
					result = result +'                     <th>Date</th>                                             ';
					result = result +'                     <th>Rate</th>                                             ';
					result = result +'                     <th>Total Hours</th>                                      ';
					result = result +'                     <th>Total Amount</th>                                     ';
					result = result +'                 </tr>                                                         ';
					result = result +'             </thead>                                                          ';
					result = result +'             <tbody>                                                           ';
					for (var j=0; j<report.invoiceLineItemsList.length; j++)
		    		{
						var items = report.invoiceLineItemsList[i]; 
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
							startDateStr = items.startDateStr;
						}else{
							startDateStr = "--";
						}
						if(items.endDtaeStr != null && items.endDtaeStr != '' && items.endDtaeStr != 'undefined'){
							endDtaeStr = items.endDtaeStr;
						}else{
							endDtaeStr = "--";
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
						
						result = result +'                 <tr>                                                          ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_80">0022557788</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot-sm" title="'+candidateName+'">'+candidateName+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_80" tittle="'+sowName+'">'+sowName+'</div>       ';
						result = result +'                     </td>                                                     ';
						result = result +'                     <td>                                                      ';
						result = result +'                         <div class="tbldata-dotdot_80">'+startDateStr+'</div>       ';
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
						
						/* ======================== InvoiceItem List End============================ */
	    		}
				
				result = result +'</tbody>';
				result = result +'</table>';
				result = result +' </div>';
					
	    		}
				$("#viewInvoiceId").empty();  
			    $("#viewInvoiceId").append(result)
	        },
	        
	    });
}

</script>
 <jsp:include page="../masterFooter.jsp" />	
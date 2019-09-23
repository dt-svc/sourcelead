 <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Purchase Order</h2>
      </div>  
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/PurchaseOrderList'/>">Purchase Order List</a>  &raquo;Purchase Order</div>
    </section>
    <div class="clearfix backgroundWhite">
		<div class="col-md-12 mt mb">
		<div class="invoiceview_area clearfix">
                <div class="form-horizontal row">
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Order No :</label>
                    <div class="col-md-8">
                      <label><strong>${marketplacePurchaseOrder.orderNo}</strong></label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Order Date :</label>
                    <div class="col-md-8">
                      <label>${marketplacePurchaseOrder.orderDate}</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Order By :</label>
                    <div class="col-md-8">
                      <label>${marketplacePurchaseOrder.companyName}</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Total Resumes :</label>
                    <div class="col-md-8">
                      <label>${marketplacePurchaseOrder.totalresume != null ? marketplacePurchaseOrder.totalresume : "---"}</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Total Price :</label>
                    <div class="col-md-8">
                      <label>${marketplacePurchaseOrder.totalPrice != null ? marketplacePurchaseOrder.totalPrice : "---"}</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Service Fee :</label>
                    <div class="col-md-8">
                      <label>${marketplacePurchaseOrder.serviceFee != null ? marketplacePurchaseOrder.serviceFee : "---"}</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Discount :</label>
                    <div class="col-md-8 addressInvoice">
                      <label>${marketplacePurchaseOrder.discount != null ? marketplacePurchaseOrder.discount : "---"}</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Tax :</label>
                    <div class="col-md-8 addressInvoice">
                      <label>${marketplacePurchaseOrder.tax  != null ? marketplacePurchaseOrder.tax : "---" }</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Grand Total :</label>
                    <div class="col-md-8">
						<label class="text-primary textBold">${marketplacePurchaseOrder.grandTotal  != null ? marketplacePurchaseOrder.grandTotal : "---"}</label>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label class="col-md-4 control-label">Payment Status :</label>
                    <div class="col-md-8">
						<label class="text-success">${marketplacePurchaseOrder.paymentStatus}</label>
                      <p></p>
                    </div>
                  </div>
                </div>
              </div>
		<div class="clearfix">
		<div class="invoiceGridview clearfix">
                <table width="100%" cellspacing="0" cellpadding="0">
                  <thead>
                    <tr>
                      <th>Details</th>
                      <th>Account</th>
                      <th>Quantity</th>
                      <th>Rate</th>
                      <th>Tax</th>
                      <th>Amount</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td><div class="tbldata-dotdot">${marketplacePurchaseOrder.candidateName != null ? marketplacePurchaseOrder.candidateName : "---"}</div></td>
                      <td><div class="tblminwidth-100">---</div></td>
                      <td><div class="tblminwidth-100">---</div></td>
                      <td><div class="tblminwidth-100">---</div></td>
                      <td><div class="tblminwidth-100">---</div></td>
                      <td><div class="tblminwidth-100">---</div></td>
                    </tr>
                  </tbody>
                </table>
              </div>	
			
		</div>
		</div>
    </div>
  </section>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
<script type="text/javascript">
	$(function () {
		$('#datetimepicker3').datetimepicker({
			format: 'LT'
		});
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
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#savevendor").click(function(){
			$('#myform').validate({
			rules:{
				things:{
					required:true
				}
			}
			});
		});
	});
	$(function () {

	});
</script> 
<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
function runPayrollregister() {
	$("#payrollRegister").toggle();
}
	// Close Advanced Search
	function closePop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").hide();
	}
	function openPop(){
	$(".advancedsearch-mask").show();
	$("div.cand-advsrch-popup").toggle();
	}
//Date Picker
$( function(){
	$("#frmDate").datepicker();
	$("#toDate").datepicker();
 });
</script>

<jsp:include page="../masterFooter.jsp" /> 
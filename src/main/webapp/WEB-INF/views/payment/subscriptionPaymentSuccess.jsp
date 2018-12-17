<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper">
    <section class="content-header clearfix">
        <div class="pull-left">
            <h2>Payment Success</h2>
        </div>
        <div class="pull-right brud-crum">
				<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
					href="<c:url value='/app/orgLicenseSubscriptionList'/>"> License Subscription List</a> &raquo; Payment Success
			</div>
    </section>
    <div class="scontent-new clearfix">
        <div class="clearfix">
            <section class="payment-success">

                <div class="paymentsuccess-header">
                    <h4 class="mb5"><i class="fa fa-check-circle fa-2x mr"></i><span>Payment Successful!</span></h4>
                    <p><strong>Thank you ! Your payment of $${amount} has been received.</strong></p>
                    <div class="clearfix">
                        <ul class="list-unstyled list-inline">
                            <li class="mr5"><label class="text-muted paddingRight">Order ID :</label> <span>${orderId}</span></li><span class="text-muted">|</span>
                            <li><label class="text-muted paddingRight">Transaction ID :</label><span>${transactionId}</span></li>
                        </ul>
                    </div>
                </div>
                <div class="paymentsuccess-details text-center mt clearfix">
                    <h4>Payment Details</h4>
                    <div class="col-md-8 col-md-offset-2 clearfix mt">
                        <table class="table">
                            <thead>
                            <th>Type</th><th>Description</th><th>Amount</th>
                            </thead>
                            <tbody>
                            <td class="text-left">${subscriptionType}</td><td class="text-left">License Subscription</td><td class="text-left">$ ${amount}</td>
                            </tbody>
                            <tfoot>
                            <td>&nbsp;</td>
                            <td class="text-left">Total Amount</td>
                            <td class="text-left">$${amount}</td>
                            </tfoot>
                        </table>
                    </div>
                    <div class="clearfix"></div>
                    <p class="text-muted clearfix">Invoice sent to your Registered Email.</p>
                </div>
                <div class="clearfix text-center mt25">
                    <a href="<c:url value='${appPath}/dashboard'/>" class="btn btn-primary"><i class="fa fa-angle-double-left paddingRight"></i>Take me home<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>
                    </span></a>
                </div>
            </section>
            <!-- Content Area Ends--> 
        </div>
    </div>

</section>
<jsp:include page="../masterFooter.jsp" />
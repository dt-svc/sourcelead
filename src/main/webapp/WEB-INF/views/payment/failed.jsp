<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper">
    <section class="content-header clearfix">
        <div class="pull-left">
            <h2>Payment Failed</h2>
        </div>
        <div class="pull-right brud-crum">
				<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a
					href="<c:url value='/app/payablesList'/>"> Bills List</a>
				&raquo; Payment Failed
			</div>
			
    </section>
    <div class="scontent-new clearfix">
        <div class="clearfix">
            <section class="payment-success">

                <div class="paymentfailed-header text-center">
                    <h4 class="mb5"><i class="fa fa-times-circle-o fa-2x mr"></i><span>Your transaction could not be processed!</span></h4>
                    <div class="clearfix">
                        <ul class="list-unstyled clearfix text-left col-md-7 col-md-offset-3 mt5">
                            <li class="clearfix mb5"><label class="col-md-3">Reason:</label><div class="col-md-9">The system is currently unavailable.</div></li>
                            <li class="clearfix"><label class="col-md-3">Suggestions:</label><div class="col-md-9">Please try agan.<br>If the problem persists, Please Contact:<br>
                                    <a href="mailto:support@sourcelead.com" class="text-primary mt5">support@sourcelead.com</a></div>
                            </li>
                        </ul>
                    </div>
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
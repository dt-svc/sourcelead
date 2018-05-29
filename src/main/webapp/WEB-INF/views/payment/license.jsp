<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper clearfix">
    <section class="content-header clearfix">
        <div class="pull-left">
            <h2>Product License</h2>
        </div>
        <div class="pull-right brud-crum">Home &raquo; Product License Upgrade</div>
    </section>
    <!-- hiring Process  -->
    <div class="clearfix dashboard-widget col-md-12">
        <div class="clearfix"></div>

        <div class="clearfix">
            <div class="clearfix trial-css">
                <h4 class="text-muted">
                    Trial Version: Features </h4>
                <ul class="list-unstyled clearfix mt mb0 list-inline">
                    <li><i class="fa fa-check text-success paddingRight"></i>5GB Data</li>
                    <li><i class="fa fa-check text-success paddingRight"></i>CRM</li>
                    <li><i class="fa fa-check text-success paddingRight"></i>VMS</li>
                    <li><i class="fa fa-check text-success paddingRight"></i>ATS</li>
                    <li><i class="fa fa-check text-success paddingRight"></i>PMO</li>
                </ul>
                <div class="clearfix"></div>
                <div class="clearfix col-md-12 padding_left padding_right mt">
                    <button type="button" class="btn btn-success mr">Free Trial Expires in 60 Days</button>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-3">
                <div class="clearfix product-features">
                    <h4 class="text-center text-muted">Features</h4>
                    <ul class="clearfix list-unstyled">
                        <li>Data</li>
                        <li>CRM</li>
                        <li>VMS</li>
                        <li>ATS</li>
                        <li>PMO</li>
                        <li>Bulk Resume Parsing</li>
                        <li>Email Campaign</li>
                        <li>Payroll</li>
                        <li>Jobs Integration</li>
                        <li>Resume Harvesting</li>
                    </ul>
                </div>
            </div>
            <div class="col-md-3">
                <div class="clearfix product-features">
                    <h4 class="text-center text-info">Basic</h4>
                    <ul class="clearfix list-unstyled text-center">
                        <li>Data-10GB</li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-muted"></i></li>
                        <li><i class="fa fa-check text-muted"></i></li>
                        <li><i class="fa fa-check text-muted"></i></li>
                        <li><i class="fa fa-check text-muted"></i></li>
                        <li><strong>Price : <span class="paddingLeft">100$</span></strong></li>
                        <li><a href="javascript:void(0)" class="btn btn-success btn-block btn-sm" onclick='initPayment(1)'>Upgrade</a></li>
                    </ul>
                </div>
            </div>      	
            <div class="col-md-3">
                <div class="clearfix product-features">
                    <h4 class="text-center text-success">Advanced</h4>
                    <ul class="clearfix list-unstyled text-center">
                        <li>Data-20GB</li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-muted"></i></li>
                        <li><i class="fa fa-check text-muted"></i></li>
                        <li><strong>Price : <span class="paddingLeft">200$</span></strong></li>
                        <li><a href="javascript:void(0)" class="btn btn-success btn-block btn-sm" onclick="initPayment(2)">Upgrade</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-md-3">
                <div class="clearfix product-features">
                    <h4 class="text-center text-danger">Pro</h4>
                    <ul class="clearfix list-unstyled text-center">
                        <li>Data-50GB</li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><i class="fa fa-check text-success"></i></li>
                        <li><strong>Price : <span class="paddingLeft">500$</span></strong></li>
                        <li><a href="javascript:void(0)" class="btn btn-success btn-block btn-sm" onclick="initPayment(3)">Upgrade</a></li>
                    </ul>
                </div>
            </div>
        </div>

    </div>

</section>

<script type="text/javascript">
    function initPayment(a) {
        $.post("/app/init-payment", {type: 'license', model: a}, function (data) {
            if (data) {
                location.href = data;
            }
        });
    }
</script>
<jsp:include page="../masterFooter.jsp" />
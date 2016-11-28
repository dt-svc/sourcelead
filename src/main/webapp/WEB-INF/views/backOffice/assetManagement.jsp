<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>


 <section class="content-wraper">
    <div class="content-dashboard clearfix">
        <div class="row">
          <div class="col-lg-12">
            <div class="content-area mt clearfix">
              <div class="blank-pages-tech">
                <p class="text-center text-danger permissondenied mb">
                    <img src="resources/images/under-construction.jpg" >
                </p>
                <p class="big-txt mt">UNDER CONSTRUCTION</p>
                <p class="positionRelative move-btn btn-group mt">
                <i class="fa fa-home btn btn-primary"></i>
                <a href="<c:url value='/dashboard'/>" type="button" class="btn btn-primary">Take me home<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>
                </span></a>
                </p>
              </div>
            </div>
          </div>
        </div>
    </div>
  </section>

<jsp:include page="../masterFooter.jsp" />
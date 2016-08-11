<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>


<section class="content-wraper">
  <div class="content-dashboard clearfix">
   <div id="loading"></div>
    <section class="content-header clearfix">
         <c:if test="${not empty msg}">
	        <div class="success_pop" id="selector">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Jobs List</h2>
      </div>
      
    </section>
      
      </div>
      </div>
  </section>
  
<jsp:include page="../masterFooter.jsp" />
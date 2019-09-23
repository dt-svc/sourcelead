<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>My Expenses</h2>
    </div>
    </section>
    
    
    <li> <a href="<c:url value='${appPath}/addExpense'/>"><i class="fa fa-plus"></i>Add Expenses</a></li>
    <li> <a href="<c:url value='${appPath}/expenses'/>"><i class="fa fa-plus"></i>Expenses</a></li>
 </section>
 
<jsp:include page="../masterFooter.jsp" />
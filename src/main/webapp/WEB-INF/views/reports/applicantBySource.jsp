<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<script type="text/javascript">
$(document).ready(function(){
	getApplicantBySourceChart();
});
</script>


<jsp:include page="../masterFooter.jsp" />



<script type="text/javascript">
function getApplicantBySourceChart(){
	
  $.ajax({ 
	   	type: "GET",
	   	url: sourceleadUrl+"/getApplicantBySourceDetails?${_csrf.parameterName}=${_csrf.token}",
	    success: function(data) {
	    	
	    }
	 });
}
</script>
<jsp:include page="../masterHeader.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script>
		$(document).ready(function() {
			var rateStatus = '${param.rateStatus}';
			alert("rateStatus"+rateStatus);
			if(rateStatus == 'Accepted'){           
					$("#accept").removeAttr("style");
			}
			if(rateStatus == 'Rejected'){
				$("#reject").removeAttr("style");		
			}
			if(rateStatus == 'Negotiate'){
				$("#negotiation").removeAttr("style");		
			}
		});
</script>
 

<!-- Content Area -->
<section class="content-wraper">
		  <section class="content-header clearfix">
		     <div class="pull-left">
		      <h2>Candidate Rate</h2>
		    </div>
		  </section>
		  <div class="content-area content-new clearfix">
		  		<div id="accept" style="display:none">
		  		        your rate is accepted
		  		</div>
		  		<div id="reject" style="display:none">
		  		    your rate is rejected
		  		</div>
		  		<div id="negotiation" style="display:none">
		  		   	 your rate is negotiate.......  
		  		</div>
		  </div>
</section>

<jsp:include page="../masterFooter.jsp" />

  <!--   HTML   -->	  
<jsp:include page="../staticHeader.jsp" />   
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="staticBan blogBan">
	<div class="staticBanInn"><h4>Release Note</h4><div class="clear"></div></div>
</section>  

<section class="blogNewWrap releaseWrapOut">
    <section class="releaseWrap">
	    <c:forEach var="note" items="${allReleaseNote}">
		    <a  href="/blog/viewPublicReleaseNote?releaseUUID=${note.releaseUUID}" class="cursor"><div class="relCta">
	            <h3>${note.releaseName} <span> - </span> ${note.releaseDateStr}</h3>
	            <p>${note.shortDescrption}</p>
	        </div></a>
	    </c:forEach>
                                                
    </section>
</section>


<jsp:include page="../staticFooter.jsp" />
 <script type="text/javascript">
window.onload = function() {
	$("#releaseNoteID").addClass("slactive");
}
</script>
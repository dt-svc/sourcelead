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
        <div class="relCta relCtaView">
            <h3>${allReleaseNote.releaseName}  -  ${releaseDateStr}  </h3> 
            <%-- <a href="/downloadReleaseNote?releaseUUID=${allReleaseNote.releaseUUID}" class="cursor">Download</a> --%>
            <p>${allReleaseNote.releaseDescrption}</p>
        </div>                                    
    </section>



<jsp:include page="../staticFooter.jsp" />
<script type="text/javascript">
window.onload = function() {
	$("#releaseNoteID").addClass("slactive");
}
</script>
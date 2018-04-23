  <!--   HTML   -->	  
<jsp:include page="../staticHeader.jsp" />   
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="staticBan blogBan">
	<div class="staticBanInn"><h4>SourceLead blogs</h4><div class="clear"></div></div>
</section>  

<section class="blogNewWrap">
	<section class="blogNewInn">
		     <c:forEach var="all" items="${allBlogs}">
		       <article>
				<a href="/blog/${all.href}.html"><span><img src="${all.imgFileUrl}" alt="" class="img-responsive"></span>
				 
				 <h2>${all.blogTitle}</h2>
					<div class="clearfix blogNewInn-content">
						<p>${all.shortDescription}</p>
					</div>
					<c:if test="${!empty all.shortDescription}">		
						<b>Read more...</b>	
					</c:if>	
				</a>	
				</article>
			</c:forEach> 
			
			<c:if test="${empty allBlogs}">
			<div class="dataTables_empty"> No Blogs Found</div>
			</c:if>
		 <div class="clear"></div>
	</section>
</section>

 <jsp:include page="../staticFooter.jsp" />
<script type="text/javascript">
window.onload = function() {
	$("#blogID").addClass("slactive");
}
</script>
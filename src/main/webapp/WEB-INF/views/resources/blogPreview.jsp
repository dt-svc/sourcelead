<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<section class="content-wraper">
 <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Blog Preview</h2>
    </div>
   <div class="pull-right brud-crum"><a href="<c:url value='/app/blogs'/>">Blogs</a> &raquo;Blog Preview</div>
  </section>
 
	<section class="testiWrap dashboard-widget clearfix">
		<section class="blogWrap">

				<h4 class="blHd">${sourceleadBlog.blogTitle}</h4>
				
				<c:if test="${!empty sourceleadBlog.blogImage}">
				<div class="blogImg mb">
					<img src="${sourceleadBlog.blogImage}" alt="" class="">
				</div>
                </c:if>
                
                <c:if test="${empty sourceleadBlog.blogImage}">
				<div class="blogImg mb">
					<img src="#" alt="" class="">
				</div>
                </c:if>
                
				<div class="clearfix"></div>
                <c:if test="${!empty sourceleadBlog.description}">
				<div class="blogpreviewContent">
				${sourceleadBlog.description}
				</div>
				</c:if>
				
			 <c:if test="${empty sourceleadBlog.description}">
				<div class="blogpreviewContent">
				
				</div>
				</c:if>
				
		</section>
	</section>

</section>

<!-- Script area -->
<jsp:include page="../masterFooter.jsp" />
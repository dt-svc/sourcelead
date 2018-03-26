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
 
	<section class="testiWrap mt25 clearfix">
		<section class="blogWrap">
			<div class="blogInn">

				<h4 class="margin-top0">${sourceleadBlog.blogTitle}</h4>
				<div class="blogImg mb">
					<img src="${sourceleadBlog.blogImage}" alt="" class="img-responsive">
				</div>

				<div class="clearfix"></div>

				<div class="blogpreviewContent">
				${sourceleadBlog.description}
				</div>
				
			</div>


		</section>
	</section>

</section>

<!-- Script area -->
<jsp:include page="../masterFooter.jsp" />
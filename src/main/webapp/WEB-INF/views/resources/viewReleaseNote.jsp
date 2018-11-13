<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="content-wraper">
 <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Release Note Preview</h2>
    </div>
   <div class="pull-right brud-crum"><a href="<c:url value='/app/releaseNote'/>">Release Note</a> &raquo;  Release Note Preview</div>
  </section>
  
  <div class="project_wrap">
         <div class="releViewWrap">
            <h2>${releaseNote.releaseName}  <span> (${releaseNote.releaseDateStr})</span> </h2>
               <c:if test="${!empty releaseNote.releaseDescrption}">
					<div class="blogpreviewContent"> ${releaseNote.releaseDescrption} </div>
				</c:if>
				
			 <c:if test="${empty releaseNote.releaseDescrption}">
				<div class="blogpreviewContent dataTables_empty"></div>
			</c:if>           
		</div>
    </div>
	
  
  </section>


<!-- Script area -->
<jsp:include page="../masterFooter.jsp" />

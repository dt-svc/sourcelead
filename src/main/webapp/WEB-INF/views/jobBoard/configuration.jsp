<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <div class="success_pop" id="selector1" style="display: none;">
        <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
    </div>
    <section class="content-header clearfix">
        <div class="pull-left">
            <h2>Job Board Configuration</h2>
        </div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Job Board Configuration</div>
    </section>
    
<div class="project_wrap">  
                    
    <div class="positionRelative reportHead clearfix">
    	<ul class="pull-right list-unstyled list-inline">
    		<li><button data-toggle="modal" type="button" class="newAdBtn mr5" onclick="location.href = '${sourceleadUrl}/manage-job-board-accounts';">Manage Job Board Accounts</button></li>
    	</ul>
    </div>
  
     <div class="clearfix">
       <ul class="list-unstyled jobsboard-list">
           <c:forEach items="${jobBoardList}" var="j">
               <li class="clearfix">
                   <div class="col-md-11 noPad">
                       <span class="jobboard-company"><img src="${j.logoUrl}" alt="${j.name}" title="${j.name}"></span>
                       <div class="clearfix"></div>
                       <p class="text-muted small mb0">Please request the ${j.name} representative to provide the API access to post the jobs via the ATS</p>
                   </div>
                   <div class="col-md-1 noPad">
                       <div class="toggle-button mt">
                           <label class="switch ml">
                               <input type="checkbox" ${j.active ? 'checked' : ''}>
                               <div class="slider round" data-jobboardid='${j.jobBoardId}' data-id="${j.id}" data-name='${j.name}' data-active='${j.active}' data-compname='jobBoard'></div>
                           </label>
                       </div>
                       <div class="clearfix mt10"></div>
                   </div>
               </li>
           </c:forEach>
       </ul>
   </div>
    
    </div>
</section>

<jsp:include page="../masterFooter.jsp" />
<script type="text/javascript" src="/resources/js/jobBoard.js?staticTime=${staticTime}"></script>

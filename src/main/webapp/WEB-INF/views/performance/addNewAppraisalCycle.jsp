<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Appraisal Cycle</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/performanceManagementConfiguration'/>">Configuration</a> &raquo; <a onclick="performanceManagement.viewAppraisalCycle();">Appraisal Cycle List</a> &raquo; Add Appraisal Cycle</div>
    </section>
    
 <div class="project_wrap">
	  <div class="clearfix newAddWidth">
		    <div class="form-horizontal mt myForm">
		    
		    </div>
	  </div>
 </div>

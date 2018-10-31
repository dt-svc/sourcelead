<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <link href="/resources/css/jquery-ui.css" rel="stylesheet"> -->

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
     <script src="/resources/js/sourcelead.common.js"></script> 

<style>

.dashboard-widget.dashboard-widget-icons{
min-height: 125px !important;
}
.dashboard-widget .blank-pages{
padding: 0; min-height: 100px;
}

.custom-datatble table{
  margin-top: 20px !important;
 }
 .custom-datatble .dataTables_filter{
   top: -35px !important; padding: 0 !important;
 }
 .dashboard-datepkr{
       position:relative;
}
.ui-timepicker-wrapper {
z-index: 999999;
width: 11.5%;
}
.pdleft{
padding-left:0;
}
.pdRight{
padding-right:0;
}

.col-md-3 {
    width: 25% !important;
}
</style>

 <style type="text/css">
.clients_wrap_table {
overflow-x: auto;
}
</style>

 <script type="text/javascript">
 $( document ).ready(function() {
	 var settingsConfigured = '${SourceLeadWeb_settingsConfigured}';
	 if(settingsConfigured == "N"){
		configureDiv();
	 }
	});
</script> 


<form:form class="login-form clearfix"  id="configure_form"  name="configure_form" method="post" >
   <div id ="configurePopUpDiv"></div>
</form:form>

<script>
function logoutForConfigure() {
	document.getElementById("logoutForm").submit();
}
</script>


<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<section class="content-wraper">
	    <section class="content-header clearfix">
	      
	      <c:if test="${!empty param.invitation}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>You accepted invitation successfully.</span>
			</div>
	     </c:if>
	     
	     <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector1"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	    
	      <div class="pull-left">
	        <h2>Dashboard</h2>
	      </div>
	    </section>
	    
	    
		
		
		 <div class="clearfix row">
      		<div class="col-md-12">
				<div class="dashboard-widget clearfix">
				    <div class="row">
					    <div class="col-md-3 paddingRight">
					    	<h4>Bulk Imports</h4>
					    </div>
				  	</div>
			    	<div class="col-sm-6 col-md-3">
			            <div class="content_widget jobs-graphview buttonView">
			              <span class="label label-warning">Associates</span>
			            <span class="navIcons assIconImport"></span>
			            	  <a href="${appPath}/uploadForm"><span class="count text-primary">Import</span></a>
			              
		               </div>
			        </div>
			        
			        <div class="col-sm-6 col-md-3">
			            <div class="content_widget jobs-graphview buttonView">
			              <span class="label label-success">Jobs</span>
			            <span class="navIcons jobIconImport"></span>
			            	  <a href="${appPath}/uploadJobs"><span class="count text-primary">Import</span></a>
			              
		               </div>
			        </div>
			        
			        <div class="col-sm-6 col-md-3">
			            <div class="content_widget jobs-graphview buttonView">
			              <span class="label label-info">Candidates</span>
			            <span class="navIcons canIconImport"></span>
			            	  <a href="${appPath}/uploadCandidateForm"><span class="count text-primary">Import</span></a>
			              
		               </div>
			        </div>
			        
			        <div class="col-sm-6 col-md-3">
			            <div class="content_widget jobs-graphview buttonView">
			              <span class="label label-danger">Projects</span>
			            <span class="navIcons projIconImport"></span>
			            	  <a href="${appPath}/projectDashboard"><span class="count text-primary">Import</span></a>
			              
		               </div>
			        </div>
			        
			        
			        
			   </div>
			   <div class="dashboard-widget clearfix"> 
			   
			   		<div class="col-sm-6 col-md-3">
			            <div class="content_widget jobs-graphview buttonView">
			              <span class="label label-warning">Assets</span>
			            <span class="navIcons asstIconImport"></span>
			            	  <a href="${appPath}/uploadassetManagement"><span class="count text-primary">Import</span></a>
			              
		               </div>
			        </div>
			            
			        <div class="col-sm-6 col-md-3">
			            <div class="content_widget jobs-graphview buttonView">
			              <span class="label label-success">LCA</span>
			            <span class="navIcons immIconImport"></span>
			            	  <a href="${appPath}/uploadLcaForm"><span class="count text-primary">Import</span></a>
			              
		               </div>
			        </div>
			        
			        <div class="col-sm-6 col-md-3">
			            <div class="content_widget jobs-graphview buttonView">
			              <span class="label label-info">Petition</span>
			            <span class="navIcons immIconImport"></span>
			            	  <a href="${appPath}/uploadImmigrationForm"><span class="count text-primary">Import</span></a>
			              
		               </div>
			        </div>
			        
		      </div>
      </div>
      <div class="clearfix"></div>
      </div>
      
</section>

<jsp:include page="masterFooter.jsp" />
<jsp:include page="dashboard_scripts.jsp"/>
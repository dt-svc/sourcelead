<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Run Payroll</h2>
</div>
   <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Run Payroll</div>
  </section>
		<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
  <div class="project_wrap">
   <div id="tabs-menu" class="clearfix project-tabs-menu">
	<ul class="nav nav-tabs newTabs">
		<li id="initiatePayrollLi" class=""><a onclick="initiatePayrollDiv();highlight('initiatePayrollLi');" title="Initiate Payroll">Initiate Payroll</a></li>
		<li id="payrollLi"  class=""><a onclick="payrollDiv(); highlight('payrollLi');" title="Payroll">Payroll</a></li>
		<li id="groupsLi"  class=""><a onclick="groupsDiv(); highlight('groupsLi');" title="Groups">Groups</a></li>
		<li id="formsLi"  class=""><a onclick="formsDiv(); highlight('formsLi');" title="Forms">Forms</a></li>
	</ul>
   </div>
   </div>
   <div class="clearfix"></div>
   <div class='data_loading' id="loadTime"></div>
   <div id="runPayRollTabsContent"></div>
</section>

<script>
	$(document).ready(function() {
		initiatePayrollDiv();
		highlight('initiatePayrollLi');
	});
</script> 
 <script>
	function initiatePayrollDiv(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/initiateIndPayroll",		
	        		      success :function(data) {
	        		    	  $("#runPayRollTabsContent").empty();
	        		    	  $("#runPayRollTabsContent").append(data);
	        		    	  $("#loadTime").removeAttr('style');
	        		    	  prepareChosenScripts();
	        		      }
	        	    }); 
	}
</script>

 <script>
	function payrollDiv(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $("#runPayRollTabsContent").empty();
	               $("#loadTime").removeAttr('style');
	}
</script>
 <script>
	function groupsDiv(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $("#runPayRollTabsContent").empty();
	               $("#loadTime").removeAttr('style');
	}
</script>
 <script>
	function formsDiv(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               $("#runPayRollTabsContent").empty();
	               $("#loadTime").removeAttr('style');
	}
</script>

<script>
function highlight(id) {
	$("#tabs-menu>ul>li.active").removeClass("active");
    $("#"+id).addClass("active");
} 
</script>
<jsp:include page="../../masterFooter.jsp" />
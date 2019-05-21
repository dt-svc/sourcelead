<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="/pms/resources/js/tax/my_payroll.js" type="text/javascript"></script>

<section class="content-wraper contentWraper">
  <section class="content-header clearfix">
	<div class="pull-left">
	<h2>My Payroll</h2>
	</div>
	    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; My Payroll</div>
  </section>
  <div class="project_wrap">
	   <div id="tabs-menu" class="clearfix project-tabs-menu">
			<ul class="nav nav-tabs newTabs">
				<li id="myPaysLi" class=""><a onclick="getMyPays();" title="Pays">Pays</a></li>
				<li id="myFormsLi"  class=""><a onclick="getMyForms();" title="Forms">Forms</a></li>
				<li id="myLoansAdvancesLi"  class=""><a onclick="getMyLoansAdv('Outstanding');" title="Loans & Advances">Loans & Advances</a></li>
			</ul>
	   </div>
   
   <div class="clearfix"></div>
   <div class='data_loading' id="loadTime"></div>
   <div id="myPayRollTabsContent" class="require_wrap_table datatable_filter"></div>
   </div>
</section>

<script>
	$(document).ready(function() {
		var redirectionTab = '${redirectionTab}';
		if(redirectionTab === "forms")
			getMyForms();
		else 
			getMyPays();
	});
</script>
 
<script>
	function getMyPays(){ 
		var pmsUrl ='${SourceLeadWeb_pmsUrl}';
              $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
       	   $.ajax({
      	          type : "GET",
       	      	  url: pmsUrl+"/getMyPays",		
       		      success :function(data) {
       		    	  $("#myPayRollTabsContent").empty();
       		  		  $("#myPayRollTabsContent").append(data);
       		    	  $("#loadTime").removeAttr('style');
       		      }
       	    });
       	highlight('myPaysLi');
	}
</script>

<script>
	function getMyForms(){
			var pmsUrl ='${SourceLeadWeb_pmsUrl}';
              $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
       	   $.ajax({
      	          type : "GET",
       	      	  url: pmsUrl+"/getMyForms",		
       		      success :function(data) {
       		    	  $("#myPayRollTabsContent").empty();
       		  		  $("#myPayRollTabsContent").append(data);
       		    	  $("#loadTime").removeAttr('style');
       		      }
       	    });
       	   highlight('myFormsLi');
	}
</script>

<script>
	function getMyLoansAdv(loanStatus){ 
			 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
      	   $.ajax({
     	          type : "GET",
      	      	  url: pmsUrl+"/getMyLoansAdv?loanStatus="+loanStatus,		
      		      success :function(data) {
      		    	  $("#myPayRollTabsContent").empty();
      		  		  $("#myPayRollTabsContent").append(data);
      		    	  $("#loadTime").removeAttr('style');
      		    	  getMyLoansAdvList('Outstanding');
      		      }
      	    });
      	   highlight('myLoansAdvancesLi');
	}
</script>

<script>
function highlight(id) {
	$("#tabs-menu>ul>li.active").removeClass("active");
    $("#"+id).addClass("active");
} 
</script>
<script>
function downloadPayslip(payslipUUID){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	 window.location.href = pmsUrl+"/getDownloadPayslipInd?indEmpPayrollUUID="+payslipUUID;

}

</script>






<jsp:include page="../../masterFooter.jsp" />
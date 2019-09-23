<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheets</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/app/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Timesheets</div>
        </section>
		 <form id="timesheet_form" name="timesheet_form">
		 	    <div class='timesheet_out' id="timesheet_out">
				<div class='timesheet_wrap' id='timesheet_wrap'>
				 <div class='data_loading' id="loadTime"></div>
				 <div id ="timesheetEntriesDiv">
		           <jsp:include page="timesheetInvoiceList.jsp"></jsp:include>
		          </div>
		       </div>
		  </div>
		 </form> 		
      </section>
 <script>
	$(document).ready(function() {
		timesheetEntriesDiv();
	});
</script> 
 <script>
	function timesheetEntriesDiv(){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               $("#timesheetEntriesDiv").empty();
	               var invoiceFrequency = '';
	               var currentTimeFrequency = '';
	               $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/getEmployeeTimesheetEntryDetails?invoiceFrequency="+invoiceFrequency+"&currentTimeFrequency="+currentTimeFrequency+"&timeSheetsFor=user",		
	        		      success :function(data) {
	        		    	  $("#timesheetEntriesDiv").empty();
	        		    	  $("#timesheetEntriesDiv").append(data);
	        		    	  $("#loadTime").removeAttr('style');
	        		    	  prepareChosenScripts();
	        		    	  setTotals();
	        		      }
	        	    }); 
	}
</script> 
<script type="text/javascript">
         function prepareChosenScripts(){ 
        	      var config = {
        			  '.chosen-select'           : {},
        			  '.chosen-select-deselect'  : {allow_single_deselect:true},
        			  '.chosen-select-no-single' : {disable_search_threshold:10},
        			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        			  '.chosen-select-width'     : {width:"95%"}
        			};
        		   for (var selector in config) {
        			  $(selector).chosen(config[selector]);
        			};
        			$('.chosen-select').each(function() {
        			var $this = $(this);
        			$this.next().css({'width': '100%'});
        			})
           };
         
</script> 
 <script>
$(document).ready(function(){
	if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	 }
});
</script>
<script type="text/javascript"	src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>
<jsp:include page="../masterFooter.jsp" />
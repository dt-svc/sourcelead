<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

<script>
// project side bar tabs
$(document).ready(function() {
	timesheetReportsDiv();
});
</script>  

<script>  
	function timesheetReportsDiv(){ 
		
               var sourceleadUrl ='${sourceleadUrl}';
               var frequencyYear = $("#frequencyYear").val();
               if(frequencyYear == null || frequencyYear == '' || frequencyYear == 'undefined')
            	   frequencyYear = '';
               
  	           var frequencyMonth = $('#frequencyMonth').val();
  	           if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
  	        	 frequencyMonth = '';
	           
               $("#timesheetReportsDiv").empty();
               $("#timesheetReportsDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
        	   $.ajax({
	      	          type : "POST",
	          	      dataType : 'json',
        	      	  url: sourceleadUrl+"/timesheetReportsDiv?${_csrf.parameterName}=${_csrf.token}&frequencyYear="+frequencyYear+"&frequencyMonth="+frequencyMonth,		
        		      success :function(result) {
        		    	    $("#timesheetReportsDiv").removeAttr("style");
        		    	    $("#timesheetReportsDiv").empty();
        		    	  	$("#timesheetReportsDiv").append(result);
        		    	  	prepareChosenScripts();
        		    	  	pagination();
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
           };
				
</script> 
<script>
function pagination(){ 
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
    		"sSearch": "",
    		"sZeroRecords": "", "sEmptyTable": ""
    	}
    } );
}
</script>
<body>
      <%-- <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet Monthly Reports</h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Dashboard</div>
           <div class="clearfix"></div>
        </section>
		<div id="timesheetReportsDiv"></div>
         </section> --%>
         
        <section class="content-wraper">
		<section class="content-header clearfix">
  		<div class="pull-left">
    	<h2>Timesheet Reports</h2>
 		</div>
 		<div class="pull-right brud-crum">Home &raquo; List by status</div>
		</section>
        <div id="timesheetReportsDiv"></div>
        </section>   
 </body>
    

<jsp:include page="../masterFooter.jsp" />
 
      
    
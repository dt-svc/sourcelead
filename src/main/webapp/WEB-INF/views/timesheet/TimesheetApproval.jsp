<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>

<script>
// project side bar tabs
$(document).ready(function() {
	timesheetApprovalDiv();
});
</script>  

<script>  
	function timesheetApprovalDiv(filter){ 
		
               var sourceleadUrl ='${sourceleadUrl}';
               var frequencyMonth = $("#frequencyMonth").val();
               var locationCode ='${SourceLeadWeb_locationCode}'; 
               if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
            	   frequencyMonth = '';
               
  	           var timesheetFrequency = $('#timesheetFrequency').val();
  	           if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
  	        	 timesheetFrequency = '';
  	           
  	           var weekFrequency = $('#weekFrequency').val();
	           if((weekFrequency == null || weekFrequency == '' || weekFrequency == 'undefined') || (filter == 'month')  || (filter == 'frequency'))
	        	   weekFrequency = '';
  	         
	           var stage = $('#stage').val();
	           if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
	        	   stage = '';
	           
	           var client = $('#client').val();
	           if(client == null || client == '' || client == 'undefined')
	        	   client = '';
	           
               $("#timesheetApprovalDiv").empty();
               $("#timesheetApprovalDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        	   $.ajax({
	      	          type : "POST",
	          	      dataType : 'json',
        	      	  url: sourceleadUrl+"/timesheetApprovalDiv?${_csrf.parameterName}=${_csrf.token}&frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency+"&weekFrequency="+weekFrequency+"&client="+client+"&stage="+stage+"&locationCode="+locationCode,		
        		      success :function(result) {
        		    	    $("#timesheetApprovalDiv").removeAttr("style");
        		    	    $("#timesheetApprovalDiv").empty();
        		    	  	$("#timesheetApprovalDiv").append(result);
        		    	  	prepareChosenScripts();
        		    	  	pagination();
        		    	  	var checkEmptyList = document.getElementById("hideExportApproval").value;  
        		    	  	if(checkEmptyList == 'EmptyList'){
        		    			$('#exportHideId').hide();
        		    		}
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

<body  onclick="bodyClick();">
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet approval</h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Timesheet approval</div>
           <div class="clearfix"></div>
        </section>
		<div id="timesheetApprovalDiv" class="datatable_filter"></div>
         </section>
 </body>
    
 <jsp:include page="TimesheetApprovalCommonScripts.jsp" />

<jsp:include page="../masterFooter.jsp" />
 
      
    
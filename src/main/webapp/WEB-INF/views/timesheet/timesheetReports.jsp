<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="/resources/js/formvalidator.js"></script>

<style type ="text/css">
.timesheet_container {
background: #f5f5f5;
box-shadow: 0px 0px 3px #707070;
    -webkit-box-shadowbox-shadow: 0px 0px 3px #707070;
    -moz-box-shadow: 0px 0px 3px #707070;
    padding:0;
    -moz-transition: all ease-in-out 0.3s;
    -ms-transition: all ease-in-out 0.3s;
    -webkit-transition: all ease-in-out;
    /* overflow-x: auto; border-radius: 3px; */
}
.timesheet_container {
overflow-x: inherit;
}
.timesheet_container .content-area {
box-shadow:none;
}
</style>

 <script>
$(document).ready(function() {
	timesheetReportsDiv();
});
</script>

<body>
	<section class="content-wraper">
	
		<section class="content-header clearfix">
			<div class="pull-left"><h2 class="pull-left">TimeSheet Monthly Report</h2></div>
			<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports	Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo; TimeSheet Monthly Report</div>
		</section>
		
		<div class="timesheet_container datatable_filter">
			<div class='content-area content-new clearfix'>
				<div  id="reportsDiv">
					<jsp:include page="timeReportsDropDown.jsp"></jsp:include>
				</div>	
			</div>
		</div>
	</section>
</body>


<script>  
	function timesheetReportsDiv(searchData){ 
               var sourceleadUrl ='${sourceleadUrl}';
               var frequencyYear = $("#frequencyYear").val();
               if(frequencyYear == null || frequencyYear == '' || frequencyYear == 'undefined')
            	   frequencyYear = '';
  	           var frequencyMonth = null;
  	           if(frequencyYear!=null && frequencyYear!='')
  	           frequencyMonth = $(".time_months[data-year="+frequencyYear+"]").find("select").val();
  	           if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
  	        	 frequencyMonth = '';
  	           var searchvalue = $('#dataTableSearchTimesheetReports').val();
  	           if(searchData != null && searchData !='' && searchData!='undefined'){
  	        	 searchValue = searchData;
  	           }else{
  	        	 searchValue = '';
  	           }
               $("#reportsDiv").empty();
               $("#reportsDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        	   $.ajax({
	      	          type : "POST",
        	      	  url: sourceleadUrl+"/timesheetReportsDiv?&frequencyYear="+frequencyYear+"&frequencyMonth="+frequencyMonth+"&searchValue="+searchValue,		
        		      success :function(result) {
        		    	  	  $("#reportsDiv").empty();
	        		    	  $("#reportsDiv").append(result);
	        		    	  $("#reportsDiv").removeAttr('style');
	        		    	  prepareChosenScripts();
	        		    	  preparePagination(1);
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
 function exportToggle(){
	 $("#exportPop").toggle();
	 $(".export_pop").slideUp();
	 
 	 $("html").click(function(){
	 $("#exportPop").hide();
	   });
	 $("#exportPop, #mypop").click(function(e) {  
	     e.stopPropagation();
	 });
 }
</script>

<script type="text/javascript">
function exportTimeSheetReportsToPdf(type){
	var sourceleadUrl ='${sourceleadUrl}';
	var frequencyYear = $("#frequencyYear").val();
    if(frequencyYear == null || frequencyYear == '' || frequencyYear == 'undefined')
 	   frequencyYear = '';
      var frequencyMonth = null;
      if(frequencyYear!=null && frequencyYear!='')
      frequencyMonth = $(".time_months[data-year="+frequencyYear+"]").find("select").val();
      if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
   	 frequencyMonth = '';
      var searchValue = $('#dataTableSearchExpenses').val() ;
	 window.location.href = sourceleadUrl+"/timeSheetReportExportList?${_csrf.parameterName}=${_csrf.token}&frequencyYear="+frequencyYear+"&frequencyMonth="+frequencyMonth+"&exportType="+type+"&searchValue="+searchValue;
}
</script>

<jsp:include page="../masterFooter.jsp" />
 
      
    
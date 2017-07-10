<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

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
    overflow-x: auto; border-radius: 3px;
}
.timesheet_container .content-area {
box-shadow:none;
}
</style>



<script>
// project side bar tabs
$(document).ready(function() {
	timesheetReportsDiv();
});
</script>  
<script>  
	function getMonths(yr){ 
		var yrStr = $("#"+yr.value+"MonthList").val().replace("[","").replace("]", "");
		var frequencyYear = $("#frequencyYear").val();
		var arr =  yrStr.split(",");
		 var internalOutput = "";
			if(arr != null && arr != 'undefined' && arr != ''){
				for(var i = 0; i < arr.length; i++) {
					var temp1 = arr[i].replace(' ','');
					var temp2 = $("#currentMonth").val().replace(' ','');
					var currentYear = $("#currentYear").val().replace(' ','');
					if(temp1 == temp2 && currentYear == frequencyYear){
						internalOutput = internalOutput+"<option value='"+arr[i]+"' selected>"+arr[i]+"</option>";
					}
					else{
						internalOutput = internalOutput+"<option value='"+arr[i]+"'>"+arr[i]+"</option>";
					}
				   
				}
			}
			
			
		$("#frequencyMonth").empty();
		$("#frequencyMonth").append(internalOutput);
 		$("#frequencyMonth").trigger("chosen:updated");
		
	}
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
               $("#timesheetReportsDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
        		    	  	var checkEmptyList = document.getElementById("hideExport").value; 
        		    		if(checkEmptyList == 'EmptyList'){
        		    			document.getElementById("mypop").children[0].style.display = "none";
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
<script>
function pagination(){ 
    $('#timesheetReportsPageNation').DataTable( { 
    	"scrollX": true,
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>



<script>
/*     function mypop(){
		$("#exportPop").toggle();
		$(".export_pop, #filter2ApprovedPop").slideUp();
    }
 */
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
       var frequencyMonth = $('#frequencyMonth').val();
       if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
    	 frequencyMonth = '';
       
	window.location.href = sourceleadUrl+"/timeSheetReportExportList?${_csrf.parameterName}=${_csrf.token}&frequencyYear="+frequencyYear+"&frequencyMonth="+frequencyMonth+"&exportType="+type;
	
	
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
      	<h2 class="pull-left">Reports</h2>
		 <div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Timesheet Reports</a>
			<div class="RePop">
				<ul>
					<li><a href="<c:url value='/requirementListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Posted</a></li>
					<li><a href="<c:url value='/requirementStagesListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Jobs Stages</a></li>
<%-- 					<li><a href="<c:url value='/applicantsReceivedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Received</a></li> --%>
<%-- 				    <li><a href="<c:url value='/applicantsListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants</a></li> --%>
				    <li><a href="<c:url value='/applicantsSubmittedListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Applicants Submitted</a></li>
<%-- 				    <li><a href="<c:url value='/requirementAssignToReports'/>"><span><i class="fa fa-briefcase"></i></span> Assign To</a></li> --%>
				    <li><a href="<c:url value='/candidateListForReports'/>"><span><i class="fa fa-briefcase"></i></span>Added Candidates</a></li>
				    <li><a href="<c:url value='/timeSheetReports'/>"><span><i class="fa fa-briefcase"></i></span>TimeSheet Reports</a></li>
				</ul>
				</div>
			</div>  
    	</div>
		
 		<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&nbsp;&raquo;&nbsp;<a href="<c:url value='/recruitersReports'/>">Reports</a>&nbsp;&raquo; TimeSheet Reports</div>
		</section>
        <div id="timesheetReportsDiv" class="timesheet_container datatable_filter"></div>
        </section>   
 </body>
    

<jsp:include page="../masterFooter.jsp" />
 
      
    
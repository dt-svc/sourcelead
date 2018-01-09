<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
	$(document).ready(function() {
		timesheetApprovalDetils('month');
	});
</script>

<section class="content-wraper">
		  <section class="content-header clearfix">
		       <div class="pull-left"><h2>Timesheet approval</h2></div>
		            <div class="pull-right brud-crum"><a href="">Home</a> » Timesheet approval</div>
		       <div class="clearfix"></div>
		       <span><a href="<c:url value='${appPath}/uploadTimesheet'/>"><i class="glyphicon glyphicon-import expIcon"></i>&nbsp;</a></span>
		   </section>
		    <div  id="loadTimeHead"></div>
		   <div id="timesheetApprovalDiv" class="datatable_filter">
			   <div class="timesheet_out">
			     	<div class="timesheet_appr_wrap">
			     	 <div id="timeApprovaldiv">
				     		<jsp:include page="TimesheetApprovalDetils.jsp"></jsp:include>
			         </div>
	     		</div>
	     	</div> 
	 	 </div>
</section>

 <script>
	function timesheetApprovalDetils(filter){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var locationCode ='${SourceLeadWeb_locationCode}';
	               var userId = '${SourceLeadWeb_user.id}';
	               var frequencyMonth = $("#frequencyMonth").val();
	               var searchData = $('#dataTableSearchExpenses').val();
	               if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
	            	   frequencyMonth = '';
	               
	  	           var timesheetFrequency = $('#timesheetFrequency').val();
	  	           if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
	  	        	 timesheetFrequency = '';
	  	           
	  	           var weekFrequency = $('#weekFrequency').val();
		           if((weekFrequency == null || weekFrequency == '' || weekFrequency == 'undefined') || (filter == 'month')  || (filter == 'frequency'))
		        	   weekFrequency = '';
	  	         
		           var stage = $('#stage').val();
		           if(stage == null || stage == '' || stage == 'undefined')
		        	   stage = '';
		           
		           var client = $('#client').val();
		           if(client == null || client == '' || client == 'undefined')
		        	   client = '';
		           
		           var searchvalue = '';
	  	           if(searchData != null && searchData !='' && searchData!='undefined'){
	  	        	 searchValue = searchData;
	  	           }else{
	  	        	 searchValue = '';
	  	           }
		           $("#timeApprovaldiv").empty();
	               $("#loadTimeHead").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	        	   $.ajax({
		      	          type : "POST",
	        	      	  url: sourceleadUrl+"/timesheetApprovalDetails?frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency+"&weekFrequency="+weekFrequency+"&client="+client+"&stage="+stage+"&locationCode="+locationCode+"&userId="+userId+"&searchValue="+searchValue,		
	        		      success :function(data) {
	        		    	  $("#loadTimeHead").removeAttr('style');
	        		    	  $("#timeApprovaldiv").empty();
	        		    	  $("#timeApprovaldiv").append(data);
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
        			$('.chosen-select').each(function() {
        			var $this = $(this);
        			$this.next().css({'width': '100%'});
        			})
           };
         
</script> 

<jsp:include page="../masterFooter.jsp" />

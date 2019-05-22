<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!-- Timesheet Approval Page Section Starts  -->
 <section class="content-wraper">
		  <section class="content-header clearfix">
		       <div class="pull-left"><h2>Timesheets approval</h2></div>
		            <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> » Timesheets approval</div>
		       <div class="clearfix"></div>
		   </section>
		    <div  id="loadTimeHead"></div>
		   <div id="timesheetApprovalDiv" class="datatable_filter" >
			   <div class="timesheet_out">
			     	<div class="timesheet_appr_wrap">
			     	 <input type='hidden' id='notifyLink' name='notifyLink' value="${notifyLink}"/>
			     	 <div id="timeApprovaldiv">
			     	        <!-- Timesheet Approval Details Page will be generated after execution of onload-function timesheetApprovalDetils(filter)  -->
				     		<jsp:include page="timesheetApprovalDetils.jsp"></jsp:include>
			         </div>
	     		</div>
	     	</div> 
	 	 </div>
 </section>


<script>
	$(document).ready(function() {
		    if ('${notifyLink}' == 'true') 
			{
		    	timesheetApprovalDetils('notifyLink');
			} else{
				timesheetApprovalDetils('month');
			}
			selectedRecords = {};
			$(".checkBoxForSubmitForApproval").click(function () {
				selectedRecords[$(this).val()] = $(this).is(":checked");
			});
	});
</script>

<!--Getting timesheetApprovalDetails based on default/filter data  -->
<script>
	function timesheetApprovalDetils(filter){ 
	               var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	               var locationCode ='${SourceLeadWeb_locationCode}';
	               var userId = '${SourceLeadWeb_user.id}';
	               
	               var frequencyMonth = $('input[id=frequencyMonth]:checked').val();
	               var timesheetFrequency = $('input[id=timesheetFrequency]:checked').val();
	               var weekFrequency = $('input[id=weekFrequency]:checked').val();
	               var client = $('input[id=client]:checked').val();
	               var project = $('input[id=project]:checked').val();
	               
	               var projectName = '';
	               var stage = $('input[id=stage]:checked').val();
	               if(filter != null && filter != '' && filter != 'undefined' &&  filter == 'notifyLink'){
	            	   frequencyMonth = '${frequencyMonth}';
	            	   timesheetFrequency = '${timesheetFrequency}';
	            	   searchData = '${searchData}';
	            	   weekFrequency = '${weekFrequency}';
	            	   projectName= '${projectName}';
	               }
	               if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
	            	   frequencyMonth = '';
	               
	  	         
	  	           if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
	  	        	 timesheetFrequency = '';
	  	           
	  	           
		           if((weekFrequency == null || weekFrequency == '' || weekFrequency == 'undefined') || (filter == 'month')  || (filter == 'frequency'))
		        	   weekFrequency = '';
	  	         
		          
		           if(stage == null || stage == '' || stage == 'undefined')
		        	   stage = '';
		           
		          
		           if(client == null || client == '' || client == 'undefined')
		        	   client = '';
		           
		           if(project == null || project == '' || project == 'undefined')
		        	   project = '';
		           var searchValue = $('#dataTableSearchTimesheets').val();
		   	        if(searchValue == null ||  searchValue =='' || searchValue =='undefined'){
		   	        	searchValue = '';
		            }
		           $("#timeApprovaldiv").empty();
	               $("#loadTimeHead").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	               var formData = new FormData();
	               formData.append("frequencyMonth", frequencyMonth);
	               formData.append("timesheetFrequency", timesheetFrequency);
	               formData.append("weekFrequency", weekFrequency);
	               formData.append("client", client);
	               formData.append("project", project);
	               formData.append("stage", stage);
	               formData.append("locationCode", locationCode);
	               formData.append("userId", userId);
	               formData.append("searchValue", searchValue);
	               formData.append("projectName", projectName);
	               
	        	   $.ajax({
		      	          type : "POST",
		      	          data: formData,
		  		          processData: false,
		  		          contentType:false, 
	        	      	  url: sourceleadUrl+"/timesheetApprovalDetails",		
	        		      success :function(data) {
	        		    	  $("#loadTimeHead").removeAttr('style');
	        		    	  $("#timeApprovaldiv").empty();
	        		    	  /* Returns timesheetApprovalDetils.jsp content */
	        		    	  $("#timeApprovaldiv").append(data);
	        		    	  $(".checkBoxForSubmitForApproval").click(function () {
			             	    	selectedRecords[$(this).val()] = $(this).is(":checked");
			             	  });
	        		    	  prepareChosenScripts();
	        		    	  preparePagination(1);
	        		      }
	        	    }); 
	}
</script> 



<!--  chosen-select script   -->
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

<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="TimesheetScripts.jsp" />


<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/jquery-fun.js"></script>
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 

<script>
// project side bar tabs
$(document).ready(function() {
	timesheetApprovalDiv();
});
</script>  


<script>  
	function timesheetApprovalDiv(){ 
               var sourceleadUrl ='${sourceleadUrl}';
               $("#timesheetApprovalDiv").empty();
               $("#timesheetApprovalDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
        	   $.ajax({
	      	          type : "POST",
	          	      dataType : 'json',
        	      	  url: sourceleadUrl+"/timesheetApprovalDiv?${_csrf.parameterName}=${_csrf.token}",		
        		      success :function(result) {
        		    	    $("#timesheetApprovalDiv").removeAttr("style");
        		    	    $("#timesheetApprovalDiv").empty();
        		    	  	$("#timesheetApprovalDiv").append(result);
        		    	  	prepareChosenScripts();
        		      }
        	    }); 
	}
</script>


<script>
	function timesheetApprovalDivByProject(projectId){ 
			 
	         var sourceleadUrl ='${sourceleadUrl}';
	         $("#timesheetApprovalDivByProject").empty();
	         $("#timesheetApprovalDivByProject").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetApprovalDivByProject?${_csrf.parameterName}=${_csrf.token}",		
   		          success :function(result) {
   		        	 $("#timesheetApprovalDivByProject").removeAttr("style");
   		        	 $("#timesheetApprovalDivByProject").empty();
   		             $("#timesheetApprovalDivByProject").append(result);  
		    	  	 
   		          }
   	         }); 
	}
</script>	

   

<script>
	function timesheetApprovalDivByFrequency(frequency){   
	         var sourceleadUrl ='${sourceleadUrl}';
	         $("#timesheetApprovalDivByFrequency").empty();
	         $("#timesheetApprovalDivByFrequency").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetApprovalDivByFrequency?${_csrf.parameterName}=${_csrf.token}",		
   		          success :function(result) {
   		        	      $("#timesheetApprovalDivByFrequency").removeAttr("style");
	   		    	      $("#timesheetApprovalDivByFrequency").empty();
			    	  	  $("#timesheetApprovalDivByFrequency").append(result); 
   		          }
   	         }); 
	}
</script> 

<script>
	function timesheetApprovalDivByStatus(frequency){   
				
	         var sourceleadUrl ='${sourceleadUrl}';
	         $("#timesheetApprovalDivByStatus").empty();
	         $("#timesheetApprovalDivByStatus").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetApprovalDivByStatus?${_csrf.parameterName}=${_csrf.token}",		
   		          success :function(result) {
   		        	  		
   		        	      $("#timesheetApprovalDivByStatus").removeAttr("style");
	   		    	      $("#timesheetApprovalDivByStatus").empty();
			    	  	  $("#timesheetApprovalDivByStatus").append(result); 
			    	  	  
   		          }
   	         }); 
	}
</script> 


<script>
	function timesheetApprovalPopup(startDate,endDate){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         $.ajax({
    	          type : "POST",
        	      dataType : 'json',
  	      	  	  url: sourceleadUrl+"/timesheetDivByFrequency?${_csrf.parameterName}=${_csrf.token}",		
  		          success :function(result) {
  		    	       
  		    	       $("#timesheetPopUp").empty();
		    	  	   $("#timesheetPopUp").append(result); 
		    	  	   
		    	  	  
		    	 		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
		    	 		$(".side_mask").fadeIn(200);
		    	 		$("body").css('overflow-y', 'hidden');
		    	 	 
		    	 		$("#tab-1").show()
		 		    	   $(".project-tabs-menu a").click(function(event) {
		 		    	        event.preventDefault();
		 		    	        $(this).parent().addClass("current");
		 		    	        $(this).parent().siblings().removeClass("current");
		 		    	        var tab = $(this).attr("href");
		 		    	        $(".project_tabs_inn").not(tab).css("display", "none");
		 		    	        $(tab).fadeIn();
		 		    	 });
		    	  	   
  		          }
  	         }); 
	}
</script>
<body>
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet approval</h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; TimeSheet List</div>
        </section>
        
		<!-- Project -->
<!-- put id here -->
		<div id="timesheetApprovalDiv"></div>


</section>
      
</div>


<div class="side_mask"><div class="proj_close"></div></div>	
<div class="time_appr_side_wrap">
	<div class="time_appr_side_wrap_inn">
	 	<div id="timesheetPopUp"></div>
	</div>
</div>	
<div class="mask_time"></div>	
<div class="time_appr_rejct_pop">
	<textarea placeholder="Enter comment here"></textarea>
	<a href="javascript:void(0)">Submit</a><a class="close_color" href="javascript:void(0)">Cancel</a>
</div>	

<div class="time_pop time_pop_a"><p>Monday hours should not be more than 24<b>X</b></p></div>
<!--   Timesheet approval popup   -->
	</body>
    
<script>
$(document).ready(function() {
    $(".timesheet_appr_table .glyphicon").click(function(){
		$(".timesheet_appr_table .glyphicon strong").slideToggle(200);
	});
    $("html").click(function() {
        $(".timesheet_appr_table .glyphicon strong").slideUp(200);
    });
    $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
        e.stopPropagation();
    });	
});


// Timesheet his pop
$(document).ready(function() {
    $(".time_newhead h2").click(function(){
		$(".time_newhead_pop").slideToggle(100);
	});
    $("html").click(function() {
        $(".time_newhead_pop").slideUp(100);
    });
    $(".time_newhead_pop, .time_newhead h2").click(function(e) {
        e.stopPropagation();
    });	
});


// close popup
$(document).ready(function() {
    $(".timesheet_appr_table .fa-times").click(function(){
		$(".mask_time").fadeIn(200);
		$(".time_appr_rejct_pop").slideToggle(200);
	});
    $(".time_appr_rejct_pop a").click(function(){
		$(".mask_time").fadeOut(200);
		$(".time_appr_rejct_pop").slideUp(200);
	});	
});

//  Timesheet approval side bar
$(document).ready(function() {
   /*  $(".timesheet_appr_table a").click(function(){
		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	}); */
    $(".proj_close, .time_cancel").click(function(){
		$(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

$(document).ready(function() {
    $(".glyphicon-comment").mouseenter(function(){
		$(".app_error_mes").addClass("app_error_mes_active");
	});
    $(".glyphicon-comment").mouseleave(function(){
		$(".app_error_mes").removeClass("app_error_mes_active");
	});	
});

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
 
      
    
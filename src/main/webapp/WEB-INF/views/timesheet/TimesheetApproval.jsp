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

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<style type="text/css">
.glyphicon-none:before {
    content: "\2122";
    color: transparent !important;
}
.timesheet-content {
background: #fff;
min-height: 100px;
clear:both;
display: inline-block;
width:100%;
}
.timesheet-content #example_filter {
position: relative !important;
top:0px !important;
}
</style>

<script>
function pagination(){ 
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
    		"sSearch": ""
    	}
    } );
}
</script>



<script>
// project side bar tabs
$(document).ready(function() {
	timesheetApprovalDiv();
});
</script>  
<script>
	function predefineProfileImage(imageId,userId){
			$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>
<script>  
	function timesheetApprovalDiv(filter){ 
		
               var sourceleadUrl ='${sourceleadUrl}';
               var frequencyMonth = $("#frequencyMonth").val();
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
               $("#timesheetApprovalDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
        	   $.ajax({
	      	          type : "POST",
	          	      dataType : 'json',
        	      	  url: sourceleadUrl+"/timesheetApprovalDiv?${_csrf.parameterName}=${_csrf.token}&frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency+"&weekFrequency="+weekFrequency+"&client="+client+"&stage="+stage,		
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
	function timesheetApprovalDivByMonthAndTimesheetFrequency(){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         var frequencyMonth = $("#frequencyMonth").val();
	         var timesheetFrequency = $('#timesheetFrequency').val();
	         $("#timesheetApprovalDivByMonthAndTimesheetFrequency").empty();
	         $("#timesheetApprovalDivByMonthAndTimesheetFrequency").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	         $.ajax({
     	          type : "POST",
         	      dataType : 'json',
   	      	  	  url: sourceleadUrl+"/timesheetApprovalDivByMonthAndTimesheetFrequency?${_csrf.parameterName}=${_csrf.token}&frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency,		
   		          success :function(result) {
   		        	 $("#timesheetApprovalDivByMonthAndTimesheetFrequency").removeAttr("style");
   		             $("#timesheetApprovalDivByMonthAndTimesheetFrequency").html(result);  
   		      	     prepareChosenScripts();
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
<script>
function saveRejectSubmissionByProjectMemberId(projectMemberId,startDate,endDate){
	
	 var msg = $("#reject"+projectMemberId+"message").val(); 
     var sourceleadUrl ='${sourceleadUrl}';
     var rowHtmlContent =  $("#row"+projectMemberId).html();
	 $("#row"+projectMemberId).empty();
	 $("#row"+projectMemberId).html("<td colspan='5'  id='loading"+projectMemberId+"'></td>");
     $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
   /*  
    $("#timesheetApprList").empty(); 
    $("#timesheetApprList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');   */
    $(".mask_time").fadeOut(200);
    $("#reject"+projectMemberId+"panel").slideUp(200);
    $.ajax({
    type: "POST",
    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&rejectedReason="+msg+"&approvalStatus=REJECTED",       
    success: function(data) {    
   	   // approvalList();
   	   var uniqueSepartor = startDate.replace('-','').replace('-','');
   	   if(projectMemberId.indexOf(uniqueSepartor) >= 0){
   		   var table = $('#example').DataTable();
   		   table.row('#row'+projectMemberId+'').remove().draw( false );
   	   }
   	   else{
   		  $("#row"+projectMemberId).empty();
 	    	  $("#row"+projectMemberId).html(rowHtmlContent);
 	    	  $("#status"+projectMemberId).html("REJECTED");
 	    	  $("#action"+projectMemberId).empty();
 	    	  $("#action"+projectMemberId).append(" <div><h3 class='reje_color'>Rejected</h3><i class='glyphicon glyphicon-comment' id='"+projectMemberId+"' onclick='rejectedMessage(\"rejectedMessage"+projectMemberId+"\");'><div class='app_error_mes' id='rejectedMessage"+projectMemberId+"'>"+msg+"</div></i></div>");  
   	   }
   	  pagination();
      }
    }); 
    
}


function cancelRejectSubmission(projectMemberId){
		 $(".mask_time").fadeOut(200);
		 $("#reject"+projectMemberId+"panel").slideUp(200);
}

function rejecteSubmission(projectMemberId,startDate,endDate){  
        
        var result = "";
        result = result +'<div class="time_appr_rejct_pop" id="reject'+projectMemberId+'panel" >';
		result = result +'<textarea placeholder="Enter comment here" id="reject'+projectMemberId+'message" ></textarea>';
		result = result +'<a href="#" onClick="saveRejectSubmission(\''+projectMemberId+'\',\''+startDate+'\',\''+endDate+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectSubmission(\''+projectMemberId+'\');">Cancel</a>';
		result = result +'</div>';
		 
		$("#rejectedPopup").append(result);
	    $(".mask_time").fadeIn(200); 
		$("#reject"+projectMemberId+"panel").slideToggle(200);
}
function approveSubmissionByProjectMemberId(projectMemberId,startDate,endDate){
		//$("#timesheetApprList").empty();
		var rowHtmlContent =  $("#row"+projectMemberId).html();
		$("#row"+projectMemberId).empty();
		$("#row"+projectMemberId).html("<td colspan='5'  id='loading"+projectMemberId+"'></td>");
	    $("#loading"+projectMemberId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
	    var rejectedReason;
        var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveSubmissionApprovalStatusByProjectMemberId?${_csrf.parameterName}=${_csrf.token}&projectMemberId="+projectMemberId+"&startDate="+startDate+"&endDate="+endDate+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
		    success: function(data) {
		    	 var uniqueSepartor = startDate.replace('-','').replace('-','');
	        	 if(projectMemberId.indexOf(uniqueSepartor) >= 0){
			    	 var table = $('#example').DataTable();
	        		 table.row('#row'+projectMemberId+'').remove().draw( false );
	        		   
	        	 }
	        	 else{
	        		 $("#row"+projectMemberId).empty();
			    	 $("#row"+projectMemberId).html(rowHtmlContent);
			    	 $("#status"+projectMemberId).html("APPROVED");
			    	 $("#action"+projectMemberId).html("<div><h3>Approved</h3></div>"); 
	        	   }
		    	 
		    }
	    });   
}
</script>

<jsp:include page="../masterFooter.jsp" />
 
      
    
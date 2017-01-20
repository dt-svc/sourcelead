<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="TimesheetScripts.jsp" />



<script>
	function timesheetApprovalPopup(startDate,endDate){ 
	         var sourceleadUrl ='${sourceleadUrl}';
	         
	        /*  $("#timesheetDivByTab").empty();
	         $("#timesheetDivByTab").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	          */
	         alert("one");
	         $.ajax({
    	          type : "POST",
        	      dataType : 'json',
  	      	  	  url: sourceleadUrl+"/timesheetDivByFrequency?${_csrf.parameterName}=${_csrf.token}",		
  		          success :function(result) {
  		    	       
  		        	   alert("hai"+result);
  		        	   /* $("#timesheetDivByTab").removeAttr("style"); */
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
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">
	<div class="timesheet_appr_wrap">
		<div class="timesheetNewHead">
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Select Project</option>
                  <option>Project 1</option>
                  <option>Project 2</option>
                  <option>Project 3</option>
                  <option>Project 4</option>
                </select>
            </div>
			<div class="timeNewSe timeNewSe_a">
                <select class="form-control chosen-select">
                  <option>Select Week Frequency</option>
                  <option>28/11/2016 to 04/12/2016</option>
                  <option>05/12/2016 to 11/12/2016</option>
                  <option>12/12/2016 to 18/12/2016</option>
                  <option>19/12/2016 to 25/12/2016</option>
                  <option>26/12/2016 to 01/01/2017</option>
                  <option>02/01/2017 to 08/01/2017</option>
                  <option>09/01/2017 to 15/01/2017</option>
                  <option>16/12/2017 to 22/12/2017</option>
                  <option>23/12/2016 to 29/12/2017</option>
                 
                  </select>
            </div>
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Stages</option>
                  <option>New</option>
                  <option>Submit for approval</option>
                  <option>Rejected</option>
                </select>
            </div>             
			<div class="clearfix"></div>          
		</div>	
		
		<div class="timesheet_appr_table">
    	<table>
        	<tbody>
			<thead>
            	<tr>
                	<th>Employee</th>
                    <th>Email id</th>
                    <th>Project</th>
                    <th>Frequency</th>
                    <th>Total hours</th>
                    <th>Stage</th>
                    <th>Action</th>
                    <th>Approved By</th>
                </tr>
			</thead>	
               <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div>Timesheets</div></td>
                    <td>
						<div id="datefrequency609012017"><span class="res_date"><i class="fa fa-calendar"></i>09-01-2017 To 15-01-2017</span></div>                    	</td>
                    <td><div><a href="javascript:void(0)" onclick="timesheetApprovalPopup();">50/50</a></div></td>
                    <td><div>Waiting for approval</div></td>
                    <td><div><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div>
					</td>
               		<td><div>Ranjit</div></td>
                </tr>
            </tbody>
        </table> 
</div>
<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li><a href="#">4</a></li>
	<li><a href="#">5</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
    <div class="clearfix"></div>
	</ul>
	<div class="clearfix"></div>
</nav>
	
	</div>
	
</div>
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



<jsp:include page="../masterFooter.jsp" />
 
      
    
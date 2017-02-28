<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/formvalidator.js"></script>
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap-select.min.css">
<link rel="stylesheet" href="datetimepicker.css">
<link rel="stylesheet" href="css/chosen.min.css">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<link href="css/sourcelead-main.css" rel="stylesheet">
<link href="css/timesheet.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->


<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2 class="pull-left">Reports</h2>
		<div class=" pull-left reportsH">
			<a title="Reports"><i class="fa fa-pie-chart"></i>Requirements Posted</a>
			<div class="RePop">
				<ul>
					<li><a href="reports.html"><i class="fa fa-user"></i>Requirements Posted</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Client Requirements</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Applicants Received</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Applicants</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Short  listed</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Interview</a></li>
					<li><a href="reports.html"><i class="fa fa-user"></i>Offer</a></li>
				</ul>
			</div>
		</div>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
     
      <div class="timesheet_head">

<div class="pull-left reportBtns">
	  <div class="reportBtn">
		<select class="form-control select-bg">
		  <option>To day</option>
		  <option>Yesterday</option>
		  <option>1 week</option>
		  <option>2 weeks</option>
		  <option>1 month</option>
		  <option>2 months</option>
		  <option>Custom</option>
		</select>
	 </div>	
	 <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker"><i class="fa fa-calendar"></i></span>
	 <span class="dashboard-datepkr"><input type="text" class="input-sm hasDatepicker" id="datepicker1"><i class="fa fa-calendar"></i></span>			
</div>
                     
        <ul style="padding: 2px 220px 2px 0px;">
          <li><i class="fa fa-filter"></i><span>Fliter</span><i class="glyphicon glyphicon-play" id="status-pop"></i>|
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
          <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        <div class="remove_search">
          <input type="text" placeholder="search" />
        </div>
      </div>
      
	<div class="reportsCount">
		<ul>
			<li title="Applicants">
				<span><i class="fa fa-users"></i></span><b>100</b>
			</li>
			<li title="Shortlisted" class="colorTwo">
				<span><i class="fa fa-file-text"></i></span><b>100</b>
			</li>
			<li title="Submit to Client">
				<span><i class="glyphicon glyphicon-send"></i></span><b>100</b>
			</li>
			<li title="Interviews" class="colorTwo">
				<span><img src="images/interview1.png" alt=""/></span><b>100</b>
			</li>
			<li title="Offers">
				<span><i class="fa fa-tags"></i></span><b>100</b>
			</li>
			<li title="Rejected" class="colorTwo">
				<span><i class="glyphicon glyphicon-trash"></i></span><b>100</b>
			</li>											
		</ul>
	</div>

<div class="project_tabs_inn_table vendor_wrap_inn_table">
                  <table>
                    <tbody>
                      <tr>
                        <th>Name</th>
                        <th>Provider</th>
                        <th>Score</th>
                        <th>Description</th>
                        <th>Valid from</th>
                        <th>Valid to</th>
                        <th>Action</th>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <div><span>TCS Pvt Ltd</span></div>
                        </td>
                        <td>
                          <div><span>Web developer</span></div>
                        </td>
                        <td>
                          <div><span>300</span></div>
                        </td>
                        <td>
                          <div><span>Description Description</span></div>
                        </td>
                        <td>
                          <div><span>30/08/2016</span></div>
                        </td>
                        <td>
                          <div><span>30/09/2016</span></div>
                        </td>
                        <td>
                          <div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div>
                        </td>
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
        </ul>
        <div class="clearfix"></div>
      </nav>
    </div>


  </div>
</section>
<!-- Footer Area -->
<footer class="footer-area clearfix">
  <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
  <div class="powered-by"> Powered by: businessintelli.com </div>
</footer>
<!-- Footer Area --> 

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script>
<script src="js/bootstrap-datetimepicker.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 

<script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});

});
 		</script> 
<script>

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// project side bar tabs
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   event.preventDefault();
});

$(".addSkills").click(function(){
	$(".add-skills").toggleClass('add-skills-active');
	});
$(".close-div").click(function(){
	$(".add-skills").removeClass('add-skills-active');
	});
$(".add-skills").click(function(e){
	 e.stopPropagation();
 });


// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 
<script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
<script>
$(document).ready(function(){
	// Levels adding script
$(".add-offer").click(function(){
	 $(".addOffer-Req").toggleClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-interview").click(function(){
	 $(".addInterview-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-applicant").click(function(){
	 $(".addApplicant-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
 });
$(".reminder").click(function(){
	 $(".reminder-div").toggleClass("reminder-div-active");
 });

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
   
// new SlideToggle
 $("#id1").click(function(){
	  $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
	 });	 
   
   
   
</script>
<script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
        </script>
<div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
  <ul class="list-unstyled">
    <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
    <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
    <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
  </ul>
</div>

    
      <script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
		}
		function openPop(){
			$("div.cand-advsrch-popup").toggle();
		}
	</script> 

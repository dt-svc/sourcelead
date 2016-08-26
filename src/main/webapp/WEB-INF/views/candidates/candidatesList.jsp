<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>


<!-- Content Area --> 
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Candidate list</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head candidateHead">
        <ul>
          <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            <div class="export_pop">
              <ul>
                <li>PDF<i class="fa fa-file-pdf-o"></i></li>
                <li>Excel<i class="fa fa-file-excel-o"></i></li>
                <li>Word<i class="fa fa-file-word-o"></i></li>
              </ul>
            </div>
          </li>
         <!--  <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>
          <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li> -->
          <li><a href="<c:url value='/candidate'/>" title=""><i class="fa fa-plus"></i>Add Candidate</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
        
            <div class="remove_search">
            	<input type="text" placeholder="search" />
            </div>          
        
      </div>
      <div class="candidate_wrap_table">
        <table>
          <tbody>
          <thead>
            <tr>
              <th><input type="checkbox" id="checkbox1"/></th>
              <th>Name</th>
              <th>Designation</th>
              <th>Position type</th>
              <th>Email</th>
              <th>Mobile</th>
              <th>Address</th>
              <th>Last Update</th>
            </tr>
          </thead>
          <tr>
            <td><input type="checkbox" id="checkbox12"/></td>
            <td><div class="proj_show"><span>Vannam vannam vannam vannam</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div ><span>vannam@domain.com</span></div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="checkbox1"/></td>
            <td><div class="proj_show"><span class="td_overflow">Broadridge</span><i class="fa fa-binoculars"></i></div></td>
            <td><div><span>Web designer</span></div></td>
            <td><div>Full Time</div></td>
            <td><div>domain.com</div></td>
            <td><div>9000 000 000</div></td>
            <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">Hyderabad, Hyderabad, Hyderabad</span></div></td>
            <td><div>11/08/2016</div></td>
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
    
    <!-- Compnay details -->
    <div class="side_mask">
      <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
       <div class="profile_details clearfix" style="margin-bottom:20px;">
                    <div class="col-md-6">
                        <div class="media">
                          <div class="media-left image-parent">
                          <div class="imageParent">
                          	<img src="./images/24.jpg" alt="candidate" title="Candidate">
                          </div>
                          </div>
                          <div class="media-body candidate-details">
                            <h4 class="media-heading">Jaden Smith</h4>
                            <ul class="list-unstyled">
                              <li><strong>Business Analyst - Full Time</strong></li>
                              <li>Experience - 4 years</li>
                            </ul>
                        </div>
                      </div>
                      <div class="other-detials">
                      <div class="professinal-details">
                      	<ul class="list-inline list-unstyled"><li>BA</li><li>BRS</li><li>SRS</li><li>Agile</li><li>SDLC</li></ul>
                      </div>
                      <div class="socialnetwork-details">
                      <ul class="list-inline list-unstyled">
                                      <li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>
                                      <li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>
                                      <li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>
                                      <li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>
                                      <li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>
                                    </ul>
                      </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <ul class="list-unstyled can-perdt">
                        <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">jadensmith@hotmail.com</a></li>
                        <li><i class="fa fa-phone"></i>+91 9900223355</li>
                        <li><i class="fa fa-location-arrow"></i>Hyderabad, TS, India</li>
                        <li><i class="fa fa-rupee"></i>200/Per Hour - Current Rate / Salary</li>
                        <li><i class="fa fa-rupee"></i>300/Per Hour - Expected Rate / Salary</li>
                      </ul>
                    </div>
				</div>
        <div class="proj_close"></div>
        <div class="project_side_wrap_inn_tabs">
          <ul class="project-tabs-menu">
            <li class="current"><a href="#tab-1" onclick="candidateProfile();">Profile</a></li>
            <li><a href="#tab-2">Resume</a></li>
            <li><a href="#tab-3">Experience</a></li>
            <li><a href="#tab-4">Education</a></li>
            <li><a href="#tab-5">Certificate</a></li>
            <li><a href="#tab-6">Activities</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="project_tabs">
                <jsp:include page="candidatesProfile.jsp"></jsp:include>
                <jsp:include page="candidatesResumes.jsp"></jsp:include>
                <jsp:include page="candidatesExperience.jsp"></jsp:include>
                <jsp:include page="candidatesEducation.jsp"></jsp:include>
                <jsp:include page="candidatesCertificates.jsp"></jsp:include>
                <jsp:include page="candidatesActivities.jsp"></jsp:include>
          </div>
        </div>
      </div>
    </div>
    <!-- Compnay details Ends --> 
    <!-- Project --> 
  </div>
</section>


<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/jquery-ui.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
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
	
	
	// Levels adding script
$(".addUser-org").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".assign-org").click(function(){
	 $(".collapse-div").toggleClass("show-div");
	 $(".collapse-div-adduser").removeClass("show-div");
 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
});
</script>
<script>
  $(function() {
    $("#validFrom, #edu-endDate, #edu-startDate, #validTo").datepicker();
  } );
  </script>
  
	<div class="theme-color">
        <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
        <ul class="list-unstyled">
            <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
            <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
            <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
        </ul>
	</div>  
  
</body>
</html>

<jsp:include page="../masterFooter.jsp" />
  <!--   HTML   -->	  
  <%
    if (request.getRequestURL().toString().indexOf("www.") == -1 
            && request.getRequestURL().toString().indexOf("localhost") == -1
            && request.getRequestURL().toString().indexOf("qa.") == -1
            && request.getRequestURL().toString().indexOf("staging.") == -1
			&& request.getRequestURL().toString().indexOf("dev.") == -1
            && request.getRequestURL().toString().indexOf("demo.") == -1) {
        response.sendRedirect("https://www.sourcelead.com/login");
    } %>
<jsp:include page="../staticHeader.jsp" /> 

<div class="homebanner_parent clearfix positionRelative">
<div class="clearfix homebanner_desktop">
	<img src="/resources/static-web/images/sourceleadBannerHome.jpg" alt="Sourcelead Banner" title="Banner" class="img-responsive">
</div>
<div class="clearfix homebanner_mobile">
	<img src="/resources/static-web/images/sourceleadBanner_mobile.jpg" alt="Sourcelead Banner" title="Banner" class="img-responsive">
</div>
<div class="clearfix"></div>
	<div class="home-slider">
		<div class="row">
	<div class="homebannerslider clearfix positionRelative col-xs-12 col-sm-12 col-md-8 col-lg-8">
			<div class="carousel slide" data-ride="carousel" id="carouselHome">
			<div class="carousel-inner clearfix">
				<div class="item active">
				<a href="/ats.html" title="Application Tracking System" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/002-strategy-4.png" class="media-object" style="width:60px" alt="ATS">
					</div>
					<div class="media-body">
						<h4 class="media-heading">Application Tracking System</h4>
						<p>In the realm of passion for innovation in recruitment process, Sourcelead provides an automated process for the talent search. Be it a niche profile or a generic role, you have access to marketplace using ATS. </p>
					</div>
					</div>
				</a>
				</div>
				<div class="item">
					<a href="/vms.html" title="Vendor Management System" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/017-skills.png" class="media-object" style="width:60px" alt="atsSVG">
					</div>
					<div class="media-body">
						<h4 class="media-heading">Vendor Management System</h4>
						<p>We at Sourcelead identify your business need and help recruitment firms design integrated business model. Analyze the requirement, Increase your revenues, and expand your business horizons with Sourcelead VMS solution. </p>
					</div>
					</div>
					</a>
				</div>
				<div class="item">
					<a href="/crms.html" title="Client Relevance Management System" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/041-management.png" class="media-object" style="width:60px" alt="atsSVG">
					</div>
					<div class="media-body">
						<h4 class="media-heading">Client Relevance Management System</h4>
						<p>SourceLead’s constant endeavor to build sincere relationship with clients gave an insight to our unique Client Relevance Management System (CRMS). With an emphasis on ’RELEVANCE’.</p>
					</div>
					</div>
					</a>
				</div>
				
				<div class="item">
					<a href="/pms.html" title="Project Management System" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/018-pie-graphic.png" class="media-object" style="width:60px" alt="atsSVG">
					</div>
					<div class="media-body">
						<h4 class="media-heading">Project Management System</h4>
						<p>Implement the integration of various HR workflows which encompass multiple tasks.
SourceLead PMO helps you stay dedicated and disciplined on the regular project management track.</p>
					</div>
					</div>
					</a>
				</div>
				
				<div class="item">
					<a href="/hrms.html" title="HRMS & Contract Management" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/045-human-resources.png" class="media-object" style="width:60px" alt="atsSVG">
					</div>
					<div class="media-body">
						<h4 class="media-heading">HRMS &amp; Contract Management</h4>
						<p>Provides an advanced technology solution combining the workforce management &amp; human capital management. It connects the payroll, talent acquisition, employee on boarding, internal and exit HR policies ...</p>
					</div>
					</div>
					</a>
				</div>
				<div class="item">
					<a href="/ims.html" title="Invoice Management" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/010-analysis.png" class="media-object" style="width:60px" alt="atsSVG">
					</div>
					<div class="media-body">
						<h4 class="media-heading">Invoice Management</h4>
						<p> A comprehensive solution with streamlined and automated end-to-end invoice management process.
This provides a quick and easy access to information for bill generation and payments.</p>
					</div>
					</div>
					</a>
				</div>
				
				<div class="item">
					<a href="/immigration.html" title="Immigration" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/air-freight.png" class="media-object" style="width:60px" alt="atsSVG">
					</div>
					<div class="media-body">
						<h4 class="media-heading">Immigration</h4>
						<p>A comprehensive solution with streamlined and automated end-to-end invoice management process.
This provides a quick and easy access to information for bill generation and payments.</p>
					</div>
					</div>
					</a>
				</div>
				
				<div class="item">
					<a href="/mergers-acquisition.html" title="Mergers &amp; Acquisition" target="_blank">
					<div class="media">
					<div class="media-left">
						<img src="/resources/static-web/images/049-options.png" class="media-object" style="width:60px" alt="atsSVG">
					</div>
					<div class="media-body">
						<h4 class="media-heading">Mergers &amp; Acquisition (M &amp; A)</h4>
						<p>content covers multiple client deals, including US-target and non-US-target companies. Our highly confidential database is used globally, to monitor and report on transaction activity, perform market share analysis...</p>
					</div>
					</div>
					</a>
				</div>
			</div>
			</div>
	
		<div class="carouselarrowBts">
			<ul class="list-unstyled list-inline mb0">
				<li><i class="fa fa-angle-left" href="#carouselHome" data-slide="prev"></i></li>
				<li><i class="fa fa-angle-right" href="#carouselHome" data-slide="next"></i></li>
			</ul>
			
		</div>	
	</div>
	
	<div class="registerbuttons clearfix col-xs-12 col-sm-12 col-md-4 col-lg-4">
	<ul class="list-unstyled pull-right">
	<li>
		<a href="/register" target="_blank">Sign Up</a>
	</li>
	<li>
		<a href="javascript:void(0)" id="demoRequest" onClick="requestDemo()">GET A DEMO</a>
	</li>
	</ul>
	</div>
</div>
</div>
</div>
<div class="clearfix"></div>

<div class="container-fluid">
	<div class="row">
			<div class="aboutSourcelead">
				<div class="col-sm-12 col-sm-offset-0 col-md-12 col-md-offset-0 col-lg-10 col-lg-offset-2">
			<div class="aboutinner-content animated bounceIn" style="margin-top: 45px;">
				<div class="media">
    <div class="media-left">
     <img class="mr" src="/resources/static-web/images/aiImageHome.jpg" alt="Artificial Intelligency" title="Artificial Intelliency" style="max-height: 250px;">
    </div>
    <div class="media-body ml text-xs-center">
      <h2>Artificial Intelligence</h2>
      <p>SourceLead is a <span>SaaS</span> based end to end talent management solution, which uses the most powerful and cutting edge <span>Artificial Intelligence</span> for efficient sourcing.  Our next level technology enables you to easily manage, search, and track all applicants from Requisition to On Boarding.  All it takes is a click to nail the best talent effectively.</p>
		<div class="clearfix"></div>
      <a href="/about-us.html" class="btnReadmore mt">Read More</a>
    </div>
  </div>
			</div>
			</div>
		</div>
	</div>
</div>

<!--  new section div -->
<!-- Widget-->
<section class="container-fluid">
		<div class="clearfix row">
			<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 marketplaceWidgetParent slideInLeft animated ">
				<div class="clearfix text-xs-center">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6">
							<img src="/resources/static-web/images/marketplacebg_Home.png" alt="marketimg" class="img-responsive" title="Market Place">
						</div>
					<div class="col-xs-12 col-sm-12 col-md-6">
						<h3>Market Place</h3>
						<p>A one stop centre to to cater to your employment needs.  We curate our network with a diverse set of opportunities across ...</p>
						<div class="clearfix mt"></div>
						<a href="/market-place.html" class="btn btn-primary customizedButton mt" title="Marketplace">Know more</a>
					</div>
						</div>
			</div>
				</div>
			<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 blogWidgetParent slideInRight animated">
				<div class="clearfix text-xs-center">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6">
							<img src="/resources/static-web/images/blogbg_Home.png" alt="Blog image" title="Blog" class="img-responsive">
						</div>
					<div class="col-xs-12 col-sm-12 col-md-6">
					<h3>Blog</h3>
					<p>
						Visit our blog to know more about Integrating with multiple job boards with automated On & Off boarding, Women Empowerment , Recruiting through social media and more ...
					</p>
					<div class="clearfix mt"></div>
					<a href="/blog/all.html" class="btn btn-primary customizedButton mt">Know more</a>
				</div>
						</div>
					</div>
			</div>
	</div>	
</section>
<!-- Widget-->

<!-- Widget Features -->
<section class="container-fluid doMore slideInLeft animated">
<div class="clearfix row">
	<div class="col-xs-12 col-md-6 col-lg-7">
		<img src="/resources/static-web/images/doMoreBg.png" alt="domore" title="Do more" class="img-responsive domoreImg">
	</div>		
	<div class="col-xs-12 col-md-6 col-lg-5">
				<div class="homeFeatures-inner clearfix mt">
			<ul class="list-inline list-unstyled clearfix">
				<li>
				<a href="/ats.html" title="Application Tracking System" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/002-strategy-4.png" style="width:50px" alt="ATS">
					</div>
						<h5 class="media-heading mt5">ATS</h5>
				</a>
				</li>
				<li>
				<a href="/vms.html" title="VMS" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/017-skills.png"  style="width:50px" alt="ATS">
					</div>
						<h5 class="media-heading mt5">VMS</h5>
				</a>
				</li>
				<li>
				<a href="/crms.html" title="CRM" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/041-management.png" style="width:50px" alt="CRM">
					</div>
						<h5 class="media-heading mt5">CRM</h5>
				</a>
				</li>
				<li>
				<a href="/pms.html" title="PMS" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/018-pie-graphic.png"  style="width:50px" alt="ATS">
					</div>
						<h5 class="media-heading mt5">PMS</h5>
				</a>
				</li>
				<li>
				<a href="/hrms.html" title="HRMS" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/045-human-resources.png"  style="width:50px" alt="ATS">
					</div>
						<h5 class="media-heading mt5">HRMS</h5>
				</a>
				</li>
				<li>
				<a href="/ims.html" title="IMS" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/010-analysis.png" style="width:50px" alt="ATS">
					</div>
						<h5 class="media-heading mt5">IMS</h5>
				</a>
				</li>

				<li>
				<a href="/immigration.html" title="Immigriation" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/air-freight.png" style="width:50px" alt="Immigriation">
					</div>
						<h5 class="media-heading mt5">Immigriation</h5>
				</a>
				</li>
				<li>
				<a href="/mergers-acquisition.html" title="Mergers & Accquisition" target="_blank" class="text-center">
					<div class="clearfix">
						<img src="/resources/static-web/images/049-options.png" style="width:50px" alt="mergers-acquisition">
					</div>
						<h5 class="media-heading mt5">M &amp; A</h5>
				</a>
				</li>
			</ul>
			<div class="clearfix"></div>
			<div class="clearfix mt5">
				<p>Designed an intuitive approach that focuses our innovative technology &amp; expertise into
powerful, easy to use solutions.</p>
			</div>
		</div>
			</div>
	</div>	
</section>
<!-- Widget Features -->

<section class="rightSkills clearfix container-fluid  slideInRight animated">
	<!-- Clients Div -->
		 <section class="clearfix">
				<div class="col-xs-12 col-sm-12 col-md-5 col-lg-6">
						<img src="/resources/static-web/images/jobSearch.png" alt="Skills Image" class="img-responsive">
				</div>
			<div class="col-xs-12 col-sm-12 col-md-7 col-lg-6">
				<div class="rightSkills_inner">
				<h3>Are you having Right Skills<b class="small text-muted">&amp;</b><br/>Worrying about finding right job <span>?</span></h3>
				<h4>Now your are at right place... <br/> You can find your perfect job can fit career for today.</h4>
				<div class="clearfix mt"></div>
				<a href="/search-jobs.html" class="btnReadmore" title="Search Job">Read more</a>
				</div>
			</div>
		</section>
</section>


<!--  new section div ends -->







<!-- Testimonials -->
<section class="testimonialDiv container-fluid">
  <div class="container">
	  <div class="row">
	  <div class="col-sm-8 col-sm-offset-4 col-md-7 col-md-offset-5 col-lg-8 col-lg-offset-4">
	  <div id="testimonal" class="carousel slide" data-ride="carousel"> 
  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    
    <div class="item active">
      <!-- Testimonials starts herer -->
     <div class="clearfix row">
    <div class="col-md-1">
		<i class="fa fa-quote-left fa-3x text-muted"></i> 
	</div>
		 <div class="col-md-11">
	  <div class="testimonial-media">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Kim, <small class="pl5">Accounts Manager, USA</small></h4>
      </div>
      <div class="testimonial-mediasbody">
        <p><i class="text-muted paddingRight"></i>
        SourceLead is saving my hours of effort through customised reports generation &amp; helped our business to reach next level
        </p>
      </div>
			 </div>
    </div>
  <!-- Testimonials starts herer -->
    </div>

    <div class="item">

   <!-- Testimonials starts herer -->
     <div class="clearfix row">
      <div class="col-md-1">
		<i class="fa fa-quote-left fa-3x text-muted"></i> 
	</div>
		 <div class="col-md-11">
		 <div class="testimonial-media">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Pradeep MG,<small class="pl5">India</small></h4>
      </div>
      <div class="testimonial-mediasbody">
        <p> <i class="text-muted paddingRight"></i>It is a pleasure to be with SourceLead. SourceLead is a User-friendly or Independent platform which makes our work more intuitive and easy.</p>
      </div>
			 </div>
    </div>
  <!-- Testimonials starts herer -->
   
    </div>

    <div class="item">
<!-- Testimonials starts herer -->
     <div class="clearfix row">
		 <div class="col-md-1">
		<i class="fa fa-quote-left fa-3x text-muted"></i> 
	</div>
		 <div class="col-md-11">
      <div class="testimonial-media">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Prasad, <small class="pl5">USA</small></h4>
      </div>
      <div class="testimonial-mediasbody">
        <p> <i class="text-muted paddingRight"></i>SourceLead is very easy &amp; User-friendly applicant tracking system that helping our recruitment process effective to track Requirements, Best Resources</p>
      </div>
			 </div>
    </div>
  <!-- Testimonials starts herer -->
    </div>
    
    <div class="item">

   <!-- Testimonials starts herer -->
     <div class="clearfix row">
		 <div class="col-md-1">
		<i class="fa fa-quote-left fa-3x text-muted"></i> 
	</div>
		 <div class="col-md-11">
      <div class="testimonial-media">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Srinivas,<small class="pl5">India</small></h4>
      </div>
      <div class="testimonial-mediasbody">
        <p> <i class="text-muted paddingRight"></i>The decision to become a SourceLead partner has been brilliant move. We now have all the features we need &amp; confidence to move forward to grow our business.</p>
      </div>
		 </div>
    </div>
  <!-- Testimonials starts herer -->
   
    </div>
  </div>
   <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#testimonal" data-slide-to="0" class="active"></li>
    <li data-target="#testimonal" data-slide-to="1"></li>
    <li data-target="#testimonal" data-slide-to="2"></li>
    <li data-target="#testimonal" data-slide-to="3"></li>
  </ol>
 </div>
  </div>
		  </div>
  </div>
</section>
<!-- Testimonials -->


<section class="container-clientsdiv">
<!-- Clients Div -->
   <section class="clearfix clientsdiv-inner">
    <div class="container">
	  <section id="owl-carousel-div" class="mt">
      <div class="clearfix">
        <div class="large-12 columns">
          <div class="owl-carousel owl-theme owlcarousel-custom">
            <div class="itemowl">
              <img src="/resources/static-web/images/businessintelli_revised.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/soft.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/cloudious.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/sarian_logo.png" alt="Client Image" class="img-responsive">
            </div>
             <div class="itemowl">
              <img src="/resources/static-web/images/businessintelli_revised.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/soft.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/cloudious.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/sarian_logo.png" alt="Client Image" class="img-responsive">
            </div>
          </div>
          </div>
          </div>
    </section>
	    
	        
    
  </div>
</section>
<!-- Clients Div -->
</section>
<section class="homewidgetBenefitDiv text-center hidden">
 <div class="homewidgetBenefitDiv-inner">
  <div class="container-fluid">
 	<div class="row">
 	  <div class="col-md-6">
 			<div class="demoTraildiv clearfix">
 			<h2>See What SourceLead<br>can do for you</h2>
  		<div class="clearfix"></div>
  			<div class="col-md-4">
  			<a href="javascript:void(0)" class="btn btn-demo mr btn-block">Book A Demo</a>
  			</div>
  			<div class="col-md-8 freetrial">
				<input type="text" placeholder="Email" class="pull-left">
				<button type="button" class="btn btn-trial pull-left">FREE TRIAL</button>
			</div>
  		</div>
  		</div>
	  </div>
  </div>
  </div>
<!-- sing in  -->
</section>
<!-- Footer -->

<!-- Benefits of sourcelead  -->
<section class="homewidget-benefitsdiv text-center">
  <div class="container-fluid">
 		<div class="col-md-12">
 			<h2>Benefits of SourceLead</h2>
 			<p>Integration which provides a one-stop solution to entire recruitment life cycle with a single login to Clients, Vendors and Candidates.</p>
 		</div>
  		<div class="col-md-12 clearfix">
  			<a href="javascript:void(0)" class="btn btn-custom mr" onClick="requestDemo();">GET A DEMO</a>
  			<a href="/register" class="btn btn-custom" target="_blank">SIGNUP</a>
  		</div>
  </div>
</section>
<!-- Benefits of sourcelead  -->
<jsp:include page="../staticFooter.jsp" />
<script type="text/javascript">
window.onload = function() {
	$("#indexpageID").addClass("slactive");
	var demoRequest = '${demoRequest}';
	if(demoRequest != null && demoRequest != '' && demoRequest != 'undefined'){
		$("#demoRequest").click();
	}
}
</script>
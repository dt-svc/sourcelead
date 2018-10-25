  <!--   HTML   -->	  
  <%
    if (request.getRequestURL().toString().indexOf("www.") == -1 
            && request.getRequestURL().toString().indexOf("localhost") == -1
            && request.getRequestURL().toString().indexOf("qa.") == -1
            && request.getRequestURL().toString().indexOf("staging.") == -1
            && request.getRequestURL().toString().indexOf("demo.") == -1) {
       /*  response.sendRedirect("https://www.sourcelead.com/login"); */
    } %>
<jsp:include page="../staticHeader.jsp" /> 
<div class="homeBanner">
<div class="home-caption">
<h2>Bridging talent through technology</h2>
	<div class="registerbuttons clearfix">
	<ul class="list-unstyled">
	<li>
		<a href="/register" target="_blank">Sign Up</a>
	</li>
	<li>
		<a href="javascript:void(0)" id="demoRequest" onClick="requestDemo()">GET A DEMO</a>
	</li>
	<li class="hidden">
		<a href="javascript:void(0)">GET A FREE TRIAL</a>
	</li>
	</ul>
	</div>
</div>

<div class="clearfix"></div>
</div>
<div id="myCarousel" class="carousel slide carousel-custom hidden" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators ">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
      <li data-target="#myCarousel" data-slide-to="3"></li>
<!--      <li data-target="#myCarousel" data-slide-to="3"></li>
      <li data-target="#myCarousel" data-slide-to="4"></li>
      <li data-target="#myCarousel" data-slide-to="5"></li>-->
    </ol>
    <!-- Wrapper for slides -->
    <div class="carousel-inner" role="listbox">
      <div class="item active" style="min-height: 300px; color: #f60;">
        <div class="container">
        	<div class="row">
        		<div class="col-md-4">
        			ATS
        		</div>
        		<div class="col-md-4">
        			ATS
        		</div>
        		<div class="col-md-4">
        			ATS
        		</div>
        		<div class="col-md-4">
        			ATS
        		</div>
        		<div class="col-md-4">
        			ATS
        		</div>
        		<div class="col-md-4">
        			ATS
        		</div>
        		<div class="col-md-4">
        			ATS
        		</div>
        	</div>
        </div>
      </div>
             <div class="item">
        <img src="/resources/static-web/images/bannerCandidate.jpg" alt="Banner">
        <div class="carousel-caption animated slideInLeft">
       		<div class="col-md-6 col-md-offset-6">
       		<div class="pull-right">
					<a href="javascript:void(0)" class="btn btn-success btn-lg ml">Sign up for free</a>
					<a href="javascript:void(0)" class="btn btn-danger btn-lg ml mr">GET A DEMO</a>
					<a href="javascript:void(0)" class="btn btn-primary btn-lg">GET A FREE TRIAL</a>
      		</div>
       		</div>
       		<h1 class="col-md-7 text-right hidden">Bridging the talent through technology ...</h1>
        </div>      
      </div>
      <div class="item">
        <img src="/resources/static-web/images/bannerSearchProfile.jpg" alt="Banner">
        <div class="carousel-caption animated slideInLeft">
       		<div class="col-md-6 col-md-offset-6">
       		<div class="pull-right">
					<a href="javascript:void(0)" class="btn btn-success btn-lg ml">Sign up for free</a>
					<a href="javascript:void(0)" class="btn btn-danger btn-lg ml mr">GET A DEMO</a>
					<a href="javascript:void(0)" class="btn btn-primary btn-lg">GET A FREE TRIAL</a>
      		</div>
       		</div>
       		<h1 class="col-md-7 text-right hidden">Bridging the talent through technology ...</h1>
        </div>      
      </div>
      <div class="item">
        <img src="/resources/static-web/images/bannerCrowd.jpg" alt="Banner">
        <div class="carousel-caption animated slideInLeft">
       		<div class="col-md-6 col-md-offset-6">
       		<div class="pull-right">
					<a href="javascript:void(0)" class="btn btn-success btn-lg ml">Sign up for free</a>
					<a href="javascript:void(0)" class="btn btn-danger btn-lg ml mr">GET A DEMO</a>
					<a href="javascript:void(0)" class="btn btn-primary btn-lg">GET A FREE TRIAL</a>
      		</div>
       		</div>
       		<h1 class="col-md-7 text-right hidden">Bridging the talent through technology ...</h1>
        </div>      
      </div>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
</div>
<div class="container-fluid">
	<div class="row">
			<div class="aboutSourcelead">
			<div class="col-md-6">
			<div class="aboutinner-content animated bounceIn">
				<h2>SourceLead is</h2>
				<p>a <span>SaaS</span> based end to end talent management solution, <br> which uses the most powerful and cutting edge <span>Artificial Intelligence</span><br>for efficient sourcing. Our next level technology enables you to easily manage, <br>search, and track all applicants from Requisition to On Boarding.<br>All it takes is a click to nail the best talent effectively.</p>
				 <a href="/about-us.html" class="btn btn-primary mt">Read More</a>
			</div>
			</div>
			<div class="col-md-6">
			<div class="clearfix home-demovideo animated bounceIn">
				<div class="gallery clearfix list-unstyled">
					<a href="https://www.youtube.com/watch?v=Vbi24I70NYU&t=263s" rel="prettyPhoto" title="Know about Jobs"><img src="/resources/static-web/images/videoBanner.jpg" alt="YouTube" class="img-responsive"/></a>
				</div>
        	  </div>
			</div>
		</div>
	</div>
</div>
<!-- Testimonials -->
<section class="testimonialDiv container-fluid">
  <div class="clearfix text-center">
    <h2 class="test-muted">See what people are saying ...</h2>
  </div>
  <div class="clearfix"></div>
  <div class="container">
    <div id="testimonal" class="carousel slide" data-ride="carousel"> 
  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    
    <div class="item active">
      <!-- Testimonials starts herer -->
     <div class="clearfix">
      <div class="testimonial-media text-center">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Kim, <small class="text-muted pl5">Accounts Manager, USA</small></h4>
      </div>
      <div class="testimonial-mediasbody text-center">
        <p><i class="fa fa-quote-left fa-3x text-muted paddingRight"></i>
        SourceLead is saving my hours of effort through customised reports generation &amp; helped our business to reach next level
        </p>
      </div>
    </div>
  <!-- Testimonials starts herer -->
    </div>

    <div class="item">

   <!-- Testimonials starts herer -->
     <div class="clearfix">
      <div class="testimonial-media text-center">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Pradeep MG,<small class="text-muted pl5">India</small></h4>
      </div>
      <div class="testimonial-mediasbody text-center">
        <p> <i class="fa fa-quote-left fa-3x text-muted paddingRight"></i> It is a pleasure to be with SourceLead. SourceLead is a User-friendly or Independent platform which makes our work more intuitive and easy.</p>
              </div>
    </div>
  <!-- Testimonials starts herer -->
   
    </div>

    <div class="item">
<!-- Testimonials starts herer -->
     <div class="clearfix">
      <div class="testimonial-media text-center">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Prasad, <small class="text-muted pl5">USA</small></h4>
      </div>
      <div class="testimonial-mediasbody text-center">
        <p> <i class="fa fa-quote-left fa-3x text-muted paddingRight"></i> SourceLead is very easy &amp; User-friendly applicant tracking system that helping our recruitment process effective to track Requirements, Best Resources </p>
      </div>
    </div>
  <!-- Testimonials starts herer -->
    </div>
    
    <div class="item">

   <!-- Testimonials starts herer -->
     <div class="clearfix">
      <div class="testimonial-media text-center">
        <div class="clearfix mt5 mb10"></div>
        <h4 class="media-heading text-primary">Srinivas,<small class="text-muted pl5">India</small></h4>
      </div>
      <div class="testimonial-mediasbody text-center">
        <p> <i class="fa fa-quote-left fa-3x text-muted paddingRight"></i>The decision to become a SourceLead partner has been brilliant move. We now have all the features we need &amp; confidence to move forward to grow our business.</p>
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
</section>
<!-- Testimonials -->
<section class="container-clientsdiv">
<!-- Clients Div -->
   <section class="clearfix clientsdiv-inner">
    <div class="container">
       <div class="clearfix text-center animated bounceIn clientsdiv-hdr">
	    <h2 class="text-center animated bounceIn">Our Clients</h2>
	  </div>
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
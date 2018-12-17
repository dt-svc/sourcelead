<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
 <!-- Content Area -->
  <section class="content-wraper">
    <div class="content-dashboard clearfix mt">
        <div class="row">
          <div class="col-lg-12">
            <div class="content-area mt clearfix">
              <exception></exception>
              <div class="blank-pages-tech">
                <p class="text-center">
                	<img src="/resources/images/alertone.png" alt="Alert Tone">
                </p>
                <p class="big-txt">Well, this is Awkward ...</p>
                <p>Due to technical difficulties we are unable to process your request.
                 <br/>Please try again later. <span class="text-info"></span></p>
                <p class="positionRelative move-btn btn-group">
                <i class="fa fa-home btn btn-primary"></i>
                <a href="/app/dashboard" type="button" class="btn btn-primary btnerror-custom">Go to Home Page<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>
                </span></a>
                </p>
                
                <p class="positionRelative move-btn btn-group">
                <i class="fa fa-ticket btn btn-primary"></i>
                <a href="/app/addSourceleadTicket" type="button" class="btn btn-primary btnerror-custom">Report This<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>
                </span></a>
                </p>
              </div>
            </div>
          </div>
        </div>
    </div>
  </section>
  <!-- Content Area Ends-->  

<jsp:include page="masterFooter.jsp" />

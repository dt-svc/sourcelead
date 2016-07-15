<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Dashboard</div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="clearfix section-action-btns hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><i class="fa fa-filter" aria-hidden="true"></i>&nbsp;Filers&nbsp;</a>
			<select>
			<option>All</option>
				<option>This Week</option>
				<option>Month</option>
				<option>Year</option>
			</select>
		  </li>
        </ul>
	</section>
        <div class="clearfix row db-widget-sm">

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-orange">
				<i class="fa fa-floppy-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text">Saved</p>
              <p class="info-box-number">1000</p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-blue">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text">Waiting</p>
              <p class="info-box-number"><strong>90</strong></strong></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-green">
				<i class="fa fa-check-circle-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text">Approved</p>
              <p class="info-box-number"><strong>900</strong></strong></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-red">
				<i class="fa fa-ban" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text">Rejected</p>
              <p class="info-box-number"><strong>10</strong></strong></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->
		  
		</div>
		
		<!-- Pie chart -->
			<div class="clearfix row">
				<section class="clearfix">
				 <div class="dropdown col-xs-2">
					<button class="btn btn-primary dropdown-toggle  btn-block" type="button" data-toggle="dropdown">Select Client
					&nbsp;&nbsp;<span class="caret"></span></button>
					<ul class="dropdown-menu">
					  <li><a href="#">Client</a></li>
					  <li><a href="#">Client</a></li>
					  <li><a href="#">Client</a></li>
					</ul>
				  </div>
				  
				 
				  <div class="dropdown col-xs-2">
					<button class="btn btn-success dropdown-toggle  btn-block" type="button" data-toggle="dropdown">Select Projects
					&nbsp;&nbsp;<span class="caret"></span></button>
					<ul class="dropdown-menu">
					  <li><a href="#">Project-1</a></li>
					  <li><a href="#">Project-2</a></li>
					  <li><a href="#">Project-3</a></li>
					</ul>
				  </div>
				</section>
				<!-- Highchart -->
				<div class="col-md-12">
				<section class="clearfix piechart-area mt">
						<h3>Timesheet Highchart</h3>
				</section></div>
				<!-- Highchart Ends -->
			</div>
				
				
			</div>
		<!-- Pie chart -->
  </section>
  <!-- Content Area Ends-->

<jsp:include page="masterFooter.jsp" />

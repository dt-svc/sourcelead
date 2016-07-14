<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>List of Users</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Users Dashboard</div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="clearfix section-action-btns">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="javascript:void(0)"><i class="fa fa-th"></i>&nbsp;All</a></li>
          <li><a href="javascript:void(0)"><i class="fa fa-check" aria-hidden="true"></i>&nbsp;Role&nbsp;</a>
			<select>
				<option>Administrator</option>
				<option>User</option>
				<option>Developer</option>
			</select>
		  </li>
		  <li>
		  <a href="javascript:void(0)">
		  <i class="fa fa-user"></i>&nbsp;Emp&nbsp;&nbsp;</a>
			<span class="action-search"><input type="text" value=""><i class="fa fa-search"></i></span>
		  </li>
		  <li><a href="<c:url value='/userCreation'/>"><i class="fa fa-user-plus"></i>&nbsp;Create</a></li>
		  <li><a href="javascript:void(0)"><i class="fa fa-upload"></i>&nbsp;Bulk Imports</a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
        </div>
      </div>
      </div>
  </section>
      <!-- Content Area Ends--> 

      <!-- Footer Area -->
      <footer class="footer-area clearfix">
        <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
        <div class="powered-by"> Powered by: businessintelli.com </div>
      </footer>
      <!-- Footer Area --> 
  

    
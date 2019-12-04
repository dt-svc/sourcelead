<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

 <!-- Content Area -->
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Edit Background Verification By Category</h2></div>
      <div class="pull-right brud-crum">Home &raquo; Edit Background Verification</div>
    </section>
    
    <div class="project_wrap clearfix">
      <div class="clearfix mt minHeightcol5"> 
        <div class="col-md-5">
          <label class="clearfix">Id</label>
          <div class="clearfix mt5"><input type="text" class="form-control" value="4002" readonly></div>
        </div>
        <div class="col-md-5">
          <label class="clearfix">Name</label>
          <div class="clearfix mt5"><input type="text" class="form-control" value="Rajkumar Santhoshi" readonly></div>
        </div>
        <div class="col-md-5">
          <label class="clearfix">Education / Academic credentials Status</label>
          <div class="clearfix mt5">
              <select class="sel">
                <option>-- Select --</option>
                  <option>Not Initiated</option>
                  <option>Initiated</option>
                  <option>In Progress</option>
                  <option>Completed</option>
                  <option>Not Approved</option>
                  <option>Onhold</option>
                </select>          	
        </div>
        </div>

        <div class="col-md-5">
            <label class="clearfix">Prior Employment Status</label>
            <div class="clearfix mt5">
                  <select class="sel">
                    <option>-- Select --</option>
                      <option>Not Initiated</option>
                      <option>Initiated</option>
                      <option>In Progress</option>
                      <option>Completed</option>
                      <option>Not Approved</option>
                      <option>Onhold</option>
                    </select>          	
            </div>
          </div>
          
          <div class="col-md-5">
              <label class="clearfix">Personal Information Status</label>
              <div class="clearfix mt5">
                    <select class="sel">
                      <option>-- Select --</option>
                        <option>Not Initiated</option>
                        <option>Initiated</option>
                        <option>In Progress</option>
                        <option>Completed</option>
                        <option>Not Approved</option>
                        <option>Onhold</option>
                      </select>          	
              </div>
            </div> 
            <div class="col-md-5">
              <label class="clearfix">Health Status</label>
              <div class="clearfix mt5">
                  <select class="sel">
                      <option>-- Select --</option>
                        <option>Not Initiated</option>
                        <option>Initiated</option>
                        <option>In Progress</option>
                        <option>Completed</option>
                        <option>Not Approved</option>
                        <option>Onhold</option>
                      </select>    
              </div>
            </div>
            <div class="col-md-5">
              <label class="clearfix">Criminal Record Status</label>
              <div class="clearfix mt5">
                  <select class="sel">
                      <option>-- Select --</option>
                        <option>Not Initiated</option>
                        <option>Initiated</option>
                        <option>In Progress</option>
                        <option>Completed</option>
                        <option>Not Approved</option>
                        <option>Onhold</option>
                      </select>    
              </div>
            </div>
            <!--  <div class="col-md-5">
              <label class="clearfix">Credit</label>
              <div class="clearfix mt5">
                  <select class="sel">
                      <option>-- Select --</option>
                        <option>Not Initiated</option>
                        <option>Initiated</option>
                        <option>In Progress</option>
                        <option>Completed</option>
                        <option>Not Approved</option>
                        <option>Onhold</option>
                      </select>    
              </div>
            </div>-->

            <div class="col-md-5">
                <label class="clearfix">Comments</label>
                <div class="clearfix mt5">
                    <textarea placeholder="Description" class="form-control" cols="2"></textarea>   
                </div>
              </div>

              <div class="clearfix"></div>
        </div>
	<div class="footerstyelsNew">
	<div class="newAddWidth clearfix">
		<div class="pull-right mr">
		  <input value="Save" class="btn btn-primary" type="button">
			<a href="my-performance-goals.html"><input value="Cancel" class="btn btn-default ml" type="button"></a>
		</div>
	</div>
	</div>
       	 	
	</div>
  </section>

<jsp:include page="../masterFooter.jsp" /> 	
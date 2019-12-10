<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

 <!-- Content Area -->
<section class="content-wraper">
<script src="${SourceLeadWeb_vmsUrl}/resources/js/bgcCandidate.js?staticTime=${staticTime}"></script>
<script type="text/javascript">
var uuid = '${bgcCandidate.uuid eq null ? "--" : bgcCandidate.uuid}';
var name = '${bgcCandidate.fullName eq null ? "--" : bgcCandidate.fullName}';
var educationCheckStaus = '${bgcCandidate.educationCheckStaus eq null ? "--" : bgcCandidate.educationCheckStaus}';
var priorEmployersCheckStatus = '${bgcCandidate.priorEmployersCheckStatus eq null ? "--" : bgcCandidate.priorEmployersCheckStatus}';
var personalInfoStatus = '${bgcCandidate.personalInfoStatus eq null ? "--" : bgcCandidate.personalInfoStatus}';
var healthCheckStatus = '${bgcCandidate.healthCheckStatus eq null ? "--" : bgcCandidate.healthCheckStatus}';
var crimeCheckStatus = '${bgcCandidate.crimeCheckStatus eq null ? "--" : bgcCandidate.crimeCheckStatus}';	
var comments = '${bgcCandidate.comments eq null ? "--" : bgcCandidate.comments}';
$(document).ready(function() {
	bgc.editBgcCandidate();
	
});
	</script>
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Edit Background Verification By Category</h2></div>
      <div class="pull-right brud-crum">Home &raquo; Edit Background Verification</div>
    </section>
    <form id="editBgcCandidateForm" name="editBgcCandidateForm">
    <div class="project_wrap clearfix">
      <div class="clearfix mt minHeightcol5"> 
        <div class="col-md-5">
          <label class="clearfix">Id</label>
          <div class="clearfix mt5"><input type="text" id = "bgcId" name="uuid" class="form-control" value="--" readonly></div>
        </div>
        <div class="col-md-5">
          <label class="clearfix">Name</label>
          <div class="clearfix mt5"><input type="text" id = "bgcName" name="name" class="form-control" value="--" readonly></div>
        </div>
        <div class="col-md-5">
          <label class="clearfix">Education / Academic credentials Status</label>
          <div class="clearfix mt5">
              <select class="sel" id = "educationId" name="educationCheckStaus">
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
                  <select class="sel" id = "employmentId" name="priorEmployersCheckStatus">
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
                    <select class="sel" id = "personalInfoId" name="personalInfoStatus">
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
                  <select class="sel" id = "healthId" name="healthCheckStatus">
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
                  <select class="sel" id = "criminalRecordId" name="crimeCheckStatus">
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
                    <textarea placeholder="Description" id = "commentsId" name="comments" class="form-control" cols="2"></textarea>   
                </div>
              </div>

              <div class="clearfix"></div>
        </div>
	<div class="footerstyelsNew">
	<div class="newAddWidth clearfix">
		<div class="pull-right mr">
		  <input id="saveBgcCandidate" value="Save" class="btn btn-primary" type="button" onclick="bgc.saveEditBgcCandidate();">
			<a href="my-performance-goals.html"><input value="Cancel" class="btn btn-default ml" type="button"></a>
		</div>
	</div>
	</div>
       	 	
	</div>
	</form>
	
  </section>
<jsp:include page="../masterFooter.jsp" /> 	
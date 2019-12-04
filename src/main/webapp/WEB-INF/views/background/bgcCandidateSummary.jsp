<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script src="${SourceLeadWeb_vmsUrl}/resources/js/bgcCandidate.js?staticTime=${staticTime}"></script>
<!-- Content Area -->
  <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix">
					<div class="pull-left">
					  <h2>Background Verification</h2>
					</div>
					<div class="pull-right brud-crum">Home &raquo; Background Verification</div>
	</section>

				<section class="immigraTabsWrap">
						<div class="immHeadWrap">
								<div class="immHead clearfix">
										<div class="col-md-4">
											<h2>${bgcCandidate.fullName eq null ? "--" : bgcCandidate.fullName}</h2>
											<h3>${bgcCandidate.email eq null ? "--" : bgcCandidate.email}</h3>
										</div>
										<div class="col-md-8">
												<div class="progress mt mb0">
														<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"
														aria-valuemin="0" aria-valuemax="100" style="width:${bgcCandidate.updatedPercentage eq null ? '50%' : bgcCandidate.updatedPercentage}">
														Profile Updated ${bgcCandidate.updatedPercentage eq null ? "50%" : bgcCandidate.updatedPercentage}
														</div>
													  </div>
										</div>						  
									</div>

									<div class="clearfix"></div>
									<div class="immigraHead">
										<ul class="immigraTabs">
										  <li class="current"><a href="#tab-Details">Personal / Identification</a></li>
										  <li><a href="#tab-education">Education</a></li>
										  <li><a href="#tab-previousEmployer">Previous Employer</a></li>
										  <li><a href="#tab-criminialCheck">Legal and Criminial Check</a></li>
										  <div class="clearfix"></div>
										</ul>
									</div>
						</div>


    

  
	  <div id="tab-Details" class="immigraTabs_inn">
		  <div class="immigraDetailsOut">
			  <div class="immigraDetailsCta" id="details">
				  <h2>Personal / Identification</h2>
				  <ul>
					  <li class="clearfix"><span>Aadhar Card</span><p>: ${bgcCandidate.aadharNumber eq null ? "--" : bgcCandidate.aadharNumber}</p></li>
					  <li class="clearfix"><span>PAN Card</span><p>: ${bgcCandidate.panNumber eq null ? "--" : bgcCandidate.panNumber}</p></li>
					  <li class="clearfix"><span>Address</span><p>: ${bgcCandidate.address eq null ? "--" : bgcCandidate.address}</p></li>
					  <li class="clearfix"><span>Family</span><p>: --</p></li>
					  <li class="clearfix"><span>Attach document</span><p>: <a>--<i class="fa fa-download ml"></i></a></p></li>
					  <li class="clearfix"><span>Comments</span><p>: ${bgcCandidate.comments eq null ? "--" : bgcCandidate.comments}</p></li>
				  </ul>
			  </div>		  
			  <div class="clearfix"></div>
		  </div>		  		  		  
		
	  </div>

	  <div id="tab-education" class="immigraTabs_inn">
	  <div class="immigraDetailsCta" id ="educationList">
		<jsp:include page="bgcCandidateEducationSummary.jsp"/>
		</div>
	 </div>
	 
	  <div id="tab-previousEmployer" class="immigraTabs_inn">
	  <div class="immigraDetailsCta" id = "employmentList">
			<jsp:include page="bgcCandidateEmploymentSummary.jsp"/>
		</div>
	  </div>
	  
	  <div id="tab-criminialCheck" class="immigraTabs_inn">
			<div class="immigraDetailsCta">
				<h2>Legal and Criminial Check</h2>
				<ul>
					<li class="clearfix"><span>Financial Check</span><p>: --</p></li>
					<li class="clearfix"><span>Criminal Check</span><p>: ${bgcCandidate.crimeCheckStatus eq null ? "--" : bgcCandidate.crimeCheckStatus}</p></li>
					<li class="clearfix"><span>Bank and Property Check</span><p>: --</p></li>
					<li class="clearfix"><span>Salary Check</span><p>: --</p></li>
					<li class="clearfix"><span>Attach document</span><p>: --</p></li>
				</ul>
			</div>	
			</div>
	  </div>
	</section>
    
  </section>
  <!-- Content Area -->
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
<script src="js/jquery-ui.min.js"></script> 

<script>
var vmsUrl = '${SourceLeadWeb_vmsUrl}';
var candidateUuid = '${bgcCandidate.candidateUUID}';
// project side bar tabs
$(document).ready(function() {
	$("#tab-Details").show()
    $(".immigraTabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".immigraTabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
        if(tab == "#tab-education"){
        	bgc.getBgcCandidateEducationSummary(candidateUuid);
        }else if(tab == "#tab-previousEmployer"){
        	bgc.getBgcCandidateEmploymentSummary(candidateUuid);        	
        }
    });
	
	
});	


	</script>
    <!-- Script for Linked Invoice pop -->
    <script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        $('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        $('.side_mask').fadeOut(200);
        });
    </script>
    <!-- Script for Linked Invoice pop -->

<jsp:include page="../masterFooter.jsp" /> 	
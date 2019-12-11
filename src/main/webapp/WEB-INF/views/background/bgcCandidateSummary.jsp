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
	  <div id ="detailsLoading"></div>
		  <!-- <div class="immigraDetailsOut"> -->
			  	  <jsp:include page="bgcCandidateDetailsSummary.jsp"/>
			  <div class="clearfix"></div>
		  <!-- </div> -->		  		  		  
		
	  </div>

	  <div id="tab-education" class="immigraTabs_inn">
	  <div id ="educationLoading"></div>
		<jsp:include page="bgcCandidateEducationSummary.jsp"/>
	 </div>
	 
	  <div id="tab-previousEmployer" class="immigraTabs_inn">
	  <div id = "employmentLoading"></div>
			<jsp:include page="bgcCandidateEmploymentSummary.jsp"/>
	  </div>
	  
	  <div id="tab-criminialCheck" class="immigraTabs_inn">
	  <div id = "legalAndCriminalLoading"></div>
			<jsp:include page="bgcCandidateLegalAndCriminalSummary.jsp"/>
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
var bgcUuid = '${bgcCandidate.uuid}'
// project side bar tabs
$(document).ready(function() {
	showLoading("detailsLoading");
	bgc.getBgcCandidateSummary(bgcUuid);
	$("#tab-Details").show()
    $(".immigraTabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".immigraTabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
        if(tab == "#tab-Details"){
        	showLoading("detailsLoading");
        	bgc.getBgcCandidateSummary(bgcUuid);
        }else if(tab == "#tab-education"){
        	showLoading("educationLoading");
        	bgc.getBgcCandidateEducationSummary(candidateUuid);
        }else if(tab == "#tab-previousEmployer"){
        	showLoading("employmentLoading");
        	bgc.getBgcCandidateEmploymentSummary(candidateUuid);        	
        }else if(tab == "#tab-criminialCheck"){
        	showLoading("legalAndCriminalLoading");
        	bgc.getBgcCandidateLegalAndCriminalSummary(bgcUuid);
        }
    });
	
	
});	

function showLoading(loadingId){
	$("#"+loadingId).attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; right:0px; margin:0px auto; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
}

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
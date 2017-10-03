<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Vendor On-board</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Vendor On-board
		</div>
	</section>
	
	<div id="loading"></div>
	<div class="newPrDetails newPrDetailsPMO"> 
		<div class="newPrTabs">
		<ul class="newPrTabsMenu">
				<li class="current" onclick="getVendorDetails();"><a href="#tab-a" data-toggle="tab">Vendor Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="vendorDocuments">Documents</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="vendorTasks">Tasks</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
			  
		  <div class="project_tabs">
			 <div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt" id="tab-a">
			 	<form:form id="vendor_onboard_form" name="vendor_onboard_form" method="post">
			 		<div class="clearfix form-horizontal inner-content">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								<div class="form-group">
									<label class="col-md-3" for="Project Id">Org Name</label>
										<div class="col-md-9" id="clientNameDiv">
											<input type="text" class="form-control" id="orgName"
												name="orgName" value="${SourceLeadWeb_orgLocations.orgLocationName}"
												field-name="Vendor Name" readonly="readonly"
												data-validation="required validate_length length2-250"
												value=""> <input type="hidden" id="orgLocationId" /> <span
												class="hidden">Enter Vendor Name</span>
										</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-3" for="Project Id">Website</label>
										<div class="col-md-9" id="clientNameDiv">
											<input type="text" class="form-control" id="website"
												name="website" value="${SourceLeadWeb_orgLocations.website}"
												field-name="Website" readonly="readonly"
												data-validation="required validate_length length2-250"
												value=""> <input type="hidden" id="orgLocationId" /> <span
												class="hidden">Enter Vendor Name</span>
										</div>
								</div>
								
							</div>
						</div>
					</div>
			 	
			 	</form:form>
			 </div>
		 </div>
		</div>
	</div>
</section>	




<script>
	$(document).ready(function() {
		var vendorDocuments = document.getElementById("vendorDocuments");
		if(vendorDocuments){
			vendorDocuments.href = "#";
			vendorDocuments.className = "disabledLink";
		}
		
		var vendorTasks = document.getElementById("vendorTasks");
		if(vendorTasks){
			vendorTasks.href = "#";
			vendorTasks.className = "disabledLink";
		}
		
		$("#tab-a").show()
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
	});
</script>


<jsp:include page="../masterFooter.jsp" />
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
			 	
			 		<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Client Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								
								<div class="col-xs-12 col-sm-9 col-md-9">
									<div class="form-group">
											<label class="col-md-4">Client Name :</label>
											<p class="tbldata-dotdot-me" id="clientName"></p>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Website :</label>
										<p class="tbldata-dotdot-me" id="website"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Location :</label>
										<p class="tbldata-dotdot-me" id="location"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Address :</label>
										<p class="tbldata-dotdot-me" id="address"></p>
								    </div>
								
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Client Contact Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								
								<div class="col-xs-12 col-sm-9 col-md-9">
									<div class="form-group">
											<label class="col-md-4">Contact Name :</label>
											<p class="tbldata-dotdot-me" id="clientContactName"></p>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Email :</label>
										<p class="tbldata-dotdot-me" id="clientContactEmail"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Phone :</label>
										<p class="tbldata-dotdot-me" id="clientContactPhone"></p>
								    </div>
								
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Vendor Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								
								<div class="col-xs-12 col-sm-9 col-md-9">
									<div class="form-group">
											<label class="col-md-4">Vendor Name :</label>
											<p class="tbldata-dotdot-me" id="vendorName"></p>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Website :</label>
										<p class="tbldata-dotdot-me" id="vendorWebsite"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Location :</label>
										<p class="tbldata-dotdot-me" id="vendorLocation"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Address :</label>
										<p class="tbldata-dotdot-me" id="vendorAddress"></p>
								    </div>
								
								</div>
							</div>
						</div>
					</div>
					
					
			 	
			 	
			 	 <form:form id="vendor_onboard_form" name="vendor_onboard_form" method="post">
			 		<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Company Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								<div class="form-group">
									<label class="col-md-3" for="Project Id">Client Name</label>
										<div class="col-md-9" id="clientNameDiv">
											<input type="text" class="form-control" id="orgName"
												name="orgName" value=""
												field-name="Vendor Name" readonly="readonly"
												data-validation="required validate_length length2-250"
												value=""> <input type="hidden" id="orgLocationId" /> <span
												class="hidden">Client Name</span>
										</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-3" for="Project Id">Website</label>
										<div class="col-md-9" id="clientNameDiv">
											<input type="text" class="form-control" id="website"
												name="website" value=""
												field-name="Website" readonly="readonly"
												data-validation="required validate_length length2-250"
												value=""> <input type="hidden" id="orgLocationId" /> <span
												class="hidden">Website</span>
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
		
		
		getVendorOnboardDetails();
	
		
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

<script>
function getVendorOnboardDetails(){
	var vendorOnboardId = '${vendorOnboardId}';
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getVendorOnboardDetailsByVendorOnboardId?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId, 
	        success: function(json) {
	        	$("#clientName").html(json.clientName);
	        	if(json.clientWebsite != null){
	        		$("#website").html(json.clientWebsite);
	        	}else{
	        		$("#website").html("-------");
	        	}
	        	
	        	$("#location").html(json.clientLocation);
	        	
	        	if(json.clientAddress != null){
	        		$("#address").html(json.clientAddress);
	        	}else{
	        		$("#address").html("-------");
	        	}
	        	
	        	$("#vendorName").html(json.vendorName);
	        	if(json.vendorWebsite != null){
	        		$("#vendorWebsite").html(json.vendorWebsite);
	        	}else{
	        		$("#vendorWebsite").html("-------");
	        	}
	        	$("#vendorLocation").html(json.vendorLocation);
	        	if(json.vendorAddress != null){
	        		$("#vendorAddress").html(json.vendorAddress);
	        	}else{
	        		$("#vendorAddress").html("-------");
	        	}
	        	

	        	$("#clientContactName").html(json.clientContactName);
	        	$("#clientContactEmail").html(json.clientContactEmail);
	        	if(json.clientContactPhone != null){
	        		$("#clientContactPhone").html(json.clientContactPhone);
	        	}else{
	        		$("#clientContactPhone").html("-------");
	        	}
	        },
	    }); 
  }
</script>


<jsp:include page="../masterFooter.jsp" />
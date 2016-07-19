<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>::Org Settings</title>
</head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<meta charset="utf-8">
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>

<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>

<script>
  var loadFile = function(event) {
    var output = document.getElementById('orgImage');
    output.src = URL.createObjectURL(event.target.files[0]);
  };
</script>

<body>



<!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Org Settings</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Account Settings</div>
    </section>
    <div class="content-area clearfix">
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab org-settings-area">
		  <div class="tab-content panel-body">
			<div class="clearfix">
			  <form:form class="login-form clearfix"  id="orgSettings_form"  name="orgSettings_form" method="post" > 
			  
			  <div class="col-xs-12 col-sm-3 col-md-3 text-center well">
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" alt="Org Image" class="comp-image img-responsive">
				  <div class="btn-group uploadfile">
				  <button type="button" class="btn btn-primary">
				  <i class="fa fa-upload"></i></button>
				  <label class="btn btn-primary">
                    Upload / Change<input type="file" name="file" onchange="loadFile(event)" style="display: none;">
                </label>
				  </div>
				  </div>
			  </div>
			  
			  			
				<div class="col-xs-12 col-sm-9 col-md-9">
					<div class="row">
						<div class="col-md-7">
							<div class="form-group">
							<label class="col-md-3">Company</label>
							<div class="col-md-9">
							<input type="text" name="companyName" class="form-control"  field-name="Company" data-validation="required"  value="${SourceLeadWeb_orgName}" />
							</div>
							</div>
							<div class="form-group">
							<label class="col-md-3">Website</label>
							<div class="col-md-9">
							<input type="text" readonly="readonly" class="form-control" value="${SourceLeadWeb_website}">
							</div>
							</div>
							<div class="form-group comp-textarea">
							<label class="col-md-3">About</label>
							<div class="col-md-9">
							<textarea rows="2" name="aboutCompany" field-name="About" class="form-control">${SourceLeadWeb_org.orgDescription}</textarea>
							</div>
							</div>
						</div>
						<div class="col-md-5">
						<div class="form-group">
							<label class="col-md-3">Country</label>
							<div class="col-md-9">
							<input type="text" class="form-control" readonly="readonly" value="${SourceLeadWeb_country}" />
							</div>
						</div>
						<div class="form-group mt">
							<label class="col-md-3">State</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="state" field-name="State"  value="${SourceLeadWeb_orgLocations.state}" />
							</div>
						</div>
						<div class="form-group mt">
							<label class="col-md-3">City</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="city" field-name="City"  value="${SourceLeadWeb_orgLocations.city}" />
							</div>
						</div>
						<div class="form-group mt">
							<label class="col-md-3">Zip</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="zipcode" field-name="Zip"   value="${SourceLeadWeb_orgLocations.zipCode}" />
							</div>
						</div>
						<div class="form-group mt">
							<label class="col-md-3">Phone</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="phone" field-name="phone"  value="${SourceLeadWeb_orgLocations.phone}" />
							</div>
						</div>
						<div class="form-group mt">
							<label class="col-md-3">Fax</label>
							<div class="col-md-9">
							<input type="text"  class="form-control" name="fax" field-name="fax"  value="${SourceLeadWeb_orgLocations.fax}" />
							</div>
						</div>
						</div>
						</div>
					<div class="clearfix">
					<div class="pull-right">
					<input type="submit" value="Save" class="btn btn-primary mr">
					<input type="button" value="Cancel" class="btn btn-default" onClick="self.location='/sourcelead/dashboard'">
					</div>
					</div>
					</div>
					</form:form>
				</div>

			
			</div>
			</div>
		   </div>
		</div>
			<!-- Tab for Profile setting-area ends -->
		  </div>
		  </div>
  </section>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends-->

</body>

    

<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#orgSettings_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          	    var frm = $('#orgSettings_form').serialize();
	          	    frm = "testvalue=testvalue&"+frm;
	          		var con = JSON.stringify(frm);
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		var form = $('#orgSettings_form')[0];
	           		var formData = new FormData(form);
	          		
	                  $.ajax({
	              	      type: "POST",
	              	      data: formData,
	              	      contentType : 'application/json; charset=utf-8',
	              	      enctype: 'multipart/form-data',
	          	          processData: false,
	          	          contentType:false,
	              	      dataType : 'json',
	              	      
	                    	  url: sourceleadUrl+"/saveOrgSettings?${_csrf.parameterName}=${_csrf.token}&json="+con,
	                      	success: function(result) {
	  			        	 if((result == 'home')){
	  			        		location.replace(sourceleadUrl+"/orgSettings");  
	  			        	} 
	  			        },  
	                  });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#orgSettings_form').showHelpOnFocus();
         $('#orgSettings_form').validateOnBlur(false, validationSettings); 
            
     
	</script> 



<jsp:include page="masterFooter.jsp" />
</html>







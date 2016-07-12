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
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
  var loadFile = function(event) {
    var output = document.getElementById('orgImage');
    output.src = URL.createObjectURL(event.target.files[0]);
  };
</script>

<body>

  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Org Settings</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/home'/>">Home</a> &raquo; Account Settings</div>
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
		  <div class="accountsettings-tab">
		  <div class="tab-content panel-body">
			<div class="clearfix">
			  <div class="col-xs-12 col-sm-3 col-md-3 text-center well">
				  
			  <form:form class="login-form clearfix"  id="orgSettings_form"  name="orgSettings_form" method="post" >
				  <div class="profile-img-container" style="position:relative;">
				   <img id="orgImage" onerror="predefineOrgImage(this);" src="<c:url value='/displayOrgImage/${SourceLeadWeb_orgCode}'/>" alt="Org Image" class="img-responsive">
				  <div class="btn-group uploadfile">
				  <button type="button" class="btn btn-primary">
				  <i class="fa fa-upload"></i></button>
				  <label class="btn btn-primary">
                    Upload / Change<input type="file" name="file" onchange="loadFile(event)" style="display: none;" required>
                </label>
				  </div>
				  </div>
				  
				  
				  <div class="clearfix mt">
					<div class="text-center">
					  <input type="submit" class="btn btn-primary mr" value="Save">
					  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/home'">
					</div>
				  </div>
			  </form:form>
				  
			  </div>

			<div class="col-xs-12 col-sm-9 col-md-9">
				  <div class="row mt org-details">
					<div class="col-md-8">
						<div class="form-group">
						<span class="col-md-3">Company:</span>
						<label class="col-md-8">${SourceLeadWeb_orgName}</label>
						</div>
						<div class="form-group">
					<span class="col-md-3">
					Website:
					</span>

						<label class="col-md-8">
					<a href="javascript:void(0)">${SourceLeadWeb_org.webSite}</a>
					</label>
				  </div>
					</div>
	
					</div>
				 
				 </div>
			</div>
			</div>
		   </div>
		</div>
			<!-- Tab for Profile setting-area ends -->
		  </div>
		  </div>
		  </div>
  </section>


</body>
</html>

<jsp:include page="masterFooter.jsp" />




<script type="text/javascript">
        $('#orgSettings_form')
           .submit(function(e) {
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
            });
</script>
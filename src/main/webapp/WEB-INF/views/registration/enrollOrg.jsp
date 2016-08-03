<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

  <!-- Content Area -->
  <section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Org Registraion</h2>
    </div>
    <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; <a href="enrolledOrgsList">Orgs List</a> &raquo; Enroll Org </div>
  </section>
  <div class="content-area clearfix">
  <section class="action-btns clearfix">
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
              <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
            </ul>
          </section>
    <div class="clearfix form-horizontal inner-content">
      <form:form id="createOrg_form"  name="createOrg_form" method="post">
      <div class="row">
        <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9">
              <input type="text" class="form-control" name="First Name" value="${firstName}" field-name="First Name" data-validation="required validate_Space validate_length length2-100" />
              <span class="hidden">First Name</span>
            </div>
          </div>
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <label class="col-md-3">Last Name <span class="text-danger">&nbsp;*</label>
            <div class="col-md-9">
               <input type="text" name="lastName"  value="${lastName}" class="form-control" field-name="Last Name" data-validation="required validate_Space validate_length length2-100"  />
             <span class="hidden">Last Name</span>
            </div>
          </div>
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <label class="col-md-3">Company<span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9">
              <input type="text" name="companyName"  value="${companyName}" class="form-control"  field-name="Company Name" data-validation="required validate_Space validate_length length2-250" />
             <span class="hidden">Company</span>
            </div>
          </div>
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <label class="col-md-3">Website<span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9">
              <input type="text" name="website"  value="${website}"   id="companyWebsite"  onblur="validateWebsite();validateCompanyEmail();"  field-name="Company Website" data-validation="required validate_Space validate_domain validate_length length2-100"    class="form-control" />
            <span class="hidden">Website</span>
            </div>
          </div>
          
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9">
              <input type="text" name="email" id="email"   value="${email}" onblur="validateCompanyEmail();validateWebsite();" field-name="Company Email" data-validation="required validate_Space validate_email validate_length length2-100" class="form-control"  />
            <span class="hidden">Email</span>
            </div>
          </div>
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <label class="col-md-3">Country<span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9">
              <select class="form-control select-bg" name="country" field-name="Country" data-validation="required" value="${country}" >
                <option value="" >Select Location</option>
                <option value="Australia">Australia</option>
                <option value="France">France</option>
                <option value="India">India</option>
                <option value="Singapore">Singapore</option>
                <option value="United Kingdom">United Kingdom</option>
                <option value="United States">United States</option>
              </select>
               <span class="hidden">Country</span>
            </div>
          </div>
          <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
            <label class="col-md-3">Modules<span class="text-danger">&nbsp;*</label>
            <div class="col-md-9 checkbox">
            <ul class="list-unstyled pull-left">
            	<li>
                <label class="mr pull-left">
                <input type="checkbox" id="isRecruiting" onclick="getRecruitmentTypes()" onblur="moduleCheckValidation(this)">
                Recruitment &nbsp;
                </label>
                	<ul class="list-unstyled pull-left tree-checkbox" id="recrutmentTypes" style="display: none;">
                    	<li>
                            <label>
                            <input type="checkbox" value="">
                            ATS
                            </label>
		                </li>
                        
                    	<li>
                            <label class="mr">
                                <input type="checkbox" value="">
                                VMS
                            </label>
		                </li>
                    </ul>
                </li>
            </ul>
              <label class="mr">
                <input type="checkbox" id="isTimeSheet" onblur="moduleCheckValidation(this)">
                Timesheet</label>
            </div>
            
            <input type="hidden"  id="moduleValue" name="module" value="${module}" field-name="Module" data-validation="required"/> 
          </div>

      </div>
      
      <div class="form-footer clearfix row">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Register" class="btn btn-primary" id="register_button" onclick="createOrg();">
          <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/enrolledOrgsList'/>'"  class="btn btn-default ml">
        </div>
      </div>
    </div>
    </form:form>
    </div>
    
    <!-- Content Area Ends--> 
  </div>
</section>
      <!-- Content Area Ends--> 

      <jsp:include page="../masterFooter.jsp" />
      
      
      
  <script type="text/javascript">
	function moduleCheckValidation(id){
		if (document.getElementById('isRecruiting').checked || document.getElementById('isTimeSheet').checked) {
		document.getElementById("moduleValue").value = id.value;
		}else{
			$('#moduleValue').val(''); 
		}
	} 
</script>

 <script type="text/javascript">
function getRecruitmentTypes(){
		if (document.getElementById('isRecruiting').checked) {
		 document.getElementById("recrutmentTypes").style.display="";
		}else{
			document.getElementById("recrutmentTypes").style.display="none";
		}
	} 
</script>

    
<script type="text/javascript">
 function createOrg() {
	   if ($('#createOrg_form').validate(false, validationSettings)){ 
	    	e.preventDefault();
        	var frm = $('#createOrg_form').serializeFormJSON();
        	var con = JSON.stringify(frm);
		    con = con.replace(/[{}]/g, ""); 
     		var sourceleadUrl ='${sourceleadUrl}';
     		$("#register_button").attr("disabled", "disabled");
     		$( "#register_button" ).addClass('button').val('Processing..');
     		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
           	$.ajax({
      	      type: "POST",
      	      contentType : 'application/json; charset=utf-8',
      	      dataType : 'json',
            	  url: sourceleadUrl+"/companyRegistration?&${_csrf.parameterName}=${_csrf.token}&json="+con,
            			  
            			  
              	success: function(result) {
		        	if((result == 'home')){
		        		location.replace(sourceleadUrl+"/dashboard");  
		        	}
		        	
		        },  
          }); 
            
        	    return true;
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#createOrg_form', function() {
		$("#createOrg_form").showHelpOnFocus();
		$("#createOrg_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>  

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 



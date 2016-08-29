<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>

<!-- these files for autocomplete with tagit -->
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>

	<!-- end files for autocomplete with tagit -->
	<script type="text/javascript">
	window.onload = function () {
		cscInit();
	 };
	</script>
	 
	 <script>
		$(document).ready(function() {
			document.getElementById("candidate_form").reset();
		});
	</script>
	 
	<style>
	.clsDatePicker {
		z-index: 100000 !important;
	}
	</style>

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Add Candidate Details</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Client Contact Details</div>
  </section>
  <div class="content-area content-new clearfix">
  <form:form id="candidate_form" name="candidate_form"  enctype='multipart/form-data' >
    <div class="row form-horizontal mt myForm">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
        
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        <div class="col-md-12 clearfix">
        <div class="well">
          <div class="input-group col-md-6" style="margin:0 auto">
            <label class="input-group-btn"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" name="uploadBtn" id="uploadBtn"  class="hidden upload">
              </span> 
           </label>
             <input type="text" class="form-control" readonly id="uploadFile" name="docFile" type="file" name="" placeholder="Upload Resume ...">
          </div>
          </div>
        </div>
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        
        <div class="form-group">
          <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}"  field-name="First Name" data-validation="required validate_Space validate_length length2-100" value="">
            <div class="input-group hidden">
              <div class="input-group-btn">
                <button type="button" class="btn btn-danger dropdown-toggle"
                                	data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Action <span class="caret"></span> </button>
                <ul class="dropdown-menu">
                  <li><a href="#">Action</a></li>
                  <li><a href="#">Another action</a></li>
                  <li><a href="#">Something else here</a></li>
                  <li role="separator" class="divider"></li>
                  <li><a href="#">Separated link</a></li>
                </ul>
              </div>
              <!-- /btn-group -->
              <input type="text" class="form-control" aria-label="...">
            </div>
            <!-- /input-group --> 
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Last Name</label>
          <div class="col-md-9">
           <input type="text" class="form-control" id="lastName" name="lastName" value="${lastName}"  field-name="Last Name" data-validation="validate_Space validate_length length2-100" data-validation-optional="true" value="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="email" name="email" value="${email}"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email"  value="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Mobile</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="phone" name="phone" value="${phone}"  field-name="Mobile Number" data-validation="validate_Space validate_length length9-20" data-validation-optional="true" value="">
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">Country</label>
          <div class="col-md-9">
            <input type="hidden" class="form-control" id="CountryAutocomplete" name="country"
						name="country" field-name="Country" value="${loginCountryName}" data-validation="validate_Space" data-validation-optional="true"/> 
			<ul id="country" class="form-control" onkeypress="getAllCountries('${sourceleadUrl}');"></ul>
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">State</label>
          <div class="col-md-9">
            <input type="hidden" id="stateAutoComplete" name="state"
					field-name="State" class="form-control" value="" data-validation="validate_Space" data-validation-optional="true">
			<ul id="state"  onkeypress="getStates('${sourceleadUrl}');"></ul>
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">City</label>
          <div class="col-md-9">
           <input type="hidden" id="cityAutoComplete" name="city" field-name="State" class="form-control" value="" data-validation="validate_Space" data-validation-optional="true">
			    <ul id="city"  onkeypress="getCities('${sourceleadUrl}');"></ul>
          </div>
        </div>  
        
        <div class="form-group">
          <label class="col-md-3">Address</label>
          <div class="col-md-9">
           <input type="text" class="form-control" id="address" name="address" value="${address}"  field-name="Address" data-validation="validate_Space validate_length length2-250" data-validation-optional="true">
          </div>
        </div>
        
        <div class="form-group">
         <label class="col-md-3">Designation</label>
         <div class="col-md-9">
           <div class="row div-padding">
           <div class="col-xs-8 col-md-8">
             <input type="text" id="designation" name="designation" value="${designation}" field-name="Designation"  data-validation="validate_Space validate_length length2-250" data-validation-optional="true"  value="" class="form-control">
           </div>
             <div class="col-xs-4 col-md-4">
             <select class="form-control" id="designationType" name="designationType">
                 <option><a href="#">C2C</a></option>
                 <option><a href="#">W-2</a></option>
                 <option><a href="#">Part Time</a></option>
                 <option><a href="#">Full Time</a></option>
                 <option><a href="#">Other</a></option>
             </select>
             </div>
           </div>
         </div>
       </div>

        <div class="form-group">
          <label class="col-md-3">Experience</label>
          <div class="col-md-9">
            <div class="row">
             <div class="col-xs-6 col-md-6">
             		<select class="form-control" id="experienceYears" name="experienceYears">
             		  <option selected disabled>Year</option>
             		<option>0</option>
             		<option>1</option>
             		<option>2</option>
             		<option>3</option>
             		<option>4</option>
             		<option>5</option>
             		<option>6</option>
             		<option>7</option>
             		<option>8</option>
             		<option>9</option>
             		<option>10</option>
             		<option>11</option>
             		<option>12</option>
             		<option>13</option>
             		<option>14</option>
             		<option>15</option>
             		<option>16</option>
             		<option>17</option>
             		<option>18</option>
             		<option>19</option>
             		<option>20+</option>
             		</select>
             </div>
             
             <div class="col-xs-6 col-md-6">
             		<select class="form-control" id="experienceMonths" name="experienceMonths">
             		<option selected disabled>Month</option>
             		<option>0</option>
             		<option>1</option>
             		<option>2</option>
             		<option>3</option>
             		<option>4</option>
             		<option>5</option>
             		<option>6</option>
             		<option>7</option>
             		<option>8</option>
             		<option>9</option>
             		<option>10</option>
             		<option>11</option>
             		<option>12</option>
             		</select>
             </div>
            </div>
          </div>
        </div>
       
         <div class="form-group">
         <label class="col-md-3">Current Rate</label>
         <div class="col-md-9">
           <div class="row div-padding">
           <div class="col-xs-8 col-md-8">
             <input type="text" id="currentRate" name="currentRate" value="${currentRate}" field-name="Current Rate"  value="" class="form-control">
           </div>
             <div class="col-xs-4 col-md-4">
             <select class="form-control" id="currentRateCurrency" name="currentRateCurrency">
                 <option><a href="#">&#x20B9;</a></option>
                 <option><a href="#">&#x20A0;</a></option>
                 <option><a href="#">$</a></option>
             </select>
             </div>
           </div>
         </div>
       </div>
        
         <div class="form-group">
         <label class="col-md-3">Expected Rate</label>
         <div class="col-md-9">
           <div class="row div-padding">
           <div class="col-xs-8 col-md-8">
            <input type="text" id="expectedRate" name="expectedRate" value="${expectedRate}" field-name="Expected Rate"  value="" class="form-control">
           </div>
             <div class="col-xs-4 col-md-4">
             <select class="form-control" id="expectedRateCurrencey" name="expectedRateCurrencey">
                 <option><a href="#">&#x20B9;</a></option>
                 <option><a href="#">&#x20A0;</a></option>
                 <option><a href="#">$</a></option>
             </select>
             </div>
           </div>
         </div>
       </div>
        
        <div class="form-group">
          <label class="col-md-3">Skills</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="candidateSkills" name="candidateSkills" value="${candidateSkills}"  field-name="Skills"  value="">
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">Education</label>
          <div class="col-md-9">
             <input type="text" class="form-control" id="candidateEducation" name="candidateEducation" value="${candidateEducation}"  field-name="Education"  value="">
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">Employer's</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="candidateEmployers" name="candidateEmployers" value="${candidateEmployers}"  field-name="Employers"  value="">
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">Summary</label>
          <div class="col-md-9">
            <textarea rows="2" class="form-control" id="summary" name="summary" value="${summary}" field-name="Summary" data-validation="validate_Space validate_length length2-5000" data-validation-optional="true"></textarea>
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3"></label>
         <!--  <div class="col-md-9 checkbox checkBox">
            <label>
              <input type="checkbox" value="">
              Send Invitation </label>
          </div> -->
        </div> 
        <div class="clearfix"></div>
      </div> 
    </div>
    
    
    <div class="form-footer clearfix">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Save" id="store_candidate" onclick="storeCandidate();" class="btn btn-primary">
          <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/candidatesList'/>'" class="btn btn-default ml">
        </div>
      </div>
    </div>
    
     </form:form>
     
    </div>
    
    <!-- Content Area Ends--> 
  </section>
	<!-- Footer Area -->
	<jsp:include page="../masterFooter.jsp" />
	<!-- Footer Area -->

<script type="text/javascript">
	function storeCandidate() {
		
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		 if ($('#candidate_form').validate(false, validationSettings)){ 
			var frm = $('#candidate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			var form = $('#candidate_form')[0];
      		var formData = new FormData(form); 
			  //It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeCandidate?${_csrf.parameterName}=${_csrf.token}&json="+con,
						success : function(result) {
								location.replace(sourceleadUrl+"/candidatesList");
						},
					});
			return true;
		 }else{ 
				return false;
		 } 
	} 
</script>

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<!-- Country,State,City Script Starts Here -->

<script>
function getAllCountries(sourceleadUrl){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: sourceleadUrl+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').tagit({
        		allowSpaces: false,
        		availableTags: json
        		
	       	 	});
        },
        error: function(e){
        }
    });
}
</script> 

<script>
function getStates(sourceleadUrl){
	var countryName = document.getElementById("CountryAutocomplete").value;
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').tagit({
	        		allowSpaces: false,
	        		availableTags: json
	        		
		       	 	});
	        },
	        error: function(e){
	        }
	    });
	}
}
</script> 


<script>
function getCities(sourceleadUrl){
	var countryName = document.getElementById("CountryAutocomplete").value;
	var stateName = document.getElementById("stateAutoComplete").value;
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').tagit({
	        		allowSpaces: false,
	        		availableTags: json
	        		
		       	 	});
	        },
	        error: function(e){
	        }
	    });
	}
}

/* upload file script */
document.getElementById("uploadBtn").onchange = function () {
document.getElementById("uploadFile").value = this.value;
};
</script>

<!-- Country,State,City Script Ends Here -->
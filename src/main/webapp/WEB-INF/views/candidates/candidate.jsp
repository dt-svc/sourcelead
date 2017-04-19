<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<link href="resources/css/candidate-common.css" rel="stylesheet">

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->
	
	<script type="text/javascript">
		window.onload = function () {
			getAllCountries();
			disableMonths();
		 };
	</script>
	 
	 <script>
		$(document).ready(function() {
			document.getElementById("candidate_form").reset();
		});
	</script>

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Parse Resume</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/candidatesList'/>"> Candidate List</a> &raquo; Create Candidate</div>
  </section>
  <div class="content-area content-new clearfix">
  <div id="loading"></div> 
  <form:form id="candidate_form" name="candidate_form"  enctype='multipart/form-data' >
    <div class="row form-horizontal mt myForm">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
        
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        <div class="col-md-12 clearfix">
        <div class="well">
          <div class="input-group col-md-6" id="uploadFileGroup" style="margin:0 auto; position:relative;">
            <label class="input-group-btn"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File<span class="text-danger">&nbsp;*</span>
              <input type="file" name="uploadBtn" id="uploadBtn" class="hidden upload">
              </span> 
           </label>

             <input type="text" class="form-control" readonly id="uploadFile" name="docFile" type="file"  name="" placeholder="Upload Resume ..." field-name="Upload Resume" data-validation="required validate_Space validatedoc " >
         </div>
          </div>
        </div>
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        
        <div class="form-group">
          <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="firstName" name="firstName" value="${firstName}"  field-name="First Name" data-validation="required validate_AlphaNumber validate_Space validate_length length1-100" value="">
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
           <input type="text" class="form-control" id="lastName" name="lastName" value="${lastName}"  field-name="Last Name" data-validation="validate_AlphaNumber validate_Space validate_length length1-100" data-validation-optional="true" value="">
          </div>
        </div>
        <div class="form-group" id="emailDiv">
          <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="email" name="email" value="${email}"  field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email" onblur="validateCandidateEmail();" onmouseout="validateCandidateEmail();" value="">
          <span class="hidden">Valid Email</span>
          </div>
        </div>
						
        <div class="form-group">
          <label class="col-md-3">Mobile</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="mobilePhone" name="mobilePhone" value="${phone}"  field-name="Mobile Number" data-validation="validate_Space validate_length length9-20 validate_phone" data-validation-optional="true" value="">
          </div>
        </div>
        
        <div class="form-group">
          <label class="col-md-3">Alternative Number</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="alternateMobilePhone" name="alternateMobilePhone"   field-name="Alternative Number" data-validation="validate_Space validate_length length9-20 validate_phone" data-validation-optional="true" value="">
          </div>
        </div>
        
        
        <div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <select class="chosen-select form-control select-bg" name="country" id="country" field-name="Country"  >
                 </select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<select  class="chosen-select form-control" name="state" id="state" field-name="State"  >
                 </select>
				</div>
		 </div>
		
		<div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <select  class="chosen-select form-control" name="city" id="city" field-name="City"  >
                 </select>
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
         <div class="col-md-9 ratetype">
             <input type="text" id="designation" name="designation" value="${designation}" field-name="Designation"  data-validation="validate_Space validate_Name validate_length length2-250" data-validation-optional="true"  value="" class="form-control">
         </div>
       </div>
       
        <div class="form-group">
         <label class="col-md-3">Employment type<span class="text-danger">&nbsp;*</span></label>
         <div class="col-md-9">
              <select class="selectpicker form-control" id="designationType" name="designationType" onChange="getDesignationType(this);" field-name="Employment type" data-validation="required">
                <option value="">--Select--</option>
                 <option>C2C</option>
                 <option>C2C-1099</option>
                 <option>W-2</option>
                 <option>C2H</option>
                 <option>C2H -1099</option>
                 <option>Part Time</option>
                 <option>Full Time</option>
                 <option>Other</option>
             </select>
         </div>
       </div>

        <div class="form-group">
          <label class="col-md-3">Experience</label>
          <div class="col-md-9">
            <div class="row exp-drop">
             <div class="col-xs-6 col-md-6">
             		<select class="form-control selectpicker" id="experienceYears" name="experienceYears" onchange="disableMonths();">
             		  <option value="">Years</option>
             		  <c:forEach begin="0" end="25" varStatus="loop">
						    <option>${loop.index}</option>
						</c:forEach>
             		</select>
             </div>
             
             <div class="col-xs-6 col-md-6">
             		<select class="form-control selectpicker" id="experienceMonths" name="experienceMonths">
             		<option value="">Months</option>
             		 <c:forEach begin="0" end="11" varStatus="loop">
						    <option>${loop.index}</option>
						</c:forEach>
             		</select>
             </div>
            </div>
          </div>
        </div>
       
        <div class="form-group designationTypeDiv" id="designationTypeDiv" style="display:none">
            <label class="col-md-3">Employment Type</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="designationOtherType" name="designationOtherType" value="${candidate.designationOtherType}"  field-name="Job Type" value="">
            </div>
          </div>
       
       <div class="clearfix" id="hideVendor" style="display:none">
       <div class="form-group">
			<label class="col-md-3">Vendor<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9" id="errorvendor">
			     <select  class="chosen-select form-control" name="vendors" id="vendors" placeholder="select Vendor" field-name="Vendor" onchange="getVendorContactList();removeValiationVendor();" >
                 </select>
			</div>
		</div>
		
		 <div class="form-group">
			<label class="col-md-3">Vendor Contact<span class="text-danger">&nbsp;*</span></label>
			<div class="col-md-9" id="errorvendorContact">
			     <select  class="chosen-select form-control" name="vendorcontact" id="vendorcontact" field-name="Vendor Contact"  placeholder="select Vendor Contact" onchange="removeValiationVendorContact();">
                 </select>
			</div>
		</div>
		</div>
		
       <div class="form-group">
            <label class="col-md-3">Current Rate</label>
            <div class="col-md-9">
             <div class="input-group er ratetype">
                <div class="input-group-btn tblminwidth-75">
                  
                  <select class="selectpicker" id="currentRateCurrency" name="currentRateCurrency">
                 
                 <option ${currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
                 <option ${currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
                 <option ${currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
                 <option ${currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
                 <option ${currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
                 <option ${currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
                 
                </select>
                
                </div>
                 <input type="text" id="currentRate" name="currentRate" value="${currentRate}" field-name="Current Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true" value="" class="form-control">
                <div class="input-group-btn tblminwidth-75 ratetype">
                  <select class="selectpicker" id="currentRateType" name="currentRateType" field-name="Rate Type">
                    <option value="">Rate Per</option>
                    <option ${currentRateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
                    <option ${currentRateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
                    <option ${currentRateType  == 'Biweekly' ? 'selected' : ''}>Bi-weekly</option>
                    <option ${currentRateType  == 'SemiMonthly' ? 'selected' : ''}>Semi-Monthly</option>
                    <option ${currentRateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
                    <option ${currentRateType  == 'BiMonthly' ? 'selected' : ''}>Bi-Monthly</option>
                    <option ${currentRateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
                  </select>
                </div>
                 <div class="clearfix"></div> <div class="clearfix"></div>
              </div>
            </div>
          </div>
       
        
        
        <div class="form-group">
            <label class="col-md-3">Expected Rate</label>
            <div class="col-md-9">
             <div class="input-group er ratetype">
                <div class="input-group-btn tblminwidth-75">
                  
                  <select class="selectpicker" id="expectedRateCurrencey" name="expectedRateCurrencey">
                 
                 <option ${currencyCode  == 'INR' ? 'selected' : ''}>INR</option>
                 <option ${currencyCode  == 'AUD' ? 'selected' : ''}>AUD</option>
                 <option ${currencyCode  == 'USD' ? 'selected' : ''}>USD</option>
                 <option ${currencyCode  == 'GBP' ? 'selected' : ''}>GBP</option>
                 <option ${currencyCode  == 'SGD' ? 'selected' : ''}>SGD</option>
                 <option ${currencyCode  == 'EUR' ? 'selected' : ''}>EUR</option>
                 
                </select>
                
                </div>
                 <input type="text" id="expectedRate" name="expectedRate" value="${expectedRate}" field-name="Expected Rate" data-validation="validate_Space validate_float validate_length length2-8" data-validation-optional="true" value="" class="form-control">
                <div class="input-group-btn tblminwidth-75 ratetype">
                  <select class="selectpicker" id="rateType" name="rateType" field-name="Rate Type">
                    <option value="">Rate Per</option>
                    <option ${rateType  == 'Hourly' ? 'selected' : ''}>Hourly</option>
                    <option ${rateType  == 'Weekly' ? 'selected' : ''}>Weekly</option>
                    <option ${rateType  == 'Biweekly' ? 'selected' : ''}>Bi-weekly</option>
                    <option ${rateType  == 'SemiMonthly' ? 'selected' : ''}>Semi-Monthly</option>
                    <option ${rateType  == 'Monthly' ? 'selected' : ''}>Monthly</option>
                    <option ${rateType  == 'BiMonthly' ? 'selected' : ''}>Bi-Monthly</option>
                    <option ${rateType  == 'Yearly' ? 'selected' : ''}>Yearly</option>
                  </select>
                </div>
                 <div class="clearfix"></div> <div class="clearfix"></div>
              </div>
            </div>
          </div>
        
        
         <div class="form-group">
          <label class="col-md-3">Employer's</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="candidateEmployers" name="candidateEmployers" value="${candidateEmployers}"  field-name="Employers" data-validation="validate_Space validate_length length2-250" data-validation-optional="true"  value="">
          </div>
        </div>

        <div class="form-group">
         <label class="col-md-3">Notice Period</label>
          <div class="col-md-9">
               <select class="form-control valid selectpicker" id="noticePeriod" name="noticePeriod">
                 <option value="">--Select--</option>
                 <option ${noticePeriod  == 'Immediately available' ? 'selected' : ''}>Immediately available</option>
                 <option ${noticePeriod  == '15 Days' ? 'selected' : ''}>15 Days</option>
                 <option ${noticePeriod  == '1 Month' ? 'selected' : ''}>1 Month</option>
                 <option ${noticePeriod  == '2 Months' ? 'selected' : ''}>2 Months</option>
                 <option ${noticePeriod  == '3 Months' ? 'selected' : ''}>3 Months</option>
               </select>
           </div>
         </div>
         
         <div class="clearfix">
          <div class="form-group">
              <label class="col-md-3">Skills</label>
              <div class="col-md-9">
                <select multiple data-placeholder="select Skills" class="chosen-select form-control" name="candidateSkills" id="candidateSkills"  data-validation-optional="true">
                <c:forEach var="skill" items="${skillsList}">
	                     <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
        
         <div class="form-group">
          <label class="col-md-3">Primary Skill</label>
          <div class="col-md-9">
            <input type="text" class="form-control" id="primarySkill" name="primarySkill" value="${primarySkill}"  field-name="Primary Skill" data-validation="validate_Space validate_length length2-200" data-validation-optional="true">
          </div>
         </div>
        
         
            </div>
        
        <div class="form-group mb">
          <label class="col-md-3">Summary</label>
          <div class="col-md-9">
            <textarea rows="2" class="form-control" id="summary" name="summary" value="${summary}" field-name="Summary" data-validation=" validate_length length2-5000" data-validation-optional="true"></textarea>
          </div>
        </div>
        
        <div class="form-group">
              <label class="col-md-3">Education</label>
              <div class="col-md-9">
                <select multiple data-placeholder="select Education" class="chosenone-selectone form-control" name="candidateEducation" id="candidateEducation" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="edu" items="${educationsList}">
	                     <option value="${edu.name}">${edu.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
        <div class="clearfix"></div>
      </div> 
    </div>
    
    
    <div class="form-footer clearfix mt">
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
	
	<%-- 
     @author : prasad k.
     this script is for save/store candidate.
     1.usage     : onclick.
     2.parameter : csrf token,con.
     3.ajax url implementation in CandidateRestController.
    --%>
<script type="text/javascript">
	function storeCandidate() {
		
		rateValidations();
		
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#candidate_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#candidate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
			
			//It will escape all the special characters
		    var jsonData = encodeURIComponent(con);
			
			var form = $('#candidate_form')[0];
      		var formData = new FormData(form); 
			
			$("#store_candidate").attr("disabled", "disabled");
			$("#store_candidate").addClass('button').val('Processing..');

			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeCandidate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
								location.replace(sourceleadUrl+"/candidatesList");
						},
					});
			return true;
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#candidate_form', function() {
		$("#candidate_form").showHelpOnFocus();
		$("#candidate_form").validateOnBlur(false, validationSettings);
	});
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
<script type="text/javascript">
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>
<script type="text/javascript">
function getAllCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---select country---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${loginCountryName}'){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#country').trigger("chosen:updated");
            getStates();
        },
        error: function(e){
        }
    });
} 
</script>


<script>
function getStates(){
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').append('<option value="">---select city---</option>');
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select state---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>


<!-- Country,State,City Script Ends Here -->


<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			
		   $("#country").chosen();
	       $("#state").chosen();
	       $("#city").chosen();
	       
	       
	       
	        //Adding Customized Skills to the list
	        var select, chosen;
		    select = $("#candidateSkills");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
		    chosen = select.data('chosen');
		    
		    $("#candidateSkills").chosen().change(function(event) {
		    	 var sourceleadUrl ='${sourceleadUrl}';
		    	var allSkills = $(event.target).val();
		    	$.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
			        success: function(data) {
			        	var primarySkill = data;
			        	      $("#primarySkill").val(primarySkill);
			        	
			        },
			    });
		    	
		    });
		    
		    chosen.search_field.on('keyup', function(e)
		    {
		    	var newSkill = this.value.replace(",","");
		        if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(newSkill).text(newSkill);
		            select.append(option);
		            select.find(option).prop('selected', true);
		          //to doesn't allow duplicates
                    $("#candidateSkills option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
                    //end
	        		$("#candidateSkills").trigger("chosen:updated");
	        		
		        }
		    });
		    //Adding Customized Skills to the list
		    
		    //Adding Customized Educations to the list
	        var selectone, chosenone;
	        selectone = $("#candidateEducation");
	        selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Education:' });
	        chosenone = selectone.data('chosen');
	        chosenone.search_field.on('keyup', function(e1)
		    {
		    	var newEducation = this.value.replace(",","");
		        if ((e1.which == 13 || e1.which == 188) && (newEducation != null  && newEducation != '' && newEducation != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(newEducation).text(newEducation);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
		          //to doesn't allow duplicates
                    $("#candidateEducation option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
                    //end
	        		$("#candidateEducation").trigger("chosen:updated");
	        		
		        }
		    }); 
		    //Adding Customized Educations to the list
	       
	       
	       $("#country").on('change', function (e) {
	    	   getStates();
	       });
	       $("#state").on('change', function (e) {
	    	   getCities();
	       });
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
			
			$("#candidateSkills").val('').trigger("chosen:updated");
	       $("#candidateEducation").val('').trigger("chosen:updated");
				
				
</script> 
<!-- Eductions and Skills Autocomplete Scripts End Here -->

<!-- Candidate Email validation script starts here -->

<script type="text/javascript">
 function validateCandidateEmail() {
	 var email = document.getElementById("email").value; 
	 $('#emailDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(email != null && email != ""){
		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&candidateType=Candidate", 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#emailDiv').find('p.jquery_form_error_message').remove();
						  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
						  document.getElementById("email").setAttribute('record-exist','yes');
						  document.getElementById("email").setAttribute('record-exist-errorMsg',email+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#store_candidate" ).addClass('button').val('Save');
                         $("#store_candidate").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>     
 
 <!-- Candidate Email validation script ends here -->
 
 <!-- Experience Years and Months validation Script years greater than  20 the months dropdown disabled script here -->
 <script type="text/javascript">
		 function disableMonths() {
		     $("#experienceYears").change(function() {
		         if ($("#experienceYears").val() == '25'){
		        	 $("#experienceMonths").val('');  
		        	 $('#experienceMonths').selectpicker('refresh');
		        	/*  $('#experienceMonths').attr("disabled", true);
		             $('#experienceMonths').val("disabled", true); */
		         }else{
		        	 $("#experienceMonths").val('');  
		        	 $('#experienceMonths').selectpicker('refresh');
		        	 $("#experienceMonths").attr("disabled", false); 
		         }
		     });
		 }
		   
 
	      var selectedFile = document.getElementById("uploadBtn");
	      selectedFile.addEventListener("change", function() {
	      	 $("#uploadFile").focus();
	      	 if(validate_fileupload(this.value)){
	      		 
	      		 var docSize = this.files[0].size;
	     	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	     	      
	     	      if(docSize > 2097152){
	     	    	  document.getElementById("uploadFile").value = '';
	     	    	 /*  $("#uploadFileGroup").append("<p class='jquery_form_error_message'></p>"); */
	     	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
	     			  $("input#uploadFile").after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
	     			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
	     			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	     	      }else{
	     	    	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	     	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
	        		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
	     		var form = $('#candidate_form')[0];
	       		var formData = new FormData(form); 
	     		var sourceleadUrl = '${sourceleadUrl}';
	     		   $.ajax({
	     					type : "POST",
	     	        	    data: formData,
	     	        	    contentType : 'application/json; charset=utf-8',
	     	        	    enctype: 'multipart/form-data',
	     	    	        processData: false,
	     	    	        contentType:false,
	     	        	    dataType : 'json',
	     				 	url : sourceleadUrl+"/parseResume?${_csrf.parameterName}=${_csrf.token}", 
	     					success : function(data) {
	     						var json = JSON.parse(data);
	     						var name = json.name;
	     						var firstName = '';
	     						var lastName = '';
	     						if(name != null && name != 'undefined' && name != ''){
	     							 firstName = name.split(".")[0]; 
	     							 lastName = name.split(".")[1]; 
	     							 if(firstName == null || firstName ==  'undefined' || firstName == '' ||
	     									 lastName ==  null || lastName ==  'undefined'|| lastName ==  ''){
	     								 firstName = name;
	     							 }
	     						}
	     						
	     						$("#firstName").val(firstName);
	     						$("#lastName").val(lastName);
	     						$("#email").val(json.email);
	     						$("#mobilePhone").val(json.phoneNumbers);
	     						validateCandidateEmail();
	     						$("#candidateSkills").empty();
	     						 var internalOutput = "";
	     						 <c:forEach var="skill" items="${skillsList}">
	     						    internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
	     	                     </c:forEach>
	     						var skills = json.skills;
	     						if(skills != null && skills != 'undefined' && skills != ''){
	     							var str_array = skills.split(',');
	     							for(var i = 0; i < str_array.length; i++) {
	     							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	     							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
	     							}
	     						}
	     						$("#candidateSkills").append(internalOutput);
	     		        		$("#candidateSkills").trigger("chosen:updated");
	     		        		$("#candidateEducation").trigger("chosen:updated");
	     		        		
	     		        		$("#candidateEducation").empty();
	     						 var internalOutput = "";
	     						 <c:forEach var="edu" items="${educationsList}">
	     						    internalOutput = internalOutput+"<option value='${edu.name}'>${edu.name}</option>";
	     	                     </c:forEach>
	     						var edu = json.edu;
	     						if(edu != null && edu != 'undefined' && edu != ''){
	     							var str_array = edu.split(',');
	     							for(var i = 0; i < str_array.length; i++) {
	     							   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
	     							   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
	     							}
	     						}
	     						$("#candidateEducation").append(internalOutput);
	     		        		
	     		        		
	     		        		
	     						$("#loading").removeAttr("style");
	     						
	     					},
	     				});
	      	 }
	      	 }
	      });

	     /* upload file script */
	     document.getElementById("uploadBtn").onchange = function () {
	     	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
	       // document.getElementById("uploadFile").value = this.value.split( '\\' ).pop();
	     };

 
</script>
<!-- Experience Years and Months validation Script End here -->


<script>
function getDesignationType(designationType){
	
	var existingDesignationType = '${requirement.designationType}';
	if(designationType.value == 'Other'){
		 document.getElementById("designationTypeDiv").style.display="block";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		document.getElementById("designationTypeDiv").style.display="none";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationTypeDiv").find('p.jquery_form_error_message').remove();
		  if(existingDesignationType ==null || existingDesignationType == "" || existingDesignationType == "undefined" || existingDesignationType != 'Other'){
			  $("#designationOtherType").val('');
		  }
	}
	
	if(designationType.value == 'C2C' || designationType.value == 'C2C-1099'){
		 document.getElementById("hideVendor").style.display="block";
		 $('#vendors').attr("data-validation","required"); 
		 $('#vendorcontact').attr("data-validation","required"); 
		  getVendorsByOrgLocation();
	}else{
		 document.getElementById("hideVendor").style.display="none";
		 $('#vendors').removeAttr("data-validation","required"); 
		 $('#vendorcontact').removeAttr("data-validation","required"); 
	}
	$('#designationType + p').remove();
}
</script>

<!-- This script for Current Rate ,Rate Type,Expected Rate, Expected Rate Type validation checks -->
<script>

function rateValidations(){
var rateType = document.getElementById("rateType").value; 
if(rateType != null && rateType != ""){
	 $('#expectedRate').removeAttr('data-validation-optional');
	 $('#expectedRate').attr("data-validation","required"); 
}else{
	 $('#expectedRate').removeAttr('data-validation');
	 $('#expectedRate').attr("data-validation-optional","true");
	 $('#rateType').removeAttr('data-validation');
	 $('#rateType').attr("data-validation-optional","true"); 
} 

var expectedRate = document.getElementById("expectedRate").value; 
if(expectedRate != null && expectedRate != ""){
	
	 $('#rateType').removeAttr('data-validation-optional');
	 $('#rateType').attr("data-validation","required"); 
}else{
$('#rateType').removeAttr('data-validation');
$('#rateType').attr("data-validation-optional","true"); 
}



var currentRateType = document.getElementById("currentRateType").value; 
	if(currentRateType != null && currentRateType != ""){
		$('#currentRate').removeAttr('data-validation-optional');
		$('#currentRate').attr("data-validation","required"); 
	}else{
		$('#currentRate').removeAttr('data-validation');
	 	$('#currentRate').attr("data-validation-optional","true"); 
		$('#currentRateType').removeAttr('data-validation');
	 	$('#currentRateType').attr("data-validation-optional","true"); 
	} 


var currentRate = document.getElementById("currentRate").value; 
	 if(currentRate != null && currentRate != ""){
		
		 $('#currentRateType').removeAttr('data-validation-optional');
		 $('#currentRateType').attr("data-validation","required"); 
		 
	 }else{
		 
			 $('#currentRateType').removeAttr('data-validation');
			 $('#currentRateType').attr("data-validation-optional","true"); 
	 
	 }
	 
}
</script>

<script>
 function getVendorsByOrgLocation(){
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getVendorsByOrgLocation?", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendors').empty();
		        	$('#vendors').append('<option value="">--select Vendor --</option>');
		            for(var i=0;i<json.length;i++){
		            	  var opt = json[i];
		            		$('#vendors').append('<option value='+opt.id+' >'+opt.orgName+'</option>');
		            }
		            $('#vendors').trigger("chosen:updated");
		            
		            
		        }
		 });
		
 }
 </script>
 
 <script>
 function  removeValiationVendor() {
	$("#errorvendor").find("p.jquery_form_error_message").remove();
}
</script>
<script>
function  removeValiationVendorContact() {
	 $("#errorvendorContact").find("p.jquery_form_error_message").remove();
}
</script>
<script>
 function getVendorContactList(){
	var orgRelationID = $('#vendors').val();
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
			    type: "POST",
		        url: sourceleadUrl+"/getOrgSpocsList?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationID+"&type=Vendor",  
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	$('#vendorcontact').empty();
		        	$('#vendorcontact').append('<option value="">--select Vendor contact--</option>');
		             for(var i=0;i<json.length;i++){
		            	 var opt = json[i];
		            	 $('#vendorcontact').append('<option value='+opt.id+' >'+opt.spocName+'</option>');
		            } 
		            $('#vendorcontact').trigger("chosen:updated");
		        }
		 }); 
 }
 </script>

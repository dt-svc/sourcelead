<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<!-- these files for autocomplete with tagit -->
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>


 <!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Vendor Contact Details</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Vendor &raquo; Vendor Contact Details</div>
        </section>
        <div class="content-area content-new clearfix">
        <form:form id="vendor_contact_form" name="vendor_contact_form" method="post">
          <div class="clearfix form-horizontal inner-content">
            <div class="row">
              <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
          <div class="form-group">
            <label class="col-md-3">Select Vendor <span class="text-danger">&nbsp;*</span></label>
            <div class="col-md-9">
              <div class="form-icon">
                <select class="select-bg form-control" id="vendorName" name="vendorName" field-name="vendorName" data-validation="required">
                  <option>Select Vendor</option>
                   <c:forEach var="report" items="${vendorList}">
                     <c:if test="${report.locationId.id == loginOrgLocId}">  
                    <option value="${report.id}">${report.responderLocationId.org.orgName}</option>
                   </c:if>  
                    <c:if test="${report.responderLocationId.id == loginOrgLocId}"> 
                  	 	<option value="${report.id}">${report.locationId.org.orgName}</option>
                   </c:if>  
                 </c:forEach> 
                </select>
                <i data-target="#selectVendor" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i> </div>
              <div role="dialog" id="selectVendor" class="modal fade">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                      <h4 class="modal-title">Select Vendor</h4>
                    </div>
                    <div class="modal-body clearfix">
                      <div class="form-horizontal custom-form clearfix">
                        <div class="col-xs-12 col-md-12 col-lg-12">
                          <div class="form-group">
                            <label class="col-lg-3">Vendor Name <span class="text-danger">&nbsp;*</span></label>
                            <div class="col-lg-9">
                              <input type="text" class="form-control" value="">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-lg-3">Website</label>
                            <div class="col-lg-9">
                              <input type="text" class="form-control">
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                      <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
                <div class="form-group">
                  <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                    <input class="form-control error" id="firstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_length length2-100" type="text">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">Last Name</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="lasttName" name="lastName" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3">Email<span class="text-danger">&nbsp;*</span></label>
                  <div class="col-md-9">
                   <input type="text" class="form-control" id="email" name="email" value="" field-name="Email Id" data-validation="required validate_Space validate_email"">
                  </div>
                  </div>
                   <div class="form-group">
                  <label class="col-md-3">Work Phone</label>
                  <div class="col-md-9">
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"  value="">
                  </div>
                </div>
             <div class="form-group">
              <label class="col-md-3">Owner<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <select class="chosen-select form-control" name="owner" id="owner" multiple="true" data-validation="required">
                <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
                </c:forEach>
                </select>
              </div>
            </div>
                 <div class="form-group">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">
		                        <option value="">-Select Role-</option>
									<option value="Admin">Admin</option>
									<option value="Approver">Approver/Project Manager</option>
									<option value="HR Manager">HR Manager</option>
									<option value="Account Manager">Account Manager</option>
									<option value="Recruiter">Recruiter</option>
			              </select>
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-md-12 checkbox">
                      <label><input type="checkbox" value="Y"
							name="isMailSent" checked="checked">Send Invitation</label>
                  </div>
                </div>
                </div>
                </div>
                </div>
            <div class="form-footer clearfix">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Save" id="save_vendor_contact" onclick="saveVendorContact();" class="btn btn-primary">
                  <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/vendorsList'/>'" class="btn btn-default ml">
                </div>
              </div>
            </div>
          
      <!-- Content Area Ends--> 
      </form:form>
	  </div>
      </section>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
</script> 


<script type="text/javascript">
	function saveVendorContact() {
	var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#vendor_contact_form').validate(false, validationSettings)) {
			var frm = $('#vendor_contact_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_vendor_contact").attr("disabled", "disabled");
			$("#save_vendor_contact").addClass('button').val('Processing..');
			$
					.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveVendorContact?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData,
						success : function(result) {
							if (result == 'vendorsList') {
								location.replace(sourceleadUrl
										+ "/vendorsList");
							}

						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#vendor_contact_form', function() {
		$("#vendor_contact_form").showHelpOnFocus();
		$("#vendor_contact_form").validateOnBlur(false, validationSettings);
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

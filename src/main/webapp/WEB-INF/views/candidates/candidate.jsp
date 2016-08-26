<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
 
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
  <form:form id="candidate_form" name="candidate_form" method="get">
    <div class="row form-horizontal mt myForm">
      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
        
        <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
        <div class="col-md-12 clearfix">
        <div class="well">
          <div class="input-group col-md-6" style="margin:0 auto">
            <label class="input-group-btn"> <span class="btn btn-danger"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
              <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
              </span> </label>
            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume ...">
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
            <input type="text" class="form-control" name="Email" value="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Mobile</label>
          <div class="col-md-9">
            <input type="text" class="form-control" name="Mobile" value="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Country</label>
          <div class="col-md-9">
            <select class="form-control selectpicker">
              <option>India</option>
              <option>United States of America</option>
              <option>UK (United Kingdom)</option>
              <option>Swizerland</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">State</label>
          <div class="col-md-9">
            <select class="form-control selectpicker">
              <option>Maharashtra</option>
              <option>Telanga</option>
              <option>Andhra Pradesh</option>
              <option>Uttar Pradesh</option>
              <option>Rajastahan</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">City</label>
          <div class="col-md-9">
            <select class="form-control selectpicker">
              <option>Delhi</option>
              <option>Hyderabad</option>
              <option>Visakhapatnam</option>
              <option>Mumbai</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Address</label>
          <div class="col-md-9">
            <input type="text" class="form-control" name="Mobile" value="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Job Title</label>
          <div class="col-md-9">
            <div class="input-group">
              <input type="text" value="" class="form-control">
              <div class="input-group-btn">
                <button aria-expanded="false" data-toggle="dropdown" class="btn btn-primary dropdown-toggle" type="button"> Type <span class="caret"></span> </button>
                <ul class="dropdown-menu dropdown-menu-right">
                  <li><a href="#">C2C</a></li>
                  <li><a href="#">W-2</a></li>
                  <li><a href="#">Part</a></li>
                  <li><a href="#">Time</a></li>
                  <li><a href="#">Other</a></li>
                </ul>
              </div>
              /btn-group 
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Experience</label>
          <div class="col-md-9">
            <input type="text" class="form-control" name="Mobile" value="">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Current Rate</label>
          <div class="col-md-9">
            <div class="input-group">
              <input type="text" value="" class="form-control">
              <div class="input-group-btn">
                <button aria-expanded="false" data-toggle="dropdown" 
                                class="btn btn-primary dropdown-toggle" type="button"> Select <span class="caret"></span> </button>
                <ul class="dropdown-menu dropdown-menu-right">
                  <li><a href="#">&#x20B9;</a></li>
                  <li><a href="#">&#x20A0;</a></li>
                  <li><a href="#">$</a></li>
                </ul>
              </div>
              /btn-group 
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Expected Rate</label>
          <div class="col-md-9">
            <div class="input-group">
              <input type="text" value="" class="form-control">
              <div class="input-group-btn">
                <button aria-expanded="false" data-toggle="dropdown" 
                                class="btn btn-primary dropdown-toggle" type="button"> Select <span class="caret"></span> </button>
                <ul class="dropdown-menu dropdown-menu-right">
                  <li><a href="#">Action</a></li>
                  <li><a href="#">Another action</a></li>
                  <li><a href="#">Something else here</a></li>
                  <li class="divider" role="separator"></li>
                  <li><a href="#">Separated link</a></li>
                </ul>
              </div>
              /btn-group 
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Skills</label>
          <div class="col-md-9">
            <input type="text" class="form-control" value="Need to tagit code">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Education</label>
          <div class="col-md-9">
            <input type="text" value="" name="Education" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Employer's</label>
          <div class="col-md-9">
            <input type="text" value="" name="Education" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3">Summary</label>
          <div class="col-md-9">
            <textarea rows="2" class="form-control"></textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="col-md-3"></label>
          <div class="col-md-9 checkbox checkBox">
            <label>
              <input type="checkbox" value="">
              Send Invitation </label>
          </div>
        </div>
      </div> 
    </div>
    </div>
    
    <div class="form-footer clearfix">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Save" id="store_candidate" onclick="storeCandidate();" class="btn btn-primary">
          <input type="button" value="Cancel" class="btn btn-default ml">
        </div>
      </div>
    </div>
    </form:form>
    
    <!-- Content Area Ends--> 
  </div>
</section>
<!-- Footer Area -->
<footer class="footer-area clearfix">
  <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
  <div class="powered-by"> Powered by: businessintelli.com </div>
</footer>
<!-- Footer Area --> 

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
<script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 
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

});
 		</script> 
<script>
$(document).ready(function(){
    $(function() {
    $('#accordion-sl .content').hide();
    $('#accordion-sl h2:first').addClass('active').next().slideDown('slow');
    $('#accordion-sl h2').click(function() {
        if($(this).next().is(':hidden')) {
            $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
            $(this).toggleClass('active').next().slideDown('slow');
        }
    });
});
});
</script>
<div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
  <ul class="list-unstyled">
    <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
    <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
    <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
  </ul>
</div>


<script type="text/javascript">
	function storeCandidate() {
			var frm = $('#candidate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';
			alert("sourceleadUrl"+sourceleadUrl);
			alert("aaa"+con);
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/storeCandidate?${_csrf.parameterName}=${_csrf.token}&json="+con,
						success : function(result) {
							if (result == 'candidatesList') {
								location.replace(sourceleadUrl
										+ "/candidatesList");
							}
						},
					});
			return true;
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
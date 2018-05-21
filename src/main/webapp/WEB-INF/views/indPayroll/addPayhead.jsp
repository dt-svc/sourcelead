<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area -->
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Add Payhead</h2>
		</div>
		<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configure</a> &raquo; <a href="<c:url value='${appPath}/payheadList'/>">Payhead List</a> &raquo; Add Payhead</div>
	</section>
<form:form id="payhead_form" name="payhead_form">
	<div class="contentareaInner-styles clearfix">
		<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
			<div class="row min">

				<div class="col-md-5">
					<label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix">
						<input type="text" class="form-control" id="name" name="name"
							value="" field-name="Name"  data-validation="required validate_Space validate_length length1-100">
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Short Code<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix">
						<input type="text" class="form-control" id="shortCode" name="shortCode" 
							value="" field-name="Short Code"  data-validation="required validate_Space validate_length length1-3">
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Effective Date</label>
					<div class="clearfix">
						<div class="input-group">
							<input type="text" class="form-control calendar" id="effectiveDate" name="effectiveDate"
								value="" field-name="Effective Date">
								<span class="input-group-addon" onClick="clearEffectiveDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
						</div>
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Pay Type</label>
					<div class="clearfix">
						<div class="row">
							<div class="col-md-10">
								<select class="form-control chosen-select" id="payType" name="payType" field-name="Pay Type">
									<option value="">--Select Pay Type--</option>
		                			<c:forEach var="list" items="${payTypeList}">
				                    	<option value="${list.id}" ${list.id == 1 ? 'selected' : ''} >${list.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2">
								<a href="javascript:void(0)" class="btn btn-default" onClick="addPaytypeFun();"><i
									class="fa fa-plus"></i></a>
							</div>
						</div>
					</div>
				</div>

				<!-- <div class="col-md-5" id="flatDiv">
					<label class="clearfix">Amount</label>
					<div class="clearfix">
						<input type="text" class="form-control" id="amount" name="amount" value="" field-name="Amount" data-validation-optional="true"  data-validation="validate_Space validate_float validate_length length1-3">
					</div>
				</div>
				<div class="col-md-5" id="formulaDiv hidden">
					<label class="clearfix">Formula</label>
					<div class="clearfix">
						<input type="text" class="form-control" value="">
					</div>
				</div> -->

				<div class="col-md-5">
					<label class="clearfix">Form-16</label>
					<div class="clearfix dropdown-select">
						<select id="form16" name="form16">
							<option value="">Select</option>
							<option value="Form-16A">Form-16A</option>
							<option value="Form-16B">Form-16B</option>
						</select>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Tax</label>
					<div class="clearfix">
						<span class="radioStyles mr"> 
							<input type="radio" id="taxable" name="tax" value="Taxable"> <label for="taxable">Taxable</label>
						</span> 
						<span class="radioStyles"> 
							<input type="radio" id="nonTaxable" name="tax" value="NonTaxable"> <label for="nonTaxable">Non Taxable</label>
						</span>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Applicable For</label>
					<div class="clearfix">
						<span class="radioStyles mr"> 
							<input type="radio" id="all" name="applicableFor" value="All"> <label for="all">All</label>
						</span> 
						<span class="radioStyles"> 
							<input type="radio" id="experienced" name="applicableFor" value="Experienced"> <label for="experienced">Experienced</label>
						</span>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Component</label>
					<div class="clearfix">
						<span class="radioStyles mr"> 
							<input type="radio" id="variable" name="component" value="Variable"> <label for="variable">Variable</label>
						</span> 
						<span class="radioStyles"> 
							<input type="radio" id="fixed" name="component" value="Fixed"> <label for="fixed">Fixed</label>
						</span>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Other</label>
					<div class="clearfix">
						<div class="checkbox-new pull-left">
							<input id="pc" class="checkbox-custom" name="paidComponent"
								type="checkbox" value="Y"> <label for="pc"
								class="checkbox-custom-label"><span class="ml10">Paid
									Component</span></label>
						</div>
						<div class="checkbox-new ml mr pull-left">
							<input id="partofaCTC" class="checkbox-custom" name="partofaCTC"
								type="checkbox" value="Y"> <label for="partofaCTC"
								class="checkbox-custom-label"><span class="ml10">Part
									of a CTC</span></label>
						</div>
						<div class="checkbox-new pull-left">
							<input id="fbpComponent" class="checkbox-custom" name="fbpComponent"
								type="checkbox" value="Y"> <label for="fbpComponent"
								class="checkbox-custom-label"><span class="ml10">FBP
									Component</span></label>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<div class="clearfix footerstyelsNew">
		<div class="col-lg-12">
			<div class="pull-right">
				<input value="Save" class="btn btn-success btnSuccess" type="button" onclick="storePayhead();">
				<input value="Cancel" class="btn btn-default ml10" type="button">
			</div>
		</div>
	</div>
 </form:form>
	<!-- Content Area Ends-->

<div class="comMask"></div>
<div class="modal addPaytype" id="addPaytype">
	<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" onClick="closePaytypeFun();">&times;</button>
			<h4>Add Paytype</h4>
		</div>
		<form:form id="paytype_form" name="paytype_form">
			<div class="modal-body">
				<div class="minHeight50 mb">
					<label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix">
						<input type="text" class="form-control" id="paytypeName" name="name" field-name="Name"  data-validation="required validate_Space validate_length length1-100">
					</div>
				</div>
				<div class="minHeight50 mt">
					<label class="clearfix">Description</label>
					<div class="clearfix">
						<textarea class="form-control" rows="3" id="paytypeDescription" name="description" field-name="Description" data-validation-optional="true" data-validation="validate_Space validate_length length1-500"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="pull-right">
					<a class="btn btn-success btnSuccess mr" id="savePaytypeBtn">Save</a>
					<a class="btn btn-default" data-dismiss="modal" id="cancelPaytypeBtn" onClick="closePaytypeFun();">Cancel</a>
				</div>
			</div>
		</form:form>
		</div>
	</div>
</div>



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
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script>
        
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#savevendor").click(function(){
			$('#myform').validate({
			rules:{
				things:{
					required:true
				}
			}
			});
		});
	});
</script>
<script>	
	$("#aa").click(function() {
	  $(".subMenuView").slideToggle(100);
	});
</script>


<script type="text/javascript">
	function storePayhead() {
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
			 
		     if ($('#payhead_form').validate(false, validationSettings)){
      			var formData = getFormData1("payhead_form");
				$.ajax({
					 type : "POST",
					 data: formData,
	 		         processData: false,
	 		         contentType:false, 
					  url : sourceleadUrl+"/storePayhead?"+tokenParamName+"="+tokenValue,
					  	success : function(result) {
					  		
					  	},
				});
			
				return true;
		   	}else{ 
				return false;
		 }   
	} 
	
	$('body').on('blur', '#payhead_form', function() {
		$("#payhead_form").showHelpOnFocus();
		$("#payhead_form").validateOnBlur(false, validationSettings);
	});
	
</script>

<script type="text/javascript">
	$(function(){
	    $('#savePaytypeBtn').click(function() {
	    	var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
				 
			     if ($('#paytype_form').validate(false, validationSettings)){
	      			var formData = getFormData1("paytype_form");
					$.ajax({
						 type : "POST",
						 data: formData,
		 		         processData: false,
		 		         contentType:false, 
						 url : sourceleadUrl+"/storePaytype?"+tokenParamName+"="+tokenValue,
						 success : function(result) {
						  		
						  	},
					});
					$('#paytypeName').val('');
			    	$('#paytypeDescription').val('');
			    	$(".addPaytype").hide();
			    	$(".comMask").fadeOut();
					return true;
			   	}else{
					return false;
			 	}
	    });
	});
	
	$('body').on('blur', '#paytype_form', function() {
		$("#paytype_form").showHelpOnFocus();
		$("#paytype_form").validateOnBlur(false, validationSettings);
	});
	
	
	$(function(){
	    $('#cancelPaytypeBtn').click(function() {
	    	$('#paytypeName').val('');
	    	$('#paytypeName').siblings('p').remove();
	    	$('#paytypeDescription').val('');
	    	$('#paytypeDescription').siblings('p').remove();
	    });
	});
</script>


<script>
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var jsDateFormat= '';
	  if(locationDate.match("yyyy-MM-dd")){
		  jsDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  jsDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  jsDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  jsDateFormat = 'yy-dd-mm'; 
	  }
	  $('#effectiveDate').datepicker({
	      dateFormat: jsDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  /* minDate: 1, */
	  	  onSelect : function(selected) {
	  	  }
	  });
	  
	  function clearEffectiveDate(){
			$("#effectiveDate").val("");
	  }
</script>
<script>
function addPaytypeFun() {
	$(".addPaytype, .comMask").show();
}
function closePaytypeFun() {
	$(".addPaytype, .comMask").hide();
}
</script>

	<jsp:include page="../masterFooter.jsp" />
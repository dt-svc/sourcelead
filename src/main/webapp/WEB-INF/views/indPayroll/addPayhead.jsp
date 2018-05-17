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

	<div class="contentareaInner-styles clearfix">
		<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
			<div class="row min">

				<div class="col-md-5">
					<label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix">
						<input type="text" class="form-control" name="Company Name"
							value="">
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Short Code</label>
					<div class="clearfix">
						<input type="text" class="form-control" name="Website" value="">
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Effective Date</label>
					<div class="clearfix">
						<input type="text" class="form-control calendar" name="Website"
							value="">
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Pay Type</label>
					<div class="clearfix">
						<div class="row">
							<div class="col-md-10">
								<select class="form-control chosen-select">
									<option>Select Pay Type</option>
									<option>Deduction</option>
									<option>Overtime</option>
									<option>Earning</option>
									<option>Reimbursement</option>
								</select>
							</div>
							<div class="col-md-2">
								<a href="javascript:void(0)" class="btn btn-default"
									data-toggle="modal" data-target="#addPaytype"><i
									class="fa fa-plus"></i></a>
							</div>
						</div>
					</div>

				</div>
				<div class="col-md-5 hidden">
					<label class="clearfix">Method</label>
					<div class="clearfix dropdown-select">
						<select>
							<option selected>Select</option>
							<option>Flat</option>
							<option>Formula</option>
						</select>
					</div>
				</div>

				<div class="col-md-5" id="flatDiv">
					<label class="clearfix">Amount</label>
					<div class="clearfix">
						<input type="text" class="form-control" value="">
					</div>
				</div>
				<div class="col-md-5" id="formulaDiv hidden">
					<label class="clearfix">Formula</label>
					<div class="clearfix">
						<input type="text" class="form-control" value="">
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Form-16</label>
					<div class="clearfix dropdown-select">
						<select>
							<option selected>Select</option>
							<option>Form-16A</option>
							<option>Form-16B</option>
						</select>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Tax</label>
					<div class="clearfix">
						<span class="radioStyles mr"> <input type="radio"
							id="taxable" name="tax"> <label for="taxable">Taxable</label>
						</span> <span class="radioStyles"> <input type="radio"
							id="nonTaxable" name="tax"> <label for="nonTaxable">Non
								Taxable</label>
						</span>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Applicable For</label>
					<div class="clearfix">
						<span class="radioStyles mr"> <input type="radio" id="all"
							name="applicableFor"> <label for="all">All</label>
						</span> <span class="radioStyles"> <input type="radio"
							id="experienced" name="applicableFor"> <label
							for="experienced">Experienced</label>
						</span>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Component</label>
					<div class="clearfix">
						<span class="radioStyles mr"> <input type="radio"
							id="variable" name="applicableFor"> <label for="variable">Variable</label>
						</span> <span class="radioStyles"> <input type="radio" id="fixed"
							name="applicableFor"> <label for="fixed">Fixed</label>
						</span>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Other</label>
					<div class="clearfix">
						<div class="checkbox-new pull-left">
							<input id="pc" class="checkbox-custom" name="paidComponent"
								type="checkbox"> <label for="pc"
								class="checkbox-custom-label"><span class="ml10">Paid
									Component</span></label>
						</div>
						<div class="checkbox-new ml mr pull-left">
							<input id="partofaCTC" class="checkbox-custom" name="PartofaCTC"
								type="checkbox"> <label for="partofaCTC"
								class="checkbox-custom-label"><span class="ml10">Part
									of a CTC</span></label>
						</div>
						<div class="checkbox-new pull-left">
							<input id="fBP" class="checkbox-custom" name="FBP"
								type="checkbox"> <label for="fBP"
								class="checkbox-custom-label"><span class="ml10">FBP
									Component</span></label>
						</div>
					</div>
				</div>

				<div class="col-md-5 hidden">
					<label class="clearfix">Experienced</label>
					<div class="clearfix clearfix">
						<div class="col-md-6 noPad">
							<select class="form-control chosen-select">
								<option>Year</option>
								<option>Month</option>
								<option>Days</option>
							</select>
						</div>
						<div class="col-md-6">
							<input type="text" class="form-control" name="Website" value="">
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
				<input value="Save" class="btn btn-success btnSuccess" type="button">
				<input value="Cancel" class="btn btn-default ml10" type="button">
			</div>
		</div>
	</div>

	<!-- Content Area Ends-->


<div class="modal fade" id="addPaytype">
	<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>Add Paytype</h4>
		</div>
		<div class="modal-body">
			<div class="minHeight50 mb">
				<label class="clearfix">Name</label>
				<div class="clearfix">
					<input type="text" class="form-control">
				</div>
			</div>
			<div class="minHeight50 mt">
				<label class="clearfix">Description</label>
				<div class="clearfix">
					<textarea class="form-control" rows="3"></textarea>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="pull-right">
				<a href="javascript:void(0)" class="btn btn-success btnSuccess mr">Save</a>
				<a href="javascript:void(0)" class="btn btn-default" data-dismiss="modal">Cancel</a>
			</div>
		</div>
		</div>
	</div>
</div>


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
	$(function () {

	});
</script>
<script>	
	$("#aa").click(function() {
	  $(".subMenuView").slideToggle(100);
	});
</script>



	<jsp:include page="../masterFooter.jsp" />
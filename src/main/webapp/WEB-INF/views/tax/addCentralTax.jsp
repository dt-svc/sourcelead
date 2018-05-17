<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper contentWraper">
	<jsp:include page="../tax/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">

		<div class="tablelist-wrap">

  <form:form id="createCentralTax_form"  name="createCentralTax_form" method="post">
			<div class="col-md-10">
				<div class="clearfix row">
					<div class="col-md-4 dropdown-select mt10 mb10">
						<select class="selectpicker width100percnt"  id="sourceleadTaxType " name="sourceleadTaxType" 
							title="Select Type of Tax" data-live-search="true">
							<option>Income tax</option>
							<option>Sales tax</option>
							<option>Excise duty</option>
							<option>Service tax</option>
							<option>Custom duty</option>	
						</select>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>

			<div class="col-md-12">
				<div class="clearfix minHeightcol5">
					<div class="row">
						<h4 class="col-md-10">Provident Fund</h4>
						<div class="col-md-5">
							<label class="clearfix">Ceil Value</label>
							<div class="clearfix">
								<input type="text" id = "ceilvaluepf"  name = "ceilvaluepf" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">No. of Employees</label>
							<div class="clearfix">
								<input type="text" id = "noofemployees" name = "noofemployees" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employee</label>
							<div class="clearfix">
								<input type="text" id = "employeepf" name = "employeepf" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employer</label>
							<div class="clearfix">
								<input type="text"  id = "employerpf" name = "employerpf" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="clearfix"></div>
					</div>

					<div class="row">
						<h4 class="col-md-10">ESI</h4>
						<div class="col-md-5">
							<label class="clearfix">ESI Ceil Value</label>
							<div class="clearfix">
								<input type="text" id = "ceilvalueesi"  name =  "ceilvalueEsi"  class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employee</label>
							<div class="clearfix">
								<input type="text" id = "employeeesi" name = "employeeEsi" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employer</label>
							<div class="clearfix">
								<input type="text" id = "employeresi" name = "employerEsi" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
					<div class="clearfix">
						<div class="clearfix minHeightcol5">
							<div class="row">
								<h4 class="col-md-6">
									TDS <a href="javascript:void(0)"
										class="paddingLeft cursorPointer ml" id="clonediv"
										title="Add Row">+</a>
								</h4>
							</div>
							<div class="row clonedInput mb">
								<div class="col-md-3">
									<label class="clearfix">Age</label>
									<div class="clearfix">
										<select class="width100percnt selectpicker" id = "age" name = "age"
											data-live-search="true" title="Select Age">
											<option>0 - 60 Years</option>
											<option>60 - 80 Years</option>
											<option>Above 80</option>
										</select>
									</div>
								</div>
								<div class="col-md-3">
									<label class="clearfix">From</label>
									<div class="clearfix">
										<input type="text" id = "fromamount" name = "fromamount" class="form-control" name="Website"
											value="" placeholder="Enter">
									</div>
								</div>
								<div class="col-md-3">
									<label class="clearfix">To</label>
									<div class="clearfix">
										<input type="text" id = "toamount" name = "toamount" class="form-control" name="Website"
											value="" placeholder="Enter">
									</div>
								</div>
								<div class="col-md-2">
									<label class="clearfix">Percentage</label>
									<div class="clearfix">
										<input type="text" id = "percentage" name = "percentage" class="form-control" name="Website"
											value="" placeholder="Enter">
									</div>
								</div>
								<div class="col-md-1">
									<label class="clearfix">Actions</label>
									<ul class="list-unstyled list-inline mt5 text-center">
										<li><a title="Delete Row"
											class="fa fa-trash text-danger deleteRow"></a></li>
										<li><a title="Add Row"
											class="fa fa fa-floppy-o text-primary"></a></li>
									</ul>
								</div>
								<div class="clearfix"></div>
							</div>

							<div id="cloneArea"></div>

						</div>
					</div>

				</div>
			</div>
			<div class="clearfix"></div>
			<div class="clearfix footerstyelsNew">
				<div class="col-lg-12">
					<div class="pull-right">
						<input value="Save" id = "centralTax" onclick="createCentralTax();" class="btn btn-success btnSuccess" type="button"> 
						<input value="Cancel" class="btn btn-default ml10" type="button">
					</div>
				</div>
			</div>
</form:form>
		</div>
	</div>
</section>

<script>
	function createCentralTax() {
		var formData = getFormData("createCentralTax_form");
		$.ajax({
			type : "POST",
			data : formData,
			url : sourceleadUrl.trim()+"/saveCentralTax",
			success : function(result) {

			},
		});
	}
</script>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var activeTaxTab = "createStateTax";
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">
	<jsp:include page="../tax/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">
		<div class="tablelist-wrap">
			<form action="" id="addStateTaxForm" method="post">
				<div class="col-md-10">
					<div class="clearfix row">
						<div class="col-md-4 dropdown-select mt10 mb10">
							<select class="selectpicker width100percnt"
								title="Select Type of Tax" data-live-search="true" id="taxType"
								field-name="TaxType" data-validation="required" name="taxTypeId">
								<c:forEach items="${taxTypes}" var="t">
									<option value="${t.id}">${t.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-4 dropdown-select mt10 mb10">
							<select class="selectpicker width100percnt" title="Select Year"
								data-live-search="true" id="financialYear" field-name="Year"
								data-validation="required" name="financialYear">
								<option value="">Select Year</option>
								<c:forEach items="${yearList}" var="year">
									<option ${year.value==true?"selected=selected":""}
										value="${year.key}">${year.key}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-4 dropdown-select mt10 mb10">
							<select class="selectpicker width100percnt" title="Select State"
								data-live-search="true" id="state" field-name="State"
								data-validation="required" name="stateId">
								<option value="0">Select State</option>
								<c:forEach items="${stateList}" var="s">
									<option value="${s.id}">${s.stateName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="clearfix minHeightcol5">
						<div class="row">
							<h4 class="col-md-6">
								Professional Tax <a href="javascript:void(0)"
									class="paddingLeft cursorPointer ml" id="addPTRow"
									onclick="tax.addDynamicPTRow(false);" title="Add Row">+ </a>
							</h4>
						</div>
						<div class="row clonedInput mb" id="dynamicPTList" name="professionalTaxList">
							<!-- <div data-id="ptRow0">
							<div class="col-md-3">
								<label class="clearfix">From</label>
								<div class="clearfix">
									<input type="text" class="form-control" name="amountFrom"
										value="" placeholder="Enter">
								</div>
							</div>
							<div class="col-md-3">
								<label class="clearfix">To</label>
								<div class="clearfix">
									<input type="text" class="form-control" name="amountTo"
										value="" placeholder="Enter">
								</div>
							</div>
							<div class="col-md-3">
								<label class="clearfix">Amount</label>
								<div class="clearfix">
									<input type="text" class="form-control" name="amount" value=""
										placeholder="Enter">
								</div>
							</div>
							<div class="col-md-2">
								<label class="clearfix">Month</label>
								<div class="clearfix">
									<select class="width100percnt selectpicker"
										name="monthApplicable" data-live-search="true"
										title="Select Month">
										<option>April</option>
										<option>May</option>
										<option>June</option>
										<option>July</option>
										<option>August</option>
										<option>September</option>
										<option>October</option>
										<option>November</option>
										<option>December</option>
										<option>January</option>
										<option>February</option>
										<option>March</option>
									</select>
								</div>
							</div>
							<div class="col-md-1">
								<label class="clearfix">Actions</label>
								<ul class="list-unstyled list-inline mt5 text-center">
									<li><a href="javascript:void(0)"
										class="cursorPointer"
										onclick="tax.addDynamicPTRow(false);" title="Add Row" style="font-size: 20px;"> + </a></li>
									<li><a title="Delete Row"
										class="fa fa-trash text-danger deleteRow"
										onClick="tax.deletePTRow(0)"></a></li>
									<li><a title="Add Row"
									class="fa fa fa-floppy-o text-primary"></a></li>
								</ul>
							</div>
							<div class="clearfix"></div> -->
						</div>
					</div>
					<div id="cloneArea"></div>
				</div>
			</form>
		</div>
		<div class="clearfix"></div>
		<div class="clearfix footerstyelsNew">
			<div class="col-lg-12">
				<div class="pull-right">
					<input value="Save" class="btn btn-success btnSuccess"
						type="button" id="saveStateTax"> <input value="Cancel"
						class="btn btn-default ml10" type="button">
				</div>
			</div>
		</div>
	</div>
	</div>
</section>
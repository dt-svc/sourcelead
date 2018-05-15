<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-wraper contentWraper">
	<jsp:include page="../tax/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">

		<div class="tablelist-wrap">

			<div class="col-md-10">
				<div class="clearfix row">
					<div class="col-md-4 dropdown-select mt10 mb10">
						<select class="selectpicker width100percnt"
							title="Select Type of Tax" data-live-search="true">
							<option>Sales tax</option>
							<option>Value added tax</option>
							<option>Entertaiment</option>
							<option>Road</option>
							<option>Professional tax</option>
							<option>Stamp duty</option>
							<option>Luxury</option>
							<option>Entry</option>
							<option>Capital gains</option>
						</select>
					</div>
					<div class="col-md-4 dropdown-select mt10 mb10">
						<select class="selectpicker width100percnt" title="Select Year"
							data-live-search="true">
							<option>Select Year</option>
							<option selected>2018-2019</option>
							<option>2019-2020</option>
							<option>2021-2022</option>
						</select>
					</div>
					<div class="col-md-4 dropdown-select mt10 mb10">
						<select class="selectpicker width100percnt" title="Select State"
							data-live-search="true">
							<option>Andra Pradesh</option>
							<option>Arunachal Pradesh</option>
							<option>Assam</option>
							<option>Bihar</option>
							<option>Chhattisgarh</option>
							<option>Goa</option>
							<option>Gujarat</option>
							<option>Haryana</option>
							<option>Himachal Pradesh</option>
							<option>Jammu and Kashmir</option>
							<option>Jharkhand</option>
							<option>Karnataka</option>
							<option>Kerala</option>
							<option>Madya Pradesh</option>
							<option>Maharashtra</option>
							<option>Manipur</option>
							<option>Meghalaya</option>
							<option>Mizoram</option>
							<option>Nagaland</option>
							<option>Orissa</option>
							<option>Punjab</option>
							<option>Rajasthan</option>
							<option>Sikkim</option>
							<option>Tamil Nadu</option>
							<option>Telangana</option>
							<option>Tripura</option>
							<option>Uttaranchal</option>
							<option>Uttar Pradesh</option>
							<option>West Bengal</option>
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
								class="paddingLeft cursorPointer ml" id="clonediv"
								title="Add Row">+</a>
						</h4>
					</div>
					<div class="row clonedInput mb">
						<div class="col-md-3">
							<label class="clearfix">From</label>
							<div class="clearfix">
								<input type="text" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-3">
							<label class="clearfix">To</label>
							<div class="clearfix">
								<input type="text" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-3">
							<label class="clearfix">Amount</label>
							<div class="clearfix">
								<input type="text" class="form-control" name="Website" value=""
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-2">
							<label class="clearfix">Month</label>
							<div class="clearfix">
								<select class="width100percnt selectpicker"
									data-live-search="true" title="Select Month">
									<option selected>April</option>
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
			<div class="clearfix"></div>
			<div class="clearfix footerstyelsNew">
				<div class="col-lg-12">
					<div class="pull-right">
						<input value="Save" class="btn btn-success btnSuccess"
							type="button"> <input value="Cancel"
							class="btn btn-default ml10" type="button">
					</div>
				</div>
			</div>

		</div>
	</div>
</section>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pfRowId" value="0"></c:set>
<script type="text/javascript">
	var activeTaxTab = "createStateTax";
	var stateTaxId = ${stateTax.id};
	var pfRowId = ${pfRowId};
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">

	<jsp:include page="../payroll_india/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">
		<div class="tablelist-wrap">
			<form action="" id="addStateTaxForm" method="post">
				<input type="hidden" value="${stateTax.id}" name="id" />
				<input type = "hidden" value = "${stateTax.statetaxuuid }" name ="statetaxuuid">
				<input type="hidden" value="" name="removeIds" id="removeIds"/>
				<c:if test="${stateTax.id > 0}">
					<input type="hidden" value="${stateTax.taxTypeId}" name="taxTypeId"/>
					<input type="hidden" value="${stateTax.financialYear}" name="financialYear"/>
					<input type="hidden" value="${stateTax.stateId}" name="stateId"/>
				</c:if>
				<div class="col-md-10">
					<div class="clearfix row">
						<div class="col-md-4 dropdown-select mt10 mb10">
							<select class="selectpicker width100percnt"
								title="Select Type of Tax" data-live-search="true" id="taxType"
								field-name="TaxType" data-validation="required" name="taxTypeId" ${stateTax.id > 0 ? "disabled='disabled'" : ""}>
								<c:forEach items="${taxTypes}" var="t">
									<option value="${t.id}"
										${stateTax.taxTypeId==t.id?"selected='selected'":""}>${t.description}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-md-4 dropdown-select mt10 mb10">
							<select class="selectpicker width100percnt" title="Select Year"
								data-live-search="true" id="financialYear" field-name="Year"
								data-validation="required" name="financialYear" ${stateTax.id > 0 ? "disabled='disabled'" : ""}>
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
								data-validation="required" name="stateId" ${stateTax.id > 0 ? "disabled='disabled'" : ""}>
								<option value="0">Select State</option>
								<c:forEach items="${stateList}" var="s">
									<option value="${s.id}"
										${stateTax.stateId==s.id?"selected='selected'":""}>${s.stateName}</option>
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
						<div class="row clonedInput mb" id="dynamicPTList"
							name="professionalTaxList">
							<c:forEach items="${stateTax.professionalTaxList}" var="pt">
								<div data-name='ptRow' data-id='${pt.id}'>
									<input type="hidden" value="${pt.id}"
										name="professionalTaxList[${pfRowId}].id">
									<div class="col-md-3">
										<label class="clearfix">${pfRowId==0?"From":""}</label>
										<div class="clearfix">
											<input type='text' class='form-control'
												data-name='amountFrom'
												name='professionalTaxList[${pfRowId}].amountFrom'
												value='${pt.amountFrom}' placeholder='Enter Amount'
												field-name='From Amount'
												data-validation='required validate_float'>
										</div>
									</div>
									<div class="col-md-3">
										<label class="clearfix">${pfRowId==0?"To":""}</label>
										<div class="clearfix">
											<input type='text' class='form-control' data-name='amountTo'
												name='professionalTaxList[${pfRowId}].amountTo'
												value='${pt.amountTo}' placeholder='Enter Amount'
												field-name='To Amount'
												data-validation='required validate_float'>
										</div>
									</div>
									<div class="col-md-3">
										<label class="clearfix">${pfRowId==0?"Amount":""}</label>
										<div class="clearfix">
											<input type='text' class='form-control' data-name='amount'
												name='professionalTaxList[${pfRowId}].amount'
												value='${pt.amount}' placeholder='Enter Amount'
												field-name='Amount'
												data-validation='required validate_float'>
										</div>
									</div>
									<div class="col-md-2">
										<label class="clearfix">${pfRowId==0?"Month":""}</label>
										<div class="clearfix">
											<select class='width100percnt selectpicker'
												data-live-search='true' title='Select Month'
												data-name='monthApplicable'
												name='professionalTaxList[${pfRowId}].monthApplicable'
												field-name='Month' data-validation='required'>
												<option
													${pt.monthApplicable=="All"?"selected='selected'":""}>All</option>
												<option
													${pt.monthApplicable=="April"?"selected='selected'":""}>April</option>
												<option
													${pt.monthApplicable=="May"?"selected='selected'":""}>May</option>
												<option
													${pt.monthApplicable=="June"?"selected='selected'":""}>June</option>
												<option
													${pt.monthApplicable=="July"?"selected='selected'":""}>July</option>
												<option
													${pt.monthApplicable=="August"?"selected='selected'":""}>August</option>
												<option
													${pt.monthApplicable=="September"?"selected='selected'":""}>September</option>
												<option
													${pt.monthApplicable=="October"?"selected='selected'":""}>October</option>
												<option
													${pt.monthApplicable=="November"?"selected='selected'":""}>November</option>
												<option
													${pt.monthApplicable=="December"?"selected='selected'":""}>December</option>
												<option
													${pt.monthApplicable=="January"?"selected='selected'":""}>January</option>
												<option
													${pt.monthApplicable=="February"?"selected='selected'":""}>February</option>
												<option
													${pt.monthApplicable=="March"?"selected='selected'":""}>March</option>
											</select>
										</div>
									</div>
									<div class="col-md-1">
										<label class="clearfix">${pfRowId==0?"Actions":""}</label>
										<ul class="list-unstyled list-inline mt5 text-center">
											<c:choose>
												<c:when test="${pfRowId==0}">
													<li><a href="javascript:void(0)" class="cursorPointer"
														onclick="tax.addDynamicPTRow(false);" title="Add Row"
														style="font-size: 20px;"> + </a></li>
												</c:when>
												<c:otherwise>
													<li><a title="Delete Row"
														class="fa fa-trash text-danger deleteRow"
														onClick="tax.deletePTRow(this)"></a></li>
												</c:otherwise>
											</c:choose>
										</ul>
									</div>
									<div class="clearfix"></div>
									<c:set var="pfRowId" value="${pfRowId+1}"></c:set>
									<script type="text/javascript">
										var pfRowId = ${pfRowId};
									</script>
									</div>
							</c:forEach>
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
					<input value="Save" class="btn btn-success btnSuccess"type="button" id="saveStateTax"> 
					<input value="Cancel" class="btn btn-default ml10" type="button" id ="cancel" onclick = "cancel();" >
				</div>
			</div>
		</div>
	</div>
</section>

<script>
function cancel(){
	window.location.href =  sourceleadUrl+"/stateTax/list";
}

</script>

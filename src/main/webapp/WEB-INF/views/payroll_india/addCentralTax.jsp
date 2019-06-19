<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="tdsRowId" value="0"></c:set>
<script type="text/javascript">
	var activeTaxTab = "createCentralTax";
	var centralTaxId = ${centralTax.id};
	var tdsRowId = ${tdsRowId};
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">
	<jsp:include page="../payroll_india/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">
		<div id="loading"></div>
		<div class="tablelist-wrap">

			<form:form id="createCentralTaxform" name="createCentralTaxform"
				method="post">
				<input type="hidden" value="${centralTax.id}" name="id" />
				<input type="hidden" value="${centralTax.centraltaxuuid }" name="centraltaxuuid" id="centraltaxuuid" />
				<input type="hidden" value="" name="removeIds" id="removeIds" />
				<c:if test="${centralTax.id > 0}">
					<input type="hidden" value="${centralTax.sourceleadTaxId}"
						name="taxTypeId" />
				</c:if>
				<div class="col-md-10">
					<div class="clearfix row">
						<div class="col-md-4 dropdown-select mt10 mb10">
							<select class="selectpicker width100percnt"
								title="Select Type of Tax" data-live-search="true"
								id="sourceleadTaxType " field-name="TaxType"
								data-validation="required" name="sourceleadTaxId">
								<c:forEach items="${taxTypes}" var="t">
									<option value="${t.id}" ${centralTax.sourceleadTaxId==t.id?"selected='selected'":""}>${t.description}</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-md-3 dropdown-select mt10 mb10">
							<select class="selectpicker width100percnt" title="Select Year"
								data-live-search="true" id="stateTaxYearList" field-name="Year"
								data-validation="required" name="ctfinancialYear">
								<c:forEach items="${yearList}" var="year">
									<option ${year.value==true?"selected=selected":""}
										value="${year.key}">${year.key}</option>
								</c:forEach>
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
							<input type="number" class="form-control" id="ceilvaluepf"
								name="ceilvaluepf" field-name="CeilValue" data-validation="validate_length length1-10 "
								 data-validation-optional="true" value="${centralTax.ceilvaluepf}" placeholder="Enter">
						</div>
							</div>
							<div class="col-md-5">
								<label class="clearfix">No. of Employees</label>
								<div class="clearfix">
									<input type="number" title="no.of employees" id="noofemployees"
										name="noofemployees" class="form-control" name="Website" data-validation="validate_length length1-10 "
										value="${centralTax.noofemployees}" placeholder="Enter">
								</div>
							</div>
							<div class="col-md-5">
								<label class="clearfix">Employee</label>
								<div class="clearfix">
									<input type="number" id="employeepf" name="employeepf"
										class="form-control" name="Website" data-validation=" validate_float validate_length length1-5 "
										value="${centralTax.employeepf}" placeholder="Enter %" >
									
								</div>
							</div>
							<div class="col-md-5">
								<label class="clearfix">Employer</label>
								<div class="clearfix">
						
									<input type="number" id="employerpf" name="employerpf" data-validation=" validate_float validate_length length1-5 "
										class="form-control" name="Website"
										value="${centralTax.employerpf}" placeholder="Enter %">
								</div>
							</div>
							<div class="clearfix"></div>
						</div>

						<div class="row">
							<h4 class="col-md-10">ESI</h4>
							<div class="col-md-5">
								<label class="clearfix">ESI Ceil Value</label>
								<div class="clearfix">
									<input type="number" title="ESI Ceil Value"
										data-live-search="true" id="ceilvalueesi"
										field-name="ESI CeilValue" data-validation="validate_length length1-10 "
										name="ceilvalueEsi" class="form-control" name="Website"
										value="${centralTax.ceilvalueEsi}" placeholder="Enter" >
								</div>
							</div>
							<div class="col-md-5">
								<label class="clearfix">Employee</label>
								<div class="clearfix">
									<input type="number"  id="employeeesi" name="employeeEsi" data-validation=" validate_float validate_length length1-5 "
										class="form-control" name="Website"
										value="${centralTax.employeeEsi}" placeholder="Enter">
								</div>
							</div>
							<div class="col-md-5">
								<label class="clearfix">Employer</label>
								<div class="clearfix">
									<input type="number" id="employeresi" name="employerEsi" data-validation=" validate_float validate_length length1-5 "
										class="form-control" name="Website"
										value="${centralTax.employerEsi}" placeholder="Enter">
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
						<div class="clearfix">
							<div class="clearfix minHeightcol5">
								<div class="row">
									<h4 class="col-md-6">
										TDS <a href="javascript:void(0)" id="addTDSRow"
											onclick="tax.addDynamicTDSRow(false);"
											class="paddingLeft cursorPointer ml" id="clonediv"
											title="Add Row">+</a>
									</h4>
								</div>
								<div class="row clonedInput mb colmd2Custom" id="dynamicTDSList"
									name="tdsTaxList">

									<c:forEach var="tds" items="${centralTax.tdsTaxList}">
										<div data-name='tdsRow' data-id='${tds.id}'>
											<input type="hidden" value="${tds.id}"
												name="tdsTaxList[${tdsRowId}].id">
											<div class="col-md-2 mb">
												<label class="clearfix">${tdsRowId==0?"Age":""}</label>
												<div class="clearfix">
													<select class="width100percnt selectpicker" id="age"
														name="tdsTaxList[${tdsRowId}].age" data-live-search="true" data-name='age' title="Select Age">
														<option ${tds.age == '0 - 60 Years' ? 'selected' : ''}
															value="0 - 60 Years">0 - 60 Years</option>
														<option ${tds.age == '60 - 80 Years' ? 'selected' : ''}
															value="60 - 80 Years">60 - 80 Years</option>
														<option ${tds.age == 'Above 80' ? 'selected' : ''}
															value="Above 80">Above 80</option>

													</select>
												</div>
											</div>
											<div class="col-md-2 mb">
												<label class="clearfix">${tdsRowId==0?"From":""}</label>
												<div class="clearfix">
													<input type="text" id="fromamount" name="tdsTaxList[${tdsRowId}].fromamount"
														class="form-control" data-name="fromamount"
														value="${tds.fromamount}" placeholder="Enter">
												</div>
											</div>
											<div class="col-md-2">
												<label class="clearfix">${tdsRowId==0?"To":""}</label>
												<div class="clearfix mb">
													<input type="text" id="toamount" name="tdsTaxList[${tdsRowId}].toamount"
														class="form-control" data-name="toamount"
														value="${tds.toamount}" placeholder="Enter">
												</div>
											</div>
											<div class="col-md-2 mb">
												<label class="clearfix">${tdsRowId==0?"Percentage":""}</label>
												<div class="clearfix">
													<input type="text" id="percentage" name="tdsTaxList[${tdsRowId}].percentage"
														class="form-control" data-name="percentage"
														value="${tds.percentage}" placeholder="Enter">
												</div>
											</div>
											<div class="col-md-2 mb">
												<label class="clearfix">${tdsRowId==0?"Education Ceiss":""}</label>
												<div class="clearfix">
													<input type="text" id="educationceiss"
														name="tdsTaxList[${tdsRowId}].educationceiss" class="form-control" data-name="educationceiss"
														value="${tds.educationceiss}" placeholder="Enter">
												</div>
											</div>
											<div class="col-md-2 mb">
												<label class="clearfix">${tdsRowId==0?"Surcharge":""}</label>
												<div class="clearfix">
													<input type="text" id="surcharge" name="tdsTaxList[${tdsRowId}].surcharge"
														class="form-control" data-name="surcharge"
														value="${tds.surcharge}" placeholder="Enter">
												</div>
											</div>

											<div class="col-md-1 mb">
												<label class="clearfix">${tdsRowId==0?"Actions":""}</label>
												<ul class="list-unstyled list-inline mt5 text-center">
													<c:choose>
														<c:when test="${tdsRowId==0}">
															<li><a href="javascript:void(0)"
																class="cursorPointer"
																onclick="tax.addDynamicTDSRow(false);" title="Add Row"
																style="font-size: 20px;"> + </a></li>
														</c:when>
														<c:otherwise>
															<li><a title="Delete Row"
																class="fa fa-trash text-danger deleteRow"
																onClick="tax.deleteTDSRow(this)"></a></li>
														</c:otherwise>
													</c:choose>
												</ul>
											</div>
											<div class="clearfix"></div>
											<c:set var="tdsRowId" value="${tdsRowId+1}"></c:set>
											<script type="text/javascript">
												var tdsRowId = ${tdsRowId};
											</script>
										</div>
									</c:forEach>
								</div>
							</div>
							<div id="cloneArea"></div>
						</div>
					</div>
				</div>

				<div class="clearfix"></div>
				<div class="clearfix footerstyelsNew">
					<div class="col-lg-12">
						<div class="pull-right">
							<input value="Save" id="saveCentralTax"
								class="btn btn-success btnSuccess" type="button"> <input
								value="Cancel" class="btn btn-default ml10" type="button" onclick = "cancel();">
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	</div>
</section>

<script>
function cancel(){
	window.location.href =  sourceleadUrl+"/centralTax/list";
}

</script>
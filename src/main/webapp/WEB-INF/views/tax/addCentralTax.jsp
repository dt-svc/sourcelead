<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript">
	var activeTaxTab = "createCentralTax";
</script>
<script type="text/javascript" src='/resources/js/tax/tax.js'></script>
<section class="content-wraper contentWraper">
	<jsp:include page="../tax/taxHeader.jsp"></jsp:include>
	<div class="timesheet_out">
<div id="loading"></div>
		<div class="tablelist-wrap">

  <form:form id="createCentralTaxform"  name="createCentralTaxform" method="post">
			<div class="col-md-10">
				<div class="clearfix row">
					<div class="col-md-4 dropdown-select mt10 mb10">
						<select class="selectpicker width100percnt" title ="Select Type of Tax" data-live-search="true" id="sourceleadTaxType " field-name="TaxType" data-validation="required"  name="sourceleadTaxId" >
						<c:forEach items="${taxTypes}" var="t">
									<option value="${t.id}"
										${centralTax.sourceleadTaxId==t.id?"selected='selected'":""}>${t.description}</option>
								</c:forEach>
						</select>
					</div>
					
						<div class="col-md-3 dropdown-select mt10 mb10">
					<select class="selectpicker width100percnt" title ="Select Year" data-live-search="true" id="stateTaxYearList" field-name="Year" data-validation="required" name = "ctfinancialYear" >
						<c:forEach items="${yearList}" var="year">
									<%-- <option ${year.key == centralTax.ctfinancialYear ? 'selected=selected':""}	value="${year.key}">${year.key}</option> --%>
									<option ${year.value==true?"selected=selected":""} value="${year.key}">${year.key}</option>
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
								<input type="text" title ="Ceil Value" data-live-search="true" id = "ceilvaluepf" field-name="CeilValue" data-validation="required" name = "ceilvaluepf" class="form-control" name="Website" value="${centralTax.ceilvaluepf}" placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">No. of Employees</label>
							<div class="clearfix">
								<input type="text" title="no.of employees" id = "noofemployees" name = "noofemployees" class="form-control" name="Website" value="${centralTax.noofemployees}" placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employee</label>
							<div class="clearfix">
								<input type="text" id = "employeepf" name = "employeepf" class="form-control" name="Website" value="${centralTax.employeepf}"
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employer</label>
							<div class="clearfix">
								<input type="text"  id = "employerpf" name = "employerpf" class="form-control" name="Website" value="${centralTax.employerpf}"
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
								<input type="text" title ="ESI Ceil Value" data-live-search ="true" id = "ceilvalueesi" field-name="ESI CeilValue" data-validation="required"  name =  "ceilvalueEsi"  class="form-control" name="Website" value="${centralTax.ceilvalueEsi}"
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employee</label>
							<div class="clearfix">
								<input type="text" id = "employeeesi" name = "employeeEsi" class="form-control" name="Website" value="${centralTax.employeeEsi}"
									placeholder="Enter">
							</div>
						</div>
						<div class="col-md-5">
							<label class="clearfix">Employer</label>
							<div class="clearfix">
								<input type="text" id = "employeresi" name = "employerEsi" class="form-control" name="Website" value="${centralTax.employerEsi}"
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
									TDS <a href="javascript:void(0)" id="addTDSRow" onclick="tax.addDynamicTDSRow(false);" class="paddingLeft cursorPointer ml" id="clonediv" title="Add Row">+</a>
								</h4>
							</div>
							<div class="row clonedInput mb" id="dynamicTDSList" name="tdsTaxList">
								
							<c:forEach var="tds" items="${centralTax.tdsTaxList}">
								
						 
								<div class="col-md-2">
									<label class="clearfix">Age</label>
									<div class="clearfix">
										<select class="width100percnt selectpicker" id = "age" name = "age" 
											data-live-search="true" title="Select Age">
											 <option ${tds.age == '0 - 60 Years' ? 'selected' : ''} value="0 - 60 Years">0 - 60 Years</option>
											 <option ${tds.age == '60 - 80 Years' ? 'selected' : ''} value="60 - 80 Years">60 - 80 Years</option>
											 <option ${tds.age == 'Above 80' ? 'selected' : ''} value="Above 80">Above 80</option>
											
										</select>
									</div>
								</div>
								<div class="col-md-2">
									<label class="clearfix">From</label>
									<div class="clearfix">
										<input type="text" id = "fromamount" name = "fromamount" class="form-control" name="Website"
											value="${tds.fromamount}" placeholder="Enter">
									</div>
								</div>
								<div class="col-md-2">
									<label class="clearfix">To</label>
									<div class="clearfix">
										<input type="text" id = "toamount" name = "toamount" class="form-control" name="Website"
											value="${tds.toamount}" placeholder="Enter">
									</div>
								</div>
								<div class="col-md-1">
									<label class="clearfix">Percentage</label>
									<div class="clearfix">
										<input type="text" id = "percentage" name = "percentage" class="form-control" name="Website"
											value="${tds.percentage}" placeholder="Enter">
									</div>
								</div>
									<div class="col-md-2">
									<label class="clearfix">Education Ceiss</label>
									<div class="clearfix">
										<input type="text" id = "educationceiss" name = "educationceiss" class="form-control" name="Website"
											value="${tds.educationceiss}" placeholder="Enter">
									</div>
								</div>
									<div class="col-md-1">
									<label class="clearfix">Surcharge</label>
									<div class="clearfix">
										<input type="text" id = "surcharge" name = "surcharge" class="form-control" name="Website"
											value="${tds.surcharge}" placeholder="Enter">
									</div>
								</div>
							
								 <div class="col-md-1">
										<label class="clearfix">${tdsRowId==0?"Actions":""}</label>
										<ul class="list-unstyled list-inline mt5 text-center">
											<c:choose>
												<c:when test="${tdsRowId==0}">
													<li><a href="javascript:void(0)" class="cursorPointer"
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

							<div id="cloneArea"></div>

						</div>
					</div>

				</div>
			</div>
			<div class="clearfix"></div>
			<div class="clearfix footerstyelsNew">
				<div class="col-lg-12">
					<div class="pull-right">
						<input value="Save"  id = "saveCentralTax"  class="btn btn-success btnSuccess" type="button"> 
						<input value="Cancel" class="btn btn-default ml10" type="button">
					</div>
				</div>
			</div>
</form:form>
		</div>
	</div>
</section>


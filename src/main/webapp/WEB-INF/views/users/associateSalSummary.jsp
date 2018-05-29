  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
 
   <div id="tab-25" class="tabsMenu_tabs_inn">
			<div class="clearfix row minHeightcol5">
						<div class="col-md-3 col-md-offset-9">
						<ul class="list-inline list-unstyled pull-right mb0">
							<li><a href="javascript:void(0)" class="btn btn-primary btn-block" onClick="updateSalaryDetails();">Add New</a></li>
							<li><a href="javascript:void(0)" class="btn btn-warning btn-block" onClick="salaryHistory();">History</a></li>
							<li><a href="us-associates.html" class="btn btn-default btn-block">Edit</a></li>
							</ul>
						</div>
                	<div class="clearfix"></div>
                 <div class="col-md-5">
                  <label>Paygroup</label>
                  <div>
                    <p class="form-control-static text-muted">Monthly</p>
                  </div>
                </div>
                
                 <div class="col-md-5">
                  <label>Pay Type</label>
                  <div>
                    <p class="form-control-static text-muted">Hourly</p>
                  </div>
                </div>

               <div class="col-md-5">
                  <label>Pay Frequency</label>
                  <div>
                    <p class="form-control-static text-muted">Annually</p>
                  </div>
                </div>
                
                <div class="col-md-5">
                  <label>Normaly Hours</label>
                  <div>
                    <p class="form-control-static text-muted">80000</p>
                  </div>
                </div>
                <div class="col-md-5">
                  <label>Annual Salary</label>
                  <div>
                    <p class="form-control-static text-muted">20000</p>
                  </div>
                </div>
                 <div class="col-md-5">
                  <label>Per Pay Salary</label>
                  <div>
                    <p class="form-control-static text-muted">20000</p>
                  </div>
                </div>
                <div class="col-md-5">
                  <label>Hourly</label>
                  <div>
                    <p class="form-control-static text-muted">200$</p>
                  </div>
                </div>
                
                  <div class="clearfix"></div>
                  <h4 class="col-md-12">Pay Item</h4>
                 <div class="clearfix col-md-12 styleTables tableStyles-textLeft">
                 <div class="row">
                 	<table>
                 		<thead>
                 			<tr>
                 				<th>Pay Items</th>
                 				<th>Schedule</th>
                 				<th>Override</th>
                 				<th>Effective Date</th>
                 				<th>End Date</th>
                 				<th>Amount</th>
                 			</tr>
                 		</thead>
                 		<tbody>
                 			<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Bonus</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Commission</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Cash Tips</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>

                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Jury</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>

                 			</tr>
                 		<tr>
                 				<td>
									<div class="tblminwidth-100 text-left">Reimbursement</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div>Every Pay</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>override1</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>01/04/2018</div>
									</div>
                 				</td>
                 			</tr>
                 		</tbody>
                 	</table>
                 </div>
                 </div>
                 <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <a href="javascript:void(0)" class="anchorBlack">Skip</a>
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" type="button">
                    <input value="Cancel" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
              
            </div>
      </div>

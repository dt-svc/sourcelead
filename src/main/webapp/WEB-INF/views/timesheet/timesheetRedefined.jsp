<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function() {
	alert('${timesheetEmployeeDTO.currentTimeFrequency}');
});
</script>  

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Timesheets</h2>
		</div>
		<div class="pull-right brud-crum">Home &raquo; General Form
			&raquo; Create User</div>
	</section>

	<!-- Timesheet -->
	<form id="timesheet_form" name="timesheet_form">


		<div class="timesheet_out">
			<div class="timesheet_wrap">
				<div class="data_loading"></div>

				<div class="timesheetNewHead">
					<div class="timeNewSe">
						<select class="form-control chosen-select">
							<c:forEach var="projectList" items="${timesheetEmployeeDTO.projectMembersList}">
							    <option  value="${projectList.projectName}">${projectList.projectName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="timeNewSe">
						<select class="form-control chosen-select">
							<c:forEach var="timesheetFrequencies" items="${timesheetEmployeeDTO.timeFrequencies}">
							    <option  ${timesheetFrequencies.key == timesheetEmployeeDTO.currentTimeFrequency ?'selected':'' }>${timesheetFrequencies.value}</option>
							</c:forEach>
						</select>
					</div>
					<div class="timeNewSe">
						<b class="proj_active">${timesheetEmployeeDTO.timesheetSubmission.timesheetStatus}</b>
					</div>
					<div class="clearfix"></div>
				</div>

				<div class="timesheetNewTabs">
					<div class="project_side_wrap_inn_tabs">
						<ul class="project-tabs-menu">
						    <c:forEach var="timeFrequencies" items="${timesheetEmployeeDTO.timeSubFrequencies}">
						     	<c:if test="${timeFrequencies.key == timesheetEmployeeDTO.currentTimeSubFrequency}">
						     			<li class="current"><a href="#tab-1">${timeFrequencies.value}</a><i class="fa fa-star"></i></li>
						     	</c:if>
						     	<c:if test="${timeFrequencies.key != timesheetEmployeeDTO.currentTimeSubFrequency}">
						     			<li class="current"><a href="#tab-1">${timeFrequencies.value}</a><i class="fa fa-star"></i></li>
						     	</c:if>
						 	</c:forEach>
							
							<div class="export">
								<p>
									<i class="glyphicon glyphicon-export"></i>Export<i
										class="fa fa-caret-down"></i>
								</p>
								<ul>
									<li>PDF<i class="fa fa-file-pdf-o"></i></li>
									<li>Excel<i class="fa fa-file-excel-o"></i></li>
									<li>Word<i class="fa fa-file-word-o"></i></li>
								</ul>
							</div>
							<div class="clearfix"></div>
						</ul>

						<div class="project_tabs">
							<div id="tab-1" class="project_tabs_inn">
								<div class="timesheet_tableNew">
									<table id="timesheet" cellpadding="0" cellspacing="0">

										<tbody>
											<tr>
												<th>Tasks</th>
												<th>Mon<i>09-01-2017</i></th>
												<th>Tue<i>11-01-2017</i></th>
												<th>Wed<i>12-01-2017</i></th>
												<th>Thu<i>13-01-2017</i></th>
												<th>Fri<i>14-01-2017</i></th>
												<th>Sat<i>15-01-2017</i></th>
												<th>Sun<i>16-01-2017</i></th>
											</tr>

											<tr id="timesheetRow1">
												<td>
													<div>
														<select>
															<option value="volvo">Task1</option>
															<option value="saab">Task2</option>
															<option value="mercedes">Task3</option>
															<option value="audi">Task4</option>
														</select> <input type="button" value=""
															onClick="addTimeSheetRow('1')" class="addtaskfield" />
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>

											</tr>
										</tbody>
									</table>

								</div>
							</div>

							<div id="tab-2" class="project_tabs_inn">
								<div class="timesheet_tableNew">
									<table id="timesheet" cellpadding="0" cellspacing="0">

										<tbody>
											<tr>
												<th>Tasks</th>
												<th>Mon<i>09-01-2017</i></th>
												<th>Tue<i>11-01-2017</i></th>
												<th>Wed<i>12-01-2017</i></th>
												<th>Thu<i>13-01-2017</i></th>
												<th>Fri<i>14-01-2017</i></th>
												<th>Sat<i>15-01-2017</i></th>
												<th>Sun<i>16-01-2017</i></th>
											</tr>

											<tr id="timesheetRow1">
												<td>
													<div>
														<select>
															<option value="volvo">Task1</option>
															<option value="saab">Task2</option>
															<option value="mercedes">Task3</option>
															<option value="audi">Task4</option>
														</select> <input type="button" value=""
															onClick="addTimeSheetRow('1')" class="addtaskfield" />
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>

											</tr>
										</tbody>
									</table>

								</div>
							</div>

							<div id="tab-3" class="project_tabs_inn">
								<div class="timesheet_tableNew">
									<table id="timesheet" cellpadding="0" cellspacing="0">

										<tbody>
											<tr>
												<th>Tasks</th>
												<th>Mon<i>09-01-2017</i></th>
												<th>Tue<i>11-01-2017</i></th>
												<th>Wed<i>12-01-2017</i></th>
												<th>Thu<i>13-01-2017</i></th>
												<th>Fri<i>14-01-2017</i></th>
												<th>Sat<i>15-01-2017</i></th>
												<th>Sun<i>16-01-2017</i></th>
											</tr>

											<tr id="timesheetRow1">
												<td>
													<div>
														<select>
															<option value="volvo">Task1</option>
															<option value="saab">Task2</option>
															<option value="mercedes">Task3</option>
															<option value="audi">Task4</option>
														</select> <input type="button" value=""
															onClick="addTimeSheetRow('1')" class="addtaskfield" />
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>

											</tr>
										</tbody>
									</table>
								</div>
							</div>

							<div id="tab-4" class="project_tabs_inn">
								<div class="timesheet_tableNew">
									<table id="timesheet" cellpadding="0" cellspacing="0">

										<tbody>
											<tr>
												<th>Tasks</th>
												<th>Mon<i>09-01-2017</i></th>
												<th>Tue<i>11-01-2017</i></th>
												<th>Wed<i>12-01-2017</i></th>
												<th>Thu<i>13-01-2017</i></th>
												<th>Fri<i>14-01-2017</i></th>
												<th>Sat<i>15-01-2017</i></th>
												<th>Sun<i>16-01-2017</i></th>
											</tr>

											<tr id="timesheetRow1">
												<td>
													<div>
														<select>
															<option value="volvo">Task1</option>
															<option value="saab">Task2</option>
															<option value="mercedes">Task3</option>
															<option value="audi">Task4</option>
														</select> <input type="button" value=""
															onClick="addTimeSheetRow('1')" class="addtaskfield" />
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>

											</tr>
										</tbody>
									</table>
								</div>
							</div>

							<div id="tab-5" class="project_tabs_inn">
								<div class="timesheet_tableNew">
									<table id="timesheet" cellpadding="0" cellspacing="0">

										<tbody>
											<tr>
												<th>Tasks</th>
												<th>Mon<i>09-01-2017</i></th>
												<th>Tue<i>11-01-2017</i></th>
												<th>Wed<i>12-01-2017</i></th>
												<th>Thu<i>13-01-2017</i></th>
												<th>Fri<i>14-01-2017</i></th>
												<th>Sat<i>15-01-2017</i></th>
												<th>Sun<i>16-01-2017</i></th>
											</tr>

											<tr id="timesheetRow1">
												<td>
													<div>
														<select>
															<option value="volvo">Task1</option>
															<option value="saab">Task2</option>
															<option value="mercedes">Task3</option>
															<option value="audi">Task4</option>
														</select> <input type="button" value=""
															onClick="addTimeSheetRow('1')" class="addtaskfield" />
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment com_active"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>
												<td>
													<div>
														<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
															name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
														<div class="timesheet_comm">
															<i class="glyphicon glyphicon-comment"></i> <span
																id="teskcomment"><textarea></textarea></span>
														</div>
													</div>
												</td>

											</tr>
										</tbody>
									</table>
								</div>
							</div>

						</div>


						<!--<div id="divadttTd1" style="display:none;"  > <div id="projectName">Sourcelead </div></div>-->
						<div id="divadttTd1" style="display: none;">
							<div>
								<select>
									<option value="volvo">Task1</option>
									<option value="saab">Task2</option>
									<option value="mercedes">Task3</option>
									<option value="audi">Task4</option>
								</select> <input type="button" value=""
									onClick="addTimeSheetRow(REPLACEROWID)"
									class="addtaskfield removetaskfield" />
							</div>

						</div>
						<div id="divadttTd2" style="display: none;">
							<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
								name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
							<div class="timesheet_comm">
								<i class="glyphicon glyphicon-comment"></i> <span
									id="teskcomment"><textarea></textarea></span>
							</div>
						</div>
						<div id="divadttTd3" style="display: none;">
							<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
								name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
							<div class="timesheet_comm">
								<i class="glyphicon glyphicon-comment"></i> <span
									id="teskcomment"><textarea></textarea></span>
							</div>
						</div>
						<div id="divadttTd4" style="display: none;">
							<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
								name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
							<div class="timesheet_comm">
								<i class="glyphicon glyphicon-comment"></i> <span
									id="teskcomment"><textarea></textarea></span>
							</div>
						</div>
						<div id="divadttTd5" style="display: none;">
							<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
								name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
							<div class="timesheet_comm">
								<i class="glyphicon glyphicon-comment"></i> <span
									id="teskcomment"><textarea></textarea></span>
							</div>
						</div>
						<div id="divadttTd6" style="display: none;">
							<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
								name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
							<div class="timesheet_comm">
								<i class="glyphicon glyphicon-comment"></i> <span
									id="teskcomment"><textarea></textarea></span>
							</div>
						</div>
						<div id="divadttTd7" style="display: none;">
							<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
								name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
							<div class="timesheet_comm">
								<i class="glyphicon glyphicon-comment"></i> <span
									id="teskcomment"><textarea></textarea></span>
							</div>
						</div>
						<div id="divadttTd8" style="display: none;">
							<input type="text" id="REPLACEROWIDtimeSheetDay1Id"
								name="timeSheetDay1Id" class="inline_field" value="HH:MM" />
							<div class="timesheet_comm">
								<i class="glyphicon glyphicon-comment"></i> <span
									id="teskcomment"><textarea></textarea></span>
							</div>
						</div>


					</div>
				</div>




				<div class="timesheet_bot">
					<div class="timesheet_week_comm">
						<div class="commentFile">
							<i class="fa fa-info-circle"
								title="Text here text here text here"></i>
							<!--<span><input type="file" ><i class="fa fa-times"></i></span>-->
							<span><input type="file"><i class="fa fa-plus"></i></span>
						</div>

						<div class="totalWrap">
							<ul>
								<li><span>Billable Hours</span><input
									name="grandBillableTotal" id="grandBillableTotal" value="00:00"
									placeholder="HH/HH" readonly type="text"></li>
								<li><span>Non-Billable Hours</span><input
									name="grandNonBillableTotal" id="grandNonBillableTotal"
									value="00:00" placeholder="HH/HH" readonly type="text"></li>
								<li><span>Total Hours</span><input id="grandTotal"
									value="00:00" placeholder="HH/HH" readonly type="text"></li>
								<div class="clearfix"></div>
							</ul>
						</div>
						<div class="clearfix"></div>
					</div>
					<!--    	<div class="timesheet_sub_other">
        	Submit to others <a href="javascript:void(0)"><i class="fa fa-envelope"></i> Email</a>
            <a href="javascript:void(0)"><i class="fa fa-users"></i> Users</a>
        </div>-->
					<div class="clearfix"></div>

					<div class="timesheet_button clearfix">
						<div class="commentTime">
							<textarea class="form-control" placeholder="comments here..."></textarea>
						</div>
						<input type="button" value="Draft" class="time_btn time_dra" /> <input
							type="button" value="Submit" class="time_btn" /> <input
							type="button" value="Clear" class="time_btn time_cancel" />
					</div>

				</div>

				<div class="res_total_hours">
					<i></i>
					<p>Total hours</p>
					<h2>45:30</h2>
					<span>This Week</span>
				</div>

			</div>



		</div>

	</form>
	<!-- Timesheet -->

</section>




<script type="text/javascript"
	src='resources/js/slead-common.js?staticTime=${staticTime}'></script>

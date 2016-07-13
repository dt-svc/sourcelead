<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<body>
<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet approval</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">

	<div class="timesheet_appr_wrap">
		<div class="timesheet_appr_wrap">
		<div class="timesheet_head">
        	<ul>
                <li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c1"><label for="c1">All</label></li>
							<li><input type="checkbox" id="c2"><label for="c2">Approved</label></li>
							<li><input type="checkbox" id="c3"><label for="c3">Draft</label></li>
							<li><input type="checkbox" id="c4"><label for="c4">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>
                <li><i class="fa fa-filter"></i><span>Project</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c5"><label for="c5">All</label></li>
							<li><input type="checkbox" id="c6"><label for="c6">Approved</label></li>
							<li><input type="checkbox" id="c7"><label for="c7">Draft</label></li>
							<li><input type="checkbox" id="c8"><label for="c8">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>				
                <li><i class="fa fa-filter"></i><span>Employee</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c9"><label for="c9">All</label></li>
							<li><input type="checkbox" id="c10"><label for="c10">Approved</label></li>
							<li><input type="checkbox" id="c11"><label for="c11">Draft</label></li>
							<li><input type="checkbox" id="c12"><label for="c12">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>				
                <li><i class="fa fa-filter"></i><span>Approved<b>30</b></span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c13"><label for="c13">All</label></li>
							<li><input type="checkbox" id="c14"><label for="c14">Approved</label></li>
							<li><input type="checkbox" id="c15"><label for="c15">Draft</label></li>
							<li><input type="checkbox" id="c16"><label for="c16">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>				
                <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
                	<div class="export_pop">
                    	<ul>
                        	<li>PDF<i class="fa fa-file-pdf-o"></i></li>
                            <li>Excel<i class="fa fa-file-excel-o"></i></li>
                            <li>Word<i class="fa fa-file-word-o"></i></li>
                        </ul>
                    </div>
                </li>                
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
		
		<div class="timesheet_appr_table">
		
		<table>
        	<tbody>
			<thead>
            	<tr>
                	<th>Employee</th>
                    <th>Frequency</th>
                    <th>Total hours</th>
                    <th>Stage</th>
                    <th>Action</th>
                </tr>
			</thead>	
		<c:forEach items="${sourcelead_timesheetSubmissionsList}" var="approval" varStatus="p">
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>${approval.projectMembers.employee.user.firstName}</b><b>Admin</b></span></div></div>
					</td>
                    <td><div> <fmt:formatDate type="date" value="${approval.startDate}" />  To
                             <fmt:formatDate type="date" value="${approval.endDate}" />
						 </div>
					</td>
                    <td><div><a href="javascript:void(0)"> ${approval.totalHours}/40</a></div></td>
                    <td><div>${approval.status}</div></td>
                    <td><div><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div>
					</td>
                </tr>
            </c:forEach>
            
             <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>
					</td>
                    <td><div><input type="text" placeholder="DD/MM/YY" value="31/12/2030" >
						<input type="text" placeholder="DD/MM/YY" value="31/12/2030" ></div>
					</td>
                    <td><div><a href="javascript:void(0)">50/50</a></div></td>
                    <td><div>Waiting for approval</div></td>
                    <td><div><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div>
					</td>
                </tr>
				<tr>
                	<td><div class="time_appr_user"><img src="images/userprofile-img.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>
					</td>
                    <td><div><input type="text" placeholder="DD/MM/YY" value="31/12/2030" >
						<input type="text" placeholder="DD/MM/YY" value="31/12/2030" ></div>
					</td>
                    <td><div><a href="javascript:void(0)">50/50</a></div></td>
                    <td><div>Waiting for approval</div></td>
                    <td><div><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div>
					</td>
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>
					</td>
                    <td><div><input type="text" placeholder="DD/MM/YY" value="31/12/2030" >
						<input type="text" placeholder="DD/MM/YY" value="31/12/2030" ></div>
					</td>
                    <td><div><a href="javascript:void(0)">50/50</a></div></td>
                    <td><div>Waiting for approval</div></td>
                    <td><div><h3>Approved</h3></div>
					</td>
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>
					</td>
                    <td><div><input type="text" placeholder="DD/MM/YY" value="31/12/2030" >
						<input type="text" placeholder="DD/MM/YY" value="31/12/2030" ></div>
					</td>
                    <td><div><a href="javascript:void(0)">50/50</a></div></td>
                    <td><div>Waiting for approval</div></td>
                    <td><div><h3 class="reje_color">Rejected</h3>
					<i class="glyphicon glyphicon-comment">
							<strong><p>Rejected message here</p></strong>
						</i></div>
					</td>
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>
					</td>
                    <td><div><input type="text" placeholder="DD/MM/YY" value="31/12/2030" >
						<input type="text" placeholder="DD/MM/YY" value="31/12/2030" ></div>
					</td>
                    <td><div><a href="javascript:void(0)">50/50</a></div></td>
                    <td><div>Waiting for approval</div></td>
                    <td><div><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div>
					</td>
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div></div>
					</td>
                    <td><div><input type="text" placeholder="DD/MM/YY" value="31/12/2030" >
						<input type="text" placeholder="DD/MM/YY" value="31/12/2030" ></div>
					</td>
                    <td><div><a href="javascript:void(0)">50/50</a></div></td>
                    <td><div>Waiting for approval</div></td>
                    <td><div><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div>
					</td>
                </tr>				
            </tbody>
        </table>
       
	</div>
		
	</div>
	
</div>

		
		<!-- Project -->
		
      </section>
      <!-- Content Area Ends--> 

	
<!--   Timesheet approval popup   -->
	
<div class="time_appr_side_wrap">
	<div class="time_appr_side_wrap_inn">
	<div class="proj_close"><i class="glyphicon glyphicon-share-alt"></i></div>
        <div class="timesheet_table">
        	<table id="timesheet" cellpadding="0" cellspacing="0">
                
            	<tbody>
                	<tr>
                    	<th>Projects</th>
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Mon<i>15-06-2016</i></th>
                        <th>Total Hours</th>
                         </table>
                        </th> 
                        
                    </tr>
                    
                    <tr id="timesheetRow1">
                         <td class="pname"> <div>Sourcelead</div> </td>
                         <td>
                                 <input type="hidden" name="maxRowNumber1"  class="initiateTimesheetMax" id="maxRowNumber1"  value="1" />
                                 <table id="table1" cellpadding="0" cellspacing="0">
                                         <td>
                                              <div> 
                                                <select>
                                                  <option value="volvo">Task1</option>
                                                  <option value="saab">Task2</option>
                                                  <option value="mercedes">Task3</option>
                                                  <option value="audi">Task4</option>
                                                </select>
                                                <input type="button" value="" onClick="addTimeSheetRow('1')" class="addtaskfield"/>
                                              </div>
                                         </td> 
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment com_active"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment com_active"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
 										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
     										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>                                         
                                 
                                 
                                 </table>
                       </td>   
                    </tr>
                    
					<tr id="timesheetRow2">
                         <td class="pname"> <div>Sourcelead</div> </td>
                         <td>
                                 <input type="hidden" name="maxRowNumber2" id="maxRowNumber2" class="initiateTimesheetMax"  value="1" /> 
                                 <table id="table2" cellpadding="0" cellspacing="0"> 
                                         <td>
                                              <div> 
                                                <select>
                                                  <option value="volvo">Task1</option>
                                                  <option value="saab">Task2</option>
                                                  <option value="mercedes">Task3</option>
                                                  <option value="audi">Task4</option>
                                                </select>
                                                <input type="button" value="" onClick="addTimeSheetRow('2')" class="addtaskfield"/>
                                              </div>
                                         </td> 
                                         										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
 										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
     										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>                  
                                         
                                 </table>
                         </td>
                    </tr>                    
                    
					<tr class="last_child">
                    	<td></td>
                        <td> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <td class="total_field">Total</td>
                        <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                        <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>
                  
                                         <td> <div>
                  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
                  </div> </td>                 
                                         
                                 </table>
                         </td> 
                        
                    </tr>  
                                                                                    
                </tbody>
            </table>
            
            
             <!--<div id="divadttTd1" style="display:none;"  > <div id="projectName">Sourcelead </div></div>-->
             <div id="divadttTd1" style="display:none;" > 
                  <div> 
                         <select>
                          <option value="volvo">Task1</option>
                          <option value="saab">Task2</option>
                          <option value="mercedes">Task3</option>
                          <option value="audi">Task4</option>
                        </select>
                        <input type="button" value="" onClick="addTimeSheetRow(REPLACEROWID)" class="addtaskfield removetaskfield"/>
                  </div>
                  
             </div>
             <div id="divadttTd2" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
	     	</div>
             <div id="divadttTd3" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
             </div>
             <div id="divadttTd4" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
             </div>
             <div id="divadttTd5" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>           
             </div>
             <div id="divadttTd6" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>  
	    	 </div>
             <div id="divadttTd7" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
              </div>
             <div id="divadttTd8" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>            
	    	 </div>
             <div id="divadttTd9" style="display:none;">
				  <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" placeholder="" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>  
             </div>
                        
        </div>	
	</div>
</div>	
	
	<!--   Timesheet approval popup   -->
	
	
	<!--   Timesheet approval popup   -->

<div class="mask_time"></div>	
<div class="time_appr_rejct_pop">
	<textarea placeholder="Enter comment here"></textarea>
	<a href="javascript:void(0)">Submit</a><a class="close_color" href="javascript:void(0)">Cancel</a>
</div>	
	
	<!--   Timesheet approval popup   -->

	<!--   Timesheet approval seript files   -->
	
<script>
$(document).ready(function() {
    $(".timesheet_appr_table .glyphicon").click(function(){
		$(".timesheet_appr_table .glyphicon strong").slideToggle(200);
	});
    $("html").click(function() {
        $(".timesheet_appr_table .glyphicon strong").slideUp(200);
    });
    $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
        e.stopPropagation();
    });	
});


// close popup
$(document).ready(function() {
    $(".timesheet_appr_table .fa-times").click(function(){
		$(".mask_time").fadeIn(200);
		$(".time_appr_rejct_pop").slideToggle(200);
	});
    $(".time_appr_rejct_pop a").click(function(){
		$(".mask_time").fadeOut(200);
		$(".time_appr_rejct_pop").slideUp(200);
	});	
});

//  Timesheet approval side bar
$(document).ready(function() {
    $(".timesheet_appr_table a").click(function(){
		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
	});
    $(".proj_close").click(function(){
		$(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
	});	
});

$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

//Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	



</script>	



</body>
<jsp:include page="../masterFooter.jsp" />	
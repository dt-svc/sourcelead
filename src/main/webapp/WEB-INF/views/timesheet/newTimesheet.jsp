<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- Content Area -->
<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheets</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Create User</div>
        </section>
        
		<!-- Timesheet -->
<form id="timesheet_form" name="timesheet_form">

 
<div class="timesheet_out">
	<div class="timesheet_wrap">
	<div class="data_loading"></div>
       
		<div class="timesheetNewHead">
			<div class="timeNewSe">
			<select class="form-control chosen-select">
			    <c:forEach var="pm" items="${ProjectMembersList}">  
	                  <option value="${pm.id}">${pm.projects.projectName}</option>
                 </c:forEach>
             </select>
            </div>
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>January - 2017</option>
                  <option>February - 2017</option>
                  <option>March - 2017</option>
                  <option>April - 2017</option>
                  <option>May - 2017</option>
                  <option>June - 2017</option>
                  <option>July - 2017</option>
                  <option>August - 2017</option>
                  <option>September - 2017</option>
                  <option>October - 2017</option>
                  <option>November - 2017</option>
                  <option>December - 2017</option>
                </select>
            </div> 
			<div class="timeNewSe">
				<b class="proj_active">Active</b><i class="glyphicon glyphicon-comment com_active" >
					<div class="timeNewSeMess"><p>Rejected message here Rejected message here Rejected message here</p></div>
				</i>
			</div>
			<div class="clearfix"></div>          
		</div>  
       
<div class="timesheetNewTabs">
<div class="project_side_wrap_inn_tabs">
    <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Week 1</a></li>
        <li><a href="#tab-2">Week 2</a></li>
        <li><a href="#tab-3">Week 3</a></li>
        <li><a href="#tab-4">Week 4</a></li>
        <li><a href="#tab-5">Week 5</a></li>
		<div class="export">
			<p><i class="glyphicon glyphicon-export"></i>Export<i class="fa fa-caret-down"></i></p>
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
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow1">
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
                                 </table>
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
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow2">
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
                                 </table>
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
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow3">
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
                                 </table>
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
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow4">
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
                                 </table>
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
                        <th> 
                         <table id="1" cellpadding="0" cellspacing="0">
                             <th>Tasks </th>
                        <th>Mon<i>09-01-2017</i></th>
                        <th>Tue<i>11-01-2017</i></th>
                        <th>Wed<i>12-01-2017</i></th>
                        <th>Thu<i>13-01-2017</i></th>
                        <th>Fri<i>14-01-2017</i></th>
                        <th>Sat<i>15-01-2017</i></th>
                        <th>Sun<i>16-01-2017</i></th>
                         </table>
                        </th>                   
                    </tr>
                    
                    <tr id="timesheetRow5">
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
                                 </table>
                       </td>   
                        
                    </tr>                                                        
                </tbody>
            </table>
        </div>        	
        </div>         
        
    </div>
    
    
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

    
</div>	
</div>       
       
       
        
        
    <div class="timesheet_bot">
    	<div class="timesheet_week_comm">
            <div class="commentFile">
            	<span><input type="file" ><i class="fa fa-times"></i></span>
                <span><input type="file" ><i class="fa fa-plus"></i></span>
            </div>    
            <div class="commentTime"><textarea placeholder="comments here..."></textarea></div>
            <div class="clearfix"></div>
        </div>
<!--    	<div class="timesheet_sub_other">
        	Submit to others <a href="javascript:void(0)"><i class="fa fa-envelope"></i> Email</a>
            <a href="javascript:void(0)"><i class="fa fa-users"></i> Users</a>
        </div>-->
    	<div class="clearfix"></div>
        
    	<div class="timesheet_button">
        	<input type="button" value="Draft" class="time_btn time_dra" />
            <input type="button" value="Submit" class="time_btn" />
            <input type="button" value="Clear" class="time_btn time_cancel" />
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
      <!-- Content Area Ends--> 

      <!-- Footer Area -->
      <footer class="footer-area clearfix">
        <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
        <div class="powered-by"> Powered by: businessintelli.com </div>
      </footer>
      <!-- Footer Area --> 
    </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
    
        <script src="js/moment.min.js"></script>
        <script src="js/bootstrap-datetimepicker.min.js"></script> 
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script>     
    <script src="js/jquery-fun.js"></script>	
    
    
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
    
<script>

$(document).ready(function() {
    $("#hover").mouseenter(function(){
		$(".app_error_mes").addClass("app_error_mes_active");
	});
    $("#hover").mouseleave(function(){
		$(".app_error_mes").removeClass("app_error_mes_active");
	});	
});
</script>    
    	
    <script src="js/masterHeaderAction.js"></script>		
        
    <!-- Include all compiled plugins (below), or include individual files as needed --> 
    <script type="text/javascript">
        $(document).ready(function(e) {
            $('.side-menu').click(function(){
                $('.side-bar').toggleClass("side-bar-active");
                $('.user-panel').toggleClass("user-panel-active");
                $('.content-wraper').toggleClass("content-wraper-active");
                });
            $('.srch-btn-xs').click(function(){
              $(".search-area").toggleClass("search-area-active");
            });
            $('html').click(function(){
              $(".search-area").removeClass("search-area-active");
            });
            $('.srch-btn-xs, .search-area-txt').click(function(e){
              e.stopPropagation();
            });
            $('.login-user').click(function(){
             $('.user-details').toggleClass('user-details-active');
            });

            $("#notification").click(function(){
                $('.note-area').toggleClass('note-area-active');
                $('.user-details').removeClass('user-details-active');
                $('.setting-area').removeClass('setting-area-active');
                $('.alert-area').removeClass('alert-area-active');
            });
            $("#alert").click(function(){
                $('.alert-area').toggleClass('alert-area-active');
                $('.user-details').removeClass('user-details-active');
                $('.setting-area').removeClass('setting-area-active');
                $('.note-area').removeClass('note-area-active');
                $('.qlinks-area').removeClass('qlinks-area-active');
            });
            $("#qlinks").click(function(){
                $('.qlinks-area').toggleClass('qlinks-area-active');
                $('.alert-area').removeClass('alert-area-active');
                $('.user-details').removeClass('user-details-active');
                $('.setting-area').removeClass('setting-area-active');
                $('.note-area').removeClass('note-area-active');
            });
            $('html').click(function(){
              $('.user-details').removeClass("user-details-active");
              $('.setting-area').removeClass('setting-area-active');
              $('.note-area').removeClass('note-area-active');
              $('.alert-area').removeClass('alert-area-active');
              $('.qlinks-area').removeClass('qlinks-area-active');
            });
            $('.header-nav .navbar, .custom-navbar, .user-details').click(function(e){
              e.stopPropagation();
            });
            $('.settings').click(function(){
            $('.setting-area').toggleClass('setting-area-active');
            $('.note-area').removeClass('note-area-active');
            $('.user-details').removeClass('user-details-active');
            $('.alert-area').removeClass('alert-area-active');
            });
            $(".collpase-div").click(function(){
                $(".toggle-div").slideToggle(800);
            });
            $(".faclose").click(function(){
                $(".remove-div").addClass('hidden');
            });
            $(".bell").click(function(){
                    $(this).toggleClass('bell-active');			
                });
           $(".addLevel").click(function(){
               $(".level-second").toggleClass("level-second-active");
            });
            /* Date and time picker script */

        });   
       </script>
        <script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script>
    </div>
	
	<div class="time_pop"><p>Monday hours should not be more than 24<b>X</b></p></div>
	
<div class="app_error_mes">	
		 bifidshf ohdihfhd hh hodfo odjf dsofh ohsdfohf
	</div>      
    
    </body>
	
<script>
$(document).ready(function() {
    $(".initiateTimesheetMax").each(function(){
		        this.val("1");
    });
	
});	
</script> 
<script>
 
function callInlineEdit(local){     
       var id = local.id;
	   $("#"+id).addClass("inline_field");
       $("#"+id).editInPlace({
		
		saving_animation_color: "#ECF2F8",
		callback: function(idOfEditor, enteredText, orinalHTMLContent, settingsParams, animationCallbacks) {
			return enteredText;
		}
	});
}
</script>


<script>
 
function addTimeSheetRow(id) {
	          
		      
		    var tbl = document.getElementById("table"+id);
			var rowNum = $("#maxRowNumber"+id).val();
			var maxRowNum = parseInt(rowNum);
			$("#maxRowNumber"+id).val(maxRowNum + 1); 
			var row = tbl.insertRow(rowNum);
			row.setAttribute("id", "timesheetRow"+maxRowNum);
		    
		 
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (id));
			cell0.innerHTML = divadttTd0Str;
			
			 var cell1 = row.insertCell(1);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
                        divadttTd1Str = divadttTd1Str.replace(/ProjectId/g, (id));
			cell1.innerHTML = divadttTd1Str;
			
			
			var cell2 = row.insertCell(2);
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/ProjectId/g, (id));
			cell2.innerHTML = divadttTd2Str;
			
			
			var cell3 = row.insertCell(3);
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/ProjectId/g, (id));
			cell3.innerHTML = divadttTd3Str;
			
			var cell4= row.insertCell(4);
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/ProjectId/g, (id)); 
			cell4.innerHTML = divadttTd4Str;
			
			var cell5 = row.insertCell(5);
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/ProjectId/g, (id)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML;
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/ProjectId/g, (id));
			cell6.innerHTML = divadttTd6Str;
			
			var cell7 = row.insertCell(7);
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/ProjectId/g, (id));
			cell7.innerHTML = divadttTd7Str;

			
			//var cell9 = row.insertCell(9);
//			var divadttTd9Str = document.getElementById("divadttTd10").innerHTML;
//			divadttTd9Str = divadttTd9Str.replace("REPLACEROWID", (maxRowNum));
//			cell9.innerHTML = divadttTd9Str;
			
			 //mergeColumns();
			
			  
		}	
	 
       function mergeColumns(){
		          
				  var previous = null;
                  var cellToExtend = null;
		          $( ".pname" ).each(function() {
				        
						var jthis = $(this);
						var content = jthis.text()
						var valueOne = $.trim(previous);
					    var valueTwo = $.trim(content);
						  
						 
						if (valueOne == valueTwo) {     
							jthis.remove();
							if (cellToExtend.attr("rowspan") == undefined) {
								cellToExtend.attr("rowspan", 2);
							}
							else {    
								currentrowspan = parseInt(cellToExtend.attr("rowspan"));
								cellToExtend.attr("rowspan", currentrowspan + 1);
							}
						}
						else {      
							previous = content;
							cellToExtend = jthis;
						}
	
			    });
	   }
	 
$(".inline_field").click(function(){
	$(".time_pop").show();
});
$(".time_pop b").click(function(){
	$(".time_pop").hide();
});	 
	

$(".export p").click(function(){
	$(".export ul").slideToggle(200);
});	

	 
	</script>	
    <div class="theme-color">
<span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
	<ul class="list-unstyled">
    	<li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
    	<li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
    	<li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
    </ul>
</div>	
	</body>
    </html>
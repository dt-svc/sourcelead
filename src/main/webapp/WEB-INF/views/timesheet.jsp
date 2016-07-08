
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Timesheets .::</title>
    <link rel="icon" type="image/png" href="images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    
    <!--  <link href="css/bootstrap.min.css" rel="stylesheet"> -->
    <!-- <link href="css/timesheet.css" rel="stylesheet"> -->
    <link href="resources/css/jquery-ui.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
   
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/jquery-fun.js"></script>
    
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/css/font-awesome.min.css" type="text/css">
	<link rel="stylesheet" href="resources/css/login-styles.css" type="text/css" >
	
	<!-- for mask -->
	<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.js"></script>
	<script src="https://cloud.github.com/downloads/digitalBush/jquery.maskedinput/jquery.maskedinput-1.3.min.js"></script>
	
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/base/jquery-ui.css" type="text/css" >
    <script src="https://raw.githubusercontent.com/igorescobar/jQuery-Mask-Plugin/master/src/jquery.mask.js"></script>
 -->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
  <!--   <link rel="stylesheet" href="resources/css/jquery-ui.css"> -->
    
   
    
    <script>
	$(document).ready(function() {  alert("one");
			    $.mask.definitions['H'] = "[0-9]";
			    $.mask.definitions['h'] = "[0-9]";
			    $.mask.definitions['M'] = "[0-9]";
			    $.mask.definitions['m'] = "[0-9]";
			    $.mask.definitions['P'] = "[AaPp]";
			    $.mask.definitions['p'] = "[Mm]";
			    $(".timepicker").mask("Hh:Mm");
	 });
    </script>
 
</head>

<body>
 <form id="timesheet_form" name="timesheet_form" >

 
<div class="timesheet_out">   
<input class="timepicker" type="text" size="10" placeholder="hh:mm" />
<input class="timepicker" type="text" size="10" placeholder="hh:mm" />
	<div class="timesheet_wrap">
    	<div class="timesheet_head">
        	<ul>
            	<li><i class="glyphicon glyphicon-print"></i></li>
                <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
                	<div class="export_pop">
                    	<ul>
                        	<li>PDF<i class="fa fa-file-pdf-o"></i></li>
                            <li>Excel<i class="fa fa-file-excel-o"></i></li>
                            <li>Word<i class="fa fa-file-word-o"></i></li>
                        </ul>
                    </div>
                </li>
                <li><i class="fa fa-filter"></i><span>Approved<b>30</b></span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li>All <b>50</b></li>
                            <li>Approved <b>30</b></li>
                            <li>Draft <b>10</b></li>
                            <li>Rejected <b>10</b></li>
                        </ul>
                    </div>                	
                </li>                
            	<li><i class="glyphicon glyphicon-chevron-left"></i><span class="res_date"><i class="fa fa-calendar"></i>15/06 - 21/06</span>
                <i class="glyphicon glyphicon-chevron-right"></i><abbr>|</abbr></li>                
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
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
                    <c:forEach items="${sourcelead_projectsList}" var="project" varStatus="p">
                    <input type="hidden" name="projectId" value="${project.id}"/>
                    <tr id="timesheetRow1">
                         <td class="pname"> <div>${project.projectName}</div> </td>
                         <td>
                               <input type="hidden" name="maxRowNumber${project.id}"  class="initiateTimesheetMax" id="maxRowNumber${project.id}"  value="2" />
                               <table id="table${project.id}" cellpadding="0" cellspacing="0">
                               <tr id="timesheetRow1">
                                         <td>
                                              <div> 
                                              <select>
	                                               <c:forEach items="${sourcelead_tasksList}" var="task" varStatus="t">
	                                                  	<option value="${task.id}">${task.taskName}</option>
	                                               </c:forEach>
                                               </select>     
                                                <input type="button" value="" onClick="addTimeSheetRow(${project.id})" class="addtaskfield"/>
                                              </div>
                                         </td> 
										<td> <div>    
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay1Id" name="1project${project.id}timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment com_active" onclick="timesheetComment('1project${project.id}comment1Id');"></i>
                  <span id="1project${project.id}comment1Id"><textarea name="1project${project.id}comment1Id" ></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay2Id" name="1project${project.id}timeSheetDay2Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment com_active" onclick="timesheetComment('1project${project.id}comment2Id');"></i>
                  <span id="1project${project.id}comment2Id"><textarea></textarea></span></div>
                  </div> </td>
                  
 			      <td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay3Id" name="1project${project.id}timeSheetDay3Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment" onclick="timesheetComment('1project${project.id}comment3Id');"></i>
                  <span id="1project${project.id}comment3Id"><textarea></textarea></span></div>
                  </div> 
                  </td>
                  
     										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay4Id" name="1project${project.id}timeSheetDay4Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="1project${project.id}comment4Id" ><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay5Id" name="1project${project.id}timeSheetDay5Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="1project${project.id}comment5Id"><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay6Id" name="1project${project.id}timeSheetDay6Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="1project${project.id}comment6Id" ><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay7Id" name="1project${project.id}timeSheetDay7Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="1project${project.id}comment7Id" ><textarea></textarea></span></div>
                  </div> </td>
                  
										<td> <div>
                  <input type="text" onkeypress="return isNumber(event)" id="1project${project.id}timeSheetDay8Id" name="1project${project.id}timeSheetDay8Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="1project${project.id}comment8Id"><textarea></textarea></span></div>
                  </div> </td>                                         
                  </tr>              
                                 </table>
                       </td>   
                    </tr>
                    </c:forEach>   
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
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay1Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay1Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm">
	                  <i class="glyphicon glyphicon-comment" ></i>
	                  <span id="1project${project.id}comment1Id" ><textarea></textarea></span>
                  </div>
	     	</div>
	     	
	     	<div class="timesheet_comm">
                  <i class="glyphicon glyphicon-comment com_active" onclick="timesheetComment('1project${project.id}comment1Id');"></i>
                  <span id="1project${project.id}comment1Id"><textarea name="1project${project.id}comment1Id" ></textarea></span></div>
                  </div> </td>
                  
                  
                  
             <div id="divadttTd3" style="display:none;">
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay2Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay2Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
             </div>
             <div id="divadttTd4" style="display:none;">
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay3Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay3Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
             </div>
             <div id="divadttTd5" style="display:none;">
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay4Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay4Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>           
             </div>
             <div id="divadttTd6" style="display:none;">
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay5Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay5Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>  
	    	 </div>
             <div id="divadttTd7" style="display:none;">
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay6Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay6Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>
              </div>
             <div id="divadttTd8" style="display:none;">
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay7Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay7Id" class="inline_field" value="HH:MM" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>            
	    	 </div>
             <div id="divadttTd9" style="display:none;">
				  <input type="text" id="REPLACEROWIDprojectProjectIdtimeSheetDay8Id" name="REPLACEROWIDprojectProjectIdtimeSheetDay8Id" class="inline_field" placeholder="" />	
                  <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                  <span id="teskcomment"><textarea></textarea></span></div>  
             </div>
                        
        </div>
      
      
     <!--   Responsive  --> 
        
        
<div class="timesheet_table_res">
<div id="accordion">
    <h2>Product one</h2>
    <div class="time_cont_res">
    	<table cellpadding="0" cellspacing="0">
        	<tbody>
            	<tr>
                	<th>Task 1</th>
                    <th><div><input type="button" value="" onClick="addTimeSheetRow('2')"/><span><i class="fa fa-plus"></i>Add</span></div></th>
                </tr>
                <tr>
                	<td>Mon 15-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment com_active"></i>
                          <span id="teskcomment_res"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Tue 16-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Wed 17-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Thu 18-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Fri 19-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sat 20-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sun 21-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Total hours </td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>                                                                                                                
            </tbody>
        </table>
    </div>
    <h2>Product two</h2>
	<div class="time_cont_res">
    	<table cellpadding="0" cellspacing="0">
        	<tbody>
            	<tr>
                	<th>Task 1</th>
                    <th><div><input type="button" value="" onClick="addTimeSheetRow('2')"/><span><i class="fa fa-plus"></i>Add</span></div></th>
                </tr>
                <tr>
                	<td>Mon 15-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Tue 16-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Wed 17-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Thu 18-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Fri 19-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sat 20-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sun 21-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Total hours </td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>                                                                                                                
            </tbody>
        </table>
    </div>
    <h2>Product three</h2>
	<div class="time_cont_res">
    	<table cellpadding="0" cellspacing="0">
        	<tbody>
            	<tr>
                	<th>Task 1</th>
                    <th><div><input type="button" value="" onClick="addTimeSheetRow('2')"/><span><i class="fa fa-plus"></i>Add</span></div></th>
                </tr>
                <tr>
                	<td>Mon 15-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Tue 16-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Wed 17-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Thu 18-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Fri 19-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sat 20-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Sun 21-06-2016</td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>
                <tr>
                	<td>Total hours </td>
                    <td><div>
                          <input type="text" id="REPLACEROWIDtimeSheetDay1Id" name="timeSheetDay1Id" class="inline_field" value="HH:MM" />	
                          <div class="timesheet_comm"><i class="glyphicon glyphicon-comment"></i>
                          <span id="teskcomment"><textarea></textarea></span></div>
                  	</div></td>
                </tr>                                                                                                                
            </tbody>
        </table>
    </div>
</div>

</div>
        
 <!--   Responsive  --> 
          
      
      
      
      
        
    <div class="timesheet_bot">
    	<div class="timesheet_week_comm">
        	<textarea placeholder="comments here..."></textarea>
            <span>
            	<input type="file" ><label><i class="glyphicon glyphicon-save"></i>Choose file</label>
            </span>
            <div class="clearfix"></div>
        </div>
<!--    	<div class="timesheet_sub_other">
        	Submit to others <a href="javascript:void(0)"><i class="fa fa-envelope"></i> Email</a>
            <a href="javascript:void(0)"><i class="fa fa-users"></i> Users</a>
        </div>-->
    	<div class="timesheet_button">
    	    <input type="submit" name="submit" vaue="save">
        	<input type="button" value="Draft" class="time_btn time_dra" />
            <input type="button" value="Submit" class="time_btn" />
            <input type="button" value="Clear" class="time_btn time_cancel" />
        </div>
    	<div class="clearfix"></div>
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
 
function addTimeSheetRow(projectId) {
	          
		    var tbl = document.getElementById("table"+projectId);
			var rowNum = $("#maxRowNumber"+projectId).val();
			var maxRowNum = parseInt(rowNum);
			$("#maxRowNumber"+projectId).val(maxRowNum + 1); 
			var row = tbl.insertRow(maxRowNum-1);
			row.setAttribute("id", "timesheetRow"+maxRowNum);
			
			  
			var cell0 = row.insertCell(0);
			var divadttTd0Str = document.getElementById("divadttTd1").innerHTML;
			divadttTd0Str = divadttTd0Str.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.setAttribute("class", "pname");  
			cell0.innerHTML = divadttTd0Str;
			
			
			
			 var cell1 = row.insertCell(1);
			var divadttTd1Str = document.getElementById("divadttTd2").innerHTML;
			divadttTd1Str = divadttTd1Str.replace(/REPLACEROWID/g, (maxRowNum));
                        divadttTd1Str = divadttTd1Str.replace(/ProjectId/g, (projectId));
			cell1.innerHTML = divadttTd1Str;
			
			
			var cell2 = row.insertCell(2);
			var divadttTd2Str = document.getElementById("divadttTd3").innerHTML;
			divadttTd2Str = divadttTd2Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd2Str = divadttTd2Str.replace(/ProjectId/g, (projectId));
			cell2.innerHTML = divadttTd2Str;
			
			
			var cell3 = row.insertCell(3);
			var divadttTd3Str = document.getElementById("divadttTd4").innerHTML;
			divadttTd3Str = divadttTd3Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd3Str = divadttTd3Str.replace(/ProjectId/g, (projectId));
			cell3.innerHTML = divadttTd3Str;
			
			var cell4= row.insertCell(4);
			var divadttTd4Str = document.getElementById("divadttTd5").innerHTML;
			divadttTd4Str = divadttTd4Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd4Str = divadttTd4Str.replace(/ProjectId/g, (projectId)); 
			cell4.innerHTML = divadttTd4Str;
			
			var cell5 = row.insertCell(5);
			var divadttTd5Str = document.getElementById("divadttTd6").innerHTML;
			divadttTd5Str = divadttTd5Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd5Str = divadttTd5Str.replace(/ProjectId/g, (projectId)); 
			cell5.innerHTML = divadttTd5Str;
			
			var cell6 = row.insertCell(6);
			var divadttTd6Str = document.getElementById("divadttTd7").innerHTML;
			divadttTd6Str = divadttTd6Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd6Str = divadttTd6Str.replace(/ProjectId/g, (projectId));
			cell6.innerHTML = divadttTd6Str;
			
			var cell7 = row.insertCell(7);
			var divadttTd7Str = document.getElementById("divadttTd8").innerHTML;
			divadttTd7Str = divadttTd7Str.replace(/REPLACEROWID/g, (maxRowNum));
			divadttTd7Str = divadttTd7Str.replace(/ProjectId/g, (projectId));
			cell7.innerHTML = divadttTd7Str;

			var cell8 = row.insertCell(8);
			var divadttTd8Str = document.getElementById("divadttTd9").innerHTML;
			divadttTd8Str = divadttTd8Str.replace(/REPLACEROWID/g, (maxRowNum));
                        divadttTd8Str = divadttTd8Str.replace(/ProjectId/g,  (projectId));
			cell8.innerHTML = divadttTd8Str;
			
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
	 
	</script>
	
	<!-- <script>
	  function isNumber(evt) {   
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 59)) {
	        return false;
	    }
	    return true;
	 }
	</script> -->
	
<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>
	
<script type="text/javascript">
        $('#timesheet_form').submit(function(e) {
               e.preventDefault();
               var frm = $('#timesheet_form').serializeFormJSON();
               var data = JSON.stringify(frm); 
        	   alert("data"+data);
        	   $.ajax({
        		   	  type: "GET",
        	      	  url: "http://localhost:8080/sourcelead/saveTimesheet?json="+data,		
        		      success :function(result) {
        		    	   alert("hai");
        		      }
        	      	  
        	    }); 
       	    });
   </script>
<script type="text/javascript">
function timesheetComment(local){
	alert("hii"+local);
	$("#"+local).slideToggle(200);
} 
$(document).ready(function() {
    /* $(".timesheet_comm i").click(function(){
		$("#teskcomment").slideToggle(200);
	}); */
    $("html").click(function() {
        $(".teskcomment").slideUp(200);
    });
   /*  $(".timesheet_comm i, #teskcomment").click(function(e) {
        e.stopPropagation();
    });	 */
});	 
</script>	
   
</html>
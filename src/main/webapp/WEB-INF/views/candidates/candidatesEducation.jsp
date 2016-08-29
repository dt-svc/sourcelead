<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
 
 <script>
	$(document).ready(function() {
		document.getElementById("candidateEducation_form").reset();
	});
</script>
 
<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>


<div id="tab-4" class="project_tabs_inn">
              <div class="project_tabs_inn_head addUser-org">
              	<a href="javascript:void(0)"><i class="glyphicon glyphicon-plus"></i>Add Education</a>
                <!-- Popup Div -->
                    <div class="collapse-div-adduser col-md-4">
                    <form:form id="candidateEducation_form"  name="candidateEducation_form" method="post">
                    <i class="fa fa-close close-div"></i>
                          <div class="form-horizontal clearfix">
                          
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Education *" required>
                              </div>
                            </div>
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" placeholder="Specialization">
                              </div>
                            </div>   
                            
                            <div class="clearfix mb10">
                              <div class="col-md-6 positionRelative">
                                <input type="text" class="form-control input-sm" value="" name="startDate"  placeholder="Start Date" id="edu-startDate">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
                              </div>
                              <div class="col-md-6 positionRelative">
                                <input type="text" class="form-control input-sm" value="" name="endDate" placeholder="End Date" id="edu-endDate">
                                <span class="input-group-addon input-groupAddon"><i class="fa fa-calendar"></i></span>
                              </div>
                            </div>   
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" name= "grade" placeholder="Grade">
                              </div>
                            </div>                                                                              
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" name= "institute"  placeholder="Institute">
                              </div>
                            </div>

                            <div class="clearfix mb10">
                              <div class="col-md-12">
                                <input type="text" class="form-control input-sm" value="" name= "address" placeholder="Address">
                              </div>
                            </div>

                            <div class="clearfix mr">
                              <button type="submit" class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </div>
                         </form:form>
                        </div>
					<!-- Popup Div -->
              </div>
              <div class="project_tabs_inn_table vendor_wrap_inn_table">
                <table>
                  <tbody>
                    <tr>
                      <th>Education</th>
                      <th>Specilization</th>                      
                      <th>Start date</th>
                      <th>End date</th>            
                      <th>Grade %</th>                                
                      <th>Institute/University</th>
                      <th>Address</th>
                      <th>Action</th>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>
                    <tr>
                      <td><div><span>MBA MBA MBA MBA MBA</span></div></td>
                      <td><div><span>Finance</span></div></td>                      
                      <td><div><span>30/08/2016</span></div></td>
                      <td><div><span>30/09/2016</span></div></td>         
                      <td><div><span>65%</span></div></td>                                   
                      <td><div><span>JNTU JNTU JNTU JNTU</span></div></td>
                      <td><div><span>Hyderabad Hyderabad</span></div></td>
                      <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>
                    </tr>                   
                  </tbody>
                </table>
              </div>
              <nav>
                <ul id="pagingg" class="pagination">
                  <li><a href="#">Previous</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li class="disabled"><a href="#">6</a></li>
                  <li data-page="last"><a href="#">Next</a></li>
                </ul>
                <div class="clearfix"></div>
              </nav>
            </div>
            
            
  
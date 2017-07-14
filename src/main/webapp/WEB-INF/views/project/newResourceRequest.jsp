<!-- <link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet"> -->
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
         <h2 class="pull-left">New Resource Request</h2>
        <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector" style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/projectDashboard'/>">Projects List</a> &raquo;Project Summary</div>
        </section>
	   
		   <div class="content-area content-new clearfix">
			    <div class="row form-horizontal mt myForm">
			      <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
			         <div class="row">
			          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
			            <div class="form-group">
			              <label class="col-md-3">Request Id<span class="text-danger paddingLeft">*</span></label>
								<div class="col-md-9">
					                  <div class="form-icon">
					                   <input class="form-control pull-left" name="Project Id" value="" type="text">
					                   <i class="fa fa-refresh btn btn-default formic1" title="Project Id"></i>
					                   <span class="hidden">Project Id</span> 
					                   </div>
					           </div>            
					     </div>
			            <div class="form-group">
			            <label class="col-md-3">Approver <span class="text-danger paddingLeft">*</span></label>
			            <div class="col-md-9">
			             <select class="form-control chosen-select" multiple data-placeholder="Select Approver">
                  <option>Approver 1</option>
                  <option>Approver 2</option>
                  <option>Approver 3</option>
                  <option>Approver 4</option>
                </select></div>
			            </div>
			            <div class="form-group">
			              <label class="col-md-3">Description<span class="text-danger paddingLeft">*</span></label>
			              <div class="col-md-9">
			                  <textarea cols="1" class="form-control"></textarea>
			              </div>
			            </div>
			            </div>
			          </div>
			
			<div class="project_side_wrap_inn_tabs mt clearfix">
			    <div class="project_tabs_inn_head">
			        <a href="javascript:void(0)" class="AdRes"><i class="glyphicon glyphicon-plus"></i>Add</a>
			    </div>
			    <div class="addResource add-resource" style="display: block;">
					<span class="addResClo addResClose"><i class="fa fa-times"></i></span>
			          <div class="col-xs-12 col-sm-12 myForm noPad clearfix">
			              
			              <div class="col-md-3 mb10 dropdown-select">
			               <label>Role <span class="text-danger paddingLeft">*</span></label>
			                <select class="form-control">
			                  <option selected="">---Select Role---</option>
			                  <option>Role 1</option>
			                  <option>Role 2</option>
			                  <option>Role 3</option>
			                  <option>Other</option>
			                </select>
			              </div>
			            <div class="col-md-3 mb10">
			               <label>Custom Role<span class="text-danger paddingLeft">*</span></label>
			                <input type="text" class="form-control">
			              </div>
			              <div class="col-md-3 mb10 dropdown-select">
			               <label>No. of Resources <span class="text-danger paddingLeft">*</span></label>
			                <input type="text" class="form-control" name="" value="">
			              </div>
			              <div class="col-md-3 mb10 dropdown-select">
			               <label>Experience</label>
			                  <div class="row">
			                      <div class="col-md-6">
			                    <input type="text" class="form-control" name="" value="Min">
			                          </div>
			                      <div class="col-md-6">
			                        <input type="text" class="form-control" name="" value="Max">
			                      </div>
			                 </div>
			              </div>
			              
			              <div class="col-md-3 mb10 dropdown-select">
			               <label>Primary Skills <span class="text-danger paddingLeft">*</span></label>
			                <input type="text" class="form-control" name="" value="">
			              </div>
			              
			              <div class="col-md-3 mb10">
			               <label>Bill Rate (Per hour)</label>
			                <input type="text" class="form-control" name="Request Id" value="">
			              </div>
			              <div class="col-md-3 mb10">
			               <label>Pay Rate (Per hour)</label>
			                <input type="text" class="form-control" name="Request Id" value="">
			              </div>
			
			              <div class="col-md-3 mb10">
			               <label>Start Date</label>
			                <input type="text" class="form-control calendar" name="Request Id" value="">
			              </div>
			              <div class="col-md-3 mb10">
			               <label>End Date</label>
			                <input type="text" class="form-control calendar" name="Request Id" value="">
			              </div>  
			              <div class="col-md-3 mb10">
			               <label>Estimated Hours</label>
			                <input type="text" class="form-control timeicon" name="Request Id" value="">
			              </div>  
			              <div class="col-md-3 mb10 currencySymbol">
			                  
			                  <label>Estimated Budget</label>
			                  <div class="positionRelative">
			                <input type="text" class="form-control" name="Request Id" value="">
			                 <span class="currencyValue">INR</span>
			                      </div>
			              </div>
			              
			                    
	                    <div class="col-md-3 mb10 dropdown-select">
			               <label>Consulting <span class="text-danger paddingLeft">*</span></label>
			                <select class="form-control">
			                  <option selected="">--Select--</option>
	                          <option value="Offsite">Off Site</option>
	                          <option value="Onsite">On Site</option>
	                          <option value="Onsite">Near Shore</option>
	                          <option value="Onsite">Off Shore</option>
	                          <option value="Onsite">Hybrid</option>
			                </select>
			              </div>
			            
			            </div>
						<div class="col-xs-12 col-sm-12 myForm noPad clearfix">
							<div class="pull-right mr">
								<button class="btn btn-primary btn-sm mr10"><i class="fa fa-plus"></i> Add </button>
							    <button class="btn btn-default btn-sm cancel-btn">Cancel</button>
							</div>
						</div>
							<div class="clearfix"></div>
			          </div>
						
			<!-- Table Starts Here -->
			<div class="project_tabs_inn_table col-md-12">
					<table>
			        	<tbody>
			            	<tr>
			                    <th>Role</th>
			                    <th>No.Resources</th>
			                    <th>Exp.</th>
			                    <th>Primary Skills</th>
			                    <th>Bill/Pay Rate</th>
			                    <th>Duration</th>
			                    <th>On/Offsite</th>
			                    <th>Est Hours</th>
			                    <th>Est Budget</th>
			                    <th>Action</th>
			                </tr>
			                <tr>
			                	<td><div>Admin</div></td>
			                    <td><div>10</div></td>
			                    <td><div>6 Years</div></td>
			                    <td><div>Java</div></td>
			                    <td>
			                       <div class="width215px">
			                            <small class="text-muted paddingRight pull-left">INR</small>
			                           <div class="width150px inr_values"><small>23456789</small> <small>23456789</small></div>
			                           <small class="text-muted pull-right paddingLeft">Year</small>
			                        </div>
			                    </td>
			                    <td><div>05/02/2017 <br> 15/06/2017</div></td>
			                    <td><div>Offsite</div></td>
			                    <td><div>200</div></td>
			                    <td><div>25000</div></td>
			                    <td><div><i class=" fa fa-edit"></i><i class="glyphicon glyphicon-trash"></i></div></td>
			                </tr>
			            </tbody>
			            <tfoot class="table-footer">
			                <tr>
			                    <td><strong>Total</strong></td>
			                    <td><strong>50</strong></td>
			                    <td colspan="5"></td>
			                    <td><strong>800</strong></td>
			                    <td><strong>100000</strong></td>
			                    <td></td>
			                </tr>
			            </tfoot>
			        </table>  
					      
			<!-- Table Ends Here -->
			    </div> 
			</div>        
			
					<div class="form-footer clearfix mt">
					  <div class="col-lg-12">
						<div class="pull-right">
						  <input type="button" value="Save" class="btn btn-primary" id="save">
						  <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='/projectSummary?projectUUID=${projectUUID}&tabType=resources'/>'" class="btn btn-default ml">
						</div>
					  </div>
					</div>        
			        </div> 
			
			      </div>
			    </div>
			
	   </section>
          
            <jsp:include page="../masterFooter.jsp" />	

	<script>
	$(".addResource").slideUp(0); 
	$(".AdRes").click(function(){
		$(".addResource").slideToggle(0);
	});
	$(".addResClo").click(function(){
		$(".addResource").slideUp(0);
	});
	$(".cancel-btn").click(function(){
		$(".addResource").slideUp(0);
	});
	
	</script>
	

</html>
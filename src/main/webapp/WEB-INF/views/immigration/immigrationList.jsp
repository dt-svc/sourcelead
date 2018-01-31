
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
    <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix addImmiHead">
      <div class="pull-left"><h2>Immigration list</h2></div>
      <div class="pull-right brud-crum">Home &raquo; Immigrations List</div>
	<div class="clearfix"></div>
    </section>    
    
    <div class="immigrationWrap">
		<div class="immigraCountHead">
			<ul class="clearfix">
				<li class="clearfix">
					<span><i class="fa fa-plane"></i></span>
					<p>Draft <b>1000</b></p>
				</li>			
				<li class="clearfix">
					<span><i class="fa fa-plane"></i></span>
					<p> New <b>1000</b></p>
				</li>
				<li class="clearfix">
					<span><i class="fa fa-plane"></i></span>
					<p> Inprogress <b>1000</b></p>
				</li>
				<li class="clearfix">
					<span><i class="fa fa-plane"></i></span>
					<p>Accepted <b>1000</b></p>
				</li>
				<li class="clearfix">
					<span><i class="fa fa-plane"></i></span>
					<p>Rejected <b>1000</b></p>
				</li>				
			</ul>
		</div>
   
		<div class="immigraInnWrap">
			<div class="timesheet_head">
			  <ul>
				  <li>
					  <a href="${appPath}/loadNewPetition"><i class="fa fa-plus paddingRight"></i> New </a>
				  </li>

				  <li class="dropdown">
					<span class="dropdown-toggle" id="menu1" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch1" name="resourcerequest" id="ch1" type="checkbox">
						<label for="ch1">Draft</label>
					  </li>
					  <li>
						<input id="ch2" name="resourcerequest" type="checkbox">
						<label for="ch2">New</label>
					  </li>
					  <li>
						<input id="ch3" name="resourcerequest" type="checkbox">
						<label for="ch3">Inprogress</label>
					  </li>
					  <li>
						<input id="ch4" name="resourcerequest" type="checkbox">
						<label for="ch4">Accepted</label>
					  </li>
					  <li>
						<input id="ch5" name="resourcerequest" type="checkbox">
						<label for="ch5">Rejected</label>
					  </li>					  					  
					</ul>
				  </div>
				</li>

				  <li class="dropdown">
					<span class="dropdown-toggle" id="" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Attorney</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch6" name="resourcerequest" id="ch6" type="checkbox">
						<label for="ch6">Attorney 1</label>
					  </li>
					  <li>
						<input id="ch7" name="resourcerequest" type="checkbox">
						<label for="ch7">Attorney 2</label>
					  </li>
					  <li>
						<input id="ch8" name="resourcerequest" type="checkbox">
						<label for="ch8">Attorney 3</label>
					  </li>
					</ul>
				  </div>
				</li>

				  <li class="dropdown">
					<span class="dropdown-toggle" id="" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch11" name="resourcerequest" id="ch11" type="checkbox">
						<label for="ch11">H1B</label>
					  </li>
					  <li>
						<input id="ch12" name="resourcerequest" type="checkbox">
						<label for="ch12">H2B</label>
					  </li>
					  <li>
						<input id="ch13" name="resourcerequest" type="checkbox">
						<label for="ch13">Green card</label>
					  </li>
					</ul>
				  </div>
				</li> 
				
				  <li class="dropdown">
					<span class="dropdown-toggle" id="" data-toggle="dropdown">
						<i class="fa fa-filter"></i><span>Duration</span><i class="glyphicon glyphicon-play"></i></span>|

					<div class="dropdown-menu bootstrap_dropdownMenu" role="menu" aria-labelledby="menu1">
					<ul>
					  <li>
						<input value="ch15" name="resourcerequest" id="ch15" type="checkbox">
						<label for="ch15">This month</label>
					  </li>
					  <li>
						<input id="ch16" name="resourcerequest" type="checkbox">
						<label for="ch16">This year</label>
					  </li>
					  <li>
						<input id="ch17" name="resourcerequest" type="checkbox">
						<label for="ch17">Last year</label>
					  </li>
					  <li>
						<input id="ch18" name="resourcerequest" type="checkbox">
						<label for="ch18">Custom</label>
					  </li>					  
					</ul>
				  </div>
				</li> 				                                              

				<div class="clearfix"></div>
			  </ul>
			  <div class="clearfix"></div>
			  <div class="remove_search">
				<input placeholder="Search" type="text">
			  </div>
			</div>
			
			<div class="immigraTable">
			  <table>

				<thead>
				  <tr>
					<th>Status</th>
					<th>ID</th>
					<th>Type</th>
					<th>Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>On date</th>
					<th>Attorney</th>
					<th>&nbsp;</th>
				  </tr>
				</thead>

				<tbody>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="<c:url value='${appPath}/viewImmigration'/>">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                  
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                    
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                    
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                    
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                    
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                    
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                     
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                    
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                     
				  </tr>

				<tr>
					<td>
					  <div class="proj_show tbldata-dotdot-100">
						<select class="">
							<option value="">Status</option>
							<option value="">Option 1</option>
							<option value="">Option 2</option>
							<option value="">Option 3</option>
							<option value="">Option 4</option>
						 </select>              
					  </div>
					</td>
					<td>
					  <div class="proj_show tbldata-dotdot-100"><a href="immigration-view.html">
						  <div class="pull-left tbldata-dotdot_80" title="BIS10041">BIS10041</div>
						  <i class="fa fa-binoculars pull-right"></i></a>
					  </div>
					</td>
					<td>
					  <div title="H1B" class="tbldata-dotdot-80">H1B</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Vannam Mahesh</div>
					</td>
					<td>
						<div title="mahesh@businessintelli.com" class="tbldata-dotdot-150">mahesh@businessintelli.com</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">9177061166</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">1/12/2017</div>
					</td>
					<td>
						<div title="Vannam Mahesh" class="tbldata-dotdot-100">Attorney</div>
					</td>
					<td class="immigraAction">
						<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
						<div class="mmigraActionInn mmigraActionInnBig">
							<span>
								<a href="javascript:void(0)" title="Accept" class="fa fa-check"></a>
								<a href="javascript:void(0)" title="Reject" class="fa fa-times"></a>
								<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
								<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							</span>
						</div>
					</td>                                   
				  </tr>

				</tbody>

			  </table>
			</div>
			
		<div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div>			
			
		</div>   
   
    </div>
  </section>
  <!-- Content Area -->
  
  <div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-calendar-check-o"></i></span>
    <ul class="list-unstyled">
      <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
      <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
      <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
    </ul>
  </div>
</div>

    
<!-- Footer Area --> 

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
<script src="js/jquery-ui.min.js"></script> 
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
    // Script for tabs next pre button
    var $tabs = $('.tabbable li');
    $('#prevtab').on('click', function() {
        $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
    });
    $('#nexttab').on('click', function() {
        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
    });
</script> 
<script>
    function showMenu() {
        $('.menupop').toggle();
    };
    function receivePaymentPop(){
       $("#recvPymntpop").fadeIn(200);
        $('.side_mask').fadeIn(200).addClass('in');
    }
    $(".close").click(function(){
       $("#recvPymntpop").fadeOut(200);
        $('.side_mask').fadeOut(200).removeClass('in');
    });
</script>

  <script>
    //addcandidate-popup script
    function createBill(){
        $('.addcandidate-popup').fadeIn(200);
        $('.pop_mask').fadeIn(200);
    };
       function closeCandidate(){
        $('.addcandidate-popup').fadeOut(200);
        $('.pop_mask').fadeOut(200);
       }
    </script>
    
    <!-- Script for Linked Invoice pop -->
    <script>
        $(".sendReminder").click(function(){
        $('.modal.modal_invoice').fadeIn(200);
        $('.side_mask').fadeIn(200);
        });
        
        $(".close, .cancel").click(function(){
        $('.modal.modal_invoice').fadeOut(200);
        $('.side_mask').fadeOut(200);
        });
    </script>
    <!-- Script for Linked Invoice pop -->
<jsp:include page="../masterFooter.jsp" />
 <script type="text/javascript">

</script>

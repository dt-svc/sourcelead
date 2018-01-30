
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
      <div class="pull-right brud-crum">Home » Immigrations List</div>
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
					  <a href="new-petition.html"><i class="fa fa-plus paddingRight"></i> New </a>
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
				<li><a href="#"> &lt;&lt; </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> &gt;&gt; </a></li>
			  </ul>
		</div>			
			
		</div>   
   
    </div>    
   </section> 
   
<jsp:include page="../masterFooter.jsp" />
 

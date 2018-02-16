
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
   
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Immigrations</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Immigrations List &raquo; Immigration Summery</div>
      
		<div class="clearfix"></div>
		<div class="immigraHead">
			<ul class="immigraTabs">
              <li class="current"><a href="#tab-1">Details</a></li>
              <li><a href="#tab-9">Passport</a></li>
              <li><a href="#tab-10">Visa</a></li>
              <li><a href="#tab-11">Forms</a></li>
              <li><a href="#tab-2">Documents</a></li>
              <li><a href="#tab-3">Employers</a></li>
              <li><a href="#tab-4">Residence</a></li>
              <li><a href="#tab-5">Educations</a></li>
              <li><a href="#tab-6">Family</a></li>
              <li><a href="#tab-7">Notes</a></li>
              <li><a href="#tab-8">Activities</a></li>
              <div class="clearfix"></div>
            </ul>
		</div>
      
    </section>
    
	<section class="immigraTabsWrap">
  
	  <div id="tab-1" class="immigraTabs_inn">
		
		  <div class="immigraDetailsOut">
		  	<div class="immigraDetailsWrap">
			  <div class="immigraDetailsCta">
				  <h2>Petition detail</h2>
				  <ul>
					  <li class="clearfix"><span>Petition type</span><p>: Green Card</p></li>
					  <li class="clearfix"><span>Open date</span><p>: DD/MM/YY</p></li>
					  <li class="clearfix"><span>Current USCIS Status</span><p>: Green Card</p></li>
					  <li class="clearfix"><span>Date of arrival</span><p>: DD/MM/YY</p></li>
					  <li class="clearfix"><span>Place of entry</span><p>: Hyderabad</p></li>
					  <li class="clearfix"><span>Choose Template</span><p>: Template 1</p></li>
				  </ul>
			  </div>
			  <div class="immigraDetailsCta">
				  <h2>Attorney Details</h2>
				  <ul>
					  <li class="clearfix"><span>Attorney Name</span><p>: Snigi</p></li>
					  <li class="clearfix"><span>Email</span><p>: snigi@domain.com</p></li>
					  <li class="clearfix"><span>Phone</span><p>: 9000 000 000</p></li>
					  <li class="clearfix"><span>Fax</span><p>: 59896596</p></li>
					  <li class="clearfix"><span>Law firm</span><p>: Business Intelli</p></li>
				  </ul>
			  </div>
			  <div class="immigraDetailsCta">
				  <h2>Job Details</h2>
				  <ul>
					  <li class="clearfix"><span>Job Title</span><p>: Developer</p></li>
					  <li class="clearfix"><span>Employment Level</span><p>: Level 2</p></li>
					  <li class="clearfix"><span>Employer</span><p>: Business Intelli</p></li>
					  <li class="clearfix"><span>Location</span><p>: Hyderabad</p></li>
					  <li class="clearfix"><span>Start Date</span><p>: DD/MM/YY</p></li>
					  <li class="clearfix"><span>End Date</span><p>: DD/MM/YY</p></li>
				  </ul>
			  </div>			  
			  </div>
			  <div class="immigraDetailsWrap">
			  <div class="immigraDetailsCta">
				  <h2>Beneficiary details</h2>
				  <ul>
					  <li class="clearfix"><span>Assocaite (Beneficiary)</span><p>: Beneficiary 1</p></li>
					  <li class="clearfix"><span>First Name</span><p>: Vannam</p></li>
					  <li class="clearfix"><span>Middle Name</span><p>: ---</p></li>
					  <li class="clearfix"><span>Last Name</span><p>: Mahesh</p></li>
					  <li class="clearfix"><span>Email</span><p>: vannam@domain.com</p></li>
					  <li class="clearfix"><span>Mobile</span><p>: 9000 000 000</p></li>
					  <li class="clearfix"><span>Alternative Mobile</span><p>: 9000 000 000</p></li>
					  <li class="clearfix"><span>DOB</span><p>: DD/MM/YY</p></li>
					  <li class="clearfix"><span>Place of Birth</span><p>: Hyderabad</p></li>
					  <li class="clearfix"><span>Nationality</span><p>: India</p></li>
					  <li class="clearfix"><span>Material status</span><p>: Singel</p></li>
					  <li class="clearfix"><span>SSN</span><p>: JFJFSS4545</p></li>
					  <li class="clearfix"><span>I-94 Number</span><p>: FHJDFASD6546</p></li>
					  <li class="clearfix"><span>I-20 SEVIS Number If Student</span><p>: DSDF5454</p></li>
					  <li class="clearfix"><span>EAD Number If Student</span><p>: ---</p></li>
					  <li class="clearfix"><span>Previous LCA Number</span><p>: HDAID45611</p></li>
					  <li class="clearfix"><span>Previous LCA Start Dates</span><p>: DD/MM/YY</p></li>
					  <li class="clearfix"><span>Experience</span><p>: 2 years 3 months</p></li>
				  </ul>
			  </div>	
			  </div>		  			  			  
			  <div class="clearfix"></div>
		  </div>		  		  		  
		
	  </div>
	  
	  <div id="tab-2" class="immigraTabs_inn">
		<div class="immigraTabsHead">
		<a href="javascript:void(0)" id="immDocu"><i class="glyphicon glyphicon-plus"></i> Request Document</a> 			  	
		  	<a href="javascript:void(0)" id="addDocu"><i class="glyphicon glyphicon-plus"></i> Add Document</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Document Type</th>
				<th>Required</th>
				<th>Document</th>
				<th>Status</th>
				<th>Description</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Not uploaded</div></td>
				<td><div class="tbldata-dotdot-big">9000 000 000</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			
			<tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Uploaded</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  
			<tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Uploaded</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  

			<tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Uploaded</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
		  						
			<tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Uploaded</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
		  						
			<tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Uploaded</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
		  						
			<tr>
				<td><div class="tbldata-dotdot-vsm">Pan Card</div></td>
				<td><div class="tbldata-dotdot-vsm">Yes</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">Pad.jpg</span>
					<a class="fa fa-eye" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="View"></a></div>
				</td>
				<td><div class="tbldata-dotdot-vsm">Uploaded</div></td>
				<td><div class="tbldata-dotdot-big">Description text</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
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
	  
	  <div id="tab-3" class="immigraTabs_inn">
		<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addEmpl"><i class="glyphicon glyphicon-plus"></i> Add Employer</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Employer</th>
				<th>Tax Id</th>
				<th>Designation</th>
				<th>Address</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>    
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>     
			  <tr>
				<td><div class="tbldata-dotdot-sm">Business Intelli</div></td>
				<td><div class="">DFDD551</div></td>
				<td><div class="tbldata-dotdot-sm">UI Developer</div></td>
				<td><div class="tbldata-dotdot-me">Address text</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td><div class="">MM/YYYY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
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
	  
	  <div id="tab-4" class="immigraTabs_inn">
		<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addResi"><i class="glyphicon glyphicon-plus"></i> Add Residence</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Residence</th>
				<th>Country</th>
				<th>From</th>
				<th>To</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>    
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-big">Madhapur, Hyderabad</div></td>
				<td><div class="tbldata-dotdot-vsm">India</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
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
	   
	  <div id="tab-5" class="immigraTabs_inn">
		<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addEdu"><i class="glyphicon glyphicon-plus"></i> Add Education</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Education</th>
				<th>Specialization</th>
				<th>Start date</th>
				<th>End date</th>
				<th>Score</th>
				<th>Score Type</th>
				<th>Institute/University</th>
				<th>Address</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Bachelor of Commerce</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot_80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">80</div></td>
				<td><div class="tbldata-dotdot-80">Percentage</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot-vsm">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Bachelor of Commerce</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot_80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">80</div></td>
				<td><div class="tbldata-dotdot-80">Percentage</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot-vsm">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>    
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Bachelor of Commerce</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot_80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">80</div></td>
				<td><div class="tbldata-dotdot-80">Percentage</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot-vsm">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>     
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Bachelor of Commerce</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot_80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">80</div></td>
				<td><div class="tbldata-dotdot-80">Percentage</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot-vsm">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>    
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Bachelor of Commerce</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot_80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">80</div></td>
				<td><div class="tbldata-dotdot-80">Percentage</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot-vsm">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Bachelor of Commerce</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot_80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">80</div></td>
				<td><div class="tbldata-dotdot-80">Percentage</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot-vsm">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-vsm">Bachelor of Commerce</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot_80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-80">80</div></td>
				<td><div class="tbldata-dotdot-80">Percentage</div></td>
				<td><div class="tbldata-dotdot-vsm">----</div></td>
				<td><div class="tbldata-dotdot-vsm">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
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
	  
	  <div id="tab-6" class="immigraTabs_inn">
		<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" class="immMenuClick">Add Family <i class="fa fa-plus"></i>
				<div class="immiSubMenu">
					<ul>
						<li id="addSpou"><i class="fa fa-plus"></i> Add Spouse</li>
						<li id="addChil"><i class="fa fa-plus"></i> Add Child</li>
						<li id="addFath"><i class="fa fa-plus"></i> Add Father</li>
						<li id="addMoth"><i class="fa fa-plus"></i> Add Mother</li>
					</ul>
				</div>
		  	</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Name</th>
				<th>Relationship</th>
				<th>Date of Birth</th>
				<th>Place of Birth</th>
				<th>Address</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-me">Mahesh Vannam</div></td>
				<td><div class="tbldata-dotdot-me">Brother</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-vsm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">Hyderabad</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
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
	  
	  <div id="tab-7" class="immigraTabs_inn">
		<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addNote"><i class="glyphicon glyphicon-plus"></i> Add Note</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
			<div class="immiNote clearfix">
				<img src="images/23.jpg" alt=""/>
				<p>
					<b>By <span>Vannam Mahesh</span><u>(21 min ago)</u></b>
					Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever
				</p>
				<div class="removeNote"><i class="fa fa-trash"></i></div>
			</div>
		  <div class="immiNote clearfix">
				<img src="images/23.jpg" alt=""/>
				<p>
					<b>By <span>Vannam Mahesh</span><u>(21 min ago)</u></b>
					Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever
				</p>
				<div class="removeNote"><i class="fa fa-trash"></i></div>
			</div>
		  <div class="immiNote clearfix">
				<img src="images/23.jpg" alt=""/>
				<p>
					<b>By <span>Vannam Mahesh</span><u>(21 min ago)</u></b>
					Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever
				</p>
				<div class="removeNote"><i class="fa fa-trash"></i></div>
			</div>
			<div class="noRecordFound">
		    	<img src="images/noReco.png" alt=""/><p>No. Records found...</p>
		    </div>
		</div>
		
	  </div>  
	  
	  <div id="tab-8" class="immigraTabs_inn">
		<div class="immigraTabsHead">
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<td><div class="">Vannam Mahesh</div></td>
				<td><div class="">Changed status New to In-Progress</div></td>
				<td><div class="">1 hour ago</div></td>
				<td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td>
			  </tr>  			  			  
			  <tr>
				<td><div class="">Vannam Mahesh</div></td>
				<td><div class="">Changed status New to In-Progress</div></td>
				<td><div class="">1 hour ago</div></td>
				<td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td>
			  </tr>    
			  <tr>
				<td><div class="">Vannam Mahesh</div></td>
				<td><div class="">Changed status New to In-Progress</div></td>
				<td><div class="">1 hour ago</div></td>
				<td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td>
			  </tr>   
			  <tr>
				<td><div class="">Vannam Mahesh</div></td>
				<td><div class="">Changed status New to In-Progress</div></td>
				<td><div class="">1 hour ago</div></td>
				<td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td>
			  </tr>    
			  <tr>
				<td><div class="">Vannam Mahesh</div></td>
				<td><div class="">Changed status New to In-Progress</div></td>
				<td><div class="">1 hour ago</div></td>
				<td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td>
			  </tr>   
			  <tr>
				<td><div class="">Vannam Mahesh</div></td>
				<td><div class="">Changed status New to In-Progress</div></td>
				<td><div class="">1 hour ago</div></td>
				<td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td>
			  </tr>    
			  <tr>
				<td><div class="">Vannam Mahesh</div></td>
				<td><div class="">Changed status New to In-Progress</div></td>
				<td><div class="">1 hour ago</div></td>
				<td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td>
			  </tr>   
			  						
			</tbody>
		  </table>
		</div>
		
	  </div>  
	  
	  <div id="tab-9" class="immigraTabs_inn">
		<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addPass"><i class="glyphicon glyphicon-plus"></i> Add Passport</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Passport Id</th>
				<th>Country</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-big">IND5656256</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-big">IND5656256</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-big">IND5656256</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-big">IND5656256</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-big">IND5656256</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-big">IND5656256</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-big">IND5656256</div></td>
				<td><div class="tbldata-dotdot-me">India</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-me">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
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
	  
	  <div id="tab-10" class="immigraTabs_inn">
		<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addVisa"><i class="glyphicon glyphicon-plus"></i> Add Visa</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Visa Type</th>
				<th>Visa Id</th>
				<th>Country</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-sm">H1B</div></td>
				<td><div class="tbldata-dotdot-sm">INODA65464</div></td>
				<td><div class="tbldata-dotdot-sm">India</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-sm">H1B</div></td>
				<td><div class="tbldata-dotdot-sm">INODA65464</div></td>
				<td><div class="tbldata-dotdot-sm">India</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-sm">H1B</div></td>
				<td><div class="tbldata-dotdot-sm">INODA65464</div></td>
				<td><div class="tbldata-dotdot-sm">India</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-sm">H1B</div></td>
				<td><div class="tbldata-dotdot-sm">INODA65464</div></td>
				<td><div class="tbldata-dotdot-sm">India</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr> 
			  <tr>
				<td><div class="tbldata-dotdot-sm">H1B</div></td>
				<td><div class="tbldata-dotdot-sm">INODA65464</div></td>
				<td><div class="tbldata-dotdot-sm">India</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-sm">H1B</div></td>
				<td><div class="tbldata-dotdot-sm">INODA65464</div></td>
				<td><div class="tbldata-dotdot-sm">India</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>   
			  <tr>
				<td><div class="tbldata-dotdot-sm">H1B</div></td>
				<td><div class="tbldata-dotdot-sm">INODA65464</div></td>
				<td><div class="tbldata-dotdot-sm">India</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td><div class="tbldata-dotdot-sm">DD/MM/YY</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" class="fa fa-trash"></a>
							<a href="javascript:void(0)" class="fa fa-pencil"></a>
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
	  
	  <div id="tab-11" class="immigraTabs_inn">
		<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addForm"><i class="glyphicon glyphicon-plus"></i> Add Form</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Form Name</th>
				<th>File</th>
				<th>Status</th>
				<th>&nbsp;</th>
			  </tr>
			  <tr>
				<td><div class="tbldata-dotdot-me">Form one</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">mainform.jpg</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">Not uploaded</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  			  			  
			  <tr>
				<td><div class="tbldata-dotdot-me">Form one</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">mainform.jpg</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">Not uploaded</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr> 
			  <tr>
				<td><div class="tbldata-dotdot-me">Form one</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">mainform.jpg</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">Uploaded</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-me">Form one</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">mainform.jpg</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">Uploaded</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-me">Form one</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">mainform.jpg</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">Not uploaded</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr>  
			  <tr>
				<td><div class="tbldata-dotdot-me">Form one</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">mainform.jpg</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">Not uploaded</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td>
			  </tr> 
			  <tr>
				<td><div class="tbldata-dotdot-me">Form one</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">mainform.jpg</span>
					<a class="glyphicon glyphicon-download-alt" title="Download"></a></div>
				</td>
				<td><div class="tbldata-dotdot-big">Uploaded</div></td>
				<td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
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

	</section>
    
    
  </section>
  <!-- Content Area -->
</div>
   
<!-- Footer Area --> 

<!--   Popup Code  -->
	<div class="popMask"></div>
	
	<div class="popUpOut" id="docuView">
		<div class="popupHead">
			<h2>Add Document</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Document Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Document Type</option>
					  <option>Document-1</option>
					  <option>Document-2</option>
					  <option>Document-3</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Tax Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<input type="text" class="form-control" />
				</div>
			</div>	         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">Document<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Description</label>
              	<div class="col-md-8 padding_right">
					<textarea rows="2" class="form-control"></textarea>
				</div>
			</div>		
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	
	<div class="popUpOut" id="viewFormpop">
		<div class="popupHead">
			<h2>Add Form</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Form name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<input type="text" class="form-control" />
				</div>
			</div>	         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn1"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile1" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          	
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	
	<div class="popUpOut" id="uploadFormPop">
		<div class="popupHead">
			<h2>Upload file</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid popupMidHeight clearfix">
         
          <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn10"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile10" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>					          	
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut popUpOutBig" id="docuImportView">
		<div class="popupHead">
			<h2>Request Document</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix mt">		
			<table class="table table-responsive table-bordered table-hover noMar">
				<tbody>
					<tr>
						<td width="200px">
							<select class="chosen-select form-control">
							  <option>Select Document Type</option>
							  <option>Document-1</option>
							  <option>Document-2</option>
							  <option>Document-3</option>
							</select>
						</td>
						<td width="auto"><textarea rows="2" placeholder="Description" class="form-control"></textarea></td>
						<td width="120px">
							<label class="switch mt10 ml10 pull-left"><input checked="" type="checkbox"><div class="slider round"></div></label>
							<div class="pull-right"><button type="button" class="btn btm-sm pull-right mt5"><i class="fa fa-plus text-primary"></i></button></div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="emploView">
		<div class="popupHead">
			<h2>Add Employee</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Imployer<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Designation</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right"> 
              		<input type="text" class="form-control calendar" />
				</div>
			</div>				
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>			
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>
	
	<div class="popUpOut" id="popEduView">
		<div class="popupHead">
			<h2>Add Education</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Select Education</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Education</option>
					  <option>Education-1</option>
					  <option>Education-2</option>
					  <option>Education-3</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Specialization</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score Type</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Score Type</option>
					  <option>Percentage</option>
					  <option>Points</option>
					</select>
				</div>
			</div>	
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Score</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Institute/University</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>											
									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>		
	
	<div class="popUpOut" id="popNoteView">
		<div class="popupHead">
			<h2>Add Note</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">
			<div class="col-sm-12 noPad mt clearfix"><textarea class="form-control" rows="3"></textarea></div>						
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="popPassView">
		<div class="popupHead">
			<h2>Add Passport</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Passport Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid From</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="popResiView">
		<div class="popupHead">
			<h2>Add Residence</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Residence<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Start Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="popUpVisa">
		<div class="popupHead">
			<h2>Add Visa</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
	<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Type</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Visa Type</option>
					  <option>H1B</option>
					  <option>H2B</option>
					</select>
				</div>
			</div>
         
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Visa Id<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Country</label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control">
					  <option>Select Country</option>
					  <option>India</option>
					  <option>USA</option>
					  <option>UK</option>
					</select>
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid From</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Valid To</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>									
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>		
	
<!--   Popup Code  --> 
<!--   Family Popup's  -->  

	<div class="popUpOut" id="spousePopView">
		<div class="popupHead">
			<h2>Add Spouse</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                           				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Marriage<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">End Date</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="childPopView">
		<div class="popupHead">
			<h2>Add Child</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="fathPopView">
		<div class="popupHead">
			<h2>Add Father</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	
	
	<div class="popUpOut" id="mothPopView">
		<div class="popupHead">
			<h2>Add Mother</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Middle Name</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div> 
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>                         				          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Date of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control calendar" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Place of Birth<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Address</label>
              	<div class="col-md-8 padding_right">
              		<input type="text" class="form-control" />
				</div>
			</div>																		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script> 
<script src="js/moment.min.js"></script> 
<script src="js/bootstrap-datetimepicker.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/masterHeaderAction.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script> 
<script src="js/jquery-ui.min.js"></script> 


<script>
$(document).ready(function() {
    // Add Document PopUp
	$("#addDocu").click(function(){
		$(".popMask").fadeIn(200);
		$("#docuView").fadeIn(100);
	});
    // Add Form PopUp
	$("#addForm").click(function(){
		$(".popMask").fadeIn(200);
		$("#viewFormpop").fadeIn(100);
	});	
    // upload Form PopUp
	$(".uploadForm").click(function(){
		$(".popMask").fadeIn(200);
		$("#uploadFormPop").fadeIn(100);
	});		
    // Import Document PopUp
	$("#immDocu").click(function(){
		$(".popMask").fadeIn(200);
		$("#uploadFormPop").fadeIn(100);
	});	
    // Add Employee PopUp
	$("#addEmpl").click(function(){
		$(".popMask").fadeIn(200);
		$("#emploView").fadeIn(100);
	});	
    // Add addEdu Div  
	$("#addEdu").click(function(){
		$(".popMask").fadeIn(200);
		$("#popEduView").fadeIn(100);
	});
    // Add addResi Div  
	$("#addResi").click(function(){
		$(".popMask").fadeIn(200);
		$("#popResiView").fadeIn(100);
	});	
    // Add addVisa Div  
	$("#addVisa").click(function(){
		$(".popMask").fadeIn(200);
		$("#popUpVisa").fadeIn(100);
	});	
    // Add addNote Div   
	$("#addNote").click(function(){
		$(".popMask").fadeIn(200);
		$("#popNoteView").fadeIn(100);
	});	
    // Add addPass Div  
	$("#addPass").click(function(){
		$(".popMask").fadeIn(200);
		$("#popPassView").fadeIn(100);
	});		
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$("#docuView, #emploView, #popUpVisa, #popEduView, #popResiView, #docuImportView, #popNoteView, #popPassView, #viewFormpop, #uploadFormPop").fadeOut(100);
	});
});	
</script>

<!--   Family Popup's  -->
<script>
$(document).ready(function() {
	$(".immMenuClick").click(function(){
		$(".immiSubMenu").show();
	});	
	$("body").click(function(){
		$(".immiSubMenu").hide();
	});
	$(".immMenuClick, .immiSubMenu").click(function(event) {
	  event.stopImmediatePropagation();
	});	
	
    // Add Spouse
	$("#addSpou").click(function(){
		$(".popMask").fadeIn(200);
		$("#spousePopView").fadeIn(100);
	});
	$("#addChil").click(function(){
		$(".popMask").fadeIn(200);
		$("#childPopView").fadeIn(100);
	});
	$("#addFath").click(function(){
		$(".popMask").fadeIn(200);
		$("#fathPopView").fadeIn(100);
	});
	$("#addMoth").click(function(){
		$(".popMask").fadeIn(200);
		$("#mothPopView").fadeIn(100);
	});	
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$("#spousePopView, #childPopView, #fathPopView, #mothPopView").fadeOut(100);
	});	
	
});	
</script>

	<script>
// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".immigraTabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".immigraTabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});	
	</script>

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
 

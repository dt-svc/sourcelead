<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 
 
 
 <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix cusFieldHead">
      <div class="pull-left positionRelative"><h2>Custom Fields</h2>
		  <select class="form-control selectpicker">
			  <option>Add Job</option>
			  <option>Add Candidate</option>
			  <option>Add Vendor</option>
		  </select>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Custom Fields</div>
	<div class="clearfix"></div>
    </section>
    
    
<section class="immigraTabsWrap">
	  
	  <div class="cusFielInn">
	  
		<div class="cusFielInnHead clearfix">
	  		<h2>Job Details</h2>
		  	<a href="javascript:void(0)" id="addField"><i class="glyphicon glyphicon-plus"></i> Add Field</a> 
		</div>
	  	<div class="cusFielTable">
		  <table>
			<tbody>
			  <tr>
				<th>Action</th>
				<th>Feild Name</th>
				<th>Feild Type</th>
				<th>Description</th>
				<th>Visability</th>
				<th>Status</th>
				<th>Info</th>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort hideIcon" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/textfield.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/radio.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/checkbox.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/selectfeild.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/choosen.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>  						
			</tbody>
		  </table>
		</div>
		
	  </div>
	    
	  <div class="cusFielInn">
	  
		<div class="cusFielInnHead clearfix">
	  		<h2>Company Details</h2>
		</div>
	  	<div class="cusFielTable">
		  <table>
			<tbody>
			  <tr>
				<th>Action</th>
				<th>Feild Name</th>
				<th>Feild Type</th>
				<th>Description</th>
				<th>Visability</th>
				<th>Status</th>
				<th>Info</th>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort hideIcon" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/textfield.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/radio.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/checkbox.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/selectfeild.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>
			  <tr>
				<td class="cusFielAction"><div>
					<i class="fa fa-sort" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div></td>
				<td><div>First Name</div></td>
				<td><div>Text Feild</div></td>
				<td><div>Description text</div></td>
				<td><div class="positionRelative"><i class="fa fa-eye"></i>
					<div class="cusFieldView">
						<span><img src="images/choosen.jpg" alt=""/></span>
					</div>				
				</div></td>
				<td><div>Active</div></td>
				<td><div>Information text here..</div></td>
			  </tr>  						
			</tbody>
		  </table>
		</div>
		
	  </div>
	    
	</section>    
    
  </section>
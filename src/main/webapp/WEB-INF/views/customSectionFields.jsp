<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <table>
<tbody>
  <tr>
	<th>Field Name</th>
	<th>Field Type</th>
	<th>Is Required</th>
	<th>PlaceHolder</th>
	<th>Description</th>
	<th>Info</th>
	<th>Status</th>
	<th>Action</th>
  </tr>
	<c:forEach var="fieldValue" items="${sectionFieldValue}">
		  <tr>
			<td><div>${fieldValue.fieldName}</div></td>
			<td><div>${fieldValue.fieldType}</div></td>
			<td><div>${fieldValue.isFieldRequired == '0'? 'No':'Yes'}</div></td>
			<td><div>${fieldValue.placeHolder}</div></td>
			<td><div>${fieldValue.fieldDesc}</div></td>
			<td><div>${fieldValue.labelInfo}</div></td>
			<td><div>${fieldValue.status == '0'? 'InActive':'Active'}</div></td>
			<td><div class="positionRelative"><i class="fa fa-eye"></i>
				<div class="cusFieldView">
					<span><img src="images/textfield.jpg" alt=""/></span>
					<i class="fa fa-sort hideIcon" title="Drag"></i>
					<i class="fa fa-file-text-o" title="View file"></i>
					<i class="fa fa-trash" title="Remove"></i>
				</div>				
			</div></td>
		  </tr>
    </c:forEach>						
</tbody>
 </table>

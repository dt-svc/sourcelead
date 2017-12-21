<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<tr id="custom${customFormDetails.id}">
	<td class="cusFielAction tblminwidth-100">
	   <div>
	    <i class="fa fa-sort paddingRight" title="Drag"></i>
		<i class="fa fa-eye"></i>
		<i class="fa fa-file-text-o" title="edit file" onclick="editFileDetails(${customFormDetails.id});"></i>
		<i class="fa fa-trash" title="Remove" onclick="deleteCustomField(${customFormDetails.id});"></i>
	  </div>
	</td>
	<td><div class="tblminwidth-150">${customFormDetails.labelName}</div></td>
	<td><div class="tblminwidth-75">${customFormDetails.fieldType}</div></td>
	<td><div class="tblminwidth-30">${customFormDetails.isRequired == '0'? 'No':'Yes'}</div></td>
	<td><div class="tblminwidth-150">${customFormDetails.placeHolder}</div></td>
	<td><div class="tblminwidth-75">${customFormDetails.status == '0'? 'InActive':'Active'}</div></td>
	<c:if test="${!empty customFormDetails.fieldDescription}">
		<td><div class="showmore tblminwidth-150">${customFormDetails.fieldDescription}</div></td>
	</c:if>
	<c:if test="${empty customFormDetails.fieldDescription}">
		<td><div class="showmore tblminwidth-150 text-center">--</div></td>
	</c:if>
	<td><div class="showmore tblminwidth-150">${customFormDetails.labelinfo}</div></td>
</tr>
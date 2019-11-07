<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<tr id="custom${customFormDetails.id}">
	<td class="cusFielAction tblminwidth-125">
	   <div>
	   <!--  <i class="fa fa-sort paddingRight" title="Drag"></i> -->
		<i class="fa fa-eye mr5" onclick="editFileDetails(${customFormDetails.id},'summary');"></i>
		<i class="fa fa-pencil mr5" title="edit file" onclick="editFileDetails(${customFormDetails.id},'editCustomRowField');"></i>
		<c:if test="${customFormDetails.status eq 1}">
			<i class="fa fa-trash" title="Delete" id="deletFildRow${customFormDetails.id}" onclick="activateOrDeactivateCustomField(${customFormDetails.id},'deactivate');"></i>
		</c:if>
		<c:if test="${customFormDetails.status eq 0}">
			<i class="fa fa-check" title="Activate" id="deletFildRow${customFormDetails.id}" onclick="activateOrDeactivateCustomField(${customFormDetails.id},'activate');"></i>
		</c:if>
	  </div>
	</td>
	<td><div class="tblminwidth-150">${customFormDetails.labelName}</div></td>
	<td><div class="tblminwidth-75">${customFormDetails.fieldType}</div></td>
	<td><div class="tblminwidth-30">${customFormDetails.isRequired == '0'? 'No':'Yes'}</div></td>
	<td><div class="tblminwidth-150">${customFormDetails.placeHolder}</div></td>
	<td><div id = "customFieldStatus${customFormDetails.id}" class="tblminwidth-75">${customFormDetails.status == '0'? 'InActive':'Active'}</div></td>
	<%-- <c:if test="${!empty customFormDetails.fieldDescription}">
		<td><div class="showmore tblminwidth-150">${customFormDetails.fieldDescription}</div></td>
	</c:if>
	<c:if test="${empty customFormDetails.fieldDescription}">
		<td><div class="showmore tblminwidth-150 text-center">--</div></td>
	</c:if>
	<td><div class="showmore tblminwidth-150">${customFormDetails.labelinfo}</div></td> --%>
</tr>
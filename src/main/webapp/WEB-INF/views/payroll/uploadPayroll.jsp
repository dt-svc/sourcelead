<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
 
<body> 

<h2>Spring MVC - Uploading a file.. </h2>
	<form:form method="POST" commandName="file"	enctype="multipart/form-data" action="uploadPayrollForm?${_csrf.parameterName}=${_csrf.token}">
  <%-- <form:form id="upload_form" name="upload_form" method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" action="fileUpload?${_csrf.parameterName}=${_csrf.token}"> --%>
		Upload your file please: 
		<input type="file" name="file" />
		<input type="submit" value="upload" />
		<form:errors path="file" cssStyle="color: #ff0000;" />
	</form:form>
 
</body>
</html>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<title>Welcome to SourceLead:: Login Page</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="/resources/css/bootstrap.min.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" type="text/css">
<link rel="stylesheet" href="/resources/css/login-styles.css?staticTime=${staticTime}" type="text/css" >

<title>Edit Customer</title>
</head>

<body>
    <div align="center">
        <h1>New/Edit Contact</h1>
        <form:form action="saveCustomer" method="post" modelAttribute="customer">
        <table>
            <form:hidden path="id"/>
            <form:hidden path="autoIncrement"/>
            <tr>
                <td>Name:</td>
                <td><form:input path="customerName" /></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><form:input path="customerAddress" /></td>
            </tr>
            <tr>
                <td>Address:</td>
                <td><form:input path="city" /></td>
            </tr>
            <tr>
                <td>Zipcode:</td>
                <td><form:input path="zipcode" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Save"></td>
            </tr>
        </table>
        </form:form>
    </div>
</body>
</html>
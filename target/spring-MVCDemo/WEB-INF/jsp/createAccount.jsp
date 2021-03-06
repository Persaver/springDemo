<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
<style>
.error {color:red;font-size:15px;
}
</style>
</head>
<body>
	<form:form commandName="aNewAccount" action="createAccount">
		<table>
			<tr>
				<td>First Name: <form:input path="firstName" type="text"
						name="firstName" /></td>
				<form:errors path="firstName" cssClass="error"></form:errors>
			</tr>
			<tr>
				<td>Last Name: <form:input path="lastName" type="text"
						name="lastName" /></td>
				<form:errors path="lastName" cssClass="error"></form:errors>
			</tr>
			<tr>
				<td>Age: <form:input path="age" type="number" name="age" /></td>
				<form:errors path="age" cssClass="error"></form:errors>
			</tr>
			<tr>
				<td>Address: <form:input path="address" type="text"
						name="address" /></td>
				<form:errors path="address" cssClass="error"></form:errors>
			</tr>
			<tr>
				<td>Email: <form:input path="email" type="text" name="email" /></td>
				<form:errors path="email" cssClass="error"></form:errors>
			</tr>
			<tr>
				<td><input type="submit" value="Create" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="update" method="post">
		社員ID : <c:out value="${updateKey.employeeId}" /><br /> 
		社員名 : <c:out value="${updateKey.employeeName}" /><br />
		メールアドレス : <input type="text" name="email"value="${updateKey.email}" />
		<input type="hidden" name="employeeID" value= "${updateKey.employeeId}"/>
		<input type="hidden" name="mode" value= "doupdate"/>
		<input type="submit" value= "更新"/>
		
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>参照画面</title>
</head>
<body>
	<c:forEach var="list" items="${empList}">
		<form action="update" method="post">
			${list.employeeId}:${list.employeeName}<br /> Eメール:${list.email}<br />
			<br /> <input type="hidden" name="employeeID"
				value="${list.employeeId}" /> <input type="submit" value="更新画面へ" />
			<br />
		</form>
	</c:forEach>
</body>
</html>
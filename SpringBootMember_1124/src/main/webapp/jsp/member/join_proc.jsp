<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 성공</title>
</head>
<body>
<script>
	alert("회원 가입에 성공하셨습니다.");
	location.href="${pageContext.request.contextPath}/login.do";
</script>
</body>
</html>
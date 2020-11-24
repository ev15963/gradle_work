<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>welcome</title>
</head>
<body>
	
	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<script>
			alert("이미 로그아웃되었습니다. 로그인 페이지로 이동합니다.");
			location.href="${pageContext.request.contextPath}/login.do";
		</script>
	</c:if>
	
	<c:if test="${not empty pageContext.request.userPrincipal.name}">
		${pageContext.request.userPrincipal.name} 님 환영합니다.<br><br>
	
		<input type="button" 
			   value="로그아웃"
			   onclick="location.href='${rootPath}/logout_proc.do'" />	
	
		<input type="button" 
			   value="회원정보 보기"
			   onclick="location.href='${rootPath}/member/member_view.do?memberId=${pageContext.request.userPrincipal.name}'" />
	
		<input type="button" 
			   value="회원정보 수정"
			   onclick="location.href='${rootPath}/member/member_update.do?memberId=${pageContext.request.userPrincipal.name}'" />
			   
		<input type="button" 
			   value="회원정보 삭제"
			   onclick="location.href='${rootPath}/member/member_delete.do?memberId=${pageContext.request.userPrincipal.name}'" />	   
			   		   
	</c:if>
	
</body>
</html>
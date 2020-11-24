<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>login</title>
<style>
.error 
{
	padding: 10px;
	color: red;
}

.msg 
{
	padding:10px;
	color:red;
}

#login-box 
{
	width: 350px;
	padding: 50px;
	margin: 0px auto;
	background: #fff;
	border: 1px solid #333;
}
			
td 
{ 
	height: 50px; 
}			
</style>

<!-- jQuery -->
<script src="<c:url value="/webjars/jquery/3.5.1/jquery.min.js" />"></script>

<!-- bootstrap css -->
<link rel="stylesheet" href="${rootPath}/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />

<!-- bootstrap js -->
<script src="${rootPath}/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- popper.js -->
<script src="${rootPath}/webjars/popper.js/2.5.2/umd/popper.min.js"></script>

<script>
	$(document).ready(function() {
		
		 // 필드 공백 제거
		 $("#username").val().replace(/\s/g, "");
		 $("#password").val().replace(/\s/g, "");
		 
		 $("#login").click(function() {
			
			// 공백 여부 점검
			if ($.trim($("#username").val()) == "" || 
				$.trim($("#password").val()) == "")   {
				
				alert("공백이 입력되었습니다. 다시 입력하십시오.");
				$("#username").val("");
				$("#password").val("");
			} else {
				
				// 전송
				$("#loginForm").submit();
			}
	    	
	    }) // login
	    
	}) //
</script>
</head>
<body>

	<div id="login-box">

		<h3>로그인</h3>
		
		<c:if test="${error eq 'true'}">
     		<div class="msg">${msg.message}</div>
        </c:if>

		<form id="loginForm" 
			  name="loginForm" 
			  action="<c:url value='/login.do' />"
			  method="POST">
			  
			<!-- csrf token -->			  
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />  
	 
			<table>
				<tr>
					<td>아이디 : </td>
					<td><input type="text" 
							   id = "username"	
							   name="username" />
					</td>
				</tr>
				<tr>
					<td>패쓰워드 : </td>
					<td><input type="text"
							   id="password"
							   name="password" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						
						<input id="login"
							   name="login" 
							   type="button"
							   value="로그인" />
						&nbsp;
						<input name="reset" 
							   type="reset" 
							   value="취소" />
					    &nbsp;
						<input id="joinbtn"
					    	   name="joinbtn" 	
					    	   type="button"
							   value="회원가입"
							   onclick="location.href='${rootPath}/member/member_join.do'" />	   
					</td>
				</tr>
			</table>
			
		</form>
	
 	</div> <!-- login box -->	
</body>
</html>
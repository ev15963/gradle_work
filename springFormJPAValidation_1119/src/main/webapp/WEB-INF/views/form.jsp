<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>심플 회원 가입 폼</title>
<style>
.err 
{ 
	font-size:12px;
	color:red;
}	
</style>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="<c:url value='/js/datepicker.ko.js' />"></script>

<script>
  $(function() {
	
    $("#birthday").datepicker();
    
    // $("#birthday").datepicker("option", "dateFormat", "yy-mm-dd");
    // $("#birthday").datepicker("option", "ko", "yy-mm-dd");
    
  });
</script>
  
</head>
<body>

memberValid : ${memberValid}<br><br>

	<form:form commandName="memberValid"
			   method="POST"
	  		   action="formActionValid.do">
						  
			<form:errors path="*" cssClass="err" /><p>
									  
			아이디 : <form:input name="id" 
							   path="id" 
							   maxlength="20" 
							   size="25" />
				  <form:errors path="id" cssClass="err" /><br>
				  
			패쓰워드 : <form:password name="pw" 
								   path="pw" 
								   maxlength="20" 
								   size="25" />
				    <form:errors path="pw" cssClass="err" /><br>
				    
			이름 : <form:input name="name" 
							  path="name"	
							  maxlength="50" 
							  size="25" />
			   	 <form:errors path="name" cssClass="err" /><br>
			   	 
			이메일 : <form:input path="email" />
			   	 <form:errors path="email" cssClass="err" /><br>   	 
			   	 
			생년월일 : <form:input name="birthday"  
								path="birthday" 
								size="30" 
								type="date" /> <!--  반드시 type="date" 삽입  -->
								
			   	    <%-- <form:errors path="birthday" cssClass="err"/><br> --%>
			   	    
			   	    <spring:hasBindErrors name="memberValid">
			   	     	<c:if test="${errors.hasFieldErrors('birthday')}">
							<!-- <span class="err">${errors.getFieldErrors("birthday")}</span> -->
							<span class="err">${birthday_error}</span>
					    </c:if>
			   	    </spring:hasBindErrors>
			   	    
   	     	<br>
			<input type="submit" value="전송" />		   
					   	
	</form:form>				
			  
</body>
</html>
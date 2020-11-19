<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html lang="ko-kr">
<head>
<meta charset="UTF-8">
<title>Form Demo</title>
<style>
.err 
{ 
 	font-size:9pt; 
 	color:red 
}
</style>
</head>
<body>

	<form:form modelAttribute="memberVO"
			   method="POST"
			   action="formAction">
			   
	   아이디 : <form:input path="id" size="20" maxlength="20"/>
	   	    <form:errors path="id" cssClass="err" /><br>
	   		  
	   패쓰워드 : <form:password path="pw" size="20" maxlength="20" />
			 <form:errors path="pw" cssClass="err" /><br>
				
	 <input type="submit" value="전송">&nbsp;
	 <input type="reset" value="취소"> 
	  
    </form:form> 

</body>
</html>
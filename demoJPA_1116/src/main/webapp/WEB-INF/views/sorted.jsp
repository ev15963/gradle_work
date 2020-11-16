<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>sorted</title>

</head>
<body>

    <c:forEach items="${list}" var="member" varStatus="st">
        ${st.count}  ${member.id}   ${member.name}<br>
    </c:forEach>

</body>
</html>
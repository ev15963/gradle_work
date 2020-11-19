<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>rest client</title>
<script src="<c:url value="/jQuery/jquery-3.4.1.min.js" />"></script>
<script>
$(function() {
    
    /* $.ajax 옵션들 */
    /* data : JSON.stringify(data), */
    /* timeout : 100000, */
    /* dataType : 'json',*/
    /* type : "POST",    */
    
    $("input#btn").click(function(e) {
    	
    	alert("요청");
            
          $.ajax({
                     // url : "${pageContext.request.contextPath}/jsonFeed2",
                     url : "${pageContext.request.contextPath}/jsonFeed",
                     contentType : "application/json",
                     data : {
                    	 id : $("#id").val()
                     },
                     success : function (json) {
                    	 
                    	  alert("성공");
                    	  
                    	  // Spring :: @ResponseBody JSON Feed용 
                          // 주의사항 ! JSON.stringify() 사용 !
                          // var jsonObj = JSON.parse(JSON.stringify(json));
                          var jsonObj = JSON.parse(json);
                                
                          alert("JSON name : " +jsonObj.name);
                     }, 
                     
                     error : function(xhr, status) {
                         alert(xhr+" : "+status);
                    } 
         }); // $.ajax
         
    }); 
    
});     
</script>
</head>
<body>
 
    <input type="text" id="id"><br>
    <input type="button" id="btn" value="JSON 요청">
 
</body>
</html>
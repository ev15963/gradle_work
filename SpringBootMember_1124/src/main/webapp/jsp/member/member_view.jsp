<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>개별 회원 정보 조회</title>

<!-- CSS 외장화 -->
<!-- 회원가입 CSS -->
<link rel="stylesheet" href="${rootPath}/static/css/member_view.css">

</head>  
<body>  

	<div id="wrap">
	  
	    <div id="viewFormPnl">
		
	        <table id="viewFormTbl">  
	  
	            <!-- 아이디 -->  
	            <tr>  
	                <td><span class="requiredFld">*</span> 아이디</td>  
	                <td>
	                	<input type="text" 
	                		   id="memberId" 
	                		   name="memberId"
	                		   readonly="readonly"
	                		   value="<c:out value='${member.memberId}' />" />
	                		    
	                </td>  
	            </tr>  
				<!--// 아이디 -->  
	            
	            <!-- 별명 -->  
	            <tr>  
	                <td><span class="requiredFld">*</span> 별명</td>  
	                <td>
	                	<input type="text" 
	                		   id="memberNickname"  
	                    	   name="memberNickname"
	                    	   readonly
	                    	   value="${member.memberNickname}" /> 
	             	</td>  
	            </tr>  
				<!--// 별명 -->  
				
	            <!-- 이름  -->  
	            <tr>  
	                <td><span class="requiredFld">*</span> 이름</td>  
	                <td>
	                	<input type="text" 
	                		   id="memberName" 
	                		   name="memberName"
	                		   readonly
	                		   value="${member.memberName}" />  
	                </td>  
	            </tr>  
				<!--// 이름  -->  
				
	            <!-- 성별  -->  
	            <tr>  
	                <td><span class="requiredFld">*</span> 성별</td>  
	                <td>
					   ${member.memberGender eq 'm' ? "남자" : "여자"}
	                </td>  
	            </tr>  
				<!--// 성별  -->  
				
	            <!-- 이메일 -->  
	            <tr>  
	                <td><span class="requiredFld">*</span> 이메일</td>  
	                <td>
	                	<input type="text" 
	                		   id="memberEmail" 
	                		   name="memberEmail"
	                		   value="${member.memberEmail}" />   
	                </td>  
	            </tr> 
				<!--// 이메일 -->  
	              
	            <!-- 핸드폰번호  -->  
	            <tr>  
	                <td><span class="requiredFld">*</span> 연락처</td>  
	                <td>
						<input type="text" 
	                		   id="memberPhone" 
	                		   name="memberPhone"
	                		   value="${member.memberPhone}" />  
	            	</td>  
	            </tr>  
				<!--// 핸드폰번호  -->  
				
	            <!-- 생년월일 -->  
	            <tr>  
	                <td><span class="requiredFld">*</span> 생년월일</td>  
	                <td>
	                	 <fmt:formatDate value="${birth}" pattern="yyyy년  M월  d일" />	     
	                </td>  
	            </tr>   
				<!--// 생년월일 -->  
	            					
	            <!-- 우편변호  -->  
	            <tr>  
	                <td>우편번호</td>  
	                <td>
	               		<input type="text" 
	                		   id="memberZip" 
	                		   name="memberZip"   
	                    	   readonly
	                    	   value="${member.memberZip}" />
					</td>  
	            </tr>  
				<!--// 우편변호  -->  
				
	            <!-- 기본주소 -->  
	            <tr>  
	                <td>기본주소</td>  
	                <td>
	                	<input type="text" 
	                		   id="memberAddressBasic"  
			                   name="memberAddressBasic" 
			                   readonly
			                   value="${member.memberAddressBasic}" />
	                </td>  
	            </tr>  
				<!--// 기본주소 -->  
						            
	            <!-- 상세주소 -->  
	            <tr>  
	                <td>상세주소</td>
	                <td>
	                	<input type="text" 
							   id="memberAddressDetail"  
			                   name="memberAddressDetail"  
			                   readonly
			                   value="${member.memberAddressDetail}" />
	                </td>  
	            </tr>  
	            <!--// 상세주소 -->  
	            
	            <!-- 가입일 --> 
	            <tr>  
	                <td>가입일</td>
	                <td>
	                	<fmt:formatDate value="${member.memberJoinDate}" pattern="yyyy년  M월  d일" />
	                </td>  
	            </tr>  
	            <!-- // 가입일 --> 
			</table>  

	    </div>
    
    </div>
</body>  
</html>
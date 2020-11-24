<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원 가입</title>

<!-- google material -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<!-- CSS 외장화 -->
<!--  modal -->
<link rel="stylesheet" href="${rootPath}/static/css/modal.css">

<!-- 회원가입 CSS -->
<link rel="stylesheet" href="${rootPath}/static/css/member_join.css">

<!-- jquery -->  
<script src="${rootPath}/webjars/jquery/3.5.1/jquery.min.js"></script>
  
<!-- jqueryui -->  
<link rel="stylesheet" href="${rootPath}/webjars/jquery-ui/1.12.1/jquery-ui.min.css">
<script src="${rootPath}/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>

<!-- daum 주소 검색 -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<!-- javascript/jquery 외장화 -->
<script>
// 주의사항) 외장화할 경우 JSP 코드 단절 현상으로 인해 ajax 전송시 문제 발생 -> 광역 javascript 변수로 패치
// ex) 
// before) url : '${pageContext.request.contextPath}/member/idCheck.do',
// after)  url : rootPath + '/member/idCheck.do',
const rootPath = "${pageContext.request.contextPath}";
</script>
<script src="${rootPath}/static/js/member_join.js" charset="UTF-8"></script>

<script>
// modal 출력
$(function() {
	
	$("#modal_btn").click(function(){
		$("#msg_modal").show();
	});
});
</script>
</head>  
<body>  

	<!-- modal -->
	<div id="msg_modal">
	    
	    <!-- modal header -->
		<div id="modal_header">
		
			<!-- bullet -->
		    <div id="modal_header_icon">
				<span class="material-icons">
		        	web_asset
		        </span>		        
	        </div>
	        
	        <!-- title -->
	        <div id="modal_header_title">
				폼점검 오류 정보
			</div>
			
			<!-- closing button icon -->
			<div id="modal_header_close">
			    <a href="#" id="modal_close_btn">
					<span class="material-icons" style="color:#333">
			        	clear
			        </span>
		        </a>
			</div>
		</div>
		
		<!-- modal content -->
		<div id="modal_body">

		</div>
		
		<!-- modal footer -->
		<div id="modal_footer">
		</div>
		
	</div>
    
    <!-- ---------------------------------------------------------- -->

	<div id="join_wrap">
	  
	    <div id="joinFormPnl">
		
		    <form:form id="joinForm" 
		    	  name="joinForm"
		    	  modelAttribute="memberDTO" 
		          method="post"
		          action="${rootPath}/member/join_proc.do">
		          
	            <!-- csrf token -->			  
				<input type="hidden"                        
					   name="${_csrf.parameterName}"
					   value="${_csrf.token}"/>  
		  
		        <table id="joinFormTbl">  
		  
		            <!-- 아이디 -->  
		            <tr>  
		                <td><span class="requiredFld">*</span> 아이디</td>  
		                <td>
		                	<input type="text" 
		                		   id="memberId" 
		                		   name="memberId"  
				                   maxlength="20" 
								   pattern="[a-zA-Z]{1}\w{7,19}"
								   title="아이디는 영문으로 시작하며 영문/숫자 조합하여 8~20자로 입력하십시오"
				                   required>
				            <div id="memberId_err" class="err_msg"></div>      
		                </td>  
		            </tr>  
					<!--// 아이디 -->  
		  
		            <!-- 패쓰워드  -->  
		            <tr>  
		                <td><span class="requiredFld">*</span> 패쓰워드</td>  
		                <td>
		                	<input type="text" 
		                		   id="memberPassword"  
		                    	   name="memberPassword" 
		                    	   maxlength="20" 
		                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,20}"  
		                           required
								   title="패쓰워드는 영문대소문자/특수문자/숫자 조합하여 8~20자로 입력하십시오">
		                </td>  
		            </tr>  
					<!--// 패쓰워드  -->  
		            
		            <!-- 별명 -->  
		            <tr>  
		                <td><span class="requiredFld">*</span> 별명</td>  
		                <td>
		                	<input type="text" 
		                		   id="memberNickname"  
		                    	   name="memberNickname" 
		                    	   maxlength="50" 
		                    	   pattern="[가-힣]{2,25}|[a-zA-Z]{2,50}|\s[a-zA-Z]{2,50}"  								   
		                    	   required
								   title="별명은 두글자 이상으로 입력하십시오">
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
				                   maxlength="25" 
				                   pattern="[가-힣]{2,25}"  
				                   required
								   title="이름은 한글로 입력하십시오">
		                </td>  
		            </tr>  
					<!--// 이름  -->  
					
		            <!-- 성별  -->  
		            <tr>  
		                <td><span class="requiredFld">*</span> 성별</td>  
		                <td>
		                    <!-- 버그 패치 : 최상단 네비게이션과의 CSS 충돌 보정
		                    : id 부여 -->
							<ul id="radio_gender">
								<li>
									<input type="radio" 
										   id="memberGenderMale" 
										   name="memberGender" 
										   value="m"
										   checked
										   required>
									<label for="memberGenderMale">남</label>
									<div class="check"></div>
								</li>
								<li>
									<input type="radio" 
										   id="memberGenderFemale" 
										   name="memberGender" 
										   value="f">
									<label for="memberGenderFemale">여</label>
									<div class="check"></div>
								</li>		  
							</ul>
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
				                   pattern="[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}"  
				                   size="25"
				                   maxlength="50"
				                   required
								   title="이메일을 입력하십시오">
							<span id="memberEmail_err" class="err_msg"></span>    	   
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
		                    	   maxlength="13" 
		                    	   pattern="01\d{1}-\d{3,4}-\d{4}"
		                    	   required
		                    	   size="15"
								   title="전화번호는 우측 예시와 같이 입력하십시오"> 
		                    ex) 010-1234-5678
		                    <div id="memberPhone_err" class="err_msg"></div>
		            	</td>  
		            </tr>  
					<!--// 핸드폰번호  -->  
					
		            <!-- 생년월일 -->  
		            <tr>  
		                <td><span class="requiredFld">*</span> 생년월일</td>  
		                <td>
		                	<input type="text" 
		                		   id="memberBirth" 
		                		   name="memberBirth"  
		                    	   pattern="\d{4}-\d{2}-\d{2}"  
		                    	   maxlength="11" 
		                    	   required
								   title="생년월일을 입력하십시오">
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
		                    	   maxlength="5" 		                    	   
		                    	   pattern="\d{5}"
		                    	   readonly>
		                    	      
		                    <input type="button" 
		                    	   id="joinAddressSearchBtn" 
								   name="joinAddressSearchBtn" 
		                    	   value="주소검색"
		                    	   onClick="searchPost()"><br>  
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
				                   pattern="[\w | \W | 가-힣 | / | - | (  |  ) | ,]{2,200}" 
				                   maxlength="200"  
				                   size="50"				                   
								   title="기본주소를 입력하십시오"
								   readonly>
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
				                   pattern="[\w | \W | 가-힣 | / | -]{2,100}" 
				                   maxlength="100"  
				                   size="50"
								   title="상세주소를 입력하십시오">
		                </td>  
		            </tr>  
				</table>  
				<!--// 상세주소 -->  
					
				<table id="joinFormMenu">	
		            
					<!-- 회원가입/취소 버튼 -->
		            <tr>  
		                <td>
		                	<input type="submit" 
		                		   id="joinSubmitBtn"
								   name="joinSubmitBtn"
				                   value="회원가입">  
				                   
		                    <input type="reset" 
								   id="joinResetBtn"
								   name="joinResetBtn"
								   value="가입취소">
		               	</td>  
		            </tr>  
					<!--// 회원가입/취소 버튼 -->
					
		        </table>  
		    </form:form>  
			
	    </div>
    
    </div>
</body>  
</html>
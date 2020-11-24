/**
 * 회원수정폼 
 */
function searchPost() { 
  
  new daum.Postcode({
	  
      oncomplete: function(data) {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var fullAddr = ''; // 최종 주소 변수
          var extraAddr = ''; // 조합형 주소 변수

          // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
              fullAddr = data.roadAddress;

          } else { // 사용자가 지번 주소를 선택했을 경우(J)
              fullAddr = data.jibunAddress;
          }

          // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
          if(data.userSelectedType === 'R'){
              //법정동명이 있을 경우 추가한다.
              if(data.bname !== ''){
                  extraAddr += data.bname;
              }
              // 건물명이 있을 경우 추가한다.
              if(data.buildingName !== ''){
                  extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
              }
              // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
              fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
          }
          
          // 3단계 : 해당 필드들에 정보 입력
          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById('memberZip').value = data.zonecode; //5자리 새우편번호 사용
          document.getElementById('memberAddressBasic').value = fullAddr;
      }
	  
  }).open();
}; //

$(function() {  
	
	// spring  security post 전송시 403 에러 대처 패치
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(function() {
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });
	
	// 이메일 중복 점검 플래그 : 기존 검증된 정보이므로 true 설정
	var emailCheckFlag = true;
	// 연락처 중복 점검 플래그 : 기존 검증된 정보이므로 true 설정
	var phoneCheckFlag = true;
	
	// 모달 초기 상태 : 은닉
	$("#msg_modal").hide();
	// 모달 위치 조정
	$("#msg_modal").offset({left:0, top:0});
	
	// modal 팝업창 닫기
	$("#modal_close_btn").click(function() {
		$("#msg_modal").hide();
		// 추가 : 메시지 삭제(초기화)
		$("#msg_modal #modal_body").text("");
	}); //
	
	////////////////////////////////////////////////
    	
 	// 이메일 중복 점검
    $("#memberEmail").blur(function() {
    	
    	console.log("이메일 중복점검");
    	
    	// 정규식 점검으로 유효 데이터 전송
    	var regexEmail = new RegExp($("#memberEmail").attr("pattern"));
    	var memberEmail = $("#memberEmail").val();

    	if (regexEmail.test(memberEmail)) { // 정규식 점검 통과
    	
	    	$.ajax({
	    		 url : rootPath + '/member/emailCheckUpdate.do',
	             type : 'post',
	             dataType:'text',
	             data : {
	            	 memberId : $('#memberId').val(),
	                 memberEmail : $('#memberEmail').val()
	             },
	             success : function(data) {
	               
	                console.log("이메일 중복 점검 수신 ! : "+data);
	                
	             	// 중복 점검 체크 플래그 재설정
                    if (data.trim() == '사용할 수 있는 이메일입니다.') {
                	   // 메시지 초기화 : 정상적일 경우는 메시지 표기 불필요할 경우
                	   $("#memberEmail_err").text("");
                	   emailCheckFlag = true;
                    } else {
                   	   $("#memberEmail_err").text(data);
                       emailCheckFlag = false;
                    }
	             	
                    // 플래그 인쇄
 	                console.log("emailCheckFlag : "+ emailCheckFlag);
 	                console.log("phoneCheckFlag : "+ phoneCheckFlag);
	               
	            }, // success
	             
	             error : function(xhr, status) {
	                console.log(xhr+" : "+status); // 에러 코드
	                emailCheckFlag = false;
	            }
	 
	    	}); // ajax
    
    	} else { // 정규식 점검 실패
    		
    		// 에러 메시징
    		$("#memberEmail_err").text(($("#memberEmail").attr("title")));
    		// 재입력 대기
    		$("#memberEmail").focus();
    	} //
    	
    });
 	
 	// 연락처 중복 점검
    $("#memberPhone").blur(function() {
    	
    	console.log("연락처 중복점검");
    	
    	// 정규식 점검으로 유효 데이터 전송
    	var regexPhone = new RegExp($("#memberPhone").attr("pattern"));
    	var memberPhone = $("#memberPhone").val();

    	if (regexPhone.test(memberPhone)) { // 정규식 점검 통과
    		
	    	$.ajax({
	    		 url : rootPath + '/member/phoneCheckUpdate.do',
	             type : 'post',
	             dataType:'text',
	             data : {
	            	 memberId : $('#memberId').val(),
	                 memberPhone : $('#memberPhone').val()
	             },
	             success : function(data) {
	               
	                console.log("연락처 중복 점검 수신 !");
	                
	            	// 중복 점검 체크 플래그 재설정
                    if (data.trim() == '사용할 수 있는 연락처입니다.') {
                	   // 메시지 초기화 : 정상적일 경우는 메시지 표기 불필요할 경우
                	   $("#memberPhone_err").text("");
                	   phoneCheckFlag = true;
                    } else {
                       $("#memberPhone_err").text(data);
                       phoneCheckFlag = false;
                    }
	            	
                 	// 플래그 인쇄
 	                console.log("emailCheckFlag : "+ emailCheckFlag);
 	                console.log("phoneCheckFlag : "+ phoneCheckFlag);
	               
	            }, // success
	             
	             error : function(xhr, status) {
	                console.log(xhr+" : "+status); // 에러 코드
	                phoneCheckFlag = false;
	            }
	 
	    	});
    	
    	} else { // 정규식 점검 실패
    		
    		// 에러 메시징
    		$("#memberPhone_err").text(($("#memberPhone").attr("title")));
    		// 재입력 대기
    		$("#memberPhone").focus();
    		
    	} //
    	
    });
 	
 	// 폼 데이터 전송
 	$("form#updateForm").submit(function(){
 		
 		alert("폼 전송");
 		
 		console.log("emailCheckFlag : "+emailCheckFlag);
 		console.log("phoneCheckFlag : "+phoneCheckFlag);
 		
 		if (emailCheckFlag == false || phoneCheckFlag == false) {
 			
 			// alert("오류")
 			
 			if (emailCheckFlag == false) {
 			    $("#msg_modal #modal_body").text("중복되지 않는 이메일을 입력하십시오.");
 				$("#msg_modal").show();
 				$("#memberEmail").focus();
 			}
 			
 			if (phoneCheckFlag == false) {
 				$("#msg_modal #modal_body").text("중복되지 않는 연락처를 입력하십시오.");
 				$("#msg_modal").show();
 				$("#memberPhone").focus();
 			}
 			
 			return false; // 전송금지
 		}
 		
 		// 우편번호/주소/상세주소 점검
 		// 주소 성분이 필수 사항이 아닐지라도 입력할 경우 상세주소 누락되는지 최종적으로 점검
 		var regexAddressDetail = new RegExp($("#memberAddressDetail").attr("pattern"));
    	var memberAddressDetail = $("#memberAddressDetail").val();
    	
 		if ($("#memberZip").val() != "" &&
			$("#memberAddressBasic").val() != "" &&
			!regexAddressDetail.test(memberAddressDetail) ) 
 		{
 			$("#msg_modal #modal_body").text("상세주소를 입력하십시오.");
			$("#msg_modal").show();
			return false; // 전송 금지
    		$("#memberAddressDetail").focus();
 		} // if
 		
 		if ($("#memberZip").val() == "" &&
			$("#memberAddressBasic").val() == "" &&
			$("#memberAddressDetail").val().trim() != "" ) 
 		{
 			$("#msg_modal #modal_body").text("우편번호와 기본 주소를 입력하십시오.");
			$("#msg_modal").show();
 			return false; // 전송 금지    	 							
    		$("#updateAddressSearchBtn").focus();
 		} // if
 		
 		//////////////////////////////////////////////////////////////////
 		
 		// 신규 패쓰워드 비교 점검 : 동등 비교
 		if ($("#memberPassword1").val().trim() != $("#memberPassword2").val().trim()) {
 			
 			$("#msg_modal #modal_body").text("신규 패쓰워드가 일치하지 않습니다.");
			$("#msg_modal").show();
 			return false;
 			$("#memberPassword1").focus();
 		}
 		
 		// 신규 패쓰워드 비교 점검 : 정규식 점검
 		// 신규 패쓰워드에 변경에 가해지면...
 		if ($("#memberPassword1").val().trim() != "" || 
 			$("#memberPassword2").val().trim() != "") 
 		{	
			var regexPassword = new RegExp($("#memberPassword1").attr("pattern"));
	    	var memberPassword1 = $("#memberPassword1").val().trim();
	    	var memberPassword2 = $("#memberPassword2").val().trim();
	    	
	    	if (!regexPassword.test(memberPassword1)) 
	    	{ // 정규식 점검 실패
	    		$("#msg_modal #modal_body").text($("#memberPassword1").attr("title"));
				$("#msg_modal").show();
				return false;
	 			$("#memberPassword1").focus();			
	    	} // if
	    	
	    	if (!regexPassword.test(memberPassword2)) 
	    	{ // 정규식 점검 실패
	    		$("#msg_modal #modal_body").text($("#memberPassword2").attr("title"));
				$("#msg_modal").show();
				return false;
	 			$("#memberPassword2").focus();			
	    	} // if
 		} // 
    	
    	//////////////////////////////////////////////////////////////////
 		
 	}); //
 	
});    
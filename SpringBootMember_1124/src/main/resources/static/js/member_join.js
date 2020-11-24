/**
 * 회원가입폼 
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


/* jquery & jQueryUI */  
// 생년월일 자동 입력 달력 컴포넌트
$(function() {  
	
	// 아이디 중복 점검 플래그
	var idCheckFlag = false;
	// 이메일 중복 점검 플래그
	var emailCheckFlag = false;
	// 연락처 중복 점검 플래그
	var phoneCheckFlag = false;
	
	////////////////////////////////////////////////
	// 달력 컴포넌트 한글/현실화
	/*
	$("#memberBirth").datepicker({  
        changeYear:true,  
        changeMonth:true,
        dateFormat:"yy-mm-dd"      
    });   
	*/
	
	$.datepicker.regional['ko'] = {
		  autoSize : true,
		  closeText: '닫기',
		  prevText: '이전달',
		  nextText: '다음달',
		  currentText: '오늘',
		  yearRange: "1930:2019", // 1930 ~ 2019
		  // yearRange: "-36:-20", // 36년전부터 20년전까지
		  monthNames: ['1월','2월','3월','4월','5월','6월',
		  '7월','8월','9월','10월','11월','12월'],
		  monthNamesShort: ['1월','2월','3월','4월','5월','6월',
		  '7월','8월','9월','10월','11월','12월'],
		  dayNames: ['일','월','화','수','목','금','토'],
		  dayNamesShort: ['일','월','화','수','목','금','토'],
		  dayNamesMin: ['일','월','화','수','목','금','토'],
		  weekHeader: 'Wk',
		  dateFormat: 'yy-mm-dd', // 날짜 포맷
		  firstDay: 0,
		  minDate: new Date(1930, 1 - 1, 1),
		  isRTL: false,
		  showMonthAfterYear: true,
		  yearSuffix: '년',
		  // 버그 패치 : 컴포넌트 겹침 현상 해소 ex) 성별 필드와의 겹침
		  beforeShow: function() {
	         setTimeout(function(){
	            $('.ui-datepicker').css('z-index', 10);
	         }, 0);
	      }
	  };
    	
	  $.datepicker.setDefaults($.datepicker.regional['ko']);
	  
	  $("#memberBirth").datepicker({changeYear:true, changeMonth:true});
    
	////////////////////////////////////////////////
    
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
    	
    // 아이디 중복 점검
    $("#memberId").blur(function() {
    	
    	console.log("아이디 중복점검");
    	
    	// 정규식 점검으로 유효 데이터 전송
    	// var regexId = new RegExp('[a-zA-Z]{1}\w{7,19}');
    	// var regexId = new RegExp(/[a-zA-Z]{1}\w{7,19}/);
    	// var regexId = /[a-zA-Z]{1}\w{7,19}/;
    	var regexId = new RegExp($("#memberId").attr("pattern"));
    	
    	var memberId = $("#memberId").val();
    	
    	console.log("rootPath : "+rootPath);

    	if (regexId.test(memberId)) { // 정규식 점검 통과
	    		
    		$.ajax({
    			// 주의사항) 외장화할 경우 코드 단절현상으로 인해 문제 발생 -> 광역 javascript 변수로 패치
	       		// url : '${pageContext.request.contextPath}/member/idCheck.do',
    			url : rootPath + '/member/idCheck.do',
                type : 'get',
                dataType:'text',
                data : {
                    memberId : $('#memberId').val()
                },
                success : function(data) {
                  
                   console.log("아이디 중복 점검 수신 !");

                   // 중복 점검 체크 플래그 재설정
                   if (data.trim() == '사용할 수 있는 아이디입니다.') {
                	   // 메시지 초기화 : 정상적일 경우는 메시지 표기 불필요할 경우
                	   $("#memberId_err").text("");
                	   idCheckFlag = true;
                   } else {
                       $("#memberId_err").text(data);
                       idCheckFlag = false;
                   }
                                  	
	               // 플래그 인쇄
	               console.log("idCheckFlag : "+ idCheckFlag);
	               console.log("emailCheckFlag : "+ emailCheckFlag);
	               console.log("phoneCheckFlag : "+ phoneCheckFlag);
                   
               }, // success
                
                error : function(xhr, status) {
                   console.log(xhr+" : "+status); // 에러 코드
               }
	    
	       	}); // ajax
    		
    	} else { // 정규식 통과 실패
    		
    		// 에러 메시징
    		$("#memberId_err").text(($("#memberId").attr("title")));
    		// 재입력 대기
    		$("#memberId").focus();
    		
    	} // 
    	
    });
    
 	// 이메일 중복 점검
    $("#memberEmail").blur(function() {
    	
    	console.log("이메일 중복점검");
    	
    	// 정규식 점검으로 유효 데이터 전송
    	var regexEmail = new RegExp($("#memberEmail").attr("pattern"));
    	var memberEmail = $("#memberEmail").val();

    	if (regexEmail.test(memberEmail)) { // 정규식 점검 통과
    	
	    	$.ajax({
	    		 // url : '${pageContext.request.contextPath}/member/emailCheck.do',
	    		 url : rootPath + '/member/emailCheck.do',
	             type : 'get',
	             dataType:'text',
	             data : {
	                 memberEmail : $('#memberEmail').val()
	             },
	             success : function(data) {
	               
	                console.log("이메일 중복 점검 수신 !");
	                
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
 	                console.log("idCheckFlag : "+ idCheckFlag);
 	                console.log("emailCheckFlag : "+ emailCheckFlag);
 	                console.log("phoneCheckFlag : "+ phoneCheckFlag);
	               
	            }, // success
	             
	             error : function(xhr, status) {
	                console.log(xhr+" : "+status); // 에러 코드
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
	    		 // url : '${pageContext.request.contextPath}/member/phoneCheck.do',
	    		 url : rootPath + '/member/phoneCheck.do',
	             type : 'get',
	             dataType:'text',
	             data : {
	                 memberPhone : $('#memberPhone').val()
	             },
	             success : function(data) {
	               
	                console.log("연락처 중복 점검 수신 !");
	                console.log("메시지 : "+data);
	                	                
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
 	                console.log("idCheckFlag : "+ idCheckFlag);
 	                console.log("emailCheckFlag : "+ emailCheckFlag);
 	                console.log("phoneCheckFlag : "+ phoneCheckFlag);
	               
	            }, // success
	             
	             error : function(xhr, status) {
	                console.log(xhr+" : "+status); // 에러 코드
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
 	$("form#joinForm").submit(function(){
 		
 		alert("폼 전송");
 		
 		// 아이디/이메일/연락처 중복점검 플래그 점검
 		console.log("아이디 중복 점검 : " + idCheckFlag);
 		console.log("이메일 중복 점검 : " + emailCheckFlag);
 		console.log("연락처 중복 점검 : " + phoneCheckFlag);
 		
 		if (idCheckFlag == false || emailCheckFlag == false || phoneCheckFlag == false) {
 			
 			if (idCheckFlag == false) {
 				
 				$("#msg_modal #modal_body").text("중복되지 않은 아이디를 입력하십시오.");
 				$("#msg_modal").show();
 				// alert("중복되지 않은 아이디를 입력하십시오.");
 				$("#memberId").focus();
 			}
 			
 			if (emailCheckFlag == false) {
 				$("#msg_modal #modal_body").text("중복되지 않는 이메일을 입력하십시오.");
 				$("#msg_modal").show();
 				// alert("중복되지 않는 이메일을 입력하십시오.");
 				$("#memberEmail").focus();
 			}
 			
 			if (phoneCheckFlag == false) {
 				$("#msg_modal #modal_body").text("중복되지 않는 연락처를 입력하십시오.");
 				$("#msg_modal").show();
 				// alert("중복되지 않는 연락처를 입력하십시오.");
 				$("#memberPhone").focus();
 			}
 			
 			return false; // 전송금지
 		}
 		
 		// 우편번호/주소/상세주소 점검
 		// 주소 성분이 필수 사항이 아닐지라도 입력할 경우 상세주소 누락되는지 최종적으로 점검
 		var regexAddressDetail = new RegExp($("#memberAddressDetail").attr("pattern"));
    	var memberAddressDetail = $("#memberAddressDetail").val();
    	
    	//alert("우편번호 : "+$("#memberZip").val());
    	//alert("기본주소 : "+$("#memberAddressBasic").val());
    	//alert("상세주소 : "+$("#memberAddressDetail").val());
 	    	
 		if ($("#memberZip").val() != "" &&
			$("#memberAddressBasic").val() != "" &&
			!regexAddressDetail.test(memberAddressDetail) ) 
 		{
 			$("#msg_modal #modal_body").text("상세주소를 입력하십시오.");
			$("#msg_modal").show();
    		// alert("상세주소를 입력하십시오.");
			return false; // 전송 금지
    		$("#memberAddressDetail").focus();
 		} // if
 		
 		// TODO
 		// 우편번호/기본주소가 비었는데 상세주소에 데이터가 입력되었을 때
 		// 1) 상세주소 정규식 점검 -> 오류 : 비움(초기화)
 		// 2) 전송 금지
 		// 3) 메시징 : 기본 주소를 검색하여 입력하십시오.
 		// 4) focus : 검색 버튼
 		
 		// alert($("#memberAddressDetail").val().trim())
 		
 		if ($("#memberZip").val() == "" &&
			$("#memberAddressBasic").val() == "" &&
			$("#memberAddressDetail").val().trim() != "" ) 
 		{
 			$("#msg_modal #modal_body").text("우편번호와 기본 주소를 입력하십시오.");
			$("#msg_modal").show();
 			// alert("우편번호와 기본 주소를 입력하십시오."); 
 			return false; // 전송 금지    	 							
    		$("#joinAddressSearchBtn").focus();
 		} // if
 		
 	});
 	
});    
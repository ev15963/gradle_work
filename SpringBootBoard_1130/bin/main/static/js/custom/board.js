/**
 *
 */

// jQuery
$(document).ready(function(){

	// 게시글 보기
	$("a[id^=boardNum_]").click(function(e){

		var boardNum = e.target.id.substring(9);
		
		$.ajax ({

			url : contextPath + "/board/boardDetailREST.do/boardNum/"+boardNum,
			contentType : "application/json",
			type : "POST",
			success : function (article) {

				// 게시글 내용 : modal
				$("div#viewModal [name=boardNum]").val(article.boardNum);
				$("div#viewModal [name=boardName]").val(article.boardName);
				$("div#viewModal [name=boardSubject]").val(article.boardSubject);
				// $("div#viewModal #boardContent").val(article.boardContent);
				
				// html 해석(parsing)
				$("div#viewModal [name=boardContent]").html(article.boardContent);

				// 글 조회수 갱신
				$("div#viewModal #boardReadCount_"+article.boardNum).text(article.boardReadCount);
				
				//////////////////////////////////////////////////////////////////////////////////////
				//
				// 추가 : ckeditor용 => 댓글 여부
				$("div#viewModal [name=boardReLev]").val(article.boardReLev);
				
				
				console.log("#### article.boardReLev : "+article.boardReLev);
				
				// ckeditor에 맞게 교정
				// 댓글/원글 수정 버튼 선택
				if (article.boardReLev == "1") {

					console.log("댓글 수정");
					$("#viewModal [id^=updateContentBtn_]").attr("data-target", "#replyUpdateModal");
									
				} else if (article.boardReLev == "0") {
				
					console.log("원글 수정");
					$("#viewModal [id^=updateContentBtn_]").attr("data-target", "#updateModal");
					
				}
				
				var boardFile = article.boardFile==null ?
						        "파일없음": article.boardFile;

				console.log("실제 업로드 파일명 : "+boardFile);
				console.log("원래 업로드 파일명 : "+article.oriBoardFile);

				if (boardFile == '파일없음')
					$("div#viewModal #viewBoardFile").text(boardFile);
				else {

				    $("div#viewModal #viewBoardFile").html("<a href=\""
				   		  + contextPath + "/upload/" + article.boardNum + "/"+boardFile+"/\">"+article.oriBoardFile+"</a>");
				} //


				/////////////////////////////////////////////////////////////////////////////////////
				// 각종 버튼 아이디 변경(버튼 아이디에 게시글 번호 인자 첨부 전송)
				// 버튼 아이디 변경 : ex) "updateContentBtn_"+article.boardNum
								
				$("button[id^=updateContentBtn_]").attr("id", "updateContentBtn_"+article.boardNum);
				$("button[id^=replyContentBtn_]").attr("id", "replyContentBtn_"+article.boardNum);
				$("button[id^=replyUpdateContentBtn_]").attr("id", "replyUpdateContentBtn_"+article.boardNum);
				$("button[id^=update_btn_]").attr("id", "update_submit_btn_"+article.boardNum);
				$("button[id^=reply_update_btn_]").attr("id", "reply_update_submit_btn_"+article.boardNum);
				$("button[id^=reply_submit_btn_]").attr("id", "reply_submit_btn_"+article.boardNum);
				$("button[id^=deleteContentBtn_]").attr("id", "deleteContentBtn_"+article.boardNum);
				$("button[id^=delete_submit_btn_]").attr("id", "delete_submit_btn_"+article.boardNum);
				
				// 게시글 삭제란 패쓰워드 초기화
				$("form#boardDeleteForm input[name=boardPass]").val("");

			} //success

		}); // ajax

	});
	// 게시글 보기 끝

	/////////////////////////////////////////////////////////////////////////////////////////

	// 게시글 팝업(modal) 원글 수정 : 전송
	$("button[id^=update_btn_]").click(function (e) {

		var len = "update_submit_btn_".length;
		var boardNum = e.target.id.substring(len); // "update_submit_btn_" 뒤부분 "글번호" 취득 (교정)
		
		console.log("### 글수정 번호 : "+boardNum);

		var form = $('form#updateForm')[0];
        var formData = new FormData(form);

     	// 파일 필드 초기화 : 버그 패치(초기화하지 않으면 이전 업로드 파일이 반영됨)
        // 수정할 글이 원글이 아닌 댓글이면... : 댓글은 파일 업로드 없음
		if ($("[name=update_boardReLev]").val() == 0) {
        	$("#update_boardFile_new").val("");
		}

        $.ajax ({

			url : contextPath + "/board/updateBoard.do/"+boardNum,

			cache: false,
            async: false,
            cache: false,
            contentType: false,
            processData: false,

			type : "POST",
			data : formData,

			success : function (result) {

				alert(result);
				location.reload(); // 페이지 리프레시(재설정)
			},

			error : function(xhr, status) {

                console.log(xhr+" : "+status); // 에러 코드
            }
            
		}); // ajax


	}); // 게시글 팝업(modal) 수정 : 전송 끝
	
	/////////////////////////////////////////////////////////////////////////////////////////

	// 게시글 팝업(modal) 답글(댓글) 수정 : 전송
	$("button[id^=reply_update_btn_]").click(function (e) {

		alert("글수정 요청");

		var len = "reply_update_submit_btn_".length;
		var boardNum = e.target.id.substring(len); // "reply_update_submit_btn_" 뒤부분 "글번호" 취득
		
		console.log("### 글수정 번호 : "+boardNum);

		var form = $('form#replyUpdateForm')[0];
        var formData = new FormData(form);

		
        $.ajax ({

			url : contextPath + "/board/replyUpdateBoard.do/"+boardNum,

			cache: false,
            async: false,
            cache: false,
            contentType: false,
            processData: false,

			type : "POST",
			data : formData,

			success : function (result) {

				alert(result);
				location.reload(); // 페이지 리프레시(재설정)
			},

			error : function(xhr, status) {

                console.log(xhr+" : "+status); // 에러 코드
            }
            
		}); // ajax


	}); // 게시글 팝업(modal) 수정 : 전송 끝

	//////////////////////////////////////////////////////////////////////////////////////

	// 게시글 삭제
	$("button[id^=deleteContentBtn_]").click(function (e) {

		var len = "deleteContentBtn_".length;
		var boardNum = e.target.id.substring(len); // "deleteContentBtn_" 뒤부분 "글번호" 취득
		// alert(boardNum);

		// 삭제폼에 글번호 표시(입력)
		$("form#boardDeleteForm input[name=boardNum]").val(boardNum);

	});	// 게시글 삭제  끝

	// 게시글 삭제 : 전송
	$("button[id^=delete_submit_btn_]").click(function (e) {

		alert("글삭제 요청");

		var len = "delete_submit_btn_".length;
		var boardNum = e.target.id.substring(len); // "delete_submit_btn_" 뒤부분 "글번호" 취득

		console.log("삭제할 게시글 번호 : "+boardNum);

        $.ajax ({

			url : contextPath + "/board/deleteBoardProcREST.do/"+boardNum,
			type : "POST",

			data : { boardPass : $("input#boardPass").val() },

			success : function (result) {

				alert(result);
				location.reload(); // 페이지 리프레시(재설정)
			},

			error : function(xhr, status) {

                console.log(xhr+" : "+status); // 에러 코드
            }
		}); // ajax

	}); // 게시글 삭제 : 전송 끝
	
	///////////////////////////////////////////////////////////////////////////////////////

	// 게시글 답글 쓰기(Modal)
	$("button[id^=replyContentBtn_]").click(function (e) {
	
		var len = "replyContentBtn_".length;
		var boardNum = e.target.id.substring(len); // "replyContentBtn_" 뒤부분 "글번호" 취득
		
		console.log("## 답글 게시글 번호 : " + boardNum);
		
		$.ajax ({

			url : contextPath + "/board/boardDetailREST.do/boardNum/"+boardNum,
			contentType : "application/json",
			type : "POST",
			success : function (article) {
			
				console.log("article : "+article.boardNum);

				// 기존 게시글 내용
				$("[name=boardNum]").val(article.boardNum);

				// 답글 관련 필드값 설정
				$("[name=boardReRef]").val(article.boardReRef);
				$("[name=boardReLev]").val(article.boardReLev);
				$("[name=boardReSeq]").val(article.boardReSeq);

				// 이중 답글 방지 : level=1 만 가능하도록 설정함
				if (article.boardReLev==1) {

					alert("이중 답글을 쓸 수 없습니다.");
					$("#replyModal").hide();
					location.reload();

				} //
			},

			error : function(xhr, status) {

                console.log(xhr+" : "+status); // 에러 코드
            }

		}); // ajax

	}); // 게시글 답글 쓰기(modal) 끝
	
	///////////////////////////////////////////////////////////////////////////////////////

	// 게시글 답글 처리
	$("button[id^=reply_submit_btn_]").click(function (e) {

		alert("답글 쓰기 요청");
		
		var len = "reply_submit_btn_".length;
		var boardNum = e.target.id.substring(len); // "reply_submit_btn_" 뒤부분 "글번호" 취득
		$("#page").val(page);
		
		console.log("원글 게시글 번호 : " + boardNum);

		var form = $('form#boardReplyForm')[0];
        var formData = new FormData(form);

        $.ajax ({

			url : contextPath + "/board/replyWriteBoardProc.do",
			cache: false,
            async: false,
            cache: false,
            contentType: false,
            processData: false,

			type : "POST",
			data : formData,

			success : function (result) {

				if (result.trim() == "true") {
					alert("답글 저장 성공");
				} else {
					alert("답글 저장 실패");
				}

				location.reload(); // 페이지 리프레시(재설정)
			}
		}); // ajax

	}); // 게시글 팝업(modal) 수정 : 전송 끝
	
});


////////////////////////////////////////////////////////////////////////////////////////
//
// AngularJS 제어 : 폼점검을 위해서는 형식적으로  Controller 부분을 기입해야 됨.
//
// 아래의 스크립트는 ck editor 4 버전 전용 스크립트입니다. 5 버전에서는 적용되지 않습니다.
//
//////////////////////////////////////////////////////////////////////////////////////// 

var app = angular.module("boardFormApp", ["ngCkeditor"]);

// for ckeditor
app.directive('ckEditor', function () {
	  return {
	    require: '?ngModel',
	    link: function (scope, elm, attr, ngModel) {
	      var ck = CKEDITOR.replace(elm[0]);
	      if (!ngModel) return;
	      ck.on('instanceReady', function () {
	        ck.setData(ngModel.$viewValue);
	      });
	      function updateModel() {
	        scope.$apply(function () {
	          ngModel.$setViewValue(ck.getData());
	        });
	      }
	      ck.on('change', updateModel);
	      ck.on('key', updateModel);
	      ck.on('dataReady', updateModel);
	
	      ngModel.$render = function (value) {
	        ck.setData(ngModel.$viewValue);
	      };
	    }
	  };
});

/***************************************************************************************/
/* 팝업 제어  																		   */
/***************************************************************************************/

app.controller('boardFormController', ['$scope', '$http', function($scope, $http) {

	$scope.boardContent = "";

	// 게시글 쓰기폼 초기화 : 초기화하지 않을 경우 이전의 글들(쓰다가 빠져나갈 경우)이 남아 있을 수 있음.
	$scope.initWriteForm = function($event) {
	
		console.log("------------ 글쓰기 폼 초기화 --------------");
				
		$scope.boardName = "";
		$scope.boardPass = "";
		$scope.boardSubject = "";
		$scope.boardContent = "";
		$scope.boardFile = "";

	} // 게시글 쓰기폼 초기화 끝
	
	// 글쓰기  폼점검
	$scope.writeFormCheck = function($event) {
	
		console.log("글쓰기 폼점검");

		if ($scope.boardForm.boardName.$valid == true &&
			$scope.boardForm.boardPass.$valid == true &&
			$scope.boardForm.boardSubject.$valid == true &&
			$scope.boardForm.boardContent.$valid == true)
		{
	  		console.log("글쓰기 요청 전송");
	  		alert("글쓰기 요청 전송");

			var form = $('form#boardForm')[0];
	        
	        console.log("글 제목 : " + form.boardSubject.value);
	        
	        /////////////////////////////////////////////////////////////////////////////////
	        //
	        // 주의) ckeditor를 사용한 글내용이 제대로 인자 전송되지 않기 때문에 angular 방식으로 인자 합성
	        // 
	        console.log("글 내용(new) : " + $scope.boardForm.boardContent.$viewValue);
	        
	        form.boardContent.value = $scope.boardForm.boardContent.$viewValue;

			var formData = new FormData(form);
			
			console.log("게시글 내용 : " + formData.get("boardContent"));

	        $.ajax ({

				url : contextPath + "/board/writeBoardProcREST.do",

				cache: false,
	            async: false,
	            contentType: false,
	            processData: false,

				type : "POST",
				data : formData,

				success : function (result) {

					alert(result);
					location.reload(); // 페이지 리프레시(재설정)
				},

				error : function(xhr, status) {

	                console.log(xhr+" : "+status); // 에러 코드
	            }
			}); // ajax
		

		} else {

			alert("폼점검 미완료")
			document.boardForm.boardName.focus();
		} //
		

	} // 글쓰기  폼점검 끝


	// 목록 버튼 시작
	$("#list_btn").click(function(e) {

		location.href = contextPath;

	});
	// 목록 끝

	
/////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 주의) 게시판 수정 패널의 초기값은 여기서 설정
	// 임시 보관 영역에 저장된 내용을 할당
	// $scope.update_boardContent = defaultContent;

	// 게시글 댓글 쓰기폼 초기화 : 초기화하지 않을 경우 이전의 글들이 남아 있을 수 있음.
	$scope.initReplyWriteForm = function($event) {

		$scope.boardName = "";
		$scope.boardPass = "";
		$scope.boardSubject = "";
		$scope.boardContent = "";

	} // 게시글 댓글 쓰기폼 초기화 끝

	// 목록 버튼 시작
	$("#list_btn").click(function(e) {

		location.href = contextPath;

	});
	// 목록 끝
	
///////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시글 수정폼 초기화 : 초기화하지 않을 경우 이전의 글들이 남아 있을 수 있음.
	$scope.initUpdateForm = function($event) {
	
		$scope.update_boardPass = ""; // 패쓰워드 초기화
		$scope.reply_update_boardPass = ""; // 패쓰워드 초기화
		
		// 주의사항) ckeditor로 작성된 컴포넌트에는 위와 같은 방식으로는 angularjs 에서 기존값 대입이 안되므로 아래와 같이 조치 ! 
		// ckeditor용 컴포넌트 대입 변수
		var updateBoardContent = "";

		var boardNum = $event.target.id.substring(17); // "updateContentBtn_" 뒤부분 "글번호" 취득
		
		/////////////////////////////////////////////////////////////////////////////////
		//
		// angularJS AJAX
		
		$http({
			method: 'POST', 
			url: contextPath + "/board/boardDetailREST.do/boardNum/"+boardNum, 
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		// 서버 통신이 성공할 경우
		.then(function(response) {
		
				$scope.status = response.status;
				$scope.data = response.data;
				
				article = $scope.data;

				if (article.boardReLev == 0) { // 원글일 경우(답글이 아닐 경우) : ckeditor 적용
								
					// 기존 게시글 내용(원글)
					// modal
					$("#updateForm #update_boardNum").val(article.boardNum);
					$("#updateForm #update_boardName").val(article.boardName);
					$("#updateForm #update_boardSubject").val(article.boardSubject);
					$("#updateForm #update_boardContent").val(article.boardContent);
					
					// 댓글 여부 점검위한 필드값 할당
					$("#updateForm #update_boardReLev").val(article.boardReLev);
				
					////////////////////////////////////////////////////////////////////////////////////////////////
					//
					// 주의사항) ckeditor로 작성된 컴포넌트에는 위와 같은 방식으로는 angularjs 에서 기존값 대입이 안되므로 아래와 같이 조치 ! 
					//
					updateBoardContent = article.boardContent;
					
					console.log("### updateBoardContent : "+ updateBoardContent);
			
					$scope.update_boardContent = updateBoardContent;

					// 중복 첨가 방지를 위해 기존 필드 있는지 여부 점검
					var fileFldFlag =$("#updateForm").find("span label[for=update_boardFile_new]").text().trim();

					if (fileFldFlag == '') { // 파일 필드가 첨부되어 있지 않으면 ...

						var fileFldContent  = "<div name=\"update_file_section\" class=\"input-group\">"
											+ "<div class=\"input-group-prepend\">"
										    + "<label for=\"update_boardFile\" class=\"control-label input-group-text ml-2\">"
										    + "첨부파일 :</label>"
											+ "</div>"
											+ "<div class=\"custom-file\">"
											+ "		<span class=\"btn btn-default btn-file\">"
											+ "			<label for=\"update_boardFile_new\" class=\"custom-file-label\">파일 선택</label>"
											+ "			<input type=\"file\" class=\"custom-file-input\" id=\"update_boardFile_new\" name=\"update_boardFile_new\" />"
											+ "		</span>"
											+ "</div>"
											+ "<div class=\"input-group\">"
											+ "		<div class=\"input-group-prepend\">"
											+ "			<div class=\"ml-3 mt-2\" id=\"update_boardFile\"></div>"
											+ "		</div>"
											+ "</div>";

						// 파일 필드 추가
						$("form[id=updateForm] #article_content").append(fileFldContent);	// fileFldContent

					} //

					var boardFile = article.boardFile == null ? "파일없음": article.boardFile;

					console.log("# 첨부 파일 : "+boardFile);

					if (boardFile == '파일없음')
						$("#update_boardFile").text(boardFile);
					else {
						$("#update_boardFile").html(article.oriBoardFile);
					} //
					
					// 주의사항) ckeditor로 작성된 컴포넌트에는 위와 같은 방식으로는 angularjs 에서 기존값 대입이 안되므로 아래와 같이 조치 !
					// console.log("### updateBoardContent : "+ updateBoardContent);
					$scope.update_boardContent = updateBoardContent;
					
					// 폼점검 플래그 초기화 : 원글일 경우
					$scope.updateForm.update_boardPass.$valid = "false"; // 비밀번호 미입력 상태 (초기 상태)
					$scope.updateForm.update_boardSubject.$valid = "true";
					$scope.updateForm.update_boardContent.$valid = "true";

				/////////////////////////////////////////////////////////////////////////////////////////////////
				
				} else { // 답글일 경우 : ckeditor 미적용
				
					// 기존 게시글 내용(답글)
					// modal
					$("#replyUpdateForm #reply_update_boardNum").val(article.boardNum);
					$("#replyUpdateForm #reply_update_boardName").val(article.boardName);
					$("#replyUpdateForm #reply_update_boardSubject").val(article.boardSubject);
					$("#replyUpdateForm #reply_update_boardContent").val(article.boardContent);
					
					// 댓글 여부 점검위한 필드값 할당
					$("#replyUpdateForm #reply_update_boardReLev").val(article.boardReLev);

					// 기존에 파일 필드가 추가되어 있다면 제거
					// $("form[id=updateForm] #article_content [name=update_file_section]").remove();
					
					// 폼점검 플래그 초기화 : 답글일 경우
					$scope.replyUpdateForm.reply_update_boardPass.$valid = "false"; // 비밀번호 미입력 상태 (초기 상태)
					$scope.replyUpdateForm.reply_update_boardSubject.$valid = "true";
					$scope.replyUpdateForm.reply_update_boardContent.$valid = "true";

				}

				// 조회수 업데이트
				$tempId = "#boardReadCount_"+article.boardNum;
				$($tempId).text(article.boardReadCount);
				
		}, function(response) {
		
		     $scope.data = response.data || 'Request failed';
         	 $scope.status = response.status;
		});
		
	} // 게시글 수정폼 초기화 끝
	
// }]);


///////////////////////////////////////////////////////////////////////////////////////////////////

	// 게시글 댓글(답글) 수정폼 초기화 : 초기화하지 않을 경우 이전의 글들이 남아 있을 수 있음.
	$scope.initReplyUpdateForm = function($event) {
	
		alert("댓글 수정폼 초기화");

		$scope.update_boardPass = ""; // 패쓰워드 초기화
		
		// 주의사항) ckeditor로 작성된 컴포넌트에는 위와 같은 방식으로는 angularjs 에서 기존값 대입이 안되므로 아래와 같이 조치 ! 
		// ckeditor용 컴포넌트 대입 변수
		var updateBoardContent = "";

		var boardNum = $event.target.id.substring(17); // "updateContentBtn_" 뒤부분 "글번호" 취득
		
		/////////////////////////////////////////////////////////////////////////////////
		//
		// angularJS AJAX
		
		$http({
			method: 'POST', 
			url: contextPath + "/board/boardDetailREST.do/boardNum/"+boardNum, 
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		// 서버 통신이 성공할 경우
		.then(function(response) {
		
				$scope.status = response.status;
				$scope.data = response.data;
				
				article = $scope.data;
			
				console.log("------ 글내용 : " +article); 
				
				// 기존 게시글 내용
				// modal
				$("form#replyUpdateForm #update_boardNum").val(article.boardNum);
				$("form#replyUpdateForm #update_boardName").val(article.boardName);
				$("form#replyUpdateForm #update_boardSubject").val(article.boardSubject);
				
				// $("form#replyUpdateForm #update_boardContent").val(article.boardContent);
				
				// console.log("게시글 내용 : " + article.boardContent);
				
				////////////////////////////////////////////////////////////////////////////////////////////////
				//
				// 주의사항) ckeditor로 작성된 컴포넌트에는 위와 같은 방식으로는 angularjs 에서 기존값 대입이 안되므로 아래와 같이 조치 ! 
				//
				updateBoardContent = article.boardContent;
				
				console.log("### updateBoardContent : "+ updateBoardContent);
		
				$scope.update_boardContent = updateBoardContent;

				// 댓글 여부 점검위한 필드값 할당
				$("form#replyUpdateForm #update_boardReLev").val(article.boardReLev);

				// 표제 추가 ex) 게시글 수정 : "답글"
				var article_title = article.boardReLev == 1 ? ": 답글" : "";
				$("span#article_title").text(article_title);

				if (article.boardReLev == 0) { // 원글일 경우(답글이 아닐 경우)

					// 중복 첨가 방지를 위해 기존 필드 있는지 여부 점검
					// alert("첨부 파일 필드 여부 : "+$("#replyUpdateForm").find("label[for=update_boardFile_new]").text());
					var fileFldFlag =$("#replyUpdateForm").find("span label[for=update_boardFile_new]").text().trim();

					if (fileFldFlag == '') { // 파일 필드가 첨부되어 있지 않으면 ...

						var fileFldContent  = "<div name=\"update_file_section\" class=\"input-group\">"
											+ "<div class=\"input-group-prepend\">"
										    + "<label for=\"update_boardFile\" class=\"control-label input-group-text ml-2\">"
										    + "첨부파일 :</label>"
											+ "</div>"
											+ "<div class=\"custom-file\">"
											+ "		<span class=\"btn btn-default btn-file\">"
											+ "			<label for=\"update_boardFile_new\" class=\"custom-file-label\">파일 선택</label>"
											+ "			<input type=\"file\" class=\"custom-file-input\" id=\"update_boardFile_new\" name=\"update_boardFile_new\" />"
											+ "		</span>"
											+ "</div>"
											+ "<div class=\"input-group\">"
											+ "		<div class=\"input-group-prepend\">"
											+ "			<div class=\"ml-3 mt-2\" id=\"update_boardFile\"></div>"
											+ "		</div>"
											+ "</div>";

						// 파일 필드 추가
						$("form[id=updateForm] #article_content").append(fileFldContent);	// fileFldContent

					} //

					var boardFile = article.boardFile == null ?
					        "파일없음": article.boardFile;

					console.log("# 첨부 파일 : "+boardFile);

					if (boardFile == '파일없음')
						$("#update_boardFile").text(boardFile);
					else {
						$("#update_boardFile").html(article.oriBoardFile);
					} //
					
					// 주의사항) ckeditor로 작성된 컴포넌트에는 위와 같은 방식으로는 angularjs 에서 기존값 대입이 안되므로 아래와 같이 조치 !
					console.log("### updateBoardContent : "+ updateBoardContent);
					
					$scope.update_boardContent = updateBoardContent;

				} else { // 답글일 경우

					// 기존에 파일 필드가 추가되어 있다면 제거
					$("form[id=updateForm] #article_content [name=update_file_section]").remove();
					
				}

				// 조회수 업데이트
				$tempId = "#boardReadCount_"+article.boardNum;
				$($tempId).text(article.boardReadCount);
				
		}, function(response) {
		
		     $scope.data = response.data || 'Request failed';
         	 $scope.status = response.status;
		});

		// 폼점검 플래그 초기화 : 답글일 경우
		$scope.replyUpdateForm.reply_update_boardPass.$valid = "false"; // 비밀번호 미입력 상태 (초기 상태)
		$scope.replyUpdateForm.reply_update_boardSubject.$valid = "true";
		$scope.replyUpdateForm.reply_update_boardContent.$valid = "true";

	} // 게시글 수정폼 초기화 끝
	
}]); 
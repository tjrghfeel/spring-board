<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/postCss.css?ver=12"/>
</head>
<body>
	<!-- 작성자 id클릭시 보이는 작은 메뉴창. 게시물 보기 페이지에서만 동작한다.  -->
	<div id="accountMenu">
		<table>
			<tr><td id="showPostList">게시글 보기</td></tr>
			<tr><td id="sendMessage">쪽지 보내기</td></tr>
			<tr><td class="showCreditList">신고 내역</td></tr>
		</table>
	</div>
	
	<div id="postPage">
		<div id="post">
			<table>
				<tr>
					<td id="sort" colspan="2"><span>${post.sort1 } &gt; ${post.sort2 }</span></td>
				</tr>
				<tr>
					<td id="title" colspan="2"><span>${post.title }</span></td>
				</tr>
				<tr>
					<td id="info1" colspan="2">
						<span id="writerId">작성자 : ${post.writerId }</span>
						<span id="reportCredit"><img src="http://localhost/emergencyLight.png"/>신고하기</span> 
						<span class="showCreditList">신고내역 : ${writerAccount.credit}</span>
					</td>
				</tr>
				<tr>
					<td id="info2" colspan="2">
						<span>댓글 : ${post.commentCount }</span>
						<span>조회 : ${post.viewCount }</span>
						<span>${post.writeTime } (수정 ${post.updateTime })</span>
					</td>
				</tr>
				<tr>
					<%-- <td id="image"><img src="http://localhost/${post.mainImage }"/></td> --%>
					<td id="image"><img src="http://49.50.163.173/${post.mainImage }"/></td>  
					<td id="info3">
						<span id="price">가격 : ${post.price }원</span><br>
						<c:if test="${post.open ==true }">
							<span id="writerInfo">전화번호 : ${writerAccount.pNum }    email : ${writerAccount.email }</span><br>
						</c:if>
						<c:if test="${post.open ==false }">
							<span id="writerInfo">전화번호 : 비공개       email : 비공개</span><br>
						</c:if>
						<c:if test="${post.saleComplete == true }">
							<span id="saleComplete" data-value="true">판매 완료</span>
						</c:if>
						<c:if test="${post.saleComplete == false }">
							<span id="saleComplete" data-value="false">판매중</span>
						</c:if>
					</td>
				</tr>
				<tr><td id="content" colspan="2">
					${post.content }
				</td></tr>
			</table>
	
			<!-- 게시물 삭제 버튼 -->
			<c:if test="${post.writerNum==sessionScope.loginedAccount.num || sessionScope.loginedAccount.grade==1 }">
				<div id="writerButton">
					<form id="saleCompleteButton" action="saleCompleteH" method="post">
						<input type="hidden" name="postNum" value="${post.num }"/>
						<input type="submit" value="판매완료"/>
					</form>
					<form id="deleteButton" action="deletePostH" method="post">
						<input type="hidden" name="postNum" value="${post.num }"/>
						<input type="submit" value="게시글삭제"/>
					</form>
				</div>
			</c:if>
		</div>
		
		<div id="comment">
			<!-- 댓글부분 -->
				<!-- 댓글 작성 부분 -->
			<form action="writeCommentH" method="post" class="writeComment">
				<textarea name="content" maxlength="80"></textarea><br>
				<input type="hidden" name="postNum" value="${post.num }"/>
				<input type="submit" value="완료"/>
			</form>
			<br><br>
				<!-- 댓글 목록 부분 -->
			<c:if test="${commentList !=null }"><!-- 댓글이 없으면 아무것도 출력안함. -->
				<%--'댓글' 목록 뿌려주는 부분. --%>
				<c:forEach var="item" varStatus = "st" items="${commentList }">
					<table class="mainComment">
						<tr>
							<td class="commentInfo">
								<span>${item[0].writerId }</span>
							</td>
						</tr>
						<tr><td class="commentContent">
							${item[0].content }
						</td></tr>
						<tr><td class="commentInfo2">
							<span>${item[0].time }</span>
							<span>덧글 달기</span>
							<c:if test="${item[0].writerId==sessionScope.loginedAccount.id || sessionScope.loginedAccount.grade==1 }">
								<span data-commentNum="${item[0].num }">댓글 삭제</span>
							</c:if>
						</td></tr>
					</table>
					<div class="writeInnerComment">
						<form action="writeCommentH" method="POST" class="writeComment">
							<input type="hidden" name="parrentComment" value="${item[0].num }">
							<input type="hidden" name="postNum" value="${item[0].postNum }">
							<textarea name="content" maxlength="80"></textarea><br>
							<input type="submit" value="덧글달기">
						</form>
					</div>
					
					<%--'덧글' 목록 뿌려주는 부분. --%>
					<div class="innerCommentSection">
						<c:if test="${not empty item[1] }"><!--'덧글'이 존재한다면 -->
							<c:forEach var="innerItem" items="${item }" begin="1">
								<table class="innerComment">
									<tr>
										<td class="commentInfo">
											<span>${innerItem.writerId }</span>
										</td>
									</tr>
									<tr><td class="commentContent">
										${innerItem.content }
									</td></tr>
									<tr><td class="commentInfo2">
										<span>${innerItem.time }</span>
										<c:if test="${innerItem.writerId==sessionScope.loginedAccount.id || sessionScope.loginedAccount.grade==1 }">
											<span data-commentNum="${innerItem.num }">댓글 삭제</span>
										</c:if>
									</td></tr>
								</table>
								
							</c:forEach>
						</c:if>	
					</div>
				</c:forEach>
			</c:if>
		</div><!-- comment부분 끝 -->
	</div><!-- post부분 끝 -->
			
	<!-- 작성자 아이디클릭하고 소메뉴에서 '쪽지보내기'클릭시  -->
	<div id=sendMessageForm>
		<form action="showView" method="post" target="messageForm">
			<input type="hidden" name="sender" value="${sessionScope.loginedAccount.id }"/>
			<input type="hidden" name="receiver" value="${post.writerId }"/>
			<input type="hidden" name="pageName" value="sendMessage"/>
		</form>
	</div>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		var click=true;
		$('#writerId').on('click',function(e){
			if(click==true){
				$('#accountMenu').css('top',e.pageY+'px');
				$('#accountMenu').css('left',e.pageX+'px');
				$('#accountMenu').css('display','block');
			}
			else {
				$('#accountMenu').css('display','none');
			}
			click=!click;
		})
		
		//아이디클릭시 뜨는 작은 메뉴창의 클릭 이벤트. 순서대로 해당 회원의 게시물 리스트보기, 쪽지보내기, 신고 내역 보기 이다. 
		$('#showPostList').on('click',function(e){
			location.href="showPostListH?condition=writerId&value=${post.writerId}";
		})
		$('#sendMessage').on('click',function(e){
			var w = window.open('about:blank','messageForm','width=500,height=350');
			$('#sendMessageForm form').submit();
		})
		$('.showCreditList').on('click',function(e){ 
			var w = window.open('about:blank', 'creditList', 'width=900,height=900');
			w.document.location.href="showCreditList?accountNum=${post.writerNum}";
		})
		
		//신고하러 이동하는 이벤트. 
		$('#reportCredit').on('click',function(e){
			var w = window.open('about.blank', 'reportCredit', 'width=900,height=900');
			w.document.location.href="readyReportCreditH?postNum=${post.num}";
		})
		
		$('#saleCompleteButton').on('submit',function(e){
			if(!confirm('해당 게시물을 판매완료 상태로 변경하시겠습니까?')){
				e.preventDefault();
			}
		})
		//삭제 버튼 클릭시, 한번 더 물어보는 이벤트
		$('#deleteButton').on('submit', function(e){
			if(!confirm('정말 삭제하시겠습니까?')){
				e.preventDefault();	
			}
		})
		
		//댓글작성시, 댓글내용의 용량을 제한하는 이벤트. 80자로 제한. 
		$('.writeComment').on('submit', function(e){
			var commentContent = $(this).children('textarea').val();
			if(commentContent.length>80){
				e.preventDefault();
				alert('댓글의 내용이 너무 많습니다. 80자 이하로 작성해 주세요.');
			}
		})
		
		//댓글 삭제 버튼 눌렀을때 이벤트처리. data-commentNum속성으로부터 댓글 번호를 받아서 삭제요청을 보낸다. 
		$('.commentInfo2 span:nth-child(3)').on('click',function(e){
			var commentNum=$(this).attr('data-commentNum');
			var form = $('<form class="deleteButton" action="deleteCommentH" method="post">'
					+'<input type="hidden" name="num" value="'+commentNum+'"/>'
					+'<input type="hidden" name="postNum" value="${post.num}"/></form>');
			form.appendTo('body');
			form.submit();
		})
		//댓글에 답글입력하기위해 버튼누르면 답글입력창 뜨도록. 
		$('.commentInfo2 span:nth-child(2)').on('click', function(e){/*이벤트 객체 e*/
			var form = $(this).parents('.mainComment').next('.writeInnerComment');
			var display = $(form).css('display');
			if(display=='block'){
				$(form).css('display','none');
			}
			else{
				$(form).css('display','block');
			}
			//e.target.nextSibling.nextSibling.style.display = 'block';/*target이 이벤트가 발생한 요소(여기선 클릭이 발생한 button요소), nextSibling속성이
			//바로 다음 형제노드를 가리키는 값. 여기선 button다음의 텍스트값. nextSibling한번 더하여 form을 가리킴. style.display가 form의 css값 display를 가리킴.*/
		})
		$('.innerCommentSection .commentInfo2 span:nth-child(2)').on('click',function(e){
			var commentNum=$(this).attr('data-commentNum');
			var form = $('<form action="deleteCommentH" method="post">'
					+'<input type="hidden" name="num" value="'+commentNum+'"/>'
					+'<input type="hidden" name="postNum" value="${post.num}"/></form>');
			form.appendTo('body');
			form.submit();
		})
		
		$('body').on('keydown',function(e){
			if (e.keyCode == 116) {
				event.keyCode= 2;
				e.preventDefault();
				window.location.reload();
			}
			else if(event.ctrlKey && (event.keyCode==78 || event.keyCode == 82)){
				e.preventDefault();
				window.location.reload();
			}
		})
	</script>
</body>
</html>
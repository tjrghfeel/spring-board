<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/messageListCss.css?ver=72"/>
</head>
<body>
	<div id="messageListPage">
		<div id="myUtility">
			<!-- 위에 유틸 버튼들 부분 -->
			<a id="logo" href="showMainBoard">로고</a>
			<span>환영합니다 ${sessionScope.loginedAccount.id }님</span>
			<c:if test="${sessionScope.loginedAccount.grade==1 }">
				<a id="myPage" href="readyManagerPageH">관리자 페이지</a>
			</c:if>
			<c:if test="${sessionScope.loginedAccount.grade==0 }">
				<a id="myPage" href="readyMyPageH">MyPage</a>
			</c:if>
			<a id="logoutButton" href="logoutH">로그아웃</a>
			<!-- 쪽지함 이동 버튼. -->
			<form action="showMessageListH" method="post">
				<input type="hidden" name="id" value="${sessionScope.loginedAccount.id }"/>
				<input type="submit" value="쪽지함"/>
			</form>
		</div>
		
		<div id="upperContent">
		</div>
		
		<div id="messageList">
			<p>쪽지함</p>
			<div id="messageListTable">
				<table>
					<c:if test="${ empty messageList }">
						쪽지함이 비어있습니다.
					</c:if>
					<c:if test="${not empty messageList }">
						<c:forEach var="item" items="${messageList }" varStatus="status">
							<c:if test="${sessionScope.loginedAccount.id==item[0].sender }">
								<c:set var="partnerId" value="${item[0].receiver }"/>
							</c:if>
							<c:if test="${sessionScope.loginedAccount.id!=item[0].sender }">
								<c:set var="partnerId" value="${item[0].sender }"/>
							</c:if>
							
							<tr data-listIndex="${status.index }">
								<td class="">
									<span class="partnerId">${partnerId }</span>
									<span class="time">${item[0].time }</span><br>
									<span class="content">${item[0].content }</span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
		</div>
		
		<!-- 대화내역을 모두 table로 그려놓고 위에서 클릭하는 대화창만 display:block으로 바꿔주는식으로 구현. -->
		<div id="message">
			<p id="partnerId">대화 내용</p>
			<div id="messageTable">
				<c:forEach var="arrayList" items="${messageList }" varStatus="arrayStatus">
					<div id="${arrayStatus.index }">
						<c:if test="${sessionScope.loginedAccount.id==arrayList[0].sender }">
							<c:set var="partnerId" value="${arrayList[0].receiver }"/>
						</c:if>
						<c:if test="${sessionScope.loginedAccount.id!=arrayList[0].sender }">
							<c:set var="partnerId" value="${arrayList[0].sender }"/>
						</c:if>
						
						<table>
							<c:forEach var="i" begin="0" end="${fn:length(arrayList)-1 }">
							<c:set var="index" value="${fn:length(arrayList)-1-i }"/>
								<tr data-senderId="${arrayList[index].sender }">
									<td class="${(partnerId eq arrayList[index].sender)? 'partnerMessage' : 'myMessage' }">
										<div>
											<span class="time">${arrayList[index].time }</span><br>
											<span class="content">${arrayList[index].content }</span>
										</div>
									</td>
								</tr>
							</c:forEach>
						</table>
						<div class="sendMessage">
							<!-- 쪽지 보내는 부분 -->
							<form action="sendMessageH" method="post">
								<textarea name="content"></textarea>
								<input type="hidden" name="sender" value="${sessionScope.loginedAccount.id }"/>
								<input type="hidden" name="receiver" value="${partnerId }"/>
								<input type="submit" value="전송"/>
							</form>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		//
		$('#logoutButton').on('click',function(e){
			location.href="logoutH";
		})
	
		window.onload = function(){
			$('#messageList tr:nth-child(1) td').attr('class','currentMessageBox');
			var partnerId=$('#messageList #messageListTable tr:nth-child(1) .partnerId').text();
			$('#message > #partnerId').text(partnerId);

			var scrollDiv = $('#message #messageTable')[0];
			scrollDiv.scrollTop = scrollDiv.scrollHeight;
		}
		
		$('#messageList tr').on('click',function(e){
			$('#messageList tr td').attr('class','');
			$(this).children('td').attr('class','currentMessageBox');
			
			var arrayListIndex = $(this).attr('data-listIndex');
			$('#message #messageTable > div').css('display','none');
			$('#'+arrayListIndex).css('display','block');

			var partnerId = $(this).find('.partnerId').text();
			$('#message > #partnerId').text(partnerId);

			var scrollDiv = $('#message #messageTable')[0];
			scrollDiv.scrollTop = scrollDiv.scrollHeight;
		})

		//메세지 전송시 내용검증.
		$('#message form').on('submit',function(e){
			var content=$(this).children('textarea').val();
			if(content.length<1){
				alert('내용을 입력하세요');
				e.preventDefault();
			}
			else if(content.length>80){
				alert('내용이 너무 많습니다');
				e.preventDefault();
			}
		})
		
	</script>
</body>
</html>
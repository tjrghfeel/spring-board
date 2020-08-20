<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/sendMessageCss.css?ver=41232"/>

</head>
<body>
	<span>받는이  ${param.receiver}</span>
	<form id="sendMessageForm" action='sendMessageH' method='get'>
		<textarea name='content' maxlength="80"></textarea>
		<input type='hidden' name='sender' value='${param.sender}'/>
		<input type='hidden' name='receiver' value='${param.receiver}'/>
		<button id="sendButton">보내기</button><button id="cancelButton">취소</button>
	</form>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$('#sendButton').on('click',function(e){
			var content = $('textarea').val();
			$.ajax({
				url:'sendMessageH',
				type:'post',
				dataType:'text',
				data:{sender:'${param.sender}', receiver:"${param.receiver}", content:content}
			})
			alert('메세지가 전송되었습니다');
			close();
		}) 
		$('#cancelButton').on('click',function(e){
			close();
		})
		
	</script>
</body>
</html>
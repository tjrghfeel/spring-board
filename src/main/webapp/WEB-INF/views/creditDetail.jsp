<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/creditDetailCss.css?ver=42"/>

</head>
<body>
	<div id="creditDetail">
		<p>신고 내역</p>
		<div id="creditDetailTable">
			<table>
				<tr>
					<td>신고 번호 :</td>
					<td>${credit.num }</td>
				</tr>
				<tr>
					<td>신고 대상 :</td>
					<td>${credit.accountId }</td>
				</tr>
				<tr>
					<td>시간 :</td>
					<td>${credit.time }</td>
				</tr>
				<tr>
					<td>신고한 회원 : </td>
					<td>${credit.reportAccountId }</td>
				</tr>
				<tr>
					<td>게시글 :</td>
					<td id="post"><span>${credit.postNum }</span></td>
				</tr>
				<tr>
					<td id="content" colspan="2">
						<span>내용 :</span><br><br>
						<pre>${credit.content }</pre>
					</td>
				</tr>
			
			</table>
		</div>
		<button>확인</button>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$('button').on('click',function(e){
			window.close();
		})
		$('#post span').on('click',function(e){
			var postNum= $(this).text();
			window.opener.location.href="showPost?postNum="+postNum;
		})
	</script>
</body>
</html>
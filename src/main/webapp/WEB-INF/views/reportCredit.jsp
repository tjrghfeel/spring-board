<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/reportCreditCss.css?ver=41232"/>

</head>
<body>
	<div id="reportCredit">
		<p>신고하기</p>
		<form method="post" action="reportCreditH">
			<table>
				<tr>
					<td>신고 대상 : </td>
					<td>${credit.accountId }</td>
				</tr>
				<tr><td id="content" colspan="2">
					내용 : <br><br>
					<textarea name="content"></textarea>
				</td></tr>
			</table>
		</form>
	</div>
	<div id="buttonDiv">
		<button id="reportButton">신고하기</button><button id="cancelButton">취소</button>
	</div>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$('#reportCredit form').on('submit',function(e){
			var content=$(this).find('textarea').val();
			if(content.length>21000){
				e.preventDefault();
				alert('내용의 용량이 너무 많습니다');
			}
		})
		$('#reportButton').on('click',function(e){
			var content = $('textarea').val();
			$.ajax({
				url:'reportCreditH',
				type:'post',
				dataType:'text',
				data:{accountId:'${credit.accountId}', accountNum:${credit.accountNum}, 
					reportAccountNum:${credit.reportAccountNum}, reportAccountId:"${credit.reportAccountId}",
					postNum:${credit.postNum}, content:content 
					}
			})
			alert('신고가 접수되었습니다');
			window.opener.location.href="showPost?postNum=${credit.postNum}";
			close();
		})
		$('#cancelButton').on('click',function(e){
			window.close();
		})
		
		

	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/signUpCss.css?ver=2"/>
<title>회원가입</title>
</head>
<body>
	<div id="signUpPage">
		<div id="signUpTable">
			<form:form modelAttribute="account" action="signUpH" method="POST">
				<table>
					<tr>
						<td>이름 </td>	
						<td id="inputName" colspan="3">
							<form:input path="name" maxlength="10"/>
							<form:errors path="name" cssClass="errorMessage"/><br>
						</td>
					</tr>
					<tr>
						<td>id</td>
						<td id="inputId">
							<form:input path="id" maxlength="20"/><form:errors path="id" cssClass="errorMessage"/>
						</td>
					</tr>
					<tr>
						<td>pw</td>
						<td id="inputPw" colspan="3">
							<form:password path="pw" maxlength="20"/><form:errors path="pw" cssClass="errorMessage"/>
						</td>
					</tr>
					<tr>
						<td>email</td>
						<td id="inputEmail" colspan="3">
							<form:input path="email" maxlength="35"/><form:errors path="email" cssClass="errorMessage"/>
						</td>
					</tr>
					<tr>
						<td>주소</td>
						<td id="inputAddress" colspan="3">
							<p>${account.address }</p>
							<a onclick="forwardSearchPage()">주소 찾기</a>
							<form:errors path="address" cssClass="errorMessage"/>
						</td>
					</tr>
					<tr>
						<td>전화번호</td>
						<td id="inputPNum" colspan="4">
							<form:input path="pNum"/>
							<form:errors path="pNum" cssClass="errorMessage"></form:errors>
						</td>
					</tr>
				</table>	
				<input type="hidden" name="address"/><br>
			</form:form>
		</div><!-- #writeTable 끝. -->
		<div id="button">
			<button id="signUpButton">회원가입</button><button id="cancelButton">취소</button>
		</div>
	</div>
	
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		var forwardSearchPage = function(){
			window.open('showView?pageName=searchAddress','search address','height=1500, width=900');
		}
		
		var setAddress=function(roadFullAddr){
			$('input[name="address"]').attr('value',roadFullAddr);
			$('p').text(roadFullAddr);
		}

		$('#signUpPage #signUpButton').on('click',function(e){
			$('#signUpPage form').submit();
		})
		$('#signUpPage #cancelButton').on('click',function(e){
			location.href="showView?pageName=login";
		})
	</script>
</body>
</html>
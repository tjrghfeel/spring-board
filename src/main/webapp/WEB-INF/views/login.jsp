<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/dealSiteCss.css?ver=222"/>

</head>
<body>
	<div class="startPage">
		<h1>
			중고 거래 사이트에 오신걸 환영합니다
		</h1>
				
		<form:form modelAttribute="account" action="loginH" method="POST">
			<table>
				<tr>
					<td><label for="id">id</label></td>
					<td><input type="text" name="id" maxlength="20"></td>
				</tr>
				<tr>
					<td><label for="pw">pw</label> </td>
					<td><input type="password" name="pw" maxlength="20"></td>
				</tr>
				<tr><td colspan="2"><form:errors id="loginError" path="*" cssClass="errorMessage"/></td></tr>
				<tr>
					<td><button>로그인</button></td>
					<td><button type="button" id="signUpButton">회원가입</button></td>
				</tr>
			</table>
			
		</form:form>
		
		
	</div>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$('#signUpButton').on('click', function(e){
			location.href="readySignUpPage";
		})
	</script>
</body>
</html>
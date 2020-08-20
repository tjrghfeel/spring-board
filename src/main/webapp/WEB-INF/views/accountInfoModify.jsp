<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
</head>
<body>
	<!-- 회원정보 수정 -->
	<form:form modelAttribute="account" action="accountInfoModifyH" method="POST">
		<input type="hidden" name="num" value="${loginedAccount.num }"/>
		name : ${loginedAccount.name }<input type="hidden" name="name" value="${loginedAccount.name }"/><br>
		id : ${loginedAccount.id }<input type="hidden" name="id" value="${loginedAccount.id }"/><br>
		pw : <form:input path="pw"/><form:errors path="pw" cssClass="errorMessage"/><br>
		email : <form:input path="email"/><form:errors path="email" cssClass="errorMessage"/><br>
		address : <a onclick="forwardSearchPage()">주소 찾기</a><br>
		<p>${loginedAccount.address }</p><form:errors path="address" cssClass="errorMessage"/><br>
		phone number : <form:input path="pNum"/><form:errors path="pNum" cssClass="errorMessage"/><br>
		
		<input type="hidden" name="address" value="${loginedAccount.address }"/><br>
		<input type="submit" value="정보 수정"><br>
	</form:form>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		var forwardSearchPage = function(){
			window.open('showView?pageName=searchAddress','search address','height=1500, width=900');
		}
		
		var setAddress=function(roadFullAddr){
			$('input[name="address"]').attr('value',roadFullAddr);
			$('p').text(roadFullAddr);
		}
	</script>
</body>
</html>
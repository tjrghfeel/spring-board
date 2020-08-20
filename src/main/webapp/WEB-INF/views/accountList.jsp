<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<table>
		<tr>
			<th>id</th>
			<th>email</th>
			<th>phone</th>
			<th>address</th>
			<th>grade</th>
			<th>credit</th>
			<th>name</th>
			<th>대화 내역</th>
			<th></th>
		</tr>
		<c:if test="${empty accountList }">
			검색 결과가 없습니다.
		</c:if>
		<c:forEach var="item" items="${accountList }">
			<tr>
				<td>${item.id }</td>
				<td>${item.email }</td>
				<td>${item.pNum }</td>
				<td>${item.address }</td>
				<td>${item.grade }</td>
				<td data-creditList="${item.num }">${item.credit }</td>
				<td>${item.name }</td>
				<td data-messageList="${item.id }">대화내역</td>
				<td data-delete="${item.num }">삭제</td>
			</tr>
		</c:forEach>	
	</table>
	
	<!-- 회원 검색 창 -->
	<form action="showAccountListH" method="post">
		<select name="condition">
			<option value="id">아이디</option>
			<option value="email">이메일</option>
			<option value="pNum">전화번호</option>
		</select>
		<input type="text" name="value"/>
		<input type="submit" value="검색"/>
	</form>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$('td[data-creditList]').on('click', function(e){
			var accountNum = $(this).attr('data-creditList');
			var form = $('<form action="showCreditList" method="post">'
					+'<input type="hidden" name="accountNum" value="'+accountNum+'"/></form>');
			form.appendTo('body');
			form.submit();
		})
		$('td[data-messageList]').on('click', function(e){
			var accountId = $(this).attr('data-messageList');
			var form = $('<form action="showMessageListH" method="post">'
					+'<input type="hidden" name="id" value="'+accountId+'"/></form>');
			form.appendTo('body');
			form.submit();
		})
		$('td[data-delete]').on('click', function(e){
			var accountNum = $(this).attr('data-delete');
			var form = $('<form action="showMessageListH" method="post">'
					+'<input type="hidden" name="id" value="'+accountId+'"/></form>');
			form.appendTo('body');
			form.submit();
		})
	</script>
</body>
</html>
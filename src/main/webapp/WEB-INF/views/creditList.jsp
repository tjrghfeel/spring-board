<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/creditListCss.css?ver=1"/>

</head>
<body>
	<div id="creditList">
		<p>신고 내역 리스트</p>
		<div id="creditListTable">
			<table>
				<tr>
					<th>게시물 번호</th>
					<th>신고자 아이디</th>
					<th>시간</th>
					<th></th>
				</tr>
				<c:if test="${empty creditList }">
					<tr><td colspan="4">신고내역이 없습니다.</td></tr>
				</c:if>
				<c:if test="${not empty creditList }">
					<c:forEach var="i" items="${creditList }">
						<tr>
							<td>${i.postNum }</td>
							<td>${i.reportAccountId }</td>
							<td>${i.time }</td>
							<td><a href="showCreditH?creditNum=${i.num }">내용보기</a></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
		<button>닫기</button>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$('#creditList button').on('click', function(e){
			window.close();
		})
	</script>
</body>
</html>
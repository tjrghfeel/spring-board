<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/postListCss.css?ver=2122"/>
</head>
<body>
	
	<div id="postList">
		<!-- 게시물 목록 뿌려주는 부분 -->
		<table>
			<tr>
				<th>분류</th>
				<th>제목</th>
				<th>작성자</th>
				<th>시간</th>
				<th>조회</th>
			</tr>	
			<c:if test="${empty postList }">
				<tr><td colspan="5"><br>게시물이 없습니다.</td></tr>
			</c:if>
			<c:forEach var="item" items="${postList }" varStatus="status">
				<tr>
				<td>${item.sort2 }</td>
				<td>
					<a href="showPost?postNum=${item.num}">${item.title } &#91 ${item.commentCount } &#93</a>
					<c:if test="${item.saleComplete == true }"><span class="saleCompleteCheck">판매완료</span></c:if>
				</td>
				<td><a href="showPostListH?condition=writerId&value=${item.writerId }">${item.writerId }</a></td>
				<td>${item.writeTime }</td>	
				<td>${item.viewCount }</td>
				<tr>
			</c:forEach>
		</table>
	</div>
	
	<div id="utilityBar">
		<!-- 아래 유틸버튼들 부분 -->
		<a href="readyWritePostH">write post</a>
		<br><br>
		<div id="indexAndSearch">
			<div id="pageIndex">
				<!-- index버튼부분. -->
				<span class="nextPreIndex" data-index="-10">&lt;이전</span>
				<c:if test="${startIndex>0 && startIndex <= lastIndex }">
					<c:forEach var="i" begin="${startIndex }" end="${lastIndex }">
						<!-- 인덱스가 현재 인덱스인경우 표시를 위해 id가 적용된 < button>을 적용.  -->
						<c:if test="${currentIndex == i }">
							<span id="currentIndex">${i }</span>
						</c:if>
						<c:if test="${currentIndex != i }">
							<span>${i }</span> 
						</c:if>
					</c:forEach>
				</c:if>
				<span class="nextPreIndex" data-index="+10">다음&gt;</span>
			</div>
			<!-- 게시글 검색창 -->
			<form action="showPostListH" method="get">
				<select name="condition">
					<option value="titleAndContent">제목+내용</option>
					<option value="title">제목</option>
					<option value="writerId">작성자</option>
				</select>
				<input type="text" name="value"/>
				<input type="submit" value="검색"/>
			</form>
		
		</div>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		//새 게시물일 경우 제목옆에 'New'표시해주는 부분.
		var nowTime = new Date();//현재 시간을 가져옴.
		var timeTdList = $('td:nth-child(4)');//new단추를 표시해주기위해 제목td요소들을 모두 가져옴.
		var titleTdList=$('td:nth-child(2)');//time td요소들을 모두 가져옴.
	
		for(var i=0; i<timeTdList.length; i++){//모든 time td에 대해 반복을 하며 시간이 3일 이내인지 계산.
			var postTime = new Date(timeTdList[i].innerText);//time td의 시간값으로 js Date객체 만듦.
			var timeDiff = nowTime - postTime;//현재시간과 뺄셈.
			
			if(timeDiff < 216000000){//차이가 밀리세컨드단위 기준 3일 이내이면,
				$(titleTdList[i]).children('a').append('<span class="newCheck">new!</span>');//new단추 표시.
			}
		}

		//게시물 리스트에서 작성자id클릭시 해당 작성자의 게시물 검색하도록.
		/*$('#postList button').on('click',function(e){
			var iframe=$('#iframe iframe')[0];
			iframe.contentWindow.document.location.href="showPostListH?condition=writerId&value="+e.target.innerText;
		})*/

		//pageindex에서 index번호 버튼을 위한 이벤트처리.
		$('#pageIndex span').on('click',function(e){
			location.href="showPostListH?condition=${condition}&value=${value}&pageIndex="+e.target.innerText;
		})
		//pageindex에서 '다음', '이전' 버튼을 위한 이벤트처리.
		$('.nextPreIndex').on('click',function(e){
			var value=$(this).attr('data-index');
			var aa = Number(value);
			location.href="showPostListH?condition=${condition}&value=${value}&pageIndex="+(${currentIndex }+ aa);
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
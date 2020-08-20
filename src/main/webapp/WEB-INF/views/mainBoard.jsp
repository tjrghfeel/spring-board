<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/dealSiteCss.css?ver=2773"/>
	<script>
		function calHeight(a){
			a.style.height =  a.contentWindow.document.body.scrollHeight + 120+'px';
		}
	</script>
</head>
<body>
	<div id="mainBoard">
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
				<input type="submit" value="쪽지함"/>
			</form>
		</div>
		
		<div id="upperContent">
		</div>
		
		<div id="mainBody">
			<!-- 옆에있는 분류창 -->
			<div id="sortMenu">
				<h3><a href="showPostListH" target="iframe">전체 글보기</a></h3>
				<p>컴퓨터</p>
				<ul>
					<li id="이어폰">이어폰</li>
					<li id="노트북">노트북</li>
					<li id="데스크탑">데스크탑</li>
					<li id="모니터">모니터</li>
				</ul>
				<p>가전</p>
				<ul>
					<li id="세탁기">세탁기</li>
					<li id="에어컨">에어컨</li>
					<li id="냉장고">냉장고</li>
				</ul>
				<p>스포츠,레저</p>
				<ul>
					<li id="축구">축구</li>
					<li id="농구">농구</li>
					<li id="스키">스키</li>
					<li id="헬스">헬스</li>
				</ul>
				<p>류류</p>
				<ul>
					<li id="상의">상의</li>
					<li id="하의">하의</li>
					<li id="신발">신발</li>
					<li id="지갑">지갑</li>
				</ul>
				<p>도서</p>
				<ul>
					<li id="학습">학습</li>
					<li id="소설">소설</li>
					<li id="잡지">잡지</li>
					<li id="요리">요리</li>
				</ul>
			</div>
		
			<div id="iframe">
				<iframe src="showPostListH" name="iframe" onload="calHeight(this)" scrolling="no"></iframe>
				
			</div><!-- #iframe 끝. -->
		</div>
	</div>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>

		//대분류클릭시 소분류가 보이게또는 가려지게.
		$('#sortMenu p').on('click',function(e){
			var ulDisplay = $(this).next('ul').css('display');
			if(ulDisplay == 'block'){
				$(this).next('ul').css('display',"none");
			}
			else{$(this).next('ul').css('display','block');}
		})
			
		//소분류 클릭시 해당 분류의 게시물이 검색되도록.
		$('#sortMenu li').on('click',function(e){
			var iframe=$('#iframe iframe')[0];
			iframe.contentWindow.document.location.href="showPostListH?condition=sort2&value="+e.target.innerText;
		});	

		//로그아웃 버튼 클릭 이벤트. 
		$('#logoutButton').on('click',function(e){
			location.href="logoutH";
		})
		
		$('body').on('keydown',function(e){
			if (e.keyCode == 116) {
				event.keyCode= 2;
				e.preventDefault();
				var iframe=$('#iframe iframe')[0];
				console.log(iframe);
				iframe.contentWindow.document.location.reload();
			}
			else if(event.ctrlKey && (event.keyCode==78 || event.keyCode == 82)){
				e.preventDefault();
				var iframe=$('#iframe iframe')[0];
				iframe.contentWindow.document.location.reload();
			}
		})
	
	</script>
</body>
</html>
			
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/writePostCss.css?ver=12"/>
<title>Insert title here</title>
</head>
<body>	
	<div id="writePost">
		<div id="writeTable">
			<form:form modelAttribute="post" action="writePostH" method="POST" enctype="multipart/form-data">
				<table>
					<tr>
						<td>제목 </td>	
						<td id="titleTd" colspan="3"><form:input path="title"/><form:errors path="title" cssClass="errorMessage"/></td>
					</tr>
					<tr>
						<td>분류1</td>
						<td id="sortTd">
							<select name="sort1" id="sort1">
								<option value="0">분류를 선택해주세요</option>
								<option value="컴퓨터">컴퓨터</option>
								<option value="가전">가전</option>
								<option value="스포츠,레저">스포츠,레저</option>
								<option value="의류">의류</option>
								<option value="도서">도서</option>
							</select>
	
							<select name="sort2" id="sort2">
								<option>분류1을 선택해주세요</option>
							</select>
							<form:errors path="sort1" cssClass="errorMessage"/>
							<form:errors path="sort2" cssClass="errorMessage"/>
						</td>
					</tr>
					<tr>
						<td>대표이미지</td>
						<td id="mainImageTd" colspan="3">
							<button type="button">올리기</button>
							<input type="file" name="mainImageFile"/>
							<span></span>
							<form:errors path="mainImage" cssClass="errorMessage"/>
						</td>
					</tr>
					<tr>
						<td>가격</td>
						<td id="priceTd" colspan="3"><form:input path="price"/><form:errors path="price" cssClass="errorMessage"/><br></td>
					</tr>
					<tr>
						<td>개인정보 공개 여부</td>
						<td id="openTd" colspan="3">
							<form:radiobutton path="open" value="true" label="공개"/>
							<form:radiobutton path="open" value="false" label="비공개"/>
							<form:errors path="open" cssClass="errorMessage"/><br>
						</td>
					</tr>
					<tr>
						<td id="contentTd" colspan="4">
							<p>내용</p>
							<div id="content" contentEditable="true"></div>
							<form:input path="content"/><form:errors path="content" cssClass="errorMessage"/><br>
						</td>
					</tr>
				</table>	
			</form:form>
		</div><!-- #writeTable 끝. -->
		<div id="button">
			<button id="writeButton">올리기</button><button id="cancelButton">취소</button>
		</div>
	</div>
		
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
	<%-- 분류 선택 이벤트 처리. 분류1을 선택하면 그에 맞는 분류2의 내용이 뜨도록 설정. 모든 < select>를 display:none으로 하고있다가 클릭에 맞게
	display:inline을 바꿔줌으로서 구현. --%>
	$("#sort1").on('change', function(e){
		if(e.target.value=='0'){
			$('#sort2').html('<option value="0">분류1을 선택해 주세요</option>');
		}
		if(e.target.value=='컴퓨터'){
			$('#sort2').html('<option value="0">분류2를 선택해 주세요</option><option value="이어폰">이어폰</option><option value="노트북">노트북</option>'
					+'<option value="데스크탑">데스크탑</option><option value="모니터">모니터</option>');
		}
		if(e.target.value=='가전'){
			$('#sort2').html('<option value="0">분류2를 선택해 주세요</option><option value="세탁기">세탁기</option><option value="에어컨">에어컨</option>'
					+'<option value="냉장고">냉장고</option>');
		}
		if(e.target.value=='스포츠,레저'){
			$('#sort2').html('<option value="0">분류2를 선택해 주세요</option><option value="축구">축구</option><option value="농구">농구</option>'
					+'<option value="스키">스키</option><option value="헬스">헬스</option>');
		}
		if(e.target.value=='의류'){
			$('#sort2').html('<option value="0">분류2를 선택해 주세요</option><option value="상의">상의</option><option value="하의">하의</option>'
					+'<option value="신발">신발</option><option value="지갑">지갑</option>');
		}
		if(e.target.value=='도서'){
			$('#sort2').html('<option value="0">분류2를 선택해 주세요</option><option value="학습">학습</option><option value="소설">소설</option>'
					+'<option value="잡지">잡지</option><option value="요리">요리</option>');
		}
	})

	$('#writePost #contentTd #content').on('input',function(e){
		var pastedImages = $(this).children('img');
		var divWidth = $(this).css('width');
		divWidth=divWidth.replace('px',"");
		for(var i=0; i<pastedImages.length; i++){
			if(pastedImages[i].width > divWidth){
				pastedImages[i].width = divWidth - 60;
			}
		}
	}) 
	//이미지 입력을 위해 div에 내용을 입력하다가 다 작성하고 제출할때 div의 내용을 < form :input>에 옮겨넣어 보내질 수 있도록. 
	$('#writePost form').on('submit', function(e){
		var content = $('#content').html();
		if(content.length>15000000){
			e.preventDefault();
			alert('본문의 용량이 너무 많습니다.')
		}
		$('input[name="content"]').val(content);
	})
	
	//input file 버튼 바꾸기. button클릭하면 input file이 클릭되도록.
	$('#mainImageTd button').on('click',function(e){
		$('#mainImageTd input[type="file"]').trigger('click');
	})
	//바로 이어서, 이미지 선택하면 input file의 값을 span의 내용으로 추가하여 보이게함. 
	$('#mainImageTd input').on('input',function(e){
		var fileName = $(this).val();
		$(this).siblings('span').html(fileName);
	})
	
	//글 올리기 버튼
	$('#writePost #writeButton').on('click',function(e){
		$('#writeTable form').submit();
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
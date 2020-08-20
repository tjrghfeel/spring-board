<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<style>
		iframe{
			border:none;
			width:80vw;
			height:100vh;
		}
		table{
			width:80vw;
		}
		td{width:40vw;}
	</style>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/dealSiteCss.css?ver=2122"/>
</head>
<body>
	<form action="noName2" method="post" enctype="multipart/form-data">
		<input type="file" name="file"/>
		<input type="submit" value="submit"/>
	
	</form>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$('form').on('submit',function(e){
			var file = $('form input[type="file"]').val();
			console.log(file);
			e.preventDefault();
		})
	
	</script>	
</body>
</html>

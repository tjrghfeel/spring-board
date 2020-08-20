<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>

	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		$(document).ready(function(){
			var form = $('<form action="showView" method="post">'
					+'<input type="hidden" name="pageName" value="login"/></form>');
			form.appendTo('body');
			form.submit();
		})
	</script>
</body>
</html>

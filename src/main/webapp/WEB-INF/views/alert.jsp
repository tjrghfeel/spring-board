<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>alert page</title>
</head>
<body>

	<script>
		alert('haha');
		alert('${message}');
		location.href='/UsedArticleDealSite/UsedArticleDealSite/forward?pageName=${url}';

	</script>
</body>
</html>
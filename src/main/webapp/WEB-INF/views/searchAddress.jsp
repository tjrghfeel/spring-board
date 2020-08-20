<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
<%
	String address = request.getParameter("roadFullAddr");
	System.out.println("hello");
	System.out.println(address);
%>
<script>
	function init(){
		var confmKey='devU01TX0FVVEgyMDIwMDcwODE1MDUxMTEwOTkzNTg=';
		var url=location.href;
		var resultType=2;
		var inputYn = '${param.inputYn}';
		
		if(inputYn!='Y'){
			 document.form.confmKey.value = confmKey;
			 document.form.returnUrl.value = url;
			 document.form.resultType.value = resultType;
			 document.form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do";

			 document.form.submit();
		}
		else{
			window.opener.setAddress('${param.roadFullAddr}');
			window.close();
		}
		
	}

</script>
</head>
<body onload="init()">
	
	<form name="form" method="POST">
		<input type="hidden" name="confmKey" value=""/>
		<input type="hidden" name="returnUrl" value=""/>
		<input type="hidden" name="resultType" value=""/>
		<!-- <input type="hidden" id="encodingType" name="encodingType" value="EUC-KR"/> --> 
	</form>

	
	
</body>
</html>
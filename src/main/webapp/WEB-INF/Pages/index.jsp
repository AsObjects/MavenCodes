<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鞋购网</title>
<base href="<%=basePath%>">
</head>
<script type="text/javascript" src="JavaScript/jquery.min.js"></script>
<script type="text/javascript" src="JavaScript/Common.js"></script>
<link rel="stylesheet" type="text/css" href="CSS/Common.css"><link>
<body>
<%@ include file="header.jsp" %>
<p>hao</p>
</body>
</html>
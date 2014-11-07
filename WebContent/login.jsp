<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>移动警务系统登录</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/ext/resources/css/ext-all.css" />
<style type="text/css">
#container {
	width:510px;
	height:320px;
	margin:0 auto;
	margin-top:60px
}
</style>
<script type="text/javascript" src="<%=path%>/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/ext/ext-all-debug.js"></script>
<script type="text/javascript"  src="<%=path%>/js/admin_login.js"></script>
<script type="text/javascript">
setPath('<%=path%>');
</script>
</head>
<body>
	<div id="container">
    </div>
</body>
</html>







































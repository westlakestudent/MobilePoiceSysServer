<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">    
 body{    
      background-image: url(images/back.png);    
      background-repeat: repeat-x;    
 }    
  </style>  
<title>事件发布模块</title>
<script type="text/javascript">     
function check(){        
 var result = action.result;
 if(result == 'success'){
	 
 }
 
 }     
</script> 
</head>
<h1 align="center"><font color="white" size=15 face="楷体">最新事件发布</font></h1>
<body>
<% long l = new java.util.Date().getTime();
	String time = String.valueOf(l);
%>
<% request.setAttribute("time",time); %>
<center>
<s:form action="/actions/event_srelease.do" method="post" theme="simple">
<s:hidden name="event.time" value="%{#request.time}"></s:hidden>
<s:hidden name="event.path" value="null"></s:hidden>
<s:hidden name="event.status" value="N"></s:hidden>
<s:text name="标题:"/>&nbsp;&nbsp;&nbsp;<s:textfield name="event.title"/>
<s:text name="地点:"/>&nbsp;&nbsp;&nbsp;<s:textfield name="event.location"/><br><br>
<s:textarea cols="60" rows="10" name="event.content"/><br><br>
<s:submit value="提交" />
<s:reset value="清除"/>
</s:form>
</center>
</body>
</html>
<%-- 
    Document   : erroDeletarContratoView
    Created on : 10/05/2018, 15:48:06
    Author     : Emm
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
 <head>
        <meta charset = "utf-8">
        <meta http-equiv = "X-UA-Compatible" content = "IE = edge">
        <meta name = "viewport" content = "width = device-width, initial-scale = 1">
        <!-- Bootstrap -->
        <link href = "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel = "stylesheet">
    <title>Mensagens</title>
 </head>
 
    <body style="background-color: activeborder">
 
    <jsp:include page="/template/_header.jsp"></jsp:include>
    <jsp:include page="/template/_menu.jsp"></jsp:include>
    
    
    <h3 style="color: red;">${errorString}</h3>
    <br>
    <br>
    
    
    <jsp:include page="/template/_footer.jsp"></jsp:include>
    
 </body>
</html>
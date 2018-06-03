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
    <meta charset="UTF-8">
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
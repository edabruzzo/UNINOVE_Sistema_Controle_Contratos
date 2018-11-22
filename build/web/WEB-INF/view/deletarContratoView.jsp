<%-- 
    Document   : deletarContratoView
    Created on : 10/05/2018, 15:24:09
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
    <title>Deletar Contrato</title>
 </head>
 
    <body style="background-color: activeborder">
 
    <jsp:include page="/template/_header.jsp"></jsp:include>
    <jsp:include page="/template/_menu.jsp"></jsp:include>
    
      <h3>Deletar Contrato</h3>
      
      <h4>REGRA DE NEGÓCIO: FUNCIONÁRIO SÓ PODE DELETAR UM CONTRATO SE ELE FOR GESTOR DO CONTRATO</h4> 
      
      <h5 style="color: red;">${errorString}</h5>
 
      <c:if test="${not empty contrato}">
         <form method="POST" action="${pageContext.request.contextPath}/jdbcDependente/deletarContrato">
            <input type="hidden" name="idContrato" value="${contrato.idContrato}" />
                      <input type="submit" value="DELETAR CONTRATO" />
                      <a href="${pageContext.request.contextPath}/jdbcDependente/contratos">Cancelar</a>
                 
         </form>
      </c:if>
    
    <a href="/jdbcDependente/contratos">Contratos</a>
    
    <jsp:include page="/template/_footer.jsp"></jsp:include>
    
 </body>
</html>
<%-- 
    Document   : homeView
    Created on : 10/05/2018, 13:17:15
    Author     : Emm
--%>

<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sistema de Controle de Contratos</title>
    </head>
    <body style="background-color: activeborder">
        <jsp:include page="/template/_header.jsp"></jsp:include>
        <jsp:include page="/template/_menu.jsp"></jsp:include>
            <strong>
                <h3>Home Page</h3>
                <br>PROJETO DESENVOLVIDO PARA A MATÉRIA PROGRAMAÇÃO ORIENTADA A OBJETOS.<br>
                <b>Propósito do projeto:</b>
                <ul>
                    <li>Login</li>
                    <li>Gerenciamento de contratos</li>
                    <li>Criação de Contrato</li>
                    <li>Atualização do Contrato</li>
                    <li>Remoção do Contrato</li>
                    <li>Listagem dos usuários cadastrados</li>
                </ul>
            </strong>
            
        <jsp:include page="/template/_footer.jsp"></jsp:include>
 </body>
</html>
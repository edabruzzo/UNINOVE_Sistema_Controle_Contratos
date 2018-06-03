<%-- 
    Document   : userInfoView
    Created on : 10/05/2018, 13:53:37
    Author     : Emm
--%>
 

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Informações de usuários</title>
 </head>
    <body style="background-color: activeborder">

    <jsp:include page="/template/_header.jsp"></jsp:include>
    <jsp:include page="/template/_menu.jsp"></jsp:include>



    
    <jsp:useBean id="controll" class="Controller.UsuariosServletController"/>
    <h3>LISTA USUÁRIOS</h3>

            <p style="color: red;">${errorString}</p>

        <table border="1" cellpadding="5" cellspacing="1" >
            
            <tr>
                <th>Id Usuário</th>
                <th>Nome</th>
                <th>Departamento</th>
                <th>Login</th>
                <th>Data de Admissão</th>
                <th>Ativo ?</th>
                <th>Papel do Usuário</th>
                
            </tr>
            <c:forEach items="${listaUsuarios}" var="usuario" >
                <tr>
                    <td>${usuario.idUsuario}</td>
                    <td>${usuario.nome}</td>
                    <td>${usuario.departamento}</td>
                    <td>${usuario.login}</td>
                    <td>${usuario.dataAdmissao}</td>
                    <td>${usuario.ativo}</td>
                    <td>${usuario.papelUsuario}</td>
                </tr>
            </c:forEach>
        </table>
    
    
    <jsp:include page="/template/_footer.jsp"></jsp:include>
 
    
    
    
 </body>
</html>
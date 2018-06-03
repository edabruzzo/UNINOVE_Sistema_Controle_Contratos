<%-- 
    Document   : contratosView
    Created on : 10/05/2018, 14:02:08
    Author     : Emm
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>LISTA CONTRATOS</title>
    </head>
    <body style="background-color: activeborder">

        <jsp:include page="/template/_header.jsp"></jsp:include>
        <jsp:include page="/template/_menu.jsp"></jsp:include>

            <h3>LISTA CONTRATOS</h3>
        <h4><a href="${pageContext.request.contextPath}/jdbcDependente/criarContrato">Criar Contrato</a></h4>
            <p style="color: red;">${errorString}</p>

        
        <table border="1" cellpadding="5" cellspacing="1" >
            <tr>
                <th>Id_Contrato</th>
                <th>Objeto do Contrato</th>
                <th>Orçamento Comprometido em R$</th>
                <th>Empresa contratada</th>
                <th>Departamento Responsável</th>
                <th>Funcionário Gestor</th>
                <th>Ativo</th>
                <th>Editar</th>
                <th>Deletar</th>
            </tr>

            <c:forEach items="${listaContratos}" var="contrato" >
                <tr>
                    <td>${contrato.idContrato}</td>
                    <td>${contrato.objetoContrato}</td>
                    <td>${contrato.orcamentoComprometido}</td>
                    <td>${contrato.empresaContratada}</td>
                    <td>${contrato.departamentoResponsavel}</td>
                    <td>${contrato.funcionarioGestor.nome}</td>
                    <td>${contrato.ativo}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/jdbcDependente/editarContrato?idContrato=${contrato.idContrato}">Editar</a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/jdbcDependente/deletarContrato?idContrato=${contrato.idContrato}">Deletar</a>
                    </td>
                </tr>
            </c:forEach>
        </table>


        <jsp:include page="/template/_footer.jsp"></jsp:include>

    </body>
</html>
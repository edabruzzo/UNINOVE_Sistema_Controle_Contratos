<%-- 
    Document   : editarContratoView
    Created on : 10/05/2018, 14:56:49
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
        <title>Editar Contrato</title>
    </head>

    <body style="background-color: activeborder">

        <jsp:include page="/template/_header.jsp"></jsp:include>
        <jsp:include page="/template/_menu.jsp"></jsp:include>

            <h3>EDITAR CONTRATO</h3>

            <h4>REGRA DE NEGÓCIO: FUNCIONÁRIO SÓ PODE EDITAR UM CONTRATO SE ELE FOR GESTOR DO CONTRATO</h4> 

            <h3 style="color: red;">${errorString}</h3>



        <c:if test="${not empty contrato}">
            <form method="POST" action="${pageContext.request.contextPath}/jdbcDependente/editarContrato">
                <input type="hidden" name="idContrato" value="${contrato.idContrato}" />
                <table border="0">
                    <tr>
                        <td>Código</td>
                        <td style="color:red;">${contrato.idContrato}</td>
                        <td><input type="hidden" name="idContrato" value="${contrato.idContrato}" 
                    </tr>
                    <tr>
                        <td>Objeto do Contrato</td>
                        <td><input type="text" name="objetoContrato" value="${contrato.objetoContrato}" /></td>
                    </tr>
                    <tr>
                        <td>Orçamento comprometido</td>
                        <td><input type="number" name="orcamentoComprometido" value="${contrato.orcamentoComprometido}" /></td>
                    </tr>
                    <tr>
                        <td>Empresa contratada</td>
                        <td><input type="text" name="empresaContratada" value="${contrato.empresaContratada}" /></td>
                    </tr>

                    <tr>
                        <td>Departamento Responsável</td>
                        <td><input type="text" name="departamentoResponsavel" value="${contrato.departamentoResponsavel}" /></td>
                    </tr>

                    <tr>
                        <td>Funcionario Gestor</td>
                        <td> 
                            <select name="id_usuario">
                                <c:forEach var="item" items="${funcionarios}">
                                    <option value="${item.idUsuario}">${item.nome}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>Contrato Ativo?</td>
                        <td><input type="radio" name="ativo" value="S" />SIM</td>
                        <td><input type="radio" name="ativo" value="N" />NÃO</td>

                    </tr>


                    <td colspan = "2">
                        <input type="submit" value="EDITAR CONTRATO" />
                        <a href="${pageContext.request.contextPath}/jdbcDependente/contratos">Cancelar</a>
                    </td>
                    </tr>
                </table>
            </form>
        </c:if>

        <jsp:include page="/template/_footer.jsp"></jsp:include>

    </body>
</html>
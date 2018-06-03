/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Usuario;
import java.sql.Connection;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Emm
 */
@WebServlet(name = "ConexaoServletController", urlPatterns = {"/jdbcDependente/*"}/*, loadOnStartup = 0*/)
public class ConexaoServletController extends HttpServlet {

   private static final long serialVersionUID = 1L;
   
   public static final String ATRIBUTO_CONEXAO = "ATRIBUTO_PARA_CONEXÃO";
 
  private static Usuario usuarioAtualmenteLogado;

   public Usuario getUsuarioAtualmenteLogado() {
        
        return usuarioAtualmenteLogado;
    }

    public static void setUsuarioAtualmenteLogado(Usuario usuarioLogado) {
        usuarioAtualmenteLogado = usuarioLogado;
    }
    // Store Connection in request attribute.
    // (Information guardard only exist during requests)
    public static void guardarConexao(ServletRequest request, Connection conn) {
        System.out.println("Guardando conexão JDBC na sessão");
        request.setAttribute(ATRIBUTO_CONEXAO, conn);
    }
 
    // Get the Connection object has been guardard in attribute of the request.
    public static Connection getConexaoGuardada(ServletRequest request) {
        System.out.println("Recuperando conexão JDBC guardada na sessão");
        Connection conn = (Connection) request.getAttribute(ATRIBUTO_CONEXAO);
        return conn;
    }
    
    
    
    // Guarda usuario info in Session.
    public static void guardarUsuarioLogado(HttpSession session, Usuario usuarioLogado) {
        // On the JSP can access via ${usuarioLogado}
        System.out.println("Guardando usuário logado na sessão");
        session.setAttribute("usuarioLogado", usuarioLogado);
        setUsuarioAtualmenteLogado(usuarioLogado);
    }
 
    // Get the usuario information stored in the session.
    public static Usuario getUsuarioLogado(HttpSession session) {
        System.out.println("Verificando se há usuário logado na sessão");
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        return usuarioLogado;
    }
 
 
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;



import DAO.ContratoDAO;
import DAO.UsuarioDAO;
import Model.Contrato;
import Model.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Emm
 */

@WebServlet(urlPatterns = { "/jdbcDependente/editarContrato" }, loadOnStartup = 0)
public class EditarContratoServletController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ContratoDAO contratoDAO = new ContratoDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    
    public EditarContratoServletController() {
        super();
    }
 
    // Show contrato edit page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        
        Usuario usuarioLogado =  new ConexaoServletController().getUsuarioAtualmenteLogado();
      
        
    Connection conn = ConexaoServletController.getConexaoGuardada(request);
            List<Usuario> funcionarios = null;
        try {
            funcionarios = usuarioDAO.consultaUsuarios(conn);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CriarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("funcionarios", funcionarios);

        //NECESSÁRIO FAZER O PARSER DO PARÂMETRO
        String idContratoStrg = (String) request.getParameter("idContrato");
        int idContrato = 0;
        idContrato = Integer.parseInt(idContratoStrg);
        Contrato contrato = null;
        String errorString = null;
 
        try {
            contrato = contratoDAO.findContrato(conn, idContrato);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } catch (ClassNotFoundException ex) {
            
            Logger.getLogger(EditarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        // If no error.
        // The contrato does not exist to edit.
        // Redirect to contratos page.
        if (errorString != null && contrato == null) {
            
             request.setAttribute("errorString", errorString);
            response.sendRedirect(request.getServletPath() + "/jdbcDependente/contratos");
            return;
        }else if(usuarioLogado.getIdUsuario() != contrato.getFuncionarioGestor().getIdUsuario()){
        
            errorString = "Você não é gestor do contrato e, portanto, não tem permissão para editá-lo!\n"
                    + "\n"
                    + "O gestor deste contrato é o funcionário : "+contrato.getFuncionarioGestor().getNome();

             request.setAttribute("errorString", errorString);
            // 
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/erroContratoView.jsp");
            dispatcher.forward(request, response);

            return;
            
        }
        // Store errorString in request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("contrato", contrato);
 
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/view/editarContratoView.jsp");
        dispatcher.forward(request, response);
 
    }
 
    // After the user modifies the contrato information, and click Submit.
    // This method will be executed.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Connection conn = ConexaoServletController.getConexaoGuardada(request);
    
        String codigoContrato = (String) request.getParameter("idContrato");
        String objetoContrato = (String) request.getParameter("objetoContrato");
        String empresaContratada = (String) request.getParameter("empresaContratada");
        String active = (String) request.getParameter("ativo");
        String orcamento = (String) request.getParameter("orcamentoComprometido");
        String departamentoResponsavel = (String) request.getParameter("departamentoResponsavel");
        String idFuncionarioGestor = request.getParameter("id_usuario");
        
        int idContrato = 0;
        double orcamentoComprometido = 0;
        boolean ativo = false;
        int idGestor = 0;
        
        String errorString = null;
        
        
        try{
            //NECESSÁRIO FAZER A CONVERSÃO DOS VALORES QUE VÊM DA TELA
            idContrato = Integer.parseInt(codigoContrato);
            orcamentoComprometido = Double.parseDouble(orcamento);
            idGestor = Integer.parseInt(idFuncionarioGestor);
            if("S".equals(active)) ativo = true;
            
        } catch (Exception e) {
         
            errorString = "Não foi possível ler os dados passados!";
            System.out.println("Não foi possível fazer o parser dos valores que vieram no request");
            
        }

        Contrato contrato = new Contrato();
        contrato.setIdContrato(idContrato);
        contrato.setAtivo(ativo);
        contrato.setDepartamentoResponsavel(departamentoResponsavel);
        contrato.setEmpresaContratada(empresaContratada);
        contrato.setOrcamentoComprometido(orcamentoComprometido);
        contrato.setObjetoContrato(objetoContrato);
        
        Usuario funcionarioGestor = new Usuario();
        
        try {
        
            funcionarioGestor = usuarioDAO.findUsuario(conn, idGestor);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CriarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CriarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        contrato.setFuncionarioGestor(funcionarioGestor);
 
        if (errorString == null) {
            try {
            
            errorString =  contratoDAO.editarContrato(conn, contrato);
            
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CriarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 
        // Store infomation to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("contrato", contrato);
 
        
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/erroContratoView.jsp");
            dispatcher.forward(request, response);

            
            
        }
    
}
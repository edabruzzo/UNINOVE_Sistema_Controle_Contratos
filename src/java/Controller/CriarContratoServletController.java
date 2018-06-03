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
 
@WebServlet(urlPatterns = { "/jdbcDependente/criarContrato" })
public class CriarContratoServletController extends HttpServlet {
    

    private static final long serialVersionUID = 1L;
    
    ContratoDAO contratoDAO = new ContratoDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
 
    public CriarContratoServletController() {
        super();
    }
 
    // Show product creation page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


            Usuario usuarioLogado = new ConexaoServletController().getUsuarioAtualmenteLogado();

            if(!usuarioLogado.getPapelUsuario().equals("gestor")){
                
                String errorString = ""
                        + "REGRA DE NEGÓCIO: \n"
                        + "SOMENTE GESTORES PODEM CRIAR CONTRATOS\n"
                        + "\n"
                        + "Você não tem permissão para criar um novo contrato";
            request.setAttribute("errorString", errorString);
        
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/view/erroContratoView.jsp");
        dispatcher.forward(request, response);
                
                
                return;
            }



        
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
        
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/view/criarContratoView.jsp");
        dispatcher.forward(request, response);
    }
 
    // When the user enters the product information, and click Submit.
    // This method will be called.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection conn = ConexaoServletController.getConexaoGuardada(request);
        String objetoContrato = (String) request.getParameter("objetoContrato");
        String empresaContratada = (String) request.getParameter("empresaContratada");
        String orcamento = (String) request.getParameter("orcamentoComprometido");
        String departamentoResponsavel = (String) request.getParameter("departamentoResponsavel");
        String idFuncionarioGestor = request.getParameter("id_usuario");
        
        int codigo = 0;
        double orcamentoComprometido = 0;
        int idGestor = 0;
        
        String errorString = null;
        
        
        try{
            //NECESSÁRIO FAZER A CONVERSÃO DOS VALORES QUE VÊM DA TELA
            orcamentoComprometido = Double.parseDouble(orcamento);
            idGestor = Integer.parseInt(idFuncionarioGestor);
            
        } catch (Exception e) {
         
            errorString = "Não foi possível ler os dados passados!";
            System.out.println("Não foi possível fazer o parser dos valores que vieram no request");
            
        }

        Contrato contrato = new Contrato();
        contrato.setAtivo(true);
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
            
          errorString =  contratoDAO.criarContrato(conn, contrato);
            
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
 
        // If error, forward to Edit page.
        if (!errorString.contains("sucesso")) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/criarContratoView.jsp");
            dispatcher.forward(request, response);
        }
        // If everything nice.
        // Redirect to the product listing page.
        else {

            // 
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/erroContratoView.jsp");
            dispatcher.forward(request, response);

        }
    }
 
}
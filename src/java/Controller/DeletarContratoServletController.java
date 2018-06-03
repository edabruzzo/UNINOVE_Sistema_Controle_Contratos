/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import DAO.ContratoDAO;
import Model.Contrato;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
@WebServlet(urlPatterns = { "/jdbcDependente/deletarContrato" }, loadOnStartup = 0)
public class DeletarContratoServletController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    ContratoDAO contratoDAO = new ContratoDAO();
 
    public DeletarContratoServletController() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         Connection conn = ConexaoServletController.getConexaoGuardada(request);
         Contrato contrato = new Contrato();

         String codigoString = (String) request.getParameter("idContrato");
 
        int idContrato = 0;
        idContrato = Integer.parseInt(codigoString);

        String errorString = null;
        
        try {
            contrato = contratoDAO.findContrato(conn, idContrato);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeletarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DeletarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            errorString = contratoDAO.removerContrato(conn, contrato);

        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } catch (ClassNotFoundException ex) { 
            Logger.getLogger(DeletarContratoServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
            request.setAttribute("errorString", errorString);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/erroContratoView.jsp");
            dispatcher.forward(request, response);
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
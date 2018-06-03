/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Util.OperacoesBancoDados;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Emm
 *
 *
 * ESTA SERVLET NÃO PODE PASSAR PELO FILTRO JDBC
 *
 * https://stackoverflow.com/questions/31318397/webfilter-exclude-url-pattern
 *
 *
 *
 */
@WebServlet(name = "HomeServletController", urlPatterns = {"/home"}, loadOnStartup = 1)
public class HomeServletController extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public HomeServletController() {

       super();

        try {
        
        new OperacoesBancoDados().criaBaseDados();
        new OperacoesBancoDados().criaInfraestrutura();
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeServletController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeServletController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    // Show Login page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

             
        try {
            new OperacoesBancoDados().criaBaseDados();
            new OperacoesBancoDados().criaInfraestrutura();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeServletController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeServletController.class.getName()).log(Level.SEVERE, null, ex);
        }
           String url = "homeView.jsp";
            request.getRequestDispatcher(url).forward(request, response);
        }

}
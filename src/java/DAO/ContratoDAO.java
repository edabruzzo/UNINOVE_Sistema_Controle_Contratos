/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Controller.ConexaoServletController;
import Model.Contrato;
import Model.Usuario;
import Util.OperacoesBancoDados;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emm
 */
public class ContratoDAO {

    OperacoesBancoDados fabrica = new OperacoesBancoDados();

    ConexaoServletController conexao = new ConexaoServletController();

    UsuarioDAO usuarioDAO = new UsuarioDAO();



    public String criarContrato(Connection conn, Contrato contrato) throws ClassNotFoundException, SQLException {

        /*REGRA DE NEGÓCIO: 
            FUNCIONÁRIO SÓ PODE CRIAR UM CONTRATO SE ELE TIVER PAPEL DE GESTOR NO SISTEMA
         */
        String mensagem = null;
        
        //REGRA DE NEGÓCIO: A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA
        if(contrato.getFuncionarioGestor().isAtivo() == true){

        if (conexao.getUsuarioAtualmenteLogado().getPapelUsuario().equals("gestor")) {

            String sql = "INSERT INTO tb_contrato("
                    + "objetoContrato, "
                    + "orcamentoComprometido, "
                    + "ATIVO, "
                    + "empresaContratada, "
                    + "departamentoResponsavel, "
                    + "id_funcionarioGestor)"
                    + "VALUES ('" + contrato.getObjetoContrato()
                    + "', " + contrato.getOrcamentoComprometido()
                    + ", " + contrato.isAtivo()
                    + ", '" + contrato.getEmpresaContratada()
                    + "', '" + contrato.getDepartamentoResponsavel()
                    + "', " + contrato.getFuncionarioGestor().getIdUsuario()
                    + ");";

            fabrica.executaQuerieUpdate(conn, sql);

            mensagem = "Contrato criado com sucesso";

        } else {
            mensagem = "REGRA DE NEGÓCIO: \n" 
                       +"UNCIONÁRIO SÓ PODE CRIAR UM CONTRATO SE ELE TIVER PAPEL DE GESTOR NO SISTEMA\n"
                       +"Você não tem permissão para criar contratos no sistema";
        }
        }else{
             mensagem = "REGRA DE NEGÓCIO: \n"
                     + "A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA\n"
                     + "\n"
                     + "O funcionário ao qual você está tentando atribuir a gestão do contrato não está ativo no sistema" ;  
        } 

        return mensagem;

    }

    public String editarContrato(Connection conn, Contrato contrato) throws ClassNotFoundException, SQLException {
        
        
        String mensagem = null;
        //    REGRA DE NEGÓCIO: FUNCIONÁRIO SÓ PODE EDITAR UM CONTRATO SE ELE FOR GESTOR DO CONTRATO ESPECÍFICO

        
        
        //REGRA DE NEGÓCIO: A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA
        if(contrato.getFuncionarioGestor().isAtivo() == true){

        
        String sql1 = "UPDATE tb_contrato "
                + "SET objetoContrato = '" + contrato.getObjetoContrato()
                + "', orcamentoComprometido = " + contrato.getOrcamentoComprometido()
                + ", ATIVO = " + contrato.isAtivo()
                + ", empresaContratada = '" + contrato.getEmpresaContratada()
                + "', departamentoResponsavel = '" + contrato.getDepartamentoResponsavel()
                + "', id_funcionarioGestor = " + contrato.getFuncionarioGestor().getIdUsuario()
                + " WHERE id_contrato = " + contrato.getIdContrato() + ";";
        try{
        fabrica.executaQuerieUpdate(conn, sql1);
        mensagem = "Contrato editado com sucesso";    
        
        }catch(Exception ex){
            
        ex.printStackTrace();
        mensagem = ex.getMessage();
        
        }
        }else{
            
          mensagem = "REGRA DE NEGÓCIO: \n"
                     + "A GESTÃO DE CONTRATOS SÓ PODE SER FEITA POR FUNCIONÁRIOS ATIVOS NO SISTEMA\n"
                  +"\n"   
                  + "O funcionário "
                  + "("+contrato.getFuncionarioGestor().getNome()+") "
                  + "ao qual você está tentando atribuir a gestão"
                  + " do contrato não está ativo no sistema" ;  
        
        }
        return mensagem;
        
    }

    public String removerContrato(Connection conn, Contrato contrato) throws SQLException, ClassNotFoundException {

        /*
        
        REGRA DE NEGÓCIO:
        
        APENAS O GESTOR DO CONTRATO PODE EXCLUIR UM CONTRATO
        
         */
        String mensagem = null;

        if (contrato.getFuncionarioGestor().getIdUsuario() == conexao.getUsuarioAtualmenteLogado().getIdUsuario()) {

            String sql = "DELETE FROM tb_contrato WHERE id_contrato = "
                    + contrato.getIdContrato() + ";";

            fabrica.executaQuerieUpdate(conn, sql);

            mensagem = "sucesso";

        } else {

            mensagem = "REGRA DE NEGÓCIO:\n" +
"        \n" +
"        APENAS O GESTOR DO CONTRATO, QUE NESTE CONTRATO É O FUNCIONÁRIO"
                    + " "+contrato.getFuncionarioGestor().getNome()+" "
                    + "PODE EXCLUIR UM CONTRATO\n"
         
        + "Você não tem permissão para remover o contrato";

        }

        return mensagem;

    }

    public List<Contrato> consultaContratos(Connection conn) throws ClassNotFoundException, SQLException {

        /*
        
        REGRA DE NEGÓCIO: 
        
        SE FUNCIONÁRIO É GESTOR ELE ENXERGA TODOS OS CONTRATOS,
        INCLUSIVE DE OUTROS DEPARTAMENTOS.
        SE ELE NÃO FOR GESTOR, 
        APENAS ENXERGA CONTRATOS DO SEU DEPARTAMENTO OU CONTRATOS 
        DE OUTROS DEPARTAMENTOS EM QUE ELE É GESTOR
        
         */
        String sql = null;

        if (conexao.getUsuarioAtualmenteLogado().getPapelUsuario().equals("gestor")) {
            sql = "SELECT * FROM tb_contrato;";
        } else {
            sql = "SELECT * FROM tb_contrato "
                    + "WHERE departamentoResponsavel = '"
                    + conexao.getUsuarioAtualmenteLogado().getDepartamento() + "'"
                    + "OR id_funcionarioGestor = "+conexao.getUsuarioAtualmenteLogado().getIdUsuario()+";";
        }

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);

        return extraiListaContratos(conn, rs);

    }

    public Contrato findContrato(Connection conn, int id) throws ClassNotFoundException, SQLException {

        /*        
        REGRA DE NEGÓCIO: FUNCIONÁRIO SÓ PODE ENXERGAR CONTRATOS DO SEU DEPARTAMENTO,
        EXCETO SE ELE FOR GESTOR. NESTE ÚLTIMO CASO, PODE ENXERGAR CONTRATOS DE OUTROS
        DEPARTAMENTOS.
        */
        Contrato contrato = new Contrato();
        String sql = null;
        
        if(conexao.getUsuarioAtualmenteLogado().getPapelUsuario().equals("gestor")){
            
            sql = "SELECT * FROM tb_contrato WHERE id_contrato = "
                + id + " ;";
        }else {
            
            sql = "SELECT * FROM tb_contrato WHERE id_contrato = "
                    +id + " AND departamentoResponsavel = '"
                    +conexao.getUsuarioAtualmenteLogado().getDepartamento()+"';";
            
        } 
        
        ResultSet rs = null;

        try{
         rs = fabrica.executaQuerieResultSet(conn, sql);
        }catch(NullPointerException npe){
            
            System.out.println("Usuário sem permissão para visualisar contrato"
                    + "de outro departamento!");
        }
        
        if (rs != null){
        contrato = this.extraiContratoResultSet(conn, rs);    
        }
        return contrato;

    }

    public List<Contrato> findByObjeto(Connection conn, String objeto) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_contrato WHERE objetoContrato LIKE '"
                + objeto + "';";

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);

        return extraiListaContratos(conn, rs);

    }

    public List<Contrato> findAtivos(Connection conn) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_contrato WHERE ativo IS TRUE;";

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);

        return this.extraiListaContratos(conn, rs);

    }

    public List<Contrato> findInativos(Connection conn) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_contrato WHERE ativo IS FALSE;";

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);

        return this.extraiListaContratos(conn, rs);
    }

    public List<Contrato> findByEmpresaContratada(Connection conn, String empresaContratada) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_contrato WHERE empresaContratada = '"
                + empresaContratada + "';";

        ResultSet rs = fabrica.executaQuerieResultSet(conn, sql);

        return this.extraiListaContratos(conn, rs);
    }

    public Contrato extraiContratoResultSet(Connection conn, ResultSet rs) throws SQLException, ClassNotFoundException {
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Contrato contrato = new Contrato();
        
        while (rs.next()) {

            contrato.setIdContrato(rs.getInt("id_contrato"));
            contrato.setObjetoContrato(rs.getString("objetoContrato"));
            contrato.setEmpresaContratada(rs.getString("empresaContratada"));
            contrato.setAtivo(rs.getBoolean("ativo"));
            contrato.setDepartamentoResponsavel(rs.getString("departamentoResponsavel"));
            contrato.setOrcamentoComprometido(rs.getDouble("orcamentoComprometido"));
            int idFuncionarioGestor = rs.getInt("id_FuncionarioGestor");
            Usuario funcionarioGestor = usuarioDAO.findUsuario(conn, idFuncionarioGestor);
            contrato.setFuncionarioGestor(funcionarioGestor);
        
        }

        rs.close();

        return contrato;
    }

    private List<Contrato> extraiListaContratos(Connection conn, ResultSet rs) throws SQLException, ClassNotFoundException {

        List<Contrato> listaContratos = new ArrayList();

        while (rs.next()) {

            Contrato contrato = new Contrato();

            contrato.setIdContrato(rs.getInt("id_contrato"));
            contrato.setObjetoContrato(rs.getString("objetoContrato"));
            contrato.setEmpresaContratada(rs.getString("empresaContratada"));
            contrato.setAtivo(rs.getBoolean("ativo"));
            contrato.setDepartamentoResponsavel(rs.getString("departamentoResponsavel"));
            contrato.setOrcamentoComprometido(rs.getDouble("orcamentoComprometido"));
            int idFuncionarioGestor = rs.getInt("id_FuncionarioGestor");
            Usuario funcionarioGestor = usuarioDAO.findUsuario(conn, idFuncionarioGestor);
            contrato.setFuncionarioGestor(funcionarioGestor);
            
            listaContratos.add(contrato);

        }

        rs.close();

        return listaContratos;

    }

}

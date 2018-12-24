
import com.br.phdev.srs.daos.BasicDAO;
import com.br.phdev.srs.daos.GerenciadorDAO;
import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.jdbc.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class Teste {
    
    public static void main(String[] args) {
        try (Connection conexao = new FabricaConexao().conectar()){            
            GerenciadorDAO gerenciadorDAO = new GerenciadorDAO(conexao);
            System.out.println(GerenciadorDAO.getProximoIndex(conexao, BasicDAO.Tabela.item));
            //GerenciadorDAO.desalocarIndex(conexao, BasicDAO.Tabela.item, 2);
        } catch (SQLException e) {
            
        } catch (DAOException e) {
            if (e.getCause() instanceof SQLException) {                
                SQLException sqle = (SQLException) e.getCause();
                if (sqle.getSQLState().equals("45000")) {
                    System.err.println(sqle.getMessage());
                }                
            }
        }
    }
    
}

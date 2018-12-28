
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
        /*
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
        }*/
        
        
        String cpf = "02295260202";
        StringBuilder ultimosDigitos = new StringBuilder();
        int soma = 0;
        int fator = 10;
        for (int i=0; i<cpf.length()-2; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * fator--;
        }
        int resto = soma % 11;
        if (resto == 0 || resto == 1)
            ultimosDigitos.append(0);
        else
            ultimosDigitos.append((11 - resto));
        soma = 0;
        fator = 11;
        for (int i=0; i<cpf.length()-2; i++) {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * fator--;
        }
        soma += Integer.parseInt(String.valueOf(ultimosDigitos.toString())) * 2;
        resto = soma % 11;
        if (resto == 0 || resto == 1)
            ultimosDigitos.append(0);
        else
            ultimosDigitos.append((11 - resto));
        System.out.println("Ultimos digitos: " + ultimosDigitos.toString());
        
        /*
        String nome = "Paulo Henrique";
        for (char c : nome.toCharArray()) {                        
            if (!(((int) c > 64 && (int) c < 91) || ((int) c > 96 && (int) c < 122)) && (int)c != 32) {
                System.out.println(c);
                System.out.println("Nome errado");
            }
        }*/
    }
    
}

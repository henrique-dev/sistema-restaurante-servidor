
import com.br.phdev.srs.daos.BasicDAO;
import com.br.phdev.srs.daos.GerenciadorDAO;
import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.utils.ServicoAutenticacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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

    public static void main(String args[]) {
        System.out.println(new Timestamp(Calendar.getInstance(Locale.GERMAN).getTimeInMillis()));        
    }

}

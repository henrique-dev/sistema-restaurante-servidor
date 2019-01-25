
import com.br.phdev.srs.models.Cliente;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.Foto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.SocketAddress;

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

    public static void main(String args[]) throws JsonProcessingException, IOException {
        Socket socket = new Socket("35.202.51.59", 45578);        
    }

}

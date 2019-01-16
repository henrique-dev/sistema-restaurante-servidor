
import com.br.phdev.srs.models.Cliente;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.Foto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.MathContext;

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

    public static void main(String args[]) throws JsonProcessingException {
        Complemento c = new Complemento();
        Foto f = new Foto();
        f.setId(1);
        c.setNome("Limão");
        c.setPreco(3.50);
        c.setFoto(f);
        ObjectMapper m = new ObjectMapper();
        System.out.println(m.writeValueAsString(c));
    }

}

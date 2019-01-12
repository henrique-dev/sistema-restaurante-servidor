
import com.br.phdev.srs.models.Cliente;
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

    public static void main(String args[]) {
        Cliente c = new Cliente();
        c.setTelefone("96991100443");
        System.out.println(c.getCodigoAreaTelefone());
        System.out.println(c.getTelefoneSemCodigoArea());
    }

}

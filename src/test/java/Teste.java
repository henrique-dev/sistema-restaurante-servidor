
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
        double precoTotal = 2.65660;
        double desconto = 1;
        BigDecimal resultado = new BigDecimal(String.valueOf(precoTotal)).multiply(new BigDecimal(String.valueOf(desconto)));
        System.out.println(resultado);
        resultado = resultado.setScale(2, BigDecimal.ROUND_HALF_UP);        
        System.out.println(resultado);
    }

}

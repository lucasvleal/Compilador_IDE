
package analisador.lexico;

import java.io.File;


public class AnalisadorLexico {

    public static void main(String[] args) {
        String rotaLexer = "src\\analisador\\lexico\\Lexer.flex";
        gerarLexer(rotaLexer);
        UI novo = new UI();
        novo.setTitle("Leal & Luro IDE");
        novo.setLocationRelativeTo(null);
        novo.setVisible(true);
    }
    
    public static void gerarLexer (String rotaLexer){
        File arq = new File(rotaLexer);
        jflex.Main.generate(arq);
    }
}

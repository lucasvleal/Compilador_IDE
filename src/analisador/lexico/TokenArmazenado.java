
package analisador.lexico;

public class TokenArmazenado {
    public String token, valor;
    public int linha, coluna;
    
    public TokenArmazenado(){
        
    }

    public TokenArmazenado(String token, String valor, int linha, int coluna) {
        this.token = token;
        this.valor = valor;
        this.linha = linha;
        this.coluna = coluna;
    }
    
    
    
    
}

package analisador.lexico;

public class TokenOld {

    private String tipo;
    private String valor;
    private int colunaIni;
    private int colunaFim;
    private int linha;

    public TokenOld(String tipo, String valor, int linhaIni, int linhaFim, int coluna) {
        this.tipo = tipo;
        this.valor = valor;
        this.colunaIni = linhaIni;
        this.colunaFim = linhaFim;
        this.linha = coluna;
    }
   
    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getLinha() {
        return linha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public void setLinha(int coluna) {
        this.linha = coluna;
    }

    public int getColunaIni() {
        return colunaIni;
    }

    public int getColunaFim() {
        return colunaFim;
    }

    public void setColunaIni(int linhaIni) {
        this.colunaIni = linhaIni;
    }

    public void setColunaFim(int linhaFim) {
        this.colunaFim = linhaFim;
    }
    
    
    
   

}

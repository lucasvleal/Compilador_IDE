package analisador.lexico;

public class ComandoInterpretador {
    
    public int label;
    public String comando;
    public int info;

    public ComandoInterpretador() {
    }

    public ComandoInterpretador(int label, String comando, int info) {
        this.label = label;
        this.comando = comando;
        this.info = info;
    }
    
    
}

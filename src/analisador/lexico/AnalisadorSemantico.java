package analisador.lexico;

import java.util.ArrayList;


public class AnalisadorSemantico {

    public ArrayList <Variavel> varList = new ArrayList ();
    public ArrayList <TokenArmazenado> varListUndeclared = new ArrayList();
    
    public AnalisadorSemantico() {
    }

    public void setVarListUndeclared(ArrayList<TokenArmazenado> varListUndeclared) {
        this.varListUndeclared = varListUndeclared;
    }

    public void setVarList(ArrayList<Variavel> varList) {
        this.varList = varList;
    }
    
    public void printVar(){
        for(int i = 0; i < varList.size(); i++){
            System.out.println(varList.get(i).print());
        }
    }
    
    public void printVarUndeclared(){
        for(int i = 0; i < varListUndeclared.size(); i++){
            System.out.println("Variáveis não usadas: " + varListUndeclared.get(i).valor);
        }
    }
    
    
}

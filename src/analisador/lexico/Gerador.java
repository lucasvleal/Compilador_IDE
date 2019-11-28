
package analisador.lexico;

import java.util.ArrayList;

/**
 *
 * @author lukin
 */

public class Gerador {
    public ArrayList <TokenArmazenado> varList = new ArrayList();
    public ArrayList <String[]> varDec = new ArrayList();
    public ArrayList <String[]> varVal = new ArrayList();    
    public ArrayList <String> ladoEsq = new ArrayList();
    int auxMemoria = 0, auxLinha = 0, contIF = 0,  auxNADAW;
    int flagIFELSE;
    boolean flagAMEM, flagEXP, flagLEIT, flagIMPR, flagIF, flagNADA, flag1P, flagWHILE, flagBEIF;
    String gerado = "";
    String[] auxARMZ = new String[2];
    String[] auxIF = new String[7];

    public Gerador(ArrayList<TokenArmazenado> t) {
        varList = t;
    }
    
    public Gerador(){
        
    }
    
    public void setVarList(ArrayList<TokenArmazenado> varList) {
        this.varList = varList;
    }
    
    public String gerarCodigo(){        
        for(int i = 0; i < varList.size(); i++){
            TokenArmazenado tk = varList.get(i);
            
            if(tk.valor.equals(";") && flagAMEM == true){ //quando chega no ponto e virgula da linha de declaração muda a flag
                flagAMEM = false;
            }            

            if(flagAMEM){ //vai adicionando amem ate achar um ponto e virgula
                if(!tk.valor.equals(",")){
                    gera("AMEM", "1");
                    
                    String[] str = new String[2];
                    str[0] = tk.valor;
                    str[1] = Integer.toString(auxMemoria);                   
                    varDec.add(str);
                    ladoEsq.add(str[0]);
                    auxMemoria++;
                }
            } else if(flagEXP){
                TokenArmazenado tkProx = varList.get(i+1);
                TokenArmazenado tkProx2 = varList.get(i+2); 
                
                int flag = verificaVar(tk.valor); //ve o tipo da variavel q esta na primeira parte da expressao                          
                if(flag == 1){
                    gera("CRVL", buscaPosicMemo(tk.valor));                    
                } else if(flag == 0) {
                    gera("CRCT", tk.valor);
                } else {
                    String bool = tk.valor.equals("true") ? "1" : "0";
                    gera("CRCT", bool);
                }
                
                int flag2 = verificaVar(tkProx2.valor); //ve o tipo da variavel q esta na segunda parte da expressao                          
                if(flag2 == 1){
                    gera("CRVL", buscaPosicMemo(tkProx2.valor));
                } else if(flag2 == 0) {
                    gera("CRCT", tkProx2.valor);
                } else {
                    String bool = tkProx2.valor.equals("true") ? "1" : "0";
                    gera("CRCT", bool);
                }
                
                String op = verificaOp(tkProx.valor); //verifica qual é a op dps de ter empilhado as duas anteriores
                gera(op,"vazio");
                gera(auxARMZ[0], auxARMZ[1]);

                flagEXP = false;
            } else if(flagLEIT){
                if(!tk.valor.equals(")")){
                    if(!tk.valor.equals("(") && !tk.valor.equals(",")){
                        gera("ARMZ", buscaPosicMemo(tk.valor));
                    } 
                    
                    if(tk.valor.equals(",")){
                        gera("LEIT","vazio");
                    }
                } else {
                    flagLEIT = false;
                }
            } else if(flagIMPR){
                if(!tk.valor.equals(")")){
                    if(!tk.valor.equals("(") && !tk.valor.equals(",")){
                        gera("IMPR", "vazio");
                    }
                    
                    if(tk.valor.equals("(")){
                        TokenArmazenado tkProx = varList.get(i+1);
                        
                        int flag = verificaVar(tkProx.valor);                            
                        if(flag == 1){
                            gera("CRVL", buscaPosicMemo(tkProx.valor));                                
                        } else if(flag == 0) {
                            gera("CRCT", tkProx.valor);
                        } else {
                            String bool = tkProx.valor.equals("true") ? "1" : "0";
                            gera("CRCT", bool);
                        }
                    }
                    
                    if(tk.valor.equals(",")){
                        TokenArmazenado tkProx = varList.get(i+1);
                        
                        int flag = verificaVar(tkProx.valor);                            
                        if(flag == 1){
                            gera("CRVL", buscaPosicMemo(tkProx.valor));                                
                        } else if(flag == 0) {
                            gera("CRCT", tkProx.valor);
                        } else {
                            String bool = tkProx.valor.equals("true") ? "1" : "0";
                            gera("CRCT", bool);
                        }
                    }
                } else {
                    flagIMPR = false;
                }
            } else if(flagIF){ 
                if(!tk.valor.equals(")")){                   
                    if(tk.valor.equals("(")){                        
                        TokenArmazenado tkProx = varList.get(i+1);
                        TokenArmazenado tkProx2 = varList.get(i+2);

                        if(tkProx2.valor.equals(")")){
                            int flag = verificaVar(tkProx.valor);                            
                                if(flag == 1){
                                    gera("CRVL", buscaPosicMemo(tkProx.valor));                                
                                } else if(flag == 0) {
                                    gera("CRCT", tkProx.valor);
                                } else {
                                    String bool = tkProx.valor.equals("true") ? "1" : "0";
                                    gera("CRCT", bool);
                                }
                                
                            gera("DSVF","vazio");
                            flag1P = true;
                        }
                    } else {
                        String op = verificaOp(tk.valor);
                        String comp = verificaComp(tk.valor);
                        
                        if(op.equals("") && comp.equals("") && !flag1P){
                           int flag = verificaVar(tk.valor);                            
                            if(flag == 1){
                                gera("CRVL", buscaPosicMemo(tk.valor));                                
                            } else if(flag == 0) {
                                gera("CRCT", tk.valor);
                            } else {
                                String bool = tk.valor.equals("true") ? "1" : "0";
                                gera("CRCT", bool);
                            } 
                        } else {
                            if(!flag1P){
                                if(op.equals("")){
                                    auxIF[contIF++] = comp;                                
                                } else {
                                    auxIF[contIF++] = op;
                                }
                            }
                        }
                    }                   
                } else {
                    for(int j = auxIF.length - 1; j >= 0; j--){
                        if(auxIF[j] != null && !auxIF[j].equals("")){
                            gera(auxIF[j],"vazio");
                            auxIF[j] = "";   
                        }                        
                    }
                    
                    if(!flag1P) gera("DSVF","vazio");
                    
                    contIF = 0;                    
                    flagIF = false;
                    flagNADA = true;
                }               
            }
            
//            ================================================================
            
            else {            
                switch(tk.valor){
                    case "program" :
                        gera("INPP", "vazio");
                        break;

                    case "end" :
                        if(flagIFELSE == 1 || flagIFELSE == 2 || flagWHILE){
                            if(flagNADA){
                                if(flagIFELSE == 2 || flagWHILE) gera("DSVS","vazio");
                                gera("NADA","vazio");
                                flagNADA = false;

                                insereFimDSVF(auxLinha-1);
                                if(flagWHILE) insereFimDSVS(auxNADAW);
                                else insereFimDSVS(auxLinha-1);

                                flagIFELSE = 0;
                                if(flagWHILE) flagWHILE = false;
                            }                        
                        } else {
                            if(!flagBEIF){
                                TokenArmazenado tkPoint = varList.get(i+1);
                                if(tkPoint.valor.equals(".") || tkPoint.valor.equals(";")){
                                   gera("PARA", "vazio");
                                } else {
                                    gera("PARA", "Erro!");
                                }

                                printaSOUT();
                                return gerado;
                            } else {
                                flagBEIF = false;
                            }
                        }                       
                        
                        break;

                    case "int" :
                        flagAMEM = true;   
                        break;

                    case "boolean" :
                        flagAMEM = true;
                        break;
                        
                    case ":=" :
                        TokenArmazenado tkAnt = varList.get(i-1);
                        TokenArmazenado tkProx = varList.get(i+1);
                        TokenArmazenado tkProx2 = varList.get(i+2);
                                                
                        if(tkProx2.valor.equals(";") || tkProx2.valor.equals("end")){
                            int flag = verificaVar(tkProx.valor);
                            
                            if(flag == 1){
                               gera("CRVL", buscaPosicMemo(tkProx.valor));                                
                            } else if(flag == 0) {
                               gera("CRCT", tkProx.valor);
                            } else {
                                String bool = tkProx.valor.equals("true") ? "1" : "0";
                                gera("CRCT", bool);
                            }                          
                            
                            String qual = buscaPosicMemo(tkAnt.valor);
                            gera("ARMZ", qual); 
                        } else {
                            flagEXP = true;
                            auxARMZ[0] = "ARMZ";
                            auxARMZ[1] = buscaPosicMemo(tkAnt.valor);
                        }
                        
                        break;
                        
                    case "read":
                        gera("LEIT","vazio"); 
                        flagLEIT = true;
                        break;
                        
                    case "write":                        
                        flagIMPR = true;
                        break;
                        
                    case "if":     
                        flagIF = true;
                        flagIFELSE = 1;                         
                        break;
                        
                    case "begin":
                        if(flagIFELSE != 0 || flagWHILE){
                            flagBEIF = true;
                        }
                        break;
                        
                    case ";":
                        if(flagIFELSE != 0 || flagWHILE){
                            if(flagNADA){
                                if(flagIFELSE == 2 || flagWHILE) gera("DSVS","vazio");
                                gera("NADA","vazio");
                                flagNADA = false;

                                insereFimDSVF(auxLinha-1);
                                if(flagIFELSE == 2) insereFimDSVS(auxLinha-1);
                                if(flagWHILE) insereFimDSVS(auxNADAW);
                                
                                flagIFELSE = 0;
                                if(flagWHILE) flagWHILE = false;
                            }
                        }                        
                        break;
                    
                    case "else":                         
                        flagIFELSE = 2;
                        flagNADA = true;
                        break;
                    
                    case "while":
                        auxNADAW = auxLinha;
                        gera("NADA","vazio");
                        
                        flagIF = true;
                        flagWHILE = true;
                        break;                        
                   
                        
                }
            }
        }
        
        return gerado;
    }
    
    public void gera(String cod, String opc){
        String label = Integer.toString(auxLinha);
        
        opc = cod.equals("DSVF") || cod.equals("DSVS") ?  "x" : opc;
        
        if(cod != null){ 
            if(opc.equals("vazio")){
                gerado += label + " " + cod + "\n";
            } else {
                gerado += label + " " + cod + " " + opc + "\n";
            }
        }

        auxLinha++;
    }
    
    public String buscaPosicMemo(String str){
        String result = "";
        
        for(int i = 0; i < varDec.size(); i++) {
            String[] aux = varDec.get(i);
            
            if(aux[0].equals(str)){
                result = aux[1];
            }
        }
        
        if(result.equals("")){
            result = "ERRO!";
        }
        
        return result;
    }
    
    public void insereFimDSVF(int numLinha){
        String newGerado = "";
        String[] linha = gerado.split("\n");
        
        for(int i = 0; i < linha.length; i++){
            String[] coluna = linha[i].split(" ");
            
            if(coluna.length == 3){
                if(coluna[1].equals("DSVF")){
                    if(coluna[2].equals("x")) coluna[2] = Integer.toString(numLinha);
                }

                linha[i] = coluna[0] + " " + coluna[1] + " " + coluna[2];
            } else {
                linha[i] = coluna[0] + " " + coluna[1];
            }
            
            newGerado += linha[i] + "\n";
        }
        
        gerado = newGerado;
    }
    
    public void insereFimDSVS(int numLinha){
        String newGerado = "";
        String[] linha = gerado.split("\n");
        int qnts = 0;
        
        for(int i = 0; i < linha.length; i++){
            String[] coluna = linha[i].split(" ");
            
            if(coluna.length == 3){
                if(coluna[1].equals("DSVS")){
                    qnts++;
                    if(coluna[2].equals("x")) coluna[2] = Integer.toString(numLinha);
                }

                linha[i] = coluna[0] + " " + coluna[1] + " " + coluna[2];
            } else {
                linha[i] = coluna[0] + " " + coluna[1];
            }
            
            newGerado += linha[i] + "\n";
        }
        
        if(qnts > 2){
            String[] linha2 = newGerado.split("\n");
        
            for(int i = 0; i < linha2.length; i++){
                String[] coluna = linha2[i].split(" ");

                if(coluna.length == 3){
                    if(coluna[1].equals("DSVS")){
                        coluna[2] = Integer.toString(numLinha);
                    }

                    linha[i] = coluna[0] + " " + coluna[1] + " " + coluna[2];
                } else {
                    linha[i] = coluna[0] + " " + coluna[1];
                }

                newGerado += linha[i] + "\n";
            }
        }
        
        gerado = newGerado;
    }
    
    public void corrigeParada(){
        String newGerado = "";
        String[] linha = gerado.split("\n");
        boolean flag = false;
        
        for(int i = 0; i < linha.length; i++){
            String[] coluna = linha[i].split(" ");
            
            if(coluna.length == 3){                
                linha[i] = coluna[0] + " " + coluna[1] + " " + coluna[2];
                newGerado += linha[i] + "\n";
            } else {
                if(coluna[1].equals("PARA")){
                    if(i+1 != linha.length){
                        flag = true;
                    }                
                }
                
                if(!flag) {
                   linha[i] = coluna[0] + " " + coluna[1];  
                   newGerado += linha[i] + "\n";
                }                
            }
            
            flag = false;
        }
        
        gerado = newGerado;
    }
    
    public int buscaValor(String str){
        int resp = -1;
        
        for(int k = 0; k < varVal.size(); k++){
            String[] aux = varVal.get(k);
            
            if(aux[0].equals(str)){
                resp = Integer.parseInt(aux[1]);              
            }
        }
        
        return resp;
    }
    
    public int verificaVar(String str){
        int result = 0;
        
        if(str.equals("true") || str.equals("false")){
            result = 2;
            return result;
        }
        
        for(int i = 0; i < ladoEsq.size(); i++) {
            String aux = ladoEsq.get(i);
            
            if(aux.equals(str)){
                result = 1;
            }
        }
        
        if(result != 1){
            result = 0;
        }
        
        return result;
    }
    
    public String verificaOp(String str){
        String result = "";
        
        if(str.equals("+")) result = "SOMA";
        if(str.equals("-")) result = "SUBT";
        if(str.equals("*")) result = "MULT";
        if(str.equals("div")) result = "DIVI";
        if(str.equals("and")) result = "CONJ";
        if(str.equals("or")) result = "DISJ";
        if(str.equals("not")) result = "NEGA";
        
        return result;
    }
    
    public String verificaComp(String str){
        String result = "";
        
        if(str.equals(">")) result = "CMMA";
        if(str.equals("<")) result = "CMME";
        if(str.equals(">=")) result = "CMAG";
        if(str.equals("<=")) result = "CMEG";
        if(str.equals("<>")) result = "CMDG";
        if(str.equals("==")) result = "CMIG";
        
        return result;
    }
    
    public void printaSOUT(){
        System.out.println("====================");
        System.out.println(gerado);
        System.out.println("====================");
    }
}

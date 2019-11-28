package analisador.lexico;

import java.util.ArrayList;

public class AnalisadorSintatico {

    private ArrayList<TokenArmazenado> tokens = new ArrayList();
    int i;
    boolean ladoEsquerdo;
    int ic;
    String tipoRW;
    Variavel varGlobal;
    TokenArmazenado procGlobal;
    ArrayList<Variavel> varList = new ArrayList();
    ArrayList<TokenArmazenado> varListUndeclared = new ArrayList();
    String indicador = "global";
    String indicador_tipo = "";
    ArrayList<String> erros = new ArrayList();
    ArrayList<String> errosSemanticos = new ArrayList();
    TokenArmazenado a = new TokenArmazenado();
    Boolean status = true;
    String[] programa = {"RESERVADO_PROGRAM"};
    String[] identificador = {"IDENTIFICADOR"};
    String[] sinal_ponto_virgula = {"SINAL_PONTO_VIRGULA"};
    String[] declaracao_var = {"RESERVADO_INT", "RESERVADO_BOOLEAN"};
    String[] sub_rotinas = {"RESERVADO_PROCEDURE"};
    String[] comando_composto = {"BEGIN"};
    String[] sinal_virgula = {"SINAL_VIRGULA"};
    String[] abre_parent = {"ABRE_PARENT"};
    String[] fecha_parent = {"FECHA_PARENT"};
    String[] reservado_var = {"RESERVADO_VAR"};
    String[] tipo_var = {"ATRIB_VARIAVEL"};
    String[] relacao = {"RESERVADO_DIF","RESERVADO_MENOR","RESERVADO_MAIOR","RESERVADO_MAIOR_IGUAL","RESERVADO_MENOR_IGUAL","RESERVADO_IGUAL"};
    String[] reservado_not = {"RESERVADO_NOT"};
    String[] sinal_op = {"SINAL_SOMA","SINAL_SUB"};
    String[] reservado_or = {"RESERVADO_OR"};
    String[] sinal_op_mult = {"RESERVADO_AND","RESERVADO_DIV","SINAL_MULT"};
    String[] begin = {"RESERVADO_BEGIN"};
    String[] end = {"RESERVADO_END"};
    String[] sinal_atrib = {"SINAL_ATRIB"};
    String[] num_int = {"NUM_INT"};
    String[] reservado_if = {"RESERVADO_IF"};
    String[] reservado_else = {"RESERVADO_ELSE"};
    String[] reservado_then = {"RESERVADO_THEN"};
    String[] reservado_fim = {"RESERVADO_FIM_PROGRAMA"};
    String[] reservado_while = {"RESERVADO_WHILE"};
    String[] reservado_do = {"RESERVADO_DO"};
    String[] reservado_true = {"RESERVADO_TRUE"};
    String[] reservado_false = {"RESERVADO_FALSE"};
    String[] reservado_read_write = {"RESERVADO_READ", "RESERVADO_WRITE"};
    String[] ponto_seguro = {"RESERVADO_IDENTIFICADOR", "RESERVADO_READ", "RESERVADO_WRITE", "RESERVADO_BEGIN","RESERVADO_IF", "RESERVADO_WHILE"};
    Boolean ccontrol = false;
    
    
    public AnalisadorSintatico(ArrayList<TokenArmazenado> t) {
            tokens = t;
            i = 0;
    }
    
    public Boolean Iniciar (){
        
        Proximo();
        status = Regra(0, programa);
        if(!status) return false;
        Proximo();
        status = Regra(0, identificador);
        if(!status) return false;
        Proximo();
        status = Regra(0, sinal_ponto_virgula);
        if(!status) return false;
        status = Bloco();
        if(!status) return false;
        Proximo();
        status = Regra(0, reservado_fim);
        if(!status) return false;
        Proximo();
        if(a.token.equals("VAZIO")) return status;
        else return false;
    }
    
    public Boolean Bloco(){
        
        Proximo();
        //Caso parte declaração de variáveis
        while(status){
            status = Regra(1,declaracao_var);
            indicador_tipo = a.valor;
            if(!status){
                status = true;
                break;
            }
            status = ListaVar();
            if(!status){
                return false;
            }
            Proximo();
        }
        // caso declaração de sub rotinas
        while(status){
            status = Regra(1,sub_rotinas);
            if(!status){
                status = true;
                break;
            }
            Proximo();
            status = Regra(0, identificador);
            if(!status) return false;
            Variavel v = new Variavel(a.valor, "PROCEDURE", indicador);
            varList.add(v);
            indicador = a.valor;
            status = ListaParam();
            if(!status) return false;
            Proximo();
            status = Regra(0, sinal_ponto_virgula);
            if(!status) return false;
            status = Bloco();
            if(!status) return false;
            Proximo();
        }
        indicador = "global";
        //caso comando composto *FAZER*
        status = ComandoComposto();
        if(!status) return false;
  
        
    return status;
    }
    
    public boolean ComandoComposto(){
        Boolean status;
        
        status = Regra(0,begin);
        if(!status) return false;
        status = Comando();
        if(!status) {
            erros.add("Token " + a.token + " inesperado na linha: "+ a.linha + " e coluna: " + a.coluna);
            status = RecuperarPontoSeguro();
            if (!status) return false;
        }
        if(Regra(1, sinal_ponto_virgula)){
            status = Comando();
            if(!status){
                if(erros.isEmpty()){
                    erros.add("';' inesperado. Linha:" + a.linha + " Coluna:" + a.coluna);
                    return false;
                }
                else {
                    status = RecuperarPontoSeguro();
                    if (!status) return false;
                }
            }
            while(Regra(1, sinal_ponto_virgula)){
                 status = Comando();
                 if(!status){
                    status = RecuperarPontoSeguro();
                    if (!status) return false;
                 }
            }
            
        }
        status = Regra(0, end);
        if(!status) return false;
        
        return true;
    }
    
    public boolean RecuperarPontoSeguro(){
       
        while(!Regra(1,sinal_ponto_virgula)){
            Proximo();
            if(Regra(1,reservado_fim)) return false;
        }
        
        return true;
    }
    
    public boolean Comando(){
        
            Proximo();
            if(ReadOrWrite()){
                return true;
            }
            if(Atribuicao()){
                return true;
            }
            else if(ComandoComposto()){
                Proximo();
                return true;
            }
            else{
                erros.remove(erros.size()-1);
                
                if(ComandoCondicional()){ 
                    return true;
                }
                else if(ComandoRepetitivo()){
                    return true;
                }
            }
        //erros.clear();
        //erros.add("Comando esperado na linha:" + a.linha +" Coluna:" + a.coluna);
        return false;
    }
    
    public boolean ReadOrWrite(){
        
        if(Regra(1, reservado_read_write) ){
            tipoRW = "";
            int var = 0;
            Proximo();
            if(Regra(0,abre_parent)){
                Proximo();
                if(Regra(0, identificador)){
                    
                    for(int j = 0; j < varList.size(); j++){
                        if(varList.get(j).nome.equals(a.valor)){
                            tipoRW = varList.get(j).tipo;
                            var ++;
                        }
                    }
                    if(var == 0){
                        varListUndeclared.add(a);
                    }
                    
                    Proximo();
                    if(Regra(1,sinal_virgula)){
                        Proximo();
                        while(Regra(0, identificador)){
                             for(int j = 0; j < varList.size(); j++){
                                if(varList.get(j).nome.equals(a.valor)){
                                    if(tipoRW.equals(varList.get(j).tipo)){
                                        var ++;
                                    }
                                    else{
                                        errosSemanticos.add("Tipos conflitantes nos parametros do Read/Write na linha: " + a.linha + " coluna: " + a.coluna);
                                    }
                                }
                            }
                            if(var == 0){
                                varListUndeclared.add(a);
                            }

                            Proximo();
                            if(Regra(1,sinal_virgula)) Proximo();
                            else break;
                        }
                    }
                    
                    if(!Regra(0,fecha_parent)) return false;
                    Proximo();
                } 
                else return false;
            }
            else return false;
        }
        else return false;
        return true;
    }
    
    public boolean ComandoRepetitivo(){
        
        if(!Regra(1,reservado_while)) return false;
        if(!Expressao()) return false;
        if(a.token.equals("FECHA_PARENT")){
           erros.add("')' inesperado na linha: " + a.linha + " coluna: " + a.coluna);
           Proximo();
        }
        if(!Regra(0,reservado_do)) return false;
        if(!Comando()) return true;
        return true;
    }
   
    public boolean ComandoCondicional(){
        
        if(!Regra(1,reservado_if)) return false;
        if(!Expressao()) return false;
        if(a.token.equals("FECHA_PARENT")){
            erros.add("')' inesperado na linha: " + a.linha + " coluna: " + a.coluna);
            Proximo();
        }
        if(!Regra(0,reservado_then)) return false;
        if(!Comando()) return false;
        if(Regra(1,reservado_else)){
            if(!Comando()) return false;
        }
        
        return true;
    }
    
    public boolean Atribuicao(){
        ladoEsquerdo = true;
        if(!Variavel()) return false;
        procGlobal = a;
        Proximo();
        if(Regra(1,abre_parent)){
            int ver = 0;
            int ver1 = 0;
            int contadorParam = 0;
            Proximo();
            if(Regra(1,identificador)){
                for(int j= 0; j < varList.size(); j++){
                    if(a.valor.equals(varList.get(j).nome)){
                        ver = 1;
                        
                        while(contadorParam < varList.size()){
                            if(varList.get(contadorParam).indicador.equals(procGlobal.valor + "_param")){
                                if(varList.get(j).tipo.equals(varList.get(contadorParam).tipo)){
                                    ver1 = 1;
                                    contadorParam++;
                                    break;
                                }
                                else{
                                    errosSemanticos.add("Tipos conflitantes nos parametros da chamada da procedure '" + procGlobal.valor + "' na linha: " + procGlobal.linha + " coluna: " + procGlobal.coluna);
                                    ver1 = 1;
                                    contadorParam++;
                                    break;
                                }
                            }
                            contadorParam++;
                        }
                        if(ver1 == 0) errosSemanticos.add("Muitos parametros na chamada da procedure '"+ procGlobal.valor + "' na linha: " + procGlobal.linha + " coluna: " + procGlobal.coluna);
                        
                    }
                }
                if(ver == 0) errosSemanticos.add("Váriavel '" + a.valor + "' não declarada na chamada de procedimento da linha: " + a.linha + " coluna: " + a.coluna);
                Proximo();
                
            }
            while(Regra(1, sinal_virgula)){
                Proximo();
                if(!Regra(0, identificador)) return false;
                ver = 0;
                ver1 = 0;
                for(int j= 0; j < varList.size(); j++){
                    if(a.valor.equals(varList.get(j).nome)){
                        ver = 1;
                        
                        while(contadorParam < varList.size()){
                            if(varList.get(contadorParam).indicador.equals(procGlobal.valor + "_param")){
                                if(varList.get(j).tipo.equals(varList.get(contadorParam).tipo)){
                                    ver1 = 1;
                                    contadorParam++;
                                    j = varList.size();
                                    break;
                                }
                                else{
                                    errosSemanticos.add("Tipos conflitantes nos parametros da chamada da procedure '" + procGlobal.valor + "' na linha: " + procGlobal.linha + " coluna: " + procGlobal.coluna);
                                    ver1 = 1;
                                    contadorParam++;
                                    j = varList.size();
                                    break;
                                }
                            }
                            contadorParam++;
                        }
                        if(ver1 == 0) errosSemanticos.add("Muitos parametros na chamada da procedure '"+ procGlobal.valor + "' na linha: " + procGlobal.linha + " coluna: " + procGlobal.coluna);
                        
                    }
                }
                if(ver == 0) errosSemanticos.add("Váriavel '" + a.valor + "' não declarada na chamada de procedimento da linha: " + a.linha + " coluna: " + a.coluna);
                Proximo();
            }
            
            while(contadorParam < varList.size()){
                if(varList.get(contadorParam).indicador.equals(procGlobal.valor + "_param")){
                    errosSemanticos.add("Faltando váriaveis na chamada de procedimento '" + procGlobal.valor + "' na linha: '" + procGlobal.linha + " coluna: " + procGlobal.coluna);
                    break;
                }
                contadorParam++;
            }
            
            if(!Regra(0, fecha_parent)) return false;
            Proximo();
            return true;
        }
        else if(!Regra(0, sinal_atrib)) return false;
        ladoEsquerdo = false;
        if(!Expressao()){
            erros.add("Faltando complemento para atribuição na linha: " + a.linha + " coluna: "+ a.coluna);
            return false;
        }
        return true;
    } 
    
    public boolean Expressao(){
        Proximo();
        if(!ExpressaoSimples()) return false;
        if(Regra(1, relacao)){
            if(!Expressao()) return false;
        }      
        return true;
    }
    
    public boolean ExpressaoSimples(){
        if(Regra(1, sinal_op)) Proximo();
        if(!Termo()) return false;
        while(true){
            if((Regra(1,sinal_op)||Regra(1,reservado_or))){
                Proximo();
                if(!Termo()) return false;
            }
            else break;
        }
        
        return true;
    }
    
    public boolean Termo(){
        if(!Fator()) return false;
        Proximo();
        while(true){
            if(Regra(1,sinal_op_mult)){
                Proximo();
                if(!Fator()) return false;
                Proximo();
            }
            else break;
        }
        
        return true;
    }
    
    public boolean Fator(){
        if(Variavel()) return true;
        else if(Num()) return true;
        else if(ParExp()) return true;
        else{
            if(!Regra(1,reservado_not)) return false;
            Proximo();
            if(!Fator())return false;
            return true;
        }
    }
    
    public boolean ParExp(){
        if(!Regra(1,abre_parent))return false;
        if(!Expressao()) return false;
        if(!Regra(0,fecha_parent))return false;
        return true;
    }
    
    public boolean Num(){
        if(Regra(1, num_int)){
            if(varGlobal != null){
                if(!ladoEsquerdo){
                    if(varGlobal.tipo.equals("boolean")){
                        errosSemanticos.add("Tentativa de operação com inteiros na váriavel '" + varGlobal.nome + "' do tipo boolean!");
                    }
                }
            }
            
            return true;
        }
        else return false;
    }
    
    public boolean Variavel(){
        if(Regra(1,identificador) || Regra(1, reservado_true) || Regra(1, reservado_false)){
            if(Regra(1,identificador)){
                
                for(int cont = 0; cont < varList.size(); cont++){
                    if(varList.get(cont).nome.equals(a.valor)){
                        
                        if(varList.get(cont).indicador.equals(indicador)){
                            varList.get(cont).setUsado(true);
                            if(ladoEsquerdo) varGlobal = varList.get(cont); 
                        }
                        else if(varList.get(cont).indicador.equals("global")){
                            varList.get(cont).setUsado(true);
                            if(ladoEsquerdo) varGlobal = varList.get(cont); 
                        }
                        else{
                            errosSemanticos.add("Váriavel '" + a.valor + "' está sendo usada fora do escopo!" );
                            varGlobal = new Variavel(a.valor, a.token, " ");
                            for(int j = 0; j < varListUndeclared.size(); j++){
                                if(varListUndeclared.get(j).valor.equals(a.valor)) varListUndeclared.remove(j);
                            }
                            return true;
                        }
                        return true;
                    }
                }
                varListUndeclared.add(a);
                if(ladoEsquerdo) varGlobal = null;
            }
            else{
                if(!ladoEsquerdo){
                    if(varGlobal != null) if(varGlobal.tipo.equals("boolean")) varGlobal.setValor(a.valor);
                    else errosSemanticos.add("Tentativa de operação com boolean na váriavel '" + varGlobal.nome + "' do tipo int!");
                }
            }
            return true;
        }
        else return false;
    }
   
    public boolean ListaParam(){
        Boolean aux = true;
        Proximo();
        aux = Regra(0, abre_parent);
        if(!aux) return false;
        Proximo();
        aux = (Regra(1,reservado_var) || Regra(1, identificador));
        indicador_tipo = a.valor;
        if(aux){
            aux = ListaVarVar();
            if(!aux) return false;
            aux = Regra(0, tipo_var);
            if(!aux) return false;
            Proximo();
            aux = Regra(0, declaracao_var);
            for(int m = 0; m < varList.size();m++){
                if(varList.get(m).getTipo().equals("var")){
                    varList.get(m).setTipo(a.valor);
                }
            }
            if(!aux) return false;
            Proximo();
            aux = Regra(0, fecha_parent);
            if(!aux) return false;
        }
        else{
            aux = Regra(0, fecha_parent);
            if(!aux) return false;
        }
        return true;
    }
    
    public boolean ListaVarVar(){
        Boolean aux = true;
        int ver = 0;
        Variavel v;
        if(a.token.equals("RESERVADO_VAR")){
            Proximo();
            aux = Regra(0,identificador);
            if(!aux) return false;
            v = new Variavel(a.valor, indicador_tipo, indicador + "_param");
            for(int it = 0; it < varList.size(); it++){
                if(varList.get(it).Igual(v)){
                    errosSemanticos.add("Váriavel '" + v.getNome() + "' já declarada neste escopo!");
                    ver ++;
                } 
            }
            if (ver == 0) varList.add(v);
            Proximo();
            aux = Regra(1,sinal_virgula);
            if(aux){
                Proximo();
                if(a.token.equals("RESERVADO_VAR")) Proximo();
                while(Regra(0,identificador)){
                    v = new Variavel(a.valor, indicador_tipo, indicador + "_param");
                    ver = 0;
                    for(int it = 0; it < varList.size(); it++){
                        if(varList.get(it).Igual(v)){
                            errosSemanticos.add("Váriavel " + v.getNome() + " já declarada neste escopo!");
                            ver ++;
                        } 
                    }
                    if (ver == 0) varList.add(v);
                    Proximo();
                    aux = Regra(1,sinal_virgula);
                    if(!aux) break;
                    Proximo();
                    if(a.token.equals("RESERVADO_VAR")) Proximo();
                }
                if(aux) return false;
            }
        }
        else{
            Proximo();
            aux = Regra(1,sinal_virgula);
            if(aux){
                Proximo();
                if(a.token.equals("RESERVADO_VAR")) Proximo();
                while(Regra(0,identificador)){
                    v = new Variavel(a.valor, indicador_tipo, indicador + "_param");
                    ver = 0;
                    for(int it = 0; it < varList.size(); it++){
                        if(varList.get(it).Igual(v)){
                            errosSemanticos.add("Váriavel " + v.getNome() + " já declarada neste escopo!");
                            ver ++;
                        } 
                    }
                    if (ver == 0) varList.add(v);
                    Proximo();
                    aux = Regra(1,sinal_virgula);
                    if(!aux) break;
                    Proximo();
                    if(a.token.equals("RESERVADO_VAR")) Proximo();
                }
                if(aux) return false;
            }
        }
        return true;
    }
    
    public boolean ListaVar(){
        Boolean aux = true;
        int ver = 0;
        Variavel v;
        Proximo();
        aux = Regra(0,identificador);
        if(!aux) return false;
        v = new Variavel(a.valor, indicador_tipo, indicador);
        for(int it = 0; it < varList.size(); it++){
           if(varList.get(it).Igual(v)){
               errosSemanticos.add("Váriavel " + v.getNome() + " já declarada neste escopo!");
               ver ++;
           } 
        }
        if (ver == 0) varList.add(v);
        Proximo();
        aux = Regra(1,sinal_virgula);
        if(aux){
            Proximo();
            while(Regra(0,identificador)){
                v = new Variavel(a.valor, indicador_tipo, indicador);
                ver = 0;
                for(int it = 0; it < varList.size(); it++){
                    if(varList.get(it).Igual(v)){
                        errosSemanticos.add("Váriavel " + v.getNome() + " já declarada neste escopo!");
                        ver ++;
                    } 
                }
                if (ver == 0) varList.add(v);
                Proximo();
                aux = Regra(1,sinal_virgula);
                if(!aux) break;
                Proximo();
            }
            if(aux) return false;
        }
        aux = Regra(0,sinal_ponto_virgula);
        if(!aux) return false;
        
        return aux;
    }
    
    public boolean Regra(int opcional, String[] op){
        String erro = "Faltando ";
        if(a.token.equals("VAZIO")){
            
            for(int i = 0; i < op.length; i++){
                erro += " '" + op[i] +"'";
            }
            erro += " na linha:" + a.linha + " linha:" + a.coluna;
            if(opcional == 0)erros.add(erro);
            return false;
        }
        for(int i = 0; i < op.length; i++){
            if(a.token.equals(op[i])){
                return true;
            }
        }
        
        for(int i = 0; i < op.length; i++){
            erro += " '" + op[i] +"'";
        }
        erro += " na linha:" + a.linha + " coluna:" + a.coluna;
        if(opcional == 0)erros.add(erro);
       
        return false;
        
    }
    
    public void Proximo(){
        if(i < tokens.size()){
            a = tokens.get(i);
            i++;
        }
        else{
            a.token = "VAZIO";
        }
    }
       
    public String Erros(){
        
        String erro = "";
        
        for(int j = 0; j < erros.size(); j++){
            erro = erro + erros.get(j) + "\n";
        }
        
        return erro;
    }
    
    public String ErrosS(){
        
        String erro = "";
        
        for(int j = 0; j < varListUndeclared.size(); j++){
           erro = "Aviso: Váriavel '" + varListUndeclared.get(j).valor + "' não declarada! \n";
        }
                
        for(int j = 0; j < errosSemanticos.size(); j++){
            erro = erro +"Aviso: " + errosSemanticos.get(j) + "\n";
        }
        
        return erro;
    }
    
    public void CheckPoint(){
        if(!ccontrol){
            ic = i-1;
            ccontrol = true;
        }
    }
    
    public void Backup(){
        a = tokens.get(ic);
        i = ic + 1;
        ccontrol = false;
    }
     
    public ArrayList<Variavel> returnVars(){
        return varList;
    }
    
    public ArrayList<TokenArmazenado> returnVarsUndeclared(){
        return varListUndeclared;
    }
    
 }

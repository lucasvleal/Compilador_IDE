
package analisador.lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class InterpretadorUI extends javax.swing.JFrame {
    
    public ArrayList<ComandoInterpretador> comandos = new ArrayList();
    ComandoInterpretador atual;
    public int[] D = new int[500];
    public int s;
    public int sc;
    public int i;
    public int auxint;
    public String auxString;
    

    public InterpretadorUI() {
        initComponents();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        logTxt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logTxt.setEditable(false);
        logTxt.setBackground(new java.awt.Color(0, 0, 0));
        logTxt.setColumns(20);
        logTxt.setForeground(new java.awt.Color(255, 255, 255));
        logTxt.setRows(5);
        jScrollPane1.setViewportView(logTxt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterpretadorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterpretadorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterpretadorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterpretadorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterpretadorUI().setVisible(true);
            }
        });
    }
    
    public void run() throws FileNotFoundException, IOException{
        
        JFileChooser file = new JFileChooser();
        file.setDialogTitle("Selecionar arquivo: ");
        
        int response = file.showOpenDialog(this);
        
        if(response == JFileChooser.APPROVE_OPTION){
            File arq = file.getSelectedFile();
            String path = arq.getPath();
    
        BufferedReader in = new BufferedReader(new FileReader(path));
        String read = in.readLine();
        
        while(read != null){
            String []data = read.split(" ");
            ComandoInterpretador c = null;
            
            if(data.length == 2){
                c = new ComandoInterpretador(Integer.valueOf(data[0]), data[1], -1);
            }
            else if(data.length == 3){
                c = new ComandoInterpretador(Integer.valueOf(data[0]), data[1], Integer.valueOf(data[2]));
            }
            
            comandos.add(c);
            read = in.readLine();
        }
        
        /*for(int j = 0; j < comandos.size(); j++){
           System.out.println("Comando " + j + ": " + comandos.get(j).label + " " + comandos.get(j).comando + " " + comandos.get(j).info);
        }*/
        
        atual = comandos.get(0);
        
        while(!atual.comando.equals("PARA")){
            
            switch (atual.comando){
                case "INPP":
                    i = 1;
                    s = 100;
                    break;
                case "AMEM":
                    sc++;
                    break;
                case "DMEM":
                    sc--;
                    break;
                case "LEIT":
                    s++;
                    logTxt.setText(logTxt.getText() + "Insira um valor inteiro: \n");
                    D[s] = Integer.valueOf(JOptionPane.showInputDialog(this,""));
                    logTxt.setText(logTxt.getText() + D[s] + "\n");
                    break;
                case "IMPR":
                    logTxt.setText(logTxt.getText() + D[s] +"\n");
                    break;
                case "IMPE":
                    logTxt.setText(logTxt.getText() +"\n");
                    break;
                case "NADA":
                    break;
                case "DSVF":
                    if(D[s] == 0) i = atual.info;
                    break;
                case "DSVS":
                    i = atual.info;
                    break;
                case "CRCT":
                    s++;
                    D[s] = atual.info;
                    break;
                case "CRVL":
                    s++;
                    D[s] = D[atual.info];
                    break;
                case "ARMZ":
                    D[atual.info] = D[s];
                    s--;
                    break;
                case "SOMA":
                    D[s-1] = D[s-1] + D[s];
                    s--;
                    break;
                case "SUBT":
                    D[s-1] = D[s-1] - D[s];
                    s--;
                    break;
                case "MULT":
                    D[s-1] = D[s-1] * D[s];
                    s--;
                    break;
                case "DIVI":
                    D[s-1] = (int)D[s-1]/D[s];
                    s--;
                    break;
                case "INVR":
                    D[s] = -D[s];
                    break;
                case "CONJ": 
                    if(D[s-1]==1 && D[s]==1) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break;
                case "DISJ":
                    if(D[s-1]==1 || D[s]==1) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break;
                case "NEGA":
                    D[s] = 1 - D[s];
                    break;
                case "CMME":
                    if(D[s-1] < D[s]) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break;
                case "CMMA":
                    if(D[s-1] > D[s]) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break;
                case "CMIG":
                    if(D[s-1] == D[s]) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break;
                case "CMDG":
                    if(D[s-1] != D[s]) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break;
                case "CMAG":
                    if(D[s-1] >= D[s]) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break;
                case "CMEG":
                    if(D[s-1] <= D[s]) D[s-1] = 1;
                    else D[s-1] = 0;
                    s--;
                    break; 
            }
            
            atual = comandos.get(i);
            i++;
        }
        
        logTxt.setText(logTxt.getText() + "\nExecução encerrada com sucesso!");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logTxt;
    // End of variables declaration//GEN-END:variables
}

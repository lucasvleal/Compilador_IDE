/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giova
 */
public class UI extends javax.swing.JFrame {
   int linhaT = 0;
   Gerador gc = new Gerador();
    /**
     * Creates new form UI
     */
    public UI() {
        initComponents();
        lblArqEscolhido.setVisible(false);
        TextLineNumber contadorLinhas = new TextLineNumber(codigoTxt);
        jScrollPane1.setRowHeaderView(contadorLinhas);    
  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        codigoTxt = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        lblArq = new javax.swing.JLabel();
        lblArqEscolhido = new javax.swing.JLabel();
        scroll = new javax.swing.JScrollPane();
        tableLog = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        cTxtLogSintatico = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        abrirMenu = new javax.swing.JMenu();
        menuArquivo = new javax.swing.JMenuItem();
        compilarMenu = new javax.swing.JMenu();
        gerarCodMenu = new javax.swing.JMenu();
        interpretadorMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        codigoTxt.setColumns(20);
        codigoTxt.setRows(5);
        jScrollPane1.setViewportView(codigoTxt);

        jLabel1.setText("Log Lexico:");

        lblArq.setText("Escreva ou selecione:");

        tableLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TIPO", "LEXEMA", "LINHA", "COLUNA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableLog.getTableHeader().setReorderingAllowed(false);
        scroll.setViewportView(tableLog);

        cTxtLogSintatico.setColumns(20);
        cTxtLogSintatico.setRows(5);
        jScrollPane2.setViewportView(cTxtLogSintatico);

        jLabel2.setText("Log Sintatico / Semantico:");

        abrirMenu.setText("Arquivo");

        menuArquivo.setText("Abrir");
        menuArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArquivoActionPerformed(evt);
            }
        });
        abrirMenu.add(menuArquivo);

        jMenuBar1.add(abrirMenu);

        compilarMenu.setText("Compilar");
        compilarMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                compilarMenuMouseClicked(evt);
            }
        });
        compilarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarMenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(compilarMenu);

        gerarCodMenu.setText("Gerar Código");
        gerarCodMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gerarCodMenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(gerarCodMenu);

        interpretadorMenu.setText("Abrir Interpretador");
        interpretadorMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                interpretadorMenuMouseClicked(evt);
            }
        });
        interpretadorMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interpretadorMenuActionPerformed(evt);
            }
        });
        jMenuBar1.add(interpretadorMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(lblArqEscolhido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblArq))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblArqEscolhido, javax.swing.GroupLayout.DEFAULT_SIZE, 2, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblArq))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArquivoActionPerformed
        String arquivo = "";
        JFileChooser file = new JFileChooser();
        file.setDialogTitle("Selecionar arquivo: ");
        
        int response = file.showOpenDialog(this);
        
        if(response == JFileChooser.APPROVE_OPTION){
            File arq = file.getSelectedFile();
            String path = arq.getPath();
            
            lblArqEscolhido.setText(path);
            lblArq.setText("Diretório: ");
            lblArq.setVisible(true);
            lblArqEscolhido.setVisible(true);
            
            try {
                FileReader newArq = new FileReader(arq.getPath());
                BufferedReader lerArq = new BufferedReader(newArq);

                String linha = lerArq.readLine(); 

                while (linha != null) {
                  arquivo += linha + "\n";
                  linha = lerArq.readLine();
                }
                
                codigoTxt.setText(arquivo);

                newArq.close();
              } catch (IOException e) {
                  System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
              }
        }
        
    }//GEN-LAST:event_menuArquivoActionPerformed

    private void compilarMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_compilarMenuMouseClicked
        try{
            Analisar();
        }
        catch(IOException ex){
            System.out.println("Erro ao iniciar compilador!");
        }
    }//GEN-LAST:event_compilarMenuMouseClicked

    private void compilarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarMenuActionPerformed

    }//GEN-LAST:event_compilarMenuActionPerformed

    private void interpretadorMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interpretadorMenuActionPerformed
      
    }//GEN-LAST:event_interpretadorMenuActionPerformed

    private void interpretadorMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_interpretadorMenuMouseClicked
        Color a = cTxtLogSintatico.getBackground();
        
        if(a == Color.GREEN){
            InterpretadorUI u = new InterpretadorUI();
            u.setVisible(true);
            try {
                u.run();
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "É necessário compilar o arquivo sem erros primeiro!");
        }
        
    }//GEN-LAST:event_interpretadorMenuMouseClicked

    private void gerarCodMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gerarCodMenuMouseClicked
        Color a = cTxtLogSintatico.getBackground();
        System.out.println(a);
//        String caminho = "";
        String result = gc.gerarCodigo();
       
        
        if(a == Color.GREEN){
            JFileChooser file = new JFileChooser();
            file.setDialogTitle("Selecionar arquivo: ");

            int response = file.showSaveDialog(this);

            if(response == JFileChooser.APPROVE_OPTION){
                File arq = file.getSelectedFile();
//                String caminho = arq.getPath();
                
                try {
//                 FileWriter doc = new FileWriter(caminho);
                 PrintWriter gravarDoc = new PrintWriter(new FileWriter(arq));

                 gravarDoc.printf(result);
                 gravarDoc.close();
                 
                } catch (IOException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "É necessário compilar o arquivo sem erros primeiro!");
        }
    }//GEN-LAST:event_gerarCodMenuMouseClicked

    public void Analisar() throws IOException {
        File arq = new File("codigo.txt");
        ArrayList <TokenArmazenado> tokens = new ArrayList();
        PrintWriter writer;
        DefaultTableModel model = (DefaultTableModel) tableLog.getModel();
        linhaT = 0;
        model.setNumRows(0);

        try{
            writer = new PrintWriter(arq);
            writer.print(codigoTxt.getText());
            writer.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("Erro ao escrever código!");
        }
        Reader reader = new BufferedReader(new FileReader("codigo.txt"));
        Lexer lexer = new Lexer(reader);
        String result = "";
        while(true){
            Token token = lexer.yylex();

//          System.out.println("name---- " + token.name());
                       
            if(token == null){              
                break;
            }
            
            model.addRow(new Object[]{null, null, null, null});
            
            TokenArmazenado nt = new TokenArmazenado(token.toString(), lexer.lexema, lexer.linha, lexer.coluna);
            tokens.add(nt);

            if((token.name() == "SIMB_DESCONHECIDO") || (token.name() == "ERRO_INT_LONGO") || (token.name() == "ERRO_ID_LONGO") || (token.name() == "COMENTARIO_NAO_FECHADO")){
//                tableLog.setRowSelectionInterval(linhaT, linhaT);
                tableLog.addRowSelectionInterval(linhaT, linhaT);
                tableLog.setSelectionBackground(Color.red);
                tableLog.setSelectionForeground(Color.white);
            }
            
            tableLog.setValueAt(token, linhaT, 0);
            tableLog.setValueAt(lexer.lexema, linhaT, 1);
            tableLog.setValueAt(lexer.linha, linhaT, 2);
            tableLog.setValueAt(lexer.coluna, linhaT, 3);
                
            linhaT++;         
        }
        
        AnalisadorSintatico as = new AnalisadorSintatico(tokens);        
        gc.setVarList(tokens);
        Boolean status = as.Iniciar();
            cTxtLogSintatico.setText(as.Erros());
            String a = cTxtLogSintatico.getText();
            if(a.length() > 0) cTxtLogSintatico.setBackground(Color.red);
            else{
                cTxtLogSintatico.setBackground(Color.green);
                cTxtLogSintatico.setText("Arquivo compilado com sucesso!");
            }
        cTxtLogSintatico.setText(cTxtLogSintatico.getText() + "\n" +as.ErrosS());
        AnalisadorSemantico asm = new AnalisadorSemantico();
        asm.setVarList(as.returnVars());
        asm.setVarListUndeclared(as.returnVarsUndeclared());
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu abrirMenu;
    private javax.swing.JTextArea cTxtLogSintatico;
    private javax.swing.JTextArea codigoTxt;
    private javax.swing.JMenu compilarMenu;
    private javax.swing.JMenu gerarCodMenu;
    private javax.swing.JMenu interpretadorMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblArq;
    private javax.swing.JLabel lblArqEscolhido;
    private javax.swing.JMenuItem menuArquivo;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JTable tableLog;
    // End of variables declaration//GEN-END:variables
}

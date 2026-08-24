package view;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import model.Usuario;
import java.beans.PropertyVetoException;
import util.SessaoUsuario;

public class FrmPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmPrincipal.class.getName());

    public FrmPrincipal() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        carregarUsuario();
        aplicarPermissoes();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    
    private void carregarUsuario() {
        
        Usuario usuario = SessaoUsuario.getUsuarioLogado();
        
        if (usuario != null) {
            
            lblUsuario.setText("Usuario: " + usuario.getNome());
            
            lblNivel.setText("  Nível: " + usuario.getNivel());
        } else {
            lblUsuario.setText("Usuário: Não Identificado");
            
            lblNivel.setText("  Nível: Não Identificado");
        }
    }
    
    private void aplicarPermissoes() {
        if (SessaoUsuario.isMaster()) {
            mnuUsuarios.setEnabled(true);
            mnuContasPagar.setEnabled(true);
        } else {
            mnuUsuarios.setEnabled(false);
            mnuContasPagar.setEnabled(false);
        }
    }
    
    private void abrirTela(JInternalFrame tela) {
        
        for ( JInternalFrame frame : desktopPrincipal.getAllFrames()){
            
            if (frame.getClass().equals(tela.getClass())){
                try {
                    frame.setSelected(true);
                    
                    if (frame.isIcon()){
                        frame.setIcon(false);
                    }
                } catch (PropertyVetoException erro) {
                    JOptionPane.showMessageDialog(this, "Não é possível selecionar a tela.");
                }
                frame.toFront();
            
                return;
            }
            
        }
        
        desktopPrincipal.add(tela);
        tela.setVisible(true);
        centralizarInternalFrame(tela);
    }
    
    private void centralizarInternalFrame(JInternalFrame tela) {
        
        int x = (desktopPrincipal.getWidth() - tela.getWidth()) / 2;
        int y = (desktopPrincipal.getHeight() - tela.getHeight()) / 2;
        
        if (x < 0 ){
            x = 0;
        }
        
        if (y < 0 ) {
            y = 0;
        }
        
        tela.setLocation(x,y);
    }
    
    private void confirmarSaida() {
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente encerrar o FerragemGK?", "Sair", JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPrincipal = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        lblUsuario = new javax.swing.JLabel();
        lblNivel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuSistema = new javax.swing.JMenu();
        mnuLogout = new javax.swing.JMenuItem();
        mnuSair = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuClientes = new javax.swing.JMenuItem();
        mnuFornecedores = new javax.swing.JMenuItem();
        mnuProdutos = new javax.swing.JMenuItem();
        mnuUsuarios = new javax.swing.JMenuItem();
        mnuMovimentos = new javax.swing.JMenu();
        mnuCompras = new javax.swing.JMenuItem();
        mnuVendas = new javax.swing.JMenuItem();
        mnuFinanceiro = new javax.swing.JMenu();
        mnuContasPagar = new javax.swing.JMenuItem();
        mnuContasReceber = new javax.swing.JMenuItem();
        mnuAjuda = new javax.swing.JMenu();
        mnuSobre = new javax.swing.JMenuItem();
        mnuAbrirTeste = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setRollover(true);

        lblUsuario.setText("jLabel1");
        jToolBar1.add(lblUsuario);

        lblNivel.setText("jLabel2");
        jToolBar1.add(lblNivel);

        desktopPrincipal.setLayer(jToolBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktopPrincipalLayout = new javax.swing.GroupLayout(desktopPrincipal);
        desktopPrincipal.setLayout(desktopPrincipalLayout);
        desktopPrincipalLayout.setHorizontalGroup(
            desktopPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
        );
        desktopPrincipalLayout.setVerticalGroup(
            desktopPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, desktopPrincipalLayout.createSequentialGroup()
                .addGap(0, 361, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mnuSistema.setText("Sistema");

        mnuLogout.setText("Logout");
        mnuLogout.addActionListener(this::mnuLogoutActionPerformed);
        mnuSistema.add(mnuLogout);

        mnuSair.setText("Sair");
        mnuSair.addActionListener(this::mnuSairActionPerformed);
        mnuSistema.add(mnuSair);

        jMenuBar1.add(mnuSistema);

        jMenu2.setText("Cadastros");

        mnuClientes.setText("Clientes");
        mnuClientes.addActionListener(this::mnuClientesActionPerformed);
        jMenu2.add(mnuClientes);

        mnuFornecedores.setText("Fornecedores");
        mnuFornecedores.addActionListener(this::mnuFornecedoresActionPerformed);
        jMenu2.add(mnuFornecedores);

        mnuProdutos.setText("Produtos");
        mnuProdutos.addActionListener(this::mnuProdutosActionPerformed);
        jMenu2.add(mnuProdutos);

        mnuUsuarios.setText("Usuários");
        mnuUsuarios.addActionListener(this::mnuUsuariosActionPerformed);
        jMenu2.add(mnuUsuarios);

        jMenuBar1.add(jMenu2);

        mnuMovimentos.setText("Movimentos");

        mnuCompras.setText("Compras");
        mnuCompras.addActionListener(this::mnuComprasActionPerformed);
        mnuMovimentos.add(mnuCompras);

        mnuVendas.setText("Vendas");
        mnuVendas.addActionListener(this::mnuVendasActionPerformed);
        mnuMovimentos.add(mnuVendas);

        jMenuBar1.add(mnuMovimentos);

        mnuFinanceiro.setText("Financeiro");

        mnuContasPagar.setText("Contas a Pagar");
        mnuContasPagar.addActionListener(this::mnuContasPagarActionPerformed);
        mnuFinanceiro.add(mnuContasPagar);

        mnuContasReceber.setText("Contas a Receber");
        mnuContasReceber.addActionListener(this::mnuContasReceberActionPerformed);
        mnuFinanceiro.add(mnuContasReceber);

        jMenuBar1.add(mnuFinanceiro);

        mnuAjuda.setText("Ajuda");

        mnuSobre.setText("Sobre");
        mnuSobre.addActionListener(this::mnuSobreActionPerformed);
        mnuAjuda.add(mnuSobre);

        mnuAbrirTeste.setText("Abrir Tela de Teste");
        mnuAbrirTeste.addActionListener(this::mnuAbrirTesteActionPerformed);
        mnuAjuda.add(mnuAbrirTeste);

        jMenuBar1.add(mnuAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPrincipal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuAbrirTesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAbrirTesteActionPerformed
        abrirTela(new FrmTeste());
    }//GEN-LAST:event_mnuAbrirTesteActionPerformed

    private void mnuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClientesActionPerformed
        abrirTela( new FrmCliente());
    }//GEN-LAST:event_mnuClientesActionPerformed

    private void mnuFornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuFornecedoresActionPerformed
        abrirTela(new FrmFornecedor());
    }//GEN-LAST:event_mnuFornecedoresActionPerformed

    private void mnuProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProdutosActionPerformed
        abrirTela(new FrmProduto());
    }//GEN-LAST:event_mnuProdutosActionPerformed

    private void mnuUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUsuariosActionPerformed
        if (!SessaoUsuario.isMaster()) {
            JOptionPane.showMessageDialog(this, "Acesso permitido somente para usuário MASTER");
            return;
        }
        
        abrirTela(new FrmUsuario());
    }//GEN-LAST:event_mnuUsuariosActionPerformed

    private void mnuComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuComprasActionPerformed
        //abrirTela(new FrmCompra());
    }//GEN-LAST:event_mnuComprasActionPerformed

    private void mnuVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuVendasActionPerformed
        //abrirTela(new FrmVenda());
    }//GEN-LAST:event_mnuVendasActionPerformed

    private void mnuContasPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuContasPagarActionPerformed
        if (!SessaoUsuario.isMaster()){
            JOptionPane.showMessageDialog(this, "Acesso permitido somente para usuário MASTER");
            return;
        }
        
        //abrirTela(new FrmContasPagar());
    }//GEN-LAST:event_mnuContasPagarActionPerformed

    private void mnuContasReceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuContasReceberActionPerformed
        //abrirTela(new FrmContasReceber());
    }//GEN-LAST:event_mnuContasReceberActionPerformed

    private void mnuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLogoutActionPerformed
        int resposta = JOptionPane.showConfirmDialog(this,"Deseja encerrar a sessão atual?", "Logout", JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            
            SessaoUsuario.encerrar();
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_mnuLogoutActionPerformed

    private void mnuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSairActionPerformed
        confirmarSaida();
    }//GEN-LAST:event_mnuSairActionPerformed

    private void mnuSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSobreActionPerformed
        JOptionPane.showMessageDialog(this, "FerragemGK\n Sistema desenvolvido em Java Swing\n Banco de dados PostgreSQL");
    }//GEN-LAST:event_mnuSobreActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        confirmarSaida();
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> new FrmPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPrincipal;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JMenuItem mnuAbrirTeste;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenuItem mnuClientes;
    private javax.swing.JMenuItem mnuCompras;
    private javax.swing.JMenuItem mnuContasPagar;
    private javax.swing.JMenuItem mnuContasReceber;
    private javax.swing.JMenu mnuFinanceiro;
    private javax.swing.JMenuItem mnuFornecedores;
    private javax.swing.JMenuItem mnuLogout;
    private javax.swing.JMenu mnuMovimentos;
    private javax.swing.JMenuItem mnuProdutos;
    private javax.swing.JMenuItem mnuSair;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JMenuItem mnuSobre;
    private javax.swing.JMenuItem mnuUsuarios;
    private javax.swing.JMenuItem mnuVendas;
    // End of variables declaration//GEN-END:variables
}

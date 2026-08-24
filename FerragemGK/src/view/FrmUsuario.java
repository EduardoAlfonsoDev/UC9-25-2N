
package view;

import dao.UsuarioDAO;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.Usuario;
import util.SessaoUsuario;

/**
 *
 * @author edual
 */
public class FrmUsuario extends javax.swing.JInternalFrame {

    private final UsuarioDAO usuarioDAO =
        new UsuarioDAO();
    private long idUsuarioSelecionado = 0;
   
    public FrmUsuario() {
        initComponents();
        configurarTela();
        listarUsuarios();
        limparCampos();
    }
    
    private void configurarTela() {

    if (!SessaoUsuario.isMaster()) {

        JOptionPane.showMessageDialog(
                this,
                "Acesso permitido somente para usuário MASTER."
        );

        dispose();
        return;
    }

    txtCodigo.setEditable(false);

    tblUsuarios.setModel(
            criarModeloTabela()
    );

    tblUsuarios.setSelectionMode(
            javax.swing.ListSelectionModel
                    .SINGLE_SELECTION
    );

    tblUsuarios.setAutoCreateRowSorter(true);
}
    
    private DefaultTableModel criarModeloTabela() {

    return new DefaultTableModel(
            new Object[]{
                "Código",
                "Nome",
                "Login",
                "Nível",
                "Ativo"
            },
            0
    ) {

        @Override
        public boolean isCellEditable(
                int row,
                int column
        ) {
            return false;
        }
    };
}
    
   
    
    private void preencherTabela(
        List<Usuario> usuarios
) {

    DefaultTableModel modelo =
            (DefaultTableModel)
            tblUsuarios.getModel();

    modelo.setRowCount(0);

    for (Usuario usuario : usuarios) {

        modelo.addRow(
                new Object[]{
                    usuario.getIdUsuario(),
                    usuario.getNome(),
                    usuario.getLogin(),
                    usuario.getNivel(),
                    usuario.isAtivo()
                            ? "Sim"
                            : "Não"
                }
        );
    }
}
    
    private void listarUsuarios() {

    try {

        preencherTabela(
                usuarioDAO.listarTodos()
        );

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(
                this,
                "Erro ao carregar usuários.\n"
                + erro.getMessage()
        );
    }
}
    
    private void limparCampos() {

    idUsuarioSelecionado = 0;

    txtCodigo.setText("");
    txtNome.setText("");
    txtLogin.setText("");
    txtSenha.setText("");

    cmbNivel.setSelectedItem("USER");

    chkAtivo.setSelected(true);

    txtNome.requestFocus();
}
    
    private boolean validarCampos(
        boolean novoUsuario
) {

    if (
            txtNome
            .getText()
            .trim()
            .isEmpty()
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Informe o nome."
        );

        txtNome.requestFocus();
        return false;
    }

    if (
            txtLogin
            .getText()
            .trim()
            .isEmpty()
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Informe o login."
        );

        txtLogin.requestFocus();
        return false;
    }

    if (
            novoUsuario
            && txtSenha
                    .getPassword()
                    .length == 0
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Informe a senha."
        );

        txtSenha.requestFocus();
        return false;
    }

    if (
            usuarioDAO.loginExiste(
                    txtLogin
                    .getText()
                    .trim(),
                    idUsuarioSelecionado
            )
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Este login já está sendo utilizado."
        );

        txtLogin.requestFocus();
        return false;
    }

    return true;
}
    
    private Usuario criarUsuarioComCampos() {

    Usuario usuario =
            new Usuario();

    usuario.setIdUsuario(
            idUsuarioSelecionado
    );

    usuario.setNome(
            txtNome.getText().trim()
    );

    usuario.setLogin(
            txtLogin.getText().trim()
    );

    usuario.setSenha(
            new String(
                    txtSenha.getPassword()
            )
    );

    usuario.setNivel(
            cmbNivel
            .getSelectedItem()
            .toString()
    );

    usuario.setAtivo(
            chkAtivo.isSelected()
    );

    return usuario;
}
    
    private void carregarUsuarioSelecionado() {

    int linha =
            tblUsuarios.getSelectedRow();

    if (linha < 0) {

        JOptionPane.showMessageDialog(
                this,
                "Selecione um usuário."
        );

        return;
    }

    int linhaModelo =
            tblUsuarios.convertRowIndexToModel(
                    linha
            );

    long idUsuario =
            Long.parseLong(
                    tblUsuarios
                    .getModel()
                    .getValueAt(
                            linhaModelo,
                            0
                    )
                    .toString()
            );

    try {

        Usuario usuario =
                usuarioDAO.buscarPorId(
                        idUsuario
                );

        if (usuario == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário não encontrado."
            );

            return;
        }

        preencherCampos(usuario);

        tabUsuario.setSelectedIndex(0);

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(
                this,
                "Erro ao carregar usuário.\n"
                + erro.getMessage()
        );
    }
}
    
    private void preencherCampos(
        Usuario usuario
) {

    idUsuarioSelecionado =
            usuario.getIdUsuario();

    txtCodigo.setText(
            String.valueOf(
                    usuario.getIdUsuario()
            )
    );

    txtNome.setText(
            usuario.getNome()
    );

    txtLogin.setText(
            usuario.getLogin()
    );

    txtSenha.setText("");

    cmbNivel.setSelectedItem(
            usuario.getNivel()
    );

    chkAtivo.setSelected(
            usuario.isAtivo()
    );
}
    
    
    
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabUsuario = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        cmbNivel = new javax.swing.JComboBox<>();
        chkAtivo = new javax.swing.JCheckBox();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cmbFiltro = new javax.swing.JComboBox<>();
        txtPesquisa = new javax.swing.JTextField();
        btnLocalizar = new javax.swing.JButton();
        btnListarTodos = new javax.swing.JButton();
        btnCarregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro de Usuários ");

        txtCodigo.setBorder(javax.swing.BorderFactory.createTitledBorder("Código"));

        txtNome.setBorder(javax.swing.BorderFactory.createTitledBorder("Nome"));

        txtLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));

        txtSenha.setBorder(javax.swing.BorderFactory.createTitledBorder("Senha"));

        cmbNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "USER", "MASTER" }));
        cmbNivel.setBorder(javax.swing.BorderFactory.createTitledBorder("Nível"));

        chkAtivo.setText("Ativo");
        chkAtivo.addActionListener(this::chkAtivoActionPerformed);

        btnNovo.setText("Novo");
        btnNovo.addActionListener(this::btnNovoActionPerformed);

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(this::btnSalvarActionPerformed);

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(this::btnAlterarActionPerformed);

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(this::btnExcluirActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkAtivo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSenha)
                                .addGap(65, 65, 65)
                                .addComponent(cmbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtLogin)
                            .addComponent(txtNome)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar)
                                .addGap(18, 18, 18)
                                .addComponent(btnAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                                .addComponent(btnExcluir)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar)))
                        .addGap(37, 37, 37))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAtivo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir)
                    .addComponent(btnCancelar))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        tabUsuario.addTab("Cadastro", jPanel1);

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOME", "LOGIN", "NIVEL", "ID" }));
        cmbFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));

        txtPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));

        btnLocalizar.setText("Localizar");
        btnLocalizar.addActionListener(this::btnLocalizarActionPerformed);

        btnListarTodos.setText("Listar Todos ");
        btnListarTodos.addActionListener(this::btnListarTodosActionPerformed);

        btnCarregar.setText("Carregar Selecionado ");
        btnCarregar.addActionListener(this::btnCarregarActionPerformed);

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLocalizar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnListarTodos)
                        .addGap(18, 18, 18)
                        .addComponent(btnCarregar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLocalizar))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListarTodos)
                    .addComponent(btnCarregar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        );

        tabUsuario.addTab("Consulta", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabUsuario)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabUsuario)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkAtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAtivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkAtivoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
       if (!SessaoUsuario.isMaster()) {

        JOptionPane.showMessageDialog(
                this,
                "Somente MASTER pode excluir usuários."
        );

        return;
    }

    if (idUsuarioSelecionado == 0) {

        JOptionPane.showMessageDialog(
                this,
                "Selecione um usuário."
        );

        return;
    }

    Usuario usuarioLogado =
            SessaoUsuario.getUsuarioLogado();

    if (
            usuarioLogado != null
            && usuarioLogado.getIdUsuario()
            == idUsuarioSelecionado
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Você não pode excluir o usuário que está conectado."
        );

        return;
    }

    int resposta =
            JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir este usuário?",
                    "Excluir Usuário",
                    JOptionPane.YES_NO_OPTION
            );

    if (resposta != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        boolean excluido =
                usuarioDAO.excluir(
                        idUsuarioSelecionado
                );

        if (excluido) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário excluído com sucesso."
            );

            limparCampos();
            listarUsuarios();
        }

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(
                this,
                "Não foi possível excluir o usuário.\n"
                + "Ele pode possuir compras ou vendas vinculadas.\n"
                + "Nesse caso, utilize a opção Ativo para desativá lo."
        );
    }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
         limparCampos();
         tabUsuario.setSelectedIndex(0);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (idUsuarioSelecionado != 0) {

        JOptionPane.showMessageDialog(
                this,
                "Existe um usuário carregado para edição.\n"
                + "Utilize Alterar ou clique em Novo."
        );

        return;
    }

    if (!validarCampos(true)) {
        return;
    }

    try {

        Usuario usuario =
                criarUsuarioComCampos();

        long codigo =
                usuarioDAO.cadastrar(usuario);

        JOptionPane.showMessageDialog(
                this,
                "Usuário cadastrado com sucesso.\n"
                + "Código: "
                + codigo
        );

        limparCampos();
        listarUsuarios();

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(
                this,
                "Não foi possível cadastrar o usuário.\n"
                + erro.getMessage()
        );
    }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        String filtro =
            cmbFiltro
            .getSelectedItem()
            .toString();

    String pesquisa =
            txtPesquisa
            .getText()
            .trim();

    if (pesquisa.isEmpty()) {
        listarUsuarios();
        return;
    }

    if ("ID".equals(filtro)) {

        try {
            Long.parseLong(pesquisa);

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    this,
                    "Para pesquisar por ID informe apenas números."
            );

            return;
        }
    }

    try {

        List<Usuario> usuarios =
                usuarioDAO.pesquisar(
                        filtro,
                        pesquisa
                );

        preencherTabela(usuarios);

        if (usuarios.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum usuário encontrado."
            );
        }

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(
                this,
                "Erro na pesquisa.\n"
                + erro.getMessage()
        );
    }
    }//GEN-LAST:event_btnLocalizarActionPerformed

    private void btnListarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarTodosActionPerformed
        txtPesquisa.setText("");
        listarUsuarios();
    }//GEN-LAST:event_btnListarTodosActionPerformed

    private void btnCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarregarActionPerformed
        carregarUsuarioSelecionado();
    }//GEN-LAST:event_btnCarregarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        if (evt.getClickCount() == 2) {
        carregarUsuarioSelecionado();
    }
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        if (idUsuarioSelecionado == 0) {

        JOptionPane.showMessageDialog(
                this,
                "Localize e carregue um usuário antes de alterar."
        );

        return;
    }

    if (!validarCampos(false)) {
        return;
    }

    Usuario usuarioLogado =
            SessaoUsuario.getUsuarioLogado();

    if (
            usuarioLogado != null
            && usuarioLogado.getIdUsuario()
            == idUsuarioSelecionado
            && !chkAtivo.isSelected()
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Você não pode desativar o usuário que está conectado."
        );

        return;
    }

    if (
            usuarioLogado != null
            && usuarioLogado.getIdUsuario()
            == idUsuarioSelecionado
            && !"MASTER".equals(
                    cmbNivel
                    .getSelectedItem()
                    .toString()
            )
    ) {

        JOptionPane.showMessageDialog(
                this,
                "Você não pode retirar sua própria permissão MASTER durante a sessão."
        );

        return;
    }

    int resposta =
            JOptionPane.showConfirmDialog(
                    this,
                    "Deseja salvar as alterações deste usuário?",
                    "Alterar Usuário",
                    JOptionPane.YES_NO_OPTION
            );

    if (resposta != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        Usuario usuario =
                criarUsuarioComCampos();

        boolean alterado =
                usuarioDAO.alterar(usuario);

        if (alterado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário alterado com sucesso."
            );

            limparCampos();
            listarUsuarios();
        }

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(
                this,
                "Não foi possível alterar o usuário.\n"
                + erro.getMessage()
        );
    }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCarregar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnListarTodos;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JComboBox<String> cmbNivel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane tabUsuario;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}

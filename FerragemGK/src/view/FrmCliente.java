
package view;
import dao.ClienteDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import util.SessaoUsuario;

public class FrmCliente extends javax.swing.JInternalFrame {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private long idClienteSelecionado = 0;

    public FrmCliente() {
        initComponents();
        configurarTela();
        listarClientes();
        limparCampos();
        setTitle("Cadastro de Clientes");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
    }
    
    private void configurarTela(){
        txtCodigo.setEditable(false);
        
        btnExcluir.setEnabled(SessaoUsuario.isMaster());
        tabelaClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.setAutoCreateRowSorter(true);
        tabelaClientes.setModel(criarModeloTabela());
    }
    
    private DefaultTableModel criarModeloTabela(){
        
        return new DefaultTableModel(new Object[]{
            "Código","Nome","CPF","Telefone","Email","Cidade","UF","Ativo"
        },0
        ){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
    }
    
    private void preencherTabela(List<Cliente> clientes){
        DefaultTableModel modelo = (DefaultTableModel)tabelaClientes.getModel();
        
        modelo.setRowCount(0);
        
        for (Cliente cliente : clientes){
            modelo.addRow(new Object[]{
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getCidade(),
                cliente.getUf(),
                cliente.isAtivo()? "Sim" : "Não"
            });
        }
    }
    
    private void listarClientes(){
        
        try{
            preencherTabela(clienteDAO.listarTodos());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro ao carrega clientes.\n" + erro.getMessage());
        }
    }
    
    private void limparCampos() {

        idClienteSelecionado = 0;

        txtCodigo.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtNumero.setText("");
        txtComplemento.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        txtCep.setText("");

        cmbUf.setSelectedIndex(-1);

        chkAtivo.setSelected(true);

        txtNome.requestFocus();
}
    
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome ao cliente.");
            
            txtNome.requestFocus();
            
            return false;
        }
        
        if( cmbUf.getSelectedIndex() == 0 && !txtCidade.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Selecione a UF");
            
            cmbUf.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private Cliente criarClienteComCampos() {
        Cliente cliente = new Cliente();
        
        cliente.setIdCliente(idClienteSelecionado);
        cliente.setNome(txtNome.getText().trim());
        cliente.setCpf(txtCpf.getText().trim());
        cliente.setTelefone(txtTelefone.getText().trim());
        cliente.setEmail(txtEmail.getText().trim());
        cliente.setEndereco(txtEndereco.getText().trim());
        cliente.setNumero(txtNumero.getText().trim());
        cliente.setComplemento(txtComplemento.getText().trim());
        cliente.setBairro(txtBairro.getText().trim());
        cliente.setCidade(txtCidade.getText().trim());
        
        if(cmbUf.getSelectedIndex() > 0) {
            cliente.setUf(cmbUf.getSelectedItem().toString());
        } else {
            cliente.setUf("");
        }
        
        cliente.setCep(txtCep.getText().trim());
        cliente.setAtivo(chkAtivo.isSelected());
        
        return cliente;
        
    }
    
    private void carregarClienteSelecionado() {
        int linha = tabelaClientes.getSelectedRow();
        
        if(linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela.");
            return;
        }
        
        int linhaModelo = tabelaClientes.convertRowIndexToModel(linha);
        long idCliente = Long.parseLong(tabelaClientes.getModel().getValueAt(linhaModelo, 0).toString());
        
        try {
            Cliente cliente = clienteDAO.buscarPorId(idCliente);
            
            if(cliente == null){
                JOptionPane.showMessageDialog(this, "Cliente não encontrado");
                return;
            }
            
            preencherCampos(cliente);
            tabCliente.setSelectedIndex(0);
        } catch (Exception erro){
            JOptionPane.showMessageDialog(this, "Erro ao carregar cliente. \n" + erro.getMessage());
        }
    }
    
    private void preencherCampos(Cliente cliente){
        idClienteSelecionado = cliente.getIdCliente();
        txtCodigo.setText(String.valueOf(cliente.getIdCliente()));
        txtNome.setText(valorTexto(cliente.getNome()));
        txtCpf.setText(valorTexto(cliente.getCpf()));
        txtTelefone.setText(valorTexto(cliente.getTelefone()));
        txtEmail.setText(valorTexto(cliente.getEmail()));
        txtEndereco.setText(valorTexto(cliente.getEndereco()));
        txtNumero.setText(valorTexto(cliente.getNumero()));
        txtComplemento.setText(valorTexto(cliente.getComplemento()));
        txtBairro.setText(valorTexto(cliente.getBairro()));
        txtCidade.setText(valorTexto(cliente.getCidade()));
        txtCep.setText(valorTexto(cliente.getCep()));
        
        if( cliente.getUf() != null && !cliente.getUf().isBlank()){
            cmbUf.setSelectedItem(cliente.getUf());
        } else {
            cmbUf.setSelectedIndex(0);
        }
        
        chkAtivo.setSelected(cliente.isAtivo());
    }
    
    private String valorTexto(String valor){
        if(valor == null) {
            return "";
        }
        return valor;
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabCliente = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtCpf = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtEndereco = new javax.swing.JTextField();
        txtComplemento = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        txtCep = new javax.swing.JTextField();
        txtBairro = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        cmbUf = new javax.swing.JComboBox<>();
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
        btntListarTodos = new javax.swing.JButton();
        btnCarregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaClientes = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro de Clientes");

        txtCodigo.setBackground(new java.awt.Color(242, 242, 242));
        txtCodigo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código"));

        txtEmail.setBackground(new java.awt.Color(242, 242, 242));
        txtEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        txtCpf.setBackground(new java.awt.Color(242, 242, 242));
        txtCpf.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CPF"));

        txtTelefone.setBackground(new java.awt.Color(242, 242, 242));
        txtTelefone.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Celular / WhatsApp"));

        txtNome.setBackground(new java.awt.Color(242, 242, 242));
        txtNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome"));

        txtEndereco.setBackground(new java.awt.Color(242, 242, 242));
        txtEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereço"));

        txtComplemento.setBackground(new java.awt.Color(242, 242, 242));
        txtComplemento.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Complemento"));

        txtNumero.setBackground(new java.awt.Color(242, 242, 242));
        txtNumero.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Número"));

        txtCep.setBackground(new java.awt.Color(242, 242, 242));
        txtCep.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CEP"));

        txtBairro.setBackground(new java.awt.Color(242, 242, 242));
        txtBairro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Bairro"));

        txtCidade.setBackground(new java.awt.Color(242, 242, 242));
        txtCidade.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cidade"));

        cmbUf.setBackground(new java.awt.Color(242, 242, 242));
        cmbUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        cmbUf.setToolTipText("");
        cmbUf.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "UF"));

        chkAtivo.setText("Ativo");

        btnNovo.setBackground(new java.awt.Color(242, 242, 242));
        btnNovo.setText("Novo");
        btnNovo.addActionListener(this::btnNovoActionPerformed);

        btnSalvar.setBackground(new java.awt.Color(242, 242, 242));
        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(this::btnSalvarActionPerformed);

        btnAlterar.setBackground(new java.awt.Color(242, 242, 242));
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(this::btnAlterarActionPerformed);

        btnExcluir.setBackground(new java.awt.Color(242, 242, 242));
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(this::btnExcluirActionPerformed);

        btnCancelar.setBackground(new java.awt.Color(242, 242, 242));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEmail)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEndereco)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCep)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNovo)
                                .addGap(18, 18, 18)
                                .addComponent(btnSalvar)
                                .addGap(18, 18, 18)
                                .addComponent(btnAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnExcluir)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancelar)
                            .addComponent(chkAtivo))
                        .addGap(0, 62, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(txtNome)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkAtivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir)
                    .addComponent(btnCancelar))
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(78, 78, 78)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(327, Short.MAX_VALUE)))
        );

        tabCliente.addTab("Cadastro", jPanel1);

        cmbFiltro.setBackground(new java.awt.Color(242, 242, 242));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOME", "ID", "CPF", "EMAIL" }));
        cmbFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtro"));
        cmbFiltro.addActionListener(this::cmbFiltroActionPerformed);

        txtPesquisa.setBackground(new java.awt.Color(242, 242, 242));
        txtPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pesquisa"));

        btnLocalizar.setBackground(new java.awt.Color(242, 242, 242));
        btnLocalizar.setText("Localizar");
        btnLocalizar.addActionListener(this::btnLocalizarActionPerformed);

        btntListarTodos.setBackground(new java.awt.Color(242, 242, 242));
        btntListarTodos.setText("Listar Todos");
        btntListarTodos.addActionListener(this::btntListarTodosActionPerformed);

        btnCarregar.setBackground(new java.awt.Color(242, 242, 242));
        btnCarregar.setText("Carregar");
        btnCarregar.addActionListener(this::btnCarregarActionPerformed);

        tabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnLocalizar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btntListarTodos)
                        .addGap(18, 18, 18)
                        .addComponent(btnCarregar)))
                .addContainerGap(192, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbFiltro)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisa)
                        .addComponent(btnLocalizar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntListarTodos)
                    .addComponent(btnCarregar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
        );

        tabCliente.addTab("Consulta", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCliente)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabCliente)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFiltroActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        limparCampos();
        tabCliente.setSelectedIndex(0);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (idClienteSelecionado !=0){
            JOptionPane.showMessageDialog(this, "Existe um cliente carregado para edição.\n Utilize Alterar ou clique em Novo");
            return;
        }
        
        if(!validarCampos()){
            return;
        }
        
        try{
            Cliente cliente = criarClienteComCampos();
            
            long codigo = clienteDAO.cadastrar(cliente);
            
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!\n Código: " + codigo);
            
            limparCampos();
            listarClientes();
        } catch (Exception erro){
            JOptionPane.showMessageDialog(this, "Não foi possível cadastrar o cliente. \n" + erro.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        String pesquisa = txtPesquisa.getText().trim();
        String filtro = cmbFiltro.getSelectedItem().toString();
        
        if (pesquisa.isEmpty()) {
            listarClientes();
            return;
        }
        
        if(filtro.equals("ID")) {
            try {
                Long.parseLong(pesquisa);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(this, "Para pesquisar por ID informe apenas número.");
                
                txtPesquisa.requestFocus();
                return;
            }
        }
        
        try {
            List<Cliente> clientes = clienteDAO.pesquisar(filtro, pesquisa);
            
            preencherTabela(clientes);
            
            if(clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Nenhum cliente encontrado");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro na pesquisa. \n" + erro.getMessage());
        }
    }//GEN-LAST:event_btnLocalizarActionPerformed

    private void btntListarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntListarTodosActionPerformed
        txtPesquisa.setText("");
        listarClientes();
    }//GEN-LAST:event_btntListarTodosActionPerformed

    private void btnCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarregarActionPerformed
        carregarClienteSelecionado();
    }//GEN-LAST:event_btnCarregarActionPerformed

    private void tabelaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaClientesMouseClicked
        if(evt.getClickCount() == 2){
            carregarClienteSelecionado();
        }
    }//GEN-LAST:event_tabelaClientesMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        if(idClienteSelecionado == 0){
            JOptionPane.showMessageDialog(this, "Localize e carregue um cliente");
            
            return;
        }
        
        if (!validarCampos()){
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja salvar as alterações?", "Alterar Cliente", JOptionPane.YES_NO_OPTION);
        
        if(resposta != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            Cliente cliente = criarClienteComCampos();
            
            boolean alterado = clienteDAO.alterar(cliente);
            
            
            if (alterado) {
                JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!");
                limparCampos();
                listarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro foi alterado");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Não foi possível alterar o cliente" + erro.getMessage());
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if  (!SessaoUsuario.isMaster()){
            JOptionPane.showMessageDialog(this, "Você não possui permissão para excluir clientes");
            return;
        }
        
        if (idClienteSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Localize e carrege um cliente antes de excluir.");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este cliente?", "Excluir cliente", JOptionPane.YES_NO_OPTION);
        
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            boolean excluido = clienteDAO.excluir(idClienteSelecionado);
            
            if (excluido) {
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso.");
                
                limparCampos();
                listarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encotrado");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Não foi possível excluir o cliente.\n O Cliente pode possuir registros vinculados. \n" + erro.getMessage());
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCarregar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btntListarTodos;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JComboBox<String> cmbUf;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane tabCliente;
    private javax.swing.JTable tabelaClientes;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}

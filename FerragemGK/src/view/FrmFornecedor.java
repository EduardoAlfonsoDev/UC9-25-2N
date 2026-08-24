
package view;
import dao.FornecedorDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Fornecedor;
import util.SessaoUsuario;


public class FrmFornecedor extends javax.swing.JInternalFrame {
    
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private long idFornecedorSelecionado = 0;

    public FrmFornecedor() {
        initComponents();
        configurarTela();
        listarFornecedores();
        limparCampos();
        setTitle("Cadastro de Fornecedores");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
    }
    
    private void configurarTela(){
        txtCodigo.setEditable(false);
        btnExcluir.setEnabled(SessaoUsuario.isMaster());
        tabelaFornecedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabelaFornecedores.setAutoCreateRowSorter(true);
        tabelaFornecedores.setModel(criarModeloTabela());
    }
    
    private DefaultTableModel criarModeloTabela(){
        return new DefaultTableModel(new Object[]{
            "Código",
            "Razão Social",
            "Nome Fantasia",
            "CNPJ",
            "Telefone",
            "Email",
            "Cidade",
            "UF",
            "Ativo"
        }
        ,0){
        @Override
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    }
    
    private void preencherTabela(List<Fornecedor> fornecedores){
        DefaultTableModel modelo = (DefaultTableModel)tabelaFornecedores.getModel();
        modelo.setRowCount(0);
        
        for(Fornecedor fornecedor : fornecedores){
            modelo.addRow(new Object[]{
                fornecedor.getIdFornecedor(),
                fornecedor.getRazaoSocial(),
                fornecedor.getNomeFantasia(),
                fornecedor.getCnpj(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                fornecedor.getCidade(),
                fornecedor.getUf(),
                fornecedor.isAtivo()? "Sim" : "Não"
            });
        }
    }
    
    private void listarFornecedores(){
        try {
            preencherTabela(fornecedorDAO.listarTodos());
        } catch (Exception erro){
            JOptionPane.showMessageDialog(this, "Erro ao carregar fornecedores." + erro.getMessage());
        }
    }
    
    private void limparCampos(){
        idFornecedorSelecionado = 0;
        
        txtCodigo.setText("");
        txtRazaoSocial.setText("");
        txtNomeFantasia.setText("");
        txtCnpj.setText("");
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

        txtRazaoSocial.requestFocus();
    }
    
    private boolean validarCampos(){
        if (txtRazaoSocial.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Informe a razão social");
            txtRazaoSocial.requestFocus();
            return false;
        }
        
        if (txtCnpj.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Informe o CNPJ.");
            txtCnpj.requestFocus();
            return false;
        }
        
        if (cmbUf.getSelectedIndex() == 0 && !txtCidade.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Selecioe a UF.");
            cmbUf.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private Fornecedor criarFornecedorComCampos() {

    Fornecedor fornecedor =
            new Fornecedor();

    fornecedor.setIdFornecedor(
            idFornecedorSelecionado
    );

    fornecedor.setRazaoSocial(
            txtRazaoSocial.getText().trim()
    );

    fornecedor.setNomeFantasia(
            txtNomeFantasia.getText().trim()
    );

    fornecedor.setCnpj(
            txtCnpj.getText().trim()
    );

    fornecedor.setTelefone(
            txtTelefone.getText().trim()
    );

    fornecedor.setEmail(
            txtEmail.getText().trim()
    );

    fornecedor.setEndereco(
            txtEndereco.getText().trim()
    );

    fornecedor.setNumero(
            txtNumero.getText().trim()
    );

    fornecedor.setComplemento(
            txtComplemento.getText().trim()
    );

    fornecedor.setBairro(
            txtBairro.getText().trim()
    );

    fornecedor.setCidade(
            txtCidade.getText().trim()
    );

    if (
            cmbUf.getSelectedIndex() > 0
    ) {

        fornecedor.setUf(
                cmbUf
                .getSelectedItem()
                .toString()
        );

    } else {

        fornecedor.setUf("");
    }

    fornecedor.setCep(
            txtCep.getText().trim()
    );

    fornecedor.setAtivo(
            chkAtivo.isSelected()
    );

    return fornecedor;
}
    
    private void carregarFornecedorSelecionado(){
        int linha = tabelaFornecedores.getSelectedRow();
        
        if(linha == -1){
            JOptionPane.showMessageDialog(this, "Selecione um fornecedor a tabela.");
            return;
        }
        
        int linhaModelo = tabelaFornecedores.convertRowIndexToModel(linha);
        
        long idFornecedor = Long.parseLong(tabelaFornecedores.getModel().getValueAt(linhaModelo, 0).toString());
        
        try {
            Fornecedor fornecedor = fornecedorDAO.buscarPorId(idFornecedor);
            
            if(fornecedor == null){
                JOptionPane.showMessageDialog(this, "Fornecedor não encontrado.");
                return;
            }
            preencherCampos(fornecedor);
            tabFornecedor.setSelectedIndex(0);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar fornecedor." + erro.getMessage());
        }
    }
    
    private String valorTexto(
        String valor
) {

    if (valor == null) {

        return "";
    }

    return valor;
}
    
    private void preencherCampos(
        Fornecedor fornecedor
) {

    idFornecedorSelecionado =
            fornecedor.getIdFornecedor();

    txtCodigo.setText(
            String.valueOf(
                    fornecedor.getIdFornecedor()
            )
    );

    txtRazaoSocial.setText(
            valorTexto(
                    fornecedor.getRazaoSocial()
            )
    );

    txtNomeFantasia.setText(
            valorTexto(
                    fornecedor.getNomeFantasia()
            )
    );

    txtCnpj.setText(
            valorTexto(
                    fornecedor.getCnpj()
            )
    );

    txtTelefone.setText(
            valorTexto(
                    fornecedor.getTelefone()
            )
    );

    txtEmail.setText(
            valorTexto(
                    fornecedor.getEmail()
            )
    );

    txtEndereco.setText(
            valorTexto(
                    fornecedor.getEndereco()
            )
    );

    txtNumero.setText(
            valorTexto(
                    fornecedor.getNumero()
            )
    );

    txtComplemento.setText(
            valorTexto(
                    fornecedor.getComplemento()
            )
    );

    txtBairro.setText(
            valorTexto(
                    fornecedor.getBairro()
            )
    );

    txtCidade.setText(
            valorTexto(
                    fornecedor.getCidade()
            )
    );

    txtCep.setText(
            valorTexto(
                    fornecedor.getCep()
            )
    );

    if (
            fornecedor.getUf() != null
            && !fornecedor
                    .getUf()
                    .isBlank()
    ) {

        cmbUf.setSelectedItem(
                fornecedor.getUf()
        );

    } else {

        cmbUf.setSelectedIndex(
                0
        );
    }

    chkAtivo.setSelected(
            fornecedor.isAtivo()
    );
}

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabFornecedor = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        txtRazaoSocial = new javax.swing.JTextField();
        txtNomeFantasia = new javax.swing.JTextField();
        txtCnpj = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtEndereco = new javax.swing.JTextField();
        txtComplemento = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        txtBairro = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        txtCep = new javax.swing.JTextField();
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
        btnListarTodos = new javax.swing.JButton();
        btnCarregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaFornecedores = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro de Fornecedores ");

        txtCodigo.setBackground(new java.awt.Color(242, 242, 242));
        txtCodigo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Código"));

        txtRazaoSocial.setBackground(new java.awt.Color(242, 242, 242));
        txtRazaoSocial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Razão Social"));

        txtNomeFantasia.setBackground(new java.awt.Color(242, 242, 242));
        txtNomeFantasia.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Nome Fantasia"));

        txtCnpj.setBackground(new java.awt.Color(242, 242, 242));
        txtCnpj.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CNPJ"));

        txtTelefone.setBackground(new java.awt.Color(242, 242, 242));
        txtTelefone.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Telefone"));

        txtEmail.setBackground(new java.awt.Color(242, 242, 242));
        txtEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        txtEndereco.setBackground(new java.awt.Color(242, 242, 242));
        txtEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Endereco"));

        txtComplemento.setBackground(new java.awt.Color(242, 242, 242));
        txtComplemento.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Complemento"));

        txtNumero.setBackground(new java.awt.Color(242, 242, 242));
        txtNumero.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Numero"));

        txtBairro.setBackground(new java.awt.Color(242, 242, 242));
        txtBairro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Bairro"));

        txtCidade.setBackground(new java.awt.Color(242, 242, 242));
        txtCidade.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cidade"));

        txtCep.setBackground(new java.awt.Color(242, 242, 242));
        txtCep.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "CEP"));

        cmbUf.setBackground(new java.awt.Color(242, 242, 242));
        cmbUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        cmbUf.addActionListener(this::cmbUfActionPerformed);

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
                    .addComponent(txtRazaoSocial)
                    .addComponent(txtNomeFantasia, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnNovo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSalvar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAlterar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnExcluir)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCancelar))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(chkAtivo))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbUf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkAtivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir)
                    .addComponent(btnCancelar))
                .addGap(24, 24, 24))
        );

        tabFornecedor.addTab("Cadastro", jPanel1);

        cmbFiltro.setBackground(new java.awt.Color(242, 242, 242));
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RAZAO SOCIAL", "NOME FANTASIA", "ID", "CNPJ", "EMAIL" }));
        cmbFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtro"));

        txtPesquisa.setBackground(new java.awt.Color(242, 242, 242));
        txtPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pesquisa"));

        btnLocalizar.setBackground(new java.awt.Color(242, 242, 242));
        btnLocalizar.setText("Localizar");
        btnLocalizar.addActionListener(this::btnLocalizarActionPerformed);

        btnListarTodos.setBackground(new java.awt.Color(242, 242, 242));
        btnListarTodos.setText("Listar Todos");
        btnListarTodos.addActionListener(this::btnListarTodosActionPerformed);

        btnCarregar.setBackground(new java.awt.Color(242, 242, 242));
        btnCarregar.setText("Carregar");
        btnCarregar.addActionListener(this::btnCarregarActionPerformed);

        tabelaFornecedores.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaFornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFornecedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaFornecedores);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnListarTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCarregar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLocalizar))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCarregar, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(btnListarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
        );

        tabFornecedor.addTab("Consulta", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabFornecedor)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabFornecedor)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUfActionPerformed

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        String pesquisa = txtPesquisa.getText().trim();
        String filtro = cmbFiltro.getSelectedItem().toString();
        
        if(pesquisa.isEmpty()){
            listarFornecedores();
            return;
        }
        
        if(filtro.equals("ID")){
            try {
                Long.parseLong(pesquisa);
            } catch (NumberFormatException erro){
                JOptionPane.showMessageDialog(this, "Para pesquisar por ID informe apenas números.");
                txtPesquisa.requestFocus();
                return;
            }
        }
        
        try {
            List<Fornecedor> fornecedores = fornecedorDAO.pesquisar(filtro, pesquisa);
            preencherTabela(fornecedores);
            
            if(fornecedores.isEmpty()){
                JOptionPane.showMessageDialog(this, "Nenhum fornecedor encontrado.");
            }
        } catch (Exception erro){
            JOptionPane.showMessageDialog(this, "Erro na pesquisa." + erro.getMessage());
        }
            
    }//GEN-LAST:event_btnLocalizarActionPerformed

    private void btnListarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarTodosActionPerformed
        txtPesquisa.setText("");
        listarFornecedores();
    }//GEN-LAST:event_btnListarTodosActionPerformed

    private void btnCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarregarActionPerformed
        carregarFornecedorSelecionado();
    }//GEN-LAST:event_btnCarregarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        limparCampos();
        tabFornecedor.setSelectedIndex(0);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (idFornecedorSelecionado != 0){
            JOptionPane.showMessageDialog(this, "Existe um fornecedor carregado para edição.\n Utilize Alterar ou clique em Novo");
            return;
        }
        
        if (!validarCampos()){
            return;
        }
        
        try{
            Fornecedor fornecedor = criarFornecedorComCampos();
            
            long codigo = fornecedorDAO.cadastrar(fornecedor);
            JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso.\n Código: " + codigo);
            
            limparCampos();
            listarFornecedores();
        }catch (Exception erro){
            JOptionPane.showMessageDialog(this, "Não foi possível cadastrar o fornecedor." + erro.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void tabelaFornecedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFornecedoresMouseClicked
        if (evt.getClickCount() == 2){
            carregarFornecedorSelecionado();
        }
    }//GEN-LAST:event_tabelaFornecedoresMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        if (idFornecedorSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Localize e carregue um fornecedor antes de alterar.");
            return;
        }
        
        if(!validarCampos()){
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja salvar as alterações deste fornecedor?", "Alterar Fornecedor", JOptionPane.YES_NO_OPTION);
        
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            Fornecedor fornecedor = criarFornecedorComCampos();
            
            boolean alterado = fornecedorDAO.alterar(fornecedor);
            
            if(alterado){
                JOptionPane.showMessageDialog(this, "Fornecedor alterado com sucesso");
                
                limparCampos();
                listarFornecedores();
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum registro foi alterado");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Não foi possível alterar o fornecedor." + erro.getMessage());
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (!SessaoUsuario.isMaster()){
            JOptionPane.showMessageDialog(this,"Você não possui permissão para excluir fornecedores.");
            return;
        }
        
        if (idFornecedorSelecionado == 0){
            JOptionPane.showMessageDialog(this, "Localize e carregue um fornecedor antes de excluir.");
            return;
        }
        
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este fornecedor?", "Excluir Fornecedor", JOptionPane.YES_NO_OPTION);
        
        if (resposta != JOptionPane.YES_OPTION){
            return;
        }
        
        try {
            boolean excluido = fornecedorDAO.excluir(idFornecedorSelecionado);
            
            if (excluido) {
                JOptionPane.showMessageDialog(this, "Fornecedor excluído com sucesso.");
                
                limparCampos();
                listarFornecedores();
            } else {
                JOptionPane.showMessageDialog(this, "Fornecedor não encontrado.");
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Não foi possível excluir o fornecedor\n Ele pode possuir compras vinculadas." + erro.getMessage());
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
    private javax.swing.JButton btnListarTodos;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JComboBox<String> cmbUf;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane tabFornecedor;
    private javax.swing.JTable tabelaFornecedores;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCnpj;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNomeFantasia;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtRazaoSocial;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}

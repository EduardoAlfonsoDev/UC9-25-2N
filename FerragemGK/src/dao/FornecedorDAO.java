package dao;
import conexao.Conexao;
import model.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class FornecedorDAO {
    
    private String textoOuNull(String texto) {
        if (texto == null || texto.trim().isEmpty()){
            return null;
        }
        
        return texto.trim();
    }
    
    public long cadastrar(Fornecedor fornecedor){
        String sql = """
                     INSERT INTO fornecedor (razao_social, nome_fantasia, cnpj, telefone, email, endereco, numero, complemento, bairro, cidade, uf, cep, ativo)
                     VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                     RETURNING id_fornecedor
                     """;
        
        try {
        Connection conexao = Conexao.conectar();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, fornecedor.getRazaoSocial());
        stmt.setString(2, textoOuNull(fornecedor.getNomeFantasia()));
        stmt.setString(3, fornecedor.getCnpj());
        stmt.setString(4, textoOuNull(fornecedor.getTelefone()));
        stmt.setString(5, textoOuNull(fornecedor.getEmail()));
        stmt.setString(6, textoOuNull(fornecedor.getEndereco()));
        stmt.setString(7, textoOuNull(fornecedor.getNumero()));
        stmt.setString(8, textoOuNull(fornecedor.getComplemento()));
        stmt.setString(9, textoOuNull(fornecedor.getBairro()));
        stmt.setString(10, textoOuNull(fornecedor.getCidade()));
        stmt.setString(11, textoOuNull(fornecedor.getUf()));
        stmt.setString(12, textoOuNull(fornecedor.getCep()));
        stmt.setBoolean(13, fornecedor.isAtivo());
       
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            return rs.getLong("id_fornecedor");
    }
        } catch (SQLException erro){
            throw new RuntimeException("Erro ao cadastrar fornecedor.", erro);
        }
        return 0;
    }
    
    public boolean alterar(Fornecedor fornecedor) {
        
        String sql = """
                     UPDATE fornecedor
                     SET razao_social = ?, nome_fantasia = ?, cnpj = ?, telefone = ?, email = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = 0, uf = ?, cep = ?, ativo = ?
                     WHERE id_fornecedor = ?
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setString(1, fornecedor.getRazaoSocial());
            stmt.setString(2, textoOuNull(fornecedor.getNomeFantasia()));
            stmt.setString(3, fornecedor.getCnpj());
            stmt.setString(4, textoOuNull(fornecedor.getTelefone()));
            stmt.setString(5, textoOuNull(fornecedor.getEmail()));
            stmt.setString(6, textoOuNull(fornecedor.getEndereco()));
            stmt.setString(7, textoOuNull(fornecedor.getNumero()));
            stmt.setString(8, textoOuNull(fornecedor.getComplemento()));
            stmt.setString(9, textoOuNull(fornecedor.getBairro()));
            stmt.setString(10, textoOuNull(fornecedor.getCidade()));
            stmt.setString(11, textoOuNull(fornecedor.getUf()));
            stmt.setString(12, textoOuNull(fornecedor.getCep()));
            stmt.setBoolean(13, fornecedor.isAtivo());
            stmt.setLong(14, fornecedor.getIdFornecedor());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao alterar fornecedor.", erro);
        }
    }
    
    public boolean excluir(long idFornecedor) {
        
        String sql = """
                     DELETE FROM fornecedor
                     WHERE id_fornededor = ?
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setLong(1, idFornecedor);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException erro){
            throw new RuntimeException("Não foi possível excluir o fornecedor.", erro);
        }
    }
    
    private Fornecedor montarFornecedor(ResultSet rs)throws SQLException{
        
        Fornecedor fornecedor = new Fornecedor();
        
        fornecedor.setIdFornecedor(rs.getLong("id_fornecedor"));
        fornecedor.setRazaoSocial(rs.getString("razao_social"));
        fornecedor.setNomeFantasia(rs.getString("nome_fantasia"));
        fornecedor.setCnpj(rs.getString("cnpj"));
        fornecedor.setTelefone(rs.getString("telefone"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setEndereco(rs.getString("endereco"));
        fornecedor.setNumero(rs.getString("numero"));
        fornecedor.setComplemento(rs.getString("complemento"));
        fornecedor.setBairro(rs.getString("bairro"));
        fornecedor.setCidade(rs.getString("cidade"));
        fornecedor.setUf(rs.getString("uf"));
        fornecedor.setCep(rs.getString("cep"));
        fornecedor.setAtivo(rs.getBoolean("ativo"));
        
        return fornecedor;
    }
    
    public Fornecedor buscarPorId(long idFornecedor) {
        
        String sql = """
                     SELECT * FROM fornecedor
                     WHERE id_fornecedor = ?
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setLong(1, idFornecedor);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return montarFornecedor(rs);
            }
        } catch (SQLException erro){
            throw new RuntimeException("Erro ao localizar fornecedor.", erro);
        }
        
        return null;
    }
    
    public List<Fornecedor> listarTodos() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        
        String sql = """
                     SELECT * FROM fornecedor
                     ORDER BY razao_social
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                fornecedores.add(montarFornecedor(rs));
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao listar fornecedores.", erro);
        }
        
        return fornecedores;
    }
    
    public List<Fornecedor> pesquisar(String filtro, String pesquisa){
        
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql;
        
        switch(filtro) {
            case "ID":
                sql = """
                      SELECT * FROM fornecedor
                      WHERE id_fornecedor = ?
                      ORDER BY razao_social
                      """;
                break;
            case "NOME FANTASIA":
                sql = """
                      SELECT * FROM fornecedor
                      WHERE nome_fantasia ILIKE ?
                      ORDER BY razao_social
                      """;
                break;
            case "CNPJ":
                sql = """
                      SELECT * FROM fornecedor
                      WHERE cnpj ILIKE ?
                      ORDER BY razao_social
                      """;
                break;
            case "EMAIL":
                sql = """
                      SELECT * FROM fornecedor
                      WHERE email ILIKE ?
                      ORDER BY razao_social
                      """;
                break;
            default:
                sql = """
                      SELECT * FROM fornecedor
                      WHERE razao_social ILIKE ?
                      ORDER BY razao_social
                      """;
                break;
        }
        
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            if(filtro.equals("ID")){
              stmt.setLong(1,Long.parseLong(pesquisa));
            } else {
                stmt.setString(1,"%" + pesquisa + "%");
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                fornecedores.add(montarFornecedor(rs));
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao pesquisar fornecedores.");
        }
        
        return fornecedores;
    }
}

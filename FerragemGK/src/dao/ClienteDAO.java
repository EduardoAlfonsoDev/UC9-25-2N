package dao;

import conexao.Conexao;
import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public long cadastrar (Cliente cliente) {
        String sql = """
                     INSERT INTO cliente (nome, cpf, telefone, email, endereco, numero, complemento, bairro, cidade, uf, cep, ativo)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                     RETURNING id_cliente
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, textoOuNull(cliente.getCpf()));
            stmt.setString(3, textoOuNull(cliente.getTelefone()));
            stmt.setString(4, textoOuNull(cliente.getEmail()));
            stmt.setString(5, textoOuNull(cliente.getEndereco()));
            stmt.setString(6, textoOuNull(cliente.getNumero()));
            stmt.setString(7, textoOuNull(cliente.getComplemento()));
            stmt.setString(8, textoOuNull(cliente.getBairro()));
            stmt.setString(9, textoOuNull(cliente.getCidade()));
            stmt.setString(10, textoOuNull(cliente.getUf()));
            stmt.setString(11, textoOuNull(cliente.getCep()));
            stmt.setBoolean(12, cliente.isAtivo());
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()){
                return rs.getLong("id_cliente");
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao cadastrar cliente", erro);
        }
        return 0;
    }
    
    public boolean alterar(Cliente cliente) {
        String sql = """
                     UPDATE cliente
                     SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, ativo = ?
                     Where id_cliente = ?
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setLong(13, cliente.getIdCliente());
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, textoOuNull(cliente.getCpf()));
            stmt.setString(3, textoOuNull(cliente.getTelefone()));
            stmt.setString(4, textoOuNull(cliente.getEmail()));
            stmt.setString(5, textoOuNull(cliente.getEndereco()));
            stmt.setString(6, textoOuNull(cliente.getNumero()));
            stmt.setString(7, textoOuNull(cliente.getComplemento()));
            stmt.setString(8, textoOuNull(cliente.getBairro()));
            stmt.setString(9, textoOuNull(cliente.getCidade()));
            stmt.setString(10, textoOuNull(cliente.getUf()));
            stmt.setString(11, textoOuNull(cliente.getCep()));
            stmt.setBoolean(12, cliente.isAtivo());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException erro) {
            throw new RuntimeException("Err ao alterar cliente", erro);
        }
    }
    
    public boolean excluir(long idCliente) {
        String sql = """
                     DELETE FROM cliente
                     WHERE id_cliente = ?
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setLong(1, idCliente);
            return stmt.executeUpdate() > 0;
        } catch (SQLException erro) {
            throw new RuntimeException("Não foi possível excluir o cliente", erro);
        }
    }
    
    public Cliente buscarPorId (long idCliente) {
        String sql = """
                     SELECT * FROM cliente
                     WHERE id_cliente = ?
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            stmt.setLong(1, idCliente);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()){
                return  montarCliente(rs);
            }
        } catch (SQLException erro){
            throw new RuntimeException("Erro ao localizar cliente", erro);
        }
        
        return null;
    }
    
    public List<Cliente> listarTodos(){
        List<Cliente> clientes = new ArrayList<>();
        
        String sql = """
                     SELECT * FROM cliente
                     ORDER BY nome
                     """;
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                clientes.add(montarCliente(rs));
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao listar clientes");
        }
        return clientes;
    }
    
    public List<Cliente> pesquisar(String filtro, String pesquisa) {
        List <Cliente> clientes = new ArrayList<>();
        
        String sql;
        
        switch(filtro){
            case "ID":
                sql = """
                      SELECT * FROM cliente
                      WHERE id_cliente = ?
                      ORDER BY nome
                      """;
                break;                
            case "CPF":
                sql = """
                      SELECT * FROM cliente
                      WHERE cpf ILIKE ?
                      ORDER BY nome
                      """;
                break;
            case "EMAIL":
                sql = """
                      SELECT * FROM cliente
                      WHERE email ILIKE ?
                      ORDER BY nome
                      """;
                break;
            default:
                sql = """
                      SELECT * FROM cliente
                      WHERE nome ILIKE ?
                      ORDER BY nome
                      """;
                break;
        }
        
        try {
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
            if(filtro.equals("ID")){
                stmt.setLong(1, Long.parseLong(pesquisa));
            } else {
                stmt.setString(1,"%"+pesquisa+"%");
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()){
                clientes.add(montarCliente(rs));
            }
        } catch (SQLException erro) {
            throw new RuntimeException("Erro ao pesquisar cliente", erro);
        }
        return clientes;
    }
    
    private Cliente montarCliente(ResultSet rs)throws SQLException {
        
        Cliente cliente = new Cliente();
        
        cliente.setIdCliente(rs.getLong("id_cliente"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEmail(rs.getString("email"));
        cliente.setEndereco(rs.getString("endereco"));
        cliente.setNumero(rs.getString("numero"));
        cliente.setComplemento(rs.getString("complemento"));
        cliente.setBairro(rs.getString("bairro"));
        cliente.setCidade(rs.getString("cidade"));
        cliente.setUf(rs.getString("uf"));
        cliente.setCep(rs.getString("cep"));
        cliente.setAtivo(rs.getBoolean("ativo"));
        
        return cliente;
    }
    
    private String textoOuNull(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        return texto.trim();
    }
}

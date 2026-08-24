
package dao;

/**
 *
 * @author Eder Silva
 */


import conexao.Conexao;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario autenticar(
            String login,
            String senha
    ) {

        String sql =
                "SELECT id_usuario, nome, login, nivel, ativo "
                + "FROM usuario "
                + "WHERE login = ? "
                + "AND senha = ? "
                + "AND ativo = TRUE";

        try (
                Connection conexao = Conexao.conectar();

                PreparedStatement stmt =
                        conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(
                            rs.getLong("id_usuario")
                    );

                    usuario.setNome(
                            rs.getString("nome")
                    );

                    usuario.setLogin(
                            rs.getString("login")
                    );

                    usuario.setNivel(
                            rs.getString("nivel")
                    );

                    usuario.setAtivo(
                            rs.getBoolean("ativo")
                    );

                    return usuario;
                }
            }

        } catch (SQLException erro) {

            throw new RuntimeException(
                    "Erro ao autenticar usuário.",
                    erro
            );
        }

        return null;
    }
    
    public long cadastrar(
        Usuario usuario
) {

    String sql =
            "INSERT INTO usuario ("
            + "nome, "
            + "login, "
            + "senha, "
            + "nivel, "
            + "ativo"
            + ") VALUES (?, ?, ?, ?, ?) "
            + "RETURNING id_usuario";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getLogin());
        stmt.setString(3, usuario.getSenha());
        stmt.setString(4, usuario.getNivel());
        stmt.setBoolean(5, usuario.isAtivo());

        try (
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getLong("id_usuario");
            }
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao cadastrar usuário.",
                erro
        );
    }

    return 0;
}
    
    public boolean loginExiste(
        String login,
        long idUsuario
) {

    String sql =
            "SELECT 1 "
            + "FROM usuario "
            + "WHERE LOWER(login) = LOWER(?) "
            + "AND id_usuario <> ?";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setString(1, login);
        stmt.setLong(2, idUsuario);

        try (
                ResultSet rs =
                        stmt.executeQuery()
        ) {
            return rs.next();
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao verificar login.",
                erro
        );
    }
}
    
    public boolean alterar(
        Usuario usuario
) {

    boolean alterarSenha =
            usuario.getSenha() != null
            && !usuario
                    .getSenha()
                    .isBlank();

    String sql;

    if (alterarSenha) {

        sql =
                "UPDATE usuario SET "
                + "nome = ?, "
                + "login = ?, "
                + "senha = ?, "
                + "nivel = ?, "
                + "ativo = ? "
                + "WHERE id_usuario = ?";

    } else {

        sql =
                "UPDATE usuario SET "
                + "nome = ?, "
                + "login = ?, "
                + "nivel = ?, "
                + "ativo = ? "
                + "WHERE id_usuario = ?";
    }

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        if (alterarSenha) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getNivel());
            stmt.setBoolean(5, usuario.isAtivo());
            stmt.setLong(6, usuario.getIdUsuario());

        } else {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getNivel());
            stmt.setBoolean(4, usuario.isAtivo());
            stmt.setLong(5, usuario.getIdUsuario());
        }

        return stmt.executeUpdate() > 0;

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao alterar usuário.",
                erro
        );
    }
}
    
    public Usuario buscarPorId(
        long idUsuario
) {

    String sql =
            "SELECT "
            + "id_usuario, "
            + "nome, "
            + "login, "
            + "nivel, "
            + "ativo "
            + "FROM usuario "
            + "WHERE id_usuario = ?";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setLong(1, idUsuario);

        try (
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {
                return montarUsuario(rs);
            }
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao localizar usuário.",
                erro
        );
    }

    return null;
}
    
    private Usuario montarUsuario(
        ResultSet rs
) throws SQLException {

    Usuario usuario =
            new Usuario();

    usuario.setIdUsuario(
            rs.getLong("id_usuario")
    );

    usuario.setNome(
            rs.getString("nome")
    );

    usuario.setLogin(
            rs.getString("login")
    );

    usuario.setNivel(
            rs.getString("nivel")
    );

    usuario.setAtivo(
            rs.getBoolean("ativo")
    );

    return usuario;
}
    
    public List<Usuario> listarTodos() {

    List<Usuario> usuarios =
            new ArrayList<>();

    String sql =
            "SELECT "
            + "id_usuario, "
            + "nome, "
            + "login, "
            + "nivel, "
            + "ativo "
            + "FROM usuario "
            + "ORDER BY nome";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    stmt.executeQuery()
    ) {

        while (rs.next()) {
            usuarios.add(
                    montarUsuario(rs)
            );
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao listar usuários.",
                erro
        );
    }

    return usuarios;
}
    
    public List<Usuario> pesquisar(
        String filtro,
        String pesquisa
) {

    List<Usuario> usuarios =
            new ArrayList<>();

    String sql;

    switch (filtro) {

        case "ID":

            sql =
                    "SELECT id_usuario, nome, login, nivel, ativo "
                    + "FROM usuario "
                    + "WHERE id_usuario = ? "
                    + "ORDER BY nome";

            break;

        case "LOGIN":

            sql =
                    "SELECT id_usuario, nome, login, nivel, ativo "
                    + "FROM usuario "
                    + "WHERE login ILIKE ? "
                    + "ORDER BY nome";

            break;

        case "NIVEL":

            sql =
                    "SELECT id_usuario, nome, login, nivel, ativo "
                    + "FROM usuario "
                    + "WHERE nivel ILIKE ? "
                    + "ORDER BY nome";

            break;

        default:

            sql =
                    "SELECT id_usuario, nome, login, nivel, ativo "
                    + "FROM usuario "
                    + "WHERE nome ILIKE ? "
                    + "ORDER BY nome";

            break;
    }

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        if ("ID".equals(filtro)) {

            stmt.setLong(
                    1,
                    Long.parseLong(pesquisa)
            );

        } else {

            stmt.setString(
                    1,
                    "%" + pesquisa + "%"
            );
        }

        try (
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {
                usuarios.add(
                        montarUsuario(rs)
                );
            }
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao pesquisar usuários.",
                erro
        );
    }

    return usuarios;
}
    
    public boolean excluir(
        long idUsuario
) {

    String sql =
            "DELETE FROM usuario "
            + "WHERE id_usuario = ?";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setLong(1, idUsuario);

        return stmt.executeUpdate() > 0;

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Não foi possível excluir o usuário.",
                erro
        );
    }
}
    
    
}
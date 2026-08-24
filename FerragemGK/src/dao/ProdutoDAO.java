
package dao;

import conexao.Conexao;
import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author edual
 */
public class ProdutoDAO {
    
    public long cadastrar(
        Produto produto
) {

    String sql =
            "INSERT INTO produto ("
            + "descricao, "
            + "unidade, "
            + "preco_custo, "
            + "preco_venda, "
            + "estoque, "
            + "estoque_minimo, "
            + "ativo"
            + ") VALUES ("
            + "?, ?, ?, ?, ?, ?, ?"
            + ") "
            + "RETURNING id_produto";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setString(
                1,
                produto.getDescricao()
        );

        stmt.setString(
                2,
                produto.getUnidade()
        );

        stmt.setBigDecimal(
                3,
                produto.getPrecoCusto()
        );

        stmt.setBigDecimal(
                4,
                produto.getPrecoVenda()
        );

        stmt.setBigDecimal(
                5,
                produto.getEstoque()
        );

        stmt.setBigDecimal(
                6,
                produto.getEstoqueMinimo()
        );

        stmt.setBoolean(
                7,
                produto.isAtivo()
        );

        try (
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getLong(
                        "id_produto"
                );
            }
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao cadastrar produto.",
                erro
        );
    }

    return 0;
}
    
    public boolean alterar(
        Produto produto
) {

    String sql =
            "UPDATE produto SET "
            + "descricao = ?, "
            + "unidade = ?, "
            + "preco_custo = ?, "
            + "preco_venda = ?, "
            + "estoque = ?, "
            + "estoque_minimo = ?, "
            + "ativo = ? "
            + "WHERE id_produto = ?";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setString(
                1,
                produto.getDescricao()
        );

        stmt.setString(
                2,
                produto.getUnidade()
        );

        stmt.setBigDecimal(
                3,
                produto.getPrecoCusto()
        );

        stmt.setBigDecimal(
                4,
                produto.getPrecoVenda()
        );

        stmt.setBigDecimal(
                5,
                produto.getEstoque()
        );

        stmt.setBigDecimal(
                6,
                produto.getEstoqueMinimo()
        );

        stmt.setBoolean(
                7,
                produto.isAtivo()
        );

        stmt.setLong(
                8,
                produto.getIdProduto()
        );

        return stmt.executeUpdate() > 0;

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao alterar produto.",
                erro
        );
    }
}
    
    public boolean excluir(
        long idProduto
) {

    String sql =
            "DELETE FROM produto "
            + "WHERE id_produto = ?";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setLong(
                1,
                idProduto
        );

        return stmt.executeUpdate() > 0;

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Não foi possível excluir o produto.",
                erro
        );
    }
}
    
    private Produto montarProduto(
        ResultSet rs
) throws SQLException {

    Produto produto =
            new Produto();

    produto.setIdProduto(
            rs.getLong(
                    "id_produto"
            )
    );

    produto.setDescricao(
            rs.getString(
                    "descricao"
            )
    );

    produto.setUnidade(
            rs.getString(
                    "unidade"
            )
    );

    produto.setPrecoCusto(
            rs.getBigDecimal(
                    "preco_custo"
            )
    );

    produto.setPrecoVenda(
            rs.getBigDecimal(
                    "preco_venda"
            )
    );

    produto.setEstoque(
            rs.getBigDecimal(
                    "estoque"
            )
    );

    produto.setEstoqueMinimo(
            rs.getBigDecimal(
                    "estoque_minimo"
            )
    );

    produto.setAtivo(
            rs.getBoolean(
                    "ativo"
            )
    );

    return produto;
}
    
    public Produto buscarPorId(
        long idProduto
) {

    String sql =
            "SELECT * "
            + "FROM produto "
            + "WHERE id_produto = ?";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        stmt.setLong(
                1,
                idProduto
        );

        try (
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {

                return montarProduto(
                        rs
                );
            }
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao localizar produto.",
                erro
        );
    }

    return null;
}
    
    public List<Produto> listarTodos() {

    List<Produto> produtos =
            new ArrayList<>();

    String sql =
            "SELECT * "
            + "FROM produto "
            + "ORDER BY descricao";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    stmt.executeQuery()
    ) {

        while (rs.next()) {

            produtos.add(
                    montarProduto(
                            rs
                    )
            );
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao listar produtos.",
                erro
        );
    }

    return produtos;
}
    
    public List<Produto> pesquisar(
        String filtro,
        String pesquisa
) {

    List<Produto> produtos =
            new ArrayList<>();

    String sql;

    switch (filtro) {

        case "ID":

            sql =
                    "SELECT * "
                    + "FROM produto "
                    + "WHERE id_produto = ? "
                    + "ORDER BY descricao";

            break;

        case "UNIDADE":

            sql =
                    "SELECT * "
                    + "FROM produto "
                    + "WHERE unidade ILIKE ? "
                    + "ORDER BY descricao";

            break;

        default:

            sql =
                    "SELECT * "
                    + "FROM produto "
                    + "WHERE descricao ILIKE ? "
                    + "ORDER BY descricao";

            break;
    }

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
    ) {

        if (
                filtro.equals("ID")
        ) {

            stmt.setLong(
                    1,
                    Long.parseLong(
                            pesquisa
                    )
            );

        } else {

            stmt.setString(
                    1,
                    "%"
                    + pesquisa
                    + "%"
            );
        }

        try (
                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                produtos.add(
                        montarProduto(
                                rs
                        )
                );
            }
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao pesquisar produtos.",
                erro
        );
    }

    return produtos;
}
    
    public List<Produto> listarEstoqueBaixo() {

    List<Produto> produtos =
            new ArrayList<>();

    String sql =
            "SELECT * "
            + "FROM produto "
            + "WHERE estoque <= estoque_minimo "
            + "AND ativo = TRUE "
            + "ORDER BY descricao";

    try (
            Connection conexao =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            ResultSet rs =
                    stmt.executeQuery()
    ) {

        while (rs.next()) {

            produtos.add(
                    montarProduto(
                            rs
                    )
            );
        }

    } catch (SQLException erro) {

        throw new RuntimeException(
                "Erro ao listar produtos com estoque baixo.",
                erro
        );
    }

    return produtos;
}
    
    
    
    
}

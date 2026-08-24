
package util;

/**
 *
 * @author Eder Silva
 */
import model.Usuario;

public class SessaoUsuario {

    private static Usuario usuarioLogado;

    public static void iniciar(Usuario usuario) {

        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {

        return usuarioLogado;
    }

    public static boolean isMaster() {

        return usuarioLogado != null
                && "MASTER".equalsIgnoreCase(
                        usuarioLogado.getNivel()
                );
    }

    public static boolean isUser() {

        return usuarioLogado != null
                && "USER".equalsIgnoreCase(
                        usuarioLogado.getNivel()
                );
    }

    public static void encerrar() {

        usuarioLogado = null;
    }
}
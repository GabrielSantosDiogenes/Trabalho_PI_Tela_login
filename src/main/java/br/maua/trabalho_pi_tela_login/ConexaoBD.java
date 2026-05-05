
package br.maua.trabalho_pi_tela_login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por conectar ao banco de dados.
 * Pense nela como a tomada que liga o Java ao MySQL.
 */
public class ConexaoBD {

    // Endereço do banco (localhost = meu próprio computador)
    private static final String URL    =
        "jdbc:mysql://localhost:3306/etec_db";

    // Usuário e senha do MySQL
    private static final String USUARIO = "root";
    private static final String SENHA   = "sua_senha_aqui";

    /**
     * Método que cria e retorna a conexão.
     * Retorna null se não conseguir conectar.
     */
    public static Connection obterConexao() {
        try {
            // Tenta criar a conexão
            Connection con = DriverManager.getConnection(
                URL, USUARIO, SENHA
            );
            return con;

        } catch (SQLException e) {
            // Se der erro, mostra uma mensagem amigável
            System.out.println(
                "Erro ao conectar: " + e.getMessage()
            );
            return null;
        }
    }
}
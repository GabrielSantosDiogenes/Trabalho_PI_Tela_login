
package br.maua.trabalho_pi_tela_login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Responsável por verificar o login no banco de dados.
 */
public class Autenticacao {

    /**
     * Verifica se o RA e senha existem no banco.
     * Retorna um Usuario se encontrar, ou null se não.
     */
    public Usuario autenticar(String ra, String senha) {

        // A pergunta que fazemos ao banco
        String sql = "SELECT * FROM usuarios "
                   + "WHERE ra = ? AND senha = ?";

        try {
            // 1. Abrimos a conexão
            Connection con = ConexaoBD.obterConexao();

            // 2. Preparamos a consulta (? = valor que vem depois)
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ra);    // 1º ? = RA
            ps.setString(2, senha); // 2º ? = senha

            // 3. Executamos e pegamos o resultado
            ResultSet rs = ps.executeQuery();

            // 4. Se encontrou algum registro...
            if (rs.next()) {
                int idEncontrado = rs.getInt("id");
                String nomeEncontrado = rs.getString("nome");
                String tipoEncontrado = rs.getString("tipo");
                
                return new Usuario(idEncontrado, ra, senha, nomeEncontrado, tipoEncontrado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Não encontrou ninguém
        return null;
    }
}
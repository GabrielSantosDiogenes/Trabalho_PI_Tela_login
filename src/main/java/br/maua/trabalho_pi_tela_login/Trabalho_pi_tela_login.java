/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.maua.trabalho_pi_tela_login;

import javax.swing.SwingUtilities;

public class Trabalho_pi_tela_login {

    public static void main(String[] args) {

        // SwingUtilities garante que a janela
        // apareça na thread correta (boa prática)
        SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}

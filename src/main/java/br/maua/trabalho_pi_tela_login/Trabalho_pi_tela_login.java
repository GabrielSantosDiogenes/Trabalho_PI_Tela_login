/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.maua.trabalho_pi_tela_login;

import javax.swing.SwingUtilities;

public class Trabalho_pi_tela_login {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaBoaVinda tela = new TelaBoaVinda();
            tela.setVisible(true);
        });
    }
}

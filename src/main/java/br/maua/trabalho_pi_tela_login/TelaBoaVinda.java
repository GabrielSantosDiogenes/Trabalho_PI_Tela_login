
package br.maua.trabalho_pi_tela_login;

import javax.swing.*;
import java.awt.*;

public class TelaBoaVinda extends JFrame {

    public TelaBoaVinda() {
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("ETEC — Centro Paula Souza");
        setSize(420, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void criarComponentes() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(60, 40, 40, 40));

        // Ícone
        JLabel icone = new JLabel("🎓", SwingConstants.CENTER);
        icone.setFont(new Font("Arial", Font.PLAIN, 60));
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título
        JLabel titulo = new JLabel("ETEC");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(122, 32, 48));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel subtitulo = new JLabel("Centro Paula Souza");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(120, 120, 120));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Pergunta
        JLabel pergunta = new JLabel("Você já tem uma conta?");
        pergunta.setFont(new Font("Arial", Font.BOLD, 18));
        pergunta.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão "Já tenho conta"
        JButton btnLogin = new JButton("Já tenho conta");
        btnLogin.setBackground(new Color(122, 32, 48));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botão "Quero me cadastrar"
        JButton btnCadastro = new JButton("Quero me cadastrar");
        btnCadastro.setBackground(Color.WHITE);
        btnCadastro.setForeground(new Color(122, 32, 48));
        btnCadastro.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnCadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastro.setFocusPainted(false);
        btnCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastro.setBorder(BorderFactory.createLineBorder(new Color(122, 32, 48), 2));

        // Eventos dos botões
        btnLogin.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose(); // fecha a tela atual
        });

        btnCadastro.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });

        // Montar painel
        painel.add(icone);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(titulo);
        painel.add(Box.createRigidArea(new Dimension(0, 6)));
        painel.add(subtitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 60)));
        painel.add(pergunta);
        painel.add(Box.createRigidArea(new Dimension(0, 30)));
        painel.add(btnLogin);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(btnCadastro);

        add(painel, BorderLayout.CENTER);
    }
}

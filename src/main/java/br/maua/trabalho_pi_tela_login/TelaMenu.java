
package br.maua.trabalho_pi_tela_login;

import javax.swing.*;
import java.awt.*;

public class TelaMenu extends JFrame {

    private Usuario usuarioLogado;

    // Construtor recebe o usuário que fez login
    public TelaMenu(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("ETEC — Menu Principal");
        setSize(420, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void criarComponentes() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Saudação
        JLabel lblOla = new JLabel("Olá, " + usuarioLogado.getNome() + "!");
        lblOla.setFont(new Font("Arial", Font.BOLD, 22));
        lblOla.setForeground(new Color(122, 32, 48));
        lblOla.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("O que deseja fazer hoje?");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(120, 120, 120));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ícone
        JLabel icone = new JLabel("⚗️", SwingConstants.CENTER);
        icone.setFont(new Font("Arial", Font.PLAIN, 50));
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão Jogar
        JButton btnJogar = criarBotao("🎮  Jogar Dominó Químico", true);
        btnJogar.addActionListener(e -> {
            new TelaSelecaoNivel(usuarioLogado).setVisible(true);
            dispose();
        });

        // Botão Meu Desempenho
        JButton btnDesempenho = criarBotao("📊  Meu Desempenho", false);
        btnDesempenho.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Em breve!");
        });

        // Botão Professor (só aparece se for professor)
        JButton btnProfessor = criarBotao("👨‍🏫  Painel do Professor", false);
        btnProfessor.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Em breve!");
        });

        // Botão Sair
        JButton btnSair = new JButton("Sair");
        btnSair.setBackground(Color.WHITE);
        btnSair.setForeground(new Color(150, 150, 150));
        btnSair.setFont(new Font("Arial", Font.PLAIN, 13));
        btnSair.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.setFocusPainted(false);
        btnSair.setBorderPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> {
            new TelaBoaVinda().setVisible(true);
            dispose();
        });

        // Montar painel
        painel.add(icone);
        painel.add(Box.createRigidArea(new Dimension(0, 16)));
        painel.add(lblOla);
        painel.add(Box.createRigidArea(new Dimension(0, 6)));
        painel.add(lblSubtitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 40)));
        painel.add(btnJogar);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(btnDesempenho);

        // Só mostra o botão do professor se for professor
        if ("professor".equals(usuarioLogado.getTipo())) {
            painel.add(Box.createRigidArea(new Dimension(0, 14)));
            painel.add(btnProfessor);
        }

        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        painel.add(btnSair);

        add(painel, BorderLayout.CENTER);
    }

    private JButton criarBotao(String texto, boolean primario) {
        JButton btn = new JButton(texto);
        if (primario) {
            btn.setBackground(new Color(122, 32, 48));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(122, 32, 48));
            btn.setBorder(BorderFactory.createLineBorder(new Color(122, 32, 48), 2));
        }
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}

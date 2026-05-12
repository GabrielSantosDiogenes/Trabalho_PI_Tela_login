package br.maua.trabalho_pi_tela_login;

import javax.swing.*;
import java.awt.*;

public class TelaSelecaoNivel extends JFrame {

    private Usuario usuarioLogado;

    public TelaSelecaoNivel(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("ETEC — Selecionar Nível");
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
        painel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titulo = new JLabel("Escolha o Nível");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(122, 32, 48));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Selecione a dificuldade do jogo");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(120, 120, 120));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Card Fácil
        JPanel cardFacil = criarCard(
            "🟢  Fácil",
            "Fórmula ↔ Função química",
            "Ex: HCl → Ácido",
            new Color(230, 255, 230),
            new Color(40, 160, 40)
        );
        cardFacil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new TelaJogo(usuarioLogado, "facil").setVisible(true);
                dispose();
            }
        });

        // Card Médio
        JPanel cardMedio = criarCard(
            "?  Médio",
            "Nome ↔ Fórmula química",
            "Ex: Ácido Clorídrico → HCl",
            new Color(255, 255, 220),
            new Color(180, 140, 0)
        );
        cardMedio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new TelaJogo(usuarioLogado, "medio").setVisible(true);
                dispose();
            }
        });

        // Card Difícil
        JPanel cardDificil = criarCard(
            "🔴  Difícil",
            "Propriedade ↔ Classificação",
            "Ex: pH menor que 7 → Ácido",
            new Color(255, 230, 230),
            new Color(160, 40, 40)
        );
        cardDificil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new TelaJogo(usuarioLogado, "dificil").setVisible(true);
                dispose();
            }
        });

        // Botão Voltar
        JButton btnVoltar = new JButton("← Voltar ao Menu");
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(122, 32, 48));
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnVoltar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            new TelaMenu(usuarioLogado).setVisible(true);
            dispose();
        });

        painel.add(titulo);
        painel.add(Box.createRigidArea(new Dimension(0, 6)));
        painel.add(subtitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 30)));
        painel.add(cardFacil);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(cardMedio);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(cardDificil);
        painel.add(Box.createRigidArea(new Dimension(0, 20)));
        painel.add(btnVoltar);

        add(painel, BorderLayout.CENTER);
    }

    private JPanel criarCard(String titulo, String descricao, String exemplo,
                              Color corFundo, Color corTitulo) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(corFundo);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(corTitulo, 1),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(corTitulo);

        JLabel lblDesc = new JLabel(descricao);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(60, 60, 60));

        JLabel lblEx = new JLabel(exemplo);
        lblEx.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEx.setForeground(new Color(100, 100, 100));

        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(lblDesc);
        card.add(Box.createRigidArea(new Dimension(0, 2)));
        card.add(lblEx);

        return card;
    }
}

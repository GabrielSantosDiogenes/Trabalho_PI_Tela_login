package br.maua.trabalho_pi_tela_login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class TelaJogo extends JFrame {

    private Usuario usuarioLogado;
    private String nivel;
    private java.util.List<String[]> pecas;
    private java.util.List<String[]> cadeia;
    private JPanel painelCadeia;
    private JPanel painelMao;
    private JLabel lblAcertos;
    private JLabel lblErros;
    private JLabel lblTempo;
    private int acertos = 0;
    private int erros = 0;
    private int tempo = 0;
    private javax.swing.Timer timer;
    private String[] pecaSelecionada = null;
    private int tentativas = 0;
    private static final int MAX_ERROS = 3;
    private int cadeiaAtual = 0;

    public TelaJogo(Usuario usuarioLogado, String nivel) {
        this.usuarioLogado = usuarioLogado;
        this.nivel = nivel;
        this.pecas = new ArrayList<>();
        this.cadeia = new ArrayList<>();
        configurarJanela();
        carregarPecas();
        criarComponentes();
        iniciarTimer();
    }

    private void configurarJanela() {
        setTitle("ETEC — Dominó Químico (" + nivel.toUpperCase() + ")");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));
    }

    private void carregarPecas() {
        String sql = "SELECT lado_a, lado_b FROM pecas WHERE nivel = ?";
        try {
            Connection con = ConexaoBD.obterConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nivel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pecas.add(new String[]{rs.getString("lado_a"), rs.getString("lado_b")});
            }
            // Embaralha as peças
            Collections.shuffle(pecas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar peças: " + e.getMessage());
        }
    }

    private void criarComponentes() {
        // Painel do topo com placar
        JPanel painelTopo = new JPanel(new GridLayout(1, 4));
        painelTopo.setBackground(new Color(122, 32, 48));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblAcertos = new JLabel("✅ Acertos: 0");
        lblAcertos.setFont(new Font("Arial", Font.BOLD, 14));
        lblAcertos.setForeground(Color.WHITE);

        lblErros = new JLabel("❌ Erros: 0");
        lblErros.setFont(new Font("Arial", Font.BOLD, 14));
        lblErros.setForeground(Color.WHITE);

        lblTempo = new JLabel("⏱ Tempo: 0s");
        lblTempo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTempo.setForeground(Color.WHITE);

        JLabel lblNivel = new JLabel("Nível: " + nivel.toUpperCase());
        lblNivel.setFont(new Font("Arial", Font.BOLD, 14));
        lblNivel.setForeground(Color.WHITE);

        painelTopo.add(lblNivel);
        painelTopo.add(lblAcertos);
        painelTopo.add(lblErros);
        painelTopo.add(lblTempo);

        // Painel da cadeia (onde as peças são encaixadas)
        painelCadeia = new JPanel();
        painelCadeia.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        painelCadeia.setBackground(new Color(200, 230, 200));
        painelCadeia.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(40, 160, 40), 2),
            "Cadeia de Peças — Clique aqui para encaixar",
            0, 0, new Font("Arial", Font.BOLD, 12), new Color(40, 160, 40)
        ));
        painelCadeia.setPreferredSize(new Dimension(780, 180));

        // Clique na cadeia para encaixar a peça selecionada
        painelCadeia.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                encaixarPeca();
            }
        });

        JScrollPane scrollCadeia = new JScrollPane(painelCadeia);
        scrollCadeia.setPreferredSize(new Dimension(780, 200));

        // Painel da mão (peças disponíveis)
        painelMao = new JPanel();
        painelMao.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
        painelMao.setBackground(new Color(240, 240, 240));
        painelMao.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(122, 32, 48), 2),
            "Suas Peças — Clique para selecionar",
            0, 0, new Font("Arial", Font.BOLD, 12), new Color(122, 32, 48)
        ));

        JScrollPane scrollMao = new JScrollPane(painelMao);
        scrollMao.setPreferredSize(new Dimension(780, 280));

        // Botão voltar
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setBackground(new Color(122, 32, 48));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            timer.stop();
            salvarPartida();
            new TelaSelecaoNivel(usuarioLogado).setVisible(true);
            dispose();
        });

        JPanel painelBaixo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBaixo.setBackground(new Color(245, 245, 245));
        painelBaixo.add(btnVoltar);

        add(painelTopo, BorderLayout.NORTH);
        add(scrollCadeia, BorderLayout.CENTER);
        add(scrollMao, BorderLayout.SOUTH);
        add(painelBaixo, BorderLayout.EAST);

        atualizarMao();
    }

    private void atualizarMao() {
        painelMao.removeAll();
        for (String[] peca : pecas) {
            JPanel cartaPeca = criarCartaPeca(peca, false);
            cartaPeca.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selecionarPeca(peca, cartaPeca);
                }
            });
            painelMao.add(cartaPeca);
        }
        painelMao.revalidate();
        painelMao.repaint();
    }

    private void selecionarPeca(String[] peca, JPanel cartaPeca) {
        pecaSelecionada = peca;
        // Destaca a peça selecionada
        for (Component c : painelMao.getComponents()) {
            c.setBackground(Color.WHITE);
        }
        cartaPeca.setBackground(new Color(255, 255, 180));
        JOptionPane.showMessageDialog(this,
            "Peça selecionada: " + peca[0] + " | " + peca[1] +
            "\n\nAgora clique na área verde para encaixar!",
            "Peça Selecionada",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private void encaixarPeca() {
    if (pecaSelecionada == null) {
        JOptionPane.showMessageDialog(this,
            "Primeiro selecione uma peça!",
            "Atenção", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Primeira peça — vai direto
    if (cadeia.isEmpty()) {
        cadeia.add(new String[]{pecaSelecionada[0], pecaSelecionada[1]});
        pecas.remove(pecaSelecionada);
        acertos++;
        tentativas = 0;
        lblAcertos.setText("Acertos: " + acertos);
        atualizarCadeia();
        atualizarMao();
        pecaSelecionada = null;
        verificarCadeiaCompleta();
        return;
    }

    // Pega o último lado da cadeia
    String[] ultimaPeca = cadeia.get(cadeia.size() - 1);
    String ultimoLado = ultimaPeca[1];

    // Tenta encaixar pelo lado_a
    boolean encaixaNormal    = ultimoLado.equalsIgnoreCase(pecaSelecionada[0]);
    // Tenta encaixar pelo lado_b (invertida)
    boolean encaixaInvertido = ultimoLado.equalsIgnoreCase(pecaSelecionada[1]);

    if (encaixaNormal) {
        cadeia.add(new String[]{pecaSelecionada[0], pecaSelecionada[1]});
        confirmarEncaixe();
    } else if (encaixaInvertido) {
        cadeia.add(new String[]{pecaSelecionada[1], pecaSelecionada[0]});
        confirmarEncaixe();
    } else {
        // Errou!
        tentativas++;
        erros++;
        lblErros.setText("Erros: " + erros);

        if (tentativas >= MAX_ERROS) {
            JOptionPane.showMessageDialog(this,
                "Você errou " + MAX_ERROS + " vezes seguidas!\n" +
                "Pontuação e tela resetadas!",
                "Muitos erros!", JOptionPane.ERROR_MESSAGE);

            // Reseta TUDO
            acertos = 0;
            erros = 0;
            tempo = 0;
            tentativas = 0;
            cadeiaAtual = 0;
            lblAcertos.setText("Acertos: " + acertos);
            lblErros.setText("Erros: " + erros);
            lblTempo.setText("Tempo: " + tempo + "s");

            // Devolve peças da cadeia para a mão
            pecas.addAll(cadeia);
            cadeia.clear();
            Collections.shuffle(pecas);

            atualizarCadeia();
            atualizarMao();
        } else {
            JOptionPane.showMessageDialog(this,
                "Essa peça não encaixa aqui!\n" +
                "A cadeia termina em: " + ultimoLado + "\n" +
                "Tentativas restantes: " + (MAX_ERROS - tentativas),
                "Não encaixou!", JOptionPane.ERROR_MESSAGE);
        }
        pecaSelecionada = null;
    }
}

private void confirmarEncaixe() {
    pecas.remove(pecaSelecionada);
    acertos++;
    tentativas = 0;
    lblAcertos.setText("Acertos: " + acertos);
    atualizarCadeia();
    atualizarMao();
    pecaSelecionada = null;
    verificarCadeiaCompleta();
}
    private void atualizarCadeia() {
        painelCadeia.removeAll();
        for (String[] peca : cadeia) {
            painelCadeia.add(criarCartaPeca(peca, true));
        }
        painelCadeia.revalidate();
        painelCadeia.repaint();
    }

    private JPanel criarCartaPeca(String[] peca, boolean naCadeia) {
        JPanel carta = new JPanel(new GridLayout(3, 1));
        carta.setPreferredSize(new Dimension(110, 70));
        carta.setBackground(naCadeia ? new Color(220, 255, 220) : Color.WHITE);
        carta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(122, 32, 48), 2),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));

        JLabel lblA = new JLabel(peca[0], SwingConstants.CENTER);
        lblA.setFont(new Font("Arial", Font.BOLD, 11));
        lblA.setForeground(new Color(122, 32, 48));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(122, 32, 48));

        JLabel lblB = new JLabel(peca[1], SwingConstants.CENTER);
        lblB.setFont(new Font("Arial", Font.BOLD, 11));
        lblB.setForeground(new Color(40, 40, 120));

        carta.add(lblA);
        carta.add(sep);
        carta.add(lblB);

        return carta;
    }

    private void iniciarTimer() {
        timer = new javax.swing.Timer(1000, e -> {
            tempo++;
            lblTempo.setText("Tempo: " + tempo + "s");
        });
        timer.start();
    }

    private void verificarCadeiaCompleta() {
        // Cadeia completa quando tem 3 peças encaixadas
        if (cadeia.size() >= 3) {
            cadeiaAtual++;
            JOptionPane.showMessageDialog(this,
                "🎉 Cadeia " + cadeiaAtual + " completa!\n" +
                "Você encaixou " + cadeia.size() + " peças!\n" +
                "Continue jogando!",
                "Cadeia Completa!",
                JOptionPane.INFORMATION_MESSAGE);

            // Reseta a cadeia para começar uma nova
            cadeia.clear();
            tentativas = 0;
            atualizarCadeia();

            // Verifica se acabaram todas as peças
            if (pecas.isEmpty()) {
                verificarFimDeJogo();
            }
        }
    }
    private void verificarFimDeJogo() {
        if (pecas.isEmpty()) {
            timer.stop();
            salvarPartida();
            JOptionPane.showMessageDialog(this,
                "Parabéns! Você completou o jogo!\n" +
                "Acertos: " + acertos + "\n" +
                "Erros: " + erros + "\n" +
                "Tempo: " + tempo + " segundos",
                "Jogo Concluído!",
                JOptionPane.INFORMATION_MESSAGE);
            new TelaSelecaoNivel(usuarioLogado).setVisible(true);
            dispose();
        }
    }

    private void salvarPartida() {
        String sql = "INSERT INTO partidas (usuario_id, nivel, acertos, erros, tempo) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = ConexaoBD.obterConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuarioLogado.getId());
            ps.setString(2, nivel);
            ps.setInt(3, acertos);
            ps.setInt(4, erros);
            ps.setInt(5, tempo);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Erro ao salvar partida: " + e.getMessage());
        }
    }
}

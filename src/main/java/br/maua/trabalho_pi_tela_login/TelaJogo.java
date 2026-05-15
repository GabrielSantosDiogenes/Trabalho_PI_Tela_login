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
    private int tentativas = 0;
    private int cadeiaAtual = 0;
    private static final int MAX_ERROS = 3;
    private javax.swing.Timer timer;
    private String[] pecaSelecionada = null;
    private String[] funcoes = {"Ácido", "Base", "Sal", "Óxido"};

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

        JLabel lblNivel = new JLabel("Nível: " + nivel.toUpperCase());
        lblNivel.setFont(new Font("Arial", Font.BOLD, 14));
        lblNivel.setForeground(Color.WHITE);

        lblAcertos = new JLabel("Acertos: 0");
        lblAcertos.setFont(new Font("Arial", Font.BOLD, 14));
        lblAcertos.setForeground(Color.WHITE);

        lblErros = new JLabel("Erros: 0");
        lblErros.setFont(new Font("Arial", Font.BOLD, 14));
        lblErros.setForeground(Color.WHITE);

        lblTempo = new JLabel("Tempo: 0s");
        lblTempo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTempo.setForeground(Color.WHITE);

        painelTopo.add(lblNivel);
        painelTopo.add(lblAcertos);
        painelTopo.add(lblErros);
        painelTopo.add(lblTempo);

        // Painel da cadeia
        painelCadeia = new JPanel();
        painelCadeia.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        painelCadeia.setBackground(new Color(200, 230, 200));
        painelCadeia.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(40, 160, 40), 2),
            "Cadeia de Peças — Clique aqui para encaixar",
            0, 0, new Font("Arial", Font.BOLD, 12), new Color(40, 160, 40)
        ));

        painelCadeia.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                encaixarPeca();
            }
        });

        JScrollPane scrollCadeia = new JScrollPane(painelCadeia);
        scrollCadeia.setPreferredSize(new Dimension(780, 200));

        // Painel da mão
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

        // Botão Embaralhar
        JButton btnEmbaralhar = new JButton("Embaralhar");
        btnEmbaralhar.setBackground(new Color(40, 100, 160));
        btnEmbaralhar.setForeground(Color.WHITE);
        btnEmbaralhar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEmbaralhar.setFocusPainted(false);
        btnEmbaralhar.setBorderPainted(false);
        btnEmbaralhar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEmbaralhar.addActionListener(e -> {
            pecas.addAll(cadeia);
            cadeia.clear();
            Collections.shuffle(pecas);
            atualizarCadeia();
            atualizarMao();
            JOptionPane.showMessageDialog(this,
                "Peças embaralhadas! Tente novamente.",
                "Embaralhado!", JOptionPane.INFORMATION_MESSAGE);
        });

        // Botão Voltar
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

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(245, 245, 245));
        painelBotoes.add(btnEmbaralhar);
        painelBotoes.add(btnVoltar);

        add(painelTopo, BorderLayout.NORTH);
        add(scrollCadeia, BorderLayout.CENTER);
        add(scrollMao, BorderLayout.SOUTH);
        add(painelBotoes, BorderLayout.EAST);

        atualizarMao();
    }

    // Verifica se um lado é uma função química
    private boolean eFuncao(String lado) {
        for (String f : funcoes) {
            if (lado.equalsIgnoreCase(f)) return true;
        }
        return false;
    }

    private void encaixarPeca() {
        if (pecaSelecionada == null) {
            JOptionPane.showMessageDialog(this,
                "Primeiro selecione uma peça!",
                "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Primeira peça — garante que função fica no lado_b
        if (cadeia.isEmpty()) {
            String[] pecaOrdenada = ordenarPeca(pecaSelecionada);
            cadeia.add(pecaOrdenada);
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

        // Pega a função do último lado da cadeia
        String[] ultimaPeca = cadeia.get(cadeia.size() - 1);
        String ultimaFuncao = ultimaPeca[1]; // sempre vai ser uma função

        // Verifica se a peça tem a mesma função em algum lado
        boolean ladoAEhMesmaFuncao = pecaSelecionada[0].equalsIgnoreCase(ultimaFuncao);
        boolean ladoBEhMesmaFuncao = pecaSelecionada[1].equalsIgnoreCase(ultimaFuncao);

        if (ladoAEhMesmaFuncao || ladoBEhMesmaFuncao) {
            // Encaixou! Garante que função fica no lado_b
            String[] pecaOrdenada = ordenarPeca(pecaSelecionada);
            cadeia.add(pecaOrdenada);
            pecas.remove(pecaSelecionada);
            acertos++;
            tentativas = 0;
            lblAcertos.setText("Acertos: " + acertos);
            atualizarCadeia();
            atualizarMao();
            pecaSelecionada = null;
            verificarCadeiaCompleta();
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

                acertos = 0;
                erros = 0;
                tempo = 0;
                tentativas = 0;
                cadeiaAtual = 0;
                lblAcertos.setText("Acertos: " + acertos);
                lblErros.setText("Erros: " + erros);
                lblTempo.setText("Tempo: " + tempo + "s");

                pecas.addAll(cadeia);
                cadeia.clear();
                Collections.shuffle(pecas);
                atualizarCadeia();
                atualizarMao();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Essa peça não encaixa aqui!\n" +
                    "A cadeia termina em: " + ultimaFuncao + "\n" +
                    "Tentativas restantes: " + (MAX_ERROS - tentativas),
                    "Não encaixou!", JOptionPane.ERROR_MESSAGE);
            }
            pecaSelecionada = null;
        }
    }

    // Garante que a função sempre fica no lado_b
    private String[] ordenarPeca(String[] peca) {
        if (eFuncao(peca[1])) {
            return new String[]{peca[0], peca[1]};
        } else {
            return new String[]{peca[1], peca[0]};
        }
    }

    private void atualizarMao() {
        painelMao.removeAll();
        for (String[] peca : pecas) {
            JPanel cartaPeca = criarCartaPeca(peca, false);
            final String[] p = peca;
            cartaPeca.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selecionarPeca(p, cartaPeca);
                }
            });
            painelMao.add(cartaPeca);
        }
        painelMao.revalidate();
        painelMao.repaint();
    }

    private void selecionarPeca(String[] peca, JPanel cartaPeca) {
        pecaSelecionada = peca;
        for (Component c : painelMao.getComponents()) {
            c.setBackground(Color.WHITE);
            for (Component filho : ((JPanel)c).getComponents()) {
                filho.setBackground(Color.WHITE);
            }
        }
        cartaPeca.setBackground(new Color(255, 255, 180));
        for (Component filho : cartaPeca.getComponents()) {
            filho.setBackground(new Color(255, 255, 180));
        }
        JOptionPane.showMessageDialog(this,
            "Peça selecionada: " + peca[0] + " | " + peca[1] +
            "\n\nAgora clique na área verde para encaixar!",
            "Peça Selecionada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void atualizarCadeia() {
        painelCadeia.removeAll();
        for (int i = 0; i < cadeia.size(); i++) {
            painelCadeia.add(criarCartaPeca(cadeia.get(i), true));
            if (i < cadeia.size() - 1) {
                JLabel conector = new JLabel("—", SwingConstants.CENTER);
                conector.setFont(new Font("Arial", Font.BOLD, 20));
                conector.setForeground(new Color(122, 32, 48));
                conector.setPreferredSize(new Dimension(20, 70));
                painelCadeia.add(conector);
            }
        }
        painelCadeia.revalidate();
        painelCadeia.repaint();
    }

    private JPanel criarCartaPeca(String[] peca, boolean naCadeia) {
        JPanel carta = new JPanel(new GridLayout(1, 3));
        carta.setPreferredSize(new Dimension(180, 70));
        carta.setBorder(BorderFactory.createLineBorder(new Color(122, 32, 48), 2));
        carta.setBackground(naCadeia ? new Color(220, 255, 220) : Color.WHITE);

        JPanel ladoA = new JPanel(new BorderLayout());
        ladoA.setBackground(naCadeia ? new Color(220, 255, 220) : Color.WHITE);
        ladoA.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        JLabel lblA = new JLabel(peca[0], SwingConstants.CENTER);
        lblA.setFont(new Font("Arial", Font.BOLD, 11));
        lblA.setForeground(new Color(122, 32, 48));
        ladoA.add(lblA, BorderLayout.CENTER);

        JPanel divisoria = new JPanel();
        divisoria.setBackground(new Color(122, 32, 48));
        divisoria.setPreferredSize(new Dimension(3, 70));

        JPanel ladoB = new JPanel(new BorderLayout());
        ladoB.setBackground(naCadeia ? new Color(220, 255, 220) : Color.WHITE);
        ladoB.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        JLabel lblB = new JLabel(peca[1], SwingConstants.CENTER);
        lblB.setFont(new Font("Arial", Font.BOLD, 11));
        lblB.setForeground(new Color(40, 40, 120));
        ladoB.add(lblB, BorderLayout.CENTER);

        carta.add(ladoA);
        carta.add(divisoria);
        carta.add(ladoB);

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
        if (cadeia.size() >= 3) {
            cadeiaAtual++;
            JOptionPane.showMessageDialog(this,
                "Cadeia " + cadeiaAtual + " completa!\n" +
                "Você encaixou " + cadeia.size() + " peças!\n" +
                "Continue jogando!",
                "Cadeia Completa!", JOptionPane.INFORMATION_MESSAGE);
            cadeia.clear();
            tentativas = 0;
            atualizarCadeia();
            if (pecas.isEmpty()) verificarFimDeJogo();
        }
    }

    private void verificarFimDeJogo() {
        timer.stop();
        salvarPartida();
        JOptionPane.showMessageDialog(this,
            "Parabéns! Você completou o jogo!\n\n" +
            "Cadeias formadas: " + cadeiaAtual + "\n" +
            "Acertos: " + acertos + "\n" +
            "Erros: " + erros + "\n" +
            "Tempo: " + tempo + " segundos",
            "Jogo Concluído!", JOptionPane.INFORMATION_MESSAGE);
        new TelaSelecaoNivel(usuarioLogado).setVisible(true);
        dispose();
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

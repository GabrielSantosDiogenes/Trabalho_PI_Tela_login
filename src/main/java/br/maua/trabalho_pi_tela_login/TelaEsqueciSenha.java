
package br.maua.trabalho_pi_tela_login;



import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TelaEsqueciSenha extends JFrame {

    private JTextField campoRA;
    private JButton    btnBuscar;
    private JButton    btnVoltar;

    public TelaEsqueciSenha() {
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("ETEC — Esqueci minha senha");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void criarComponentes() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(50, 40, 40, 40));

        JLabel titulo = new JLabel("Esqueci minha senha");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Digite seu RA para recuperar sua senha.");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(120, 120, 120));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblRA = new JLabel("Registro do Aluno (RA)");
        lblRA.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRA.setForeground(new Color(60, 60, 60));
        lblRA.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoRA = new JTextField();
        campoRA.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campoRA.setPreferredSize(new Dimension(340, 44));
        campoRA.setBackground(new Color(240, 240, 240));
        campoRA.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)));
        campoRA.setFont(new Font("Arial", Font.PLAIN, 13));
        campoRA.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnBuscar = new JButton("Buscar senha");
        btnBuscar.setBackground(new Color(122, 32, 48));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVoltar = new JButton("← Voltar para o login");
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(122, 32, 48));
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnVoltar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnVoltar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBuscar.addActionListener(e -> buscarSenha());
        btnVoltar.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });

        painel.add(titulo);
        painel.add(Box.createRigidArea(new Dimension(0, 6)));
        painel.add(subtitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 30)));
        painel.add(lblRA);
        painel.add(Box.createRigidArea(new Dimension(0, 6)));
        painel.add(campoRA);
        painel.add(Box.createRigidArea(new Dimension(0, 24)));
        painel.add(btnBuscar);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(btnVoltar);

        add(painel, BorderLayout.CENTER);
    }

    private void buscarSenha() {
        String ra = campoRA.getText().trim();

        if (ra.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Digite seu RA!",
                "Campo obrigatório",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT nome, senha FROM usuarios WHERE ra = ?";

        try {
            Connection con = ConexaoBD.obterConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ra);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nome  = rs.getString("nome");
                String senha = rs.getString("senha");
                JOptionPane.showMessageDialog(this,
                    "Olá, " + nome + "!\nSua senha é: " + senha,
                    "Senha encontrada",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Nenhum usuário encontrado com esse RA.",
                    "RA não encontrado",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao buscar senha: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

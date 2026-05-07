
package br.maua.trabalho_pi_tela_login;


import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TelaCadastro extends JFrame {

    private JTextField     campoRA;
    private JTextField     campoNome;
    private JTextField     campoTurma;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmarSenha;
    private JButton        btnCadastrar;
    private JButton        btnVoltar;

    public TelaCadastro() {
        configurarJanela();
        criarComponentes();
    }

    private void configurarJanela() {
        setTitle("ETEC — Cadastro");
        setSize(420, 620);
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

        JLabel titulo = new JLabel("Criar conta");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Preencha os dados para se cadastrar.");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(120, 120, 120));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Campos
        JLabel lblRA = criarLabel("Registro do Aluno (RA)");
        campoRA = new JTextField();
        estilizarCampo(campoRA);

        JLabel lblNome = criarLabel("Nome completo");
        campoNome = new JTextField();
        estilizarCampo(campoNome);

        JLabel lblTurma = criarLabel("Turma/Curso");
        campoTurma = new JTextField();
        estilizarCampo(campoTurma);

        JLabel lblSenha = criarLabel("Senha");
        campoSenha = new JPasswordField();
        estilizarCampo(campoSenha);

        JLabel lblConfirmar = criarLabel("Confirmar senha");
        campoConfirmarSenha = new JPasswordField();
        estilizarCampo(campoConfirmarSenha);

        // Botão Cadastrar
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(122, 32, 48));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnCadastrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botão Voltar
        btnVoltar = new JButton("← Voltar");
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(122, 32, 48));
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnVoltar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnVoltar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Eventos
        btnCadastrar.addActionListener(e -> realizarCadastro());
        btnVoltar.addActionListener(e -> {
            new TelaBoaVinda().setVisible(true);
            dispose();
        });

        // Montar painel
        painel.add(titulo);
        painel.add(Box.createRigidArea(new Dimension(0, 6)));
        painel.add(subtitulo);
        painel.add(Box.createRigidArea(new Dimension(0, 24)));
        painel.add(lblRA);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(campoRA);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(lblNome);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(campoNome);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(lblTurma);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(campoTurma);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(lblSenha);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(campoSenha);
        painel.add(Box.createRigidArea(new Dimension(0, 14)));
        painel.add(lblConfirmar);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(campoConfirmarSenha);
        painel.add(Box.createRigidArea(new Dimension(0, 24)));
        painel.add(btnCadastrar);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(btnVoltar);

        add(painel, BorderLayout.CENTER);
    }

    private void realizarCadastro() {
        String ra             = campoRA.getText().trim();
        String nome           = campoNome.getText().trim();
        String turma          = campoTurma.getText().trim();
        String senha          = new String(campoSenha.getPassword()).trim();
        String confirmarSenha = new String(campoConfirmarSenha.getPassword()).trim();

        // Validações
        if (ra.isEmpty() || nome.isEmpty() || turma.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos!",
                "Campos obrigatórios",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this,
                "As senhas não coincidem!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Salvar no banco
        String sql = "INSERT INTO usuarios (ra, nome, turma, senha) VALUES (?, ?, ?, ?)";

        try {
            Connection con = ConexaoBD.obterConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ra);
            ps.setString(2, nome);
            ps.setString(3, turma);
            ps.setString(4, senha);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Cadastro realizado com sucesso!\nBem-vindo, " + nome + "!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

            // Vai para a tela de login
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
            dispose();

        } catch (SQLIntegrityConstraintViolationException e) {
            // RA já cadastrado
            JOptionPane.showMessageDialog(this,
                "Este RA já está cadastrado!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao cadastrar: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel criarLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setForeground(new Color(60, 60, 60));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campo.setPreferredSize(new Dimension(340, 44));
        campo.setBackground(new Color(240, 240, 240));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}


package br.maua.trabalho_pi_tela_login;
import javax.swing.*;
import java.awt.*;

/**
 * Tela de Login — a janela que o usuário vê.
 * Herda de JFrame (é um JFrame especializado).
 */
public class TelaLogin extends JFrame {

    // Componentes da tela
    private JTextField     campoRA;
    private JPasswordField  campoSenha;
    private JButton        btnEntrar;

    // Construtor — monta a janela quando chamado
    public TelaLogin() {
        configurarJanela();
        criarComponentes();
        adicionarEventos();
    }

    private void configurarJanela() {
        setTitle("ETEC — Centro Paula Souza");
        setSize(420, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centralizar
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void criarComponentes() {
        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory
            .createEmptyBorder(40, 40, 40, 40));

        // Ícone e título
        JLabel titulo = new JLabel("Bem-vindo de volta");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel(
            "Insira suas credenciais para acessar o sistema."
        );
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(120, 120, 120));
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Campo RA
        JLabel lblRA = criarLabel("Registro do Aluno (RA)");
        campoRA = new JTextField();
        estilizarCampo(campoRA, "Digite seu RA");
        
        // Cria o campoSenha ANTES do painelSenha
        campoSenha = new JPasswordField();
        estilizarCampo(campoSenha, "••••••••");
        
        // Campo Senha
        JPanel painelSenha = new JPanel(new BorderLayout());
        painelSenha.setBackground(Color.WHITE);
        painelSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        painelSenha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSenha = criarLabel("Senha");
        JLabel lblEsqueci = new JLabel("Esqueci minha senha");
        lblEsqueci.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEsqueci.setForeground(new Color(122, 32, 48));
        lblEsqueci.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblEsqueci.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new TelaEsqueciSenha().setVisible(true);
                dispose();
            }
        });

        painelSenha.add(lblSenha, BorderLayout.WEST);
        painelSenha.add(lblEsqueci, BorderLayout.EAST);

        // Botão Entrar
        btnEntrar = new JButton("→  Entrar");
        estilizarBotao(btnEntrar);

        // Adicionar tudo ao painel
        painel.add(titulo);
        painel.add(Box.createRigidArea(new Dimension(0,6)));
        painel.add(subtitulo);
        painel.add(Box.createRigidArea(new Dimension(0,28)));
        painel.add(lblRA);
        painel.add(Box.createRigidArea(new Dimension(0,6)));
        painel.add(campoRA);
        painel.add(Box.createRigidArea(new Dimension(0,18)));
        painel.add(painelSenha);
        painel.add(Box.createRigidArea(new Dimension(0,6)));
        painel.add(campoSenha);
        painel.add(Box.createRigidArea(new Dimension(0,28)));
        painel.add(btnEntrar);

        add(painel, BorderLayout.CENTER);
    }

    private void adicionarEventos() {
        btnEntrar.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String ra    = campoRA.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();

        // Validação básica
        if (ra.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Preencha o RA e a senha!",
                "Campos obrigatórios",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Tenta autenticar no banco
        Autenticacao dao = new Autenticacao();
        Usuario usuario = dao.autenticar(ra, senha);

        if (usuario != null) {
            JOptionPane.showMessageDialog(
                this,
                "Olá, " + usuario.getNome() + "! Login realizado.",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
            new TelaMenu(usuario).setVisible(true); // ← muda para TelaMenu
            dispose(); 
            // Aqui você abriria a próxima tela
        } else {
            JOptionPane.showMessageDialog(
                this,
                "RA ou senha incorretos.",
                "Erro de autenticação",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Métodos auxiliares de estilo
    private JLabel criarLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setForeground(new Color(60, 60, 60));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private void estilizarCampo(JTextField campo, String hint) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        campo.setPreferredSize(new Dimension(340, 44));
        campo.setBackground(new Color(240, 240, 240));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220,220,220)),
            BorderFactory.createEmptyBorder(0, 12, 0, 12)
        ));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void estilizarBotao(JButton btn) {
        btn.setBackground(new Color(122, 32, 48));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}


package br.maua.trabalho_pi_tela_login;
/**
 * Classe que representa um Usuário (aluno) do sistema.
 * Em OOP, isso se chama "Modelo" ou "Entidade".
 */
public class Usuario {

    // Atributos privados (ninguém acessa direto)
    private String ra;
    private String senha;
    private String nome;

    // Construtor — chamado quando criamos um novo Usuario
    public Usuario(String ra, String senha, String nome) {
        this.ra    = ra;
        this.senha = senha;
        this.nome  = nome;
    }

    // Getters — formas de LER os atributos privados
    public String getRa()    { return ra; }
    public String getSenha() { return senha; }
    public String getNome()  { return nome; }

    // Setters — formas de ALTERAR os atributos
    public void setNome(String nome) {
        this.nome = nome;
    }
}
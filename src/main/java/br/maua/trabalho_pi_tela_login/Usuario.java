
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
    private String tipo;
    private int id;

    // Construtor — chamado quando criamos um novo Usuario
    public Usuario(int id, String ra, String senha, String nome, String tipo) {
        this.id = id;
        this.ra    = ra;
        this.senha = senha;
        this.nome  = nome;
        this.tipo = tipo;
    }

    // Getters — formas de LER os atributos privados
    public String getRa(){ 
        return ra; 
    }
    public String getSenha(){
        return senha; 
    }
    public String getNome(){
        return nome; 
    }
    public String getTipo(){
        return tipo;
    }
    public int getId(){
        return id;
    }

    // Setters — formas de ALTERAR os atributos
    public void setNome(String nome) {
        this.nome = nome;
    }
}
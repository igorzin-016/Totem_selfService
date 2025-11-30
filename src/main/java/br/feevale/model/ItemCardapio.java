package br.feevale.model;

public abstract class ItemCardapio implements VendaInterface, java.io.Serializable {
    private static final long serialVersionUID = 1L; // implementado como segurança entre o arquivo a ser salvo


    protected String nome;
    protected double preco;

    public ItemCardapio(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public abstract double getPrecoFinal(); // polimorfismo implementado aqui

    public String getNome() { 
        return nome; 
    }
}
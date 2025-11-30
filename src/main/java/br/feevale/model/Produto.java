package br.feevale.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Produto extends ItemCardapio implements Serializable{
    private static final long serialVersionUID = 1L; // Boa prática para evitar erros de versão


    
    //Lista de itens dentro do produto (composição e herança)
    private List<ItemCardapio> adicionais = new ArrayList<>();

    public Produto(String nome, double preco) {
        super(nome, preco);
    }

    public void adicionarExtra(ItemCardapio extra) {
        this.adicionais.add(extra);
    }

    @Override
    public double getPrecoFinal() {
        double total = this.preco;
        for (ItemCardapio item : adicionais) {
            total += item.getPrecoFinal();
        }
        return total;
    }
    
    @Override
    public String toString() { 
        return getNome(); 
    }
}
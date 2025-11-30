package br.feevale.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable{

    private static final long serialVersionUID = 1L;

    private static int contador = 1;
    private int numero;
    private List<Produto> itens = new ArrayList<>();
    private StatusPedido status;

    public Pedido() {
        this.numero = contador++;
        this.status = StatusPedido.PENDENTE;
    }

    public void adicionarItem(Produto p) { 
        itens.add(p); 
    }
    
    public double getTotal() {
        return itens.stream().mapToDouble(Produto::getPrecoFinal).sum(); // o stream inicia o carrinho, pega o preço de cada item e no final soma tudo
    }

    public int getNumero() { 
        return numero; 
    }

    public StatusPedido getStatus() { 
        return status; 
    }

    public void setStatus(StatusPedido status) { 
        this.status = status; 
    }

    public List<Produto> getItens() { 
        return itens; 
    }
    
    @Override
    public String toString() {
        return "#" + numero + " - " + status + " (R$ " + getTotal() + ")";
    }
}
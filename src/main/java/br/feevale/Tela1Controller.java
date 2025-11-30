package br.feevale;

import java.io.IOException;

import br.feevale.model.Pedido;
import br.feevale.model.Produto;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Tela1Controller {

    @FXML private ListView<String> listaCarrinho;
    @FXML private Label lblTotal;
    @FXML private VBox painelProdutos;

    private Pedido pedidoAtual = new Pedido();

    @FXML
    public void initialize() {
        carregarProdutos("todos");
    }


    // as opções
    @FXML void filtrarTodos() { 
        carregarProdutos("todos"); 
    }
    @FXML void filtrarBebidas() { 
        carregarProdutos("bebidas"); 
    }
    @FXML void filtrarBurgers() { 
        carregarProdutos("burgers"); 
    }
    @FXML void filtrarSobremesas() {
        carregarProdutos("sobremesa");
    }


    private void carregarProdutos(String categoria) {
        painelProdutos.getChildren().clear();

        // adicionando bebidas
        if (categoria.equals("todos") || categoria.equals("bebidas")) {
            painelProdutos.getChildren().add(criarCardProduto("Água c/ Gás 500ml", 5.00, "Garrafa 500ml", "imagens/agua2.png"));

            painelProdutos.getChildren().add(criarCardProduto("Chopp Pilsen 400ml", 15.00, "Artesanal IBU12", "imagens/chopp.jpg"));

            painelProdutos.getChildren().add(criarCardProduto("Coca cola 600ml",7.00, "Coca cola 600ml", "imagens/coca.png"));
        }
        // adicionando hamburguer
        if (categoria.equals("todos") || categoria.equals("burgers")) {
            painelProdutos.getChildren().add(criarCardProduto("Burger Mas Bah Salad", 30.00, "Pão cacetinho, blend 180g de costela suculento assado no fogo, cheddar, alface, tomate e cebola roxa", "imagens/ham_salada.png"));

            painelProdutos.getChildren().add(criarCardProduto("Burger Mas Bah Bacon", 30.00, "Pão cacetinho, blend 180g de costela suculento assado no fogo, cheddar e bacon mas bah", "imagens/ham_bacon.png"));
        }
        // adicionando sobremesas
        if (categoria.equals("todos") || categoria.equals("sobremesa")){
            painelProdutos.getChildren().add(criarCardProduto("Trufa na taça 300g", 32.00, "Morangos picados com ganache duplo, branco e ao leite", "imagens/taca_truf.jpeg"));

            painelProdutos.getChildren().add(criarCardProduto("Trufa chocolate branco na taça 150g", 18.00, "Trufa chocolate branco na taça 150g", "imagens/taca_truf_cb.jpg"));

            painelProdutos.getChildren().add(criarCardProduto("Petit Gateau", 28.00, "Bolinho recheado de chocolate, sorvete e calda de chocolate quente", "imagens/petit_ga.jpeg"));
        }


    }

    // criar o visual de cada produto
    private HBox criarCardProduto(String nome, double preco, String descricao, String caminhoImagem) {
        HBox card = new HBox();
        card.setStyle("-fx-border-color: #ddd; -fx-border-width: 0 0 1 0; -fx-padding: 10; -fx-background-color: white;");
        card.setSpacing(20);
        card.setAlignment(Pos.CENTER_LEFT);

        // foto do produto
        ImageView imageView = new ImageView();
        try {
            // O getResource() procura o arquivo na pasta resources do projeto
            Image img = new Image(getClass().getResourceAsStream(caminhoImagem)); 
            imageView.setImage(img);
            imageView.setFitWidth(80); // Define um tamanho pequeno para o card
            imageView.setFitHeight(80);
        } catch (Exception e) {
            // Se a imagem não for achada, mostra uma mensagem no console
            System.err.println("Aviso: Imagem não encontrada no caminho: " + caminhoImagem);
            // substitui a foto lá
            Label placeholder = new Label("[ SEM FOTO ]");
            placeholder.setPrefSize(80, 80);
            placeholder.setStyle("-fx-border-color: #aaa; -fx-alignment: center;");
            card.getChildren().add(placeholder);
        }
        
        // Coluna da Esquerda (Nome e Preço)
        VBox info = new VBox(5);
        Label lblNome = new Label(nome);
        lblNome.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
        
        Label lblDesc = new Label(descricao);
        lblDesc.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        Label lblPreco = new Label("R$ " + String.format("%.2f", preco));
        lblPreco.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #E69B00;"); // Cor laranja do preço

        info.getChildren().addAll(lblNome, lblDesc, lblPreco);

        // Botão de Adicionar
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        
        Button btnAdd = new Button("ADICIONAR");
        btnAdd.setStyle("-fx-background-color: #E69B00; -fx-text-fill: white; -fx-cursor: hand;");
        btnAdd.setOnAction(e -> {
            Produto p = new Produto(nome, preco);
            pedidoAtual.adicionarItem(p);
            atualizarCarrinho();
        });

        // Adiciona a Imagem e o resto
        card.getChildren().addAll(imageView, info, espaco, btnAdd); 
        return card;
    }

    // --- Métodos do Carrinho e Finalização (salvos no arquivos .bin lá) ---
    private void atualizarCarrinho() {
        listaCarrinho.getItems().clear();
        for (Produto p : pedidoAtual.getItens()) {
            listaCarrinho.getItems().add(p.getNome() + " - R$ " + p.getPrecoFinal());
        }
        lblTotal.setText("Total: R$ " + String.format("%.2f", pedidoAtual.getTotal()));
    }

    @FXML
    private void remItem(){
        int indiceSelecionado = listaCarrinho.getSelectionModel().getSelectedIndex();

        if (indiceSelecionado >=0){
            pedidoAtual.getItens().remove(indiceSelecionado);
            atualizarCarrinho();
        } else {
            // Mantida a lógica original de alerta
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atenção");
            alerta.setHeaderText("Nenhum item selecionado!");
            alerta.setContentText("Por favor, clique em um lanche da lista antes de tentar remover.");
            alerta.showAndWait();
        }
    }
    
    @FXML
    private void finalizarPedido() {
        if (pedidoAtual.getItens().isEmpty()) {
            return; // Não envia pedido vazio
        }

        // CHAVE PARA SALVAR O PEDIDO NO SISTEMA/ARQUIVO
        DadosSistema.adicionarPedido(pedidoAtual);

        // Alerta de confirmação
        Alert alertaDePedidoEnviado = new Alert(Alert.AlertType.INFORMATION);
        alertaDePedidoEnviado.setTitle("Sucesso");
        alertaDePedidoEnviado.setHeaderText("Pedido #" + pedidoAtual.getNumero() + " Enviado para Cozinha!");
        alertaDePedidoEnviado.showAndWait();

        // Reinicia o carrinho para o próximo cliente
        pedidoAtual = new Pedido();
        atualizarCarrinho();
    }

    @FXML
    private void irParaCozinha() throws IOException {
        App.setRoot("tela2");
    }
}
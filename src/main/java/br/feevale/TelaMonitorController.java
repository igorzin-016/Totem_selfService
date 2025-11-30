package br.feevale;

import br.feevale.model.Pedido;
import br.feevale.model.StatusPedido;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Duration;

public class TelaMonitorController {

    @FXML private ListView<String> listaPreparo;
    @FXML private ListView<String> listaPronto;

    @FXML
    public void initialize() {
        // Atualiza a primeira vez
        atualizarTela();

        // Cria um timer para atualizar a tela a cada 2 segundos, pra ficar mais próximo de real time
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> atualizarTela())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void atualizarTela() {
        listaPreparo.getItems().clear();
        listaPronto.getItems().clear();

        for (Pedido p : DadosSistema.getPedidos()) {
            if (p.getStatus() == StatusPedido.PREPARANDO) {
                // Mostra só o número ou nome do cliente
                listaPreparo.getItems().add("SENHA #" + p.getNumero());
            } 
            else if (p.getStatus() == StatusPedido.PRONTO) {
                listaPronto.getItems().add("SENHA #" + p.getNumero());
            }
        }
    }
}
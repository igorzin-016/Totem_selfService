package br.feevale;

import java.io.IOException;

import br.feevale.model.Pedido;
import br.feevale.model.StatusPedido;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Tela2Controller {

    @FXML private ListView<Pedido> listaPendentes;
    @FXML private ListView<Pedido> listaPreparo;
    @FXML private ListView<Pedido> listaPronto;

    @FXML
    public void initialize() {
        atualizarListas();
    }

    @FXML
    private void irParaTotem() throws IOException {
        App.setRoot("tela1");
    }

    // monitor que avisa do pedido pronto
    @FXML
    private void abrirMonitorCliente() {
        try {
            // Tenta achar o arquivo
            java.net.URL url = App.class.getResource("telaMonitor.fxml");
            if (url == null) url = App.class.getResource("/telaMonitor.fxml");
            
            if (url == null) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("Arquivo não encontrado");
                alerta.setContentText("Não achei o telaMonitor.fxml!");
                alerta.showAndWait();
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(url);
            
            Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
            
            Stage stage = new Stage();
            stage.setTitle("Monitor de Pedidos - Mas Bah Tchê");
            stage.setScene(scene);
            
            stage.setMaximized(false);

            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    

    private void atualizarListas() {
        listaPendentes.getItems().clear();
        listaPreparo.getItems().clear();
        listaPronto.getItems().clear();

        for (Pedido p : DadosSistema.getPedidos()) {
            if (p.getStatus() == StatusPedido.PENDENTE) listaPendentes.getItems().add(p);
            else if (p.getStatus() == StatusPedido.PREPARANDO) listaPreparo.getItems().add(p);
            else if (p.getStatus() == StatusPedido.PRONTO) listaPronto.getItems().add(p);
        }
    }

    @FXML
    private void moverParaPreparo() {
        Pedido p = listaPendentes.getSelectionModel().getSelectedItem();
        if (p != null) {
            p.setStatus(StatusPedido.PREPARANDO);
            DadosSistema.atualizarBanco(); // ATUALIZANDO
            atualizarListas();
        }
    }

    

    @FXML
    private void moverParaPronto() {
        Pedido p = listaPreparo.getSelectionModel().getSelectedItem();
        if (p != null) {
            p.setStatus(StatusPedido.PRONTO);
            DadosSistema.atualizarBanco();
            atualizarListas();
        }
    }
    
    @FXML
    private void entregarPedido() {
        Pedido p = listaPronto.getSelectionModel().getSelectedItem();
        if (p != null) {
            DadosSistema.getPedidos().remove(p); 
            DadosSistema.atualizarBanco();
            atualizarListas();
        }
    }
}
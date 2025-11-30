package br.feevale;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;


public class Tela3Controller {
    @FXML
    private void iniciarPedido(MouseEvent event) throws IOException{
        App.setRoot("tela1");
    }
}

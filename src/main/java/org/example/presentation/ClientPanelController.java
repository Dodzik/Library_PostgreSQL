package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientPanelController {


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToBooksView(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("listBooksView.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void idzDoListyRezerwacji(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("widokListyRezerwacji.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoInformacji(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("clientInfoPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

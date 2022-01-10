package org.example.presentation;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToClient(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("clientLogPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToAdmin(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminLogPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


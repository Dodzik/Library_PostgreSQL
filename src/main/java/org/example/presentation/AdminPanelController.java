package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanelController {




    private Stage stage;
    private Scene scene;
    private Parent root;

    public void idzDoListyKlientow(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminListaKlientow.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void idzDoInformacji(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminInfoPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoRezerwacji(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminRezerwacjePage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoAutorzy(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminAutorzyPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoKsiazki(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminKsiazkiPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoStanowisk(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminStanowiskaPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoDane(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminDanePage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoWypozyczenia(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminWypozyczeniaPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoAutorzyKsiazek(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminAutorzyKsiazkiPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoWydawnictwa(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminWydawnictwaPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoGatunki(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminGatunkiPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void idzDoPracownicy(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminPracownicyPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

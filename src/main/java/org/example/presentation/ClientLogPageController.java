package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.domain.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ClientLogPageController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txthaslo;

//    @FXML
//    private ListView<Klient> klientList;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        klientList.getItems().addAll(persistenceHandler.getKlienci());
//    }

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    public void clientLogIn(ActionEvent actionEvent) throws IOException {

        if (logIn()){
//                root = FXMLLoader.load(getClass().getResource("klientPanel.fxml"));\


                root = FXMLLoader.load(getClass().getResource("klientPanel.fxml"));
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }
        else {
            txtemail.setText("");
            txtemail.setPromptText("ZLY LOGIN LUB HASLO!");
            txthaslo.setText("");
            txthaslo.setPromptText("ZLY LOGIN LUB HASLO!");
        }

    }
    private boolean logIn(){
        String email = txtemail.getText();
        String haslo = txthaslo.getText();
        return persistenceHandler.checkClient(email,haslo);
    }


    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

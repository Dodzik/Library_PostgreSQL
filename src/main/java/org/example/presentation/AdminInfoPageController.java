package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminInfoPageController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TextField txtid;
    @FXML
    public TextField txtlogin;
    @FXML
    public TextField txtstanowisko;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Pracownik pracownik = persistenceHandler.getPracownkInformacje();
        Stanowisko stanowisko = persistenceHandler.getStanowiskoPracownik(pracownik.getId());

        txtid.setText( pracownik.getId()+"");
        txtlogin.setText(pracownik.getLogin());
        txtstanowisko.setText(stanowisko.getNazwa());

    }
}

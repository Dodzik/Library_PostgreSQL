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
import org.example.domain.Dane;
import org.example.domain.IPersistenceHandler;
import org.example.domain.Klient;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientInfoPageController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TextField email;
    @FXML
    public TextField imie;
    @FXML
    public TextField nazwisko;
    @FXML
    public TextField miasto;
    @FXML
    public TextField ulica;
    @FXML
    public TextField nrDomu;
    @FXML
    public TextField kodPocztowy;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("klientPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Klient klient = persistenceHandler.getKlientInformacje();
        Dane dane = persistenceHandler.getKlientDane(klient.getId_dane());

        email.setText( klient.getEmail());
        imie.setText( klient.getName());
        nazwisko.setText( klient.getSurname());
        miasto.setText( dane.getMiasto());
        ulica.setText( dane.getUlica());
        nrDomu.setText( dane.getNr_domu());
        kodPocztowy.setText( dane.getKod_pocztowy());
    }

}

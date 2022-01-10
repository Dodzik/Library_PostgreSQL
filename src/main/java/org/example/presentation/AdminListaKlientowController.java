package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.IPersistenceHandler;
import org.example.domain.Klient;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminListaKlientowController implements Initializable {
    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();


    @FXML
    private TableView<Klient> klientTableView;

    @FXML
    private TableColumn<Klient,Integer> id;

    @FXML
    private TableColumn<Klient,Integer> id_dane;

    @FXML
    private TableColumn<Klient,String> imie;

    @FXML
    private TableColumn<Klient,String> nazwisko;

    @FXML
    private TableColumn<Klient,String> email;

    @FXML
    private TableColumn<Klient,String> haslo;

    ObservableList<Klient> list = FXCollections.observableList(
            persistenceHandler.getKlienci()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<Klient,Integer>("id"));
        id_dane.setCellValueFactory(new PropertyValueFactory<>("id_dane"));
        imie.setCellValueFactory(new PropertyValueFactory<Klient,String>("name"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Klient,String>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<Klient,String>("email"));
        haslo.setCellValueFactory(new PropertyValueFactory<Klient,String>("haslo"));

        klientTableView.setItems(list);

    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("clientPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

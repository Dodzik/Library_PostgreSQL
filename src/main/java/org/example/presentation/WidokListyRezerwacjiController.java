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
import org.example.domain.Book;
import org.example.domain.IPersistenceHandler;
import org.example.domain.Rezerwacja;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class WidokListyRezerwacjiController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private TableView<Rezerwacja> rezerwacjeTableView;

    @FXML
    private TableColumn<Rezerwacja,String> ksiazka;

    @FXML
    private TableColumn<Rezerwacja,String> imie;

    @FXML
    private TableColumn<Rezerwacja,String> nazwisko;

    @FXML
    private TableColumn<Rezerwacja, Date> date;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("clientPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Rezerwacja> list = FXCollections.observableList(
            persistenceHandler.getRezerwacjeKlient()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rezerwacjeTableView.getItems().addAll(persistenceHandler.getRezerwacjeKlient());

        ksiazka.setCellValueFactory(new PropertyValueFactory<Rezerwacja,String>("ksiazka"));
        imie.setCellValueFactory(new PropertyValueFactory<Rezerwacja,String>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Rezerwacja,String>("nazwisko"));
        date.setCellValueFactory(new PropertyValueFactory<Rezerwacja,Date>("date"));

        rezerwacjeTableView.setItems(list);

    }
}

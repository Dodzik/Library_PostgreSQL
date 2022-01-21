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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KlientKsiazkiPageController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private TableView<Ksiazka> booksTableView;

    @FXML
    private TableColumn<Ksiazka,Integer> id;

    @FXML
    private TableColumn<Ksiazka,Integer> gatunek;

    @FXML
    private TableColumn<Ksiazka,Integer> wydawnictwo;

    @FXML
    private TableColumn<Ksiazka,String> tytul;

    @FXML
    private TableColumn<Ksiazka,Integer> liczbaStron;

    @FXML
    private TableColumn<Ksiazka,String> opis;

    @FXML
    public TextField szukane;

    ObservableList<Ksiazka> list = FXCollections.observableList(
           persistenceHandler.getKsiazki()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        gatunek.setCellValueFactory(new PropertyValueFactory<>("gatunek_id"));
        wydawnictwo.setCellValueFactory(new PropertyValueFactory<>("wydawnictwo_id"));
        tytul.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        liczbaStron.setCellValueFactory(new PropertyValueFactory<>("liczbaStron"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));

        booksTableView.setItems(list);
        updateUI();

    }
    public void sortKsiazki(){
        list=FXCollections.observableList(persistenceHandler.sortKsiazki(szukane.getText()));
        booksTableView.setItems(list);
        updateUI();
    }

    private void updateUI(){

        gatunek.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer gatunek_id, boolean empty) {
                super.updateItem(gatunek_id, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(persistenceHandler.getGatunekId(gatunek_id));
                }
            }
        });

        wydawnictwo.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer wydawnictwo_id, boolean empty) {
                super.updateItem(wydawnictwo_id, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(persistenceHandler.getWydawnictwoById(wydawnictwo_id));
                }
            }
        });

    }

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


}

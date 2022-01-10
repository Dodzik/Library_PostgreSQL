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
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListBooksViewController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private ListView booksListView;

    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book,Integer> id;

    @FXML
    private TableColumn<Book,Integer> gatunek;

    @FXML
    private TableColumn<Book,Integer> wydawnictwo;

    @FXML
    private TableColumn<Book,String> tytul;

    @FXML
    private TableColumn<Book,Integer> liczbaStron;

    @FXML
    private TableColumn<Book,String> opis;

    ObservableList<Book> list = FXCollections.observableList(
           persistenceHandler.getKsiazki()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        booksListView.getItems().addAll(persistenceHandler.getKsiazki());

        id.setCellValueFactory(new PropertyValueFactory<Book,Integer>("id"));
        gatunek.setCellValueFactory(new PropertyValueFactory<>("gatunek_id"));
        wydawnictwo.setCellValueFactory(new PropertyValueFactory<Book,Integer>("wydawnictwo_id"));
        tytul.setCellValueFactory(new PropertyValueFactory<Book,String>("tytul"));
        liczbaStron.setCellValueFactory(new PropertyValueFactory<Book,Integer>("liczbaStron"));
        opis.setCellValueFactory(new PropertyValueFactory<Book,String>("opis"));

        booksTableView.setItems(list);
        updateUI();

    }

    private void updateUI(){
        booksListView.getItems().addAll(persistenceHandler.getKsiazki());

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
        root = FXMLLoader.load(getClass().getResource("clientPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}

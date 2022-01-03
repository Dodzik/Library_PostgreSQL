package org.example.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.domain.Book;
import org.example.domain.Friend;
import org.example.domain.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class ListBooksViewController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private ListView<Book> booksListView;

    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private TableColumn<Book,Integer> id;

    @FXML
    private TableColumn<Book,Integer> gatunek_id;

    @FXML
    private TableColumn<Book,Integer> wydawnictwo_id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        booksListView.getItems().addAll(persistenceHandler.getKsiazki());
        booksTableView.getItems().addAll(persistenceHandler.getKsiazki());

    }

    private void updateUI(){

        booksListView.getItems().addAll(persistenceHandler.getKsiazki());
    }


}

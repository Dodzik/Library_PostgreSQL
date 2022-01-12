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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.domain.AutorzyKsiazek;
import org.example.domain.Klient;
import org.example.domain.Wypozyczenie;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAutorzyKsiazkiPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<AutorzyKsiazek> autorzyKsiazekTableView;
    @FXML
    public TableColumn<AutorzyKsiazek,Integer> id_autor;
    @FXML
    public TableColumn<AutorzyKsiazek,Integer> id_Ksiazki;
    @FXML
    public TextField fieldIdAutor;
    @FXML
    public TextField fieldIdKsiazka;

    private Integer id_aut;
    private Integer id_ksia;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getSelected(MouseEvent mouseEvent) {
        Integer index = autorzyKsiazekTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_aut = id_autor.getCellData(index);
        id_ksia = id_Ksiazki.getCellData(index);
    }

    public void createAutorzyKsiazek(ActionEvent actionEvent) {
        AutorzyKsiazek autorzyKsiazek = new AutorzyKsiazek(
                Integer.parseInt(fieldIdAutor.getText()),Integer.parseInt(fieldIdKsiazka.getText()));

        if (persistenceHandler.createAutorzyKsiazek(autorzyKsiazek)){
            System.out.println("Dodano Autora Ksiazki");
            fieldIdAutor.setPromptText("id_autor");
            fieldIdKsiazka.setPromptText("id_ksiazka");
        }
        else {
            System.out.println("Error!");
            fieldIdAutor.setPromptText("Niepoprawne id_autor");
            fieldIdKsiazka.setPromptText("Niepoprawne id_ksiazka");
        }
        updateUI();
    }

    ObservableList<AutorzyKsiazek> list = FXCollections.observableList(
            persistenceHandler.getAutorzyKsiazek()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_autor.setCellValueFactory(new PropertyValueFactory<AutorzyKsiazek,Integer>("autor_id"));
        id_Ksiazki.setCellValueFactory(new PropertyValueFactory<AutorzyKsiazek,Integer>("ksiazka_id"));
        autorzyKsiazekTableView.setItems(list);
    }

    public void deleteAutorzyKsiazki(ActionEvent actionEvent) {
        persistenceHandler.deleteAutorzyKsiazek(id_aut,id_ksia);
        updateUI();
    }
    private void updateUI() {
        fieldIdAutor.clear();
        fieldIdKsiazka.clear();
        autorzyKsiazekTableView.getItems().clear();
        autorzyKsiazekTableView.getItems().addAll(persistenceHandler.getAutorzyKsiazek());
    }
}

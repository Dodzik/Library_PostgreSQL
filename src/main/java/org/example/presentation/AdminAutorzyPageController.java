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
import org.example.domain.Autor;
import org.example.domain.Rezerwacja;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminAutorzyPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TextField fieldImie;
    @FXML
    public TextField fieldNazwisko;
    @FXML
    public TableView<Autor> autorzyTableView;
    @FXML
    public TableColumn<Autor,Integer> id;
    @FXML
    public TableColumn<Autor,String> imie;
    @FXML
    public TableColumn<Autor,String> nazwisko;


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
    private Integer id_autor;

    @FXML
    public void getSelected (MouseEvent event){

        Integer index = autorzyTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_autor = id.getCellData(index);

    }

    ObservableList<Autor> list = FXCollections.observableList(
            persistenceHandler.getAutorzy()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<Autor,Integer>("id"));
        imie.setCellValueFactory(new PropertyValueFactory<Autor,String>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Autor,String >("nazwisko"));

        autorzyTableView.setItems(list);

    }

    public void deleteAutor(ActionEvent actionEvent){
        try {
            persistenceHandler.deleteAutor(id_autor);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }
    public void createAutor(ActionEvent actionEvent){
        Autor autor = new Autor(null,fieldImie.getText(),
                fieldNazwisko.getText());

        if (persistenceHandler.createAutor(autor)){
            System.out.println("Dodano Rezerwacje");
            fieldImie.setPromptText("imie");
            fieldNazwisko.setPromptText("nazwisko");
        }
        else {
            System.out.println("Error!");
            fieldImie.setPromptText("Niepoprawne imie");
            fieldNazwisko.setPromptText("Niepoprawne nazwisko");
        }
        updateUI();

    }
    private void updateUI() {
        fieldImie.clear();
        fieldNazwisko.clear();
        autorzyTableView.getItems().clear();
        autorzyTableView.getItems().addAll(persistenceHandler.getAutorzy());
    }

}

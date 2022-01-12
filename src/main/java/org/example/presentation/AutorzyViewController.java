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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.domain.Autor;
import org.example.domain.IPersistenceHandler;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AutorzyViewController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private TableView<Autor> autorzyTableView;

    @FXML
    private TableColumn<Autor,Integer> id;

    @FXML
    private TableColumn<Autor,String> imie;

    @FXML
    private TableColumn<Autor,String> nazwisko;

    public Integer autor_id;
    private Integer index=-1;

    ObservableList<Autor> list = FXCollections.observableList(
            persistenceHandler.getAutorzy()
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<Autor,Integer>("id"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Autor,String>("nazwisko"));

        autorzyTableView.setItems(list);
    }

    @FXML
    void getSelected (MouseEvent event){

        index = autorzyTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        autor_id=id.getCellData(index);

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

    public void checkAutor(ActionEvent actionEvent) throws IOException{
        persistenceHandler.setTemp(autor_id);
        root = FXMLLoader.load(getClass().getResource("autorBooks.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

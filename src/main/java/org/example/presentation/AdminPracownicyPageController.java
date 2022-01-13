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
import org.example.domain.Gatunek;
import org.example.domain.Pracownik;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPracownicyPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Pracownik> pracownicyTableView;
    @FXML
    public TableColumn<Pracownik,Integer> id;
    @FXML
    public TableColumn<Pracownik,Integer> id_stanowisko;
    @FXML
    public TableColumn<Pracownik,String> login;
    @FXML
    public TableColumn<Pracownik,String> haslo;
    @FXML
    public TextField fieldIdStanowisko;
    @FXML
    public TextField fieldLogin;
    @FXML
    public TextField fieldHaslo;

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
    Integer id_pracownik;

    public void getSelected(MouseEvent mouseEvent) {
        Integer index = pracownicyTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_pracownik = id.getCellData(index);
    }

    public void createPracownik(ActionEvent actionEvent) {
        Pracownik pracownik = new Pracownik(null,Integer.parseInt(fieldIdStanowisko.getText()),fieldLogin.getText(),fieldHaslo.getText());
        if (persistenceHandler.createPracownik(pracownik)){
            System.out.println("Dodano Gatunek");
            fieldIdStanowisko.setPromptText("id_stanowisko");
            fieldLogin.setPromptText("login");
            fieldHaslo.setPromptText("haslo");
        }
        else {
            System.out.println("Error!");
            fieldIdStanowisko.setPromptText("Niepoprawna id_stanowisko");
            fieldLogin.setPromptText("Niepoprawna login");
            fieldHaslo.setPromptText("Niepoprawna haslo");
        }
        updateUI();
    }

    public void deletePracownik(ActionEvent actionEvent) {
        try {
            persistenceHandler.deletePracownik(id_pracownik);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }

    ObservableList<Pracownik> list = FXCollections.observableList(
            persistenceHandler.getPracownicy()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Pracownik,Integer>("id"));
        id_stanowisko.setCellValueFactory(new PropertyValueFactory<Pracownik,Integer>("stanowisko_id"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        haslo.setCellValueFactory(new PropertyValueFactory<>("haslo"));

        pracownicyTableView.setItems(list);

    }
    private void updateUI() {
        fieldIdStanowisko.clear();
        fieldLogin.clear();
        fieldHaslo.clear();
        pracownicyTableView.getItems().clear();
        pracownicyTableView.getItems().addAll(persistenceHandler.getPracownicy());
    }
}

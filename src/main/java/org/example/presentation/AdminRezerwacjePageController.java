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
import org.example.domain.IPersistenceHandler;
import org.example.domain.Klient;
import org.example.domain.Rezerwacja;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminRezerwacjePageController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Rezerwacja> rezerwacjeTableView;

    @FXML
    public TableColumn<Rezerwacja,Integer> id;
    @FXML
    public TableColumn<Rezerwacja,Integer> id_Ksiazki;
    @FXML
    public TableColumn<Rezerwacja,Integer> id_Klient;
    @FXML
    public TableColumn<Rezerwacja, Date> date;
    @FXML
    public TextField fieldIdKsiazka;
    @FXML
    public TextField fieldIdKlient;
    @FXML
    public TextField fieldDate;

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
    private Integer id_rezerwacja;

    @FXML
    public void getSelected (MouseEvent event){

        Integer index = rezerwacjeTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_rezerwacja =id.getCellData(index);

    }

    ObservableList<Rezerwacja> list = FXCollections.observableList(
            persistenceHandler.getRezerwacje()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<>("id_rezerwacje"));
        id_Ksiazki.setCellValueFactory(new PropertyValueFactory<>("ksiazka_id"));
        id_Klient.setCellValueFactory(new PropertyValueFactory<>("klient_id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        rezerwacjeTableView.setItems(list);

    }

    public void deleteRezerwacja(){
        persistenceHandler.deleteKlient(id_rezerwacja);
        updateUI();
    }
    public void createRezerwacja(){
        String sdate = fieldDate.getText();
        java.sql.Date date1;
        try {
            date1 = java.sql.Date.valueOf(sdate);
        }
        catch (Exception e){
            fieldDate.clear();
            fieldDate.setPromptText("Niepoprawna data");
            return;
        }
        Rezerwacja rezerwacja = new Rezerwacja(null,Integer.parseInt(fieldIdKsiazka.getText()),
                Integer.parseInt(fieldIdKlient.getText()),date1);

        if (persistenceHandler.createRezerwacjaAdmin(rezerwacja)){
            System.out.println("Dodano Rezerwacje");
            fieldIdKsiazka.setPromptText("id_Ksiazka");
            fieldIdKlient.setPromptText("id_Klient");
            fieldDate.setPromptText("date");
        }
        else {
            System.out.println("Error!");
            fieldIdKsiazka.setPromptText("Niepoprawne id_Ksiazka");
            fieldIdKlient.setPromptText("Niepoprawne id_Klient");
            fieldDate.setPromptText("Niepoprawne date");
        }
        updateUI();

    }
    private void updateUI() {
        fieldDate.clear();
        fieldIdKlient.clear();
        fieldIdKsiazka.clear();
        rezerwacjeTableView.getItems().clear();
        rezerwacjeTableView.getItems().addAll(persistenceHandler.getRezerwacje());
    }
}

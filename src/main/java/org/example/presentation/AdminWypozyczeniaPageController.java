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
import org.example.domain.Dane;
import org.example.domain.Wypozyczenie;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AdminWypozyczeniaPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Wypozyczenie> wypozyczeniaTableView;
    @FXML
    public TableColumn<Wypozyczenie,Integer> id_pracownik;
    @FXML
    public TableColumn<Wypozyczenie,Integer> id_klient;
    @FXML
    public TableColumn<Wypozyczenie,Integer> id_ksiazka;
    @FXML
    public TableColumn<Wypozyczenie, Date> data_wypozyczenia;
    @FXML
    public TableColumn<Wypozyczenie, Date> data_oddania;
    @FXML
    public TextField fieldId_pracownik;
    @FXML
    public TextField fieldId_klient;
    @FXML
    public TextField fieldId_ksiazka;
    @FXML
    public TextField fieldData_wypozyczenia;
    @FXML
    public TextField fieldData_oddania;

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

    private Integer id_prac;
    private Integer id_klien;
    private Integer id_ksiaz;


    public void getSelected(MouseEvent mouseEvent) {
        Integer index = wypozyczeniaTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_prac = id_pracownik.getCellData(index);
        id_klien = id_klient.getCellData(index);
        id_ksiaz = id_ksiazka.getCellData(index);
    }

    public void createWypozyczenie(ActionEvent actionEvent) {
        String sdate1 = fieldData_wypozyczenia.getText();
        String sdate2 = fieldData_oddania.getText();
        Date date1;
        Date date2 = null;
        try {
            date1 = Date.valueOf(sdate1);
            if (fieldData_oddania.getText()!="") {
                date2 = Date.valueOf(sdate2);
            }
        }
        catch (Exception e){
            fieldData_wypozyczenia.clear();
            fieldData_wypozyczenia.setPromptText("Niepoprawna data wypozyczenia");
            fieldData_oddania.clear();
            fieldData_oddania.setPromptText("Niepoprawna data oddania");
            return;
        }
        Wypozyczenie wypozyczenie = new Wypozyczenie(Integer.parseInt(fieldId_pracownik.getText()),
                Integer.parseInt(fieldId_klient.getText()),Integer.parseInt(fieldId_ksiazka.getText()),
                date1,date2);

        if (persistenceHandler.createWypozyczenie(wypozyczenie)){
            System.out.println("Dodano Wypozyczenie");
            fieldId_pracownik.setPromptText("id_pracownik");
            fieldId_klient.setPromptText("id_klient");
            fieldId_ksiazka.setPromptText("id_ksiazka");
            fieldData_wypozyczenia.setPromptText("data_wypozyczenia");
            fieldData_oddania.setPromptText("data_oddania");
        }
        else {
            System.out.println("Error!");
            fieldId_pracownik.setPromptText("Niepoprawneid_pracownik");
            fieldId_klient.setPromptText("Niepoprawneid_klient");
            fieldId_ksiazka.setPromptText("Niepoprawneid_ksiazka");
            fieldData_wypozyczenia.setPromptText("Niepoprawnedata_wypozyczenia");
            fieldData_oddania.setPromptText("Niepoprawnedata_oddania");
        }
        updateUI();
    }

    public void deleteWypozyczenie(ActionEvent actionEvent) {
        try {
            persistenceHandler.deleteWypozyczenie(id_prac,id_klien,id_ksiaz);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }

    ObservableList<Wypozyczenie> list = FXCollections.observableList(
            persistenceHandler.getWypozyczenia()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_pracownik.setCellValueFactory(new PropertyValueFactory<Wypozyczenie,Integer>("id_pracownik"));
        id_klient.setCellValueFactory(new PropertyValueFactory<Wypozyczenie,Integer>("id_klient"));
        id_ksiazka.setCellValueFactory(new PropertyValueFactory<Wypozyczenie,Integer >("id_ksiazka"));
        data_wypozyczenia.setCellValueFactory(new PropertyValueFactory<>("data_wypozyczenia"));
        data_oddania.setCellValueFactory(new PropertyValueFactory<>("data_oddania"));

        wypozyczeniaTableView.setItems(list);
    }
    private void updateUI() {
        fieldId_pracownik.clear();
        fieldId_klient.clear();
        fieldId_ksiazka.clear();
        fieldData_wypozyczenia.clear();
        fieldData_oddania.clear();
        wypozyczeniaTableView.getItems().clear();
        wypozyczeniaTableView.getItems().addAll(persistenceHandler.getWypozyczenia());
    }
}

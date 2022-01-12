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
import org.example.domain.Klient;
import org.example.domain.Ksiazka;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminKsiazkiPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Ksiazka> ksiazkiTableView;
    @FXML
    public TableColumn<Ksiazka,Integer> id;
    @FXML
    public TableColumn<Ksiazka,Integer> id_gatunek;
    @FXML
    public TableColumn<Ksiazka,Integer> id_wydawnictwo;
    @FXML
    public TableColumn<Ksiazka,String> tytul;
    @FXML
    public TableColumn<Ksiazka,Integer> liczba_stron;
    @FXML
    public TableColumn<Ksiazka,String> opis;

    @FXML
    public TextField fieldIdGatunek;
    @FXML
    public TextField fieldIdWydawnictwo;
    @FXML
    public TextField fieldTytul;
    @FXML
    public TextField fieldLiczbaStron;
    @FXML
    public TextField fieldOpis;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Integer id_ksiazka;

    public void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void getSelected (MouseEvent event){

        Integer index = ksiazkiTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_ksiazka=id.getCellData(index);

    }

    public void createKsiazka(ActionEvent actionEvent) {
        Ksiazka ksiazka = new Ksiazka(null,Integer.parseInt(fieldIdGatunek.getText()),
                Integer.parseInt(fieldIdWydawnictwo.getText()),fieldTytul.getText(),
                Integer.parseInt(fieldLiczbaStron.getText()),fieldOpis.getText());

        if (persistenceHandler.createKsiazka(ksiazka)){
            System.out.println("Dodano Rezerwacje");
            fieldIdGatunek.setPromptText("id_gatunek");
            fieldIdWydawnictwo.setPromptText("id_wydawnictwo");
            fieldTytul.setPromptText("tytul");
            fieldLiczbaStron.setPromptText("liczba_stron");
            fieldOpis.setPromptText("opis");
        }
        else {
            System.out.println("Error!");
            fieldIdGatunek.setPromptText("Niepoprawny id_gatunek");
            fieldIdWydawnictwo.setPromptText("Niepoprawny id_wydawnictwo");
            fieldTytul.setPromptText("Niepoprawny tytul");
            fieldLiczbaStron.setPromptText("Niepoprawny liczba_stron");
            fieldOpis.setPromptText("Niepoprawny opis");
        }
        updateUI();
    }

    public void deleteKsiazka(ActionEvent actionEvent) {
        try {
            persistenceHandler.deleteKsiazka(id_ksiazka);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }

    ObservableList<Ksiazka> list = FXCollections.observableList(
            persistenceHandler.getKsiazki()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Ksiazka,Integer>("id"));
        id_gatunek.setCellValueFactory(new PropertyValueFactory<>("gatunek_id"));
        id_wydawnictwo.setCellValueFactory(new PropertyValueFactory<Ksiazka,Integer>("wydawnictwo_id"));
        tytul.setCellValueFactory(new PropertyValueFactory<Ksiazka,String>("tytul"));
        liczba_stron.setCellValueFactory(new PropertyValueFactory<Ksiazka,Integer>("liczbaStron"));
        opis.setCellValueFactory(new PropertyValueFactory<Ksiazka,String>("opis"));

        ksiazkiTableView.setItems(list);

    }

    private void updateUI() {
        fieldIdGatunek.clear();
        fieldIdWydawnictwo.clear();
        fieldTytul.clear();
        fieldLiczbaStron.clear();
        fieldOpis.clear();
        ksiazkiTableView.getItems().clear();
        ksiazkiTableView.getItems().addAll(persistenceHandler.getKsiazki());
    }
}

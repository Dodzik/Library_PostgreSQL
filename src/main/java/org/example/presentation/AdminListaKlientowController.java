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
import org.example.persistence.PersistenceHandler;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminListaKlientowController implements Initializable {
    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();


    @FXML
    private TableView<Klient> klientTableView;

    @FXML
    private TableColumn<Klient,Integer> id;

    @FXML
    private TableColumn<Klient,Integer> id_dane;

    @FXML
    private TableColumn<Klient,String> imie;

    @FXML
    private TableColumn<Klient,String> nazwisko;

    @FXML
    private TableColumn<Klient,String> email;

    @FXML
    private TableColumn<Klient,String> haslo;

    @FXML
    private TextField fieldDane;

    @FXML
    private TextField fieldImie;

    @FXML
    private TextField fieldNazwisko;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField fieldHaslo;

    ObservableList<Klient> list = FXCollections.observableList(
            persistenceHandler.getKlienci()
    );
    private Integer id_klient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id.setCellValueFactory(new PropertyValueFactory<Klient,Integer>("id"));
        id_dane.setCellValueFactory(new PropertyValueFactory<>("id_dane"));
        imie.setCellValueFactory(new PropertyValueFactory<Klient,String>("name"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Klient,String>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<Klient,String>("email"));
        haslo.setCellValueFactory(new PropertyValueFactory<Klient,String>("haslo"));

        klientTableView.setItems(list);

    }

    @FXML
    public void getSelected (MouseEvent event){

        Integer index = klientTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_klient=id.getCellData(index);

    }

    public void deleteKlient(ActionEvent actionEvent){
        persistenceHandler.deleteKlient(id_klient);
        updateUI();
    }
    public void createKlient(ActionEvent actionEvent){
        Klient klient = new Klient(null,
                Integer.parseInt(fieldDane.getText()),fieldImie.getText(),
                fieldNazwisko.getText(),fieldEmail.getText(),fieldHaslo.getText());

        if (persistenceHandler.createKlient(klient)){
            System.out.println("Dodano Klienta");
            fieldDane.setPromptText("id_dane");
            fieldImie.setPromptText("Imie");
            fieldNazwisko.setPromptText("Nazwisko");
            fieldEmail.setPromptText("Email");
            fieldHaslo.setPromptText("haslo");
        }
        else {
            System.out.println("Error!");
            fieldDane.setPromptText("Niepoprawne id_dane");
            fieldImie.setPromptText("Niepoprawne Imie");
            fieldNazwisko.setPromptText("Niepoprawne Nazwisko");
            fieldEmail.setPromptText("Niepoprawne Email");
            fieldHaslo.setPromptText("Niepoprawne haslo");
        }
        updateUI();

    }
    private void updateUI() {
        fieldDane.clear();
        fieldImie.clear();
        fieldNazwisko.clear();
        fieldEmail.clear();
        fieldHaslo.clear();
        klientTableView.getItems().clear();
        klientTableView.getItems().addAll(persistenceHandler.getKlienci());
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void Back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}

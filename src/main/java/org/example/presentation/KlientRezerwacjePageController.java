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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.IPersistenceHandler;
import org.example.domain.Rezerwacja;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.util.ResourceBundle;

public class KlientRezerwacjePageController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private TableView<Rezerwacja> rezerwacjeTableView;

    @FXML
    private TableColumn<Rezerwacja,Integer> ksiazka;

    @FXML
    private TableColumn<Rezerwacja,Integer> imie;

    @FXML
    private TableColumn<Rezerwacja,Integer> nazwisko;

    @FXML
    private TableColumn<Rezerwacja, Date> date;

    @FXML
    private TextField fieldKsiazka;

    @FXML
    private TextField fieldData;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("klientPanel.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Rezerwacja> list = FXCollections.observableList(
            persistenceHandler.getRezerwacjeKlient()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        rezerwacjeTableView.getItems().addAll(persistenceHandler.getRezerwacjeKlient());

        ksiazka.setCellValueFactory(new PropertyValueFactory<Rezerwacja,Integer>("ksiazka_id"));
        imie.setCellValueFactory(new PropertyValueFactory<Rezerwacja,Integer>("klient_id"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Rezerwacja,Integer>("klient_id"));
        date.setCellValueFactory(new PropertyValueFactory<Rezerwacja,Date>("date"));

        rezerwacjeTableView.setItems(list);

        updateUI();
    }

    @FXML
    public void dodajRezerwacje(ActionEvent actionEvent) throws ParseException {
        String sdate = fieldData.getText();
        Date date1;
        try {
            date1 = Date.valueOf(sdate);
        }
        catch (Exception e){
            fieldData.clear();
            fieldData.setPromptText("Niepoprawna data");
            return;
        }
        Rezerwacja rezerwacja = new Rezerwacja(null,persistenceHandler.getKsiazkaId(fieldKsiazka.getText()),null,date1);

        if(persistenceHandler.createRezerwacja(rezerwacja)){
            System.out.println("Rezerwacja inserted into database");
            fieldData.setPromptText("Data");
        } else {
            System.out.println("Something went wrong");
        }
        updateUI();
    }

    private void updateUI(){
        fieldKsiazka.clear();
        fieldData.clear();
        rezerwacjeTableView.getItems().clear();
        rezerwacjeTableView.getItems().addAll(persistenceHandler.getRezerwacjeKlient());

        ksiazka.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer ksiazka_id, boolean empty) {
                super.updateItem(ksiazka_id, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(persistenceHandler.getNazwaKsiazka(ksiazka_id));
                }
            }
        });

        imie.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer klient_id, boolean empty) {
                super.updateItem(klient_id, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(persistenceHandler.getKlientById(klient_id).getName());
                }
            }
        });

        nazwisko.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer klient_id, boolean empty) {
                super.updateItem(klient_id, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(persistenceHandler.getKlientById(klient_id).getSurname());
                }
            }
        });

    }
//    @FXML
//    void addFriend(ActionEvent event) {
//        Friend friend = new Friend(null, name.getText(), Integer.parseInt(phoneNumber.getText()));
//        if(persistenceHandler.createFriend(friend)){
//            System.out.println("Friend inserted into database");
//        } else {
//            System.out.println("Something went wrong");
//        }
//        updateUI();
//    }
//
//    private void updateUI(){
//        name.clear();
//        phoneNumber.clear();
//        friendsListView.getItems().clear();
//        friendsListView.getItems().addAll(persistenceHandler.getFriends());
//    }
}

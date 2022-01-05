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
import javafx.stage.Stage;
import org.example.domain.Book;
import org.example.domain.Friend;
import org.example.domain.IPersistenceHandler;
import org.example.domain.Rezerwacja;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class WidokListyRezerwacjiController implements Initializable {

    IPersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    private TableView<Rezerwacja> rezerwacjeTableView;

    @FXML
    private TableColumn<Rezerwacja,String> ksiazka;

    @FXML
    private TableColumn<Rezerwacja,String> imie;

    @FXML
    private TableColumn<Rezerwacja,String> nazwisko;

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
        root = FXMLLoader.load(getClass().getResource("clientPanel.fxml"));
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

        rezerwacjeTableView.getItems().addAll(persistenceHandler.getRezerwacjeKlient());

        ksiazka.setCellValueFactory(new PropertyValueFactory<Rezerwacja,String>("ksiazka"));
        imie.setCellValueFactory(new PropertyValueFactory<Rezerwacja,String>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Rezerwacja,String>("nazwisko"));
        date.setCellValueFactory(new PropertyValueFactory<Rezerwacja,Date>("date"));

        rezerwacjeTableView.setItems(list);

    }

    @FXML
    public void dodajRezerwacje(ActionEvent actionEvent) throws ParseException {
        String sdate = fieldData.getText();
        Date date1 = Date.valueOf(sdate);
        Rezerwacja rezerwacja = new Rezerwacja(null,fieldKsiazka.getText(),null,null,date1);

        if(persistenceHandler.createRezerwacja(rezerwacja)){
            System.out.println("Rezerwacja inserted into database");
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

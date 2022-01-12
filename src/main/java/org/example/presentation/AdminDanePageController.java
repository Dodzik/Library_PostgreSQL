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
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDanePageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Dane> daneTableView;
    @FXML
    public TableColumn<Dane,Integer> id;
    @FXML
    public TableColumn<Dane,String > miasto;
    @FXML
    public TableColumn<Dane,String> ulica;
    @FXML
    public TableColumn<Dane,String> nr_domu;
    @FXML
    public TableColumn<Dane,String> kod_pocztowy;
    @FXML
    public TextField fieldMiasto;
    @FXML
    public TextField fieldUlica;
    @FXML
    public TextField fieldNrDomu;
    @FXML
    public TextField fieldKodPocztowy;

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
    private Integer id_dane;

    public void getSelected(MouseEvent mouseEvent) {
        Integer index = daneTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        id_dane = id.getCellData(index);
    }

    public void createDane(ActionEvent actionEvent) {
        Dane dane = new Dane(null,fieldMiasto.getText(),fieldUlica.getText(),
                fieldNrDomu.getText(),fieldKodPocztowy.getText());
        if (!dane.getKod_pocztowy().contains("-")){
            fieldKodPocztowy.setPromptText("Niepoprawne kod_pocztowy");
            fieldKodPocztowy.clear();
            return;
        }

        if (persistenceHandler.createDane(dane)){
            System.out.println("Dodano Dane");
            fieldMiasto.setPromptText("miasto");
            fieldUlica.setPromptText("ulica");
            fieldNrDomu.setPromptText("nr_domu");
            fieldKodPocztowy.setPromptText("kod_pocztowy");
        }
        else {
            System.out.println("Error!");
            fieldMiasto.setPromptText("Niepoprawne miasto");
            fieldUlica.setPromptText("Niepoprawne ulica");
            fieldNrDomu.setPromptText("Niepoprawne nr_domu");
            fieldKodPocztowy.setPromptText("Niepoprawne kod_pocztowy");
        }
        updateUI();
    }

    public void deleteDane(ActionEvent actionEvent) {
        try {
            persistenceHandler.deleteDane(id_dane);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }

    ObservableList<Dane> list = FXCollections.observableList(
            persistenceHandler.getDane()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Dane,Integer>("id"));
        miasto.setCellValueFactory(new PropertyValueFactory<Dane,String>("miasto"));
        ulica.setCellValueFactory(new PropertyValueFactory<Dane,String >("ulica"));
        nr_domu.setCellValueFactory(new PropertyValueFactory<>("nr_domu"));
        kod_pocztowy.setCellValueFactory(new PropertyValueFactory<>("kod_pocztowy"));

        daneTableView.setItems(list);

    }
    private void updateUI() {
        fieldMiasto.clear();
        fieldUlica.clear();
        fieldNrDomu.clear();
        fieldKodPocztowy.clear();
        daneTableView.getItems().clear();
        daneTableView.getItems().addAll(persistenceHandler.getDane());
    }
}

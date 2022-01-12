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
import org.example.domain.Stanowisko;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminStanowiskaPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Stanowisko> stanowiskaTableView;
    @FXML
    public TableColumn<Stanowisko,Integer> id;
    @FXML
    public TableColumn<Stanowisko,String> nazwa;
    @FXML
    public TextField fieldNazwa;

    private Integer id_stanowisko;

    public void deleteStanowisko(ActionEvent actionEvent) {
        try {
            persistenceHandler.deleteStanowisko(id_stanowisko);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }

    public void createStanowisko(ActionEvent actionEvent) {
        Stanowisko stanowisko = new Stanowisko(null,fieldNazwa.getText());

        if (persistenceHandler.createStanowisko(stanowisko)){
            System.out.println("Dodano Rezerwacje");
            fieldNazwa.setPromptText("nazwa");
        }
        else {
            System.out.println("Error!");
            fieldNazwa.setPromptText("Niepoprawna nazwa");
        }
        updateUI();

    }

    public void getSelected(MouseEvent mouseEvent) {
        Integer index = stanowiskaTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        id_stanowisko = id.getCellData(index);
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    ObservableList<Stanowisko> list = FXCollections.observableList(
            persistenceHandler.getStanowiska()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Stanowisko,Integer>("id"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Stanowisko,String>("nazwa"));

        stanowiskaTableView.setItems(list);
    }

    private void updateUI() {
        fieldNazwa.clear();
        stanowiskaTableView.getItems().clear();
        stanowiskaTableView.getItems().addAll(persistenceHandler.getStanowiska());
    }
}

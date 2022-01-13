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
import org.example.domain.Stanowisko;
import org.example.domain.Wydawnictwo;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminWydawnictwaPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Wydawnictwo> wydawnictwTableView;
    @FXML
    public TableColumn<Wydawnictwo,Integer> id;
    @FXML
    public TableColumn<Wydawnictwo,String> nazwa;
    @FXML
    public TextField fieldNazwa;

    Integer id_wydawnictwo;

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

    public void getSelected(MouseEvent mouseEvent) {
        Integer index = wydawnictwTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        id_wydawnictwo = id.getCellData(index);
    }

    public void createWydawnictwo(ActionEvent actionEvent) {
        Wydawnictwo wydawnictwo = new Wydawnictwo(null,fieldNazwa.getText());

        if (persistenceHandler.createWydawnictwo(wydawnictwo)){
            System.out.println("Dodano Wydawnictwo");
            fieldNazwa.setPromptText("nazwa");
        }
        else {
            System.out.println("Error!");
            fieldNazwa.setPromptText("Niepoprawna nazwa");
        }
        updateUI();
    }

    public void deleteWydawnictwo(ActionEvent actionEvent) {
        try {
            persistenceHandler.deleteWydawnictwo(id_wydawnictwo);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }

    ObservableList<Wydawnictwo> list = FXCollections.observableList(
            persistenceHandler.getWydawnictwa()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Wydawnictwo,Integer>("id"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Wydawnictwo,String>("nazwa"));

        wydawnictwTableView.setItems(list);
    }
    private void updateUI() {
        fieldNazwa.clear();
        wydawnictwTableView.getItems().clear();
        wydawnictwTableView.getItems().addAll(persistenceHandler.getWydawnictwa());
    }
}

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
import org.example.domain.Wydawnictwo;
import org.example.persistence.PersistenceHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminGatunkiPageController implements Initializable {

    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();

    @FXML
    public TableView<Gatunek> gatunkiTableView;
    @FXML
    public TableColumn<Gatunek,Integer> id;
    @FXML
    public TableColumn<Gatunek,String> nazwa;
    @FXML
    public TextField fieldNazwa;

    Integer id_gatunek;

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
        Integer index = gatunkiTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        id_gatunek = id.getCellData(index);
    }

    public void createGatunek(ActionEvent actionEvent) {
        Gatunek gatunek = new Gatunek(null,fieldNazwa.getText());
        if (persistenceHandler.createGatunek(gatunek)){
            System.out.println("Dodano Gatunek");
            fieldNazwa.setPromptText("nazwa");
        }
        else {
            System.out.println("Error!");
            fieldNazwa.setPromptText("Niepoprawna nazwa");
        }
        updateUI();
    }

    public void deleteGatunek(ActionEvent actionEvent) {
        try {
            persistenceHandler.deleteGatunek(id_gatunek);
        }catch (Exception e){
            System.out.println("Error!");
        }
        updateUI();
    }

    ObservableList<Gatunek> list = FXCollections.observableList(
            persistenceHandler.getGatunki()
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Gatunek,Integer>("id"));
        nazwa.setCellValueFactory(new PropertyValueFactory<Gatunek,String>("nazwa"));

        gatunkiTableView.setItems(list);
    }

    private void updateUI() {
        fieldNazwa.clear();
        gatunkiTableView.getItems().clear();
        gatunkiTableView.getItems().addAll(persistenceHandler.getGatunki());
    }
}

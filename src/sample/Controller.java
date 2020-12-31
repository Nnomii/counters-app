package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public ComboBox<String> championsBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        championsBox.getItems().setAll(getChampions());
    }

    public List<String> getChampions() {
        List<String> championsList = new ArrayList<>();
        championsList.add("Renekton");
        championsList.add("Zoe");
        return championsList;
    }

    public void testingButtonClicked(ActionEvent actionEvent) {
        System.out.println("test");
    }

    public void newChampion(ActionEvent actionEvent) {

    }
}

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import sample.SocketConnection;

public class Controller implements Initializable {

    public ComboBox<String> championsBox;
    public Label tips;

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

    public void testingButtonClicked(ActionEvent actionEvent) throws IOException {
        for (String tip : getTips("renekton")) {
            System.out.println(tip);
        }
    }

    public void newChampion(ActionEvent actionEvent) throws IOException {
        List<String> tipsList = getTips(championsBox.getSelectionModel().getSelectedItem().toString());
        for (String tip : tipsList) {
            tips.setText(tips.getText() + tip);
        }
    }

    
    public List<String> getTips(String champion) throws IOException {
        //gives tips
        List<String> tips = new ArrayList<>();
        String source = SocketConnection.getURLSource("https://lolcounter.com/champions/" + champion);
        int elementNumber = 0;
        for(String element : source.split("<span class='_tip'>")) {
            if (elementNumber != 0) {
                tips.add(element.split("</span>")[0]);
            }
            elementNumber++;
        }
        return tips;
    }
}

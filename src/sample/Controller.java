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
        try {
            championsBox.getItems().setAll(getChampions());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<String> getChampions() throws IOException {
        List<String> championsList = new ArrayList<>();
        String source = SocketConnection.getURLSource("http://euw.op.gg/champion/statistics");
        int elementNumber = 0;
        for(String element : source.split("<div class=\"champion-index__champion-item__name\">")) {
            if (elementNumber != 0) {
                String champion = element.split("</div>")[0];
                if (champion.contains("&amp;")) {
                    champion = champion.replace("&amp;", "&");
                }
                championsList.add(champion);
            }
            elementNumber++;
        }
        return championsList;
    }

    public void testingButtonClicked(ActionEvent actionEvent) throws IOException {
        for (String tip : getTips("malphite")) {
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

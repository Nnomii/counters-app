package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller implements Initializable {

    public ComboBox<String> championsBox;
    public Label tips;
    public ImageView best1;
    public Label bestlabel;
    public ImageView best2;
    public ImageView best3;
    public Label worstlabel;
    public ImageView worst1;
    public ImageView worst2;
    public ImageView worst3;
    public Label popularlabel;
    public ImageView popular1;
    public ImageView popular2;
    public ImageView popular3;
    public ImageView championimage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bestlabel.setText("");
        worstlabel.setText("");
        popularlabel.setText("");
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
        loadImages();
        loadTips();
        loadCounters();
    }

    private void loadCounters() {
        bestlabel.setText("Best Picks Against " + championsBox.getSelectionModel().getSelectedItem().toString());
        worstlabel.setText("Worst Picks Against " + championsBox.getSelectionModel().getSelectedItem().toString());
        popularlabel.setText("Most Popular " + championsBox.getSelectionModel().getSelectedItem().toString() + " Counters");
    }

    public void loadImages() {
        String url = "https://www.mobafire.com/images/champion/square/" + parseChampion(championsBox.getSelectionModel().getSelectedItem().toString()) + ".png";
        championimage.setImage(new Image(url));
    }

    public String parseChampion(String champion) {
        String parsedChampion = champion.toLowerCase();
        parsedChampion = parsedChampion.replace(". ", "-");
        parsedChampion = parsedChampion.replace(" & ", "-");
        parsedChampion = parsedChampion.replace(" ", "-");
        parsedChampion = parsedChampion.replace("'", "");
        return parsedChampion;
    }

    public void loadTips() throws IOException {
        tips.setText(""); 
        List<String> tipsList = getTips(parseChampion(championsBox.getSelectionModel().getSelectedItem().toString()));
        for (String tip : tipsList) {
            if (tip.length() > 120) {
                int lastSpace = tip.substring(0, 100).lastIndexOf(" ");
                tip = tip.substring(0, lastSpace) + "\n" + tip.substring(lastSpace + 1);
            }
            if (tip.length() > 240) {
                int lastSpace = tip.substring(0, 200).lastIndexOf(" ");
                tip = tip.substring(0, lastSpace) + "\n" + tip.substring(lastSpace + 1);
            }
            if (tip.length() > 360) {
                int lastSpace = tip.substring(0, 300).lastIndexOf(" ");
                tip = tip.substring(0, lastSpace) + "\n" + tip.substring(lastSpace + 1);
            }
            if (tip.length() > 480) {
                int lastSpace = tip.substring(0, 400).lastIndexOf(" ");
                tip = tip.substring(0, lastSpace) + "\n" + tip.substring(lastSpace + 1);
            }
            if (tip.length() > 600) {
                int lastSpace = tip.substring(0, 500).lastIndexOf(" ");
                tip = tip.substring(0, lastSpace) + "\n" + tip.substring(lastSpace + 1);
            }
            tips.setText(tips.getText() + tip + "\n" + "\n");
        }
    }
    
    public List<String> getTips(String champion) {
        //gives tips
        List<String> tips = new ArrayList<>();
        if (champion.equals("nunu-willump")) {
            champion = "nunu";
        }
        String source;
        try {
            source = SocketConnection.getURLSource("https://lolcounter.com/champions/" + champion);
        } catch (IOException e) {
            tips.add("No tips for this Champion yet, or the app is experiencing problems!");
            e.printStackTrace();
            return tips;
        }
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

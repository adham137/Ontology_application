import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Classes.*;
public class FXMLController1 {

    @javafx.fxml.FXML
    private AnchorPane rootPane1;
    @javafx.fxml.FXML
    private ComboBox <String> included_actors_dropdown;
    @javafx.fxml.FXML
    private Button add_included_actor_btn;
    @javafx.fxml.FXML
    private Button remove_included_actor_btn;
    @javafx.fxml.FXML
    private Label included_actors_label;
    @javafx.fxml.FXML
    private Button add_excluded_actor_btn;
    @javafx.fxml.FXML
    private Button remove_excluded_actor_btn;
    @javafx.fxml.FXML
    private Label excluded_actors_label;

    @javafx.fxml.FXML
    public void initialize() {
        // Initialize the drop down list
        included_actors_dropdown.getItems().addAll(Actors.getAllAvailableActors());
        included_actors_label.setText("Included Actors: "+ LabelValues.includedActors);
        excluded_actors_label.setText("Excluded Actors: "+ LabelValues.excludedActors);
        //included_actors_dropdown.setOnAction(this::);
    }
    public void update(ActionEvent actionEvent) {
        // Update the actors dropdown and label
        included_actors_dropdown.getItems().clear();
        included_actors_dropdown.getItems().addAll(Actors.getAllAvailableActors());
        //System.out.println(Actors.getIncludedActorsAsString());
        // Update the included actors label
        LabelValues.includedActors = Actors.getIncludedActorsAsString();
        included_actors_label.setText("Included Actors: "+LabelValues.includedActors);
        // Update the excluded actors label
        LabelValues.excludedActors = Actors.getExcludedActorsAsString();
        excluded_actors_label.setText("Excluded Actors: "+LabelValues.excludedActors);

    }

    @javafx.fxml.FXML
    public void add_included_actor(ActionEvent actionEvent) {
        // Find the chosen
        String chosen = included_actors_dropdown.getValue();
        // Include the choice
        if (Actors.addIncludedActor(chosen)) {
            // Update the dropdown list and label
            update(new ActionEvent());
        }


    }

    @javafx.fxml.FXML
    public void remove_included_actor(ActionEvent actionEvent) {
        // Remove the last choice
        String lastChoice = Actors.getLastIncludedActor();
        if(Actors.removeIncludedActor(lastChoice)){
            // Update the dropdown list
            update(new ActionEvent());
        }

    }

    @javafx.fxml.FXML
    public void add_excluded_actor(ActionEvent actionEvent) {
        // Find the chosen
        String chosen = included_actors_dropdown.getValue();
        // Include the choice
        if(Actors.addExcludedActor(chosen)) {
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    @javafx.fxml.FXML
    public void remove_excluded_actor(ActionEvent actionEvent) {
        // Get the last choice
        String lastChoice = Actors.getLastexcludedActor();
        // Remove the included choice
        if(Actors.removeExcludedActor(lastChoice)){
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }



    public void toPane2(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Pane 2.fxml"));
        rootPane1.getChildren().setAll(pane);
    }
}

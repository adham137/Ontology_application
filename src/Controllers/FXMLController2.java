import Classes.Actors;
import Classes.Directors;
import Classes.LabelValues;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FXMLController2 {
    @javafx.fxml.FXML
    private AnchorPane rootPane2;
    @javafx.fxml.FXML
    private Button add_included_directors_btn;
    @javafx.fxml.FXML
    private Button remove_included_directors_btn;
    @javafx.fxml.FXML
    private Label included_directors_label;
    @javafx.fxml.FXML
    private Button add_excluded_directors_btn;
    @javafx.fxml.FXML
    private Button remove_excluded_directors_btn;
    @javafx.fxml.FXML
    private Label excluded_directors_label;
    @javafx.fxml.FXML
    private ComboBox <String> included_directors_dropdown;

    @javafx.fxml.FXML
    public void initialize() {
        // Initialize the drop down list
        included_directors_dropdown.getItems().addAll(Directors.getAllAvailableDirectors());
        included_directors_label.setText("Included Directors: "+ LabelValues.includedDirectors);
        excluded_directors_label.setText("Excluded Directors: "+ LabelValues.excludedDirectors);
        //included_actors_dropdown.setOnAction(this::);
    }


    public void update(ActionEvent actionEvent) {
        // Update the directors dropdown and label
        included_directors_dropdown.getItems().clear();
        included_directors_dropdown.getItems().addAll(Directors.getAllAvailableDirectors());
        //System.out.println(Directors.getIncludedDirectorsAsString());
        // Update the included directors label
        LabelValues.includedDirectors = Directors.getIncludedDirectorsAsString();
        included_directors_label.setText("Included Directors: "+LabelValues.includedDirectors);
        // Update the excluded directors label
        LabelValues.excludedDirectors = Directors.getExcludedDirectorsAsString();
        excluded_directors_label.setText("Excluded Directors: "+LabelValues.excludedDirectors);

    }

    @javafx.fxml.FXML
    public void add_included_directors(ActionEvent actionEvent) {
        // Find the chosen
        String chosen = included_directors_dropdown.getValue();
        // Include the choice
        if (Directors.addIncludedDirectors(chosen)) {
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    @javafx.fxml.FXML
    public void remove_included_directors(ActionEvent actionEvent) {
        // Remove the last choice
        String lastChoice = Directors.getLastIncludedDirector();
        // Remove the included choice
        if(Directors.removeIncludedDirectors(lastChoice)){
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    @javafx.fxml.FXML
    public void add_excluded_directors(ActionEvent actionEvent) {
        // Find the chosen
        String chosen = included_directors_dropdown.getValue();
        // Include the choice
        if(Directors.addExcludedDirectors(chosen)) {
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    @javafx.fxml.FXML
    public void remove_excluded_directors(ActionEvent actionEvent) {
        // Remove the last choice
        String lastChoice = Directors.getLastExcludedDirector();
        // Remove the included choice
        if(Directors.removeExcludedDirectors(lastChoice)){
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    public void toPane1(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Pane 1.fxml"));
        rootPane2.getChildren().setAll(pane);
    }

    public void toPane3(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Pane 3.fxml"));
        rootPane2.getChildren().setAll(pane);
    }
}

import Classes.Actors;
import Classes.Directors;
import Classes.Genres;
import Classes.LabelValues;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FXMLController3 {
    @javafx.fxml.FXML
    private Button add_included_genre_btn;
    @javafx.fxml.FXML
    private Button remove_included_genre_btn;
    @javafx.fxml.FXML
    private Label included_genre_label;
    @javafx.fxml.FXML
    private Button add_excluded_genre_btn;
    @javafx.fxml.FXML
    private Button remove_excluded_genre_btn;
    @javafx.fxml.FXML
    private Label excluded_genre_label;
    @javafx.fxml.FXML
    private AnchorPane rootPane3;
    @javafx.fxml.FXML
    private ComboBox<String> included_genres_dropdown;


    @javafx.fxml.FXML
    public void initialize() {
        // Initialize the drop down list
        included_genres_dropdown.getItems().addAll(Genres.getAllAvailableGenres());
        included_genre_label.setText("Included Genres: "+ LabelValues.includedGenres);
        excluded_genre_label.setText("Excluded Genres: "+ LabelValues.excludedGenres);
        //included_genres_dropdown.setOnAction(this::);
    }
    @Deprecated
    public void update(ActionEvent actionEvent) {
        // Update the included actors dropdown
        included_genres_dropdown.getItems().clear();
        included_genres_dropdown.getItems().addAll(Genres.getAllAvailableGenres());
        //System.out.println(Actors.getIncludedActorsAsString());
        // Update the included genres label
        LabelValues.includedGenres = Genres.getIncludedGenresAsString();
        included_genre_label.setText("Included Genres: "+LabelValues.includedGenres);
        // Update the excluded genres label
        LabelValues.excludedGenres = Genres.getExcludedGenresAsString();
        excluded_genre_label.setText("Excluded Genres: "+LabelValues.excludedGenres);
    }

    @javafx.fxml.FXML
    public void add_included_genre(ActionEvent actionEvent) {
        // Find the chosen
        String chosen = included_genres_dropdown.getValue();
        // Include the choice
        if (Genres.addIncludedGenre(chosen)) {
            // Update the dropdown list and label
            update(new ActionEvent());
        }

    }

    @javafx.fxml.FXML
    public void remove_included_genre(ActionEvent actionEvent) {
        // Remove the last choice
        String lastChoice = Genres.getLastIncludedGenre();
        // Remove the included choice
        if(Genres.removeIncludedGenre(lastChoice)){
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    @javafx.fxml.FXML
    public void add_excluded_genre(ActionEvent actionEvent) {
        // Find the chosen
        String chosen = included_genres_dropdown.getValue();
        // Include the choice
        if(Genres.addExcludedGenre(chosen)) {
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    @javafx.fxml.FXML
    public void remove_excluded_genre(ActionEvent actionEvent) {
        // Remove the last choice
        String lastChoice = Genres.getLastExcludedGenre();
        // Remove the included choice
        if(Genres.removeExcludedGenre(lastChoice)){
            // Update the dropdown list and label
            update(new ActionEvent());
        }
    }

    public void toPane2(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Pane 2.fxml"));
        rootPane3.getChildren().setAll(pane);
    }

    public void toPane4(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Pane 4.fxml"));
        rootPane3.getChildren().setAll(pane);
    }
}

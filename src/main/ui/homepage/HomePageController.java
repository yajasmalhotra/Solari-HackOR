package ui.homepage;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {

    public Button volunteerButton;
    public Button recruiterButton;

    public void categorySelected(ActionEvent event) throws IOException {
        if (event.getSource() == volunteerButton) {
            switchScenes(event, "volunteerpage");
        } else if (event.getSource() == recruiterButton) {
            switchScenes(event, "recruiterpage");
        }
    }

    public void switchScenes(ActionEvent event, String string) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/" + string + "/" + string + ".fxml"));
        Parent courseParent = loader.load();
        Scene courseScene = new Scene(courseParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(courseScene);
        window.show();
    }
}

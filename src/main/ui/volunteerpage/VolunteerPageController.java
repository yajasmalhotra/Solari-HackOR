package ui.volunteerpage;

import SQL.DBConnection;
import SQL.PopulateDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.volunteerpage.volunteerpageone.VolunteerPageOneController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class VolunteerPageController {

    public TextField name;
    public TextField age;
    public TextField describe;
    public Button conti;
    public Button back;

    Connection myConnection = DBConnection.connect();

    public void buttonClick(ActionEvent event) throws IOException {
        if (event.getSource() == back) {
            backButtonClicked(event);
        } else if (event.getSource() == conti) {

            System.out.println("test");
            try {
                PopulateDatabase.InsertVolunteer(myConnection, name.getText(), age.getText(), describe.getText());
                System.out.println(name.getText());
                System.out.println(age.getText());
                System.out.println(describe.getText());
            } catch (SQLException e) {
                System.out.println(e);
            }
            moveToPageOne(event);
        }
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/ui/homepage/homepage.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        DBConnection.disconnect(myConnection);
    }

    public void moveToPageOne(ActionEvent event) throws IOException {
        int tableSize = PopulateDatabase.CountTuples(myConnection, "Volunteer");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/volunteerpage/volunteerpageone/volunteerpageone.fxml"));
        Parent courseParent = loader.load();
        VolunteerPageOneController controller = (VolunteerPageOneController) loader.getController();
        controller.setTopLabel("Volunteer ID: " + (tableSize));
        Scene scene = new Scene(courseParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        DBConnection.disconnect(myConnection);
    }
}

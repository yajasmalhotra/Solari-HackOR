package ui.recruiterpage;

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
import ui.recruiterpage.recruiterpageone.RecruiterPageOneController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RecruiterPageController {

    public TextField name;
    public TextField companyname;
    public Button conti;
    public Button back;

    Connection myConnection = DBConnection.connect();

    public void buttonClick(ActionEvent event) throws IOException {
        if (event.getSource() == back) {
            backButtonClicked(event);
        } else if (event.getSource() == conti) {

            try {
                PopulateDatabase.PopulateRecruiter(myConnection, name.getText(), companyname.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        //    System.out.println(name.getText());
        //    System.out.println(companyname.getText());
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/recruiterpage/recruiterpageone/recruiterpageone.fxml"));
        Parent courseParent = loader.load();
        RecruiterPageOneController controller = (RecruiterPageOneController)loader.getController();
        controller.setTopLabel(companyname.getText());
        Scene scene = new Scene(courseParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        // TODO
        DBConnection.disconnect(myConnection);
    }

}

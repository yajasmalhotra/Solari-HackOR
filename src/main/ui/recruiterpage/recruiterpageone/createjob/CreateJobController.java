package ui.recruiterpage.recruiterpageone.createjob;

import SQL.DBConnection;
import SQL.PopulateDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ui.recruiterpage.recruiterpageone.RecruiterPageOneController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CreateJobController {

    public TextField jobTitle;
    public TextField loc;
    public TextField tag;

    public Button addJob;
    public Button back;

    public Label companyLabel;

    Connection myConnection = DBConnection.connect();

    public void buttonClick(ActionEvent event) throws IOException {
        if (event.getSource() == back) {
            backButtonClicked(event);
        } else if (event.getSource() == addJob) {
            try {
                PopulateDatabase.AddJobPosting(myConnection, jobTitle.getText(), companyLabel.getText(), loc.getText());
                List<String> tagsList = createTagsList(tag.getText());
                int mostRecent = PopulateDatabase.CountTuples(myConnection, "JobPostings");
                PopulateDatabase.addJobTags(myConnection, tagsList, Integer.toString(mostRecent));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            backButtonClicked(event);
        }
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
//        Parent parent = FXMLLoader.load(getClass().getResource("/ui/recruiterpage/recruiterpageone/" +
//                "recruiterpageone.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/recruiterpage/recruiterpageone/" +
                "recruiterpageone.fxml"));
        Parent parent = loader.load();
        RecruiterPageOneController recruiterPageOneController = (RecruiterPageOneController)loader.getController();
        recruiterPageOneController.setTopLabel(companyLabel.getText());
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        DBConnection.disconnect(myConnection);

    }

    public List<String> createTagsList(String entry) {
        List<String> tagsList = Arrays.asList(entry.split("\\s*,\\s*"));
        System.out.println(tagsList);
        return tagsList;
    }

    public void setTopLabel(String text) {
        companyLabel.setText(text);
    }
}

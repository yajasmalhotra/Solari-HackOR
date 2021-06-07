package ui.recruiterpage.recruiterpageone.closedjob;

import SQL.DBConnection;
import SQL.PopulateDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JobPosting;
import model.Volunteer;
import ui.MainGUI;
import ui.recruiterpage.recruiterpageone.RecruiterPageOneController;
import ui.recruiterpage.recruiterpageone.closedjob.viewvolunteerspopup.ViewVolunteersPopUpController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClosedJobController {

    public Button back;
    public Button viewVolunteers;
    public Label companyLabel;

    public ListView<String> closedJobs;
    public List<JobPosting> closedJobPostings;
    public List<Volunteer> volunteers;

    public int selectedIndex;

    Connection myConnection = DBConnection.connect();

    public void displayClosedPostings() {

        closedJobPostings = getClosedPostings();

        for (int i = 0; i < closedJobPostings.size(); i++) {
            closedJobs.getItems().add(closedJobPostings.get(i).getRole() +
                    "\n" + closedJobPostings.get(i).getCompany() +
                    "\n" + closedJobPostings.get(i).getLocation());

            System.out.println(closedJobPostings.get(i).getRole() +
                    "  " + closedJobPostings.get(i).getCompany() +
                    "  " + closedJobPostings.get(i).getLocation());
        }
    }


    public void buttonClick(ActionEvent event) throws IOException {
        if (event.getSource() == back) {
            backButtonClicked(event);
        } else if (event.getSource() == viewVolunteers) {
            showVolunteers();
        }
    }

    public void showVolunteers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/recruiterpage/recruiterpageone/closedjob/viewvolunteerspopup/viewvolunteerspopup.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.getIcons().add(new Image(MainGUI.class.getResourceAsStream("logo.png")));
        window.setTitle("Volunteers");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
        int JobID = closedJobPostings.get(selectedIndex).getJobID();
        ViewVolunteersPopUpController controller = (ViewVolunteersPopUpController) loader.getController();
        controller.displayVolunteers(Integer.toString(JobID));
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        //Parent parent = FXMLLoader.load(getClass().getResource("/ui/recruiterpage/recruiterpageone/recruiterpageone.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/recruiterpage/recruiterpageone/" +
                "recruiterpageone.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        RecruiterPageOneController recruiterPageOneController = (RecruiterPageOneController)loader.getController();
        recruiterPageOneController.setTopLabel(companyLabel.getText());
    }

    public void setTopLabel(String text) {
        companyLabel.setText(text);
    }

    public List<JobPosting> getClosedPostings() {
        List<JobPosting> closedPostings = new ArrayList<>();

        try {
            Statement myStatement = myConnection.createStatement();
            String sql = "SELECT * FROM " +
                    "JobPostings WHERE JobPostings.statusActive = 0 AND " +
                    "JobPostings.companyName = " + "'" + companyLabel.getText() + "'" + "";

            System.out.println(sql);

            ResultSet rs = myStatement.executeQuery(sql);

            while (rs.next()) {
                JobPosting temp = new JobPosting(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));

                closedPostings.add(temp);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("THIS IS CLOSED POSTINGS");
        System.out.println(closedPostings);
        return closedPostings;
    }

    public void displayVolunteers(String jobID){
        try {
            volunteers = PopulateDatabase.RecruiterViewVoluntees(myConnection, jobID);
            for (int i = 0; i < volunteers.size(); i++) {
                closedJobs.getItems().add(volunteers.get(i).getName() +
                        "\n" + volunteers.get(i).getAge() +
                        "\n" + volunteers.get(i).getDescription());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectJob() {
        selectedIndex = closedJobs.getSelectionModel().getSelectedIndex();
//        System.out.println("Index:" + selectedIndex);
    }

}

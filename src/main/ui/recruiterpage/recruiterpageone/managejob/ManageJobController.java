package ui.recruiterpage.recruiterpageone.managejob;

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
import javafx.stage.Stage;
import model.JobPosting;
import model.Volunteer;
import ui.recruiterpage.recruiterpageone.RecruiterPageOneController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManageJobController {

    public Button back;
    public Button viewVolunteers;
    public Button closePosting;
    public Button addVolunteer;

    public Label companyLabel;

    public List<JobPosting> activeJobs;
    public List<Volunteer> volunteers;
    public ListView<String> jobPostings;
    public ListView<String> volunteersAvailable;

    public int jobSelectedIndex;
    public int volunteerSelectedIndex;

    Connection myConnection = DBConnection.connect();

    public void buttonClick(ActionEvent event) throws IOException {
        if (event.getSource() == back) {
            backButtonClicked(event);
        } else if (event.getSource() == viewVolunteers) {
            showVolunteers();
        } else if (event.getSource() == closePosting) {
            closeJobPosting();
        } else if (event.getSource() == addVolunteer) {
            addaVolunteer();
        }
    }

    public void showVolunteers() {
        int JobID = activeJobs.get(jobSelectedIndex).getJobID();
        displayVolunteers(Integer.toString(JobID));
    }

    public void displayVolunteers(String jobID) {
        try {
            volunteersAvailable.getItems().clear();
            volunteers = PopulateDatabase.RecruiterSearch(myConnection, jobID);
            for (int i = 0; i < volunteers.size(); i++) {
                volunteersAvailable.getItems().add(volunteers.get(i).getName() +
                        "\n" + volunteers.get(i).getAge() +
                        "\n" + volunteers.get(i).getDescription());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeJobPosting() {
        int JobID = activeJobs.get(jobSelectedIndex).getJobID();
        try {
            PopulateDatabase.UpdateJobPosting(myConnection, Integer.toString(JobID));
            displayOpenPostings();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addaVolunteer() {
        int JobID = activeJobs.get(jobSelectedIndex).getJobID();
        int VolunteerID = volunteers.get(volunteerSelectedIndex).getVolunteerID();
        try {
            PopulateDatabase.RecruiterAssignJob(myConnection, Integer.toString(JobID), Integer.toString(VolunteerID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectJob() {
        jobSelectedIndex = jobPostings.getSelectionModel().getSelectedIndex();
        System.out.println("Manage Job Index:" + jobSelectedIndex);
        volunteersAvailable.getItems().clear();
    }

    public void selectVolunteer() {
        volunteerSelectedIndex = volunteersAvailable.getSelectionModel().getSelectedIndex();
        System.out.println("Volunteers Index:" + volunteerSelectedIndex);
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
//        Parent parent = FXMLLoader.load(getClass().getResource("/ui/recruiterpage/recruiterpageone/recruiterpageone.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/recruiterpage/recruiterpageone/" +
                "recruiterpageone.fxml"));
        Parent parent = loader.load();
        RecruiterPageOneController recruiterPageOneController = (RecruiterPageOneController) loader.getController();
        recruiterPageOneController.setTopLabel(companyLabel.getText());
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void setTopLabel(String text) {
        companyLabel.setText(text);
    }

    public void displayOpenPostings() {
        activeJobs = getOpenPostings();
        jobPostings.getItems().clear();
        for (int i = 0; i < activeJobs.size(); i++) {
            jobPostings.getItems().add(activeJobs.get(i).getRole() +
                    "\n" + activeJobs.get(i).getCompany() +
                    "\n" + activeJobs.get(i).getLocation());

            System.out.println(activeJobs.get(i).getRole() +
                    "  " + activeJobs.get(i).getCompany() +
                    "  " + activeJobs.get(i).getLocation());
        }
    }

    public List<JobPosting> getOpenPostings() {
        List<JobPosting> openPostings = new ArrayList<>();

        try {
            Statement myStatement = myConnection.createStatement();
            String sql = "SELECT * FROM JobPostings WHERE JobPostings.statusActive = 1 AND " +
                    "JobPostings.companyName = " + "'" + companyLabel.getText() + "'" + "";

            System.out.println(sql);
            ResultSet rs = myStatement.executeQuery(sql);

            while (rs.next()) {
                JobPosting temp = new JobPosting(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));

                openPostings.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(openPostings);

        System.out.println("THIS IS OPEN POSTINGS");
        System.out.println(openPostings);
        return openPostings;
    }
}

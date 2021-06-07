package ui.volunteerpage.volunteerpageone;

import SQL.DBConnection;
import SQL.PopulateDatabase;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JobPosting;
import ui.MainGUI;
import ui.volunteerpage.volunteerpageone.assignedjobspopup.AssignedJobsPopUpController;
import ui.volunteerpage.volunteerpageone.requestedjobspopup.RequestedJobsPopUpController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VolunteerPageOneController {

//    String st1[] = { "California", "Vancouver", "Delhi", "Paris" };
//    String st2[] = { "Google", "Amazon", "Oracle", "Subway"};
//    String st3[] = { "Software Developer", "Barista", "Teacher"};
//    String st4[] = { "Computers", "IT", "Health"};

    public ChoiceBox searchByRegion = new ChoiceBox();
    public ChoiceBox searchByCompany = new ChoiceBox();
    public ChoiceBox searchByJobTitle = new ChoiceBox();
    public ChoiceBox searchByTag = new ChoiceBox();
    public Button search;

    public TextField name;
    public TextField age;
    public TextField describe;
    public Button requested;
    public Button assigned;
    public Button requestJob;
    public Button back;

    public Label VolunteerID;

    public ListView<String> availableJobs;
    public List<JobPosting> jobPostings;

    public int selectedIndex;


    Connection myConnection = DBConnection.connect();

    @FXML
    private void initialize() throws SQLException {
        jobPostings = SQL.PopulateDatabase.makeAvailableJobPostingObjects(myConnection);

        for (int i = 0; i < jobPostings.size(); i++) {
            availableJobs.getItems().add(jobPostings.get(i).getRole() +
                    "\n" + jobPostings.get(i).getCompany() +
                    "\n" + jobPostings.get(i).getLocation());

            System.out.println(jobPostings.get(i).getRole() + " " +
                    jobPostings.get(i).getCompany() + " " +
                    jobPostings.get(i).getLocation());
        }

        String st1[] = new String[0];
        String st2[] = new String[0];
        String st3[] = new String[0];
        String st4[] = new String[0];

        try {
            st1 = PopulateDatabase.getRegions(myConnection).toArray(new String[0]);
            st2 = PopulateDatabase.getCompanies(myConnection).toArray(new String[0]);
            st3 = PopulateDatabase.getJobTitles(myConnection).toArray(new String[0]);
            st4 = PopulateDatabase.getTags(myConnection).toArray(new String[0]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        searchByRegion.setItems(FXCollections.observableArrayList(st1));
        searchByJobTitle.setItems(FXCollections.observableArrayList(st3));
        searchByCompany.setItems(FXCollections.observableArrayList(st2));
        searchByTag.setItems(FXCollections.observableArrayList(st4));
    }

    public void buttonClick(ActionEvent event) throws IOException {

        if (event.getSource() == requested) {
//            System.out.println("list of requested jobs");
            //The only input here should be the volunteer's ID, need to figure out how to pass this from volunteerpage
            showRequestedJobs();
            try {
                PopulateDatabase.VolunteerViewRequests(myConnection, "1");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (event.getSource() == assigned) {
//            System.out.println("list of assigned jobs");
            showAssignedJobs();
            try {
                PopulateDatabase.VolunteerViewAssigned(myConnection,"2");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (event.getSource() == search) {
            searchJob();

        } else if (event.getSource() == requestJob) {
            requestJob();
        } else if (event.getSource() == back) {
            moveToVolunteerPage(event);
        }
    }

    public void moveToVolunteerPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/volunteerpage/volunteerpage.fxml"));
        Parent courseParent = loader.load();
        Scene courseScene = new Scene(courseParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(courseScene);
        window.show();
    }


    public void showRequestedJobs() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/volunteerpage/volunteerpageone/requestedjobspopup/requestedjobspopup.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.getIcons().add(new Image(MainGUI.class.getResourceAsStream("logo.png")));
        window.setTitle("Requested Jobs");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
        String s = VolunteerID.getText();
        s = s.substring(14);
        RequestedJobsPopUpController controller = (RequestedJobsPopUpController) loader.getController();
        controller.displayRequestedJobs(s);
    }

    public void showAssignedJobs() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/volunteerpage/volunteerpageone/assignedjobspopup/assignedjobspopup.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = new Stage();
        window.getIcons().add(new Image(MainGUI.class.getResourceAsStream("logo.png")));
        window.setTitle("Assigned Jobs");
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.show();
        String s = VolunteerID.getText();
        s = s.substring(14);
        AssignedJobsPopUpController controller = (AssignedJobsPopUpController) loader.getController();
        controller.displayAssignedJobs(s);
    }


    public void searchJob(){
        try {
            jobPostings = PopulateDatabase.VolunteerSearch(myConnection,
                    (String) searchByJobTitle.getValue(),          // Casting objects from .getValue to String
                    (String) searchByCompany.getValue(),
                    (String) searchByRegion.getValue(),
                    (String) searchByTag.getValue());

            availableJobs.getItems().clear();
            System.out.println(jobPostings);

            for (int i = 0; i < jobPostings.size(); i++) {
                availableJobs.getItems().add(jobPostings.get(i).getRole() +
                        "\n" + jobPostings.get(i).getCompany() +
                        "\n" + jobPostings.get(i).getLocation());

                System.out.println(jobPostings.get(i).getRole() + " " +
                        jobPostings.get(i).getCompany() + " " +
                        jobPostings.get(i).getLocation());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void selectJob() {
        selectedIndex = availableJobs.getSelectionModel().getSelectedIndex();
        System.out.println("Index:" + selectedIndex);
    }

    public void requestJob() {
        int JobID = jobPostings.get(selectedIndex).getJobID();
        System.out.println("job number clicked" + selectedIndex);
        String s = VolunteerID.getText();
        s = s.substring(14);
        try {
            PopulateDatabase.VolunteerRequest(myConnection, Integer.toString(JobID), s);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setTopLabel(String text) {
        VolunteerID.setText(text);
    }
}

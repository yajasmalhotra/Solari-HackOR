package ui.volunteerpage.volunteerpageone.assignedjobspopup;

import SQL.DBConnection;
import SQL.PopulateDatabase;
import javafx.scene.control.ListView;
import model.JobPosting;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignedJobsPopUpController {

    public ListView<String> assignedJobs;
    Connection myConnection = DBConnection.connect();
    List<JobPosting> jobPostedAssigned = new ArrayList();

    public void displayAssignedJobs(String arg1){
        try {
            jobPostedAssigned = PopulateDatabase.VolunteerViewAssigned(myConnection, arg1);

//            assignedJobs.getItems().clear();
            System.out.println(assignedJobs);
            for (int i = 0; i < jobPostedAssigned.size(); i++) {
                assignedJobs.getItems().add(jobPostedAssigned.get(i).getRole() +
                        "\n" + jobPostedAssigned.get(i).getCompany() +
                        "\n" + jobPostedAssigned.get(i).getLocation());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

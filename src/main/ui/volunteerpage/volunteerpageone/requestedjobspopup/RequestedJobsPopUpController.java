package ui.volunteerpage.volunteerpageone.requestedjobspopup;

import SQL.DBConnection;
import SQL.PopulateDatabase;
import javafx.scene.control.ListView;
import model.JobPosting;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestedJobsPopUpController {

    public ListView<String> requestedJobs;
    Connection myConnection = DBConnection.connect();
    List<JobPosting> jobPostedRequested = new ArrayList();

    public void displayRequestedJobs(String arg1){
        try {
            jobPostedRequested = PopulateDatabase.VolunteerViewRequests(myConnection, arg1);

//            assignedJobs.getItems().clear();
            System.out.println(requestedJobs);
            for (int i = 0; i < jobPostedRequested.size(); i++) {
                requestedJobs.getItems().add(jobPostedRequested.get(i).getRole() +
                        "\n" + jobPostedRequested.get(i).getCompany() +
                        "\n" + jobPostedRequested.get(i).getLocation());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        System.out.println("hi");
    }

}

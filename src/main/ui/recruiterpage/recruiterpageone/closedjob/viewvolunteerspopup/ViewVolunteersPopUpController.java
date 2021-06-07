package ui.recruiterpage.recruiterpageone.closedjob.viewvolunteerspopup;

import SQL.DBConnection;
import SQL.PopulateDatabase;
import javafx.scene.control.ListView;
import model.Volunteer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewVolunteersPopUpController {

    public ListView<String> viewVolunteers;
    Connection myConnection = DBConnection.connect();
    List<Volunteer> volunteers = new ArrayList();
//
    public void displayVolunteers(String jobID){
    try {
        volunteers = PopulateDatabase.RecruiterViewVoluntees(myConnection, jobID);
        for (int i = 0; i < volunteers.size(); i++) {
            viewVolunteers.getItems().add(volunteers.get(i).getName() +
                    "\n" + volunteers.get(i).getAge() +
                    "\n" + volunteers.get(i).getDescription());
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
}
}

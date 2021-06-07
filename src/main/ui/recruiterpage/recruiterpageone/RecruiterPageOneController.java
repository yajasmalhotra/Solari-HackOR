package ui.recruiterpage.recruiterpageone;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ui.recruiterpage.recruiterpageone.closedjob.ClosedJobController;
import ui.recruiterpage.recruiterpageone.createjob.CreateJobController;
import ui.recruiterpage.recruiterpageone.managejob.ManageJobController;

import java.io.IOException;

public class RecruiterPageOneController {

    public Button createJob;
    public Button manageJob;
    public Button closedJob;
    public Button back;

    public Label companyLabel;

    public void categorySelected(ActionEvent event) throws IOException {
        if (event.getSource() == createJob) {
            switchScenes(event, "createjob");
        } else if (event.getSource() == manageJob) {
            switchScenes(event, "managejob");
        } else if (event.getSource() == closedJob) {
            switchScenes(event, "closedjob");
        } else  if (event.getSource() == back) {
            backButtonClicked(event);
        }
    }

//    public void switchScenes(ActionEvent event, String string) throws IOException {
//        Parent parent = FXMLLoader.load(getClass().getResource("/ui/recruiterpage/recruiterpageone/" + string + "/" + string +".fxml"));
//        Scene scene = new Scene(parent);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(scene);
//        window.show();
//    }

    public void switchScenes(ActionEvent event, String string) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/recruiterpage/recruiterpageone/" + string + "/" + string +".fxml"));
        Parent courseParent = loader.load();
        if (string == "createjob") {
            CreateJobController createController = (CreateJobController)loader.getController();
            createController.setTopLabel(companyLabel.getText());
        } else if (string == "managejob") {
            ManageJobController manageController = (ManageJobController)loader.getController();
            manageController.setTopLabel(companyLabel.getText());
            manageController.getOpenPostings();
            manageController.displayOpenPostings();
        }  else if (string == "closedjob") {
            ClosedJobController closedController = (ClosedJobController)loader.getController();
            closedController.setTopLabel(companyLabel.getText());
            closedController.getClosedPostings();
            closedController.displayClosedPostings();
        }
        Scene scene = new Scene(courseParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/ui/recruiterpage/recruiterpage.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void setTopLabel(String text) {
        companyLabel.setText(text);
    }
}

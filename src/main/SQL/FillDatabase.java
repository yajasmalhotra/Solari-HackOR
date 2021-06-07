package SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static SQL.PopulateDatabase.*;

public class FillDatabase {
    public static void main (String []args){
        Connection myConnection = DBConnection.connect();
        try {
            //Insert random volunteers
            InsertVolunteer(myConnection, "Jeff McAvoy", "23", "Quirky, Smart, Caring");
            InsertVolunteer(myConnection, "Adam Levy", "22", "Fast, Strong, Kind");
            InsertVolunteer(myConnection, "Kim Braxton", "25", "Careful, Serious, Professional");
            InsertVolunteer(myConnection, "Kevin Jackson", "23", "Helpful, Playful, Talkative");
            InsertVolunteer(myConnection, "Brittany Viktunas", "23", "Punctual, Open Minded, Risk Taker");

            //Insert random recruiters
            PopulateRecruiter(myConnection, "Jimmy Butler", "Miami Heat");
            PopulateRecruiter(myConnection, "Lebron James", "LA Lakers");
            PopulateRecruiter(myConnection, "Kevin Durant", "Brooklyn Nets");
            PopulateRecruiter(myConnection, "Kawhi Leonard", "LA Clippers");
            PopulateRecruiter(myConnection, "Anthony Edwards", "Minnesota Timberwolves");

            //Insert random jobs
            AddJobPosting(myConnection, "Project Assistant", "Latincouver", "Vancouver");
            AddJobPosting(myConnection, "Social Media Volunteer", "LaSalle College", "Vancouver");
            AddJobPosting(myConnection, "Research Volunteer", "Cerebral Palsy Association", "Los Angeles");
            AddJobPosting(myConnection, "Sewing Volunteer", "Annex Consulting Group", "Ohio");
            AddJobPosting(myConnection, "Manager Volunteer", "Kidsafe Project Society", "Austin");
            AddJobPosting(myConnection, "Business Analyst", "Canadian Pet Cancer Foundation", "Toronto");

            //Close some job postings


            //Insert some volunteer requests
            VolunteerRequest(myConnection,"1", "1");
            VolunteerRequest(myConnection,"2", "1");
            VolunteerRequest(myConnection,"5", "1");
            VolunteerRequest(myConnection,"3", "2");
            VolunteerRequest(myConnection,"4", "2");
            VolunteerRequest(myConnection,"6", "3");
            VolunteerRequest(myConnection,"1", "4");
            VolunteerRequest(myConnection,"2", "4");
            VolunteerRequest(myConnection,"4", "5");
            VolunteerRequest(myConnection,"5", "6");

            //Insert some assing volunteers
            RecruiterAssignJob(myConnection, "3", "1");
            RecruiterAssignJob(myConnection, "1", "2");
            RecruiterAssignJob(myConnection, "4", "3");
            RecruiterAssignJob(myConnection, "3", "4");
            RecruiterAssignJob(myConnection, "1", "5");

            //Insert some job tags
            List<String> JobTags1 = new ArrayList<String>();
            JobTags1.add("Fashion");
            JobTags1.add("Dancing");
            addJobTags(myConnection, JobTags1, "1");
            List<String> JobTags2 = new ArrayList<String>();
            JobTags2.add("Social Media");
            JobTags2.add("Educational");
            addJobTags(myConnection, JobTags2, "2");
            List<String> JobTags3 = new ArrayList<String>();
            JobTags3.add("Medical");
            JobTags3.add("Educational");
            addJobTags(myConnection, JobTags2, "3");
            List<String> JobTags4 = new ArrayList<String>();
            JobTags4.add("Fashion");
            addJobTags(myConnection, JobTags4, "4");
        } catch (SQLException e){
            System.out.print(e);
        }
    }
}

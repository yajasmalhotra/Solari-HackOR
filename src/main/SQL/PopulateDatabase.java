package SQL;

import model.JobPosting;
import model.Volunteer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PopulateDatabase {
    public static void main (String []args){
        Connection myConnection = DBConnection.connect();
        try {
//            PopulateRecruiter(myConnection, "James Johnson", "Miami Heat");
//            InsertVolunteer(myConnection, "Louis Hamilton", "7");
//            InsertVolunteer(myConnection, "Toto Wolff", "33");
//            AddJobPosting(myConnection, "Software Developer", "Google", "Vancouver");
//            UpdateJobPosting(myConnection, "7");
//            UpdateJobPosting(myConnection, "11");
//            VolunteerSearch(myConnection, "Software Developer", "", "", "");
//            VolunteerSearch(myConnection, "", "Google", "", "");
//            VolunteerSearch(myConnection, "", "", "Vancouver", "");
//            VolunteerSearch(myConnection, "Software Developer", "Google", "", "");
//            VolunteerSearch(myConnection, "", "Gppgle", "Vancouver", "");
//            VolunteerSearch(myConnection, "Software Developer", "", "Vancouver", "");
//            VolunteerSearch(myConnection, "Software Developer", "Google", "Vancouver", "");
//            DeleteJobPosting(myConnection, "4");
//            RecruiterSearch(myConnection, "1");
//            VolunteerRequest(myConnection,"1", "3");
//            RecruiterAssignJob(myConnection, "1", "2");
//            VolunteerViewAssigned(myConnection, "2");

            UpdateJobPosting(myConnection, "1");
            UpdateJobPosting(myConnection, "2");
            UpdateJobPosting(myConnection, "3");
            UpdateJobPosting(myConnection, "4");
            UpdateJobPosting(myConnection, "5");
            UpdateJobPosting(myConnection, "6");
            UpdateJobPosting(myConnection, "7");



            List<JobPosting> test = new ArrayList();
            test = VolunteerViewRequests(myConnection, "31");
            System.out.println(test);
//            List<String> jobTitles = getJobTitles(myConnection);
//            System.out.println(jobTitles);
//            List<String> companies = getCompanies(myConnection);
//            System.out.println(companies);
//            List<String> regions = getRegions(myConnection);
//            System.out.println(regions);
//            List<String> tagCategories = getTags(myConnection);
//            System.out.println(tagCategories);
//            int tagID = getTagNumber(myConnection, "Salary");
//            System.out.println(tagID);
//            List<String> JobTags = new ArrayList<String>();
//            JobTags.add("Fashion");
//            JobTags.add("Health");
//            addJobTags(myConnection, JobTags, "2");
//            List<Volunteer> example = new ArrayList<>();
//            example = MakeVolunteerObjects(myConnection);
//            System.out.println(example);
        } catch (SQLException e){
            System.out.print(e);
        }
    }

    public static void PopulateRecruiter(Connection con, String arg1, String arg2) throws SQLException{
        Statement myStatement = con.createStatement();
        int tableSize = CountTuples(con, "Recruiter");
        tableSize++;
        String sql = "INSERT INTO Recruiter (recruiterID, recruiterName, companyName) VALUES (" + tableSize + ",'" +
                arg1 + "','" + arg2 + "')";
        System.out.println(sql);
        int rs = myStatement.executeUpdate(sql);

        System.out.println("Recruiter Table has been populated");
    }

    public static void InsertVolunteer(Connection con, String arg1, String arg2, String arg3) throws SQLException{
        Statement myStatement = con.createStatement();
        int tableSize = CountTuples(con, "Volunteer");
        tableSize++;
        String sql = "INSERT INTO Volunteer (volunteerID, volunteerName, age, description) VALUES (" + tableSize + ",'" + arg1 + "',"
                + arg2 + ",'" + arg3 + "')";
        System.out.println(sql);
        int rs = myStatement.executeUpdate(sql);

        System.out.println("Volunteer Table has been populated");
    }

    public static void AddJobPosting(Connection con, String arg1, String arg2, String arg3) throws SQLException{
        Statement myStatement = con.createStatement();
        int tableSize = CountTuples(con, "JobPostings");
        tableSize++;
        String sql = "INSERT INTO JobPostings (jobID, jobTitle, companyName, location, statusActive) VALUES (" + tableSize + ", '" + arg1 + "', '" + arg2 + "', '" + arg3 + "', 1)";
        //System.out.println(sql);
        int rs = myStatement.executeUpdate(sql);

        System.out.println("Successfully Added new Job Posting!");
    }

    public static void UpdateJobPosting(Connection con, String arg1) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql ="UPDATE JobPostings SET statusActive = 0 WHERE jobID = " + arg1;
        System.out.println(sql);
        int rs = myStatement.executeUpdate(sql);

        System.out.println("Successfully Changed the status of Job Posting!");
    }

    public static void DeleteJobPosting(Connection con, String arg1) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "DELETE FROM JobPostings WHERE jobID = " + arg1;
        int rs = myStatement.executeUpdate(sql);

        System.out.println("Successfully deleted a Job Posting!");
    }

    public static List<JobPosting> VolunteerSearch (Connection con, String arg1, String arg2, String arg3, String arg4) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "SELECT * FROM JobPostings WHERE ";
        List<JobPosting> jobPostings = new ArrayList<>();
        int currentLength = sql.length();
        if (arg1 !=null){
            sql = sql + "jobTitle = '" + arg1 + "'";
        }
        if (arg2 != null){
            if (sql.length() != currentLength) {
                currentLength = sql.length();
                sql = sql + " AND companyName = '" + arg2 + "'";
            } else {
                sql = sql + "companyName = '" + arg2 + "'";
            }
        }
        if (arg3 !=null){
            if (sql.length() != currentLength) {
                currentLength = sql.length();
                sql = sql + " AND location = '" + arg3 + "'";
            } else {
                sql = sql + "location = '" + arg3 + "'";
            }
        }
        if (sql.length() != currentLength) {
            sql = sql + " AND statusActive = 1";
        } else {
            sql = sql + "statusActive = 1";
        }
        System.out.println(sql);
        ResultSet rs = myStatement.executeQuery(sql);
        while(rs.next()){
            if (arg4 != null) {
                if (JobHasTag(con, Integer.toString(rs.getInt(1)), arg4)) {
                    JobPosting temp = new JobPosting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
                    jobPostings.add(temp);
                }
            } else {
                JobPosting temp = new JobPosting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
                jobPostings.add(temp);
            }
        }
        return jobPostings;
    }

    public static boolean JobHasTag(Connection con, String arg1, String arg2) {
        Boolean output = false;
        try{
            Statement myStatement = con.createStatement();
            String sql = "SELECT * FROM JobTags, TagsName WHERE JobTags.tagID = TagsName.tagID AND JobTags.jobID = " + arg1 + " AND TagsName.tagName = '" + arg2 + "'";
            ResultSet rs = myStatement.executeQuery(sql);

            while (rs.next()){
                output = true;
            }
            System.out.println(sql);
        } catch (SQLException e){
            System.out.println(e);
            return false;
        }
        return output;

    }
    public static List<Volunteer> RecruiterSearch (Connection con, String arg1) throws SQLException{
        Statement myStatement = con.createStatement();
        List<Volunteer> volunteers = new ArrayList();
        String sql = "SELECT Volunteer.volunteerID, Volunteer.volunteerName, Volunteer.age, Volunteer.description" +
                " FROM JobRequests, Volunteer WHERE JobRequests.volunteerID = Volunteer.volunteerID AND JobRequests.jobID = ";
        sql = sql + arg1;
        ResultSet rs = myStatement.executeQuery(sql);

        System.out.println("Voluntees Interested in Job");
        while(rs.next()){
            Volunteer temp = new Volunteer(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            volunteers.add(temp);
        }
        return volunteers;
    }

    public static List<Volunteer> RecruiterViewVoluntees (Connection con, String arg1) throws SQLException{
        Statement myStatement = con.createStatement();
        List<Volunteer> volunteers = new ArrayList();
        String sql = "SELECT Volunteer.volunteerID, Volunteer.volunteerName, Volunteer.age, Volunteer.description" +
                " FROM JobAssigned, Volunteer WHERE JobAssigned.volunteerID = Volunteer.volunteerID AND JobAssigned.jobID = ";
        sql = sql + arg1;
        ResultSet rs = myStatement.executeQuery(sql);

        System.out.println("Voluntees Interested in Job");
        while(rs.next()){
            Volunteer temp = new Volunteer(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            volunteers.add(temp);
        }
        return volunteers;
    }

    public static void VolunteerRequest(Connection con, String arg1, String arg2) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "INSERT INTO JobRequests (jobID, volunteerID) VALUES (" + arg1 + ", " + arg2+ ")";
        int rs = myStatement.executeUpdate(sql);
    }

    public static void RecruiterAssignJob(Connection con, String arg1, String arg2) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "INSERT INTO JobAssigned (jobID, volunteerID) VALUES (" + arg1 + ", " + arg2+ ")";
        int rs = myStatement.executeUpdate(sql);
    }

    public static List<JobPosting> VolunteerViewRequests(Connection con, String arg1) throws SQLException{
        List<JobPosting> jobPostings = new ArrayList<>();
        Statement myStatement = con.createStatement();
        String sql = "SELECT JobPostings.jobID, JobPostings.jobTitle, JobPostings.companyName, JobPostings.location, JobPostings.statusActive FROM JobRequests, JobPostings WHERE JobRequests.jobID = JobPostings.jobID AND JobRequests.volunteerID = " + arg1;
        ResultSet rs = myStatement.executeQuery(sql);
        System.out.println("Applied Jobs");
        while (rs.next()){
            JobPosting temp = new JobPosting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
            jobPostings.add(temp);
        }
        return jobPostings;
    }

    public static List<JobPosting> VolunteerViewAssigned(Connection con, String arg1) throws SQLException{
        List<JobPosting> jobPostings = new ArrayList<>();
        Statement myStatement = con.createStatement();
        String sql = "SELECT JobPostings.jobID, JobPostings.jobTitle, JobPostings.companyName, JobPostings.location, JobPostings.statusActive FROM JobAssigned, JobPostings WHERE JobAssigned.jobID = JobPostings.jobID AND JobAssigned.volunteerID = " + arg1;
        ResultSet rs = myStatement.executeQuery(sql);
        System.out.println("Assigned Jobs");
        while (rs.next()){
            JobPosting temp = new JobPosting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
            jobPostings.add(temp);
        }
        return jobPostings;
    }

    public static List<String> getJobTitles(Connection con) throws SQLException {
        List<String> jobTitles = new ArrayList<String>();
        Statement myStatement = con.createStatement();
        String sql = "SELECT DISTINCT JobPostings.jobTitle FROM JobPostings";
        ResultSet rs = myStatement.executeQuery(sql);
        while(rs.next()){
            jobTitles.add(rs.getString(1));
        }
        return jobTitles;
    }

    public static List<String> getCompanies(Connection con) throws SQLException {
        List<String> companyNames = new ArrayList<String>();
        Statement myStatement = con.createStatement();
        String sql = "SELECT DISTINCT JobPostings.companyName FROM JobPostings";
        ResultSet rs = myStatement.executeQuery(sql);
        while(rs.next()){
            companyNames.add(rs.getString(1));
        }
        return companyNames;
    }

    public static List<String> getRegions(Connection con) throws SQLException {
        List<String> regions = new ArrayList<String>();
        Statement myStatement = con.createStatement();
        String sql = "SELECT DISTINCT JobPostings.location FROM JobPostings";
        ResultSet rs = myStatement.executeQuery(sql);
        while(rs.next()){
            regions.add(rs.getString(1));
        }
        return regions;
    }

    public static List<String> getTags(Connection con) throws SQLException {
        List<String> tagCategories = new ArrayList<String>();
        Statement myStatement = con.createStatement();
        String sql = "SELECT DISTINCT TagsName.tagName FROM TagsName";
        ResultSet rs = myStatement.executeQuery(sql);
        while(rs.next()){
            tagCategories.add(rs.getString(1));
        }
        return tagCategories;
    }

    public static void addJobTags(Connection con, List<String> arg1, String arg2) throws SQLException {
        Statement myStatement = con.createStatement();

        for (String temp : arg1) {
            System.out.println(temp);
            int tagID = 0;
            tagID = getTagNumber(con, temp);
            if (tagID == -1){
                int tableSize = CountTuples(con, "TagsName");
                tableSize++;
                String sql = "INSERT INTO TagsName(tagID, tagName) VALUES ("+ tableSize + ", '"+ temp + "')";
                tagID = tableSize;
                int rs = myStatement.executeUpdate(sql);
                System.out.println(sql);
            }

            String sql = "INSERT INTO JobTags (jobID, tagID) VALUES (" + arg2 + ", " + tagID + ")";
            int rs = myStatement.executeUpdate(sql);

            System.out.println(sql);
        }
    }


    public static int getTagNumber(Connection con, String arg1) throws SQLException {
        int tagID = -1;
        Statement myStatement = con.createStatement();
        String sql = "SELECT TagsName.tagID FROM TagsName WHERE tagName = '" + arg1 + "'";
        ResultSet rs = myStatement.executeQuery(sql);
        while(rs.next()){
            tagID = rs.getInt(1);
        }
        return tagID;
    }

    public static int CountTuples(Connection con, String arg1){
        int size = 0;
        try {
            Statement myStatement = con.createStatement();
            String sql = "SELECT COUNT (*) FROM " + arg1;
            ResultSet rs = myStatement.executeQuery(sql);
            size = rs.getInt(1);
        } catch (SQLException e){
            System.out.println("Error in retrieving table");
        }
        return size;
    }

    public static List<Volunteer> MakeVolunteerObjects(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "SELECT * FROM Volunteer";
        ResultSet rs = myStatement.executeQuery(sql);
        List<Volunteer> volunteers = new ArrayList<>();
        while(rs.next()){
            Volunteer temp = new Volunteer(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            volunteers.add(temp);
        }
        return volunteers;
    }

    public static List<JobPosting> MakeJobPostingObjects(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "SELECT * FROM JobPostings";
        ResultSet rs = myStatement.executeQuery(sql);
        List<JobPosting> jobPostings = new ArrayList<>();
        while(rs.next()){
            JobPosting temp = new JobPosting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
            jobPostings.add(temp);
        }
        return jobPostings;
    }

    public static List<JobPosting> makeAvailableJobPostingObjects(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "SELECT * FROM JobPostings WHERE JobPostings.statusActive = 1";
        ResultSet rs = myStatement.executeQuery(sql);
        List<JobPosting> jobPostings = new ArrayList<>();
        while(rs.next()){
            JobPosting temp = new JobPosting(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
            jobPostings.add(temp);
        }
        return jobPostings;
    }


}

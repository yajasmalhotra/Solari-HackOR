package SQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void main (String []args){
        Connection myConnection = DBConnection.connect();
        try {
            InitializeRecruiter(myConnection);
            InitializeVolunteer(myConnection);
            InitializeJobPostings(myConnection);
            InitializeJobRequests(myConnection);
            InitializeJobAssigned(myConnection);
            InitializeTagsName(myConnection);
            InitializeJobTags(myConnection);

        } catch (SQLException e){
            System.out.print(e);
        }
    }

    public static void InitializeRecruiter(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "CREATE TABLE Recruiter(recruiterID int ,recruiterName text, companyName text)";
        int rs = myStatement.executeUpdate(sql);

        System.out.println("Recruiter Table has been Initialized");
    }

    public static void InitializeVolunteer(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "CREATE TABLE Volunteer(volunteerID int, volunteerName text, age int, description text)";
        int rs = myStatement.executeUpdate(sql);

        System.out.println("Volunteer Table has been Initialized");
    }

    public static void InitializeJobPostings(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "CREATE TABLE JobPostings(jobID int, jobTitle text, companyName text, location text, statusActive int)";
        int rs = myStatement.executeUpdate(sql);

        System.out.println("JobPostings Table has been Initialized");
    }

    public static void InitializeJobRequests(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "CREATE TABLE JobRequests(jobID int, volunteerID int)";
        int rs = myStatement.executeUpdate(sql);

        System.out.println("JobRequeusts Table has been Initialized");
    }

    public static void InitializeJobAssigned(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "CREATE TABLE JobAssigned(jobID int, volunteerID int)";
        int rs = myStatement.executeUpdate(sql);

        System.out.println("JobAssigned Table has been Initialized");
    }

    public static void InitializeTagsName(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "CREATE TABLE TagsName(tagID int, tagName text)";
        int rs = myStatement.executeUpdate(sql);

        System.out.println("TagsName Table has been Initialized");
    }
    public static void InitializeJobTags(Connection con) throws SQLException{
        Statement myStatement = con.createStatement();
        String sql = "CREATE TABLE JobTags(jobID int, tagID int)";
        int rs = myStatement.executeUpdate(sql);

        System.out.println("JobTags Table has been Initialized");
    }
}

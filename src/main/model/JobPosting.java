package model;

public class JobPosting {

    private int JobID;
    private String role;
    private String company;
    private String location;
    private boolean status;

    public JobPosting(int JobID, String role, String company, String location, boolean status) {
        this.JobID = JobID;
        this.role = role;
        this.company = company;
        this.location = location;
        this.status = status;
    }

    public int getJobID() {
        return JobID;
    }

    public void setJobID(int jobID) {
        this.JobID = jobID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

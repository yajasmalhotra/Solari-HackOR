package model;

import java.util.ArrayList;
import java.util.List;

public class Recruiter {

    private int RecruiterID;
    private String name;
    private String company;
    private List<String> tags;
    private List<JobPosting> postings;

    public Recruiter(int RecruiterID, String name, String company) {
        this.RecruiterID = RecruiterID;
        this.company = company;
        this.name = name;
        this.tags = new ArrayList<>();
        this.postings = new ArrayList<>();
    }

    public List<JobPosting> getPostings() {
        return postings;
    }

    public void addPosting(JobPosting posting) {
        if (!postings.contains(posting)) {
            postings.add(posting);
        }
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTags(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public int getRecruiterID() {
        return RecruiterID;
    }

    public void setRecruiterID(int recruiterID) {
        RecruiterID = recruiterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

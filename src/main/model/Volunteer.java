package model;

import java.util.ArrayList;
import java.util.List;

public class Volunteer {

    private int VolunteerID;
    private String name;
    private int age;
    private String description;
    private List<String> tags;

    public Volunteer(int VolunteerID, String name, int age, String description) {
        this.VolunteerID = VolunteerID;
        this.name = name;
        this.age = age;
        this.description = description;
        this.tags = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVolunteerID() {
        return VolunteerID;
    }

    public void setVolunteerID(int volunteerID) {
        VolunteerID = volunteerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTags(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }
}

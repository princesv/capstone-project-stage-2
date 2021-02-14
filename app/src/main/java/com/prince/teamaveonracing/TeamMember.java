package com.prince.teamaveonracing;

public class TeamMember {
    String name;
    String year;
    String contact;
    String image;
    String subteam;


    public TeamMember(String id, String name, String year, String contact, String image, String subteam) {
        this.name = name;
        this.year = year;
        this.contact = contact;
        this.image = image;
        this.subteam = subteam;
    }

    public TeamMember(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubteam() {
        return subteam;
    }

    public void setSubteam(String subteam) {
        this.subteam = subteam;
    }
}

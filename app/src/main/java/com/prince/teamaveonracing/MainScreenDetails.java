package com.prince.teamaveonracing;

public class MainScreenDetails {
    String content;
    String image;

    public MainScreenDetails(String content, String image) {
        this.content = content;
        this.image = image;
    }

    public MainScreenDetails() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

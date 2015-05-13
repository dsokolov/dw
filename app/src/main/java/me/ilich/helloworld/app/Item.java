package me.ilich.helloworld.app;

public class Item {

    private final String title;
    private final String description;

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
}

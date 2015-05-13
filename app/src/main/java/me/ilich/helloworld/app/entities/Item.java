package me.ilich.helloworld.app.entities;

import java.util.List;

public class Item {

    private final String title;
    private final String description;
    private boolean movable = true;

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void onMove(List<Item> fromItems, List<Item> toItems) {
        if (movable) {
            fromItems.remove(this);
            toItems.add(this);
        }
    }

}

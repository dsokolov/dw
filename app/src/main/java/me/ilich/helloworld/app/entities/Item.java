package me.ilich.helloworld.app.entities;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String title;
    private String description;
    private boolean pickable = true;
    private boolean containable = false;
    private List<Item> items = new ArrayList<>();

    private Item() {

    }

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

    public boolean isPickable() {
        return pickable;
    }

    public boolean isContainable() {
        return containable;
    }

    public void onMove(List<Item> fromItems, List<Item> toItems) {
        fromItems.remove(this);
        toItems.add(this);
    }

    public List<Item> getItems() {
        return items;
    }

    public static class Builder {

        private Item item = new Item();

        public Builder title(String s) {
            item.title = s;
            return this;
        }

        public Builder description(String s) {
            item.description = s;
            return this;
        }

        public Builder pickable(boolean b) {
            item.pickable = b;
            return this;
        }

        public Builder containable(boolean b) {
            item.containable = b;
            return this;
        }

        public Builder item(Item subItem) {
            item.items.add(subItem);
            return this;
        }

        public Item build() {
            return item;
        }

    }

}

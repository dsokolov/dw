package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Entity;
import me.ilich.helloworld.app.entities.primitives.Scenable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Item extends Entity implements Titlelable, Scenable {

    private Titlelable titlelable;
    private Scenable scenable;
    private boolean pickable = true;
    private boolean containable = false;
    private List<Item> items = new ArrayList<>();

    private Item(UUID id, UUID parentId) {
        super(id, parentId);
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

    @Override
    public String getTitle() {
        return titlelable.getTitle();
    }

    @Override
    public void onScene(Controller controller) {
        scenable.onScene(controller);
    }

    public static class Builder {

        private final Item item;

        public Builder(UUID id, UUID parentId) {
            item = new Item(id, parentId);
        }

        public Builder title(String s) {
            item.titlelable = new Titlelable.Impl(s);
            return this;
        }

        public Builder scene(String s) {
            item.scenable = new Scenable.Impl(s);
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
            if (item.titlelable == null) {
                item.titlelable = new Titlelable.Impl("");
            }
            if (item.scenable == null) {
                item.scenable = new Scenable.Impl("");
            }
            return item;
        }

    }

}

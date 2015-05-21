package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.*;

import java.util.UUID;

public class PickableItem extends Entity implements Titlelable, Scenable, Lookable, Pickable {

    private Titlelable titlelable;
    private Scenable scenable;
    private Lookable lookable;
    private Pickable pickable;

    private PickableItem(UUID id, UUID parentId) {
        super(id, parentId);
        pickable = new Pickable.Impl();
    }

    @Override
    public String getTitle() {
        return titlelable.getTitle();
    }

    @Override
    public void onScene(Controller controller) {
        scenable.onScene(controller);
    }

    @Override
    public void onLook(Controller controller) {
        lookable.onLook(controller);
    }

    @Override
    public void onPickup(Controller controller, Entity entity, Player player) {
        pickable.onPickup(controller, entity, player);
    }

    public static class Builder {

        private final PickableItem item;

        public Builder(UUID id, UUID parentId) {
            item = new PickableItem(id, parentId);
        }

        public Builder title(String s) {
            item.titlelable = new Titlelable.Impl(s);
            return this;
        }

        public Builder scene(String s) {
            item.scenable = new Scenable.Impl(s);
            return this;
        }

        public Builder look(String s) {
            item.lookable = new Lookable.Impl(s);
            return this;
        }

        public PickableItem build() {
            if (item.titlelable == null) {
                item.titlelable = new Titlelable.Impl("");
            }
            if (item.scenable == null) {
                item.scenable = new Scenable.Impl("");
            }
            if (item.lookable == null) {
                item.lookable = new Lookable.Impl("");
            }
            return item;
        }

    }

}

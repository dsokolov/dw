package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Lookable;
import me.ilich.helloworld.app.entities.primitives.Pickable;
import me.ilich.helloworld.app.entities.primitives.Scenable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.UUID;

public class PickableItem extends Entity implements Titlelable, Scenable, Lookable, Pickable {

    private Titlelable titlelable;
    private Scenable scenable;
    private Lookable lookable;
    private Pickable pickable;

    private PickableItem(Entity parent) {
        super(parent);
        pickable = new Pickable.Impl();
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

    @Override
    public String getTitle(int index) {
        return titlelable.getTitle(index);
    }

    @Override
    public boolean isTitleSuitable(String s) {
        return titlelable.isTitleSuitable(s);
    }

    public static class Builder {

        private final PickableItem item;

        public Builder(Entity parent) {
            item = new PickableItem(parent);
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

package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Containable;
import me.ilich.helloworld.app.entities.primitives.Lookable;
import me.ilich.helloworld.app.entities.primitives.Scenable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.UUID;

public class ContainerItem extends Entity implements Titlelable, Scenable, Lookable, Containable {

    protected Titlelable titlelable;
    protected Scenable scenable;
    protected Lookable lookable;
    private Containable containable;

    protected ContainerItem(Builder builder) {
        super(builder);
        titlelable = builder.titlelable;
        scenable = builder.scenable;
        lookable = builder.lookable;
        containable = new Containable.Impl();
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
    public void onPutInContainer(Controller controller, Entity container, Entity item) {
        containable.onPutInContainer(controller, container, item);
    }

    @Override
    public String getTitle(int index) {
        return titlelable.getTitle(index);
    }

    @Override
    public boolean isTitleSuitable(String s) {
        return titlelable.isTitleSuitable(s);
    }

    public static class Builder<B extends Builder<B>> extends Entity.Builder<Builder<B>> {

        private Titlelable titlelable;
        private Scenable scenable;
        private Lookable lookable;

        public Builder(Entity parent) {
            super(parent);
        }

        @Override
        protected B getThis() {
            return (B) this;
        }

        public B title(String s) {
            titlelable = new Titlelable.Impl(s);
            return getThis();
        }

        public B scene(String s) {
            scenable = new Scenable.Impl(s);
            return getThis();
        }

        public Builder look(String s) {
            lookable = new Lookable.Impl(s);
            return this;
        }

        @Override
        public ContainerItem build() {
            return new ContainerItem(this);
        }

    }

}

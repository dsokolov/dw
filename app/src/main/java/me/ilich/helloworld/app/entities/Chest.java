package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Openable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.List;

/**
 * Created by disokolov on 04.06.15.
 */
public class Chest extends ContainerItem implements Openable {

    private Openable openable;

    protected Chest(Builder builder) {
        super(builder);
        openable = new Openable.Impl();
    }

    @Override
    public void open(Controller controller) {
        openable.open(controller);
    }

    @Override
    public void close(Controller controller) {
        openable.close(controller);
    }

    @Override
    public void onLook(Controller controller) {
        lookable.onLook(controller);
        controller.println(String.format("В %s можно что-нибудь положить.", TitleUtils.v(this)));
        switch (openable.getOpenState()) {
            case OPEN:
                controller.println("Открыто");
                List<Entity> containItems = controller.getChildEntities(getId(), Titlelable.class);
                switch (containItems.size()) {
                    case 0:
                        controller.println(String.format("Сейчас внутри %s пусто.", TitleUtils.r(this)));
                        break;
                    default:
                        controller.println(String.format("Сейчас %s содержит в себе:", TitleUtils.i(this)));
                        containItems.forEach(entity1 -> controller.println(String.format("\t%s", TitleUtils.v(entity1))));
                }
                break;
            case CLOSE:
                controller.println("Закрыто");
                break;
        }
    }

    @Override
    public OpenState getOpenState() {
        return openable.getOpenState();
    }

    public static class Builder<B extends Builder<B>> extends ContainerItem.Builder<Builder<B>> {

        public Builder(Entity parent) {
            super(parent);
        }

        @Override
        protected B getThis() {
            return (B) this;
        }

        @Override
        public Chest build() {
            return new Chest(this);
        }

    }

}

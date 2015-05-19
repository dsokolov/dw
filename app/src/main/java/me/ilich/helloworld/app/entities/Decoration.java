package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Entity;
import me.ilich.helloworld.app.entities.primitives.Lookable;
import me.ilich.helloworld.app.entities.primitives.Scenable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.UUID;

public class Decoration extends Entity implements Titlelable, Scenable, Lookable {

/*    public static Decoration fromJson(JSONObject jsonObject) {

    }*/

    private final Titlelable titlelable;
    private final Scenable scenable;
    private final Lookable lookable;

    public Decoration(UUID id, UUID parentId, String title, String scene, String lookText) {
        super(id, parentId);
        this.titlelable = new Titlelable.Impl(title);
        this.scenable = new Scenable.Impl(scene);
        this.lookable = new Lookable.Impl(lookText);
    }

    @Override
    public void onLook(Controller controller) {
        lookable.onLook(controller);
    }

    @Override
    public String getTitle() {
        return titlelable.getTitle();
    }

    @Override
    public void onScene(Controller controller) {
        scenable.onScene(controller);
    }

}

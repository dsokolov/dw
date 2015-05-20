package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;

public interface Pickable extends Primitive {

    void onPickup(Controller controller);

}

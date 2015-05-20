package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;

public interface Dropable extends Primitive {

    void onDrop(Controller controller);
}

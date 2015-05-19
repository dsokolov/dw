package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;

public interface Scenable {

    void onScene(Controller controller);

    class Impl implements Scenable {

        private final String sceneText;

        public Impl(String sceneText) {
            this.sceneText = sceneText;
        }

        @Override
        public void onScene(Controller controller) {
            controller.println(sceneText);
        }

    }

}

package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;

public interface Lookable {

    void onLook(Controller controller);

    class Impl implements Lookable {

        private final String lookText;

        public Impl(String lookText) {
            this.lookText = lookText;
        }

        @Override
        public void onLook(Controller controller) {
            controller.println(lookText);
        }

    }

}

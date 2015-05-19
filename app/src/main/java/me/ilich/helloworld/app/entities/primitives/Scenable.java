package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;
import org.json.JSONException;
import org.json.JSONObject;

public interface Scenable extends Primitive {

    void onScene(Controller controller);

    class Impl extends Primitive.Impl implements Scenable {

        private final String sceneText;

        public Impl(String sceneText) {
            this.sceneText = sceneText;
        }

        @Override
        public void onScene(Controller controller) {
            if (sceneText != null && !sceneText.isEmpty()) {
                controller.println(sceneText);
            }
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return null;
        }

        @Override
        public String toString() {
            return sceneText;
        }

    }

}

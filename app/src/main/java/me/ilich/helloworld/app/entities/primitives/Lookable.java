package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;
import org.json.JSONException;
import org.json.JSONObject;

public interface Lookable extends Primitive {

    void onLook(Controller controller);

    class Impl extends Primitive.Impl implements Lookable {

        private final String lookText;

        public Impl(JSONObject jsonObject) {
            lookText = jsonObject.optString("look");
        }

        public Impl(String lookText) {
            this.lookText = lookText;
        }

        @Override
        public void onLook(Controller controller) {
            if (lookText == null || lookText.isEmpty()) {
                controller.println("Ничего особенного");
            } else {
                controller.println(lookText);
            }
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return jsonObject.put("look", lookText);
        }

    }

}

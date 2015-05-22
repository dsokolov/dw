package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;
import org.json.JSONException;
import org.json.JSONObject;

public interface Enterable extends Primitive {

    void onEnter(Controller controller);

    class Impl extends Primitive.Impl implements Enterable {

        @Override
        public void onEnter(Controller controller) {
            controller.println("enter");
            //TODO
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return jsonObject;
        }
    }

}

package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import org.json.JSONException;
import org.json.JSONObject;

public interface Containable extends Primitive {

    void onPutInContainer(Controller controller, Entity container, Entity item);

    class Impl extends Primitive.Impl implements Containable {

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return jsonObject;
        }

        @Override
        public void onPutInContainer(Controller controller, Entity container, Entity item) {
            item.setParentId(container.getId());
        }
    }

}

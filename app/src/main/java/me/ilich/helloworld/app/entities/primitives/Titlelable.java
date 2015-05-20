package me.ilich.helloworld.app.entities.primitives;

import org.json.JSONException;
import org.json.JSONObject;

public interface Titlelable extends Primitive {

    String getTitle();

    class Impl extends Primitive.Impl implements Titlelable {

        private final String title;

        public Impl(String title) {
            this.title = title;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            JSONObject j = new JSONObject();
            j.put("text", title);
            jsonObject.put("title", j);
            return jsonObject;
        }
    }

}

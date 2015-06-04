package me.ilich.helloworld.app.entities.primitives;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface Titlelable extends Primitive {

    String getTitle(int index);

    boolean isTitleSuitable(String s);

    class Impl extends Primitive.Impl implements Titlelable {

        private final String[] title;

        public Impl(String title) {
            this.title = title.split("\\|");
        }

        @Override
        public String getTitle(int i) {
            return title[i];
        }

        @Override
        public boolean isTitleSuitable(String s) {
            boolean b = false;
            for (String t : title) {
                if (t.toLowerCase().startsWith(s.toLowerCase())) {
                    b = true;
                    break;
                }
            }
            return b;
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            JSONArray jsonArray = new JSONArray();
            for (String s : title) {
                jsonArray.put(s);
            }
            jsonObject.put("title", jsonArray);
            return jsonObject;
        }
    }

}

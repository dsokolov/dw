package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.entities.Entity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface Titlelable extends Primitive {

    class Utils {

        private static String firstUpper(String s) {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }

    }

    static boolean isSuitable(Entity entity, String s) {
        return ((Titlelable) entity).isTitleSuitable(s);
    }

    /**
     * кто, что
     *
     * @param entity
     * @return
     */
    static String i(Entity entity) {
        return ((Titlelable) entity).getTitle(0);
    }

    /**
     * кого, что
     *
     * @param entity
     * @return
     */
    static String r(Entity entity) {
        return ((Titlelable) entity).getTitle(1);
    }

    /**
     * кому, чему
     *
     * @param entity
     * @return
     */
    static String d(Entity entity) {
        return ((Titlelable) entity).getTitle(2);
    }

    /**
     * кому, чему
     *
     * @param entity
     * @return
     */
    static String D(Entity entity) {
        return Utils.firstUpper(((Titlelable) entity).getTitle(2));
    }

    /**
     * кого, что
     *
     * @param entity
     * @return
     */
    static String v(Entity entity) {
        return ((Titlelable) entity).getTitle(3);
    }

    static String V(Entity entity) {
        return Utils.firstUpper(((Titlelable) entity).getTitle(3));
    }

    /**
     * кем, чем
     *
     * @param entity
     * @return
     */
    static String t(Entity entity) {
        return ((Titlelable) entity).getTitle(4);
    }

    /**
     * о ком, о чём
     *
     * @param entity
     * @return
     */
    static String p(Entity entity) {
        return ((Titlelable) entity).getTitle(5);
    }

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

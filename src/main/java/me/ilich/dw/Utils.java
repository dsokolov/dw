package me.ilich.dw;

import org.json.JSONArray;

public class Utils {

    private Utils() {

    }

    public static String[] jsonArrayToStringArray(JSONArray jsonArray) {
        final String[] result;
        if (jsonArray == null) {
            result = new String[]{};
        } else {
            result = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                String s = jsonArray.optString(i);
                result[i] = s;
            }
        }
        return result;
    }

}

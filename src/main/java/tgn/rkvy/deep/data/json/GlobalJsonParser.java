package tgn.rkvy.deep.data.json;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

class GlobalJsonParser {

    private final Map<String, String> globalConstants;

    public GlobalJsonParser(Map<String, String> globalConstants) {
        this.globalConstants = globalConstants;
    }

    public void parse(JSONObject globalJsonObject) {
        if (globalJsonObject != null) {
            Iterator iterator = globalJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next() + "";
                String value = globalJsonObject.optString(key);
                globalConstants.put(key, value);
            }
        }
    }
}

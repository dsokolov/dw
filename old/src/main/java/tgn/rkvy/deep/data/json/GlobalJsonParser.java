package tgn.rkvy.deep.data.json;

import org.json.JSONObject;

import java.util.Iterator;

class GlobalJsonParser {

    private JsonParser.OnParsedListener onParsedListener;

    public GlobalJsonParser(JsonParser.OnParsedListener onParsedListener) {
        this.onParsedListener = onParsedListener;
    }

    public void parse(JSONObject globalJsonObject) {
        if (globalJsonObject != null) {
            Iterator iterator = globalJsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next() + "";
                String value = globalJsonObject.optString(key);
                onParsedListener.onGlobalConstant(key, value);
            }
        }
    }
}

package me.ilich.helloworld.app.datasource.json;

import me.ilich.helloworld.app.entities.Room;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class JsonDataSource {

    private final JsonParser jsonParser = new JsonParser(new JsonParser.OnParsedListener() {

        @Override
        public void onRoom(Room room) {

        }

    });

    public JsonDataSource() {
        String[] fileNames = new String[]{
                "sandbox.json"
        };
        for (String fileName : fileNames) {
            try (
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
                    StringWriter writer = new StringWriter()
            ) {
                IOUtils.copy(inputStream, writer);
                String s = writer.toString();
                JSONObject jsonObject = new JSONObject(s);
                //jsonParser.parse(jsonObject);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }


}

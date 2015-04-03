package me.ilich.dw.seeds;

import me.ilich.dw.data.Seed;
import me.ilich.dw.data.UuidSeed;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VkSeedSource extends SeedSource {

    // https://api.vk.com/method/users.get?user_id=2682551
    // https://api.vk.com/method/friends.get?user_id=2682551
    private final String PATTERN = "https://api.vk.com/method/friends.get?user_id=%s";

    public VkSeedSource() {
    }

    @Override
    public String getStartTag() {
        //int id = new Random().nextInt(9999999);
        int id = 2682551;
        //int id = 46810449;
        return Integer.toString(id);
    }

    @Override
    public String getRandomTag() {
        int id = new Random().nextInt(9999999);
        return Integer.toString(id);
    }

    @Override
    public void load(String tag) {
        Seed currentSeed = new UuidSeed(tag);
        setCurrentSeed(currentSeed);
        try {
            URL url = new URL(String.format(PATTERN, tag));
            URLConnection connection = url.openConnection();
            try (
                    InputStream inputStream = connection.getInputStream();
                    StringWriter writer = new StringWriter()
            ) {
                IOUtils.copy(inputStream, writer);
                String s = writer.toString();
                JSONObject jsonObject = new JSONObject(s);
                List<Seed> directionSeeds = parse(jsonObject);
                setDirectionSeeds(directionSeeds);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Seed> parse(JSONObject jsonObject) {
        List<Seed> seedList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.optJSONArray("response");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                long l = jsonArray.optLong(i);
                Seed seed = new UuidSeed(Long.toString(l));
                seedList.add(seed);
            }
        }
        return seedList;
    }

}

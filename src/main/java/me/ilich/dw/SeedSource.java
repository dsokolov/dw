package me.ilich.dw;

import me.ilich.dw.data.Seed;

import java.util.List;

public abstract class SeedSource {

    // https://api.vk.com/method/users.get?user_id=2682551
    // https://api.vk.com/method/friends.get?user_id=2682551
    private final String PATTERN = "https://api.vk.com/method/friends.get?user_id=%s";

    private Seed currentSeed;
    private List<Seed> directionSeeds;

    public abstract void load(String tag);
/*        try {
            URL url = new URL(String.format(PATTERN, tag));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/

    public Seed getCurrentSeed() {
        return currentSeed;
    }

    protected void setCurrentSeed(Seed seed) {
        currentSeed = seed;
    }

    public List<Seed> getDirectionSeeds() {
        return directionSeeds;
    }

    protected void setDirectionSeeds(List<Seed> directionSeeds) {
        this.directionSeeds = directionSeeds;
    }
}

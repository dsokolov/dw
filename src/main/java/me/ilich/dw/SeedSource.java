package me.ilich.dw;

import me.ilich.dw.data.Seed;

import java.util.List;

public abstract class SeedSource {

    private Seed currentSeed;
    private List<Seed> directionSeeds;

    public abstract void load(String tag);

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

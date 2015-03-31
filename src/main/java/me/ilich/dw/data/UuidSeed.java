package me.ilich.dw.data;

import java.util.UUID;


public class UuidSeed implements Seed {

    private final String s;

    public UuidSeed(UUID uuid) {
        s = uuid.toString().toUpperCase();
    }


    @Override
    public String getSettingId() {
        return s.substring(0, 1);
    }

    @Override
    public String getRoomId() {
        return s.substring(2, 4);
    }
}
